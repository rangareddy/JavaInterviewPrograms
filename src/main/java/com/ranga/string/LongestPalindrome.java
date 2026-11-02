package com.ranga.string;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class to find the longest palindromic substring using the expand-around-center approach.
 *
 * <p><b>Algorithm Strategy (Expand Around Center):</b>
 * <ol>
 *   <li>Every palindrome mirrors around its center. The center is a single character for odd-length palindromes
 *       (such as {@code "aba"}) or a pair of adjacent characters for even-length palindromes (such as {@code "abba"}).</li>
 *   <li>A string of length {@code n} has {@code 2n - 1} possible centers. For every index {@code i}, try the odd
 *       center {@code (i, i)} and the even center {@code (i, i + 1)}.</li>
 *   <li>For each center, move {@code start} left and {@code end} right while both are inside the string and
 *       {@code str.charAt(start) == str.charAt(end)}.</li>
 *   <li>When the expansion stops, {@code str.substring(start + 1, end)} is the longest palindrome around that center.</li>
 *   <li>Keep the longest palindrome seen over all centers.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code "bananas"}:
 * <ul>
 *   <li>Center {@code i = 2} (letter {@code n}): expands {@code n -> ana} and stops at {@code "ana"} because {@code b != n}.</li>
 *   <li>Center {@code i = 3} (letter {@code a}): expands {@code a -> nan -> anana} (length 5, new best) and stops.</li>
 *   <li>Result: {@code "anana"}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — There are {@code 2n - 1} centers and each expansion takes up to {@code O(n)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} auxiliary space (excluding the result substring).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestPalindrome {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("BBABCBCAB", "aaa", "12321", "bananas");
        for (String str : list) {
            System.out.println("Input  : \"" + str + "\"");
            System.out.println("Output : \"" + getLongestPalindrome(str) + "\"");
            System.out.println();
        }
    }

    /**
     * Finds the longest palindromic substring of {@code str}.
     *
     * @param str the input string
     * @return the longest palindromic substring
     */
    private static String getLongestPalindrome(String str) {
        // Guard Clause: null, empty and single-character strings are palindromes themselves
        if (str == null || str.length() < 2) {
            return str;
        }

        String longest = str.substring(0, 1);
        int n = str.length();
        for (int i = 0; i < n; i++) {

            // get longest palindrome with center of i (odd length)
            String palindrome = intermediatePalindrome(str, i, i);
            if (palindrome.length() > longest.length()) {
                longest = palindrome;
            }

            // get longest palindrome with center of i, i+1 (even length)
            palindrome = intermediatePalindrome(str, i, i + 1);
            if (palindrome.length() > longest.length()) {
                longest = palindrome;
            }
        }
        return longest;
    }

    /**
     * Expands outwards from the given center while the characters match.
     *
     * @param str   the input string
     * @param start the left index of the center
     * @param end   the right index of the center
     * @return the palindrome found around the center
     */
    private static String intermediatePalindrome(String str, int start, int end) {
        while (start >= 0 && end < str.length() && str.charAt(start) == str.charAt(end)) {
            start--;
            end++;
        }
        return str.substring(start + 1, end);
    }
}
