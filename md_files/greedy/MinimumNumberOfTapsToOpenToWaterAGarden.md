# Minimum Number of Taps to Open to Water a Garden

A one-dimensional garden spans points 0 to `n`. There are `n + 1` taps at points `0..n`; tap `i` waters the area `[i - ranges[i], i + ranges[i]]` when opened. Return the minimum number of taps to open to water the whole garden, or `-1` if it is impossible.

## Algorithm Strategy (Convert to Jump Game)

1. For each tap, the leftmost point it covers is `left = max(0, i - ranges[i])`. Store in `reach[left]` the furthest right end any tap starting there can reach.
2. Now the problem is a jump game: from position `p` you can jump anywhere up to `reach[p]`.
3. Greedily extend: keep the current covered end `curEnd`, the furthest end seen so far `farthest`, and a counter of taps.
4. When the scan reaches `curEnd`, open the tap that gave `farthest` (counter + 1); if `farthest` did not move beyond `curEnd`, return `-1`.

## Example walkthrough

- For `n = 5` and `ranges = [3, 4, 1, 1, 0, 0]`: the tap at point 1 covers `[-3, 5]`, the whole garden.
- Result: `1`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over the points.
- **Space Complexity:** `O(n)` — The reach array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DP over Positions

`dp[x]` is the fewest taps to water `[0, x]`; update it from every tap range. Quadratic.

```java
public static int dp(int n, int[] ranges) {
    int[] d = new int[n + 1]; java.util.Arrays.fill(d, n + 2); d[0] = 0;
    for (int i = 0; i <= n; i++) { int l = Math.max(0, i - ranges[i]), r = Math.min(n, i + ranges[i]); for (int j = l; j <= r; j++) d[r] = Math.min(d[r], d[l] + 1); for (int x = r - 1; x >= l; x--) d[x] = Math.min(d[x], d[l] + 1); }
    for (int x = n - 1; x >= 0; x--) d[x] = Math.min(d[x], d[x + 1]);
    return d[n] > n ? -1 : d[n];
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Jump Game Greedy (Optimized)

Convert each tap into a farthest-reach jump and greedily extend the covered range. This is the version implemented in the repository.

```java
public static int greedy(int n, int[] ranges) {
    int[] reach = new int[n + 1]; for (int i = 0; i <= n; i++) { int l = Math.max(0, i - ranges[i]); reach[l] = Math.max(reach[l], Math.min(n, i + ranges[i])); }
    int taps = 0, cur = 0, far = 0;
    for (int i = 0; i < n; i++) { far = Math.max(far, reach[i]); if (i == cur) { if (far <= i) return -1; taps++; cur = far; } }
    return taps;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DP over Positions | O(n^2) | O(n) |
| 2 | Jump Game Greedy (Optimized) | O(n) | O(n) |

## Source Code

### MinimumNumberOfTapsToOpenToWaterAGarden.java

```java
import java.util.Arrays;

public class MinimumNumberOfTapsToOpenToWaterAGarden {

    public static void main(String[] args) {
        int[] ns = {5, 3, 7, 8};
        int[][] ranges = {{3, 4, 1, 1, 0, 0}, {0, 0, 0, 0}, {1, 2, 1, 0, 2, 1, 0, 1}, {4, 0, 0, 0, 0, 0, 0, 0, 4}};
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", ranges = " + Arrays.toString(ranges[i]));
            System.out.println("Output : " + minTaps(ns[i], ranges[i]));
            System.out.println();
        }
    }

    public static int minTaps(int n, int[] ranges) {
        if (ranges == null || ranges.length != n + 1) {
            return -1;
        }
        int[] reach = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            int left = Math.max(0, i - ranges[i]);
            int right = Math.min(n, i + ranges[i]);
            reach[left] = Math.max(reach[left], right);
        }
        int taps = 0;
        int curEnd = 0;
        int farthest = 0;
        for (int i = 0; i < n; i++) {
            farthest = Math.max(farthest, reach[i]);
            if (i == curEnd) {
                if (farthest <= i) {
                    return -1;
                }
                taps++;
                curEnd = farthest;
            }
        }
        return taps;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: only the leftmost covered point and the rightmost reach of each tap matter, so each tap is a jump that starts at its left end. Covering `[0, n]` with the fewest tap intervals is then the minimum-jumps problem.

**Input:** `n = 5`, `ranges = [3, 4, 1, 1, 0, 0]`.

| Tap | Covers | `reach[left]` |
|-----|--------|----------------|
| 0 | [0, 3] | `reach[0] = 3` |
| 1 | [-3, 5] clipped to [0, 5] | `reach[0] = 5` |
| 2 | [1, 3] | `reach[1] = 3` |
| 3 | [2, 4] | `reach[2] = 4` |

At `i = 0` the furthest reach is 5, which is the end of the garden, so one tap is enough.

**Output:** `1`.
