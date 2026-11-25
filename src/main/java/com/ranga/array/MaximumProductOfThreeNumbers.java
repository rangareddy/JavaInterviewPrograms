package com.ranga.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums}, find three numbers whose product is maximum and return the maximum product.
 *
 * <p><b>Algorithm Strategy (Track the Three Largest and Two Smallest):</b>
 * <ol>
 *   <li>The best product is either the three largest numbers, or the two smallest (most negative) numbers times the largest number.</li>
 *   <li>Walk the array once and keep {@code max1 >= max2 >= max3} and {@code min1 <= min2}.</li>
 *   <li>Compare {@code max1 * max2 * max3} with {@code min1 * min2 * max1}.</li>
 *   <li>Return the larger one (use {@code long} internally to avoid overflow in intermediate values).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [-10, -10, 5, 2]}: the top three are 5, 2, -10 (product -100) but the two smallest -10, -10 with the largest 5 give 500.</li>
 *   <li>Result: {@code 500}. For {@code [1, 2, 3, 4]} it is {@code 24}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Five variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumProductOfThreeNumbers {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 4}, {-10, -10, 5, 2}, {-1, -2, -3}, {1, 2, 3}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + maximumProduct(nums));
            System.out.println();
        }
    }

    public static int maximumProduct(int[] nums) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num > max1) {
                max3 = max2;
                max2 = max1;
                max1 = num;
            } else if (num > max2) {
                max3 = max2;
                max2 = num;
            } else if (num > max3) {
                max3 = num;
            }
            if (num < min1) {
                min2 = min1;
                min1 = num;
            } else if (num < min2) {
                min2 = num;
            }
        }
        return (int) Math.max((long) max1 * max2 * max3, (long) min1 * min2 * max1);
    }
}
