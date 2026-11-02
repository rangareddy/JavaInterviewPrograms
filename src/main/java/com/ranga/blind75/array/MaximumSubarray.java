package com.ranga.blind75.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums}, find the contiguous subarray with the largest sum and return that sum.
 *
 * <p><b>Algorithm Strategy (Kadane's Algorithm):</b>
 * <ol>
 *   <li>Keep {@code current}, the best sum of a subarray that ends at the current index, and {@code best}, the best sum seen anywhere.</li>
 *   <li>At each element decide whether to extend the previous subarray or start a new one: {@code current = max(num, current + num)}.</li>
 *   <li>A negative running sum can never help a later subarray, which is exactly why the "start new" option is taken in that case.</li>
 *   <li>Update {@code best = max(best, current)} and return it after the last element.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [-2, 1, -3, 4, -1, 2, 1, -5, 4]}: {@code current} is -2, 1, -2, 4, 3, 5, 6, 1, 5.</li>
 *   <li>The maximum is {@code 6}, the subarray {@code [4, -1, 2, 1]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — A single pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumSubarray {

    public static void main(String[] args) {
        int[][] inputs = {{-2, 1, -3, 4, -1, 2, 1, -5, 4}, {1}, {5, 4, -1, 7, 8}, {-3, -2, -5}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + maxSubArray(nums));
            System.out.println();
        }
    }

    public static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        int current = nums[0];
        int best = nums[0];
        for (int i = 1; i < nums.length; i++) {
            current = Math.max(nums[i], current + nums[i]);
            best = Math.max(best, current);
        }
        return best;
    }
}
