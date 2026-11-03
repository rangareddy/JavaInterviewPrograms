package com.ranga.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * A transformation sequence from {@code beginWord} to {@code endWord} using a dictionary {@code wordList} is a sequence of words where every neighbouring pair differs by exactly one letter and every word after {@code beginWord} is in the dictionary. Return the number of words in the shortest transformation sequence (including {@code beginWord} and {@code endWord}), or 0 if none exists.
 *
 * <p><b>Algorithm Strategy (Breadth-First Search over Words):</b>
 * <ol>
 *   <li>Think of the words as nodes of a graph; two words are connected when they differ by one letter. The shortest transformation is a shortest path, so use BFS.</li>
 *   <li>Put the dictionary into a {@code HashSet} for {@code O(1)} lookups; if {@code endWord} is not in it, return 0.</li>
 *   <li>For each word taken from the queue, generate its neighbours by replacing each position with every letter {@code a}-{@code z}; every neighbour in the set is queued and removed from the set (so it is never queued twice).</li>
 *   <li>The BFS level at which {@code endWord} is reached is the length of the sequence.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code hit -> cog} with {@code [hot, dot, dog, lot, log, cog]}: {@code hit -> hot -> dot -> dog -> cog} has 5 words.</li>
 *   <li>Result: {@code 5}. Without {@code cog} in the list, the answer is {@code 0}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(N * L * 26)} — {@code N} words of length {@code L}: each word generates {@code 26 * L} candidate neighbours.</li>
 *   <li><b>Space Complexity:</b> {@code O(N * L)} — The word set and the queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class WordLadder {

    public static void main(String[] args) {
        String[] begins = {"hit", "hit", "a"};
        String[] ends = {"cog", "cog", "c"};
        List<List<String>> lists = Arrays.asList(
                Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"),
                Arrays.asList("hot", "dot", "dog", "lot", "log"),
                Arrays.asList("a", "b", "c"));
        for (int i = 0; i < begins.length; i++) {
            System.out.println("Input  : beginWord = \"" + begins[i] + "\", endWord = \"" + ends[i] + "\", wordList = " + lists.get(i));
            System.out.println("Output : " + ladderLength(begins[i], ends[i], lists.get(i)));
            System.out.println();
        }
    }

    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> words = new HashSet<>(wordList);
        if (!words.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        words.remove(beginWord);
        int length = 1;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                char[] word = queue.poll().toCharArray();
                for (int i = 0; i < word.length; i++) {
                    char original = word[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        word[i] = c;
                        String next = new String(word);
                        if (words.remove(next)) {                   // unseen dictionary word
                            if (next.equals(endWord)) {
                                return length + 1;
                            }
                            queue.add(next);
                        }
                    }
                    word[i] = original;
                }
            }
            length++;
        }
        return 0;
    }
}
