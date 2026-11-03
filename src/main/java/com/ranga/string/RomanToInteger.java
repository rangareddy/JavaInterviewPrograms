package com.ranga.string;

/**
 * Convert a Roman numeral (symbols {@code I=1, V=5, X=10, L=50, C=100, D=500, M=1000}) to an integer. A smaller symbol written before a larger one is subtracted, for example {@code IV = 4} and {@code IX = 9}. The input is a valid numeral in the range 1 to 3999.
 *
 * <p><b>Algorithm Strategy (Left-to-Right Scan with Subtraction Rule):</b>
 * <ol>
 *   <li>Store the value of each symbol in a lookup (an array indexed by the character).</li>
 *   <li>Scan the string from left to right.</li>
 *   <li>If the current symbol is smaller than the next one, subtract its value ({@code IV}: subtract 1, then add 5); otherwise add it.</li>
 *   <li>The running total after the last symbol is the answer (the last symbol is always added).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code "MCMXCIV"}: {@code M +1000}, {@code C -100}, {@code M +1000}, {@code X -10}, {@code C +100}, {@code I -1}, {@code V +5} gives {@code 1994}.</li>
 *   <li>{@code "LVIII"} has no subtraction: {@code 50 + 5 + 1 + 1 + 1 = 58}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over the numeral.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A fixed lookup table.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RomanToInteger {

    public static void main(String[] args) {
        String[] inputs = {"III", "IV", "IX", "LVIII", "MCMXCIV", "MMMCMXCIX"};
        for (String roman : inputs) {
            System.out.println("Input  : roman = \"" + roman + "\"");
            System.out.println("Output : " + romanToInt(roman));
            System.out.println();
        }
    }

    public static int romanToInt(String roman) {
        int total = 0;
        for (int i = 0; i < roman.length(); i++) {
            int value = valueOf(roman.charAt(i));
            if (i + 1 < roman.length() && value < valueOf(roman.charAt(i + 1))) {
                total -= value;
            } else {
                total += value;
            }
        }
        return total;
    }

    private static int valueOf(char symbol) {
        switch (symbol) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: throw new IllegalArgumentException("Not a Roman symbol: " + symbol);
        }
    }
}
