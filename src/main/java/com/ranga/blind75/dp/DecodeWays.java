package com.ranga.blind75.dp;

/**
 * A message of letters A-Z is encoded as numbers ('A' = 1, 'B' = 2, ..., 'Z' = 26). Given a string of digits, return the number of ways to decode it.
 *
 * <p><b>Algorithm Strategy (Dynamic Programming over Prefixes):</b>
 * <ol>
 *   <li>Let {@code dp[i]} be the number of ways to decode the first {@code i} digits; {@code dp[0] = 1} (the empty string).</li>
 *   <li>One digit: if {@code s[i - 1]} is not '0', it can be decoded alone, so add {@code dp[i - 1]}. A '0' cannot be decoded alone.</li>
 *   <li>Two digits: if the number formed by {@code s[i - 2]} and {@code s[i - 1]} is between 10 and 26, add {@code dp[i - 2]}.</li>
 *   <li>Only the last two values are needed, so they are kept in two variables. The answer is the last value (0 means the string cannot be decoded).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "226"}: {@code "2 2 6"} (BBF), {@code "22 6"} (VF) and {@code "2 26"} (BZ) are valid, so the result is {@code 3}.</li>
 *   <li>For {@code "06"}: a leading zero can neither stand alone nor pair with a previous digit, so the result is {@code 0}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — A single pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DecodeWays {

    public static void main(String[] args) {
        String[] inputs = {"12", "226", "06", "11106", "10"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + numDecodings(s));
            System.out.println();
        }
    }

    public static int numDecodings(String s) {
        if (s == null || s.isEmpty() || s.charAt(0) == '0') {
            return 0;
        }

        int prev2 = 1;      // dp[i - 2]
        int prev1 = 1;      // dp[i - 1]
        for (int i = 2; i <= s.length(); i++) {
            int current = 0;
            if (s.charAt(i - 1) != '0') {
                current += prev1;                       // decode the last digit alone
            }
            int twoDigits = Integer.parseInt(s.substring(i - 2, i));
            if (twoDigits >= 10 && twoDigits <= 26) {
                current += prev2;                       // decode the last two digits together
            }
            prev2 = prev1;
            prev1 = current;
        }
        return prev1;
    }
}
