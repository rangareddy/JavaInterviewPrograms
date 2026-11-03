# Best Time to Buy and Sell Stock with Cooldown

Given an array `prices` where `prices[i]` is the price of a stock on day `i`, find the maximum profit when you may complete as many transactions as you like (buy one and sell one share repeatedly) with the restrictions that you cannot hold more than one share at a time and, after you sell, you cannot buy on the next day (a one-day cooldown).

## Algorithm Strategy (State Machine DP (Hold, Sold, Rest))

1. Each day you are in one of three states: `hold` (you own a share), `sold` (you sold today, so tomorrow is the cooldown) and `rest` (you own nothing and may buy). Keep the best profit for each state.
2. `sold(i) = hold(i - 1) + prices[i]`: you must have been holding yesterday.
3. `hold(i) = max(hold(i - 1), rest(i - 1) - prices[i])`: keep the share, or buy today (only allowed from the rest state, not right after a sale).
4. `rest(i) = max(rest(i - 1), sold(i - 1))`: stay out, or come out of the cooldown. The answer is `max(sold, rest)` on the last day.

## Example walkthrough

- For `[1, 2, 3, 0, 2]`: buy at 1, sell at 2, cooldown, buy at 0, sell at 2 gives profit `1 + 2 = 3`.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single pass.
- **Space Complexity:** `O(1)` — Three variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion with State

At each day choose to skip, buy (if not holding) or sell (if holding, then skip a day).

```java
public static int maxProfitRecursive(int[] prices) {
    return go(prices, 0, false);
}

private static int go(int[] prices, int day, boolean holding) {
    if (day >= prices.length) return 0;
    int skip = go(prices, day + 1, holding);
    if (holding) return Math.max(skip, prices[day] + go(prices, day + 2, false));
    return Math.max(skip, -prices[day] + go(prices, day + 1, true));
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Top-Down Memoization

Cache the result for each `(day, holding)` state.

```java
public static int maxProfitMemo(int[] prices) {
    Integer[][] memo = new Integer[prices.length + 2][2];
    return goMemo(prices, 0, 0, memo);
}

private static int goMemo(int[] prices, int day, int holding, Integer[][] memo) {
    if (day >= prices.length) return 0;
    if (memo[day][holding] != null) return memo[day][holding];
    int skip = goMemo(prices, day + 1, holding, memo);
    int act = holding == 1 ? prices[day] + goMemo(prices, day + 2, 0, memo) : -prices[day] + goMemo(prices, day + 1, 1, memo);
    return memo[day][holding] = Math.max(skip, act);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Bottom-Up Arrays

Keep the best profit of each state for every day in arrays.

```java
public static int maxProfitArrays(int[] prices) {
    int n = prices.length;
    if (n < 2) return 0;
    int[] hold = new int[n], sold = new int[n], rest = new int[n];
    hold[0] = -prices[0];
    for (int i = 1; i < n; i++) {
        sold[i] = hold[i - 1] + prices[i];
        hold[i] = Math.max(hold[i - 1], rest[i - 1] - prices[i]);
        rest[i] = Math.max(rest[i - 1], sold[i - 1]);
    }
    return Math.max(sold[n - 1], rest[n - 1]);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Three Variables (Optimized)

Only yesterday's states are needed. This is the version implemented in the repository.

```java
public static int maxProfit(int[] prices) {
    if (prices == null || prices.length < 2) return 0;
    int hold = -prices[0], sold = 0, rest = 0;
    for (int i = 1; i < prices.length; i++) {
        int previousSold = sold;
        sold = hold + prices[i];
        hold = Math.max(hold, rest - prices[i]);
        rest = Math.max(rest, previousSold);
    }
    return Math.max(sold, rest);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion with State | O(2^n) | O(n) |
| 2 | Top-Down Memoization | O(n) | O(n) |
| 3 | Bottom-Up Arrays | O(n) | O(n) |
| 4 | Three Variables (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### BestTimeToBuyAndSellStockWithCooldown.java

```java
import java.util.Arrays;

public class BestTimeToBuyAndSellStockWithCooldown {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 0, 2}, {1}, {1, 2, 4}, {6, 1, 6, 4, 3, 0, 2}};
        for (int[] prices : inputs) {
            System.out.println("Input  : prices = " + Arrays.toString(prices));
            System.out.println("Output : " + maxProfit(prices));
            System.out.println();
        }
    }

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int hold = -prices[0];      // best profit while holding a share
        int sold = 0;               // best profit on a day when a share was just sold
        int rest = 0;               // best profit while holding nothing and free to buy
        for (int i = 1; i < prices.length; i++) {
            int previousSold = sold;
            sold = hold + prices[i];
            hold = Math.max(hold, rest - prices[i]);
            rest = Math.max(rest, previousSold);
        }
        return Math.max(sold, rest);
    }
}
```
