# N-Queens

Place `n` queens on an `n x n` chessboard so that no two queens attack each other (no two share a row, column or diagonal). Return all distinct solutions; in each solution `'Q'` marks a queen and `'.'` an empty square.

## Algorithm Strategy (Backtracking Row by Row with Column and Diagonal Sets)

1. Place exactly one queen per row, going from the top row to the bottom. Row `r` tries every column `c`.
2. A square is attacked if its column `c`, its diagonal `r - c` or its anti-diagonal `r + c` already holds a queen; three boolean arrays make this check `O(1)`.
3. If the square is free, place the queen (mark column and both diagonals), recurse on row `r + 1`, and unmark everything afterwards (backtrack).
4. When all `n` rows have a queen, convert the recorded columns to the board strings and add the solution.

## Example walkthrough

- For `n = 4` there are exactly 2 solutions: `.Q.. / ...Q / Q... / ..Q.` and `..Q. / Q... / ...Q / .Q..`.
- For `n = 1` the single queen fits; for `n = 2` and `n = 3` there is no solution.

## Complexity Analysis

- **Time Complexity:** `O(n!)` — Upper bound on the number of explored placements; pruning removes most of them.
- **Space Complexity:** `O(n)` — The three marker arrays and the recursion (excluding the output).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force over Column Permutations

A solution has one queen per row and per column, so it is a permutation of the columns. Generate all `n!` permutations and keep those in which no two queens share a diagonal.

```java
public static List<List<String>> solveNQueensPermutations(int n) {
    List<List<String>> result = new ArrayList<>();
    permute(new int[n], new boolean[n], 0, n, result);
    return result;
}

private static void permute(int[] columns, boolean[] used, int row, int n, List<List<String>> result) {
    if (row == n) {
        if (noSharedDiagonal(columns)) result.add(board(columns, n));
        return;
    }
    for (int col = 0; col < n; col++) {
        if (used[col]) continue;
        used[col] = true;
        columns[row] = col;
        permute(columns, used, row + 1, n, result);
        used[col] = false;
    }
}

private static boolean noSharedDiagonal(int[] columns) {
    for (int i = 0; i < columns.length; i++)
        for (int j = i + 1; j < columns.length; j++)
            if (Math.abs(columns[i] - columns[j]) == j - i) return false;
    return true;
}

private static List<String> board(int[] columns, int n) {
    List<String> board = new ArrayList<>();
    for (int row = 0; row < n; row++) {
        char[] line = new char[n];
        Arrays.fill(line, '.');
        line[columns[row]] = 'Q';
        board.add(new String(line));
    }
    return board;
}
```

- **Time Complexity:** O(n! * n^2)
- **Space Complexity:** O(n)

### Approach 2: Backtracking Checking the Placed Queens

Place queens row by row and, for each candidate square, compare it with every queen already placed (same column, or the row distance equals the column distance). No extra marker arrays, but each check costs `O(n)`.

```java
public static List<List<String>> solveNQueensCheck(int n) {
    List<List<String>> result = new ArrayList<>();
    placeRow(new int[n], 0, n, result);
    return result;
}

private static void placeRow(int[] columns, int row, int n, List<List<String>> result) {
    if (row == n) { result.add(board(columns, n)); return; }
    for (int col = 0; col < n; col++) {
        boolean safe = true;
        for (int previous = 0; previous < row && safe; previous++) {
            if (columns[previous] == col || Math.abs(columns[previous] - col) == row - previous) safe = false;
        }
        if (safe) { columns[row] = col; placeRow(columns, row + 1, n, result); }
    }
}
```

- **Time Complexity:** O(n!)
- **Space Complexity:** O(n)

### Approach 3: Bit-Mask Backtracking

Keep the attacked columns and both diagonals as bits of three integers that are shifted from row to row. The free squares of a row are found with a single bit expression (`~(cols | d1 | d2)`), which makes this the fastest way to count or list solutions.

