# Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit

Given an integer array `nums` and an integer `limit`, return the size of the longest non-empty subarray in which the absolute difference between any two elements is at most `limit`.

## Algorithm Strategy (Sliding Window with Min and Max Deques)

1. A window is valid when `max - min <= limit`.
2. Maintain two monotonic deques: one with decreasing values (front is the window maximum) and one with increasing values (front is the minimum).
3. Extend `right`; if the window becomes invalid, advance `left` and drop the deque fronts that left the window.
4. The best window length is the answer.

## Example walkthrough

- For `[8, 2, 4, 7]` and `limit = 4`: the window `[2, 4]` has a spread of 2, and `[4, 7]` has a spread of 3; `[8, 2]` is too wide.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index enters and leaves each deque once.
- **Space Complexity:** `O(n)` — The deques.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Track the min and max of every subarray.

```java
public static int bruteForce(int[] a, int limit) {
    int best = 0;
    for (int i = 0; i < a.length; i++) { int mn = a[i], mx = a[i]; for (int j = i; j < a.length; j++) { mn = Math.min(mn, a[j]); mx = Math.max(mx, a[j]); if (mx - mn > limit) break; best = Math.max(best, j - i + 1); } }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Sliding Window with a TreeMap

Keep the window's values in a `TreeMap` of counts and read the min and max from its ends.

```java
public static int treeMap(int[] a, int limit) {
    java.util.TreeMap<Integer, Integer> m = new java.util.TreeMap<>(); int l = 0, best = 0;
    for (int r = 0; r < a.length; r++) {
        m.merge(a[r], 1, Integer::sum);
        while (m.lastKey() - m.firstKey() > limit) { if (m.merge(a[l], -1, Integer::sum) == 0) m.remove(a[l]); l++; }
        best = Math.max(best, r - l + 1);
    }
    return best;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Two Monotonic Deques (Optimized)

Maintain the window's maximum and minimum in amortised O(1). This is the version implemented in the repository.

```java
public static int deques(int[] a, int limit) {
    java.util.Deque<Integer> mx = new java.util.ArrayDeque<>(), mn = new java.util.ArrayDeque<>(); int l = 0, best = 0;
    for (int r = 0; r < a.length; r++) {
        while (!mx.isEmpty() && mx.peekLast() < a[r]) mx.pollLast(); mx.addLast(a[r]);
        while (!mn.isEmpty() && mn.peekLast() > a[r]) mn.pollLast(); mn.addLast(a[r]);
        while (mx.peekFirst() - mn.peekFirst() > limit) { if (mx.peekFirst() == a[l]) mx.pollFirst(); if (mn.peekFirst() == a[l]) mn.pollFirst(); l++; }
        best = Math.max(best, r - l + 1);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Sliding Window with a TreeMap | O(n log n) | O(n) |
| 3 | Two Monotonic Deques (Optimized) | O(n) | O(n) |

## Source Code

### LongestContinuousSubarrayWithAbsoluteDiffLessThanOrEqualToLimit.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class LongestContinuousSubarrayWithAbsoluteDiffLessThanOrEqualToLimit {

    public static void main(String[] args) {
        int[][] nums = {{8, 2, 4, 7}, {10, 1, 2, 4, 7, 2}, {4, 2, 2, 2, 4, 4, 2, 2}, {1}};
        int[] limits = {4, 5, 0, 0};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", limit = " + limits[i]);
            System.out.println("Output : " + longestSubarray(nums[i], limits[i]));
            System.out.println();
        }
    }

    public static int longestSubarray(int[] nums, int limit) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Deque<Integer> maxQueue = new ArrayDeque<>();
        Deque<Integer> minQueue = new ArrayDeque<>();
        int left = 0;
        int best = 0;
        for (int right = 0; right < nums.length; right++) {
            while (!maxQueue.isEmpty() && maxQueue.peekLast() < nums[right]) {
                maxQueue.pollLast();
            }
            maxQueue.addLast(nums[right]);
            while (!minQueue.isEmpty() && minQueue.peekLast() > nums[right]) {
                minQueue.pollLast();
            }
            minQueue.addLast(nums[right]);
            while (maxQueue.peekFirst() - minQueue.peekFirst() > limit) {
                if (maxQueue.peekFirst() == nums[left]) {
                    maxQueue.pollFirst();
                }
                if (minQueue.peekFirst() == nums[left]) {
                    minQueue.pollFirst();
                }
                left++;
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: a window is valid when its maximum minus its minimum is at most `limit`. Two monotonic deques give the window's maximum and minimum at their fronts while the window slides.

**Input:** `nums = [8, 2, 4, 7]`, `limit = 4`.

| `right` | Window | Max - min | Action | Best |
|---------|--------|-----------|--------|------|
| 0 | [8] | 0 | | 1 |
| 1 | [8, 2] | 6 | shrink, `left = 1` | 1 |
| 2 | [2, 4] | 2 | | 2 |
| 3 | [2, 4, 7] | 5 | shrink, `left = 2` -> [4, 7] spread 3 | 2 |

**Output:** `2`.
