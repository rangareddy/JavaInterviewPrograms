package com.ranga.graph;

import java.util.Arrays;

/**
 * Given an {@code m x n} binary grid, an island is a group of 1s connected 4-directionally (horizontally or vertically). The area of an island is the number of cells with value 1 in it. Return the maximum area of an island in the grid, or 0 if there is no island.
 *
 * <p><b>Algorithm Strategy (DFS Flood Fill that Returns the Area):</b>
 * <ol>
 *   <li>Scan every cell of (a copy of) the grid. An unvisited land cell starts a new island.</li>
 *   <li>A DFS from that cell counts the cell itself plus the areas reached through its four neighbours; each visited land cell is set to 0 so it is never counted twice.</li>
 *   <li>The DFS call returns the size of the island that was flooded; keep the maximum over all starting cells.</li>
 *   <li>Water cells and cells outside the grid return 0 immediately.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>In the standard example grid (8 rows, 13 columns) the islands have areas 1, 4, 3, 1, 3, 6 ... and the largest one is the group of six cells in the lower right.</li>
 *   <li>Result: {@code 6}. A grid of only zeros gives {@code 0}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Each cell is visited a constant number of times.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — Recursion depth in the worst case (plus the copy of the grid).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaxAreaOfIsland {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                        {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0}},
                {{1, 1}, {1, 0}},
                {{1, 0, 1}, {0, 1, 0}}
        };
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + maxAreaOfIsland(grid));
            System.out.println();
        }
    }

    public static int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int[][] copy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();              // do not modify the caller's grid
        }

        int best = 0;
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[0].length; j++) {
                best = Math.max(best, area(copy, i, j));
            }
        }
        return best;
    }

    private static int area(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != 1) {
            return 0;
        }
        grid[i][j] = 0;                             // mark as visited
        return 1 + area(grid, i + 1, j) + area(grid, i - 1, j) + area(grid, i, j + 1) + area(grid, i, j - 1);
    }
}
