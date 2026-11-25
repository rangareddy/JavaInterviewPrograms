# Triangle

Given a `triangle` array, return the minimum path sum from top to bottom. At each step you may move to an adjacent number on the row below: from index `i` to index `i` or `i + 1`.

## Algorithm Strategy (Bottom-Up DP on One Row)

1. Start from the last row, which is the base case: its minimum sums are the values themselves.
2. Move up one row at a time: `best[j] = triangle[row][j] + min(best[j], best[j + 1])`.
3. Reuse a single array of the row's width, overwriting it as you go up.
4. After the top row, `best[0]` is the answer.

## Example walkthrough

- For `[[2],[3,4],[6,5,7],[4,1,8,3]]`: the minimum path is 2 + 3 + 5 + 1.
- Result: `11`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Every number is visited once (the triangle has about n^2 / 2 numbers).
- **Space Complexity:** `O(n)` — One row.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion over All Paths

Try both children at every step. Exponential.

```java
public static int recursive(java.util.List<java.util.List<Integer>> t) { return t.isEmpty() ? 0 : go(t, 0, 0); }
private static int go(java.util.List<java.util.List<Integer>> t, int row, int j) {
    if (row == t.size() - 1) return t.get(row).get(j);
    return t.get(row).get(j) + Math.min(go(t, row + 1, j), go(t, row + 1, j + 1));
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Top-Down DP Table

Store the best sum to every cell from the top.

```java
public static int topDown(java.util.List<java.util.List<Integer>> t) {
    if (t.isEmpty()) return 0; int n = t.size(); int[][] d = new int[n][n]; d[0][0] = t.get(0).get(0);
    for (int i = 1; i < n; i++) for (int j = 0; j <= i; j++) {
        int up = j < i ? d[i - 1][j] : Integer.MAX_VALUE, left = j > 0 ? d[i - 1][j - 1] : Integer.MAX_VALUE;
        d[i][j] = t.get(i).get(j) + Math.min(up, left);
    }
    int best = Integer.MAX_VALUE; for (int j = 0; j < n; j++) best = Math.min(best, d[n - 1][j]); return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Approach 3: Bottom-Up on One Row (Optimized)

Fold the triangle upward into one array. This is the version implemented in the repository.

```java
public static int bottomUp(java.util.List<java.util.List<Integer>> t) {
    if (t.isEmpty()) return 0; int n = t.size(); int[] b = new int[n];
    for (int j = 0; j < n; j++) b[j] = t.get(n - 1).get(j);
    for (int row = n - 2; row >= 0; row--) for (int j = 0; j <= row; j++) b[j] = t.get(row).get(j) + Math.min(b[j], b[j + 1]);
    return b[0];
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion over All Paths | O(2^n) | O(n) |
| 2 | Top-Down DP Table | O(n^2) | O(n^2) |
| 3 | Bottom-Up on One Row (Optimized) | O(n^2) | O(n) |

## Source Code

### TriangleMinimumPathSum.java

```java
import java.util.Arrays;
import java.util.List;

public class TriangleMinimumPathSum {

    public static void main(String[] args) {
        int[][][] inputs = {
            {{2}, {3, 4}, {6, 5, 7}, {4, 1, 8, 3}},
            {{-10}},
            {{1}, {2, 3}},
            {}
        };
        for (int[][] rows : inputs) {
            List<List<Integer>> triangle = new java.util.ArrayList<>();
            for (int[] row : rows) {
                List<Integer> list = new java.util.ArrayList<>();
                for (int x : row) {
                    list.add(x);
                }
                triangle.add(list);
            }
            System.out.println("Input  : triangle = " + Arrays.deepToString(rows));
            System.out.println("Output : " + minimumTotal(triangle));
            System.out.println();
        }
    }

    public static int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.isEmpty()) {
            return 0;
        }
        int n = triangle.size();
        int[] best = new int[n];
        List<Integer> last = triangle.get(n - 1);
        for (int j = 0; j < n; j++) {
            best[j] = last.get(j);
        }
        for (int row = n - 2; row >= 0; row--) {
            for (int j = 0; j <= row; j++) {
                best[j] = triangle.get(row).get(j) + Math.min(best[j], best[j + 1]);
            }
        }
        return best[0];
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: working from the bottom, the best cost from any cell is its own value plus the cheaper of the two cells below it, so each row can be folded into the one beneath using a single array.

**Input:** `[[2], [3, 4], [6, 5, 7], [4, 1, 8, 3]]`.

| Row processed | `best` array afterwards |
|---------------|-------------------------|
| last row | [4, 1, 8, 3] |
| [6, 5, 7] | [7, 6, 10] |
| [3, 4] | [9, 10] |
| [2] | [11] |

**Output:** `11`.
