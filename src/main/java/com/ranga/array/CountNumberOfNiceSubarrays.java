package com.ranga.array;

import java.util.Arrays;

/**
 * Given an array of integers {@code nums} and an integer {@code k}, a continuous subarray is nice if it contains exactly {@code k} odd numbers. Return the number of nice subarrays.
 *
 * <p><b>Algorithm Strategy (Prefix Odd Count with a Frequency Table):</b>
 * <ol>
 *   <li>Let {@code odd} be the number of odd values seen so far. A subarray ending at the current index has exactly {@code k} odd numbers when some earlier prefix had {@code odd - k} odd numbers.</li>
 *   <li>Keep an array {@code freq} where {@code freq[x]} is the number of prefixes with {@code x} odd numbers; start with {@code freq[0] = 1} (the empty prefix).</li>
 *   <li>For every element, update {@code odd}, add {@code freq[odd - k]} (if non-negative) to the answer, then increase {@code freq[odd]}.</li>
 *   <li>Return the total.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 1, 2, 1, 1]} and {@code k = 3}: the nice subarrays are {@code [1,1,2,1]} and {@code [1,2,1,1]}.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The frequency array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CountNumberOfNiceSubarrays {

    public static void main(String[] args) {
        int[][] nums = {{1, 1, 2, 1, 1}, {2, 4, 6}, {2, 2, 2, 1, 2, 2, 1, 2, 2, 2}, {1}};
        int[] ks = {3, 1, 2, 1};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + numberOfSubarrays(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static int numberOfSubarrays(int[] nums, int k) {
        if (nums == null || k < 0) {
            return 0;
        }
        int[] freq = new int[nums.length + 1];
        freq[0] = 1;
        int odd = 0;
        int count = 0;
        for (int num : nums) {
            odd += num & 1;
            if (odd >= k) {
                count += freq[odd - k];
            }
            freq[odd]++;
        }
        return count;
    }
}
