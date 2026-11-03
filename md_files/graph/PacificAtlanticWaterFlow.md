# Pacific Atlantic Water Flow

In a grid of heights, rain water flows from a cell to a neighboring cell (north, south, east, west) whose height is less than or equal to the current one. The Pacific Ocean touches the top and left edges and the Atlantic Ocean the bottom and right edges. Return all cells from which water can flow to both oceans.

## Algorithm Strategy (Reverse DFS from Each Ocean)

1. Instead of checking water flow from every cell, search backwards from the oceans: water that can reach an ocean cell comes from cells that are at least as high.
2. Start a DFS from all cells on the Pacific border (top row and left column) and mark every cell reachable by moving to neighbors that are not lower. Do the same from the Atlantic border (bottom row and right column).
3. A cell is part of the answer when it is marked by both searches.
4. Collect the cells in row-major order.

## Example walkthrough

- For the 5 x 5 example, the cell (0, 4) with height 5 touches the Pacific (top) and the Atlantic (right edge), and so does (1, 3) and (1, 4).
- Result: `[[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Each cell is visited at most once per ocean.
- **Space Complexity:** `O(m * n)` — Two visited matrices and the recursion stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force (DFS from Every Cell)

For every cell, follow the water downhill (to neighbors that are not higher) and check whether both oceans are reached. Cells are explored again and again.

```java
public static List<List<Integer>> pacificAtlanticBrute(int[][] heights) {
    List<List<Integer>> result = new ArrayList<>();
    int rows = heights.length, cols = heights[0].length;
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            boolean[] reached = new boolean[2];          // [pacific, atlantic]
            flow(heights, i, j, new boolean[rows][cols], reached);
            if (reached[0] && reached[1]) result.add(Arrays.asList(i, j));
        }
    }
    return result;
}

private static void flow(int[][] h, int i, int j, boolean[][] visited, boolean[] reached) {
    visited[i][j] = true;
    if (i == 0 || j == 0) reached[0] = true;
    if (i == h.length - 1 || j == h[0].length - 1) reached[1] = true;
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int[] d : directions) {
        int x = i + d[0], y = j + d[1];
        if (x >= 0 && y >= 0 && x < h.length && y < h[0].length && !visited[x][y] && h[x][y] <= h[i][j]) {
            flow(h, x, y, visited, reached);
        }
    }
}
```

- **Time Complexity:** O((m * n)^2)
- **Space Complexity:** O(m * n)

### Approach 2: Reverse BFS from Both Oceans

The same reverse search with a queue, which avoids deep recursion on large grids.

```java
public static List<List<Integer>> pacificAtlanticBfs(int[][] heights) {
    int rows = heights.length, cols = heights[0].length;
    Queue<int[]> pacificQueue = new LinkedList<>(), atlanticQueue = new LinkedList<>();
    boolean[][] pacific = new boolean[rows][cols], atlantic = new boolean[rows][cols];
    for (int i = 0; i < rows; i++) {
        pacificQueue.add(new int[]{i, 0}); pacific[i][0] = true;
        atlanticQueue.add(new int[]{i, cols - 1}); atlantic[i][cols - 1] = true;
    }
    for (int j = 0; j < cols; j++) {
        if (!pacific[0][j]) { pacificQueue.add(new int[]{0, j}); pacific[0][j] = true; }
        if (!atlantic[rows - 1][j]) { atlanticQueue.add(new int[]{rows - 1, j}); atlantic[rows - 1][j] = true; }
    }
    bfs(heights, pacificQueue, pacific);
    bfs(heights, atlanticQueue, atlantic);
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) if (pacific[i][j] && atlantic[i][j]) result.add(Arrays.asList(i, j));
    }
    return result;
}

private static void bfs(int[][] h, Queue<int[]> queue, boolean[][] reachable) {
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        for (int[] d : directions) {
            int x = cell[0] + d[0], y = cell[1] + d[1];
            if (x >= 0 && y >= 0 && x < h.length && y < h[0].length && !reachable[x][y] && h[x][y] >= h[cell[0]][cell[1]]) {
                reachable[x][y] = true;
                queue.add(new int[]{x, y});
            }
        }
    }
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 3: Reverse DFS with an Explicit Stack

The reverse search from the two oceans, but with a stack of cells instead of recursion, which is safe for large grids.

