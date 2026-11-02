# Longest Increasing Subsequence

Given an integer array `nums`, return the length of the longest strictly increasing subsequence (elements in order, not necessarily adjacent).

## Algorithm Strategy (Patience Sorting with Binary Search)

1. Keep an array `tails` where `tails[i]` is the smallest possible last value of an increasing subsequence of length `i + 1` found so far. It is always sorted.
2. For each number, binary-search the first position in `tails` whose value is `>= num`.
3. If the position is at the end, the number extends the longest subsequence: append it. Otherwise replace that entry, which keeps a smaller tail for the same length and so leaves more room for later numbers.
4. The length of `tails` is the length of the longest increasing subsequence. (The array is not itself the subsequence.)

## Example walkthrough

- For `[10, 9, 2, 5, 3, 7, 101, 18]`: tails evolve as [10], [9], [2], [2,5], [2,3], [2,3,7], [2,3,7,101], [2,3,7,18].
- Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — One binary search per number.
- **Space Complexity:** `O(n)` — The `tails` array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Recursion

For each element decide to take it (if it is larger than the previous taken one) or skip it. Exponential.

```java
public static int lengthOfLISRecursive(int[] nums) {
    return solve(nums, 0, Long.MIN_VALUE);
}

private static int solve(int[] nums, int index, long previous) {
    if (index == nums.length) return 0;
    int skip = solve(nums, index + 1, previous);
    int take = nums[index] > previous ? 1 + solve(nums, index + 1, nums[index]) : 0;
    return Math.max(skip, take);
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Dynamic Programming

`dp[i]` is the length of the longest increasing subsequence that ends at `i`: one more than the best `dp[j]` over earlier, smaller elements.

```java
public static int lengthOfLISDp(int[] nums) {
    if (nums.length == 0) return 0;
    int[] dp = new int[nums.length];
    int best = 1;
    for (int i = 0; i < nums.length; i++) {
        dp[i] = 1;
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
        }
        best = Math.max(best, dp[i]);
    }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 3: Patience Sorting with Binary Search (Optimized)

Keep, for every length, the smallest possible tail value; binary-search where each number belongs. This is the version implemented in the repository.

```java
public static int lengthOfLIS(int[] nums) {
    int[] tails = new int[nums.length];
    int size = 0;
    for (int num : nums) {
        int low = 0, high = size;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (tails[mid] < num) low = mid + 1; else high = mid;
        }
        tails[low] = num;
        if (low == size) size++;
    }
    return size;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Recursion | O(2^n) | O(n) |
| 2 | Dynamic Programming | O(n^2) | O(n) |
| 3 | Patience Sorting with Binary Search (Optimized) | O(n log n) | O(n) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### LongestIncreasingSubsequence.java

```java
import java.util.Arrays;

public class LongestIncreasingSubsequence {

    public static void main(String[] args) {
        int[][] inputs = {{10, 9, 2, 5, 3, 7, 101, 18}, {0, 1, 0, 3, 2, 3}, {7, 7, 7, 7}, {1, 2, 3, 4}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + lengthOfLIS(nums));
            System.out.println();
        }
    }

    public static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] tails = new int[nums.length];
        int size = 0;
        for (int num : nums) {
            // find the first tail >= num
            int low = 0;
            int high = size;
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (tails[mid] < num) {
                    low = mid + 1;
                } else {
                    high = mid;
                }
            }
            tails[low] = num;
            if (low == size) {
                size++;
            }
        }
        return size;
    }
}
```
