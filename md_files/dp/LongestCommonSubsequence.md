# Longest Common Subsequence

Given two strings `text1` and `text2`, return the length of their longest common subsequence (characters in the same relative order, not necessarily contiguous), or 0 if there is none.

## Algorithm Strategy (Two-Dimensional Dynamic Programming)

1. Let `dp[i][j]` be the LCS length of the first `i` characters of `text1` and the first `j` characters of `text2`. Row 0 and column 0 are 0 (an empty prefix has no common characters).
2. If the characters `text1[i - 1]` and `text2[j - 1]` are equal, they extend the best subsequence of both shorter prefixes: `dp[i][j] = dp[i - 1][j - 1] + 1`.
3. Otherwise skip one character from either string and keep the better result: `dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])`.
4. The answer is `dp[m][n]` in the bottom-right corner.

## Example walkthrough

- For `"abcde"` and `"ace"`: the equal pairs a, c and e each add 1 along a diagonal.
- Result: `3` ("ace").

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every table cell is computed once.
- **Space Complexity:** `O(m * n)` — The table.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Recursion

If the last characters match they are part of the subsequence; otherwise drop the last character of either string and take the better result. Exponential.

```java
public static int lcsRecursive(String a, String b) {
    return solve(a, b, a.length(), b.length());
}

private static int solve(String a, String b, int i, int j) {
    if (i == 0 || j == 0) return 0;
    if (a.charAt(i - 1) == b.charAt(j - 1)) return 1 + solve(a, b, i - 1, j - 1);
    return Math.max(solve(a, b, i - 1, j), solve(a, b, i, j - 1));
}
```

- **Time Complexity:** O(2^(m + n))
- **Space Complexity:** O(m + n)

### Approach 2: Memoization (Top-Down DP)

The same recursion with a table of already solved `(i, j)` pairs.

```java
public static int lcsMemo(String a, String b) {
    int[][] memo = new int[a.length() + 1][b.length() + 1];
    for (int[] row : memo) Arrays.fill(row, -1);
    return solve(a, b, a.length(), b.length(), memo);
}

private static int solve(String a, String b, int i, int j, int[][] memo) {
    if (i == 0 || j == 0) return 0;
    if (memo[i][j] != -1) return memo[i][j];
    if (a.charAt(i - 1) == b.charAt(j - 1)) memo[i][j] = 1 + solve(a, b, i - 1, j - 1, memo);
    else memo[i][j] = Math.max(solve(a, b, i - 1, j, memo), solve(a, b, i, j - 1, memo));
    return memo[i][j];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 3: Bottom-Up DP Table

`dp[i][j]` is the LCS of the first `i` and `j` characters. This is the version implemented in the repository.

```java
public static int longestCommonSubsequence(String text1, String text2) {
    int m = text1.length(), n = text2.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1] + 1;
            else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
    }
    return dp[m][n];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 4: Two Rolling Rows (Optimized)

A row of the table only depends on the previous row, so keep just two rows of the shorter string's length. The LCS length is unchanged and the memory drops to `O(min(m, n))`.

```java
public static int lcsTwoRows(String text1, String text2) {
    if (text1.length() < text2.length()) { String t = text1; text1 = text2; text2 = t; }   // columns = shorter string
    int n = text2.length();
    int[] previous = new int[n + 1], current = new int[n + 1];
    for (int i = 1; i <= text1.length(); i++) {
        for (int j = 1; j <= n; j++) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) current[j] = previous[j - 1] + 1;
            else current[j] = Math.max(previous[j], current[j - 1]);
        }
        int[] temp = previous; previous = current; current = temp;
    }
    return previous[n];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(min(m, n))

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Recursion | O(2^(m + n)) | O(m + n) |
| 2 | Memoization (Top-Down DP) | O(m * n) | O(m * n) |
| 3 | Bottom-Up DP Table | O(m * n) | O(m * n) |
| 4 | Two Rolling Rows (Optimized) | O(m * n) | O(min(m, n)) |

## Source Code

### LongestCommonSubsequence.java

```java
public class LongestCommonSubsequence {

    public static void main(String[] args) {
        String[][] inputs = {{"abcde", "ace"}, {"abc", "abc"}, {"abc", "def"}, {"AGGTAB", "GXTXAYB"}};
        for (String[] input : inputs) {
            System.out.println("Input  : text1 = \"" + input[0] + "\", text2 = \"" + input[1] + "\"");
            System.out.println("Output : " + longestCommonSubsequence(input[0], input[1]));
            System.out.println();
        }
    }

    public static int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0;
        }

        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }
}
```

## Solution

The optimized solution is Approach 4 above. The version implemented in this repository is Approach 3.

**Note:** the page lists Approach 4 (two rolling rows) as the optimized one, but the class in this repository implements Approach 3, the full bottom-up table. This block explains the code as written; the rolling-row version computes the same cells while keeping only two rows.

The idea: `dp[i][j]` is the LCS length of the first `i` characters of `text1` and the first `j` of `text2`. If the last characters match, they extend the best answer of both shorter prefixes (`dp[i-1][j-1] + 1`); otherwise drop one character from either string and keep the better result (`max(dp[i-1][j], dp[i][j-1])`). Row 0 and column 0 stay 0 (an empty prefix).

**Input:** `text1 = "abcde"`, `text2 = "ace"`.

| `i` (char of text1) | `dp[i][0..3]` (cols: "", a, c, e) | What happened |
|---------------------|-----------------------------------|---------------|
| 0 (none) | `[0, 0, 0, 0]` | initial |
| 1 (`a`) | `[0, 1, 1, 1]` | `a = a` gives `dp[0][0] + 1 = 1`; the rest copy the left cell |
| 2 (`b`) | `[0, 1, 1, 1]` | no match, `max(above, left)` everywhere |
| 3 (`c`) | `[0, 1, 2, 2]` | `c = c` gives `dp[2][1] + 1 = 2` |
| 4 (`d`) | `[0, 1, 2, 2]` | no match |
| 5 (`e`) | `[0, 1, 2, 3]` | `e = e` gives `dp[4][2] + 1 = 3` |

```text
        ""  a  c  e
   ""    0  0  0  0
   a     0  1  1  1
   b     0  1  1  1
   c     0  1  2  2
   d     0  1  2  2
   e     0  1  2  3   <- dp[5][3] = 3  (subsequence "ace")
```

**Edge cases.** A `null` string returns `0`. An empty string leaves the whole table at 0, so the result is `0`. Strings with nothing in common (`"abc"`, `"def"`) also give `0`.

**Result:** `longestCommonSubsequence("abcde", "ace")` returns `3`, matching the class output.
