package com.ranga.heap;

import java.util.Arrays;

/**
 * Given an array of integers {@code nums}, choose two different indices {@code i} and {@code j} and return the maximum value of {@code (nums[i] - 1) * (nums[j] - 1)}.
 *
 * <p><b>Algorithm Strategy (Two Largest Values in One Pass):</b>
 * <ol>
 *   <li>Because the values are positive, the product {@code (a - 1) * (b - 1)} is largest for the two largest values.</li>
 *   <li>Keep {@code first} and {@code second}, the two largest values seen.</li>
 *   <li>For each number, update them: a new maximum pushes {@code first} into {@code second}, otherwise a number above {@code second} replaces it.</li>
 *   <li>Return {@code (first - 1) * (second - 1)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 4, 5, 2]} the two largest are 5 and 4.</li>
 *   <li>Result: {@code (5 - 1) * (4 - 1) = 12}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumProductOfTwoElementsInAnArray {

    public static void main(String[] args) {
        int[][] inputs = {{3, 4, 5, 2}, {1, 5, 4, 5}, {3, 7}, {10, 2, 5, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + maxProduct(nums));
            System.out.println();
        }
    }

    public static int maxProduct(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int first = 0;
        int second = 0;
        for (int num : nums) {
            if (num > first) {
                second = first;
                first = num;
            } else if (num > second) {
                second = num;
            }
        }
        return (first - 1) * (second - 1);
    }
}
