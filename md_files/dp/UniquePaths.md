# Unique Paths

A robot starts at the top-left corner of an `m x n` grid and wants to reach the bottom-right corner, moving only down or right. Return the number of unique paths.

## Algorithm Strategy (Dynamic Programming with One Row)

1. Every cell can only be entered from the cell above or from the cell on its left, so `paths[i][j] = paths[i - 1][j] + paths[i][j - 1]`.
2. The first row and the first column have exactly 1 path to each cell (straight line).
3. Only the previous row is needed, so keep a single array `row` of length `n`, initialised with 1s.
4. For every following row, update `row[j] += row[j - 1]` from left to right; `row[n - 1]` is the answer.

## Example walkthrough

- For `m = 3` and `n = 2`: the row starts as `[1, 1]`; after row 2 it is `[1, 2]`; after row 3 it is `[1, 3]`.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every cell is updated once.
- **Space Complexity:** `O(n)` — A single row.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Recursion

The paths to a cell are the paths to the cell above plus the paths to the cell on the left. Exponential, because cells are recomputed.

```java
public static int uniquePathsRecursive(int m, int n) {
    if (m == 1 || n == 1) return 1;
    return uniquePathsRecursive(m - 1, n) + uniquePathsRecursive(m, n - 1);
}
```

- **Time Complexity:** O(2^(m + n))
- **Space Complexity:** O(m + n)

### Approach 2: 2D DP Table

Fill a table where each cell is the sum of the cell above and the cell to the left.

```java
public static int uniquePathsTable(int m, int n) {
    int[][] dp = new int[m][n];
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            dp[i][j] = (i == 0 || j == 0) ? 1 : dp[i - 1][j] + dp[i][j - 1];
        }
    }
    return dp[m - 1][n - 1];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 3: Single Row DP

A row only depends on the previous row, so one array updated left to right is enough. This is the version implemented in the repository.

```java
public static int uniquePaths(int m, int n) {
    int[] row = new int[n];
    Arrays.fill(row, 1);
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) row[j] += row[j - 1];
    }
    return row[n - 1];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(n)

### Approach 4: Combinatorics (Optimized)

Every path consists of `m - 1` moves down and `n - 1` moves right in any order, so the answer is the binomial coefficient `C(m + n - 2, m - 1)`. Compute it iteratively with a `long` to avoid overflow.

```java
public static int uniquePathsMath(int m, int n) {
    long result = 1;
    int k = Math.min(m, n) - 1;
    for (int i = 1; i <= k; i++) {
        result = result * (m + n - 2 - k + i) / i;      // stays an integer at every step
    }
    return (int) result;
}
```

- **Time Complexity:** O(min(m, n))
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Recursion | O(2^(m + n)) | O(m + n) |
| 2 | 2D DP Table | O(m * n) | O(m * n) |
| 3 | Single Row DP | O(m * n) | O(n) |
| 4 | Combinatorics (Optimized) | O(min(m, n)) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above. The version implemented in this repository is Approach 3.

## Source Code

### UniquePaths.java

```java
import java.util.Arrays;

public class UniquePaths {

    public static void main(String[] args) {
        int[][] inputs = {{3, 2}, {3, 7}, {3, 3}, {1, 1}};
        for (int[] input : inputs) {
            System.out.println("Input  : m = " + input[0] + ", n = " + input[1]);
            System.out.println("Output : " + uniquePaths(input[0], input[1]));
            System.out.println();
        }
    }

    public static int uniquePaths(int m, int n) {
        int[] row = new int[n];
        Arrays.fill(row, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                row[j] += row[j - 1];
            }
        }
        return row[n - 1];
    }
}
```
