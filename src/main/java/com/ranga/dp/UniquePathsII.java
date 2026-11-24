package com.ranga.dp;

import java.util.Arrays;

/**
 * A robot starts at the top-left cell of an {@code m x n} grid and can only move right or down. Some cells are obstacles, marked {@code 1}. Return the number of distinct paths to the bottom-right cell that avoid every obstacle.
 *
 * <p><b>Algorithm Strategy (Grid DP with a Rolling Row):</b>
 * <ol>
 *   <li>{@code ways[j]} holds the number of paths to column {@code j} of the current row.</li>
 *   <li>If the start or the end is an obstacle, the answer is 0.</li>
 *   <li>For each cell: if it is an obstacle, set {@code ways[j] = 0}; otherwise add the paths from the left, {@code ways[j] += ways[j - 1]} (the value already in {@code ways[j]} came from above).</li>
 *   <li>The answer is {@code ways[n - 1]} after the last row.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the 3x3 grid with one obstacle in the centre: the first row is {@code 1 1 1}, the second row is {@code 1 0 1}, the last row is {@code 1 1 2}.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Each cell is processed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — One row of counters is reused.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class UniquePathsII {

    public static void main(String[] args) {
        int[][][] inputs = {
            {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}},
            {{0, 1}, {0, 0}},
            {{1}},
            {{0}}
        };
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + uniquePathsWithObstacles(grid));
            System.out.println();
        }
    }

    public static int uniquePathsWithObstacles(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0 || grid[0][0] == 1) {
            return 0;
        }
        int n = grid[0].length;
        int[] ways = new int[n];
        ways[0] = 1;
        for (int[] row : grid) {
            for (int j = 0; j < n; j++) {
                if (row[j] == 1) {
                    ways[j] = 0;
                } else if (j > 0) {
                    ways[j] += ways[j - 1];
                }
            }
        }
        return ways[n - 1];
    }
}
