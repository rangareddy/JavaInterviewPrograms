package com.ranga.graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * In an {@code n x n} grid, {@code grid[i][j]} is the elevation at that cell. At time {@code t} the water depth is {@code t}, and you can swim from a cell to a 4-directionally adjacent cell only if both elevations are at most {@code t}. Starting at the top-left cell, return the least time until you can reach the bottom-right cell.
 *
 * <p><b>Algorithm Strategy (Dijkstra on the Path's Maximum Elevation):</b>
 * <ol>
 *   <li>The time needed for a path is the highest elevation on it, because you must wait until the water covers its highest cell.</li>
 *   <li>So the task is to find the path that minimises the maximum elevation. This is Dijkstra with "cost = maximum so far" instead of a sum.</li>
 *   <li>Use a min-heap of {@code (maximum elevation of the path so far, row, col)}, starting with the top-left cell.</li>
 *   <li>Pop the cheapest cell; when it is the bottom-right corner its value is the answer. Otherwise push each unvisited neighbour with {@code max(current maximum, neighbour elevation)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[0,2],[1,3]]}: the path 0 {@code ->} 1 {@code ->} 3 has maximum 3, the path 0 {@code ->} 2 {@code ->} 3 also 3.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2 log n)} — {@code n^2} cells, each pushed to the heap at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n^2)} — Visited array and heap.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SwimInRisingWater {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{0, 2}, {1, 3}},
                {{0, 1, 2, 3, 4}, {24, 23, 22, 21, 5}, {12, 13, 14, 15, 16}, {11, 17, 18, 19, 20}, {10, 9, 8, 7, 6}},
                {{0}},
                {{3, 2}, {0, 1}}
        };
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + swimInWater(grid));
            System.out.println();
        }
    }

    public static int swimInWater(int[][] grid) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt((int[] a) -> a[0]));      // {time, row, col}
        heap.add(new int[]{grid[0][0], 0, 0});
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!heap.isEmpty()) {
            int[] current = heap.poll();
            int time = current[0];
            int row = current[1];
            int col = current[2];
            if (visited[row][col]) {
                continue;
            }
            visited[row][col] = true;
            if (row == n - 1 && col == n - 1) {
                return time;
            }
            for (int[] d : directions) {
                int x = row + d[0];
                int y = col + d[1];
                if (x >= 0 && y >= 0 && x < n && y < n && !visited[x][y]) {
                    heap.add(new int[]{Math.max(time, grid[x][y]), x, y});
                }
            }
        }
        return -1;
    }
}
