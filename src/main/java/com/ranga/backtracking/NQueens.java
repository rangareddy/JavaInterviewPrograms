package com.ranga.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Place {@code n} queens on an {@code n x n} chessboard so that no two queens attack each other (no two share a row, column or diagonal). Return all distinct solutions; in each solution {@code 'Q'} marks a queen and {@code '.'} an empty square.
 *
 * <p><b>Algorithm Strategy (Backtracking Row by Row with Column and Diagonal Sets):</b>
 * <ol>
 *   <li>Place exactly one queen per row, going from the top row to the bottom. Row {@code r} tries every column {@code c}.</li>
 *   <li>A square is attacked if its column {@code c}, its diagonal {@code r - c} or its anti-diagonal {@code r + c} already holds a queen; three boolean arrays make this check {@code O(1)}.</li>
 *   <li>If the square is free, place the queen (mark column and both diagonals), recurse on row {@code r + 1}, and unmark everything afterwards (backtrack).</li>
 *   <li>When all {@code n} rows have a queen, convert the recorded columns to the board strings and add the solution.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 4} there are exactly 2 solutions: {@code .Q.. / ...Q / Q... / ..Q.} and {@code ..Q. / Q... / ...Q / .Q..}.</li>
 *   <li>For {@code n = 1} the single queen fits; for {@code n = 2} and {@code n = 3} there is no solution.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n!)} — Upper bound on the number of explored placements; pruning removes most of them.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The three marker arrays and the recursion (excluding the output).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NQueens {

    public static void main(String[] args) {
        int[] inputs = {4, 1, 2, 6};
        for (int n : inputs) {
            List<List<String>> solutions = solveNQueens(n);
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + solutions.size() + " solution(s)" + (n <= 4 ? " " + solutions : ""));
            System.out.println();
        }
    }

    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        place(0, n, new int[n], new boolean[n], new boolean[2 * n - 1], new boolean[2 * n - 1], result);
        return result;
    }

    // queenColumn[r] = column of the queen in row r
    private static void place(int row, int n, int[] queenColumn, boolean[] columns, boolean[] diagonals, boolean[] antiDiagonals, List<List<String>> result) {
        if (row == n) {
            result.add(toBoard(queenColumn, n));
            return;
        }
        for (int col = 0; col < n; col++) {
            int diagonal = row - col + n - 1;
            int antiDiagonal = row + col;
            if (columns[col] || diagonals[diagonal] || antiDiagonals[antiDiagonal]) {
                continue;
            }
            columns[col] = diagonals[diagonal] = antiDiagonals[antiDiagonal] = true;
            queenColumn[row] = col;
            place(row + 1, n, queenColumn, columns, diagonals, antiDiagonals, result);
            columns[col] = diagonals[diagonal] = antiDiagonals[antiDiagonal] = false;
        }
    }

    private static List<String> toBoard(int[] queenColumn, int n) {
        List<String> board = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            char[] line = new char[n];
            Arrays.fill(line, '.');
            line[queenColumn[row]] = 'Q';
            board.add(new String(line));
        }
        return board;
    }
}
