# Design Tic-Tac-Toe

Design a Tic-Tac-Toe game on an `n x n` board for two players. `move(row, col, player)` places the mark of `player` (1 or 2) on an empty cell and returns the winner after the move: `1` or `2` if that player now has a full row, column or diagonal, and `0` if there is no winner yet. Moves are always valid.

## Algorithm Strategy (Counters per Line)

1. Keep one counter for each of the `n` rows, `n` columns and the two diagonals.
2. A move by player 1 adds +1 to its row, its column, and its diagonals; player 2 adds -1.
3. If the absolute value of any of those counters reaches `n`, that player has filled the line.
4. Return the player, or 0 if nothing reached `n`.

## Example walkthrough

- On a 3x3 board player 1 plays (0,0), (0,1), (0,2): the row-0 counter reaches +3.
- The move at (0,2) returns `1`; earlier moves returned `0`.

## Complexity Analysis

- **Time Complexity:** `O(1) per move` — Four counters are updated.
- **Space Complexity:** `O(n)` — The row and column counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Scan the Board After Every Move

Store the board and check the row, column and diagonals of the move. O(n) per move.

```java
public static class Board {
    private final int n; private final int[][] b;
    public Board(int n) { this.n = n; b = new int[n][n]; }
    public int move(int r, int c, int p) {
        b[r][c] = p; boolean row = true, col = true, d = r == c, a = r + c == n - 1;
        for (int i = 0; i < n; i++) { row &= b[r][i] == p; col &= b[i][c] == p; if (d) d &= b[i][i] == p; if (a) a &= b[i][n - 1 - i] == p; }
        return row || col || d || a ? p : 0;
    }
}
```

- **Time Complexity:** O(n) per move
- **Space Complexity:** O(n^2)

### Approach 2: Counters per Line (Optimized)

Keep one signed counter per row, column and diagonal. This is the version implemented in the repository.

```java
public static class Counters {
    private final int n; private final int[] rows, cols; private int d, a;
    public Counters(int n) { this.n = n; rows = new int[n]; cols = new int[n]; }
    public int move(int r, int c, int p) {
        int x = p == 1 ? 1 : -1; rows[r] += x; cols[c] += x; if (r == c) d += x; if (r + c == n - 1) a += x;
        return Math.abs(rows[r]) == n || Math.abs(cols[c]) == n || Math.abs(d) == n || Math.abs(a) == n ? p : 0;
    }
}
```

- **Time Complexity:** O(1) per move
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Scan the Board After Every Move | O(n) per move | O(n^2) |
| 2 | Counters per Line (Optimized) | O(1) per move | O(n) |

## Source Code

### DesignTicTacToe.java

```java
public class DesignTicTacToe {

    public static void main(String[] args) {
        DesignTicTacToe game = new DesignTicTacToe(3);
        System.out.println("Input  : move(0, 0, 1)");
        System.out.println("Output : " + game.move(0, 0, 1));
        System.out.println();
        System.out.println("Input  : move(0, 2, 2), move(2, 2, 1), move(1, 1, 2), move(2, 0, 1), move(1, 0, 2), move(2, 1, 1)");
        System.out.println("Output : " + game.move(0, 2, 2) + ", " + game.move(2, 2, 1) + ", " + game.move(1, 1, 2) + ", " + game.move(2, 0, 1) + ", " + game.move(1, 0, 2) + ", " + game.move(2, 1, 1));
    }

    private final int n;
    private final int[] rows;
    private final int[] cols;
    private int diagonal;
    private int antiDiagonal;

    public DesignTicTacToe(int n) {
        this.n = n;
        rows = new int[n];
        cols = new int[n];
    }

    public int move(int row, int col, int player) {
        int delta = player == 1 ? 1 : -1;
        rows[row] += delta;
        cols[col] += delta;
        if (row == col) {
            diagonal += delta;
        }
        if (row + col == n - 1) {
            antiDiagonal += delta;
        }
        if (Math.abs(rows[row]) == n || Math.abs(cols[col]) == n || Math.abs(diagonal) == n || Math.abs(antiDiagonal) == n) {
            return player;
        }
        return 0;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a line is won when all `n` cells hold the same player's mark. Adding +1 for player 1 and -1 for player 2 to a counter per line means a line is won exactly when its counter reaches `+n` or `-n`.

**Sequence** on a 3x3 board:

| Move | Player | Counters touched | Winner? |
|------|--------|------------------|---------|
| (0,0) | 1 | row 0 = 1, col 0 = 1, diagonal = 1 | 0 |
| (0,2) | 2 | row 0 = 0, col 2 = -1, anti-diagonal = -1 | 0 |
| (2,2) | 1 | row 2 = 1, col 2 = 0, diagonal = 2 | 0 |
| (1,1) | 2 | diagonals change | 0 |
| (2,0) | 1 | row 2 = 2, col 0 = 2 | 0 |
| (1,0) | 2 | row 1 = 0, col 0 = 1 | 0 |
| (2,1) | 1 | row 2 = 3 | 1 |

**First output in `main`:** `0`; the last move returns `1`.
