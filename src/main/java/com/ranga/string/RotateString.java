package com.ranga.string;

/**
 * Given two strings {@code s} and {@code goal}, return {@code true} if {@code s} can become {@code goal} after some number of left shifts, where one shift moves the first character of {@code s} to the end.
 *
 * <p><b>Algorithm Strategy (Search in the Doubled String):</b>
 * <ol>
 *   <li>If the lengths differ, the answer is {@code false}.</li>
 *   <li>Every rotation of {@code s} is a substring of {@code s + s}.</li>
 *   <li>So {@code s} can become {@code goal} exactly when {@code (s + s).contains(goal)}.</li>
 *   <li>Both strings being empty is a valid rotation.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code abcde} and {@code cdeab}: {@code abcdeabcde} contains {@code cdeab} starting at index 2.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — With a linear-time substring search; {@code String.contains} is O(n * m) worst case.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The doubled string.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RotateString {

    public static void main(String[] args) {
        String[][] inputs = {{"abcde", "cdeab"}, {"abcde", "abced"}, {"", ""}, {"a", "ab"}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", goal = \"" + in[1] + "\"");
            System.out.println("Output : " + rotateString(in[0], in[1]));
            System.out.println();
        }
    }

    public static boolean rotateString(String s, String goal) {
        if (s == null || goal == null || s.length() != goal.length()) {
            return false;
        }
        return (s + s).contains(goal);
    }
}
