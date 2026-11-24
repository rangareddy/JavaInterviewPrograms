package com.ranga.dp;

import java.util.Arrays;

/**
 * Given an {@code m x n} binary matrix filled with {@code '0'} and {@code '1'}, find the largest square containing only {@code '1'} and return its area.
 *
 * <p><b>Algorithm Strategy (DP on the Side Length):</b>
 * <ol>
 *   <li>Let {@code dp[i][j]} be the side of the largest all-ones square whose bottom-right corner is {@code (i, j)}.</li>
 *   <li>If the cell is {@code '0'}, {@code dp[i][j] = 0}.</li>
 *   <li>Otherwise {@code dp[i][j] = 1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])}: the square can only be as large as the smallest of its three neighbours allows.</li>
 *   <li>Track the maximum side; the answer is {@code side * side}. A single rolling row is enough.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,0,1,0,0],[1,0,1,1,1],[1,1,1,1,1],[1,0,0,1,0]]} the largest block of ones is 2x2, with its bottom-right corner at row 2, column 3 (or column 4).</li>
 *   <li>Result: area {@code 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Each cell is computed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — One row of side lengths, plus the previous diagonal value.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximalSquare {

    public static void main(String[] args) {
        char[][][] inputs = {
            {{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}},
            {{'0', '1'}, {'1', '0'}},
            {{'0'}},
            {{'1', '1'}, {'1', '1'}}
        };
        for (char[][] matrix : inputs) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrix));
            System.out.println("Output : " + maximalSquare(matrix));
            System.out.println();
        }
    }

    public static int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int n = matrix[0].length;
        int[] dp = new int[n + 1];
        int side = 0;
        for (char[] row : matrix) {
            int diagonal = 0;
            for (int j = 1; j <= n; j++) {
                int above = dp[j];
                if (row[j - 1] == '1') {
                    dp[j] = 1 + Math.min(Math.min(dp[j], dp[j - 1]), diagonal);
                    side = Math.max(side, dp[j]);
                } else {
                    dp[j] = 0;
                }
                diagonal = above;
            }
        }
        return side * side;
    }
}
