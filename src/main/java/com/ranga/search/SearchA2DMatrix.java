package com.ranga.search;

import java.util.Arrays;

/**
 * Given an {@code m x n} integer matrix where every row is sorted in ascending order and the first integer of each row is greater than the last integer of the previous row, return {@code true} if {@code target} is in the matrix. The algorithm must run in {@code O(log(m * n))} time.
 *
 * <p><b>Algorithm Strategy (Binary Search over the Flattened Matrix):</b>
 * <ol>
 *   <li>Because each row starts after the previous one ends, reading the matrix row by row gives one sorted sequence of {@code m * n} numbers.</li>
 *   <li>Binary-search the virtual positions {@code 0 .. m * n - 1}; the position {@code mid} lives in row {@code mid / n} and column {@code mid % n}.</li>
 *   <li>Compare that cell with the target: equal means found, a smaller cell means search the upper half, a larger cell the lower half.</li>
 *   <li>No flattened copy is built; only the index arithmetic maps a position to a cell.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the matrix {@code [[1,3,5,7],[10,11,16,20],[23,30,34,60]]} and target 3 (12 cells): {@code mid = 5} is 11, too large; {@code mid = 2} is 5, too large; {@code mid = 0} is 1, too small; {@code mid = 1} is 3.</li>
 *   <li>Result: {@code true}. Target 13 is never found: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log(m * n))} — The search range halves each step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Index variables only.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SearchA2DMatrix {

    public static void main(String[] args) {
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        int[] targets = {3, 13, 60, 1, 0, 61};
        for (int target : targets) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrix) + ", target = " + target);
            System.out.println("Output : " + searchMatrix(matrix, target));
            System.out.println();
        }
    }

    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int low = 0;
        int high = rows * cols - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int value = matrix[mid / cols][mid % cols];
            if (value == target) {
                return true;
            } else if (value < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }
}
