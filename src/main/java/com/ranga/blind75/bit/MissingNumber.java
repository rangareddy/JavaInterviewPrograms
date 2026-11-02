package com.ranga.blind75.bit;

import java.util.Arrays;

/**
 * Given an array {@code nums} containing {@code n} distinct numbers in the range {@code [0, n]}, return the only number in the range that is missing from the array.
 *
 * <p><b>Algorithm Strategy (XOR of Indices and Values):</b>
 * <ol>
 *   <li>XOR of a number with itself is 0 and XOR is commutative and associative, so equal numbers cancel out in any order.</li>
 *   <li>Start with {@code missing = n} (the index that the array does not have).</li>
 *   <li>For every index {@code i}, XOR {@code missing} with both {@code i} and {@code nums[i]}.</li>
 *   <li>Every number that is present appears once as an index and once as a value and cancels out; only the missing number is left. (Alternative: {@code n(n+1)/2} minus the array sum.)</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 0, 1]} ({@code n = 3}): {@code 3 ^ (0^3) ^ (1^0) ^ (2^1) = 2}.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — A single pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — One variable.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MissingNumber {

    public static void main(String[] args) {
        int[][] inputs = {{3, 0, 1}, {0, 1}, {9, 6, 4, 2, 3, 5, 7, 0, 1}, {0}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + missingNumber(nums));
            System.out.println();
        }
    }

    public static int missingNumber(int[] nums) {
        int missing = nums.length;
        for (int i = 0; i < nums.length; i++) {
            missing ^= i ^ nums[i];
        }
        return missing;
    }
}
