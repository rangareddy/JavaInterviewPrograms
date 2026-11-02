package com.ranga.dp;

/**
 * Utility class to print the first {@code n} Fibonacci numbers using bottom-up dynamic programming.
 *
 * <p><b>Algorithm Strategy (Bottom-Up Tabulation):</b>
 * <ol>
 *   <li>Create a table {@code fib_dp} of size {@code n} and seed it with 0 and 1.</li>
 *   <li>Fill the table left to right using {@code fib_dp[i] = fib_dp[i - 1] + fib_dp[i - 2]}.</li>
 *   <li>Print the table.</li>
 * </ol>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each entry is computed once from two earlier entries.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The table stores all {@code n} numbers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class Fibonacci {
    public static void main(String[] args) {
        int n = 10;
        printFibonacciNumber(n);
    }

    /**
     * Prints the first {@code n} Fibonacci numbers using a table.
     *
     * @param n how many numbers to print (at least 2)
     */
    private static void printFibonacciNumber(int n) {
        int[] fib_dp = new int[n];
        fib_dp[0] = 0;
        fib_dp[1] = 1;
        for (int i = 2; i < n; i++) {
            fib_dp[i] = fib_dp[i - 1] + fib_dp[i - 2];
        }
        for (int fib : fib_dp) {
            System.out.print(fib + " ");
        }
    }
}
