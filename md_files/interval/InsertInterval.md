# Insert Interval

Given non-overlapping intervals sorted by start and a new interval, insert the new interval so the list stays sorted and non-overlapping, merging overlapping intervals if necessary.

## Algorithm Strategy (Three Phases in One Pass)

1. Phase 1: copy every interval that ends before the new interval starts (`end < newStart`); they cannot overlap it.
2. Phase 2: while an interval starts before or at the new end (`start <= newEnd`), it overlaps: widen the new interval to `[min(starts), max(ends)]`. Then add the merged interval.
3. Phase 3: copy the remaining intervals, which start after the merged interval.
4. Because the input is already sorted, a single pass suffices.

## Example walkthrough

- For `[[1,3],[6,9]]` and new `[2,5]`: `[1,3]` overlaps (2 <= 3), giving `[1,5]`; `[6,9]` starts after 5.
- Result: `[[1,5],[6,9]]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over the intervals.
- **Space Complexity:** `O(n)` — The output list.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Append, Sort and Merge

Add the new interval to the list, sort by start and run the ordinary merge-intervals sweep. Ignores that the input is already sorted.

```java
public static int[][] insertSortMerge(int[][] intervals, int[] newInterval) {
    List<int[]> all = new ArrayList<>(Arrays.asList(intervals));
    all.add(newInterval);
    all.sort(Comparator.comparingInt(a -> a[0]));
    List<int[]> merged = new ArrayList<>();
    for (int[] interval : all) {
        if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < interval[0]) {
            merged.add(new int[]{interval[0], interval[1]});
        } else {
            int[] last = merged.get(merged.size() - 1);
            last[1] = Math.max(last[1], interval[1]);
        }
    }
    return merged.toArray(new int[0][]);
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Binary Search for the Boundaries

Because the intervals are sorted, binary-search the first interval that overlaps the new one and the first one that starts after it, then copy the untouched ranges in bulk. The search is `O(log n)`, though copying the output is still `O(n)`.

```java
public static int[][] insertBinary(int[][] intervals, int[] newInterval) {
    int n = intervals.length;
    int first = 0, high = n;                       // first interval with end >= newStart
    while (first < high) {
        int mid = (first + high) / 2;
        if (intervals[mid][1] < newInterval[0]) first = mid + 1; else high = mid;
    }
    int last = first;
    high = n;                                      // first interval with start > newEnd
    while (last < high) {
        int mid = (last + high) / 2;
        if (intervals[mid][0] <= newInterval[1]) last = mid + 1; else high = mid;
    }
    int start = newInterval[0], end = newInterval[1];
    if (first < last) {
        start = Math.min(start, intervals[first][0]);
        end = Math.max(end, intervals[last - 1][1]);
    }
    List<int[]> result = new ArrayList<>(Arrays.asList(intervals).subList(0, first));
    result.add(new int[]{start, end});
    result.addAll(Arrays.asList(intervals).subList(last, n));
    return result.toArray(new int[0][]);
}
```

- **Time Complexity:** O(log n) search + O(n) copy
- **Space Complexity:** O(n)

### Approach 3: Single Loop with an "Inserted" Flag

Walk through the intervals once, merging every overlapping interval into the new one on the fly and emitting the new interval exactly when it is passed; a boolean remembers whether it has been emitted.

```java
public static int[][] insertFlag(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    int start = newInterval[0], end = newInterval[1];
    boolean inserted = false;
    for (int[] interval : intervals) {
        if (interval[1] < start) {                          // completely before the new interval
            result.add(interval);
        } else if (interval[0] > end) {                     // completely after: emit the new one first
            if (!inserted) { result.add(new int[]{start, end}); inserted = true; }
            result.add(interval);
        } else {                                            // overlap: absorb into the new interval
            start = Math.min(start, interval[0]);
            end = Math.max(end, interval[1]);
        }
    }
    if (!inserted) result.add(new int[]{start, end});
    return result.toArray(new int[0][]);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Recursion

Define the result recursively: the first interval is either entirely before the new interval (keep it and recurse), entirely after it (emit the new interval, then everything else), or overlaps (merge into the new interval and recurse).

```java
public static int[][] insertRecursive(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    build(intervals, 0, newInterval[0], newInterval[1], result);
    return result.toArray(new int[0][]);
}

private static void build(int[][] intervals, int index, int start, int end, List<int[]> result) {
    if (index == intervals.length) { result.add(new int[]{start, end}); return; }
    int[] current = intervals[index];
    if (current[1] < start) {
        result.add(current);
        build(intervals, index + 1, start, end, result);
    } else if (current[0] > end) {
        result.add(new int[]{start, end});
        for (int i = index; i < intervals.length; i++) result.add(intervals[i]);
    } else {
        build(intervals, index + 1, Math.min(start, current[0]), Math.max(end, current[1]), result);
    }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n) recursion stack

### Approach 5: TreeMap of Disjoint Intervals

