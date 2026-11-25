# Count Vowel Strings in Ranges

Given a 0-indexed array of strings `words` and queries `[l, r]`, for each query return the number of strings in `words[l..r]` that start and end with a vowel (`a`, `e`, `i`, `o` or `u`).

## Algorithm Strategy (Prefix Sum over Qualifying Words)

1. Mark every word that starts and ends with a vowel.
2. Build a prefix array where `prefix[i + 1]` is the number of marked words among the first `i + 1`.
3. Answer each query as `prefix[r + 1] - prefix[l]`.
4. Collect the answers in order.

## Example walkthrough

- For `[aba, bcb, ece, aa, e]` and the query `[0, 2]`: `aba` and `ece` qualify.
- Result for the first query: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n + q)` — Build once, answer each query in O(1).
- **Space Complexity:** `O(n)` — The prefix array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Scan the Range for Every Query

Check each word in the range for every query.

```java
public static int[] scan(String[] w, int[][] q) { int[] r = new int[q.length]; String v = "aeiou"; for (int k = 0; k < q.length; k++) for (int i = q[k][0]; i <= q[k][1]; i++) if (!w[i].isEmpty() && v.indexOf(w[i].charAt(0)) >= 0 && v.indexOf(w[i].charAt(w[i].length() - 1)) >= 0) r[k]++; return r; }
```

- **Time Complexity:** O(n * q)
- **Space Complexity:** O(1)

### Approach 2: Prefix Sums (Optimized)

Precompute counts so that each query is a subtraction. This is the version implemented in the repository.

```java
public static int[] prefix(String[] w, int[][] q) { int[] p = new int[w.length + 1]; String v = "aeiou"; for (int i = 0; i < w.length; i++) p[i + 1] = p[i] + (!w[i].isEmpty() && v.indexOf(w[i].charAt(0)) >= 0 && v.indexOf(w[i].charAt(w[i].length() - 1)) >= 0 ? 1 : 0); int[] r = new int[q.length]; for (int k = 0; k < q.length; k++) r[k] = p[q[k][1] + 1] - p[q[k][0]]; return r; }
```

- **Time Complexity:** O(n + q)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Scan the Range for Every Query | O(n * q) | O(1) |
| 2 | Prefix Sums (Optimized) | O(n + q) | O(n) |

## Source Code

### CountVowelStringsInRanges.java

```java
import java.util.Arrays;

public class CountVowelStringsInRanges {

    public static void main(String[] args) {
        String[] words = {"aba", "bcb", "ece", "aa", "e"};
        int[][] queries = {{0, 2}, {1, 4}, {1, 1}};
        System.out.println("Input  : words = " + Arrays.toString(words) + ", queries = " + Arrays.deepToString(queries));
        System.out.println("Output : " + Arrays.toString(vowelStrings(words, queries)));
        System.out.println();
        String[] other = {"a", "e", "i"};
        int[][] otherQueries = {{0, 2}, {0, 1}, {2, 2}};
        System.out.println("Input  : words = " + Arrays.toString(other) + ", queries = " + Arrays.deepToString(otherQueries));
        System.out.println("Output : " + Arrays.toString(vowelStrings(other, otherQueries)));
    }

    public static int[] vowelStrings(String[] words, int[][] queries) {
        if (words == null || queries == null) {
            return new int[0];
        }
        int[] prefix = new int[words.length + 1];
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            boolean good = !word.isEmpty() && isVowel(word.charAt(0)) && isVowel(word.charAt(word.length() - 1));
            prefix[i + 1] = prefix[i] + (good ? 1 : 0);
        }
        int[] answers = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            answers[i] = prefix[queries[i][1] + 1] - prefix[queries[i][0]];
        }
        return answers;
    }

    private static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the number of qualifying words in a range is a difference of two running totals, so the totals are computed once and each query becomes a single subtraction.

**Input:** `words = [aba, bcb, ece, aa, e]`.

| Index | Word | Qualifies? | `prefix` |
|-------|------|------------|----------|
| | | | 0 |
| 0 | aba | yes | 1 |
| 1 | bcb | no | 1 |
| 2 | ece | yes | 2 |
| 3 | aa | yes | 3 |
| 4 | e | yes | 4 |

Query `[0, 2]` is `prefix[3] - prefix[0] = 2`, `[1, 4]` is `4 - 1 = 3` and `[1, 1]` is `1 - 1 = 0`.

**Output:** `[2, 3, 0]`.
