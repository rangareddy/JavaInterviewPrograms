# Longest String Chain

A word `a` is a predecessor of a word `b` if you can insert exactly one letter anywhere in `a` (without changing the order of the other letters) to make `b`. A word chain is a sequence of words in which each word is a predecessor of the next. Given a list of `words`, return the length of the longest possible word chain.

## Algorithm Strategy (DP over Words Sorted by Length)

1. Sort the words by length so that predecessors are processed first.
2. Let `best[w]` be the length of the longest chain ending at word `w`, stored in a `HashMap`.
3. For each word, try deleting each character to get a candidate predecessor; if it is in the map, `best[w] = max(best[w], best[pred] + 1)`.
4. The answer is the maximum of `best` over all words.

## Example walkthrough

- For `["a","b","ba","bca","bda","bdca"]`: `a -> ba -> bda -> bdca` has length 4.
- Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(n * L^2)` — For each word, `L` deletions each cost O(L) to build and hash.
- **Space Complexity:** `O(n * L)` — The map of chain lengths.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Pairwise Predecessor Check

Sort by length and test every earlier word as a predecessor with a two-pointer comparison.

```java
public static int pairwise(String[] in) {
    String[] w = in.clone(); java.util.Arrays.sort(w, (a, b) -> a.length() - b.length()); int[] dp = new int[w.length]; int best = 1;
    for (int i = 0; i < w.length; i++) { dp[i] = 1; for (int j = 0; j < i; j++) if (w[j].length() + 1 == w[i].length() && pred(w[j], w[i])) dp[i] = Math.max(dp[i], dp[j] + 1); best = Math.max(best, dp[i]); }
    return best;
}
private static boolean pred(String a, String b) { int i = 0, j = 0, skip = 0; while (i < a.length() && j < b.length()) { if (a.charAt(i) == b.charAt(j)) { i++; j++; } else { j++; skip++; if (skip > 1) return false; } } return true; }
```

- **Time Complexity:** O(n^2 * L)
- **Space Complexity:** O(n)

### Approach 2: HashMap and Single-Letter Deletion (Optimized)

Build each word's predecessors by deleting one letter and look up their chain length. This is the version implemented in the repository.

```java
public static int deletions(String[] in) {
    String[] w = in.clone(); java.util.Arrays.sort(w, (a, b) -> a.length() - b.length()); java.util.Map<String, Integer> best = new java.util.HashMap<>(); int ans = 1;
    for (String s : w) { int len = 1; for (int i = 0; i < s.length(); i++) len = Math.max(len, best.getOrDefault(s.substring(0, i) + s.substring(i + 1), 0) + 1); best.put(s, len); ans = Math.max(ans, len); }
    return ans;
}
```

- **Time Complexity:** O(n * L^2)
- **Space Complexity:** O(n * L)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Pairwise Predecessor Check | O(n^2 * L) | O(n) |
| 2 | HashMap and Single-Letter Deletion (Optimized) | O(n * L^2) | O(n * L) |

## Source Code

### LongestStringChain.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LongestStringChain {

    public static void main(String[] args) {
        String[][] inputs = {{"a", "b", "ba", "bca", "bda", "bdca"}, {"xbc", "pcxbcf", "xb", "cxbc", "pcxbc"}, {"abcd", "dbqca"}, {"a"}};
        for (String[] words : inputs) {
            System.out.println("Input  : words = " + Arrays.toString(words));
            System.out.println("Output : " + longestStrChain(words.clone()));
            System.out.println();
        }
    }

    public static int longestStrChain(String[] words) {
        if (words == null || words.length == 0) {
            return 0;
        }
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        Map<String, Integer> best = new HashMap<>();
        int answer = 1;
        for (String word : words) {
            int length = 1;
            for (int i = 0; i < word.length(); i++) {
                String predecessor = word.substring(0, i) + word.substring(i + 1);
                length = Math.max(length, best.getOrDefault(predecessor, 0) + 1);
            }
            best.put(word, length);
            answer = Math.max(answer, length);
        }
        return answer;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a word's predecessors are exactly the words you get by deleting one letter, and there are only as many of those as the word has letters. So for every word, look up each of its one-letter deletions in a map of chain lengths already computed for shorter words.

**Input:** `words = ["a", "b", "ba", "bca", "bda", "bdca"]`, sorted by length.

| Word | Deletions found in the map | `best` |
|------|----------------------------|--------|
| a | none | 1 |
| b | none | 1 |
| ba | `a` (1), `b` (1) | 2 |
| bca | `ba` (2) | 3 |
| bda | `ba` (2) | 3 |
| bdca | `bca` (3), `bda` (3) | 4 |

**Output:** `4`.
