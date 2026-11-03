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

### Approach 3: Memoized Recursion on Both Ranges

Top-down DP for the two linear sub-problems (without the last house, without the first house) with a cache per run.

```java
public static int robMemo(int[] nums) {
    int n = nums.length;
    if (n == 1) return nums[0];
    return Math.max(solve(nums, 0, n - 2, new int[n]), solve(nums, 1, n - 1, new int[n]));
}

private static int solve(int[] nums, int i, int last, int[] memo) {
    if (i > last) return 0;
    if (memo[i] != 0) return memo[i];
    return memo[i] = Math.max(nums[i] + solve(nums, i + 2, last, memo), solve(nums, i + 1, last, memo));
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: State DP with a "First House Robbed" Flag

Run one DP whose state remembers whether the first house was robbed. If it was, the last house is forbidden; if it was not, the first house is simply skipped. The answer is the better of the two final states.

```java
public static int robStates(int[] nums) {
    int n = nums.length;
    if (n == 1) return nums[0];
    // first house robbed: rob nums[0], then houses 2..n-2 are free
    int prev2 = nums[0], prev1 = nums[0];            // best up to house 0 and up to house 1 (house 1 is forbidden)
    for (int i = 2; i <= n - 2; i++) {
        int best = Math.max(prev1, prev2 + nums[i]);
        prev2 = prev1;
        prev1 = best;
    }
    int robbedFirst = prev1;
    // first house skipped: houses 1..n-1 are free
    prev2 = 0;
    prev1 = 0;
    for (int i = 1; i <= n - 1; i++) {
        int best = Math.max(prev1, prev2 + nums[i]);
        prev2 = prev1;
        prev1 = best;
    }
    return Math.max(robbedFirst, prev1);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 5: Both Ranges in a Single Loop

Instead of two passes, advance the two linear runs together in one loop over the houses.

```java
public static int robSingleLoop(int[] nums) {
    int n = nums.length;
    if (n == 1) return nums[0];
    int a2 = 0, a1 = 0;      // run over houses 0 .. n-2
    int b2 = 0, b1 = 0;      // run over houses 1 .. n-1
    for (int i = 0; i < n; i++) {
        if (i < n - 1) { int best = Math.max(a1, a2 + nums[i]); a2 = a1; a1 = best; }
        if (i > 0) { int best = Math.max(b1, b2 + nums[i]); b2 = b1; b1 = best; }
    }
    return Math.max(a1, b1);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 6: Reuse the Linear House Robber on Array Copies

Cut out the two sub-arrays with `Arrays.copyOfRange` and call the plain House Robber solution on each of them. Clear and reusable, at the cost of two copies.

```java
public static int robCopies(int[] nums) {
    if (nums.length == 1) return nums[0];
    return Math.max(robLinear(Arrays.copyOfRange(nums, 0, nums.length - 1)), robLinear(Arrays.copyOfRange(nums, 1, nums.length)));
}

private static int robLinear(int[] nums) {
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
- **Space Complexity:** O(n)

### Approach 7: Two Linear Runs with Two Variables (Optimized)

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
| 3 | Memoized Recursion on Both Ranges | O(n) | O(n) |
| 4 | State DP with a "First House Robbed" Flag | O(n) | O(1) |
| 5 | Both Ranges in a Single Loop | O(n) | O(1) |
| 6 | Reuse the Linear House Robber on Array Copies | O(n) | O(n) |
| 7 | Two Linear Runs with Two Variables (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
