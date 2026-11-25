package com.ranga.array;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Given an integer array {@code nums} and an integer {@code limit}, return the size of the longest non-empty subarray in which the absolute difference between any two elements is at most {@code limit}.
 *
 * <p><b>Algorithm Strategy (Sliding Window with Min and Max Deques):</b>
 * <ol>
 *   <li>A window is valid when {@code max - min <= limit}.</li>
 *   <li>Maintain two monotonic deques: one with decreasing values (front is the window maximum) and one with increasing values (front is the minimum).</li>
 *   <li>Extend {@code right}; if the window becomes invalid, advance {@code left} and drop the deque fronts that left the window.</li>
 *   <li>The best window length is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [8, 2, 4, 7]} and {@code limit = 4}: the window {@code [2, 4]} has a spread of 2, and {@code [4, 7]} has a spread of 3; {@code [8, 2]} is too wide.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index enters and leaves each deque once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The deques.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestContinuousSubarrayWithAbsoluteDiffLessThanOrEqualToLimit {

    public static void main(String[] args) {
        int[][] nums = {{8, 2, 4, 7}, {10, 1, 2, 4, 7, 2}, {4, 2, 2, 2, 4, 4, 2, 2}, {1}};
        int[] limits = {4, 5, 0, 0};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", limit = " + limits[i]);
            System.out.println("Output : " + longestSubarray(nums[i], limits[i]));
            System.out.println();
        }
    }

    public static int longestSubarray(int[] nums, int limit) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Deque<Integer> maxQueue = new ArrayDeque<>();
        Deque<Integer> minQueue = new ArrayDeque<>();
        int left = 0;
        int best = 0;
        for (int right = 0; right < nums.length; right++) {
            while (!maxQueue.isEmpty() && maxQueue.peekLast() < nums[right]) {
                maxQueue.pollLast();
            }
            maxQueue.addLast(nums[right]);
            while (!minQueue.isEmpty() && minQueue.peekLast() > nums[right]) {
                minQueue.pollLast();
            }
            minQueue.addLast(nums[right]);
            while (maxQueue.peekFirst() - minQueue.peekFirst() > limit) {
                if (maxQueue.peekFirst() == nums[left]) {
                    maxQueue.pollFirst();
                }
                if (minQueue.peekFirst() == nums[left]) {
                    minQueue.pollFirst();
                }
                left++;
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
}
