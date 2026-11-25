package com.ranga.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums} sorted in non-decreasing order, return an array of the squares of each number, also sorted in non-decreasing order.
 *
 * <p><b>Algorithm Strategy (Two Pointers from the Ends):</b>
 * <ol>
 *   <li>The largest square is at one of the two ends, because the array is sorted and negatives have large magnitudes.</li>
 *   <li>Keep {@code left = 0} and {@code right = n - 1} and fill the result from the back.</li>
 *   <li>Compare {@code |nums[left]|} with {@code |nums[right]|}: the larger one is squared and written at {@code pos}, then its pointer moves inward.</li>
 *   <li>Decrease {@code pos} and repeat until the pointers cross.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [-4, -1, 0, 3, 10]}: 10, then 16 (from -4), then 9, then 1, then 0 are placed from the back.</li>
 *   <li>Result: {@code [0, 1, 9, 16, 100]} after squaring.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The output array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SquaresOfASortedArray {

    public static void main(String[] args) {
        int[][] inputs = {{-4, -1, 0, 3, 10}, {-7, -3, 2, 3, 11}, {1, 2, 3}, {-5}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + Arrays.toString(sortedSquares(nums)));
            System.out.println();
        }
    }

    public static int[] sortedSquares(int[] nums) {
        if (nums == null) {
            return new int[0];
        }
        int n = nums.length;
        int[] result = new int[n];
        int left = 0;
        int right = n - 1;
        for (int pos = n - 1; pos >= 0; pos--) {
            if (Math.abs(nums[left]) > Math.abs(nums[right])) {
                result[pos] = nums[left] * nums[left];
                left++;
            } else {
                result[pos] = nums[right] * nums[right];
                right--;
            }
        }
        return result;
    }
}
