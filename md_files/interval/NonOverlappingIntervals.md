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

### Approach 3: DP with Binary Search (Weighted-Interval Style)

Sort by end time. `dp[i]` is the most intervals that can be kept among the first `i` intervals: either skip interval `i`, or keep it and add the best result for the intervals that end no later than its start (found with binary search). The answer is `n - dp[n]`.

```java
public static int eraseOverlapDpBinary(int[][] intervals) {
    int n = intervals.length;
    int[][] sorted = intervals.clone();
    Arrays.sort(sorted, Comparator.comparingInt((int[] a) -> a[1]));
    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; i++) {
        int start = sorted[i - 1][0];
        int low = 0, high = i - 1;                          // count of intervals (among the first i-1) with end <= start
        while (low < high) {
            int mid = (low + high) / 2;
            if (sorted[mid][1] <= start) low = mid + 1; else high = mid;
        }
        dp[i] = Math.max(dp[i - 1], dp[low] + 1);
    }
    return n - dp[n];
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 4: Memoized Recursion (Keep or Skip)

Sort by start and decide for every interval whether to keep it (only if it does not overlap the last kept one) or to skip it; cache the result per `(index, last kept index)`.

```java
public static int eraseOverlapMemo(int[][] intervals) {
    int n = intervals.length;
    int[][] sorted = intervals.clone();
    Arrays.sort(sorted, Comparator.comparingInt((int[] a) -> a[0]));
    int[][] memo = new int[n][n + 1];
    for (int[] row : memo) Arrays.fill(row, -1);
    return n - best(sorted, 0, -1, memo);
}

// most intervals that can be kept among sorted[index..], given the last kept interval 'last'
private static int best(int[][] sorted, int index, int last, int[][] memo) {
    if (index == sorted.length) return 0;
    if (memo[index][last + 1] != -1) return memo[index][last + 1];
    int result = best(sorted, index + 1, last, memo);                                   // skip
    if (last == -1 || sorted[last][1] <= sorted[index][0]) {
        result = Math.max(result, 1 + best(sorted, index + 1, index, memo));            // keep
    }
    return memo[index][last + 1] = result;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Approach 5: Brute Force over All Subsets

Try every subset of the intervals and keep the largest one without overlaps. Exponential, so only for a handful of intervals, but it is the definition of the problem.

```java
public static int eraseOverlapSubsets(int[][] intervals) {
    int n = intervals.length, best = 0;
    for (int mask = 0; mask < (1 << n); mask++) {
        boolean ok = true;
        int count = Integer.bitCount(mask);
        for (int i = 0; i < n && ok; i++) {
            if ((mask & (1 << i)) == 0) continue;
            for (int j = i + 1; j < n && ok; j++) {
                if ((mask & (1 << j)) != 0 && intervals[i][0] < intervals[j][1] && intervals[j][0] < intervals[i][1]) ok = false;
            }
        }
        if (ok) best = Math.max(best, count);
    }
    return n - best;
}
```

- **Time Complexity:** O(2^n * n^2)
- **Space Complexity:** O(1)

### Approach 6: Activity Selection with a Min-Heap on End Times

The greedy "earliest end first" rule, but without sorting: put the intervals in a min-heap ordered by end time and take them out one by one, keeping an interval when it starts at or after the last kept end.

```java
public static int eraseOverlapHeap(int[][] intervals) {
    PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt((int[] a) -> a[1]));
    heap.addAll(Arrays.asList(intervals));
    int kept = 0, lastEnd = Integer.MIN_VALUE;
    while (!heap.isEmpty()) {
        int[] interval = heap.poll();
        if (interval[0] >= lastEnd) { kept++; lastEnd = interval[1]; }
    }
    return intervals.length - kept;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 7: Greedy: Sort by End (Optimized)

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
| 3 | DP with Binary Search (Weighted-Interval Style) | O(n log n) | O(n) |
| 4 | Memoized Recursion (Keep or Skip) | O(n^2) | O(n^2) |
| 5 | Brute Force over All Subsets | O(2^n * n^2) | O(1) |
| 6 | Activity Selection with a Min-Heap on End Times | O(n log n) | O(n) |
| 7 | Greedy: Sort by End (Optimized) | O(n log n) | O(1) besides the sort |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
