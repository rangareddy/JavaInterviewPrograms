package com.ranga.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * In a grid, 0 is an empty cell, 1 a fresh orange and 2 a rotten orange. Every minute, each fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten. Return the minimum number of minutes until no fresh orange is left, or -1 if that is impossible.
 *
 * <p><b>Algorithm Strategy (Multi-Source BFS by Minutes):</b>
 * <ol>
 *   <li>Put all initially rotten oranges into a queue and count the fresh oranges.</li>
 *   <li>Process the queue in rounds; one round is one minute. Every fresh neighbour of a rotten orange becomes rotten (set to 2), is added to the queue and decreases the fresh counter.</li>
 *   <li>Count a minute only for rounds in which at least one orange rotted.</li>
 *   <li>When the queue is empty, return the minutes if no fresh orange remains, otherwise -1 (some orange is unreachable).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[2,1,1],[1,1,0],[0,1,1]]}: minute 1 rots (0,1) and (1,0); minute 2 rots (0,2) and (1,1); minute 3 rots (2,1); minute 4 rots (2,2).</li>
 *   <li>Result: {@code 4}. For {@code [[2,1,1],[0,1,1],[1,0,1]]} the orange at (2,0) is isolated: {@code -1}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Each cell becomes rotten and is queued at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — The queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RottingOranges {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{2, 1, 1}, {1, 1, 0}, {0, 1, 1}},
                {{2, 1, 1}, {0, 1, 1}, {1, 0, 1}},
                {{0, 2}},
                {{1}},
                {{0}}
        };
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + orangesRotting(grid));
            System.out.println();
        }
    }

    public static int orangesRotting(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] copy = new int[rows][];
        Queue<int[]> queue = new LinkedList<>();
        int fresh = 0;
        for (int i = 0; i < rows; i++) {
            copy[i] = grid[i].clone();
            for (int j = 0; j < cols; j++) {
                if (copy[i][j] == 2) {
                    queue.add(new int[]{i, j});
                } else if (copy[i][j] == 1) {
                    fresh++;
                }
            }
        }

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int minutes = 0;
        while (!queue.isEmpty() && fresh > 0) {
            minutes++;
            for (int size = queue.size(); size > 0; size--) {
                int[] cell = queue.poll();
                for (int[] d : directions) {
                    int x = cell[0] + d[0];
                    int y = cell[1] + d[1];
                    if (x >= 0 && y >= 0 && x < rows && y < cols && copy[x][y] == 1) {
                        copy[x][y] = 2;
                        fresh--;
                        queue.add(new int[]{x, y});
                    }
                }
            }
        }
        return fresh == 0 ? minutes : -1;
    }
}
