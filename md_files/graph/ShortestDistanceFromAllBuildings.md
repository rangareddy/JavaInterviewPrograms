# Shortest Distance from All Buildings

A grid has `0` (empty land you can walk on), `1` (a building) and `2` (an obstacle). You want to build a house on an empty land cell so that the total travel distance to all buildings is the smallest; you can only move up, down, left and right, and the distance is the number of steps. Return the minimum total distance, or `-1` if no empty cell can reach every building.

## Algorithm Strategy (BFS from Each Building)

1. Instead of starting from empty cells, run a BFS from every building (usually fewer than empty cells).
2. Keep two grids: `dist[i][j]`, the sum of the distances from all buildings processed so far, and `reach[i][j]`, how many buildings reached the cell.
3. Each BFS only moves through empty cells and adds the distance to `dist` and one to `reach`.
4. The answer is the minimum `dist[i][j]` among the empty cells whose `reach` equals the number of buildings, or -1.

## Example walkthrough

- For the classic 3x5 sample with three buildings, the best empty cell is at row 1, column 2 with distances 3 + 3 + 1.
- Result: `7`.

## Complexity Analysis

- **Time Complexity:** `O(buildings * m * n)` — One BFS per building.
- **Space Complexity:** `O(m * n)` — Distance and reach grids and a queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BFS from Every Empty Cell

For each empty cell run a BFS to all buildings and sum the distances. Slower when empty cells outnumber buildings.

```java
public static int fromEmpty(int[][] g) {
    int m = g.length, n = g[0].length, buildings = 0, best = Integer.MAX_VALUE; for (int[] row : g) for (int v : row) if (v == 1) buildings++;
    for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) if (g[i][j] == 0) { boolean[][] seen = new boolean[m][n]; java.util.Queue<int[]> q = new java.util.ArrayDeque<>(); q.add(new int[]{i, j}); seen[i][j] = true; int steps = 0, found = 0, total = 0;
        while (!q.isEmpty()) { steps++; for (int z = q.size(); z > 0; z--) { int[] c = q.poll(); int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; for (int[] x : d) { int r = c[0] + x[0], cc = c[1] + x[1]; if (r < 0 || cc < 0 || r >= m || cc >= n || seen[r][cc]) continue; seen[r][cc] = true; if (g[r][cc] == 1) { found++; total += steps; } else if (g[r][cc] == 0) q.add(new int[]{r, cc}); } } }
        if (found == buildings) best = Math.min(best, total); }
    return best == Integer.MAX_VALUE ? -1 : best;
}
```

- **Time Complexity:** O(empty * m * n)
- **Space Complexity:** O(m * n)

### Approach 2: BFS from Every Building (Optimized)

Accumulate distances and reach counts from each building. This is the version implemented in the repository.

```java
public static int fromBuildings(int[][] g) {
    int m = g.length, n = g[0].length; int[][] dist = new int[m][n], reach = new int[m][n]; int b = 0;
    for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) if (g[i][j] == 1) { b++; boolean[][] seen = new boolean[m][n]; java.util.Queue<int[]> q = new java.util.ArrayDeque<>(); q.add(new int[]{i, j}); seen[i][j] = true; int steps = 0;
        while (!q.isEmpty()) { steps++; for (int z = q.size(); z > 0; z--) { int[] c = q.poll(); int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; for (int[] x : d) { int r = c[0] + x[0], cc = c[1] + x[1]; if (r >= 0 && cc >= 0 && r < m && cc < n && !seen[r][cc] && g[r][cc] == 0) { seen[r][cc] = true; dist[r][cc] += steps; reach[r][cc]++; q.add(new int[]{r, cc}); } } } } }
    int best = Integer.MAX_VALUE; for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) if (g[i][j] == 0 && reach[i][j] == b) best = Math.min(best, dist[i][j]);
    return best == Integer.MAX_VALUE ? -1 : best;
}
```

- **Time Complexity:** O(buildings * m * n)
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | BFS from Every Empty Cell | O(empty * m * n) | O(m * n) |
| 2 | BFS from Every Building (Optimized) | O(buildings * m * n) | O(m * n) |

## Source Code

### ShortestDistanceFromAllBuildings.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class ShortestDistanceFromAllBuildings {

    public static void main(String[] args) {
        int[][][] grids = {{{1, 0, 2, 0, 1}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}}, {{1, 0}}, {{1}}, {{1, 2, 0}}};
        for (int[][] grid : grids) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + shortestDistance(grid));
            System.out.println();
        }
    }

    public static int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return -1;
        }
        int m = grid.length;
        int n = grid[0].length;
        int[][] dist = new int[m][n];
        int[][] reach = new int[m][n];
        int buildings = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    buildings++;
                    bfs(grid, i, j, dist, reach);
                }
            }
        }
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0 && reach[i][j] == buildings) {
                    best = Math.min(best, dist[i][j]);
                }
            }
        }
        return best == Integer.MAX_VALUE ? -1 : best;
    }

    private static void bfs(int[][] grid, int si, int sj, int[][] dist, int[][] reach) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] seen = new boolean[m][n];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{si, sj});
        seen[si][sj] = true;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int steps = 0;
        while (!queue.isEmpty()) {
            steps++;
            for (int size = queue.size(); size > 0; size--) {
                int[] cell = queue.poll();
                for (int[] d : dirs) {
                    int r = cell[0] + d[0];
                    int c = cell[1] + d[1];
                    if (r >= 0 && c >= 0 && r < m && c < n && !seen[r][c] && grid[r][c] == 0) {
                        seen[r][c] = true;
                        dist[r][c] += steps;
                        reach[r][c]++;
                        queue.add(new int[]{r, c});
                    }
                }
            }
        }
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a distance from a building to an empty cell is the same in both directions, so one BFS per building adds that building's distance to every cell it can reach. Counting how many buildings reached each cell tells which cells are reachable from all of them.

**Input:** `[[1, 0, 2, 0, 1], [0, 0, 0, 0, 0], [0, 0, 1, 0, 0]]`.

| Building | Contribution to the best cell (1, 2) |
|----------|---------------------------------------|
| (0, 0) | 3 |
| (0, 4) | 3 |
| (2, 2) | 1 |

Total `3 + 3 + 1 = 7`.

**Output:** `7`.
