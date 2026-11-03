# Best Time to Buy and Sell Stock

Given an array `prices` where `prices[i]` is the price of a stock on day `i`, return the maximum profit from buying on one day and selling on a later day (0 if no profit is possible).

## Algorithm Strategy (Track the Minimum So Far)

1. Scan the prices once from left to right, remembering the lowest price seen so far in `minPrice`.
2. For every day, the best profit if we sell today is `price - minPrice`; keep the maximum of these values in `maxProfit`.
3. Update `minPrice` when a lower price appears. Selling can only happen after buying because `minPrice` only contains earlier days.
4. Return `maxProfit` (it stays 0 when prices never rise).

## Example walkthrough

- For `[7, 1, 5, 3, 6, 4]`: the minimum becomes 1 on day 2; selling at 5 gives 4 and selling at 6 gives 5.
- Result: `5` (buy at 1, sell at 6).

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single pass over the prices.
- **Space Complexity:** `O(1)` — Only two variables are kept.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Try every pair of days `(buy, sell)` with `buy < sell` and keep the largest difference.

```java
public static int maxProfitBrute(int[] prices) {
    int best = 0;
    for (int buy = 0; buy < prices.length; buy++) {
        for (int sell = buy + 1; sell < prices.length; sell++) {
            best = Math.max(best, prices[sell] - prices[buy]);
        }
    }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Kadane on Daily Differences

Buying on day `i` and selling on day `j` earns the sum of the daily price changes between them, so the answer is the maximum subarray sum of the differences (never less than 0).

```java
public static int maxProfitKadane(int[] prices) {
    int best = 0, current = 0;
    for (int i = 1; i < prices.length; i++) {
        current = Math.max(0, current + prices[i] - prices[i - 1]);
        best = Math.max(best, current);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Divide and Conquer

Split the days in two halves. The best trade is inside the left half, inside the right half, or buys in the left half and sells in the right half (the highest right price minus the lowest left price). Each call returns `{min, max, best}` of its range.

```java
public static int maxProfitDivide(int[] prices) {
    if (prices.length < 2) return 0;
    return divide(prices, 0, prices.length - 1)[2];
}

// returns {minPrice, maxPrice, bestProfit}
private static int[] divide(int[] p, int lo, int hi) {
    if (lo == hi) return new int[]{p[lo], p[lo], 0};
    int mid = (lo + hi) / 2;
    int[] left = divide(p, lo, mid), right = divide(p, mid + 1, hi);
    return new int[]{Math.min(left[0], right[0]), Math.max(left[1], right[1]),
            Math.max(Math.max(left[2], right[2]), right[1] - left[0])};
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(log n)

### Approach 4: Prefix Minimum and Suffix Maximum Arrays

Precompute the cheapest price up to each day and the highest price from each day on; the best profit is the largest difference between the two at the same day.

```java
public static int maxProfitArrays(int[] prices) {
    int n = prices.length;
    if (n < 2) return 0;
    int[] minLeft = new int[n], maxRight = new int[n];
    minLeft[0] = prices[0];
    for (int i = 1; i < n; i++) minLeft[i] = Math.min(minLeft[i - 1], prices[i]);
    maxRight[n - 1] = prices[n - 1];
    for (int i = n - 2; i >= 0; i--) maxRight[i] = Math.max(maxRight[i + 1], prices[i]);
    int best = 0;
    for (int i = 0; i < n; i++) best = Math.max(best, maxRight[i] - minLeft[i]);
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 5: State-Machine DP (Hold / Sold)

Model the single transaction as two states: `hold` is the best balance while holding the stock (we paid the price) and `sold` the best balance after selling. Update both for every price.

```java
public static int maxProfitStates(int[] prices) {
    int hold = Integer.MIN_VALUE, sold = 0;
    for (int price : prices) {
        hold = Math.max(hold, -price);          // buy today (only one transaction allowed)
        sold = Math.max(sold, hold + price);    // sell today
    }
    return sold;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 6: Monotonic Stack

Keep a stack of prices in increasing order by popping every price that is not lower than the new one. The bottom of the stack is always the lowest price so far, so the profit of selling today is today's price minus the bottom.

```java
public static int maxProfitStack(int[] prices) {
    Deque<Integer> stack = new ArrayDeque<>();
    int best = 0;
    for (int price : prices) {
        while (!stack.isEmpty() && stack.peekLast() >= price) stack.pollLast();
        stack.addLast(price);
        best = Math.max(best, price - stack.peekFirst());
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 7: Track the Minimum So Far (Optimized)

Remember the cheapest price seen so far and, for every day, the profit of selling today. This is the version implemented in the repository.

```java
public static int maxProfit(int[] prices) {
    if (prices == null || prices.length < 2) return 0;
    int minPrice = prices[0], best = 0;
    for (int i = 1; i < prices.length; i++) {
        best = Math.max(best, prices[i] - minPrice);
        minPrice = Math.min(minPrice, prices[i]);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Kadane on Daily Differences | O(n) | O(1) |
| 3 | Divide and Conquer | O(n) | O(log n) |
| 4 | Prefix Minimum and Suffix Maximum Arrays | O(n) | O(n) |
| 5 | State-Machine DP (Hold / Sold) | O(n) | O(1) |
| 6 | Monotonic Stack | O(n) | O(n) |
| 7 | Track the Minimum So Far (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### BestTimeToBuyAndSellStock.java

```java
import java.util.Arrays;

public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        int[][] inputs = {{7, 1, 5, 3, 6, 4}, {7, 6, 4, 3, 1}};
        for (int[] prices : inputs) {
            System.out.println("Input  : prices = " + Arrays.toString(prices));
            System.out.println("Output : " + maxProfit(prices));
            System.out.println();
        }
    }

    public static int maxProfit(int[] prices) {
        // Guard Clause: at least two days are needed to buy and sell
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int minPrice = prices[0];
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
            minPrice = Math.min(minPrice, prices[i]);
        }
        return maxProfit;
    }
}
```
