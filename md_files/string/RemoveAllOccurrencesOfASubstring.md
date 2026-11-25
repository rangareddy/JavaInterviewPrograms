# Remove All Occurrences of a Substring

Given two strings `s` and `part`, repeatedly remove the leftmost occurrence of `part` from `s` until none remains, and return `s`.

## Algorithm Strategy (Build with a StringBuilder and Check the Suffix)

1. Append the characters of `s` one by one to a `StringBuilder`.
2. After each append, if the builder ends with `part`, delete those last `part.length()` characters.
3. Because removals can create a new match with earlier characters, the builder acts like a stack that is re-checked after every append.
4. The builder's content is the answer.

## Example walkthrough

- For `daabcbaabcbc` and `abc`: the first `abc` is deleted, which exposes a new `abc` shortly after, and so on.
- Result: `dab`.

## Complexity Analysis

- **Time Complexity:** `O(n * m)` — Each append may compare a suffix of length `m`.
- **Space Complexity:** `O(n)` — The builder.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Repeated indexOf and Delete

Find the leftmost occurrence, delete it, and repeat.

```java
public static String loop(String s, String part) {
    StringBuilder sb = new StringBuilder(s); int i;
    while ((i = sb.indexOf(part)) >= 0) sb.delete(i, i + part.length());
    return sb.toString();
}
```

- **Time Complexity:** O(n^2 / m)
- **Space Complexity:** O(n)

### Approach 2: Build and Check the Suffix (Optimized)

Append characters and delete whenever the suffix equals `part`. This is the version implemented in the repository.

```java
public static String suffixCheck(String s, String part) {
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) { sb.append(c); int st = sb.length() - part.length(); if (st >= 0 && sb.indexOf(part, st) == st) sb.setLength(st); }
    return sb.toString();
}
```

- **Time Complexity:** O(n * m)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Repeated indexOf and Delete | O(n^2 / m) | O(n) |
| 2 | Build and Check the Suffix (Optimized) | O(n * m) | O(n) |

## Source Code

### RemoveAllOccurrencesOfASubstring.java

```java
public class RemoveAllOccurrencesOfASubstring {

    public static void main(String[] args) {
        String[][] inputs = {{"daabcbaabcbc", "abc"}, {"axxxxyyyyb", "xy"}, {"aaaa", "aa"}, {"abc", "x"}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", part = \"" + in[1] + "\"");
            System.out.println("Output : \"" + removeOccurrences(in[0], in[1]) + "\"");
            System.out.println();
        }
    }

    public static String removeOccurrences(String s, String part) {
        if (s == null || part == null || part.isEmpty()) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(c);
            int start = sb.length() - part.length();
            if (start >= 0 && sb.indexOf(part, start) == start) {
                sb.setLength(start);
            }
        }
        return sb.toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: scanning left to right and deleting a match as soon as it is completed always removes the leftmost occurrence, and the characters that remain in the builder can still combine with later ones.

**Input:** `s = "daabcbaabcbc"`, `part = "abc"`.

| Char read | Builder after append | Action |
|-----------|----------------------|--------|
| d, a, a, b | daab | no match |
| c | daabc | ends with abc, delete -> `da` |
| b, a, a, b | dabaab | no match |
| c | dabaabc | ends with abc, delete -> `daba` |
| b, c | dababc | ends with abc, delete -> `dab` |

**Output:** `"dab"`.
