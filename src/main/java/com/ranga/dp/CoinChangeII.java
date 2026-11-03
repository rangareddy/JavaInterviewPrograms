package com.ranga.dp;

import java.util.Arrays;

/**
 * Given coins of different denominations and a total {@code amount}, return the number of combinations of coins that make up that amount (the order of coins does not matter). Return 0 if the amount cannot be made. Each coin can be used any number of times.
 *
 * <p><b>Algorithm Strategy (Unbounded Knapsack Counting (Coins in the Outer Loop)):</b>
 * <ol>
 *   <li>Let {@code dp[a]} be the number of combinations that make amount {@code a}; {@code dp[0] = 1} (use no coin).</li>
 *   <li>Process the coins one at a time in the outer loop. For the current coin {@code c} and every amount {@code a} from {@code c} up to {@code amount}: {@code dp[a] += dp[a - c]} (all combinations of the earlier coins plus any number of this coin).</li>
 *   <li>Because the coins are the outer loop, each combination is counted once regardless of order. (Swapping the loops would count permutations such as 1+2 and 2+1 separately.)</li>
 *   <li>The answer is {@code dp[amount]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For amount 5 and coins {@code [1, 2, 5]}: with coin 1 only, {@code dp[5] = 1}; adding coin 2 makes it 3 (2+2+1, 2+1+1+1, 1x5); adding coin 5 makes it 4.</li>
 *   <li>Result: {@code 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(amount * coins)} — Two nested loops.</li>
 *   <li><b>Space Complexity:</b> {@code O(amount)} — One array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CoinChangeII {

    public static void main(String[] args) {
        int[] amounts = {5, 3, 10, 0, 7};
        int[][] coins = {{1, 2, 5}, {2}, {10}, {7}, {2, 3}};
        for (int i = 0; i < amounts.length; i++) {
            System.out.println("Input  : amount = " + amounts[i] + ", coins = " + Arrays.toString(coins[i]));
            System.out.println("Output : " + change(amounts[i], coins[i]));
            System.out.println();
        }
    }

    public static int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int a = coin; a <= amount; a++) {
                dp[a] += dp[a - coin];
            }
        }
        return dp[amount];
    }
}
