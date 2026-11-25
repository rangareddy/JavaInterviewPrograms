package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * A ball rolls in a maze (grid of {@code 0} empty and {@code 1} walls). It rolls in one direction (up, down, left or right) until it hits a wall, then it can choose a new direction. Given the {@code start} and {@code destination} coordinates, return {@code true} if the ball can stop at the destination.
 *
 * <p><b>Algorithm Strategy (BFS over Stopping Points):</b>
 * <ol>
 *   <li>From a stopping point, try each of the four directions and roll until the next cell is a wall or the border.</li>
 *   <li>The cell where the ball stops is a new node; if it has not been visited, add it to the queue.</li>
 *   <li>The ball must stop on the destination, not just pass through it, so test destination equality on stopping points.</li>
 *   <li>If the queue empties first, return {@code false}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>In the classic 5x5 maze the ball can roll left, down, left, down, right, down, right and stop at the destination.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n * (m + n))} — Each cell is a stopping point at most once and each roll is at most max(m, n).</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — The visited array and the queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TheMaze {

    public static void main(String[] args) {
        int[][] maze = {{0, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 1, 0}, {1, 1, 0, 1, 1}, {0, 0, 0, 0, 0}};
        int[][][] queries = {{{0, 4}, {4, 4}}, {{0, 4}, {3, 2}}, {{0, 4}, {0, 4}}};
        for (int[][] q : queries) {
            System.out.println("Input  : start = " + Arrays.toString(q[0]) + ", destination = " + Arrays.toString(q[1]));
            System.out.println("Output : " + hasPath(maze, q[0], q[1]));
            System.out.println();
        }
    }

    public static boolean hasPath(int[][] maze, int[] start, int[] destination) {
        if (maze == null || maze.length == 0) {
            return false;
        }
        int m = maze.length;
        int n = maze[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(start);
        visited[start[0]][start[1]] = true;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            if (cell[0] == destination[0] && cell[1] == destination[1]) {
                return true;
            }
            for (int[] d : dirs) {
                int r = cell[0];
                int c = cell[1];
                while (r + d[0] >= 0 && r + d[0] < m && c + d[1] >= 0 && c + d[1] < n && maze[r + d[0]][c + d[1]] == 0) {
                    r += d[0];
                    c += d[1];
                }
                if (!visited[r][c]) {
                    visited[r][c] = true;
                    queue.add(new int[]{r, c});
                }
            }
        }
        return false;
    }
}
