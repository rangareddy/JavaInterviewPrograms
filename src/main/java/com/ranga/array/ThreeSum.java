package com.ranga.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an integer array {@code nums}, return all unique triplets {@code [nums[i], nums[j], nums[k]]} with distinct indices whose sum is 0. The result must not contain duplicate triplets.
 *
 * <p><b>Algorithm Strategy (Sort + Two Pointers):</b>
 * <ol>
 *   <li>Sort the array so that duplicates are adjacent and two pointers can be used.</li>
 *   <li>Fix the first number {@code nums[i]}. If it is greater than 0, stop: all remaining numbers are positive, so no triplet can sum to 0. Skip it if it equals the previous first number.</li>
 *   <li>Place {@code left = i + 1} and {@code right = n - 1}. Compute {@code sum = nums[i] + nums[left] + nums[right]}.</li>
 *   <li>If the sum is 0, record the triplet, move both pointers, and skip over equal values on both sides to avoid duplicate triplets. If the sum is negative increase {@code left}, if positive decrease {@code right}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Sorted {@code [-4, -1, -1, 0, 1, 2]}: for i = 1 (-1), left = -1 and right = 2 give 0, so {@code [-1, -1, 2]}; then 0 and 1 give {@code [-1, 0, 1]}.</li>
 *   <li>The second -1 is skipped as a first number. Result: {@code [[-1, -1, 2], [-1, 0, 1]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Sorting is {@code O(n log n)} and each first number does an {@code O(n)} two-pointer scan.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Ignoring the output list and the sort space.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ThreeSum {

    public static void main(String[] args) {
        int[][] inputs = {{-1, 0, 1, 2, -1, -4}, {0, 1, 1}, {0, 0, 0}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + threeSum(nums));
            System.out.println();
        }
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return result;
        }

        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int n = sorted.length;

        for (int i = 0; i < n - 2; i++) {
            if (sorted[i] > 0) {
                break;
            }
            if (i > 0 && sorted[i] == sorted[i - 1]) {
                continue;       // skip duplicate first numbers
            }

            int left = i + 1;
            int right = n - 1;
            while (left < right) {
                int sum = sorted[i] + sorted[left] + sorted[right];
                if (sum == 0) {
                    result.add(Arrays.asList(sorted[i], sorted[left], sorted[right]));
                    left++;
                    right--;
                    while (left < right && sorted[left] == sorted[left - 1]) {
                        left++;
                    }
                    while (left < right && sorted[right] == sorted[right + 1]) {
                        right--;
                    }
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }
}
