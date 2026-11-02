package com.ranga.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Given an {@code m x n} board of characters and a list of words, return all words that can be built from sequentially adjacent (horizontal or vertical) cells, using each cell at most once per word.
 *
 * <p><b>Algorithm Strategy (Trie + Backtracking):</b>
 * <ol>
 *   <li>Build a trie of all the words. This lets one board traversal search for every word at the same time and prune a path as soon as no word starts with it.</li>
 *   <li>Start a DFS from every cell. Follow the trie child of the cell letter; if there is none, stop.</li>
 *   <li>When the current trie node stores a complete word, add it to the result and clear it from the node (so it is reported only once).</li>
 *   <li>Mark the cell as visited while exploring the four neighbours and restore it afterwards (backtracking).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the board {@code oaan / etae / ihkr / iflv} and words {@code oath, pea, eat, rain}: the path o-a-t-h is found, and e-a-t is found.</li>
 *   <li>{@code pea} and {@code rain} cannot be built, so the result is {@code [eat, oath]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m * n * 3^L)} — Each cell starts a DFS of depth at most the longest word length {@code L}, pruned by the trie.</li>
 *   <li><b>Space Complexity:</b> {@code O(W * L)} — The trie of {@code W} words.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class WordSearchII {

    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word;
    }

    public static void main(String[] args) {
        char[][] board = {{'o', 'a', 'a', 'n'}, {'e', 't', 'a', 'e'}, {'i', 'h', 'k', 'r'}, {'i', 'f', 'l', 'v'}};
        String[][] wordLists = {{"oath", "pea", "eat", "rain"}, {"abcb"}};
        for (String[] words : wordLists) {
            System.out.println("Input  : board = " + Arrays.deepToString(board) + ", words = " + Arrays.toString(words));
            System.out.println("Output : " + findWords(board, words));
            System.out.println();
        }
    }

    public static List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        if (board == null || board.length == 0 || words == null) {
            return result;
        }

        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
            }
            node.word = word;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root, result);
            }
        }
        Collections.sort(result);
        return result;
    }

    private static void dfs(char[][] board, int i, int j, TrieNode parent, List<String> result) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
            return;
        }
        char c = board[i][j];
        if (c == '#' || parent.children[c - 'a'] == null) {
            return;
        }

        TrieNode node = parent.children[c - 'a'];
        if (node.word != null) {
            result.add(node.word);
            node.word = null;           // report each word only once
        }

        board[i][j] = '#';              // mark as visited
        dfs(board, i + 1, j, node, result);
        dfs(board, i - 1, j, node, result);
        dfs(board, i, j + 1, node, result);
        dfs(board, i, j - 1, node, result);
        board[i][j] = c;                // backtrack
    }
}
