# Target Sum

You are given an integer array `nums` and an integer `target`. Put a `+` or a `-` in front of every number and concatenate the results into an expression. Return the number of different expressions that evaluate to `target`.

## Algorithm Strategy (Reduce to Counting Subsets with a Given Sum)

1. Let `P` be the sum of the numbers with a plus and `N` the sum of those with a minus. Then `P - N = target` and `P + N = total`, so `P = (total + target) / 2`.
2. If `|target| > total` or `total + target` is odd, no expression works: return 0.
3. The problem becomes: count the subsets whose sum is `P` (the numbers that get a plus). This is the 0/1 knapsack counting problem.
4. Let `dp[s]` be the number of subsets with sum `s`; `dp[0] = 1`. For every number update `s` from `P` down to the number: `dp[s] += dp[s - num]`. The answer is `dp[P]`.

## Example walkthrough

- For `[1, 1, 1, 1, 1]` and target 3: total 5, `P = (5 + 3) / 2 = 4`; the number of ways to choose four of the five ones is 5.
- Result: `5`.

## Complexity Analysis

- **Time Complexity:** `O(n * P)` — `P` is the required subset sum.
- **Space Complexity:** `O(P)` — One array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force Recursion

Try both signs for every number and count the expressions that hit the target.

```java
public static int findTargetSumWaysBrute(int[] nums, int target) {
    return ways(nums, 0, target);
}

private static int ways(int[] nums, int i, int remaining) {
    if (i == nums.length) return remaining == 0 ? 1 : 0;
    return ways(nums, i + 1, remaining - nums[i]) + ways(nums, i + 1, remaining + nums[i]);
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Memoization on (index, sum)

Cache the count for each pair of position and remaining target.

```java
public static int findTargetSumWaysMemo(int[] nums, int target) {
    return waysMemo(nums, 0, target, new HashMap<>());
}

private static int waysMemo(int[] nums, int i, int remaining, Map<String, Integer> memo) {
    if (i == nums.length) return remaining == 0 ? 1 : 0;
    String key = i + "," + remaining;
    Integer cached = memo.get(key);
    if (cached != null) return cached;
    int result = waysMemo(nums, i + 1, remaining - nums[i], memo) + waysMemo(nums, i + 1, remaining + nums[i], memo);
    memo.put(key, result);
    return result;
}
```

- **Time Complexity:** O(n * total)
- **Space Complexity:** O(n * total)

### Approach 3: Map of Reachable Sums

Keep a map from each reachable running total to the number of ways to reach it.

```java
public static int findTargetSumWaysMap(int[] nums, int target) {
    Map<Integer, Integer> counts = new HashMap<>();
    counts.put(0, 1);
    for (int num : nums) {
        Map<Integer, Integer> next = new HashMap<>();
        for (Map.Entry<Integer, Integer> e : counts.entrySet()) {
            next.merge(e.getKey() + num, e.getValue(), Integer::sum);
            next.merge(e.getKey() - num, e.getValue(), Integer::sum);
        }
        counts = next;
    }
    return counts.getOrDefault(target, 0);
}
```

- **Time Complexity:** O(n * total)
- **Space Complexity:** O(total)

### Approach 4: Subset-Sum Reduction (Optimized)

Counting subsets with sum `(total + target) / 2` replaces the signs. This is the version implemented in the repository.

```java
public static int findTargetSumWays(int[] nums, int target) {
    int total = 0;
    for (int n : nums) total += n;
    if (Math.abs(target) > total || (total + target) % 2 != 0) return 0;
    int plusSum = (total + target) / 2;
    int[] dp = new int[plusSum + 1];
    dp[0] = 1;
    for (int num : nums)
        for (int sum = plusSum; sum >= num; sum--) dp[sum] += dp[sum - num];
    return dp[plusSum];
}
```

- **Time Complexity:** O(n * total)
- **Space Complexity:** O(total)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force Recursion | O(2^n) | O(n) |
| 2 | Memoization on (index, sum) | O(n * total) | O(n * total) |
| 3 | Map of Reachable Sums | O(n * total) | O(total) |
| 4 | Subset-Sum Reduction (Optimized) | O(n * total) | O(total) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### TargetSum.java

```java
import java.util.Arrays;

public class TargetSum {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 1, 1, 1}, {1}, {1, 0}, {2, 3, 5}, {1, 2, 3, 4, 5}};
        int[] targets = {3, 1, 1, 0, 3};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + findTargetSumWays(inputs[i], targets[i]));
            System.out.println();
        }
    }

    public static int findTargetSumWays(int[] nums, int target) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        if (Math.abs(target) > total || (total + target) % 2 != 0) {
            return 0;
        }

        int plusSum = (total + target) / 2;
        int[] dp = new int[plusSum + 1];
        dp[0] = 1;
        for (int num : nums) {
            for (int sum = plusSum; sum >= num; sum--) {
                dp[sum] += dp[sum - num];
            }
        }
        return dp[plusSum];
    }
}
```
