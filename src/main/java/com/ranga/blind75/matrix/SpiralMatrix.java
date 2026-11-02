package com.ranga.blind75.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an {@code m x n} matrix, return all of its elements in spiral order (clockwise, starting at the top-left cell).
 *
 * <p><b>Algorithm Strategy (Shrinking Boundaries):</b>
 * <ol>
 *   <li>Keep four boundaries: {@code top}, {@code bottom}, {@code left} and {@code right}.</li>
 *   <li>Walk the top row from left to right, then increment {@code top}.</li>
 *   <li>Walk the right column from top to bottom, then decrement {@code right}.</li>
 *   <li>If rows remain, walk the bottom row from right to left and decrement {@code bottom}; if columns remain, walk the left column from bottom to top and increment {@code left}.</li>
 *   <li>Repeat while {@code top <= bottom} and {@code left <= right}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,2,3],[4,5,6],[7,8,9]]}: top row 1,2,3; right column 6,9; bottom row 8,7; left column 4; then the inner cell 5.</li>
 *   <li>Result: {@code [1, 2, 3, 6, 9, 8, 7, 4, 5]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Every cell is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Excluding the output list.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SpiralMatrix {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
                {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}}
        };
        for (int[][] matrix : inputs) {
            System.out.println("Input  : " + Arrays.deepToString(matrix));
            System.out.println("Output : " + spiralOrder(matrix));
            System.out.println();
        }
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) {
            return result;
        }

        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) {
                result.add(matrix[top][j]);
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;

            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    result.add(matrix[bottom][j]);
                }
                bottom--;
            }

            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }
        }
        return result;
    }
}
