package com.ranga.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Given an array of strings, group the anagrams together. The groups can be returned in any order.
 *
 * <p><b>Algorithm Strategy (Hash Map Keyed by the Sorted Letters):</b>
 * <ol>
 *   <li>Two words are anagrams exactly when their sorted letters are equal, so the sorted word is a canonical key for the group.</li>
 *   <li>For every word, build the key by sorting its characters.</li>
 *   <li>Use {@code Map<String, List<String>>}: append the word to the list stored under its key.</li>
 *   <li>Return all the lists of the map.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code ["eat", "tea", "tan", "ate", "nat", "bat"]}: the keys are {@code aet}, {@code aet}, {@code ant}, {@code aet}, {@code ant}, {@code abt}.</li>
 *   <li>Groups: {@code [eat, tea, ate]}, {@code [tan, nat]}, {@code [bat]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * k log k)} — {@code n} words, each of length at most {@code k}, sorted once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n * k)} — The map stores all words.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class GroupAnagrams {

    public static void main(String[] args) {
        String[][] inputs = {{"eat", "tea", "tan", "ate", "nat", "bat"}, {""}, {"a"}};
        for (String[] strs : inputs) {
            System.out.println("Input  : strs = " + Arrays.toString(strs));
            System.out.println("Output : " + groupAnagrams(strs));
            System.out.println();
        }
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null) {
            return new ArrayList<>();
        }

        Map<String, List<String>> groups = new LinkedHashMap<>();
        for (String word : strs) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            groups.computeIfAbsent(new String(chars), key -> new ArrayList<>()).add(word);
        }
        return new ArrayList<>(groups.values());
    }
}
