package com.ranga.string;

import java.util.Arrays;

/**
 * Write a function that returns the longest common prefix of an array of strings, or an empty string if there is none.
 *
 * <p><b>Algorithm Strategy (Vertical Scanning):</b>
 * <ol>
 *   <li>If the array is empty there is no prefix. Otherwise compare the strings column by column.</li>
 *   <li>For every index {@code i} of the first string, take its character {@code c}.</li>
 *   <li>If any other string is shorter than {@code i + 1} or has a different character at index {@code i}, stop: the answer is the first string up to (not including) {@code i}.</li>
 *   <li>If the whole first string matches in every other string, the first string itself is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code ["flower", "flow", "flight"]}: column 0 is {@code f} everywhere, column 1 is {@code l} everywhere, column 2 is {@code o}, {@code o}, {@code i} - mismatch, so the prefix is {@code "fl"}.</li>
 *   <li>For {@code ["dog", "racecar", "car"]} column 0 already differs: {@code ""}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(S)} — {@code S} is the total number of characters of all strings, in the worst case every character is compared once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only the answer is a substring of the first string.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestCommonPrefix {

    public static void main(String[] args) {
        String[][] inputs = {{"flower", "flow", "flight"}, {"dog", "racecar", "car"}, {"interview", "internet", "internal"}, {"single"}, {"", "abc"}, {}};
        for (String[] words : inputs) {
            System.out.println("Input  : strs = " + Arrays.toString(words));
            System.out.println("Output : \"" + longestCommonPrefix(words) + "\"");
            System.out.println();
        }
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        String first = strs[0];
        for (int i = 0; i < first.length(); i++) {
            char c = first.charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[j].charAt(i) != c) {
                    return first.substring(0, i);
                }
            }
        }
        return first;
    }
}
