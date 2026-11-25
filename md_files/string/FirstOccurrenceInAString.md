# Find the Index of the First Occurrence in a String

Given two strings `haystack` and `needle`, return the index of the first occurrence of `needle` in `haystack`, or `-1` if `needle` is not part of `haystack`. An empty `needle` matches at index 0.

## Algorithm Strategy (Sliding Comparison)

1. If `needle` is empty return 0; if it is longer than `haystack` return -1.
2. Try each start index `i` from 0 to `n - m`.
3. Compare `needle` with `haystack[i .. i + m - 1]` character by character, stopping at the first mismatch.
4. Return the first `i` that matches, or -1.

## Example walkthrough

- For `"sadbutsad"` and `"sad"`: index 0 matches immediately.
- For `"hello"` and `"ll"`: index 2 matches.

## Complexity Analysis

- **Time Complexity:** `O(n * m)` — Worst case compares `m` characters at each of `n` starts; KMP makes it O(n + m).
- **Space Complexity:** `O(1)` — Only indexes.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Built-in indexOf

Delegate to `String.indexOf`. Correct, but hides the algorithm.

```java
public static int builtIn(String h, String n) { return h.indexOf(n); }
```

- **Time Complexity:** O(n * m)
- **Space Complexity:** O(1)

### Approach 2: Brute Force Comparison (Optimized)

Compare at every start index. This is the version implemented in the repository.

```java
public static int brute(String h, String n) {
    int m = n.length(); if (m == 0) return 0;
    for (int i = 0; i + m <= h.length(); i++) { int j = 0; while (j < m && h.charAt(i + j) == n.charAt(j)) j++; if (j == m) return i; }
    return -1;
}
```

- **Time Complexity:** O(n * m)
- **Space Complexity:** O(1)

### Approach 3: KMP

Build the failure function of the needle so that a mismatch never moves the haystack index backward.

```java
public static int kmp(String h, String n) {
    int m = n.length(); if (m == 0) return 0;
    int[] f = new int[m];
    for (int i = 1, k = 0; i < m; i++) { while (k > 0 && n.charAt(i) != n.charAt(k)) k = f[k - 1]; if (n.charAt(i) == n.charAt(k)) k++; f[i] = k; }
    for (int i = 0, k = 0; i < h.length(); i++) {
        while (k > 0 && h.charAt(i) != n.charAt(k)) k = f[k - 1];
        if (h.charAt(i) == n.charAt(k)) k++;
        if (k == m) return i - m + 1;
    }
    return -1;
}
```

- **Time Complexity:** O(n + m)
- **Space Complexity:** O(m)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Built-in indexOf | O(n * m) | O(1) |
| 2 | Brute Force Comparison (Optimized) | O(n * m) | O(1) |
| 3 | KMP | O(n + m) | O(m) |

## Source Code

### FirstOccurrenceInAString.java

```java
public class FirstOccurrenceInAString {

    public static void main(String[] args) {
        String[][] inputs = {{"hello", "ll"}, {"sadbutsad", "sad"}, {"leetcode", "leeto"}, {"abc", ""}};
        for (String[] in : inputs) {
            System.out.println("Input  : haystack = \"" + in[0] + "\", needle = \"" + in[1] + "\"");
            System.out.println("Output : " + strStr(in[0], in[1]));
            System.out.println();
        }
    }

    public static int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }
        int n = haystack.length();
        int m = needle.length();
        if (m == 0) {
            return 0;
        }
        for (int i = 0; i + m <= n; i++) {
            int j = 0;
            while (j < m && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }
            if (j == m) {
                return i;
            }
        }
        return -1;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository; KMP (Approach 3) is the one to use when worst-case time matters.

The idea: slide the needle along the haystack and compare. Only start positions that leave room for the whole needle need to be tried.

**Input:** `haystack = "hello"`, `needle = "ll"`.

| Start `i` | Compared | Match? |
|-----------|----------|--------|
| 0 | he | no |
| 1 | el | no |
| 2 | ll | yes |

**Output:** `2`.
