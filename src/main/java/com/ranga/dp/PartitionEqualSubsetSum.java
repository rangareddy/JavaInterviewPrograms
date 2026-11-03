package com.ranga.dp;

import java.util.Arrays;

/**
 * Given an integer array {@code nums} of positive integers, return {@code true} if it can be partitioned into two subsets with equal sums.
 *
 * <p><b>Algorithm Strategy (0/1 Knapsack: Can a Subset Reach Half of the Total?):</b>
 * <ol>
 *   <li>If the total sum is odd the two halves cannot be equal, so return {@code false}. Otherwise the task is to find a subset with sum {@code target = total / 2}; the rest then also sums to {@code target}.</li>
 *   <li>Let {@code dp[s]} be {@code true} when some subset of the numbers processed so far has sum {@code s}. Initially only {@code dp[0]} is true (the empty subset).</li>
 *   <li>For each number {@code num}, update the sums from {@code target} down to {@code num}: {@code dp[s] |= dp[s - num]}. Iterating downwards ensures that each number is used at most once.</li>
 *   <li>The answer is {@code dp[target]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 5, 11, 5]}: total 22, target 11. After the numbers 1, 5, 11 the reachable sums include 11 (the number 11 itself), so {@code dp[11]} is true.</li>
 *   <li>The subsets are {@code [11]} and {@code [1, 5, 5]}. For {@code [1, 2, 3, 5]} the total 11 is odd: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * target)} — For every number all sums up to the target are updated.</li>
 *   <li><b>Space Complexity:</b> {@code O(target)} — One boolean array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PartitionEqualSubsetSum {

    public static void main(String[] args) {
        int[][] inputs = {{1, 5, 11, 5}, {1, 2, 3, 5}, {2, 2, 3, 5}, {1}, {3, 3, 3, 4, 5}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + canPartition(nums));
            System.out.println();
        }
    }

    public static boolean canPartition(int[] nums) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        if (total % 2 != 0) {
            return false;
        }

        int target = total / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int num : nums) {
            for (int sum = target; sum >= num; sum--) {
                dp[sum] = dp[sum] || dp[sum - num];
            }
        }
        return dp[target];
    }
}
