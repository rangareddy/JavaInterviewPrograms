package com.ranga.blind75.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums}, find the contiguous subarray with the largest product and return that product.
 *
 * <p><b>Algorithm Strategy (Track Both the Maximum and the Minimum Product):</b>
 * <ol>
 *   <li>A negative number turns the smallest (most negative) product into the largest one, so keep both {@code max} and {@code min} products of subarrays that end at the current index.</li>
 *   <li>For each number {@code n}, the new maximum is {@code max(n, max * n, min * n)} and the new minimum is {@code min(n, max * n, min * n)}.</li>
 *   <li>Compute both values from the old {@code max} and {@code min} (use a temporary variable) before overwriting them.</li>
 *   <li>A zero resets both to 0 automatically, which starts a fresh subarray. Keep the largest {@code max} seen as the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 3, -2, 4]}: (max, min) after each element is (2, 2), (6, 3), (-2, -12), (4, -48).</li>
 *   <li>The best maximum seen is {@code 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — A single pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Three variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumProductSubarray {

    public static void main(String[] args) {
        int[][] inputs = {{2, 3, -2, 4}, {-2, 0, -1}, {-2, 3, -4}, {0, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + maxProduct(nums));
            System.out.println();
        }
    }

    public static int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        int max = nums[0];
        int min = nums[0];
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int n = nums[i];
            int candidate1 = max * n;
            int candidate2 = min * n;
            max = Math.max(n, Math.max(candidate1, candidate2));
            min = Math.min(n, Math.min(candidate1, candidate2));
            result = Math.max(result, max);
        }
        return result;
    }
}
