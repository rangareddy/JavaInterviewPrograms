package com.ranga.string;

/**
 * Given two strings {@code haystack} and {@code needle}, return the index of the first occurrence of {@code needle} in {@code haystack}, or {@code -1} if {@code needle} is not part of {@code haystack}. An empty {@code needle} matches at index 0.
 *
 * <p><b>Algorithm Strategy (Sliding Comparison):</b>
 * <ol>
 *   <li>If {@code needle} is empty return 0; if it is longer than {@code haystack} return -1.</li>
 *   <li>Try each start index {@code i} from 0 to {@code n - m}.</li>
 *   <li>Compare {@code needle} with {@code haystack[i .. i + m - 1]} character by character, stopping at the first mismatch.</li>
 *   <li>Return the first {@code i} that matches, or -1.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "sadbutsad"} and {@code "sad"}: index 0 matches immediately.</li>
 *   <li>For {@code "hello"} and {@code "ll"}: index 2 matches.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * m)} — Worst case compares {@code m} characters at each of {@code n} starts; KMP makes it O(n + m).</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only indexes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FirstOccurrenceInAString {

    public static void main(String[] args) {
        String[][] inputs = {{"hello", "ll"}, {"sadbutsad", "sad"}, {"leetcode", "leeto"}, {"abc", ""}};
        for (String[] in : inputs) {
            System.out.println("Input  : haystack = \"" + in[0] + "\", needle = \"" + in[1] + "\"");
            System.out.println("Output : " + strStr(in[0], in[1]));
            System.out.println();
        }
    }

    public static int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }
        int n = haystack.length();
        int m = needle.length();
        if (m == 0) {
            return 0;
        }
        for (int i = 0; i + m <= n; i++) {
            int j = 0;
            while (j < m && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }
            if (j == m) {
                return i;
            }
        }
        return -1;
    }
}
