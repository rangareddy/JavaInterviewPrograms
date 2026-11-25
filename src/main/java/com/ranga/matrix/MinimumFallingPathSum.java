package com.ranga.matrix;

import java.util.Arrays;

/**
 * Given an {@code n x n} array of integers {@code matrix}, return the minimum sum of any falling path through {@code matrix}. A falling path starts at any element in the first row and chooses the element in the next row that is either directly below or diagonally left or right.
 *
 * <p><b>Algorithm Strategy (Best Path Ending at Each Cell):</b>
 * <ol>
 *   <li>Let {@code best[c]} be the minimum falling-path sum ending at column {@code c} of the current row; for the first row it is the row itself.</li>
 *   <li>For each next row, a cell can be reached from the columns {@code c - 1}, {@code c} and {@code c + 1} above it.</li>
 *   <li>So the new value at {@code c} is {@code matrix[r][c] + min(best[c - 1], best[c], best[c + 1])}, ignoring out-of-range columns.</li>
 *   <li>After the last row, the answer is the minimum of {@code best}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[2, 1, 3], [6, 5, 4], [7, 8, 9]]}: row 1 becomes {@code [7, 6, 5]}, row 2 becomes {@code [13, 13, 14]}.</li>
 *   <li>Result: {@code 13}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Every cell is processed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — One rolling row.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumFallingPathSum {

    public static void main(String[] args) {
        int[][][] inputs = {{{2, 1, 3}, {6, 5, 4}, {7, 8, 9}}, {{-19, 57}, {-40, -5}}, {{-48}}, {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}};
        for (int[][] matrix : inputs) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrix));
            System.out.println("Output : " + minFallingPathSum(matrix));
            System.out.println();
        }
    }

    public static int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int[] best = matrix[0].clone();
        for (int r = 1; r < n; r++) {
            int[] next = new int[n];
            for (int c = 0; c < n; c++) {
                int min = best[c];
                if (c > 0) {
                    min = Math.min(min, best[c - 1]);
                }
                if (c < n - 1) {
                    min = Math.min(min, best[c + 1]);
                }
                next[c] = matrix[r][c] + min;
            }
            best = next;
        }
        int answer = Integer.MAX_VALUE;
        for (int value : best) {
            answer = Math.min(answer, value);
        }
        return answer;
    }
}
