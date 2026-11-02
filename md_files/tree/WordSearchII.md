# Word Search II

Given an `m x n` board of characters and a list of words, return all words that can be built from sequentially adjacent (horizontal or vertical) cells, using each cell at most once per word.

## Algorithm Strategy (Trie + Backtracking)

1. Build a trie of all the words. This lets one board traversal search for every word at the same time and prune a path as soon as no word starts with it.
2. Start a DFS from every cell. Follow the trie child of the cell letter; if there is none, stop.
3. When the current trie node stores a complete word, add it to the result and clear it from the node (so it is reported only once).
4. Mark the cell as visited while exploring the four neighbours and restore it afterwards (backtracking).

## Example walkthrough

- For the board `oaan / etae / ihkr / iflv` and words `oath, pea, eat, rain`: the path o-a-t-h is found, and e-a-t is found.
- `pea` and `rain` cannot be built, so the result is `[eat, oath]`.

## Complexity Analysis

- **Time Complexity:** `O(m * n * 3^L)` — Each cell starts a DFS of depth at most the longest word length `L`, pruned by the trie.
- **Space Complexity:** `O(W * L)` — The trie of `W` words.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Word Search I for Every Word

Run the single-word board search once per word. Correct, but the board is traversed once per word.

```java
public static List<String> findWordsEach(char[][] board, String[] words) {
    List<String> result = new ArrayList<>();
    for (String word : words) {
        if (exists(board, word)) result.add(word);
    }
    Collections.sort(result);
    return result;
}

private static boolean exists(char[][] board, String word) {
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (dfs(board, word, i, j, 0)) return true;
        }
    }
    return false;
}

private static boolean dfs(char[][] board, String word, int i, int j, int index) {
    if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != word.charAt(index)) return false;
    if (index == word.length() - 1) return true;
    char original = board[i][j];
    board[i][j] = '#';
    boolean found = dfs(board, word, i + 1, j, index + 1) || dfs(board, word, i - 1, j, index + 1)
            || dfs(board, word, i, j + 1, index + 1) || dfs(board, word, i, j - 1, index + 1);
    board[i][j] = original;
    return found;
}
```

- **Time Complexity:** O(W * m * n * 3^L)
- **Space Complexity:** O(L)

### Approach 2: Trie + Backtracking (Optimized)

Put all words in a trie and search the board once: a path is abandoned as soon as no word starts with it, and every word is found while walking. This is the version implemented in the repository.

```java
private static class TrieNode { TrieNode[] children = new TrieNode[26]; String word; }

public static List<String> findWords(char[][] board, String[] words) {
    TrieNode root = new TrieNode();
    for (String word : words) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) node.children[c - 'a'] = new TrieNode();
            node = node.children[c - 'a'];
        }
        node.word = word;
    }
    List<String> result = new ArrayList<>();
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) search(board, i, j, root, result);
    }
    Collections.sort(result);
    return result;
}

private static void search(char[][] board, int i, int j, TrieNode parent, List<String> result) {
    if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) return;
    char c = board[i][j];
    if (c == '#' || parent.children[c - 'a'] == null) return;
    TrieNode node = parent.children[c - 'a'];
    if (node.word != null) { result.add(node.word); node.word = null; }
    board[i][j] = '#';
    search(board, i + 1, j, node, result);
    search(board, i - 1, j, node, result);
    search(board, i, j + 1, node, result);
    search(board, i, j - 1, node, result);
    board[i][j] = c;
}
```

- **Time Complexity:** O(m * n * 3^L)
- **Space Complexity:** O(W * L)

### Approach 3: Trie with Pruning of Exhausted Branches

Same as above, but after a word has been found, delete the trie branches that no longer lead to any remaining word. Later searches then skip those branches entirely, which is a large saving on boards with many matches.

```java
private static class PruneNode { PruneNode[] children = new PruneNode[26]; String word; }

public static List<String> findWordsPruned(char[][] board, String[] words) {
    PruneNode root = new PruneNode();
    for (String word : words) {
        PruneNode node = root;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) node.children[c - 'a'] = new PruneNode();
            node = node.children[c - 'a'];
        }
        node.word = word;
    }
    List<String> result = new ArrayList<>();
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) searchPruned(board, i, j, root, result);
    }
    Collections.sort(result);
    return result;
}

private static void searchPruned(char[][] board, int i, int j, PruneNode parent, List<String> result) {
    if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) return;
    char c = board[i][j];
    PruneNode node = c == '#' ? null : parent.children[c - 'a'];
    if (node == null) return;
    if (node.word != null) {
        result.add(node.word);
        node.word = null;
    }
    board[i][j] = '#';
    searchPruned(board, i + 1, j, node, result);
    searchPruned(board, i - 1, j, node, result);
    searchPruned(board, i, j + 1, node, result);
    searchPruned(board, i, j - 1, node, result);
    board[i][j] = c;
    boolean exhausted = node.word == null;
    for (PruneNode child : node.children) if (child != null) exhausted = false;
    if (exhausted) parent.children[c - 'a'] = null;     // nothing left below this letter
}
```

- **Time Complexity:** O(m * n * 3^L), much faster in practice
- **Space Complexity:** O(W * L)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Word Search I for Every Word | O(W * m * n * 3^L) | O(L) |
| 2 | Trie + Backtracking (Optimized) | O(m * n * 3^L) | O(W * L) |
| 3 | Trie with Pruning of Exhausted Branches | O(m * n * 3^L), much faster in practice | O(W * L) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### WordSearchII.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
```
