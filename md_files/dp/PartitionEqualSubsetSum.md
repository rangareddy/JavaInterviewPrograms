# Partition Equal Subset Sum

Given an integer array `nums` of positive integers, return `true` if it can be partitioned into two subsets with equal sums.

## Algorithm Strategy (0/1 Knapsack: Can a Subset Reach Half of the Total?)

1. If the total sum is odd the two halves cannot be equal, so return `false`. Otherwise the task is to find a subset with sum `target = total / 2`; the rest then also sums to `target`.
2. Let `dp[s]` be `true` when some subset of the numbers processed so far has sum `s`. Initially only `dp[0]` is true (the empty subset).
3. For each number `num`, update the sums from `target` down to `num`: `dp[s] |= dp[s - num]`. Iterating downwards ensures that each number is used at most once.
4. The answer is `dp[target]`.

## Example walkthrough

- For `[1, 5, 11, 5]`: total 22, target 11. After the numbers 1, 5, 11 the reachable sums include 11 (the number 11 itself), so `dp[11]` is true.
- The subsets are `[11]` and `[1, 5, 5]`. For `[1, 2, 3, 5]` the total 11 is odd: `false`.

## Complexity Analysis

- **Time Complexity:** `O(n * target)` — For every number all sums up to the target are updated.
- **Space Complexity:** `O(target)` — One boolean array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force Recursion

For every number decide whether it goes into the first subset and check whether the half-sum is reached.

```java
public static boolean canPartitionBrute(int[] nums) {
    int total = 0;
    for (int n : nums) total += n;
    return total % 2 == 0 && reach(nums, 0, total / 2);
}

private static boolean reach(int[] nums, int i, int remaining) {
    if (remaining == 0) return true;
    if (i == nums.length || remaining < 0) return false;
    return reach(nums, i + 1, remaining - nums[i]) || reach(nums, i + 1, remaining);
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Top-Down Memoization

Cache the result for each `(index, remaining)` pair.

```java
public static boolean canPartitionMemo(int[] nums) {
    int total = 0;
    for (int n : nums) total += n;
    if (total % 2 != 0) return false;
    int target = total / 2;
    Boolean[][] memo = new Boolean[nums.length + 1][target + 1];
    return reachMemo(nums, 0, target, memo);
}

private static boolean reachMemo(int[] nums, int i, int remaining, Boolean[][] memo) {
    if (remaining == 0) return true;
    if (i == nums.length || remaining < 0) return false;
    if (memo[i][remaining] != null) return memo[i][remaining];
    boolean result = reachMemo(nums, i + 1, remaining - nums[i], memo) || reachMemo(nums, i + 1, remaining, memo);
    return memo[i][remaining] = result;
}
```

- **Time Complexity:** O(n * target)
- **Space Complexity:** O(n * target)

### Approach 3: Set of Reachable Sums

Grow the set of subset sums number by number and look for the target.

```java
public static boolean canPartitionSet(int[] nums) {
    int total = 0;
    for (int n : nums) total += n;
    if (total % 2 != 0) return false;
    Set<Integer> sums = new HashSet<>();
    sums.add(0);
    for (int num : nums) {
        Set<Integer> next = new HashSet<>(sums);
        for (int s : sums) next.add(s + num);
        sums = next;
    }
    return sums.contains(total / 2);
}
```

- **Time Complexity:** O(n * total)
- **Space Complexity:** O(total)

### Approach 4: 1D Knapsack Array (Optimized)

A boolean array of reachable sums updated from high to low. This is the version implemented in the repository.

```java
public static boolean canPartition(int[] nums) {
    int total = 0;
    for (int n : nums) total += n;
    if (total % 2 != 0) return false;
    int target = total / 2;
    boolean[] dp = new boolean[target + 1];
    dp[0] = true;
    for (int num : nums)
        for (int sum = target; sum >= num; sum--) dp[sum] = dp[sum] || dp[sum - num];
    return dp[target];
}
```

- **Time Complexity:** O(n * target)
- **Space Complexity:** O(target)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force Recursion | O(2^n) | O(n) |
| 2 | Top-Down Memoization | O(n * target) | O(n * target) |
| 3 | Set of Reachable Sums | O(n * total) | O(total) |
| 4 | 1D Knapsack Array (Optimized) | O(n * target) | O(target) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: the array can be split into two equal halves only if its total is even and some subset adds up to `total / 2`. That is a 0/1 subset-sum: `dp[s]` is `true` when some subset of the numbers seen so far sums to `s`. For each number, update the sums from `target` **down** to the number (`dp[s] = dp[s] || dp[s - num]`); going downwards guarantees each number is used at most once.

**Input:** `nums = [1, 5, 11, 5]`. `total = 22` (even), `target = 11`. Start: only `dp[0]` is true, so the reachable sums are `{0}`.

| Number | Sums checked | Reachable sums afterwards (`dp[s]` true) |
|--------|--------------|-------------------------------------------|
| 1 | `s = 11..1` | `{0, 1}` |
| 5 | `s = 11..5`: `dp[6] = dp[1]`, `dp[5] = dp[0]` | `{0, 1, 5, 6}` |
| 11 | `s = 11`: `dp[11] = dp[0]` | `{0, 1, 5, 6, 11}` |
| 5 | `s = 11..5`: `dp[10] = dp[5]`, `dp[11]` already true | `{0, 1, 5, 6, 10, 11}` |

`dp[11]` became true while processing the number 11 (the subset `{11}`, the other half is `{1, 5, 5}`). No diagram is added; the table of reachable sums shows the whole process.

**Edge cases.** An odd total (e.g. `[1, 2, 3, 5]` has total 11, or `[1]`) returns `false` immediately without building the array. An even total with no matching subset (e.g. `[2, 2, 3, 5]`, total 12, target 6) fills the array and ends with `dp[6] = false`. Space is `O(target)`.

**Result:** `dp[11]` is true, so the method returns `true`, matching the class output.

## Source Code

### PartitionEqualSubsetSum.java

```java
import java.util.Arrays;

public class PartitionEqualSubsetSum {

    public static void main(String[] args) {
        int[][] inputs = {{1, 5, 11, 5}, {1, 2, 3, 5}, {2, 2, 3, 5}, {1}, {3, 3, 3, 4, 5}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + canPartition(nums));
            System.out.println();
        }
    }

    public static boolean canPartition(int[] nums) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        if (total % 2 != 0) {
            return false;
        }

        int target = total / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int num : nums) {
            for (int sum = target; sum >= num; sum--) {
                dp[sum] = dp[sum] || dp[sum - num];
            }
        }
        return dp[target];
    }
}
```
