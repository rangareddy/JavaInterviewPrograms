# Shortest Path in a Grid with Obstacles Elimination

Given an `m x n` grid of `0` (empty) and `1` (obstacle) and an integer `k`, you start at the top-left cell and want to reach the bottom-right cell moving up, down, left or right. You may eliminate at most `k` obstacles along the way. Return the minimum number of steps, or `-1` if it is impossible.

## Algorithm Strategy (BFS over (cell, remaining eliminations))

1. If `k` is at least `m + n - 2`, the answer is `m + n - 2` immediately (walk straight, eliminating every obstacle).
2. Otherwise BFS over states `(row, col, k remaining)` with a visited set on the same triple.
3. Moving into an obstacle costs one elimination; moving into an empty cell costs none.
4. The first time the bottom-right cell is dequeued, its step count is the answer.

## Example walkthrough

- For the sample grid and `k = 1`: eliminate the obstacle in the third row, giving a 6-step path.
- Result: `6`.

## Complexity Analysis

- **Time Complexity:** `O(m * n * k)` — Each state is visited once.
- **Space Complexity:** `O(m * n * k)` — The visited array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS with Backtracking

Explore every path, tracking the obstacles removed. Exponential.

```java
public static int dfs(int[][] g, int k) {
    int m = g.length, n = g[0].length; int r = go(g, k, 0, 0, new boolean[m][n], 0); return r == Integer.MAX_VALUE ? -1 : r;
}
private static int go(int[][] g, int k, int i, int j, boolean[][] seen, int steps) {
    int m = g.length, n = g[0].length; if (g[i][j] == 1) k--; if (k < 0) return Integer.MAX_VALUE; if (i == m - 1 && j == n - 1) return steps; seen[i][j] = true; int best = Integer.MAX_VALUE; int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int[] x : d) { int a = i + x[0], b = j + x[1]; if (a >= 0 && b >= 0 && a < m && b < n && !seen[a][b]) best = Math.min(best, go(g, k, a, b, seen, steps + 1)); }
    seen[i][j] = false; return best;
}
```

- **Time Complexity:** O(4^(m*n))
- **Space Complexity:** O(m * n)

### Approach 2: BFS with Remaining Eliminations (Optimized)

BFS over `(row, col, k left)` states. This is the version implemented in the repository.

```java
public static int bfs(int[][] g, int k) {
    int m = g.length, n = g[0].length; if (k >= m + n - 2) return m + n - 2; boolean[][][] v = new boolean[m][n][k + 1]; java.util.Queue<int[]> q = new java.util.ArrayDeque<>(); q.add(new int[]{0, 0, k}); v[0][0][k] = true; int steps = 0; int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!q.isEmpty()) { for (int z = q.size(); z > 0; z--) { int[] c = q.poll(); if (c[0] == m - 1 && c[1] == n - 1) return steps; for (int[] x : d) { int r = c[0] + x[0], cc = c[1] + x[1]; if (r < 0 || cc < 0 || r >= m || cc >= n) continue; int left = c[2] - g[r][cc]; if (left >= 0 && !v[r][cc][left]) { v[r][cc][left] = true; q.add(new int[]{r, cc, left}); } } } steps++; }
    return -1;
}
```

- **Time Complexity:** O(m * n * k)
- **Space Complexity:** O(m * n * k)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS with Backtracking | O(4^(m*n)) | O(m * n) |
| 2 | BFS with Remaining Eliminations (Optimized) | O(m * n * k) | O(m * n * k) |

## Source Code

### ShortestPathInAGridWithObstaclesElimination.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class ShortestPathInAGridWithObstaclesElimination {

    public static void main(String[] args) {
        int[][][] grids = {{{0, 0, 0}, {1, 1, 0}, {0, 0, 0}, {0, 1, 1}, {0, 0, 0}}, {{0, 1, 1}, {1, 1, 1}, {1, 0, 0}}, {{0}}};
        int[] ks = {1, 1, 0};
        for (int i = 0; i < grids.length; i++) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grids[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + shortestPath(grids[i], ks[i]));
            System.out.println();
        }
    }

    public static int shortestPath(int[][] grid, int k) {
        if (grid == null || grid.length == 0) {
            return -1;
        }
        int m = grid.length;
        int n = grid[0].length;
        if (k >= m + n - 2) {
            return m + n - 2;
        }
        boolean[][][] visited = new boolean[m][n][k + 1];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0, 0, k});
        visited[0][0][k] = true;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int steps = 0;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                int[] cell = queue.poll();
                if (cell[0] == m - 1 && cell[1] == n - 1) {
                    return steps;
                }
                for (int[] d : dirs) {
                    int r = cell[0] + d[0];
                    int c = cell[1] + d[1];
                    if (r < 0 || c < 0 || r >= m || c >= n) {
                        continue;
                    }
                    int left = cell[2] - grid[r][c];
                    if (left >= 0 && !visited[r][c][left]) {
                        visited[r][c][left] = true;
                        queue.add(new int[]{r, c, left});
                    }
                }
            }
            steps++;
        }
        return -1;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the same cell can be reached with different numbers of eliminations left, and having more left is strictly better, so the state is the cell plus the eliminations remaining. BFS over those states finds the fewest steps.

**Input:** a 5x3 grid with obstacles at (1,0), (1,1), (3,1), (3,2) and `k = 1`.

| Steps | Frontier highlights |
|-------|----------------------|
| 0 | (0,0) with 1 elimination |
| 2 | (0,2), then (1,2) |
| 4 | (2,2), then (2,1)... |
| 6 | (4,2), the target |

The shortest route passes through the obstacle region once, using the single elimination.

**Output:** `6`.
