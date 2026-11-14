# Longest Palindromic Subsequence

Given a string `s`, return the length of its longest palindromic subsequence (a sequence of characters that keeps the original order, may skip characters and reads the same in both directions).

## Algorithm Strategy (Interval DP on Substrings)

1. Let `dp[i][j]` be the length of the longest palindromic subsequence of `s[i..j]`. A single character is a palindrome of length 1: `dp[i][i] = 1`.
2. If the end characters match (`s[i] == s[j]`) they can wrap the best palindrome of the inside: `dp[i][j] = dp[i + 1][j - 1] + 2`.
3. Otherwise one of the end characters is not used: `dp[i][j] = max(dp[i + 1][j], dp[i][j - 1])`.
4. Fill the table for increasing substring length (or with `i` going from the end of the string); the answer is `dp[0][n - 1]`.

## Example walkthrough

- For `"bbbab"` the longest palindromic subsequence is `"bbbb"`, length 4.
- For `"cbbd"` it is `"bb"`, length 2.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Every pair `(i, j)` is computed once.
- **Space Complexity:** `O(n^2)` — The table; it can be reduced to O(n) by keeping only the previous row.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Compare the end characters of `s[i..j]`: wrap the inside if equal, otherwise drop one end.

```java
public static int lpsRecursive(String s) {
    return s.isEmpty() ? 0 : go(s, 0, s.length() - 1);
}

private static int go(String s, int i, int j) {
    if (i > j) return 0;
    if (i == j) return 1;
    if (s.charAt(i) == s.charAt(j)) return 2 + go(s, i + 1, j - 1);
    return Math.max(go(s, i + 1, j), go(s, i, j - 1));
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Top-Down Memoization

Cache the answer for each substring `(i, j)`.

```java
public static int lpsMemo(String s) {
    int n = s.length();
    return n == 0 ? 0 : goMemo(s, 0, n - 1, new int[n][n]);
}

private static int goMemo(String s, int i, int j, int[][] memo) {
    if (i > j) return 0;
    if (i == j) return 1;
    if (memo[i][j] != 0) return memo[i][j];
    int result = s.charAt(i) == s.charAt(j) ? 2 + goMemo(s, i + 1, j - 1, memo)
            : Math.max(goMemo(s, i + 1, j, memo), goMemo(s, i, j - 1, memo));
    return memo[i][j] = result;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Approach 3: Longest Common Subsequence with the Reverse

The longest palindromic subsequence of `s` is the longest common subsequence of `s` and its reverse.

```java
public static int lpsViaLcs(String s) {
    String r = new StringBuilder(s).reverse().toString();
    int n = s.length();
    int[][] dp = new int[n + 1][n + 1];
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= n; j++)
            dp[i][j] = s.charAt(i - 1) == r.charAt(j - 1) ? dp[i - 1][j - 1] + 1 : Math.max(dp[i - 1][j], dp[i][j - 1]);
    return dp[n][n];
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Approach 4: Interval DP Table (Optimized)

`dp[i][j]` for substrings, filled with `i` descending. This is the version implemented in the repository.

```java
public static int longestPalindromeSubseq(String s) {
    int n = s.length();
    int[][] dp = new int[n][n];
    for (int i = n - 1; i >= 0; i--) {
        dp[i][i] = 1;
        for (int j = i + 1; j < n; j++)
            dp[i][j] = s.charAt(i) == s.charAt(j) ? dp[i + 1][j - 1] + 2 : Math.max(dp[i + 1][j], dp[i][j - 1]);
    }
    return n == 0 ? 0 : dp[0][n - 1];
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(2^n) | O(n) |
| 2 | Top-Down Memoization | O(n^2) | O(n^2) |
| 3 | Longest Common Subsequence with the Reverse | O(n^2) | O(n^2) |
| 4 | Interval DP Table (Optimized) | O(n^2) | O(n^2) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: `dp[i][j]` is the longest palindromic subsequence inside `s[i..j]`. Every single character is a palindrome of length 1 (`dp[i][i] = 1`). If `s[i] == s[j]` they wrap the best palindrome of the inside `s[i+1..j-1]` and add 2; otherwise one end is useless, so take the better of `dp[i+1][j]` and `dp[i][j-1]`. Rows are filled from the bottom (`i = n-1`) up so the cells below and to the left are ready.

**Input:** `s = "bbbab"` (indices 0-4: `b b b a b`). Cells with `j < i` stay 0.

| `i` | Row `dp[i][0..4]` | Key cells |
|-----|-------------------|-----------|
| 4 | `[0, 0, 0, 0, 1]` | `dp[4][4] = 1` |
| 3 | `[0, 0, 0, 1, 1]` | `a != b`: `dp[3][4] = max(dp[4][4], dp[3][3]) = 1` |
| 2 | `[0, 0, 1, 1, 3]` | `dp[2][3] = 1` (`b != a`); `dp[2][4]`: `b = b`, `dp[3][3] + 2 = 3` |
| 1 | `[0, 1, 2, 2, 3]` | `dp[1][2]`: `b = b`, `dp[2][1] + 2 = 2`; `dp[1][3] = max(dp[2][3], dp[1][2]) = 2`; `dp[1][4]`: `b = b`, `dp[2][3] + 2 = 3` |
| 0 | `[1, 2, 3, 3, 4]` | `dp[0][2] = dp[1][1] + 2 = 3`; `dp[0][3] = max(dp[1][3], dp[0][2]) = 3`; `dp[0][4]`: `b = b`, `dp[1][3] + 2 = 4` |

```text
      j=0 1 2 3 4
i=0     1 2 3 3 4   <- dp[0][4] = 4  ("bbbb")
i=1       1 2 2 3
i=2         1 1 3
i=3           1 1
i=4             1
```

**Edge cases.** An empty string returns `0` (the `n == 0` check avoids reading `dp[0][-1]`). A single character returns `1`. A string with all different characters (`"abcde"`) returns `1`.

**Result:** `longestPalindromeSubseq("bbbab")` returns `4`, matching the class output.

## Source Code

### LongestPalindromicSubsequence.java

```java
public class LongestPalindromicSubsequence {

    public static void main(String[] args) {
        String[] inputs = {"bbbab", "cbbd", "a", "abcde", "agbdba"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + longestPalindromeSubseq(s));
            System.out.println();
        }
    }

    public static int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return n == 0 ? 0 : dp[0][n - 1];
    }
}
```
