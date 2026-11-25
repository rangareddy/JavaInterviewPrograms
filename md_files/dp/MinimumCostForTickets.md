# Minimum Cost For Tickets

You plan to travel on the days listed in `days` (sorted, values from 1 to 365). A 1-day pass, a 7-day pass and a 30-day pass cost `costs[0]`, `costs[1]` and `costs[2]`. A pass covers consecutive days starting on the day it is bought. Return the minimum cost to cover every travel day.

## Algorithm Strategy (DP over the Calendar)

1. Let `dp[d]` be the minimum cost to cover all travel days up to day `d`.
2. If `d` is not a travel day, `dp[d] = dp[d - 1]`.
3. Otherwise `dp[d] = min(dp[d - 1] + costs[0], dp[max(0, d - 7)] + costs[1], dp[max(0, d - 30)] + costs[2])`.
4. The answer is `dp[lastDay]`.

## Example walkthrough

- For `days = [1, 4, 6, 7, 8, 20]` and `costs = [2, 7, 15]`: a 1-day pass on day 1, a 7-day pass on days 4 to 10, and a 1-day pass on day 20.
- Total: `2 + 7 + 2 = 11`.

## Complexity Analysis

- **Time Complexity:** `O(lastDay)` — One DP step per calendar day (at most 365).
- **Space Complexity:** `O(lastDay)` — The dp array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion over Travel Days

For each travel day try the three passes and recurse on the first uncovered day. Exponential without memoisation.

```java
public static int recursive(int[] days, int[] c) { return go(days, c, 0); }
private static int go(int[] d, int[] c, int i) {
    if (i >= d.length) return 0; int best = Integer.MAX_VALUE; int[] len = {1, 7, 30};
    for (int k = 0; k < 3; k++) { int j = i; while (j < d.length && d[j] < d[i] + len[k]) j++; best = Math.min(best, c[k] + go(d, c, j)); }
    return best;
}
```

- **Time Complexity:** O(3^n)
- **Space Complexity:** O(n)

### Approach 2: DP over Calendar Days (Optimized)

Minimum cost to cover everything up to each day. This is the version implemented in the repository.

```java
public static int calendar(int[] days, int[] c) {
    int last = days[days.length - 1]; boolean[] t = new boolean[last + 1]; for (int d : days) t[d] = true; int[] dp = new int[last + 1];
    for (int d = 1; d <= last; d++) { if (!t[d]) { dp[d] = dp[d - 1]; continue; } dp[d] = Math.min(dp[d - 1] + c[0], Math.min(dp[Math.max(0, d - 7)] + c[1], dp[Math.max(0, d - 30)] + c[2])); }
    return dp[last];
}
```

- **Time Complexity:** O(lastDay)
- **Space Complexity:** O(lastDay)

### Approach 3: DP over Travel Days with a Moving Window

Keep only the passes that still cover the current day in two queues (7-day and 30-day).

```java
public static int windows(int[] days, int[] c) {
    java.util.Deque<int[]> w7 = new java.util.ArrayDeque<>(), w30 = new java.util.ArrayDeque<>(); int cost = 0;
    for (int d : days) {
        while (!w7.isEmpty() && w7.peek()[0] + 7 <= d) w7.poll(); while (!w30.isEmpty() && w30.peek()[0] + 30 <= d) w30.poll();
        w7.add(new int[]{d, cost + c[1]}); w30.add(new int[]{d, cost + c[2]}); cost = Math.min(cost + c[0], Math.min(w7.peek()[1], w30.peek()[1]));
    }
    return cost;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion over Travel Days | O(3^n) | O(n) |
| 2 | DP over Calendar Days (Optimized) | O(lastDay) | O(lastDay) |
| 3 | DP over Travel Days with a Moving Window | O(n) | O(n) |

## Source Code

### MinimumCostForTickets.java

```java
import java.util.Arrays;

public class MinimumCostForTickets {

    public static void main(String[] args) {
        int[][] days = {{1, 4, 6, 7, 8, 20}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 30, 31}, {5}};
        int[][] costs = {{2, 7, 15}, {2, 7, 15}, {3, 10, 20}};
        for (int i = 0; i < days.length; i++) {
            System.out.println("Input  : days = " + Arrays.toString(days[i]) + ", costs = " + Arrays.toString(costs[i]));
            System.out.println("Output : " + mincostTickets(days[i], costs[i]));
            System.out.println();
        }
    }

    public static int mincostTickets(int[] days, int[] costs) {
        if (days == null || days.length == 0) {
            return 0;
        }
        int last = days[days.length - 1];
        boolean[] travel = new boolean[last + 1];
        for (int d : days) {
            travel[d] = true;
        }
        int[] dp = new int[last + 1];
        for (int d = 1; d <= last; d++) {
            if (!travel[d]) {
                dp[d] = dp[d - 1];
                continue;
            }
            dp[d] = Math.min(dp[d - 1] + costs[0],
                    Math.min(dp[Math.max(0, d - 7)] + costs[1], dp[Math.max(0, d - 30)] + costs[2]));
        }
        return dp[last];
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the cheapest way to cover everything up to day `d` ends with some pass that covers day `d`, so the answer is the best of "pay for one more day", "buy a 7-day pass that started 7 days ago" and "buy a 30-day pass".

**Input:** `days = [1, 4, 6, 7, 8, 20]`, `costs = [2, 7, 15]`.

| Day | Travel? | `dp[d]` |
|-----|---------|---------|
| 1 | yes | min(0 + 2, 0 + 7, 0 + 15) = 2 |
| 4 | yes | min(2 + 2, 0 + 7, 0 + 15) = 4 |
| 6 | yes | min(4 + 2, 0 + 7, 0 + 15) = 6 |
| 7 | yes | min(6 + 2, 0 + 7, 0 + 15) = 7 |
| 8 | yes | min(7 + 2, `dp[1]` + 7 = 9, 15) = 9 |
| 20 | yes | min(9 + 2, `dp[13]` + 7 = 16, 15) = 11 |

**Output:** `11`.
