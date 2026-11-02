# Word Break

Given a string `s` and a dictionary of words, return `true` if `s` can be split into a space-separated sequence of one or more dictionary words (a word may be reused).

## Algorithm Strategy (Dynamic Programming over Prefixes)

1. Let `dp[i]` be `true` when the first `i` characters of `s` can be segmented. `dp[0] = true` (the empty prefix).
2. For every end position `i` from 1 to `n`, try every split point `j < i`: if `dp[j]` is true and the substring `s[j..i)` is in the dictionary, then `dp[i]` is true.
3. Store the dictionary in a `HashSet` so each check is `O(L)` (hashing the substring).
4. The answer is `dp[n]`.

## Example walkthrough

- For `"leetcode"` with `["leet", "code"]`: `dp[4]` is true ("leet") and `dp[8]` is true because `dp[4]` is true and "code" is a word.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n^2 * L)` — Pairs of positions times the cost of building and hashing a substring of length up to `L`.
- **Space Complexity:** `O(n)` — The `dp` array (plus the dictionary).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Recursion

Try every dictionary word as a prefix of the remaining string and recurse on the rest. Exponential on strings like `"aaaa...b"`.

```java
public static boolean wordBreakRecursive(String s, List<String> wordDict) {
    if (s.isEmpty()) return true;
    for (String word : wordDict) {
        if (s.startsWith(word) && wordBreakRecursive(s.substring(word.length()), wordDict)) return true;
    }
    return false;
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Memoization (Top-Down DP)

Remember for every start index whether the rest of the string can be segmented.

```java
public static boolean wordBreakMemo(String s, List<String> wordDict) {
    return solve(s, 0, new HashSet<>(wordDict), new Boolean[s.length()]);
}

private static boolean solve(String s, int start, Set<String> words, Boolean[] memo) {
    if (start == s.length()) return true;
    if (memo[start] != null) return memo[start];
    for (int end = start + 1; end <= s.length(); end++) {
        if (words.contains(s.substring(start, end)) && solve(s, end, words, memo)) return memo[start] = true;
    }
    return memo[start] = false;
}
```

- **Time Complexity:** O(n^2 * L)
- **Space Complexity:** O(n)

### Approach 3: Bottom-Up DP over Prefixes (Optimized)

`dp[i]` says whether the first `i` characters can be segmented. This is the version implemented in the repository.

```java
public static boolean wordBreak(String s, List<String> wordDict) {
    Set<String> words = new HashSet<>(wordDict);
    boolean[] dp = new boolean[s.length() + 1];
    dp[0] = true;
    for (int i = 1; i <= s.length(); i++) {
        for (int j = 0; j < i; j++) {
            if (dp[j] && words.contains(s.substring(j, i))) { dp[i] = true; break; }
        }
    }
    return dp[s.length()];
}
```

- **Time Complexity:** O(n^2 * L)
- **Space Complexity:** O(n)

### Approach 4: BFS over the Cut Positions

Treat each reachable cut position as a node; from a position try all dictionary words that match there. Reaching the end of the string means success; every position is expanded at most once.

```java
public static boolean wordBreakBfs(String s, List<String> wordDict) {
    Set<String> words = new HashSet<>(wordDict);
    boolean[] visited = new boolean[s.length() + 1];
    Queue<Integer> queue = new LinkedList<>();
    queue.add(0);
    while (!queue.isEmpty()) {
        int start = queue.poll();
        if (start == s.length()) return true;
        for (int end = start + 1; end <= s.length(); end++) {
            if (!visited[end] && words.contains(s.substring(start, end))) {
                visited[end] = true;
                queue.add(end);
            }
        }
    }
    return false;
}
```

- **Time Complexity:** O(n^2 * L)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Recursion | O(2^n) | O(n) |
| 2 | Memoization (Top-Down DP) | O(n^2 * L) | O(n) |
| 3 | Bottom-Up DP over Prefixes (Optimized) | O(n^2 * L) | O(n) |
| 4 | BFS over the Cut Positions | O(n^2 * L) | O(n) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### WordBreak.java

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak {

    public static void main(String[] args) {
        String[] strings = {"leetcode", "applepenapple", "catsandog"};
        List<List<String>> dictionaries = Arrays.asList(
                Arrays.asList("leet", "code"),
                Arrays.asList("apple", "pen"),
                Arrays.asList("cats", "dog", "sand", "and", "cat"));
        for (int i = 0; i < strings.length; i++) {
            System.out.println("Input  : s = \"" + strings[i] + "\", wordDict = " + dictionaries.get(i));
            System.out.println("Output : " + wordBreak(strings[i], dictionaries.get(i)));
            System.out.println();
        }
    }

    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> words = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && words.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
```
