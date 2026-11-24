# Subarray Product Less Than K

Given an array of positive integers `nums` and an integer `k`, return the number of contiguous subarrays whose product is strictly less than `k`.

## Algorithm Strategy (Sliding Window Counting New Subarrays)

1. Keep a window `[left, right]` and its `product`. Because all numbers are positive, growing the window increases the product and shrinking it decreases it.
2. Extend `right` and multiply `product` by `nums[right]`.
3. While `product >= k` (and `left <= right`), divide by `nums[left]` and advance `left`.
4. Every subarray that ends at `right` and starts at or after `left` is valid, so add `right - left + 1` to the count.

## Example walkthrough

- For `[10, 5, 2, 6]` and `k = 100`: the window grows to `[10, 5, 2]` (product 100) which is too large, so `10` leaves.
- Counting the new subarrays at each step gives 8.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index enters and leaves the window once.
- **Space Complexity:** `O(1)` — Two pointers and a product.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Test the product of every subarray.

```java
public static int bruteForce(int[] a, int k) {
    int c = 0;
    for (int i = 0; i < a.length; i++) { long p = 1; for (int j = i; j < a.length; j++) { p *= a[j]; if (p < k) c++; else break; } }
    return c;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Logarithms and Binary Search

Turn products into sums of logs, then binary search over prefix sums for each start.

```java
public static int logSearch(int[] a, int k) {
    if (k <= 1) return 0;
    double target = Math.log(k) - 1e-9; int n = a.length; double[] pre = new double[n + 1];
    for (int i = 0; i < n; i++) pre[i + 1] = pre[i] + Math.log(a[i]);
    int c = 0;
    for (int i = 0; i < n; i++) {
        int lo = i + 1, hi = n + 1;
        while (lo < hi) { int mid = (lo + hi) / 2; if (pre[mid] - pre[i] < target) lo = mid + 1; else hi = mid; }
        c += lo - i - 1;
    }
    return c;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Sliding Window (Optimized)

Shrink while the product is too large and count the subarrays ending at each index. This is the version implemented in the repository.

```java
public static int window(int[] a, int k) {
    if (k <= 1) return 0;
    int c = 0, left = 0; long p = 1;
    for (int right = 0; right < a.length; right++) {
        p *= a[right];
        while (p >= k) p /= a[left++];
        c += right - left + 1;
    }
    return c;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Logarithms and Binary Search | O(n log n) | O(n) |
| 3 | Sliding Window (Optimized) | O(n) | O(1) |

## Source Code

### SubarrayProductLessThanK.java

```java
import java.util.Arrays;

public class SubarrayProductLessThanK {

    public static void main(String[] args) {
        int[][] nums = {{10, 5, 2, 6}, {1, 2, 3}, {1, 1, 1}, {}};
        int[] ks = {100, 0, 2, 5};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + numSubarrayProductLessThanK(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static int numSubarrayProductLessThanK(int[] nums, int k) {
        if (nums == null || k <= 1) {
            return 0;
        }
        int count = 0;
        int left = 0;
        long product = 1;
        for (int right = 0; right < nums.length; right++) {
            product *= nums[right];
            while (product >= k) {
                product /= nums[left++];
            }
            count += right - left + 1;
        }
        return count;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: if the window `[left, right]` has a small enough product, then every subarray that ends at `right` and starts anywhere in the window also does, so `right - left + 1` new subarrays are added at once.

**Input:** `nums = [10, 5, 2, 6]`, `k = 100`.

| `right` | Value | Product after shrinking | `left` | Added | Total |
|---------|-------|--------------------------|--------|-------|-------|
| 0 | 10 | 10 | 0 | 1 | 1 |
| 1 | 5 | 50 | 0 | 2 | 3 |
| 2 | 2 | 100 -> 10 (10 removed) | 1 | 2 | 5 |
| 3 | 6 | 60 | 1 | 3 | 8 |

**Output:** `8`.
