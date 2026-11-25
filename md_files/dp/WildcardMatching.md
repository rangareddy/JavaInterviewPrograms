# Wildcard Matching

Given an input string `s` and a pattern `p`, implement wildcard matching with support for `?` (matches any single character) and `*` (matches any sequence of characters, including the empty one). The match must cover the entire string.

## Algorithm Strategy (Greedy with Backtracking on the Last Star)

1. Walk `s` with index `i` and `p` with index `j`. Remember the position of the last `*` (`star`) and the position in `s` where it started matching (`mark`).
2. If `p[j]` is `?` or equals `s[i]`, advance both.
3. If `p[j]` is `*`, record `star = j`, `mark = i` and advance only `j` (the star initially matches nothing).
4. On a mismatch with a recorded star, retry: set `j = star + 1`, `mark++`, `i = mark` (the star swallows one more character). Without a star, return `false`. After `s` ends, skip trailing stars in `p`; the match succeeds if `p` is exhausted.

## Example walkthrough

- For `s = "aa"` and `p = "a"`: after matching `a` the string still has a character but the pattern is over and there is no star.
- Result: `false`. For `"adceb"` and `"*a*b"` the stars absorb `dce`, giving `true`.

## Complexity Analysis

- **Time Complexity:** `O(m * n) worst case` — Usually close to linear; the backtracking is bounded by the string length per star.
- **Space Complexity:** `O(1)` — A few indexes.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Try the star as empty and as one more character. Exponential without memoisation.

```java
public static boolean recursive(String s, String p) { return go(s, p, 0, 0); }
private static boolean go(String s, String p, int i, int j) {
    if (j == p.length()) return i == s.length();
    if (p.charAt(j) == '*') return go(s, p, i, j + 1) || (i < s.length() && go(s, p, i + 1, j));
    return i < s.length() && (p.charAt(j) == '?' || p.charAt(j) == s.charAt(i)) && go(s, p, i + 1, j + 1);
}
```

- **Time Complexity:** O(2^(m+n))
- **Space Complexity:** O(m + n)

### Approach 2: 2D Dynamic Programming

`dp[i][j]` tells whether the first `i` characters match the first `j` pattern characters.

```java
public static boolean dp(String s, String p) {
    int m = s.length(), n = p.length(); boolean[][] d = new boolean[m + 1][n + 1]; d[0][0] = true;
    for (int j = 1; j <= n; j++) if (p.charAt(j - 1) == '*') d[0][j] = d[0][j - 1];
    for (int i = 1; i <= m; i++) for (int j = 1; j <= n; j++) { char c = p.charAt(j - 1); if (c == '*') d[i][j] = d[i][j - 1] || d[i - 1][j]; else d[i][j] = d[i - 1][j - 1] && (c == '?' || c == s.charAt(i - 1)); }
    return d[m][n];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 3: Greedy with Backtracking on the Last Star (Optimized)

Remember only the last star and extend it on a mismatch. This is the version implemented in the repository.

```java
public static boolean greedy(String s, String p) {
    int i = 0, j = 0, star = -1, mark = 0;
    while (i < s.length()) {
        if (j < p.length() && (p.charAt(j) == '?' || p.charAt(j) == s.charAt(i))) { i++; j++; }
        else if (j < p.length() && p.charAt(j) == '*') { star = j++; mark = i; }
        else if (star != -1) { j = star + 1; i = ++mark; }
        else return false;
    }
    while (j < p.length() && p.charAt(j) == '*') j++;
    return j == p.length();
}
```

- **Time Complexity:** O(m * n) worst, near linear typical
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(2^(m+n)) | O(m + n) |
| 2 | 2D Dynamic Programming | O(m * n) | O(m * n) |
| 3 | Greedy with Backtracking on the Last Star (Optimized) | O(m * n) worst, near linear typical | O(1) |

## Source Code

### WildcardMatching.java

```java
public class WildcardMatching {

    public static void main(String[] args) {
        String[][] inputs = {{"aa", "a"}, {"aa", "*"}, {"cb", "?a"}, {"adceb", "*a*b"}, {"acdcb", "a*c?b"}, {"", "***"}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", p = \"" + in[1] + "\"");
            System.out.println("Output : " + isMatch(in[0], in[1]));
            System.out.println();
        }
    }

    public static boolean isMatch(String s, String p) {
        int i = 0;
        int j = 0;
        int star = -1;
        int mark = 0;
        while (i < s.length()) {
            if (j < p.length() && (p.charAt(j) == '?' || p.charAt(j) == s.charAt(i))) {
                i++;
                j++;
            } else if (j < p.length() && p.charAt(j) == '*') {
                star = j++;
                mark = i;
            } else if (star != -1) {
                j = star + 1;
                i = ++mark;
            } else {
                return false;
            }
        }
        while (j < p.length() && p.charAt(j) == '*') {
            j++;
        }
        return j == p.length();
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: only the most recent `*` ever needs to be reconsidered. If a later mismatch occurs, letting that star absorb one more character is always at least as good as any choice involving an earlier star.

**Input:** `s = "adceb"`, `p = "*a*b"`.

| Step | `i` | `j` | Action |
|------|-----|-----|--------|
| 1 | 0 | 0 | `*` recorded (`star = 0`, `mark = 0`) |
| 2 | 0 | 1 | `a` matches `a` |
| 3 | 1 | 2 | `*` recorded (`star = 2`, `mark = 1`) |
| 4 | 1 | 3 | `b` vs `d` mismatch: retry, `mark = 2`, `i = 2` |
| 5 | 2, 3 | 3 | `b` vs `c`, `b` vs `e` mismatch: retry, `mark = 4`, `i = 4` |
| 6 | 4 | 3 | `b` matches `b`, `i = 5`, `j = 4` |

The string and the pattern are both consumed.

**Output for this input:** `true`. For the first sample in `main`, `"aa"` and `"a"`, the output is `false`.
