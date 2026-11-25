package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * An {@code n x n} binary grid contains exactly two islands (groups of four-directionally connected {@code 1}s). You may flip {@code 0}s to {@code 1}s to connect the two islands into one. Return the smallest number of {@code 0}s you must flip.
 *
 * <p><b>Algorithm Strategy (Colour One Island, BFS Outward):</b>
 * <ol>
 *   <li>Find the first {@code 1} and flood-fill its island with DFS, marking its cells as 2 and putting them in a queue.</li>
 *   <li>Run a multi-source BFS from all those cells, expanding one layer at a time through {@code 0} cells.</li>
 *   <li>Each layer corresponds to one flipped zero.</li>
 *   <li>When the BFS reaches a cell with value 1 (the other island), return the number of layers expanded.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[0,1],[1,0]]} the islands are the two diagonal cells; one flipped zero joins them.</li>
 *   <li>Result: {@code 1}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Each cell enters the queue at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n^2)} — The queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ShortestBridge {

    public static void main(String[] args) {
        int[][][] grids = {{{0, 1}, {1, 0}}, {{0, 1, 0}, {0, 0, 0}, {0, 0, 1}}, {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}};
        for (int[][] grid : grids) {
            int[][] copy = new int[grid.length][];
            for (int i = 0; i < grid.length; i++) {
                copy[i] = grid[i].clone();
            }
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + shortestBridge(copy));
            System.out.println();
        }
    }

    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static int shortestBridge(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int n = grid.length;
        Queue<int[]> queue = new ArrayDeque<>();
        boolean found = false;
        for (int i = 0; i < n && !found; i++) {
            for (int j = 0; j < n && !found; j++) {
                if (grid[i][j] == 1) {
                    mark(grid, i, j, queue);
                    found = true;
                }
            }
        }
        int flips = 0;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                int[] cell = queue.poll();
                for (int[] d : DIRS) {
                    int r = cell[0] + d[0];
                    int c = cell[1] + d[1];
                    if (r < 0 || c < 0 || r >= n || c >= n || grid[r][c] == 2) {
                        continue;
                    }
                    if (grid[r][c] == 1) {
                        return flips;
                    }
                    grid[r][c] = 2;
                    queue.add(new int[]{r, c});
                }
            }
            flips++;
        }
        return -1;
    }

    private static void mark(int[][] grid, int i, int j, Queue<int[]> queue) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid.length || grid[i][j] != 1) {
            return;
        }
        grid[i][j] = 2;
        queue.add(new int[]{i, j});
        for (int[] d : DIRS) {
            mark(grid, i + d[0], j + d[1], queue);
        }
    }
}
