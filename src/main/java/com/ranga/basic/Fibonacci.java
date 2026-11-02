package com.ranga.basic;

/**
 * Utility class to print the first {@code n} Fibonacci numbers using iteration.
 *
 * <p><b>Algorithm Strategy (Rolling Pair):</b>
 * <ol>
 *   <li>Start with the first two numbers, 0 and 1.</li>
 *   <li>In each iteration compute the next number as the sum of the previous two.</li>
 *   <li>Slide the pair forward so only the last two numbers are kept.</li>
 * </ol>
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
        printFibonacciNumber(n);
    }

    /**
     * Prints the first {@code n} Fibonacci numbers.
     *
     * @param n how many numbers to print
     */
    private static void printFibonacciNumber(int n) {
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
