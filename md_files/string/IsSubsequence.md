# Is Subsequence

Given two strings `s` and `t`, return `true` if `s` is a subsequence of `t`, that is, `s` can be obtained from `t` by deleting some characters (possibly none) without changing the order of the rest.

## Algorithm Strategy (Two Pointers)

1. Keep one pointer `i` into `s` and scan `t` with another pointer.
2. When the current character of `t` equals `s[i]`, advance `i`.
3. If `i` reaches the end of `s`, every character was found in order.
4. An empty `s` is a subsequence of any string.

## Example walkthrough

- For `s = "abc"` and `t = "ahbgdc"`: a, b, c are found at positions 0, 2 and 5 in order.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(|t|)` — One pass over `t`.
- **Space Complexity:** `O(1)` — Two indices.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Match the first characters and recurse on the rests.

```java
public static boolean recursive(String s, String t) {
    if (s.isEmpty()) return true;
    if (t.isEmpty()) return false;
    if (s.charAt(0) == t.charAt(0)) return recursive(s.substring(1), t.substring(1));
    return recursive(s, t.substring(1));
}
```

- **Time Complexity:** O(|t|^2)
- **Space Complexity:** O(|t|)

### Approach 2: indexOf Chain

Find each character of `s` in `t` after the previous match.

```java
public static boolean indexOfChain(String s, String t) {
    int from = 0;
    for (int i = 0; i < s.length(); i++) {
        int idx = t.indexOf(s.charAt(i), from);
        if (idx < 0) return false;
        from = idx + 1;
    }
    return true;
}
```

- **Time Complexity:** O(|t|)
- **Space Complexity:** O(1)

### Approach 3: Two Pointers (Optimized)

Scan `t` once and advance in `s` on every match. This is the version implemented in the repository.

```java
public static boolean twoPointers(String s, String t) {
    int i = 0;
    for (int j = 0; j < t.length() && i < s.length(); j++) if (s.charAt(i) == t.charAt(j)) i++;
    return i == s.length();
}
```

- **Time Complexity:** O(|t|)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(|t|^2) | O(|t|) |
| 2 | indexOf Chain | O(|t|) | O(1) |
| 3 | Two Pointers (Optimized) | O(|t|) | O(1) |

## Source Code

### IsSubsequence.java

```java
public class IsSubsequence {

    public static void main(String[] args) {
        String[][] inputs = {{"abc", "ahbgdc"}, {"axc", "ahbgdc"}, {"", "abc"}, {"a", ""}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", t = \"" + in[1] + "\"");
            System.out.println("Output : " + isSubsequence(in[0], in[1]));
            System.out.println();
        }
    }

    public static boolean isSubsequence(String s, String t) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        if (t == null) {
            return false;
        }
        int i = 0;
        for (int j = 0; j < t.length() && i < s.length(); j++) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
        }
        return i == s.length();
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: greedily match each character of `s` to the earliest possible position in `t`. Taking the earliest match never hurts, because it leaves the most of `t` for the remaining characters.

**Input:** `s = "abc"`, `t = "ahbgdc"`.

| `t` index | `t` char | Needed (`s[i]`) | Action | `i` |
|-----------|----------|-----------------|--------|-----|
| 0 | a | a | match | 1 |
| 1 | h | b | skip | 1 |
| 2 | b | b | match | 2 |
| 3 | g | c | skip | 2 |
| 4 | d | c | skip | 2 |
| 5 | c | c | match | 3 |

`i` equals `s.length()`, so `s` is a subsequence.

**Output:** `true`.
