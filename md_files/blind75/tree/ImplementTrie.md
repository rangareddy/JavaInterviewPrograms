# Implement Trie (Prefix Tree)

Implement a trie (prefix tree) with `insert(word)`, `search(word)` (is the exact word stored?) and `startsWith(prefix)` (is any stored word starting with the prefix?).

## Algorithm Strategy (Tree of Characters)

1. Each trie node has an array of 26 children (one per letter) and a flag `isWord` that marks the end of an inserted word.
2. `insert`: walk from the root, creating the missing child node for each letter; mark the last node as a word end.
3. `search`: walk the letters; if a child is missing return `false`, otherwise the word exists only if the last node has `isWord == true`.
4. `startsWith`: the same walk, but reaching the end of the prefix is enough (the `isWord` flag is ignored).

## Example walkthrough

- After `insert("apple")`: `search("apple")` is true, `search("app")` is false (no word ends at p), `startsWith("app")` is true.
- After `insert("app")`: `search("app")` becomes true.

## Complexity Analysis

- **Time Complexity:** `O(L)` — Every operation walks the `L` letters of its argument.
- **Space Complexity:** `O(N * L)` — In total for `N` inserted words of length `L`.

## Source Code

### ImplementTrie.java

```java
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
```
