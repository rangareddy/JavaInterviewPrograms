# Employee Free Time

Each employee has a schedule, a sorted list of non-overlapping busy intervals `[start, end]`. Return the finite intervals during which all employees are free, in sorted order (do not include the infinite time before the first and after the last busy interval).

## Algorithm Strategy (Merge All Busy Intervals, Report the Gaps)

1. Flatten all employees' intervals into one list and sort it by start time.
2. Sweep the list keeping the furthest `end` seen so far.
3. If the next interval starts after that `end`, the gap `[end, start]` is free time for everyone: record it.
4. Update `end = max(end, interval.end)` and continue.

## Example walkthrough

- For the schedules `[[1,2],[5,6]]`, `[[1,3]]` and `[[4,10]]`: merging gives busy time `[1, 3]` and `[4, 10]`.
- The free time between them is `[3, 4]`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Sorting all intervals.
- **Space Complexity:** `O(n)` — The flat list.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Mark Every Busy Time Point

Mark all busy unit times in an array and read off the free stretches between busy ones. Only practical for small times.

```java
public static int[][] marks(int[][][] sched) {
    int max = 0; for (int[][] e : sched) for (int[] i : e) max = Math.max(max, i[1]); boolean[] busy = new boolean[max + 1];
    for (int[][] e : sched) for (int[] i : e) for (int t = i[0]; t < i[1]; t++) busy[t] = true;
    java.util.List<int[]> out = new java.util.ArrayList<>(); int first = -1, last = -1; for (int t = 0; t < max; t++) if (busy[t]) { if (first < 0) first = t; last = t; }
    if (first < 0) return new int[0][]; int t = first; while (t <= last) { if (!busy[t]) { int s = t; while (!busy[t]) t++; out.add(new int[]{s, t}); } else t++; }
    return out.toArray(new int[0][]);
}
```

- **Time Complexity:** O(maxTime)
- **Space Complexity:** O(maxTime)

### Approach 2: Sort All Intervals and Sweep (Optimized)

Sort the flat list by start and report the gaps beyond the furthest end. This is the version implemented in the repository.

```java
public static int[][] sweep(int[][][] sched) {
    java.util.List<int[]> all = new java.util.ArrayList<>(); for (int[][] e : sched) all.addAll(java.util.Arrays.asList(e)); if (all.isEmpty()) return new int[0][];
    all.sort((a, b) -> a[0] - b[0]); java.util.List<int[]> free = new java.util.ArrayList<>(); int end = all.get(0)[1];
    for (int[] i : all) { if (i[0] > end) free.add(new int[]{end, i[0]}); end = Math.max(end, i[1]); }
    return free.toArray(new int[0][]);
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Mark Every Busy Time Point | O(maxTime) | O(maxTime) |
| 2 | Sort All Intervals and Sweep (Optimized) | O(n log n) | O(n) |

## Source Code

### EmployeeFreeTime.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeeFreeTime {

    public static void main(String[] args) {
        int[][][][] inputs = {
            {{{1, 2}, {5, 6}}, {{1, 3}}, {{4, 10}}},
            {{{1, 3}, {6, 7}}, {{2, 4}}, {{2, 5}, {9, 12}}},
            {{{1, 5}}, {{2, 3}}}
        };
        for (int[][][] schedule : inputs) {
            System.out.println("Input  : schedule = " + Arrays.deepToString(schedule));
            System.out.println("Output : " + Arrays.deepToString(employeeFreeTime(schedule)));
            System.out.println();
        }
    }

    public static int[][] employeeFreeTime(int[][][] schedule) {
        List<int[]> all = new ArrayList<>();
        if (schedule != null) {
            for (int[][] employee : schedule) {
                all.addAll(Arrays.asList(employee));
            }
        }
        if (all.isEmpty()) {
            return new int[0][];
        }
        all.sort((a, b) -> a[0] - b[0]);
        List<int[]> free = new ArrayList<>();
        int end = all.get(0)[1];
        for (int[] interval : all) {
            if (interval[0] > end) {
                free.add(new int[]{end, interval[0]});
            }
            end = Math.max(end, interval[1]);
        }
        return free.toArray(new int[0][]);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: everyone is free exactly when no employee is busy. Merging every busy interval into one stream sorted by start time turns free time into the gaps in that stream.

**Input:** `[[[1, 2], [5, 6]], [[1, 3]], [[4, 10]]]`.

| Sorted interval | Current furthest end | Gap found |
|-----------------|----------------------|-----------|
| [1, 2] | 2 | |
| [1, 3] | 3 | |
| [4, 10] | start 4 > end 3 | [3, 4] |
| [5, 6] | 10 | |

**Output:** `[[3, 4]]`.
