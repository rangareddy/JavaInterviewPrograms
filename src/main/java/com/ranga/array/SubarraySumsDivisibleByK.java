package com.ranga.array;

import java.util.Arrays;

/**
 * Given an integer array {@code nums} and an integer {@code k}, return the number of non-empty subarrays that have a sum divisible by {@code k}.
 *
 * <p><b>Algorithm Strategy (Count Equal Prefix Remainders):</b>
 * <ol>
 *   <li>A subarray {@code nums[i..j]} has a sum divisible by {@code k} when the prefix sums before {@code i} and up to {@code j} leave the same remainder modulo {@code k}.</li>
 *   <li>Keep an array {@code count[k]} of how many prefixes had each remainder; start with {@code count[0] = 1} for the empty prefix.</li>
 *   <li>For each element, update the running sum and compute its remainder, made non-negative with {@code ((sum % k) + k) % k}.</li>
 *   <li>Add {@code count[remainder]} to the answer, then increase it.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [4, 5, 0, -2, -3, 1]} and {@code k = 5} the prefix remainders are 4, 4, 4, 2, 4... leading to 7 qualifying subarrays.</li>
 *   <li>Result: {@code 7}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(k)} — The remainder counts.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SubarraySumsDivisibleByK {

    public static void main(String[] args) {
        int[][] nums = {{4, 5, 0, -2, -3, 1}, {5}, {-1, 2, 9}, {}};
        int[] ks = {5, 9, 2, 3};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + subarraysDivByK(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static int subarraysDivByK(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return 0;
        }
        int[] count = new int[k];
        count[0] = 1;
        int sum = 0;
        int result = 0;
        for (int num : nums) {
            sum = ((sum + num) % k + k) % k;
            result += count[sum]++;
        }
        return result;
    }
}
