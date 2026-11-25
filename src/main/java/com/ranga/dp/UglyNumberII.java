package com.ranga.dp;

/**
 * An ugly number is a positive integer whose prime factors are limited to {@code 2}, {@code 3} and {@code 5}. Given an integer {@code n}, return the {@code n}-th ugly number (the sequence starts {@code 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, ...}).
 *
 * <p><b>Algorithm Strategy (Generate the Sequence in Order with Three Pointers):</b>
 * <ol>
 *   <li>Every ugly number after 1 is an earlier ugly number times 2, 3 or 5.</li>
 *   <li>Keep three indexes {@code i2}, {@code i3}, {@code i5} into the list of ugly numbers found so far.</li>
 *   <li>The next ugly number is the minimum of {@code ugly[i2] * 2}, {@code ugly[i3] * 3} and {@code ugly[i5] * 5}.</li>
 *   <li>Advance every pointer that produced that minimum, so duplicates such as {@code 6 = 2 * 3 = 3 * 2} appear only once.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>The sequence grows as 1, 2, 3, 4, 5, 6, 8, 9, 10, 12.</li>
 *   <li>The 10th element is 12.</li>
 *   <li>Result: {@code 12}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One constant-time step per number.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The list of ugly numbers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class UglyNumberII {

    public static void main(String[] args) {
        int[] inputs = {10, 1, 7, 15, 1690};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + nthUglyNumber(n));
            System.out.println();
        }
    }

    public static int nthUglyNumber(int n) {
        if (n <= 0) {
            return 0;
        }
        int[] ugly = new int[n];
        ugly[0] = 1;
        int i2 = 0;
        int i3 = 0;
        int i5 = 0;
        for (int i = 1; i < n; i++) {
            int next2 = ugly[i2] * 2;
            int next3 = ugly[i3] * 3;
            int next5 = ugly[i5] * 5;
            int next = Math.min(next2, Math.min(next3, next5));
            ugly[i] = next;
            if (next == next2) {
                i2++;
            }
            if (next == next3) {
                i3++;
            }
            if (next == next5) {
                i5++;
            }
        }
        return ugly[n - 1];
    }
}
