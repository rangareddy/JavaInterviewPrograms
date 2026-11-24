# Maximal Square

Given an `m x n` binary matrix filled with `'0'` and `'1'`, find the largest square containing only `'1'` and return its area.

## Algorithm Strategy (DP on the Side Length)

1. Let `dp[i][j]` be the side of the largest all-ones square whose bottom-right corner is `(i, j)`.
2. If the cell is `'0'`, `dp[i][j] = 0`.
3. Otherwise `dp[i][j] = 1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])`: the square can only be as large as the smallest of its three neighbours allows.
4. Track the maximum side; the answer is `side * side`. A single rolling row is enough.

## Example walkthrough

- For `[[1,0,1,0,0],[1,0,1,1,1],[1,1,1,1,1],[1,0,0,1,0]]` the largest block of ones is 2x2, with its bottom-right corner at row 2, column 3 (or column 4).
- Result: area `4`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Each cell is computed once.
- **Space Complexity:** `O(n)` — One row of side lengths, plus the previous diagonal value.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

From every cell try growing a square and verify all of its cells.

```java
public static int bruteForce(char[][] m) {
    if (m == null || m.length == 0) return 0;
    int best = 0;
    for (int i = 0; i < m.length; i++) for (int j = 0; j < m[0].length; j++) {
        int s = 1;
        while (i + s <= m.length && j + s <= m[0].length && allOnes(m, i, j, s)) { best = Math.max(best, s); s++; }
    }
    return best * best;
}
private static boolean allOnes(char[][] m, int i, int j, int s) {
    for (int a = i; a < i + s; a++) for (int b = j; b < j + s; b++) if (m[a][b] != '1') return false;
    return true;
}
```

- **Time Complexity:** O(m * n * min(m, n)^3)
- **Space Complexity:** O(1)

### Approach 2: Full DP Table

Store the side length for every cell.

```java
public static int table(char[][] m) {
    if (m == null || m.length == 0) return 0;
    int R = m.length, C = m[0].length, best = 0; int[][] dp = new int[R + 1][C + 1];
    for (int i = 1; i <= R; i++) for (int j = 1; j <= C; j++)
        if (m[i - 1][j - 1] == '1') { dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])); best = Math.max(best, dp[i][j]); }
    return best * best;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 3: Rolling Row DP (Optimized)

Keep one row and the previous diagonal value. This is the version implemented in the repository.

```java
public static int rolling(char[][] m) {
    if (m == null || m.length == 0) return 0;
    int n = m[0].length, best = 0; int[] dp = new int[n + 1];
    for (char[] row : m) {
        int diag = 0;
        for (int j = 1; j <= n; j++) {
            int up = dp[j];
            if (row[j - 1] == '1') { dp[j] = 1 + Math.min(Math.min(dp[j], dp[j - 1]), diag); best = Math.max(best, dp[j]); } else dp[j] = 0;
            diag = up;
        }
    }
    return best * best;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(m * n * min(m, n)^3) | O(1) |
| 2 | Full DP Table | O(m * n) | O(m * n) |
| 3 | Rolling Row DP (Optimized) | O(m * n) | O(n) |

## Source Code

### MaximalSquare.java

```java
import java.util.Arrays;

public class MaximalSquare {

    public static void main(String[] args) {
        char[][][] inputs = {
            {{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}},
            {{'0', '1'}, {'1', '0'}},
            {{'0'}},
            {{'1', '1'}, {'1', '1'}}
        };
        for (char[][] matrix : inputs) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrix));
            System.out.println("Output : " + maximalSquare(matrix));
            System.out.println();
        }
    }

    public static int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int n = matrix[0].length;
        int[] dp = new int[n + 1];
        int side = 0;
        for (char[] row : matrix) {
            int diagonal = 0;
            for (int j = 1; j <= n; j++) {
                int above = dp[j];
                if (row[j - 1] == '1') {
                    dp[j] = 1 + Math.min(Math.min(dp[j], dp[j - 1]), diagonal);
                    side = Math.max(side, dp[j]);
                } else {
                    dp[j] = 0;
                }
                diagonal = above;
            }
        }
        return side * side;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: a square ending at a cell is as big as its three neighbours (above, left, diagonal) allow, plus one. Storing the side length turns a search for squares into a simple recurrence.

**Input:** `[[1,0,1,0,0],[1,0,1,1,1],[1,1,1,1,1],[1,0,0,1,0]]`.

| Row | Side lengths `dp` after the row |
|-----|--------------------------------|
| 0 | 1 0 1 0 0 |
| 1 | 1 0 1 1 1 |
| 2 | 1 1 1 2 2 |
| 3 | 1 0 0 1 0 |

The largest side seen is 2, at row 2, columns 3 and 4.

**Output:** `4`.
