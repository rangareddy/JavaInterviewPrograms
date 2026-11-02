package com.ranga.string;

/**
 * Given a string {@code s}, return the number of palindromic substrings in it. Substrings with different start or end positions are counted separately even if they have the same characters.
 *
 * <p><b>Algorithm Strategy (Expand Around Every Center):</b>
 * <ol>
 *   <li>Every palindrome has a center: one character (odd length) or two adjacent characters (even length). There are {@code 2n - 1} centers.</li>
 *   <li>For each center, expand outwards while the characters on both sides are equal.</li>
 *   <li>Every successful expansion step is one more palindromic substring, so add 1 to the counter each time.</li>
 *   <li>Return the counter after all centers have been processed.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "aaa"}: the centers produce {@code a, a, a} (3 single letters), {@code aa, aa} (2) and {@code aaa} (1).</li>
 *   <li>Result: {@code 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — {@code 2n - 1} centers, each expansion up to {@code O(n)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PalindromicSubstrings {

    public static void main(String[] args) {
        String[] inputs = {"abc", "aaa", "abba"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + countSubstrings(s));
            System.out.println();
        }
    }

    public static int countSubstrings(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (int center = 0; center < s.length(); center++) {
            count += expand(s, center, center);         // odd length palindromes
            count += expand(s, center, center + 1);     // even length palindromes
        }
        return count;
    }

    private static int expand(String s, int left, int right) {
        int count = 0;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++;
            left--;
            right++;
        }
        return count;
    }
}
