# Number of Islands

Given an `m x n` grid of '1' (land) and '0' (water), return the number of islands. An island is formed by land cells connected horizontally or vertically and surrounded by water.

## Algorithm Strategy (DFS Flood Fill)

1. Scan every cell of (a copy of) the grid.
2. When an unvisited land cell is found, a new island starts: increment the counter.
3. Flood fill the whole island with a DFS: change every reachable land cell ('1') to water ('0') so it is not counted again.
4. After the scan the counter is the number of islands.

## Example walkthrough

- For the grid `11110 / 11010 / 11000 / 00000`: the first cell starts a flood fill that visits every connected 1, so the counter is 1 and nothing is left.
- For `11000 / 11000 / 00100 / 00011` three separate flood fills are started: `3`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every cell is visited a constant number of times.
- **Space Complexity:** `O(m * n)` — Recursion depth in the worst case (plus the copy of the grid).

## Source Code

### NumberOfIslands.java

```java
import java.util.Arrays;

public class NumberOfIslands {

    public static void main(String[] args) {
        char[][][] inputs = {
                {{'1', '1', '1', '1', '0'}, {'1', '1', '0', '1', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '0', '0', '0'}},
                {{'1', '1', '0', '0', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '1', '0', '0'}, {'0', '0', '0', '1', '1'}}
        };
        for (char[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + numIslands(grid));
            System.out.println();
        }
    }

    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        char[][] copy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = grid[i].clone();      // do not modify the caller's grid
        }

        int islands = 0;
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[0].length; j++) {
                if (copy[i][j] == '1') {
                    islands++;
                    sink(copy, i, j);
                }
            }
        }
        return islands;
    }

    private static void sink(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '0';
        sink(grid, i + 1, j);
        sink(grid, i - 1, j);
        sink(grid, i, j + 1);
        sink(grid, i, j - 1);
    }
}
```
