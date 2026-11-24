package com.ranga.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums} and an integer {@code k}, find the contiguous subarray of length exactly {@code k} that has the maximum average value and return that value.
 *
 * <p><b>Algorithm Strategy (Fixed Sliding Window Sum):</b>
 * <ol>
 *   <li>Compute the sum of the first {@code k} elements.</li>
 *   <li>Slide the window one step at a time: add the entering element and subtract the leaving one.</li>
 *   <li>Track the maximum window sum.</li>
 *   <li>Divide the maximum sum by {@code k} at the end, as a {@code double}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 12, -5, -6, 50, 3]} and {@code k = 4}: the window sums are 2, 51 and 42.</li>
 *   <li>The best is 51, and {@code 51 / 4 = 12.75}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A running sum.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumAverageSubarrayI {

    public static void main(String[] args) {
        int[][] nums = {{1, 12, -5, -6, 50, 3}, {5}, {-1, -2, -3}, {0, 4, 0, 3, 2}};
        int[] ks = {4, 1, 2, 1};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + findMaxAverage(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static double findMaxAverage(int[] nums, int k) {
        if (nums == null || k <= 0 || nums.length < k) {
            return 0;
        }
        long sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        long best = sum;
        for (int i = k; i < nums.length; i++) {
            sum += nums[i] - nums[i - k];
            best = Math.max(best, sum);
        }
        return (double) best / k;
    }
}
