# Maximum Sum Circular Subarray

Given a circular integer array `nums`, return the maximum possible sum of a non-empty subarray, where the subarray may wrap around the end of the array.

## Algorithm Strategy (Max Subarray vs Total Minus Min Subarray)

1. Without wrapping, the answer is the usual maximum subarray sum (Kadane).
2. With wrapping, the chosen elements are everything except one contiguous middle part, so the wrapped maximum is `total - minSubarray`.
3. Compute `maxSub` and `minSub` in one pass with two Kadane runs.
4. The answer is `max(maxSub, total - minSub)`; if all elements are negative (`maxSub < 0`), return `maxSub`, since `total - minSub` would be an empty subarray.

## Example walkthrough

- For `[5, -3, 5]`: the best non-wrapping sum is 7 and `total - minSub = 7 - (-3) = 10`, the wrapped subarray `[5, 5]`.
- For `[1, -2, 3, -2]` the best is `3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — A few running values.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force over Starts and Lengths

Try every start and every length up to `n`, wrapping with modulo.

```java
public static int bruteForce(int[] a) {
    int n = a.length, best = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) { int s = 0; for (int len = 1; len <= n; len++) { s += a[(i + len - 1) % n]; best = Math.max(best, s); } }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Kadane for Max and Min (Optimized)

Compare the best plain subarray with the total minus the worst subarray. This is the version implemented in the repository.

```java
public static int kadane(int[] a) {
    int total = 0, cm = 0, cn = 0, mx = Integer.MIN_VALUE, mn = Integer.MAX_VALUE;
    for (int x : a) { total += x; cm = Math.max(cm + x, x); mx = Math.max(mx, cm); cn = Math.min(cn + x, x); mn = Math.min(mn, cn); }
    return mx < 0 ? mx : Math.max(mx, total - mn);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force over Starts and Lengths | O(n^2) | O(1) |
| 2 | Kadane for Max and Min (Optimized) | O(n) | O(1) |

## Source Code

### MaximumSumCircularSubarray.java

```java
import java.util.Arrays;

public class MaximumSumCircularSubarray {

    public static void main(String[] args) {
        int[][] inputs = {{1, -2, 3, -2}, {5, -3, 5}, {-3, -2, -3}, {3, -1, 2, -1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + maxSubarraySumCircular(nums));
            System.out.println();
        }
    }

    public static int maxSubarraySumCircular(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int total = 0;
        int curMax = 0;
        int curMin = 0;
        int maxSub = Integer.MIN_VALUE;
        int minSub = Integer.MAX_VALUE;
        for (int x : nums) {
            total += x;
            curMax = Math.max(curMax + x, x);
            maxSub = Math.max(maxSub, curMax);
            curMin = Math.min(curMin + x, x);
            minSub = Math.min(minSub, curMin);
        }
        return maxSub < 0 ? maxSub : Math.max(maxSub, total - minSub);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a subarray that wraps around is the whole array minus a contiguous piece in the middle, so the best wrapped sum is `total - (smallest subarray sum)`. The answer is the better of the two cases.

**Input:** `nums = [5, -3, 5]`, total = 7.

| Case | Computation | Value |
|------|-------------|-------|
| no wrap | best plain subarray `[5]`, or `[5, -3, 5]` | 7 |
| wrap | total - minimum subarray (-3) = 7 - (-3) | 10 |

**Output for this input:** `10`. For the first sample in `main`, `[1, -2, 3, -2]`, the answer is `3`.
