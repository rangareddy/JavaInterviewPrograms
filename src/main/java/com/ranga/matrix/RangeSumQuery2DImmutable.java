package com.ranga.matrix;

import java.util.Arrays;

/**
 * Given a 2D matrix, answer many queries {@code sumRegion(row1, col1, row2, col2)} that return the sum of the elements inside the rectangle with upper-left corner {@code (row1, col1)} and lower-right corner {@code (row2, col2)}, each query in {@code O(1)}.
 *
 * <p><b>Algorithm Strategy (2D Prefix Sums):</b>
 * <ol>
 *   <li>Build {@code prefix[i][j]}, the sum of the rectangle from {@code (0, 0)} to {@code (i - 1, j - 1)}, using one extra row and column of zeros.</li>
 *   <li>Fill it with {@code prefix[i][j] = matrix[i-1][j-1] + prefix[i-1][j] + prefix[i][j-1] - prefix[i-1][j-1]}.</li>
 *   <li>A query is then {@code prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]} (inclusion-exclusion).</li>
 *   <li>Each query takes constant time after an {@code O(m * n)} build.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the 5x5 sample matrix, {@code sumRegion(2, 1, 4, 3)} covers the rows 2 to 4 and columns 1 to 3.</li>
 *   <li>Result: {@code 8}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n) build, O(1) query} — Inclusion-exclusion on four prefix values.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — The prefix table.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RangeSumQuery2DImmutable {

    public static void main(String[] args) {
        int[][] matrix = {{3, 0, 1, 4, 2}, {5, 6, 3, 2, 1}, {1, 2, 0, 1, 5}, {4, 1, 0, 1, 7}, {1, 0, 3, 0, 5}};
        RangeSumQuery2DImmutable sums = new RangeSumQuery2DImmutable(matrix);
        int[][] queries = {{2, 1, 4, 3}, {1, 1, 2, 2}, {1, 2, 2, 4}, {0, 0, 4, 4}};
        for (int[] q : queries) {
            System.out.println("Input  : sumRegion(" + q[0] + ", " + q[1] + ", " + q[2] + ", " + q[3] + ")");
            System.out.println("Output : " + sums.sumRegion(q[0], q[1], q[2], q[3]));
            System.out.println();
        }
    }

    private final int[][] prefix;

    public RangeSumQuery2DImmutable(int[][] matrix) {
        int m = matrix == null ? 0 : matrix.length;
        int n = m == 0 ? 0 : matrix[0].length;
        prefix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefix[i][j] = matrix[i - 1][j - 1] + prefix[i - 1][j] + prefix[i][j - 1] - prefix[i - 1][j - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return prefix[row2 + 1][col2 + 1] - prefix[row1][col2 + 1] - prefix[row2 + 1][col1] + prefix[row1][col1];
    }
}
