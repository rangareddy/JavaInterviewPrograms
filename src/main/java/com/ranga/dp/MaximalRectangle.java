package com.ranga.dp;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Given a {@code rows x cols} binary matrix of {@code '0'} and {@code '1'} characters, find the largest rectangle containing only {@code '1'}s and return its area.
 *
 * <p><b>Algorithm Strategy (Largest Rectangle in a Histogram per Row):</b>
 * <ol>
 *   <li>Keep an array {@code height[j]}: the number of consecutive {@code '1'}s ending at the current row in column {@code j} (reset to 0 on a {@code '0'}).</li>
 *   <li>After updating the heights for a row, the largest rectangle whose bottom edge is on that row is the largest rectangle in the histogram {@code height}.</li>
 *   <li>Compute that with a monotonic stack: pop bars taller than the current one and compute the area using the popped height and the width up to the new stack top.</li>
 *   <li>The answer is the maximum area over all rows.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the 4x5 sample matrix, the heights after row 2 are {@code 3, 1, 3, 2, 2}, and the largest histogram rectangle is {@code 2 * 3 = 6} (columns 2 to 4 with height 2).</li>
 *   <li>Result: {@code 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(rows * cols)} — One linear stack pass per row.</li>
 *   <li><b>Space Complexity:</b> {@code O(cols)} — The heights and the stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximalRectangle {

    public static void main(String[] args) {
        char[][][] inputs = {
            {{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}},
            {{'0'}},
            {{'1'}},
            {{'1', '1'}, {'1', '1'}}
        };
        for (char[][] matrix : inputs) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrix));
            System.out.println("Output : " + maximalRectangle(matrix));
            System.out.println();
        }
    }

    public static int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int cols = matrix[0].length;
        int[] heights = new int[cols + 1];
        int best = 0;
        for (char[] row : matrix) {
            for (int j = 0; j < cols; j++) {
                heights[j] = row[j] == '1' ? heights[j] + 1 : 0;
            }
            Deque<Integer> stack = new ArrayDeque<>();
            for (int j = 0; j <= cols; j++) {
                while (!stack.isEmpty() && heights[stack.peek()] > heights[j]) {
                    int height = heights[stack.pop()];
                    int left = stack.isEmpty() ? -1 : stack.peek();
                    best = Math.max(best, height * (j - left - 1));
                }
                stack.push(j);
            }
        }
        return best;
    }
}
