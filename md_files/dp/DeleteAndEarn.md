# Delete and Earn

Given an integer array `nums`, you may repeatedly pick a number `x`, earn `x` points, and then delete every element equal to `x - 1` or `x + 1`. Return the maximum number of points you can earn.

## Algorithm Strategy (House Robber on Value Sums)

1. Taking a value `v` earns `v * count(v)` and forbids `v - 1` and `v + 1`.
2. Build `points[v] = v * count(v)` for every value up to the maximum.
3. This is the House Robber problem over `points`: you cannot take two adjacent values.
4. Use `take` and `skip` running totals: `newTake = skip + points[v]`, `newSkip = max(skip, take)`.

## Example walkthrough

- For `[2, 2, 3, 3, 3, 4]`: points are 2 -> 4, 3 -> 9, 4 -> 4.
- Taking 3 (9) beats taking 2 and 4 (8), so the best is 9.

## Complexity Analysis

- **Time Complexity:** `O(n + maxValue)` — Counting plus one pass over the values.
- **Space Complexity:** `O(maxValue)` — The points array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion over Distinct Values

Take or skip each distinct value in sorted order. Exponential without memoization.

```java
public static int recursive(int[] a) {
    java.util.TreeMap<Integer, Integer> pts = new java.util.TreeMap<>(); for (int x : a) pts.merge(x, x, Integer::sum);
    Integer[] keys = pts.keySet().toArray(new Integer[0]); int[] vals = new int[keys.length]; for (int i = 0; i < keys.length; i++) vals[i] = pts.get(keys[i]);
    return go(keys, vals, 0);
}
private static int go(Integer[] k, int[] v, int i) {
    if (i >= k.length) return 0;
    int skip = go(k, v, i + 1);
    int next = i + 1; if (next < k.length && k[next] == k[i] + 1) next++;
    return Math.max(skip, v[i] + go(k, v, next));
}
```

- **Time Complexity:** O(2^d)
- **Space Complexity:** O(d)

### Approach 2: DP over the Points Array

`dp[v] = max(dp[v - 1], dp[v - 2] + points[v])`.

```java
public static int dpArray(int[] a) {
    if (a.length == 0) return 0; int max = 0; for (int x : a) max = Math.max(max, x);
    int[] p = new int[max + 1]; for (int x : a) p[x] += x;
    int[] dp = new int[max + 2]; dp[1] = p[0];
    for (int v = 1; v <= max; v++) dp[v + 1] = Math.max(dp[v], dp[v - 1] + p[v]);
    return dp[max + 1];
}
```

- **Time Complexity:** O(n + maxValue)
- **Space Complexity:** O(maxValue)

### Approach 3: Rolling Take and Skip (Optimized)

Keep only two running totals. This is the version implemented in the repository.

```java
public static int rolling(int[] a) {
    if (a.length == 0) return 0; int max = 0; for (int x : a) max = Math.max(max, x);
    int[] p = new int[max + 1]; for (int x : a) p[x] += x;
    int take = 0, skip = 0;
    for (int v = 0; v <= max; v++) { int nt = skip + p[v]; skip = Math.max(skip, take); take = nt; }
    return Math.max(take, skip);
}
```

- **Time Complexity:** O(n + maxValue)
- **Space Complexity:** O(maxValue)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion over Distinct Values | O(2^d) | O(d) |
| 2 | DP over the Points Array | O(n + maxValue) | O(maxValue) |
| 3 | Rolling Take and Skip (Optimized) | O(n + maxValue) | O(maxValue) |

## Source Code

### DeleteAndEarn.java

```java
import java.util.Arrays;

public class DeleteAndEarn {

    public static void main(String[] args) {
        int[][] inputs = {{2, 2, 3, 3, 3, 4}, {3, 4, 2}, {1, 1, 1, 2, 4, 5, 5, 5, 6}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + deleteAndEarn(nums));
            System.out.println();
        }
    }

    public static int deleteAndEarn(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = 0;
        for (int x : nums) {
            max = Math.max(max, x);
        }
        int[] points = new int[max + 1];
        for (int x : nums) {
            points[x] += x;
        }
        int take = 0;
        int skip = 0;
        for (int v = 0; v <= max; v++) {
            int newTake = skip + points[v];
            skip = Math.max(skip, take);
            take = newTake;
        }
        return Math.max(take, skip);
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: picking a value deletes its neighbours, which is exactly "you cannot take two adjacent houses". Group equal values into one house worth `value * count`, then run House Robber.

**Input:** `nums = [2, 2, 3, 3, 3, 4]`. Points: 2 -> 4, 3 -> 9, 4 -> 4.

| Value | `points[v]` | `take` | `skip` |
|-------|-------------|--------|--------|
| 0 | 0 | 0 | 0 |
| 1 | 0 | 0 | 0 |
| 2 | 4 | 4 | 0 |
| 3 | 9 | 9 | 4 |
| 4 | 4 | 8 | 9 |

**Output:** `max(8, 9) = 9`.
