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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Lists of Words by Length

Group the stored words by length in a map and match a query against every word of the same length, letter by letter (a dot matches anything). Simple, but every search scans all candidates.

```java
static class ListDictionary {
    private final Map<Integer, List<String>> byLength = new HashMap<>();

    public void addWord(String word) { byLength.computeIfAbsent(word.length(), k -> new ArrayList<>()).add(word); }

    public boolean search(String query) {
        for (String word : byLength.getOrDefault(query.length(), Collections.emptyList())) {
            boolean match = true;
            for (int i = 0; i < query.length() && match; i++) {
                match = query.charAt(i) == '.' || query.charAt(i) == word.charAt(i);
            }
            if (match) return true;
        }
        return false;
    }
}
```

- **Time Complexity:** addWord O(L), search O(N * L)
- **Space Complexity:** O(N * L)

### Approach 2: Trie with Iterative Level-by-Level Search

Search with a set of "current nodes": for each letter replace the set by the children it can reach (all children for a dot). No recursion; the work per query is bounded by the number of trie nodes.

```java
static class IterativeDictionary {
    private static class Node { Node[] children = new Node[26]; boolean isWord; }
    private final Node root = new Node();

    public void addWord(String word) {
        Node node = root;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) node.children[c - 'a'] = new Node();
            node = node.children[c - 'a'];
        }
        node.isWord = true;
    }

    public boolean search(String word) {
        List<Node> current = new ArrayList<>();
        current.add(root);
        for (char c : word.toCharArray()) {
            List<Node> next = new ArrayList<>();
            for (Node node : current) {
                if (c == '.') { for (Node child : node.children) if (child != null) next.add(child); }
                else if (node.children[c - 'a'] != null) next.add(node.children[c - 'a']);
            }
            if (next.isEmpty()) return false;
            current = next;
        }
        for (Node node : current) if (node.isWord) return true;
        return false;
    }
}
```

- **Time Complexity:** O(L * nodes) worst case
- **Space Complexity:** O(nodes)

### Approach 3: Regular Expression per Stored Word

The query is almost a regular expression: a dot already means "any character". Store the words in a list and test each candidate with `Pattern.matches`. Trivial to write, but every search tests every word.

```java
static class RegexDictionary {
    private final List<String> words = new ArrayList<>();

    public void addWord(String word) { words.add(word); }

    public boolean search(String query) {
        Pattern pattern = Pattern.compile(query);
        for (String word : words) if (pattern.matcher(word).matches()) return true;
        return false;
    }
}
```

- **Time Complexity:** addWord O(1), search O(N * L)
- **Space Complexity:** O(N * L)

### Approach 4: Trie with a HashMap per Node

A trie whose nodes store their children in a `HashMap<Character, Node>`; a dot recurses into all children. Handles any alphabet and uses memory only for the letters that really occur.

```java
static class MapTrieDictionary {
    private static class Node { Map<Character, Node> children = new HashMap<>(); boolean isWord; }
    private final Node root = new Node();

    public void addWord(String word) {
        Node node = root;
        for (char c : word.toCharArray()) node = node.children.computeIfAbsent(c, k -> new Node());
        node.isWord = true;
    }

    public boolean search(String word) { return search(word, 0, root); }

    private boolean search(String word, int index, Node node) {
        if (index == word.length()) return node.isWord;
        char c = word.charAt(index);
        if (c == '.') {
            for (Node child : node.children.values()) if (search(word, index + 1, child)) return true;
            return false;
        }
        Node child = node.children.get(c);
        return child != null && search(word, index + 1, child);
    }
}
```

- **Time Complexity:** addWord O(L), search O(L) without dots
- **Space Complexity:** O(N * L)

### Approach 5: Store Every Wildcard Variant of Each Word

When a word is added, also add all of its variants in which some letters are replaced by a dot (`2^L` of them) to a hash set. A search is then a single hash lookup, `O(L)`, however many dots it contains. Fast queries, exponential memory in the word length.

