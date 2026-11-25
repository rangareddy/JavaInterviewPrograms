# Minimum Number of Days to Make m Bouquets

You are given an integer array `bloomDay`, an integer `m` and an integer `k`. You want `m` bouquets. To make a bouquet you use `k` adjacent flowers from the garden, and the `i`-th flower blooms on day `bloomDay[i]` and can be used in exactly one bouquet. Return the minimum number of days you need to wait to make `m` bouquets, or `-1` if it is impossible.

## Algorithm Strategy (Binary Search on the Number of Days)

1. If `m * k` is more than the number of flowers, return `-1`.
2. The answer lies between the smallest and the largest bloom day. For a given day `d`, count how many bouquets can be made: scan the garden and count runs of adjacent flowers with `bloomDay <= d`, taking one bouquet per `k` flowers.
3. The count never decreases as `d` grows, so binary search for the smallest day with at least `m` bouquets.
4. Return that day.

## Example walkthrough

- For `bloomDay = [1, 10, 3, 10, 2]`, `m = 3`, `k = 1`: on day 3, flowers 0, 2 and 4 are open, giving 3 bouquets.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n log(max day))` — One linear check per binary search step.
- **Space Complexity:** `O(1)` — A counter.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Day in Order

Check each day from the smallest bloom day upward until enough bouquets can be made.

```java
public static int linear(int[] b, int m, int k) {
    if ((long) m * k > b.length) return -1; int mx = 0; for (int x : b) mx = Math.max(mx, x); for (int d = 1; d <= mx; d++) { int c = 0, run = 0; for (int x : b) { if (x <= d) { if (++run == k) { c++; run = 0; } } else run = 0; } if (c >= m) return d; } return -1;
}
```

- **Time Complexity:** O(n * maxDay)
- **Space Complexity:** O(1)

### Approach 2: Binary Search on the Answer (Optimized)

Search the smallest day with enough bouquets. This is the version implemented in the repository.

```java
public static int binary(int[] b, int m, int k) {
    if ((long) m * k > b.length) return -1; int lo = Integer.MAX_VALUE, hi = 0; for (int x : b) { lo = Math.min(lo, x); hi = Math.max(hi, x); }
    while (lo < hi) { int mid = (lo + hi) / 2; int c = 0, run = 0; for (int x : b) { if (x <= mid) { if (++run == k) { c++; run = 0; } } else run = 0; } if (c >= m) hi = mid; else lo = mid + 1; } return lo;
}
```

- **Time Complexity:** O(n log maxDay)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Day in Order | O(n * maxDay) | O(1) |
| 2 | Binary Search on the Answer (Optimized) | O(n log maxDay) | O(1) |

## Source Code

### MinimumNumberOfDaysToMakeMBouquets.java

```java
import java.util.Arrays;

public class MinimumNumberOfDaysToMakeMBouquets {

    public static void main(String[] args) {
        int[][] days = {{1, 10, 3, 10, 2}, {1, 10, 3, 10, 2}, {7, 7, 7, 7, 12, 7, 7}, {1, 10, 2, 9, 3, 8, 4, 7, 5, 6}};
        int[] ms = {3, 3, 2, 4};
        int[] ks = {1, 2, 3, 2};
        for (int i = 0; i < days.length; i++) {
            System.out.println("Input  : bloomDay = " + Arrays.toString(days[i]) + ", m = " + ms[i] + ", k = " + ks[i]);
            System.out.println("Output : " + minDays(days[i], ms[i], ks[i]));
            System.out.println();
        }
    }

    public static int minDays(int[] bloomDay, int m, int k) {
        if (bloomDay == null || m <= 0 || k <= 0 || (long) m * k > bloomDay.length) {
            return -1;
        }
        int lo = Integer.MAX_VALUE;
        int hi = 0;
        for (int day : bloomDay) {
            lo = Math.min(lo, day);
            hi = Math.max(hi, day);
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (bouquets(bloomDay, mid, k) >= m) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private static int bouquets(int[] bloomDay, int day, int k) {
        int count = 0;
        int run = 0;
        for (int bloom : bloomDay) {
            if (bloom <= day) {
                if (++run == k) {
                    count++;
                    run = 0;
                }
            } else {
                run = 0;
            }
        }
        return count;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: waiting longer can never reduce the number of bouquets, so "enough bouquets by day `d`" is false up to some day and true afterwards. That is exactly the shape binary search needs. Checking one day is a linear scan that counts runs of open flowers.

**Input:** `bloomDay = [1, 10, 3, 10, 2]`, `m = 3`, `k = 1`.

| Day tried | Open flowers | Bouquets | Decision |
|-----------|--------------|----------|----------|
| 5 (mid of 1..10) | 0, 2, 4 | 3 | enough: search lower |
| 3 | 0, 2, 4 | 3 | enough: search lower |
| 2 | 0, 4 | 2 | not enough: search higher |

**Output:** `3`.
