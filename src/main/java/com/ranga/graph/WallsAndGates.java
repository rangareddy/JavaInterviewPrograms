package com.ranga.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Given an {@code m x n} grid of rooms with the values -1 (a wall), 0 (a gate) and {@code INF = 2147483647} (an empty room), fill every empty room with the distance (number of steps) to its nearest gate. A room that cannot reach a gate stays {@code INF}.
 *
 * <p><b>Algorithm Strategy (Multi-Source BFS from All Gates):</b>
 * <ol>
 *   <li>Instead of searching from every room, start from all gates at the same time: put every gate into the BFS queue.</li>
 *   <li>Process the queue level by level. For a cell with distance {@code d}, every unvisited empty neighbour is reached with distance {@code d + 1}.</li>
 *   <li>A neighbour is "unvisited" while it still holds {@code INF}; overwriting it with the distance both records the answer and marks it visited.</li>
 *   <li>Because BFS expands in increasing distance, the first time a room is reached it gets the shortest distance to some gate.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the 4 x 4 example, the gates at (0,2) and (3,0) spread outwards: the room (0,3) next to the first gate gets 1, (1,2) gets 1, (0,0) is 3 steps from the second gate.</li>
 *   <li>Result: {@code [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Each cell enters the queue at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — The queue in the worst case.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class WallsAndGates {

    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        int[][][] inputs = {
                {{INF, -1, 0, INF}, {INF, INF, INF, -1}, {INF, -1, INF, -1}, {0, -1, INF, INF}},
                {{-1}},
                {{INF, INF}, {INF, 0}}
        };
        for (int[][] rooms : inputs) {
            System.out.println("Input  : rooms = " + show(rooms));
            wallsAndGates(rooms);
            System.out.println("Output : " + show(rooms));
            System.out.println();
        }
    }

    public static void wallsAndGates(int[][] rooms) {
        if (rooms == null || rooms.length == 0) {
            return;
        }

        int rows = rooms.length;
        int cols = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (rooms[i][j] == 0) {
                    queue.add(new int[]{i, j});
                }
            }
        }

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            for (int[] d : directions) {
                int x = cell[0] + d[0];
                int y = cell[1] + d[1];
                if (x >= 0 && y >= 0 && x < rows && y < cols && rooms[x][y] == INF) {
                    rooms[x][y] = rooms[cell[0]][cell[1]] + 1;
                    queue.add(new int[]{x, y});
                }
            }
        }
    }

    private static String show(int[][] rooms) {
        return Arrays.deepToString(rooms).replace(String.valueOf(INF), "INF");
    }
}
