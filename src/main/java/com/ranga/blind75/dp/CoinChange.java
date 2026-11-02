package com.ranga.blind75.dp;

import java.util.Arrays;

/**
 * Given coins of different denominations and a total {@code amount}, return the fewest number of coins needed to make that amount, or {@code -1} if it cannot be made. Each coin can be used any number of times.
 *
 * <p><b>Algorithm Strategy (Bottom-Up Dynamic Programming):</b>
 * <ol>
 *   <li>Let {@code dp[a]} be the fewest coins needed to make amount {@code a}. Initialize {@code dp[0] = 0} and every other entry with a value larger than any answer ({@code amount + 1}).</li>
 *   <li>For every amount {@code a} from 1 to {@code amount} and every coin {@code c <= a}: {@code dp[a] = min(dp[a], dp[a - c] + 1)}, i.e. use coin {@code c} and the best solution of the remaining amount.</li>
 *   <li>If {@code dp[amount]} is still greater than {@code amount}, the amount cannot be made, so return {@code -1}.</li>
 *   <li>Otherwise return {@code dp[amount]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For coins {@code [1, 2, 5]} and amount 11: {@code dp[5] = 1}, {@code dp[10] = 2}, {@code dp[11] = dp[10] + 1 = 3}.</li>
 *   <li>Result: {@code 3} (5 + 5 + 1).</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(amount * coins)} — Two nested loops.</li>
 *   <li><b>Space Complexity:</b> {@code O(amount)} — The {@code dp} array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CoinChange {

    public static void main(String[] args) {
        int[][] coins = {{1, 2, 5}, {2}, {1}, {2, 5, 10}};
        int[] amounts = {11, 3, 0, 27};
        for (int i = 0; i < coins.length; i++) {
            System.out.println("Input  : coins = " + Arrays.toString(coins[i]) + ", amount = " + amounts[i]);
            System.out.println("Output : " + coinChange(coins[i], amounts[i]));
            System.out.println();
        }
    }

    public static int coinChange(int[] coins, int amount) {
        if (amount < 0) {
            return -1;
        }

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);        // amount + 1 means "not reachable"
        dp[0] = 0;
        for (int a = 1; a <= amount; a++) {
            for (int coin : coins) {
                if (coin <= a) {
                    dp[a] = Math.min(dp[a], dp[a - coin] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }
}
