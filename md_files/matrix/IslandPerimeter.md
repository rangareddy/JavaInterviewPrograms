# Island Perimeter

You are given a `row x col` grid where `grid[i][j] = 1` is land and `0` is water. The grid contains exactly one island (connected land cells) and no lakes. Return the perimeter of the island.

## Algorithm Strategy (Four Edges per Cell Minus Shared Edges)

1. Each land cell contributes four edges.
2. Every pair of adjacent land cells hides two edges (one from each cell).
3. For each land cell, count its land neighbour above and to the left only, so each pair is counted once.
4. The perimeter is `4 * land - 2 * adjacentPairs`.

## Example walkthrough

- For the 4x4 sample there are 7 land cells and 6 adjacent pairs.
- Result: `4 * 7 - 2 * 6 = 16`.

## Complexity Analysis

- **Time Complexity:** `O(rows * cols)` — Each cell once.
- **Space Complexity:** `O(1)` — Two counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Check Four Neighbours of Every Land Cell

Add one for every side of a land cell that touches water or the border.

```java
public static int sides(int[][] g) { int p = 0; int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; for (int i = 0; i < g.length; i++) for (int j = 0; j < g[i].length; j++) if (g[i][j] == 1) for (int[] x : d) { int a = i + x[0], b = j + x[1]; if (a < 0 || b < 0 || a >= g.length || b >= g[0].length || g[a][b] == 0) p++; } return p; }
```

- **Time Complexity:** O(rows * cols)
- **Space Complexity:** O(1)

### Approach 2: Land Cells and Adjacent Pairs (Optimized)

Four edges per cell minus two per adjacent pair. This is the version implemented in the repository.

```java
public static int pairs(int[][] g) { int land = 0, pr = 0; for (int i = 0; i < g.length; i++) for (int j = 0; j < g[i].length; j++) if (g[i][j] == 1) { land++; if (i > 0 && g[i - 1][j] == 1) pr++; if (j > 0 && g[i][j - 1] == 1) pr++; } return 4 * land - 2 * pr; }
```

- **Time Complexity:** O(rows * cols)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Check Four Neighbours of Every Land Cell | O(rows * cols) | O(1) |
| 2 | Land Cells and Adjacent Pairs (Optimized) | O(rows * cols) | O(1) |

## Source Code

### IslandPerimeter.java

```java
import java.util.Arrays;

public class IslandPerimeter {

    public static void main(String[] args) {
        int[][][] inputs = {{{0, 1, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 0}, {1, 1, 0, 0}}, {{1}}, {{1, 0}}, {}};
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + islandPerimeter(grid));
            System.out.println();
        }
    }

    public static int islandPerimeter(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int land = 0;
        int pairs = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    land++;
                    if (i > 0 && grid[i - 1][j] == 1) {
                        pairs++;
                    }
                    if (j > 0 && grid[i][j - 1] == 1) {
                        pairs++;
                    }
                }
            }
        }
        return 4 * land - 2 * pairs;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a lone land cell has a perimeter of 4. Gluing two land cells together hides one edge of each, so the total drops by 2 for every pair of neighbours. Counting only the neighbour above and the neighbour to the left visits each pair exactly once.

**Input:** `[[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0]]`.

| Quantity | Value |
|----------|-------|
| land cells | 7 |
| adjacent pairs | 6 |
| perimeter | 4 * 7 - 2 * 6 |

**Output:** `16`.
