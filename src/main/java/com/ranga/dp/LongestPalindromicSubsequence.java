package com.ranga.dp;

/**
 * Given a string {@code s}, return the length of its longest palindromic subsequence (a sequence of characters that keeps the original order, may skip characters and reads the same in both directions).
 *
 * <p><b>Algorithm Strategy (Interval DP on Substrings):</b>
 * <ol>
 *   <li>Let {@code dp[i][j]} be the length of the longest palindromic subsequence of {@code s[i..j]}. A single character is a palindrome of length 1: {@code dp[i][i] = 1}.</li>
 *   <li>If the end characters match ({@code s[i] == s[j]}) they can wrap the best palindrome of the inside: {@code dp[i][j] = dp[i + 1][j - 1] + 2}.</li>
 *   <li>Otherwise one of the end characters is not used: {@code dp[i][j] = max(dp[i + 1][j], dp[i][j - 1])}.</li>
 *   <li>Fill the table for increasing substring length (or with {@code i} going from the end of the string); the answer is {@code dp[0][n - 1]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "bbbab"} the longest palindromic subsequence is {@code "bbbb"}, length 4.</li>
 *   <li>For {@code "cbbd"} it is {@code "bb"}, length 2.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Every pair {@code (i, j)} is computed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n^2)} — The table; it can be reduced to O(n) by keeping only the previous row.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestPalindromicSubsequence {

    public static void main(String[] args) {
        String[] inputs = {"bbbab", "cbbd", "a", "abcde", "agbdba"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + longestPalindromeSubseq(s));
            System.out.println();
        }
    }

    public static int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return n == 0 ? 0 : dp[0][n - 1];
    }
}
