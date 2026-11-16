package com.ranga.dp;

/**
 * A robot starts at the top-left corner of an {@code m x n} grid and wants to reach the bottom-right corner, moving only down or right. Return the number of unique paths.
 *
 * <p><b>Algorithm Strategy (Combinatorics):</b>
 * <ol>
 *   <li>Guard first: a grid with {@code m <= 0} or {@code n <= 0} has no cells, so there are 0 paths.</li>
 *   <li>Every path consists of exactly {@code m - 1} moves down and {@code n - 1} moves right, in any order. Choosing which of the {@code m + n - 2} moves go down gives {@code C(m + n - 2, m - 1)} paths.</li>
 *   <li>Compute the binomial coefficient with {@code k = min(m, n) - 1} multiplications: {@code result = result * (m + n - 2 - k + i) / i} for {@code i = 1 .. k}. The value is an integer after every step, so the integer division is exact.</li>
 *   <li>A {@code long} holds the intermediate products. The result must fit an {@code int}; if it does not, the method returns {@code -1} instead of a wrong number.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code m = 3} and {@code n = 2}: {@code k = 1}, so {@code result = 1 * (3 + 2 - 2 - 1 + 1) / 1 = 3}, which is {@code C(3, 1)}.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(min(m, n))} — One multiplication and division per step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class UniquePaths {

    public static void main(String[] args) {
        int[][] inputs = {{3, 2}, {3, 7}, {3, 3}, {1, 1}, {0, 5}, {23, 12}, {100, 100}};
        for (int[] input : inputs) {
            System.out.println("Input  : m = " + input[0] + ", n = " + input[1]);
            System.out.println("Output : " + uniquePaths(input[0], input[1]));
            System.out.println();
        }
    }

    /**
     * Counts the paths from the top-left to the bottom-right corner of an {@code m x n} grid.
     *
     * @param m the number of rows
     * @param n the number of columns
     * @return the number of paths; {@code 0} if the grid is empty; {@code -1} if the count does not fit an {@code int}
     */
    public static int uniquePaths(int m, int n) {
        // Guard Clause: an empty grid has no path
        if (m <= 0 || n <= 0) {
            return 0;
        }

        long total = (long) m + n - 2;
        int k = Math.min(m, n) - 1;
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (total - k + i) / i;       // stays an integer at every step
            if (result > Integer.MAX_VALUE) {
                return -1;                                // the count only grows, so it can never fit an int
            }
        }
        return (int) result;
    }
}
