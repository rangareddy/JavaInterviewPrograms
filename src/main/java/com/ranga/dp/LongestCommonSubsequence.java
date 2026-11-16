package com.ranga.dp;

/**
 * Given two strings {@code text1} and {@code text2}, return the length of their longest common subsequence (characters in the same relative order, not necessarily contiguous), or 0 if there is none.
 *
 * <p><b>Algorithm Strategy (Dynamic Programming with Two Rolling Rows):</b>
 * <ol>
 *   <li>Guard first: a {@code null} string has no common subsequence, so the answer is 0.</li>
 *   <li>Let {@code dp[i][j]} be the LCS length of the first {@code i} characters of {@code text1} and the first {@code j} characters of {@code text2}. If {@code text1[i - 1] == text2[j - 1]} then {@code dp[i][j] = dp[i - 1][j - 1] + 1}, otherwise {@code dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])}.</li>
 *   <li>Row {@code i} depends only on row {@code i - 1}, so keep just two arrays, {@code previous} and {@code current}, and swap them after every row.</li>
 *   <li>Use the shorter string for the columns, so the arrays have {@code min(m, n) + 1} entries.</li>
 *   <li>The answer is the last entry of the final row.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "abcde"} and {@code "ace"} (columns = the shorter {@code "ace"}): the equal pairs a, c and e each add 1 along a diagonal, so the last row ends with {@code [0, 1, 2, 3]}.</li>
 *   <li>Result: {@code 3} ("ace").</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Every table cell is computed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(min(m, n))} — Two rows of the shorter string's length instead of the whole table.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestCommonSubsequence {

    public static void main(String[] args) {
        String[][] inputs = {{"abcde", "ace"}, {"abc", "abc"}, {"abc", "def"}, {"AGGTAB", "GXTXAYB"}, {"", "abc"}, {null, "abc"}};
        for (String[] input : inputs) {
            System.out.println("Input  : text1 = " + quote(input[0]) + ", text2 = " + quote(input[1]));
            System.out.println("Output : " + longestCommonSubsequence(input[0], input[1]));
            System.out.println();
        }
    }

    private static String quote(String s) {
        return s == null ? "null" : "\"" + s + "\"";
    }

    public static int longestCommonSubsequence(String text1, String text2) {
        // Guard Clause: a missing string has nothing in common with anything
        if (text1 == null || text2 == null) {
            return 0;
        }
        // The columns follow the shorter string, which keeps the two rows small
        if (text1.length() < text2.length()) {
            String temp = text1;
            text1 = text2;
            text2 = temp;
        }

        int n = text2.length();
        int[] previous = new int[n + 1];
        int[] current = new int[n + 1];
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    current[j] = previous[j - 1] + 1;
                } else {
                    current[j] = Math.max(previous[j], current[j - 1]);
                }
            }
            int[] temp = previous;
            previous = current;
            current = temp;
        }
        return previous[n];
    }
}
