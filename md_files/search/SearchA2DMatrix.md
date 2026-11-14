# Search a 2D Matrix

Given an `m x n` integer matrix where every row is sorted in ascending order and the first integer of each row is greater than the last integer of the previous row, return `true` if `target` is in the matrix. The algorithm must run in `O(log(m * n))` time.

## Algorithm Strategy (Binary Search over the Flattened Matrix)

1. Because each row starts after the previous one ends, reading the matrix row by row gives one sorted sequence of `m * n` numbers.
2. Binary-search the virtual positions `0 .. m * n - 1`; the position `mid` lives in row `mid / n` and column `mid % n`.
3. Compare that cell with the target: equal means found, a smaller cell means search the upper half, a larger cell the lower half.
4. No flattened copy is built; only the index arithmetic maps a position to a cell.

## Example walkthrough

- For the matrix `[[1,3,5,7],[10,11,16,20],[23,30,34,60]]` and target 3 (12 cells): `mid = 5` is 11, too large; `mid = 2` is 5, too large; `mid = 0` is 1, too small; `mid = 1` is 3.
- Result: `true`. Target 13 is never found: `false`.

## Complexity Analysis

- **Time Complexity:** `O(log(m * n))` — The search range halves each step.
- **Space Complexity:** `O(1)` — Index variables only.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Linear Scan

Check every cell of the matrix. Ignores the sorted structure.

```java
public static boolean searchMatrixLinear(int[][] matrix, int target) {
    for (int[] row : matrix) for (int value : row) if (value == target) return true;
    return false;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(1)

### Approach 2: Staircase Search from the Top-Right Corner

Start in the top-right corner: if the cell is larger than the target move left, if it is smaller move down. Every step removes a row or a column. Works for any matrix sorted by rows and columns, but needs `O(m + n)` steps.

```java
public static boolean searchMatrixStaircase(int[][] matrix, int target) {
    if (matrix.length == 0) return false;
    int row = 0, col = matrix[0].length - 1;
    while (row < matrix.length && col >= 0) {
        if (matrix[row][col] == target) return true;
        if (matrix[row][col] > target) col--; else row++;
    }
    return false;
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(1)

### Approach 3: Binary Search the Row, Then the Column

First binary-search the row whose range can contain the target (compare with the first and last element of the row), then binary-search inside that row. Two separate searches instead of the virtual flattening.

```java
public static boolean searchMatrixTwoSteps(int[][] matrix, int target) {
    if (matrix.length == 0 || matrix[0].length == 0) return false;
    int top = 0, bottom = matrix.length - 1, last = matrix[0].length - 1;
    while (top <= bottom) {
        int mid = (top + bottom) / 2;
        if (target > matrix[mid][last]) top = mid + 1;
        else if (target < matrix[mid][0]) bottom = mid - 1;
        else return Arrays.binarySearch(matrix[mid], target) >= 0;
    }
    return false;
}
```

- **Time Complexity:** O(log m + log n)
- **Space Complexity:** O(1)

### Approach 4: Binary Search over the Flattened Matrix (Optimized)

Treat the matrix as one sorted array of `m * n` numbers and map each position to a cell with `/` and `%`. This is the version implemented in the repository.

```java
public static boolean searchMatrix(int[][] matrix, int target) {
    if (matrix.length == 0 || matrix[0].length == 0) return false;
    int cols = matrix[0].length, low = 0, high = matrix.length * cols - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        int value = matrix[mid / cols][mid % cols];
        if (value == target) return true;
        if (value < target) low = mid + 1; else high = mid - 1;
    }
    return false;
}
```

- **Time Complexity:** O(log(m * n))
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Linear Scan | O(m * n) | O(1) |
| 2 | Staircase Search from the Top-Right Corner | O(m + n) | O(1) |
| 3 | Binary Search the Row, Then the Column | O(log m + log n) | O(1) |
| 4 | Binary Search over the Flattened Matrix (Optimized) | O(log(m * n)) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: the rows are sorted and every row starts above the previous row's last value, so reading the matrix row by row gives one sorted list of `rows * cols` values. Binary search runs on the virtual indices `0 .. rows*cols - 1` and converts each `mid` to a cell with `row = mid / cols`, `col = mid % cols`; no copy is made. This is O(log(rows * cols)) time and O(1) space.

**Input:** `matrix = [[1, 3, 5, 7], [10, 11, 16, 20], [23, 30, 34, 60]]` (3 rows, 4 columns, 12 cells), `target = 3`.

| Step | low | high | mid | row = mid / 4 | col = mid % 4 | value | Compare with 3 | Next |
|------|-----|------|-----|---------------|---------------|-------|----------------|------|
| 1 | 0 | 11 | 5 | 1 | 1 | 11 | too large | high = 4 |
| 2 | 0 | 4 | 2 | 0 | 2 | 5 | too large | high = 1 |
| 3 | 0 | 1 | 0 | 0 | 0 | 1 | too small | low = 1 |
| 4 | 1 | 1 | 1 | 0 | 1 | 3 | equal | return true |

```text
flat index :  0  1  2  3 | 4   5   6   7 |  8   9  10  11
value      :  1  3  5  7 | 10  11  16  20 | 23  30  34  60
```

For target `13` the probes are `mid = 5` (11, low = 6), `mid = 8` (23, high = 7), `mid = 6` (16, high = 5); then `low > high` and the result is `false`.

**Edge cases.** `null`, zero rows or zero columns return `false` immediately. A target below the first or above the last cell (0 or 61) is never matched and ends with an empty range. The rows must satisfy the two ordering rules; the code uses `matrix[0].length` as the column count for all rows.

**Result:** `true` for target 3 and `false` for 13, matching the class output.

## Source Code

### SearchA2DMatrix.java

```java
import java.util.Arrays;

public class SearchA2DMatrix {

    public static void main(String[] args) {
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        int[] targets = {3, 13, 60, 1, 0, 61};
        for (int target : targets) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrix) + ", target = " + target);
            System.out.println("Output : " + searchMatrix(matrix, target));
            System.out.println();
        }
    }

    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int low = 0;
        int high = rows * cols - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int value = matrix[mid / cols][mid % cols];
            if (value == target) {
                return true;
            } else if (value < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }
}
```
