package com.ranga.math;

import java.math.BigInteger;

/**
 * Compute {@code n!} (the product of the integers from 1 to {@code n}, with {@code 0! = 1}). Because factorials grow very quickly, show how to handle results that do not fit in a {@code long}.
 *
 * <p><b>Algorithm Strategy (Iterative Product with BigInteger for Large Values):</b>
 * <ol>
 *   <li>Reject negative input, because the factorial is only defined for non-negative integers.</li>
 *   <li>Start with {@code result = 1} (this also covers {@code 0! = 1}).</li>
 *   <li>Multiply {@code result} by every integer from 2 up to {@code n}.</li>
 *   <li>A {@code long} overflows after {@code 20!}, so use {@code BigInteger} (an arbitrary precision integer) when larger values are needed.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 5}: {@code 1 * 2 * 3 * 4 * 5 = 120}.</li>
 *   <li>For {@code n = 25} the value {@code 15511210043330985984000000} needs {@code BigInteger}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — {@code n - 1} multiplications (for BigInteger each multiplication costs more as the number grows).</li>
 *   <li><b>Space Complexity:</b> {@code O(1) for long, O(n) digits for BigInteger} — The result itself grows with {@code n}.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class Factorial {

    public static void main(String[] args) {
        int[] inputs = {0, 1, 5, 10, 20};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + factorial(n));
            System.out.println();
        }

        System.out.println("Input  : n = 25 (needs BigInteger)");
        System.out.println("Output : " + bigFactorial(25));
    }

    public static long factorial(int n) {
        if (n < 0 || n > 20) {
            throw new IllegalArgumentException("n must be between 0 and 20 for a long result: " + n);
        }

        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static BigInteger bigFactorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must not be negative: " + n);
        }

        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}
