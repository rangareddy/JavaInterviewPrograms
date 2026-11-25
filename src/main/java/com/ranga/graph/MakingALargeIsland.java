package com.ranga.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given an {@code n x n} binary grid, you may change at most one {@code 0} to {@code 1}. Return the size of the largest island (a group of four-directionally connected {@code 1}s) after the change.
 *
 * <p><b>Algorithm Strategy (Label Islands then Try Each Flip):</b>
 * <ol>
 *   <li>Flood-fill every island with a unique id (starting at 2) and record its size.</li>
 *   <li>For every {@code 0} cell, collect the distinct ids of its four neighbours.</li>
 *   <li>Flipping that cell gives size {@code 1 + sum of the sizes of those distinct islands}.</li>
 *   <li>The answer is the maximum of that over all zeros, or the largest existing island if there are no zeros.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,0],[0,1]]} flipping either zero joins both 1-cell islands plus the flipped cell.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Labelling is linear and each zero looks at four neighbours.</li>
 *   <li><b>Space Complexity:</b> {@code O(n^2)} — The sizes and recursion.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MakingALargeIsland {

    public static void main(String[] args) {
        int[][][] grids = {{{1, 0}, {0, 1}}, {{1, 1}, {1, 0}}, {{1, 1}, {1, 1}}, {{0, 0}, {0, 0}}};
        for (int[][] grid : grids) {
            int[][] copy = new int[grid.length][];
            for (int i = 0; i < grid.length; i++) {
                copy[i] = grid[i].clone();
            }
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + largestIsland(copy));
            System.out.println();
        }
    }

    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static int largestIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int n = grid.length;
        Map<Integer, Integer> size = new HashMap<>();
        int id = 2;
        int best = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    int s = fill(grid, i, j, id);
                    size.put(id++, s);
                    best = Math.max(best, s);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    Set<Integer> neighbours = new HashSet<>();
                    for (int[] d : DIRS) {
                        int r = i + d[0];
                        int c = j + d[1];
                        if (r >= 0 && r < n && c >= 0 && c < n && grid[r][c] > 1) {
                            neighbours.add(grid[r][c]);
                        }
                    }
                    int total = 1;
                    for (int nb : neighbours) {
                        total += size.get(nb);
                    }
                    best = Math.max(best, total);
                }
            }
        }
        return best;
    }

    private static int fill(int[][] grid, int i, int j, int id) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid.length || grid[i][j] != 1) {
            return 0;
        }
        grid[i][j] = id;
        int count = 1;
        for (int[] d : DIRS) {
            count += fill(grid, i + d[0], j + d[1], id);
        }
        return count;
    }
}
