# Smallest Subsequence of Distinct Characters

Given a string `s`, return the lexicographically smallest subsequence of `s` that contains all the distinct characters of `s` exactly once.

## Algorithm Strategy (Monotonic Stack with Last Occurrence)

1. Record the last index of every character.
2. Walk the string; skip a character that is already on the stack.
3. While the stack top is greater than the current character and the top occurs again later (its last index is after `i`), pop it and unmark it.
4. Push the current character and mark it. The stack, read from the bottom, is the answer.

## Example walkthrough

- For `cbacdcbc`: `c`, then `b`, then `a` pops `b` and `c` (both occur later); then `c`, `d` follow, and the later `b` is pushed at the end.
- Result: `acdb`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each character is pushed and popped once.
- **Space Complexity:** `O(1)` — At most 26 characters on the stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try All Subsequences

Enumerate subsets of the distinct characters positions and keep the smallest valid one. Exponential.

```java
public static String brute(String s) {
    int n = s.length(); String best = null; java.util.Set<Character> all = new java.util.HashSet<>(); for (char c : s.toCharArray()) all.add(c);
    for (int m = 0; m < (1 << n); m++) { StringBuilder sb = new StringBuilder(); java.util.Set<Character> seen = new java.util.HashSet<>(); boolean ok = true; for (int i = 0; i < n && ok; i++) if ((m >> i & 1) == 1) { if (!seen.add(s.charAt(i))) ok = false; else sb.append(s.charAt(i)); } if (ok && seen.size() == all.size() && (best == null || sb.toString().compareTo(best) < 0)) best = sb.toString(); }
    return best == null ? "" : best;
}
```

- **Time Complexity:** O(2^n * n)
- **Space Complexity:** O(n)

### Approach 2: Monotonic Stack (Optimized)

Pop larger characters that still occur later. This is the version implemented in the repository.

```java
public static String stack(String s) {
    int[] last = new int[26]; for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i; boolean[] used = new boolean[26]; StringBuilder st = new StringBuilder();
    for (int i = 0; i < s.length(); i++) { char c = s.charAt(i); if (used[c - 'a']) continue; while (st.length() > 0 && st.charAt(st.length() - 1) > c && last[st.charAt(st.length() - 1) - 'a'] > i) { used[st.charAt(st.length() - 1) - 'a'] = false; st.setLength(st.length() - 1); } st.append(c); used[c - 'a'] = true; }
    return st.toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try All Subsequences | O(2^n * n) | O(n) |
| 2 | Monotonic Stack (Optimized) | O(n) | O(1) |

## Source Code

### SmallestSubsequenceOfDistinctCharacters.java

```java
public class SmallestSubsequenceOfDistinctCharacters {

    public static void main(String[] args) {
        String[] inputs = {"cbacdcbc", "bcabc", "abcd", "", "ecbacba"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + smallestSubsequence(s));
            System.out.println();
        }
    }

    public static String smallestSubsequence(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }
        boolean[] used = new boolean[26];
        StringBuilder stack = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (used[c - 'a']) {
                continue;
            }
            while (stack.length() > 0 && stack.charAt(stack.length() - 1) > c && last[stack.charAt(stack.length() - 1) - 'a'] > i) {
                used[stack.charAt(stack.length() - 1) - 'a'] = false;
                stack.setLength(stack.length() - 1);
            }
            stack.append(c);
            used[c - 'a'] = true;
        }
        return stack.toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: to make the result small, a smaller character should come as early as possible. A bigger character already chosen can be dropped in favour of a smaller one only if the bigger one appears again later, so the string can still contain it. The last-occurrence table answers that question, and a stack keeps the best prefix so far.

**Input:** `s = "cbacdcbc"`.

| Char | Stack after | Reason |
|------|-------------|--------|
| c | c | |
| b | b | `c` > `b` and `c` occurs later: pop |
| a | a | `b` > `a` and `b` occurs later: pop |
| c | ac | |
| d | acd | |
| c | acd | already used |
| b | acdb | `d` < `b` is false... `b` is smaller than `d` but `d` never occurs again: keep, then push `b` |
| c | acdb | already used |

**Output:** `acdb`.
