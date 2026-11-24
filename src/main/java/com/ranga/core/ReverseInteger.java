package com.ranga.core;

/**
 * Given a signed 32-bit integer {@code x}, return {@code x} with its digits reversed. If reversing makes the value go outside the signed 32-bit range, return {@code 0}.
 *
 * <p><b>Algorithm Strategy (Pop and Push Digits):</b>
 * <ol>
 *   <li>Repeatedly take the last digit with {@code x % 10} (the sign is kept automatically) and remove it with {@code x / 10}.</li>
 *   <li>Before pushing the digit onto the result, check that {@code result * 10 + digit} would not overflow: compare {@code result} with {@code Integer.MAX_VALUE / 10} and {@code Integer.MIN_VALUE / 10}.</li>
 *   <li>If an overflow would happen, return 0.</li>
 *   <li>Otherwise build {@code result = result * 10 + digit} and continue until {@code x} is 0.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 123}: digits 3, 2, 1 are pushed, giving 3, 32, 321.</li>
 *   <li>For {@code 1534236469} the reversed value does not fit in 32 bits, so the result is 0.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log10 |x|)} — One step per digit.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two integers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReverseInteger {

    public static void main(String[] args) {
        int[] inputs = {123, -123, 120, 0, 1534236469, Integer.MIN_VALUE};
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
