# Minimum Knight Moves

On an infinite chessboard with coordinates from `-infinity` to `+infinity`, a knight starts at `(0, 0)`. Given a target `(x, y)`, return the minimum number of moves for the knight to reach it. A knight moves two squares in one direction and one square perpendicular.

## Algorithm Strategy (BFS on the First Quadrant)

1. The board is symmetric, so replace `(x, y)` by `(|x|, |y|)`.
2. BFS from `(0, 0)` over the eight knight moves, but allow squares slightly outside the quadrant (a small margin, for example coordinates down to -2) since the shortest path may dip there.
3. Keep a visited set of coordinates.
4. The first time `(|x|, |y|)` is dequeued, the number of levels is the answer.

## Example walkthrough

- For `(2, 1)` one knight move is enough.
- For `(5, 5)` the shortest path takes 4 moves.

## Complexity Analysis

- **Time Complexity:** `O(x * y)` — The BFS visits squares in the bounding box.
- **Space Complexity:** `O(x * y)` — The visited set.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DP with Memoisation

Use the recurrence `f(x, y) = 1 + min(f(|x - 1|, |y - 2|), f(|x - 2|, |y - 1|))` with base cases.

```java
public static int memo(int x, int y) { return go(Math.abs(x), Math.abs(y), new java.util.HashMap<>()); }
private static int go(int x, int y, java.util.Map<String, Integer> m) {
    if (x + y == 0) return 0; if (x + y == 2) return 2; String k = x + "," + y; Integer c = m.get(k); if (c != null) return c;
    int r = Math.min(go(Math.abs(x - 1), Math.abs(y - 2), m), go(Math.abs(x - 2), Math.abs(y - 1), m)) + 1; m.put(k, r); return r;
}
```

- **Time Complexity:** O(x * y)
- **Space Complexity:** O(x * y)

### Approach 2: BFS from the Origin (Optimized)

Level-by-level search with a visited set and a small margin outside the quadrant. This is the version implemented in the repository.

```java
public static int bfs(int x, int y) {
    int tx = Math.abs(x), ty = Math.abs(y); int[][] mv = {{1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};
    java.util.Queue<int[]> q = new java.util.ArrayDeque<>(); java.util.Set<Long> seen = new java.util.HashSet<>(); q.add(new int[]{0, 0}); seen.add(0L); int moves = 0;
    while (!q.isEmpty()) { for (int z = q.size(); z > 0; z--) { int[] c = q.poll(); if (c[0] == tx && c[1] == ty) return moves; for (int[] m : mv) { int a = c[0] + m[0], b = c[1] + m[1]; if (a >= -2 && b >= -2 && seen.add(((long) a << 32) ^ (b & 0xffffffffL))) q.add(new int[]{a, b}); } } moves++; }
    return -1;
}
```

- **Time Complexity:** O(x * y)
- **Space Complexity:** O(x * y)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DP with Memoisation | O(x * y) | O(x * y) |
| 2 | BFS from the Origin (Optimized) | O(x * y) | O(x * y) |

## Source Code

### MinimumKnightMoves.java

```java
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class MinimumKnightMoves {

    public static void main(String[] args) {
        int[][] targets = {{2, 1}, {5, 5}, {0, 0}, {-3, 4}};
        for (int[] t : targets) {
            System.out.println("Input  : x = " + t[0] + ", y = " + t[1]);
            System.out.println("Output : " + minKnightMoves(t[0], t[1]));
            System.out.println();
        }
    }

    private static final int[][] MOVES = {{1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};

    public static int minKnightMoves(int x, int y) {
        int tx = Math.abs(x);
        int ty = Math.abs(y);
        Queue<int[]> queue = new ArrayDeque<>();
        Set<Long> visited = new HashSet<>();
        queue.add(new int[]{0, 0});
        visited.add(key(0, 0));
        int moves = 0;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                int[] cell = queue.poll();
                if (cell[0] == tx && cell[1] == ty) {
                    return moves;
                }
                for (int[] m : MOVES) {
                    int nx = cell[0] + m[0];
                    int ny = cell[1] + m[1];
                    if (nx >= -2 && ny >= -2 && visited.add(key(nx, ny))) {
                        queue.add(new int[]{nx, ny});
                    }
                }
            }
            moves++;
        }
        return -1;
    }

    private static long key(int x, int y) {
        return ((long) x << 32) ^ (y & 0xffffffffL);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a knight reaches every square in the same number of moves as its mirror images, so only the first quadrant needs to be searched. The shortest path to some squares briefly leaves the quadrant, which is why the BFS keeps a small margin below zero.

**Input:** `(x, y) = (5, 5)`.

| Moves | A square reachable in that many moves |
|-------|----------------------------------------|
| 0 | (0, 0) |
| 1 | (1, 2), (2, 1) |
| 2 | (3, 3), (1, 4)... |
| 3 | (4, 5), (5, 4)... |
| 4 | (5, 5) |

**Output for this input:** `4`. For the first sample in `main`, `(2, 1)`, the output is `1`.
