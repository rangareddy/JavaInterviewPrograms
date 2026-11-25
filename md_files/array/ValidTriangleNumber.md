# Valid Triangle Number

Given an integer array `nums`, return the number of triplets chosen from the array that can make the sides of a triangle (three lengths `a <= b <= c` form a triangle when `a + b > c`).

## Algorithm Strategy (Sort, Fix the Longest Side, Two Pointers)

1. Sort the array.
2. Fix the largest side `nums[k]` for `k` from the end down to index 2.
3. Use two pointers `i = 0` and `j = k - 1`: if `nums[i] + nums[j] > nums[k]`, then every index from `i` to `j - 1` works with `j`, so add `j - i` and decrease `j`; otherwise increase `i`.
4. Sum the counts.

## Example walkthrough

- For `[2, 2, 3, 4]` sorted: with `c = 4`, `(2, 3)` and `(2, 3)` using the two 2s work (2 + 3 > 4), and `(2, 2)` does not; with `c = 3`, `(2, 2)` works.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Sorting plus one two-pointer pass per longest side.
- **Space Complexity:** `O(1)` — Apart from the sort.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Check Every Triple

Test the triangle inequality for all triples.

```java
public static int brute(int[] a) { int c = 0; for (int i = 0; i < a.length; i++) for (int j = i + 1; j < a.length; j++) for (int k = j + 1; k < a.length; k++) { int x = a[i], y = a[j], z = a[k]; if (x + y > z && x + z > y && y + z > x) c++; } return c; }
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(1)

### Approach 2: Sort and Two Pointers (Optimized)

Fix the longest side and count the valid pairs below it with two pointers. This is the version implemented in the repository.

```java
public static int pointers(int[] in) { int[] a = in.clone(); java.util.Arrays.sort(a); int c = 0; for (int k = a.length - 1; k >= 2; k--) { int i = 0, j = k - 1; while (i < j) { if (a[i] + a[j] > a[k]) { c += j - i; j--; } else i++; } } return c; }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Check Every Triple | O(n^3) | O(1) |
| 2 | Sort and Two Pointers (Optimized) | O(n^2) | O(1) |

## Source Code

### ValidTriangleNumber.java

```java
import java.util.Arrays;

public class ValidTriangleNumber {

    public static void main(String[] args) {
        int[][] inputs = {{2, 2, 3, 4}, {4, 2, 3, 4}, {1, 1, 1}, {1, 2, 3}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + triangleNumber(nums.clone()));
            System.out.println();
        }
    }

    public static int triangleNumber(int[] nums) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);
        int count = 0;
        for (int k = nums.length - 1; k >= 2; k--) {
            int i = 0;
            int j = k - 1;
            while (i < j) {
                if (nums[i] + nums[j] > nums[k]) {
                    count += j - i;
                    j--;
                } else {
                    i++;
                }
            }
        }
        return count;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: once the values are sorted and the longest side `c = nums[k]` is fixed, only `a + b > c` needs checking, because the other two inequalities hold automatically. For a fixed `j`, if the smallest candidate `nums[i]` already works, so does everything between `i` and `j`, which counts `j - i` triples at once.

**Input:** `nums = [2, 2, 3, 4]` (already sorted).

| `k` | `c` | Pointers | Counted |
|-----|-----|----------|---------|
| 3 | 4 | i=0, j=2: 2 + 3 > 4 | 2 (pairs with j=2: indexes 0 and 1) |
| 3 | 4 | i=0, j=1: 2 + 2 > 4? no, i=1: stop | 0 |
| 2 | 3 | i=0, j=1: 2 + 2 > 3 | 1 |

**Output:** `3`.
