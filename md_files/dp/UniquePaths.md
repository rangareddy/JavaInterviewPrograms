# Unique Paths

A robot starts at the top-left corner of an `m x n` grid and wants to reach the bottom-right corner, moving only down or right. Return the number of unique paths.

## Algorithm Strategy (Combinatorics)

1. Guard first: a grid with `m <= 0` or `n <= 0` has no cells, so there are 0 paths.
2. Every path consists of exactly `m - 1` moves down and `n - 1` moves right, in any order. Choosing which of the `m + n - 2` moves go down gives `C(m + n - 2, m - 1)` paths.
3. Compute the binomial coefficient with `k = min(m, n) - 1` multiplications: `result = result * (m + n - 2 - k + i) / i` for `i = 1 .. k`. The value is an integer after every step, so the integer division is exact.
4. A `long` holds the intermediate products. The result must fit an `int`; if it does not, the method returns `-1` instead of a wrong number.

## Example walkthrough

- For `m = 3` and `n = 2`: `k = 1`, so `result = 1 * (3 + 2 - 2 - 1 + 1) / 1 = 3`, which is `C(3, 1)`.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(min(m, n))` — One multiplication and division per step.
- **Space Complexity:** `O(1)` — A few variables.

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

## Source Code

### UniquePaths.java

```java
public class UniquePaths {

    public static void main(String[] args) {
        int[][] inputs = {{3, 2}, {3, 7}, {3, 3}, {1, 1}, {0, 5}, {23, 12}, {100, 100}};
        for (int[] input : inputs) {
            System.out.println("Input  : m = " + input[0] + ", n = " + input[1]);
            System.out.println("Output : " + uniquePaths(input[0], input[1]));
            System.out.println();
        }
    }

    /**
     * Counts the paths from the top-left to the bottom-right corner of an {@code m x n} grid.
     *
     * @param m the number of rows
     * @param n the number of columns
     * @return the number of paths; {@code 0} if the grid is empty; {@code -1} if the count does not fit an {@code int}
     */
    public static int uniquePaths(int m, int n) {
        // Guard Clause: an empty grid has no path
        if (m <= 0 || n <= 0) {
            return 0;
        }

        long total = (long) m + n - 2;
        int k = Math.min(m, n) - 1;
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (total - k + i) / i;       // stays an integer at every step
            if (result > Integer.MAX_VALUE) {
                return -1;                                // the count only grows, so it can never fit an int
            }
        }
        return (int) result;
    }
}
```

## Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

The idea: every path is a sequence of `m - 1` moves down and `n - 1` moves right, in any order, so the number of paths is the number of ways to choose where the downs go: the binomial coefficient `C(m + n - 2, k)` with `k = min(m, n) - 1`. The loop builds it one factor at a time, and the result is an exact integer after every step.

**Input:** `m = 3`, `n = 7`. The path has `m + n - 2 = 8` moves, and `k = min(3, 7) - 1 = 2`.

| Step `i` | Calculation `result * (8 - 2 + i) / i` | `result` |
|----------|-----------------------------------------|----------|
| start | | 1 |
| 1 | `1 * 7 / 1` | 7 |
| 2 | `7 * 8 / 2` | 28 |

The result is `C(8, 2) = 28`. A grid with 3 rows and 7 columns therefore has 28 paths, and only 2 multiplications were needed instead of filling 21 cells.

**Edge cases.** `m <= 0` or `n <= 0` returns 0 through the guard clause. A `1 x n` or `m x 1` grid has `k = 0`, so the loop does not run and the answer is 1 (one straight line). `m = 23, n = 12` gives `193536720`, which still fits an `int`. For `100 x 100` the count has 58 digits; the loop notices that the intermediate value exceeds `Integer.MAX_VALUE` and returns `-1` instead of a wrong number. The intermediate products are kept in a `long`: they stay below 2^62, because the check stops the loop as soon as the value passes 2^31.

**Result:** `28` for `m = 3, n = 7`, matching the class output.
