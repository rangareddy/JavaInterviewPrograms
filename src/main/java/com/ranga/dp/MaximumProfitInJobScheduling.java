package com.ranga.dp;

import java.util.Arrays;

/**
 * There are {@code n} jobs, where job {@code i} runs from {@code startTime[i]} to {@code endTime[i]} and pays {@code profit[i]}. Return the maximum profit you can earn by choosing a subset of jobs with no two overlapping in time. A job that ends at time {@code X} does not overlap a job that starts at time {@code X}.
 *
 * <p><b>Algorithm Strategy (Sort by End Time, DP with Binary Search):</b>
 * <ol>
 *   <li>Sort the jobs by end time. Let {@code dp[i]} be the best profit using only the first {@code i} jobs in that order.</li>
 *   <li>For job {@code i} there are two choices: skip it ({@code dp[i - 1]}) or take it, in which case the profit is its own plus the best profit among jobs that end no later than its start time.</li>
 *   <li>Because the jobs are sorted by end time, the last compatible job can be found with a binary search over the end times (the largest index whose end time is at most this job's start).</li>
 *   <li>{@code dp[i] = max(dp[i - 1], profit[i] + dp[lastCompatible + 1])}. The answer is the last value of {@code dp}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For start {@code [1, 2, 3, 3]}, end {@code [3, 4, 5, 6]}, profit {@code [50, 10, 40, 70]}: sorted by end time the same order holds.</li>
 *   <li>Taking job 1 (1 to 3, profit 50) then job 4 (3 to 6, profit 70) gives {@code 120}, which beats taking jobs 1 and 3 ({@code 90}).</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Sorting plus one binary search per job.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The sorted jobs and the {@code dp} array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumProfitInJobScheduling {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{1, 2, 3, 3}, {3, 4, 5, 6}, {50, 10, 40, 70}},
                {{1, 2, 3, 4, 6}, {3, 5, 10, 6, 9}, {20, 20, 100, 70, 60}},
                {{1, 1, 1}, {2, 3, 4}, {5, 6, 4}}
        };
        for (int[][] in : inputs) {
            System.out.println("Input  : startTime = " + Arrays.toString(in[0]) + ", endTime = " + Arrays.toString(in[1])
                    + ", profit = " + Arrays.toString(in[2]));
            System.out.println("Output : " + jobScheduling(in[0], in[1], in[2]));
            System.out.println();
        }
    }

    public static int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][];
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(jobs, (a, b) -> Integer.compare(a[1], b[1]));
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int[] job = jobs[i - 1];
            int lo = 0;
            int hi = i - 1;
            while (lo < hi) {
                int mid = lo + (hi - lo + 1) / 2;
                if (jobs[mid - 1][1] <= job[0]) {
                    lo = mid;
                } else {
                    hi = mid - 1;
                }
            }
            dp[i] = Math.max(dp[i - 1], job[2] + dp[lo]);
        }
        return dp[n];
    }
}
