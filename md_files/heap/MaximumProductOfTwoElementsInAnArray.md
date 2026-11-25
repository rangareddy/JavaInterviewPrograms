# Maximum Product of Two Elements in an Array

Given an array of integers `nums`, choose two different indices `i` and `j` and return the maximum value of `(nums[i] - 1) * (nums[j] - 1)`.

## Algorithm Strategy (Two Largest Values in One Pass)

1. Because the values are positive, the product `(a - 1) * (b - 1)` is largest for the two largest values.
2. Keep `first` and `second`, the two largest values seen.
3. For each number, update them: a new maximum pushes `first` into `second`, otherwise a number above `second` replaces it.
4. Return `(first - 1) * (second - 1)`.

## Example walkthrough

- For `[3, 4, 5, 2]` the two largest are 5 and 4.
- Result: `(5 - 1) * (4 - 1) = 12`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Two variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Pair

Compute the product for every pair of indexes.

```java
public static int brute(int[] a) { int best = 0; for (int i = 0; i < a.length; i++) for (int j = i + 1; j < a.length; j++) best = Math.max(best, (a[i] - 1) * (a[j] - 1)); return best; }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Sort

Sort and use the last two values.

```java
public static int sort(int[] in) { int[] a = in.clone(); java.util.Arrays.sort(a); return (a[a.length - 1] - 1) * (a[a.length - 2] - 1); }
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1)

### Approach 3: Track the Two Largest (Optimized)

One pass with two variables. This is the version implemented in the repository.

```java
public static int two(int[] a) { int f = 0, s = 0; for (int x : a) { if (x > f) { s = f; f = x; } else if (x > s) s = x; } return (f - 1) * (s - 1); }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Pair | O(n^2) | O(1) |
| 2 | Sort | O(n log n) | O(1) |
| 3 | Track the Two Largest (Optimized) | O(n) | O(1) |

## Source Code

### MaximumProductOfTwoElementsInAnArray.java

```java
import java.util.Arrays;

public class MaximumProductOfTwoElementsInAnArray {

    public static void main(String[] args) {
        int[][] inputs = {{3, 4, 5, 2}, {1, 5, 4, 5}, {3, 7}, {10, 2, 5, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + maxProduct(nums));
            System.out.println();
        }
    }

    public static int maxProduct(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int first = 0;
        int second = 0;
        for (int num : nums) {
            if (num > first) {
                second = first;
                first = num;
            } else if (num > second) {
                second = num;
            }
        }
        return (first - 1) * (second - 1);
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: for positive integers, `(a - 1) * (b - 1)` grows with both `a` and `b`, so the best pair is simply the two largest numbers (they can be equal values at different indexes). One scan with two variables finds them without sorting.

**Input:** `nums = [3, 4, 5, 2]`.

| Number | `first` | `second` |
|--------|---------|----------|
| 3 | 3 | 0 |
| 4 | 4 | 3 |
| 5 | 5 | 4 |
| 2 | 5 | 4 |

**Output:** `(5 - 1) * (4 - 1) = 12`.
