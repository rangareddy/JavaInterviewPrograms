package com.ranga.matrix;

import java.util.Arrays;

/**
 * Conway's Game of Life is played on an {@code m x n} board where each cell is live (1) or dead (0). Every cell interacts with its eight neighbours: a live cell with fewer than two or more than three live neighbours dies; a live cell with two or three live neighbours lives; a dead cell with exactly three live neighbours becomes live. Compute the next state of the board in place, with all cells updating simultaneously.
 *
 * <p><b>Algorithm Strategy (Encode Transitions in the Same Array):</b>
 * <ol>
 *   <li>Use two extra values: {@code 2} means "was live, will die" and {@code 3} means "was dead, will become live".</li>
 *   <li>Count a cell's live neighbours by treating {@code 1} and {@code 2} as originally live.</li>
 *   <li>Apply the rules and write {@code 2} or {@code 3} for cells that change.</li>
 *   <li>A final pass maps {@code 2 -> 0} and {@code 3 -> 1}, so every cell used the original state of its neighbours.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the 4x3 sample board {@code [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]} the next generation is {@code [[0,0,0],[1,0,1],[0,1,1],[0,1,0]]}.</li>
 *   <li>The in-place encoding avoids a second board.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Eight neighbours per cell, plus a final pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — The board is updated in place.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class GameOfLife {

    public static void main(String[] args) {
        int[][][] boards = {
            {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}},
            {{1, 1}, {1, 0}},
            {{1}}
        };
        for (int[][] board : boards) {
            System.out.println("Input  : board = " + Arrays.deepToString(board));
            gameOfLife(board);
            System.out.println("Output : " + Arrays.deepToString(board));
            System.out.println();
        }
    }

    public static void gameOfLife(int[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int live = 0;
                for (int di = -1; di <= 1; di++) {
                    for (int dj = -1; dj <= 1; dj++) {
                        if (di == 0 && dj == 0) {
                            continue;
                        }
                        int r = i + di;
                        int c = j + dj;
                        if (r >= 0 && r < m && c >= 0 && c < n && (board[r][c] == 1 || board[r][c] == 2)) {
                            live++;
                        }
                    }
                }
                if (board[i][j] == 1 && (live < 2 || live > 3)) {
                    board[i][j] = 2;
                } else if (board[i][j] == 0 && live == 3) {
                    board[i][j] = 3;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = board[i][j] == 1 || board[i][j] == 3 ? 1 : 0;
            }
        }
    }
}
