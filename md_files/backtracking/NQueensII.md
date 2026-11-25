# N-Queens II

The n-queens puzzle places `n` queens on an `n x n` chessboard so that no two queens attack each other. Given `n`, return the number of distinct solutions.

## Algorithm Strategy (Row-by-Row Backtracking with Bitmasks)

1. Place one queen per row. Keep three bit masks: occupied columns, occupied `/` diagonals and occupied `\` diagonals.
2. For the current row, the free squares are the bits not set in `cols | diag1 | diag2`.
3. For each free square, place the queen (set the bits, shifting the diagonal masks one step for the next row) and recurse.
4. When all `n` rows have a queen, count one solution.

## Example walkthrough

- For `n = 4` there are two solutions: columns `[1, 3, 0, 2]` and `[2, 0, 3, 1]`.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n!)` — The pruning makes it much smaller in practice.
- **Space Complexity:** `O(n)` — Recursion depth.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Backtracking with Boolean Arrays

Mark used columns and diagonals in arrays and recurse row by row.

```java
public static int arrays(int n) { return go(n, 0, new boolean[n], new boolean[2 * n], new boolean[2 * n]); }
private static int go(int n, int r, boolean[] c, boolean[] d1, boolean[] d2) {
    if (r == n) return 1; int cnt = 0; for (int i = 0; i < n; i++) { if (c[i] || d1[r + i] || d2[r - i + n]) continue; c[i] = d1[r + i] = d2[r - i + n] = true; cnt += go(n, r + 1, c, d1, d2); c[i] = d1[r + i] = d2[r - i + n] = false; } return cnt;
}
```

- **Time Complexity:** O(n!)
- **Space Complexity:** O(n)

### Approach 2: Backtracking with Bitmasks (Optimized)

The same search with the three constraints packed into integers. This is the version implemented in the repository.

```java
public static int bits(int n) { return go(n, 0, 0, 0, 0); }
private static int go(int n, int row, int cols, int d1, int d2) {
    if (row == n) return 1; int cnt = 0; int free = ((1 << n) - 1) & ~(cols | d1 | d2);
    while (free != 0) { int b = free & -free; free -= b; cnt += go(n, row + 1, cols | b, (d1 | b) << 1, (d2 | b) >> 1); } return cnt;
}
```

- **Time Complexity:** O(n!)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Backtracking with Boolean Arrays | O(n!) | O(n) |
| 2 | Backtracking with Bitmasks (Optimized) | O(n!) | O(n) |

## Source Code

### NQueensII.java

```java
public class NQueensII {

    public static void main(String[] args) {
        int[] inputs = {4, 1, 2, 6, 8};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + totalNQueens(n));
            System.out.println();
        }
    }

    public static int totalNQueens(int n) {
        if (n <= 0) {
            return 0;
        }
        return place(n, 0, 0, 0, 0);
    }

    private static int place(int n, int row, int cols, int diag1, int diag2) {
        if (row == n) {
            return 1;
        }
        int count = 0;
        int free = ((1 << n) - 1) & ~(cols | diag1 | diag2);
        while (free != 0) {
            int bit = free & -free;
            free -= bit;
            count += place(n, row + 1, cols | bit, (diag1 | bit) << 1, (diag2 | bit) >> 1);
        }
        return count;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: one queen per row removes the row conflict by construction. What is left to track is which columns and diagonals are taken. Each of those is a set of at most `2n` positions, so a single integer can hold it, and "free squares" becomes one bitwise expression. Diagonals shift one bit left or right on every new row.

**Input:** `n = 4`.

| Row | Placed column (first solution) |
|-----|--------------------------------|
| 0 | 1 |
| 1 | 3 |
| 2 | 0 |
| 3 | 2 |

The mirror image (`2, 0, 3, 1`) is the second solution; no other placement survives.

**Output:** `2`.
