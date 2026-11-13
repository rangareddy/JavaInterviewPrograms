# Sudoku Solver

Fill the empty cells (`'.'`) of a 9 x 9 Sudoku board so that every row, every column and every one of the nine 3 x 3 boxes contains the digits 1 to 9 exactly once. The input is guaranteed to have exactly one solution.

## Algorithm Strategy (Backtracking with Bitmasks for Rows, Columns and Boxes)

1. Keep three arrays of 9 bitmasks: `rows[r]`, `cols[c]` and `boxes[b]`, where bit `d` is set when digit `d + 1` is already used. Fill them from the given digits.
2. Collect the empty cells and solve recursively. At each cell, the digits that are still possible are those whose bit is clear in `rows[r] | cols[c] | boxes[b]`, found in constant time.
3. Try each possible digit: set its bit in the three masks, recurse to the next empty cell, and if the recursion fails, clear the bits (backtrack) and try the next digit.
4. Pick the empty cell with the fewest candidates first (the most constrained cell). This prunes the search a lot and keeps hard puzzles fast; with a fixed left-to-right order the same code is much slower.

## Example walkthrough

- For the empty center cell (row 4, column 4) of the example board: its row holds 4, 8, 3, 1, its column holds 7, 9, 6, 2, 1, 8 and its box holds 6, 8, 3, 2.
- The used digits are `{1, 2, 3, 4, 6, 7, 8, 9}`, so only 5 is possible and it is placed without any branching. Cells with several candidates are branched on, and a dead end undoes the placements.

## Complexity Analysis

- **Time Complexity:** `O(9^m)` — `m` is the number of empty cells in the worst case; the most-constrained-first rule makes it fast in practice.
- **Space Complexity:** `O(m)` — Recursion depth and the three masks.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Backtracking with Validity Checks

Go through the cells in reading order and try the digits 1 to 9, checking the row, column and box by scanning each time. It is correct but scans 27 cells per attempt and can be very slow on hard puzzles.

```java
public static boolean solveSudokuPlain(char[][] board) {
    for (int r = 0; r < 9; r++) {
        for (int c = 0; c < 9; c++) {
            if (board[r][c] != '.') continue;
            for (char d = '1'; d <= '9'; d++) {
                if (isValid(board, r, c, d)) {
                    board[r][c] = d;
                    if (solveSudokuPlain(board)) return true;
                    board[r][c] = '.';
                }
            }
            return false;
        }
    }
    return true;
}

private static boolean isValid(char[][] board, int r, int c, char d) {
    for (int i = 0; i < 9; i++) {
        if (board[r][i] == d || board[i][c] == d) return false;
        if (board[r / 3 * 3 + i / 3][c / 3 * 3 + i % 3] == d) return false;
    }
    return true;
}
```

- **Time Complexity:** O(9^m) with a large constant
- **Space Complexity:** O(m)

### Approach 2: Bitmasks with Most-Constrained Cell First (Optimized)

Track used digits per row, column and box as bitmasks, and branch on the empty cell with the fewest candidates. This is the version implemented in the repository.

```java
public static void solveSudoku(char[][] board) {
    int[] rows = new int[9];
    int[] cols = new int[9];
    int[] boxes = new int[9];
    List<int[]> empty = new ArrayList<>();
    for (int r = 0; r < 9; r++) {
        for (int c = 0; c < 9; c++) {
            if (board[r][c] == '.') {
                empty.add(new int[]{r, c});
            } else {
                int bit = 1 << (board[r][c] - '1');
                rows[r] |= bit;
                cols[c] |= bit;
                boxes[r / 3 * 3 + c / 3] |= bit;
            }
        }
    }
    solve(board, rows, cols, boxes, empty, 0);
}

private static boolean solve(char[][] board, int[] rows, int[] cols, int[] boxes, List<int[]> empty, int filled) {
    if (filled == empty.size()) {
        return true;
    }
    int best = filled;
    int bestCount = 10;
    for (int i = filled; i < empty.size(); i++) {
        int[] cell = empty.get(i);
        int used = rows[cell[0]] | cols[cell[1]] | boxes[cell[0] / 3 * 3 + cell[1] / 3];
        int count = 9 - Integer.bitCount(used);
        if (count < bestCount) {
            bestCount = count;
            best = i;
            if (count <= 1) {
                break;
            }
        }
    }
    java.util.Collections.swap(empty, filled, best);
    int[] cell = empty.get(filled);
    int r = cell[0];
    int c = cell[1];
    int b = r / 3 * 3 + c / 3;
    int available = ~(rows[r] | cols[c] | boxes[b]) & 0x1FF;
    for (int d = 0; d < 9; d++) {
        int bit = 1 << d;
        if ((available & bit) == 0) {
            continue;
        }
        rows[r] |= bit;
        cols[c] |= bit;
        boxes[b] |= bit;
        board[r][c] = (char) ('1' + d);
        if (solve(board, rows, cols, boxes, empty, filled + 1)) {
            return true;
        }
        rows[r] &= ~bit;
        cols[c] &= ~bit;
        boxes[b] &= ~bit;
        board[r][c] = '.';
    }
    java.util.Collections.swap(empty, filled, best);
    return false;
}
```

