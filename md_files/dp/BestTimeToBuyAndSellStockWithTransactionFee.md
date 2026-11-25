# Best Time to Buy and Sell Stock with Transaction Fee

You are given an array `prices` where `prices[i]` is the price of a stock on day `i`, and an integer `fee` representing a transaction fee. Find the maximum profit you can achieve. You may complete as many transactions as you like, but you must pay the fee for each transaction, and you may not hold more than one share at a time.

## Algorithm Strategy (Track Cash and Holding States)

1. Let `cash` be the best profit when you do not hold a share, and `hold` the best profit when you do. Initially `cash = 0` and `hold = -prices[0]`.
2. For each later price, either sell: `cash = max(cash, hold + price - fee)` (the fee is paid on the sale), or keep.
3. Either buy: `hold = max(hold, cash - price)`, or keep.
4. The answer is `cash` after the last day.

## Example walkthrough

- For `[1, 3, 2, 8, 4, 9]` and `fee = 2`: buy at 1, sell at 8 (profit 5), buy at 4, sell at 9 (profit 3).
- Result: `8`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Two variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion over Days and State

Try buy, sell or skip on each day. Exponential.

```java
public static int recursive(int[] p, int fee) { return go(p, fee, 0, false); }
private static int go(int[] p, int fee, int i, boolean holding) { if (i == p.length) return 0; int skip = go(p, fee, i + 1, holding); return holding ? Math.max(skip, p[i] - fee + go(p, fee, i + 1, false)) : Math.max(skip, -p[i] + go(p, fee, i + 1, true)); }
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Two-State DP with Two Variables (Optimized)

Track the best profit while holding and while not holding. This is the version implemented in the repository.

```java
public static int states(int[] p, int fee) { if (p.length < 2) return 0; int cash = 0, hold = -p[0]; for (int i = 1; i < p.length; i++) { cash = Math.max(cash, hold + p[i] - fee); hold = Math.max(hold, cash - p[i]); } return cash; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion over Days and State | O(2^n) | O(n) |
| 2 | Two-State DP with Two Variables (Optimized) | O(n) | O(1) |

## Source Code

### BestTimeToBuyAndSellStockWithTransactionFee.java

```java
import java.util.Arrays;

public class BestTimeToBuyAndSellStockWithTransactionFee {

    public static void main(String[] args) {
        int[][] prices = {{1, 3, 2, 8, 4, 9}, {1, 3, 7, 5, 10, 3}, {5}, {}};
        int[] fees = {2, 3, 1, 1};
        for (int i = 0; i < prices.length; i++) {
            System.out.println("Input  : prices = " + Arrays.toString(prices[i]) + ", fee = " + fees[i]);
            System.out.println("Output : " + maxProfit(prices[i], fees[i]));
            System.out.println();
        }
    }

    public static int maxProfit(int[] prices, int fee) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int cash = 0;
        int hold = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            cash = Math.max(cash, hold + prices[i] - fee);
            hold = Math.max(hold, cash - prices[i]);
        }
        return cash;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: at the end of any day you are in one of two states, holding a share or not, and only the best profit in each state matters for the future. Each day, the new best profit in a state comes from either staying in it or moving over from the other state with a buy or a sell. Charging the fee at the sale makes every transaction pay once.

**Input:** `prices = [1, 3, 2, 8, 4, 9]`, `fee = 2`.

| Day | Price | `cash` | `hold` |
|-----|-------|--------|--------|
| 0 | 1 | 0 | -1 |
| 1 | 3 | 0 | -1 |
| 2 | 2 | 0 | -1 |
| 3 | 8 | 5 | -1 |
| 4 | 4 | 5 | 1 |
| 5 | 9 | 8 | 1 |

**Output:** `8`.
