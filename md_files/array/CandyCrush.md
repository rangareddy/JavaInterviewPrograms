# Candy Crush

A `m x n` board holds candies of different types (positive integers); `0` means an empty cell. Repeatedly: crush every run of three or more candies of the same type in a row or a column at the same time (set them to 0), then let the remaining candies fall down to fill the empty cells. Stop when no more runs exist and return the stable board.

## Algorithm Strategy (Mark by Negation, Crush, Drop, Repeat)

1. Scan every row and column for runs of three or more equal non-zero candies and mark all of them by making them negative (so the original type is still readable through `Math.abs`).
2. If nothing was marked, the board is stable.
3. Otherwise, for each column compact the non-negative candies to the bottom and fill the top with zeros.
4. Repeat from the first step.

## Example walkthrough

- A column of three equal candies is marked, crushed and the candies above fall down; the fall may create new runs, so the process repeats.
- The printed result is the final stable board for the classic 10x5 sample.

## Complexity Analysis

- **Time Complexity:** `O((m * n)^2) worst case` — Each round is O(m * n) and rounds can repeat many times.
- **Space Complexity:** `O(1)` — The board is updated in place.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Mark in a Separate Boolean Grid

Mark crushable cells in a second grid, then crush and drop. Uses extra memory but is easy to follow.

```java
public static int[][] separate(int[][] in) {
    int R = in.length, C = in[0].length; int[][] b = new int[R][]; for (int i = 0; i < R; i++) b[i] = in[i].clone();
    while (true) {
        boolean[][] mark = new boolean[R][C]; boolean any = false;
        for (int i = 0; i < R; i++) for (int j = 0; j + 2 < C; j++) if (b[i][j] != 0 && b[i][j] == b[i][j + 1] && b[i][j] == b[i][j + 2]) { mark[i][j] = mark[i][j + 1] = mark[i][j + 2] = true; any = true; }
        for (int j = 0; j < C; j++) for (int i = 0; i + 2 < R; i++) if (b[i][j] != 0 && b[i][j] == b[i + 1][j] && b[i][j] == b[i + 2][j]) { mark[i][j] = mark[i + 1][j] = mark[i + 2][j] = true; any = true; }
        if (!any) return b;
        for (int j = 0; j < C; j++) { int w = R - 1; for (int i = R - 1; i >= 0; i--) if (!mark[i][j] && b[i][j] != 0) b[w--][j] = b[i][j]; while (w >= 0) b[w--][j] = 0; }
    }
}
```

- **Time Complexity:** O((m * n)^2)
- **Space Complexity:** O(m * n)

### Approach 2: Mark by Negation in Place (Optimized)

Mark crushable cells by making them negative, then drop. This is the version implemented in the repository.

```java
public static int[][] inPlace(int[][] in) {
    int R = in.length, C = in[0].length; int[][] b = new int[R][]; for (int i = 0; i < R; i++) b[i] = in[i].clone(); boolean crushed = true;
    while (crushed) { crushed = false;
        for (int i = 0; i < R; i++) for (int j = 0; j + 2 < C; j++) { int v = Math.abs(b[i][j]); if (v != 0 && v == Math.abs(b[i][j + 1]) && v == Math.abs(b[i][j + 2])) { b[i][j] = b[i][j + 1] = b[i][j + 2] = -v; crushed = true; } }
        for (int j = 0; j < C; j++) for (int i = 0; i + 2 < R; i++) { int v = Math.abs(b[i][j]); if (v != 0 && v == Math.abs(b[i + 1][j]) && v == Math.abs(b[i + 2][j])) { b[i][j] = b[i + 1][j] = b[i + 2][j] = -v; crushed = true; } }
        if (crushed) for (int j = 0; j < C; j++) { int w = R - 1; for (int i = R - 1; i >= 0; i--) if (b[i][j] > 0) b[w--][j] = b[i][j]; while (w >= 0) b[w--][j] = 0; } }
    return b;
}
```

- **Time Complexity:** O((m * n)^2)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Mark in a Separate Boolean Grid | O((m * n)^2) | O(m * n) |
| 2 | Mark by Negation in Place (Optimized) | O((m * n)^2) | O(1) |

## Source Code

### CandyCrush.java

```java
import java.util.Arrays;

public class CandyCrush {

    public static void main(String[] args) {
        int[][] board = {
            {110, 5, 112, 113, 114}, {210, 211, 5, 213, 214}, {310, 311, 3, 313, 314}, {410, 411, 412, 5, 414}, {5, 1, 512, 3, 3},
            {610, 4, 1, 613, 614}, {710, 1, 2, 713, 714}, {810, 1, 2, 1, 1}, {1, 1, 2, 2, 2}, {4, 1, 4, 4, 1014}
        };
        System.out.println("Input  : board = " + Arrays.deepToString(board));
        System.out.println("Output : " + Arrays.deepToString(candyCrush(board)));
    }

    public static int[][] candyCrush(int[][] board) {
        if (board == null || board.length == 0) {
            return board;
        }
        int rows = board.length;
        int cols = board[0].length;
        boolean crushed = true;
        while (crushed) {
            crushed = false;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j + 2 < cols; j++) {
                    int v = Math.abs(board[i][j]);
                    if (v != 0 && v == Math.abs(board[i][j + 1]) && v == Math.abs(board[i][j + 2])) {
                        board[i][j] = board[i][j + 1] = board[i][j + 2] = -v;
                        crushed = true;
                    }
                }
            }
            for (int j = 0; j < cols; j++) {
                for (int i = 0; i + 2 < rows; i++) {
                    int v = Math.abs(board[i][j]);
                    if (v != 0 && v == Math.abs(board[i + 1][j]) && v == Math.abs(board[i + 2][j])) {
                        board[i][j] = board[i + 1][j] = board[i + 2][j] = -v;
                        crushed = true;
                    }
                }
            }
            if (crushed) {
                for (int j = 0; j < cols; j++) {
                    int write = rows - 1;
                    for (int i = rows - 1; i >= 0; i--) {
                        if (board[i][j] > 0) {
                            board[write--][j] = board[i][j];
                        }
                    }
                    while (write >= 0) {
                        board[write--][j] = 0;
                    }
                }
            }
        }
        return board;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: crushing must use the board as it was at the start of the round, so first mark everything that will be crushed (negating keeps the type readable), then remove all marked cells at once and let the rest fall. Repeat until a round marks nothing.

**Small example:** one column `[1, 1, 1, 2]` (top to bottom).

| Round | Action | Column afterwards |
|-------|--------|--------------------|
| 1 | mark the three 1s | [-1, -1, -1, 2] |
| 1 | crush and drop | [0, 0, 0, 2] |
| 2 | nothing to mark | stable |

The printed board for the classic 10x5 sample is the stable result after several rounds.

**Output:** the stable board printed by `main`.
