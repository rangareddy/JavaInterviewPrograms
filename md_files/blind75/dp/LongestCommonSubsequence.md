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
