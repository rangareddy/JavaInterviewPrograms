package com.ranga.heap;

import java.util.PriorityQueue;

/**
 * Design a class that finds the {@code k}-th largest element in a stream of numbers (the k-th largest in sorted order, not the k-th distinct one). {@code KthLargest(k, nums)} initialises it with an initial array and {@code add(val)} appends a number and returns the current k-th largest element.
 *
 * <p><b>Algorithm Strategy (Min-Heap of the k Largest Values):</b>
 * <ol>
 *   <li>Keep a min-heap that holds only the {@code k} largest values seen so far. The smallest of them (the top of the heap) is exactly the {@code k}-th largest.</li>
 *   <li>Initialise by adding every number of {@code nums} with the same rule as {@code add}.</li>
 *   <li>{@code add(val)}: push the value; if the heap now holds more than {@code k} values, remove the top (the smallest), which can no longer be the k-th largest.</li>
 *   <li>Return the top of the heap.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code k = 3} and {@code nums = [4, 5, 8, 2]} the heap holds {@code [4, 5, 8]}. {@code add(3)} changes nothing (3 is dropped): the answer is 4.</li>
 *   <li>{@code add(5)} gives heap {@code [5, 5, 8]}: 5. {@code add(10)}: {@code [5, 8, 10]}: 5. {@code add(9)}: {@code [8, 9, 10]}: 8. {@code add(4)}: 8.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log k)} — per {@code add}.</li>
 *   <li><b>Space Complexity:</b> {@code O(k)} — The heap never holds more than {@code k + 1} numbers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class KthLargestElementInAStream {

    private final int k;
    private final PriorityQueue<Integer> heap = new PriorityQueue<>();      // min-heap with the k largest values

    public KthLargestElementInAStream(int k, int[] nums) {
        this.k = k;
        for (int num : nums) {
            add(num);
        }
    }

    public static void main(String[] args) {
        KthLargestElementInAStream kthLargest = new KthLargestElementInAStream(3, new int[]{4, 5, 8, 2});
        System.out.println("Input  : KthLargest(3, [4, 5, 8, 2])");
        for (int val : new int[]{3, 5, 10, 9, 4}) {
            System.out.println("Input  : add(" + val + ")");
            System.out.println("Output : " + kthLargest.add(val));
        }
    }

    public int add(int val) {
        heap.add(val);
        if (heap.size() > k) {
            heap.poll();
        }
        return heap.peek();
    }
}
