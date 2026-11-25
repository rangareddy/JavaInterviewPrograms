# Count Square Submatrices with All Ones

Given an `m x n` matrix of ones and zeros, return how many square submatrices have all ones.

## Algorithm Strategy (Square Size DP)

1. Let `dp[i][j]` be the side of the largest all-ones square whose bottom-right corner is `(i, j)`.
2. If the cell is 0, `dp[i][j] = 0`.
3. Otherwise `dp[i][j] = 1 + min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])` (cells outside the matrix count as 0).
4. A largest square of side `s` at that corner means exactly `s` squares end there (sides 1 to `s`), so the answer is the sum of all `dp` values.

## Example walkthrough

- For `[[0, 1, 1, 1], [1, 1, 1, 1], [0, 1, 1, 1]]`: the dp table is `[[0, 1, 1, 1], [1, 1, 2, 2], [0, 1, 2, 3]]`.
- Its sum is `3 + 6 + 6 = 15`.
- Result: `15`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every cell is computed once.
- **Space Complexity:** `O(n)` — One rolling row would do; this version updates a copy of the row above in place.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Check Every Square

For each top-left corner and side length, verify that all cells are ones.

```java
public static int brute(int[][] m) { int rows = m.length, cols = rows == 0 ? 0 : m[0].length, c = 0; for (int i = 0; i < rows; i++) for (int j = 0; j < cols; j++) for (int s = 1; i + s <= rows && j + s <= cols; s++) { boolean ok = true; for (int x = i; x < i + s && ok; x++) for (int y = j; y < j + s; y++) if (m[x][y] == 0) { ok = false; break; } if (ok) c++; } return c; }
```

- **Time Complexity:** O(m * n * min(m,n)^3)
- **Space Complexity:** O(1)

### Approach 2: Largest Square Ending at Each Cell (Optimized)

Reuse the three neighbouring answers to get the side of the biggest square in constant time. This is the version implemented in the repository.

```java
public static int dp(int[][] m) { int rows = m.length; if (rows == 0) return 0; int cols = m[0].length; int[][] d = new int[rows][cols]; int t = 0; for (int i = 0; i < rows; i++) for (int j = 0; j < cols; j++) if (m[i][j] == 1) { d[i][j] = (i == 0 || j == 0) ? 1 : 1 + Math.min(d[i - 1][j], Math.min(d[i][j - 1], d[i - 1][j - 1])); t += d[i][j]; } return t; }
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Check Every Square | O(m * n * min(m,n)^3) | O(1) |
| 2 | Largest Square Ending at Each Cell (Optimized) | O(m * n) | O(m * n) |

## Source Code

### CountSquareSubmatricesWithAllOnes.java

```java
import java.util.Arrays;

public class CountSquareSubmatricesWithAllOnes {

    public static void main(String[] args) {
        int[][][] inputs = {{{0, 1, 1, 1}, {1, 1, 1, 1}, {0, 1, 1, 1}}, {{1, 0, 1}, {1, 1, 0}, {1, 1, 0}}, {{1}}, {{0, 0}, {0, 0}}};
        for (int[][] matrix : inputs) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrix));
            System.out.println("Output : " + countSquares(matrix));
            System.out.println();
        }
    }

    public static int countSquares(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows][cols];
        int total = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == 1) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                    }
                    total += dp[i][j];
                }
            }
        }
        return total;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a square of side `s` ending at `(i, j)` needs squares of side `s - 1` ending at the cells above, to the left and diagonally up-left. So the largest side there is one more than the smallest of those three. And if the largest side is `s`, there are exactly `s` squares ending at that corner (sides 1 to `s`).

**Input:** `matrix = [[0, 1, 1, 1], [1, 1, 1, 1], [0, 1, 1, 1]]`.

| Row | `dp` row | Row sum |
|-----|----------|---------|
| 0 | [0, 1, 1, 1] | 3 |
| 1 | [1, 1, 2, 2] | 6 |
| 2 | [0, 1, 2, 3] | 6 |

Total: `3 + 6 + 6 = 15`.

**Output:** `15`.
