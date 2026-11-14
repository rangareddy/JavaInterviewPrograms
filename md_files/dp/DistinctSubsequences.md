# Distinct Subsequences

Given two strings `s` and `t`, return the number of distinct subsequences of `s` that equal `t` (a subsequence keeps the relative order of characters but may skip characters).

## Algorithm Strategy (DP over the Prefixes of t)

1. Let `dp[j]` be the number of ways to form the first `j` characters of `t` from the characters of `s` processed so far. `dp[0] = 1`: the empty string can be formed in exactly one way.
2. Process the characters of `s` one by one. For a character `c = s[i]`, every position `j` with `t[j - 1] == c` gains `dp[j - 1]` ways: the new character can serve as the last character of `t[0..j)`.
3. Update `j` from `t.length()` down to 1, so that `dp[j - 1]` still holds the value from before this character (each character of `s` is used at most once).
4. The answer is `dp[t.length()]`.

## Example walkthrough

- For `s = "rabbbit"` and `t = "rabbit"` there are 3 ways, depending on which of the three `b` characters of `s` is skipped.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(|s| * |t|)` — Every character of `s` updates all positions of `t`.
- **Space Complexity:** `O(|t|)` — One array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Match the characters from the end: when they are equal, count both using and skipping the character of `s`.

```java
public static int numDistinctRecursive(String s, String t) {
    return count(s, t, s.length(), t.length());
}

private static int count(String s, String t, int i, int j) {
    if (j == 0) return 1;
    if (i == 0) return 0;
    int result = count(s, t, i - 1, j);
    if (s.charAt(i - 1) == t.charAt(j - 1)) result += count(s, t, i - 1, j - 1);
    return result;
}
```

- **Time Complexity:** O(2^|s|)
- **Space Complexity:** O(|s|)

### Approach 2: Top-Down Memoization

Cache the count for each pair of prefix lengths.

```java
public static int numDistinctMemo(String s, String t) {
    int[][] memo = new int[s.length() + 1][t.length() + 1];
    for (int[] row : memo) Arrays.fill(row, -1);
    return countMemo(s, t, s.length(), t.length(), memo);
}

private static int countMemo(String s, String t, int i, int j, int[][] memo) {
    if (j == 0) return 1;
    if (i == 0) return 0;
    if (memo[i][j] >= 0) return memo[i][j];
    int result = countMemo(s, t, i - 1, j, memo);
    if (s.charAt(i - 1) == t.charAt(j - 1)) result += countMemo(s, t, i - 1, j - 1, memo);
    return memo[i][j] = result;
}
```

- **Time Complexity:** O(|s| * |t|)
- **Space Complexity:** O(|s| * |t|)

### Approach 3: 2D Table

`dp[i][j]` is the number of ways the first `j` characters of `t` appear in the first `i` characters of `s`.

```java
public static int numDistinctTable(String s, String t) {
    int m = s.length(), n = t.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 0; i <= m; i++) dp[i][0] = 1;
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++) {
            dp[i][j] = dp[i - 1][j];
            if (s.charAt(i - 1) == t.charAt(j - 1)) dp[i][j] += dp[i - 1][j - 1];
        }
    return dp[m][n];
}
```

- **Time Complexity:** O(|s| * |t|)
- **Space Complexity:** O(|s| * |t|)

### Approach 4: 1D Array (Optimized)

Update the single row from right to left. This is the version implemented in the repository.

```java
public static int numDistinct(String s, String t) {
    int n = t.length();
    int[] dp = new int[n + 1];
    dp[0] = 1;
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        for (int j = n; j >= 1; j--) if (t.charAt(j - 1) == c) dp[j] += dp[j - 1];
    }
    return dp[n];
}
```

- **Time Complexity:** O(|s| * |t|)
- **Space Complexity:** O(|t|)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(2^|s|) | O(|s|) |
| 2 | Top-Down Memoization | O(|s| * |t|) | O(|s| * |t|) |
| 3 | 2D Table | O(|s| * |t|) | O(|s| * |t|) |
| 4 | 1D Array (Optimized) | O(|s| * |t|) | O(|t|) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: `dp[j]` is the number of ways the first `j` characters of `t` can be formed as a subsequence of the part of `s` read so far. `dp[0] = 1` (the empty string can be formed in one way). For each character `c` of `s`, go through `j` from `n` down to 1; when `t[j-1] == c` that character can extend every way of forming the first `j - 1` characters, so `dp[j] += dp[j-1]`. Going downwards makes sure `dp[j-1]` still holds the value from before this character.

**Input:** `s = "babgbag"`, `t = "bag"`. Start: `dp = [1, 0, 0, 0]` (for the prefixes "", "b", "ba", "bag").

| `i` | `s[i]` | Update | `dp` afterwards |
|-----|--------|--------|-----------------|
| 0 | b | `t[0] = b`: `dp[1] += dp[0]` | `[1, 1, 0, 0]` |
| 1 | a | `t[1] = a`: `dp[2] += dp[1]` | `[1, 1, 1, 0]` |
| 2 | b | `dp[1] += dp[0]` | `[1, 2, 1, 0]` |
| 3 | g | `t[2] = g`: `dp[3] += dp[2]` | `[1, 2, 1, 1]` |
| 4 | b | `dp[1] += dp[0]` | `[1, 3, 1, 1]` |
| 5 | a | `dp[2] += dp[1]` | `[1, 3, 4, 1]` |
| 6 | g | `dp[3] += dp[2]` | `[1, 3, 4, 5]` |

The last cell `dp[3] = 5` is the number of distinct ways to pick `b`, `a`, `g` in order from `babgbag`. No diagram is added; the table is the full picture.

**Edge cases.** If `t` is longer than `s` (`"abc"`, `"abcd"`) the last cell is never raised above 0, so the result is `0`. Both strings empty gives `dp = [1]` and returns `1`. A character of `s` that is not in `t` changes nothing. Space is `O(len(t))`.

**Result:** the method returns `5`, matching the class output (`rabbbit` / `rabbit` prints `3`).

## Source Code

### DistinctSubsequences.java

```java
public class DistinctSubsequences {

    public static void main(String[] args) {
        String[][] inputs = {{"rabbbit", "rabbit"}, {"babgbag", "bag"}, {"abc", "abcd"}, {"aaa", "aa"}, {"", ""}};
        for (String[] input : inputs) {
            System.out.println("Input  : s = \"" + input[0] + "\", t = \"" + input[1] + "\"");
            System.out.println("Output : " + numDistinct(input[0], input[1]));
            System.out.println();
        }
    }

    public static int numDistinct(String s, String t) {
        int n = t.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            for (int j = n; j >= 1; j--) {
                if (t.charAt(j - 1) == c) {
                    dp[j] += dp[j - 1];
                }
            }
        }
        return dp[n];
    }
}
```
