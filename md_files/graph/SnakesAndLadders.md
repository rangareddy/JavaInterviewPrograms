# Snakes and Ladders

You are given an `n x n` board numbered `1` to `n^2` in a boustrophedon style: starting from the bottom-left and alternating the direction every row. A cell with a value other than -1 is the start of a snake or a ladder and jumps to that square. From square `s` you roll a die and move to any of `s + 1` to `s + 6`; if the target holds a snake or ladder you must take it. Return the least number of moves to reach square `n^2`, or -1 if impossible.

## Algorithm Strategy (BFS over Squares)

1. Convert a square number to its row and column: `row = (n - 1) - (s - 1) / n`, and the column depends on the parity of the row counted from the bottom.
2. BFS from square 1; the queue holds squares and the level is the number of moves.
3. For each square, try the six die outcomes; if the cell has a jump, move to its destination, then mark and enqueue the square if it is unvisited.
4. Return the level at which square `n^2` is reached, or -1.

## Example walkthrough

- On the classic 6x6 board with a ladder 2 -> 15, 14 -> 35 and others, the quickest route needs 4 rolls.
- Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Each square is visited once with six moves.
- **Space Complexity:** `O(n^2)` — The visited set and queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS with Memoised Minimum

Recursively try every roll and keep the minimum. It needs cycle protection and is much slower than BFS.

```java
public static int dfs(int[][] b) {
    int n = b.length; int[] best = {Integer.MAX_VALUE}; int[] depth = new int[n * n + 1]; java.util.Arrays.fill(depth, Integer.MAX_VALUE); go(b, 1, 0, depth, best); return best[0] == Integer.MAX_VALUE ? -1 : best[0];
}
private static void go(int[][] b, int s, int d, int[] depth, int[] best) {
    int n = b.length, t = n * n; if (d >= depth[s]) return; depth[s] = d; if (s == t) { best[0] = Math.min(best[0], d); return; }
    for (int die = 1; die <= 6 && s + die <= t; die++) { int nx = s + die; int r = (nx - 1) / n, c = (nx - 1) % n; if (r % 2 == 1) c = n - 1 - c; int v = b[n - 1 - r][c]; go(b, v != -1 ? v : nx, d + 1, depth, best); }
}
```

- **Time Complexity:** O(n^2 * 6) with pruning
- **Space Complexity:** O(n^2)

### Approach 2: BFS over Squares (Optimized)

Level-order search, where the level is the number of rolls. This is the version implemented in the repository.

```java
public static int bfs(int[][] b) {
    int n = b.length, t = n * n; boolean[] seen = new boolean[t + 1]; java.util.Queue<Integer> q = new java.util.ArrayDeque<>(); q.add(1); seen[1] = true; int moves = 0;
    while (!q.isEmpty()) { for (int z = q.size(); z > 0; z--) { int s = q.poll(); if (s == t) return moves; for (int die = 1; die <= 6 && s + die <= t; die++) { int nx = s + die; int r = (nx - 1) / n, c = (nx - 1) % n; if (r % 2 == 1) c = n - 1 - c; int v = b[n - 1 - r][c]; if (v != -1) nx = v; if (!seen[nx]) { seen[nx] = true; q.add(nx); } } } moves++; }
    return -1;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS with Memoised Minimum | O(n^2 * 6) with pruning | O(n^2) |
| 2 | BFS over Squares (Optimized) | O(n^2) | O(n^2) |

## Source Code

### SnakesAndLadders.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class SnakesAndLadders {

    public static void main(String[] args) {
        int[][][] boards = {
            {{-1, -1, -1, -1, -1, -1}, {-1, -1, -1, -1, -1, -1}, {-1, -1, -1, -1, -1, -1}, {-1, 35, -1, -1, 13, -1}, {-1, -1, -1, -1, -1, -1}, {-1, 15, -1, -1, -1, -1}},
            {{-1, -1}, {-1, 3}},
            {{1, 1, -1}, {1, 1, 1}, {-1, 1, 1}}
        };
        for (int[][] board : boards) {
            System.out.println("Input  : board = " + Arrays.deepToString(board));
            System.out.println("Output : " + snakesAndLadders(board));
            System.out.println();
        }
    }

    public static int snakesAndLadders(int[][] board) {
        int n = board.length;
        int target = n * n;
        boolean[] seen = new boolean[target + 1];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(1);
        seen[1] = true;
        int moves = 0;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                int square = queue.poll();
                if (square == target) {
                    return moves;
                }
                for (int die = 1; die <= 6 && square + die <= target; die++) {
                    int next = square + die;
                    int value = cell(board, next);
                    if (value != -1) {
                        next = value;
                    }
                    if (!seen[next]) {
                        seen[next] = true;
                        queue.add(next);
                    }
                }
            }
            moves++;
        }
        return -1;
    }

    private static int cell(int[][] board, int square) {
        int n = board.length;
        int rowFromBottom = (square - 1) / n;
        int column = (square - 1) % n;
        if (rowFromBottom % 2 == 1) {
            column = n - 1 - column;
        }
        return board[n - 1 - rowFromBottom][column];
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: every roll costs one move, so the fewest moves is a shortest path in an unweighted graph whose nodes are squares and whose edges are the six die outcomes (with snakes and ladders applied on arrival). BFS finds it level by level.

**Input:** the 6x6 board from `main`: a ladder on square 2 (to 15), a ladder on square 14 (to 35) and a snake on square 17 (to 13).

| Move | Roll | Lands on | Jumps to |
|------|------|----------|----------|
| 1 | 1 | 2 | 15 (ladder) |
| 2 | 2 | 17 | 13 (snake) |
| 3 | 1 | 14 | 35 (ladder) |
| 4 | 1 | 36 | (the last square) |

No sequence of rolls does it in fewer moves.

**Output:** `4`.
