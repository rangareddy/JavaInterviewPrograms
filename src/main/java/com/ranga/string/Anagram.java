package com.ranga.string;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to check whether two strings are anagrams of each other.
 *
 * <p>An anagram is a word or phrase formed by rearranging the letters of another word or phrase, using all the
 * original letters exactly once (for example {@code "listen"} and {@code "silent"}).
 *
 * <p><b>Algorithm Strategy (Character Frequency Count):</b>
 * <ol>
 *   <li>If either string is {@code null}, or the lengths differ, the strings cannot be anagrams, return {@code false}.</li>
 *   <li>Walk through the first string and count how many times each character occurs, using a {@code Map<Character, Integer>}.</li>
 *   <li>Walk through the second string and decrement the count of every character. If a character is missing or its
 *       count is already {@code 0}, the second string has a character the first one does not have, return {@code false}.</li>
 *   <li>Because both strings have the same length and no count went negative, every count is back to {@code 0},
 *       so the strings are anagrams.</li>
 * </ol>
 *
 * <p><b>Why not XOR all the characters?</b> XOR-ing every character of both strings gives {@code 0} for anagrams, but
 * it also gives {@code 0} for strings such as {@code "aa"} and {@code "bb"} (any two characters repeated an even
 * number of times cancel out), so the XOR check reports false positives and must not be used.
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "listen"} and {@code "silent"} the counts after the first pass are {@code l=1, i=1, s=1, t=1, e=1, n=1}; decrementing for every character of {@code "silent"} brings every count to {@code 0}: anagram.</li>
 *   <li>For {@code "aa"} and {@code "bb"} the first {@code b} is not in the map, so the check fails immediately: not an anagram (XOR would have wrongly said yes).</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over each string with {@code O(1)} average map operations.</li>
 *   <li><b>Space Complexity:</b> {@code O(k)} — The map holds at most the distinct characters ({@code k}) of the string.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class Anagram {

    public static void main(String[] args) {
        String[][] input = {{"abc", "bac"}, {"bac", "bacd"}, {"abc", null}, {"listen", "silent"}, {"aa", "bb"}};

        for (String[] inputs : input) {
            String str1 = inputs[0];
            String str2 = inputs[1];
            System.out.println("Input  : " + str1 + ", " + str2);
            System.out.println("Output : " + (checkAnagram(str1, str2) ? "Anagram" : "Not an Anagram"));
            System.out.println();
        }
    }

    /**
     * Checks whether the two strings are anagrams.
     *
     * @param str1 the first string
     * @param str2 the second string
     * @return {@code true} if both strings contain exactly the same characters with the same counts
     */
    private static boolean checkAnagram(String str1, String str2) {
        // Guard Clause: null inputs and different lengths can never be anagrams
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }

        Map<Character, Integer> counts = new HashMap<>();
        for (char c : str1.toCharArray()) {
            counts.merge(c, 1, Integer::sum);
        }

        for (char c : str2.toCharArray()) {
            Integer count = counts.get(c);
            if (count == null || count == 0) {
                return false;
            }
            counts.put(c, count - 1);
        }
        return true;
    }
}
