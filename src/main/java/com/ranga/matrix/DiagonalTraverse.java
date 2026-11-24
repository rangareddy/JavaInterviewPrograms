package com.ranga.matrix;

import java.util.Arrays;

/**
 * Given an {@code m x n} matrix {@code mat}, return all of its elements in diagonal order, starting at the top-left and moving up-right on the first diagonal, then down-left on the next, alternating.
 *
 * <p><b>Algorithm Strategy (Anti-Diagonal Indexing):</b>
 * <ol>
 *   <li>Elements on one anti-diagonal share the same sum {@code i + j}, which runs from 0 to {@code m + n - 2}.</li>
 *   <li>For each diagonal {@code d}, collect the cells {@code (i, d - i)} that are inside the matrix.</li>
 *   <li>Even-numbered diagonals are read bottom-left to top-right (row decreasing), odd-numbered ones top-right to bottom-left.</li>
 *   <li>Concatenate the diagonals.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,2,3],[4,5,6],[7,8,9]]} the diagonals are {@code [1]}, {@code [2,4]}, {@code [7,5,3]}, {@code [6,8]}, {@code [9]}, read in alternating directions.</li>
 *   <li>Result: {@code [1, 2, 4, 7, 5, 3, 6, 8, 9]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Each element is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Apart from the output.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DiagonalTraverse {

    public static void main(String[] args) {
        int[][][] inputs = {
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{1, 2}, {3, 4}},
            {{1, 2, 3, 4}},
            {{1}, {2}, {3}}
        };
        for (int[][] mat : inputs) {
            System.out.println("Input  : mat = " + Arrays.deepToString(mat));
            System.out.println("Output : " + Arrays.toString(findDiagonalOrder(mat)));
            System.out.println();
        }
    }

    public static int[] findDiagonalOrder(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return new int[0];
        }
        int m = mat.length;
        int n = mat[0].length;
        int[] result = new int[m * n];
        int index = 0;
        for (int d = 0; d < m + n - 1; d++) {
            int rowStart = Math.max(0, d - n + 1);
            int rowEnd = Math.min(m - 1, d);
            if (d % 2 == 0) {
                for (int i = rowEnd; i >= rowStart; i--) {
                    result[index++] = mat[i][d - i];
                }
            } else {
                for (int i = rowStart; i <= rowEnd; i++) {
                    result[index++] = mat[i][d - i];
                }
            }
        }
        return result;
    }
}
