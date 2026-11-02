package com.ranga.blind75.tree;

/**
 * Implement a trie (prefix tree) with {@code insert(word)}, {@code search(word)} (is the exact word stored?) and {@code startsWith(prefix)} (is any stored word starting with the prefix?).
 *
 * <p><b>Algorithm Strategy (Tree of Characters):</b>
 * <ol>
 *   <li>Each trie node has an array of 26 children (one per letter) and a flag {@code isWord} that marks the end of an inserted word.</li>
 *   <li>{@code insert}: walk from the root, creating the missing child node for each letter; mark the last node as a word end.</li>
 *   <li>{@code search}: walk the letters; if a child is missing return {@code false}, otherwise the word exists only if the last node has {@code isWord == true}.</li>
 *   <li>{@code startsWith}: the same walk, but reaching the end of the prefix is enough (the {@code isWord} flag is ignored).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>After {@code insert("apple")}: {@code search("apple")} is true, {@code search("app")} is false (no word ends at p), {@code startsWith("app")} is true.</li>
 *   <li>After {@code insert("app")}: {@code search("app")} becomes true.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(L)} — Every operation walks the {@code L} letters of its argument.</li>
 *   <li><b>Space Complexity:</b> {@code O(N * L)} — In total for {@code N} inserted words of length {@code L}.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ImplementTrie {

    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord;
    }

    private final TrieNode root = new TrieNode();

    public static void main(String[] args) {
        ImplementTrie trie = new ImplementTrie();
        trie.insert("apple");
        System.out.println("Input  : insert(\"apple\")");
        System.out.println("Input  : search(\"apple\")");
        System.out.println("Output : " + trie.search("apple"));
        System.out.println("Input  : search(\"app\")");
        System.out.println("Output : " + trie.search("app"));
        System.out.println("Input  : startsWith(\"app\")");
        System.out.println("Output : " + trie.startsWith("app"));
        trie.insert("app");
        System.out.println("Input  : insert(\"app\"), search(\"app\")");
        System.out.println("Output : " + trie.search("app"));
    }

    public void insert(String word) {
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
        TrieNode node = find(word);
        return node != null && node.isWord;
    }

    public boolean startsWith(String prefix) {
        return find(prefix) != null;
    }

    private TrieNode find(String text) {
        TrieNode node = root;
        for (char c : text.toCharArray()) {
            node = node.children[c - 'a'];
            if (node == null) {
                return null;
            }
        }
        return node;
    }
}
