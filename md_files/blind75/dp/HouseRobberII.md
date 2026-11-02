# House Robber II

Like House Robber, but the houses are arranged in a circle: the first and the last house are neighbours. Return the maximum money that can be robbed without alerting the police.

## Algorithm Strategy (Two Linear House Robber Runs)

1. In a circle the first and the last house cannot both be robbed.
2. Therefore the answer is the better of two linear problems: rob houses `0 .. n - 2` (last house excluded) or rob houses `1 .. n - 1` (first house excluded).
3. Solve each linear problem with the House Robber recurrence `best = max(best(i - 1), best(i - 2) + nums[i])`.
4. A single house has no neighbour, so return its money directly.

## Example walkthrough

- For `[2, 3, 2]`: houses `[2, 3]` give 3 and `[3, 2]` give 3, while robbing the first and last (2 + 2) is not allowed.
- Result: `3`. For `[1, 2, 3, 1]`: `max(rob([1,2,3]) = 4, rob([2,3,1]) = 3) = 4`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Two linear passes.
- **Space Complexity:** `O(1)` — A few variables.

## Source Code

### HouseRobberII.java

```java
import java.util.Arrays;

public class HouseRobberII {

    public static void main(String[] args) {
        int[][] inputs = {{2, 3, 2}, {1, 2, 3, 1}, {1, 2, 3}, {7}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + rob(nums));
            System.out.println();
        }
    }

    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        return Math.max(robRange(nums, 0, nums.length - 2), robRange(nums, 1, nums.length - 1));
    }

    // House Robber on the houses from..to (inclusive)
    private static int robRange(int[] nums, int from, int to) {
        int prev2 = 0;
        int prev1 = 0;
        for (int i = from; i <= to; i++) {
            int best = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = best;
        }
        return prev1;
    }
}
```
