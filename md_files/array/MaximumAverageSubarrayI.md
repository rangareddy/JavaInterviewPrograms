# Maximum Average Subarray I

Given an integer array `nums` and an integer `k`, find the contiguous subarray of length exactly `k` that has the maximum average value and return that value.

## Algorithm Strategy (Fixed Sliding Window Sum)

1. Compute the sum of the first `k` elements.
2. Slide the window one step at a time: add the entering element and subtract the leaving one.
3. Track the maximum window sum.
4. Divide the maximum sum by `k` at the end, as a `double`.

## Example walkthrough

- For `[1, 12, -5, -6, 50, 3]` and `k = 4`: the window sums are 2, 51 and 42.
- The best is 51, and `51 / 4 = 12.75`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — A running sum.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Sum every window of length `k` from scratch.

```java
public static double bruteForce(int[] a, int k) {
    long best = Long.MIN_VALUE;
    for (int i = 0; i + k <= a.length; i++) { long s = 0; for (int j = i; j < i + k; j++) s += a[j]; best = Math.max(best, s); }
    return (double) best / k;
}
```

- **Time Complexity:** O(n * k)
- **Space Complexity:** O(1)

### Approach 2: Sliding Window (Optimized)

Update the window sum incrementally. This is the version implemented in the repository.

```java
public static double window(int[] a, int k) {
    long s = 0; for (int i = 0; i < k; i++) s += a[i];
    long best = s;
    for (int i = k; i < a.length; i++) { s += a[i] - a[i - k]; best = Math.max(best, s); }
    return (double) best / k;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n * k) | O(1) |
| 2 | Sliding Window (Optimized) | O(n) | O(1) |

## Source Code

### MaximumAverageSubarrayI.java

```java
import java.util.Arrays;

public class MaximumAverageSubarrayI {

    public static void main(String[] args) {
        int[][] nums = {{1, 12, -5, -6, 50, 3}, {5}, {-1, -2, -3}, {0, 4, 0, 3, 2}};
        int[] ks = {4, 1, 2, 1};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + findMaxAverage(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static double findMaxAverage(int[] nums, int k) {
        if (nums == null || k <= 0 || nums.length < k) {
            return 0;
        }
        long sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        long best = sum;
        for (int i = k; i < nums.length; i++) {
            sum += nums[i] - nums[i - k];
            best = Math.max(best, sum);
        }
        return (double) best / k;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the window sum changes by only two elements per step, so there is no need to re-add the whole window.

**Input:** `nums = [1, 12, -5, -6, 50, 3]`, `k = 4`.

| Window | Sum | Best |
|--------|-----|------|
| [1, 12, -5, -6] | 2 | 2 |
| [12, -5, -6, 50] | 2 + 50 - 1 = 51 | 51 |
| [-5, -6, 50, 3] | 51 + 3 - 12 = 42 | 51 |

**Output:** `51 / 4 = 12.75`.