- **Time Complexity:** O(9^m) worst case, fast in practice
- **Space Complexity:** O(m)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Backtracking with Validity Checks | O(9^m) with a large constant | O(m) |
| 2 | Bitmasks with Most-Constrained Cell First (Optimized) | O(9^m) worst case, fast in practice | O(m) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### SudokuSolver.java

```java
import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {

    public static void main(String[] args) {
        String[] puzzle = {
                "53..7....",
                "6..195...",
                ".98....6.",
                "8...6...3",
                "4..8.3..1",
                "7...2...6",
                ".6....28.",
                "...419..5",
                "....8..79"
        };
        char[][] board = new char[9][];
        for (int i = 0; i < 9; i++) {
            board[i] = puzzle[i].toCharArray();
        }
        System.out.println("Input  :");
        print(board);
        solveSudoku(board);
        System.out.println("Output :");
        print(board);
    }

    private static void print(char[][] board) {
        for (char[] row : board) {
            System.out.println("         " + new String(row));
        }
    }

    public static void solveSudoku(char[][] board) {
        int[] rows = new int[9];
        int[] cols = new int[9];
        int[] boxes = new int[9];
        List<int[]> empty = new ArrayList<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') {
                    empty.add(new int[]{r, c});
                } else {
                    int bit = 1 << (board[r][c] - '1');
                    rows[r] |= bit;
                    cols[c] |= bit;
                    boxes[r / 3 * 3 + c / 3] |= bit;
                }
            }
        }
        solve(board, rows, cols, boxes, empty, 0);
    }

    private static boolean solve(char[][] board, int[] rows, int[] cols, int[] boxes, List<int[]> empty, int filled) {
        if (filled == empty.size()) {
            return true;
        }
        int best = filled;
        int bestCount = 10;
        for (int i = filled; i < empty.size(); i++) {
            int[] cell = empty.get(i);
            int used = rows[cell[0]] | cols[cell[1]] | boxes[cell[0] / 3 * 3 + cell[1] / 3];
            int count = 9 - Integer.bitCount(used);
            if (count < bestCount) {
                bestCount = count;
                best = i;
                if (count <= 1) {
                    break;
                }
            }
        }
        java.util.Collections.swap(empty, filled, best);
        int[] cell = empty.get(filled);
        int r = cell[0];
        int c = cell[1];
        int b = r / 3 * 3 + c / 3;
        int available = ~(rows[r] | cols[c] | boxes[b]) & 0x1FF;
        for (int d = 0; d < 9; d++) {
            int bit = 1 << d;
            if ((available & bit) == 0) {
                continue;
            }
            rows[r] |= bit;
            cols[c] |= bit;
            boxes[b] |= bit;
            board[r][c] = (char) ('1' + d);
            if (solve(board, rows, cols, boxes, empty, filled + 1)) {
                return true;
            }
            rows[r] &= ~bit;
            cols[c] &= ~bit;
            boxes[b] &= ~bit;
            board[r][c] = '.';
        }
        java.util.Collections.swap(empty, filled, best);
        return false;
    }
}
```
