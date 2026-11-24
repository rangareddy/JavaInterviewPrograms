package com.ranga.greedy;

import java.util.Arrays;

/**
 * Given {@code prices[i]}, the price of a stock on day {@code i}, you may buy and sell as many times as you like, holding at most one share at a time. Return the maximum profit.
 *
 * <p><b>Algorithm Strategy (Collect Every Rise):</b>
 * <ol>
 *   <li>Any profit over several days equals the sum of the daily differences along the way.</li>
 *   <li>So adding every positive day-to-day difference gives the best possible total.</li>
 *   <li>Loop from day 1: if {@code prices[i] > prices[i - 1]}, add the difference to the profit.</li>
 *   <li>Never add a negative difference, which means you skip those days.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [7, 1, 5, 3, 6, 4]}: the rises are 1 to 5 (+4) and 3 to 6 (+3).</li>
 *   <li>Result: {@code 7}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over the prices.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only a running total is kept.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BestTimeToBuyAndSellStockII {

    public static void main(String[] args) {
        int[][] inputs = {{7, 1, 5, 3, 6, 4}, {1, 2, 3, 4, 5}, {7, 6, 4, 3, 1}, {5}, {}};
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
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }
}
