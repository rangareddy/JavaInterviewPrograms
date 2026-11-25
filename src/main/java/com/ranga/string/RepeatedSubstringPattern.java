package com.ranga.string;

/**
 * Given a string {@code s}, check whether it can be constructed by taking a substring of it and appending multiple copies of that substring together.
 *
 * <p><b>Algorithm Strategy (Find s Inside s + s Without the Trivial Matches):</b>
 * <ol>
 *   <li>If {@code s} is a repetition of a block, then rotating {@code s} by the block length gives {@code s} again.</li>
 *   <li>Every rotation of {@code s} appears inside {@code s + s}.</li>
 *   <li>Search for {@code s} in {@code (s + s)} starting at index 1. The trivial match is at index 0, and the copy at index {@code n} is the other trivial one.</li>
 *   <li>{@code s} is repeated exactly when the first match is before index {@code n}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code abab}: {@code abababab} contains {@code abab} at index 2, which is less than 4.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — {@code indexOf} is linear with a good implementation (worst case O(n^2) for the Java default).</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The doubled string.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RepeatedSubstringPattern {

    public static void main(String[] args) {
        String[] inputs = {"abab", "aba", "abcabcabcabc", "a", "aaaa"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + repeatedSubstringPattern(s));
            System.out.println();
        }
    }

    public static boolean repeatedSubstringPattern(String s) {
        if (s == null || s.length() < 2) {
            return false;
        }
        return (s + s).indexOf(s, 1) < s.length();
    }
}
