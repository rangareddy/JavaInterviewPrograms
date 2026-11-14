# Merge Intervals

Utility class to merge overlapping interval ranges.

## Algorithm Strategy (Sort and Sweep)

1. Sort all intervals by their start value in ascending order, so overlapping intervals become neighbors.
2. Copy the first interval into the output list and treat it as the `current` interval.
3. For every following interval, compare its start with the end of `current`.
4. If `next.start <= current.end` they overlap: extend the current interval with `current.end = max(current.end, next.end)`.
5. Otherwise there is a gap: start a new current interval from `next` and add it to the output list.
6. After the last interval, the list holds the minimal set of non-overlapping intervals.

## Example walkthrough

- For `[[1,3],[8,10],[2,6],[15,18]]`:
- Sorted: `[[1,3],[2,6],[8,10],[15,18]]`.
- `[1,3]` and `[2,6]` overlap and become `[1,6]`; `[8,10]` and `[15,18]` start after the previous end.
- Result: `[[1,6],[8,10],[15,18]]`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Dominated by the initial sorting step, where `n` is the number of intervals. The single linear scan takes `O(n)`.
- **Space Complexity:** `O(n)` or `O(log n)` — Depends on the sorting algorithm implementation space (e.g., Dual-Pivot Quicksort / TimSort), plus `O(n)` space for the output list.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Brute Force (Repeated Merging)

Repeatedly look for any two intervals that overlap and replace them with their union, until no pair overlaps any more.

```java
public static int[][] mergeBrute(int[][] intervals) {
    List<int[]> list = new ArrayList<>();
    for (int[] in : intervals) list.add(new int[]{in[0], in[1]});

    boolean merged = true;
    while (merged) {
        merged = false;
        outer:
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                int[] a = list.get(i), b = list.get(j);
                if (a[0] <= b[1] && b[0] <= a[1]) {
                    list.set(i, new int[]{Math.min(a[0], b[0]), Math.max(a[1], b[1])});
                    list.remove(j);
                    merged = true;
                    break outer;
                }
            }
        }
    }
    list.sort(Comparator.comparingInt(a -> a[0]));
    return list.toArray(new int[0][]);
}
```

- **Time Complexity:** O(n^3) in the worst case
- **Space Complexity:** O(n)

### Approach 2: Sort + Stack

Sort by start time and keep a stack of merged intervals: if the new interval overlaps the interval on top of the stack, extend the top; otherwise push it.

