# Set Matrix Zeroes

Given an `m x n` integer matrix, if an element is 0, set its entire row and column to 0. Do it in place.

## Algorithm Strategy (Use the First Row and Column as Markers)

1. Remember in two booleans whether the first row and the first column originally contain a 0, because they will be used as storage.
2. For every cell `(i, j)` outside the first row and column that is 0, mark its row and column by setting `matrix[i][0] = 0` and `matrix[0][j] = 0`.
3. For every cell outside the first row and column, set it to 0 if its row marker or its column marker is 0.
4. Finally zero the first row and/or the first column if the booleans say they originally had a 0.

## Example walkthrough

- For `[[1,1,1],[1,0,1],[1,1,1]]`: the 0 at (1,1) marks `matrix[1][0]` and `matrix[0][1]`.
- Zeroing by markers gives `[[1,0,1],[0,0,0],[1,0,1]]`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every cell is visited a constant number of times.
- **Space Complexity:** `O(1)` — The matrix itself stores the markers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Copy of the Matrix

Read the zeros from an unmodified copy and write the zeroed rows and columns into the original.

```java
public static void setZeroesCopy(int[][] matrix) {
    int rows = matrix.length, cols = matrix[0].length;
    int[][] copy = new int[rows][];
    for (int i = 0; i < rows; i++) copy[i] = matrix[i].clone();
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (copy[i][j] == 0) {
                for (int k = 0; k < cols; k++) matrix[i][k] = 0;
                for (int k = 0; k < rows; k++) matrix[k][j] = 0;
            }
        }
    }
}
```

- **Time Complexity:** O(m * n * (m + n))
- **Space Complexity:** O(m * n)

### Approach 2: Row and Column Flags

Record which rows and columns contain a zero in two boolean arrays, then zero those rows and columns.

```java
public static void setZeroesFlags(int[][] matrix) {
    int rows = matrix.length, cols = matrix[0].length;
    boolean[] zeroRow = new boolean[rows], zeroCol = new boolean[cols];
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (matrix[i][j] == 0) { zeroRow[i] = true; zeroCol[j] = true; }
        }
    }
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (zeroRow[i] || zeroCol[j]) matrix[i][j] = 0;
        }
    }
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m + n)

### Approach 3: Sentinel Marking

First pass: for every original zero, overwrite the non-zero cells of its row and column with a sentinel value that cannot occur in the input. Second pass: turn every sentinel into 0. Constant extra space, but it requires a value that is guaranteed to be absent (here `Integer.MIN_VALUE`).

```java
public static void setZeroesSentinel(int[][] matrix) {
    int rows = matrix.length, cols = matrix[0].length;
    final int MARK = Integer.MIN_VALUE;
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (matrix[i][j] != 0) continue;
            for (int k = 0; k < cols; k++) if (matrix[i][k] != 0) matrix[i][k] = MARK;
            for (int k = 0; k < rows; k++) if (matrix[k][j] != 0) matrix[k][j] = MARK;
        }
    }
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++) if (matrix[i][j] == MARK) matrix[i][j] = 0;
}
```

- **Time Complexity:** O(m * n * (m + n))
- **Space Complexity:** O(1)

### Approach 4: BitSet Flags

Record the rows and columns that contain a zero in two `BitSet`s (one bit per row or column) and clear those rows and columns afterwards. Same as the boolean flags but eight times smaller.

```java
public static void setZeroesBitSet(int[][] matrix) {
    int rows = matrix.length, cols = matrix[0].length;
    BitSet zeroRows = new BitSet(rows), zeroCols = new BitSet(cols);
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (matrix[i][j] == 0) { zeroRows.set(i); zeroCols.set(j); }
        }
    }
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++) if (zeroRows.get(i) || zeroCols.get(j)) matrix[i][j] = 0;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O((m + n) / 8) bytes

### Approach 5: Sets of Rows and Columns

Collect the indexes of the rows and columns that contain a zero in two `HashSet`s. Clear exactly those rows and columns, which is efficient when only a few of them contain zeros.

