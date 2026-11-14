# Coin Change

Given coins of different denominations and a total `amount`, return the fewest number of coins needed to make that amount, or `-1` if it cannot be made. Each coin can be used any number of times.

## Algorithm Strategy (Bottom-Up Dynamic Programming)

1. Let `dp[a]` be the fewest coins needed to make amount `a`. Initialize `dp[0] = 0` and every other entry with a value larger than any answer (`amount + 1`).
2. For every amount `a` from 1 to `amount` and every coin `c <= a`: `dp[a] = min(dp[a], dp[a - c] + 1)`, i.e. use coin `c` and the best solution of the remaining amount.
3. If `dp[amount]` is still greater than `amount`, the amount cannot be made, so return `-1`.
4. Otherwise return `dp[amount]`.

## Example walkthrough

- For coins `[1, 2, 5]` and amount 11: `dp[5] = 1`, `dp[10] = 2`, `dp[11] = dp[10] + 1 = 3`.
- Result: `3` (5 + 5 + 1).

## Complexity Analysis

- **Time Complexity:** `O(amount * coins)` — Two nested loops.
- **Space Complexity:** `O(amount)` — The `dp` array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Recursion

Try every coin for the remaining amount and take the best result. Exponential, because the same remaining amounts are solved repeatedly.

```java
public static int coinChangeRecursive(int[] coins, int amount) {
    if (amount == 0) return 0;
    int best = Integer.MAX_VALUE;
    for (int coin : coins) {
        if (coin <= amount) {
            int sub = coinChangeRecursive(coins, amount - coin);
            if (sub >= 0) best = Math.min(best, sub + 1);
        }
    }
    return best == Integer.MAX_VALUE ? -1 : best;
}
```

- **Time Complexity:** O(coins^amount)
- **Space Complexity:** O(amount)

### Approach 2: Memoization (Top-Down DP)

The same recursion with a cache indexed by the remaining amount.

```java
public static int coinChangeMemo(int[] coins, int amount) {
    return solve(coins, amount, new int[amount + 1]);
}

// cache: 0 = not computed, -2 = impossible, otherwise coins + 1
private static int solve(int[] coins, int remaining, int[] cache) {
    if (remaining == 0) return 0;
    if (cache[remaining] != 0) return cache[remaining] == -2 ? -1 : cache[remaining] - 1;
    int best = Integer.MAX_VALUE;
    for (int coin : coins) {
        if (coin <= remaining) {
            int sub = solve(coins, remaining - coin, cache);
            if (sub >= 0) best = Math.min(best, sub + 1);
        }
    }
    cache[remaining] = best == Integer.MAX_VALUE ? -2 : best + 1;
    return best == Integer.MAX_VALUE ? -1 : best;
}
```

- **Time Complexity:** O(amount * coins)
- **Space Complexity:** O(amount)

### Approach 3: Bottom-Up DP (Optimized)

`dp[a]` is the fewest coins for amount `a`; fill it from 0 upwards. This is the version implemented in the repository.

```java
public static int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);
    dp[0] = 0;
    for (int a = 1; a <= amount; a++) {
        for (int coin : coins) {
            if (coin <= a) dp[a] = Math.min(dp[a], dp[a - coin] + 1);
        }
    }
    return dp[amount] > amount ? -1 : dp[amount];
}
```

- **Time Complexity:** O(amount * coins)
- **Space Complexity:** O(amount)

### Approach 4: BFS over the Remaining Amounts

Treat each remaining amount as a node and each coin as an edge. The first time amount 0 is reached, the number of levels is the fewest coins.

```java
public static int coinChangeBfs(int[] coins, int amount) {
    if (amount == 0) return 0;
    boolean[] visited = new boolean[amount + 1];
    Queue<Integer> queue = new LinkedList<>();
    queue.add(amount);
    visited[amount] = true;
    int level = 0;
    while (!queue.isEmpty()) {
        level++;
        for (int i = queue.size(); i > 0; i--) {
            int remaining = queue.poll();
            for (int coin : coins) {
                int next = remaining - coin;
                if (next == 0) return level;
                if (next > 0 && !visited[next]) { visited[next] = true; queue.add(next); }
            }
        }
    }
    return -1;
}
```

