package com.ranga.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A transformation sequence from {@code beginWord} to {@code endWord} using a dictionary {@code wordList} is a sequence of words where every neighbouring pair differs by exactly one letter and every word after {@code beginWord} is in the dictionary. Return the number of words in the shortest transformation sequence (including {@code beginWord} and {@code endWord}), or 0 if none exists.
 *
 * <p><b>Algorithm Strategy (Bidirectional Breadth-First Search):</b>
 * <ol>
 *   <li>Guard first: {@code null} arguments, or an {@code endWord} that is not in the dictionary, give 0.</li>
 *   <li>Keep two frontiers, {@code front} (starting at {@code beginWord}) and {@code back} (starting at {@code endWord}), and a {@code HashSet} of the dictionary words that are still unvisited.</li>
 *   <li>Always expand the smaller frontier. For each word in it, replace each position with every letter {@code a}-{@code z}; if a candidate is in the other frontier, the two searches have met and the answer is {@code length + 1}.</li>
 *   <li>Otherwise every unvisited dictionary candidate is removed from the set and becomes part of the next frontier.</li>
 *   <li>Each round adds one word to the path length. If a frontier becomes empty, no ladder exists.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code hit -> cog} with {@code [hot, dot, dog, lot, log, cog]}: the front grows {@code {hit} -> {hot} -> {dot, lot}}; now the other side is smaller, so {@code {cog}} grows to {@code {dog, log}}; expanding {@code dog} generates {@code dot}, which is in the other frontier, so the searches meet with 5 words ({@code hit, hot, dot, dog, cog}).</li>
 *   <li>Result: {@code 5}. Without {@code cog} in the list, the answer is {@code 0}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(N * L * 26)} — {@code N} words of length {@code L}: each word is expanded at most once into {@code 26 * L} candidates; in practice the two searches meet in the middle, so far fewer words are expanded than with a one-sided search.</li>
 *   <li><b>Space Complexity:</b> {@code O(N * L)} — The word set and the two frontiers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class WordLadder {

    public static void main(String[] args) {
        String[] begins = {"hit", "hit", "a", "hit"};
        String[] ends = {"cog", "cog", "c", "hit"};
        List<List<String>> lists = Arrays.asList(
                Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"),
                Arrays.asList("hot", "dot", "dog", "lot", "log"),
                Arrays.asList("a", "b", "c"),
                Arrays.asList("hit", "hot"));
        for (int i = 0; i < begins.length; i++) {
            System.out.println("Input  : beginWord = \"" + begins[i] + "\", endWord = \"" + ends[i] + "\", wordList = " + lists.get(i));
            System.out.println("Output : " + ladderLength(begins[i], ends[i], lists.get(i)));
            System.out.println();
        }
        System.out.println("Input  : wordList = null");
        System.out.println("Output : " + ladderLength("hit", "cog", null));
    }

    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // Guard Clause: nothing to search without both words and a dictionary
        if (beginWord == null || endWord == null || wordList == null) {
            return 0;
        }
        Set<String> dictionary = new HashSet<>(wordList);
        if (!dictionary.contains(endWord)) {
            return 0;
        }
        if (beginWord.equals(endWord)) {
            return 1;                                       // the sequence is the single word
        }

        Set<String> front = new HashSet<>();
        Set<String> back = new HashSet<>();
        front.add(beginWord);
        back.add(endWord);
        dictionary.remove(beginWord);
        dictionary.remove(endWord);
        int length = 1;
        while (!front.isEmpty() && !back.isEmpty()) {
            // Always expand the smaller frontier
            if (front.size() > back.size()) {
                Set<String> temp = front;
                front = back;
                back = temp;
            }
            Set<String> next = new HashSet<>();
            for (String word : front) {
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char original = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        chars[i] = c;
                        String candidate = new String(chars);
                        if (back.contains(candidate)) {
                            return length + 1;              // the two searches met
                        }
                        if (dictionary.remove(candidate)) { // unseen dictionary word
                            next.add(candidate);
                        }
                    }
                    chars[i] = original;
                }
            }
            front = next;
            length++;
        }
        return 0;
    }
}
