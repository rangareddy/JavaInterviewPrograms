package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * A transformation sequence from {@code beginWord} to {@code endWord} using a dictionary {@code wordList} is a sequence of words where consecutive words differ by exactly one letter and every word after {@code beginWord} is in the dictionary. Return all the shortest transformation sequences from {@code beginWord} to {@code endWord}, or an empty list if there is none.
 *
 * <p><b>Algorithm Strategy (BFS Levels then Path Reconstruction):</b>
 * <ol>
 *   <li>Run a BFS from {@code beginWord}, changing one letter at a time to words in the dictionary, recording each word's level (distance from the start) and, for every word, the list of parents on shortest paths.</li>
 *   <li>Only add a parent edge when the neighbour is on the next level ({@code level[next] == level[word] + 1}).</li>
 *   <li>Stop the BFS once the level containing {@code endWord} is finished.</li>
 *   <li>Run a DFS from {@code endWord} back to {@code beginWord} through the parent lists, reverse each path, and return them all.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code hit} to {@code cog} with {@code [hot, dot, dog, lot, log, cog]}: two shortest paths of five words exist.</li>
 *   <li>Result: {@code [[hit, hot, dot, dog, cog], [hit, hot, lot, log, cog]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n * L * 26 + paths)} — BFS over the dictionary plus the size of the output.</li>
 *   <li><b>Space Complexity:</b> {@code O(n * L)} — Levels and parent lists.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class WordLadderII {

    public static void main(String[] args) {
        String[] begins = {"hit", "hit", "a"};
        String[] ends = {"cog", "cog", "c"};
        String[][] lists = {{"hot", "dot", "dog", "lot", "log", "cog"}, {"hot", "dot", "dog", "lot", "log"}, {"a", "b", "c"}};
        for (int i = 0; i < begins.length; i++) {
            List<List<String>> result = findLadders(begins[i], ends[i], Arrays.asList(lists[i]));
            for (List<String> path : result) {
                Collections.sort(path.subList(0, 0));
            }
            result.sort((a, b) -> a.toString().compareTo(b.toString()));
            System.out.println("Input  : beginWord = \"" + begins[i] + "\", endWord = \"" + ends[i] + "\", wordList = " + Arrays.toString(lists[i]));
            System.out.println("Output : " + result);
            System.out.println();
        }
    }

    public static List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> result = new ArrayList<>();
        Set<String> words = new HashSet<>(wordList);
        if (!words.contains(endWord)) {
            return result;
        }
        Map<String, Integer> level = new HashMap<>();
        Map<String, List<String>> parents = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.add(beginWord);
        level.put(beginWord, 0);
        boolean found = false;
        while (!queue.isEmpty() && !found) {
            for (int size = queue.size(); size > 0; size--) {
                String word = queue.poll();
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char original = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) {
                            continue;
                        }
                        chars[i] = c;
                        String next = new String(chars);
                        if (!words.contains(next)) {
                            continue;
                        }
                        int nextLevel = level.get(word) + 1;
                        if (!level.containsKey(next)) {
                            level.put(next, nextLevel);
                            queue.add(next);
                        }
                        if (level.get(next) == nextLevel) {
                            parents.computeIfAbsent(next, k -> new ArrayList<>()).add(word);
                        }
                        if (next.equals(endWord)) {
                            found = true;
                        }
                    }
                    chars[i] = original;
                }
            }
        }
        if (found) {
            LinkedList<String> path = new LinkedList<>();
            path.add(endWord);
            collect(endWord, beginWord, parents, path, result);
        }
        return result;
    }

    private static void collect(String word, String begin, Map<String, List<String>> parents, LinkedList<String> path, List<List<String>> result) {
        if (word.equals(begin)) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (String parent : parents.getOrDefault(word, new ArrayList<>())) {
            path.addFirst(parent);
            collect(parent, begin, parents, path, result);
            path.removeFirst();
        }
    }
}
