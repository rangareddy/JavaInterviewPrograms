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
