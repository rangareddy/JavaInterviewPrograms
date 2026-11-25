# Find the Highest Altitude

A biker goes on a road trip across `n + 1` points at different altitudes. They start at point 0 with altitude 0. You are given an integer array `gain` of length `n`, where `gain[i]` is the net gain in altitude between points `i` and `i + 1`. Return the highest altitude reached.

## Algorithm Strategy (Running Sum with a Maximum)

1. Start with `altitude = 0` and `highest = 0` (the start counts).
2. For each gain, add it to `altitude`.
3. Update `highest` with the new altitude.
4. Return `highest`.

## Example walkthrough

- For `[-5, 1, 5, 0, -7]`: altitudes are 0, -5, -4, 1, 1, -6.
- The highest is 1.
- Result: `1`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Two numbers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Build the Altitude Array

Store every altitude, then take the maximum.

```java
public static int array(int[] g) { int[] alt = new int[g.length + 1]; for (int i = 0; i < g.length; i++) alt[i + 1] = alt[i] + g[i]; int best = alt[0]; for (int v : alt) best = Math.max(best, v); return best; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Running Sum (Optimized)

Keep only the current altitude and the best so far. This is the version implemented in the repository.

```java
public static int running(int[] g) { int a = 0, best = 0; for (int x : g) { a += x; best = Math.max(best, a); } return best; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Build the Altitude Array | O(n) | O(n) |
| 2 | Running Sum (Optimized) | O(n) | O(1) |

## Source Code

### FindTheHighestAltitude.java

```java
import java.util.Arrays;

public class FindTheHighestAltitude {

    public static void main(String[] args) {
        int[][] inputs = {{-5, 1, 5, 0, -7}, {-4, -3, -2, -1, 4, 3, 2}, {}, {3, 3}, {-1}};
        for (int[] gain : inputs) {
            System.out.println("Input  : gain = " + Arrays.toString(gain));
            System.out.println("Output : " + largestAltitude(gain));
            System.out.println();
        }
    }

    public static int largestAltitude(int[] gain) {
        int altitude = 0;
        int highest = 0;
        if (gain == null) {
            return 0;
        }
        for (int g : gain) {
            altitude += g;
            highest = Math.max(highest, altitude);
        }
        return highest;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: altitudes are prefix sums of the gains, so a running total is enough, and the maximum can be kept alongside it. The starting altitude 0 counts as a candidate.

**Input:** `gain = [-5, 1, 5, 0, -7]`.

| Point | Gain | Altitude | Highest |
|-------|------|----------|---------|
| 0 | | 0 | 0 |
| 1 | -5 | -5 | 0 |
| 2 | 1 | -4 | 0 |
| 3 | 5 | 1 | 1 |
| 4 | 0 | 1 | 1 |
| 5 | -7 | -6 | 1 |

**Output:** `1`.
