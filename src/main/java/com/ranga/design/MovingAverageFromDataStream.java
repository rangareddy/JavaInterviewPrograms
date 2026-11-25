package com.ranga.design;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Given a stream of integers and a window size, calculate the moving average of the last {@code size} values. Implement {@code MovingAverage(size)} and {@code next(val)}, which adds {@code val} to the stream and returns the average of the last {@code size} values (or of all values if fewer have arrived).
 *
 * <p><b>Algorithm Strategy (Queue plus Running Sum):</b>
 * <ol>
 *   <li>Keep a queue of the values in the window and a running {@code sum}.</li>
 *   <li>On {@code next(val)}, add {@code val} to the queue and to the sum.</li>
 *   <li>If the queue now holds more than {@code size} values, remove the oldest one and subtract it from the sum.</li>
 *   <li>Return {@code sum / queue.size()} as a double.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>With {@code size = 3} and values 1, 10, 3, 5: the averages are 1.0, 5.5, 4.666..., then 6.0 (the window slides to 10, 3, 5).</li>
 *   <li>The first output is {@code 1.0}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) per call} — One enqueue, at most one dequeue.</li>
 *   <li><b>Space Complexity:</b> {@code O(size)} — The window.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MovingAverageFromDataStream {

    public static void main(String[] args) {
        MovingAverageFromDataStream average = new MovingAverageFromDataStream(3);
        int[] values = {1, 10, 3, 5};
        for (int value : values) {
            System.out.println("Input  : next(" + value + ")");
            System.out.println("Output : " + average.next(value));
            System.out.println();
        }
    }

    private final int size;
    private final Queue<Integer> window = new ArrayDeque<>();
    private long sum;

    public MovingAverageFromDataStream(int size) {
        this.size = Math.max(1, size);
    }

    public double next(int val) {
        window.add(val);
        sum += val;
        if (window.size() > size) {
            sum -= window.poll();
        }
        return (double) sum / window.size();
    }
}
