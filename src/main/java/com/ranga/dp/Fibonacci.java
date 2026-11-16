package com.ranga.dp;

/**
 * Utility class to print the first {@code n} Fibonacci numbers using iteration, and to compute a single Fibonacci number with fast doubling.
 *
 * <p><b>Algorithm Strategy (Rolling Pair to print, Fast Doubling for one number):</b>
 * <ol>
 *   <li>The sequence is defined by {@code F(0) = 0}, {@code F(1) = 1} and {@code F(i) = F(i - 1) + F(i - 2)}.</li>
 *   <li><b>Printing the first {@code n} numbers:</b> keep only the last two numbers in {@code first} and {@code second}, starting with {@code 0} and {@code 1}, and print them. For each remaining position compute {@code third = first + second}, print it, and slide the pair forward.</li>
 *   <li><b>Computing only {@code F(n)}:</b> the identities {@code F(2k) = F(k) * (2 * F(k + 1) - F(k))} and {@code F(2k + 1) = F(k)^2 + F(k + 1)^2} let one call turn the pair {@code (F(k), F(k + 1))} into the pair for {@code 2k} or {@code 2k + 1}, so {@code n} is halved at every step.</li>
 *   <li>A {@code long} holds values up to {@code F(92)}. For {@code n < 0} or {@code n > 92} the method returns {@code -1} instead of an overflowed number.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code n = 6}:
 * <ul>
 *   <li>Start: {@code 0 1}</li>
 *   <li>Then {@code 0 + 1 = 1}, {@code 1 + 1 = 2}, {@code 1 + 2 = 3}, {@code 2 + 3 = 5}</li>
 *   <li>Output: {@code 0 1 1 2 3 5}</li>
 *   <li>Fast doubling for {@code F(10)}: the pairs go {@code (F(0), F(1))}, {@code (F(1), F(2))}, {@code (F(2), F(3))}, {@code (F(5), F(6))}, {@code (F(10), F(11)) = (55, 89)}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} to print {@code n} numbers; {@code O(log n)} for a single {@code F(n)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} to print; {@code O(log n)} recursion stack for a single {@code F(n)}.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class Fibonacci {

    public static void main(String[] args) {
        int n = 10;

        System.out.println("Input  : n = " + n);
        System.out.print("Output : ");
        printFibonacciNumber(n);
        System.out.println();
        System.out.println();

        int[] single = {10, 50, 92, 0, -1, 93};
        for (int k : single) {
            System.out.println("Input  : n = " + k + "   (single number with fast doubling)");
            System.out.println("Output : " + fibonacci(k));
        }
    }

    /**
     * Computes {@code F(n)} with fast doubling in {@code O(log n)} steps.
     *
     * @param n the position in the sequence, starting at 0
     * @return {@code F(n)}, or {@code -1} when {@code n} is negative or {@code F(n)} does not fit a {@code long} ({@code n > 92})
     */
    public static long fibonacci(int n) {
        // Guard Clause: outside 0..92 there is no value that fits a long
        if (n < 0 || n > 92) {
            return -1;
        }
        return fastDoubling(n)[0];
    }

    /** Returns {F(n), F(n + 1)}. */
    private static long[] fastDoubling(int n) {
        if (n == 0) {
            return new long[]{0, 1};
        }
        long[] half = fastDoubling(n / 2);
        long a = half[0], b = half[1];
        long c = a * (2 * b - a);   // F(2k)
        long d = a * a + b * b;     // F(2k + 1)
        return (n % 2 == 0) ? new long[]{c, d} : new long[]{d, c + d};
    }

    /**
     * Prints the first {@code n} Fibonacci numbers.
     *
     * @param n how many numbers to print
     */
    private static void printFibonacciNumber(int n) {
        // Guard Clause: nothing to print for non-positive n
        if (n <= 0) {
            return;
        }
        if (n == 1) {
            System.out.print(0 + " ");
            return;
        }

        int first = 0, second = 1;
        System.out.print(first + " " + second + " ");
        int third;
        for (int i = 2; i < n; i++) {
            third = first + second;
            System.out.print(third + " ");
            first = second;
            second = third;
        }
    }
}
