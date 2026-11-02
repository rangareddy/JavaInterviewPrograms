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
