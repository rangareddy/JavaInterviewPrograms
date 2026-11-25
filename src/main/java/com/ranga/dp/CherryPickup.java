package com.ranga.dp;

import java.util.Arrays;

/**
 * An {@code n x n} grid has cells {@code 0} (empty), {@code 1} (a cherry) and {@code -1} (a thorn that blocks the way). Start at {@code (0, 0)}, walk to {@code (n - 1, n - 1)} moving only right or down, then walk back to {@code (0, 0)} moving only left or up. Each cherry you pass is picked and the cell becomes empty. Return the maximum number of cherries you can pick, or {@code 0} if there is no valid path.
 *
 * <p><b>Algorithm Strategy (Two Walkers Moving Down-Right Together):</b>
 * <ol>
 *   <li>The return trip reversed is another down-right path, so the problem is two paths from {@code (0, 0)} to {@code (n - 1, n - 1)} picking as many distinct cherries as possible.</li>
 *   <li>Move both walkers step by step. After {@code t} steps, walker one is at {@code (r1, t - r1)} and walker two at {@code (r2, t - r2)}.</li>
 *   <li>DP over {@code (t, r1, r2)}: take the best of the four ways the walkers could have arrived, add the cherries on their cells (counting a shared cell once), and block thorns.</li>
 *   <li>The answer is {@code max(0, dp[2n - 2][n - 1][n - 1])}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[0,1,-1],[1,0,-1],[1,1,1]]} one walker goes down the left column and the other along the top row and the bottom.</li>
 *   <li>Result: {@code 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^3)} — {@code 2n - 1} steps times {@code n^2} position pairs.</li>
 *   <li><b>Space Complexity:</b> {@code O(n^2)} — A rolling layer of the DP.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CherryPickup {

    public static void main(String[] args) {
        int[][][] grids = {{{0, 1, -1}, {1, 0, -1}, {1, 1, 1}}, {{1, 1, -1}, {1, -1, 1}, {-1, 1, 1}}, {{1}}, {{0, 0}, {0, 0}}};
        for (int[][] grid : grids) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + cherryPickup(grid));
            System.out.println();
        }
    }

    public static int cherryPickup(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0][0] == -1) {
            return 0;
        }
        int n = grid.length;
        int[][] dp = new int[n][n];
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        dp[0][0] = grid[0][0];
        for (int t = 1; t <= 2 * n - 2; t++) {
            int[][] next = new int[n][n];
            for (int[] row : next) {
                Arrays.fill(row, Integer.MIN_VALUE);
            }
            for (int r1 = Math.max(0, t - n + 1); r1 <= Math.min(n - 1, t); r1++) {
                for (int r2 = Math.max(0, t - n + 1); r2 <= Math.min(n - 1, t); r2++) {
                    int c1 = t - r1;
                    int c2 = t - r2;
                    if (grid[r1][c1] == -1 || grid[r2][c2] == -1) {
                        continue;
                    }
                    int best = dp[r1][r2];
                    if (r1 > 0) {
                        best = Math.max(best, dp[r1 - 1][r2]);
                    }
                    if (r2 > 0) {
                        best = Math.max(best, dp[r1][r2 - 1]);
                    }
                    if (r1 > 0 && r2 > 0) {
                        best = Math.max(best, dp[r1 - 1][r2 - 1]);
                    }
                    if (best == Integer.MIN_VALUE) {
                        continue;
                    }
                    int cherries = grid[r1][c1] + (r1 == r2 ? 0 : grid[r2][c2]);
                    next[r1][r2] = best + cherries;
                }
            }
            dp = next;
        }
        return Math.max(0, dp[n - 1][n - 1]);
    }
}
