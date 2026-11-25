package com.ranga.array;

import java.util.Arrays;

/**
 * A non-negative integer is stored as an array of its decimal digits, most significant digit first. Add one to it and return the resulting array.
 *
 * <p><b>Algorithm Strategy (Right-to-Left Carry):</b>
 * <ol>
 *   <li>Walk the array from the last digit.</li>
 *   <li>If the digit is less than 9, increment it and return the array: no carry is left.</li>
 *   <li>If it is 9, set it to 0 and carry on to the next digit to the left.</li>
 *   <li>If every digit was 9, the number is {@code 10...0}: return a new array with a leading 1.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3]} the last digit becomes 4.</li>
 *   <li>For {@code [9, 9]} both digits become 0 and a leading 1 is added, giving {@code [1, 0, 0]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — At most one pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — In place, except when a new array is needed for the all-nines case.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PlusOne {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3}, {4, 3, 2, 1}, {9}, {9, 9}, {1, 9}};
        for (int[] digits : inputs) {
            System.out.println("Input  : digits = " + Arrays.toString(digits));
            System.out.println("Output : " + Arrays.toString(plusOne(digits.clone())));
            System.out.println();
        }
    }

    public static int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) {
            return new int[]{1};
        }
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
