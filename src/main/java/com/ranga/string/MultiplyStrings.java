package com.ranga.string;

/**
 * Given two non-negative integers {@code num1} and {@code num2} represented as strings, return their product as a string. You must not use a built-in big integer library or convert the inputs to integers directly.
 *
 * <p><b>Algorithm Strategy (Grade-School Multiplication into a Digit Array):</b>
 * <ol>
 *   <li>The product of an {@code m}-digit and an {@code n}-digit number has at most {@code m + n} digits, so allocate an {@code int[m + n]}.</li>
 *   <li>The digit at position {@code i} of {@code num1} times the digit at position {@code j} of {@code num2} contributes to positions {@code i + j} (carry) and {@code i + j + 1} (units) of the result array.</li>
 *   <li>Walk both strings from the right. Add the product to {@code result[i + j + 1]}, move the carry ({@code sum / 10}) into {@code result[i + j]} and keep {@code sum % 10} in place.</li>
 *   <li>Skip leading zeros when building the output string and return {@code "0"} if either input is {@code "0"}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "123" * "456"}: partial products accumulate into {@code [0, 5, 6, 0, 8, 8]}.</li>
 *   <li>Dropping the leading zero gives {@code 56088}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Every digit of one number is multiplied by every digit of the other.</li>
 *   <li><b>Space Complexity:</b> {@code O(m + n)} — The result digit array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MultiplyStrings {

    public static void main(String[] args) {
        String[][] inputs = {{"2", "3"}, {"123", "456"}, {"0", "9133"}, {"999", "999"}};
        for (String[] in : inputs) {
            System.out.println("Input  : num1 = \"" + in[0] + "\", num2 = \"" + in[1] + "\"");
            System.out.println("Output : \"" + multiply(in[0], in[1]) + "\"");
            System.out.println();
        }
    }

    public static String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int m = num1.length();
        int n = num2.length();
        int[] result = new int[m + n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int product = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int sum = product + result[i + j + 1];
                result[i + j + 1] = sum % 10;
                result[i + j] += sum / 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int digit : result) {
            if (sb.length() == 0 && digit == 0) {
                continue;
            }
            sb.append(digit);
        }
        return sb.toString();
    }
}
