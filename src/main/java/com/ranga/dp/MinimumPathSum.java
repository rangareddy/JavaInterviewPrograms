package com.ranga.dp;

import java.util.Arrays;

/**
 * Given an {@code m x n} grid of non-negative numbers, find a path from the top-left corner to the bottom-right corner that minimizes the sum of the numbers along the path. You can only move right or down at each step.
 *
 * <p><b>Algorithm Strategy (Grid DP with a Single Row):</b>
 * <ol>
 *   <li>Let {@code dp[i][j]} be the minimum path sum to reach cell {@code (i, j)}. It is the cell value plus the cheaper of the two ways in: from above {@code dp[i-1][j]} or from the left {@code dp[i][j-1]}.</li>
 *   <li>The first row can only be reached from the left and the first column only from above, so they are running sums.</li>
 *   <li>Row {@code i} depends only on row {@code i - 1} and the cell to the left, so a single array of length {@code n} is enough: {@code dp[j] = grid[i][j] + min(dp[j], dp[j-1])}, where {@code dp[j]} still holds the value from the row above before it is overwritten.</li>
 *   <li>The answer is {@code dp[n - 1]} after the last row.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1, 3, 1], [1, 5, 1], [4, 2, 1]]} the first row becomes {@code [1, 4, 5]}.</li>
 *   <li>Row 2 gives {@code [2, 7, 6]} and row 3 gives {@code [6, 8, 7]}. The cheapest path is {@code 1 -> 3 -> 1 -> 1 -> 1} with sum {@code 7}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Every cell is computed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — One row.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumPathSum {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}},
                {{1, 2, 3}, {4, 5, 6}},
                {{7}}
        };
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + minPathSum(grid));
            System.out.println();
        }
    }

    public static int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];
        dp[0] = grid[0][0];
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j - 1] + grid[0][j];
        }
        for (int i = 1; i < m; i++) {
            dp[0] += grid[i][0];
            for (int j = 1; j < n; j++) {
                dp[j] = grid[i][j] + Math.min(dp[j], dp[j - 1]);
            }
        }
        return dp[n - 1];
    }
}
