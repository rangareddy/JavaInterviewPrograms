package com.ranga.math;

/**
 * Implement {@code pow(x, n)}, which raises {@code x} to the integer power {@code n} (which may be negative).
 *
 * <p><b>Algorithm Strategy (Fast Exponentiation (Exponentiation by Squaring)):</b>
 * <ol>
 *   <li>Convert the exponent to a {@code long} so that {@code Integer.MIN_VALUE} can be negated safely. If it is negative, invert the base ({@code x = 1 / x}) and make the exponent positive.</li>
 *   <li>Keep a {@code result} initialised to 1 and a running power {@code base = x}.</li>
 *   <li>For every bit of the exponent from the lowest: if the bit is 1, multiply {@code result} by {@code base}; then square {@code base}.</li>
 *   <li>After about {@code log2(n)} iterations {@code result} equals {@code x^n}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code x = 2}, {@code n = 10} (binary {@code 1010}): the bits multiply {@code 4} and {@code 256}, giving {@code 4 * 256 = 1024}.</li>
 *   <li>Result: {@code 1024.0}. For {@code x = 2}, {@code n = -2} the result is {@code 0.25}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — One iteration per bit of the exponent.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PowXN {

    public static void main(String[] args) {
        double[] bases = {2.0, 2.1, 2.0, 0.5, 1.0, 2.0};
        int[] exponents = {10, 3, -2, 4, Integer.MIN_VALUE, 0};
        for (int i = 0; i < bases.length; i++) {
            System.out.println("Input  : x = " + bases[i] + ", n = " + exponents[i]);
            System.out.println("Output : " + myPow(bases[i], exponents[i]));
            System.out.println();
        }
    }

    public static double myPow(double x, int n) {
        long exponent = n;
        if (exponent < 0) {
            x = 1 / x;
            exponent = -exponent;
        }

        double result = 1;
        double base = x;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result *= base;
            }
            base *= base;
            exponent >>= 1;
        }
        return result;
    }
}
