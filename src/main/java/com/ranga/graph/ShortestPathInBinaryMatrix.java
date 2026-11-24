package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * Given an {@code n x n} binary matrix {@code grid}, return the length of the shortest clear path from the top-left cell to the bottom-right cell, or {@code -1} if there is none. A clear path goes through cells with value 0, and consecutive cells are 8-directionally connected. The length is the number of cells on the path.
 *
 * <p><b>Algorithm Strategy (Breadth-First Search over 8 Directions):</b>
 * <ol>
 *   <li>If the start or the end cell is 1, return -1.</li>
 *   <li>Run BFS from {@code (0, 0)} with {@code distance = 1}, marking visited cells (reuse the grid by setting visited cells to 1).</li>
 *   <li>From each cell enqueue every unvisited 0-neighbour among the 8 directions.</li>
 *   <li>The first time the bottom-right cell is reached, its distance is the answer; if the queue empties, return -1.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[0,1],[1,0]]}: the diagonal move connects the two zeros directly, so the path has two cells.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Each cell is enqueued at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n^2)} — The BFS queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ShortestPathInBinaryMatrix {

    public static void main(String[] args) {
        int[][][] grids = {
            {{0, 1}, {1, 0}},
            {{0, 0, 0}, {1, 1, 0}, {1, 1, 0}},
            {{1, 0, 0}, {1, 1, 0}, {1, 1, 0}},
            {{0}}
        };
        for (int[][] grid : grids) {
            int[][] copy = new int[grid.length][];
            for (int i = 0; i < grid.length; i++) {
                copy[i] = grid[i].clone();
            }
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + shortestPathBinaryMatrix(copy));
            System.out.println();
        }
    }

    public static int shortestPathBinaryMatrix(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0][0] == 1) {
            return -1;
        }
        int n = grid.length;
        if (grid[n - 1][n - 1] == 1) {
            return -1;
        }
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0, 0, 1});
        grid[0][0] = 1;
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            if (cell[0] == n - 1 && cell[1] == n - 1) {
                return cell[2];
            }
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    int r = cell[0] + di;
                    int c = cell[1] + dj;
                    if (r >= 0 && r < n && c >= 0 && c < n && grid[r][c] == 0) {
                        grid[r][c] = 1;
                        queue.add(new int[]{r, c, cell[2] + 1});
                    }
                }
            }
        }
        return -1;
    }
}
