package com.ranga.core;

/**
 * Given an integer {@code n}, return the number of prime numbers that are strictly less than {@code n}.
 *
 * <p><b>Algorithm Strategy (Sieve of Eratosthenes):</b>
 * <ol>
 *   <li>Create a boolean array {@code composite[0..n-1]}, initially all false.</li>
 *   <li>For each {@code i} from 2 while {@code i * i < n}, if {@code i} is still prime, mark {@code i * i, i * i + i, i * i + 2i, ...} as composite.</li>
 *   <li>Starting at {@code i * i} is enough because smaller multiples were already marked by smaller primes.</li>
 *   <li>Count the indices from 2 to {@code n - 1} that are not marked.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 10}: 2 marks 4, 6, 8; 3 marks 9.</li>
 *   <li>The primes below 10 are 2, 3, 5, 7: four of them.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log log n)} — The classic sieve bound.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The boolean array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CountPrimes {

    public static void main(String[] args) {
        int[] inputs = {10, 0, 1, 2, 3, 100, 1000};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + countPrimes(n));
            System.out.println();
        }
    }

    public static int countPrimes(int n) {
        if (n < 3) {
            return 0;
        }
        boolean[] composite = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!composite[i]) {
                count++;
                if ((long) i * i < n) {
                    for (int j = i * i; j < n; j += i) {
                        composite[j] = true;
                    }
                }
            }
        }
        return count;
    }
}
