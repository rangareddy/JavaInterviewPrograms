# Minimum Size Subarray Sum

Given an array of positive integers `nums` and a positive integer `target`, return the minimal length of a contiguous subarray whose sum is greater than or equal to `target`. Return `0` if there is no such subarray.

## Algorithm Strategy (Sliding Window)

1. Because every number is positive, growing the window increases the sum and shrinking it decreases the sum.
2. Move the right edge forward, adding each value to `sum`.
3. While `sum >= target`, record the window length and shrink from the left, subtracting `nums[left]`.
4. The smallest recorded length is the answer, or `0` if none was recorded.

## Example walkthrough

- For `target = 7`, `nums = [2, 3, 1, 2, 4, 3]`: the window `[4, 3]` has sum 7 and length 2, and no shorter window reaches 7.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index enters and leaves the window once.
- **Space Complexity:** `O(1)` — Two pointers and a sum.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Try every start index and extend until the sum reaches the target.

```java
public static int bruteForce(int target, int[] nums) {
    int best = Integer.MAX_VALUE;
    for (int i = 0; i < nums.length; i++) {
        int sum = 0;
        for (int j = i; j < nums.length; j++) { sum += nums[j]; if (sum >= target) { best = Math.min(best, j - i + 1); break; } }
    }
    return best == Integer.MAX_VALUE ? 0 : best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Prefix Sums and Binary Search

For each start, binary search the first prefix sum that reaches `prefix[start] + target`.

```java
public static int prefixBinary(int target, int[] nums) {
    int n = nums.length; long[] pre = new long[n + 1];
    for (int i = 0; i < n; i++) pre[i + 1] = pre[i] + nums[i];
    int best = Integer.MAX_VALUE;
    for (int i = 0; i < n; i++) {
        long need = pre[i] + target; int lo = i + 1, hi = n;
        while (lo < hi) { int mid = (lo + hi) / 2; if (pre[mid] >= need) hi = mid; else lo = mid + 1; }
        if (pre[lo] >= need) best = Math.min(best, lo - i);
    }
    return best == Integer.MAX_VALUE ? 0 : best;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Sliding Window (Optimized)

Grow on the right, shrink on the left while the sum is large enough. This is the version implemented in the repository.

```java
public static int window(int target, int[] nums) {
    int best = Integer.MAX_VALUE, left = 0, sum = 0;
    for (int right = 0; right < nums.length; right++) {
        sum += nums[right];
        while (sum >= target) { best = Math.min(best, right - left + 1); sum -= nums[left++]; }
    }
    return best == Integer.MAX_VALUE ? 0 : best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Prefix Sums and Binary Search | O(n log n) | O(n) |
| 3 | Sliding Window (Optimized) | O(n) | O(1) |

## Source Code

### MinimumSizeSubarraySum.java

```java
import java.util.Arrays;

public class MinimumSizeSubarraySum {

    public static void main(String[] args) {
        int[][] nums = {{2, 3, 1, 2, 4, 3}, {1, 4, 4}, {1, 1, 1, 1, 1, 1, 1, 1}, {}};
        int[] targets = {7, 4, 11, 5};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : target = " + targets[i] + ", nums = " + Arrays.toString(nums[i]));
            System.out.println("Output : " + minSubArrayLen(targets[i], nums[i]));
            System.out.println();
        }
    }

    public static int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int best = Integer.MAX_VALUE;
        int left = 0;
        int sum = 0;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                best = Math.min(best, right - left + 1);
                sum -= nums[left++];
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: with positive numbers, extending the window can only raise the sum and shrinking can only lower it, so two pointers never need to move backward.

**Input:** `target = 7`, `nums = [2, 3, 1, 2, 4, 3]`.

| `right` | Added | `sum` | Shrink steps | Best length |
|---------|-------|-------|--------------|-------------|
| 0 | 2 | 2 | none | |
| 1 | 3 | 5 | none | |
| 2 | 1 | 6 | none | |
| 3 | 2 | 8 | length 4, remove 2 (sum 6) | 4 |
| 4 | 4 | 10 | length 4, remove 3 (7), length 3, remove 1 (6) | 3 |
| 5 | 3 | 9 | length 3, remove 2 (7), length 2, remove 4 (3) | 2 |

**Output:** `2`.
