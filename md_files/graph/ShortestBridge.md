# Shortest Bridge

An `n x n` binary grid contains exactly two islands (groups of four-directionally connected `1`s). You may flip `0`s to `1`s to connect the two islands into one. Return the smallest number of `0`s you must flip.

## Algorithm Strategy (Colour One Island, BFS Outward)

1. Find the first `1` and flood-fill its island with DFS, marking its cells as 2 and putting them in a queue.
2. Run a multi-source BFS from all those cells, expanding one layer at a time through `0` cells.
3. Each layer corresponds to one flipped zero.
4. When the BFS reaches a cell with value 1 (the other island), return the number of layers expanded.

## Example walkthrough

- For `[[0,1],[1,0]]` the islands are the two diagonal cells; one flipped zero joins them.
- Result: `1`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Each cell enters the queue at most once.
- **Space Complexity:** `O(n^2)` — The queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BFS from Every Cell of the First Island Separately

Compute the distance from each cell of the first island to the second with its own BFS. Repeats work.

```java
public static int separate(int[][] in) {
    int n = in.length; int[][] g = new int[n][]; for (int i = 0; i < n; i++) g[i] = in[i].clone(); java.util.List<int[]> a = new java.util.ArrayList<>(); boolean f = false;
    for (int i = 0; i < n && !f; i++) for (int j = 0; j < n && !f; j++) if (g[i][j] == 1) { fill(g, i, j, a); f = true; }
    int best = Integer.MAX_VALUE;
    for (int[] s : a) { int[][] dist = new int[n][n]; for (int[] row : dist) java.util.Arrays.fill(row, -1); java.util.Queue<int[]> q = new java.util.ArrayDeque<>(); q.add(s); dist[s[0]][s[1]] = 0;
        while (!q.isEmpty()) { int[] c = q.poll(); if (g[c[0]][c[1]] == 1) { best = Math.min(best, dist[c[0]][c[1]] - 1); break; } int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; for (int[] x : d) { int r = c[0] + x[0], cc = c[1] + x[1]; if (r >= 0 && cc >= 0 && r < n && cc < n && dist[r][cc] < 0) { dist[r][cc] = dist[c[0]][c[1]] + 1; q.add(new int[]{r, cc}); } } } }
    return best;
}
private static void fill(int[][] g, int i, int j, java.util.List<int[]> out) { if (i < 0 || j < 0 || i >= g.length || j >= g.length || g[i][j] != 1) return; g[i][j] = 2; out.add(new int[]{i, j}); fill(g, i + 1, j, out); fill(g, i - 1, j, out); fill(g, i, j + 1, out); fill(g, i, j - 1, out); }
```

- **Time Complexity:** O(n^4)
- **Space Complexity:** O(n^2)

### Approach 2: DFS then Multi-Source BFS (Optimized)

Mark the first island, then expand all of its border cells together layer by layer. This is the version implemented in the repository.

```java
public static int multiSource(int[][] in) {
    int n = in.length; int[][] g = new int[n][]; for (int i = 0; i < n; i++) g[i] = in[i].clone(); java.util.Queue<int[]> q = new java.util.ArrayDeque<>(); boolean f = false;
    for (int i = 0; i < n && !f; i++) for (int j = 0; j < n && !f; j++) if (g[i][j] == 1) { mk(g, i, j, q); f = true; }
    int flips = 0; int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!q.isEmpty()) { for (int z = q.size(); z > 0; z--) { int[] c = q.poll(); for (int[] x : d) { int r = c[0] + x[0], cc = c[1] + x[1]; if (r < 0 || cc < 0 || r >= n || cc >= n || g[r][cc] == 2) continue; if (g[r][cc] == 1) return flips; g[r][cc] = 2; q.add(new int[]{r, cc}); } } flips++; }
    return -1;
}
private static void mk(int[][] g, int i, int j, java.util.Queue<int[]> q) { if (i < 0 || j < 0 || i >= g.length || j >= g.length || g[i][j] != 1) return; g[i][j] = 2; q.add(new int[]{i, j}); mk(g, i + 1, j, q); mk(g, i - 1, j, q); mk(g, i, j + 1, q); mk(g, i, j - 1, q); }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | BFS from Every Cell of the First Island Separately | O(n^4) | O(n^2) |
| 2 | DFS then Multi-Source BFS (Optimized) | O(n^2) | O(n^2) |

## Source Code

### ShortestBridge.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class ShortestBridge {

    public static void main(String[] args) {
        int[][][] grids = {{{0, 1}, {1, 0}}, {{0, 1, 0}, {0, 0, 0}, {0, 0, 1}}, {{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}};
        for (int[][] grid : grids) {
            int[][] copy = new int[grid.length][];
            for (int i = 0; i < grid.length; i++) {
                copy[i] = grid[i].clone();
            }
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + shortestBridge(copy));
            System.out.println();
        }
    }

    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static int shortestBridge(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int n = grid.length;
        Queue<int[]> queue = new ArrayDeque<>();
        boolean found = false;
        for (int i = 0; i < n && !found; i++) {
            for (int j = 0; j < n && !found; j++) {
                if (grid[i][j] == 1) {
                    mark(grid, i, j, queue);
                    found = true;
                }
            }
        }
        int flips = 0;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                int[] cell = queue.poll();
                for (int[] d : DIRS) {
                    int r = cell[0] + d[0];
                    int c = cell[1] + d[1];
                    if (r < 0 || c < 0 || r >= n || c >= n || grid[r][c] == 2) {
                        continue;
                    }
                    if (grid[r][c] == 1) {
                        return flips;
                    }
                    grid[r][c] = 2;
                    queue.add(new int[]{r, c});
                }
            }
            flips++;
        }
        return -1;
    }

    private static void mark(int[][] grid, int i, int j, Queue<int[]> queue) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid.length || grid[i][j] != 1) {
            return;
        }
        grid[i][j] = 2;
        queue.add(new int[]{i, j});
        for (int[] d : DIRS) {
            mark(grid, i + d[0], j + d[1], queue);
        }
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the shortest bridge is the shortest distance from any cell of island A to any cell of island B. Starting a BFS from all of island A's cells at once expands the frontier one layer (one flipped zero) at a time and stops at the first cell of island B.

**Input:** `grid = [[0, 1, 0], [0, 0, 0], [0, 0, 1]]`.

| Step | Frontier |
|------|----------|
| DFS marks island A | (0,1) |
| layer 1 (1 flip) | (0,0), (0,2), (1,1) |
| layer 2 (2 flips) | (1,0), (1,2), (2,1)... |

The second island's cell (2, 2) is reached from layer 2's frontier.

**Output for this input:** `2`. For the first sample in `main`, `[[0, 1], [1, 0]]`, the output is `1`.
