package com.ranga.string;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class to find the longest palindromic substring using Manacher's algorithm in linear time.
 *
 * <p><b>Algorithm Strategy (Manacher's Algorithm):</b>
 * <ol>
 *   <li>Guard first: {@code null}, empty and single-character strings are returned as they are.</li>
 *   <li>Insert a separator between all characters and add sentinels at both ends, for example {@code "aba"} becomes {@code "^#a#b#a#$"}. Every palindrome of the original string is now an odd-length palindrome around one center, and the sentinels stop the expansion at the borders without bounds checks.</li>
 *   <li>{@code radius[i]} is the palindrome radius around center {@code i} in the new string; it equals the length of the palindrome in the original string.</li>
 *   <li>Keep the palindrome that reaches furthest to the right ({@code center}, {@code right}). For a position {@code i} inside it, the mirror position {@code 2 * center - i} already gives a lower bound: {@code radius[i] = min(right - i, radius[mirror])}. Only then expand further, and move {@code center} and {@code right} when the new palindrome reaches beyond {@code right}.</li>
 *   <li>The largest radius is the longest palindrome; convert its center back with {@code start = (centerIndex - maxLen) / 2}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code "bananas"}:
 * <ul>
 *   <li>The new string is {@code ^#b#a#n#a#n#a#s#$}. The position of the middle letter {@code a} (index 8) expands to radius 5, which is {@code "anana"}.</li>
 *   <li>The positions to the right of it (the second {@code n} and the last {@code a}) start from the radius of their mirror position instead of from 0, so they are not expanded again from scratch.</li>
 *   <li>Result: {@code "anana"}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — The right boundary only moves forward, so the total number of expansion steps is linear.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The transformed string and the radius array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestPalindrome {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("BBABCBCAB", "aaa", "12321", "bananas", "abba", "a", "");
        for (String str : list) {
            System.out.println("Input  : \"" + str + "\"");
            System.out.println("Output : \"" + getLongestPalindrome(str) + "\"");
            System.out.println();
        }
        System.out.println("Input  : null");
        System.out.println("Output : " + getLongestPalindrome(null));
    }

    /**
     * Finds the longest palindromic substring of {@code str} (the first one when several have the same length).
     *
     * @param str the input string
     * @return the longest palindromic substring; {@code null} for {@code null} input, and the input itself when it has fewer than two characters
     */
    private static String getLongestPalindrome(String str) {
        // Guard Clause: null, empty and single-character strings are palindromes themselves
        if (str == null || str.length() < 2) {
            return str;
        }

        StringBuilder sb = new StringBuilder("^");
        for (int i = 0; i < str.length(); i++) {
            sb.append('#').append(str.charAt(i));
        }
        String t = sb.append("#$").toString();          // e.g. "^#a#b#a#$"

        int n = t.length();
        int[] radius = new int[n];
        int center = 0, right = 0;
        for (int i = 1; i < n - 1; i++) {
            if (i < right) {
                radius[i] = Math.min(right - i, radius[2 * center - i]);
            }
            while (t.charAt(i + 1 + radius[i]) == t.charAt(i - 1 - radius[i])) {
                radius[i]++;
            }
            if (i + radius[i] > right) {
                center = i;
                right = i + radius[i];
            }
        }

        int maxLen = 0, centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (radius[i] > maxLen) {
                maxLen = radius[i];
                centerIndex = i;
            }
        }
        int start = (centerIndex - maxLen) / 2;
        return str.substring(start, start + maxLen);
    }
}
