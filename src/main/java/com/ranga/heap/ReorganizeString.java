package com.ranga.heap;

import java.util.PriorityQueue;

/**
 * Rearrange the characters of a string {@code s} so that no two adjacent characters are the same. Return any valid rearrangement, or an empty string if it is not possible.
 *
 * <p><b>Algorithm Strategy (Max-Heap by Frequency):</b>
 * <ol>
 *   <li>A valid arrangement exists only if no character occurs more than {@code (n + 1) / 2} times; otherwise two copies must touch.</li>
 *   <li>Count the frequency of each character and put {@code (count, char)} pairs in a max-heap ordered by count.</li>
 *   <li>Repeatedly take the two most frequent characters, append both to the result, decrement their counts and push back those with a count above 0. The two characters are different, so no two equal characters become adjacent.</li>
 *   <li>If one character is left at the end, append it (its count is 1 when a valid arrangement exists). Using the most frequent characters first prevents a large group from being left for the end.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "aab"}: the heap holds {@code (a, 2)} and {@code (b, 1)}. Take {@code a}, {@code b} giving {@code "ab"}, push {@code (a, 1)}, then append {@code a}.</li>
 *   <li>Result: {@code "aba"}. For {@code "aaab"} the count of {@code a} is 3 and {@code (4 + 1) / 2 = 2}, so the answer is {@code ""}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log k)} — {@code k} is the number of distinct characters (at most 26 for lowercase letters), so effectively {@code O(n)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(k)} — The heap.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReorganizeString {

    public static void main(String[] args) {
        String[] inputs = {"aab", "aaab", "vvvlo", "a", "aabbcc"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + reorganizeString(s) + "\"");
            System.out.println();
        }
    }

    public static String reorganizeString(String s) {
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            counts[c - 'a']++;
        }
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        for (int i = 0; i < 26; i++) {
            if (counts[i] > (s.length() + 1) / 2) {
                return "";
            }
            if (counts[i] > 0) {
                heap.add(new int[]{i, counts[i]});
            }
        }
        StringBuilder sb = new StringBuilder();
        while (heap.size() >= 2) {
            int[] first = heap.poll();
            int[] second = heap.poll();
            sb.append((char) ('a' + first[0])).append((char) ('a' + second[0]));
            if (--first[1] > 0) {
                heap.add(first);
            }
            if (--second[1] > 0) {
                heap.add(second);
            }
        }
        if (!heap.isEmpty()) {
            sb.append((char) ('a' + heap.poll()[0]));
        }
        return sb.toString();
    }
}
