package com.ranga.blind75.dp;

import java.util.Arrays;

/**
 * Given an integer array {@code nums}, return the length of the longest strictly increasing subsequence (elements in order, not necessarily adjacent).
 *
 * <p><b>Algorithm Strategy (Patience Sorting with Binary Search):</b>
 * <ol>
 *   <li>Keep an array {@code tails} where {@code tails[i]} is the smallest possible last value of an increasing subsequence of length {@code i + 1} found so far. It is always sorted.</li>
 *   <li>For each number, binary-search the first position in {@code tails} whose value is {@code >= num}.</li>
 *   <li>If the position is at the end, the number extends the longest subsequence: append it. Otherwise replace that entry, which keeps a smaller tail for the same length and so leaves more room for later numbers.</li>
 *   <li>The length of {@code tails} is the length of the longest increasing subsequence. (The array is not itself the subsequence.)</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [10, 9, 2, 5, 3, 7, 101, 18]}: tails evolve as [10], [9], [2], [2,5], [2,3], [2,3,7], [2,3,7,101], [2,3,7,18].</li>
 *   <li>Result: {@code 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — One binary search per number.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The {@code tails} array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestIncreasingSubsequence {

    public static void main(String[] args) {
        int[][] inputs = {{10, 9, 2, 5, 3, 7, 101, 18}, {0, 1, 0, 3, 2, 3}, {7, 7, 7, 7}, {1, 2, 3, 4}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + lengthOfLIS(nums));
            System.out.println();
        }
    }

    public static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] tails = new int[nums.length];
        int size = 0;
        for (int num : nums) {
            // find the first tail >= num
            int low = 0;
            int high = size;
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (tails[mid] < num) {
                    low = mid + 1;
                } else {
                    high = mid;
                }
            }
            tails[low] = num;
            if (low == size) {
                size++;
            }
        }
        return size;
    }
}
