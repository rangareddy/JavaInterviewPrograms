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
