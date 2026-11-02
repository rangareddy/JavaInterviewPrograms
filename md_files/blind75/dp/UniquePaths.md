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
