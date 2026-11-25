# Maximum Number of Events That Can Be Attended

Given events `[startDay, endDay]`, you can attend an event on any single day from its start to its end (inclusive), and only one event per day. Return the maximum number of events you can attend.

## Algorithm Strategy (Earliest Deadline First)

1. Sort the events by start day.
2. Sweep the days from the earliest start. On each day, add the end days of all events that start today to a min-heap.
3. Remove events from the heap whose end day has already passed.
4. If the heap is not empty, attend the event with the earliest end day (pop it) and count it; move on to the next day.

## Example walkthrough

- For `[[1,2],[2,3],[3,4]]`: attend them on days 1, 2 and 3.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n log n + D)` — Sorting plus heap operations; `D` is the number of days swept.
- **Space Complexity:** `O(n)` — The heap.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Greedy by Earliest End Day without a Heap

Sort by end day and give each event the earliest free day in its range, tracked in a boolean array.

```java
public static int byEnd(int[][] in) {
    int[][] e = new int[in.length][]; for (int i = 0; i < e.length; i++) e[i] = in[i].clone(); java.util.Arrays.sort(e, (a, b) -> a[1] != b[1] ? a[1] - b[1] : a[0] - b[0]);
    java.util.Set<Integer> used = new java.util.HashSet<>(); int c = 0;
    for (int[] x : e) for (int d = x[0]; d <= x[1]; d++) if (used.add(d)) { c++; break; }
    return c;
}
```

- **Time Complexity:** O(n * D)
- **Space Complexity:** O(D)

### Approach 2: Sweep the Days with a Min-Heap (Optimized)

On each day attend the available event that ends first. This is the version implemented in the repository.

```java
public static int sweep(int[][] in) {
    int[][] e = new int[in.length][]; for (int i = 0; i < e.length; i++) e[i] = in[i].clone(); java.util.Arrays.sort(e, (a, b) -> a[0] - b[0]);
    java.util.PriorityQueue<Integer> h = new java.util.PriorityQueue<>(); int c = 0, i = 0, day = 0;
    while (i < e.length || !h.isEmpty()) { if (h.isEmpty()) day = e[i][0]; while (i < e.length && e[i][0] <= day) h.add(e[i++][1]); while (!h.isEmpty() && h.peek() < day) h.poll(); if (!h.isEmpty()) { h.poll(); c++; } day++; }
    return c;
}
```

- **Time Complexity:** O(n log n + D)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Greedy by Earliest End Day without a Heap | O(n * D) | O(D) |
| 2 | Sweep the Days with a Min-Heap (Optimized) | O(n log n + D) | O(n) |

## Source Code

### MaximumNumberOfEventsThatCanBeAttended.java

```java
import java.util.Arrays;
import java.util.PriorityQueue;

public class MaximumNumberOfEventsThatCanBeAttended {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 2}, {2, 3}, {3, 4}}, {{1, 2}, {2, 3}, {3, 4}, {1, 2}}, {{1, 4}, {4, 4}, {2, 2}, {3, 4}, {1, 1}}, {{1, 1}}};
        for (int[][] events : inputs) {
            System.out.println("Input  : events = " + Arrays.deepToString(events));
            System.out.println("Output : " + maxEvents(events.clone()));
            System.out.println();
        }
    }

    public static int maxEvents(int[][] events) {
        if (events == null || events.length == 0) {
            return 0;
        }
        Arrays.sort(events, (a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> ends = new PriorityQueue<>();
        int attended = 0;
        int i = 0;
        int day = 0;
        while (i < events.length || !ends.isEmpty()) {
            if (ends.isEmpty()) {
                day = events[i][0];
            }
            while (i < events.length && events[i][0] <= day) {
                ends.add(events[i++][1]);
            }
            while (!ends.isEmpty() && ends.peek() < day) {
                ends.poll();
            }
            if (!ends.isEmpty()) {
                ends.poll();
                attended++;
            }
            day++;
        }
        return attended;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: on any day, among the events you could attend, the one whose deadline is closest is the most urgent. Attending it first never costs you another event.

**Input:** `events = [[1, 2], [2, 3], [3, 4], [1, 2]]`.

| Day | Heap of end days after adding | Attend | Count |
|-----|-------------------------------|--------|-------|
| 1 | [2, 2] | end 2 | 1 |
| 2 | [2, 3] | end 2 | 2 |
| 3 | [3, 4] | end 3 | 3 |
| 4 | [4] | end 4 | 4 |

**Output for this input:** `4`. For the first sample in `main`, `[[1, 2], [2, 3], [3, 4]]`, the output is `3`.
