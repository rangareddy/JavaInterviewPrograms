package com.ranga.string;

/**
 * Given two strings {@code s} and {@code t}, return {@code true} if {@code s} is a subsequence of {@code t}, that is, {@code s} can be obtained from {@code t} by deleting some characters (possibly none) without changing the order of the rest.
 *
 * <p><b>Algorithm Strategy (Two Pointers):</b>
 * <ol>
 *   <li>Keep one pointer {@code i} into {@code s} and scan {@code t} with another pointer.</li>
 *   <li>When the current character of {@code t} equals {@code s[i]}, advance {@code i}.</li>
 *   <li>If {@code i} reaches the end of {@code s}, every character was found in order.</li>
 *   <li>An empty {@code s} is a subsequence of any string.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code s = "abc"} and {@code t = "ahbgdc"}: a, b, c are found at positions 0, 2 and 5 in order.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(|t|)} — One pass over {@code t}.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two indices.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class IsSubsequence {

    public static void main(String[] args) {
        String[][] inputs = {{"abc", "ahbgdc"}, {"axc", "ahbgdc"}, {"", "abc"}, {"a", ""}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", t = \"" + in[1] + "\"");
            System.out.println("Output : " + isSubsequence(in[0], in[1]));
            System.out.println();
        }
    }

    public static boolean isSubsequence(String s, String t) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        if (t == null) {
            return false;
        }
        int i = 0;
        for (int j = 0; j < t.length() && i < s.length(); j++) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
        }
        return i == s.length();
    }
}
