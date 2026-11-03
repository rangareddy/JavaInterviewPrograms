package com.ranga.string;

/**
 * Implement {@code myAtoi(String s)}, which converts a string to a 32-bit signed integer: skip leading spaces, read an optional {@code +} or {@code -} sign, read digits until the first non-digit, and clamp the result to the range {@code [-2^31, 2^31 - 1]}. Return 0 if no digits were read.
 *
 * <p><b>Algorithm Strategy (State-by-State Parsing with Overflow Clamping):</b>
 * <ol>
 *   <li>Skip the leading spaces.</li>
 *   <li>If the next character is {@code +} or {@code -}, remember the sign and move on.</li>
 *   <li>Read digits one by one: {@code result = result * 10 + digit}. Stop at the first character that is not a digit.</li>
 *   <li>Before multiplying check for overflow: if {@code result > (Integer.MAX_VALUE - digit) / 10} return {@code Integer.MAX_VALUE} (or {@code Integer.MIN_VALUE} for a negative sign). Return {@code sign * result}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code "   -42"}: spaces skipped, sign {@code -}, digits {@code 42}, result {@code -42}.</li>
 *   <li>{@code "4193 with words"} reads {@code 4193} and stops at the space; {@code "91283472332"} exceeds the range and is clamped to {@code 2147483647}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class StringToIntegerAtoi {

    public static void main(String[] args) {
        String[] inputs = {"42", "   -42", "4193 with words", "words and 987", "-91283472332", "91283472332", "+1", "", "   "};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + myAtoi(s));
            System.out.println();
        }
    }

    public static int myAtoi(String s) {
        int i = 0;
        int n = s.length();
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }

        int sign = 1;
        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
            sign = s.charAt(i) == '-' ? -1 : 1;
            i++;
        }

        int result = 0;
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            if (result > (Integer.MAX_VALUE - digit) / 10) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            result = result * 10 + digit;
            i++;
        }
        return sign * result;
    }
}
