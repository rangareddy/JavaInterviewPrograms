# 3Sum Closest

Given an integer array `nums` of length `n` and an integer `target`, find three integers in `nums` whose sum is closest to `target` and return that sum. Assume each input has exactly one solution.

## Algorithm Strategy (Sort then Two Pointers)

1. Sort the array.
2. Fix the first number `nums[i]` and use two pointers `lo = i + 1`, `hi = n - 1` on the rest.
3. Compute `sum`; if it is closer to `target` than the best so far, remember it. If `sum == target`, return it immediately.
4. Move `lo` right when `sum < target` and `hi` left when `sum > target`.

## Example walkthrough

- For `[-1, 2, 1, -4]` and `target = 1`: the sum `-1 + 2 + 1 = 2` is the closest.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Sorting plus a two-pointer scan for every first element.
- **Space Complexity:** `O(1)` — Apart from sorting.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Try every triple.

```java
public static int bruteForce(int[] a, int t) {
    int best = a[0] + a[1] + a[2];
    for (int i = 0; i < a.length; i++) for (int j = i + 1; j < a.length; j++) for (int k = j + 1; k < a.length; k++) { int s = a[i] + a[j] + a[k]; if (Math.abs(s - t) < Math.abs(best - t)) best = s; }
    return best;
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(1)

### Approach 2: Sort and Two Pointers (Optimized)

Fix one number and squeeze the other two. This is the version implemented in the repository.

```java
public static int twoPointers(int[] in, int t) {
    int[] a = in.clone(); java.util.Arrays.sort(a); int best = a[0] + a[1] + a[2];
    for (int i = 0; i < a.length - 2; i++) { int lo = i + 1, hi = a.length - 1; while (lo < hi) { int s = a[i] + a[lo] + a[hi]; if (Math.abs(s - t) < Math.abs(best - t)) best = s; if (s == t) return s; else if (s < t) lo++; else hi--; } }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^3) | O(1) |
| 2 | Sort and Two Pointers (Optimized) | O(n^2) | O(1) |

## Source Code

### ThreeSumClosest.java

```java
import java.util.Arrays;

public class ThreeSumClosest {

    public static void main(String[] args) {
        int[][] nums = {{-1, 2, 1, -4}, {0, 0, 0}, {1, 1, 1, 0}, {4, 0, 5, -5, 3, 3, 0, -4, -5}};
        int[] targets = {1, 1, -100, -2};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + threeSumClosest(nums[i].clone(), targets[i]));
            System.out.println();
        }
    }

    public static int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);
        int best = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            int lo = i + 1;
            int hi = nums.length - 1;
            while (lo < hi) {
                int sum = nums[i] + nums[lo] + nums[hi];
                if (Math.abs(sum - target) < Math.abs(best - target)) {
                    best = sum;
                }
                if (sum == target) {
                    return sum;
                } else if (sum < target) {
                    lo++;
                } else {
                    hi--;
                }
            }
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: after sorting, the sum moves predictably when a pointer moves, so for a fixed first number the pair can be found by squeezing, and every sum seen along the way is a candidate for "closest".

**Input:** `nums = [-1, 2, 1, -4]` sorted to `[-4, -1, 1, 2]`, `target = 1`.

| `i` | `lo` | `hi` | Sum | Closest so far |
|-----|------|------|-----|----------------|
| 0 (-4) | 1 | 3 | -3 | -3 |
| 0 (-4) | 2 | 3 | -1 | -1 |
| 1 (-1) | 2 | 3 | 2 | 2 |

**Output:** `2`.
