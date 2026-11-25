# Minimum Number of Swaps to Make the String Balanced

You are given a 0-indexed string `s` of even length `n` with exactly `n / 2` opening brackets `[` and `n / 2` closing brackets `]`. You may swap the brackets at any two indices any number of times. Return the minimum number of swaps to make `s` balanced.

## Algorithm Strategy (Count Unmatched Closing Brackets)

1. Scan the string with a counter `open` of unmatched opening brackets.
2. An opening bracket increments `open`; a closing bracket decrements it if `open > 0`, otherwise it is unmatched and `unmatched` increases.
3. Each swap can fix two unmatched closing brackets (swap an unmatched `]` near the front with an unused `[` near the end).
4. The answer is `(unmatched + 1) / 2`.

## Example walkthrough

- For `][][`: the first `]` is unmatched, the pairs `[]` match, and the last `[` stays open.
- One unmatched closing bracket needs `(1 + 1) / 2 = 1` swap.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Two counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BFS over All Swap Sequences

Explore every string reachable by swaps level by level until a balanced one appears. Exponential.

```java
public static int bfs(String s) {
    java.util.Set<String> seen = new java.util.HashSet<>(); java.util.Queue<String> q = new java.util.ArrayDeque<>(); q.add(s); seen.add(s); int d = 0;
    while (!q.isEmpty()) { for (int z = q.size(); z > 0; z--) { String c = q.poll(); if (balanced(c)) return d;
        char[] a = c.toCharArray(); for (int i = 0; i < a.length; i++) for (int j = i + 1; j < a.length; j++) if (a[i] != a[j]) { char t = a[i]; a[i] = a[j]; a[j] = t; String n = new String(a); if (seen.add(n)) q.add(n); t = a[i]; a[i] = a[j]; a[j] = t; } } d++; }
    return -1;
}
private static boolean balanced(String s) { int o = 0; for (char c : s.toCharArray()) { o += c == '[' ? 1 : -1; if (o < 0) return false; } return o == 0; }
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(2^n)

### Approach 2: Count Unmatched Closing Brackets (Optimized)

One swap repairs two unmatched closing brackets. This is the version implemented in the repository.

```java
public static int count(String s) {
    int open = 0, un = 0; for (char c : s.toCharArray()) { if (c == '[') open++; else if (open > 0) open--; else un++; }
    return (un + 1) / 2;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | BFS over All Swap Sequences | O(2^n) | O(2^n) |
| 2 | Count Unmatched Closing Brackets (Optimized) | O(n) | O(1) |

## Source Code

### MinimumNumberOfSwapsToMakeTheStringBalanced.java

```java
public class MinimumNumberOfSwapsToMakeTheStringBalanced {

    public static void main(String[] args) {
        String[] inputs = {"][][", "]]][[[", "[]", "]][[[]][[]]][["};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + minSwaps(s));
            System.out.println();
        }
    }

    public static int minSwaps(String s) {
        if (s == null) {
            return 0;
        }
        int open = 0;
        int unmatched = 0;
        for (char c : s.toCharArray()) {
            if (c == '[') {
                open++;
            } else if (open > 0) {
                open--;
            } else {
                unmatched++;
            }
        }
        return (unmatched + 1) / 2;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: scanning left to right, every `]` that finds no open `[` is a problem. Swapping such a `]` with a spare `[` from the end repairs it and also supplies a matching `]` to the next problem, so two problems are fixed per swap.

**Input:** `s = "]]][[["`.

| Char | `open` | `unmatched` |
|------|--------|-------------|
| ] | 0 | 1 |
| ] | 0 | 2 |
| ] | 0 | 3 |
| [ | 1 | 3 |
| [ | 2 | 3 |
| [ | 3 | 3 |

`(3 + 1) / 2 = 2`.

**Output for this input:** `2`. For the first sample in `main`, `][][`, the output is `1`.
