package com.ranga.string;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Utility class to find the length of the longest substring without repeating characters.
 *
 * <p><b>Algorithm Strategy (Sliding Window with a Set):</b>
 * <ol>
 *   <li>Keep a window {@code [i, j)} and a set of the characters inside it.</li>
 *   <li>If the character at {@code j} is not in the set, add it, grow the window and record it when it is the longest so far.</li>
 *   <li>Otherwise remove the character at {@code i} and shrink the window from the left until the duplicate is gone.</li>
 * </ol>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index enters and leaves the window at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(min(n, k))} — The set holds at most the distinct characters of the string (k = alphabet size).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LongestSubString {
    public static void main(String[] args) {

        String[] words = {"abcadbc", "bbbb", "ABDEFGABEF", "GEEKSFORGEEKS"};
        for (String str : words) {
            int len = getLongestSubString(str);
            System.out.println(str + ", " + len);
        }
    }

    /**
     * Finds the length of the longest substring without repeating characters and prints that substring.
     *
     * @param str the input string
     * @return the length of the longest such substring
     */
    private static int getLongestSubString(String str) {
        int max = 0;
        int i = 0, j = 0;
        Set<Character> set = new LinkedHashSet<>();
        String subStr = "";
        while (j < str.length()) {
            if (!set.contains(str.charAt(j))) {
                set.add(str.charAt(j));
                if (set.size() > max) {
                    subStr = set.toString();
                    max = set.size();
                }
                j++;
            } else {
                set.remove(str.charAt(i));
                i++;
            }
        }
        System.out.println(subStr);
        return max;
    }
}
