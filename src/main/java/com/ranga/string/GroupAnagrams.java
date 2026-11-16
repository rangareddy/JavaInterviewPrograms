package com.ranga.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Given an array of strings, group the anagrams together. The groups can be returned in any order.
 *
 * <p><b>Algorithm Strategy (Hash Map Keyed by the Letter Counts):</b>
 * <ol>
 *   <li>Guard first: a {@code null} array gives an empty result and {@code null} entries are skipped.</li>
 *   <li>Two words are anagrams exactly when they have the same count of every letter, so the 26 counts are a canonical key for the group. Counting is {@code O(k)}, which avoids the {@code log k} factor of sorting the letters.</li>
 *   <li>For a word with only lowercase letters {@code a}-{@code z}, count the letters into an {@code int[26]} and use {@code Arrays.toString(count)} as the key.</li>
 *   <li>Any other word (capitals, digits, other alphabets) cannot be counted in 26 slots, so its key is its sorted characters with a {@code '#'} prefix. A word that has such characters never shares a key with a lowercase-only word, and all its anagrams get the same key.</li>
 *   <li>Use {@code Map<String, List<String>>}: append the word to the list stored under its key, then return all the lists.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code ["eat", "tea", "tan", "ate", "nat", "bat"]}: {@code eat}, {@code tea} and {@code ate} all count to {@code a=1, e=1, t=1}; {@code tan} and {@code nat} to {@code a=1, n=1, t=1}; {@code bat} to {@code a=1, b=1, t=1}.</li>
 *   <li>Groups: {@code [eat, tea, ate]}, {@code [tan, nat]}, {@code [bat]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * k)} — {@code n} words of length at most {@code k}, counted once (plus a constant 26 for building the key); words with other characters cost {@code O(k log k)} for the sort.</li>
 *   <li><b>Space Complexity:</b> {@code O(n * k)} — The map stores all words.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class GroupAnagrams {

    public static void main(String[] args) {
        String[][] inputs = {{"eat", "tea", "tan", "ate", "nat", "bat"}, {""}, {"a"}, {"Eat", "taE", "ate"}, null};
        for (String[] strs : inputs) {
            System.out.println("Input  : strs = " + Arrays.toString(strs));
            System.out.println("Output : " + groupAnagrams(strs));
            System.out.println();
        }
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        // Guard Clause: no input, no groups
        if (strs == null) {
            return new ArrayList<>();
        }

        Map<String, List<String>> groups = new LinkedHashMap<>();
        for (String word : strs) {
            if (word == null) {
                continue;
            }
            groups.computeIfAbsent(keyOf(word), key -> new ArrayList<>()).add(word);
        }
        return new ArrayList<>(groups.values());
    }

    /** The letter-count key for lowercase words, the prefixed sorted letters for any other word. */
    private static String keyOf(String word) {
        int[] count = new int[26];
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c < 'a' || c > 'z') {
                char[] chars = word.toCharArray();
                Arrays.sort(chars);
                return "#" + new String(chars);
            }
            count[c - 'a']++;
        }
        return Arrays.toString(count);
    }
}
