package com.ranga.matrix;

import java.util.Arrays;

/**
 * Given two sparse matrices {@code mat1} of size {@code m x k} and {@code mat2} of size {@code k x n}, return the product {@code mat1 x mat2}. You may assume the multiplication is always possible.
 *
 * <p><b>Algorithm Strategy (Skip Zeros in the First Matrix):</b>
 * <ol>
 *   <li>Create the result matrix {@code m x n} of zeros.</li>
 *   <li>Loop over every cell {@code mat1[i][p]}; if it is zero, skip the whole inner loop, because it contributes nothing.</li>
 *   <li>Otherwise loop over {@code j} and add {@code mat1[i][p] * mat2[p][j]} to {@code result[i][j]}, skipping zero entries of {@code mat2} as well.</li>
 *   <li>Return the result.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,0,0],[-1,0,3]]} times {@code [[7,0,0],[0,0,0],[0,0,1]]} only three of the six entries of {@code mat1} are non-zero.</li>
 *   <li>Result: {@code [[7, 0, 0], [-7, 0, 3]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * k * n) worst case, O(non-zeros) typical} — Zero entries cost only the check.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — The result matrix.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SparseMatrixMultiplication {

    public static void main(String[] args) {
        int[][][] a = {{{1, 0, 0}, {-1, 0, 3}}, {{0}}, {{1, 2}, {3, 4}}};
        int[][][] b = {{{7, 0, 0}, {0, 0, 0}, {0, 0, 1}}, {{0}}, {{0, 1}, {1, 0}}};
        for (int i = 0; i < a.length; i++) {
            System.out.println("Input  : mat1 = " + Arrays.deepToString(a[i]) + ", mat2 = " + Arrays.deepToString(b[i]));
            System.out.println("Output : " + Arrays.deepToString(multiply(a[i], b[i])));
            System.out.println();
        }
    }

    public static int[][] multiply(int[][] mat1, int[][] mat2) {
        if (mat1 == null || mat2 == null || mat1.length == 0 || mat2.length == 0) {
            return new int[0][0];
        }
        int m = mat1.length;
        int k = mat2.length;
        int n = mat2[0].length;
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int p = 0; p < k; p++) {
                if (mat1[i][p] == 0) {
                    continue;
                }
                for (int j = 0; j < n; j++) {
                    if (mat2[p][j] != 0) {
                        result[i][j] += mat1[i][p] * mat2[p][j];
                    }
                }
            }
        }
        return result;
    }
}
