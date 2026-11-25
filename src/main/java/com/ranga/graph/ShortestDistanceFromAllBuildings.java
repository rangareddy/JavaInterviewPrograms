package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * A grid has {@code 0} (empty land you can walk on), {@code 1} (a building) and {@code 2} (an obstacle). You want to build a house on an empty land cell so that the total travel distance to all buildings is the smallest; you can only move up, down, left and right, and the distance is the number of steps. Return the minimum total distance, or {@code -1} if no empty cell can reach every building.
 *
 * <p><b>Algorithm Strategy (BFS from Each Building):</b>
 * <ol>
 *   <li>Instead of starting from empty cells, run a BFS from every building (usually fewer than empty cells).</li>
 *   <li>Keep two grids: {@code dist[i][j]}, the sum of the distances from all buildings processed so far, and {@code reach[i][j]}, how many buildings reached the cell.</li>
 *   <li>Each BFS only moves through empty cells and adds the distance to {@code dist} and one to {@code reach}.</li>
 *   <li>The answer is the minimum {@code dist[i][j]} among the empty cells whose {@code reach} equals the number of buildings, or -1.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the classic 3x5 sample with three buildings, the best empty cell is at row 1, column 2 with distances 3 + 3 + 1.</li>
 *   <li>Result: {@code 7}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(buildings * m * n)} — One BFS per building.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — Distance and reach grids and a queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ShortestDistanceFromAllBuildings {

    public static void main(String[] args) {
        int[][][] grids = {{{1, 0, 2, 0, 1}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}}, {{1, 0}}, {{1}}, {{1, 2, 0}}};
        for (int[][] grid : grids) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + shortestDistance(grid));
            System.out.println();
        }
    }

    public static int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return -1;
        }
        int m = grid.length;
        int n = grid[0].length;
        int[][] dist = new int[m][n];
        int[][] reach = new int[m][n];
        int buildings = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    buildings++;
                    bfs(grid, i, j, dist, reach);
                }
            }
        }
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0 && reach[i][j] == buildings) {
                    best = Math.min(best, dist[i][j]);
                }
            }
        }
        return best == Integer.MAX_VALUE ? -1 : best;
    }

    private static void bfs(int[][] grid, int si, int sj, int[][] dist, int[][] reach) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] seen = new boolean[m][n];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{si, sj});
        seen[si][sj] = true;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int steps = 0;
        while (!queue.isEmpty()) {
            steps++;
            for (int size = queue.size(); size > 0; size--) {
                int[] cell = queue.poll();
                for (int[] d : dirs) {
                    int r = cell[0] + d[0];
                    int c = cell[1] + d[1];
                    if (r >= 0 && c >= 0 && r < m && c < n && !seen[r][c] && grid[r][c] == 0) {
                        seen[r][c] = true;
                        dist[r][c] += steps;
                        reach[r][c]++;
                        queue.add(new int[]{r, c});
                    }
                }
            }
        }
    }
}
