package com.ranga.matrix;

import java.util.Arrays;

/**
 * You are given a {@code row x col} grid where {@code grid[i][j] = 1} is land and {@code 0} is water. The grid contains exactly one island (connected land cells) and no lakes. Return the perimeter of the island.
 *
 * <p><b>Algorithm Strategy (Four Edges per Cell Minus Shared Edges):</b>
 * <ol>
 *   <li>Each land cell contributes four edges.</li>
 *   <li>Every pair of adjacent land cells hides two edges (one from each cell).</li>
 *   <li>For each land cell, count its land neighbour above and to the left only, so each pair is counted once.</li>
 *   <li>The perimeter is {@code 4 * land - 2 * adjacentPairs}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the 4x4 sample there are 7 land cells and 6 adjacent pairs.</li>
 *   <li>Result: {@code 4 * 7 - 2 * 6 = 16}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(rows * cols)} — Each cell once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class IslandPerimeter {

    public static void main(String[] args) {
        int[][][] inputs = {{{0, 1, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 0}, {1, 1, 0, 0}}, {{1}}, {{1, 0}}, {}};
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + islandPerimeter(grid));
            System.out.println();
        }
    }

    public static int islandPerimeter(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int land = 0;
        int pairs = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    land++;
                    if (i > 0 && grid[i - 1][j] == 1) {
                        pairs++;
                    }
                    if (j > 0 && grid[i][j - 1] == 1) {
                        pairs++;
                    }
                }
            }
        }
        return 4 * land - 2 * pairs;
    }
}
