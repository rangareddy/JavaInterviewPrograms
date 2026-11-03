# Interleaving String

Given strings `s1`, `s2` and `s3`, determine whether `s3` is formed by an interleaving of `s1` and `s2`: both are split into substrings that are merged alternately while keeping the relative order of the characters of each string.

## Algorithm Strategy (DP over the Prefixes of s1 and s2)

1. If `s1.length() + s2.length() != s3.length()` the answer is `false`.
2. Let `dp[i][j]` be `true` when the first `i` characters of `s1` and the first `j` characters of `s2` can interleave into the first `i + j` characters of `s3`. `dp[0][0]` is true.
3. `dp[i][j]` is true if the last character of `s3[0..i+j)` comes from `s1` (`dp[i-1][j]` and `s1[i-1] == s3[i+j-1]`) or from `s2` (`dp[i][j-1]` and `s2[j-1] == s3[i+j-1]`).
4. Only the previous row is needed, so the table is compressed to one array over `j`. The answer is `dp[n]` after processing all rows.

## Example walkthrough

- For `s1 = "aabcc"`, `s2 = "dbbca"`, `s3 = "aadbbcbcac"`: the characters of `s3` can be taken alternately from `s1` and `s2` while keeping the order inside each string.
- Result: `true`. For `"aadbbbaccc"` no interleaving works: `false`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every pair of prefixes is examined once.
- **Space Complexity:** `O(n)` — One row of the table.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Take the next character of `s3` from `s1` or `s2` whenever it matches.

```java
public static boolean isInterleaveRecursive(String s1, String s2, String s3) {
    return s1.length() + s2.length() == s3.length() && go(s1, s2, s3, 0, 0);
}

private static boolean go(String s1, String s2, String s3, int i, int j) {
    if (i + j == s3.length()) return true;
    char c = s3.charAt(i + j);
    if (i < s1.length() && s1.charAt(i) == c && go(s1, s2, s3, i + 1, j)) return true;
    return j < s2.length() && s2.charAt(j) == c && go(s1, s2, s3, i, j + 1);
}
```

- **Time Complexity:** O(2^(m + n))
- **Space Complexity:** O(m + n)

### Approach 2: Top-Down Memoization

Remember the `(i, j)` pairs that already failed.

```java
public static boolean isInterleaveMemo(String s1, String s2, String s3) {
    if (s1.length() + s2.length() != s3.length()) return false;
    return goMemo(s1, s2, s3, 0, 0, new boolean[s1.length() + 1][s2.length() + 1]);
}

private static boolean goMemo(String s1, String s2, String s3, int i, int j, boolean[][] failed) {
    if (i + j == s3.length()) return true;
    if (failed[i][j]) return false;
    char c = s3.charAt(i + j);
    if (i < s1.length() && s1.charAt(i) == c && goMemo(s1, s2, s3, i + 1, j, failed)) return true;
    if (j < s2.length() && s2.charAt(j) == c && goMemo(s1, s2, s3, i, j + 1, failed)) return true;
    failed[i][j] = true;
    return false;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 3: BFS over (i, j) States

Walk through the grid of states level by level; level `k` holds the states with `i + j = k`.

```java
public static boolean isInterleaveBfs(String s1, String s2, String s3) {
    int m = s1.length(), n = s2.length();
    if (m + n != s3.length()) return false;
    Set<Integer> level = new HashSet<>();
    level.add(0);
    for (int k = 0; k < s3.length(); k++) {
        Set<Integer> next = new HashSet<>();
        char c = s3.charAt(k);
        for (int i : level) {
            int j = k - i;
            if (i < m && s1.charAt(i) == c) next.add(i + 1);
            if (j < n && s2.charAt(j) == c) next.add(i);
        }
        level = next;
    }
    return !level.isEmpty();
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(min(m, n))

### Approach 4: 1D DP Row (Optimized)

Fill the table row by row and keep only one row. This is the version implemented in the repository.

```java
public static boolean isInterleave(String s1, String s2, String s3) {
    int m = s1.length(), n = s2.length();
    if (m + n != s3.length()) return false;
    boolean[] dp = new boolean[n + 1];
    for (int i = 0; i <= m; i++) {
        for (int j = 0; j <= n; j++) {
            if (i == 0 && j == 0) dp[j] = true;
            else if (i == 0) dp[j] = dp[j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
            else if (j == 0) dp[j] = dp[j] && s1.charAt(i - 1) == s3.charAt(i - 1);
            else dp[j] = (dp[j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
                    || (dp[j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
        }
    }
    return dp[n];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(2^(m + n)) | O(m + n) |
| 2 | Top-Down Memoization | O(m * n) | O(m * n) |
| 3 | BFS over (i, j) States | O(m * n) | O(min(m, n)) |
| 4 | 1D DP Row (Optimized) | O(m * n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### InterleavingString.java

```java
public class InterleavingString {

    public static void main(String[] args) {
        String[][] inputs = {
                {"aabcc", "dbbca", "aadbbcbcac"},
                {"aabcc", "dbbca", "aadbbbaccc"},
                {"", "", ""},
                {"a", "", "a"},
                {"abc", "def", "adbecf"}
        };
        for (String[] input : inputs) {
            System.out.println("Input  : s1 = \"" + input[0] + "\", s2 = \"" + input[1] + "\", s3 = \"" + input[2] + "\"");
            System.out.println("Output : " + isInterleave(input[0], input[1], input[2]));
            System.out.println();
        }
    }

    public static boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();
        if (m + n != s3.length()) {
            return false;
        }

        boolean[] dp = new boolean[n + 1];          // dp[j] for the current i
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 && j == 0) {
                    dp[j] = true;
                } else if (i == 0) {
                    dp[j] = dp[j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
                } else if (j == 0) {
                    dp[j] = dp[j] && s1.charAt(i - 1) == s3.charAt(i - 1);
                } else {
                    dp[j] = (dp[j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
                            || (dp[j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
                }
            }
        }
        return dp[n];
    }
}
```
