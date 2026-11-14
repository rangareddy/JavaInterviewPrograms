# Meeting Rooms II

Given an array of meeting time intervals `[start, end]`, find the minimum number of conference rooms required.

## Algorithm Strategy (Sorted Start and End Times)

1. Put all start times in one array and all end times in another, and sort both. The pairing of start and end does not matter: only how many meetings run at the same time.
2. Walk through the start times in order with a pointer `end` into the sorted end times.
3. If a meeting starts before the earliest unfinished meeting ends (`start < ends[end]`), it needs a new room: increment `rooms`.
4. Otherwise a room has just been freed and is reused: advance the `end` pointer. The final `rooms` is the maximum number of simultaneous meetings.

## Example walkthrough

- For `[[0,30],[5,10],[15,20]]`: starts `[0,5,15]`, ends `[10,20,30]`.
- 0 < 10 and 5 < 10 open two rooms; 15 >= 10 reuses the first room. Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Dominated by sorting.
- **Space Complexity:** `O(n)` — The two sorted arrays.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force (Count Overlaps at Every Start)

The number of rooms needed is the largest number of meetings running at the same moment, and that maximum is reached at some meeting start. For each start time count the meetings that are running.

```java
public static int minMeetingRoomsBrute(int[][] intervals) {
    int rooms = 0;
    for (int[] a : intervals) {
        int running = 0;
        for (int[] b : intervals) {
            if (b[0] <= a[0] && a[0] < b[1]) running++;
        }
        rooms = Math.max(rooms, running);
    }
    return rooms;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Min-Heap of End Times

Sort by start time and keep the end times of the meetings in progress in a min-heap. Before adding a meeting, free the room of the earliest finishing meeting if it has already ended; the heap size at the end of the process is the number of rooms.

```java
public static int minMeetingRoomsHeap(int[][] intervals) {
    int[][] sorted = intervals.clone();
    Arrays.sort(sorted, Comparator.comparingInt((int[] a) -> a[0]));
    PriorityQueue<Integer> ends = new PriorityQueue<>();
    for (int[] interval : sorted) {
        if (!ends.isEmpty() && ends.peek() <= interval[0]) ends.poll();    // reuse that room
        ends.add(interval[1]);
    }
    return ends.size();
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Sorted Starts and Ends with Two Pointers (Optimized)

Sort start times and end times independently and sweep: a start before the earliest unfinished end needs a new room, otherwise a room is reused. This is the version implemented in the repository.

```java
public static int minMeetingRooms(int[][] intervals) {
    int n = intervals.length;
    int[] starts = new int[n], ends = new int[n];
    for (int i = 0; i < n; i++) { starts[i] = intervals[i][0]; ends[i] = intervals[i][1]; }
    Arrays.sort(starts);
    Arrays.sort(ends);
    int rooms = 0, end = 0;
    for (int start : starts) {
        if (start < ends[end]) rooms++;
        else end++;
    }
    return rooms;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 4: Sweep Line over Events

Turn every meeting into a +1 event at its start and a -1 event at its end, process the events in time order (ends before starts at the same time) and track the maximum running total.

```java
public static int minMeetingRoomsSweep(int[][] intervals) {
    TreeMap<Integer, Integer> delta = new TreeMap<>();
    for (int[] interval : intervals) {
        delta.merge(interval[0], 1, Integer::sum);
        delta.merge(interval[1], -1, Integer::sum);
    }
    int running = 0, best = 0;
    for (int change : delta.values()) {
        running += change;
        best = Math.max(best, running);
    }
    return best;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (Count Overlaps at Every Start) | O(n^2) | O(1) |
| 2 | Min-Heap of End Times | O(n log n) | O(n) |
| 3 | Sorted Starts and Ends with Two Pointers (Optimized) | O(n log n) | O(n) |
| 4 | Sweep Line over Events | O(n log n) | O(n) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: the number of rooms needed is the largest number of meetings running at the same moment. Which end belongs to which start does not matter for that count, so starts and ends are sorted separately. Walking through the starts in order, a pointer into the sorted ends points at the earliest meeting that is still running: if a meeting starts before that end, every room is busy and a new one is opened; otherwise that earliest meeting has already finished, so its room is reused and the end pointer moves on.

**Input:** `[[0, 30], [5, 10], [15, 20]]`, giving sorted `starts = [0, 5, 15]` and sorted `ends = [10, 20, 30]`.

| `start` | `end` pointer -> `ends[end]` | Check `start < ends[end]` | Action | `rooms` after |
|---------|------------------------------|---------------------------|--------|---------------|
| 0 | 0 -> 10 | `0 < 10` true | open a new room | 1 |
| 5 | 0 -> 10 | `5 < 10` true | open a new room | 2 |
| 15 | 0 -> 10 | `15 < 10` false | reuse: `end` becomes 1 | 2 |

```text
time:  0     5    10    15   20                30
       [0 -------------------------------------30]   room A
             [5---10]                                  room B
                       [15--20]                         reuses room B (free since 10)
at most 2 meetings overlap at any time -> 2 rooms
```

All starts are consumed, so the result is `2`. For `[[1,5],[2,6],[3,7],[4,8]]` the starts `[1,2,3,4]` are all below `ends[0] = 5`, so a new room is opened each time and the result is `4`. For `[[7,10],[2,4]]` the starts are `[2,7]` and ends `[4,10]`: `2 < 4` opens room 1, `7 < 4` is false and reuses it, so the result is `1`.

**Edge cases.** `null` or an empty array returns `0`. The `end` pointer never runs past the array: the smallest start is always below the smallest end, so the first meeting always opens a room, and `end` advances at most once per remaining start. A meeting starting exactly when another ends reuses the room (`start < ends[end]` uses a strict `<`).

**Result:** `2`, `1`, `4` for the three inputs in `main`, matching the class output.

## Source Code

### MeetingRoomsII.java

```java
import java.util.Arrays;

public class MeetingRoomsII {

    public static void main(String[] args) {
        int[][][] inputs = {{{0, 30}, {5, 10}, {15, 20}}, {{7, 10}, {2, 4}}, {{1, 5}, {2, 6}, {3, 7}, {4, 8}}};
        for (int[][] intervals : inputs) {
            System.out.println("Input  : intervals = " + Arrays.deepToString(intervals));
            System.out.println("Output : " + minMeetingRooms(intervals));
            System.out.println();
        }
    }

    public static int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        int n = intervals.length;
        int[] starts = new int[n];
        int[] ends = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }
        Arrays.sort(starts);
        Arrays.sort(ends);

        int rooms = 0;
        int end = 0;
        for (int start : starts) {
            if (start < ends[end]) {
                rooms++;        // every room is busy: open a new one
            } else {
                end++;          // a meeting has ended: reuse its room
            }
        }
        return rooms;
    }
}
```
