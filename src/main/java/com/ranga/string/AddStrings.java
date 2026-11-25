package com.ranga.string;

/**
 * Given two non-negative integers {@code num1} and {@code num2} represented as decimal strings, return their sum as a string, without converting the inputs to integers or using {@code BigInteger}.
 *
 * <p><b>Algorithm Strategy (Two Pointers from the Right):</b>
 * <ol>
 *   <li>Walk both strings from the last digit with {@code carry = 0}.</li>
 *   <li>Add the two digits (0 once a string is used up) and the carry.</li>
 *   <li>Append {@code sum % 10} and set {@code carry = sum / 10}.</li>
 *   <li>Stop when both strings and the carry are finished, then reverse.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 11} + {@code 123}: 1 + 3 = 4, 1 + 2 = 3, then 0 + 1 = 1.</li>
 *   <li>Result: {@code 134}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(max(m, n))} — One step per digit.</li>
 *   <li><b>Space Complexity:</b> {@code O(max(m, n))} — The result.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class AddStrings {

    public static void main(String[] args) {
        String[][] inputs = {{"11", "123"}, {"456", "77"}, {"0", "0"}, {"999", "1"}};
        for (String[] in : inputs) {
            System.out.println("Input  : num1 = \"" + in[0] + "\", num2 = \"" + in[1] + "\"");
            System.out.println("Output : " + addStrings(in[0], in[1]));
            System.out.println();
        }
    }

    public static String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;
        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) {
                sum += num1.charAt(i--) - '0';
            }
            if (j >= 0) {
                sum += num2.charAt(j--) - '0';
            }
            result.append(sum % 10);
            carry = sum / 10;
        }
        return result.reverse().toString();
    }
}
