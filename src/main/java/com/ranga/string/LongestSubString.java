package com.ranga.string;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class to find the longest substring without repeating characters.
 *
 * <p><b>Algorithm Strategy (Sliding Window with a Set):</b>
 * <ol>
 *   <li>Maintain a window {@code [left, right)} of the string that contains no repeated characters, and a
 *       {@code Set} holding exactly the characters inside the window.</li>
 *   <li>Look at the character {@code c = str.charAt(right)}. If it is not in the set, add it and grow the window
 *       by incrementing {@code right}; if the window is now longer than the best one, remember its start and length.</li>
 *   <li>If {@code c} is already in the set, remove {@code str.charAt(left)} from the set and increment
 *       {@code left} (shrinking the window from the left); repeat until the duplicate is gone.</li>
 *   <li>Continue until {@code right} reaches the end of the string. The remembered start and length give the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b> for {@code "abcadbc"}:
 * <ul>
 *   <li>Window grows: {@code a, ab, abc} (length 3); the next {@code a} is a duplicate, so {@code a} is dropped from the left.</li>
 *   <li>Window {@code bca} then grows with {@code d}: {@code bcad} (length 4, new best); the next {@code b} is a
 *       duplicate, so the left side shrinks to {@code cad}, then {@code cadb} and so on.</li>
 *   <li>Longest substring: {@code "bcad"} with length 4.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index enters and leaves the window at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(min(n, k))} — The set holds at most the distinct characters of the string ({@code k} = alphabet size).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestSubString {

    public static void main(String[] args) {
        String[] words = {"abcadbc", "bbbb", "ABDEFGABEF", "GEEKSFORGEEKS"};
        for (String str : words) {
            String longest = getLongestSubString(str);
            System.out.println("Input  : \"" + str + "\"");
            System.out.println("Output : \"" + longest + "\" (length " + longest.length() + ")");
            System.out.println();
        }
    }

    /**
     * Finds the longest substring without repeating characters.
     *
     * @param str the input string
     * @return the first longest substring without repeating characters, or an empty string for null/empty input
     */
    private static String getLongestSubString(String str) {
        // Guard Clause: nothing to scan in a null or empty string
        if (str == null || str.isEmpty()) {
            return "";
        }

        int left = 0, right = 0;
        int bestStart = 0, bestLength = 0;
        Set<Character> window = new HashSet<>();

        while (right < str.length()) {
            char c = str.charAt(right);
            if (!window.contains(c)) {
                // Grow the window to the right
                window.add(c);
                right++;
                if (window.size() > bestLength) {
                    bestStart = left;
                    bestLength = window.size();
                }
            } else {
                // Shrink the window from the left until the duplicate is removed
                window.remove(str.charAt(left));
                left++;
            }
        }
        return str.substring(bestStart, bestStart + bestLength);
    }
}
