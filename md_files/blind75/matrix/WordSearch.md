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
