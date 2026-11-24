# Heaters

Houses and heaters are placed on a horizontal line. Every heater warms all houses within its radius. Given the positions of `houses` and `heaters`, return the minimum radius so that every house is covered by at least one heater (all heaters use the same radius).

## Algorithm Strategy (Sort and Binary Search the Nearest Heater)

1. Sort the heaters.
2. For each house, binary search the first heater at a position `>=` the house.
3. The nearest heater is that one or the one just before it; the distance to it is the house's required radius.
4. The answer is the largest of those distances.

## Example walkthrough

- For houses `[1, 2, 3]` and heater `[2]`: houses 1 and 3 are one unit away.
- Result: `1`.

## Complexity Analysis

- **Time Complexity:** `O((n + m) log m)` — Sorting the heaters and one binary search per house.
- **Space Complexity:** `O(1)` — Apart from sorting.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

For every house compute the distance to every heater and take the minimum.

```java
public static int bruteForce(int[] houses, int[] heaters) {
    int radius = 0;
    for (int h : houses) { int best = Integer.MAX_VALUE; for (int x : heaters) best = Math.min(best, Math.abs(h - x)); radius = Math.max(radius, best); }
    return radius;
}
```

- **Time Complexity:** O(n * m)
- **Space Complexity:** O(1)

### Approach 2: Sort Both and Two Pointers

Sort houses and heaters and advance a heater pointer while the next heater is at least as close.

```java
public static int twoPointers(int[] houses, int[] heaters) {
    int[] h = houses.clone(), t = heaters.clone(); java.util.Arrays.sort(h); java.util.Arrays.sort(t); int j = 0, radius = 0;
    for (int x : h) { while (j + 1 < t.length && Math.abs(t[j + 1] - x) <= Math.abs(t[j] - x)) j++; radius = Math.max(radius, Math.abs(t[j] - x)); }
    return radius;
}
```

- **Time Complexity:** O(n log n + m log m)
- **Space Complexity:** O(1)

### Approach 3: Binary Search for the Nearest Heater (Optimized)

Search the nearest heater for each house in the sorted heaters. This is the version implemented in the repository.

```java
public static int binary(int[] houses, int[] heaters) {
    int[] t = heaters.clone(); java.util.Arrays.sort(t); int radius = 0;
    for (int x : houses) {
        int lo = 0, hi = t.length; while (lo < hi) { int mid = (lo + hi) / 2; if (t[mid] < x) lo = mid + 1; else hi = mid; }
        int r = lo < t.length ? t[lo] - x : Integer.MAX_VALUE, l = lo > 0 ? x - t[lo - 1] : Integer.MAX_VALUE; radius = Math.max(radius, Math.min(l, r));
    }
    return radius;
}
```

- **Time Complexity:** O((n + m) log m)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n * m) | O(1) |
| 2 | Sort Both and Two Pointers | O(n log n + m log m) | O(1) |
| 3 | Binary Search for the Nearest Heater (Optimized) | O((n + m) log m) | O(1) |

## Source Code

### Heaters.java

```java
import java.util.Arrays;

public class Heaters {

    public static void main(String[] args) {
        int[][] houses = {{1, 2, 3}, {1, 2, 3, 4}, {1, 5}, {1, 2, 3, 5, 15}};
        int[][] heaters = {{2}, {1, 4}, {2}, {2, 30}};
        for (int i = 0; i < houses.length; i++) {
            System.out.println("Input  : houses = " + Arrays.toString(houses[i]) + ", heaters = " + Arrays.toString(heaters[i]));
            System.out.println("Output : " + findRadius(houses[i], heaters[i].clone()));
            System.out.println();
        }
    }

    public static int findRadius(int[] houses, int[] heaters) {
        if (houses == null || houses.length == 0 || heaters == null || heaters.length == 0) {
            return 0;
        }
        Arrays.sort(heaters);
        int radius = 0;
        for (int house : houses) {
            int lo = 0;
            int hi = heaters.length;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (heaters[mid] < house) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
            int right = lo < heaters.length ? heaters[lo] - house : Integer.MAX_VALUE;
            int left = lo > 0 ? house - heaters[lo - 1] : Integer.MAX_VALUE;
            radius = Math.max(radius, Math.min(left, right));
        }
        return radius;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: each house only needs its nearest heater, and in a sorted array the nearest one is either the first heater at or after the house, or the one just before it. The radius that satisfies every house is the largest of those nearest distances.

**Input:** `houses = [1, 2, 3, 5, 15]`, `heaters = [2, 30]`.

| House | Heater at or after | Heater before | Distance |
|-------|--------------------|---------------|----------|
| 1 | 2 (1) | none | 1 |
| 2 | 2 (0) | none | 0 |
| 3 | 30 (27) | 2 (1) | 1 |
| 5 | 30 (25) | 2 (3) | 3 |
| 15 | 30 (15) | 2 (13) | 13 |

**Output for this input:** `13`. The first sample in `main`, houses `[1, 2, 3]` with heater `[2]`, gives `1`.
