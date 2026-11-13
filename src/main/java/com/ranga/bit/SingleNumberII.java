package com.ranga.bit;

import java.util.Arrays;

/**
 * In an array where every element appears exactly three times except for one element that appears once, find the element that appears once. Use linear time and constant extra space.
 *
 * <p><b>Algorithm Strategy (Two-Bit State Machine per Bit Position):</b>
 * <ol>
 *   <li>For any bit position, the number of ones among the elements is a multiple of 3 for the values that appear three times, plus 0 or 1 from the single value. So that count modulo 3 is the single number's bit.</li>
 *   <li>Instead of 32 counters, keep two integers {@code ones} and {@code twos} that together hold, for every bit position at once, a two-bit counter that cycles {@code 00 -> 01 -> 10 -> 00}.</li>
 *   <li>For each number {@code x}: {@code ones = (ones ^ x) & ~twos} and {@code twos = (twos ^ x) & ~ones}. A bit of {@code x} moves the counter of that position one step; after three hits it is back to {@code 00}.</li>
 *   <li>After all elements, positions that were hit once are {@code 01}, so {@code ones} is the single number ({@code twos} is 0 at those positions).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 2, 3, 2]}: after the first 2, {@code ones = 2} (bit 1 seen once). After the second 2, {@code ones = 0, twos = 2} (bit 1 seen twice).</li>
 *   <li>The 3 hits bits 0 and 1: bit 1 reaches its third hit and clears, bit 0 is seen once, so {@code ones = 1, twos = 0}. The last 2 sets bit 1 again: {@code ones = 3}. Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass with a few bitwise operations per element.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two integers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SingleNumberII {

    public static void main(String[] args) {
        int[][] inputs = {{2, 2, 3, 2}, {0, 1, 0, 1, 0, 1, 99}, {-2, -2, 1, 1, 4, 1, 4, 4, -4, -2}, {7}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + singleNumber(nums));
            System.out.println();
        }
    }

    public static int singleNumber(int[] nums) {
        int ones = 0;
        int twos = 0;
        for (int x : nums) {
            ones = (ones ^ x) & ~twos;
            twos = (twos ^ x) & ~ones;
        }
        return ones;
    }
}
