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

### Optimized Solution

The optimized solution is Approach 4 above. The version implemented in this repository is Approach 3.

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