```java
public static List<List<Integer>> pacificAtlanticStack(int[][] heights) {
    int rows = heights.length, cols = heights[0].length;
    boolean[][] pacific = new boolean[rows][cols], atlantic = new boolean[rows][cols];
    for (int i = 0; i < rows; i++) { fill(heights, pacific, i, 0); fill(heights, atlantic, i, cols - 1); }
    for (int j = 0; j < cols; j++) { fill(heights, pacific, 0, j); fill(heights, atlantic, rows - 1, j); }
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++) if (pacific[i][j] && atlantic[i][j]) result.add(Arrays.asList(i, j));
    return result;
}

private static void fill(int[][] h, boolean[][] reachable, int row, int col) {
    if (reachable[row][col]) return;
    Deque<int[]> stack = new ArrayDeque<>();
    stack.push(new int[]{row, col});
    reachable[row][col] = true;
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!stack.isEmpty()) {
        int[] cell = stack.pop();
        for (int[] d : directions) {
            int x = cell[0] + d[0], y = cell[1] + d[1];
            if (x >= 0 && y >= 0 && x < h.length && y < h[0].length && !reachable[x][y] && h[x][y] >= h[cell[0]][cell[1]]) {
                reachable[x][y] = true;
                stack.push(new int[]{x, y});
            }
        }
    }
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 4: Fixed-Point Relaxation

Mark the border cells of each ocean and then sweep the whole grid again and again: a cell reaches an ocean when it has a neighbour that is not higher and already reaches it. Stop when a sweep changes nothing. Simple to reason about, but slower than a graph search.

```java
public static List<List<Integer>> pacificAtlanticRelax(int[][] heights) {
    int rows = heights.length, cols = heights[0].length;
    boolean[][] pacific = new boolean[rows][cols], atlantic = new boolean[rows][cols];
    for (int i = 0; i < rows; i++) { pacific[i][0] = true; atlantic[i][cols - 1] = true; }
    for (int j = 0; j < cols; j++) { pacific[0][j] = true; atlantic[rows - 1][j] = true; }
    relax(heights, pacific);
    relax(heights, atlantic);
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++) if (pacific[i][j] && atlantic[i][j]) result.add(Arrays.asList(i, j));
    return result;
}

private static void relax(int[][] h, boolean[][] reach) {
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    boolean changed = true;
    while (changed) {
        changed = false;
        for (int i = 0; i < h.length; i++) {
            for (int j = 0; j < h[0].length; j++) {
                if (reach[i][j]) continue;
                for (int[] d : directions) {
                    int x = i + d[0], y = j + d[1];
                    if (x >= 0 && y >= 0 && x < h.length && y < h[0].length && reach[x][y] && h[x][y] <= h[i][j]) {
                        reach[i][j] = true;
                        changed = true;
                        break;
                    }
                }
            }
        }
    }
}
```

- **Time Complexity:** O((m * n)^2) in the worst case
- **Space Complexity:** O(m * n)

### Approach 5: One BFS with a Bit Mask for Both Oceans

Store for each cell a two-bit mask (1 = reaches the Pacific, 2 = reaches the Atlantic) and run a single BFS seeded from both borders. A cell is queued again only when it gains a new bit, so every cell is processed at most twice; the answer is the cells with mask 3.

```java
public static List<List<Integer>> pacificAtlanticMask(int[][] heights) {
    int rows = heights.length, cols = heights[0].length;
    int[][] mask = new int[rows][cols];
    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < rows; i++) { mark(mask, queue, i, 0, 1); mark(mask, queue, i, cols - 1, 2); }
    for (int j = 0; j < cols; j++) { mark(mask, queue, 0, j, 1); mark(mask, queue, rows - 1, j, 2); }
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        for (int[] d : directions) {
            int x = cell[0] + d[0], y = cell[1] + d[1];
            if (x >= 0 && y >= 0 && x < rows && y < cols && heights[x][y] >= heights[cell[0]][cell[1]]) {
                mark(mask, queue, x, y, mask[cell[0]][cell[1]]);
            }
        }
    }
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++) if (mask[i][j] == 3) result.add(Arrays.asList(i, j));
    return result;
}

