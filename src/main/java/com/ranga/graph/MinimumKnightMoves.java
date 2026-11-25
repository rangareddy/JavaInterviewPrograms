package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * On an infinite chessboard with coordinates from {@code -infinity} to {@code +infinity}, a knight starts at {@code (0, 0)}. Given a target {@code (x, y)}, return the minimum number of moves for the knight to reach it. A knight moves two squares in one direction and one square perpendicular.
 *
 * <p><b>Algorithm Strategy (BFS on the First Quadrant):</b>
 * <ol>
 *   <li>The board is symmetric, so replace {@code (x, y)} by {@code (|x|, |y|)}.</li>
 *   <li>BFS from {@code (0, 0)} over the eight knight moves, but allow squares slightly outside the quadrant (a small margin, for example coordinates down to -2) since the shortest path may dip there.</li>
 *   <li>Keep a visited set of coordinates.</li>
 *   <li>The first time {@code (|x|, |y|)} is dequeued, the number of levels is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code (2, 1)} one knight move is enough.</li>
 *   <li>For {@code (5, 5)} the shortest path takes 4 moves.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(x * y)} — The BFS visits squares in the bounding box.</li>
 *   <li><b>Space Complexity:</b> {@code O(x * y)} — The visited set.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumKnightMoves {

    public static void main(String[] args) {
        int[][] targets = {{2, 1}, {5, 5}, {0, 0}, {-3, 4}};
        for (int[] t : targets) {
            System.out.println("Input  : x = " + t[0] + ", y = " + t[1]);
            System.out.println("Output : " + minKnightMoves(t[0], t[1]));
            System.out.println();
        }
    }

    private static final int[][] MOVES = {{1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};

    public static int minKnightMoves(int x, int y) {
        int tx = Math.abs(x);
        int ty = Math.abs(y);
        Queue<int[]> queue = new ArrayDeque<>();
        Set<Long> visited = new HashSet<>();
        queue.add(new int[]{0, 0});
        visited.add(key(0, 0));
        int moves = 0;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                int[] cell = queue.poll();
                if (cell[0] == tx && cell[1] == ty) {
                    return moves;
                }
                for (int[] m : MOVES) {
                    int nx = cell[0] + m[0];
                    int ny = cell[1] + m[1];
                    if (nx >= -2 && ny >= -2 && visited.add(key(nx, ny))) {
                        queue.add(new int[]{nx, ny});
                    }
                }
            }
            moves++;
        }
        return -1;
    }

    private static long key(int x, int y) {
        return ((long) x << 32) ^ (y & 0xffffffffL);
    }
}
