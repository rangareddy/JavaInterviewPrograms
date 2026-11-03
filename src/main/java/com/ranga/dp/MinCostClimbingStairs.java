package com.ranga.dp;

import java.util.Arrays;

/**
 * You are given an array {@code cost} where {@code cost[i]} is the price of stepping on stair {@code i}. After paying the price you may climb one or two stairs, and you may start from stair 0 or stair 1. Return the minimum cost to reach the top of the floor, which is one position beyond the last stair.
 *
 * <p><b>Algorithm Strategy (Dynamic Programming with Two Variables):</b>
 * <ol>
 *   <li>Let {@code best(i)} be the minimum cost to stand on position {@code i} (before paying for stair {@code i}). The top is position {@code n}.</li>
 *   <li>Positions 0 and 1 can be the starting point, so {@code best(0) = best(1) = 0}.</li>
 *   <li>To arrive at position {@code i} you come from {@code i - 1} (paying {@code cost[i - 1]}) or from {@code i - 2} (paying {@code cost[i - 2]}): {@code best(i) = min(best(i - 1) + cost[i - 1], best(i - 2) + cost[i - 2])}.</li>
 *   <li>Only the two previous values are needed, so keep them in two variables; {@code best(n)} is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code cost = [10, 15, 20]}: {@code best(2) = min(0 + 15, 0 + 10) = 10}, {@code best(3) = min(10 + 20, 0 + 15) = 15}.</li>
 *   <li>Result: {@code 15} (start at stair 1, pay 15, jump to the top).</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — A single pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinCostClimbingStairs {

    public static void main(String[] args) {
        int[][] inputs = {{10, 15, 20}, {1, 100, 1, 1, 1, 100, 1, 1, 100, 1}, {5, 5}, {0, 0, 0, 0}};
        for (int[] cost : inputs) {
            System.out.println("Input  : cost = " + Arrays.toString(cost));
            System.out.println("Output : " + minCostClimbingStairs(cost));
            System.out.println();
        }
    }

    public static int minCostClimbingStairs(int[] cost) {
        int twoBack = 0;        // best cost to stand on position i - 2
        int oneBack = 0;        // best cost to stand on position i - 1
        for (int i = 2; i <= cost.length; i++) {
            int current = Math.min(oneBack + cost[i - 1], twoBack + cost[i - 2]);
            twoBack = oneBack;
            oneBack = current;
        }
        return oneBack;
    }
}
