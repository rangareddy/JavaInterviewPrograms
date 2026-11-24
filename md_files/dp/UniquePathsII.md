# Unique Paths II

A robot starts at the top-left cell of an `m x n` grid and can only move right or down. Some cells are obstacles, marked `1`. Return the number of distinct paths to the bottom-right cell that avoid every obstacle.

## Algorithm Strategy (Grid DP with a Rolling Row)

1. `ways[j]` holds the number of paths to column `j` of the current row.
2. If the start or the end is an obstacle, the answer is 0.
3. For each cell: if it is an obstacle, set `ways[j] = 0`; otherwise add the paths from the left, `ways[j] += ways[j - 1]` (the value already in `ways[j]` came from above).
4. The answer is `ways[n - 1]` after the last row.

## Example walkthrough

- For the 3x3 grid with one obstacle in the centre: the first row is `1 1 1`, the second row is `1 0 1`, the last row is `1 1 2`.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Each cell is processed once.
- **Space Complexity:** `O(n)` — One row of counters is reused.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Count paths by trying a move right and a move down from every cell. Exponential without a cache.

```java
public static int recursive(int[][] g) {
    if (g == null || g.length == 0 || g[0].length == 0) return 0;
    return go(g, 0, 0);
}
private static int go(int[][] g, int i, int j) {
    if (i >= g.length || j >= g[0].length || g[i][j] == 1) return 0;
    if (i == g.length - 1 && j == g[0].length - 1) return 1;
    return go(g, i + 1, j) + go(g, i, j + 1);
}
```

- **Time Complexity:** O(2^(m+n))
- **Space Complexity:** O(m + n)

### Approach 2: Full 2D Table

`dp[i][j]` is the number of paths to that cell.

```java
public static int table(int[][] g) {
    if (g == null || g.length == 0 || g[0].length == 0 || g[0][0] == 1) return 0;
    int m = g.length, n = g[0].length;
    int[][] dp = new int[m][n];
    dp[0][0] = 1;
    for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) {
        if (g[i][j] == 1) { dp[i][j] = 0; continue; }
        if (i > 0) dp[i][j] += dp[i - 1][j];
        if (j > 0) dp[i][j] += dp[i][j - 1];
    }
    return dp[m - 1][n - 1];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 3: Single Rolling Row (Optimized)

Keep one row of counters. This is the version implemented in the repository.

```java
public static int rolling(int[][] g) {
    if (g == null || g.length == 0 || g[0].length == 0 || g[0][0] == 1) return 0;
    int n = g[0].length; int[] w = new int[n]; w[0] = 1;
    for (int[] row : g) for (int j = 0; j < n; j++) {
        if (row[j] == 1) w[j] = 0; else if (j > 0) w[j] += w[j - 1];
    }
    return w[n - 1];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(2^(m+n)) | O(m + n) |
| 2 | Full 2D Table | O(m * n) | O(m * n) |
| 3 | Single Rolling Row (Optimized) | O(m * n) | O(n) |

## Source Code

### UniquePathsII.java

```java
import java.util.Arrays;

public class UniquePathsII {

    public static void main(String[] args) {
        int[][][] inputs = {
            {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}},
            {{0, 1}, {0, 0}},
            {{1}},
            {{0}}
        };
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + uniquePathsWithObstacles(grid));
            System.out.println();
        }
    }

    public static int uniquePathsWithObstacles(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0 || grid[0][0] == 1) {
            return 0;
        }
        int n = grid[0].length;
        int[] ways = new int[n];
        ways[0] = 1;
        for (int[] row : grid) {
            for (int j = 0; j < n; j++) {
                if (row[j] == 1) {
                    ways[j] = 0;
                } else if (j > 0) {
                    ways[j] += ways[j - 1];
                }
            }
        }
        return ways[n - 1];
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: `ways[j]` already holds the paths from the row above, so adding `ways[j - 1]` (the cell to the left, just updated) gives the paths into the current cell. An obstacle simply resets the counter to 0.

**Input:** `grid = [[0,0,0],[0,1,0],[0,0,0]]`.

| Row | `ways` after the row |
|-----|----------------------|
| start | [1, 0, 0] |
| 0 | [1, 1, 1] |
| 1 (obstacle in the middle) | [1, 0, 1] |
| 2 | [1, 1, 2] |

**Output:** `2`.
