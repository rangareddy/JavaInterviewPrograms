package com.ranga.array;

import java.util.Arrays;

/**
 * Given a circular integer array {@code nums}, return the maximum possible sum of a non-empty subarray, where the subarray may wrap around the end of the array.
 *
 * <p><b>Algorithm Strategy (Max Subarray vs Total Minus Min Subarray):</b>
 * <ol>
 *   <li>Without wrapping, the answer is the usual maximum subarray sum (Kadane).</li>
 *   <li>With wrapping, the chosen elements are everything except one contiguous middle part, so the wrapped maximum is {@code total - minSubarray}.</li>
 *   <li>Compute {@code maxSub} and {@code minSub} in one pass with two Kadane runs.</li>
 *   <li>The answer is {@code max(maxSub, total - minSub)}; if all elements are negative ({@code maxSub < 0}), return {@code maxSub}, since {@code total - minSub} would be an empty subarray.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [5, -3, 5]}: the best non-wrapping sum is 7 and {@code total - minSub = 7 - (-3) = 10}, the wrapped subarray {@code [5, 5]}.</li>
 *   <li>For {@code [1, -2, 3, -2]} the best is {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few running values.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumSumCircularSubarray {

    public static void main(String[] args) {
        int[][] inputs = {{1, -2, 3, -2}, {5, -3, 5}, {-3, -2, -3}, {3, -1, 2, -1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + maxSubarraySumCircular(nums));
            System.out.println();
        }
    }

    public static int maxSubarraySumCircular(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int total = 0;
        int curMax = 0;
        int curMin = 0;
        int maxSub = Integer.MIN_VALUE;
        int minSub = Integer.MAX_VALUE;
        for (int x : nums) {
            total += x;
            curMax = Math.max(curMax + x, x);
            maxSub = Math.max(maxSub, curMax);
            curMin = Math.min(curMin + x, x);
            minSub = Math.min(minSub, curMin);
        }
        return maxSub < 0 ? maxSub : Math.max(maxSub, total - minSub);
    }
}
