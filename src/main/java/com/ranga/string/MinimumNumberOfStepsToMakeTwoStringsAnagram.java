package com.ranga.string;

/**
 * Given two strings {@code s} and {@code t} of the same length, in one step you may replace any character of {@code t} with another character. Return the minimum number of steps to make {@code t} an anagram of {@code s}.
 *
 * <p><b>Algorithm Strategy (Count the Surplus Letters):</b>
 * <ol>
 *   <li>Count the letters of {@code s} in a 26-entry array.</li>
 *   <li>For each letter of {@code t}, decrement its count.</li>
 *   <li>A letter of {@code t} that has no remaining count in {@code s} is surplus: it must be replaced.</li>
 *   <li>The answer is the number of positive counts left (or equivalently the number of surplus letters in {@code t}).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code s = "bab"} and {@code t = "aba"}: {@code s} has two {@code b} and one {@code a}, {@code t} has two {@code a} and one {@code b}, so one {@code a} in {@code t} must become {@code b}.</li>
 *   <li>Result: {@code 1}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over each string.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — 26 counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumNumberOfStepsToMakeTwoStringsAnagram {

    public static void main(String[] args) {
        String[][] inputs = {{"bab", "aba"}, {"leetcode", "practice"}, {"anagram", "mangaar"}, {"xxyyzz", "xxyyzz"}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", t = \"" + in[1] + "\"");
            System.out.println("Output : " + minSteps(in[0], in[1]));
            System.out.println();
        }
    }

    public static int minSteps(String s, String t) {
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            counts[c - 'a']++;
        }
        int steps = 0;
        for (char c : t.toCharArray()) {
            if (counts[c - 'a'] > 0) {
                counts[c - 'a']--;
            } else {
                steps++;
            }
        }
        return steps;
    }
}
