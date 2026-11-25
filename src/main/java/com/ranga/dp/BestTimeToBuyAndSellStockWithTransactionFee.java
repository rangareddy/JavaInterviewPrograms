package com.ranga.dp;

import java.util.Arrays;

/**
 * You are given an array {@code prices} where {@code prices[i]} is the price of a stock on day {@code i}, and an integer {@code fee} representing a transaction fee. Find the maximum profit you can achieve. You may complete as many transactions as you like, but you must pay the fee for each transaction, and you may not hold more than one share at a time.
 *
 * <p><b>Algorithm Strategy (Track Cash and Holding States):</b>
 * <ol>
 *   <li>Let {@code cash} be the best profit when you do not hold a share, and {@code hold} the best profit when you do. Initially {@code cash = 0} and {@code hold = -prices[0]}.</li>
 *   <li>For each later price, either sell: {@code cash = max(cash, hold + price - fee)} (the fee is paid on the sale), or keep.</li>
 *   <li>Either buy: {@code hold = max(hold, cash - price)}, or keep.</li>
 *   <li>The answer is {@code cash} after the last day.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 3, 2, 8, 4, 9]} and {@code fee = 2}: buy at 1, sell at 8 (profit 5), buy at 4, sell at 9 (profit 3).</li>
 *   <li>Result: {@code 8}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BestTimeToBuyAndSellStockWithTransactionFee {

    public static void main(String[] args) {
        int[][] prices = {{1, 3, 2, 8, 4, 9}, {1, 3, 7, 5, 10, 3}, {5}, {}};
        int[] fees = {2, 3, 1, 1};
        for (int i = 0; i < prices.length; i++) {
            System.out.println("Input  : prices = " + Arrays.toString(prices[i]) + ", fee = " + fees[i]);
            System.out.println("Output : " + maxProfit(prices[i], fees[i]));
            System.out.println();
        }
    }

    public static int maxProfit(int[] prices, int fee) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int cash = 0;
        int hold = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            cash = Math.max(cash, hold + prices[i] - fee);
            hold = Math.max(hold, cash - prices[i]);
        }
        return cash;
    }
}
