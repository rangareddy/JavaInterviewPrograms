package com.ranga.core;

import java.util.ArrayList;
import java.util.List;

/**
 * For the numbers from 1 to {@code n} produce {@code "Fizz"} for multiples of 3, {@code "Buzz"} for multiples of 5, {@code "FizzBuzz"} for multiples of both and the number itself otherwise.
 *
 * <p><b>Algorithm Strategy (Single Pass with Modulo Checks):</b>
 * <ol>
 *   <li>Loop {@code i} from 1 to {@code n}.</li>
 *   <li>Check the combined case first: if {@code i % 15 == 0} (divisible by both 3 and 5) the word is {@code "FizzBuzz"}.</li>
 *   <li>Otherwise, if {@code i % 3 == 0} the word is {@code "Fizz"}, and if {@code i % 5 == 0} it is {@code "Buzz"}.</li>
 *   <li>In every other case the word is the number {@code i} written as a string. Collect the words in order.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 15}: {@code 1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, FizzBuzz}.</li>
 *   <li>Checking {@code i % 15} before the single checks is what makes 15 print {@code FizzBuzz} instead of just {@code Fizz}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Constant work per number.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The returned list of {@code n} words.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FizzBuzz {

    public static void main(String[] args) {
        int[] inputs = {1, 5, 15};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + fizzBuzz(n));
            System.out.println();
        }
    }

    public static List<String> fizzBuzz(int n) {
        List<String> words = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) {
                words.add("FizzBuzz");
            } else if (i % 3 == 0) {
                words.add("Fizz");
            } else if (i % 5 == 0) {
                words.add("Buzz");
            } else {
                words.add(String.valueOf(i));
            }
        }
        return words;
    }
}
