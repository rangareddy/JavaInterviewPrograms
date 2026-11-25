# Bus Routes

Given `routes`, where `routes[i]` is the list of stops that bus `i` cycles through forever, you start at stop `source` and want to reach `target`, travelling only by bus. Return the least number of buses you must take, or `-1` if it is impossible.

## Algorithm Strategy (BFS over Buses)

1. Build a map from each stop to the buses that serve it.
2. BFS where a node is a bus: start with all buses at `source` (count 1) and track visited buses and visited stops.
3. When processing a bus, look at all its stops; if one is `target`, return the number of buses used.
4. Otherwise enqueue every unvisited bus serving those stops with count + 1.

## Example walkthrough

- For `routes = [[1,2,7],[3,6,7]]`, `source = 1`, `target = 6`: take bus 0 to stop 7, then bus 1 to stop 6.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(total stops)` — Each stop and each bus is processed once.
- **Space Complexity:** `O(total stops)` — The stop-to-buses map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BFS over Stops

BFS where each node is a stop; every move uses a bus, so a route change costs one. Counts correctly only if you do not re-board the same bus.

```java
public static int stops(int[][] routes, int s, int t) {
    if (s == t) return 0; java.util.Map<Integer, java.util.List<Integer>> at = new java.util.HashMap<>();
    for (int b = 0; b < routes.length; b++) for (int x : routes[b]) at.computeIfAbsent(x, k -> new java.util.ArrayList<>()).add(b);
    java.util.Queue<Integer> q = new java.util.ArrayDeque<>(); java.util.Set<Integer> seen = new java.util.HashSet<>(), used = new java.util.HashSet<>(); q.add(s); seen.add(s); int buses = 0;
    while (!q.isEmpty()) { buses++; for (int z = q.size(); z > 0; z--) { int stop = q.poll(); for (int b : at.getOrDefault(stop, new java.util.ArrayList<>())) { if (!used.add(b)) continue; for (int x : routes[b]) { if (x == t) return buses; if (seen.add(x)) q.add(x); } } } }
    return -1;
}
```

- **Time Complexity:** O(total stops)
- **Space Complexity:** O(total stops)

### Approach 2: BFS over Buses (Optimized)

Treat each bus as a node and move between buses that share a stop. This is the version implemented in the repository.

```java
public static int buses(int[][] routes, int s, int t) {
    if (s == t) return 0; java.util.Map<Integer, java.util.List<Integer>> at = new java.util.HashMap<>();
    for (int b = 0; b < routes.length; b++) for (int x : routes[b]) at.computeIfAbsent(x, k -> new java.util.ArrayList<>()).add(b);
    java.util.Queue<Integer> q = new java.util.ArrayDeque<>(); java.util.Set<Integer> used = new java.util.HashSet<>(), seen = new java.util.HashSet<>(); seen.add(s);
    for (int b : at.getOrDefault(s, new java.util.ArrayList<>())) { q.add(b); used.add(b); } int taken = 1;
    while (!q.isEmpty()) { for (int z = q.size(); z > 0; z--) { int b = q.poll(); for (int x : routes[b]) { if (x == t) return taken; if (seen.add(x)) for (int nb : at.getOrDefault(x, new java.util.ArrayList<>())) if (used.add(nb)) q.add(nb); } } taken++; }
    return -1;
}
```

- **Time Complexity:** O(total stops)
- **Space Complexity:** O(total stops)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | BFS over Stops | O(total stops) | O(total stops) |
| 2 | BFS over Buses (Optimized) | O(total stops) | O(total stops) |

## Source Code

### BusRoutes.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;

public class BusRoutes {

    public static void main(String[] args) {
        int[][][] routes = {{{1, 2, 7}, {3, 6, 7}}, {{7, 12}, {4, 5, 15}, {6}, {15, 19}, {9, 12, 13}}, {{1, 2}}, {{1, 2, 7}, {3, 6, 7}}};
        int[] sources = {1, 15, 1, 6};
        int[] targets = {6, 12, 2, 6};
        for (int i = 0; i < routes.length; i++) {
            System.out.println("Input  : routes = " + Arrays.deepToString(routes[i]) + ", source = " + sources[i] + ", target = " + targets[i]);
            System.out.println("Output : " + numBusesToDestination(routes[i], sources[i], targets[i]));
            System.out.println();
        }
    }

    public static int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) {
            return 0;
        }
        if (routes == null) {
            return -1;
        }
        Map<Integer, List<Integer>> busesAt = new HashMap<>();
        for (int bus = 0; bus < routes.length; bus++) {
            for (int stop : routes[bus]) {
                busesAt.computeIfAbsent(stop, k -> new ArrayList<>()).add(bus);
            }
        }
        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> usedBuses = new HashSet<>();
        Set<Integer> seenStops = new HashSet<>();
        seenStops.add(source);
        for (int bus : busesAt.getOrDefault(source, new ArrayList<>())) {
            queue.add(bus);
            usedBuses.add(bus);
        }
        int taken = 1;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                int bus = queue.poll();
                for (int stop : routes[bus]) {
                    if (stop == target) {
                        return taken;
                    }
                    if (seenStops.add(stop)) {
                        for (int next : busesAt.getOrDefault(stop, new ArrayList<>())) {
                            if (usedBuses.add(next)) {
                                queue.add(next);
                            }
                        }
                    }
                }
            }
            taken++;
        }
        return -1;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the number of stops can be huge, but the cost is counted in buses. Treating each bus as a node and moving to the buses that share a stop explores each route once.

**Input:** `routes = [[1, 2, 7], [3, 6, 7]]`, `source = 1`, `target = 6`.

| Level | Buses in the queue | Stops visited | Result |
|-------|--------------------|---------------|--------|
| 1 | bus 0 (serves stop 1) | 1, 2, 7 | stop 7 also served by bus 1, enqueue it |
| 2 | bus 1 | 3, 6 | stop 6 is the target |

**Output:** `2`.
