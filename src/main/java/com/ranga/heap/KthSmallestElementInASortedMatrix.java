package com.ranga.heap;

import java.util.Arrays;

/**
 * Given an {@code n x n} matrix where each row and each column is sorted in ascending order, return the {@code k}-th smallest element in the matrix (the {@code k}-th smallest in sorted order, not the {@code k}-th distinct one).
 *
 * <p><b>Algorithm Strategy (Binary Search on the Value Range):</b>
 * <ol>
 *   <li>The answer lies between {@code matrix[0][0]} and {@code matrix[n-1][n-1]}.</li>
 *   <li>For a candidate {@code mid}, count how many elements are {@code <= mid} by walking from the bottom-left corner: move up when the cell is too large, right otherwise.</li>
 *   <li>If the count is at least {@code k}, the answer is {@code <= mid}: {@code hi = mid}; otherwise {@code lo = mid + 1}.</li>
 *   <li>When {@code lo == hi}, that value is the {@code k}-th smallest.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,5,9],[10,11,13],[12,13,15]]} and {@code k = 8}: the sorted values are 1, 5, 9, 10, 11, 12, 13, 13, 15.</li>
 *   <li>The 8th smallest is {@code 13}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log(max - min))} — A binary search over the value range, with an O(n) count per step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class KthSmallestElementInASortedMatrix {

    public static void main(String[] args) {
        int[][][] matrices = {{{1, 5, 9}, {10, 11, 13}, {12, 13, 15}}, {{-5}}, {{1, 2}, {1, 3}}};
        int[] ks = {8, 1, 2};
        for (int i = 0; i < matrices.length; i++) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrices[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + kthSmallest(matrices[i], ks[i]));
            System.out.println();
        }
    }

    public static int kthSmallest(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0) {
            return -1;
        }
        int n = matrix.length;
        int lo = matrix[0][0];
        int hi = matrix[n - 1][n - 1];
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (countAtMost(matrix, mid) >= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private static int countAtMost(int[][] matrix, int value) {
        int n = matrix.length;
        int row = n - 1;
        int col = 0;
        int count = 0;
        while (row >= 0 && col < n) {
            if (matrix[row][col] <= value) {
                count += row + 1;
                col++;
            } else {
                row--;
            }
        }
        return count;
    }
}