private static void mark(int[][] mask, Queue<int[]> queue, int i, int j, int bits) {
    if ((mask[i][j] | bits) != mask[i][j]) { mask[i][j] |= bits; queue.add(new int[]{i, j}); }
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 6: Recursive DFS with a Bit Mask

The same two-bit mask idea with a recursive DFS: a cell is explored again only when it receives a bit it did not have yet.

```java
public static List<List<Integer>> pacificAtlanticMaskDfs(int[][] heights) {
    int rows = heights.length, cols = heights[0].length;
    int[][] mask = new int[rows][cols];
    for (int i = 0; i < rows; i++) { spread(heights, mask, i, 0, 1); spread(heights, mask, i, cols - 1, 2); }
    for (int j = 0; j < cols; j++) { spread(heights, mask, 0, j, 1); spread(heights, mask, rows - 1, j, 2); }
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++) if (mask[i][j] == 3) result.add(Arrays.asList(i, j));
    return result;
}

private static void spread(int[][] h, int[][] mask, int i, int j, int bits) {
    if ((mask[i][j] | bits) == mask[i][j]) return;
    mask[i][j] |= bits;
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int[] d : directions) {
        int x = i + d[0], y = j + d[1];
        if (x >= 0 && y >= 0 && x < h.length && y < h[0].length && h[x][y] >= h[i][j]) spread(h, mask, x, y, bits);
    }
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 7: Reverse DFS from Both Oceans (Optimized)

Search backwards from the ocean borders to cells that are at least as high; the answer is the intersection of the two reachable sets. This is the version implemented in the repository.

```java
public static List<List<Integer>> pacificAtlantic(int[][] heights) {
    int rows = heights.length, cols = heights[0].length;
    boolean[][] pacific = new boolean[rows][cols], atlantic = new boolean[rows][cols];
    for (int i = 0; i < rows; i++) { dfs(heights, pacific, i, 0); dfs(heights, atlantic, i, cols - 1); }
    for (int j = 0; j < cols; j++) { dfs(heights, pacific, 0, j); dfs(heights, atlantic, rows - 1, j); }
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) if (pacific[i][j] && atlantic[i][j]) result.add(Arrays.asList(i, j));
    }
    return result;
}

private static void dfs(int[][] h, boolean[][] reachable, int i, int j) {
    reachable[i][j] = true;
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int[] d : directions) {
        int x = i + d[0], y = j + d[1];
        if (x >= 0 && y >= 0 && x < h.length && y < h[0].length && !reachable[x][y] && h[x][y] >= h[i][j]) dfs(h, reachable, x, y);
    }
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (DFS from Every Cell) | O((m * n)^2) | O(m * n) |
| 2 | Reverse BFS from Both Oceans | O(m * n) | O(m * n) |
| 3 | Reverse DFS with an Explicit Stack | O(m * n) | O(m * n) |
| 4 | Fixed-Point Relaxation | O((m * n)^2) in the worst case | O(m * n) |
| 5 | One BFS with a Bit Mask for Both Oceans | O(m * n) | O(m * n) |
| 6 | Recursive DFS with a Bit Mask | O(m * n) | O(m * n) |
| 7 | Reverse DFS from Both Oceans (Optimized) | O(m * n) | O(m * n) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### PacificAtlanticWaterFlow.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacificAtlanticWaterFlow {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{1, 2, 2, 3, 5}, {3, 2, 3, 4, 4}, {2, 4, 5, 3, 1}, {6, 7, 1, 4, 5}, {5, 1, 1, 2, 4}},
                {{1}}
        };
        for (int[][] heights : inputs) {
            System.out.println("Input  : heights = " + Arrays.deepToString(heights));
            System.out.println("Output : " + pacificAtlantic(heights));
            System.out.println();
        }
    }

    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        if (heights == null || heights.length == 0) {
            return result;
        }

        int rows = heights.length;
        int cols = heights[0].length;
        boolean[][] pacific = new boolean[rows][cols];
        boolean[][] atlantic = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            dfs(heights, pacific, i, 0);
            dfs(heights, atlantic, i, cols - 1);
        }
        for (int j = 0; j < cols; j++) {
            dfs(heights, pacific, 0, j);
            dfs(heights, atlantic, rows - 1, j);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    result.add(Arrays.asList(i, j));
                }
            }
        }
        return result;
    }

    private static void dfs(int[][] heights, boolean[][] reachable, int i, int j) {
        reachable[i][j] = true;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : directions) {
            int x = i + d[0];
            int y = j + d[1];
            if (x >= 0 && y >= 0 && x < heights.length && y < heights[0].length
                    && !reachable[x][y] && heights[x][y] >= heights[i][j]) {
                dfs(heights, reachable, x, y);
            }
        }
    }
}
```
