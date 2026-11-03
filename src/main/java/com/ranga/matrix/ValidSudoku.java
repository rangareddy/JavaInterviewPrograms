package com.ranga.matrix;

import java.util.Arrays;

/**
 * Determine whether a 9 x 9 Sudoku board is valid. Only the filled cells need to be checked: each row, each column and each of the nine 3 x 3 sub-boxes must contain the digits 1-9 without repetition. Empty cells are marked '.'. The board does not have to be solvable.
 *
 * <p><b>Algorithm Strategy (Bit Masks for Rows, Columns and Boxes):</b>
 * <ol>
 *   <li>Keep one integer bit mask per row, per column and per 3 x 3 box; bit {@code d} means that digit {@code d} has been seen there.</li>
 *   <li>For every filled cell compute the digit {@code d}, its row {@code r}, its column {@code c} and its box index {@code (r / 3) * 3 + c / 3}.</li>
 *   <li>If bit {@code d} is already set in the mask of the row, the column or the box, the board is invalid.</li>
 *   <li>Otherwise set the bit in all three masks. If the whole board is scanned without a conflict, it is valid.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>In the standard example board every row, column and box contains each digit at most once, so the result is {@code true}.</li>
 *   <li>Changing the first cell to 8 puts a second 8 in the first column and in the first box: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(81) = O(1)} — A fixed number of cells.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Three arrays of nine integers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ValidSudoku {

    public static void main(String[] args) {
        String[] valid = {
                "53..7....", "6..195...", ".98....6.", "8...6...3", "4..8.3..1", "7...2...6", ".6....28.", "...419..5", "....8..79"};
        String[] invalid = valid.clone();
        invalid[0] = "83..7....";
        for (String[] rows : new String[][]{valid, invalid}) {
            char[][] board = new char[9][];
            for (int i = 0; i < 9; i++) {
                board[i] = rows[i].toCharArray();
            }
            System.out.println("Input  : board = " + Arrays.toString(rows));
            System.out.println("Output : " + isValidSudoku(board));
            System.out.println();
        }
    }

    public static boolean isValidSudoku(char[][] board) {
        int[] rows = new int[9];
        int[] cols = new int[9];
        int[] boxes = new int[9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') {
                    continue;
                }
                int bit = 1 << (board[r][c] - '1');
                int box = (r / 3) * 3 + c / 3;
                if ((rows[r] & bit) != 0 || (cols[c] & bit) != 0 || (boxes[box] & bit) != 0) {
                    return false;
                }
                rows[r] |= bit;
                cols[c] |= bit;
                boxes[box] |= bit;
            }
        }
        return true;
    }
}
