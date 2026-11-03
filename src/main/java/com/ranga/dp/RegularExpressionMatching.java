package com.ranga.dp;

/**
 * Given an input string {@code s} and a pattern {@code p}, implement regular expression matching with support for {@code .} (matches any single character) and {@code *} (matches zero or more of the preceding element). The matching must cover the entire input string.
 *
 * <p><b>Algorithm Strategy (DP over the Prefixes of the String and the Pattern):</b>
 * <ol>
 *   <li>Let {@code dp[i][j]} be {@code true} when the first {@code i} characters of {@code s} are matched by the first {@code j} characters of {@code p}. {@code dp[0][0]} is true.</li>
 *   <li>Empty string against a pattern: {@code dp[0][j]} is true when {@code p[j-1]} is {@code *} and the pattern without the pair {@code x*} matches, i.e. {@code dp[0][j - 2]}.</li>
 *   <li>If {@code p[j-1]} is a normal character or {@code .}: {@code dp[i][j] = dp[i-1][j-1]} when it matches {@code s[i-1]}.</li>
 *   <li>If {@code p[j-1]} is {@code *} (it repeats the element {@code p[j-2]}): either use it zero times, {@code dp[i][j-2]}, or use it once more when {@code p[j-2]} matches {@code s[i-1]}, {@code dp[i-1][j]}. The answer is {@code dp[m][n]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code s = "aa"} and {@code p = "a*"}: {@code a*} matches {@code a} and then one more {@code a}, so {@code dp[2][2]} is true.</li>
 *   <li>Result: {@code true}. {@code "mississippi"} does not match {@code "mis*is*p*."}: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n)} — Every pair of prefixes is examined once.</li>
 *   <li><b>Space Complexity:</b> {@code O(m * n)} — The table.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RegularExpressionMatching {

    public static void main(String[] args) {
        String[][] inputs = {{"aa", "a"}, {"aa", "a*"}, {"ab", ".*"}, {"mississippi", "mis*is*p*."}, {"aab", "c*a*b"}, {"", "a*"}, {"abc", "abc"}};
        for (String[] input : inputs) {
            System.out.println("Input  : s = \"" + input[0] + "\", p = \"" + input[1] + "\"");
            System.out.println("Output : " + isMatch(input[0], input[1]));
            System.out.println();
        }
    }

    public static boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int j = 2; j <= n; j++) {
            dp[0][j] = p.charAt(j - 1) == '*' && dp[0][j - 2];
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char pc = p.charAt(j - 1);
                if (pc == '*') {
                    boolean zeroTimes = dp[i][j - 2];
                    boolean moreTimes = matches(s.charAt(i - 1), p.charAt(j - 2)) && dp[i - 1][j];
                    dp[i][j] = zeroTimes || moreTimes;
                } else {
                    dp[i][j] = matches(s.charAt(i - 1), pc) && dp[i - 1][j - 1];
                }
            }
        }
        return dp[m][n];
    }

    private static boolean matches(char c, char pattern) {
        return pattern == '.' || pattern == c;
    }
}
