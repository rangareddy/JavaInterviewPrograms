package com.ranga.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given two strings {@code s} and {@code p}, return the start indices of all the substrings of {@code s} that are anagrams of {@code p}, in any order.
 *
 * <p><b>Algorithm Strategy (Fixed-Size Sliding Window with Character Counts):</b>
 * <ol>
 *   <li>An anagram of {@code p} is a substring of {@code s} of the same length with the same character counts, so slide a window of length {@code p.length()} across {@code s}.</li>
 *   <li>Keep {@code need[c]} (the counts of {@code p}) and {@code window[c]} (the counts inside the current window) as arrays of 26.</li>
 *   <li>When the window slides one step, add the new right character and remove the character that fell off the left edge. Both are constant-time updates.</li>
 *   <li>Whenever the two arrays are equal the window start is an answer. Comparing two arrays of 26 is a constant cost, so the total is linear in the length of {@code s}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code s = "cbaebabacd"}, {@code p = "abc"}: window {@code cba} at 0 matches; {@code bac} at 6 matches.</li>
 *   <li>Result: {@code [0, 6]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over {@code s}; each array comparison is over 26 letters.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two arrays of 26 counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FindAllAnagramsInAString {

    public static void main(String[] args) {
        String[][] inputs = {{"cbaebabacd", "abc"}, {"abab", "ab"}, {"a", "ab"}, {"aaaaa", "aa"}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", p = \"" + in[1] + "\"");
            System.out.println("Output : " + findAnagrams(in[0], in[1]));
            System.out.println();
        }
    }

    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        int n = s.length();
        int m = p.length();
        if (m > n) {
            return result;
        }
        int[] need = new int[26];
        int[] window = new int[26];
        for (int i = 0; i < m; i++) {
            need[p.charAt(i) - 'a']++;
            window[s.charAt(i) - 'a']++;
        }
        if (Arrays.equals(need, window)) {
            result.add(0);
        }
        for (int right = m; right < n; right++) {
            window[s.charAt(right) - 'a']++;
            window[s.charAt(right - m) - 'a']--;
            if (Arrays.equals(need, window)) {
                result.add(right - m + 1);
            }
        }
        return result;
    }
}
