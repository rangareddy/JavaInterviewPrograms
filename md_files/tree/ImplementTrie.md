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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: HashSet of Words (Baseline)

Store the words in a `HashSet`. `search` is `O(L)`, but `startsWith` has to scan every stored word.

```java
static class SetDictionary {
    private final Set<String> words = new HashSet<>();

    public void insert(String word) { words.add(word); }
    public boolean search(String word) { return words.contains(word); }
    public boolean startsWith(String prefix) {
        for (String word : words) if (word.startsWith(prefix)) return true;
        return false;
    }
}
```

- **Time Complexity:** insert/search O(L), startsWith O(N * L)
- **Space Complexity:** O(N * L)

### Approach 2: Trie with a Children Array (Optimized)

One node per letter with 26 child slots and an end-of-word flag; every operation walks only the letters of its argument. This is the version implemented in the repository.

```java
static class ArrayTrie {
    private static class Node { Node[] children = new Node[26]; boolean isWord; }
    private final Node root = new Node();

    public void insert(String word) {
        Node node = root;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) node.children[c - 'a'] = new Node();
            node = node.children[c - 'a'];
        }
        node.isWord = true;
    }

    public boolean search(String word) { Node node = find(word); return node != null && node.isWord; }
    public boolean startsWith(String prefix) { return find(prefix) != null; }

    private Node find(String text) {
        Node node = root;
        for (char c : text.toCharArray()) {
            node = node.children[c - 'a'];
            if (node == null) return null;
        }
        return node;
    }
}
```

- **Time Complexity:** O(L) per operation
- **Space Complexity:** O(26 * N * L) worst case

### Approach 3: Trie with a HashMap of Children

Store the children of a node in a `HashMap` instead of a 26-element array. Uses far less memory for sparse tries and supports any character set.

```java
static class MapTrie {
    private static class Node { Map<Character, Node> children = new HashMap<>(); boolean isWord; }
    private final Node root = new Node();

    public void insert(String word) {
        Node node = root;
        for (char c : word.toCharArray()) node = node.children.computeIfAbsent(c, k -> new Node());
        node.isWord = true;
    }

    public boolean search(String word) { Node node = find(word); return node != null && node.isWord; }
    public boolean startsWith(String prefix) { return find(prefix) != null; }

    private Node find(String text) {
        Node node = root;
        for (char c : text.toCharArray()) {
            node = node.children.get(c);
            if (node == null) return null;
        }
        return node;
    }
}
```

- **Time Complexity:** O(L) per operation
- **Space Complexity:** O(N * L)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | HashSet of Words (Baseline) | insert/search O(L), startsWith O(N * L) | O(N * L) |
| 2 | Trie with a Children Array (Optimized) | O(L) per operation | O(26 * N * L) worst case |
| 3 | Trie with a HashMap of Children | O(L) per operation | O(N * L) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

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
