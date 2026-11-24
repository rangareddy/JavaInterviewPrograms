package com.ranga.array;

import java.util.Arrays;

/**
 * Given an array of integers {@code nums}, you start with some positive integer {@code startValue} and add the elements one by one. Return the minimum {@code startValue} such that the running sum is never less than 1.
 *
 * <p><b>Algorithm Strategy (Minimum Prefix Sum):</b>
 * <ol>
 *   <li>Compute the running (prefix) sum of {@code nums} and track its minimum value {@code minPrefix}.</li>
 *   <li>With {@code startValue = s}, the lowest running total is {@code s + minPrefix}.</li>
 *   <li>That must be at least 1, so {@code s >= 1 - minPrefix}.</li>
 *   <li>The answer is {@code max(1, 1 - minPrefix)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [-3, 2, -3, 4, 2]} the prefix sums are -3, -1, -4, 0, 2, so the minimum is -4.</li>
 *   <li>Result: {@code 1 - (-4) = 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two numbers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumValueToGetPositiveStepByStepSum {

    public static void main(String[] args) {
        int[][] inputs = {{-3, 2, -3, 4, 2}, {1, 2}, {1, -2, -3}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + minStartValue(nums));
            System.out.println();
        }
    }

    public static int minStartValue(int[] nums) {
        int minPrefix = 0;
        int sum = 0;
        if (nums != null) {
            for (int x : nums) {
                sum += x;
                minPrefix = Math.min(minPrefix, sum);
            }
        }
        return 1 - minPrefix;
    }
}
