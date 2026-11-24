package com.ranga.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Given an {@code m x n} binary grid, an island is a group of {@code 1}s connected four-directionally. Two islands are the same if one can be translated (not rotated or reflected) to equal the other. Return the number of distinct island shapes.
 *
 * <p><b>Algorithm Strategy (DFS with a Relative-Path Signature):</b>
 * <ol>
 *   <li>Scan the grid. When an unvisited {@code 1} is found, start a DFS and sink the island as you visit it.</li>
 *   <li>Record the shape as the sequence of moves taken by the DFS ({@code U}, {@code D}, {@code L}, {@code R}) with a marker {@code B} when backtracking.</li>
 *   <li>Because the move sequence is independent of the island's position, translated copies produce the same signature.</li>
 *   <li>Add each signature to a {@code HashSet} and return its size.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,1,0,0,0],[1,1,0,0,0],[0,0,0,1,1],[0,0,0,1,1]]}: both islands are 2x2 squares and have the same signature.</li>
 *   <li>Result: {@code 1}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Each cell is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — Recursion and the set of signatures.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NumberOfDistinctIslands {

    public static void main(String[] args) {
        int[][][] grids = {
            {{1, 1, 0, 0, 0}, {1, 1, 0, 0, 0}, {0, 0, 0, 1, 1}, {0, 0, 0, 1, 1}},
            {{1, 1, 0, 1, 1}, {1, 0, 0, 0, 0}, {0, 0, 0, 0, 1}, {1, 1, 0, 1, 1}},
            {{0, 0}, {0, 0}}
        };
        for (int[][] grid : grids) {
            int[][] copy = new int[grid.length][];
            for (int i = 0; i < grid.length; i++) {
                copy[i] = grid[i].clone();
            }
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + numDistinctIslands(copy));
            System.out.println();
        }
    }

    public static int numDistinctIslands(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        Set<String> shapes = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    StringBuilder path = new StringBuilder();
                    explore(grid, i, j, 'S', path);
                    shapes.add(path.toString());
                }
            }
        }
        return shapes.size();
    }

    private static void explore(int[][] grid, int i, int j, char move, StringBuilder path) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != 1) {
            return;
        }
        grid[i][j] = 2;
        path.append(move);
        explore(grid, i + 1, j, 'D', path);
        explore(grid, i - 1, j, 'U', path);
        explore(grid, i, j + 1, 'R', path);
        explore(grid, i, j - 1, 'L', path);
        path.append('B');
    }
}
