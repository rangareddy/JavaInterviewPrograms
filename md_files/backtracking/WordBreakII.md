# Word Break II

Given a string `s` and a dictionary of strings `wordDict`, add spaces in `s` to construct all possible sentences in which every word is in the dictionary. A word may be reused. Return the sentences in any order.

## Algorithm Strategy (Memoised Recursion on the Start Index)

1. Define `sentences(start)` as all ways to break the suffix `s[start..]`.
2. Try every end index so that `s[start..end)` is in the dictionary; for each, combine the word with every sentence of the remaining suffix (or just the word when the end reaches the string end).
3. Cache the result for each `start`, because the same suffix is reached through many different prefixes.
4. Return `sentences(0)`.

## Example walkthrough

- For `s = "catsanddog"` and the dictionary `cat, cats, and, sand, dog`: `cats and dog` and `cat sand dog` both work.
- Result: two sentences.

## Complexity Analysis

- **Time Complexity:** `O(n^2 + number of sentences * n)` — Memoisation bounds the work per start; the output itself can be exponential.
- **Space Complexity:** `O(n * number of sentences)` — The memo of sentences per index.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Backtracking

Try every word at every position with no cache. Exponential even when most branches fail.

```java
public static java.util.List<String> plain(String s, java.util.List<String> d) { java.util.List<String> out = new java.util.ArrayList<>(); go(s, new java.util.HashSet<>(d), 0, "", out); return out; }
private static void go(String s, java.util.Set<String> w, int st, String cur, java.util.List<String> out) {
    if (st == s.length()) { out.add(cur.trim()); return; }
    for (int e = st + 1; e <= s.length(); e++) { String x = s.substring(st, e); if (w.contains(x)) go(s, w, e, cur + x + " ", out); }
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Memoised Recursion (Optimized)

Cache the sentences of every suffix. This is the version implemented in the repository.

```java
public static java.util.List<String> memo(String s, java.util.List<String> d) { return rec(s, new java.util.HashSet<>(d), 0, new java.util.HashMap<>()); }
private static java.util.List<String> rec(String s, java.util.Set<String> w, int st, java.util.Map<Integer, java.util.List<String>> m) {
    if (m.containsKey(st)) return m.get(st); java.util.List<String> out = new java.util.ArrayList<>();
    for (int e = st + 1; e <= s.length(); e++) { String x = s.substring(st, e); if (!w.contains(x)) continue; if (e == s.length()) out.add(x); else for (String r : rec(s, w, e, m)) out.add(x + " " + r); }
    m.put(st, out); return out;
}
```

- **Time Complexity:** O(n^2 + output * n)
- **Space Complexity:** O(output * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Backtracking | O(2^n) | O(n) |
| 2 | Memoised Recursion (Optimized) | O(n^2 + output * n) | O(output * n) |

## Source Code

### WordBreakII.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordBreakII {

    public static void main(String[] args) {
        String[] inputs = {"catsanddog", "pineapplepenapple", "catsandog"};
        String[][] dictionaries = {{"cat", "cats", "and", "sand", "dog"}, {"apple", "pen", "applepen", "pine", "pineapple"}, {"cats", "dog", "sand", "and", "cat"}};
        for (int i = 0; i < inputs.length; i++) {
            List<String> result = wordBreak(inputs[i], Arrays.asList(dictionaries[i]));
            java.util.Collections.sort(result, java.util.Collections.reverseOrder());
            System.out.println("Input  : s = \"" + inputs[i] + "\", wordDict = " + Arrays.toString(dictionaries[i]));
            System.out.println("Output : " + result);
            System.out.println();
        }
    }

    public static List<String> wordBreak(String s, List<String> wordDict) {
        if (s == null || wordDict == null) {
            return new ArrayList<>();
        }
        return break_(s, new HashSet<>(wordDict), 0, new HashMap<>());
    }

    private static List<String> break_(String s, Set<String> words, int start, Map<Integer, List<String>> memo) {
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        List<String> sentences = new ArrayList<>();
        for (int end = start + 1; end <= s.length(); end++) {
            String word = s.substring(start, end);
            if (!words.contains(word)) {
                continue;
            }
            if (end == s.length()) {
                sentences.add(word);
            } else {
                for (String rest : break_(s, words, end, memo)) {
                    sentences.add(word + " " + rest);
                }
            }
        }
        memo.put(start, sentences);
        return sentences;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: breaking a suffix does not depend on how the prefix was broken, so the sentences for each suffix can be computed once and reused. Without the cache the same suffix is solved again and again.

**Input:** `s = "catsanddog"`, dictionary `[cat, cats, and, sand, dog]`.

| Start | Suffix | Words that fit | Sentences |
|-------|--------|----------------|-----------|
| 7 | `dog` | dog | `dog` |
| 4 | `anddog` | and | `and dog` |
| 3 | `sanddog` | sand | `sand dog` |
| 3 (from `cats`) | `anddog` | (cached) | `and dog` |
| 0 | `catsanddog` | cat, cats | `cat sand dog`, `cats and dog` |

**Output (sorted for printing):** `[cats and dog, cat sand dog]`.
