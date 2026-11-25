# Making A Large Island

Given an `n x n` binary grid, you may change at most one `0` to `1`. Return the size of the largest island (a group of four-directionally connected `1`s) after the change.

## Algorithm Strategy (Label Islands then Try Each Flip)

1. Flood-fill every island with a unique id (starting at 2) and record its size.
2. For every `0` cell, collect the distinct ids of its four neighbours.
3. Flipping that cell gives size `1 + sum of the sizes of those distinct islands`.
4. The answer is the maximum of that over all zeros, or the largest existing island if there are no zeros.

## Example walkthrough

- For `[[1,0],[0,1]]` flipping either zero joins both 1-cell islands plus the flipped cell.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Labelling is linear and each zero looks at four neighbours.
- **Space Complexity:** `O(n^2)` — The sizes and recursion.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Flip Each Zero and Recount

For every zero flip it, flood-fill from it to measure the island, and flip it back. Quadratic per zero.

```java
public static int brute(int[][] in) {
    int n = in.length, best = 0; int[][] g = new int[n][]; for (int i = 0; i < n; i++) g[i] = in[i].clone();
    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) best = Math.max(best, in[i][j] == 1 ? 0 : size(g, i, j, in[i][j] == 0));
    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (in[i][j] == 1) best = Math.max(best, size(g, i, j, false));
    return best;
}
private static int size(int[][] g, int i, int j, boolean flip) {
    int n = g.length; int[][] c = new int[n][]; for (int a = 0; a < n; a++) c[a] = g[a].clone(); if (flip) c[i][j] = 1;
    boolean[][] seen = new boolean[n][n]; java.util.Deque<int[]> st = new java.util.ArrayDeque<>(); st.push(new int[]{i, j}); seen[i][j] = true; int cnt = 0;
    while (!st.isEmpty()) { int[] p = st.pop(); cnt++; int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; for (int[] x : d) { int a = p[0] + x[0], b = p[1] + x[1]; if (a >= 0 && b >= 0 && a < n && b < n && c[a][b] == 1 && !seen[a][b]) { seen[a][b] = true; st.push(new int[]{a, b}); } } }
    return cnt;
}
```

- **Time Complexity:** O(n^4)
- **Space Complexity:** O(n^2)

### Approach 2: Label Islands then Try Each Flip (Optimized)

Compute each island's size once and combine the neighbouring islands of every zero. This is the version implemented in the repository.

```java
public static int labelled(int[][] in) {
    int n = in.length; int[][] g = new int[n][]; for (int i = 0; i < n; i++) g[i] = in[i].clone(); java.util.Map<Integer, Integer> sz = new java.util.HashMap<>(); int id = 2, best = 0;
    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (g[i][j] == 1) { int s = fl(g, i, j, id); sz.put(id++, s); best = Math.max(best, s); }
    int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (g[i][j] == 0) { java.util.Set<Integer> nb = new java.util.HashSet<>(); for (int[] x : d) { int a = i + x[0], b = j + x[1]; if (a >= 0 && b >= 0 && a < n && b < n && g[a][b] > 1) nb.add(g[a][b]); } int t = 1; for (int q : nb) t += sz.get(q); best = Math.max(best, t); }
    return best;
}
private static int fl(int[][] g, int i, int j, int id) { if (i < 0 || j < 0 || i >= g.length || j >= g.length || g[i][j] != 1) return 0; g[i][j] = id; return 1 + fl(g, i + 1, j, id) + fl(g, i - 1, j, id) + fl(g, i, j + 1, id) + fl(g, i, j - 1, id); }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Flip Each Zero and Recount | O(n^4) | O(n^2) |
| 2 | Label Islands then Try Each Flip (Optimized) | O(n^2) | O(n^2) |

## Source Code

### MakingALargeIsland.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MakingALargeIsland {

    public static void main(String[] args) {
        int[][][] grids = {{{1, 0}, {0, 1}}, {{1, 1}, {1, 0}}, {{1, 1}, {1, 1}}, {{0, 0}, {0, 0}}};
        for (int[][] grid : grids) {
            int[][] copy = new int[grid.length][];
            for (int i = 0; i < grid.length; i++) {
                copy[i] = grid[i].clone();
            }
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + largestIsland(copy));
            System.out.println();
        }
    }

    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static int largestIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int n = grid.length;
        Map<Integer, Integer> size = new HashMap<>();
        int id = 2;
        int best = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    int s = fill(grid, i, j, id);
                    size.put(id++, s);
                    best = Math.max(best, s);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    Set<Integer> neighbours = new HashSet<>();
                    for (int[] d : DIRS) {
                        int r = i + d[0];
                        int c = j + d[1];
                        if (r >= 0 && r < n && c >= 0 && c < n && grid[r][c] > 1) {
                            neighbours.add(grid[r][c]);
                        }
                    }
                    int total = 1;
                    for (int nb : neighbours) {
                        total += size.get(nb);
                    }
                    best = Math.max(best, total);
                }
            }
        }
        return best;
    }

    private static int fill(int[][] grid, int i, int j, int id) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid.length || grid[i][j] != 1) {
            return 0;
        }
        grid[i][j] = id;
        int count = 1;
        for (int[] d : DIRS) {
            count += fill(grid, i + d[0], j + d[1], id);
        }
        return count;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: flipping a zero joins all the different islands around it into one. If every island already has an id and a known size, the size after the flip is the sum of the neighbouring islands' sizes plus one for the flipped cell.

**Input:** `grid = [[1, 0], [0, 1]]`.

| Step | Result |
|------|--------|
| label islands | (0,0) -> id 2, size 1; (1,1) -> id 3, size 1 |
| flip (0,1) | neighbours: ids 2 and 3 -> `1 + 1 + 1` |
| flip (1,0) | neighbours: ids 2 and 3 -> `1 + 1 + 1` |

**Output:** `3`.
