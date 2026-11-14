package com.ranga.string;

/**
 * Given a string {@code s}, find the first character that appears exactly once and return its index, or {@code -1} if there is none.
 *
 * <p><b>Algorithm Strategy (Two Passes with a Frequency Array):</b>
 * <ol>
 *   <li>Count how many times each character occurs. For lowercase letters an {@code int[26]} is enough; a {@code Map<Character, Integer>} is the general version.</li>
 *   <li>Scan the string again from the left.</li>
 *   <li>The first character whose count is exactly 1 is the answer; return its index.</li>
 *   <li>If the scan finishes without finding one, return {@code -1}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "leetcode"}: counts are {@code l=1, e=3, t=1, c=1, o=1, d=1}; the first character with count 1 is {@code l} at index 0.</li>
 *   <li>For {@code "aabb"} every count is 2, so the result is {@code -1}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Two passes over the string.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — The count array has a fixed size (26 letters).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FirstNonRepeatingCharacter {

    public static void main(String[] args) {
        String[] inputs = {"leetcode", "loveleetcode", "aabb", "z", ""};
        for (String s : inputs) {
            int index = firstUniqueChar(s);
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + index + (index == -1 ? "" : " ('" + s.charAt(index) + "')"));
            System.out.println();
        }
    }

    public static int firstUniqueChar(String s) {
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < s.length(); i++) {
            if (counts[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }
}
