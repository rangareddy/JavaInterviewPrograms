# Longest Increasing Path in a Matrix

Given an `m x n` integer matrix, return the length of the longest strictly increasing path. From each cell you may move left, right, up or down (not diagonally and not outside the matrix).

## Algorithm Strategy (DFS with Memoization)

1. Let `longest(i, j)` be the length of the longest increasing path that starts at cell `(i, j)`.
2. It equals `1 + max(longest(neighbour))` over the neighbours with a strictly larger value (or just 1 if there is none).
3. Because the values must strictly increase, the graph of allowed moves has no cycles, so the recursion terminates, and each cell's result can be cached in a `memo` table.
4. Compute `longest` for every cell and return the maximum.

## Example walkthrough

- For `[[9,9,4],[6,6,8],[2,1,1]]`: the path `1 -> 2 -> 6 -> 9` has length 4.
- Result: `4`. In `[[3,4,5],[3,2,6],[2,2,1]]` the path `3 -> 4 -> 5 -> 6` also has length 4.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every cell is computed once; each computation looks at four neighbours.
- **Space Complexity:** `O(m * n)` — The memo table and the recursion stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain DFS from Every Cell

Explore all increasing paths from each cell without caching; paths are recomputed many times.

```java
public static int longestIncreasingPathBrute(int[][] matrix) {
    int best = 0;
    for (int i = 0; i < matrix.length; i++)
        for (int j = 0; j < matrix[0].length; j++) best = Math.max(best, dfs(matrix, i, j));
    return best;
}

private static int dfs(int[][] m, int i, int j) {
    int best = 1;
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int[] d : directions) {
        int x = i + d[0], y = j + d[1];
        if (x >= 0 && y >= 0 && x < m.length && y < m[0].length && m[x][y] > m[i][j]) best = Math.max(best, 1 + dfs(m, x, y));
    }
    return best;
}
```

- **Time Complexity:** O(4^(m * n)) worst case
- **Space Complexity:** O(m * n)

### Approach 2: Sort Cells and DP

Process the cells in increasing value order: each cell's best path is one more than its best smaller neighbour.

```java
public static int longestIncreasingPathSorted(int[][] matrix) {
    int rows = matrix.length, cols = matrix[0].length;
    List<int[]> cells = new ArrayList<>();
    for (int i = 0; i < rows; i++) for (int j = 0; j < cols; j++) cells.add(new int[]{i, j});
    cells.sort((a, b) -> Integer.compare(matrix[a[0]][a[1]], matrix[b[0]][b[1]]));
    int[][] length = new int[rows][cols];
    int best = 0;
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int[] cell : cells) {
        int i = cell[0], j = cell[1];
        length[i][j] = 1;
        for (int[] d : directions) {
            int x = i + d[0], y = j + d[1];
            if (x >= 0 && y >= 0 && x < rows && y < cols && matrix[x][y] < matrix[i][j]) length[i][j] = Math.max(length[i][j], length[x][y] + 1);
        }
        best = Math.max(best, length[i][j]);
    }
    return best;
}
```

- **Time Complexity:** O(m * n log(m * n))
- **Space Complexity:** O(m * n)

### Approach 3: Topological Peeling (Kahn)

Treat larger neighbours as outgoing edges and peel the graph layer by layer; the number of layers is the answer.

```java
public static int longestIncreasingPathKahn(int[][] matrix) {
    int rows = matrix.length, cols = matrix[0].length;
    int[][] inDegree = new int[rows][cols];
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++)
            for (int[] d : directions) {
                int x = i + d[0], y = j + d[1];
                if (x >= 0 && y >= 0 && x < rows && y < cols && matrix[x][y] < matrix[i][j]) inDegree[i][j]++;
            }
    Deque<int[]> queue = new ArrayDeque<>();
    for (int i = 0; i < rows; i++) for (int j = 0; j < cols; j++) if (inDegree[i][j] == 0) queue.add(new int[]{i, j});
    int layers = 0;
    while (!queue.isEmpty()) {
        layers++;
        for (int size = queue.size(); size > 0; size--) {
            int[] cell = queue.poll();
            for (int[] d : directions) {
                int x = cell[0] + d[0], y = cell[1] + d[1];
                if (x >= 0 && y >= 0 && x < rows && y < cols && matrix[x][y] > matrix[cell[0]][cell[1]] && --inDegree[x][y] == 0) queue.add(new int[]{x, y});
            }
        }
    }
    return layers;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 4: DFS with Memoization (Optimized)

Cache the longest path from each cell so every cell is solved once. This is the version implemented in the repository.

```java
public static int longestIncreasingPath(int[][] matrix) {
    if (matrix == null || matrix.length == 0) return 0;
    int[][] memo = new int[matrix.length][matrix[0].length];
    int best = 0;
    for (int i = 0; i < matrix.length; i++)
        for (int j = 0; j < matrix[0].length; j++) best = Math.max(best, longest(matrix, i, j, memo));
    return best;
}

private static int longest(int[][] matrix, int i, int j, int[][] memo) {
    if (memo[i][j] != 0) return memo[i][j];
    int best = 1;
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int[] d : directions) {
        int x = i + d[0], y = j + d[1];
        if (x >= 0 && y >= 0 && x < matrix.length && y < matrix[0].length && matrix[x][y] > matrix[i][j]) best = Math.max(best, 1 + longest(matrix, x, y, memo));
    }
    return memo[i][j] = best;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain DFS from Every Cell | O(4^(m * n)) worst case | O(m * n) |
| 2 | Sort Cells and DP | O(m * n log(m * n)) | O(m * n) |
| 3 | Topological Peeling (Kahn) | O(m * n) | O(m * n) |
| 4 | DFS with Memoization (Optimized) | O(m * n) | O(m * n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### LongestIncreasingPathInAMatrix.java

```java
import java.util.Arrays;

public class LongestIncreasingPathInAMatrix {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{9, 9, 4}, {6, 6, 8}, {2, 1, 1}},
                {{3, 4, 5}, {3, 2, 6}, {2, 2, 1}},
                {{1}},
                {{1, 2}, {4, 3}}
        };
        for (int[][] matrix : inputs) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrix));
            System.out.println("Output : " + longestIncreasingPath(matrix));
            System.out.println();
        }
    }

    public static int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }

        int[][] memo = new int[matrix.length][matrix[0].length];
        int best = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                best = Math.max(best, longest(matrix, i, j, memo));
            }
        }
        return best;
    }

    private static int longest(int[][] matrix, int i, int j, int[][] memo) {
        if (memo[i][j] != 0) {
            return memo[i][j];
        }

        int best = 1;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : directions) {
            int x = i + d[0];
            int y = j + d[1];
            if (x >= 0 && y >= 0 && x < matrix.length && y < matrix[0].length && matrix[x][y] > matrix[i][j]) {
                best = Math.max(best, 1 + longest(matrix, x, y, memo));
            }
        }
        return memo[i][j] = best;
    }
}
```