```java
public static int[][] mergeStack(int[][] intervals) {
    int[][] sorted = intervals.clone();
    Arrays.sort(sorted, Comparator.comparingInt(a -> a[0]));

    Deque<int[]> stack = new ArrayDeque<>();
    for (int[] in : sorted) {
        if (!stack.isEmpty() && stack.peek()[1] >= in[0]) {
            stack.peek()[1] = Math.max(stack.peek()[1], in[1]);
        } else {
            stack.push(new int[]{in[0], in[1]});
        }
    }
    int[][] result = new int[stack.size()][];
    for (int i = result.length - 1; i >= 0; i--) result[i] = stack.pop();
    return result;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Separately Sorted Starts and Ends

Sort all starts and all ends independently and sweep through them: a start (taken first on ties, so touching intervals merge) opens an interval, an end closes one, and whenever the number of open intervals drops to zero a merged interval is complete.

```java
public static int[][] mergeIntervalsStartsEnds(int[][] intervals) {
    int n = intervals.length;
    int[] starts = new int[n], ends = new int[n];
    for (int i = 0; i < n; i++) { starts[i] = intervals[i][0]; ends[i] = intervals[i][1]; }
    Arrays.sort(starts);
    Arrays.sort(ends);
    List<int[]> result = new ArrayList<>();
    int i = 0, j = 0, open = 0, begin = 0;
    while (j < n) {
        if (i < n && starts[i] <= ends[j]) {
            if (open == 0) begin = starts[i];
            open++;
            i++;
        } else {
            open--;
            if (open == 0) result.add(new int[]{begin, ends[j]});
            j++;
        }
    }
    return result.toArray(new int[0][]);
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 4: Sweep Line with a TreeMap

Record `+1` at every start and `-1` at every end in a `TreeMap` and walk through the keys in order. A merged interval starts when the running count leaves 0 and ends when it returns to 0.

```java
public static int[][] mergeIntervalsTreeMap(int[][] intervals) {
    TreeMap<Integer, Integer> delta = new TreeMap<>();
    for (int[] interval : intervals) {
        delta.merge(interval[0], 1, Integer::sum);
        delta.merge(interval[1], -1, Integer::sum);
    }
    List<int[]> result = new ArrayList<>();
    int running = 0, begin = 0;
    for (Map.Entry<Integer, Integer> e : delta.entrySet()) {
        if (running == 0) begin = e.getKey();
        running += e.getValue();
        if (running == 0) result.add(new int[]{begin, e.getKey()});
    }
    return result.toArray(new int[0][]);
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 5: Difference Array over Bounded Coordinates

When the coordinates are small non-negative integers, mark the coverage in an array and read the merged intervals as runs of covered cells. Coordinates are doubled so that `[1, 2]` and `[3, 4]` (a gap between 2 and 3) are not merged.

```java
public static int[][] mergeIntervalsDifference(int[][] intervals) {
    int max = 0;
    for (int[] interval : intervals) max = Math.max(max, interval[1]);
    int[] diff = new int[2 * max + 3];
    for (int[] interval : intervals) { diff[2 * interval[0]]++; diff[2 * interval[1] + 1]--; }
    List<int[]> result = new ArrayList<>();
    int cover = 0, begin = 0;
    for (int p = 0; p < diff.length; p++) {
        int before = cover;
        cover += diff[p];
        if (before == 0 && cover > 0) begin = p / 2;
        if (before > 0 && cover == 0) result.add(new int[]{begin, (p - 1) / 2});
    }
    return result.toArray(new int[0][]);
}
```

- **Time Complexity:** O(n + M) where M is the largest coordinate
- **Space Complexity:** O(M)

### Approach 6: Overlap Graph and Connected Components

Connect every two overlapping intervals and take the connected components: each component merges into one interval from its smallest start to its largest end. Quadratic, but a good illustration of what "merge" means.

```java
public static int[][] mergeIntervalsGraph(int[][] intervals) {
    int n = intervals.length;
    boolean[] visited = new boolean[n];
    List<int[]> result = new ArrayList<>();
    for (int s = 0; s < n; s++) {
        if (visited[s]) continue;
        int start = intervals[s][0], end = intervals[s][1];
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(s);
        visited[s] = true;
        while (!stack.isEmpty()) {
            int a = stack.pop();
            start = Math.min(start, intervals[a][0]);
            end = Math.max(end, intervals[a][1]);
            for (int b = 0; b < n; b++) {
                if (!visited[b] && intervals[a][0] <= intervals[b][1] && intervals[b][0] <= intervals[a][1]) {
                    visited[b] = true;
                    stack.push(b);
                }
            }
        }
        result.add(new int[]{start, end});
    }
    result.sort(Comparator.comparingInt((int[] x) -> x[0]));
    return result.toArray(new int[0][]);
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 7: Sort + Single Sweep (Optimized)

Sort by start time and sweep once, extending the last interval of the output list while intervals overlap. Same complexity as the stack version without the stack; implemented in the repository.

```java
public static int[][] mergeIntervals(int[][] intervals) {
    if (intervals == null || intervals.length <= 1) return intervals;

    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
    List<int[]> merged = new ArrayList<>();
    int[] current = new int[]{intervals[0][0], intervals[0][1]};
    merged.add(current);

    for (int i = 1; i < intervals.length; i++) {
        if (current[1] >= intervals[i][0]) {
            current[1] = Math.max(current[1], intervals[i][1]);
        } else {
            current = new int[]{intervals[i][0], intervals[i][1]};
            merged.add(current);
        }
    }
    return merged.toArray(new int[0][]);
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n) (output) plus sort space

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (Repeated Merging) | O(n^3) in the worst case | O(n) |
| 2 | Sort + Stack | O(n log n) | O(n) |
| 3 | Separately Sorted Starts and Ends | O(n log n) | O(n) |
| 4 | Sweep Line with a TreeMap | O(n log n) | O(n) |
| 5 | Difference Array over Bounded Coordinates | O(n + M) where M is the largest coordinate | O(M) |
| 6 | Overlap Graph and Connected Components | O(n^2) | O(n) |
| 7 | Sort + Single Sweep (Optimized) | O(n log n) | O(n) (output) plus sort space |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### MergeIntervals.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeIntervals {
    public static void main(String[] args) {
        int[][] input1 = new int[][]{{1, 3}, {8, 10}, {2, 6}, {15, 18}};

        System.out.println("Input 1 : " + Arrays.deepToString(input1));
        System.out.println("Merged  : " + Arrays.deepToString(mergeIntervals(input1)));

        System.out.println();

        int[][] input2 = {{1, 4}, {4, 5}};
        System.out.println("Input 2 : " + Arrays.deepToString(input2));
        System.out.println("Merged  : " + Arrays.deepToString(mergeIntervals(input2)));
    }

    /**
     * Merges all overlapping intervals in a 2D array.
     *
     * @param intervals a 2D array where each element represents an interval {@code [start, end]}
     * @return a new 2D array containing the minimal set of non-overlapping intervals
     */
    public static int[][] mergeIntervals(int[][] intervals) {
        // Guard Clause: null, empty, or single-interval inputs require no processing
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        // Sort intervals by start time using primitive int comparator (prevents overflow issues)
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> mergedList = new ArrayList<>(intervals.length);

        // Copy the first interval to prevent mutating caller's original array elements
        int[] currentInterval = new int[]{intervals[0][0], intervals[0][1]};
        mergedList.add(currentInterval);

        for (int i = 1; i < intervals.length; i++) {
            int[] nextInterval = intervals[i];
            int currentEnd = currentInterval[1];
            int nextStart = nextInterval[0];
            int nextEnd = nextInterval[1];

            // Overlap detected: extend the end boundary of the current interval
            if (currentEnd >= nextStart) {
                currentInterval[1] = Math.max(currentEnd, nextEnd);
            } else {
                // No overlap: create a fresh interval reference and append to output list
                currentInterval = new int[]{nextStart, nextEnd};
                mergedList.add(currentInterval);
            }
        }

        return mergedList.toArray(new int[0][]);
    }
}
```
