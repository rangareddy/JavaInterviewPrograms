# Burst Balloons

There are `n` balloons painted with numbers `nums[i]`. Bursting balloon `i` earns `nums[i - 1] * nums[i] * nums[i + 1]` coins, where a missing neighbour counts as a balloon with value 1. Return the maximum number of coins you can collect by bursting all balloons in a smart order.

## Algorithm Strategy (Interval DP: Choose the Last Balloon to Burst)

1. Pad the array with a balloon of value 1 at both ends. Let `dp[l][r]` be the maximum coins from bursting every balloon strictly between `l` and `r`.
2. Think backwards: pick the balloon `k` (with `l < k < r`) that is burst <em>last</em> in the open interval. Its neighbours at that moment are exactly the borders `l` and `r`, and the two sides are independent sub-problems.
3. `dp[l][r] = max over k of dp[l][k] + nums[l] * nums[k] * nums[r] + dp[k][r]`.
4. Fill the table by increasing interval length. The answer is `dp[0][n + 1]`.

## Example walkthrough

- For `[3, 1, 5, 8]`: bursting 1, then 5, then 3, then 8 gives `3*1*5 + 3*5*8 + 1*3*8 + 1*8*1 = 15 + 120 + 24 + 8 = 167`.
- Result: `167`. For `[1, 5]` the best order gives `10`.

## Complexity Analysis

- **Time Complexity:** `O(n^3)` — Three nested loops: interval length, left border, last balloon.
- **Space Complexity:** `O(n^2)` — The table.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force over Burst Orders

Try bursting every remaining balloon first and recurse on the shorter list.

```java
public static int maxCoinsBrute(int[] nums) {
    List<Integer> list = new ArrayList<>();
    for (int n : nums) list.add(n);
    return burst(list);
}

private static int burst(List<Integer> list) {
    int best = 0;
    for (int i = 0; i < list.size(); i++) {
        int left = i == 0 ? 1 : list.get(i - 1);
        int right = i == list.size() - 1 ? 1 : list.get(i + 1);
        int value = list.remove(i);
        best = Math.max(best, left * value * right + burst(list));
        list.add(i, value);
    }
    return best;
}
```

- **Time Complexity:** O(n!)
- **Space Complexity:** O(n)

### Approach 2: Top-Down Memoization (Last Balloon)

Choose the last balloon to burst in an interval; the two sides become independent.

```java
public static int maxCoinsMemo(int[] nums) {
    int n = nums.length;
    int[] b = new int[n + 2];
    b[0] = b[n + 1] = 1;
    for (int i = 0; i < n; i++) b[i + 1] = nums[i];
    return solve(b, 0, n + 1, new int[n + 2][n + 2]);
}

private static int solve(int[] b, int left, int right, int[][] memo) {
    if (left + 1 == right) return 0;
    if (memo[left][right] != 0) return memo[left][right];
    int best = 0;
    for (int last = left + 1; last < right; last++)
        best = Math.max(best, solve(b, left, last, memo) + b[left] * b[last] * b[right] + solve(b, last, right, memo));
    return memo[left][right] = best;
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(n^2)

### Approach 3: Bottom-Up by Interval Length (Optimized)

Fill the table for increasing interval lengths. This is the version implemented in the repository.

```java
public static int maxCoins(int[] nums) {
    int n = nums.length;
    int[] balloons = new int[n + 2];
    balloons[0] = balloons[n + 1] = 1;
    for (int i = 0; i < n; i++) balloons[i + 1] = nums[i];
    int[][] dp = new int[n + 2][n + 2];
    for (int length = 2; length <= n + 1; length++)
        for (int left = 0; left + length <= n + 1; left++) {
            int right = left + length;
            for (int last = left + 1; last < right; last++)
                dp[left][right] = Math.max(dp[left][right], dp[left][last] + balloons[left] * balloons[last] * balloons[right] + dp[last][right]);
        }
    return dp[0][n + 1];
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(n^2)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force over Burst Orders | O(n!) | O(n) |
| 2 | Top-Down Memoization (Last Balloon) | O(n^3) | O(n^2) |
| 3 | Bottom-Up by Interval Length (Optimized) | O(n^3) | O(n^2) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### BurstBalloons.java

```java
import java.util.Arrays;

public class BurstBalloons {

    public static void main(String[] args) {
        int[][] inputs = {{3, 1, 5, 8}, {1, 5}, {7}, {}, {9, 76, 64, 21}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + maxCoins(nums));
            System.out.println();
        }
    }

    public static int maxCoins(int[] nums) {
        int n = nums.length;
        int[] balloons = new int[n + 2];
        balloons[0] = 1;
        balloons[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            balloons[i + 1] = nums[i];
        }

        int[][] dp = new int[n + 2][n + 2];
        for (int length = 2; length <= n + 1; length++) {
            for (int left = 0; left + length <= n + 1; left++) {
                int right = left + length;
                for (int last = left + 1; last < right; last++) {
                    int coins = dp[left][last] + balloons[left] * balloons[last] * balloons[right] + dp[last][right];
                    dp[left][right] = Math.max(dp[left][right], coins);
                }
            }
        }
        return dp[0][n + 1];
    }
}
```
