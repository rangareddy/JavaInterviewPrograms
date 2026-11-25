package com.ranga.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums}, find one continuous subarray such that sorting only this subarray in ascending order makes the whole array sorted. Return the length of the shortest such subarray.
 *
 * <p><b>Algorithm Strategy (Two Scans for the Boundaries):</b>
 * <ol>
 *   <li>Scan left to right keeping the running maximum. Any element smaller than it is out of place, so the last such index is the right boundary {@code end}.</li>
 *   <li>Scan right to left keeping the running minimum. Any element larger than it is out of place, so the last such index (the smallest one) is the left boundary {@code start}.</li>
 *   <li>If no element was out of place the array is already sorted and the answer is {@code 0}.</li>
 *   <li>Otherwise the answer is {@code end - start + 1}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 6, 4, 8, 10, 9, 15]}: moving right, 4 is below the running max 6 and 9 is below 10, so {@code end = 5}.</li>
 *   <li>Moving right to left, the running minimum becomes 4 at index 2, and 6 at index 1 is larger than it, so {@code start = 1}.</li>
 *   <li>Result: {@code 5 - 1 + 1 = 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Two linear scans.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ShortestUnsortedContinuousSubarray {

    public static void main(String[] args) {
        int[][] inputs = {{2, 6, 4, 8, 10, 9, 15}, {1, 2, 3, 4}, {1}, {2, 1}, {1, 3, 2, 2, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + findUnsortedSubarray(nums));
            System.out.println();
        }
    }

    public static int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int n = nums.length;
        int max = nums[0];
        int end = -1;
        for (int i = 1; i < n; i++) {
            if (nums[i] < max) {
                end = i;
            } else {
                max = nums[i];
            }
        }
        int min = nums[n - 1];
        int start = 0;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] > min) {
                start = i;
            } else {
                min = nums[i];
            }
        }
        return end == -1 ? 0 : end - start + 1;
    }
}
