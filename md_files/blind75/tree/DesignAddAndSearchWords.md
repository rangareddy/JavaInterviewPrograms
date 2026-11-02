# Design Add and Search Words Data Structure

Design a data structure that supports `addWord(word)` and `search(word)`, where a searched word may contain dots `.` that match any single letter.

## Algorithm Strategy (Trie with Wildcard DFS)

1. Store the words in a trie (26 children per node and an `isWord` flag).
2. `addWord` inserts the letters exactly like a normal trie.
3. `search` walks the letters recursively. For a normal letter follow the matching child (or fail if it is missing).
4. For a dot, try every non-null child recursively and succeed if any of them leads to a match. At the end of the word the node must be marked `isWord`.

## Example walkthrough

- After adding `bad, dad, mad`: `search("pad")` is false, `search("bad")` is true.
- `search(".ad")` is true (b, d or m can match the dot) and `search("b..")` is true.

## Complexity Analysis

- **Time Complexity:** `O(L)` — for `addWord`; `search` is `O(L)` without dots and up to `O(26^L)` in the worst case with many dots.
- **Space Complexity:** `O(N * L)` — Trie size for `N` words of length `L`.

## Source Code

### DesignAddAndSearchWords.java

```java
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
```
