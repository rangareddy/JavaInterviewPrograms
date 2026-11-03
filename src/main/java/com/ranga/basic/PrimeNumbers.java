package com.ranga.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * Check whether a number is prime (greater than 1 and divisible only by 1 and itself) and list all prime numbers up to a limit {@code n}.
 *
 * <p><b>Algorithm Strategy (Trial Division up to the Square Root and the Sieve of Eratosthenes):</b>
 * <ol>
 *   <li>Primality test: numbers below 2 are not prime; 2 and 3 are prime; even numbers and multiples of 3 are not.</li>
 *   <li>If {@code n} has a divisor, it has one that is at most {@code sqrt(n)}, so test only the candidates {@code 5, 7, 11, 13, ...} (numbers of the form {@code 6k +- 1}) while {@code i * i <= n}.</li>
 *   <li>Listing primes: create a boolean array {@code composite[0..n]} and, for each {@code i} from 2 while {@code i * i <= n}, if {@code i} is still unmarked, mark its multiples {@code i * i, i * i + i, ...} as composite.</li>
 *   <li>Every index from 2 that is still unmarked is a prime.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code isPrime(29)}: not divisible by 2 or 3; {@code 5 * 5 = 25 <= 29} and {@code 29 % 5 != 0}, {@code 7 * 7 > 29}: prime.</li>
 *   <li>The sieve for {@code n = 30} marks the multiples of 2, 3 and 5 and leaves {@code 2, 3, 5, 7, 11, 13, 17, 19, 23, 29}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(sqrt n) for the test, O(n log log n) for the sieve} — The test checks about sqrt(n)/3 candidates; the sieve touches every composite a few times.</li>
 *   <li><b>Space Complexity:</b> {@code O(1) for the test, O(n) for the sieve} — The sieve needs one flag per number.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PrimeNumbers {

    public static void main(String[] args) {
        int[] numbers = {1, 2, 17, 25, 29, 97, 100};
        for (int number : numbers) {
            System.out.println("Input  : n = " + number);
            System.out.println("Output : " + (isPrime(number) ? "Prime" : "Not Prime"));
            System.out.println();
        }

        System.out.println("Input  : primes up to 30");
        System.out.println("Output : " + primesUpTo(30));
    }

    public static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n < 4) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    public static List<Integer> primesUpTo(int n) {
        boolean[] composite = new boolean[n + 1];
        for (int i = 2; (long) i * i <= n; i++) {
            if (!composite[i]) {
                for (int multiple = i * i; multiple <= n; multiple += i) {
                    composite[multiple] = true;
                }
            }
        }

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (!composite[i]) {
                primes.add(i);
            }
        }
        return primes;
    }
}
