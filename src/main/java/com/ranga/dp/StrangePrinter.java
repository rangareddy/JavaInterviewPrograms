package com.ranga.dp;

/**
 * There is a strange printer with two rules: it can only print a sequence of the same character each time, and at each turn it can print new characters starting from and ending at any place, covering the original characters. Given a string {@code s}, return the minimum number of turns the printer needs to print it.
 *
 * <p><b>Algorithm Strategy (Interval DP: Share a Turn with a Matching Character):</b>
 * <ol>
 *   <li>Let {@code dp[i][j]} be the minimum turns for the substring {@code s[i..j]}.</li>
 *   <li>A safe start: print {@code s[i]} alone, then the rest: {@code dp[i][j] = dp[i + 1][j] + 1}.</li>
 *   <li>Better: if {@code s[k] == s[i]} for some {@code k} in {@code (i, j]}, the first print of {@code s[i]} can stretch to {@code k} for free, so {@code dp[i][j] = min(dp[i][j], dp[i + 1][k - 1] + dp[k][j])}.</li>
 *   <li>Fill by decreasing {@code i} and increasing {@code j}; the answer is {@code dp[0][n - 1]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code aaabbb}: {@code a} at index 0 matches indexes 1 and 2, which cost nothing extra, so the left block is one turn; the {@code bbb} block is another.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^3)} — {@code n^2} intervals, each scanning up to {@code n} split points.</li>
 *   <li><b>Space Complexity:</b> {@code O(n^2)} — The DP table.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class StrangePrinter {

    public static void main(String[] args) {
        String[] inputs = {"aaabbb", "aba", "abcabc", "a", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + strangePrinter(s));
            System.out.println();
        }
    }

    public static int strangePrinter(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = dp[i + 1][j] + 1;
                for (int k = i + 1; k <= j; k++) {
                    if (s.charAt(k) == s.charAt(i)) {
                        int inner = k == i + 1 ? 0 : dp[i + 1][k - 1];
                        dp[i][j] = Math.min(dp[i][j], inner + dp[k][j]);
                    }
                }
            }
        }
        return dp[0][n - 1];
    }
}
