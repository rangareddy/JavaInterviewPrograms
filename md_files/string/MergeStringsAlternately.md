# Merge Strings Alternately

Given two strings `word1` and `word2`, merge them by adding letters in alternating order, starting with `word1`. If one string is longer, append its remaining letters to the end of the merged string.

## Algorithm Strategy (Interleave then Append the Tail)

1. Use one index `i` that walks both strings.
2. While `i` is within `word1`, append `word1[i]`; while `i` is within `word2`, append `word2[i]`.
3. When the shorter string runs out the longer one keeps appending its own letters.
4. Return the builder content.

## Example walkthrough

- For `abc` and `pqr`: a, p, b, q, c, r.
- Result: `apbqcr`.

## Complexity Analysis

- **Time Complexity:** `O(m + n)` — Each letter is appended once.
- **Space Complexity:** `O(m + n)` — The result.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Two Pointers and a Tail

Interleave while both have letters, then append the rest.

```java
public static String tail(String a, String b) {
    StringBuilder sb = new StringBuilder(); int i = 0, j = 0;
    while (i < a.length() && j < b.length()) { sb.append(a.charAt(i++)).append(b.charAt(j++)); }
    return sb.append(a.substring(i)).append(b.substring(j)).toString();
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(m + n)

### Approach 2: Single Index to the Longer Length (Optimized)

One loop to the longer length with bounds checks. This is the version implemented in the repository.

```java
public static String single(String a, String b) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < Math.max(a.length(), b.length()); i++) { if (i < a.length()) sb.append(a.charAt(i)); if (i < b.length()) sb.append(b.charAt(i)); }
    return sb.toString();
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(m + n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Two Pointers and a Tail | O(m + n) | O(m + n) |
| 2 | Single Index to the Longer Length (Optimized) | O(m + n) | O(m + n) |

## Source Code

### MergeStringsAlternately.java

```java
public class MergeStringsAlternately {

    public static void main(String[] args) {
        String[][] inputs = {{"abc", "pqr"}, {"ab", "pqrs"}, {"abcd", "pq"}, {"", "xyz"}};
        for (String[] in : inputs) {
            System.out.println("Input  : word1 = \"" + in[0] + "\", word2 = \"" + in[1] + "\"");
            System.out.println("Output : " + mergeAlternately(in[0], in[1]));
            System.out.println();
        }
    }

    public static String mergeAlternately(String word1, String word2) {
        StringBuilder result = new StringBuilder();
        int max = Math.max(word1.length(), word2.length());
        for (int i = 0; i < max; i++) {
            if (i < word1.length()) {
                result.append(word1.charAt(i));
            }
            if (i < word2.length()) {
                result.append(word2.charAt(i));
            }
        }
        return result.toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: one loop over the longer length, appending from each string only while it still has letters, merges and appends the tail in a single pass.

**Input:** `word1 = "ab"`, `word2 = "pqrs"`.

| `i` | From `word1` | From `word2` | Result so far |
|-----|--------------|--------------|----------------|
| 0 | a | p | ap |
| 1 | b | q | apbq |
| 2 | | r | apbqr |
| 3 | | s | apbqrs |

**Output for `abc` and `pqr`:** `apbqcr`.
