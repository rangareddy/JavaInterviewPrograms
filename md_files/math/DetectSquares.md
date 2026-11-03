# Detect Squares

Design a data structure that accepts a stream of 2D points (duplicates allowed) and, for a query point, counts the number of ways to choose three stored points that form, together with the query, an axis-aligned square with positive area. Implement `add(int[] point)` and `count(int[] point)`.

## Algorithm Strategy (Hash Map of Point Counts)

1. Store the number of times each point was added in a map keyed by its coordinates.
2. `add` increments the count of the point.
3. For `count(qx, qy)`, every distinct stored point `(x, y)` is a candidate for the corner diagonally opposite the query. It qualifies when `|x - qx| == |y - qy|` and the distance is positive.
4. The other two corners are then `(qx, y)` and `(x, qy)`. The number of squares through this diagonal is the product of the counts of the three points (duplicates multiply).
5. Sum the products over all candidate diagonal points.

## Example walkthrough

- After adding `(3,10)`, `(11,2)` and `(3,2)`, the query `(11,10)` finds one square: the count is `1`. Query `(14,8)` finds `0`.
- After adding `(11,2)` again, the query `(11,10)` returns `2`.

## Complexity Analysis

- **Time Complexity:** `add O(1), count O(p)` — `p` is the number of distinct stored points.
- **Space Complexity:** `O(p)` — The map of point counts.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: List of All Points, Triple Loop

Store every added point in a list and test all triples of stored points against the query.

```java
static class ListSquares {
    private final List<int[]> points = new ArrayList<>();

    void add(int[] p) { points.add(p.clone()); }

    int count(int[] q) {
        int total = 0;
        int n = points.size();
        for (int a = 0; a < n; a++)
            for (int b = 0; b < n; b++)
                for (int c = 0; c < n; c++) {
                    if (a == b || b == c || a == c) continue;
                    if (isSquare(q, points.get(a), points.get(b), points.get(c))) total++;     // roles are fixed, so each square is counted once
                }
        return total;
    }

    private static boolean isSquare(int[] q, int[] a, int[] b, int[] c) {
        // a shares a row with q, c shares a column with q, b is the diagonal corner
        return a[1] == q[1] && c[0] == q[0] && b[0] == a[0] && b[1] == c[1]
                && Math.abs(a[0] - q[0]) == Math.abs(c[1] - q[1]) && a[0] != q[0];
    }
}
```

- **Time Complexity:** O(p^3) per count
- **Space Complexity:** O(p)

### Approach 2: Map by Row

Group the points by `y`; for each stored point on the query row, test the two possible squares.

```java
static class RowSquares {
    private final Map<Integer, Map<Integer, Integer>> rows = new HashMap<>();

    void add(int[] p) { rows.computeIfAbsent(p[1], k -> new HashMap<>()).merge(p[0], 1, Integer::sum); }

    int count(int[] q) {
        int total = 0;
        Map<Integer, Integer> sameRow = rows.get(q[1]);
        if (sameRow == null) return 0;
        for (Map.Entry<Integer, Integer> e : sameRow.entrySet()) {
            int x = e.getKey();
            if (x == q[0]) continue;
            int side = Math.abs(x - q[0]);
            for (int y : new int[]{q[1] + side, q[1] - side}) {
                Map<Integer, Integer> row = rows.get(y);
                if (row != null) total += e.getValue() * row.getOrDefault(x, 0) * row.getOrDefault(q[0], 0);
            }
        }
        return total;
    }
}
```

- **Time Complexity:** O(r) per count (r = points on the query row)
- **Space Complexity:** O(p)

### Approach 3: Hash Map of Counts, Scan Diagonals (Optimized)

Keep a map of point counts and scan the distinct points as diagonal candidates. This is the version implemented in the repository.

```java
static class MapSquares {
    private final Map<Long, Integer> counts = new HashMap<>();
    private final List<int[]> distinct = new ArrayList<>();

    void add(int[] p) {
        long key = key(p[0], p[1]);
        if (!counts.containsKey(key)) distinct.add(p.clone());
        counts.merge(key, 1, Integer::sum);
    }

    int count(int[] q) {
        int total = 0;
        for (int[] d : distinct) {
            int dx = Math.abs(d[0] - q[0]), dy = Math.abs(d[1] - q[1]);
            if (dx == 0 || dx != dy) continue;
            total += counts.get(key(d[0], d[1])) * counts.getOrDefault(key(q[0], d[1]), 0) * counts.getOrDefault(key(d[0], q[1]), 0);
        }
        return total;
    }

    private static long key(int x, int y) { return ((long) x << 32) | (y & 0xffffffffL); }
}
```

- **Time Complexity:** O(p) per count
- **Space Complexity:** O(p)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | List of All Points, Triple Loop | O(p^3) per count | O(p) |
| 2 | Map by Row | O(r) per count (r = points on the query row) | O(p) |
| 3 | Hash Map of Counts, Scan Diagonals (Optimized) | O(p) per count | O(p) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### DetectSquares.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetectSquares {

    private final Map<Long, Integer> counts = new HashMap<>();
    private final List<int[]> distinctPoints = new ArrayList<>();

    public static void main(String[] args) {
        DetectSquares squares = new DetectSquares();
        int[][] added = {{3, 10}, {11, 2}, {3, 2}};
        for (int[] point : added) {
            squares.add(point);
        }
        System.out.println("Input  : add " + Arrays.deepToString(added) + ", count [11, 10]");
        System.out.println("Output : " + squares.count(new int[]{11, 10}));
        System.out.println();

        System.out.println("Input  : count [14, 8]");
        System.out.println("Output : " + squares.count(new int[]{14, 8}));
        System.out.println();

        squares.add(new int[]{11, 2});
        System.out.println("Input  : add [11, 2], count [11, 10]");
        System.out.println("Output : " + squares.count(new int[]{11, 10}));
    }

    public void add(int[] point) {
        long key = key(point[0], point[1]);
        if (!counts.containsKey(key)) {
            distinctPoints.add(point.clone());
        }
        counts.merge(key, 1, Integer::sum);
    }

    public int count(int[] point) {
        int qx = point[0];
        int qy = point[1];
        int total = 0;
        for (int[] diagonal : distinctPoints) {
            int dx = Math.abs(diagonal[0] - qx);
            int dy = Math.abs(diagonal[1] - qy);
            if (dx == 0 || dx != dy) {
                continue;
            }
            // the diagonal corner is (x, y); the other two corners share a coordinate with the query
            int first = counts.getOrDefault(key(qx, diagonal[1]), 0);
            int second = counts.getOrDefault(key(diagonal[0], qy), 0);
            total += counts.get(key(diagonal[0], diagonal[1])) * first * second;
        }
        return total;
    }

    private static long key(int x, int y) {
        return ((long) x << 32) | (y & 0xffffffffL);
    }
}
```
