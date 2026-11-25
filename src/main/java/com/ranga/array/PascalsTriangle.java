package com.ranga.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an integer {@code numRows}, return the first {@code numRows} rows of Pascal's triangle. Each number is the sum of the two numbers directly above it, and each row starts and ends with 1.
 *
 * <p><b>Algorithm Strategy (Row from the Previous Row):</b>
 * <ol>
 *   <li>Row 0 is {@code [1]}.</li>
 *   <li>A new row has one more element than the previous one: start with 1.</li>
 *   <li>Each inner element is {@code prev[j - 1] + prev[j]}.</li>
 *   <li>End the row with 1 and add it to the result.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>From row {@code [1, 2, 1]}: the next row is {@code 1}, {@code 1 + 2 = 3}, {@code 2 + 1 = 3}, {@code 1}.</li>
 *   <li>Result: {@code [1, 3, 3, 1]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(numRows^2)} — The triangle has about numRows^2 / 2 numbers.</li>
 *   <li><b>Space Complexity:</b> {@code O(numRows^2)} — The output.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PascalsTriangle {

    public static void main(String[] args) {
        int[] inputs = {5, 1, 0, 6};
        for (int n : inputs) {
            System.out.println("Input  : numRows = " + n);
            System.out.println("Output : " + generate(n));
            System.out.println();
        }
    }

    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            row.add(1);
            if (i > 0) {
                List<Integer> prev = triangle.get(i - 1);
                for (int j = 1; j < i; j++) {
                    row.add(prev.get(j - 1) + prev.get(j));
                }
                row.add(1);
            }
            triangle.add(row);
        }
        return triangle;
    }
}
