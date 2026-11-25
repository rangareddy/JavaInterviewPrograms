package com.ranga.array;

import java.util.Arrays;

/**
 * A {@code m x n} board holds candies of different types (positive integers); {@code 0} means an empty cell. Repeatedly: crush every run of three or more candies of the same type in a row or a column at the same time (set them to 0), then let the remaining candies fall down to fill the empty cells. Stop when no more runs exist and return the stable board.
 *
 * <p><b>Algorithm Strategy (Mark by Negation, Crush, Drop, Repeat):</b>
 * <ol>
 *   <li>Scan every row and column for runs of three or more equal non-zero candies and mark all of them by making them negative (so the original type is still readable through {@code Math.abs}).</li>
 *   <li>If nothing was marked, the board is stable.</li>
 *   <li>Otherwise, for each column compact the non-negative candies to the bottom and fill the top with zeros.</li>
 *   <li>Repeat from the first step.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>A column of three equal candies is marked, crushed and the candies above fall down; the fall may create new runs, so the process repeats.</li>
 *   <li>The printed result is the final stable board for the classic 10x5 sample.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O((m * n)^2) worst case} — Each round is O(m * n) and rounds can repeat many times.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — The board is updated in place.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CandyCrush {

    public static void main(String[] args) {
        int[][] board = {
            {110, 5, 112, 113, 114}, {210, 211, 5, 213, 214}, {310, 311, 3, 313, 314}, {410, 411, 412, 5, 414}, {5, 1, 512, 3, 3},
            {610, 4, 1, 613, 614}, {710, 1, 2, 713, 714}, {810, 1, 2, 1, 1}, {1, 1, 2, 2, 2}, {4, 1, 4, 4, 1014}
        };
        System.out.println("Input  : board = " + Arrays.deepToString(board));
        System.out.println("Output : " + Arrays.deepToString(candyCrush(board)));
    }

    public static int[][] candyCrush(int[][] board) {
        if (board == null || board.length == 0) {
            return board;
        }
        int rows = board.length;
        int cols = board[0].length;
        boolean crushed = true;
        while (crushed) {
            crushed = false;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j + 2 < cols; j++) {
                    int v = Math.abs(board[i][j]);
                    if (v != 0 && v == Math.abs(board[i][j + 1]) && v == Math.abs(board[i][j + 2])) {
                        board[i][j] = board[i][j + 1] = board[i][j + 2] = -v;
                        crushed = true;
                    }
                }
            }
            for (int j = 0; j < cols; j++) {
                for (int i = 0; i + 2 < rows; i++) {
                    int v = Math.abs(board[i][j]);
                    if (v != 0 && v == Math.abs(board[i + 1][j]) && v == Math.abs(board[i + 2][j])) {
                        board[i][j] = board[i + 1][j] = board[i + 2][j] = -v;
                        crushed = true;
                    }
                }
            }
            if (crushed) {
                for (int j = 0; j < cols; j++) {
                    int write = rows - 1;
                    for (int i = rows - 1; i >= 0; i--) {
                        if (board[i][j] > 0) {
                            board[write--][j] = board[i][j];
                        }
                    }
                    while (write >= 0) {
                        board[write--][j] = 0;
                    }
                }
            }
        }
        return board;
    }
}
