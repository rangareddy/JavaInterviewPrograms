package com.ranga.dp;

/**
 * Utility class to print the first {@code n} Fibonacci numbers using bottom-up dynamic programming.
 *
 * <p><b>Algorithm Strategy (Bottom-Up Tabulation):</b>
 * <ol>
 *   <li>Define the sub-problem: {@code fib_dp[i]} is the {@code i}-th Fibonacci number.</li>
 *   <li>Create a table of size {@code n} and fill the base cases: {@code fib_dp[0] = 0} and {@code fib_dp[1] = 1}.</li>
 *   <li>Fill the table from left to right with the recurrence {@code fib_dp[i] = fib_dp[i - 1] + fib_dp[i - 2]};
 *       every entry only depends on two entries that are already computed.</li>
 *   <li>Print the table. Each sub-problem is solved once, unlike plain recursion which recomputes the same
 *       values exponentially many times.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code n = 6}:
 * <ul>
 *   <li>Base: {@code [0, 1, _, _, _, _]}</li>
 *   <li>i = 2..5: {@code 1, 2, 3, 5}</li>
 *   <li>Table: {@code [0, 1, 1, 2, 3, 5]}</li>
 * </ul>
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

        System.out.println("Input  : n = " + n);
        System.out.print("Output : ");
        printFibonacciNumber(n);
        System.out.println();
    }

    /**
     * Prints the first {@code n} Fibonacci numbers using a table.
     *
     * @param n how many numbers to print
     */
    private static void printFibonacciNumber(int n) {
        // Guard Clause: nothing to print for non-positive n
        if (n <= 0) {
            return;
        }

        int[] fib_dp = new int[Math.max(n, 2)];
        fib_dp[0] = 0;
        fib_dp[1] = 1;
        for (int i = 2; i < n; i++) {
            fib_dp[i] = fib_dp[i - 1] + fib_dp[i - 2];
        }
        for (int i = 0; i < n; i++) {
            System.out.print(fib_dp[i] + " ");
        }
    }
}
