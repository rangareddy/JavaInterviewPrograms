package com.ranga.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * The set {@code [1, 2, 3, ..., n]} contains {@code n!} unique permutations. Listing them in order and numbering from 1, return the {@code k}-th permutation sequence as a string.
 *
 * <p><b>Algorithm Strategy (Pick Each Digit with Factorials):</b>
 * <ol>
 *   <li>Precompute the factorials and keep the list of unused digits {@code 1..n}.</li>
 *   <li>Use zero-based {@code k = k - 1}. For the first position there are {@code (n - 1)!} permutations per leading digit, so the index of the digit is {@code k / (n - 1)!}.</li>
 *   <li>Take that digit out of the list, append it, set {@code k %= (n - 1)!} and repeat with {@code n - 1} positions left.</li>
 *   <li>The result is built in {@code n} steps without generating other permutations.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 3}, {@code k = 3}: {@code k = 2}; {@code 2 / 2! = 1} picks the digit 2; the rest {@code k = 0} gives the smallest arrangement {@code 13}.</li>
 *   <li>Result: {@code 213}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Removing from the list is O(n) per step.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The digit list.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PermutationSequence {

    public static void main(String[] args) {
        int[][] inputs = {{3, 3}, {4, 9}, {3, 1}, {1, 1}, {9, 362880}};
        for (int[] in : inputs) {
            System.out.println("Input  : n = " + in[0] + ", k = " + in[1]);
            System.out.println("Output : " + getPermutation(in[0], in[1]));
            System.out.println();
        }
    }

    public static String getPermutation(int n, int k) {
        if (n <= 0 || k <= 0) {
            return "";
        }
        int[] factorial = new int[n + 1];
        factorial[0] = 1;
        List<Integer> digits = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
            digits.add(i);
        }
        k--;
        StringBuilder result = new StringBuilder();
        for (int position = n; position >= 1; position--) {
            int index = k / factorial[position - 1];
            result.append(digits.remove(index));
            k %= factorial[position - 1];
        }
        return result.toString();
    }
}
