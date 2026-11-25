# Range Sum Query 2D - Immutable

Given a 2D matrix, answer many queries `sumRegion(row1, col1, row2, col2)` that return the sum of the elements inside the rectangle with upper-left corner `(row1, col1)` and lower-right corner `(row2, col2)`, each query in `O(1)`.

## Algorithm Strategy (2D Prefix Sums)

1. Build `prefix[i][j]`, the sum of the rectangle from `(0, 0)` to `(i - 1, j - 1)`, using one extra row and column of zeros.
2. Fill it with `prefix[i][j] = matrix[i-1][j-1] + prefix[i-1][j] + prefix[i][j-1] - prefix[i-1][j-1]`.
3. A query is then `prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]` (inclusion-exclusion).
4. Each query takes constant time after an `O(m * n)` build.

## Example walkthrough

- For the 5x5 sample matrix, `sumRegion(2, 1, 4, 3)` covers the rows 2 to 4 and columns 1 to 3.
- Result: `8`.

## Complexity Analysis

- **Time Complexity:** `O(m * n) build, O(1) query` — Inclusion-exclusion on four prefix values.
- **Space Complexity:** `O(m * n)` — The prefix table.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sum the Rectangle on Every Query

Add up every cell of the rectangle. O(rows * columns) per query.

```java
public static int direct(int[][] m, int r1, int c1, int r2, int c2) { int s = 0; for (int i = r1; i <= r2; i++) for (int j = c1; j <= c2; j++) s += m[i][j]; return s; }
```

- **Time Complexity:** O(m * n) per query
- **Space Complexity:** O(1)

### Approach 2: Row Prefix Sums

Keep prefix sums per row so a query sums one value per row.

```java
public static class RowPrefix {
    private final int[][] p;
    public RowPrefix(int[][] m) { p = new int[m.length][m[0].length + 1]; for (int i = 0; i < m.length; i++) for (int j = 0; j < m[0].length; j++) p[i][j + 1] = p[i][j] + m[i][j]; }
    public int sum(int r1, int c1, int r2, int c2) { int s = 0; for (int i = r1; i <= r2; i++) s += p[i][c2 + 1] - p[i][c1]; return s; }
}
```

- **Time Complexity:** O(m) per query
- **Space Complexity:** O(m * n)

### Approach 3: 2D Prefix Sums (Optimized)

One table, four lookups per query. This is the version implemented in the repository.

```java
public static class Prefix2D {
    private final int[][] p;
    public Prefix2D(int[][] m) { p = new int[m.length + 1][m[0].length + 1]; for (int i = 1; i <= m.length; i++) for (int j = 1; j <= m[0].length; j++) p[i][j] = m[i - 1][j - 1] + p[i - 1][j] + p[i][j - 1] - p[i - 1][j - 1]; }
    public int sum(int r1, int c1, int r2, int c2) { return p[r2 + 1][c2 + 1] - p[r1][c2 + 1] - p[r2 + 1][c1] + p[r1][c1]; }
}
```

- **Time Complexity:** O(1) per query
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sum the Rectangle on Every Query | O(m * n) per query | O(1) |
| 2 | Row Prefix Sums | O(m) per query | O(m * n) |
| 3 | 2D Prefix Sums (Optimized) | O(1) per query | O(m * n) |

## Source Code

### RangeSumQuery2DImmutable.java

```java
import java.util.Arrays;

public class RangeSumQuery2DImmutable {

    public static void main(String[] args) {
        int[][] matrix = {{3, 0, 1, 4, 2}, {5, 6, 3, 2, 1}, {1, 2, 0, 1, 5}, {4, 1, 0, 1, 7}, {1, 0, 3, 0, 5}};
        RangeSumQuery2DImmutable sums = new RangeSumQuery2DImmutable(matrix);
        int[][] queries = {{2, 1, 4, 3}, {1, 1, 2, 2}, {1, 2, 2, 4}, {0, 0, 4, 4}};
        for (int[] q : queries) {
            System.out.println("Input  : sumRegion(" + q[0] + ", " + q[1] + ", " + q[2] + ", " + q[3] + ")");
            System.out.println("Output : " + sums.sumRegion(q[0], q[1], q[2], q[3]));
            System.out.println();
        }
    }

    private final int[][] prefix;

    public RangeSumQuery2DImmutable(int[][] matrix) {
        int m = matrix == null ? 0 : matrix.length;
        int n = m == 0 ? 0 : matrix[0].length;
        prefix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefix[i][j] = matrix[i - 1][j - 1] + prefix[i - 1][j] + prefix[i][j - 1] - prefix[i - 1][j - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return prefix[row2 + 1][col2 + 1] - prefix[row1][col2 + 1] - prefix[row2 + 1][col1] + prefix[row1][col1];
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: `prefix[i][j]` is the sum of everything above and to the left of `(i, j)`. A rectangle is the big prefix rectangle minus the strip above, minus the strip to the left, plus the corner that was subtracted twice.

**Query:** `sumRegion(2, 1, 4, 3)` on the 5x5 sample.

| Term | Meaning |
|------|---------|
| `prefix[5][4]` | rows 0 to 4, columns 0 to 3 |
| `- prefix[2][4]` | minus rows 0 to 1 |
| `- prefix[5][1]` | minus column 0 |
| `+ prefix[2][1]` | add back the corner counted twice |

**Output:** `8`.
