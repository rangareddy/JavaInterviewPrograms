package com.ranga.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Design a search autocomplete system for sentences with a hot count (how often each was typed). {@code input(c)} receives one character of the user's query: if it is {@code #}, the typed sentence is saved with a count increment (or created with count 1) and an empty list is returned; otherwise return the top 3 historical sentences that start with the characters typed so far, ordered by hot count descending, and for equal counts in ASCII order.
 *
 * <p><b>Algorithm Strategy (Trie with Counts and a Sorted Result):</b>
 * <ol>
 *   <li>Store each sentence in a trie; the node at the end of a sentence keeps its count.</li>
 *   <li>Keep a pointer {@code current} to the trie node for the typed prefix and a {@code StringBuilder} of the characters typed.</li>
 *   <li>On a normal character, move {@code current} to the child (or to {@code null} if there is none) and collect all sentences below {@code current} with their counts; sort by {@code -count} then by sentence and return the top 3.</li>
 *   <li>On {@code #}, insert the typed sentence (adding 1 to its count), reset the pointer to the root and clear the buffer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>With sentences {@code i love you} (5), {@code island} (3), {@code i love leetcode} (2), {@code ironman} (2): typing {@code i} returns the top three of all four.</li>
 *   <li>Typing {@code i}, then a space, then {@code a} leaves nothing.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(size of the subtree) per character} — Collecting and sorting the matching sentences.</li>
 *   <li><b>Space Complexity:</b> {@code O(total characters)} — The trie.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DesignSearchAutocompleteSystem {

    public static void main(String[] args) {
        String[] sentences = {"i love you", "island", "i love leetcode", "ironman"};
        int[] times = {5, 3, 2, 2};
        DesignSearchAutocompleteSystem system = new DesignSearchAutocompleteSystem(sentences, times);
        System.out.println("Input  : sentences = " + Arrays.toString(sentences) + ", times = " + Arrays.toString(times) + ", input('i')");
        System.out.println("Output : " + system.input('i'));
        System.out.println();
        System.out.println("Input  : input(' ')");
        System.out.println("Output : " + system.input(' '));
        System.out.println();
        System.out.println("Input  : input('a'), input('#')");
        System.out.println("Output : " + system.input('a') + ", " + system.input('#'));
        System.out.println();
        System.out.println("Input  : input('i'), input(' '), input('a'), input('#') then input('i')");
        system.input('i');
        system.input(' ');
        system.input('a');
        system.input('#');
        System.out.println("Output : " + system.input('i'));
    }

    private static class Node {
        private final Map<Character, Node> children = new HashMap<>();
        private int count;
    }

    private final Node root = new Node();
    private Node current = root;
    private final StringBuilder typed = new StringBuilder();

    public DesignSearchAutocompleteSystem(String[] sentences, int[] times) {
        for (int i = 0; i < sentences.length; i++) {
            insert(sentences[i], times[i]);
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            insert(typed.toString(), 1);
            typed.setLength(0);
            current = root;
            return new ArrayList<>();
        }
        typed.append(c);
        if (current != null) {
            current = current.children.get(c);
        }
        List<String[]> matches = new ArrayList<>();
        if (current != null) {
            collect(current, new StringBuilder(typed), matches);
        }
        matches.sort((a, b) -> {
            int countA = Integer.parseInt(a[1]);
            int countB = Integer.parseInt(b[1]);
            return countA != countB ? countB - countA : a[0].compareTo(b[0]);
        });
        List<String> top = new ArrayList<>();
        for (int i = 0; i < Math.min(3, matches.size()); i++) {
            top.add(matches.get(i)[0]);
        }
        return top;
    }

    private void insert(String sentence, int times) {
        Node node = root;
        for (char ch : sentence.toCharArray()) {
            node = node.children.computeIfAbsent(ch, k -> new Node());
        }
        node.count += times;
    }

    private void collect(Node node, StringBuilder prefix, List<String[]> out) {
        if (node.count > 0) {
            out.add(new String[]{prefix.toString(), String.valueOf(node.count)});
        }
        for (Map.Entry<Character, Node> e : node.children.entrySet()) {
            prefix.append(e.getKey());
            collect(e.getValue(), prefix, out);
            prefix.setLength(prefix.length() - 1);
        }
    }
}
