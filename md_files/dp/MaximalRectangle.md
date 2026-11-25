# Maximal Rectangle

Given a `rows x cols` binary matrix of `'0'` and `'1'` characters, find the largest rectangle containing only `'1'`s and return its area.

## Algorithm Strategy (Largest Rectangle in a Histogram per Row)

1. Keep an array `height[j]`: the number of consecutive `'1'`s ending at the current row in column `j` (reset to 0 on a `'0'`).
2. After updating the heights for a row, the largest rectangle whose bottom edge is on that row is the largest rectangle in the histogram `height`.
3. Compute that with a monotonic stack: pop bars taller than the current one and compute the area using the popped height and the width up to the new stack top.
4. The answer is the maximum area over all rows.

## Example walkthrough

- For the 4x5 sample matrix, the heights after row 2 are `3, 1, 3, 2, 2`, and the largest histogram rectangle is `2 * 3 = 6` (columns 2 to 4 with height 2).
- Result: `6`.

## Complexity Analysis

- **Time Complexity:** `O(rows * cols)` — One linear stack pass per row.
- **Space Complexity:** `O(cols)` — The heights and the stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force over All Rectangles

Try every pair of corners and check that all cells are ones.

```java
public static int brute(char[][] m) {
    int R = m.length, C = m[0].length, best = 0;
    for (int r1 = 0; r1 < R; r1++) for (int c1 = 0; c1 < C; c1++) for (int r2 = r1; r2 < R; r2++) for (int c2 = c1; c2 < C; c2++) { boolean ok = true; for (int i = r1; i <= r2 && ok; i++) for (int j = c1; j <= c2; j++) if (m[i][j] != '1') { ok = false; break; } if (ok) best = Math.max(best, (r2 - r1 + 1) * (c2 - c1 + 1)); }
    return best;
}
```

- **Time Complexity:** O(R^2 * C^2 * R * C)
- **Space Complexity:** O(1)

### Approach 2: Histogram per Row with a Stack (Optimized)

Update the column heights for each row and take the largest rectangle in the histogram. This is the version implemented in the repository.

```java
public static int histogram(char[][] m) {
    int C = m[0].length; int[] h = new int[C + 1]; int best = 0;
    for (char[] row : m) { for (int j = 0; j < C; j++) h[j] = row[j] == '1' ? h[j] + 1 : 0;
        java.util.Deque<Integer> st = new java.util.ArrayDeque<>();
        for (int j = 0; j <= C; j++) { while (!st.isEmpty() && h[st.peek()] > h[j]) { int ht = h[st.pop()]; int l = st.isEmpty() ? -1 : st.peek(); best = Math.max(best, ht * (j - l - 1)); } st.push(j); } }
    return best;
}
```

- **Time Complexity:** O(R * C)
- **Space Complexity:** O(C)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force over All Rectangles | O(R^2 * C^2 * R * C) | O(1) |
| 2 | Histogram per Row with a Stack (Optimized) | O(R * C) | O(C) |

## Source Code

### MaximalRectangle.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class MaximalRectangle {

    public static void main(String[] args) {
        char[][][] inputs = {
            {{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}},
            {{'0'}},
            {{'1'}},
            {{'1', '1'}, {'1', '1'}}
        };
        for (char[][] matrix : inputs) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrix));
            System.out.println("Output : " + maximalRectangle(matrix));
            System.out.println();
        }
    }

    public static int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int cols = matrix[0].length;
        int[] heights = new int[cols + 1];
        int best = 0;
        for (char[] row : matrix) {
            for (int j = 0; j < cols; j++) {
                heights[j] = row[j] == '1' ? heights[j] + 1 : 0;
            }
            Deque<Integer> stack = new ArrayDeque<>();
            for (int j = 0; j <= cols; j++) {
                while (!stack.isEmpty() && heights[stack.peek()] > heights[j]) {
                    int height = heights[stack.pop()];
                    int left = stack.isEmpty() ? -1 : stack.peek();
                    best = Math.max(best, height * (j - left - 1));
                }
                stack.push(j);
            }
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: for each row, treat the stack of consecutive ones above every cell as a bar in a histogram. The biggest all-ones rectangle with its bottom on that row is the biggest rectangle in the histogram, found in linear time with a monotonic stack.

**Input:** `[[1,0,1,0,0],[1,0,1,1,1],[1,1,1,1,1],[1,0,0,1,0]]`.

| Row | Heights | Largest histogram rectangle |
|-----|---------|------------------------------|
| 0 | 1 0 1 0 0 | 1 |
| 1 | 2 0 2 1 1 | 3 |
| 2 | 3 1 3 2 2 | 6 |
| 3 | 4 0 0 3 0 | 4 |

**Output:** `6`.
