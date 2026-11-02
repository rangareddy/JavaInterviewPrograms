package com.ranga.blind75.heap;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Design a data structure that supports {@code addNum(int)} to add a number from a data stream and {@code findMedian()} to return the median of all numbers added so far.
 *
 * <p><b>Algorithm Strategy (Two Heaps):</b>
 * <ol>
 *   <li>Keep the smaller half of the numbers in a max-heap {@code low} and the larger half in a min-heap {@code high}.</li>
 *   <li>To add a number, push it into {@code low}, move the largest element of {@code low} to {@code high}, and if {@code high} became larger than {@code low}, move its smallest element back. This keeps {@code low.size() == high.size()} or {@code low.size() == high.size() + 1}.</li>
 *   <li>The median is the top of {@code low} when the total count is odd, otherwise the average of both tops.</li>
 *   <li>Every insert costs {@code O(log n)} and every median query {@code O(1)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Add 1, 2: low = {1}, high = {2}, median (1 + 2) / 2 = 1.5.</li>
 *   <li>Add 3: low = {1, 2}, high = {3}, median = 2.0.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — per {@code addNum}; {@code findMedian} is {@code O(1)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — All numbers are stored in the two heaps.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FindMedianFromDataStream {

    private final PriorityQueue<Integer> low = new PriorityQueue<>(Collections.reverseOrder());   // max-heap, smaller half
    private final PriorityQueue<Integer> high = new PriorityQueue<>();                             // min-heap, larger half

    public static void main(String[] args) {
        FindMedianFromDataStream medianFinder = new FindMedianFromDataStream();
        int[] stream = {1, 2, 3, 10, -5};
        for (int num : stream) {
            medianFinder.addNum(num);
            System.out.println("Input  : addNum(" + num + ")");
            System.out.println("Output : findMedian() = " + medianFinder.findMedian());
            System.out.println();
        }
    }

    public void addNum(int num) {
        low.add(num);
        high.add(low.poll());           // the largest of the small half moves to the large half
        if (high.size() > low.size()) {
            low.add(high.poll());       // rebalance so that low is never smaller than high
        }
    }

    public double findMedian() {
        if (low.isEmpty()) {
            throw new IllegalStateException("No numbers have been added.");
        }
        if (low.size() > high.size()) {
            return low.peek();
        }
        return (low.peek() + high.peek()) / 2.0;
    }
}
