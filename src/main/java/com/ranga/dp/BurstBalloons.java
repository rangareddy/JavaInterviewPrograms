package com.ranga.dp;

import java.util.Arrays;

/**
 * There are {@code n} balloons painted with numbers {@code nums[i]}. Bursting balloon {@code i} earns {@code nums[i - 1] * nums[i] * nums[i + 1]} coins, where a missing neighbour counts as a balloon with value 1. Return the maximum number of coins you can collect by bursting all balloons in a smart order.
 *
 * <p><b>Algorithm Strategy (Interval DP: Choose the Last Balloon to Burst):</b>
 * <ol>
 *   <li>Pad the array with a balloon of value 1 at both ends. Let {@code dp[l][r]} be the maximum coins from bursting every balloon strictly between {@code l} and {@code r}.</li>
 *   <li>Think backwards: pick the balloon {@code k} (with {@code l < k < r}) that is burst <em>last</em> in the open interval. Its neighbours at that moment are exactly the borders {@code l} and {@code r}, and the two sides are independent sub-problems.</li>
 *   <li>{@code dp[l][r] = max over k of dp[l][k] + nums[l] * nums[k] * nums[r] + dp[k][r]}.</li>
 *   <li>Fill the table by increasing interval length. The answer is {@code dp[0][n + 1]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 1, 5, 8]}: bursting 1, then 5, then 3, then 8 gives {@code 3*1*5 + 3*5*8 + 1*3*8 + 1*8*1 = 15 + 120 + 24 + 8 = 167}.</li>
 *   <li>Result: {@code 167}. For {@code [1, 5]} the best order gives {@code 10}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^3)} — Three nested loops: interval length, left border, last balloon.</li>
 *   <li><b>Space Complexity:</b> {@code O(n^2)} — The table.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BurstBalloons {

    public static void main(String[] args) {
        int[][] inputs = {{3, 1, 5, 8}, {1, 5}, {7}, {}, {9, 76, 64, 21}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + maxCoins(nums));
            System.out.println();
        }
    }

    public static int maxCoins(int[] nums) {
        int n = nums.length;
        int[] balloons = new int[n + 2];
        balloons[0] = 1;
        balloons[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            balloons[i + 1] = nums[i];
        }

        int[][] dp = new int[n + 2][n + 2];
        for (int length = 2; length <= n + 1; length++) {
            for (int left = 0; left + length <= n + 1; left++) {
                int right = left + length;
                for (int last = left + 1; last < right; last++) {
                    int coins = dp[left][last] + balloons[left] * balloons[last] * balloons[right] + dp[last][right];
                    dp[left][right] = Math.max(dp[left][right], coins);
                }
            }
        }
        return dp[0][n + 1];
    }
}
