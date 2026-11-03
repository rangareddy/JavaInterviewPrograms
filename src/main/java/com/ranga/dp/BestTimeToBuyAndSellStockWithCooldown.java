package com.ranga.dp;

import java.util.Arrays;

/**
 * Given an array {@code prices} where {@code prices[i]} is the price of a stock on day {@code i}, find the maximum profit when you may complete as many transactions as you like (buy one and sell one share repeatedly) with the restrictions that you cannot hold more than one share at a time and, after you sell, you cannot buy on the next day (a one-day cooldown).
 *
 * <p><b>Algorithm Strategy (State Machine DP (Hold, Sold, Rest)):</b>
 * <ol>
 *   <li>Each day you are in one of three states: {@code hold} (you own a share), {@code sold} (you sold today, so tomorrow is the cooldown) and {@code rest} (you own nothing and may buy). Keep the best profit for each state.</li>
 *   <li>{@code sold(i) = hold(i - 1) + prices[i]}: you must have been holding yesterday.</li>
 *   <li>{@code hold(i) = max(hold(i - 1), rest(i - 1) - prices[i])}: keep the share, or buy today (only allowed from the rest state, not right after a sale).</li>
 *   <li>{@code rest(i) = max(rest(i - 1), sold(i - 1))}: stay out, or come out of the cooldown. The answer is {@code max(sold, rest)} on the last day.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, 0, 2]}: buy at 1, sell at 2, cooldown, buy at 0, sell at 2 gives profit {@code 1 + 2 = 3}.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — A single pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Three variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BestTimeToBuyAndSellStockWithCooldown {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 0, 2}, {1}, {1, 2, 4}, {6, 1, 6, 4, 3, 0, 2}};
        for (int[] prices : inputs) {
            System.out.println("Input  : prices = " + Arrays.toString(prices));
            System.out.println("Output : " + maxProfit(prices));
            System.out.println();
        }
    }

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int hold = -prices[0];      // best profit while holding a share
        int sold = 0;               // best profit on a day when a share was just sold
        int rest = 0;               // best profit while holding nothing and free to buy
        for (int i = 1; i < prices.length; i++) {
            int previousSold = sold;
            sold = hold + prices[i];
            hold = Math.max(hold, rest - prices[i]);
            rest = Math.max(rest, previousSold);
        }
        return Math.max(sold, rest);
    }
}
