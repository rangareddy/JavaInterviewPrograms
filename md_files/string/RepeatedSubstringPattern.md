# Repeated Substring Pattern

Given a string `s`, check whether it can be constructed by taking a substring of it and appending multiple copies of that substring together.

## Algorithm Strategy (Find s Inside s + s Without the Trivial Matches)

1. If `s` is a repetition of a block, then rotating `s` by the block length gives `s` again.
2. Every rotation of `s` appears inside `s + s`.
3. Search for `s` in `(s + s)` starting at index 1. The trivial match is at index 0, and the copy at index `n` is the other trivial one.
4. `s` is repeated exactly when the first match is before index `n`.

## Example walkthrough

- For `abab`: `abababab` contains `abab` at index 2, which is less than 4.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — `indexOf` is linear with a good implementation (worst case O(n^2) for the Java default).
- **Space Complexity:** `O(n)` — The doubled string.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Block Length

For each divisor `len` of `n`, repeat the first `len` characters and compare with `s`.

```java
public static boolean blocks(String s) { int n = s.length(); for (int len = 1; len <= n / 2; len++) { if (n % len != 0) continue; StringBuilder sb = new StringBuilder(); for (int i = 0; i < n / len; i++) sb.append(s, 0, len); if (sb.toString().equals(s)) return true; } return false; }
```

- **Time Complexity:** O(n * d(n))
- **Space Complexity:** O(n)

### Approach 2: Search in the Doubled String (Optimized)

Look for `s` inside `s + s` starting at index 1. This is the version implemented in the repository.

```java
public static boolean doubled(String s) { return s.length() >= 2 && (s + s).indexOf(s, 1) < s.length(); }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Block Length | O(n * d(n)) | O(n) |
| 2 | Search in the Doubled String (Optimized) | O(n) | O(n) |

## Source Code

### RepeatedSubstringPattern.java

```java
public class RepeatedSubstringPattern {

    public static void main(String[] args) {
        String[] inputs = {"abab", "aba", "abcabcabcabc", "a", "aaaa"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + repeatedSubstringPattern(s));
            System.out.println();
        }
    }

    public static boolean repeatedSubstringPattern(String s) {
        if (s == null || s.length() < 2) {
            return false;
        }
        return (s + s).indexOf(s, 1) < s.length();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: if `s = block` repeated `k >= 2` times, then `s + s` contains `s` at an offset that is a multiple of the block length, other than 0 and `n`. If `s` is not periodic, the only matches are the trivial ones at 0 and `n`.

**Input:** `s = "abab"`.

| Step | Value |
|------|-------|
| `s + s` | `abababab` |
| search `abab` from index 1 | found at index 2 |
| compare with `n = 4` | 2 < 4 |

**Output:** `true`.
