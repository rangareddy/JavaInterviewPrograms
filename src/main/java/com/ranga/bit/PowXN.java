package com.ranga.bit;

/**
 * Implement {@code pow(x, n)}, which raises {@code x} to the power {@code n}, where {@code n} may be negative.
 *
 * <p><b>Algorithm Strategy (Exponentiation by Squaring):</b>
 * <ol>
 *   <li>Work with {@code long exp = n} so that {@code Integer.MIN_VALUE} can be negated safely; if it is negative, replace {@code x} by {@code 1 / x} and {@code exp} by {@code -exp}.</li>
 *   <li>Look at the bits of {@code exp} from the lowest. Keep {@code base} equal to {@code x} raised to the current bit's power of two.</li>
 *   <li>If the current bit is 1, multiply the result by {@code base}.</li>
 *   <li>Square {@code base}, shift {@code exp} right by one, and repeat until {@code exp} is 0.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 2^10}: 10 is {@code 1010} in binary, so the result multiplies {@code 2^2} and {@code 2^8}.</li>
 *   <li>Result: {@code 4 * 256 = 1024}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — One loop iteration per bit of the exponent.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only a few variables are used.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PowXN {

    public static void main(String[] args) {
        double[] xs = {2.0, 2.1, 2.0, 0.0, 1.0};
        int[] ns = {10, 3, -2, 5, Integer.MIN_VALUE};
        for (int i = 0; i < xs.length; i++) {
            System.out.println("Input  : x = " + xs[i] + ", n = " + ns[i]);
            System.out.println("Output : " + myPow(xs[i], ns[i]));
            System.out.println();
        }
    }

    public static double myPow(double x, int n) {
        long exp = n;
        if (exp < 0) {
            x = 1 / x;
            exp = -exp;
        }
        double result = 1.0;
        double base = x;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result *= base;
            }
            base *= base;
            exp >>= 1;
        }
        return result;
    }
}
