# Word Search

Given an `m x n` grid of characters and a `word`, return `true` if the word can be built from sequentially adjacent (horizontal or vertical) cells, using each cell at most once.

## Algorithm Strategy (Backtracking (DFS))

1. Try every cell as the starting point of the word.
2. From a cell, if its character does not match `word.charAt(index)` (or it is outside the grid), stop. If `index` is the last character and it matches, the word is found.
3. Mark the current cell as used (temporarily replace it with `'#'`), then try the four neighbours with `index + 1`.
4. Restore the cell before returning, so other paths can use it (backtracking).

## Example walkthrough

- For board `ABCE / SFCS / ADEE` and word `"ABCCED"`: the path A(0,0), B(0,1), C(0,2), C(1,2), E(2,2), D(2,1) matches every letter.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(m * n * 3^L)` — Each of the `m * n` start cells explores up to 3 directions per letter (`L` = word length).
- **Space Complexity:** `O(L)` — Recursion depth equals the word length.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS with a Visited Matrix

Backtracking DFS from every cell that tracks the used cells in a separate boolean matrix.

```java
public static boolean existVisited(char[][] board, String word) {
    boolean[][] visited = new boolean[board.length][board[0].length];
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (dfsVisited(board, word, i, j, 0, visited)) return true;
        }
    }
    return false;
}

private static boolean dfsVisited(char[][] board, String word, int i, int j, int index, boolean[][] visited) {
    if (index == word.length()) return true;
    if (i < 0 || j < 0 || i >= board.length || j >= board[0].length
            || visited[i][j] || board[i][j] != word.charAt(index)) return false;
    visited[i][j] = true;
    boolean found = dfsVisited(board, word, i + 1, j, index + 1, visited)
            || dfsVisited(board, word, i - 1, j, index + 1, visited)
            || dfsVisited(board, word, i, j + 1, index + 1, visited)
            || dfsVisited(board, word, i, j - 1, index + 1, visited);
    visited[i][j] = false;
    return found;
}
```

- **Time Complexity:** O(m * n * 3^L)
- **Space Complexity:** O(m * n + L)

### Approach 2: In-Place DFS with Pruning

Before searching, reject the word if the board does not have enough of each letter, and search the reversed word when its first letter is more frequent than its last one (fewer starting points, earlier failures).

```java
public static boolean existPruned(char[][] board, String word) {
    int[] counts = new int[128];
    for (char[] row : board) for (char c : row) counts[c]++;
    int[] needed = new int[128];
    for (char c : word.toCharArray()) {
        if (++needed[c] > counts[c]) return false;
    }
    if (counts[word.charAt(0)] > counts[word.charAt(word.length() - 1)]) {
        word = new StringBuilder(word).reverse().toString();
    }
    return exist(board, word);
}
```

- **Time Complexity:** O(m * n * 3^L) worst case, much faster in practice
- **Space Complexity:** O(L)

### Approach 3: Bit-Mask Visited Set (Small Boards)

If the board has at most 64 cells, the set of used cells fits into one `long`; marking and unmarking a cell is a single bit operation and no matrix needs to be allocated or modified.

```java
public static boolean existMask(char[][] board, String word) {
    if (board.length * board[0].length > 64) throw new IllegalArgumentException("board too large for a long mask");
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (searchMask(board, word, i, j, 0, 0L)) return true;
        }
    }
    return false;
}

private static boolean searchMask(char[][] board, String word, int i, int j, int index, long used) {
    int cols = board[0].length;
    if (i < 0 || j < 0 || i >= board.length || j >= cols || board[i][j] != word.charAt(index)) return false;
    long bit = 1L << (i * cols + j);
    if ((used & bit) != 0) return false;
    if (index == word.length() - 1) return true;
    used |= bit;
    return searchMask(board, word, i + 1, j, index + 1, used) || searchMask(board, word, i - 1, j, index + 1, used)
            || searchMask(board, word, i, j + 1, index + 1, used) || searchMask(board, word, i, j - 1, index + 1, used);
}
```

- **Time Complexity:** O(m * n * 3^L)
- **Space Complexity:** O(L)

### Approach 4: Iterative DFS with an Explicit Stack

Replace the recursion by a stack of frames `(row, col, matched letters, next direction to try)`. When all four directions of a frame are exhausted the cell is released and the frame is popped (the iterative form of backtracking).

```java
public static boolean existIterative(char[][] board, String word) {
    int rows = board.length, cols = board[0].length;
    int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (board[i][j] != word.charAt(0)) continue;
            boolean[][] visited = new boolean[rows][cols];
            Deque<int[]> stack = new ArrayDeque<>();
            stack.push(new int[]{i, j, 0, 0});
            visited[i][j] = true;
            while (!stack.isEmpty()) {
                int[] frame = stack.peek();
                if (frame[2] == word.length() - 1) return true;
                if (frame[3] == 4) {                              // all directions tried: backtrack
                    visited[frame[0]][frame[1]] = false;
                    stack.pop();
                    continue;
                }
                int d = frame[3]++;
                int x = frame[0] + dr[d], y = frame[1] + dc[d];
                if (x >= 0 && y >= 0 && x < rows && y < cols && !visited[x][y] && board[x][y] == word.charAt(frame[2] + 1)) {
                    visited[x][y] = true;
                    stack.push(new int[]{x, y, frame[2] + 1, 0});
                }
            }
        }
    }
    return false;
}
```

- **Time Complexity:** O(m * n * 3^L)
- **Space Complexity:** O(m * n + L)

### Approach 5: Start Only from the Cells of the First Letter

Collect the positions of the cells that contain the first letter of the word and run the in-place backtracking only from those cells, instead of probing every cell of the board.

```java
public static boolean existStarts(char[][] board, String word) {
    List<int[]> starts = new ArrayList<>();
    for (int i = 0; i < board.length; i++)
        for (int j = 0; j < board[0].length; j++) if (board[i][j] == word.charAt(0)) starts.add(new int[]{i, j});
    for (int[] start : starts) {
        if (backtrack(board, word, start[0], start[1], 0)) return true;
    }
    return false;
}

