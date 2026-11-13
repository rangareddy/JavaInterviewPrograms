package com.ranga.string;

/**
 * Convert an integer in the range {@code [1, 3999]} to its Roman numeral. Roman numerals use the symbols {@code I, V, X, L, C, D, M} and the subtractive forms {@code IV, IX, XL, XC, CD, CM}.
 *
 * <p><b>Algorithm Strategy (Greedy over Value-Symbol Pairs):</b>
 * <ol>
 *   <li>List the 13 values that can appear in a numeral, largest first, including the six subtractive pairs: {@code 1000 M, 900 CM, 500 D, 400 CD, 100 C, 90 XC, 50 L, 40 XL, 10 X, 9 IX, 5 V, 4 IV, 1 I}.</li>
 *   <li>For each value, while the remaining number is at least that value, append its symbol and subtract the value.</li>
 *   <li>Putting the subtractive pairs in the table is what makes the greedy choice correct: no special handling of 4 or 9 is needed.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1994}: {@code M} leaves 994, {@code CM} leaves 94, {@code XC} leaves 4, {@code IV} leaves 0.</li>
 *   <li>Result: {@code MCMXCIV}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — The input is bounded by 3999, so at most 15 symbols are appended and the table has 13 entries.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — The two constant tables and the output.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class IntegerToRoman {

    public static void main(String[] args) {
        int[] inputs = {3, 58, 1994, 3999, 4, 9};
        for (int num : inputs) {
            System.out.println("Input  : num = " + num);
            System.out.println("Output : " + intToRoman(num));
            System.out.println();
        }
    }

    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < VALUES.length && num > 0; i++) {
            while (num >= VALUES[i]) {
                sb.append(SYMBOLS[i]);
                num -= VALUES[i];
            }
        }
        return sb.toString();
    }
}
