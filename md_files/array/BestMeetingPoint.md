# Best Meeting Point

Given an `m x n` binary grid where each `1` marks the home of a friend, return the minimal total travel distance. The total travel distance is the sum of the Manhattan distances between the friends' homes and the meeting point, which can be any cell.

## Algorithm Strategy (Median of the Row and Column Coordinates)

1. Manhattan distance splits into a row part and a column part, so the best meeting row and column can be chosen independently.
2. Collect the row index of every friend (already sorted by scanning rows in order) and the column indexes (scan columns in order to keep them sorted).
3. The best row is the median of the row list, the best column is the median of the column list.
4. Sum the distances of every coordinate to its median.

## Example walkthrough

- For the 3x5 sample, the rows are `[0, 0, 2]` with median 0 and the columns `[0, 2, 4]` with median 2.
- Row cost 2, column cost 4: result `6`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Two scans of the grid.
- **Space Complexity:** `O(k)` — The coordinates of the k friends.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Cell as the Meeting Point

Compute the total distance for every grid cell.

```java
public static int brute(int[][] g) { int best = Integer.MAX_VALUE; boolean any = false; for (int[] r : g) for (int v : r) if (v == 1) any = true; if (!any) return 0;
    for (int a = 0; a < g.length; a++) for (int b = 0; b < g[0].length; b++) { int s = 0; for (int i = 0; i < g.length; i++) for (int j = 0; j < g[0].length; j++) if (g[i][j] == 1) s += Math.abs(i - a) + Math.abs(j - b); best = Math.min(best, s); } return best; }
```

- **Time Complexity:** O((mn)^2)
- **Space Complexity:** O(1)

### Approach 2: Median of Coordinates (Optimized)

Sum the distances to the median of rows and of columns. This is the version implemented in the repository.

```java
public static int median(int[][] g) {
    java.util.List<Integer> rs = new java.util.ArrayList<>(), cs = new java.util.ArrayList<>(); for (int i = 0; i < g.length; i++) for (int j = 0; j < g[0].length; j++) if (g[i][j] == 1) rs.add(i); for (int j = 0; j < g[0].length; j++) for (int[] row : g) if (row[j] == 1) cs.add(j);
    return span(rs) + span(cs);
}
private static int span(java.util.List<Integer> s) { int t = 0, i = 0, j = s.size() - 1; while (i < j) t += s.get(j--) - s.get(i++); return t; }
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(k)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Cell as the Meeting Point | O((mn)^2) | O(1) |
| 2 | Median of Coordinates (Optimized) | O(m * n) | O(k) |

## Source Code

### BestMeetingPoint.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BestMeetingPoint {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 0, 0, 0, 1}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}}, {{1, 1}}, {{1, 0, 1}}, {}};
        for (int[][] grid : inputs) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + minTotalDistance(grid));
            System.out.println();
        }
    }

    public static int minTotalDistance(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    rows.add(i);
                }
            }
        }
        for (int j = 0; j < grid[0].length; j++) {
            for (int[] row : grid) {
                if (row[j] == 1) {
                    cols.add(j);
                }
            }
        }
        return distanceToMedian(rows) + distanceToMedian(cols);
    }

    private static int distanceToMedian(List<Integer> sorted) {
        int total = 0;
        int i = 0;
        int j = sorted.size() - 1;
        while (i < j) {
            total += sorted.get(j--) - sorted.get(i++);
        }
        return total;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: Manhattan distance is the sum of a row distance and a column distance, so the two can be minimised separately. For one dimension, the point that minimises the sum of absolute distances is the median. The total distance to the median equals the sum of the gaps between the outermost remaining pair, which is why pairing the smallest with the largest coordinate works.

**Input:** `[[1,0,0,0,1],[0,0,0,0,0],[0,0,1,0,0]]`.

| Dimension | Sorted coordinates | Pairing (outer, inner) | Distance |
|-----------|--------------------|------------------------|----------|
| rows | 0, 0, 2 | 2 - 0 | 2 |
| columns | 0, 2, 4 | 4 - 0 | 4 |

**Output:** `6`.
