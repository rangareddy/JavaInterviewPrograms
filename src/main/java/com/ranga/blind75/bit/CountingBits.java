package com.ranga.blind75.bit;

import java.util.Arrays;

/**
 * Given an integer {@code n}, return an array {@code ans} of length {@code n + 1} where {@code ans[i]} is the number of 1's in the binary representation of {@code i}.
 *
 * <p><b>Algorithm Strategy (Dynamic Programming on the Shifted Number):</b>
 * <ol>
 *   <li>{@code ans[0] = 0}.</li>
 *   <li>Shifting a number right by one ({@code i >> 1}) removes its lowest bit, so {@code i} has the same number of 1s as {@code i / 2}, plus one if the lowest bit of {@code i} is 1.</li>
 *   <li>Therefore {@code ans[i] = ans[i >> 1] + (i & 1)}, and {@code i >> 1} is always smaller than {@code i}, so it has already been computed.</li>
 *   <li>Fill the array for {@code i = 1 .. n}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 5}: {@code ans[1] = ans[0] + 1 = 1}, {@code ans[2] = ans[1] + 0 = 1}, {@code ans[3] = ans[1] + 1 = 2}, {@code ans[4] = ans[2] + 0 = 1}, {@code ans[5] = ans[2] + 1 = 2}.</li>
 *   <li>Result: {@code [0, 1, 1, 2, 1, 2]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One constant-time step per number (no per-number bit loop).</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The output array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CountingBits {

    public static void main(String[] args) {
        int[] inputs = {2, 5, 0};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + Arrays.toString(countBits(n)));
            System.out.println();
        }
    }

    public static int[] countBits(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            ans[i] = ans[i >> 1] + (i & 1);
        }
        return ans;
    }
}
