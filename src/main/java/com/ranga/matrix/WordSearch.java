package com.ranga.matrix;

import java.util.Arrays;

/**
 * Given an {@code m x n} grid of characters and a {@code word}, return {@code true} if the word can be built from sequentially adjacent (horizontal or vertical) cells, using each cell at most once.
 *
 * <p><b>Algorithm Strategy (Backtracking (DFS)):</b>
 * <ol>
 *   <li>Try every cell as the starting point of the word.</li>
 *   <li>From a cell, if its character does not match {@code word.charAt(index)} (or it is outside the grid), stop. If {@code index} is the last character and it matches, the word is found.</li>
 *   <li>Mark the current cell as used (temporarily replace it with {@code '#'}), then try the four neighbours with {@code index + 1}.</li>
 *   <li>Restore the cell before returning, so other paths can use it (backtracking).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For board {@code ABCE / SFCS / ADEE} and word {@code "ABCCED"}: the path A(0,0), B(0,1), C(0,2), C(1,2), E(2,2), D(2,1) matches every letter.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n * 3^L)} — Each of the {@code m * n} start cells explores up to 3 directions per letter ({@code L} = word length).</li>
 *   <li><b>Space Complexity:</b> {@code O(L)} — Recursion depth equals the word length.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class WordSearch {

    public static void main(String[] args) {
        char[][] board = {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        String[] words = {"ABCCED", "SEE", "ABCB"};
        for (String word : words) {
            System.out.println("Input  : board = " + Arrays.deepToString(board) + ", word = \"" + word + "\"");
            System.out.println("Output : " + exist(board, word));
            System.out.println();
        }
    }

    public static boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || word == null || word.isEmpty()) {
            return false;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfs(char[][] board, String word, int i, int j, int index) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != word.charAt(index)) {
            return false;
        }
        if (index == word.length() - 1) {
            return true;
        }

        char original = board[i][j];
        board[i][j] = '#';          // mark as used
        boolean found = dfs(board, word, i + 1, j, index + 1)
                || dfs(board, word, i - 1, j, index + 1)
                || dfs(board, word, i, j + 1, index + 1)
                || dfs(board, word, i, j - 1, index + 1);
        board[i][j] = original;     // backtrack
        return found;
    }
}
