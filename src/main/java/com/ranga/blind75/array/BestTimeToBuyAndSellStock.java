package com.ranga.blind75.array;

import java.util.Arrays;

/**
 * Given an array {@code prices} where {@code prices[i]} is the price of a stock on day {@code i}, return the maximum profit from buying on one day and selling on a later day (0 if no profit is possible).
 *
 * <p><b>Algorithm Strategy (Track the Minimum So Far):</b>
 * <ol>
 *   <li>Scan the prices once from left to right, remembering the lowest price seen so far in {@code minPrice}.</li>
 *   <li>For every day, the best profit if we sell today is {@code price - minPrice}; keep the maximum of these values in {@code maxProfit}.</li>
 *   <li>Update {@code minPrice} when a lower price appears. Selling can only happen after buying because {@code minPrice} only contains earlier days.</li>
 *   <li>Return {@code maxProfit} (it stays 0 when prices never rise).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [7, 1, 5, 3, 6, 4]}: the minimum becomes 1 on day 2; selling at 5 gives 4 and selling at 6 gives 5.</li>
 *   <li>Result: {@code 5} (buy at 1, sell at 6).</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — A single pass over the prices.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only two variables are kept.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        int[][] inputs = {{7, 1, 5, 3, 6, 4}, {7, 6, 4, 3, 1}};
        for (int[] prices : inputs) {
            System.out.println("Input  : prices = " + Arrays.toString(prices));
            System.out.println("Output : " + maxProfit(prices));
            System.out.println();
        }
    }

    public static int maxProfit(int[] prices) {
        // Guard Clause: at least two days are needed to buy and sell
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int minPrice = prices[0];
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
            minPrice = Math.min(minPrice, prices[i]);
        }
        return maxProfit;
    }
}
