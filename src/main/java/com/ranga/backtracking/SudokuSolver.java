package com.ranga.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Fill the empty cells ({@code '.'}) of a 9 x 9 Sudoku board so that every row, every column and every one of the nine 3 x 3 boxes contains the digits 1 to 9 exactly once. The input is guaranteed to have exactly one solution.
 *
 * <p><b>Algorithm Strategy (Backtracking with Bitmasks for Rows, Columns and Boxes):</b>
 * <ol>
 *   <li>Keep three arrays of 9 bitmasks: {@code rows[r]}, {@code cols[c]} and {@code boxes[b]}, where bit {@code d} is set when digit {@code d + 1} is already used. Fill them from the given digits.</li>
 *   <li>Collect the empty cells and solve recursively. At each cell, the digits that are still possible are those whose bit is clear in {@code rows[r] | cols[c] | boxes[b]}, found in constant time.</li>
 *   <li>Try each possible digit: set its bit in the three masks, recurse to the next empty cell, and if the recursion fails, clear the bits (backtrack) and try the next digit.</li>
 *   <li>Pick the empty cell with the fewest candidates first (the most constrained cell). This prunes the search a lot and keeps hard puzzles fast; with a fixed left-to-right order the same code is much slower.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the empty center cell (row 4, column 4) of the example board: its row holds 4, 8, 3, 1, its column holds 7, 9, 6, 2, 1, 8 and its box holds 6, 8, 3, 2.</li>
 *   <li>The used digits are {@code {1, 2, 3, 4, 6, 7, 8, 9}}, so only 5 is possible and it is placed without any branching. Cells with several candidates are branched on, and a dead end undoes the placements.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(9^m)} — {@code m} is the number of empty cells in the worst case; the most-constrained-first rule makes it fast in practice.</li>
 *   <li><b>Space Complexity:</b> {@code O(m)} — Recursion depth and the three masks.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SudokuSolver {

    public static void main(String[] args) {
        String[] puzzle = {
                "53..7....",
                "6..195...",
                ".98....6.",
                "8...6...3",
                "4..8.3..1",
                "7...2...6",
                ".6....28.",
                "...419..5",
                "....8..79"
        };
        char[][] board = new char[9][];
        for (int i = 0; i < 9; i++) {
            board[i] = puzzle[i].toCharArray();
        }
        System.out.println("Input  :");
        print(board);
        solveSudoku(board);
        System.out.println("Output :");
        print(board);
    }

    private static void print(char[][] board) {
        for (char[] row : board) {
            System.out.println("         " + new String(row));
        }
    }

    public static void solveSudoku(char[][] board) {
        int[] rows = new int[9];
        int[] cols = new int[9];
        int[] boxes = new int[9];
        List<int[]> empty = new ArrayList<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') {
                    empty.add(new int[]{r, c});
                } else {
                    int bit = 1 << (board[r][c] - '1');
                    rows[r] |= bit;
                    cols[c] |= bit;
                    boxes[r / 3 * 3 + c / 3] |= bit;
                }
            }
        }
        solve(board, rows, cols, boxes, empty, 0);
    }

    private static boolean solve(char[][] board, int[] rows, int[] cols, int[] boxes, List<int[]> empty, int filled) {
        if (filled == empty.size()) {
            return true;
        }
        int best = filled;
        int bestCount = 10;
        for (int i = filled; i < empty.size(); i++) {
            int[] cell = empty.get(i);
            int used = rows[cell[0]] | cols[cell[1]] | boxes[cell[0] / 3 * 3 + cell[1] / 3];
            int count = 9 - Integer.bitCount(used);
            if (count < bestCount) {
                bestCount = count;
                best = i;
                if (count <= 1) {
                    break;
                }
            }
        }
        java.util.Collections.swap(empty, filled, best);
        int[] cell = empty.get(filled);
        int r = cell[0];
        int c = cell[1];
        int b = r / 3 * 3 + c / 3;
        int available = ~(rows[r] | cols[c] | boxes[b]) & 0x1FF;
        for (int d = 0; d < 9; d++) {
            int bit = 1 << d;
            if ((available & bit) == 0) {
                continue;
            }
            rows[r] |= bit;
            cols[c] |= bit;
            boxes[b] |= bit;
            board[r][c] = (char) ('1' + d);
            if (solve(board, rows, cols, boxes, empty, filled + 1)) {
                return true;
            }
            rows[r] &= ~bit;
            cols[c] &= ~bit;
            boxes[b] &= ~bit;
            board[r][c] = '.';
        }
        java.util.Collections.swap(empty, filled, best);
        return false;
    }
}
