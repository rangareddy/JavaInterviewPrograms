# Diagonal Traverse

Given an `m x n` matrix `mat`, return all of its elements in diagonal order, starting at the top-left and moving up-right on the first diagonal, then down-left on the next, alternating.

## Algorithm Strategy (Anti-Diagonal Indexing)

1. Elements on one anti-diagonal share the same sum `i + j`, which runs from 0 to `m + n - 2`.
2. For each diagonal `d`, collect the cells `(i, d - i)` that are inside the matrix.
3. Even-numbered diagonals are read bottom-left to top-right (row decreasing), odd-numbered ones top-right to bottom-left.
4. Concatenate the diagonals.

## Example walkthrough

- For `[[1,2,3],[4,5,6],[7,8,9]]` the diagonals are `[1]`, `[2,4]`, `[7,5,3]`, `[6,8]`, `[9]`, read in alternating directions.
- Result: `[1, 2, 4, 7, 5, 3, 6, 8, 9]`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Each element is visited once.
- **Space Complexity:** `O(1)` — Apart from the output.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Simulate the Zigzag Walk

Move up-right or down-left cell by cell and turn at the borders.

```java
public static int[] simulate(int[][] a) {
    int m = a.length, n = a[0].length; int[] out = new int[m * n]; int i = 0, j = 0; boolean up = true;
    for (int k = 0; k < out.length; k++) {
        out[k] = a[i][j];
        if (up) { if (j == n - 1) { i++; up = false; } else if (i == 0) { j++; up = false; } else { i--; j++; } }
        else { if (i == m - 1) { j++; up = true; } else if (j == 0) { i++; up = true; } else { i++; j--; } }
    }
    return out;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(1)

### Approach 2: Anti-Diagonal Indexing (Optimized)

Read each anti-diagonal and alternate the direction. This is the version implemented in the repository.

```java
public static int[] diagonals(int[][] a) {
    int m = a.length, n = a[0].length; int[] out = new int[m * n]; int k = 0;
    for (int d = 0; d < m + n - 1; d++) {
        int lo = Math.max(0, d - n + 1), hi = Math.min(m - 1, d);
        if (d % 2 == 0) for (int i = hi; i >= lo; i--) out[k++] = a[i][d - i]; else for (int i = lo; i <= hi; i++) out[k++] = a[i][d - i];
    }
    return out;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Simulate the Zigzag Walk | O(m * n) | O(1) |
| 2 | Anti-Diagonal Indexing (Optimized) | O(m * n) | O(1) |

## Source Code

### DiagonalTraverse.java

```java
import java.util.Arrays;

public class DiagonalTraverse {

    public static void main(String[] args) {
        int[][][] inputs = {
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{1, 2}, {3, 4}},
            {{1, 2, 3, 4}},
            {{1}, {2}, {3}}
        };
        for (int[][] mat : inputs) {
            System.out.println("Input  : mat = " + Arrays.deepToString(mat));
            System.out.println("Output : " + Arrays.toString(findDiagonalOrder(mat)));
            System.out.println();
        }
    }

    public static int[] findDiagonalOrder(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return new int[0];
        }
        int m = mat.length;
        int n = mat[0].length;
        int[] result = new int[m * n];
        int index = 0;
        for (int d = 0; d < m + n - 1; d++) {
            int rowStart = Math.max(0, d - n + 1);
            int rowEnd = Math.min(m - 1, d);
            if (d % 2 == 0) {
                for (int i = rowEnd; i >= rowStart; i--) {
                    result[index++] = mat[i][d - i];
                }
            } else {
                for (int i = rowStart; i <= rowEnd; i++) {
                    result[index++] = mat[i][d - i];
                }
            }
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: every cell on one anti-diagonal has the same `i + j`. Listing diagonals by that sum and flipping the reading direction on odd ones gives the zigzag without any turning logic.

**Input:** `[[1, 2, 3], [4, 5, 6], [7, 8, 9]]`.

| `d` | Cells (row, col) | Direction | Values |
|-----|------------------|-----------|--------|
| 0 | (0,0) | up | 1 |
| 1 | (0,1), (1,0) | down | 2, 4 |
| 2 | (2,0), (1,1), (0,2) | up | 7, 5, 3 |
| 3 | (1,2), (2,1) | down | 6, 8 |
| 4 | (2,2) | up | 9 |

**Output:** `[1, 2, 4, 7, 5, 3, 6, 8, 9]`.
