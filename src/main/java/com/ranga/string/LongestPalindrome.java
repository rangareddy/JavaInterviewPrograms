package com.ranga.string;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class to find the longest palindromic substring using the expand-around-center approach.
 *
 * <p><b>Algorithm Strategy (Expand Around Center):</b>
 * <ol>
 *   <li>Every palindrome mirrors around a center: a single character (odd length) or a pair of characters (even length).</li>
 *   <li>For each index, expand outwards from both kinds of center while the characters on both sides match.</li>
 *   <li>Keep the longest palindrome seen.</li>
 * </ol>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — There are {@code 2n - 1} centers and each expansion takes up to {@code O(n)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} auxiliary space space (excluding the result substring).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestPalindrome {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("BBABCBCAB", "aaa", "12321", "bananas");
        for (String str : list) {
            String longestStr = getLongestPalindrome(str);
            System.out.println(longestStr);
        }
    }

    /**
     * Finds the longest palindromic substring of {@code str}.
     *
     * @param str the input string
     * @return the longest palindromic substring
     */
    private static String getLongestPalindrome(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }

        String longest = str.substring(0, 1);
        int n = str.length();
        for (int i = 0; i < n; i++) {

            // get longest palindrome with center of i
            String palindrome = intermediatePalindrome(str, i, i);
            if (palindrome.length() > longest.length()) {
                longest = palindrome;
            }

            // get longest palindrome with center of i, i+1
            palindrome = intermediatePalindrome(str, i, i + 1);
            if (palindrome.length() > longest.length()) {
                longest = palindrome;
            }
        }
        return longest;
    }

    // Given a center, either one letter or two letter,
    /**
     * Expands outwards from the given center while the characters match.
     *
     * @param start the left index of the center
     * @param end the right index of the center
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
