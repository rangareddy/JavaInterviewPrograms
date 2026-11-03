# Swim in Rising Water

In an `n x n` grid, `grid[i][j]` is the elevation at that cell. At time `t` the water depth is `t`, and you can swim from a cell to a 4-directionally adjacent cell only if both elevations are at most `t`. Starting at the top-left cell, return the least time until you can reach the bottom-right cell.

## Algorithm Strategy (Dijkstra on the Path's Maximum Elevation)

1. The time needed for a path is the highest elevation on it, because you must wait until the water covers its highest cell.
2. So the task is to find the path that minimises the maximum elevation. This is Dijkstra with "cost = maximum so far" instead of a sum.
3. Use a min-heap of `(maximum elevation of the path so far, row, col)`, starting with the top-left cell.
4. Pop the cheapest cell; when it is the bottom-right corner its value is the answer. Otherwise push each unvisited neighbour with `max(current maximum, neighbour elevation)`.

## Example walkthrough

- For `[[0,2],[1,3]]`: the path 0 `->` 1 `->` 3 has maximum 3, the path 0 `->` 2 `->` 3 also 3.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n^2 log n)` — `n^2` cells, each pushed to the heap at most once.
- **Space Complexity:** `O(n^2)` — Visited array and heap.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Increase the Time Until the Target Is Reachable (Brute Force)

Try the times `t = grid[0][0], grid[0][0] + 1, ...` and for each run a DFS from the start restricted to cells with elevation `<= t`; the first `t` that reaches the target is the answer.

```java
public static int swimInWaterBrute(int[][] grid) {
    int n = grid.length;
    for (int time = Math.max(grid[0][0], 0); ; time++) {
        if (reachable(grid, time, new boolean[n][n], 0, 0)) return time;
    }
}

private static boolean reachable(int[][] grid, int time, boolean[][] visited, int i, int j) {
    int n = grid.length;
    if (i < 0 || j < 0 || i >= n || j >= n || visited[i][j] || grid[i][j] > time) return false;
    if (i == n - 1 && j == n - 1) return true;
    visited[i][j] = true;
    return reachable(grid, time, visited, i + 1, j) || reachable(grid, time, visited, i - 1, j)
            || reachable(grid, time, visited, i, j + 1) || reachable(grid, time, visited, i, j - 1);
}
```

- **Time Complexity:** O(n^4)
- **Space Complexity:** O(n^2)

### Approach 2: Binary Search on the Time + DFS

Whether the target is reachable at time `t` is monotone in `t`, so binary-search the smallest feasible `t` between the larger of the two corner elevations and the maximum elevation.

```java
public static int swimInWaterBinary(int[][] grid) {
    int n = grid.length, low = Math.max(grid[0][0], grid[n - 1][n - 1]), high = n * n - 1;
    for (int[] row : grid) for (int value : row) high = Math.max(high, value);
    while (low < high) {
        int mid = (low + high) / 2;
        if (reachable(grid, mid, new boolean[n][n], 0, 0)) high = mid; else low = mid + 1;
    }
    return low;
}
```

- **Time Complexity:** O(n^2 log(n^2))
- **Space Complexity:** O(n^2)

### Approach 3: Union-Find, Adding Cells in Order of Elevation

Raise the water level one unit at a time. At level `t`, the cell with elevation `t` becomes usable and is united with its already usable neighbours. The first level at which the start and the target are in the same set is the answer.

```java
public static int swimInWaterUnionFind(int[][] grid) {
    int n = grid.length;
    int[] parent = new int[n * n];
    for (int i = 0; i < parent.length; i++) parent[i] = i;
    Integer[] order = new Integer[n * n];
    for (int i = 0; i < order.length; i++) order[i] = i;
    Arrays.sort(order, Comparator.comparingInt(cell -> grid[cell / n][cell % n]));
    boolean[] open = new boolean[n * n];
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int cell : order) {
        int row = cell / n, col = cell % n, level = grid[row][col];
        open[cell] = true;
        for (int[] d : directions) {
            int x = row + d[0], y = col + d[1];
            if (x >= 0 && y >= 0 && x < n && y < n && open[x * n + y] && grid[x][y] <= level) parent[find(parent, cell)] = find(parent, x * n + y);
        }
        if (open[0] && open[n * n - 1] && find(parent, 0) == find(parent, n * n - 1)) return Math.max(level, Math.max(grid[0][0], grid[n - 1][n - 1]));
    }
    return -1;
}

private static int find(int[] parent, int x) {
    while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
    return x;
}
```

- **Time Complexity:** O(n^2 log n)
- **Space Complexity:** O(n^2)

### Approach 4: Dijkstra on the Path Maximum (Optimized)

Dijkstra where the cost of a path is its highest elevation instead of a sum: the heap always yields the cell reachable with the lowest water level. This is the version implemented in the repository.

```java
public static int swimInWater(int[][] grid) {
    int n = grid.length;
    boolean[][] visited = new boolean[n][n];
    PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt((int[] a) -> a[0]));
    heap.add(new int[]{grid[0][0], 0, 0});
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!heap.isEmpty()) {
        int[] current = heap.poll();
        if (visited[current[1]][current[2]]) continue;
        visited[current[1]][current[2]] = true;
        if (current[1] == n - 1 && current[2] == n - 1) return current[0];
        for (int[] d : directions) {
            int x = current[1] + d[0], y = current[2] + d[1];
            if (x >= 0 && y >= 0 && x < n && y < n && !visited[x][y]) heap.add(new int[]{Math.max(current[0], grid[x][y]), x, y});
        }
    }
    return -1;
}
```

- **Time Complexity:** O(n^2 log n)
- **Space Complexity:** O(n^2)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Increase the Time Until the Target Is Reachable (Brute Force) | O(n^4) | O(n^2) |
| 2 | Binary Search on the Time + DFS | O(n^2 log(n^2)) | O(n^2) |
| 3 | Union-Find, Adding Cells in Order of Elevation | O(n^2 log n) | O(n^2) |
| 4 | Dijkstra on the Path Maximum (Optimized) | O(n^2 log n) | O(n^2) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### SwimInRisingWater.java

```java
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SwimInRisingWater {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{0, 2}, {1, 3}},
                {{0, 1, 2, 3, 4}, {24, 23, 22, 21, 5}, {12, 13, 14, 15, 16}, {11, 17, 18, 19, 20}, {10, 9, 8, 7, 6}},
                {{0}},
                {{3, 2}, {0, 1}}
        };
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + swimInWater(grid));
            System.out.println();
        }
    }

    public static int swimInWater(int[][] grid) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt((int[] a) -> a[0]));      // {time, row, col}
        heap.add(new int[]{grid[0][0], 0, 0});
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!heap.isEmpty()) {
            int[] current = heap.poll();
            int time = current[0];
            int row = current[1];
            int col = current[2];
            if (visited[row][col]) {
                continue;
            }
            visited[row][col] = true;
            if (row == n - 1 && col == n - 1) {
                return time;
            }
            for (int[] d : directions) {
                int x = row + d[0];
                int y = col + d[1];
                if (x >= 0 && y >= 0 && x < n && y < n && !visited[x][y]) {
                    heap.add(new int[]{Math.max(time, grid[x][y]), x, y});
                }
            }
        }
        return -1;
    }
}
```
