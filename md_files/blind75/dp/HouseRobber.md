# House Robber

Houses along a street hold some amount of money, but adjacent houses have connected alarms. Given the amounts, return the maximum money that can be robbed without robbing two adjacent houses.

## Algorithm Strategy (Dynamic Programming (Take or Skip))

1. At house `i` there are two choices: skip it (keep the best of the first `i - 1` houses) or rob it (its money plus the best of the first `i - 2` houses).
2. So `best(i) = max(best(i - 1), best(i - 2) + nums[i])`.
3. Only the last two results are required, so keep them in two variables `skip` and `take`.
4. After the last house, the larger value is the answer.

## Example walkthrough

- For `[2, 7, 9, 3, 1]`: best values per house are 2, 7, 11, 11, 12.
- Result: `12` (houses with 2, 9 and 1).

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single pass.
- **Space Complexity:** `O(1)` — Two variables.

## Source Code

### HouseRobber.java

```java
import java.util.Arrays;

public class HouseRobber {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 1}, {2, 7, 9, 3, 1}, {5}, {}};
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

        int prev2 = 0;      // best up to house i - 2
        int prev1 = 0;      // best up to house i - 1
        for (int money : nums) {
            int best = Math.max(prev1, prev2 + money);
            prev2 = prev1;
            prev1 = best;
        }
        return prev1;
    }
}
```