- **Time Complexity:** O(amount * coins)
- **Space Complexity:** O(amount)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Recursion | O(coins^amount) | O(amount) |
| 2 | Memoization (Top-Down DP) | O(amount * coins) | O(amount) |
| 3 | Bottom-Up DP (Optimized) | O(amount * coins) | O(amount) |
| 4 | BFS over the Remaining Amounts | O(amount * coins) | O(amount) |

## Source Code

### CoinChange.java

```java
import java.util.Arrays;

public class CoinChange {

    public static void main(String[] args) {
        int[][] coins = {{1, 2, 5}, {2}, {1}, {2, 5, 10}};
        int[] amounts = {11, 3, 0, 27};
        for (int i = 0; i < coins.length; i++) {
            System.out.println("Input  : coins = " + Arrays.toString(coins[i]) + ", amount = " + amounts[i]);
            System.out.println("Output : " + coinChange(coins[i], amounts[i]));
            System.out.println();
        }
    }

    public static int coinChange(int[] coins, int amount) {
        if (amount < 0) {
            return -1;
        }

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);        // amount + 1 means "not reachable"
        dp[0] = 0;
        for (int a = 1; a <= amount; a++) {
            for (int coin : coins) {
                if (coin <= a) {
                    dp[a] = Math.min(dp[a], dp[a - coin] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: `dp[a]` is the fewest coins that make amount `a`. To make `a`, pick a last coin `c` and add one coin to the best answer for `a - c`; take the minimum over all coins. Unreachable amounts hold the sentinel `amount + 1`, which is larger than any real answer, so a plain `min` ignores them.

**Input:** `coins = [1, 2, 5]`, `amount = 11`. Start with `dp[0] = 0` and every other cell `12` (sentinel).

| `a` | Candidates `dp[a - c] + 1` for `c = 1, 2, 5` | `dp[a]` |
|-----|-----------------------------------------------|---------|
| 1 | `dp[0]+1 = 1` | 1 |
| 2 | `dp[1]+1 = 2`, `dp[0]+1 = 1` | 1 |
| 3 | `dp[2]+1 = 2`, `dp[1]+1 = 2` | 2 |
| 4 | `dp[3]+1 = 3`, `dp[2]+1 = 2` | 2 |
| 5 | `dp[4]+1 = 3`, `dp[3]+1 = 3`, `dp[0]+1 = 1` | 1 |
| 6 | `dp[5]+1 = 2`, `dp[4]+1 = 3`, `dp[1]+1 = 2` | 2 |
| 7 | `dp[6]+1 = 3`, `dp[5]+1 = 2`, `dp[2]+1 = 2` | 2 |
| 8 | `dp[7]+1 = 3`, `dp[6]+1 = 3`, `dp[3]+1 = 3` | 3 |
| 9 | `dp[8]+1 = 4`, `dp[7]+1 = 3`, `dp[4]+1 = 3` | 3 |
| 10 | `dp[9]+1 = 4`, `dp[8]+1 = 4`, `dp[5]+1 = 2` | 2 |
| 11 | `dp[10]+1 = 3`, `dp[9]+1 = 4`, `dp[6]+1 = 3` | 3 |

```text
a:    0  1  2  3  4  5  6  7  8  9  10 11
dp:  [0, 1, 1, 2, 2, 1, 2, 2, 3, 3, 2, 3]
                                  ^  ^
                       dp[11] = dp[10] + 1  (coin 1)   -> 5 + 5 + 1
```

**Edge cases.** `amount = 0` returns `0` (the loop never runs and `dp[0] = 0`). A negative amount returns `-1` immediately. If no combination works (for example `coins = [2]`, `amount = 3`), `dp[amount]` stays `amount + 1`, which is greater than `amount`, so the method returns `-1`.

**Result:** `coinChange([1, 2, 5], 11)` returns `3`, matching the class output (the class also returns `-1` for `[2], 3` and `0` for `[1], 0`).
