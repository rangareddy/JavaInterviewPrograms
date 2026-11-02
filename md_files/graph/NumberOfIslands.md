# Number of Islands

Given an `m x n` grid of '1' (land) and '0' (water), return the number of islands. An island is formed by land cells connected horizontally or vertically and surrounded by water.

## Algorithm Strategy (DFS Flood Fill)

1. Scan every cell of (a copy of) the grid.
2. When an unvisited land cell is found, a new island starts: increment the counter.
3. Flood fill the whole island with a DFS: change every reachable land cell ('1') to water ('0') so it is not counted again.
4. After the scan the counter is the number of islands.

## Example walkthrough

- For the grid `11110 / 11010 / 11000 / 00000`: the first cell starts a flood fill that visits every connected 1, so the counter is 1 and nothing is left.
- For `11000 / 11000 / 00100 / 00011` three separate flood fills are started: `3`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every cell is visited a constant number of times.
- **Space Complexity:** `O(m * n)` — Recursion depth in the worst case (plus the copy of the grid).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursive DFS Flood Fill (Optimized)

Each unvisited land cell starts a new island; a DFS sinks all connected land. This is the version implemented in the repository (it works on a copy of the grid).

```java
public static int numIslands(char[][] grid) {
    char[][] copy = new char[grid.length][];
    for (int i = 0; i < grid.length; i++) copy[i] = grid[i].clone();
    int islands = 0;
    for (int i = 0; i < copy.length; i++) {
        for (int j = 0; j < copy[0].length; j++) {
            if (copy[i][j] == '1') { islands++; sink(copy, i, j); }
        }
    }
    return islands;
}

private static void sink(char[][] grid, int i, int j) {
    if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != '1') return;
    grid[i][j] = '0';
    sink(grid, i + 1, j); sink(grid, i - 1, j); sink(grid, i, j + 1); sink(grid, i, j - 1);
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 2: Iterative BFS

Flood fill with a queue instead of recursion, which is safe for very large islands (no stack overflow).

```java
public static int numIslandsBfs(char[][] grid) {
    int rows = grid.length, cols = grid[0].length, islands = 0;
    boolean[][] visited = new boolean[rows][cols];
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (grid[i][j] != '1' || visited[i][j]) continue;
            islands++;
            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[]{i, j});
            visited[i][j] = true;
            while (!queue.isEmpty()) {
                int[] cell = queue.poll();
                for (int[] d : directions) {
                    int x = cell[0] + d[0], y = cell[1] + d[1];
                    if (x >= 0 && y >= 0 && x < rows && y < cols && grid[x][y] == '1' && !visited[x][y]) {
                        visited[x][y] = true;
                        queue.add(new int[]{x, y});
                    }
                }
            }
        }
    }
    return islands;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(min(m, n)) queue, O(m * n) visited

### Approach 3: Union-Find

Start with every land cell as its own island and union it with its right and bottom land neighbors; every successful union reduces the island count. Useful when land is added over time (the dynamic variant of the problem).

```java
public static int numIslandsUnionFind(char[][] grid) {
    int rows = grid.length, cols = grid[0].length, islands = 0;
    int[] parent = new int[rows * cols];
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            parent[i * cols + j] = i * cols + j;
            if (grid[i][j] == '1') islands++;
        }
    }
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (grid[i][j] != '1') continue;
            if (j + 1 < cols && grid[i][j + 1] == '1' && union(parent, i * cols + j, i * cols + j + 1)) islands--;
            if (i + 1 < rows && grid[i + 1][j] == '1' && union(parent, i * cols + j, (i + 1) * cols + j)) islands--;
        }
    }
    return islands;
}

private static boolean union(int[] parent, int a, int b) {
    int rootA = find(parent, a), rootB = find(parent, b);
    if (rootA == rootB) return false;
    parent[rootA] = rootB;
    return true;
}

private static int find(int[] parent, int x) {
    while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
    return x;
}
```

- **Time Complexity:** O(m * n * alpha(m * n))
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursive DFS Flood Fill (Optimized) | O(m * n) | O(m * n) |
| 2 | Iterative BFS | O(m * n) | O(min(m, n)) queue, O(m * n) visited |
| 3 | Union-Find | O(m * n * alpha(m * n)) | O(m * n) |

### Optimized Solution

The optimized solution is Approach 1 above, which is also the version implemented in this repository.

## Source Code

### NumberOfIslands.java

```java
import java.util.Arrays;

public class NumberOfIslands {

    public static void main(String[] args) {
        char[][][] inputs = {
                {{'1', '1', '1', '1', '0'}, {'1', '1', '0', '1', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '0', '0', '0'}},
                {{'1', '1', '0', '0', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '1', '0', '0'}, {'0', '0', '0', '1', '1'}}
        };
        for (char[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + numIslands(grid));
            System.out.println();
        }
    }

    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        char[][] copy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();      // do not modify the caller's grid
        }

        int islands = 0;
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[0].length; j++) {
                if (copy[i][j] == '1') {
                    islands++;
                    sink(copy, i, j);
                }
            }
        }
        return islands;
    }

    private static void sink(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '0';
        sink(grid, i + 1, j);
        sink(grid, i - 1, j);
        sink(grid, i, j + 1);
        sink(grid, i, j - 1);
    }
}
```
