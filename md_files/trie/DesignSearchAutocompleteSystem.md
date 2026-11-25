# Design Search Autocomplete System

Design a search autocomplete system for sentences with a hot count (how often each was typed). `input(c)` receives one character of the user's query: if it is `#`, the typed sentence is saved with a count increment (or created with count 1) and an empty list is returned; otherwise return the top 3 historical sentences that start with the characters typed so far, ordered by hot count descending, and for equal counts in ASCII order.

## Algorithm Strategy (Trie with Counts and a Sorted Result)

1. Store each sentence in a trie; the node at the end of a sentence keeps its count.
2. Keep a pointer `current` to the trie node for the typed prefix and a `StringBuilder` of the characters typed.
3. On a normal character, move `current` to the child (or to `null` if there is none) and collect all sentences below `current` with their counts; sort by `-count` then by sentence and return the top 3.
4. On `#`, insert the typed sentence (adding 1 to its count), reset the pointer to the root and clear the buffer.

## Example walkthrough

- With sentences `i love you` (5), `island` (3), `i love leetcode` (2), `ironman` (2): typing `i` returns the top three of all four.
- Typing `i`, then a space, then `a` leaves nothing.

## Complexity Analysis

- **Time Complexity:** `O(size of the subtree) per character` — Collecting and sorting the matching sentences.
- **Space Complexity:** `O(total characters)` — The trie.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Scan All Sentences on Every Character

Keep a map of sentence to count and filter by prefix each time. Simple, but scans every sentence per keystroke.

```java
public static class Scan {
    private final java.util.Map<String, Integer> hot = new java.util.HashMap<>(); private final StringBuilder q = new StringBuilder();
    public Scan(String[] s, int[] t) { for (int i = 0; i < s.length; i++) hot.merge(s[i], t[i], Integer::sum); }
    public java.util.List<String> input(char c) {
        if (c == '#') { hot.merge(q.toString(), 1, Integer::sum); q.setLength(0); return new java.util.ArrayList<>(); }
        q.append(c); java.util.List<String> m = new java.util.ArrayList<>(); for (String s : hot.keySet()) if (s.startsWith(q.toString())) m.add(s);
        m.sort((a, b) -> hot.get(a).equals(hot.get(b)) ? a.compareTo(b) : hot.get(b) - hot.get(a)); return new java.util.ArrayList<>(m.subList(0, Math.min(3, m.size())));
    }
}
```

- **Time Complexity:** O(sentences * length) per character
- **Space Complexity:** O(total characters)

### Approach 2: Trie with Counts (Optimized)

Follow the typed prefix down a trie and collect the sentences below it. This is the version implemented in the repository.

```java
public static class TrieVersion {
    private static class N { java.util.Map<Character, N> k = new java.util.HashMap<>(); int c; } private final N root = new N(); private N cur = root; private final StringBuilder q = new StringBuilder();
    public TrieVersion(String[] s, int[] t) { for (int i = 0; i < s.length; i++) ins(s[i], t[i]); }
    private void ins(String s, int t) { N n = root; for (char ch : s.toCharArray()) n = n.k.computeIfAbsent(ch, x -> new N()); n.c += t; }
    private void collect(N n, StringBuilder p, java.util.List<String[]> out) { if (n.c > 0) out.add(new String[]{p.toString(), "" + n.c}); for (java.util.Map.Entry<Character, N> e : n.k.entrySet()) { p.append(e.getKey()); collect(e.getValue(), p, out); p.setLength(p.length() - 1); } }
    public java.util.List<String> input(char c) {
        if (c == '#') { ins(q.toString(), 1); q.setLength(0); cur = root; return new java.util.ArrayList<>(); }
        q.append(c); if (cur != null) cur = cur.k.get(c); java.util.List<String[]> m = new java.util.ArrayList<>(); if (cur != null) collect(cur, new StringBuilder(q), m);
        m.sort((a, b) -> { int x = Integer.parseInt(a[1]), y = Integer.parseInt(b[1]); return x != y ? y - x : a[0].compareTo(b[0]); }); java.util.List<String> top = new java.util.ArrayList<>(); for (int i = 0; i < Math.min(3, m.size()); i++) top.add(m.get(i)[0]); return top;
    }
}
```

- **Time Complexity:** O(subtree) per character
- **Space Complexity:** O(total characters)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Scan All Sentences on Every Character | O(sentences * length) per character | O(total characters) |
| 2 | Trie with Counts (Optimized) | O(subtree) per character | O(total characters) |

## Source Code

### DesignSearchAutocompleteSystem.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: every keystroke extends the current prefix by one character, so a trie lets the system follow a single pointer down instead of rescanning every sentence. The sentences below the current node are exactly the matches, to be ranked by count and then alphabetically.

**Input:** sentences `i love you` (5), `island` (3), `i love leetcode` (2), `ironman` (2).

| Typed so far | Matches (count) | Top 3 |
|--------------|-----------------|-------|
| `i` | i love you (5), island (3), i love leetcode (2), ironman (2) | i love you, island, i love leetcode |
| `i ` | i love you (5), i love leetcode (2) | i love you, i love leetcode |
| `i a` | none | |

**First output in `main`:** `[i love you, island, i love leetcode]`.
