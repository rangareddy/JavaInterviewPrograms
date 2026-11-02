# Maximum Subarray

Given an integer array `nums`, find the contiguous subarray with the largest sum and return that sum.

## Algorithm Strategy (Kadane's Algorithm)

1. Keep `current`, the best sum of a subarray that ends at the current index, and `best`, the best sum seen anywhere.
2. At each element decide whether to extend the previous subarray or start a new one: `current = max(num, current + num)`.
3. A negative running sum can never help a later subarray, which is exactly why the "start new" option is taken in that case.
4. Update `best = max(best, current)` and return it after the last element.

## Example walkthrough

- For `[-2, 1, -3, 4, -1, 2, 1, -5, 4]`: `current` is -2, 1, -2, 4, 3, 5, 6, 1, 5.
- The maximum is `6`, the subarray `[4, -1, 2, 1]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single pass.
- **Space Complexity:** `O(1)` — Two variables.

## Source Code

### MaximumSubarray.java

```java
import java.util.Arrays;

public class MaximumSubarray {

    public static void main(String[] args) {
        int[][] inputs = {{-2, 1, -3, 4, -1, 2, 1, -5, 4}, {1}, {5, 4, -1, 7, 8}, {-3, -2, -5}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + maxSubArray(nums));
            System.out.println();
        }
    }

    public static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        int current = nums[0];
        int best = nums[0];
        for (int i = 1; i < nums.length; i++) {
            current = Math.max(nums[i], current + nums[i]);
            best = Math.max(best, current);
        }
        return best;
    }
}
```
