package com.ranga.array;

import java.util.Arrays;

/**
 * Given an array of positive integers {@code nums} and an integer {@code k}, return the number of contiguous subarrays whose product is strictly less than {@code k}.
 *
 * <p><b>Algorithm Strategy (Sliding Window Counting New Subarrays):</b>
 * <ol>
 *   <li>Keep a window {@code [left, right]} and its {@code product}. Because all numbers are positive, growing the window increases the product and shrinking it decreases it.</li>
 *   <li>Extend {@code right} and multiply {@code product} by {@code nums[right]}.</li>
 *   <li>While {@code product >= k} (and {@code left <= right}), divide by {@code nums[left]} and advance {@code left}.</li>
 *   <li>Every subarray that ends at {@code right} and starts at or after {@code left} is valid, so add {@code right - left + 1} to the count.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [10, 5, 2, 6]} and {@code k = 100}: the window grows to {@code [10, 5, 2]} (product 100) which is too large, so {@code 10} leaves.</li>
 *   <li>Counting the new subarrays at each step gives 8.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index enters and leaves the window once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two pointers and a product.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SubarrayProductLessThanK {

    public static void main(String[] args) {
        int[][] nums = {{10, 5, 2, 6}, {1, 2, 3}, {1, 1, 1}, {}};
        int[] ks = {100, 0, 2, 5};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + numSubarrayProductLessThanK(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static int numSubarrayProductLessThanK(int[] nums, int k) {
        if (nums == null || k <= 1) {
            return 0;
        }
        int count = 0;
        int left = 0;
        long product = 1;
        for (int right = 0; right < nums.length; right++) {
            product *= nums[right];
            while (product >= k) {
                product /= nums[left++];
            }
            count += right - left + 1;
        }
        return count;
    }
}
