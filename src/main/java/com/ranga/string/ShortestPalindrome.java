package com.ranga.string;

/**
 * Given a string {@code s}, you can convert it to a palindrome by adding characters in front of it. Return the shortest palindrome you can find this way.
 *
 * <p><b>Algorithm Strategy (KMP Prefix Function on s + # + reverse(s)):</b>
 * <ol>
 *   <li>The answer is {@code reverse(suffix) + s}, where the suffix is the part of {@code s} that is left after removing its longest palindromic prefix.</li>
 *   <li>Find the longest palindromic prefix: build {@code t = s + '#' + reverse(s)} and compute its KMP prefix function; the last value is the length of that prefix.</li>
 *   <li>Take the remaining suffix {@code s.substring(len)}, reverse it and put it in front of {@code s}.</li>
 *   <li>The separator {@code #} keeps the match from running past the end of {@code s}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code aacecaaa}, the longest palindromic prefix is {@code aacecaa} (length 7); the suffix is {@code a}.</li>
 *   <li>Result: {@code a} + {@code aacecaaa} = {@code aaacecaaa}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — The prefix function is linear.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The combined string and the table.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ShortestPalindrome {

    public static void main(String[] args) {
        String[] inputs = {"aacecaaa", "abcd", "", "a", "abb"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + shortestPalindrome(s));
            System.out.println();
        }
    }

    public static String shortestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s == null ? "" : s;
        }
        String reversed = new StringBuilder(s).reverse().toString();
        String combined = s + "#" + reversed;
        int[] prefix = new int[combined.length()];
        for (int i = 1; i < combined.length(); i++) {
            int k = prefix[i - 1];
            while (k > 0 && combined.charAt(i) != combined.charAt(k)) {
                k = prefix[k - 1];
            }
            if (combined.charAt(i) == combined.charAt(k)) {
                k++;
            }
            prefix[i] = k;
        }
        int palindromeLength = prefix[combined.length() - 1];
        return new StringBuilder(s.substring(palindromeLength)).reverse() + s;
    }
}
