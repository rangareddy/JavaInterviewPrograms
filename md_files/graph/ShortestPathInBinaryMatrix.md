# Shortest Path in Binary Matrix

Given an `n x n` binary matrix `grid`, return the length of the shortest clear path from the top-left cell to the bottom-right cell, or `-1` if there is none. A clear path goes through cells with value 0, and consecutive cells are 8-directionally connected. The length is the number of cells on the path.

## Algorithm Strategy (Breadth-First Search over 8 Directions)

1. If the start or the end cell is 1, return -1.
2. Run BFS from `(0, 0)` with `distance = 1`, marking visited cells (reuse the grid by setting visited cells to 1).
3. From each cell enqueue every unvisited 0-neighbour among the 8 directions.
4. The first time the bottom-right cell is reached, its distance is the answer; if the queue empties, return -1.

## Example walkthrough

- For `[[0,1],[1,0]]`: the diagonal move connects the two zeros directly, so the path has two cells.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Each cell is enqueued at most once.
- **Space Complexity:** `O(n^2)` — The BFS queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS with Backtracking

Explore all paths and keep the shortest. Exponential, shown only for contrast.

```java
public static int dfs(int[][] g) {
    int n = g.length; if (g[0][0] == 1 || g[n - 1][n - 1] == 1) return -1;
    boolean[][] seen = new boolean[n][n]; int r = go(g, seen, 0, 0, 1); return r == Integer.MAX_VALUE ? -1 : r;
}
private static int go(int[][] g, boolean[][] s, int i, int j, int d) {
    int n = g.length; if (i == n - 1 && j == n - 1) return d; s[i][j] = true; int best = Integer.MAX_VALUE;
    for (int a = -1; a <= 1; a++) for (int b = -1; b <= 1; b++) { int x = i + a, y = j + b; if (x >= 0 && x < n && y >= 0 && y < n && g[x][y] == 0 && !s[x][y]) best = Math.min(best, go(g, s, x, y, d + 1)); }
    s[i][j] = false; return best;
}
```

- **Time Complexity:** O(8^(n^2))
- **Space Complexity:** O(n^2)

### Approach 2: BFS with a Visited Array

Level-by-level search with a separate visited array.

```java
public static int bfsSeen(int[][] g) {
    int n = g.length; if (g[0][0] == 1 || g[n - 1][n - 1] == 1) return -1;
    boolean[][] seen = new boolean[n][n]; java.util.Queue<int[]> q = new java.util.ArrayDeque<>(); q.add(new int[]{0, 0, 1}); seen[0][0] = true;
    while (!q.isEmpty()) { int[] c = q.poll(); if (c[0] == n - 1 && c[1] == n - 1) return c[2];
        for (int a = -1; a <= 1; a++) for (int b = -1; b <= 1; b++) { int x = c[0] + a, y = c[1] + b; if (x >= 0 && x < n && y >= 0 && y < n && g[x][y] == 0 && !seen[x][y]) { seen[x][y] = true; q.add(new int[]{x, y, c[2] + 1}); } } }
    return -1;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Approach 3: BFS Marking Cells in the Grid (Optimized)

Reuse the grid as the visited marker. This is the version implemented in the repository.

```java
public static int bfsInPlace(int[][] grid) {
    int[][] g = new int[grid.length][]; for (int i = 0; i < g.length; i++) g[i] = grid[i].clone();
    int n = g.length; if (g[0][0] == 1 || g[n - 1][n - 1] == 1) return -1;
    java.util.Queue<int[]> q = new java.util.ArrayDeque<>(); q.add(new int[]{0, 0, 1}); g[0][0] = 1;
    while (!q.isEmpty()) { int[] c = q.poll(); if (c[0] == n - 1 && c[1] == n - 1) return c[2];
        for (int a = -1; a <= 1; a++) for (int b = -1; b <= 1; b++) { int x = c[0] + a, y = c[1] + b; if (x >= 0 && x < n && y >= 0 && y < n && g[x][y] == 0) { g[x][y] = 1; q.add(new int[]{x, y, c[2] + 1}); } } }
    return -1;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS with Backtracking | O(8^(n^2)) | O(n^2) |
| 2 | BFS with a Visited Array | O(n^2) | O(n^2) |
| 3 | BFS Marking Cells in the Grid (Optimized) | O(n^2) | O(n^2) |

## Source Code

### ShortestPathInBinaryMatrix.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class ShortestPathInBinaryMatrix {

    public static void main(String[] args) {
        int[][][] grids = {
            {{0, 1}, {1, 0}},
            {{0, 0, 0}, {1, 1, 0}, {1, 1, 0}},
            {{1, 0, 0}, {1, 1, 0}, {1, 1, 0}},
            {{0}}
        };
        for (int[][] grid : grids) {
            int[][] copy = new int[grid.length][];
            for (int i = 0; i < grid.length; i++) {
                copy[i] = grid[i].clone();
            }
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + shortestPathBinaryMatrix(copy));
            System.out.println();
        }
    }

    public static int shortestPathBinaryMatrix(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0][0] == 1) {
            return -1;
        }
        int n = grid.length;
        if (grid[n - 1][n - 1] == 1) {
            return -1;
        }
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0, 0, 1});
        grid[0][0] = 1;
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            if (cell[0] == n - 1 && cell[1] == n - 1) {
                return cell[2];
            }
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    int r = cell[0] + di;
                    int c = cell[1] + dj;
                    if (r >= 0 && r < n && c >= 0 && c < n && grid[r][c] == 0) {
                        grid[r][c] = 1;
                        queue.add(new int[]{r, c, cell[2] + 1});
                    }
                }
            }
        }
        return -1;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: every move costs one cell, so a breadth-first search reaches each cell by the fewest moves, and the first arrival at the target is the shortest path. Marking visited cells as 1 in the grid avoids a second array.

**Input:** `[[0, 0, 0], [1, 1, 0], [1, 1, 0]]`.

| Level | Cells reached | Distance |
|-------|---------------|----------|
| 1 | (0,0) | 1 |
| 2 | (0,1) | 2 |
| 3 | (0,2), (1,2) | 3 |
| 4 | (2,2) | 4 |

**Output for this grid:** `4`. For the first sample, `[[0, 1], [1, 0]]`, the diagonal step gives `2`.
