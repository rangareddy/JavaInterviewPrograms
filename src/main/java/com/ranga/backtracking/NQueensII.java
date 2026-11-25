package com.ranga.backtracking;

/**
 * The n-queens puzzle places {@code n} queens on an {@code n x n} chessboard so that no two queens attack each other. Given {@code n}, return the number of distinct solutions.
 *
 * <p><b>Algorithm Strategy (Row-by-Row Backtracking with Bitmasks):</b>
 * <ol>
 *   <li>Place one queen per row. Keep three bit masks: occupied columns, occupied {@code /} diagonals and occupied {@code \} diagonals.</li>
 *   <li>For the current row, the free squares are the bits not set in {@code cols | diag1 | diag2}.</li>
 *   <li>For each free square, place the queen (set the bits, shifting the diagonal masks one step for the next row) and recurse.</li>
 *   <li>When all {@code n} rows have a queen, count one solution.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 4} there are two solutions: columns {@code [1, 3, 0, 2]} and {@code [2, 0, 3, 1]}.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n!)} — The pruning makes it much smaller in practice.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Recursion depth.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NQueensII {

    public static void main(String[] args) {
        int[] inputs = {4, 1, 2, 6, 8};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + totalNQueens(n));
            System.out.println();
        }
    }

    public static int totalNQueens(int n) {
        if (n <= 0) {
            return 0;
        }
        return place(n, 0, 0, 0, 0);
    }

    private static int place(int n, int row, int cols, int diag1, int diag2) {
        if (row == n) {
            return 1;
        }
        int count = 0;
        int free = ((1 << n) - 1) & ~(cols | diag1 | diag2);
        while (free != 0) {
            int bit = free & -free;
            free -= bit;
            count += place(n, row + 1, cols | bit, (diag1 | bit) << 1, (diag2 | bit) >> 1);
        }
        return count;
    }
}
