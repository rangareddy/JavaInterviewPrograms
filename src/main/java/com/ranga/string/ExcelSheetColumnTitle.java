package com.ranga.string;

/**
 * Given a positive integer {@code columnNumber}, return its column title as it appears in an Excel sheet: 1 is {@code A}, 26 is {@code Z}, 27 is {@code AA}, 28 is {@code AB}, and so on.
 *
 * <p><b>Algorithm Strategy (Bijective Base-26):</b>
 * <ol>
 *   <li>There is no zero digit: {@code A} to {@code Z} stand for 1 to 26. So subtract 1 before taking each digit.</li>
 *   <li>Compute {@code columnNumber--}, then the last letter is {@code 'A' + columnNumber % 26}.</li>
 *   <li>Divide {@code columnNumber} by 26 and repeat while it is above 0.</li>
 *   <li>The letters come out from the last to the first, so reverse them.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For 28: {@code 28 - 1 = 27}, {@code 27 % 26 = 1} gives {@code B}; then {@code 27 / 26 = 1}, {@code 1 - 1 = 0}, {@code 0 % 26 = 0} gives {@code A}.</li>
 *   <li>Reversed: {@code AB}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log26 n)} — One step per letter.</li>
 *   <li><b>Space Complexity:</b> {@code O(log26 n)} — The result.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ExcelSheetColumnTitle {

    public static void main(String[] args) {
        int[] inputs = {28, 1, 26, 27, 701, 703, 2147483647};
        for (int n : inputs) {
            System.out.println("Input  : columnNumber = " + n);
            System.out.println("Output : " + convertToTitle(n));
            System.out.println();
        }
    }

    public static String convertToTitle(int columnNumber) {
        StringBuilder title = new StringBuilder();
        while (columnNumber > 0) {
            columnNumber--;
            title.append((char) ('A' + columnNumber % 26));
            columnNumber /= 26;
        }
        return title.reverse().toString();
    }
}
