# Max Points on a Line

Given an array of `points` where `points[i] = [x, y]` represents a point on the X-Y plane, return the maximum number of points that lie on the same straight line.

## Algorithm Strategy (Reduced Slope Counting per Anchor)

1. Use each point as an anchor.
2. For every other point, compute the slope to the anchor as a reduced fraction `(dy / g, dx / g)` with `g = gcd(dx, dy)` and a normalised sign, so that equal slopes have equal keys and no floating point is used.
3. Count how many points share each slope in a `HashMap`; the best line through the anchor is `1 + max count`.
4. Keep the best over all anchors. With fewer than three points, all of them are collinear.

## Example walkthrough

- For `[[1,1],[2,2],[3,3]]` the slopes from the first point to the other two are both `1/1`.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — For each anchor, one pass over the other points.
- **Space Complexity:** `O(n)` — The slope map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force over Triples

Check every point against every pair using the cross product.

```java
public static int triples(int[][] p) {
    int n = p.length; if (n <= 2) return n; int best = 2;
    for (int i = 0; i < n; i++) for (int j = i + 1; j < n; j++) { int c = 2; for (int k = j + 1; k < n; k++) if ((long) (p[j][0] - p[i][0]) * (p[k][1] - p[i][1]) == (long) (p[j][1] - p[i][1]) * (p[k][0] - p[i][0])) c++; best = Math.max(best, c); }
    return best;
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(1)

### Approach 2: Slope Map with Reduced Fractions (Optimized)

Count equal reduced slopes from each anchor. This is the version implemented in the repository.

```java
public static int slopes(int[][] p) {
    int n = p.length; if (n <= 2) return n; int best = 2;
    for (int i = 0; i < n; i++) { java.util.Map<String, Integer> m = new java.util.HashMap<>();
        for (int j = i + 1; j < n; j++) { int dx = p[j][0] - p[i][0], dy = p[j][1] - p[i][1]; int g = gcd(Math.abs(dx), Math.abs(dy)); dx /= g; dy /= g; if (dx < 0 || (dx == 0 && dy < 0)) { dx = -dx; dy = -dy; } best = Math.max(best, m.merge(dy + "/" + dx, 1, Integer::sum) + 1); } }
    return best;
}
private static int gcd(int a, int b) { return b == 0 ? a : gcd(b, a % b); }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force over Triples | O(n^3) | O(1) |
| 2 | Slope Map with Reduced Fractions (Optimized) | O(n^2) | O(n) |

## Source Code

### MaxPointsOnALine.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MaxPointsOnALine {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 1}, {2, 2}, {3, 3}}, {{1, 1}, {3, 2}, {5, 3}, {4, 1}, {2, 3}, {1, 4}}, {{0, 0}}, {{1, 1}, {1, 2}, {1, 3}}};
        for (int[][] points : inputs) {
            System.out.println("Input  : points = " + Arrays.deepToString(points));
            System.out.println("Output : " + maxPoints(points));
            System.out.println();
        }
    }

    public static int maxPoints(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        int n = points.length;
        if (n <= 2) {
            return n;
        }
        int best = 2;
        for (int i = 0; i < n; i++) {
            Map<String, Integer> slopes = new HashMap<>();
            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];
                int g = gcd(Math.abs(dx), Math.abs(dy));
                dx /= g;
                dy /= g;
                if (dx < 0 || (dx == 0 && dy < 0)) {
                    dx = -dx;
                    dy = -dy;
                }
                int count = slopes.merge(dy + "/" + dx, 1, Integer::sum);
                best = Math.max(best, count + 1);
            }
        }
        return best;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: all points on a line through an anchor have the same slope to it, so grouping the other points by slope finds the biggest line through that anchor. Reducing the slope to lowest terms avoids floating-point errors.

**Input:** `points = [[1, 1], [2, 2], [3, 3]]`.

| Anchor | Other point | `(dy, dx)` | Reduced slope | Count |
|--------|-------------|------------|----------------|-------|
| (1, 1) | (2, 2) | (1, 1) | 1/1 | 1 |
| (1, 1) | (3, 3) | (2, 2) | 1/1 | 2 |

The best line through the anchor has `2 + 1 = 3` points.

**Output:** `3`.
