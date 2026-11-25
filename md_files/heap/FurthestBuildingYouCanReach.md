# Furthest Building You Can Reach

Given `heights` of buildings in a row, some `bricks` and some `ladders`, you start at building 0 and move to the next one. If the next building is not taller, you move for free. Otherwise you must use either one ladder, or `h` bricks where `h` is the height difference. Return the furthest building index you can reach.

## Algorithm Strategy (Min-Heap of the Largest Climbs)

1. Use ladders on the biggest climbs and bricks on the smaller ones. Process the buildings left to right.
2. For each climb `d > 0`, push `d` into a min-heap.
3. If the heap holds more than `ladders` climbs, pay the smallest one with bricks: `bricks -= heap.poll()`.
4. If `bricks` becomes negative, return the previous index. If the end is reached, return the last index.

## Example walkthrough

- For `[4, 2, 7, 6, 9, 14, 12]`, 5 bricks and 1 ladder: the climbs are 5, 2 and 5. The ladder takes the largest, the bricks pay for the rest.
- Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(n log L)` — Heap operations on at most `ladders + 1` elements.
- **Space Complexity:** `O(L)` — The heap of ladder climbs.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Binary Search on the Answer

Check whether building `k` is reachable by giving the ladders to the largest climbs up to `k`. O(n log^2 n).

```java
public static int binary(int[] h, int bricks, int ladders) {
    int lo = 0, hi = h.length - 1;
    while (lo < hi) { int mid = (lo + hi + 1) / 2; if (can(h, bricks, ladders, mid)) lo = mid; else hi = mid - 1; }
    return lo;
}
private static boolean can(int[] h, int bricks, int ladders, int k) {
    java.util.List<Integer> d = new java.util.ArrayList<>(); for (int i = 0; i < k; i++) if (h[i + 1] > h[i]) d.add(h[i + 1] - h[i]);
    java.util.Collections.sort(d); int use = d.size() - ladders; long need = 0; for (int i = 0; i < use; i++) need += d.get(i); return need <= bricks;
}
```

- **Time Complexity:** O(n log^2 n)
- **Space Complexity:** O(n)

### Approach 2: Min-Heap of Ladder Climbs (Optimized)

Keep the largest climbs on ladders and pay the smallest with bricks. This is the version implemented in the repository.

```java
public static int heap(int[] h, int bricks, int ladders) {
    java.util.PriorityQueue<Integer> pq = new java.util.PriorityQueue<>();
    for (int i = 0; i < h.length - 1; i++) { int d = h[i + 1] - h[i]; if (d <= 0) continue; pq.add(d); if (pq.size() > ladders) bricks -= pq.poll(); if (bricks < 0) return i; }
    return h.length - 1;
}
```

- **Time Complexity:** O(n log L)
- **Space Complexity:** O(L)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Binary Search on the Answer | O(n log^2 n) | O(n) |
| 2 | Min-Heap of Ladder Climbs (Optimized) | O(n log L) | O(L) |

## Source Code

### FurthestBuildingYouCanReach.java

```java
import java.util.Arrays;
import java.util.PriorityQueue;

public class FurthestBuildingYouCanReach {

    public static void main(String[] args) {
        int[][] heights = {{4, 2, 7, 6, 9, 14, 12}, {4, 12, 2, 7, 3, 18, 20, 3, 19}, {14, 3, 19, 3}, {1, 5, 1, 2, 3, 4, 10000}};
        int[] bricks = {5, 10, 17, 4};
        int[] ladders = {1, 2, 0, 1};
        for (int i = 0; i < heights.length; i++) {
            System.out.println("Input  : heights = " + Arrays.toString(heights[i]) + ", bricks = " + bricks[i] + ", ladders = " + ladders[i]);
            System.out.println("Output : " + furthestBuilding(heights[i], bricks[i], ladders[i]));
            System.out.println();
        }
    }

    public static int furthestBuilding(int[] heights, int bricks, int ladders) {
        if (heights == null || heights.length == 0) {
            return -1;
        }
        PriorityQueue<Integer> climbs = new PriorityQueue<>();
        for (int i = 0; i < heights.length - 1; i++) {
            int climb = heights[i + 1] - heights[i];
            if (climb <= 0) {
                continue;
            }
            climbs.add(climb);
            if (climbs.size() > ladders) {
                bricks -= climbs.poll();
            }
            if (bricks < 0) {
                return i;
            }
        }
        return heights.length - 1;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: ladders are worth the most on the largest climbs. The heap always holds the climbs that currently have a ladder; when there are too many, the smallest of them is downgraded to bricks.

**Input:** `heights = [4, 2, 7, 6, 9, 14, 12]`, `bricks = 5`, `ladders = 1`.

| Step | Climb | Heap | Bricks left |
|------|-------|------|-------------|
| 4 -> 2 | free | [] | 5 |
| 2 -> 7 | 5 | [5] | 5 |
| 7 -> 6 | free | [5] | 5 |
| 6 -> 9 | 3 | [3, 5], poll 3 | 2 |
| 9 -> 14 | 5 | [5, 5], poll 5 | -3 |

The bricks run out when trying to reach building 5, so the furthest building is index 4.

**Output:** `4`.
