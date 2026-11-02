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
