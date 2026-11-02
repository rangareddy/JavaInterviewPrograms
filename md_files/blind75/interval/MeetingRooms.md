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
