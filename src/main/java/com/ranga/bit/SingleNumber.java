package com.ranga.bit;

import java.util.Arrays;

/**
 * Given a non-empty array of integers {@code nums} in which every element appears twice except for one, find the element that appears only once. The solution must use linear time and constant extra space.
 *
 * <p><b>Algorithm Strategy (XOR of All Elements):</b>
 * <ol>
 *   <li>XOR has three useful properties: {@code a ^ a = 0}, {@code a ^ 0 = a}, and it is commutative and associative.</li>
 *   <li>XOR all the numbers together. Every pair cancels to 0, whatever its position in the array.</li>
 *   <li>The only value left is the number that appears once.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [4, 1, 2, 1, 2]}: {@code 4 ^ 1 ^ 2 ^ 1 ^ 2 = 4 ^ (1 ^ 1) ^ (2 ^ 2) = 4 ^ 0 ^ 0 = 4}.</li>
 *   <li>Result: {@code 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A single integer.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SingleNumber {

    public static void main(String[] args) {
        int[][] inputs = {{2, 2, 1}, {4, 1, 2, 1, 2}, {1}, {-1, -1, -3}, {7, 3, 5, 3, 5}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + singleNumber(nums));
            System.out.println();
        }
    }

    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }
}
