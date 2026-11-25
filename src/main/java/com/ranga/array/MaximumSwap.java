package com.ranga.array;

/**
 * Given a non-negative integer {@code num}, you may swap two digits at most once. Return the maximum value you can get.
 *
 * <p><b>Algorithm Strategy (Last Position of Each Digit):</b>
 * <ol>
 *   <li>Record the last index of every digit {@code 0-9} in the number.</li>
 *   <li>Scan the digits from the left. For the digit at position {@code i}, look for a larger digit {@code d} from 9 down to the current digit + 1 whose last index is after {@code i}.</li>
 *   <li>On the first such digit, swap the two positions and stop: the leftmost improvable position gives the biggest gain, and using the last occurrence of the larger digit keeps the number as large as possible.</li>
 *   <li>If no swap helps, the number is already the maximum.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 2736}: at the first digit 2, the digit 7 occurs later, so swap them.</li>
 *   <li>Result: {@code 7236}. For {@code 9973} nothing larger exists to the right, so the answer stays {@code 9973}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(d)} — {@code d} is the number of digits; the inner search is at most 10 steps.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A 10-element array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumSwap {

    public static void main(String[] args) {
        int[] inputs = {2736, 9973, 0, 98368, 1993};
        for (int num : inputs) {
            System.out.println("Input  : num = " + num);
            System.out.println("Output : " + maximumSwap(num));
            System.out.println();
        }
    }

    public static int maximumSwap(int num) {
        if (num < 10) {
            return num;
        }
        char[] digits = String.valueOf(num).toCharArray();
        int[] last = new int[10];
        for (int i = 0; i < digits.length; i++) {
            last[digits[i] - '0'] = i;
        }
        for (int i = 0; i < digits.length; i++) {
            for (int d = 9; d > digits[i] - '0'; d--) {
                if (last[d] > i) {
                    char temp = digits[i];
                    digits[i] = digits[last[d]];
                    digits[last[d]] = temp;
                    return Integer.parseInt(new String(digits));
                }
            }
        }
        return num;
    }
}
