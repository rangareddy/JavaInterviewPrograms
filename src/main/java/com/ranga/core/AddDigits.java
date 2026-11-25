package com.ranga.core;

/**
 * Given an integer {@code num}, repeatedly add all its digits until the result has only one digit, and return it.
 *
 * <p><b>Algorithm Strategy (Digital Root in Constant Time):</b>
 * <ol>
 *   <li>The repeated digit sum of a positive number is its digital root.</li>
 *   <li>A number and its digit sum have the same remainder modulo 9, so the digital root is {@code 1 + (num - 1) % 9} for {@code num > 0}.</li>
 *   <li>For {@code num == 0} the answer is 0.</li>
 *   <li>Return the formula's value without any loop.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 38}: {@code 3 + 8 = 11}, then {@code 1 + 1 = 2}.</li>
 *   <li>The formula gives {@code 1 + (38 - 1) % 9 = 1 + 1 = 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Arithmetic only.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — No extra storage.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class AddDigits {

    public static void main(String[] args) {
        int[] inputs = {38, 0, 9, 10, 999999999};
        for (int num : inputs) {
            System.out.println("Input  : num = " + num);
            System.out.println("Output : " + addDigits(num));
            System.out.println();
        }
    }

    public static int addDigits(int num) {
        if (num <= 0) {
            return 0;
        }
        return 1 + (num - 1) % 9;
    }
}
