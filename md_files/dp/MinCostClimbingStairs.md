# Min Cost Climbing Stairs

You are given an array `cost` where `cost[i]` is the price of stepping on stair `i`. After paying the price you may climb one or two stairs, and you may start from stair 0 or stair 1. Return the minimum cost to reach the top of the floor, which is one position beyond the last stair.

## Algorithm Strategy (Dynamic Programming with Two Variables)

1. Let `best(i)` be the minimum cost to stand on position `i` (before paying for stair `i`). The top is position `n`.
2. Positions 0 and 1 can be the starting point, so `best(0) = best(1) = 0`.
3. To arrive at position `i` you come from `i - 1` (paying `cost[i - 1]`) or from `i - 2` (paying `cost[i - 2]`): `best(i) = min(best(i - 1) + cost[i - 1], best(i - 2) + cost[i - 2])`.
4. Only the two previous values are needed, so keep them in two variables; `best(n)` is the answer.

## Example walkthrough

- For `cost = [10, 15, 20]`: `best(2) = min(0 + 15, 0 + 10) = 10`, `best(3) = min(10 + 20, 0 + 15) = 15`.
- Result: `15` (start at stair 1, pay 15, jump to the top).

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single pass.
- **Space Complexity:** `O(1)` — Two variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Recursion

Try both moves from every position and take the cheaper one; the same positions are recomputed many times.

```java
public static int minCostRecursive(int[] cost) {
    return Math.min(from(cost, 0), from(cost, 1));
}

private static int from(int[] cost, int i) {
    if (i >= cost.length) return 0;
    return cost[i] + Math.min(from(cost, i + 1), from(cost, i + 2));
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Top-Down Memoization

Cache the cost of finishing from each stair so that it is computed once.

```java
public static int minCostMemo(int[] cost) {
    int[] memo = new int[cost.length];
    Arrays.fill(memo, -1);
    return Math.min(finish(cost, 0, memo), finish(cost, 1, memo));
}

private static int finish(int[] cost, int i, int[] memo) {
    if (i >= cost.length) return 0;
    if (memo[i] >= 0) return memo[i];
    return memo[i] = cost[i] + Math.min(finish(cost, i + 1, memo), finish(cost, i + 2, memo));
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Bottom-Up Table

Fill an array where `dp[i]` is the cheapest way to stand on position `i`.

```java
public static int minCostTable(int[] cost) {
    int n = cost.length;
    int[] dp = new int[n + 1];
    for (int i = 2; i <= n; i++) dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
    return dp[n];
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Two Variables (Optimized)

Only the previous two values matter. This is the version implemented in the repository.

```java
public static int minCostClimbingStairs(int[] cost) {
    int twoBack = 0, oneBack = 0;
    for (int i = 2; i <= cost.length; i++) {
        int current = Math.min(oneBack + cost[i - 1], twoBack + cost[i - 2]);
        twoBack = oneBack;
        oneBack = current;
    }
    return oneBack;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Recursion | O(2^n) | O(n) |
| 2 | Top-Down Memoization | O(n) | O(n) |
| 3 | Bottom-Up Table | O(n) | O(n) |
| 4 | Two Variables (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### MinCostClimbingStairs.java

```java
import java.util.Arrays;

public class MinCostClimbingStairs {

    public static void main(String[] args) {
        int[][] inputs = {{10, 15, 20}, {1, 100, 1, 1, 1, 100, 1, 1, 100, 1}, {5, 5}, {0, 0, 0, 0}};
        for (int[] cost : inputs) {
            System.out.println("Input  : cost = " + Arrays.toString(cost));
            System.out.println("Output : " + minCostClimbingStairs(cost));
            System.out.println();
        }
    }

    public static int minCostClimbingStairs(int[] cost) {
        int twoBack = 0;        // best cost to stand on position i - 2
        int oneBack = 0;        // best cost to stand on position i - 1
        for (int i = 2; i <= cost.length; i++) {
            int current = Math.min(oneBack + cost[i - 1], twoBack + cost[i - 2]);
            twoBack = oneBack;
            oneBack = current;
        }
        return oneBack;
    }
}
```
