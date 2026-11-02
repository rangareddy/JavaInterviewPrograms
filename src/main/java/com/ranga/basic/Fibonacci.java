package com.ranga.basic;

/**
 * Utility class to print the first {@code n} Fibonacci numbers using iteration.
 *
 * <p><b>Algorithm Strategy (Rolling Pair):</b>
 * <ol>
 *   <li>The sequence is defined by {@code F(0) = 0}, {@code F(1) = 1} and {@code F(i) = F(i - 1) + F(i - 2)}.</li>
 *   <li>Keep only the last two numbers in {@code first} and {@code second}, starting with {@code 0} and {@code 1},
 *       and print them.</li>
 *   <li>For each remaining position, compute {@code third = first + second} and print it.</li>
 *   <li>Slide the pair forward: {@code first = second} and {@code second = third}.</li>
 *   <li>Stop after {@code n} numbers have been produced.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code n = 6}:
 * <ul>
 *   <li>Start: {@code 0 1}</li>
 *   <li>Then {@code 0 + 1 = 1}, {@code 1 + 1 = 2}, {@code 1 + 2 = 3}, {@code 2 + 3 = 5}</li>
 *   <li>Output: {@code 0 1 1 2 3 5}</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — A single loop of {@code n} iterations.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only three integer variables are used.</li>
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
