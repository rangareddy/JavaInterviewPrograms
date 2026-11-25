# Longest Substring with At Least K Repeating Characters

Given a string `s` and an integer `k`, return the length of the longest substring in which every character appears at least `k` times.

## Algorithm Strategy (Split on Characters That Occur Fewer Than k Times)

1. Count the frequency of every character in the current range.
2. A character with a frequency below `k` can never be part of a valid substring, so every valid substring lies between such characters.
3. Split the range on those characters and solve each part recursively.
4. If no character is rare, the whole range is valid; return its length.

## Example walkthrough

- For `ababbc` with `k = 2`: `c` occurs once, so split on it; the part `ababb` has every character at least twice.
- Result: `5`. For `aaabb` and `k = 3` the `b`s are rare, the part `aaa` remains, so the answer is `3`.

## Complexity Analysis

- **Time Complexity:** `O(n * 26)` — The recursion depth is at most 26 because each level removes one distinct character.
- **Space Complexity:** `O(26)` — Frequency arrays along the recursion.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Check Every Substring

Count characters in each substring and check the condition.

```java
public static int brute(String s, int k) {
    int best = 0; for (int i = 0; i < s.length(); i++) { int[] c = new int[26]; for (int j = i; j < s.length(); j++) { c[s.charAt(j) - 'a']++; boolean ok = true; for (int x : c) if (x > 0 && x < k) { ok = false; break; } if (ok) best = Math.max(best, j - i + 1); } } return best;
}
```

- **Time Complexity:** O(n^2 * 26)
- **Space Complexity:** O(26)

### Approach 2: Sliding Window with a Fixed Number of Unique Characters

For each target `u` from 1 to 26, slide a window containing exactly `u` distinct characters and track how many reach `k`.

```java
public static int window(String s, int k) {
    int best = 0; for (int u = 1; u <= 26; u++) { int[] c = new int[26]; int l = 0, uniq = 0, atLeast = 0;
        for (int r = 0; r < s.length(); r++) { if (c[s.charAt(r) - 'a']++ == 0) uniq++; if (c[s.charAt(r) - 'a'] == k) atLeast++;
            while (uniq > u) { if (c[s.charAt(l) - 'a'] == k) atLeast--; if (--c[s.charAt(l) - 'a'] == 0) uniq--; l++; }
            if (uniq == u && uniq == atLeast) best = Math.max(best, r - l + 1); } }
    return best;
}
```

- **Time Complexity:** O(26 * n)
- **Space Complexity:** O(26)

### Approach 3: Divide and Conquer on Rare Characters (Optimized)

Split on any character that occurs fewer than `k` times and recurse. This is the version implemented in the repository.

```java
public static int split(String s, int k) { return go(s, 0, s.length(), k); }
private static int go(String s, int a, int b, int k) {
    if (b - a < k) return 0; int[] c = new int[26]; for (int i = a; i < b; i++) c[s.charAt(i) - 'a']++;
    for (int i = a; i < b; i++) if (c[s.charAt(i) - 'a'] < k) { int n = i + 1; while (n < b && c[s.charAt(n) - 'a'] < k) n++; return Math.max(go(s, a, i, k), go(s, n, b, k)); }
    return b - a;
}
```

- **Time Complexity:** O(26 * n)
- **Space Complexity:** O(26)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Check Every Substring | O(n^2 * 26) | O(26) |
| 2 | Sliding Window with a Fixed Number of Unique Characters | O(26 * n) | O(26) |
| 3 | Divide and Conquer on Rare Characters (Optimized) | O(26 * n) | O(26) |

## Source Code

### LongestSubstringWithAtLeastKRepeatingCharacters.java

```java
public class LongestSubstringWithAtLeastKRepeatingCharacters {

    public static void main(String[] args) {
        String[] inputs = {"aaabb", "ababbc", "weitong", "a"};
        int[] ks = {3, 2, 2, 1};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : s = \"" + inputs[i] + "\", k = " + ks[i]);
            System.out.println("Output : " + longestSubstring(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static int longestSubstring(String s, int k) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        if (k <= 1) {
            return s.length();
        }
        return solve(s, 0, s.length(), k);
    }

    private static int solve(String s, int start, int end, int k) {
        if (end - start < k) {
            return 0;
        }
        int[] count = new int[26];
        for (int i = start; i < end; i++) {
            count[s.charAt(i) - 'a']++;
        }
        for (int i = start; i < end; i++) {
            if (count[s.charAt(i) - 'a'] < k) {
                int next = i + 1;
                while (next < end && count[s.charAt(next) - 'a'] < k) {
                    next++;
                }
                return Math.max(solve(s, start, i, k), solve(s, next, end, k));
            }
        }
        return end - start;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: a character that appears fewer than `k` times in a range can never be inside a valid substring of that range, because a substring can only contain fewer copies of it. Such characters are therefore walls. The answer is the best of the pieces between the walls, and a piece with no wall is valid as a whole.

**Input:** `s = "ababbc"`, `k = 2`.

| Range | Counts | Action |
|-------|--------|--------|
| `ababbc` | a2, b3, c1 | `c` is rare: split into `ababb` and the empty tail |
| `ababb` | a2, b3 | no rare character: valid, length 5 |

**Output for this input:** `5`. For the first sample in `main`, `"aaabb"` with `k = 3`, the output is `3`.
