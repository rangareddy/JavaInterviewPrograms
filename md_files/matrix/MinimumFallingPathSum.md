# Minimum Falling Path Sum

Given an `n x n` array of integers `matrix`, return the minimum sum of any falling path through `matrix`. A falling path starts at any element in the first row and chooses the element in the next row that is either directly below or diagonally left or right.

## Algorithm Strategy (Best Path Ending at Each Cell)

1. Let `best[c]` be the minimum falling-path sum ending at column `c` of the current row; for the first row it is the row itself.
2. For each next row, a cell can be reached from the columns `c - 1`, `c` and `c + 1` above it.
3. So the new value at `c` is `matrix[r][c] + min(best[c - 1], best[c], best[c + 1])`, ignoring out-of-range columns.
4. After the last row, the answer is the minimum of `best`.

## Example walkthrough

- For `[[2, 1, 3], [6, 5, 4], [7, 8, 9]]`: row 1 becomes `[7, 6, 5]`, row 2 becomes `[13, 13, 14]`.
- Result: `13`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Every cell is processed once.
- **Space Complexity:** `O(n)` — One rolling row.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion over All Paths

Try every path from every top cell. Exponential, because each cell is reached through many routes.

```java
public static int paths(int[][] m) { int best = Integer.MAX_VALUE; for (int c = 0; c < m.length; c++) best = Math.min(best, go(m, 0, c)); return best; }
static int go(int[][] m, int r, int c) { if (r == m.length - 1) return m[r][c]; int best = Integer.MAX_VALUE; for (int d = -1; d <= 1; d++) { int nc = c + d; if (nc >= 0 && nc < m.length) best = Math.min(best, go(m, r + 1, nc)); } return m[r][c] + best; }
```

- **Time Complexity:** O(3^n)
- **Space Complexity:** O(n)

### Approach 2: Rolling-Row DP (Optimized)

Keep only the best sums of the previous row. This is the version implemented in the repository.

```java
public static int rolling(int[][] m) { int n = m.length; int[] b = m[0].clone(); for (int r = 1; r < n; r++) { int[] nx = new int[n]; for (int c = 0; c < n; c++) { int mn = b[c]; if (c > 0) mn = Math.min(mn, b[c - 1]); if (c < n - 1) mn = Math.min(mn, b[c + 1]); nx[c] = m[r][c] + mn; } b = nx; } int a = Integer.MAX_VALUE; for (int v : b) a = Math.min(a, v); return a; }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion over All Paths | O(3^n) | O(n) |
| 2 | Rolling-Row DP (Optimized) | O(n^2) | O(n) |

## Source Code

### MinimumFallingPathSum.java

```java
import java.util.Arrays;

public class MinimumFallingPathSum {

    public static void main(String[] args) {
        int[][][] inputs = {{{2, 1, 3}, {6, 5, 4}, {7, 8, 9}}, {{-19, 57}, {-40, -5}}, {{-48}}, {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}};
        for (int[][] matrix : inputs) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrix));
            System.out.println("Output : " + minFallingPathSum(matrix));
            System.out.println();
        }
    }

    public static int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int[] best = matrix[0].clone();
        for (int r = 1; r < n; r++) {
            int[] next = new int[n];
            for (int c = 0; c < n; c++) {
                int min = best[c];
                if (c > 0) {
                    min = Math.min(min, best[c - 1]);
                }
                if (c < n - 1) {
                    min = Math.min(min, best[c + 1]);
                }
                next[c] = matrix[r][c] + min;
            }
            best = next;
        }
        int answer = Integer.MAX_VALUE;
        for (int value : best) {
            answer = Math.min(answer, value);
        }
        return answer;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the cheapest path to a cell depends only on the cheapest paths to the three cells that can lead to it, all in the previous row. So one row of results is enough.

**Input:** `matrix = [[2, 1, 3], [6, 5, 4], [7, 8, 9]]`.

| Row | Best sums per column |
|-----|----------------------|
| 0 | [2, 1, 3] |
| 1 | [6 + 1, 5 + 1, 4 + 1] = [7, 6, 5] |
| 2 | [7 + 6, 8 + 5, 9 + 5] = [13, 13, 14] |

The smallest in the last row is 13.

**Output:** `13`.
