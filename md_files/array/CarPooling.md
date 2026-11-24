# Car Pooling

A car with `capacity` empty seats drives only east. Given trips `[numPassengers, from, to]` (the passengers are picked up at `from` and dropped off at `to`), return `true` if it is possible to pick up and drop off all passengers without ever exceeding the capacity.

## Algorithm Strategy (Difference Array over the Route)

1. Create an array `change` indexed by location, long enough for the largest `to`.
2. For each trip, add `numPassengers` at `from` and subtract it at `to`.
3. Sweep the locations keeping a running total of passengers on board.
4. If the running total ever exceeds `capacity`, return `false`; otherwise `true`.

## Example walkthrough

- For trips `[[2,1,5],[3,3,7]]` and capacity 4: between locations 3 and 5 there are 2 + 3 = 5 passengers.
- Result: `false`.

## Complexity Analysis

- **Time Complexity:** `O(n + maxLocation)` — Process the trips, then sweep the route.
- **Space Complexity:** `O(maxLocation)` — The change array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Simulate Every Location

For each location count the passengers of every trip that covers it.

```java
public static boolean simulate(int[][] trips, int cap) {
    int last = 0; for (int[] t : trips) last = Math.max(last, t[2]);
    for (int loc = 0; loc <= last; loc++) { int c = 0; for (int[] t : trips) if (t[1] <= loc && loc < t[2]) c += t[0]; if (c > cap) return false; }
    return true;
}
```

- **Time Complexity:** O(n * maxLocation)
- **Space Complexity:** O(1)

### Approach 2: Sort Events and Sweep

Create pickup and drop-off events, sort them by location (drop-offs first) and sweep.

```java
public static boolean events(int[][] trips, int cap) {
    java.util.List<int[]> e = new java.util.ArrayList<>(); for (int[] t : trips) { e.add(new int[]{t[1], t[0]}); e.add(new int[]{t[2], -t[0]}); }
    e.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
    int c = 0; for (int[] x : e) { c += x[1]; if (c > cap) return false; } return true;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Difference Array (Optimized)

Record passenger changes at the endpoints and take a running sum. This is the version implemented in the repository.

```java
public static boolean diffArray(int[][] trips, int cap) {
    int last = 0; for (int[] t : trips) last = Math.max(last, t[2]);
    int[] d = new int[last + 1]; for (int[] t : trips) { d[t[1]] += t[0]; d[t[2]] -= t[0]; }
    int c = 0; for (int x : d) { c += x; if (c > cap) return false; } return true;
}
```

- **Time Complexity:** O(n + maxLocation)
- **Space Complexity:** O(maxLocation)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Simulate Every Location | O(n * maxLocation) | O(1) |
| 2 | Sort Events and Sweep | O(n log n) | O(n) |
| 3 | Difference Array (Optimized) | O(n + maxLocation) | O(maxLocation) |

## Source Code

### CarPooling.java

```java
import java.util.Arrays;

public class CarPooling {

    public static void main(String[] args) {
        int[][][] trips = {{{2, 1, 5}, {3, 3, 7}}, {{2, 1, 5}, {3, 3, 7}}, {{2, 1, 5}, {3, 5, 7}}, {{3, 2, 7}, {3, 7, 9}, {8, 3, 9}}};
        int[] capacity = {4, 5, 3, 11};
        for (int i = 0; i < trips.length; i++) {
            System.out.println("Input  : trips = " + Arrays.deepToString(trips[i]) + ", capacity = " + capacity[i]);
            System.out.println("Output : " + carPooling(trips[i], capacity[i]));
            System.out.println();
        }
    }

    public static boolean carPooling(int[][] trips, int capacity) {
        if (trips == null || trips.length == 0) {
            return true;
        }
        int last = 0;
        for (int[] trip : trips) {
            last = Math.max(last, trip[2]);
        }
        int[] change = new int[last + 1];
        for (int[] trip : trips) {
            change[trip[1]] += trip[0];
            change[trip[2]] -= trip[0];
        }
        int onBoard = 0;
        for (int delta : change) {
            onBoard += delta;
            if (onBoard > capacity) {
                return false;
            }
        }
        return true;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: only the moments when passengers get on or off matter. Writing "+n at pick-up, -n at drop-off" and taking a running sum gives the number of passengers on board at every location.

**Input:** `trips = [[2, 1, 5], [3, 3, 7]]`, `capacity = 4`.

| Location | Change | On board |
|----------|--------|----------|
| 1 | +2 | 2 |
| 3 | +3 | 5 |
| 5 | -2 | 3 |
| 7 | -3 | 0 |

The count reaches 5 at location 3, above the capacity of 4.

**Output:** `false`.
