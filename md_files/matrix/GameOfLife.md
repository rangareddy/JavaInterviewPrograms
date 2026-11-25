# Game of Life

Conway's Game of Life is played on an `m x n` board where each cell is live (1) or dead (0). Every cell interacts with its eight neighbours: a live cell with fewer than two or more than three live neighbours dies; a live cell with two or three live neighbours lives; a dead cell with exactly three live neighbours becomes live. Compute the next state of the board in place, with all cells updating simultaneously.

## Algorithm Strategy (Encode Transitions in the Same Array)

1. Use two extra values: `2` means "was live, will die" and `3` means "was dead, will become live".
2. Count a cell's live neighbours by treating `1` and `2` as originally live.
3. Apply the rules and write `2` or `3` for cells that change.
4. A final pass maps `2 -> 0` and `3 -> 1`, so every cell used the original state of its neighbours.

## Example walkthrough

- For the 4x3 sample board `[[0,1,0],[0,0,1],[1,1,1],[0,0,0]]` the next generation is `[[0,0,0],[1,0,1],[0,1,1],[0,1,0]]`.
- The in-place encoding avoids a second board.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Eight neighbours per cell, plus a final pass.
- **Space Complexity:** `O(1)` — The board is updated in place.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Copy the Board

Compute the next state into a copy and copy it back. Uses `O(m * n)` extra space.

```java
public static void copy(int[][] b) {
    int m = b.length, n = b[0].length; int[][] nx = new int[m][n];
    for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) {
        int live = 0; for (int a = -1; a <= 1; a++) for (int c = -1; c <= 1; c++) { if (a == 0 && c == 0) continue; int r = i + a, q = j + c; if (r >= 0 && r < m && q >= 0 && q < n) live += b[r][q]; }
        nx[i][j] = (b[i][j] == 1 && (live == 2 || live == 3)) || (b[i][j] == 0 && live == 3) ? 1 : 0;
    }
    for (int i = 0; i < m; i++) b[i] = nx[i];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 2: In-Place with Transition States (Optimized)

Mark dying cells as 2 and born cells as 3, then normalise. This is the version implemented in the repository.

```java
public static void inPlace(int[][] b) {
    int m = b.length, n = b[0].length;
    for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) {
        int live = 0; for (int a = -1; a <= 1; a++) for (int c = -1; c <= 1; c++) { if (a == 0 && c == 0) continue; int r = i + a, q = j + c; if (r >= 0 && r < m && q >= 0 && q < n && (b[r][q] == 1 || b[r][q] == 2)) live++; }
        if (b[i][j] == 1 && (live < 2 || live > 3)) b[i][j] = 2; else if (b[i][j] == 0 && live == 3) b[i][j] = 3;
    }
    for (int i = 0; i < m; i++) for (int j = 0; j < n; j++) b[i][j] = b[i][j] == 1 || b[i][j] == 3 ? 1 : 0;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Copy the Board | O(m * n) | O(m * n) |
| 2 | In-Place with Transition States (Optimized) | O(m * n) | O(1) |

## Source Code

### GameOfLife.java

```java
import java.util.Arrays;

public class GameOfLife {

    public static void main(String[] args) {
        int[][][] boards = {
            {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}},
            {{1, 1}, {1, 0}},
            {{1}}
        };
        for (int[][] board : boards) {
            System.out.println("Input  : board = " + Arrays.deepToString(board));
            gameOfLife(board);
            System.out.println("Output : " + Arrays.deepToString(board));
            System.out.println();
        }
    }

    public static void gameOfLife(int[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int live = 0;
                for (int di = -1; di <= 1; di++) {
                    for (int dj = -1; dj <= 1; dj++) {
                        if (di == 0 && dj == 0) {
                            continue;
                        }
                        int r = i + di;
                        int c = j + dj;
                        if (r >= 0 && r < m && c >= 0 && c < n && (board[r][c] == 1 || board[r][c] == 2)) {
                            live++;
                        }
                    }
                }
                if (board[i][j] == 1 && (live < 2 || live > 3)) {
                    board[i][j] = 2;
                } else if (board[i][j] == 0 && live == 3) {
                    board[i][j] = 3;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = board[i][j] == 1 || board[i][j] == 3 ? 1 : 0;
            }
        }
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a cell's next state depends on its neighbours' current states, so overwriting a cell immediately would corrupt its neighbours' counts. Storing "will die" (2) and "will be born" (3) keeps the old state readable (1 and 2 were live, 0 and 3 were dead) until the final pass.

**Cell (0, 1)** of the sample board, a live cell with 1 live neighbour (the cell at (1, 2)):

| Step | Value | Meaning |
|------|-------|---------|
| start | 1 | live |
| count neighbours | 1 live neighbour | fewer than 2 |
| mark | 2 | will die |
| final pass | 0 | dead |

**Output for the sample board:** `[[0, 0, 0], [1, 0, 1], [0, 1, 1], [0, 1, 0]]`.
