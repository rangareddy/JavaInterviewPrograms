# Max Area of Island

Given an `m x n` binary grid, an island is a group of 1s connected 4-directionally (horizontally or vertically). The area of an island is the number of cells with value 1 in it. Return the maximum area of an island in the grid, or 0 if there is no island.

## Algorithm Strategy (DFS Flood Fill that Returns the Area)

1. Scan every cell of (a copy of) the grid. An unvisited land cell starts a new island.
2. A DFS from that cell counts the cell itself plus the areas reached through its four neighbours; each visited land cell is set to 0 so it is never counted twice.
3. The DFS call returns the size of the island that was flooded; keep the maximum over all starting cells.
4. Water cells and cells outside the grid return 0 immediately.

## Example walkthrough

- In the standard example grid (8 rows, 13 columns) the islands have areas 1, 4, 3, 1, 3, 6 ... and the largest one is the group of six cells in the lower right.
- Result: `6`. A grid of only zeros gives `0`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Each cell is visited a constant number of times.
- **Space Complexity:** `O(m * n)` — Recursion depth in the worst case (plus the copy of the grid).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursive DFS (Optimized)

A flood fill returns the number of cells it sinks. This is the version implemented in the repository.

```java
public static int maxAreaOfIsland(int[][] grid) {
    int[][] copy = new int[grid.length][];
    for (int i = 0; i < grid.length; i++) copy[i] = grid[i].clone();
    int best = 0;
    for (int i = 0; i < copy.length; i++)
        for (int j = 0; j < copy[0].length; j++) best = Math.max(best, area(copy, i, j));
    return best;
}

private static int area(int[][] grid, int i, int j) {
    if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != 1) return 0;
    grid[i][j] = 0;
    return 1 + area(grid, i + 1, j) + area(grid, i - 1, j) + area(grid, i, j + 1) + area(grid, i, j - 1);
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 2: Iterative DFS with a Stack

The same flood fill with an explicit stack of cells, which avoids stack overflow on huge islands.

```java
public static int maxAreaStack(int[][] grid) {
    int[][] g = new int[grid.length][];
    for (int i = 0; i < grid.length; i++) g[i] = grid[i].clone();
    int best = 0;
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int i = 0; i < g.length; i++) {
        for (int j = 0; j < g[0].length; j++) {
            if (g[i][j] != 1) continue;
            Deque<int[]> stack = new ArrayDeque<>();
            stack.push(new int[]{i, j});
            g[i][j] = 0;
            int area = 0;
            while (!stack.isEmpty()) {
                int[] cell = stack.pop();
                area++;
                for (int[] d : directions) {
                    int x = cell[0] + d[0], y = cell[1] + d[1];
                    if (x >= 0 && y >= 0 && x < g.length && y < g[0].length && g[x][y] == 1) { g[x][y] = 0; stack.push(new int[]{x, y}); }
                }
            }
            best = Math.max(best, area);
        }
    }
    return best;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 3: BFS with a Queue

Flood each island level by level with a queue and count the visited cells.

```java
public static int maxAreaBfs(int[][] grid) {
    int rows = grid.length, cols = grid[0].length, best = 0;
    boolean[][] visited = new boolean[rows][cols];
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (grid[i][j] != 1 || visited[i][j]) continue;
            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[]{i, j});
            visited[i][j] = true;
            int area = 0;
            while (!queue.isEmpty()) {
                int[] cell = queue.poll();
                area++;
                for (int[] d : directions) {
                    int x = cell[0] + d[0], y = cell[1] + d[1];
                    if (x >= 0 && y >= 0 && x < rows && y < cols && grid[x][y] == 1 && !visited[x][y]) { visited[x][y] = true; queue.add(new int[]{x, y}); }
                }
            }
            best = Math.max(best, area);
        }
    }
    return best;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 4: Union-Find with Component Sizes

Union every land cell with its right and lower land neighbours and keep the size of each set; the largest set size is the answer. Also handles cells being added over time.

```java
public static int maxAreaUnionFind(int[][] grid) {
    int rows = grid.length, cols = grid[0].length, best = 0;
    int[] parent = new int[rows * cols], size = new int[rows * cols];
    for (int i = 0; i < parent.length; i++) { parent[i] = i; size[i] = 1; }
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (grid[i][j] != 1) continue;
            best = Math.max(best, 1);
            if (j + 1 < cols && grid[i][j + 1] == 1) best = Math.max(best, union(parent, size, i * cols + j, i * cols + j + 1));
            if (i + 1 < rows && grid[i + 1][j] == 1) best = Math.max(best, union(parent, size, i * cols + j, (i + 1) * cols + j));
        }
    }
    return best;
}

private static int union(int[] parent, int[] size, int a, int b) {
    int rootA = find(parent, a), rootB = find(parent, b);
    if (rootA != rootB) { parent[rootA] = rootB; size[rootB] += size[rootA]; }
    return size[find(parent, a)];
}

private static int find(int[] parent, int x) {
    while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
    return x;
}
```

- **Time Complexity:** O(m * n * alpha)
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursive DFS (Optimized) | O(m * n) | O(m * n) |
| 2 | Iterative DFS with a Stack | O(m * n) | O(m * n) |
| 3 | BFS with a Queue | O(m * n) | O(m * n) |
| 4 | Union-Find with Component Sizes | O(m * n * alpha) | O(m * n) |

### Optimized Solution

The optimized solution is Approach 1 above, which is also the version implemented in this repository.

## Source Code

### MaxAreaOfIsland.java

```java
import java.util.Arrays;

public class MaxAreaOfIsland {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                        {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}},
                {{0, 0, 0, 0, 0, 0, 0, 0}},
                {{1, 1}, {1, 0}},
                {{1, 0, 1}, {0, 1, 0}}
        };
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + maxAreaOfIsland(grid));
            System.out.println();
        }
    }

    public static int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int[][] copy = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();              // do not modify the caller's grid
        }

        int best = 0;
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[0].length; j++) {
                best = Math.max(best, area(copy, i, j));
            }
        }
        return best;
    }

    private static int area(int[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != 1) {
            return 0;
        }
        grid[i][j] = 0;                             // mark as visited
        return 1 + area(grid, i + 1, j) + area(grid, i - 1, j) + area(grid, i, j + 1) + area(grid, i, j - 1);
    }
}
```
