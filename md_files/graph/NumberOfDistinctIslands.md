# Number of Distinct Islands

Given an `m x n` binary grid, an island is a group of `1`s connected four-directionally. Two islands are the same if one can be translated (not rotated or reflected) to equal the other. Return the number of distinct island shapes.

## Algorithm Strategy (DFS with a Relative-Path Signature)

1. Scan the grid. When an unvisited `1` is found, start a DFS and sink the island as you visit it.
2. Record the shape as the sequence of moves taken by the DFS (`U`, `D`, `L`, `R`) with a marker `B` when backtracking.
3. Because the move sequence is independent of the island's position, translated copies produce the same signature.
4. Add each signature to a `HashSet` and return its size.

## Example walkthrough

- For `[[1,1,0,0,0],[1,1,0,0,0],[0,0,0,1,1],[0,0,0,1,1]]`: both islands are 2x2 squares and have the same signature.
- Result: `1`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Each cell is visited once.
- **Space Complexity:** `O(m * n)` — Recursion and the set of signatures.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Normalised Coordinates

Collect each island's cells, subtract the top-left cell, and store the sorted list of offsets as the signature.

```java
public static int offsets(int[][] g) {
    int m = g.length, n = g[0].length; boolean[][] seen = new boolean[m][n]; java.util.Set<String> set = new java.util.HashSet<>();
    for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) if (g[i][j] == 1 && !seen[i][j]) {
        java.util.List<int[]> cells = new java.util.ArrayList<>(); java.util.Deque<int[]> st = new java.util.ArrayDeque<>(); st.push(new int[]{i, j}); seen[i][j] = true;
        while (!st.isEmpty()) { int[] c = st.pop(); cells.add(c); int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; for (int[] x : d) { int a = c[0] + x[0], b = c[1] + x[1]; if (a >= 0 && b >= 0 && a < m && b < n && g[a][b] == 1 && !seen[a][b]) { seen[a][b] = true; st.push(new int[]{a, b}); } } }
        int mr = Integer.MAX_VALUE, mc = Integer.MAX_VALUE; for (int[] c : cells) { mr = Math.min(mr, c[0]); mc = Math.min(mc, c[1]); }
        java.util.List<String> parts = new java.util.ArrayList<>(); for (int[] c : cells) parts.add((c[0] - mr) + "," + (c[1] - mc)); java.util.Collections.sort(parts); set.add(parts.toString());
    }
    return set.size();
}
```

- **Time Complexity:** O(m * n log(m * n))
- **Space Complexity:** O(m * n)

### Approach 2: DFS with a Move Signature (Optimized)

Record the DFS moves and backtracking markers; equal shapes give equal strings. This is the version implemented in the repository.

```java
public static int signature(int[][] grid) {
    int[][] g = new int[grid.length][]; for (int i = 0; i < g.length; i++) g[i] = grid[i].clone();
    java.util.Set<String> set = new java.util.HashSet<>();
    for (int i = 0; i < g.length; i++) for (int j = 0; j < g[0].length; j++) if (g[i][j] == 1) { StringBuilder sb = new StringBuilder(); go(g, i, j, 'S', sb); set.add(sb.toString()); }
    return set.size();
}
private static void go(int[][] g, int i, int j, char m, StringBuilder sb) {
    if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || g[i][j] != 1) return;
    g[i][j] = 2; sb.append(m); go(g, i + 1, j, 'D', sb); go(g, i - 1, j, 'U', sb); go(g, i, j + 1, 'R', sb); go(g, i, j - 1, 'L', sb); sb.append('B');
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Normalised Coordinates | O(m * n log(m * n)) | O(m * n) |
| 2 | DFS with a Move Signature (Optimized) | O(m * n) | O(m * n) |

## Source Code

### NumberOfDistinctIslands.java

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NumberOfDistinctIslands {

    public static void main(String[] args) {
        int[][][] grids = {
            {{1, 1, 0, 0, 0}, {1, 1, 0, 0, 0}, {0, 0, 0, 1, 1}, {0, 0, 0, 1, 1}},
            {{1, 1, 0, 1, 1}, {1, 0, 0, 0, 0}, {0, 0, 0, 0, 1}, {1, 1, 0, 1, 1}},
            {{0, 0}, {0, 0}}
        };
        for (int[][] grid : grids) {
            int[][] copy = new int[grid.length][];
            for (int i = 0; i < grid.length; i++) {
                copy[i] = grid[i].clone();
            }
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + numDistinctIslands(copy));
            System.out.println();
        }
    }

    public static int numDistinctIslands(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        Set<String> shapes = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    StringBuilder path = new StringBuilder();
                    explore(grid, i, j, 'S', path);
                    shapes.add(path.toString());
                }
            }
        }
        return shapes.size();
    }

    private static void explore(int[][] grid, int i, int j, char move, StringBuilder path) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != 1) {
            return;
        }
        grid[i][j] = 2;
        path.append(move);
        explore(grid, i + 1, j, 'D', path);
        explore(grid, i - 1, j, 'U', path);
        explore(grid, i, j + 1, 'R', path);
        explore(grid, i, j - 1, 'L', path);
        path.append('B');
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a DFS that always tries the neighbours in the same order and notes the direction of each step (plus a marker when it backtracks) traces the shape of the island independent of where it sits in the grid.

**Input:** two 2x2 islands.

| Island | Start cell | Moves traced by the DFS |
|--------|------------|--------------------------|
| top-left 2x2 | (0, 0) | start, down, right, up... then backtracking markers |
| bottom-right 2x2 | (2, 3) | exactly the same sequence |

The signature depends only on the sequence of moves, not on the start cell, so both islands produce the identical string and the set holds one entry.

**Output:** `1`.
