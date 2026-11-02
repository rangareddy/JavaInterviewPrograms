# Spiral Matrix

Given an `m x n` matrix, return all of its elements in spiral order (clockwise, starting at the top-left cell).

## Algorithm Strategy (Shrinking Boundaries)

1. Keep four boundaries: `top`, `bottom`, `left` and `right`.
2. Walk the top row from left to right, then increment `top`.
3. Walk the right column from top to bottom, then decrement `right`.
4. If rows remain, walk the bottom row from right to left and decrement `bottom`; if columns remain, walk the left column from bottom to top and increment `left`.
5. Repeat while `top <= bottom` and `left <= right`.

## Example walkthrough

- For `[[1,2,3],[4,5,6],[7,8,9]]`: top row 1,2,3; right column 6,9; bottom row 8,7; left column 4; then the inner cell 5.
- Result: `[1, 2, 3, 6, 9, 8, 7, 4, 5]`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every cell is visited once.
- **Space Complexity:** `O(1)` — Excluding the output list.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Simulation with Direction Vectors and a Visited Matrix

Walk through the matrix, turning right whenever the next cell is outside the matrix or already visited.

```java
public static List<Integer> spiralOrderVisited(int[][] matrix) {
    List<Integer> result = new ArrayList<>();
    int rows = matrix.length, cols = matrix[0].length;
    boolean[][] visited = new boolean[rows][cols];
    int[] dr = {0, 1, 0, -1}, dc = {1, 0, -1, 0};
    int r = 0, c = 0, d = 0;
    for (int i = 0; i < rows * cols; i++) {
        result.add(matrix[r][c]);
        visited[r][c] = true;
        int nr = r + dr[d], nc = c + dc[d];
        if (nr < 0 || nr >= rows || nc < 0 || nc >= cols || visited[nr][nc]) {
            d = (d + 1) % 4;
            nr = r + dr[d];
            nc = c + dc[d];
        }
        r = nr;
        c = nc;
    }
    return result;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 2: Shrinking Boundaries (Optimized)

Keep the top, bottom, left and right boundaries and peel one layer at a time; no visited matrix is needed. This is the version implemented in the repository.

```java
public static List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> result = new ArrayList<>();
    int top = 0, bottom = matrix.length - 1, left = 0, right = matrix[0].length - 1;
    while (top <= bottom && left <= right) {
        for (int j = left; j <= right; j++) result.add(matrix[top][j]);
        top++;
        for (int i = top; i <= bottom; i++) result.add(matrix[i][right]);
        right--;
        if (top <= bottom) {
            for (int j = right; j >= left; j--) result.add(matrix[bottom][j]);
            bottom--;
        }
        if (left <= right) {
            for (int i = bottom; i >= top; i--) result.add(matrix[i][left]);
            left++;
        }
    }
    return result;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(1) auxiliary

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Simulation with Direction Vectors and a Visited Matrix | O(m * n) | O(m * n) |
| 2 | Shrinking Boundaries (Optimized) | O(m * n) | O(1) auxiliary |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### SpiralMatrix.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpiralMatrix {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
                {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}}
        };
        for (int[][] matrix : inputs) {
            System.out.println("Input  : " + Arrays.deepToString(matrix));
            System.out.println("Output : " + spiralOrder(matrix));
            System.out.println();
        }
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) {
            return result;
        }

        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) {
                result.add(matrix[top][j]);
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;

            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    result.add(matrix[bottom][j]);
                }
                bottom--;
            }

            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }
        }
        return result;
    }
}
```
