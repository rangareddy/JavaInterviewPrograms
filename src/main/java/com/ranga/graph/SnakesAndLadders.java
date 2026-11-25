package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * You are given an {@code n x n} board numbered {@code 1} to {@code n^2} in a boustrophedon style: starting from the bottom-left and alternating the direction every row. A cell with a value other than -1 is the start of a snake or a ladder and jumps to that square. From square {@code s} you roll a die and move to any of {@code s + 1} to {@code s + 6}; if the target holds a snake or ladder you must take it. Return the least number of moves to reach square {@code n^2}, or -1 if impossible.
 *
 * <p><b>Algorithm Strategy (BFS over Squares):</b>
 * <ol>
 *   <li>Convert a square number to its row and column: {@code row = (n - 1) - (s - 1) / n}, and the column depends on the parity of the row counted from the bottom.</li>
 *   <li>BFS from square 1; the queue holds squares and the level is the number of moves.</li>
 *   <li>For each square, try the six die outcomes; if the cell has a jump, move to its destination, then mark and enqueue the square if it is unvisited.</li>
 *   <li>Return the level at which square {@code n^2} is reached, or -1.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>On the classic 6x6 board with a ladder 2 -> 15, 14 -> 35 and others, the quickest route needs 4 rolls.</li>
 *   <li>Result: {@code 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Each square is visited once with six moves.</li>
 *   <li><b>Space Complexity:</b> {@code O(n^2)} — The visited set and queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SnakesAndLadders {

    public static void main(String[] args) {
        int[][][] boards = {
            {{-1, -1, -1, -1, -1, -1}, {-1, -1, -1, -1, -1, -1}, {-1, -1, -1, -1, -1, -1}, {-1, 35, -1, -1, 13, -1}, {-1, -1, -1, -1, -1, -1}, {-1, 15, -1, -1, -1, -1}},
            {{-1, -1}, {-1, 3}},
            {{1, 1, -1}, {1, 1, 1}, {-1, 1, 1}}
        };
        for (int[][] board : boards) {
            System.out.println("Input  : board = " + Arrays.deepToString(board));
            System.out.println("Output : " + snakesAndLadders(board));
            System.out.println();
        }
    }

    public static int snakesAndLadders(int[][] board) {
        int n = board.length;
        int target = n * n;
        boolean[] seen = new boolean[target + 1];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        seen[1] = true;
        int moves = 0;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                int square = queue.poll();
                if (square == target) {
                    return moves;
                }
                for (int die = 1; die <= 6 && square + die <= target; die++) {
                    int next = square + die;
                    int value = cell(board, next);
                    if (value != -1) {
                        next = value;
                    }
                    if (!seen[next]) {
                        seen[next] = true;
                        queue.add(next);
                    }
                }
            }
            moves++;
        }
        return -1;
    }

    private static int cell(int[][] board, int square) {
        int n = board.length;
        int rowFromBottom = (square - 1) / n;
        int column = (square - 1) % n;
        if (rowFromBottom % 2 == 1) {
            column = n - 1 - column;
        }
        return board[n - 1 - rowFromBottom][column];
    }
}
