package com.ranga.array;

import java.util.Arrays;

/**
 * Given an array {@code nums} containing {@code n} distinct numbers in the range {@code [0, n]}, return the only number in the range that is missing from the array.
 *
 * <p><b>Algorithm Strategy (XOR of Indices and Values):</b>
 * <ol>
 *   <li>XOR is its own inverse: {@code a ^ a = 0} and {@code a ^ 0 = a}, and the order of the operands does not matter.</li>
 *   <li>Start with {@code result = n}, the one index that the loop below never visits.</li>
 *   <li>For every index {@code i}, XOR {@code result} with both {@code i} and {@code nums[i]}.</li>
 *   <li>Every number that is present appears once as an index and once as a value, so the pair cancels. Only the missing number appears once (as an index), so it is what remains.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 0, 1]} with {@code n = 3}: {@code 3 ^ (0 ^ 3) ^ (1 ^ 0) ^ (2 ^ 1) = 2}.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over the array.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A single integer.</li>
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
        int result = nums.length;
        for (int i = 0; i < nums.length; i++) {
            result ^= i ^ nums[i];
        }
        return result;
    }
}
