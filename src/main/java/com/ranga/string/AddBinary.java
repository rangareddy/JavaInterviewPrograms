package com.ranga.string;

/**
 * Given two binary strings {@code a} and {@code b}, return their sum as a binary string.
 *
 * <p><b>Algorithm Strategy (Two Pointers from the Right):</b>
 * <ol>
 *   <li>Start at the last character of both strings with {@code carry = 0}.</li>
 *   <li>At each step add the two bits (0 when a string is exhausted) and the carry.</li>
 *   <li>Append {@code sum % 2} to the result and set {@code carry = sum / 2}.</li>
 *   <li>Continue while either string has digits or a carry remains, then reverse the result.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 11} + {@code 1}: 1 + 1 = 10 (write 0, carry 1), 1 + 0 + carry 1 = 10 (write 0, carry 1), then the final carry writes 1.</li>
 *   <li>Result: {@code 100}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(max(m, n))} — One step per digit.</li>
 *   <li><b>Space Complexity:</b> {@code O(max(m, n))} — The result string.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class AddBinary {

    public static void main(String[] args) {
        String[][] inputs = {{"11", "1"}, {"1010", "1011"}, {"0", "0"}, {"1111", "1111"}};
        for (String[] in : inputs) {
            System.out.println("Input  : a = \"" + in[0] + "\", b = \"" + in[1] + "\"");
            System.out.println("Output : " + addBinary(in[0], in[1]));
            System.out.println();
        }
    }

    public static String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) {
                sum += a.charAt(i--) - '0';
            }
            if (j >= 0) {
                sum += b.charAt(j--) - '0';
            }
            result.append(sum % 2);
            carry = sum / 2;
        }
        return result.reverse().toString();
    }
}
