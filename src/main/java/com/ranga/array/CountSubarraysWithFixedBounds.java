package com.ranga.array;

import java.util.Arrays;

/**
 * You are given an integer array {@code nums} and two integers {@code minK} and {@code maxK}. A fixed-bound subarray is a subarray whose minimum value is exactly {@code minK} and whose maximum value is exactly {@code maxK}. Return the number of fixed-bound subarrays.
 *
 * <p><b>Algorithm Strategy (Last Positions Sweep):</b>
 * <ol>
 *   <li>Walk the array with index {@code i} and remember three positions: {@code badIndex}, the last index of a value outside {@code [minK, maxK]}; {@code lastMin}, the last index of {@code minK}; and {@code lastMax}, the last index of {@code maxK}.</li>
 *   <li>A subarray ending at {@code i} is valid if it starts after {@code badIndex} and contains both a {@code minK} and a {@code maxK}, so it must start at or before {@code min(lastMin, lastMax)}.</li>
 *   <li>The number of valid starts is {@code max(0, min(lastMin, lastMax) - badIndex)}.</li>
 *   <li>Sum that over all {@code i}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 3, 5, 2, 7, 5]}, {@code minK = 1}, {@code maxK = 5}: the valid subarrays are {@code [1, 3, 5]} and {@code [1, 3, 5, 2]}.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Three indexes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CountSubarraysWithFixedBounds {

    public static void main(String[] args) {
        int[][] nums = {{1, 3, 5, 2, 7, 5}, {1, 1, 1, 1}, {35054, 398719, 945315, 945315, 820417, 328636, 916965}, {2, 1, 3}};
        int[] mins = {1, 1, 35054, 1};
        int[] maxs = {5, 1, 945315, 3};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", minK = " + mins[i] + ", maxK = " + maxs[i]);
            System.out.println("Output : " + countSubarrays(nums[i], mins[i], maxs[i]));
            System.out.println();
        }
    }

    public static long countSubarrays(int[] nums, int minK, int maxK) {
        if (nums == null) {
            return 0;
        }
        long count = 0;
        int badIndex = -1;
        int lastMin = -1;
        int lastMax = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < minK || nums[i] > maxK) {
                badIndex = i;
            }
            if (nums[i] == minK) {
                lastMin = i;
            }
            if (nums[i] == maxK) {
                lastMax = i;
            }
            count += Math.max(0, Math.min(lastMin, lastMax) - badIndex);
        }
        return count;
    }
}
