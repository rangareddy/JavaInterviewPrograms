# Coin Change II

Given coins of different denominations and a total `amount`, return the number of combinations of coins that make up that amount (the order of coins does not matter). Return 0 if the amount cannot be made. Each coin can be used any number of times.

## Algorithm Strategy (Unbounded Knapsack Counting (Coins in the Outer Loop))

1. Let `dp[a]` be the number of combinations that make amount `a`; `dp[0] = 1` (use no coin).
2. Process the coins one at a time in the outer loop. For the current coin `c` and every amount `a` from `c` up to `amount`: `dp[a] += dp[a - c]` (all combinations of the earlier coins plus any number of this coin).
3. Because the coins are the outer loop, each combination is counted once regardless of order. (Swapping the loops would count permutations such as 1+2 and 2+1 separately.)
4. The answer is `dp[amount]`.

## Example walkthrough

- For amount 5 and coins `[1, 2, 5]`: with coin 1 only, `dp[5] = 1`; adding coin 2 makes it 3 (2+2+1, 2+1+1+1, 1x5); adding coin 5 makes it 4.
- Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(amount * coins)` — Two nested loops.
- **Space Complexity:** `O(amount)` — One array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

For each coin either skip it for good or use it once more.

```java
public static int changeRecursive(int amount, int[] coins) {
    return count(amount, coins, 0);
}

private static int count(int remaining, int[] coins, int i) {
    if (remaining == 0) return 1;
    if (remaining < 0 || i == coins.length) return 0;
    return count(remaining - coins[i], coins, i) + count(remaining, coins, i + 1);
}
```

- **Time Complexity:** O(2^(amount + n))
- **Space Complexity:** O(amount)

### Approach 2: Top-Down Memoization

Cache the count for each `(remaining, coin index)` pair.

```java
public static int changeMemo(int amount, int[] coins) {
    int[][] memo = new int[amount + 1][coins.length + 1];
    for (int[] row : memo) Arrays.fill(row, -1);
    return countMemo(amount, coins, 0, memo);
}

private static int countMemo(int remaining, int[] coins, int i, int[][] memo) {
    if (remaining == 0) return 1;
    if (remaining < 0 || i == coins.length) return 0;
    if (memo[remaining][i] >= 0) return memo[remaining][i];
    return memo[remaining][i] = countMemo(remaining - coins[i], coins, i, memo) + countMemo(remaining, coins, i + 1, memo);
}
```

- **Time Complexity:** O(amount * n)
- **Space Complexity:** O(amount * n)

### Approach 3: 2D Table

`dp[i][a]` is the number of ways to make `a` using the first `i` coins.

```java
public static int changeTable(int amount, int[] coins) {
    int n = coins.length;
    int[][] dp = new int[n + 1][amount + 1];
    for (int i = 0; i <= n; i++) dp[i][0] = 1;
    for (int i = 1; i <= n; i++)
        for (int a = 1; a <= amount; a++) {
            dp[i][a] = dp[i - 1][a];
            if (a >= coins[i - 1]) dp[i][a] += dp[i][a - coins[i - 1]];
        }
    return dp[n][amount];
}
```

- **Time Complexity:** O(amount * n)
- **Space Complexity:** O(amount * n)

### Approach 4: 1D Array (Optimized)

The table collapses to one row because row `i` only needs row `i - 1` and itself. This is the version implemented in the repository.

```java
public static int change(int amount, int[] coins) {
    int[] dp = new int[amount + 1];
    dp[0] = 1;
    for (int coin : coins)
        for (int a = coin; a <= amount; a++) dp[a] += dp[a - coin];
    return dp[amount];
}
```

- **Time Complexity:** O(amount * n)
- **Space Complexity:** O(amount)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(2^(amount + n)) | O(amount) |
| 2 | Top-Down Memoization | O(amount * n) | O(amount * n) |
| 3 | 2D Table | O(amount * n) | O(amount * n) |
| 4 | 1D Array (Optimized) | O(amount * n) | O(amount) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### CoinChangeII.java

```java
import java.util.Arrays;

public class CoinChangeII {

    public static void main(String[] args) {
        int[] amounts = {5, 3, 10, 0, 7};
        int[][] coins = {{1, 2, 5}, {2}, {10}, {7}, {2, 3}};
        for (int i = 0; i < amounts.length; i++) {
            System.out.println("Input  : amount = " + amounts[i] + ", coins = " + Arrays.toString(coins[i]));
            System.out.println("Output : " + change(amounts[i], coins[i]));
            System.out.println();
        }
    }

    public static int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int a = coin; a <= amount; a++) {
                dp[a] += dp[a - coin];
            }
        }
        return dp[amount];
    }
}
```
