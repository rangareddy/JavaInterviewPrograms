package com.ranga.dp;

import java.util.Arrays;

/**
 * Given an {@code m x n} integer matrix, return the length of the longest strictly increasing path. From each cell you may move left, right, up or down (not diagonally and not outside the matrix).
 *
 * <p><b>Algorithm Strategy (DFS with Memoization):</b>
 * <ol>
 *   <li>Let {@code longest(i, j)} be the length of the longest increasing path that starts at cell {@code (i, j)}.</li>
 *   <li>It equals {@code 1 + max(longest(neighbour))} over the neighbours with a strictly larger value (or just 1 if there is none).</li>
 *   <li>Because the values must strictly increase, the graph of allowed moves has no cycles, so the recursion terminates, and each cell's result can be cached in a {@code memo} table.</li>
 *   <li>Compute {@code longest} for every cell and return the maximum.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[9,9,4],[6,6,8],[2,1,1]]}: the path {@code 1 -> 2 -> 6 -> 9} has length 4.</li>
 *   <li>Result: {@code 4}. In {@code [[3,4,5],[3,2,6],[2,2,1]]} the path {@code 3 -> 4 -> 5 -> 6} also has length 4.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Every cell is computed once; each computation looks at four neighbours.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — The memo table and the recursion stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestIncreasingPathInAMatrix {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{9, 9, 4}, {6, 6, 8}, {2, 1, 1}},
                {{3, 4, 5}, {3, 2, 6}, {2, 2, 1}},
                {{1}},
                {{1, 2}, {4, 3}}
        };
        for (int[][] matrix : inputs) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrix));
            System.out.println("Output : " + longestIncreasingPath(matrix));
            System.out.println();
        }
    }

    public static int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }

        int[][] memo = new int[matrix.length][matrix[0].length];
        int best = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                best = Math.max(best, longest(matrix, i, j, memo));
            }
        }
        return best;
    }

    private static int longest(int[][] matrix, int i, int j, int[][] memo) {
        if (memo[i][j] != 0) {
            return memo[i][j];
        }

        int best = 1;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : directions) {
            int x = i + d[0];
            int y = j + d[1];
            if (x >= 0 && y >= 0 && x < matrix.length && y < matrix[0].length && matrix[x][y] > matrix[i][j]) {
                best = Math.max(best, 1 + longest(matrix, x, y, memo));
            }
        }
        return memo[i][j] = best;
    }
}
