package com.ranga.dp;

import java.util.Arrays;
import java.util.List;

/**
 * Given a {@code triangle} array, return the minimum path sum from top to bottom. At each step you may move to an adjacent number on the row below: from index {@code i} to index {@code i} or {@code i + 1}.
 *
 * <p><b>Algorithm Strategy (Bottom-Up DP on One Row):</b>
 * <ol>
 *   <li>Start from the last row, which is the base case: its minimum sums are the values themselves.</li>
 *   <li>Move up one row at a time: {@code best[j] = triangle[row][j] + min(best[j], best[j + 1])}.</li>
 *   <li>Reuse a single array of the row's width, overwriting it as you go up.</li>
 *   <li>After the top row, {@code best[0]} is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[2],[3,4],[6,5,7],[4,1,8,3]]}: the minimum path is 2 + 3 + 5 + 1.</li>
 *   <li>Result: {@code 11}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Every number is visited once (the triangle has about n^2 / 2 numbers).</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — One row.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TriangleMinimumPathSum {

    public static void main(String[] args) {
        int[][][] inputs = {
            {{2}, {3, 4}, {6, 5, 7}, {4, 1, 8, 3}},
            {{-10}},
            {{1}, {2, 3}},
            {}
        };
        for (int[][] rows : inputs) {
            List<List<Integer>> triangle = new java.util.ArrayList<>();
            for (int[] row : rows) {
                List<Integer> list = new java.util.ArrayList<>();
                for (int x : row) {
                    list.add(x);
                }
                triangle.add(list);
            }
            System.out.println("Input  : triangle = " + Arrays.deepToString(rows));
            System.out.println("Output : " + minimumTotal(triangle));
            System.out.println();
        }
    }

    public static int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.isEmpty()) {
            return 0;
        }
        int n = triangle.size();
        int[] best = new int[n];
        List<Integer> last = triangle.get(n - 1);
        for (int j = 0; j < n; j++) {
            best[j] = last.get(j);
        }
        for (int row = n - 2; row >= 0; row--) {
            for (int j = 0; j <= row; j++) {
                best[j] = triangle.get(row).get(j) + Math.min(best[j], best[j + 1]);
            }
        }
        return best[0];
    }
}