```java
public static List<List<String>> solveNQueensBits(int n) {
    List<List<String>> result = new ArrayList<>();
    bits(n, 0, 0, 0, 0, new int[n], result);
    return result;
}

private static void bits(int n, int row, int columns, int diagonals, int antiDiagonals, int[] queenColumn, List<List<String>> result) {
    if (row == n) { result.add(board(queenColumn, n)); return; }      // board(...) as in the first approach
    int free = ~(columns | diagonals | antiDiagonals) & ((1 << n) - 1);
    while (free != 0) {
        int bit = free & -free;                              // lowest free square
        free ^= bit;
        queenColumn[row] = Integer.numberOfTrailingZeros(bit);
        bits(n, row + 1, columns | bit, (diagonals | bit) << 1, (antiDiagonals | bit) >> 1, queenColumn, result);
    }
}
```

- **Time Complexity:** O(n!) (much faster constants)
- **Space Complexity:** O(n)

### Approach 4: Backtracking with Column and Diagonal Marker Arrays (Optimized)

Three boolean arrays tell in `O(1)` whether a column, a diagonal or an anti-diagonal is already attacked. This is the version implemented in the repository.

```java
public static List<List<String>> solveNQueens(int n) {
    List<List<String>> result = new ArrayList<>();
    place(0, n, new int[n], new boolean[n], new boolean[2 * n - 1], new boolean[2 * n - 1], result);
    return result;
}

private static void place(int row, int n, int[] queenColumn, boolean[] columns, boolean[] diagonals, boolean[] antiDiagonals, List<List<String>> result) {
    if (row == n) { result.add(board(queenColumn, n)); return; }
    for (int col = 0; col < n; col++) {
        int diagonal = row - col + n - 1, antiDiagonal = row + col;
        if (columns[col] || diagonals[diagonal] || antiDiagonals[antiDiagonal]) continue;
        columns[col] = diagonals[diagonal] = antiDiagonals[antiDiagonal] = true;
        queenColumn[row] = col;
        place(row + 1, n, queenColumn, columns, diagonals, antiDiagonals, result);
        columns[col] = diagonals[diagonal] = antiDiagonals[antiDiagonal] = false;
    }
}
```

- **Time Complexity:** O(n!)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force over Column Permutations | O(n! * n^2) | O(n) |
| 2 | Backtracking Checking the Placed Queens | O(n!) | O(n) |
| 3 | Bit-Mask Backtracking | O(n!) (much faster constants) | O(n) |
| 4 | Backtracking with Column and Diagonal Marker Arrays (Optimized) | O(n!) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### NQueens.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueens {

    public static void main(String[] args) {
        int[] inputs = {4, 1, 2, 6};
        for (int n : inputs) {
            List<List<String>> solutions = solveNQueens(n);
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + solutions.size() + " solution(s)" + (n <= 4 ? " " + solutions : ""));
            System.out.println();
        }
    }

    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        place(0, n, new int[n], new boolean[n], new boolean[2 * n - 1], new boolean[2 * n - 1], result);
        return result;
    }

    // queenColumn[r] = column of the queen in row r
    private static void place(int row, int n, int[] queenColumn, boolean[] columns, boolean[] diagonals, boolean[] antiDiagonals, List<List<String>> result) {
        if (row == n) {
            result.add(toBoard(queenColumn, n));
            return;
        }
        for (int col = 0; col < n; col++) {
            int diagonal = row - col + n - 1;
            int antiDiagonal = row + col;
            if (columns[col] || diagonals[diagonal] || antiDiagonals[antiDiagonal]) {
                continue;
            }
            columns[col] = diagonals[diagonal] = antiDiagonals[antiDiagonal] = true;
            queenColumn[row] = col;
            place(row + 1, n, queenColumn, columns, diagonals, antiDiagonals, result);
            columns[col] = diagonals[diagonal] = antiDiagonals[antiDiagonal] = false;
        }
    }

    private static List<String> toBoard(int[] queenColumn, int n) {
        List<String> board = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            char[] line = new char[n];
            Arrays.fill(line, '.');
            line[queenColumn[row]] = 'Q';
            board.add(new String(line));
        }
        return board;
    }
}
```
