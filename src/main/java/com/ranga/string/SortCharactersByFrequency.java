package com.ranga.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a string {@code s}, sort its characters in decreasing order of frequency and return the sorted string. Characters with the same frequency may appear in any order.
 *
 * <p><b>Algorithm Strategy (Count then Bucket by Frequency):</b>
 * <ol>
 *   <li>Count every character in a {@code HashMap} (or a 128-entry array).</li>
 *   <li>Create buckets indexed by frequency from 1 to {@code n}; drop each character into the bucket of its count.</li>
 *   <li>Walk the buckets from the highest frequency down.</li>
 *   <li>Append each character repeated by its frequency.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code tree}: {@code e} appears twice, {@code t} and {@code r} once.</li>
 *   <li>Result: {@code eert} (or {@code eetr}).</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Counting plus a bucket walk bounded by {@code n}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The map, the buckets and the result.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SortCharactersByFrequency {

    public static void main(String[] args) {
        String[] inputs = {"tree", "cccaaa", "Aabb", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + frequencySort(s) + "\"");
            System.out.println();
        }
    }

    public static String frequencySort(String s) {
        if (s == null || s.length() < 2) {
            return s == null ? "" : s;
        }
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : s.toCharArray()) {
            counts.merge(c, 1, Integer::sum);
        }
        List<List<Character>> buckets = new ArrayList<>();
        for (int i = 0; i <= s.length(); i++) {
            buckets.add(new ArrayList<>());
        }
        for (Map.Entry<Character, Integer> e : counts.entrySet()) {
            buckets.get(e.getValue()).add(e.getKey());
        }
        StringBuilder sb = new StringBuilder();
        for (int freq = s.length(); freq > 0; freq--) {
            for (char c : buckets.get(freq)) {
                for (int k = 0; k < freq; k++) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }
}
