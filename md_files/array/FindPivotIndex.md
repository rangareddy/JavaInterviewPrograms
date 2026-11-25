# Find Pivot Index

Given an integer array `nums`, return the leftmost pivot index: the index where the sum of the numbers strictly to its left equals the sum of the numbers strictly to its right. Return `-1` if no such index exists.

## Algorithm Strategy (Running Left Sum)

1. Compute the total sum of the array once.
2. Walk the indexes keeping `leftSum`, the sum of the elements before the current index.
3. The right sum is `total - leftSum - nums[i]`. If it equals `leftSum`, return `i`.
4. Add `nums[i]` to `leftSum` and continue. Return `-1` if the loop finishes.

## Example walkthrough

- For `[1, 7, 3, 6, 5, 6]`: at index 3 the left sum is 11 and the right sum is `28 - 11 - 6 = 11`.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Two passes.
- **Space Complexity:** `O(1)` — A few sums.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

For every index sum the left side and the right side.

```java
public static int bruteForce(int[] a) {
    for (int i = 0; i < a.length; i++) {
        int l = 0, r = 0; for (int j = 0; j < i; j++) l += a[j]; for (int j = i + 1; j < a.length; j++) r += a[j];
        if (l == r) return i;
    }
    return -1;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Prefix Sum Array

Precompute prefix sums and compare in O(1) per index.

```java
public static int prefix(int[] a) {
    int n = a.length; int[] p = new int[n + 1]; for (int i = 0; i < n; i++) p[i + 1] = p[i] + a[i];
    for (int i = 0; i < n; i++) if (p[i] == p[n] - p[i + 1]) return i;
    return -1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Running Left Sum (Optimized)

Keep only the left sum and derive the right sum from the total. This is the version implemented in the repository.

```java
public static int running(int[] a) {
    int total = 0; for (int x : a) total += x;
    int left = 0;
    for (int i = 0; i < a.length; i++) { if (left == total - left - a[i]) return i; left += a[i]; }
    return -1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Prefix Sum Array | O(n) | O(n) |
| 3 | Running Left Sum (Optimized) | O(n) | O(1) |

## Source Code

### FindPivotIndex.java

```java
import java.util.Arrays;

public class FindPivotIndex {

    public static void main(String[] args) {
        int[][] inputs = {{1, 7, 3, 6, 5, 6}, {1, 2, 3}, {2, 1, -1}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + pivotIndex(nums));
            System.out.println();
        }
    }

    public static int pivotIndex(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int total = 0;
        for (int x : nums) {
            total += x;
        }
        int leftSum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (leftSum == total - leftSum - nums[i]) {
                return i;
            }
            leftSum += nums[i];
        }
        return -1;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: knowing the total and the sum on the left is enough, since the right sum is whatever remains.

**Input:** `nums = [1, 7, 3, 6, 5, 6]`, total = 28.

| `i` | `nums[i]` | `leftSum` | Right sum = 28 - left - `nums[i]` | Equal? |
|-----|-----------|-----------|-----------------------------------|--------|
| 0 | 1 | 0 | 27 | no |
| 1 | 7 | 1 | 20 | no |
| 2 | 3 | 8 | 17 | no |
| 3 | 6 | 11 | 11 | yes |

**Output:** `3`.
