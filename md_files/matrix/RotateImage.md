# Rotate Image

Rotate an `n x n` matrix by 90 degrees clockwise, in place (do not allocate another matrix).

## Algorithm Strategy (Transpose, Then Reverse Each Row)

1. A clockwise rotation moves `matrix[i][j]` to `matrix[j][n - 1 - i]`, which equals a transpose followed by a horizontal flip.
2. Transpose: swap `matrix[i][j]` with `matrix[j][i]` for all `j > i`.
3. Reverse every row of the transposed matrix.

## Example walkthrough

- For `[[1,2,3],[4,5,6],[7,8,9]]`: the transpose is `[[1,4,7],[2,5,8],[3,6,9]]`.
- Reversing each row gives `[[7,4,1],[8,5,2],[9,6,3]]`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Every cell is touched a constant number of times.
- **Space Complexity:** `O(1)` — Swaps are done in place.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Extra Matrix

Write every element `matrix[i][j]` to its new place `result[j][n - 1 - i]` and copy back. Easy, but the problem forbids a second matrix.

```java
public static void rotateExtra(int[][] matrix) {
    int n = matrix.length;
    int[][] result = new int[n][n];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) result[j][n - 1 - i] = matrix[i][j];
    }
    for (int i = 0; i < n; i++) matrix[i] = result[i];
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Approach 2: Four-Way Cyclic Swap

Rotate the cells layer by layer, moving four cells at a time: top-left to top-right to bottom-right to bottom-left and back.

```java
public static void rotateCycles(int[][] matrix) {
    int n = matrix.length;
    for (int i = 0; i < n / 2; i++) {
        for (int j = i; j < n - 1 - i; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[n - 1 - j][i];
            matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
            matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
            matrix[j][n - 1 - i] = temp;
        }
    }
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 3: Flip Vertically, Then Transpose

Reverse the order of the rows (flip upside down) and then transpose. Same two simple steps as "transpose then reverse each row", in the other order.

```java
public static void rotateFlipTranspose(int[][] matrix) {
    int n = matrix.length;
    for (int i = 0; i < n / 2; i++) {
        int[] temp = matrix[i];
        matrix[i] = matrix[n - 1 - i];
        matrix[n - 1 - i] = temp;
    }
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            int temp = matrix[i][j]; matrix[i][j] = matrix[j][i]; matrix[j][i] = temp;
        }
    }
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 4: Reflect over the Anti-Diagonal, Then Flip Vertically

Reflecting over the anti-diagonal sends `(i, j)` to `(n - 1 - j, n - 1 - i)`; reversing the order of the rows afterwards lands every element on its clockwise-rotated position `(j, n - 1 - i)`.

```java
public static void rotateAntiTranspose(int[][] matrix) {
    int n = matrix.length;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n - 1 - i; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[n - 1 - j][n - 1 - i];
            matrix[n - 1 - j][n - 1 - i] = temp;
        }
    }
    for (int i = 0; i < n / 2; i++) {
        int[] temp = matrix[i];
        matrix[i] = matrix[n - 1 - i];
        matrix[n - 1 - i] = temp;
    }
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 5: Recursive Layer Rotation

Rotate the outer layer with four-way swaps, then recurse on the inner `(n - 2) x (n - 2)` matrix. The cycle-swap method written recursively.

```java
public static void rotateRecursive(int[][] matrix) {
    rotateLayer(matrix, 0, matrix.length - 1);
}

private static void rotateLayer(int[][] m, int first, int last) {
    if (first >= last) return;
    for (int k = 0; k < last - first; k++) {
        int temp = m[first][first + k];
        m[first][first + k] = m[last - k][first];
        m[last - k][first] = m[last][last - k];
        m[last][last - k] = m[first + k][last];
        m[first + k][last] = temp;
    }
    rotateLayer(m, first + 1, last - 1);
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n) recursion stack

### Approach 6: Streams: Read Each Column Bottom to Top

The first row of the rotated image is the first column read from bottom to top, the second row the second column, and so on. Build the rows with streams and copy them back.

```java
public static void rotateStreams(int[][] matrix) {
    int n = matrix.length;
    int[][] rotated = IntStream.range(0, n)
            .mapToObj(column -> IntStream.range(0, n).map(row -> matrix[n - 1 - row][column]).toArray())
            .toArray(int[][]::new);
    for (int i = 0; i < n; i++) matrix[i] = rotated[i];
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Approach 7: Transpose, Then Reverse Each Row (Optimized)

A 90-degree clockwise rotation equals a transpose followed by a horizontal flip; both steps are simple in-place loops. This is the version implemented in the repository.

```java
public static void rotate(int[][] matrix) {
    int n = matrix.length;
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            int temp = matrix[i][j]; matrix[i][j] = matrix[j][i]; matrix[j][i] = temp;
        }
    }
    for (int[] row : matrix) {
        for (int l = 0, r = n - 1; l < r; l++, r--) {
            int temp = row[l]; row[l] = row[r]; row[r] = temp;
        }
    }
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Extra Matrix | O(n^2) | O(n^2) |
| 2 | Four-Way Cyclic Swap | O(n^2) | O(1) |
| 3 | Flip Vertically, Then Transpose | O(n^2) | O(1) |
| 4 | Reflect over the Anti-Diagonal, Then Flip Vertically | O(n^2) | O(1) |
| 5 | Recursive Layer Rotation | O(n^2) | O(n) recursion stack |
| 6 | Streams: Read Each Column Bottom to Top | O(n^2) | O(n^2) |
| 7 | Transpose, Then Reverse Each Row (Optimized) | O(n^2) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### RotateImage.java

```java
import java.util.Arrays;

public class RotateImage {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
                {{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}}
        };
        for (int[][] matrix : inputs) {
            System.out.println("Input  : " + Arrays.deepToString(matrix));
            rotate(matrix);
            System.out.println("Output : " + Arrays.deepToString(matrix));
            System.out.println();
        }
    }

    public static void rotate(int[][] matrix) {
        if (matrix == null || matrix.length <= 1) {
            return;
        }

        int n = matrix.length;
        // 1. Transpose
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        // 2. Reverse every row
        for (int[] row : matrix) {
            for (int left = 0, right = n - 1; left < right; left++, right--) {
                int temp = row[left];
                row[left] = row[right];
                row[right] = temp;
            }
        }
    }
}
```
