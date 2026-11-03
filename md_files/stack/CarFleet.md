# Car Fleet

There are `n` cars heading to a destination `target` miles away on a one-lane road. Car `i` starts at `position[i]` with constant speed `speed[i]`. A car cannot pass another car; it can only catch up and then drive at the speed of the slower car in front, forming a fleet. Return the number of car fleets that arrive at the destination.

## Algorithm Strategy (Sort by Position, Compare Arrival Times)

1. Compute for each car the time it needs to reach the target if nothing blocked it: `(target - position) / speed`.
2. Process the cars from the closest to the target to the farthest (sort by position in decreasing order).
3. A car whose arrival time is not larger than the arrival time of the fleet in front of it catches up and joins that fleet.
4. A car with a larger arrival time can never catch the fleet in front, so it starts a new fleet. Count the fleets.

## Example walkthrough

- For target 12, positions `[10, 8, 0, 5, 3]`, speeds `[2, 4, 1, 1, 3]`: sorted by position the arrival times are 1, 1, 7, 3, 12.
- The cars with times 1, 1 form one fleet, 7 and 3 form another (3 <= 7), and 12 starts a third: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Dominated by sorting.
- **Space Complexity:** `O(n)` — Sorted indexes and arrival times.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort and a Stack of Arrival Times

Sort the cars by position (closest to the target first) and keep a stack of fleet arrival times: a car joins the fleet on top when it arrives no later than that fleet; otherwise it starts a new fleet.

```java
public static int carFleetStack(int target, int[] position, int[] speed) {
    int n = position.length;
    Integer[] order = new Integer[n];
    for (int i = 0; i < n; i++) order[i] = i;
    Arrays.sort(order, (a, b) -> position[b] - position[a]);
    Deque<Double> fleets = new ArrayDeque<>();
    for (int index : order) {
        double time = (double) (target - position[index]) / speed[index];
        if (fleets.isEmpty() || time > fleets.peek()) fleets.push(time);
    }
    return fleets.size();
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: TreeMap Ordered by Position

Put `position -> arrival time` in a `TreeMap` and iterate over it from the largest position down, counting the cars that arrive later than every car ahead.

```java
public static int carFleetTreeMap(int target, int[] position, int[] speed) {
    TreeMap<Integer, Double> times = new TreeMap<>(Comparator.reverseOrder());
    for (int i = 0; i < position.length; i++) times.put(position[i], (double) (target - position[i]) / speed[i]);
    int fleets = 0;
    double slowest = 0;
    for (double time : times.values()) {
        if (time > slowest) { fleets++; slowest = time; }
    }
    return fleets;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Array Indexed by Position (Small Targets)

Positions are distinct integers in `[0, target)`. When `target` is small, store each car's arrival time in an array indexed by its position and sweep from `target - 1` down to 0, which avoids sorting.

```java
public static int carFleetBuckets(int target, int[] position, int[] speed) {
    double[] time = new double[target];
    Arrays.fill(time, -1);
    for (int i = 0; i < position.length; i++) time[position[i]] = (double) (target - position[i]) / speed[i];
    int fleets = 0;
    double slowest = 0;
    for (int p = target - 1; p >= 0; p--) {
        if (time[p] > slowest) { fleets++; slowest = time[p]; }
    }
    return fleets;
}
```

- **Time Complexity:** O(n + target)
- **Space Complexity:** O(target)

### Approach 4: Sort and One Variable (Optimized)

The stack of the first approach only ever needs its top, so a single variable `slowestAhead` is enough. This is the version implemented in the repository.

```java
public static int carFleet(int target, int[] position, int[] speed) {
    int n = position.length;
    Integer[] order = new Integer[n];
    for (int i = 0; i < n; i++) order[i] = i;
    Arrays.sort(order, (a, b) -> position[b] - position[a]);
    int fleets = 0;
    double slowestAhead = 0;
    for (int index : order) {
        double time = (double) (target - position[index]) / speed[index];
        if (time > slowestAhead) { fleets++; slowestAhead = time; }
    }
    return fleets;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort and a Stack of Arrival Times | O(n log n) | O(n) |
| 2 | TreeMap Ordered by Position | O(n log n) | O(n) |
| 3 | Array Indexed by Position (Small Targets) | O(n + target) | O(target) |
| 4 | Sort and One Variable (Optimized) | O(n log n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### CarFleet.java

```java
import java.util.Arrays;

public class CarFleet {

    public static void main(String[] args) {
        int[] targets = {12, 10, 100, 10};
        int[][] positions = {{10, 8, 0, 5, 3}, {3}, {0, 2, 4}, {6, 8}};
        int[][] speeds = {{2, 4, 1, 1, 3}, {3}, {4, 2, 1}, {3, 2}};
        for (int i = 0; i < targets.length; i++) {
            System.out.println("Input  : target = " + targets[i] + ", position = " + Arrays.toString(positions[i]) + ", speed = " + Arrays.toString(speeds[i]));
            System.out.println("Output : " + carFleet(targets[i], positions[i], speeds[i]));
            System.out.println();
        }
    }

    public static int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        Arrays.sort(order, (a, b) -> position[b] - position[a]);        // closest to the target first

        int fleets = 0;
        double slowestAhead = 0;
        for (int index : order) {
            double time = (double) (target - position[index]) / speed[index];
            if (time > slowestAhead) {          // cannot catch the fleet in front: a new fleet
                fleets++;
                slowestAhead = time;
            }
        }
        return fleets;
    }
}
```
