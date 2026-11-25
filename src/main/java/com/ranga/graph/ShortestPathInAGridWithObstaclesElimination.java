package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * Given an {@code m x n} grid of {@code 0} (empty) and {@code 1} (obstacle) and an integer {@code k}, you start at the top-left cell and want to reach the bottom-right cell moving up, down, left or right. You may eliminate at most {@code k} obstacles along the way. Return the minimum number of steps, or {@code -1} if it is impossible.
 *
 * <p><b>Algorithm Strategy (BFS over (cell, remaining eliminations)):</b>
 * <ol>
 *   <li>If {@code k} is at least {@code m + n - 2}, the answer is {@code m + n - 2} immediately (walk straight, eliminating every obstacle).</li>
 *   <li>Otherwise BFS over states {@code (row, col, k remaining)} with a visited set on the same triple.</li>
 *   <li>Moving into an obstacle costs one elimination; moving into an empty cell costs none.</li>
 *   <li>The first time the bottom-right cell is dequeued, its step count is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the sample grid and {@code k = 1}: eliminate the obstacle in the third row, giving a 6-step path.</li>
 *   <li>Result: {@code 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n * k)} — Each state is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n * k)} — The visited array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ShortestPathInAGridWithObstaclesElimination {

    public static void main(String[] args) {
        int[][][] grids = {{{0, 0, 0}, {1, 1, 0}, {0, 0, 0}, {0, 1, 1}, {0, 0, 0}}, {{0, 1, 1}, {1, 1, 1}, {1, 0, 0}}, {{0}}};
        int[] ks = {1, 1, 0};
        for (int i = 0; i < grids.length; i++) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grids[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + shortestPath(grids[i], ks[i]));
            System.out.println();
        }
    }

    public static int shortestPath(int[][] grid, int k) {
        if (grid == null || grid.length == 0) {
            return -1;
        }
        int m = grid.length;
        int n = grid[0].length;
        if (k >= m + n - 2) {
            return m + n - 2;
        }
        boolean[][][] visited = new boolean[m][n][k + 1];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0, 0, k});
        visited[0][0][k] = true;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int steps = 0;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                int[] cell = queue.poll();
                if (cell[0] == m - 1 && cell[1] == n - 1) {
                    return steps;
                }
                for (int[] d : dirs) {
                    int r = cell[0] + d[0];
                    int c = cell[1] + d[1];
                    if (r < 0 || c < 0 || r >= m || c >= n) {
                        continue;
                    }
                    int left = cell[2] - grid[r][c];
                    if (left >= 0 && !visited[r][c][left]) {
                        visited[r][c][left] = true;
                        queue.add(new int[]{r, c, left});
                    }
                }
            }
            steps++;
        }
        return -1;
    }
}
