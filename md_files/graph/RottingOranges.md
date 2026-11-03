# Rotting Oranges

In a grid, 0 is an empty cell, 1 a fresh orange and 2 a rotten orange. Every minute, each fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten. Return the minimum number of minutes until no fresh orange is left, or -1 if that is impossible.

## Algorithm Strategy (Multi-Source BFS by Minutes)

1. Put all initially rotten oranges into a queue and count the fresh oranges.
2. Process the queue in rounds; one round is one minute. Every fresh neighbour of a rotten orange becomes rotten (set to 2), is added to the queue and decreases the fresh counter.
3. Count a minute only for rounds in which at least one orange rotted.
4. When the queue is empty, return the minutes if no fresh orange remains, otherwise -1 (some orange is unreachable).

## Example walkthrough

- For `[[2,1,1],[1,1,0],[0,1,1]]`: minute 1 rots (0,1) and (1,0); minute 2 rots (0,2) and (1,1); minute 3 rots (2,1); minute 4 rots (2,2).
- Result: `4`. For `[[2,1,1],[0,1,1],[1,0,1]]` the orange at (2,0) is isolated: `-1`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Each cell becomes rotten and is queued at most once.
- **Space Complexity:** `O(m * n)` — The queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Minute-by-Minute Simulation of the Whole Grid

Repeat: scan the whole grid and rot every fresh orange that touches an orange that was rotten at the start of the minute. Stop when nothing changes. Simple, but each minute rescans the grid.

```java
public static int orangesRottingSimulation(int[][] grid) {
    int rows = grid.length, cols = grid[0].length;
    int[][] g = new int[rows][];
    for (int i = 0; i < rows; i++) g[i] = grid[i].clone();
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    int minutes = 0;
    while (true) {
        List<int[]> toRot = new ArrayList<>();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (g[i][j] == 1)
                    for (int[] d : directions) {
                        int x = i + d[0], y = j + d[1];
                        if (x >= 0 && y >= 0 && x < rows && y < cols && g[x][y] == 2) { toRot.add(new int[]{i, j}); break; }
                    }
        if (toRot.isEmpty()) break;
        for (int[] cell : toRot) g[cell[0]][cell[1]] = 2;
        minutes++;
    }
    for (int[] row : g) for (int value : row) if (value == 1) return -1;
    return minutes;
}
```

- **Time Complexity:** O((m * n)^2)
- **Space Complexity:** O(m * n)

### Approach 2: BFS from Every Fresh Orange (Brute Force)

The time for a fresh orange to rot is its distance to the nearest rotten orange (through non-empty cells); the answer is the largest of these distances. Compute each distance with its own BFS.

```java
public static int orangesRottingPerOrange(int[][] grid) {
    int rows = grid.length, cols = grid[0].length, answer = 0;
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (grid[i][j] != 1) continue;
            boolean[][] visited = new boolean[rows][cols];
            Queue<int[]> queue = new LinkedList<>();
            queue.add(new int[]{i, j, 0});
            visited[i][j] = true;
            int found = -1;
            while (!queue.isEmpty() && found < 0) {
                int[] cell = queue.poll();
                if (grid[cell[0]][cell[1]] == 2) { found = cell[2]; break; }
                for (int[] d : directions) {
                    int x = cell[0] + d[0], y = cell[1] + d[1];
                    if (x >= 0 && y >= 0 && x < rows && y < cols && !visited[x][y] && grid[x][y] != 0) { visited[x][y] = true; queue.add(new int[]{x, y, cell[2] + 1}); }
                }
            }
            if (found < 0) return -1;
            answer = Math.max(answer, found);
        }
    }
    return answer;
}
```

- **Time Complexity:** O((m * n)^2)
- **Space Complexity:** O(m * n)

### Approach 3: BFS Storing the Rotting Time in Each Cell

A single multi-source BFS that does not process level by level: every queue entry carries the minute at which the orange rots, and the answer is the largest minute seen.

```java
public static int orangesRottingTimes(int[][] grid) {
    int rows = grid.length, cols = grid[0].length, fresh = 0, last = 0;
    int[][] g = new int[rows][];
    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < rows; i++) {
        g[i] = grid[i].clone();
        for (int j = 0; j < cols; j++) {
            if (g[i][j] == 2) queue.add(new int[]{i, j, 0}); else if (g[i][j] == 1) fresh++;
        }
    }
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        last = Math.max(last, cell[2]);
        for (int[] d : directions) {
            int x = cell[0] + d[0], y = cell[1] + d[1];
            if (x >= 0 && y >= 0 && x < rows && y < cols && g[x][y] == 1) { g[x][y] = 2; fresh--; queue.add(new int[]{x, y, cell[2] + 1}); }
        }
    }
    return fresh == 0 ? last : -1;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 4: Multi-Source BFS by Rounds (Optimized)

Queue all rotten oranges, rot the fresh neighbours round by round and count the rounds in which something rotted. This is the version implemented in the repository.

```java
public static int orangesRotting(int[][] grid) {
    int rows = grid.length, cols = grid[0].length, fresh = 0;
    int[][] g = new int[rows][];
    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < rows; i++) {
        g[i] = grid[i].clone();
        for (int j = 0; j < cols; j++) {
            if (g[i][j] == 2) queue.add(new int[]{i, j}); else if (g[i][j] == 1) fresh++;
        }
    }
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    int minutes = 0;
    while (!queue.isEmpty() && fresh > 0) {
        minutes++;
        for (int size = queue.size(); size > 0; size--) {
            int[] cell = queue.poll();
            for (int[] d : directions) {
                int x = cell[0] + d[0], y = cell[1] + d[1];
                if (x >= 0 && y >= 0 && x < rows && y < cols && g[x][y] == 1) { g[x][y] = 2; fresh--; queue.add(new int[]{x, y}); }
            }
        }
    }
    return fresh == 0 ? minutes : -1;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Minute-by-Minute Simulation of the Whole Grid | O((m * n)^2) | O(m * n) |
| 2 | BFS from Every Fresh Orange (Brute Force) | O((m * n)^2) | O(m * n) |
| 3 | BFS Storing the Rotting Time in Each Cell | O(m * n) | O(m * n) |
| 4 | Multi-Source BFS by Rounds (Optimized) | O(m * n) | O(m * n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### RottingOranges.java

```java
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class RottingOranges {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{2, 1, 1}, {1, 1, 0}, {0, 1, 1}},
                {{2, 1, 1}, {0, 1, 1}, {1, 0, 1}},
                {{0, 2}},
                {{1}},
                {{0}}
        };
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + orangesRotting(grid));
            System.out.println();
        }
    }

    public static int orangesRotting(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] copy = new int[rows][];
        Queue<int[]> queue = new LinkedList<>();
        int fresh = 0;
        for (int i = 0; i < rows; i++) {
            copy[i] = grid[i].clone();
            for (int j = 0; j < cols; j++) {
                if (copy[i][j] == 2) {
                    queue.add(new int[]{i, j});
                } else if (copy[i][j] == 1) {
                    fresh++;
                }
            }
        }

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int minutes = 0;
        while (!queue.isEmpty() && fresh > 0) {
            minutes++;
            for (int size = queue.size(); size > 0; size--) {
                int[] cell = queue.poll();
                for (int[] d : directions) {
                    int x = cell[0] + d[0];
                    int y = cell[1] + d[1];
                    if (x >= 0 && y >= 0 && x < rows && y < cols && copy[x][y] == 1) {
                        copy[x][y] = 2;
                        fresh--;
                        queue.add(new int[]{x, y});
                    }
                }
            }
        }
        return fresh == 0 ? minutes : -1;
    }
}
```
