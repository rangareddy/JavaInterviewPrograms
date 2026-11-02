package com.ranga.blind75.matrix;

import java.util.Arrays;

/**
 * Rotate an {@code n x n} matrix by 90 degrees clockwise, in place (do not allocate another matrix).
 *
 * <p><b>Algorithm Strategy (Transpose, Then Reverse Each Row):</b>
 * <ol>
 *   <li>A clockwise rotation moves {@code matrix[i][j]} to {@code matrix[j][n - 1 - i]}, which equals a transpose followed by a horizontal flip.</li>
 *   <li>Transpose: swap {@code matrix[i][j]} with {@code matrix[j][i]} for all {@code j > i}.</li>
 *   <li>Reverse every row of the transposed matrix.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,2,3],[4,5,6],[7,8,9]]}: the transpose is {@code [[1,4,7],[2,5,8],[3,6,9]]}.</li>
 *   <li>Reversing each row gives {@code [[7,4,1],[8,5,2],[9,6,3]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Every cell is touched a constant number of times.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Swaps are done in place.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RotateImage {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
                {{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}}
        };
        for (int[][] matrix : inputs) {
            System.out.println("Input  : " + Arrays.deepToString(matrix));
            rotate(matrix);
            System.out.println("Output : " + Arrays.deepToString(matrix));
            System.out.println();
        }
    }

    public static void rotate(int[][] matrix) {
        if (matrix == null || matrix.length <= 1) {
            return;
        }

        int n = matrix.length;
        // 1. Transpose
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        // 2. Reverse every row
        for (int[] row : matrix) {
            for (int left = 0, right = n - 1; left < right; left++, right--) {
                int temp = row[left];
                row[left] = row[right];
                row[right] = temp;
            }
        }
    }
}
