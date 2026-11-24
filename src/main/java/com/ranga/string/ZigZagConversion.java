package com.ranga.string;

/**
 * Write the string {@code s} in a zigzag pattern on {@code numRows} rows, going down the rows and then diagonally up, and then read the rows one after another. Return the resulting string.
 *
 * <p><b>Algorithm Strategy (Row Buckets with a Direction Flag):</b>
 * <ol>
 *   <li>Create one {@code StringBuilder} per row.</li>
 *   <li>Walk the characters of {@code s}, appending each to the current row.</li>
 *   <li>Flip the direction when the current row reaches the first or the last row.</li>
 *   <li>Concatenate the rows. If {@code numRows} is 1 (or at least the string length), return {@code s} unchanged.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code PAYPALISHIRING} with 3 rows the rows are {@code PAHN}, {@code APLSIIG} and {@code YIR}.</li>
 *   <li>Result: {@code PAHNAPLSIIGYIR}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each character is appended once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The rows together hold all characters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ZigZagConversion {

    public static void main(String[] args) {
        String[] inputs = {"PAYPALISHIRING", "PAYPALISHIRING", "A", "AB"};
        int[] rows = {3, 4, 1, 1};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : s = \"" + inputs[i] + "\", numRows = " + rows[i]);
            System.out.println("Output : " + convert(inputs[i], rows[i]));
            System.out.println();
        }
    }

    public static String convert(String s, int numRows) {
        if (s == null || numRows <= 1 || numRows >= s.length()) {
            return s;
        }
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }
        int row = 0;
        int step = 1;
        for (int i = 0; i < s.length(); i++) {
            rows[row].append(s.charAt(i));
            if (row == 0) {
                step = 1;
            } else if (row == numRows - 1) {
                step = -1;
            }
            row += step;
        }
        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rows) {
            result.append(sb);
        }
        return result.toString();
    }
}
