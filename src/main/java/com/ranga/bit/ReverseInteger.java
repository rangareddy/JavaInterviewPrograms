package com.ranga.bit;

/**
 * Given a signed 32-bit integer {@code x}, return {@code x} with its digits reversed. If reversing causes the value to go outside the signed 32-bit range {@code [-2^31, 2^31 - 1]}, return {@code 0}. Assume the environment does not allow 64-bit integers.
 *
 * <p><b>Algorithm Strategy (Pop and Push Digits with Overflow Check):</b>
 * <ol>
 *   <li>Repeatedly pop the last digit of {@code x} with {@code x % 10} (it keeps the sign in Java) and shrink {@code x} with {@code x / 10}.</li>
 *   <li>Push the digit onto {@code result} with {@code result * 10 + digit}.</li>
 *   <li>Before pushing, check that the multiplication cannot overflow: if {@code result > Integer.MAX_VALUE / 10}, or it is equal and {@code digit > 7}, the result would exceed the range. Mirror the check for negative numbers with {@code Integer.MIN_VALUE / 10} and {@code digit < -8}.</li>
 *   <li>On overflow return 0; otherwise return {@code result} when {@code x} reaches 0.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For 123: pops 3, 2, 1 build {@code 3, 32, 321}. For -123 the result is {@code -321}.</li>
 *   <li>Result: {@code 321}. For {@code 1534236469} the reversed value does not fit in 32 bits: {@code 0}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log10 |x|)} — One iteration per digit.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few integers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReverseInteger {

    public static void main(String[] args) {
        int[] inputs = {123, -123, 120, 0, 1534236469, -2147483648, 1463847412};
        for (int x : inputs) {
            System.out.println("Input  : x = " + x);
            System.out.println("Output : " + reverse(x));
            System.out.println();
        }
    }

    public static int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int digit = x % 10;
            x /= 10;
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0;
            }
            if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0;
            }
            result = result * 10 + digit;
        }
        return result;
    }
}
