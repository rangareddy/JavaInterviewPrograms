package com.ranga.dp;

/**
 * Given an input string {@code s} and a pattern {@code p}, implement wildcard matching with support for {@code ?} (matches any single character) and {@code *} (matches any sequence of characters, including the empty one). The match must cover the entire string.
 *
 * <p><b>Algorithm Strategy (Greedy with Backtracking on the Last Star):</b>
 * <ol>
 *   <li>Walk {@code s} with index {@code i} and {@code p} with index {@code j}. Remember the position of the last {@code *} ({@code star}) and the position in {@code s} where it started matching ({@code mark}).</li>
 *   <li>If {@code p[j]} is {@code ?} or equals {@code s[i]}, advance both.</li>
 *   <li>If {@code p[j]} is {@code *}, record {@code star = j}, {@code mark = i} and advance only {@code j} (the star initially matches nothing).</li>
 *   <li>On a mismatch with a recorded star, retry: set {@code j = star + 1}, {@code mark++}, {@code i = mark} (the star swallows one more character). Without a star, return {@code false}. After {@code s} ends, skip trailing stars in {@code p}; the match succeeds if {@code p} is exhausted.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code s = "aa"} and {@code p = "a"}: after matching {@code a} the string still has a character but the pattern is over and there is no star.</li>
 *   <li>Result: {@code false}. For {@code "adceb"} and {@code "*a*b"} the stars absorb {@code dce}, giving {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n) worst case} — Usually close to linear; the backtracking is bounded by the string length per star.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few indexes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class WildcardMatching {

    public static void main(String[] args) {
        String[][] inputs = {{"aa", "a"}, {"aa", "*"}, {"cb", "?a"}, {"adceb", "*a*b"}, {"acdcb", "a*c?b"}, {"", "***"}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", p = \"" + in[1] + "\"");
            System.out.println("Output : " + isMatch(in[0], in[1]));
            System.out.println();
        }
    }

    public static boolean isMatch(String s, String p) {
        int i = 0;
        int j = 0;
        int star = -1;
        int mark = 0;
        while (i < s.length()) {
            if (j < p.length() && (p.charAt(j) == '?' || p.charAt(j) == s.charAt(i))) {
                i++;
                j++;
            } else if (j < p.length() && p.charAt(j) == '*') {
                star = j++;
                mark = i;
            } else if (star != -1) {
                j = star + 1;
                i = ++mark;
            } else {
                return false;
            }
        }
        while (j < p.length() && p.charAt(j) == '*') {
            j++;
        }
        return j == p.length();
    }
}
