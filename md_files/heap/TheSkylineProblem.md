# The Skyline Problem

Given buildings `[left, right, height]`, return the skyline they form: a list of key points `[x, height]` sorted by `x`, where each key point is the left end of a horizontal segment, and the last point has height 0. There must be no two consecutive key points with the same height.

## Algorithm Strategy (Sweep Line with a Max-Heap)

1. Turn each building into two events: a start `(left, -height)` and an end `(right, height)`. Sort by `x`, with starts before ends at the same `x` (negative heights) and taller starts first.
2. Keep a max-heap (or a multiset) of active heights, initially `{0}`.
3. On a start, add the height; on an end, remove it.
4. After each event, compare the current maximum height with the previous maximum; if it changed, emit `[x, newMax]`.

## Example walkthrough

- For `[[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]` the skyline rises to 10 at x = 2, to 15 at x = 3, drops to 12 at x = 7, to 0 at x = 12, and so on.
- Result: `[[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Sorting the events and heap operations.
- **Space Complexity:** `O(n)` — The events and the multiset.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Height at Every Coordinate

Compute the maximum height at every integer coordinate and emit the points where it changes. Works only for small coordinate ranges.

```java
public static java.util.List<java.util.List<Integer>> perCoordinate(int[][] bs) {
    java.util.List<java.util.List<Integer>> out = new java.util.ArrayList<>(); if (bs.length == 0) return out; int max = 0; for (int[] b : bs) max = Math.max(max, b[1]); int prev = 0;
    for (int x = 0; x <= max; x++) { int h = 0; for (int[] b : bs) if (b[0] <= x && x < b[1]) h = Math.max(h, b[2]); if (h != prev) { out.add(java.util.Arrays.asList(x, h)); prev = h; } }
    return out;
}
```

- **Time Complexity:** O(maxX * n)
- **Space Complexity:** O(1)

### Approach 2: Sweep Line with a Multiset of Heights (Optimized)

Process sorted start and end events and report changes of the maximum height. This is the version implemented in the repository.

```java
public static java.util.List<java.util.List<Integer>> sweep(int[][] bs) {
    java.util.List<java.util.List<Integer>> out = new java.util.ArrayList<>(); java.util.List<int[]> ev = new java.util.ArrayList<>(); for (int[] b : bs) { ev.add(new int[]{b[0], -b[2]}); ev.add(new int[]{b[1], b[2]}); }
    ev.sort((a, c) -> a[0] != c[0] ? a[0] - c[0] : a[1] - c[1]); java.util.TreeMap<Integer, Integer> h = new java.util.TreeMap<>(); h.put(0, 1); int prev = 0;
    for (int[] e : ev) { if (e[1] < 0) h.merge(-e[1], 1, Integer::sum); else if (h.merge(e[1], -1, Integer::sum) == 0) h.remove(e[1]); int cur = h.lastKey(); if (cur != prev) { out.add(java.util.Arrays.asList(e[0], cur)); prev = cur; } }
    return out;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Height at Every Coordinate | O(maxX * n) | O(1) |
| 2 | Sweep Line with a Multiset of Heights (Optimized) | O(n log n) | O(n) |

## Source Code

### TheSkylineProblem.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class TheSkylineProblem {

    public static void main(String[] args) {
        int[][][] inputs = {{{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}}, {{0, 2, 3}, {2, 5, 3}}, {{1, 5, 3}}, {{0, 3, 3}, {1, 4, 5}}};
        for (int[][] buildings : inputs) {
            System.out.println("Input  : buildings = " + Arrays.deepToString(buildings));
            System.out.println("Output : " + getSkyline(buildings));
            System.out.println();
        }
    }

    public static List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> skyline = new ArrayList<>();
        if (buildings == null || buildings.length == 0) {
            return skyline;
        }
        List<int[]> events = new ArrayList<>();
        for (int[] b : buildings) {
            events.add(new int[]{b[0], -b[2]});
            events.add(new int[]{b[1], b[2]});
        }
        events.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        TreeMap<Integer, Integer> heights = new TreeMap<>();
        heights.put(0, 1);
        int previous = 0;
        for (int[] event : events) {
            if (event[1] < 0) {
                heights.merge(-event[1], 1, Integer::sum);
            } else if (heights.merge(event[1], -1, Integer::sum) == 0) {
                heights.remove(event[1]);
            }
            int current = heights.lastKey();
            if (current != previous) {
                skyline.add(Arrays.asList(event[0], current));
                previous = current;
            }
        }
        return skyline;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the skyline only changes where a building starts or ends. Sweeping those events left to right while keeping the set of active heights, a key point is emitted whenever the tallest active building changes.

**Input:** `[[2, 9, 10], [3, 7, 15], [5, 12, 12], [15, 20, 10], [19, 24, 8]]`.

| x | Event | Max height afterwards | Key point |
|---|-------|-----------------------|-----------|
| 2 | start 10 | 10 | [2, 10] |
| 3 | start 15 | 15 | [3, 15] |
| 5 | start 12 | 15 | |
| 7 | end 15 | 12 | [7, 12] |
| 9 | end 10 | 12 | |
| 12 | end 12 | 0 | [12, 0] |
| 15 | start 10 | 10 | [15, 10] |
| 19 | start 8 | 10 | |
| 20 | end 10 | 8 | [20, 8] |
| 24 | end 8 | 0 | [24, 0] |

**Output:** `[[2, 10], [3, 15], [7, 12], [12, 0], [15, 10], [20, 8], [24, 0]]`.
