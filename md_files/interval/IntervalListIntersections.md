# Interval List Intersections

Given two lists of closed intervals, `firstList` and `secondList`, each sorted by start time and made of pairwise disjoint intervals, return the intersection of the two lists. The intersection of `[a, b]` and `[c, d]` is `[max(a, c), min(b, d)]` when `max(a, c) <= min(b, d)`.

## Algorithm Strategy (Two Pointers)

1. Keep one pointer in each list.
2. For the current pair, compute `start = max(a.start, b.start)` and `end = min(a.end, b.end)`; if `start <= end`, record `[start, end]`.
3. Advance the pointer of the interval that ends first, because it cannot intersect anything further in the other list.
4. Stop when either list is exhausted.

## Example walkthrough

- For `[[0,2],[5,10],[13,23],[24,25]]` and `[[1,5],[8,12],[15,24],[25,26]]`: the first overlap is `[1, 2]`, then `[5, 5]`, then `[8, 10]`, and so on.
- Result: `[[1, 2], [5, 5], [8, 10], [15, 23], [24, 24], [25, 25]]`.

## Complexity Analysis

- **Time Complexity:** `O(m + n)` — Each interval is visited once.
- **Space Complexity:** `O(1)` — Apart from the output.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Compare Every Pair

Intersect every interval of one list with every interval of the other, then sort the results.

```java
public static int[][] allPairs(int[][] a, int[][] b) {
    java.util.List<int[]> out = new java.util.ArrayList<>();
    for (int[] x : a) for (int[] y : b) { int s = Math.max(x[0], y[0]), e = Math.min(x[1], y[1]); if (s <= e) out.add(new int[]{s, e}); }
    out.sort((p, q) -> p[0] - q[0]); return out.toArray(new int[0][]);
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(1)

### Approach 2: Two Pointers (Optimized)

Advance the pointer of the interval that ends first. This is the version implemented in the repository.

```java
public static int[][] twoPointers(int[][] a, int[][] b) {
    java.util.List<int[]> out = new java.util.ArrayList<>(); int i = 0, j = 0;
    while (i < a.length && j < b.length) { int s = Math.max(a[i][0], b[j][0]), e = Math.min(a[i][1], b[j][1]); if (s <= e) out.add(new int[]{s, e}); if (a[i][1] < b[j][1]) i++; else j++; }
    return out.toArray(new int[0][]);
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Compare Every Pair | O(m * n) | O(1) |
| 2 | Two Pointers (Optimized) | O(m + n) | O(1) |

## Source Code

### IntervalListIntersections.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntervalListIntersections {

    public static void main(String[] args) {
        int[][][] first = {{{0, 2}, {5, 10}, {13, 23}, {24, 25}}, {{1, 3}, {5, 9}}, {}, {{1, 7}}};
        int[][][] second = {{{1, 5}, {8, 12}, {15, 24}, {25, 26}}, {}, {{4, 8}, {10, 12}}, {{3, 10}}};
        for (int i = 0; i < first.length; i++) {
            System.out.println("Input  : firstList = " + Arrays.deepToString(first[i]) + ", secondList = " + Arrays.deepToString(second[i]));
            System.out.println("Output : " + Arrays.deepToString(intervalIntersection(first[i], second[i])));
            System.out.println();
        }
    }

    public static int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<int[]> result = new ArrayList<>();
        if (firstList == null || secondList == null) {
            return new int[0][];
        }
        int i = 0;
        int j = 0;
        while (i < firstList.length && j < secondList.length) {
            int start = Math.max(firstList[i][0], secondList[j][0]);
            int end = Math.min(firstList[i][1], secondList[j][1]);
            if (start <= end) {
                result.add(new int[]{start, end});
            }
            if (firstList[i][1] < secondList[j][1]) {
                i++;
            } else {
                j++;
            }
        }
        return result.toArray(new int[0][]);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: both lists are sorted and disjoint, so after comparing two intervals, the one that ends first cannot overlap anything else in the other list and can be dropped.

**Input:** `firstList = [[0, 2], [5, 10], [13, 23], [24, 25]]`, `secondList = [[1, 5], [8, 12], [15, 24], [25, 26]]`.

| `i` | `j` | Intersection | Advance |
|-----|-----|--------------|---------|
| [0,2] | [1,5] | [1, 2] | `i` (ends first) |
| [5,10] | [1,5] | [5, 5] | `j` |
| [5,10] | [8,12] | [8, 10] | `i` |
| [13,23] | [8,12] | none | `j` |
| [13,23] | [15,24] | [15, 23] | `i` |
| [24,25] | [15,24] | [24, 24] | `j` |
| [24,25] | [25,26] | [25, 25] | `i`, then done |

**Output:** `[[1, 2], [5, 5], [8, 10], [15, 23], [24, 24], [25, 25]]`.
