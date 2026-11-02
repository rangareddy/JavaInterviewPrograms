package com.ranga.dp;

import java.util.Arrays;

/**
 * A robot starts at the top-left corner of an {@code m x n} grid and wants to reach the bottom-right corner, moving only down or right. Return the number of unique paths.
 *
 * <p><b>Algorithm Strategy (Dynamic Programming with One Row):</b>
 * <ol>
 *   <li>Every cell can only be entered from the cell above or from the cell on its left, so {@code paths[i][j] = paths[i - 1][j] + paths[i][j - 1]}.</li>
 *   <li>The first row and the first column have exactly 1 path to each cell (straight line).</li>
 *   <li>Only the previous row is needed, so keep a single array {@code row} of length {@code n}, initialised with 1s.</li>
 *   <li>For every following row, update {@code row[j] += row[j - 1]} from left to right; {@code row[n - 1]} is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code m = 3} and {@code n = 2}: the row starts as {@code [1, 1]}; after row 2 it is {@code [1, 2]}; after row 3 it is {@code [1, 3]}.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Every cell is updated once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — A single row.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class UniquePaths {

    public static void main(String[] args) {
        int[][] inputs = {{3, 2}, {3, 7}, {3, 3}, {1, 1}};
        for (int[] input : inputs) {
            System.out.println("Input  : m = " + input[0] + ", n = " + input[1]);
            System.out.println("Output : " + uniquePaths(input[0], input[1]));
            System.out.println();
        }
    }

    public static int uniquePaths(int m, int n) {
        int[] row = new int[n];
        Arrays.fill(row, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                row[j] += row[j - 1];
            }
        }
        return row[n - 1];
    }
}
