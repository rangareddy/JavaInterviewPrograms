# Maximize Distance to Closest Person

Given a row of seats, `seats[i] = 1` means the seat is occupied and `0` means empty. At least one seat is empty and at least one is occupied. Alex wants to sit in an empty seat so that the distance to the closest person is as large as possible. Return that maximum distance.

## Algorithm Strategy (Longest Gap between Occupied Seats)

1. Between two occupied seats a gap of `g` positions lets Alex sit in the middle with distance `g / 2`.
2. At the ends of the row there is no person on one side, so the whole run of empty seats counts: the distance equals the run length.
3. Scan once, tracking the index of the previous occupied seat.
4. The answer is the maximum of the leading run, the trailing run and half of each inner gap.

## Example walkthrough

- For `[1, 0, 0, 0, 1, 0, 1]` the inner gap between index 0 and 4 has 4 steps, so Alex sits in the middle at distance 2.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — A few indexes.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

For every empty seat compute the distance to the nearest occupied one.

```java
public static int bruteForce(int[] s) {
    int best = 0; for (int i = 0; i < s.length; i++) { if (s[i] == 1) continue; int d = Integer.MAX_VALUE; for (int j = 0; j < s.length; j++) if (s[j] == 1) d = Math.min(d, Math.abs(i - j)); best = Math.max(best, d); }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Two Passes of Nearest Distances

Compute the distance to the nearest person on the left and on the right, then take the minimum per seat.

```java
public static int passes(int[] s) {
    int n = s.length; int[] left = new int[n], right = new int[n]; java.util.Arrays.fill(left, n); java.util.Arrays.fill(right, n);
    for (int i = 0; i < n; i++) { if (s[i] == 1) left[i] = 0; else if (i > 0) left[i] = left[i - 1] + 1; }
    for (int i = n - 1; i >= 0; i--) { if (s[i] == 1) right[i] = 0; else if (i < n - 1) right[i] = right[i + 1] + 1; }
    int best = 0; for (int i = 0; i < n; i++) if (s[i] == 0) best = Math.max(best, Math.min(left[i], right[i])); return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Largest Gap in One Pass (Optimized)

Track the previous occupied seat and compare the leading, trailing and inner gaps. This is the version implemented in the repository.

```java
public static int gaps(int[] s) {
    int best = 0, prev = -1;
    for (int i = 0; i < s.length; i++) if (s[i] == 1) { best = prev == -1 ? i : Math.max(best, (i - prev) / 2); prev = i; }
    return Math.max(best, s.length - 1 - prev);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Two Passes of Nearest Distances | O(n) | O(n) |
| 3 | Largest Gap in One Pass (Optimized) | O(n) | O(1) |

## Source Code

### MaximizeDistanceToClosestPerson.java

```java
import java.util.Arrays;

public class MaximizeDistanceToClosestPerson {

    public static void main(String[] args) {
        int[][] inputs = {{1, 0, 0, 0, 1, 0, 1}, {1, 0, 0, 0}, {0, 1}, {1, 0, 1}};
        for (int[] seats : inputs) {
            System.out.println("Input  : seats = " + Arrays.toString(seats));
            System.out.println("Output : " + maxDistToClosest(seats));
            System.out.println();
        }
    }

    public static int maxDistToClosest(int[] seats) {
        if (seats == null || seats.length == 0) {
            return 0;
        }
        int best = 0;
        int previous = -1;
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == 1) {
                best = previous == -1 ? i : Math.max(best, (i - previous) / 2);
                previous = i;
            }
        }
        if (previous != -1) {
            best = Math.max(best, seats.length - 1 - previous);
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: Alex's best seat is in the middle of the longest stretch of empty seats, except at the ends of the row, where there is no person on one side and the best seat is the very end.

**Input:** `seats = [1, 0, 0, 0, 1, 0, 1]`.

| Gap | Where | Distance |
|-----|-------|----------|
| leading | none (seat 0 is occupied) | 0 |
| inner | between indexes 0 and 4 | `(4 - 0) / 2 = 2` |
| inner | between indexes 4 and 6 | `(6 - 4) / 2 = 1` |
| trailing | none | 0 |

**Output:** `2`.
