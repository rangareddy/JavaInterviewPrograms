package com.ranga.bit;

import java.util.Arrays;

/**
 * Given an integer {@code n}, return an array {@code ans} of length {@code n + 1} where {@code ans[i]} is the number of {@code 1} bits in the binary representation of {@code i}, for every {@code i} in {@code [0, n]}.
 *
 * <p><b>Algorithm Strategy (DP on the Number with Its Last Bit Removed):</b>
 * <ol>
 *   <li>The binary form of {@code i} is the binary form of {@code i >> 1} followed by one more bit, {@code i & 1}.</li>
 *   <li>So {@code ans[i] = ans[i >> 1] + (i & 1)}: the count for {@code i} reuses the count already computed for the smaller number {@code i >> 1}.</li>
 *   <li>Fill the array from 1 up to {@code n} ({@code ans[0] = 0}). Each entry costs one shift, one mask and one addition, so the whole array takes linear time instead of counting bits for every number from scratch.</li>
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
 *   <li><b>Time Complexity:</b> {@code O(n)} — Constant work per number.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Apart from the output array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CountingBits {

    public static void main(String[] args) {
        int[] inputs = {2, 5, 0, 8};
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
