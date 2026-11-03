# Maximum Product Subarray

Given an integer array `nums`, find the contiguous subarray with the largest product and return that product.

## Algorithm Strategy (Track Both the Maximum and the Minimum Product)

1. A negative number turns the smallest (most negative) product into the largest one, so keep both `max` and `min` products of subarrays that end at the current index.
2. For each number `n`, the new maximum is `max(n, max * n, min * n)` and the new minimum is `min(n, max * n, min * n)`.
3. Compute both values from the old `max` and `min` (use a temporary variable) before overwriting them.
4. A zero resets both to 0 automatically, which starts a fresh subarray. Keep the largest `max` seen as the answer.

## Example walkthrough

- For `[2, 3, -2, 4]`: (max, min) after each element is (2, 2), (6, 3), (-2, -12), (4, -48).
- The best maximum seen is `6`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single pass.
- **Space Complexity:** `O(1)` — Three variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Multiply every subarray with a running product for each start index.

```java
public static int maxProductBrute(int[] nums) {
    int best = Integer.MIN_VALUE;
    for (int i = 0; i < nums.length; i++) {
        int product = 1;
        for (int j = i; j < nums.length; j++) {
            product *= nums[j];
            best = Math.max(best, product);
        }
    }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Prefix and Suffix Products

In any subarray with an even number of negatives the best product is a prefix or a suffix of the zero-free segment. Scan from both ends at once, restarting the product after a zero.

```java
public static int maxProductPrefixSuffix(int[] nums) {
    int n = nums.length, best = nums[0], prefix = 1, suffix = 1;
    for (int i = 0; i < n; i++) {
        prefix = (prefix == 0 ? 1 : prefix) * nums[i];
        suffix = (suffix == 0 ? 1 : suffix) * nums[n - 1 - i];
        best = Math.max(best, Math.max(prefix, suffix));
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: DP Arrays for the Maximum and Minimum

The same idea as the two-variable version but with full arrays: `maxDp[i]` and `minDp[i]` are the largest and smallest products of a subarray ending at `i`.

```java
public static int maxProductArrays(int[] nums) {
    int n = nums.length;
    int[] maxDp = new int[n], minDp = new int[n];
    maxDp[0] = minDp[0] = nums[0];
    int best = nums[0];
    for (int i = 1; i < n; i++) {
        int a = maxDp[i - 1] * nums[i], b = minDp[i - 1] * nums[i];
        maxDp[i] = Math.max(nums[i], Math.max(a, b));
        minDp[i] = Math.min(nums[i], Math.min(a, b));
        best = Math.max(best, maxDp[i]);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Split at Zeros, Drop a Negative Prefix or Suffix

A zero splits the array into independent zero-free segments. In a segment with an even number of negatives the whole segment is best; with an odd number, remove either the prefix up to the first negative or the suffix from the last negative, whichever leaves the larger product.

```java
public static int maxProductSegments(int[] nums) {
    int best = Integer.MIN_VALUE, start = 0;
    for (int i = 0; i <= nums.length; i++) {
        if (i == nums.length || nums[i] == 0) {
            if (i < nums.length) best = Math.max(best, 0);
            if (i > start) best = Math.max(best, segmentBest(nums, start, i - 1));
            start = i + 1;
        }
    }
    return best;
}

private static int segmentBest(int[] nums, int l, int r) {
    if (l == r) return nums[l];
    int product = 1;
    for (int k = l; k <= r; k++) product *= nums[k];
    if (product > 0) return product;
    int prefix = 1;
    for (int k = l; k <= r; k++) { prefix *= nums[k]; if (nums[k] < 0) break; }
    int suffix = 1;
    for (int k = r; k >= l; k--) { suffix *= nums[k]; if (nums[k] < 0) break; }
    return Math.max(product / prefix, product / suffix);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 5: Divide and Conquer

For each range return the best product, the largest/smallest prefix and suffix products and the total product; merging two ranges only needs these six numbers (the best product crossing the middle is the best of the four suffix x prefix combinations).

```java
public static int maxProductDivide(int[] nums) {
    return solve(nums, 0, nums.length - 1)[0];
}

// {best, maxPrefix, minPrefix, maxSuffix, minSuffix, total}
private static int[] solve(int[] a, int lo, int hi) {
    if (lo == hi) return new int[]{a[lo], a[lo], a[lo], a[lo], a[lo], a[lo]};
    int mid = (lo + hi) / 2;
    int[] l = solve(a, lo, mid), r = solve(a, mid + 1, hi);
    int cross = Math.max(Math.max(l[3] * r[1], l[3] * r[2]), Math.max(l[4] * r[1], l[4] * r[2]));
    return new int[]{
            Math.max(Math.max(l[0], r[0]), cross),
            Math.max(l[1], Math.max(l[5] * r[1], l[5] * r[2])),
            Math.min(l[2], Math.min(l[5] * r[1], l[5] * r[2])),
            Math.max(r[3], Math.max(r[5] * l[3], r[5] * l[4])),
            Math.min(r[4], Math.min(r[5] * l[3], r[5] * l[4])),
            l[5] * r[5]};
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(log n)

### Approach 6: Swap the Maximum and Minimum on a Negative Number

A common shortcut of the max/min method: multiplying by a negative number exchanges the roles of the maximum and the minimum, so swap them first and then update both with the same formula.

```java
public static int maxProductSwap(int[] nums) {
    int max = nums[0], min = nums[0], best = nums[0];
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] < 0) { int temp = max; max = min; min = temp; }
        max = Math.max(nums[i], max * nums[i]);
        min = Math.min(nums[i], min * nums[i]);
        best = Math.max(best, max);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 7: Track Maximum and Minimum (Optimized)

A negative number turns the smallest product into the largest, so keep both the maximum and the minimum product ending at each index. This is the version implemented in the repository.

```java
public static int maxProduct(int[] nums) {
    int max = nums[0], min = nums[0], result = nums[0];
    for (int i = 1; i < nums.length; i++) {
        int a = max * nums[i], b = min * nums[i];
        max = Math.max(nums[i], Math.max(a, b));
        min = Math.min(nums[i], Math.min(a, b));
        result = Math.max(result, max);
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Prefix and Suffix Products | O(n) | O(1) |
| 3 | DP Arrays for the Maximum and Minimum | O(n) | O(n) |
| 4 | Split at Zeros, Drop a Negative Prefix or Suffix | O(n) | O(1) |
| 5 | Divide and Conquer | O(n) | O(log n) |
| 6 | Swap the Maximum and Minimum on a Negative Number | O(n) | O(1) |
| 7 | Track Maximum and Minimum (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### MaximumProductSubarray.java

```java
import java.util.Arrays;

public class MaximumProductSubarray {

    public static void main(String[] args) {
        int[][] inputs = {{2, 3, -2, 4}, {-2, 0, -1}, {-2, 3, -4}, {0, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + maxProduct(nums));
            System.out.println();
        }
    }

    public static int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        int max = nums[0];
        int min = nums[0];
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int n = nums[i];
            int candidate1 = max * n;
            int candidate2 = min * n;
            max = Math.max(n, Math.max(candidate1, candidate2));
            min = Math.min(n, Math.min(candidate1, candidate2));
            result = Math.max(result, max);
        }
        return result;
    }
}
```
