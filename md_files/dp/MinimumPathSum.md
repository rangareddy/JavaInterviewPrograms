# Minimum Path Sum

Given an `m x n` grid of non-negative numbers, find a path from the top-left corner to the bottom-right corner that minimizes the sum of the numbers along the path. You can only move right or down at each step.

## Algorithm Strategy (Grid DP with a Single Row)

1. Let `dp[i][j]` be the minimum path sum to reach cell `(i, j)`. It is the cell value plus the cheaper of the two ways in: from above `dp[i-1][j]` or from the left `dp[i][j-1]`.
2. The first row can only be reached from the left and the first column only from above, so they are running sums.
3. Row `i` depends only on row `i - 1` and the cell to the left, so a single array of length `n` is enough: `dp[j] = grid[i][j] + min(dp[j], dp[j-1])`, where `dp[j]` still holds the value from the row above before it is overwritten.
4. The answer is `dp[n - 1]` after the last row.

## Example walkthrough

- For `[[1, 3, 1], [1, 5, 1], [4, 2, 1]]` the first row becomes `[1, 4, 5]`.
- Row 2 gives `[2, 7, 6]` and row 3 gives `[6, 8, 7]`. The cheapest path is `1 -> 3 -> 1 -> 1 -> 1` with sum `7`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every cell is computed once.
- **Space Complexity:** `O(n)` — One row.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Recursion

Try both moves from every cell. It recomputes the same sub-grids again and again.

```java
public static int minPathSumBrute(int[][] grid) {
    return walk(grid, 0, 0);
}

private static int walk(int[][] grid, int i, int j) {
    if (i == grid.length - 1 && j == grid[0].length - 1) return grid[i][j];
    int down = i + 1 < grid.length ? walk(grid, i + 1, j) : Integer.MAX_VALUE;
    int right = j + 1 < grid[0].length ? walk(grid, i, j + 1) : Integer.MAX_VALUE;
    return grid[i][j] + Math.min(down, right);
}
```

- **Time Complexity:** O(2^(m + n))
- **Space Complexity:** O(m + n) recursion

### Approach 2: Full 2D Table

Store the best sum for every cell, which makes the recurrence easy to read and lets you rebuild the path.

```java
public static int minPathSumTable(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] dp = new int[m][n];
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (i == 0 && j == 0) dp[i][j] = grid[i][j];
            else if (i == 0) dp[i][j] = dp[i][j - 1] + grid[i][j];
            else if (j == 0) dp[i][j] = dp[i - 1][j] + grid[i][j];
            else dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
        }
    }
    return dp[m - 1][n - 1];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 3: Single-Row DP (Optimized)

Reuse one array as the "row above" and update it in place, left to right. This is the version implemented in the repository.

```java
public static int minPathSum(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    int[] dp = new int[n];
    dp[0] = grid[0][0];
    for (int j = 1; j < n; j++) {
        dp[j] = dp[j - 1] + grid[0][j];
    }
    for (int i = 1; i < m; i++) {
        dp[0] += grid[i][0];
        for (int j = 1; j < n; j++) {
            dp[j] = grid[i][j] + Math.min(dp[j], dp[j - 1]);
        }
    }
    return dp[n - 1];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Recursion | O(2^(m + n)) | O(m + n) recursion |
| 2 | Full 2D Table | O(m * n) | O(m * n) |
| 3 | Single-Row DP (Optimized) | O(m * n) | O(n) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### MinimumPathSum.java

```java
import java.util.Arrays;

public class MinimumPathSum {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}},
                {{1, 2, 3}, {4, 5, 6}},
                {{7}}
        };
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + minPathSum(grid));
            System.out.println();
        }
    }

    public static int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];
        dp[0] = grid[0][0];
        for (int j = 1; j < n; j++) {
            dp[j] = dp[j - 1] + grid[0][j];
        }
        for (int i = 1; i < m; i++) {
            dp[0] += grid[i][0];
            for (int j = 1; j < n; j++) {
                dp[j] = grid[i][j] + Math.min(dp[j], dp[j - 1]);
            }
        }
        return dp[n - 1];
    }
}
```
