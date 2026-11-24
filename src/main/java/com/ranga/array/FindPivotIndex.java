package com.ranga.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums}, return the leftmost pivot index: the index where the sum of the numbers strictly to its left equals the sum of the numbers strictly to its right. Return {@code -1} if no such index exists.
 *
 * <p><b>Algorithm Strategy (Running Left Sum):</b>
 * <ol>
 *   <li>Compute the total sum of the array once.</li>
 *   <li>Walk the indexes keeping {@code leftSum}, the sum of the elements before the current index.</li>
 *   <li>The right sum is {@code total - leftSum - nums[i]}. If it equals {@code leftSum}, return {@code i}.</li>
 *   <li>Add {@code nums[i]} to {@code leftSum} and continue. Return {@code -1} if the loop finishes.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 7, 3, 6, 5, 6]}: at index 3 the left sum is 11 and the right sum is {@code 28 - 11 - 6 = 11}.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Two passes.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few sums.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FindPivotIndex {

    public static void main(String[] args) {
        int[][] inputs = {{1, 7, 3, 6, 5, 6}, {1, 2, 3}, {2, 1, -1}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + pivotIndex(nums));
            System.out.println();
        }
    }

    public static int pivotIndex(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int total = 0;
        for (int x : nums) {
            total += x;
        }
        int leftSum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (leftSum == total - leftSum - nums[i]) {
                return i;
            }
            leftSum += nums[i];
        }
        return -1;
    }
}
