package com.ranga.dp;

import java.util.Arrays;

/**
 * You are given {@code n} items, each with a weight and a value, and a bag that can carry a total weight of at most {@code capacity}. Every item can be taken at most once. Find the maximum total value that fits in the bag.
 *
 * <p><b>Algorithm Strategy (Dynamic Programming over Capacity):</b>
 * <ol>
 *   <li>Let {@code dp[w]} be the best value achievable with total weight at most {@code w}, using the items processed so far. Initially all values are 0 (no items).</li>
 *   <li>For every item with weight {@code wt} and value {@code val}, update the capacities from {@code capacity} down to {@code wt}: {@code dp[w] = max(dp[w], dp[w - wt] + val)} (skip the item, or take it on top of the best solution for the remaining capacity).</li>
 *   <li>Iterate the capacity downwards so that {@code dp[w - wt]} still refers to the state before this item was considered; going upwards would allow the same item to be taken several times (the unbounded knapsack).</li>
 *   <li>The answer is {@code dp[capacity]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For weights {@code [1, 3, 4, 5]}, values {@code [1, 4, 5, 7]} and capacity 7: taking the items of weight 3 and 4 gives value {@code 4 + 5 = 9}, which is the best.</li>
 *   <li>With capacity 0 nothing fits and the answer is 0.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * capacity)} — For every item all capacities are updated.</li>
 *   <li><b>Space Complexity:</b> {@code O(capacity)} — A single row of the table.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class Knapsack01 {

    public static void main(String[] args) {
        int[][] weights = {{1, 3, 4, 5}, {10, 20, 30}, {5}, {2, 3}};
        int[][] values = {{1, 4, 5, 7}, {60, 100, 120}, {10}, {3, 4}};
        int[] capacities = {7, 50, 4, 0};
        for (int i = 0; i < weights.length; i++) {
            System.out.println("Input  : weights = " + Arrays.toString(weights[i]) + ", values = " + Arrays.toString(values[i]) + ", capacity = " + capacities[i]);
            System.out.println("Output : " + knapsack(weights[i], values[i], capacities[i]));
            System.out.println();
        }
    }

    public static int knapsack(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];
        for (int item = 0; item < weights.length; item++) {
            for (int w = capacity; w >= weights[item]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weights[item]] + values[item]);
            }
        }
        return dp[capacity];
    }
}
