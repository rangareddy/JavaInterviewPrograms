# Perfect Squares

Given an integer `n`, return the least number of perfect square numbers (1, 4, 9, 16, ...) that sum to `n`.

## Algorithm Strategy (DP over Square Numbers)

1. Let `dp[i]` be the fewest squares that sum to `i`, with `dp[0] = 0`.
2. For each `i` from 1 to `n` try every square `s * s <= i`.
3. `dp[i] = min(dp[i - s * s] + 1)` over all such `s`.
4. The answer is `dp[n]`.

## Example walkthrough

- For `n = 12`: `12 = 4 + 4 + 4`, three squares, and no pair of squares sums to 12.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n * sqrt(n))` — For each `i`, about sqrt(i) squares are tried.
- **Space Complexity:** `O(n)` — The dp array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Try every square as the first term. Exponential.

```java
public static int recursive(int n) {
    if (n == 0) return 0; int best = Integer.MAX_VALUE;
    for (int s = 1; s * s <= n; s++) best = Math.min(best, recursive(n - s * s) + 1);
    return best;
}
```

- **Time Complexity:** O(n^(sqrt n))
- **Space Complexity:** O(n)

### Approach 2: Bottom-Up DP (Optimized)

Fill `dp` from 1 to `n`. This is the version implemented in the repository.

```java
public static int dp(int n) {
    int[] d = new int[n + 1];
    for (int i = 1; i <= n; i++) { d[i] = Integer.MAX_VALUE; for (int s = 1; s * s <= i; s++) d[i] = Math.min(d[i], d[i - s * s] + 1); }
    return d[n];
}
```

- **Time Complexity:** O(n sqrt n)
- **Space Complexity:** O(n)

### Approach 3: Lagrange's Four-Square Theorem

Every number is a sum of at most four squares, so check for 1, 2 or 3, otherwise 4.

```java
public static int math(int n) {
    if (isSq(n)) return 1;
    for (int i = 1; i * i <= n; i++) if (isSq(n - i * i)) return 2;
    int m = n; while (m % 4 == 0) m /= 4;
    return m % 8 == 7 ? 4 : 3;
}
private static boolean isSq(int x) { int r = (int) Math.sqrt(x); return r * r == x; }
```

- **Time Complexity:** O(sqrt n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(n^(sqrt n)) | O(n) |
| 2 | Bottom-Up DP (Optimized) | O(n sqrt n) | O(n) |
| 3 | Lagrange's Four-Square Theorem | O(sqrt n) | O(1) |

## Source Code

### PerfectSquares.java

```java
public class PerfectSquares {

    public static void main(String[] args) {
        int[] inputs = {12, 13, 1, 7, 100, 43};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + numSquares(n));
            System.out.println();
        }
    }

    public static int numSquares(int n) {
        if (n <= 0) {
            return 0;
        }
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int s = 1; s * s <= i; s++) {
                dp[i] = Math.min(dp[i], dp[i - s * s] + 1);
            }
        }
        return dp[n];
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the best way to build `i` ends with some square `s * s`, so `dp[i]` is one more than the best way to build what is left.

**Input:** `n = 12`.

| `i` | Best last square | `dp[i]` |
|-----|------------------|---------|
| 1 | 1 | 1 |
| 2 | 1 | 2 |
| 3 | 1 | 3 |
| 4 | 4 | 1 |
| 8 | 4 | 2 |
| 12 | 4 (`dp[8] + 1`) | 3 |

**Output:** `3`.
