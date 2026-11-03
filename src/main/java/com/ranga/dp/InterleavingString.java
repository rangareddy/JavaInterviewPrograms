package com.ranga.dp;

/**
 * Given strings {@code s1}, {@code s2} and {@code s3}, determine whether {@code s3} is formed by an interleaving of {@code s1} and {@code s2}: both are split into substrings that are merged alternately while keeping the relative order of the characters of each string.
 *
 * <p><b>Algorithm Strategy (DP over the Prefixes of s1 and s2):</b>
 * <ol>
 *   <li>If {@code s1.length() + s2.length() != s3.length()} the answer is {@code false}.</li>
 *   <li>Let {@code dp[i][j]} be {@code true} when the first {@code i} characters of {@code s1} and the first {@code j} characters of {@code s2} can interleave into the first {@code i + j} characters of {@code s3}. {@code dp[0][0]} is true.</li>
 *   <li>{@code dp[i][j]} is true if the last character of {@code s3[0..i+j)} comes from {@code s1} ({@code dp[i-1][j]} and {@code s1[i-1] == s3[i+j-1]}) or from {@code s2} ({@code dp[i][j-1]} and {@code s2[j-1] == s3[i+j-1]}).</li>
 *   <li>Only the previous row is needed, so the table is compressed to one array over {@code j}. The answer is {@code dp[n]} after processing all rows.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code s1 = "aabcc"}, {@code s2 = "dbbca"}, {@code s3 = "aadbbcbcac"}: the characters of {@code s3} can be taken alternately from {@code s1} and {@code s2} while keeping the order inside each string.</li>
 *   <li>Result: {@code true}. For {@code "aadbbbaccc"} no interleaving works: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Every pair of prefixes is examined once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — One row of the table.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class InterleavingString {

    public static void main(String[] args) {
        String[][] inputs = {
                {"aabcc", "dbbca", "aadbbcbcac"},
                {"aabcc", "dbbca", "aadbbbaccc"},
                {"", "", ""},
                {"a", "", "a"},
                {"abc", "def", "adbecf"}
        };
        for (String[] input : inputs) {
            System.out.println("Input  : s1 = \"" + input[0] + "\", s2 = \"" + input[1] + "\", s3 = \"" + input[2] + "\"");
            System.out.println("Output : " + isInterleave(input[0], input[1], input[2]));
            System.out.println();
        }
    }

    public static boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();
        if (m + n != s3.length()) {
            return false;
        }

        boolean[] dp = new boolean[n + 1];          // dp[j] for the current i
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 && j == 0) {
                    dp[j] = true;
                } else if (i == 0) {
                    dp[j] = dp[j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
                } else if (j == 0) {
                    dp[j] = dp[j] && s1.charAt(i - 1) == s3.charAt(i - 1);
                } else {
                    dp[j] = (dp[j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
                            || (dp[j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
                }
            }
        }
        return dp[n];
    }
}
