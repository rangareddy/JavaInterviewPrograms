# Squares of a Sorted Array

Given an integer array `nums` sorted in non-decreasing order, return an array of the squares of each number, also sorted in non-decreasing order.

## Algorithm Strategy (Two Pointers from the Ends)

1. The largest square is at one of the two ends, because the array is sorted and negatives have large magnitudes.
2. Keep `left = 0` and `right = n - 1` and fill the result from the back.
3. Compare `|nums[left]|` with `|nums[right]|`: the larger one is squared and written at `pos`, then its pointer moves inward.
4. Decrease `pos` and repeat until the pointers cross.

## Example walkthrough

- For `[-4, -1, 0, 3, 10]`: 10, then 16 (from -4), then 9, then 1, then 0 are placed from the back.
- Result: `[0, 1, 9, 16, 100]` after squaring.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(n)` — The output array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Square and Sort

Square every value and sort the array.

```java
public static int[] squareSort(int[] a) { int[] r = new int[a.length]; for (int i = 0; i < a.length; i++) r[i] = a[i] * a[i]; java.util.Arrays.sort(r); return r; }
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Two Pointers from the Ends (Optimized)

Fill the result from the back with the larger square. This is the version implemented in the repository.

```java
public static int[] twoPointers(int[] a) {
    int n = a.length; int[] r = new int[n]; int l = 0, h = n - 1;
    for (int p = n - 1; p >= 0; p--) { if (Math.abs(a[l]) > Math.abs(a[h])) { r[p] = a[l] * a[l]; l++; } else { r[p] = a[h] * a[h]; h--; } }
    return r;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Square and Sort | O(n log n) | O(n) |
| 2 | Two Pointers from the Ends (Optimized) | O(n) | O(n) |

## Source Code

### SquaresOfASortedArray.java

```java
import java.util.Arrays;

public class SquaresOfASortedArray {

    public static void main(String[] args) {
        int[][] inputs = {{-4, -1, 0, 3, 10}, {-7, -3, 2, 3, 11}, {1, 2, 3}, {-5}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + Arrays.toString(sortedSquares(nums)));
            System.out.println();
        }
    }

    public static int[] sortedSquares(int[] nums) {
        if (nums == null) {
            return new int[0];
        }
        int n = nums.length;
        int[] result = new int[n];
        int left = 0;
        int right = n - 1;
        for (int pos = n - 1; pos >= 0; pos--) {
            if (Math.abs(nums[left]) > Math.abs(nums[right])) {
                result[pos] = nums[left] * nums[left];
                left++;
            } else {
                result[pos] = nums[right] * nums[right];
                right--;
            }
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: in a sorted array the biggest absolute value is always at one end, so the squares can be written from the back of the result in order.

**Input:** `nums = [-4, -1, 0, 3, 10]`.

| `pos` | `left` value | `right` value | Larger magnitude | Written |
|-------|--------------|---------------|------------------|---------|
| 4 | -4 | 10 | 10 | 100 |
| 3 | -4 | 3 | -4 | 16 |
| 2 | -1 | 3 | 3 | 9 |
| 1 | -1 | 0 | -1 | 1 |
| 0 | 0 | 0 | 0 | 0 |

**Output:** `[0, 1, 9, 16, 100]`.
