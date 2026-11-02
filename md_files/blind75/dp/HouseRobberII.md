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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force (Try Excluding Each End with Recursion)

Because the first and the last house cannot both be robbed, solve the linear problem twice with plain recursion: once without the last house and once without the first.

```java
public static int robCircleRecursive(int[] nums) {
    if (nums.length == 1) return nums[0];
    return Math.max(solve(nums, 0, nums.length - 2), solve(nums, 1, nums.length - 1));
}

private static int solve(int[] nums, int i, int last) {
    if (i > last) return 0;
    return Math.max(nums[i] + solve(nums, i + 2, last), solve(nums, i + 1, last));
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Two Linear Runs with a DP Table

Use the tabulated House Robber on the two ranges `0..n-2` and `1..n-1`.

```java
public static int robCircleTable(int[] nums) {
    if (nums.length == 1) return nums[0];
    return Math.max(robRangeTable(nums, 0, nums.length - 2), robRangeTable(nums, 1, nums.length - 1));
}

private static int robRangeTable(int[] nums, int from, int to) {
    int length = to - from + 1;
    int[] dp = new int[length + 2];
    for (int i = 2; i <= length + 1; i++) dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[from + i - 2]);
    return dp[length + 1];
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Two Linear Runs with Two Variables (Optimized)

The same two ranges, each solved with two rolling variables. This is the version implemented in the repository.

```java
public static int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
    return Math.max(robRange(nums, 0, nums.length - 2), robRange(nums, 1, nums.length - 1));
}

private static int robRange(int[] nums, int from, int to) {
    int prev2 = 0, prev1 = 0;
    for (int i = from; i <= to; i++) {
        int best = Math.max(prev1, prev2 + nums[i]);
        prev2 = prev1;
        prev1 = best;
    }
    return prev1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (Try Excluding Each End with Recursion) | O(2^n) | O(n) |
| 2 | Two Linear Runs with a DP Table | O(n) | O(n) |
| 3 | Two Linear Runs with Two Variables (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

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
