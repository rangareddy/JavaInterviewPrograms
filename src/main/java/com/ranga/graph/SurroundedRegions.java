package com.ranga.graph;

import java.util.Arrays;

/**
 * Given an {@code m x n} board of 'X' and 'O', capture all regions that are 4-directionally surrounded by 'X': flip every 'O' of such a region to 'X'. An 'O' region touching the border of the board is not surrounded and stays unchanged.
 *
 * <p><b>Algorithm Strategy (Mark the Safe Regions from the Border):</b>
 * <ol>
 *   <li>An 'O' survives exactly when it is connected (through 'O' cells) to an 'O' on the border of the board.</li>
 *   <li>Run a DFS from every 'O' on the four borders and temporarily mark all reached cells with 'T' (safe).</li>
 *   <li>Scan the board: every remaining 'O' is surrounded, so flip it to 'X'.</li>
 *   <li>Finally turn the temporary 'T' marks back into 'O'.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the 4 x 4 example the 'O' at (3,1) is on the border, so it and everything connected to it stay; the group of three 'O' in the middle is not connected to the border.</li>
 *   <li>Result: the middle group becomes 'X'; only (3,1) remains an 'O'.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Each cell is visited a constant number of times.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — Recursion depth in the worst case.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SurroundedRegions {

    public static void main(String[] args) {
        String[][] inputs = {
                {"XXXX", "XOOX", "XXOX", "XOXX"},
                {"X"},
                {"OOO", "OXO", "OOO"},
                {"XXX", "XOX", "XXX"}
        };
        for (String[] rows : inputs) {
            char[][] board = new char[rows.length][];
            for (int i = 0; i < rows.length; i++) {
                board[i] = rows[i].toCharArray();
            }
            System.out.println("Input  : board = " + Arrays.toString(rows));
            solve(board);
            String[] result = new String[board.length];
            for (int i = 0; i < board.length; i++) {
                result[i] = new String(board[i]);
            }
            System.out.println("Output : " + Arrays.toString(result));
            System.out.println();
        }
    }

    public static void solve(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }

        int rows = board.length;
        int cols = board[0].length;
        for (int i = 0; i < rows; i++) {
            markSafe(board, i, 0);
            markSafe(board, i, cols - 1);
        }
        for (int j = 0; j < cols; j++) {
            markSafe(board, 0, j);
            markSafe(board, rows - 1, j);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';              // surrounded
                } else if (board[i][j] == 'T') {
                    board[i][j] = 'O';              // connected to the border: restore
                }
            }
        }
    }

    private static void markSafe(char[][] board, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != 'O') {
            return;
        }
        board[i][j] = 'T';
        markSafe(board, i + 1, j);
        markSafe(board, i - 1, j);
        markSafe(board, i, j + 1);
        markSafe(board, i, j - 1);
    }
}
