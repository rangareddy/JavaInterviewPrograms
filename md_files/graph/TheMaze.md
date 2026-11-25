# The Maze

A ball rolls in a maze (grid of `0` empty and `1` walls). It rolls in one direction (up, down, left or right) until it hits a wall, then it can choose a new direction. Given the `start` and `destination` coordinates, return `true` if the ball can stop at the destination.

## Algorithm Strategy (BFS over Stopping Points)

1. From a stopping point, try each of the four directions and roll until the next cell is a wall or the border.
2. The cell where the ball stops is a new node; if it has not been visited, add it to the queue.
3. The ball must stop on the destination, not just pass through it, so test destination equality on stopping points.
4. If the queue empties first, return `false`.

## Example walkthrough

- In the classic 5x5 maze the ball can roll left, down, left, down, right, down, right and stop at the destination.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(m * n * (m + n))` — Each cell is a stopping point at most once and each roll is at most max(m, n).
- **Space Complexity:** `O(m * n)` — The visited array and the queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS over Stopping Points

Same graph, explored depth first.

```java
public static boolean dfs(int[][] m, int[] s, int[] d) { return go(m, s[0], s[1], d, new boolean[m.length][m[0].length]); }
private static boolean go(int[][] m, int r, int c, int[] d, boolean[][] v) {
    if (v[r][c]) return false; v[r][c] = true; if (r == d[0] && c == d[1]) return true; int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int[] x : dirs) { int a = r, b = c; while (a + x[0] >= 0 && a + x[0] < m.length && b + x[1] >= 0 && b + x[1] < m[0].length && m[a + x[0]][b + x[1]] == 0) { a += x[0]; b += x[1]; } if (go(m, a, b, d, v)) return true; }
    return false;
}
```

- **Time Complexity:** O(m * n * (m + n))
- **Space Complexity:** O(m * n)

### Approach 2: BFS over Stopping Points (Optimized)

Roll in each direction until a wall and enqueue the stopping cell. This is the version implemented in the repository.

```java
public static boolean bfs(int[][] m, int[] s, int[] d) {
    java.util.Queue<int[]> q = new java.util.ArrayDeque<>(); boolean[][] v = new boolean[m.length][m[0].length]; q.add(s); v[s[0]][s[1]] = true; int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!q.isEmpty()) { int[] c = q.poll(); if (c[0] == d[0] && c[1] == d[1]) return true; for (int[] x : dirs) { int a = c[0], b = c[1]; while (a + x[0] >= 0 && a + x[0] < m.length && b + x[1] >= 0 && b + x[1] < m[0].length && m[a + x[0]][b + x[1]] == 0) { a += x[0]; b += x[1]; } if (!v[a][b]) { v[a][b] = true; q.add(new int[]{a, b}); } } }
    return false;
}
```

- **Time Complexity:** O(m * n * (m + n))
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS over Stopping Points | O(m * n * (m + n)) | O(m * n) |
| 2 | BFS over Stopping Points (Optimized) | O(m * n * (m + n)) | O(m * n) |

## Source Code

### TheMaze.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class TheMaze {

    public static void main(String[] args) {
        int[][] maze = {{0, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 1, 0}, {1, 1, 0, 1, 1}, {0, 0, 0, 0, 0}};
        int[][][] queries = {{{0, 4}, {4, 4}}, {{0, 4}, {3, 2}}, {{0, 4}, {0, 4}}};
        for (int[][] q : queries) {
            System.out.println("Input  : start = " + Arrays.toString(q[0]) + ", destination = " + Arrays.toString(q[1]));
            System.out.println("Output : " + hasPath(maze, q[0], q[1]));
            System.out.println();
        }
    }

    public static boolean hasPath(int[][] maze, int[] start, int[] destination) {
        if (maze == null || maze.length == 0) {
            return false;
        }
        int m = maze.length;
        int n = maze[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(start);
        visited[start[0]][start[1]] = true;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            if (cell[0] == destination[0] && cell[1] == destination[1]) {
                return true;
            }
            for (int[] d : dirs) {
                int r = cell[0];
                int c = cell[1];
                while (r + d[0] >= 0 && r + d[0] < m && c + d[1] >= 0 && c + d[1] < n && maze[r + d[0]][c + d[1]] == 0) {
                    r += d[0];
                    c += d[1];
                }
                if (!visited[r][c]) {
                    visited[r][c] = true;
                    queue.add(new int[]{r, c});
                }
            }
        }
        return false;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the ball cannot stop anywhere it likes, only next to a wall. So the nodes of the graph are the stopping points, and each node has at most four edges, one for each rolling direction.

**Input:** the classic 5x5 maze, `start = (0, 4)`, `destination = (4, 4)`.

| Stopping point | Rolls that lead to new stops |
|----------------|-------------------------------|
| (0, 4) | down to (2, 4), left to (0, 3) |
| (2, 4) | left to (2, 3)... |
| ... | the BFS continues until (4, 4) is a stopping point |

**Output:** `true`.
