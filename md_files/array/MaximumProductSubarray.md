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

### Approach 3: Track Maximum and Minimum (Optimized)

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
| 3 | Track Maximum and Minimum (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

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
