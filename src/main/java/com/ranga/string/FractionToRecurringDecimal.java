package com.ranga.string;

import java.util.HashMap;
import java.util.Map;

/**
 * Given two integers representing the {@code numerator} and {@code denominator} of a fraction, return the fraction in string format. If the fractional part is repeating, enclose the repeating part in parentheses.
 *
 * <p><b>Algorithm Strategy (Long Division Remembering Remainders):</b>
 * <ol>
 *   <li>Handle the sign (negative if exactly one of the numerator and the denominator is negative) and work with {@code long} absolute values to avoid overflow.</li>
 *   <li>Write the integer part {@code numerator / denominator}; if the remainder is 0, you are done.</li>
 *   <li>Otherwise add {@code .} and do long division: multiply the remainder by 10, append the digit {@code remainder / denominator}, and take the new remainder.</li>
 *   <li>Store each remainder with the position where its digit was written; when a remainder repeats, insert {@code (} at that position and append {@code )}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 2 / 3}: remainder 2 -> digit 6 remainder 2 again. The remainder 2 was seen at position 0 of the fraction, so the 6 repeats.</li>
 *   <li>Result: {@code 0.(6)}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(d)} — {@code d} is the length of the fraction part, bounded by the denominator.</li>
 *   <li><b>Space Complexity:</b> {@code O(d)} — The remainder map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FractionToRecurringDecimal {

    public static void main(String[] args) {
        long[][] inputs = {{2, 3}, {1, 2}, {2, 1}, {4, 333}, {-50, 8}, {1, -3}, {-2147483648L, -1}};
        for (long[] in : inputs) {
            System.out.println("Input  : numerator = " + in[0] + ", denominator = " + in[1]);
            System.out.println("Output : " + fractionToDecimal((int) in[0], (int) in[1]));
            System.out.println();
        }
    }

    public static String fractionToDecimal(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("denominator must not be zero");
        }
        if (numerator == 0) {
            return "0";
        }
        StringBuilder result = new StringBuilder();
        if ((numerator < 0) ^ (denominator < 0)) {
            result.append('-');
        }
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        result.append(num / den);
        long remainder = num % den;
        if (remainder == 0) {
            return result.toString();
        }
        result.append('.');
        Map<Long, Integer> seen = new HashMap<>();
        while (remainder != 0) {
            Integer position = seen.get(remainder);
            if (position != null) {
                result.insert(position.intValue(), '(');
                result.append(')');
                break;
            }
            seen.put(remainder, result.length());
            remainder *= 10;
            result.append(remainder / den);
            remainder %= den;
        }
        return result.toString();
    }
}
