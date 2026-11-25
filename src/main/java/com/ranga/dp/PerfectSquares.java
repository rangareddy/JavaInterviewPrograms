package com.ranga.dp;

/**
 * Given an integer {@code n}, return the least number of perfect square numbers (1, 4, 9, 16, ...) that sum to {@code n}.
 *
 * <p><b>Algorithm Strategy (DP over Square Numbers):</b>
 * <ol>
 *   <li>Let {@code dp[i]} be the fewest squares that sum to {@code i}, with {@code dp[0] = 0}.</li>
 *   <li>For each {@code i} from 1 to {@code n} try every square {@code s * s <= i}.</li>
 *   <li>{@code dp[i] = min(dp[i - s * s] + 1)} over all such {@code s}.</li>
 *   <li>The answer is {@code dp[n]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 12}: {@code 12 = 4 + 4 + 4}, three squares, and no pair of squares sums to 12.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * sqrt(n))} — For each {@code i}, about sqrt(i) squares are tried.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The dp array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PerfectSquares {

    public static void main(String[] args) {
        int[] inputs = {12, 13, 1, 7, 100, 43};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + numSquares(n));
            System.out.println();
        }
    }

    public static int numSquares(int n) {
        if (n <= 0) {
            return 0;
        }
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int s = 1; s * s <= i; s++) {
                dp[i] = Math.min(dp[i], dp[i - s * s] + 1);
            }
        }
        return dp[n];
    }
}
