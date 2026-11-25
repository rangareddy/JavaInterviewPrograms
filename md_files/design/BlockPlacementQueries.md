# Block Placement Queries

There is an infinite number line starting at 0 and extending to the right. Queries are `[1, x]` (build an obstacle at position `x`) or `[2, x, sz]` (can a block of size `sz` be placed in the interval `[0, x]` so that it does not touch or cross an obstacle? It may end at an obstacle but must lie fully inside `[0, x]`). Return the answers to all queries of the second type, in order.

## Algorithm Strategy (Sorted Obstacles plus a Segment Tree of Gaps)

1. Keep the obstacles in a `TreeSet` (with 0 as a permanent origin). For each obstacle `p`, its gap is the distance to the previous obstacle.
2. Store each gap in a segment tree indexed by the obstacle's position, supporting a range maximum.
3. When an obstacle `x` is added, set `gap[x] = x - prev` and update the next obstacle's gap to `next - x`.
4. For a query `(x, sz)`: let `l` be the largest obstacle at or below `x`. A block fits if the largest gap among obstacles up to `l` is at least `sz`, or if the free tail `x - l` is at least `sz`.

## Example walkthrough

- For queries `[1,2], [2,3,3], [2,3,1], [2,2,2]`: after an obstacle at 2, a block of size 3 does not fit in `[0, 3]`, size 1 fits, and size 2 fits in `[0, 2]`.
- Result: `[false, true, true]`.

## Complexity Analysis

- **Time Complexity:** `O(q log q)` — Each query does a tree lookup and a segment tree update or query.
- **Space Complexity:** `O(maxPosition)` — The segment tree.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Scan the Sorted Obstacles per Query

Keep the obstacles in a sorted set and, for each query, walk them up to `x` computing the largest gap. O(q) per query.

```java
public static java.util.List<Boolean> scan(int[][] queries) {
    java.util.List<Boolean> out = new java.util.ArrayList<>(); java.util.TreeSet<Integer> ob = new java.util.TreeSet<>(); ob.add(0);
    for (int[] q : queries) { if (q[0] == 1) ob.add(q[1]); else { int prev = 0, widest = 0; for (int o : ob) { if (o > q[1]) break; widest = Math.max(widest, o - prev); prev = o; } widest = Math.max(widest, q[1] - prev); out.add(widest >= q[2]); } }
    return out;
}
```

- **Time Complexity:** O(q^2)
- **Space Complexity:** O(q)

### Approach 2: Sorted Set and Segment Tree of Gaps (Optimized)

Maintain the gap before each obstacle in a segment tree and query a prefix maximum. This is the version implemented in the repository.

```java
public static java.util.List<Boolean> segment(int[][] queries) {
    java.util.List<Boolean> out = new java.util.ArrayList<>(); int max = 1; for (int[] q : queries) max = Math.max(max, q[1]); int size = 1; while (size < max + 2) size <<= 1; int[] t = new int[2 * size]; java.util.TreeSet<Integer> ob = new java.util.TreeSet<>(); ob.add(0);
    for (int[] q : queries) { int x = q[1]; if (q[0] == 1) { Integer p = ob.lower(x), nx = ob.higher(x); ob.add(x); set(t, size, x, x - p); if (nx != null) set(t, size, nx, nx - x); }
        else { int last = ob.floor(x); int w = x - last; int lo = size, hi = last + size + 1; while (lo < hi) { if ((lo & 1) == 1) w = Math.max(w, t[lo++]); if ((hi & 1) == 1) w = Math.max(w, t[--hi]); lo >>= 1; hi >>= 1; } out.add(w >= q[2]); } }
    return out;
}
private static void set(int[] t, int size, int pos, int v) { int i = pos + size; t[i] = v; for (i >>= 1; i >= 1; i >>= 1) t[i] = Math.max(t[2 * i], t[2 * i + 1]); }
```

- **Time Complexity:** O(q log q)
- **Space Complexity:** O(maxPosition)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Scan the Sorted Obstacles per Query | O(q^2) | O(q) |
| 2 | Sorted Set and Segment Tree of Gaps (Optimized) | O(q log q) | O(maxPosition) |

## Source Code

### BlockPlacementQueries.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class BlockPlacementQueries {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 2}, {2, 3, 3}, {2, 3, 1}, {2, 2, 2}}, {{1, 7}, {2, 7, 6}, {1, 2}, {2, 7, 5}, {2, 7, 6}}, {{2, 5, 5}, {2, 5, 6}}};
        for (int[][] queries : inputs) {
            System.out.println("Input  : queries = " + Arrays.deepToString(queries));
            System.out.println("Output : " + getResults(queries));
            System.out.println();
        }
    }

    public static List<Boolean> getResults(int[][] queries) {
        List<Boolean> answers = new ArrayList<>();
        int max = 1;
        for (int[] q : queries) {
            max = Math.max(max, q[1]);
        }
        int size = 1;
        while (size < max + 2) {
            size <<= 1;
        }
        int[] tree = new int[2 * size];
        TreeSet<Integer> obstacles = new TreeSet<>();
        obstacles.add(0);
        for (int[] q : queries) {
            int x = q[1];
            if (q[0] == 1) {
                Integer previous = obstacles.lower(x);
                Integer next = obstacles.higher(x);
                obstacles.add(x);
                update(tree, size, x, x - previous);
                if (next != null) {
                    update(tree, size, next, next - x);
                }
            } else {
                int last = obstacles.floor(x);
                int widest = Math.max(x - last, maxGap(tree, size, 0, last));
                answers.add(widest >= q[2]);
            }
        }
        return answers;
    }

    private static void update(int[] tree, int size, int position, int value) {
        int i = position + size;
        tree[i] = value;
        for (i >>= 1; i >= 1; i >>= 1) {
            tree[i] = Math.max(tree[2 * i], tree[2 * i + 1]);
        }
    }

    private static int maxGap(int[] tree, int size, int from, int to) {
        int result = 0;
        int lo = from + size;
        int hi = to + size + 1;
        while (lo < hi) {
            if ((lo & 1) == 1) {
                result = Math.max(result, tree[lo++]);
            }
            if ((hi & 1) == 1) {
                result = Math.max(result, tree[--hi]);
            }
            lo >>= 1;
            hi >>= 1;
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a block of size `sz` fits in `[0, x]` exactly when some free stretch inside it is at least `sz` long. The free stretches are the gaps between consecutive obstacles, plus the open stretch after the last obstacle that is at or before `x`. A segment tree over positions keeps each obstacle's gap, so the largest gap up to `x` is a range-maximum query.

**Queries:** `[1, 2], [2, 3, 3], [2, 3, 1], [2, 2, 2]`.

| Query | Obstacles | Gaps considered | Answer |
|-------|-----------|-----------------|--------|
| `[1, 2]` | 0, 2 | gap of obstacle 2 is 2 | |
| `[2, 3, 3]` | 0, 2 | gap 2, tail `3 - 2 = 1` | false |
| `[2, 3, 1]` | 0, 2 | gap 2, tail 1 | true |
| `[2, 2, 2]` | 0, 2 | gap 2, tail 0 | true |

**Output:** `[false, true, true]`.
