package com.ranga.array;

import java.util.Arrays;

/**
 * Given an array {@code nums}, return {@code true} if it was originally sorted in non-decreasing order and then rotated by some number of positions (possibly zero), and {@code false} otherwise. The array may contain duplicates.
 *
 * <p><b>Algorithm Strategy (Count the Descents Circularly):</b>
 * <ol>
 *   <li>In a sorted array that has been rotated, there is at most one place where a value is greater than the next one.</li>
 *   <li>Walk the array and count the indexes {@code i} where {@code nums[i] > nums[(i + 1) % n]}, including the wrap-around from the last element back to the first.</li>
 *   <li>If the count is at most 1, the array is a rotation of a sorted array.</li>
 *   <li>Otherwise it is not.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 4, 5, 1, 2]} the only descent is {@code 5 > 1}; the wrap-around {@code 2 <= 3} is fine.</li>
 *   <li>Result: {@code true}. For {@code [2, 1, 3, 4]} there are two descents ({@code 2 > 1} and {@code 4 > 2}), so the result is {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A counter.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CheckIfArrayIsSortedAndRotated {

    public static void main(String[] args) {
        int[][] inputs = {{3, 4, 5, 1, 2}, {2, 1, 3, 4}, {1, 2, 3}, {1, 1, 1}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + check(nums));
            System.out.println();
        }
    }

    public static boolean check(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return true;
        }
        int descents = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > nums[(i + 1) % nums.length]) {
                descents++;
            }
        }
        return descents <= 1;
    }
}
