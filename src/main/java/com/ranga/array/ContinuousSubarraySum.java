package com.ranga.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an integer array {@code nums} and an integer {@code k}, return {@code true} if {@code nums} has a subarray of length at least 2 whose elements sum to a multiple of {@code k}, and {@code false} otherwise. A multiple of {@code k} is {@code n * k} for any integer {@code n}, including 0.
 *
 * <p><b>Algorithm Strategy (Prefix Sum Remainders in a HashMap):</b>
 * <ol>
 *   <li>If two prefix sums have the same remainder modulo {@code k}, the elements between them add up to a multiple of {@code k}.</li>
 *   <li>Walk the array keeping {@code sum % k} and a map from each remainder to the first index where it was seen (the remainder 0 at index -1).</li>
 *   <li>If the current remainder was seen at index {@code j} and {@code i - j >= 2}, the subarray {@code (j, i]} qualifies: return {@code true}.</li>
 *   <li>Otherwise, store the remainder if it is new. Return {@code false} at the end.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [23, 2, 4, 6, 7]} and {@code k = 6}: the prefix remainders are 5, 1, 5, ...; the remainder 5 repeats at index 2, two positions after index 0.</li>
 *   <li>The subarray {@code [2, 4]} sums to 6. Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(min(n, k))} — The remainder map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ContinuousSubarraySum {

    public static void main(String[] args) {
        int[][] nums = {{23, 2, 4, 6, 7}, {23, 2, 6, 4, 7}, {23, 2, 6, 4, 7}, {0, 0}, {1, 2}};
        int[] ks = {6, 6, 13, 7, 3};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + checkSubarraySum(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static boolean checkSubarraySum(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return false;
        }
        Map<Integer, Integer> firstIndex = new HashMap<>();
        firstIndex.put(0, -1);
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int remainder = k == 0 ? sum : ((sum % k) + k) % k;
            Integer seen = firstIndex.get(remainder);
            if (seen == null) {
                firstIndex.put(remainder, i);
            } else if (i - seen >= 2) {
                return true;
            }
        }
        return false;
    }
}
