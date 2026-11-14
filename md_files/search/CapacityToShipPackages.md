# Capacity to Ship Packages Within D Days

Packages with the given `weights` must be shipped in order (no reordering) by a ship that loads packages each day up to its capacity. Return the least ship capacity that delivers all packages within `days` days.

## Algorithm Strategy (Binary Search on the Answer)

1. The answer lies between `max(weights)` (every package must fit on the ship) and `sum(weights)` (everything ships in one day).
2. Feasibility is monotonic: if capacity `c` works, any larger capacity also works. That makes the question "is `c` enough?" suitable for binary search.
3. To test a capacity, simulate the loading greedily: add packages to the current day until the next one would exceed `c`, then start a new day. Count the days.
4. If the days needed are at most `days`, try a smaller capacity (`hi = mid`), otherwise a larger one (`lo = mid + 1`). The search ends when `lo == hi`.

## Example walkthrough

- For `weights = [1..10]`, `days = 5`: the search range is `[10, 55]`.
- Capacity 15 needs 5 days (`1..5`, `6..7`, `8`, `9`, `10`), capacity 14 needs 6, so the answer is `15`.

## Complexity Analysis

- **Time Complexity:** `O(n log S)` — `S` is the sum of the weights; each feasibility check is `O(n)` and the search needs `log S` checks.
- **Space Complexity:** `O(1)` — A few integers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Capacity

Start at `max(weights)` and increase the capacity by one until the packages fit within `days`.

```java
public static int shipWithinDaysLinear(int[] weights, int days) {
    int capacity = 0;
    for (int w : weights) capacity = Math.max(capacity, w);
    while (true) {
        int used = 1, load = 0;
        for (int w : weights) {
            if (load + w > capacity) { used++; load = 0; }
            load += w;
        }
        if (used <= days) return capacity;
        capacity++;
    }
}
```

- **Time Complexity:** O(n * S)
- **Space Complexity:** O(1)

### Approach 2: Binary Search on Capacity (Optimized)

Binary search the capacity between `max(weights)` and `sum(weights)` using a greedy day count as the feasibility test. This is the version implemented in the repository.

```java
public static int shipWithinDays(int[] weights, int days) {
    int lo = 0;
    int hi = 0;
    for (int w : weights) {
        lo = Math.max(lo, w);
        hi += w;
    }
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (daysNeeded(weights, mid) <= days) {
            hi = mid;
        } else {
            lo = mid + 1;
        }
    }
    return lo;
}

private static int daysNeeded(int[] weights, int capacity) {
    int days = 1;
    int load = 0;
    for (int w : weights) {
        if (load + w > capacity) {
            days++;
            load = 0;
        }
        load += w;
    }
    return days;
}
```

- **Time Complexity:** O(n log S)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Capacity | O(n * S) | O(1) |
| 2 | Binary Search on Capacity (Optimized) | O(n log S) | O(1) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: search the ship capacity. A bigger capacity never needs more days, so "can ship within `days`" flips from false to true at one capacity; binary search finds that smallest capacity. The lowest possible capacity is the heaviest package (`lo`, or that package could never ship) and the highest is the sum of all weights (`hi`, everything in one day). `daysNeeded` simulates loading in order: when the next package does not fit, start a new day. The loop keeps `hi = mid` when feasible (mid may be the answer) and `lo = mid + 1` otherwise.

**Input:** `weights = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]`, `days = 5`. Range: `lo = 10` (max), `hi = 55` (sum).

| Step | lo | hi | mid (capacity) | Days needed | <= 5 ? | Action |
|------|----|----|----------------|-------------|--------|--------|
| 1 | 10 | 55 | 32 | 2 | yes | hi = 32 |
| 2 | 10 | 32 | 21 | 3 | yes | hi = 21 |
| 3 | 10 | 21 | 15 | 5 | yes | hi = 15 |
| 4 | 10 | 15 | 12 | 6 | no | lo = 13 |
| 5 | 13 | 15 | 14 | 6 | no | lo = 15 |
| 6 | 15 | 15 | - | - | `lo == hi`, stop | return 15 |

With capacity 15 the packages split as `1..5` (15), `6, 7` (13), `8`, `9`, `10`: exactly 5 days. With 14 it needs 6 days, so 15 is the minimum.

**Edge cases.** One package `[5]` with `days = 1` starts with `lo = hi = 5` and returns 5 without looping. When `days` is at least the number of packages the answer is the heaviest package (the same `[1, 2, 3, 1, 1]` with `days = 5` also gives 3). Packages must ship in the given order, which is why `daysNeeded` never reorders.

**Result:** `15`, matching the class output.

## Source Code

### CapacityToShipPackages.java

```java
import java.util.Arrays;

public class CapacityToShipPackages {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, {3, 2, 2, 4, 1, 4}, {1, 2, 3, 1, 1}};
        int[] days = {5, 3, 4};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : weights = " + Arrays.toString(inputs[i]) + ", days = " + days[i]);
            System.out.println("Output : " + shipWithinDays(inputs[i], days[i]));
            System.out.println();
        }
    }

    public static int shipWithinDays(int[] weights, int days) {
        int lo = 0;
        int hi = 0;
        for (int w : weights) {
            lo = Math.max(lo, w);
            hi += w;
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (daysNeeded(weights, mid) <= days) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private static int daysNeeded(int[] weights, int capacity) {
        int days = 1;
        int load = 0;
        for (int w : weights) {
            if (load + w > capacity) {
                days++;
                load = 0;
            }
            load += w;
        }
        return days;
    }
}
```
