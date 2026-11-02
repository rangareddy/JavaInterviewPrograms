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