```java
static class VariantDictionary {
    private final Set<String> patterns = new HashSet<>();

    public void addWord(String word) {
        int length = word.length();
        for (int mask = 0; mask < (1 << length); mask++) {
            char[] variant = word.toCharArray();
            for (int i = 0; i < length; i++) if ((mask & (1 << i)) != 0) variant[i] = '.';
            patterns.add(new String(variant));
        }
    }

    public boolean search(String word) { return patterns.contains(word); }
}
```

- **Time Complexity:** addWord O(2^L * L), search O(L)
- **Space Complexity:** O(N * 2^L * L)

### Approach 6: Bitmap Index per (Length, Position, Letter)

Number the stored words. For every length keep a `BitSet` of the words per `(position, letter)`. A query intersects the bitsets of its fixed letters (a dot imposes no condition); a non-empty result is a match. Very fast for long lists of short words.

```java
static class BitSetDictionary {
    private final Map<Integer, List<String>> byLength = new HashMap<>();
    private final Map<String, BitSet> index = new HashMap<>();     // "length:position:letter" -> ids of words

    public void addWord(String word) {
        List<String> same = byLength.computeIfAbsent(word.length(), k -> new ArrayList<>());
        int id = same.size();
        same.add(word);
        for (int i = 0; i < word.length(); i++) {
            index.computeIfAbsent(word.length() + ":" + i + ":" + word.charAt(i), k -> new BitSet()).set(id);
        }
    }

    public boolean search(String query) {
        List<String> same = byLength.get(query.length());
        if (same == null) return false;
        BitSet candidates = new BitSet();
        candidates.set(0, same.size());
        for (int i = 0; i < query.length(); i++) {
            if (query.charAt(i) == '.') continue;
            BitSet matching = index.get(query.length() + ":" + i + ":" + query.charAt(i));
            if (matching == null) return false;
            candidates.and(matching);
            if (candidates.isEmpty()) return false;
        }
        return !candidates.isEmpty();
    }
}
```

- **Time Complexity:** addWord O(L), search O(L * N / 64)
- **Space Complexity:** O(N * L)

### Approach 7: Trie with Recursive Wildcard Search (Optimized)

Store the words in a trie; on a dot try every child recursively, on a letter follow the single matching child. This is the version implemented in the repository.

```java
static class TrieDictionary {
    private static class Node { Node[] children = new Node[26]; boolean isWord; }
    private final Node root = new Node();

    public void addWord(String word) {
        Node node = root;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) node.children[c - 'a'] = new Node();
            node = node.children[c - 'a'];
        }
        node.isWord = true;
    }

    public boolean search(String word) { return search(word, 0, root); }

    private boolean search(String word, int index, Node node) {
        if (node == null) return false;
        if (index == word.length()) return node.isWord;
        char c = word.charAt(index);
        if (c == '.') {
            for (Node child : node.children) if (search(word, index + 1, child)) return true;
            return false;
        }
        return search(word, index + 1, node.children[c - 'a']);
    }
}
```

- **Time Complexity:** addWord O(L), search O(L) without dots, up to O(26^L) with many dots
- **Space Complexity:** O(26 * N * L) worst case

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Lists of Words by Length | addWord O(L), search O(N * L) | O(N * L) |
| 2 | Trie with Iterative Level-by-Level Search | O(L * nodes) worst case | O(nodes) |
| 3 | Regular Expression per Stored Word | addWord O(1), search O(N * L) | O(N * L) |
| 4 | Trie with a HashMap per Node | addWord O(L), search O(L) without dots | O(N * L) |
| 5 | Store Every Wildcard Variant of Each Word | addWord O(2^L * L), search O(L) | O(N * 2^L * L) |
| 6 | Bitmap Index per (Length, Position, Letter) | addWord O(L), search O(L * N / 64) | O(N * L) |
| 7 | Trie with Recursive Wildcard Search (Optimized) | addWord O(L), search O(L) without dots, up to O(26^L) with many dots | O(26 * N * L) worst case |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