```java
public static void setZeroesSets(int[][] matrix) {
    Set<Integer> rowsToClear = new HashSet<>(), colsToClear = new HashSet<>();
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[i][j] == 0) { rowsToClear.add(i); colsToClear.add(j); }
        }
    }
    for (int row : rowsToClear) Arrays.fill(matrix[row], 0);
    for (int col : colsToClear) for (int[] row : matrix) row[col] = 0;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m + n) worst case

### Approach 6: Constant Space with matrix[0][0] as a Shared Marker

The classic compact version: the first row and first column store the flags of the other rows and columns; `matrix[0][0]` serves as the flag of row 0 and one extra boolean as the flag of column 0. The cells are processed from the bottom-right to the top-left so the markers are not destroyed too early.

```java
public static void setZeroesCompact(int[][] matrix) {
    int rows = matrix.length, cols = matrix[0].length;
    boolean firstColumnZero = false;
    for (int i = 0; i < rows; i++) {
        if (matrix[i][0] == 0) firstColumnZero = true;
        for (int j = 1; j < cols; j++) {
            if (matrix[i][j] == 0) { matrix[i][0] = 0; matrix[0][j] = 0; }
        }
    }
    for (int i = rows - 1; i >= 0; i--) {
        for (int j = cols - 1; j >= 1; j--) {
            if (matrix[i][0] == 0 || matrix[0][j] == 0) matrix[i][j] = 0;
        }
        if (firstColumnZero) matrix[i][0] = 0;
    }
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(1)

### Approach 7: First Row and Column as Markers (Optimized)

Use the first row and the first column of the matrix itself as the flag arrays (after remembering whether they originally contained a zero). This is the version implemented in the repository.

```java
public static void setZeroes(int[][] matrix) {
    int rows = matrix.length, cols = matrix[0].length;
    boolean firstRowZero = false, firstColZero = false;
    for (int j = 0; j < cols; j++) if (matrix[0][j] == 0) firstRowZero = true;
    for (int i = 0; i < rows; i++) if (matrix[i][0] == 0) firstColZero = true;

    for (int i = 1; i < rows; i++) {
        for (int j = 1; j < cols; j++) {
            if (matrix[i][j] == 0) { matrix[i][0] = 0; matrix[0][j] = 0; }
        }
    }
    for (int i = 1; i < rows; i++) {
        for (int j = 1; j < cols; j++) {
            if (matrix[i][0] == 0 || matrix[0][j] == 0) matrix[i][j] = 0;
        }
    }
    if (firstRowZero) for (int j = 0; j < cols; j++) matrix[0][j] = 0;
    if (firstColZero) for (int i = 0; i < rows; i++) matrix[i][0] = 0;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Copy of the Matrix | O(m * n * (m + n)) | O(m * n) |
| 2 | Row and Column Flags | O(m * n) | O(m + n) |
| 3 | Sentinel Marking | O(m * n * (m + n)) | O(1) |
| 4 | BitSet Flags | O(m * n) | O((m + n) / 8) bytes |
| 5 | Sets of Rows and Columns | O(m * n) | O(m + n) worst case |
| 6 | Constant Space with matrix[0][0] as a Shared Marker | O(m * n) | O(1) |
| 7 | First Row and Column as Markers (Optimized) | O(m * n) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### SetMatrixZeroes.java

```java
import java.util.Arrays;

public class SetMatrixZeroes {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}},
                {{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}}
        };
        for (int[][] matrix : inputs) {
            System.out.println("Input  : " + Arrays.deepToString(matrix));
            setZeroes(matrix);
            System.out.println("Output : " + Arrays.deepToString(matrix));
            System.out.println();
        }
    }

    public static void setZeroes(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean firstRowZero = false;
        boolean firstColZero = false;

        for (int j = 0; j < cols; j++) {
            if (matrix[0][j] == 0) {
                firstRowZero = true;
            }
        }
        for (int i = 0; i < rows; i++) {
            if (matrix[i][0] == 0) {
                firstColZero = true;
            }
        }

        // Use the first row and column as markers
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        if (firstRowZero) {
            for (int j = 0; j < cols; j++) {
                matrix[0][j] = 0;
            }
        }
        if (firstColZero) {
            for (int i = 0; i < rows; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}
```