Keep the intervals in a `TreeMap` keyed by start. To insert, repeatedly find a stored interval that overlaps the new one (the floor entry or the next entry), remove it and widen the new interval; then store the result. This also supports inserting many intervals one after another.

```java
public static int[][] insertTreeMap(int[][] intervals, int[] newInterval) {
    TreeMap<Integer, Integer> map = new TreeMap<>();
    for (int[] interval : intervals) map.put(interval[0], interval[1]);
    int start = newInterval[0], end = newInterval[1];
    Map.Entry<Integer, Integer> floor = map.floorEntry(start);
    if (floor != null && floor.getValue() >= start) {                 // overlaps from the left
        start = floor.getKey();
        end = Math.max(end, floor.getValue());
        map.remove(floor.getKey());
    }
    Map.Entry<Integer, Integer> next;
    while ((next = map.ceilingEntry(start)) != null && next.getKey() <= end) {
        end = Math.max(end, next.getValue());
        map.remove(next.getKey());
    }
    map.put(start, end);
    int[][] result = new int[map.size()][];
    int i = 0;
    for (Map.Entry<Integer, Integer> e : map.entrySet()) result[i++] = new int[]{e.getKey(), e.getValue()};
    return result;
}
```

- **Time Complexity:** O(n) to build, O(log n) per insert
- **Space Complexity:** O(n)

### Approach 6: Insert in Sorted Position, Then Merge with a Stack

Binary-search the position where the new interval belongs by start time, insert it there (the list stays sorted) and run the usual stack-based merge over the list.

```java
public static int[][] insertThenMerge(int[][] intervals, int[] newInterval) {
    int low = 0, high = intervals.length;
    while (low < high) {                                    // first interval starting after the new start
        int mid = (low + high) / 2;
        if (intervals[mid][0] <= newInterval[0]) low = mid + 1; else high = mid;
    }
    List<int[]> all = new ArrayList<>(Arrays.asList(intervals));
    all.add(low, newInterval);
    Deque<int[]> stack = new ArrayDeque<>();
    for (int[] interval : all) {
        if (!stack.isEmpty() && stack.peek()[1] >= interval[0]) {
            stack.peek()[1] = Math.max(stack.peek()[1], interval[1]);
        } else {
            stack.push(new int[]{interval[0], interval[1]});
        }
    }
    int[][] result = new int[stack.size()][];
    for (int i = result.length - 1; i >= 0; i--) result[i] = stack.pop();
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 7: Three Phases in One Pass (Optimized)

Copy the intervals that end before the new one, merge the overlapping ones into it, then copy the rest. This is the version implemented in the repository.

```java
public static int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    int i = 0, n = intervals.length;
    while (i < n && intervals[i][1] < newInterval[0]) result.add(intervals[i++]);
    int start = newInterval[0], end = newInterval[1];
    while (i < n && intervals[i][0] <= end) {
        start = Math.min(start, intervals[i][0]);
        end = Math.max(end, intervals[i][1]);
        i++;
    }
    result.add(new int[]{start, end});
    while (i < n) result.add(intervals[i++]);
    return result.toArray(new int[0][]);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Append, Sort and Merge | O(n log n) | O(n) |
| 2 | Binary Search for the Boundaries | O(log n) search + O(n) copy | O(n) |
| 3 | Single Loop with an "Inserted" Flag | O(n) | O(n) |
| 4 | Recursion | O(n) | O(n) recursion stack |
| 5 | TreeMap of Disjoint Intervals | O(n) to build, O(log n) per insert | O(n) |
| 6 | Insert in Sorted Position, Then Merge with a Stack | O(n) | O(n) |
| 7 | Three Phases in One Pass (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### InsertInterval.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertInterval {

    public static void main(String[] args) {
        int[][][] intervals = {{{1, 3}, {6, 9}}, {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}}, {}};
        int[][] newIntervals = {{2, 5}, {4, 8}, {5, 7}};
        for (int i = 0; i < intervals.length; i++) {
            System.out.println("Input  : intervals = " + Arrays.deepToString(intervals[i]) + ", newInterval = " + Arrays.toString(newIntervals[i]));
            System.out.println("Output : " + Arrays.deepToString(insert(intervals[i], newIntervals[i])));
            System.out.println();
        }
    }

    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        int n = intervals.length;

        // 1. Intervals that end before the new interval starts
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i++]);
        }

        // 2. Intervals that overlap the new interval are merged into it
        int start = newInterval[0];
        int end = newInterval[1];
        while (i < n && intervals[i][0] <= end) {
            start = Math.min(start, intervals[i][0]);
            end = Math.max(end, intervals[i][1]);
            i++;
        }
        result.add(new int[]{start, end});

        // 3. Intervals that start after the merged interval
        while (i < n) {
            result.add(intervals[i++]);
        }
        return result.toArray(new int[0][]);
    }
}
```
