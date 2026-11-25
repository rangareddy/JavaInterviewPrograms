package com.ranga.matrix;

import java.util.Arrays;

/**
 * Given an {@code m x n} matrix of ones and zeros, return how many square submatrices have all ones.
 *
 * <p><b>Algorithm Strategy (Square Size DP):</b>
 * <ol>
 *   <li>Let {@code dp[i][j]} be the side of the largest all-ones square whose bottom-right corner is {@code (i, j)}.</li>
 *   <li>If the cell is 0, {@code dp[i][j] = 0}.</li>
 *   <li>Otherwise {@code dp[i][j] = 1 + min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])} (cells outside the matrix count as 0).</li>
 *   <li>A largest square of side {@code s} at that corner means exactly {@code s} squares end there (sides 1 to {@code s}), so the answer is the sum of all {@code dp} values.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[0, 1, 1, 1], [1, 1, 1, 1], [0, 1, 1, 1]]}: the dp table is {@code [[0, 1, 1, 1], [1, 1, 2, 2], [0, 1, 2, 3]]}.</li>
 *   <li>Its sum is {@code 3 + 6 + 6 = 15}.</li>
 *   <li>Result: {@code 15}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Every cell is computed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — One rolling row would do; this version updates a copy of the row above in place.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CountSquareSubmatricesWithAllOnes {

    public static void main(String[] args) {
        int[][][] inputs = {{{0, 1, 1, 1}, {1, 1, 1, 1}, {0, 1, 1, 1}}, {{1, 0, 1}, {1, 1, 0}, {1, 1, 0}}, {{1}}, {{0, 0}, {0, 0}}};
        for (int[][] matrix : inputs) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrix));
            System.out.println("Output : " + countSquares(matrix));
            System.out.println();
        }
    }

    public static int countSquares(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows][cols];
        int total = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 1) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                    }
                    total += dp[i][j];
                }
            }
        }
        return total;
    }
}
