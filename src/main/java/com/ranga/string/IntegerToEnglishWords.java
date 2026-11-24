package com.ranga.string;

/**
 * Convert a non-negative integer {@code num} to its English words representation.
 *
 * <p><b>Algorithm Strategy (Three-Digit Chunks with Scale Words):</b>
 * <ol>
 *   <li>Handle 0 separately ({@code Zero}).</li>
 *   <li>Split the number into groups of three digits from the right and pair each non-zero group with its scale: none, {@code Thousand}, {@code Million}, {@code Billion}.</li>
 *   <li>Convert a three-digit group: hundreds with {@code Hundred}, then a number below 20 from a table, or tens plus ones.</li>
 *   <li>Join the pieces with single spaces, skipping empty groups.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code 123} is one group: {@code One Hundred Twenty Three}.</li>
 *   <li>{@code 1234567} is {@code One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — At most four groups of three digits for a 32-bit integer.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — The word tables and the result.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class IntegerToEnglishWords {

    public static void main(String[] args) {
        int[] inputs = {123, 12345, 1234567, 1000010, 0, 100, Integer.MAX_VALUE};
        for (int n : inputs) {
            System.out.println("Input  : num = " + n);
            System.out.println("Output : " + numberToWords(n));
            System.out.println();
        }
    }

    private static final String[] BELOW_TWENTY = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static final String[] TENS = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private static final String[] SCALES = {"", "Thousand", "Million", "Billion"};

    public static String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        StringBuilder result = new StringBuilder();
        int scale = 0;
        while (num > 0) {
            int chunk = num % 1000;
            if (chunk > 0) {
                String words = chunkToWords(chunk);
                if (!SCALES[scale].isEmpty()) {
                    words += " " + SCALES[scale];
                }
                result.insert(0, result.length() > 0 ? words + " " : words);
            }
            num /= 1000;
            scale++;
        }
        return result.toString();
    }

    private static String chunkToWords(int n) {
        StringBuilder sb = new StringBuilder();
        if (n >= 100) {
            sb.append(BELOW_TWENTY[n / 100]).append(" Hundred");
            n %= 100;
        }
        if (n >= 20) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(TENS[n / 10]);
            n %= 10;
        }
        if (n > 0) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(BELOW_TWENTY[n]);
        }
        return sb.toString();
    }
}
