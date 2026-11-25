# Find the Smallest Divisor Given a Threshold

Given an array of integers `nums` and an integer `threshold`, choose a positive integer divisor, divide every element by it (rounding up), and sum the results. Return the smallest divisor such that this sum is less than or equal to `threshold`.

## Algorithm Strategy (Binary Search on the Divisor)

1. The divisor lies between 1 and the largest element of `nums` (at that divisor every term is 1).
2. For a candidate divisor `d`, compute `sum(ceil(x / d))`.
3. The sum decreases as `d` grows, so if the sum is within the threshold, try a smaller divisor (`hi = mid`); otherwise `lo = mid + 1`.
4. When `lo == hi`, that divisor is the answer.

## Example walkthrough

- For `[1, 2, 5, 9]` and `threshold = 6`: divisor 5 gives `1 + 1 + 1 + 2 = 5 <= 6`, divisor 4 gives `1 + 1 + 2 + 3 = 7 > 6`.
- Result: `5`.

## Complexity Analysis

- **Time Complexity:** `O(n log max)` — A binary search over the divisor range with an O(n) sum per step.
- **Space Complexity:** `O(1)` — Only counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Divisor

Increase the divisor from 1 until the sum fits the threshold.

```java
public static int linear(int[] a, int t) {
    int max = 0; for (int x : a) max = Math.max(max, x);
    for (int d = 1; d <= max; d++) { long s = 0; for (int x : a) s += (x + d - 1) / d; if (s <= t) return d; }
    return max;
}
```

- **Time Complexity:** O(n * max)
- **Space Complexity:** O(1)

### Approach 2: Binary Search on the Divisor (Optimized)

The sum shrinks as the divisor grows, so binary search works. This is the version implemented in the repository.

```java
public static int binary(int[] a, int t) {
    int lo = 1, hi = 0; for (int x : a) hi = Math.max(hi, x);
    while (lo < hi) { int mid = lo + (hi - lo) / 2; long s = 0; for (int x : a) s += (x + mid - 1) / mid; if (s <= t) hi = mid; else lo = mid + 1; }
    return lo;
}
```

- **Time Complexity:** O(n log max)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Divisor | O(n * max) | O(1) |
| 2 | Binary Search on the Divisor (Optimized) | O(n log max) | O(1) |

## Source Code

### SmallestDivisorGivenAThreshold.java

```java
import java.util.Arrays;

public class SmallestDivisorGivenAThreshold {

    public static void main(String[] args) {
        int[][] nums = {{1, 2, 5, 9}, {44, 22, 33, 11, 1}, {2, 3, 5, 7, 11}, {19}};
        int[] thresholds = {6, 5, 11, 5};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", threshold = " + thresholds[i]);
            System.out.println("Output : " + smallestDivisor(nums[i], thresholds[i]));
            System.out.println();
        }
    }

    public static int smallestDivisor(int[] nums, int threshold) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int lo = 1;
        int hi = 0;
        for (int x : nums) {
            hi = Math.max(hi, x);
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            long sum = 0;
            for (int x : nums) {
                sum += (x + mid - 1) / mid;
            }
            if (sum <= threshold) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a bigger divisor can only make the rounded-up sum smaller, so "sum is within the threshold" is true from some divisor onwards. Binary search finds the first such divisor.

**Input:** `nums = [1, 2, 5, 9]`, `threshold = 6`. Search range `[1, 9]`.

| `lo` | `hi` | `mid` | Sum of `ceil(x / mid)` | Decision |
|------|------|-------|------------------------|----------|
| 1 | 9 | 5 | 1 + 1 + 1 + 2 = 5 | fits, `hi = 5` |
| 1 | 5 | 3 | 1 + 1 + 2 + 3 = 7 | too big, `lo = 4` |
| 4 | 5 | 4 | 1 + 1 + 2 + 3 = 7 | too big, `lo = 5` |

**Output:** `5`.
