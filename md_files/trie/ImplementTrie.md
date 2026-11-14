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

### Approach 2: Trie with a HashMap of Children

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

### Approach 3: TreeSet with Ceiling Lookup

Keep the words in a sorted `TreeSet`. A word exists if the set contains it, and a prefix exists if the smallest word that is not smaller than the prefix (`ceiling`) starts with it.

```java
static class TreeSetDictionary {
    private final TreeSet<String> words = new TreeSet<>();

    public void insert(String word) { words.add(word); }
    public boolean search(String word) { return words.contains(word); }
    public boolean startsWith(String prefix) {
        String candidate = words.ceiling(prefix);
        return candidate != null && candidate.startsWith(prefix);
    }
}
```

- **Time Complexity:** O(L log N)
- **Space Complexity:** O(N * L)

### Approach 4: Ternary Search Tree

Each node holds one character and three links: smaller, equal (continue the word) and larger. It needs far less memory than a 26-way trie and keeps the words in sorted order.

```java
static class TernaryTrie {
    private static class Node { char c; Node lo, eq, hi; boolean isWord; Node(char c) { this.c = c; } }
    private Node root;

    public void insert(String word) { root = insert(root, word, 0); }

    private Node insert(Node node, String word, int index) {
        char c = word.charAt(index);
        if (node == null) node = new Node(c);
        if (c < node.c) node.lo = insert(node.lo, word, index);
        else if (c > node.c) node.hi = insert(node.hi, word, index);
        else if (index < word.length() - 1) node.eq = insert(node.eq, word, index + 1);
        else node.isWord = true;
        return node;
    }

    public boolean search(String word) { Node node = find(root, word, 0); return node != null && node.isWord; }
    public boolean startsWith(String prefix) { return find(root, prefix, 0) != null || prefix.isEmpty(); }

    private Node find(Node node, String text, int index) {
        if (node == null || text.isEmpty()) return null;
        char c = text.charAt(index);
        if (c < node.c) return find(node.lo, text, index);
        if (c > node.c) return find(node.hi, text, index);
        if (index == text.length() - 1) return node;
        return find(node.eq, text, index + 1);
    }
}
```

- **Time Complexity:** O(L + log N) average
- **Space Complexity:** O(total characters)

### Approach 5: Array-Based Trie (int[][] of Next States)

Store the trie in two flat arrays instead of node objects: `next[state][letter]` is the child state and `isWord[state]` marks word ends. No object per node, which is cache-friendly and cheap to allocate when the maximum size is known.

```java
static class ArrayTrie {
    private int[][] next = new int[16][26];
    private boolean[] isWord = new boolean[16];
    private int states = 1;                          // state 0 is the root

    public void insert(String word) {
        int state = 0;
        for (char c : word.toCharArray()) {
            if (next[state][c - 'a'] == 0) {
                if (states == next.length) {
                    next = Arrays.copyOf(next, states * 2);
                    isWord = Arrays.copyOf(isWord, states * 2);
                    for (int i = states; i < next.length; i++) next[i] = new int[26];
                }
                next[state][c - 'a'] = states++;
            }
            state = next[state][c - 'a'];
        }
        isWord[state] = true;
    }

    public boolean search(String word) { int state = find(word); return state >= 0 && isWord[state]; }
    public boolean startsWith(String prefix) { return find(prefix) >= 0; }

    private int find(String text) {
        int state = 0;
        for (char c : text.toCharArray()) {
            state = next[state][c - 'a'];
            if (state == 0) return -1;
        }
        return state;
    }
}
```

- **Time Complexity:** O(L) per operation
- **Space Complexity:** O(26 * N * L) worst case

### Approach 6: Two HashSets: Words and All Prefixes

On insertion add the word to a set of words and every one of its prefixes to a set of prefixes. Both queries are then a single hash lookup, at the cost of `O(L^2)` work and memory per inserted word.

```java
static class PrefixSetDictionary {
    private final Set<String> words = new HashSet<>(), prefixes = new HashSet<>();

    public void insert(String word) {
        words.add(word);
        for (int i = 0; i <= word.length(); i++) prefixes.add(word.substring(0, i));
    }

    public boolean search(String word) { return words.contains(word); }
    public boolean startsWith(String prefix) { return prefixes.contains(prefix); }
}
```

- **Time Complexity:** insert O(L^2), queries O(L)
- **Space Complexity:** O(N * L^2)

### Approach 7: Trie with a Children Array (Optimized)

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

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | HashSet of Words (Baseline) | insert/search O(L), startsWith O(N * L) | O(N * L) |
| 2 | Trie with a HashMap of Children | O(L) per operation | O(N * L) |
| 3 | TreeSet with Ceiling Lookup | O(L log N) | O(N * L) |
| 4 | Ternary Search Tree | O(L + log N) average | O(total characters) |
| 5 | Array-Based Trie (int[][] of Next States) | O(L) per operation | O(26 * N * L) worst case |
| 6 | Two HashSets: Words and All Prefixes | insert O(L^2), queries O(L) | O(N * L^2) |
| 7 | Trie with a Children Array (Optimized) | O(L) per operation | O(26 * N * L) worst case |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
