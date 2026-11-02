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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Recursion

At each house either rob it and skip the next one, or skip it. Exponential.

```java
public static int robRecursive(int[] nums) {
    return solve(nums, 0);
}

private static int solve(int[] nums, int i) {
    if (i >= nums.length) return 0;
    return Math.max(nums[i] + solve(nums, i + 2), solve(nums, i + 1));
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Memoization (Top-Down DP)

Cache the best amount that can be robbed starting from every house.

```java
public static int robMemo(int[] nums) {
    return solve(nums, 0, new int[nums.length]);
}

private static int solve(int[] nums, int i, int[] memo) {
    if (i >= nums.length) return 0;
    if (memo[i] != 0) return memo[i];
    return memo[i] = Math.max(nums[i] + solve(nums, i + 2, memo), solve(nums, i + 1, memo));
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Tabulation (Bottom-Up DP)

`dp[i]` is the best amount for the first `i` houses: `max(dp[i - 1], dp[i - 2] + nums[i - 1])`.

```java
public static int robTable(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n + 2];
    for (int i = 2; i <= n + 1; i++) dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 2]);
    return dp[n + 1];
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Two Variables (Optimized)

The recurrence only looks two houses back, so two variables are enough. This is the version implemented in the repository.

```java
public static int rob(int[] nums) {
    int prev2 = 0, prev1 = 0;
    for (int money : nums) {
        int best = Math.max(prev1, prev2 + money);
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
| 1 | Plain Recursion | O(2^n) | O(n) |
| 2 | Memoization (Top-Down DP) | O(n) | O(n) |
| 3 | Tabulation (Bottom-Up DP) | O(n) | O(n) |
| 4 | Two Variables (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

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
