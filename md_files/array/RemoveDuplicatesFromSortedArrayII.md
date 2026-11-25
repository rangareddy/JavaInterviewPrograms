# Remove Duplicates from Sorted Array II

Given a sorted array `nums`, remove duplicates in place so that each value appears at most twice, keeping the relative order. Return the new length `k`; the first `k` elements must hold the result.

## Algorithm Strategy (Compare with the Element Two Behind the Write Index)

1. Keep a write index `k`. The first two elements are always kept.
2. For every later element `x`, keep it only if `x != nums[k - 2]`. Because the array is sorted, this means fewer than two copies of `x` have been kept so far.
3. When kept, write it at `nums[k]` and increase `k`.
4. Return `k`.

## Example walkthrough

- For `[1, 1, 1, 2, 2, 3]`: keep 1, 1; the third 1 equals `nums[0]` so it is skipped; 2 and 2 are kept; 3 is kept.
- Result: `5`, array starts `[1, 1, 2, 2, 3]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — In place.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Count and Shift

Count the run of each value and remove extras by shifting the tail left. Quadratic.

```java
public static int shifting(int[] a) {
    int n = a.length, i = 2; while (i < n) { if (a[i] == a[i - 1] && a[i] == a[i - 2]) { for (int j = i; j + 1 < n; j++) a[j] = a[j + 1]; n--; } else i++; } return n;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Compare with Two Behind (Optimized)

Keep an element only if it differs from the one two places behind the write index. This is the version implemented in the repository.

```java
public static int twoBehind(int[] a) {
    if (a.length <= 2) return a.length; int k = 2; for (int i = 2; i < a.length; i++) if (a[i] != a[k - 2]) a[k++] = a[i]; return k;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Count and Shift | O(n^2) | O(1) |
| 2 | Compare with Two Behind (Optimized) | O(n) | O(1) |

## Source Code

### RemoveDuplicatesFromSortedArrayII.java

```java
import java.util.Arrays;

public class RemoveDuplicatesFromSortedArrayII {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 1, 2, 2, 3}, {0, 0, 1, 1, 1, 1, 2, 3, 3}, {1}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            int k = removeDuplicates(nums);
            System.out.println("Output : " + k + ", " + Arrays.toString(Arrays.copyOf(nums, k)));
            System.out.println();
        }
    }

    public static int removeDuplicates(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length <= 2) {
            return nums.length;
        }
        int k = 2;
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] != nums[k - 2]) {
                nums[k++] = nums[i];
            }
        }
        return k;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: in a sorted array, the result array is also sorted, so a value already has two copies in the result exactly when it equals the element two places behind the write position. Comparing with `nums[k - 2]` therefore needs no counter.

**Input:** `nums = [1, 1, 1, 2, 2, 3]`.

| `i` | `nums[i]` | `nums[k - 2]` | Action | `k` after |
|-----|-----------|---------------|--------|-----------|
| 2 | 1 | 1 | skip | 2 |
| 3 | 2 | 1 | write at 2 | 3 |
| 4 | 2 | 1 | write at 3 | 4 |
| 5 | 3 | 2 | write at 4 | 5 |

**Output:** `5, [1, 1, 2, 2, 3]`.
