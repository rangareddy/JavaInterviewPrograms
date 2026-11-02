package com.ranga.dp;

/**
 * Given two strings {@code text1} and {@code text2}, return the length of their longest common subsequence (characters in the same relative order, not necessarily contiguous), or 0 if there is none.
 *
 * <p><b>Algorithm Strategy (Two-Dimensional Dynamic Programming):</b>
 * <ol>
 *   <li>Let {@code dp[i][j]} be the LCS length of the first {@code i} characters of {@code text1} and the first {@code j} characters of {@code text2}. Row 0 and column 0 are 0 (an empty prefix has no common characters).</li>
 *   <li>If the characters {@code text1[i - 1]} and {@code text2[j - 1]} are equal, they extend the best subsequence of both shorter prefixes: {@code dp[i][j] = dp[i - 1][j - 1] + 1}.</li>
 *   <li>Otherwise skip one character from either string and keep the better result: {@code dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])}.</li>
 *   <li>The answer is {@code dp[m][n]} in the bottom-right corner.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "abcde"} and {@code "ace"}: the equal pairs a, c and e each add 1 along a diagonal.</li>
 *   <li>Result: {@code 3} ("ace").</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Every table cell is computed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — The table.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestCommonSubsequence {

    public static void main(String[] args) {
        String[][] inputs = {{"abcde", "ace"}, {"abc", "abc"}, {"abc", "def"}, {"AGGTAB", "GXTXAYB"}};
        for (String[] input : inputs) {
            System.out.println("Input  : text1 = \"" + input[0] + "\", text2 = \"" + input[1] + "\"");
            System.out.println("Output : " + longestCommonSubsequence(input[0], input[1]));
            System.out.println();
        }
    }

    public static int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0;
        }

        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }
}
