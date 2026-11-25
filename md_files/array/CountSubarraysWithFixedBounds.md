# Count Subarrays With Fixed Bounds

You are given an integer array `nums` and two integers `minK` and `maxK`. A fixed-bound subarray is a subarray whose minimum value is exactly `minK` and whose maximum value is exactly `maxK`. Return the number of fixed-bound subarrays.

## Algorithm Strategy (Last Positions Sweep)

1. Walk the array with index `i` and remember three positions: `badIndex`, the last index of a value outside `[minK, maxK]`; `lastMin`, the last index of `minK`; and `lastMax`, the last index of `maxK`.
2. A subarray ending at `i` is valid if it starts after `badIndex` and contains both a `minK` and a `maxK`, so it must start at or before `min(lastMin, lastMax)`.
3. The number of valid starts is `max(0, min(lastMin, lastMax) - badIndex)`.
4. Sum that over all `i`.

## Example walkthrough

- For `[1, 3, 5, 2, 7, 5]`, `minK = 1`, `maxK = 5`: the valid subarrays are `[1, 3, 5]` and `[1, 3, 5, 2]`.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Three indexes.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Track the min and max of every subarray.

```java
public static long brute(int[] a, int mn, int mx) {
    long c = 0; for (int i = 0; i < a.length; i++) { int lo = Integer.MAX_VALUE, hi = Integer.MIN_VALUE; for (int j = i; j < a.length; j++) { lo = Math.min(lo, a[j]); hi = Math.max(hi, a[j]); if (lo == mn && hi == mx) c++; } } return c;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Last Positions Sweep (Optimized)

Count the valid starts for every end index from three remembered positions. This is the version implemented in the repository.

```java
public static long sweep(int[] a, int mn, int mx) {
    long c = 0; int bad = -1, lastMin = -1, lastMax = -1;
    for (int i = 0; i < a.length; i++) { if (a[i] < mn || a[i] > mx) bad = i; if (a[i] == mn) lastMin = i; if (a[i] == mx) lastMax = i; c += Math.max(0, Math.min(lastMin, lastMax) - bad); }
    return c;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Last Positions Sweep (Optimized) | O(n) | O(1) |

## Source Code

### CountSubarraysWithFixedBounds.java

```java
import java.util.Arrays;

public class CountSubarraysWithFixedBounds {

    public static void main(String[] args) {
        int[][] nums = {{1, 3, 5, 2, 7, 5}, {1, 1, 1, 1}, {35054, 398719, 945315, 945315, 820417, 328636, 916965}, {2, 1, 3}};
        int[] mins = {1, 1, 35054, 1};
        int[] maxs = {5, 1, 945315, 3};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", minK = " + mins[i] + ", maxK = " + maxs[i]);
            System.out.println("Output : " + countSubarrays(nums[i], mins[i], maxs[i]));
            System.out.println();
        }
    }

    public static long countSubarrays(int[] nums, int minK, int maxK) {
        if (nums == null) {
            return 0;
        }
        long count = 0;
        int badIndex = -1;
        int lastMin = -1;
        int lastMax = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < minK || nums[i] > maxK) {
                badIndex = i;
            }
            if (nums[i] == minK) {
                lastMin = i;
            }
            if (nums[i] == maxK) {
                lastMax = i;
            }
            count += Math.max(0, Math.min(lastMin, lastMax) - badIndex);
        }
        return count;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a valid subarray ending at `i` cannot contain an out-of-range value and must contain both bounds. So its start must lie after the last bad index and at or before the earlier of the last `minK` and the last `maxK`; the count of such starts comes straight from three indexes.

**Input:** `nums = [1, 3, 5, 2, 7, 5]`, `minK = 1`, `maxK = 5`.

| `i` | Value | `bad` | `lastMin` | `lastMax` | Added |
|-----|-------|-------|-----------|-----------|-------|
| 0 | 1 | -1 | 0 | -1 | 0 |
| 1 | 3 | -1 | 0 | -1 | 0 |
| 2 | 5 | -1 | 0 | 2 | 1 |
| 3 | 2 | -1 | 0 | 2 | 1 |
| 4 | 7 | 4 | 0 | 2 | 0 |
| 5 | 5 | 4 | 0 | 5 | 0 |

**Output:** `2`.
