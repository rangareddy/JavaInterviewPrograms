package com.ranga.dp;

import java.util.Arrays;

/**
 * You plan to travel on the days listed in {@code days} (sorted, values from 1 to 365). A 1-day pass, a 7-day pass and a 30-day pass cost {@code costs[0]}, {@code costs[1]} and {@code costs[2]}. A pass covers consecutive days starting on the day it is bought. Return the minimum cost to cover every travel day.
 *
 * <p><b>Algorithm Strategy (DP over the Calendar):</b>
 * <ol>
 *   <li>Let {@code dp[d]} be the minimum cost to cover all travel days up to day {@code d}.</li>
 *   <li>If {@code d} is not a travel day, {@code dp[d] = dp[d - 1]}.</li>
 *   <li>Otherwise {@code dp[d] = min(dp[d - 1] + costs[0], dp[max(0, d - 7)] + costs[1], dp[max(0, d - 30)] + costs[2])}.</li>
 *   <li>The answer is {@code dp[lastDay]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code days = [1, 4, 6, 7, 8, 20]} and {@code costs = [2, 7, 15]}: a 1-day pass on day 1, a 7-day pass on days 4 to 10, and a 1-day pass on day 20.</li>
 *   <li>Total: {@code 2 + 7 + 2 = 11}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(lastDay)} — One DP step per calendar day (at most 365).</li>
 *   <li><b>Space Complexity:</b> {@code O(lastDay)} — The dp array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumCostForTickets {

    public static void main(String[] args) {
        int[][] days = {{1, 4, 6, 7, 8, 20}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 30, 31}, {5}};
        int[][] costs = {{2, 7, 15}, {2, 7, 15}, {3, 10, 20}};
        for (int i = 0; i < days.length; i++) {
            System.out.println("Input  : days = " + Arrays.toString(days[i]) + ", costs = " + Arrays.toString(costs[i]));
            System.out.println("Output : " + mincostTickets(days[i], costs[i]));
            System.out.println();
        }
    }

    public static int mincostTickets(int[] days, int[] costs) {
        if (days == null || days.length == 0) {
            return 0;
        }
        int last = days[days.length - 1];
        boolean[] travel = new boolean[last + 1];
        for (int d : days) {
            travel[d] = true;
        }
        int[] dp = new int[last + 1];
        for (int d = 1; d <= last; d++) {
            if (!travel[d]) {
                dp[d] = dp[d - 1];
                continue;
            }
            dp[d] = Math.min(dp[d - 1] + costs[0],
                    Math.min(dp[Math.max(0, d - 7)] + costs[1], dp[Math.max(0, d - 30)] + costs[2]));
        }
        return dp[last];
    }
}
