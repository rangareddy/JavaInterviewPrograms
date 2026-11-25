# Subarrays with K Different Integers

Given an integer array `nums` and an integer `k`, return the number of good subarrays: contiguous subarrays that contain exactly `k` different integers.

## Algorithm Strategy (Count at Most K, Subtract at Most K - 1)

1. Counting subarrays with exactly `k` distinct values directly is awkward, but counting those with at most `k` is easy with a sliding window.
2. `atMost(k)`: extend `right`, count values in a map; while there are more than `k` distinct values, shrink from the left. Add `right - left + 1` (all subarrays ending at `right`).
3. Exactly `k` = `atMost(k) - atMost(k - 1)`.
4. Return that difference.

## Example walkthrough

- For `[1,2,1,2,3]` and `k = 2`: there are 12 subarrays with at most 2 distinct values and 5 with at most 1.
- Result: `12 - 5 = 7`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Two linear window passes.
- **Space Complexity:** `O(k)` — The count map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Count the distinct values of every subarray.

```java
public static int brute(int[] a, int k) {
    int c = 0; for (int i = 0; i < a.length; i++) { java.util.Set<Integer> s = new java.util.HashSet<>(); for (int j = i; j < a.length; j++) { s.add(a[j]); if (s.size() == k) c++; else if (s.size() > k) break; } } return c;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: At Most K Minus At Most K - 1 (Optimized)

Two sliding-window passes. This is the version implemented in the repository.

```java
public static int atMostDiff(int[] a, int k) { return k <= 0 ? 0 : window(a, k) - window(a, k - 1); }
private static int window(int[] a, int k) {
    java.util.Map<Integer, Integer> m = new java.util.HashMap<>(); int l = 0, t = 0;
    for (int r = 0; r < a.length; r++) { m.merge(a[r], 1, Integer::sum); while (m.size() > k) { if (m.merge(a[l], -1, Integer::sum) == 0) m.remove(a[l]); l++; } t += r - l + 1; }
    return t;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(k)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(n) |
| 2 | At Most K Minus At Most K - 1 (Optimized) | O(n) | O(k) |

## Source Code

### SubarraysWithKDifferentIntegers.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SubarraysWithKDifferentIntegers {

    public static void main(String[] args) {
        int[][] nums = {{1, 2, 1, 2, 3}, {1, 2, 1, 3, 4}, {2, 2, 2}, {1}};
        int[] ks = {2, 3, 1, 1};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + subarraysWithKDistinct(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static int subarraysWithKDistinct(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return 0;
        }
        return atMost(nums, k) - atMost(nums, k - 1);
    }

    private static int atMost(int[] nums, int k) {
        Map<Integer, Integer> counts = new HashMap<>();
        int left = 0;
        int total = 0;
        for (int right = 0; right < nums.length; right++) {
            counts.merge(nums[right], 1, Integer::sum);
            while (counts.size() > k) {
                if (counts.merge(nums[left], -1, Integer::sum) == 0) {
                    counts.remove(nums[left]);
                }
                left++;
            }
            total += right - left + 1;
        }
        return total;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: "exactly `k`" is hard for a sliding window because shrinking can break the condition, but "at most `k`" is monotonic and easy. Every subarray with at most `k` distinct values that does not have at most `k - 1` has exactly `k`.

**Input:** `nums = [1, 2, 1, 2, 3]`, `k = 2`.

| Count | Value |
|-------|-------|
| subarrays with at most 2 distinct values | 12 |
| subarrays with at most 1 distinct value | 5 |
| exactly 2 distinct values | 12 - 5 = 7 |

**Output:** `7`.
