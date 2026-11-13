package com.ranga.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;

/**
 * Write a custom functional interface ({@code TriFunction}) and two custom stream collectors: {@code summarizing}, which computes count, sum, min, max and average in one pass, and {@code topN}, which keeps only the {@code n} largest elements without sorting the whole stream. Both must work for sequential and parallel streams.
 *
 * <p><b>Algorithm Strategy (Collector.of with a Mutable Accumulator and a Combiner):</b>
 * <ol>
 *   <li>A collector has four parts: a {@code supplier} that creates an empty mutable container, an {@code accumulator} that folds one element into it, a {@code combiner} that merges two containers (used by parallel streams) and an optional {@code finisher} that converts the container into the result.</li>
 *   <li>{@code summarizing} uses a small mutable {@code Stats} object as the container. {@code accept} updates count, sum, min and max, and {@code combine} adds another {@code Stats} into this one, so partial results from parallel threads can be merged.</li>
 *   <li>{@code topN} uses a {@code PriorityQueue} ordered by the comparator, so its head is the smallest of the elements kept so far. After each add, if the queue holds more than {@code n} elements, the head is removed. The combiner offers the elements of one queue to the other, and the finisher copies the queue into a list sorted from largest to smallest.</li>
 *   <li>{@code TriFunction} is a custom {@code @FunctionalInterface} with one abstract method of three arguments and a default {@code andThen} method that composes it with a {@code Function}, the same way {@code BiFunction.andThen} works.</li>
 *   <li>Each parallel thread fills its own container, so no locking is needed. This only works because the accumulator and combiner never share state between containers.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the words {@code map, collector, stream, on, identity} (lengths 3, 9, 6, 2, 8): {@code summarizing} gives count {@code 5}, sum {@code 28}, min {@code 2}, max {@code 9}, average {@code 5.6}.</li>
 *   <li>{@code topN(2, byLength)} offers every word to a min-heap of size 2: after {@code collector} and {@code stream} the heap holds 9 and 6; {@code identity} (8) pushes the heap to three and the smallest (6) is removed, so the heap ends as 8 and 9. The finisher returns {@code [collector, identity]}.</li>
 *   <li>{@code volume.andThen(v -> "volume=" + v).apply(2, 3, 4)} computes {@code 24} and then formats it: {@code volume=24}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n) for summarizing, O(n log k) for topN} — {@code topN} does one heap operation of cost {@code O(log k)} per element, {@code k} being the requested size.</li>
 *   <li><b>Space Complexity:</b> {@code O(1) for summarizing, O(k) for topN} — The accumulators hold only the statistics or the {@code k} kept elements.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CustomCollectors {

    public static void main(String[] args) {
        List<String> words = Arrays.asList("map", "collector", "stream", "on", "identity");
        System.out.println("Input  : words = " + words);
        System.out.println("Output : " + words.stream().collect(summarizing(String::length)));
        System.out.println("Output : " + words.stream().collect(topN(2, Comparator.comparingInt(String::length))));

        System.out.println();

        TriFunction<Integer, Integer, Integer, Integer> volume = (l, w, h) -> l * w * h;
        System.out.println("Input  : volume(2, 3, 4) composed with a formatter");
        System.out.println("Output : " + volume.andThen(v -> "volume=" + v).apply(2, 3, 4));
    }

    @FunctionalInterface
    public interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);

        default <V> TriFunction<A, B, C, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (a, b, c) -> after.apply(apply(a, b, c));
        }
    }

    public static final class Stats {
        private long count;
        private long sum;
        private int min = Integer.MAX_VALUE;
        private int max = Integer.MIN_VALUE;

        void accept(int value) {
            count++;
            sum += value;
            min = Math.min(min, value);
            max = Math.max(max, value);
        }

        Stats combine(Stats other) {
            count += other.count;
            sum += other.sum;
            min = Math.min(min, other.min);
            max = Math.max(max, other.max);
            return this;
        }

        public long getCount() { return count; }
        public long getSum() { return sum; }
        public int getMin() { return min; }
        public int getMax() { return max; }
        public double getAverage() { return count == 0 ? 0.0 : (double) sum / count; }

        @Override
        public String toString() {
            return "count=" + count + ", sum=" + sum + ", min=" + min + ", max=" + max + ", avg=" + getAverage();
        }
    }

    public static <T> Collector<T, ?, Stats> summarizing(ToIntFunction<? super T> mapper) {
        return Collector.of(
                Stats::new,
                (stats, element) -> stats.accept(mapper.applyAsInt(element)),
                Stats::combine);
    }

    public static <T> Collector<T, ?, List<T>> topN(int n, Comparator<? super T> comparator) {
        return Collector.of(
                () -> new PriorityQueue<T>(Math.max(1, n + 1), comparator),
                (heap, element) -> {
                    heap.add(element);
                    if (heap.size() > n) {
                        heap.poll();
                    }
                },
                (left, right) -> {
                    for (T element : right) {
                        left.add(element);
                        if (left.size() > n) {
                            left.poll();
                        }
                    }
                    return left;
                },
                heap -> {
                    List<T> result = new ArrayList<>(heap);
                    result.sort(comparator.reversed());
                    return result;
                });
    }
}
