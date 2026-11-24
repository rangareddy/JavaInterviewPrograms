package com.ranga.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Given an array of strings {@code words} and an integer {@code k}, return the {@code k} most frequent strings, ordered from the most frequent to the least frequent. Words with the same frequency are ordered lexicographically (alphabetically).
 *
 * <p><b>Algorithm Strategy (Count then Bounded Min-Heap):</b>
 * <ol>
 *   <li>Count the frequency of each word with a {@code HashMap}.</li>
 *   <li>Keep a min-heap of at most {@code k} entries, ordered by frequency ascending and then by word descending, so the heap's top is the weakest candidate.</li>
 *   <li>Offer each entry; if the heap grows beyond {@code k}, remove the top.</li>
 *   <li>Pop the heap into the result and reverse it to get the best first.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code ["i","love","leetcode","i","love","coding"]} and {@code k = 2}: {@code i} and {@code love} both appear twice, and {@code i} sorts first.</li>
 *   <li>Result: {@code ["i", "love"]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log k)} — Counting is O(n); every distinct word costs at most a log k heap operation.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The frequency map and the heap.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TopKFrequentWords {

    public static void main(String[] args) {
        String[][] words = {
            {"i", "love", "leetcode", "i", "love", "coding"},
            {"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"},
            {"a"}
        };
        int[] ks = {2, 4, 1};
        for (int i = 0; i < words.length; i++) {
            System.out.println("Input  : words = " + Arrays.toString(words[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + topKFrequent(words[i], ks[i]));
            System.out.println();
        }
    }

    public static List<String> topKFrequent(String[] words, int k) {
        List<String> result = new ArrayList<>();
        if (words == null || words.length == 0 || k <= 0) {
            return result;
        }
        Map<String, Integer> counts = new HashMap<>();
        for (String word : words) {
            counts.merge(word, 1, Integer::sum);
        }
        PriorityQueue<Map.Entry<String, Integer>> heap = new PriorityQueue<>((a, b) ->
                a.getValue().equals(b.getValue()) ? b.getKey().compareTo(a.getKey()) : a.getValue() - b.getValue());
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            heap.offer(entry);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        while (!heap.isEmpty()) {
            result.add(heap.poll().getKey());
        }
        Collections.reverse(result);
        return result;
    }
}
