package com.ranga.math;

import java.util.Arrays;

/**
 * You are given a large integer as an array of digits {@code digits}, most significant digit first, with no leading zeros. Increment the integer by one and return the resulting array of digits.
 *
 * <p><b>Algorithm Strategy (Right-to-Left Carry):</b>
 * <ol>
 *   <li>Walk from the last digit to the first.</li>
 *   <li>If the digit is less than 9, increment it and return the array: no carry remains.</li>
 *   <li>Otherwise set the digit to 0 (the carry moves one place to the left) and continue.</li>
 *   <li>If the loop finishes, every digit was 9: return a new array {@code [1, 0, 0, ..., 0]} with one extra digit.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 9]}: the 9 becomes 0, the 2 becomes 3.</li>
 *   <li>Result: {@code [1, 3, 0]}. For {@code [9, 9]} the result is {@code [1, 0, 0]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — At most one pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — In place, except for the extra digit when all digits are 9 (then O(n)).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PlusOne {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3}, {4, 3, 2, 1}, {9}, {1, 2, 9}, {9, 9, 9}};
        for (int[] digits : inputs) {
            System.out.println("Input  : digits = " + Arrays.toString(digits));
            System.out.println("Output : " + Arrays.toString(plusOne(digits.clone())));
            System.out.println();
        }
    }

    public static int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }

        int[] result = new int[digits.length + 1];
        result[0] = 1;
        return result;
    }
}
