package com.ranga.blind75.dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Given a string {@code s} and a dictionary of words, return {@code true} if {@code s} can be split into a space-separated sequence of one or more dictionary words (a word may be reused).
 *
 * <p><b>Algorithm Strategy (Dynamic Programming over Prefixes):</b>
 * <ol>
 *   <li>Let {@code dp[i]} be {@code true} when the first {@code i} characters of {@code s} can be segmented. {@code dp[0] = true} (the empty prefix).</li>
 *   <li>For every end position {@code i} from 1 to {@code n}, try every split point {@code j < i}: if {@code dp[j]} is true and the substring {@code s[j..i)} is in the dictionary, then {@code dp[i]} is true.</li>
 *   <li>Store the dictionary in a {@code HashSet} so each check is {@code O(L)} (hashing the substring).</li>
 *   <li>The answer is {@code dp[n]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "leetcode"} with {@code ["leet", "code"]}: {@code dp[4]} is true ("leet") and {@code dp[8]} is true because {@code dp[4]} is true and "code" is a word.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2 * L)} — Pairs of positions times the cost of building and hashing a substring of length up to {@code L}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The {@code dp} array (plus the dictionary).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class WordBreak {

    public static void main(String[] args) {
        String[] strings = {"leetcode", "applepenapple", "catsandog"};
        List<List<String>> dictionaries = Arrays.asList(
                Arrays.asList("leet", "code"),
                Arrays.asList("apple", "pen"),
                Arrays.asList("cats", "dog", "sand", "and", "cat"));
        for (int i = 0; i < strings.length; i++) {
            System.out.println("Input  : s = \"" + strings[i] + "\", wordDict = " + dictionaries.get(i));
            System.out.println("Output : " + wordBreak(strings[i], dictionaries.get(i)));
            System.out.println();
        }
    }

    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> words = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && words.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
