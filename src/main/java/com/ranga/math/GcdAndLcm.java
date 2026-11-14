package com.ranga.math;

/**
 * Find the greatest common divisor (GCD, the largest number that divides both) and the least common multiple (LCM, the smallest positive number divisible by both) of two integers.
 *
 * <p><b>Algorithm Strategy (Euclidean Algorithm):</b>
 * <ol>
 *   <li>The GCD does not change if the larger number is replaced by the remainder of dividing it by the smaller one: {@code gcd(a, b) = gcd(b, a % b)}.</li>
 *   <li>Repeat until the second number becomes 0; the first number is then the GCD (so {@code gcd(a, 0) = a}).</li>
 *   <li>Use the identity {@code a * b = gcd(a, b) * lcm(a, b)} to get {@code lcm(a, b) = a / gcd(a, b) * b}. Dividing first avoids an unnecessary overflow of {@code a * b}.</li>
 *   <li>If either number is 0 the LCM is defined as 0.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code gcd(48, 18)}: {@code (48, 18) -> (18, 12) -> (12, 6) -> (6, 0)}, so the GCD is {@code 6}.</li>
 *   <li>{@code lcm(48, 18) = 48 / 6 * 18 = 144}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log min(a, b))} — The remainder at least halves every two steps.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Iterative version.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class GcdAndLcm {

    public static void main(String[] args) {
        int[][] inputs = {{48, 18}, {17, 5}, {100, 75}, {7, 0}, {21, 6}};
        for (int[] pair : inputs) {
            System.out.println("Input  : a = " + pair[0] + ", b = " + pair[1]);
            System.out.println("Output : gcd = " + gcd(pair[0], pair[1]) + ", lcm = " + lcm(pair[0], pair[1]));
            System.out.println();
        }
    }

    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    public static long lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return Math.abs((long) a / gcd(a, b) * b);
    }
}
