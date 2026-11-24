# Best Time to Buy and Sell Stock II

Given `prices[i]`, the price of a stock on day `i`, you may buy and sell as many times as you like, holding at most one share at a time. Return the maximum profit.

## Algorithm Strategy (Collect Every Rise)

1. Any profit over several days equals the sum of the daily differences along the way.
2. So adding every positive day-to-day difference gives the best possible total.
3. Loop from day 1: if `prices[i] > prices[i - 1]`, add the difference to the profit.
4. Never add a negative difference, which means you skip those days.

## Example walkthrough

- For `[7, 1, 5, 3, 6, 4]`: the rises are 1 to 5 (+4) and 3 to 6 (+3).
- Result: `7`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over the prices.
- **Space Complexity:** `O(1)` — Only a running total is kept.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force Recursion

Try buying or skipping on every day and every selling day. Exponential.

```java
public static int bruteForce(int[] p) {
    if (p == null) return 0;
    return best(p, 0);
}
private static int best(int[] p, int start) {
    if (start >= p.length) return 0;
    int max = 0;
    for (int buy = start; buy < p.length; buy++)
        for (int sell = buy + 1; sell < p.length; sell++)
            if (p[sell] > p[buy]) max = Math.max(max, p[sell] - p[buy] + best(p, sell + 1));
    return max;
}
```

- **Time Complexity:** O(n^n)
- **Space Complexity:** O(n)

### Approach 2: Peak and Valley

Buy at every valley and sell at the next peak.

```java
public static int peakValley(int[] p) {
    if (p == null || p.length < 2) return 0;
    int i = 0, profit = 0, n = p.length;
    while (i < n - 1) {
        while (i < n - 1 && p[i + 1] <= p[i]) i++;
        int valley = p[i];
        while (i < n - 1 && p[i + 1] >= p[i]) i++;
        profit += p[i] - valley;
    }
    return profit;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Sum of Positive Differences (Optimized)

Add every day-to-day rise. This is the version implemented in the repository.

```java
public static int sumRises(int[] p) {
    if (p == null || p.length < 2) return 0;
    int profit = 0;
    for (int i = 1; i < p.length; i++) if (p[i] > p[i - 1]) profit += p[i] - p[i - 1];
    return profit;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force Recursion | O(n^n) | O(n) |
| 2 | Peak and Valley | O(n) | O(1) |
| 3 | Sum of Positive Differences (Optimized) | O(n) | O(1) |

## Source Code

### BestTimeToBuyAndSellStockII.java

```java
import java.util.Arrays;

public class BestTimeToBuyAndSellStockII {

    public static void main(String[] args) {
        int[][] inputs = {{7, 1, 5, 3, 6, 4}, {1, 2, 3, 4, 5}, {7, 6, 4, 3, 1}, {5}, {}};
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
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: a climb from a valley to a peak equals the sum of its daily steps, so there is no need to find the valleys and peaks. Take every upward step, ignore the downward ones.

**Input:** `prices = [7, 1, 5, 3, 6, 4]`.

| Day | Price | Change | Action | Profit |
|-----|-------|--------|--------|--------|
| 1 | 1 | -6 | skip | 0 |
| 2 | 5 | +4 | add | 4 |
| 3 | 3 | -2 | skip | 4 |
| 4 | 6 | +3 | add | 7 |
| 5 | 4 | -2 | skip | 7 |

**Output:** `7`.
