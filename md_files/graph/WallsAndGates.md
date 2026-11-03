# Walls and Gates

Given an `m x n` grid of rooms with the values -1 (a wall), 0 (a gate) and `INF = 2147483647` (an empty room), fill every empty room with the distance (number of steps) to its nearest gate. A room that cannot reach a gate stays `INF`.

## Algorithm Strategy (Multi-Source BFS from All Gates)

1. Instead of searching from every room, start from all gates at the same time: put every gate into the BFS queue.
2. Process the queue level by level. For a cell with distance `d`, every unvisited empty neighbour is reached with distance `d + 1`.
3. A neighbour is "unvisited" while it still holds `INF`; overwriting it with the distance both records the answer and marks it visited.
4. Because BFS expands in increasing distance, the first time a room is reached it gets the shortest distance to some gate.

## Example walkthrough

- For the 4 x 4 example, the gates at (0,2) and (3,0) spread outwards: the room (0,3) next to the first gate gets 1, (1,2) gets 1, (0,0) is 3 steps from the second gate.
- Result: `[[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Each cell enters the queue at most once.
- **Space Complexity:** `O(m * n)` — The queue in the worst case.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BFS from Every Empty Room (Brute Force)

For each empty room run a separate BFS until the nearest gate is found. Correct, but each room repeats a large part of the work of its neighbours.

```java
public static void wallsAndGatesBrute(int[][] rooms) {
    int rows = rooms.length, cols = rooms[0].length;
    int[][] result = new int[rows][];
    for (int i = 0; i < rows; i++) result[i] = rooms[i].clone();
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++)
            if (rooms[i][j] == Integer.MAX_VALUE) result[i][j] = nearestGate(rooms, i, j);
    for (int i = 0; i < rows; i++) rooms[i] = result[i];
}

private static int nearestGate(int[][] rooms, int si, int sj) {
    int rows = rooms.length, cols = rooms[0].length;
    boolean[][] visited = new boolean[rows][cols];
    Queue<int[]> queue = new LinkedList<>();
    queue.add(new int[]{si, sj, 0});
    visited[si][sj] = true;
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        if (rooms[cell[0]][cell[1]] == 0) return cell[2];
        for (int[] d : directions) {
            int x = cell[0] + d[0], y = cell[1] + d[1];
            if (x >= 0 && y >= 0 && x < rows && y < cols && !visited[x][y] && rooms[x][y] != -1) { visited[x][y] = true; queue.add(new int[]{x, y, cell[2] + 1}); }
        }
    }
    return Integer.MAX_VALUE;
}
```

- **Time Complexity:** O((m * n)^2)
- **Space Complexity:** O(m * n)

### Approach 2: DFS from Every Gate with Relaxation

Start a DFS at each gate and only continue into a room when the new distance is smaller than the value stored there. Rooms may be updated several times, but the result is correct.

```java
public static void wallsAndGatesDfs(int[][] rooms) {
    for (int i = 0; i < rooms.length; i++)
        for (int j = 0; j < rooms[0].length; j++)
            if (rooms[i][j] == 0) spread(rooms, i, j, 0);
}

private static void spread(int[][] rooms, int i, int j, int distance) {
    if (i < 0 || j < 0 || i >= rooms.length || j >= rooms[0].length || rooms[i][j] < distance) return;     // wall, or a shorter way is known
    rooms[i][j] = distance;
    spread(rooms, i + 1, j, distance + 1); spread(rooms, i - 1, j, distance + 1);
    spread(rooms, i, j + 1, distance + 1); spread(rooms, i, j - 1, distance + 1);
}
```

- **Time Complexity:** O((m * n)^2) worst case
- **Space Complexity:** O(m * n) recursion stack

### Approach 3: Repeated Sweeps until Nothing Changes

Sweep over the grid and let every empty room take `1 + the smallest neighbouring distance`; repeat until a sweep changes nothing (Bellman-Ford on a grid). Easy to write, slowest of the working approaches.

```java
public static void wallsAndGatesSweeps(int[][] rooms) {
    int rows = rooms.length, cols = rooms[0].length;
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    boolean changed = true;
    while (changed) {
        changed = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (rooms[i][j] <= 0) continue;
                for (int[] d : directions) {
                    int x = i + d[0], y = j + d[1];
                    if (x >= 0 && y >= 0 && x < rows && y < cols && rooms[x][y] >= 0 && rooms[x][y] != Integer.MAX_VALUE && rooms[x][y] + 1 < rooms[i][j]) {
                        rooms[i][j] = rooms[x][y] + 1;
                        changed = true;
                    }
                }
            }
        }
    }
}
```

- **Time Complexity:** O((m * n)^2) worst case
- **Space Complexity:** O(1)

### Approach 4: Multi-Source BFS from All Gates (Optimized)

Put all gates in the queue at once and expand outwards; the first time a room is reached is its shortest distance. This is the version implemented in the repository.

```java
public static void wallsAndGates(int[][] rooms) {
    int rows = rooms.length, cols = rooms[0].length;
    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++)
            if (rooms[i][j] == 0) queue.add(new int[]{i, j});
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        for (int[] d : directions) {
            int x = cell[0] + d[0], y = cell[1] + d[1];
            if (x >= 0 && y >= 0 && x < rows && y < cols && rooms[x][y] == Integer.MAX_VALUE) {
                rooms[x][y] = rooms[cell[0]][cell[1]] + 1;
                queue.add(new int[]{x, y});
            }
        }
    }
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | BFS from Every Empty Room (Brute Force) | O((m * n)^2) | O(m * n) |
| 2 | DFS from Every Gate with Relaxation | O((m * n)^2) worst case | O(m * n) recursion stack |
| 3 | Repeated Sweeps until Nothing Changes | O((m * n)^2) worst case | O(1) |
| 4 | Multi-Source BFS from All Gates (Optimized) | O(m * n) | O(m * n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### WallsAndGates.java

```java
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class WallsAndGates {

    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        int[][][] inputs = {
                {{INF, -1, 0, INF}, {INF, INF, INF, -1}, {INF, -1, INF, -1}, {0, -1, INF, INF}},
                {{-1}},
                {{INF, INF}, {INF, 0}}
        };
        for (int[][] rooms : inputs) {
            System.out.println("Input  : rooms = " + show(rooms));
            wallsAndGates(rooms);
            System.out.println("Output : " + show(rooms));
            System.out.println();
        }
    }

    public static void wallsAndGates(int[][] rooms) {
        if (rooms == null || rooms.length == 0) {
            return;
        }

        int rows = rooms.length;
        int cols = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (rooms[i][j] == 0) {
                    queue.add(new int[]{i, j});
                }
            }
        }

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            for (int[] d : directions) {
                int x = cell[0] + d[0];
                int y = cell[1] + d[1];
                if (x >= 0 && y >= 0 && x < rows && y < cols && rooms[x][y] == INF) {
                    rooms[x][y] = rooms[cell[0]][cell[1]] + 1;
                    queue.add(new int[]{x, y});
                }
            }
        }
    }

    private static String show(int[][] rooms) {
        return Arrays.deepToString(rooms).replace(String.valueOf(INF), "INF");
    }
}
```
