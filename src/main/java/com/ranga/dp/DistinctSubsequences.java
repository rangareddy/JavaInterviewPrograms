package com.ranga.dp;

/**
 * Given two strings {@code s} and {@code t}, return the number of distinct subsequences of {@code s} that equal {@code t} (a subsequence keeps the relative order of characters but may skip characters).
 *
 * <p><b>Algorithm Strategy (DP over the Prefixes of t):</b>
 * <ol>
 *   <li>Let {@code dp[j]} be the number of ways to form the first {@code j} characters of {@code t} from the characters of {@code s} processed so far. {@code dp[0] = 1}: the empty string can be formed in exactly one way.</li>
 *   <li>Process the characters of {@code s} one by one. For a character {@code c = s[i]}, every position {@code j} with {@code t[j - 1] == c} gains {@code dp[j - 1]} ways: the new character can serve as the last character of {@code t[0..j)}.</li>
 *   <li>Update {@code j} from {@code t.length()} down to 1, so that {@code dp[j - 1]} still holds the value from before this character (each character of {@code s} is used at most once).</li>
 *   <li>The answer is {@code dp[t.length()]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code s = "rabbbit"} and {@code t = "rabbit"} there are 3 ways, depending on which of the three {@code b} characters of {@code s} is skipped.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(|s| * |t|)} — Every character of {@code s} updates all positions of {@code t}.</li>
 *   <li><b>Space Complexity:</b> {@code O(|t|)} — One array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DistinctSubsequences {

    public static void main(String[] args) {
        String[][] inputs = {{"rabbbit", "rabbit"}, {"babgbag", "bag"}, {"abc", "abcd"}, {"aaa", "aa"}, {"", ""}};
        for (String[] input : inputs) {
            System.out.println("Input  : s = \"" + input[0] + "\", t = \"" + input[1] + "\"");
            System.out.println("Output : " + numDistinct(input[0], input[1]));
            System.out.println();
        }
    }

    public static int numDistinct(String s, String t) {
        int n = t.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            for (int j = n; j >= 1; j--) {
                if (t.charAt(j - 1) == c) {
                    dp[j] += dp[j - 1];
                }
            }
        }
        return dp[n];
    }
}
