# Non-overlapping Intervals

Given a list of intervals, return the minimum number of intervals to remove so that the remaining intervals do not overlap (intervals that only touch at an endpoint do not overlap).

## Algorithm Strategy (Greedy by Earliest End Time)

1. Sort the intervals by end time. Keeping the interval that finishes first leaves the most room for the following intervals.
2. Keep the first interval and remember its end as `lastEnd`.
3. For each next interval: if its start is `>= lastEnd`, there is no overlap, so keep it and update `lastEnd`; otherwise it overlaps, so it is removed (count it).
4. Return the number of removed intervals.

## Example walkthrough

- For `[[1,2],[2,3],[3,4],[1,3]]` sorted by end: `[1,2],[2,3],[1,3],[3,4]`. Keep `[1,2]` and `[2,3]`; `[1,3]` starts at 1 < 3, remove it; keep `[3,4]`.
- Result: `1`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Dominated by sorting.
- **Space Complexity:** `O(1)` — Besides the sort.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Dynamic Programming (Longest Chain)

Sort by start; `dp[i]` is the largest number of non-overlapping intervals that end with interval `i`. The intervals to remove are `n` minus the best chain.

```java
public static int eraseOverlapDp(int[][] intervals) {
    if (intervals.length == 0) return 0;
    int[][] sorted = intervals.clone();
    Arrays.sort(sorted, Comparator.comparingInt((int[] a) -> a[0]));
    int n = sorted.length, best = 1;
    int[] dp = new int[n];
    for (int i = 0; i < n; i++) {
        dp[i] = 1;
        for (int j = 0; j < i; j++) {
            if (sorted[j][1] <= sorted[i][0]) dp[i] = Math.max(dp[i], dp[j] + 1);
        }
        best = Math.max(best, dp[i]);
    }
    return n - best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Greedy: Sort by Start, Drop the Longer One

Sort by start. When two neighbouring intervals overlap, remove the one with the later end (it blocks more of what follows) and keep the smaller end.

```java
public static int eraseOverlapByStart(int[][] intervals) {
    if (intervals.length == 0) return 0;
    int[][] sorted = intervals.clone();
    Arrays.sort(sorted, Comparator.comparingInt((int[] a) -> a[0]));
    int removed = 0, lastEnd = sorted[0][1];
    for (int i = 1; i < sorted.length; i++) {
        if (sorted[i][0] < lastEnd) {
            removed++;
            lastEnd = Math.min(lastEnd, sorted[i][1]);
        } else {
            lastEnd = sorted[i][1];
        }
    }
    return removed;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1) besides the sort

### Approach 3: Greedy: Sort by End (Optimized)

Always keep the interval that finishes first; it leaves the most room for the following ones. This is the version implemented in the repository.

```java
public static int eraseOverlapIntervals(int[][] intervals) {
    if (intervals.length == 0) return 0;
    int[][] sorted = intervals.clone();
    Arrays.sort(sorted, Comparator.comparingInt((int[] a) -> a[1]));
    int removed = 0, lastEnd = sorted[0][1];
    for (int i = 1; i < sorted.length; i++) {
        if (sorted[i][0] >= lastEnd) lastEnd = sorted[i][1];
        else removed++;
    }
    return removed;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1) besides the sort

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Dynamic Programming (Longest Chain) | O(n^2) | O(n) |
| 2 | Greedy: Sort by Start, Drop the Longer One | O(n log n) | O(1) besides the sort |
| 3 | Greedy: Sort by End (Optimized) | O(n log n) | O(1) besides the sort |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### NonOverlappingIntervals.java

```java
import java.util.Arrays;
import java.util.Comparator;

public class NonOverlappingIntervals {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 2}, {2, 3}, {3, 4}, {1, 3}}, {{1, 2}, {1, 2}, {1, 2}}, {{1, 2}, {2, 3}}};
        for (int[][] intervals : inputs) {
            System.out.println("Input  : intervals = " + Arrays.deepToString(intervals));
            System.out.println("Output : " + eraseOverlapIntervals(intervals));
            System.out.println();
        }
    }

    public static int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, Comparator.comparingInt((int[] interval) -> interval[1]));

        int removed = 0;
        int lastEnd = sorted[0][1];
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i][0] >= lastEnd) {
                lastEnd = sorted[i][1];     // no overlap: keep this interval
            } else {
                removed++;                  // overlap: remove the interval that ends later
            }
        }
        return removed;
    }
}
```
