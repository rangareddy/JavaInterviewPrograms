# 0/1 Knapsack

You are given `n` items, each with a weight and a value, and a bag that can carry a total weight of at most `capacity`. Every item can be taken at most once. Find the maximum total value that fits in the bag.

## Algorithm Strategy (Dynamic Programming over Capacity)

1. Let `dp[w]` be the best value achievable with total weight at most `w`, using the items processed so far. Initially all values are 0 (no items).
2. For every item with weight `wt` and value `val`, update the capacities from `capacity` down to `wt`: `dp[w] = max(dp[w], dp[w - wt] + val)` (skip the item, or take it on top of the best solution for the remaining capacity).
3. Iterate the capacity downwards so that `dp[w - wt]` still refers to the state before this item was considered; going upwards would allow the same item to be taken several times (the unbounded knapsack).
4. The answer is `dp[capacity]`.

## Example walkthrough

- For weights `[1, 3, 4, 5]`, values `[1, 4, 5, 7]` and capacity 7: taking the items of weight 3 and 4 gives value `4 + 5 = 9`, which is the best.
- With capacity 0 nothing fits and the answer is 0.

## Complexity Analysis

- **Time Complexity:** `O(n * capacity)` — For every item all capacities are updated.
- **Space Complexity:** `O(capacity)` — A single row of the table.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

For each item decide to skip it or take it.

```java
public static int knapsackRecursive(int[] weights, int[] values, int capacity) {
    return best(weights, values, 0, capacity);
}

private static int best(int[] weights, int[] values, int i, int capacity) {
    if (i == weights.length) return 0;
    int skip = best(weights, values, i + 1, capacity);
    if (weights[i] > capacity) return skip;
    return Math.max(skip, values[i] + best(weights, values, i + 1, capacity - weights[i]));
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Top-Down Memoization

Cache the result for each `(item, remaining capacity)`.

```java
public static int knapsackMemo(int[] weights, int[] values, int capacity) {
    int[][] memo = new int[weights.length + 1][capacity + 1];
    for (int[] row : memo) Arrays.fill(row, -1);
    return bestMemo(weights, values, 0, capacity, memo);
}

private static int bestMemo(int[] weights, int[] values, int i, int capacity, int[][] memo) {
    if (i == weights.length) return 0;
    if (memo[i][capacity] >= 0) return memo[i][capacity];
    int result = bestMemo(weights, values, i + 1, capacity, memo);
    if (weights[i] <= capacity) result = Math.max(result, values[i] + bestMemo(weights, values, i + 1, capacity - weights[i], memo));
    return memo[i][capacity] = result;
}
```

- **Time Complexity:** O(n * capacity)
- **Space Complexity:** O(n * capacity)

### Approach 3: 2D Table

`dp[i][w]` is the best value using the first `i` items with capacity `w`.

```java
public static int knapsackTable(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[][] dp = new int[n + 1][capacity + 1];
    for (int i = 1; i <= n; i++)
        for (int w = 0; w <= capacity; w++) {
            dp[i][w] = dp[i - 1][w];
            if (weights[i - 1] <= w) dp[i][w] = Math.max(dp[i][w], dp[i - 1][w - weights[i - 1]] + values[i - 1]);
        }
    return dp[n][capacity];
}
```

- **Time Complexity:** O(n * capacity)
- **Space Complexity:** O(n * capacity)

### Approach 4: 1D Array, Capacity Descending (Optimized)

Reuse a single row; going from high to low capacity makes every item usable once. This is the version implemented in the repository.

```java
public static int knapsack(int[] weights, int[] values, int capacity) {
    int[] dp = new int[capacity + 1];
    for (int item = 0; item < weights.length; item++)
        for (int w = capacity; w >= weights[item]; w--) dp[w] = Math.max(dp[w], dp[w - weights[item]] + values[item]);
    return dp[capacity];
}
```

- **Time Complexity:** O(n * capacity)
- **Space Complexity:** O(capacity)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(2^n) | O(n) |
| 2 | Top-Down Memoization | O(n * capacity) | O(n * capacity) |
| 3 | 2D Table | O(n * capacity) | O(n * capacity) |
| 4 | 1D Array, Capacity Descending (Optimized) | O(n * capacity) | O(capacity) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### Knapsack01.java

```java
import java.util.Arrays;

public class Knapsack01 {

    public static void main(String[] args) {
        int[][] weights = {{1, 3, 4, 5}, {10, 20, 30}, {5}, {2, 3}};
        int[][] values = {{1, 4, 5, 7}, {60, 100, 120}, {10}, {3, 4}};
        int[] capacities = {7, 50, 4, 0};
        for (int i = 0; i < weights.length; i++) {
            System.out.println("Input  : weights = " + Arrays.toString(weights[i]) + ", values = " + Arrays.toString(values[i]) + ", capacity = " + capacities[i]);
            System.out.println("Output : " + knapsack(weights[i], values[i], capacities[i]));
            System.out.println();
        }
    }

    public static int knapsack(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];
        for (int item = 0; item < weights.length; item++) {
            for (int w = capacity; w >= weights[item]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weights[item]] + values[item]);
            }
        }
        return dp[capacity];
    }
}
```
