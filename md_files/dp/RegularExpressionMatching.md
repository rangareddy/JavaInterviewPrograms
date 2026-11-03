# Regular Expression Matching

Given an input string `s` and a pattern `p`, implement regular expression matching with support for `.` (matches any single character) and `*` (matches zero or more of the preceding element). The matching must cover the entire input string.

## Algorithm Strategy (DP over the Prefixes of the String and the Pattern)

1. Let `dp[i][j]` be `true` when the first `i` characters of `s` are matched by the first `j` characters of `p`. `dp[0][0]` is true.
2. Empty string against a pattern: `dp[0][j]` is true when `p[j-1]` is `*` and the pattern without the pair `x*` matches, i.e. `dp[0][j - 2]`.
3. If `p[j-1]` is a normal character or `.`: `dp[i][j] = dp[i-1][j-1]` when it matches `s[i-1]`.
4. If `p[j-1]` is `*` (it repeats the element `p[j-2]`): either use it zero times, `dp[i][j-2]`, or use it once more when `p[j-2]` matches `s[i-1]`, `dp[i-1][j]`. The answer is `dp[m][n]`.

## Example walkthrough

- For `s = "aa"` and `p = "a*"`: `a*` matches `a` and then one more `a`, so `dp[2][2]` is true.
- Result: `true`. `"mississippi"` does not match `"mis*is*p*."`: `false`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every pair of prefixes is examined once.
- **Space Complexity:** `O(m * n)` — The table.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Look at the first pattern character; with a following `*`, either skip the pair or consume one matching character.

```java
public static boolean isMatchRecursive(String s, String p) {
    return match(s, p, 0, 0);
}

private static boolean match(String s, String p, int i, int j) {
    if (j == p.length()) return i == s.length();
    boolean first = i < s.length() && (p.charAt(j) == '.' || p.charAt(j) == s.charAt(i));
    if (j + 1 < p.length() && p.charAt(j + 1) == '*') return match(s, p, i, j + 2) || (first && match(s, p, i + 1, j));
    return first && match(s, p, i + 1, j + 1);
}
```

- **Time Complexity:** exponential
- **Space Complexity:** O(m + n)

### Approach 2: Top-Down Memoization

Cache the result for each `(i, j)` pair of positions.

```java
public static boolean isMatchMemo(String s, String p) {
    return matchMemo(s, p, 0, 0, new Boolean[s.length() + 1][p.length() + 1]);
}

private static boolean matchMemo(String s, String p, int i, int j, Boolean[][] memo) {
    if (j == p.length()) return i == s.length();
    if (memo[i][j] != null) return memo[i][j];
    boolean first = i < s.length() && (p.charAt(j) == '.' || p.charAt(j) == s.charAt(i));
    boolean result;
    if (j + 1 < p.length() && p.charAt(j + 1) == '*') result = matchMemo(s, p, i, j + 2, memo) || (first && matchMemo(s, p, i + 1, j, memo));
    else result = first && matchMemo(s, p, i + 1, j + 1, memo);
    return memo[i][j] = result;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 3: Java Regex Library (Reference)

Java's built-in engine supports the same `.` and `*` semantics for a whole-string match. Handy as a cross-check, but not what an interview asks for.

```java
public static boolean isMatchRegex(String s, String p) {
    return Pattern.matches(p, s);
}
```

- **Time Complexity:** engine dependent (can backtrack)
- **Space Complexity:** O(m + n)

### Approach 4: Bottom-Up Table (Optimized)

The prefix table described in the strategy. This is the version implemented in the repository.

```java
public static boolean isMatch(String s, String p) {
    int m = s.length(), n = p.length();
    boolean[][] dp = new boolean[m + 1][n + 1];
    dp[0][0] = true;
    for (int j = 2; j <= n; j++) dp[0][j] = p.charAt(j - 1) == '*' && dp[0][j - 2];
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++) {
            char pc = p.charAt(j - 1);
            if (pc == '*') {
                boolean zero = dp[i][j - 2];
                boolean more = (p.charAt(j - 2) == '.' || p.charAt(j - 2) == s.charAt(i - 1)) && dp[i - 1][j];
                dp[i][j] = zero || more;
            } else {
                dp[i][j] = (pc == '.' || pc == s.charAt(i - 1)) && dp[i - 1][j - 1];
            }
        }
    return dp[m][n];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | exponential | O(m + n) |
| 2 | Top-Down Memoization | O(m * n) | O(m * n) |
| 3 | Java Regex Library (Reference) | engine dependent (can backtrack) | O(m + n) |
| 4 | Bottom-Up Table (Optimized) | O(m * n) | O(m * n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### RegularExpressionMatching.java

```java
public class RegularExpressionMatching {

    public static void main(String[] args) {
        String[][] inputs = {{"aa", "a"}, {"aa", "a*"}, {"ab", ".*"}, {"mississippi", "mis*is*p*."}, {"aab", "c*a*b"}, {"", "a*"}, {"abc", "abc"}};
        for (String[] input : inputs) {
            System.out.println("Input  : s = \"" + input[0] + "\", p = \"" + input[1] + "\"");
            System.out.println("Output : " + isMatch(input[0], input[1]));
            System.out.println();
        }
    }

    public static boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int j = 2; j <= n; j++) {
            dp[0][j] = p.charAt(j - 1) == '*' && dp[0][j - 2];
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char pc = p.charAt(j - 1);
                if (pc == '*') {
                    boolean zeroTimes = dp[i][j - 2];
                    boolean moreTimes = matches(s.charAt(i - 1), p.charAt(j - 2)) && dp[i - 1][j];
                    dp[i][j] = zeroTimes || moreTimes;
                } else {
                    dp[i][j] = matches(s.charAt(i - 1), pc) && dp[i - 1][j - 1];
                }
            }
        }
        return dp[m][n];
    }

    private static boolean matches(char c, char pattern) {
        return pattern == '.' || pattern == c;
    }
}
```
