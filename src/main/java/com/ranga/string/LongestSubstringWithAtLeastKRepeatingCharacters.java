package com.ranga.string;

/**
 * Given a string {@code s} and an integer {@code k}, return the length of the longest substring in which every character appears at least {@code k} times.
 *
 * <p><b>Algorithm Strategy (Split on Characters That Occur Fewer Than k Times):</b>
 * <ol>
 *   <li>Count the frequency of every character in the current range.</li>
 *   <li>A character with a frequency below {@code k} can never be part of a valid substring, so every valid substring lies between such characters.</li>
 *   <li>Split the range on those characters and solve each part recursively.</li>
 *   <li>If no character is rare, the whole range is valid; return its length.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code ababbc} with {@code k = 2}: {@code c} occurs once, so split on it; the part {@code ababb} has every character at least twice.</li>
 *   <li>Result: {@code 5}. For {@code aaabb} and {@code k = 3} the {@code b}s are rare, the part {@code aaa} remains, so the answer is {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * 26)} — The recursion depth is at most 26 because each level removes one distinct character.</li>
 *   <li><b>Space Complexity:</b> {@code O(26)} — Frequency arrays along the recursion.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestSubstringWithAtLeastKRepeatingCharacters {

    public static void main(String[] args) {
        String[] inputs = {"aaabb", "ababbc", "weitong", "a"};
        int[] ks = {3, 2, 2, 1};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : s = \"" + inputs[i] + "\", k = " + ks[i]);
            System.out.println("Output : " + longestSubstring(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static int longestSubstring(String s, int k) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        if (k <= 1) {
            return s.length();
        }
        return solve(s, 0, s.length(), k);
    }

    private static int solve(String s, int start, int end, int k) {
        if (end - start < k) {
            return 0;
        }
        int[] count = new int[26];
        for (int i = start; i < end; i++) {
            count[s.charAt(i) - 'a']++;
        }
        for (int i = start; i < end; i++) {
            if (count[s.charAt(i) - 'a'] < k) {
                int next = i + 1;
                while (next < end && count[s.charAt(next) - 'a'] < k) {
                    next++;
                }
                return Math.max(solve(s, start, i, k), solve(s, next, end, k));
            }
        }
        return end - start;
    }
}