private static boolean backtrack(char[][] board, String word, int i, int j, int index) {
    if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != word.charAt(index)) return false;
    if (index == word.length() - 1) return true;
    char original = board[i][j];
    board[i][j] = '#';
    boolean found = backtrack(board, word, i + 1, j, index + 1) || backtrack(board, word, i - 1, j, index + 1)
            || backtrack(board, word, i, j + 1, index + 1) || backtrack(board, word, i, j - 1, index + 1);
    board[i][j] = original;
    return found;
}
```

- **Time Complexity:** O(m * n + k * 3^L) where k is the number of start cells
- **Space Complexity:** O(k + L)

### Approach 6: DFS with the Current Path in a HashSet

Keep the cells of the current path in a `HashSet` of encoded positions instead of marking the board or using a boolean matrix. The board stays untouched and the set only grows to the path length.

```java
public static boolean existPathSet(char[][] board, String word) {
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (extend(board, word, i, j, 0, new HashSet<>())) return true;
        }
    }
    return false;
}

private static boolean extend(char[][] board, String word, int i, int j, int index, Set<Integer> path) {
    if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != word.charAt(index)) return false;
    int cell = i * board[0].length + j;
    if (path.contains(cell)) return false;
    if (index == word.length() - 1) return true;
    path.add(cell);
    boolean found = extend(board, word, i + 1, j, index + 1, path) || extend(board, word, i - 1, j, index + 1, path)
            || extend(board, word, i, j + 1, index + 1, path) || extend(board, word, i, j - 1, index + 1, path);
    path.remove(cell);
    return found;
}
```

- **Time Complexity:** O(m * n * 3^L)
- **Space Complexity:** O(L)

### Approach 7: In-Place DFS (Optimized)

Mark the current cell by temporarily overwriting its letter, so no extra visited matrix is required. This is the version implemented in the repository.

```java
public static boolean exist(char[][] board, String word) {
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (dfs(board, word, i, j, 0)) return true;
        }
    }
    return false;
}

private static boolean dfs(char[][] board, String word, int i, int j, int index) {
    if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != word.charAt(index)) return false;
    if (index == word.length() - 1) return true;
    char original = board[i][j];
    board[i][j] = '#';
    boolean found = dfs(board, word, i + 1, j, index + 1) || dfs(board, word, i - 1, j, index + 1)
            || dfs(board, word, i, j + 1, index + 1) || dfs(board, word, i, j - 1, index + 1);
    board[i][j] = original;
    return found;
}
```

- **Time Complexity:** O(m * n * 3^L)
- **Space Complexity:** O(L)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS with a Visited Matrix | O(m * n * 3^L) | O(m * n + L) |
| 2 | In-Place DFS with Pruning | O(m * n * 3^L) worst case, much faster in practice | O(L) |
| 3 | Bit-Mask Visited Set (Small Boards) | O(m * n * 3^L) | O(L) |
| 4 | Iterative DFS with an Explicit Stack | O(m * n * 3^L) | O(m * n + L) |
| 5 | Start Only from the Cells of the First Letter | O(m * n + k * 3^L) where k is the number of start cells | O(k + L) |
| 6 | DFS with the Current Path in a HashSet | O(m * n * 3^L) | O(L) |
| 7 | In-Place DFS (Optimized) | O(m * n * 3^L) | O(L) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### WordSearch.java

```java
import java.util.Arrays;

public class WordSearch {

    public static void main(String[] args) {
        char[][] board = {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        String[] words = {"ABCCED", "SEE", "ABCB"};
        for (String word : words) {
            System.out.println("Input  : board = " + Arrays.deepToString(board) + ", word = \"" + word + "\"");
            System.out.println("Output : " + exist(board, word));
            System.out.println();
        }
    }

    public static boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || word == null || word.isEmpty()) {
            return false;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfs(char[][] board, String word, int i, int j, int index) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != word.charAt(index)) {
            return false;
        }
        if (index == word.length() - 1) {
            return true;
        }

        char original = board[i][j];
        board[i][j] = '#';          // mark as used
        boolean found = dfs(board, word, i + 1, j, index + 1)
                || dfs(board, word, i - 1, j, index + 1)
                || dfs(board, word, i, j + 1, index + 1)
                || dfs(board, word, i, j - 1, index + 1);
        board[i][j] = original;     // backtrack
        return found;
    }
}
```
