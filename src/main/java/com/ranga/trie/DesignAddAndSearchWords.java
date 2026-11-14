package com.ranga.trie;

/**
 * Design a data structure that supports {@code addWord(word)} and {@code search(word)}, where a searched word may contain dots {@code .} that match any single letter.
 *
 * <p><b>Algorithm Strategy (Trie with Wildcard DFS):</b>
 * <ol>
 *   <li>Store the words in a trie (26 children per node and an {@code isWord} flag).</li>
 *   <li>{@code addWord} inserts the letters exactly like a normal trie.</li>
 *   <li>{@code search} walks the letters recursively. For a normal letter follow the matching child (or fail if it is missing).</li>
 *   <li>For a dot, try every non-null child recursively and succeed if any of them leads to a match. At the end of the word the node must be marked {@code isWord}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>After adding {@code bad, dad, mad}: {@code search("pad")} is false, {@code search("bad")} is true.</li>
 *   <li>{@code search(".ad")} is true (b, d or m can match the dot) and {@code search("b..")} is true.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(L)} — for {@code addWord}; {@code search} is {@code O(L)} without dots and up to {@code O(26^L)} in the worst case with many dots.</li>
 *   <li><b>Space Complexity:</b> {@code O(N * L)} — Trie size for {@code N} words of length {@code L}.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DesignAddAndSearchWords {

    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord;
    }

    private final TrieNode root = new TrieNode();

    public static void main(String[] args) {
        DesignAddAndSearchWords dictionary = new DesignAddAndSearchWords();
        for (String word : new String[]{"bad", "dad", "mad"}) {
            dictionary.addWord(word);
        }
        System.out.println("Input  : addWord(\"bad\"), addWord(\"dad\"), addWord(\"mad\")");
        for (String query : new String[]{"pad", "bad", ".ad", "b.."}) {
            System.out.println("Input  : search(\"" + query + "\")");
            System.out.println("Output : " + dictionary.search(query));
        }
    }

    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isWord = true;
    }

    public boolean search(String word) {
        return search(word, 0, root);
    }

    private boolean search(String word, int index, TrieNode node) {
        if (node == null) {
            return false;
        }
        if (index == word.length()) {
            return node.isWord;
        }

        char c = word.charAt(index);
        if (c == '.') {
            for (TrieNode child : node.children) {
                if (search(word, index + 1, child)) {
                    return true;
                }
            }
            return false;
        }
        return search(word, index + 1, node.children[c - 'a']);
    }
}
