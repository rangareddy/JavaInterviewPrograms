package com.ranga.array;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Given an integer array {@code nums} and a window size {@code k}, the window slides from the far left to the far right one position at a time. Return the maximum of every window.
 *
 * <p><b>Algorithm Strategy (Monotonic Decreasing Deque of Indices):</b>
 * <ol>
 *   <li>Keep a deque of indices whose values are in decreasing order; the front always holds the index of the maximum of the current window.</li>
 *   <li>For each new index {@code i}, remove indices from the back while their values are not larger than {@code nums[i]} (they can never be a maximum again), then add {@code i} at the back.</li>
 *   <li>Remove the front index when it has slid out of the window ({@code front <= i - k}).</li>
 *   <li>Once the first window is complete ({@code i >= k - 1}), the value at the front is the maximum of the window; record it.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 3, -1, -3, 5, 3, 6, 7]} and {@code k = 3}: the deque holds the indices of 3, -1 (after the first three elements), so the first maximum is 3.</li>
 *   <li>Result: {@code [3, 3, 5, 5, 6, 7]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index enters and leaves the deque at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(k)} — The deque holds at most {@code k} indices.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SlidingWindowMaximum {

    public static void main(String[] args) {
        int[][] inputs = {{1, 3, -1, -3, 5, 3, 6, 7}, {1}, {9, 11}, {4, 3, 2, 1}};
        int[] ks = {3, 1, 2, 2};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + Arrays.toString(maxSlidingWindow(inputs[i], ks[i])));
            System.out.println();
        }
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();      // indices, values in decreasing order
        for (int i = 0; i < n; i++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
            if (deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
}
