# Minimum Number of Arrows to Burst Balloons

Balloons are given as intervals `points[i] = [xstart, xend]` on a wall. An arrow shot vertically at `x` bursts every balloon with `xstart <= x <= xend`. Return the minimum number of arrows needed to burst all balloons.

## Algorithm Strategy (Sort by End, Shoot at the Earliest End)

1. Sort the balloons by their end coordinate (compare with `Integer.compare` to avoid overflow).
2. Shoot the first arrow at the end of the first balloon; every balloon that starts at or before that point is burst by it.
3. Walk the sorted list; when a balloon starts after the position of the last arrow, shoot a new arrow at its end.
4. Count the arrows.

## Example walkthrough

- For `[[10,16],[2,8],[1,6],[7,12]]`: sorted by end `[1,6],[2,8],[7,12],[10,16]`. Shoot at 6 (bursts the first two), then at 12 (bursts the last two).
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Sorting dominates.
- **Space Complexity:** `O(1)` — Apart from the sort.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Candidate Arrow Set

Choose arrow positions among all balloon ends and test every subset. Exponential.

```java
public static int brute(int[][] p) {
    if (p.length == 0) return 0; int n = p.length, best = n; for (int m = 1; m < (1 << n); m++) { if (Integer.bitCount(m) >= best) continue; boolean all = true; for (int[] b : p) { boolean hit = false; for (int i = 0; i < n; i++) if ((m >> i & 1) == 1 && p[i][1] >= b[0] && p[i][1] <= b[1]) { hit = true; break; } if (!hit) { all = false; break; } } if (all) best = Integer.bitCount(m); } return best;
}
```

- **Time Complexity:** O(2^n * n^2)
- **Space Complexity:** O(1)

### Approach 2: Greedy by End Coordinate (Optimized)

Sort by end and shoot at the end of the first unburst balloon. This is the version implemented in the repository.

```java
public static int greedy(int[][] in) { if (in.length == 0) return 0; int[][] p = in.clone(); java.util.Arrays.sort(p, (a, b) -> Integer.compare(a[1], b[1])); int c = 1, pos = p[0][1]; for (int i = 1; i < p.length; i++) if (p[i][0] > pos) { c++; pos = p[i][1]; } return c; }
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Candidate Arrow Set | O(2^n * n^2) | O(1) |
| 2 | Greedy by End Coordinate (Optimized) | O(n log n) | O(1) |

## Source Code

### MinimumNumberOfArrowsToBurstBalloons.java

```java
import java.util.Arrays;

public class MinimumNumberOfArrowsToBurstBalloons {

    public static void main(String[] args) {
        int[][][] inputs = {{{10, 16}, {2, 8}, {1, 6}, {7, 12}}, {{1, 2}, {3, 4}, {5, 6}, {7, 8}}, {{1, 2}, {2, 3}, {3, 4}, {4, 5}}, {{-2147483646, -2147483645}, {2147483646, 2147483647}}, {}};
        for (int[][] points : inputs) {
            System.out.println("Input  : points = " + Arrays.deepToString(points));
            System.out.println("Output : " + findMinArrowShots(points));
            System.out.println();
        }
    }

    public static int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        int[][] sorted = points.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[1], b[1]));
        int arrows = 1;
        int position = sorted[0][1];
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i][0] > position) {
                arrows++;
                position = sorted[i][1];
            }
        }
        return arrows;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: among the balloons not yet burst, the one that ends first limits where an arrow can go. Shooting at exactly its end bursts that balloon and every other balloon that starts at or before that point, and no better position exists, because moving left misses that balloon and moving right can only lose balloons that end earlier.

**Input:** `points = [[10, 16], [2, 8], [1, 6], [7, 12]]`.

| Sorted by end | Start vs arrow position | Action |
|---------------|--------------------------|--------|
| [1, 6] | | shoot at 6 |
| [2, 8] | 2 <= 6 | burst by the same arrow |
| [7, 12] | 7 > 6 | shoot at 12 |
| [10, 16] | 10 <= 12 | burst by the same arrow |

**Output:** `2`.
