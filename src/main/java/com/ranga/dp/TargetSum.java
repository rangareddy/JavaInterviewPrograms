package com.ranga.dp;

import java.util.Arrays;

/**
 * You are given an integer array {@code nums} and an integer {@code target}. Put a {@code +} or a {@code -} in front of every number and concatenate the results into an expression. Return the number of different expressions that evaluate to {@code target}.
 *
 * <p><b>Algorithm Strategy (Reduce to Counting Subsets with a Given Sum):</b>
 * <ol>
 *   <li>Let {@code P} be the sum of the numbers with a plus and {@code N} the sum of those with a minus. Then {@code P - N = target} and {@code P + N = total}, so {@code P = (total + target) / 2}.</li>
 *   <li>If {@code |target| > total} or {@code total + target} is odd, no expression works: return 0.</li>
 *   <li>The problem becomes: count the subsets whose sum is {@code P} (the numbers that get a plus). This is the 0/1 knapsack counting problem.</li>
 *   <li>Let {@code dp[s]} be the number of subsets with sum {@code s}; {@code dp[0] = 1}. For every number update {@code s} from {@code P} down to the number: {@code dp[s] += dp[s - num]}. The answer is {@code dp[P]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 1, 1, 1, 1]} and target 3: total 5, {@code P = (5 + 3) / 2 = 4}; the number of ways to choose four of the five ones is 5.</li>
 *   <li>Result: {@code 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * P)} — {@code P} is the required subset sum.</li>
 *   <li><b>Space Complexity:</b> {@code O(P)} — One array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TargetSum {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 1, 1, 1}, {1}, {1, 0}, {2, 3, 5}, {1, 2, 3, 4, 5}};
        int[] targets = {3, 1, 1, 0, 3};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + findTargetSumWays(inputs[i], targets[i]));
            System.out.println();
        }
    }

    public static int findTargetSumWays(int[] nums, int target) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        if (Math.abs(target) > total || (total + target) % 2 != 0) {
            return 0;
        }

        int plusSum = (total + target) / 2;
        int[] dp = new int[plusSum + 1];
        dp[0] = 1;
        for (int num : nums) {
            for (int sum = plusSum; sum >= num; sum--) {
                dp[sum] += dp[sum - num];
            }
        }
        return dp[plusSum];
    }
}
