package com.ranga.bit;

import java.util.Arrays;

/**
 * In a non-empty array where every element appears exactly twice except for one element that appears once, find that single element. Use linear time and constant extra space.
 *
 * <p><b>Algorithm Strategy (XOR of All Elements):</b>
 * <ol>
 *   <li>XOR has three properties that make this work: {@code a ^ a = 0}, {@code a ^ 0 = a}, and it is commutative and associative, so the order does not matter.</li>
 *   <li>XOR every element together. Each value that appears twice cancels itself out to 0.</li>
 *   <li>What remains is the XOR of the single value with 0, which is the single value itself.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [4, 1, 2, 1, 2]}: {@code 4 ^ 1 ^ 2 ^ 1 ^ 2 = 4 ^ (1 ^ 1) ^ (2 ^ 2) = 4 ^ 0 ^ 0 = 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — One integer.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SingleNumber {

    public static void main(String[] args) {
        int[][] inputs = {{2, 2, 1}, {4, 1, 2, 1, 2}, {1}, {-1, 5, -1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + singleNumber(nums));
            System.out.println();
        }
    }

    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int x : nums) {
            result ^= x;
        }
        return result;
    }
}
