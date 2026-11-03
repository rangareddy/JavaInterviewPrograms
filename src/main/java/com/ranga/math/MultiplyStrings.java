package com.ranga.math;

/**
 * Given two non-negative integers {@code num1} and {@code num2} represented as strings, return their product, also as a string. You must not use a big-integer library or convert the inputs to integers directly.
 *
 * <p><b>Algorithm Strategy (Grade-School Multiplication with a Digit Array):</b>
 * <ol>
 *   <li>The product of an {@code m}-digit and an {@code n}-digit number has at most {@code m + n} digits: allocate an {@code int[] product} of that length.</li>
 *   <li>Digit {@code i} of {@code num1} times digit {@code j} of {@code num2} contributes to positions {@code i + j} (carry) and {@code i + j + 1} (units).</li>
 *   <li>Process the digits from right to left: add the digit product plus the value already at {@code i + j + 1}, store {@code sum % 10} there and add {@code sum / 10} to position {@code i + j}.</li>
 *   <li>Skip the leading zeros of the array and join the rest into a string (return {@code "0"} if everything is zero).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "123"} and {@code "456"}: the digit array ends as {@code [0, 5, 6, 0, 8, 8]}, which after dropping the leading zero is {@code 56088}.</li>
 *   <li>Result: {@code "56088"}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Every pair of digits is multiplied once.</li>
 *   <li><b>Space Complexity:</b> {@code O(m + n)} — The digit array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MultiplyStrings {

    public static void main(String[] args) {
        String[][] inputs = {{"2", "3"}, {"123", "456"}, {"0", "9999"}, {"999", "999"}, {"12345678", "87654321"}};
        for (String[] input : inputs) {
            System.out.println("Input  : num1 = \"" + input[0] + "\", num2 = \"" + input[1] + "\"");
            System.out.println("Output : " + multiply(input[0], input[1]));
            System.out.println();
        }
    }

    public static String multiply(String num1, String num2) {
        int m = num1.length();
        int n = num2.length();
        int[] product = new int[m + n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int sum = (num1.charAt(i) - '0') * (num2.charAt(j) - '0') + product[i + j + 1];
                product[i + j + 1] = sum % 10;
                product[i + j] += sum / 10;
            }
        }

        StringBuilder result = new StringBuilder();
        for (int digit : product) {
            if (result.length() > 0 || digit != 0) {
                result.append(digit);
            }
        }
        return result.length() == 0 ? "0" : result.toString();
    }
}
