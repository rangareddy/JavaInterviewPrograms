# Sparse Matrix Multiplication

Given two sparse matrices `mat1` of size `m x k` and `mat2` of size `k x n`, return the product `mat1 x mat2`. You may assume the multiplication is always possible.

## Algorithm Strategy (Skip Zeros in the First Matrix)

1. Create the result matrix `m x n` of zeros.
2. Loop over every cell `mat1[i][p]`; if it is zero, skip the whole inner loop, because it contributes nothing.
3. Otherwise loop over `j` and add `mat1[i][p] * mat2[p][j]` to `result[i][j]`, skipping zero entries of `mat2` as well.
4. Return the result.

## Example walkthrough

- For `[[1,0,0],[-1,0,3]]` times `[[7,0,0],[0,0,0],[0,0,1]]` only three of the six entries of `mat1` are non-zero.
- Result: `[[7, 0, 0], [-7, 0, 3]]`.

## Complexity Analysis

- **Time Complexity:** `O(m * k * n) worst case, O(non-zeros) typical` — Zero entries cost only the check.
- **Space Complexity:** `O(m * n)` — The result matrix.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Standard Triple Loop

Compute every dot product, including all the zero terms.

```java
public static int[][] standard(int[][] a, int[][] b) {
    int m = a.length, k = b.length, n = b[0].length; int[][] r = new int[m][n];
    for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) { int s = 0; for (int p = 0; p < k; p++) s += a[i][p] * b[p][j]; r[i][j] = s; }
    return r;
}
```

- **Time Complexity:** O(m * k * n)
- **Space Complexity:** O(m * n)

### Approach 2: Skip Zero Entries (Optimized)

Skip the inner loop when `mat1[i][p]` is zero. This is the version implemented in the repository.

```java
public static int[][] skipZeros(int[][] a, int[][] b) {
    int m = a.length, k = b.length, n = b[0].length; int[][] r = new int[m][n];
    for (int i = 0; i < m; i++) for (int p = 0; p < k; p++) { if (a[i][p] == 0) continue; for (int j = 0; j < n; j++) if (b[p][j] != 0) r[i][j] += a[i][p] * b[p][j]; }
    return r;
}
```

- **Time Complexity:** O(m * k * n) worst case
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Standard Triple Loop | O(m * k * n) | O(m * n) |
| 2 | Skip Zero Entries (Optimized) | O(m * k * n) worst case | O(m * n) |

## Source Code

### SparseMatrixMultiplication.java

```java
import java.util.Arrays;

public class SparseMatrixMultiplication {

    public static void main(String[] args) {
        int[][][] a = {{{1, 0, 0}, {-1, 0, 3}}, {{0}}, {{1, 2}, {3, 4}}};
        int[][][] b = {{{7, 0, 0}, {0, 0, 0}, {0, 0, 1}}, {{0}}, {{0, 1}, {1, 0}}};
        for (int i = 0; i < a.length; i++) {
            System.out.println("Input  : mat1 = " + Arrays.deepToString(a[i]) + ", mat2 = " + Arrays.deepToString(b[i]));
            System.out.println("Output : " + Arrays.deepToString(multiply(a[i], b[i])));
            System.out.println();
        }
    }

    public static int[][] multiply(int[][] mat1, int[][] mat2) {
        if (mat1 == null || mat2 == null || mat1.length == 0 || mat2.length == 0) {
            return new int[0][0];
        }
        int m = mat1.length;
        int k = mat2.length;
        int n = mat2[0].length;
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int p = 0; p < k; p++) {
                if (mat1[i][p] == 0) {
                    continue;
                }
                for (int j = 0; j < n; j++) {
                    if (mat2[p][j] != 0) {
                        result[i][j] += mat1[i][p] * mat2[p][j];
                    }
                }
            }
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a zero in `mat1` adds nothing to any output cell, so the whole inner loop for that entry can be skipped. Only the non-zero entries do work.

**Input:** `mat1 = [[1, 0, 0], [-1, 0, 3]]`, `mat2 = [[7, 0, 0], [0, 0, 0], [0, 0, 1]]`.

| Entry used | Row of `mat2` | Adds to |
|------------|---------------|---------|
| `mat1[0][0] = 1` | [7, 0, 0] | result row 0: [7, 0, 0] |
| `mat1[1][0] = -1` | [7, 0, 0] | result row 1: [-7, 0, 0] |
| `mat1[1][2] = 3` | [0, 0, 1] | result row 1: [-7, 0, 3] |

**Output:** `[[7, 0, 0], [-7, 0, 3]]`.
