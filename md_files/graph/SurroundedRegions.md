# Surrounded Regions

Given an `m x n` board of 'X' and 'O', capture all regions that are 4-directionally surrounded by 'X': flip every 'O' of such a region to 'X'. An 'O' region touching the border of the board is not surrounded and stays unchanged.

## Algorithm Strategy (Mark the Safe Regions from the Border)

1. An 'O' survives exactly when it is connected (through 'O' cells) to an 'O' on the border of the board.
2. Run a DFS from every 'O' on the four borders and temporarily mark all reached cells with 'T' (safe).
3. Scan the board: every remaining 'O' is surrounded, so flip it to 'X'.
4. Finally turn the temporary 'T' marks back into 'O'.

## Example walkthrough

- For the 4 x 4 example the 'O' at (3,1) is on the border, so it and everything connected to it stay; the group of three 'O' in the middle is not connected to the border.
- Result: the middle group becomes 'X'; only (3,1) remains an 'O'.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Each cell is visited a constant number of times.
- **Space Complexity:** `O(m * n)` — Recursion depth in the worst case.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS from the Border Cells (Optimized)

Mark every `O` connected to a border `O` as safe (`T`), flip the remaining `O`s to `X` and restore the safe cells. This is the version implemented in the repository.

```java
public static void solve(char[][] board) {
    int rows = board.length, cols = board[0].length;
    for (int i = 0; i < rows; i++) { markSafe(board, i, 0); markSafe(board, i, cols - 1); }
    for (int j = 0; j < cols; j++) { markSafe(board, 0, j); markSafe(board, rows - 1, j); }
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++) {
            if (board[i][j] == 'O') board[i][j] = 'X'; else if (board[i][j] == 'T') board[i][j] = 'O';
        }
}

private static void markSafe(char[][] board, int i, int j) {
    if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != 'O') return;
    board[i][j] = 'T';
    markSafe(board, i + 1, j); markSafe(board, i - 1, j); markSafe(board, i, j + 1); markSafe(board, i, j - 1);
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 2: BFS from the Border Cells

The same marking with a queue instead of recursion, so a large connected region cannot overflow the call stack.

```java
public static void solveBfs(char[][] board) {
    int rows = board.length, cols = board[0].length;
    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++)
            if ((i == 0 || j == 0 || i == rows - 1 || j == cols - 1) && board[i][j] == 'O') { board[i][j] = 'T'; queue.add(new int[]{i, j}); }
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        for (int[] d : directions) {
            int x = cell[0] + d[0], y = cell[1] + d[1];
            if (x >= 0 && y >= 0 && x < rows && y < cols && board[x][y] == 'O') { board[x][y] = 'T'; queue.add(new int[]{x, y}); }
        }
    }
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++) {
            if (board[i][j] == 'O') board[i][j] = 'X'; else if (board[i][j] == 'T') board[i][j] = 'O';
        }
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 3: Union-Find with a Border Node

Add one extra node that represents "the border". Union every border `O` with it and every `O` with its `O` neighbours. An `O` is safe exactly when its set contains the border node.

```java
public static void solveUnionFind(char[][] board) {
    int rows = board.length, cols = board[0].length, border = rows * cols;
    int[] parent = new int[border + 1];
    for (int i = 0; i <= border; i++) parent[i] = i;
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (board[i][j] != 'O') continue;
            int id = i * cols + j;
            if (i == 0 || j == 0 || i == rows - 1 || j == cols - 1) union(parent, id, border);
            if (i + 1 < rows && board[i + 1][j] == 'O') union(parent, id, id + cols);
            if (j + 1 < cols && board[i][j + 1] == 'O') union(parent, id, id + 1);
        }
    }
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++)
            if (board[i][j] == 'O' && find(parent, i * cols + j) != find(parent, border)) board[i][j] = 'X';
}

private static void union(int[] parent, int a, int b) { parent[find(parent, a)] = find(parent, b); }

private static int find(int[] parent, int x) {
    while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
    return x;
}
```

- **Time Complexity:** O(m * n * alpha)
- **Space Complexity:** O(m * n)

### Approach 4: Collect Every Region, Then Decide

Flood each unvisited `O` region, remember its cells and whether any of them lies on the border. Flip the region only if it never touched the border. More code, but it handles each region as a unit.

```java
public static void solveRegions(char[][] board) {
    int rows = board.length, cols = board[0].length;
    boolean[][] visited = new boolean[rows][cols];
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (board[i][j] != 'O' || visited[i][j]) continue;
            List<int[]> region = new ArrayList<>();
            boolean touchesBorder = false;
            Deque<int[]> stack = new ArrayDeque<>();
            stack.push(new int[]{i, j});
            visited[i][j] = true;
            while (!stack.isEmpty()) {
                int[] cell = stack.pop();
                region.add(cell);
                if (cell[0] == 0 || cell[1] == 0 || cell[0] == rows - 1 || cell[1] == cols - 1) touchesBorder = true;
                for (int[] d : directions) {
                    int x = cell[0] + d[0], y = cell[1] + d[1];
                    if (x >= 0 && y >= 0 && x < rows && y < cols && board[x][y] == 'O' && !visited[x][y]) { visited[x][y] = true; stack.push(new int[]{x, y}); }
                }
            }
            if (!touchesBorder) for (int[] cell : region) board[cell[0]][cell[1]] = 'X';
        }
    }
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS from the Border Cells (Optimized) | O(m * n) | O(m * n) |
| 2 | BFS from the Border Cells | O(m * n) | O(m * n) |
| 3 | Union-Find with a Border Node | O(m * n * alpha) | O(m * n) |
| 4 | Collect Every Region, Then Decide | O(m * n) | O(m * n) |

### Optimized Solution

The optimized solution is Approach 1 above, which is also the version implemented in this repository.

## Source Code

### SurroundedRegions.java

```java
import java.util.Arrays;

public class SurroundedRegions {

    public static void main(String[] args) {
        String[][] inputs = {
                {"XXXX", "XOOX", "XXOX", "XOXX"},
                {"X"},
                {"OOO", "OXO", "OOO"},
                {"XXX", "XOX", "XXX"}
        };
        for (String[] rows : inputs) {
            char[][] board = new char[rows.length][];
            for (int i = 0; i < rows.length; i++) {
                board[i] = rows[i].toCharArray();
            }
            System.out.println("Input  : board = " + Arrays.toString(rows));
            solve(board);
            String[] result = new String[board.length];
            for (int i = 0; i < board.length; i++) {
                result[i] = new String(board[i]);
            }
            System.out.println("Output : " + Arrays.toString(result));
            System.out.println();
        }
    }

    public static void solve(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }

        int rows = board.length;
        int cols = board[0].length;
        for (int i = 0; i < rows; i++) {
            markSafe(board, i, 0);
            markSafe(board, i, cols - 1);
        }
        for (int j = 0; j < cols; j++) {
            markSafe(board, 0, j);
            markSafe(board, rows - 1, j);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';              // surrounded
                } else if (board[i][j] == 'T') {
                    board[i][j] = 'O';              // connected to the border: restore
                }
            }
        }
    }

    private static void markSafe(char[][] board, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != 'O') {
            return;
        }
        board[i][j] = 'T';
        markSafe(board, i + 1, j);
        markSafe(board, i - 1, j);
        markSafe(board, i, j + 1);
        markSafe(board, i, j - 1);
    }
}
```
