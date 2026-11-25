# Strange Printer

There is a strange printer with two rules: it can only print a sequence of the same character each time, and at each turn it can print new characters starting from and ending at any place, covering the original characters. Given a string `s`, return the minimum number of turns the printer needs to print it.

## Algorithm Strategy (Interval DP: Share a Turn with a Matching Character)

1. Let `dp[i][j]` be the minimum turns for the substring `s[i..j]`.
2. A safe start: print `s[i]` alone, then the rest: `dp[i][j] = dp[i + 1][j] + 1`.
3. Better: if `s[k] == s[i]` for some `k` in `(i, j]`, the first print of `s[i]` can stretch to `k` for free, so `dp[i][j] = min(dp[i][j], dp[i + 1][k - 1] + dp[k][j])`.
4. Fill by decreasing `i` and increasing `j`; the answer is `dp[0][n - 1]`.

## Example walkthrough

- For `aaabbb`: `a` at index 0 matches indexes 1 and 2, which cost nothing extra, so the left block is one turn; the `bbb` block is another.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n^3)` — `n^2` intervals, each scanning up to `n` split points.
- **Space Complexity:** `O(n^2)` — The DP table.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Breadth-First Search over Printed States

Start from a blank board and try every possible print until the target appears. Exact, but the number of states explodes.

```java
public static int bfs(String t) { int n = t.length(); if (n == 0) return 0; String start = new String(new char[n]).replace('\0', '.'); java.util.Set<String> seen = new java.util.HashSet<>(); java.util.Deque<String> q = new java.util.ArrayDeque<>(); q.add(start); seen.add(start); int turns = 0; while (!q.isEmpty()) { for (int sz = q.size(); sz > 0; sz--) { String cur = q.poll(); if (cur.equals(t)) return turns; for (int l = 0; l < n; l++) for (int r = l; r < n; r++) for (char c : t.toCharArray()) { char[] a = cur.toCharArray(); for (int x = l; x <= r; x++) a[x] = c; String nx = new String(a); if (seen.add(nx)) q.add(nx); } } turns++; } return -1; }
```

- **Time Complexity:** exponential
- **Space Complexity:** exponential

### Approach 2: Interval DP (Optimized)

Reuse the best answer for each substring and merge turns of equal characters. This is the version implemented in the repository.

```java
public static int dp(String s) { int n = s.length(); if (n == 0) return 0; int[][] d = new int[n][n]; for (int i = n - 1; i >= 0; i--) { d[i][i] = 1; for (int j = i + 1; j < n; j++) { d[i][j] = d[i + 1][j] + 1; for (int k = i + 1; k <= j; k++) if (s.charAt(k) == s.charAt(i)) { int in = k == i + 1 ? 0 : d[i + 1][k - 1]; d[i][j] = Math.min(d[i][j], in + d[k][j]); } } } return d[0][n - 1]; }
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(n^2)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Breadth-First Search over Printed States | exponential | exponential |
| 2 | Interval DP (Optimized) | O(n^3) | O(n^2) |

## Source Code

### StrangePrinter.java

```java
public class StrangePrinter {

    public static void main(String[] args) {
        String[] inputs = {"aaabbb", "aba", "abcabc", "a", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + strangePrinter(s));
            System.out.println();
        }
    }

    public static int strangePrinter(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = dp[i + 1][j] + 1;
                for (int k = i + 1; k <= j; k++) {
                    if (s.charAt(k) == s.charAt(i)) {
                        int inner = k == i + 1 ? 0 : dp[i + 1][k - 1];
                        dp[i][j] = Math.min(dp[i][j], inner + dp[k][j]);
                    }
                }
            }
        }
        return dp[0][n - 1];
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the printer can paint over earlier work, so the first character of a substring can be printed as one long stroke that is later overwritten in the middle. If `s[k] == s[i]`, one stroke reaches from `i` to `k` at no extra cost, and the part strictly between them is solved independently.

**Input:** `s = "aaabbb"`.

| Substring | Best | Reason |
|-----------|------|--------|
| `aaa` | 1 | all equal, one stroke |
| `bbb` | 1 | all equal, one stroke |
| `aaabbb` | 2 | the first two `a` strokes merge into the third (`dp[0][5] = dp[1][5] = dp[2][5]`), which costs one turn for `a` and one for `bbb` |

**Output:** `2`.
