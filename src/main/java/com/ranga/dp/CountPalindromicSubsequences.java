package com.ranga.dp;

import java.util.Arrays;

/**
 * Given a string {@code s} of digits, return the number of palindromic subsequences of length 5 (the subsequence may contain leading zeros), modulo {@code 10^9 + 7}. A subsequence is obtained by deleting characters without changing the order of the rest.
 *
 * <p><b>Algorithm Strategy (Prefix Pairs and Suffix Pairs):</b>
 * <ol>
 *   <li>A palindrome of length 5 looks like {@code a b c b a}. Fix the middle index {@code i}.</li>
 *   <li>Maintain {@code pre[a][b]}: the number of subsequences {@code a b} (in that order) in {@code s[0..i-1]}, and {@code suf[b][a]}: the number of subsequences {@code b a} in {@code s[i+1..]}.</li>
 *   <li>For the middle position, the count is {@code sum over a, b of pre[a][b] * suf[b][a]}.</li>
 *   <li>Update {@code pre} as the index moves right (adding {@code s[i]} as the second digit for every first digit) and remove {@code s[i]} from {@code suf}. Sum over all middle positions.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 103301}: the palindromic subsequences of length 5 are {@code 10301}, {@code 10301} (a second time using the other 3)... in total 2.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * 100)} — 100 digit pairs per position.</li>
 *   <li><b>Space Complexity:</b> {@code O(100)} — Two 10 x 10 count tables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CountPalindromicSubsequences {

    public static void main(String[] args) {
        String[] inputs = {"103301", "0000000", "9999900000", "123"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + countPalindromes(s));
            System.out.println();
        }
    }

    private static final long MOD = 1_000_000_007L;

    public static int countPalindromes(String s) {
        if (s == null || s.length() < 5) {
            return 0;
        }
        int n = s.length();
        long[][] pre = new long[10][10];
        long[][] suf = new long[10][10];
        int[] preCount = new int[10];
        int[] sufCount = new int[10];
        for (int i = n - 1; i >= 0; i--) {
            int d = s.charAt(i) - '0';
            for (int x = 0; x < 10; x++) {
                suf[d][x] = (suf[d][x] + sufCount[x]) % MOD;
            }
            sufCount[d]++;
        }
        long total = 0;
        int[] seen = new int[10];
        for (int i = 0; i < n; i++) {
            int d = s.charAt(i) - '0';
            sufCount[d]--;
            for (int x = 0; x < 10; x++) {
                suf[d][x] = ((suf[d][x] - sufCount[x]) % MOD + MOD) % MOD;
            }
            for (int a = 0; a < 10; a++) {
                for (int b = 0; b < 10; b++) {
                    total = (total + pre[a][b] * suf[b][a]) % MOD;
                }
            }
            for (int x = 0; x < 10; x++) {
                pre[x][d] = (pre[x][d] + seen[x]) % MOD;
            }
            seen[d]++;
        }
        return (int) total;
    }
}
