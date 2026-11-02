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
