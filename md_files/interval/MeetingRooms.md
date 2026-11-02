# Meeting Rooms

Given an array of meeting time intervals `[start, end]`, determine whether a person can attend all the meetings (no two meetings overlap).

## Algorithm Strategy (Sort by Start Time)

1. Sort the meetings by start time.
2. Compare every meeting with the previous one: if it starts before the previous meeting ends (`start < previousEnd`), they overlap and the person cannot attend both.
3. If no pair of neighboring meetings overlaps, all meetings can be attended. (Meetings that end exactly when the next one starts are fine.)

## Example walkthrough

- For `[[0,30],[5,10],[15,20]]`: sorted order is the same; 5 < 30 means the first two meetings overlap, so the result is `false`.
- For `[[7,10],[2,4]]` sorted `[[2,4],[7,10]]`: 7 >= 4, so `true`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Dominated by sorting.
- **Space Complexity:** `O(1)` — Besides the sort.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force (Compare Every Pair)

Two meetings conflict if each starts before the other ends. Check all pairs.

```java
public static boolean canAttendMeetingsBrute(int[][] intervals) {
    for (int i = 0; i < intervals.length; i++) {
        for (int j = i + 1; j < intervals.length; j++) {
            if (intervals[i][0] < intervals[j][1] && intervals[j][0] < intervals[i][1]) return false;
        }
    }
    return true;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Online Check with a TreeMap

Insert the meetings one by one into a `TreeMap` keyed by start time. A new meeting conflicts only with the closest earlier meeting (if it ends after the new start) or the closest later meeting (if it starts before the new end). Works when meetings arrive one at a time.

```java
public static boolean canAttendMeetingsTreeMap(int[][] intervals) {
    TreeMap<Integer, Integer> booked = new TreeMap<>();      // start -> end
    for (int[] interval : intervals) {
        Map.Entry<Integer, Integer> before = booked.floorEntry(interval[0]);
        Map.Entry<Integer, Integer> after = booked.ceilingEntry(interval[0]);
        if (before != null && before.getValue() > interval[0]) return false;
        if (after != null && after.getKey() < interval[1]) return false;
        booked.put(interval[0], interval[1]);
    }
    return true;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Difference Array over a Bounded Time Range

If the times are small non-negative integers (for example minutes of a day), mark `+1` at every start and `-1` at every end in an array and take prefix sums; a value above 1 means two meetings overlap. Linear time, but needs memory proportional to the largest time.

```java
public static boolean canAttendMeetingsDifference(int[][] intervals) {
    int maxTime = 0;
    for (int[] interval : intervals) maxTime = Math.max(maxTime, interval[1]);
    int[] diff = new int[maxTime + 2];
    for (int[] interval : intervals) {
        diff[interval[0]]++;
        diff[interval[1]]--;
    }
    int running = 0;
    for (int change : diff) {
        running += change;
        if (running > 1) return false;
    }
    return true;
}
```

- **Time Complexity:** O(n + T) where T is the largest time
- **Space Complexity:** O(T)

### Approach 4: Sweep Line over Events

Turn every meeting into a +1 event at its start and a -1 event at its end and process the events in time order. If more than one meeting is ever running at the same time, the person cannot attend all of them. Events at the same time are summed first, so a meeting ending when the next one starts is fine.

```java
public static boolean canAttendMeetingsSweep(int[][] intervals) {
    TreeMap<Integer, Integer> delta = new TreeMap<>();
    for (int[] interval : intervals) {
        delta.merge(interval[0], 1, Integer::sum);
        delta.merge(interval[1], -1, Integer::sum);
    }
    int running = 0;
    for (int change : delta.values()) {
        running += change;
        if (running > 1) return false;
    }
    return true;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 5: Sort and Compare Neighbours (Optimized)

After sorting by start time only neighbouring meetings can overlap. This is the version implemented in the repository.

```java
public static boolean canAttendMeetings(int[][] intervals) {
    int[][] sorted = intervals.clone();
    Arrays.sort(sorted, Comparator.comparingInt((int[] a) -> a[0]));
    for (int i = 1; i < sorted.length; i++) {
        if (sorted[i][0] < sorted[i - 1][1]) return false;
    }
    return true;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1) besides the sort

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (Compare Every Pair) | O(n^2) | O(1) |
| 2 | Online Check with a TreeMap | O(n log n) | O(n) |
| 3 | Difference Array over a Bounded Time Range | O(n + T) where T is the largest time | O(T) |
| 4 | Sweep Line over Events | O(n log n) | O(n) |
| 5 | Sort and Compare Neighbours (Optimized) | O(n log n) | O(1) besides the sort |

### Optimized Solution

The optimized solution is Approach 5 above, which is also the version implemented in this repository.

## Source Code

### MeetingRooms.java

```java
import java.util.Arrays;
import java.util.Comparator;

public class MeetingRooms {

    public static void main(String[] args) {
        int[][][] inputs = {{{0, 30}, {5, 10}, {15, 20}}, {{7, 10}, {2, 4}}, {{1, 5}, {5, 8}}};
        for (int[][] intervals : inputs) {
            System.out.println("Input  : intervals = " + Arrays.deepToString(intervals));
            System.out.println("Output : " + canAttendMeetings(intervals));
            System.out.println();
        }
    }

    public static boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length < 2) {
            return true;
        }

        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, Comparator.comparingInt((int[] interval) -> interval[0]));
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i][0] < sorted[i - 1][1]) {
                return false;
            }
        }
        return true;
    }
}
```
