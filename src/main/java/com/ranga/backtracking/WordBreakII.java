package com.ranga.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Given a string {@code s} and a dictionary of strings {@code wordDict}, add spaces in {@code s} to construct all possible sentences in which every word is in the dictionary. A word may be reused. Return the sentences in any order.
 *
 * <p><b>Algorithm Strategy (Memoised Recursion on the Start Index):</b>
 * <ol>
 *   <li>Define {@code sentences(start)} as all ways to break the suffix {@code s[start..]}.</li>
 *   <li>Try every end index so that {@code s[start..end)} is in the dictionary; for each, combine the word with every sentence of the remaining suffix (or just the word when the end reaches the string end).</li>
 *   <li>Cache the result for each {@code start}, because the same suffix is reached through many different prefixes.</li>
 *   <li>Return {@code sentences(0)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code s = "catsanddog"} and the dictionary {@code cat, cats, and, sand, dog}: {@code cats and dog} and {@code cat sand dog} both work.</li>
 *   <li>Result: two sentences.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2 + number of sentences * n)} — Memoisation bounds the work per start; the output itself can be exponential.</li>
 *   <li><b>Space Complexity:</b> {@code O(n * number of sentences)} — The memo of sentences per index.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class WordBreakII {

    public static void main(String[] args) {
        String[] inputs = {"catsanddog", "pineapplepenapple", "catsandog"};
        String[][] dictionaries = {{"cat", "cats", "and", "sand", "dog"}, {"apple", "pen", "applepen", "pine", "pineapple"}, {"cats", "dog", "sand", "and", "cat"}};
        for (int i = 0; i < inputs.length; i++) {
            List<String> result = wordBreak(inputs[i], Arrays.asList(dictionaries[i]));
            java.util.Collections.sort(result, java.util.Collections.reverseOrder());
            System.out.println("Input  : s = \"" + inputs[i] + "\", wordDict = " + Arrays.toString(dictionaries[i]));
            System.out.println("Output : " + result);
            System.out.println();
        }
    }

    public static List<String> wordBreak(String s, List<String> wordDict) {
        if (s == null || wordDict == null) {
            return new ArrayList<>();
        }
        return break_(s, new HashSet<>(wordDict), 0, new HashMap<>());
    }

    private static List<String> break_(String s, Set<String> words, int start, Map<Integer, List<String>> memo) {
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        List<String> sentences = new ArrayList<>();
        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            if (!words.contains(word)) {
                continue;
            }
            if (end == s.length()) {
                sentences.add(word);
            } else {
                for (String rest : break_(s, words, end, memo)) {
                    sentences.add(word + " " + rest);
                }
            }
        }
        memo.put(start, sentences);
        return sentences;
    }
}
