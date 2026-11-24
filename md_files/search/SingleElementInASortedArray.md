# Single Element in a Sorted Array

You are given a sorted array in which every element appears exactly twice, except for one element that appears once. Return that single element in `O(log n)` time and `O(1)` space.

## Algorithm Strategy (Binary Search on Even Indexes)

1. Before the single element, each pair starts at an even index (`nums[2k] == nums[2k + 1]`); after it, the pairs start at odd indexes.
2. Binary search on even `mid` (use `mid -= mid % 2`).
3. If `nums[mid] == nums[mid + 1]`, the pairs are still aligned, so the single element is to the right: `lo = mid + 2`.
4. Otherwise it is at `mid` or to the left: `hi = mid`. When `lo == hi`, that index holds the answer.

## Example walkthrough

- For `[1, 1, 2, 3, 3, 4, 4, 8, 8]`: the pairs start at even indexes only up to index 1; the first break is at index 2, where the single element sits.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — The range halves each step.
- **Space Complexity:** `O(1)` — A few indexes.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: XOR of All Elements

Equal values cancel out under XOR, leaving the single element. Ignores the sorted property, so it is linear.

```java
public static int xor(int[] a) { int x = 0; for (int v : a) x ^= v; return x; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Linear Scan in Pairs

Compare `nums[i]` with `nums[i + 1]` stepping by two.

```java
public static int pairs(int[] a) {
    for (int i = 0; i + 1 < a.length; i += 2) if (a[i] != a[i + 1]) return a[i];
    return a[a.length - 1];
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Binary Search on Even Indexes (Optimized)

Use the parity of the pair start to decide the side. This is the version implemented in the repository.

```java
public static int binary(int[] a) {
    int lo = 0, hi = a.length - 1;
    while (lo < hi) { int mid = lo + (hi - lo) / 2; mid -= mid % 2; if (a[mid] == a[mid + 1]) lo = mid + 2; else hi = mid; }
    return a[lo];
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | XOR of All Elements | O(n) | O(1) |
| 2 | Linear Scan in Pairs | O(n) | O(1) |
| 3 | Binary Search on Even Indexes (Optimized) | O(log n) | O(1) |

## Source Code

### SingleElementInASortedArray.java

```java
import java.util.Arrays;

public class SingleElementInASortedArray {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 2, 3, 3, 4, 4, 8, 8}, {3, 3, 7, 7, 10, 11, 11}, {5}, {1, 2, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + singleNonDuplicate(nums));
            System.out.println();
        }
    }

    public static int singleNonDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            mid -= mid % 2;
            if (nums[mid] == nums[mid + 1]) {
                lo = mid + 2;
            } else {
                hi = mid;
            }
        }
        return nums[lo];
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: the single element shifts every pair after it by one position. Left of it, pairs begin at even indexes; right of it, at odd indexes. Checking the pair at an even `mid` tells which side the single element is on.

**Input:** `nums = [1, 1, 2, 3, 3, 4, 4, 8, 8]`.

| `lo` | `hi` | `mid` (even) | `nums[mid] == nums[mid+1]`? | Next |
|------|------|--------------|------------------------------|------|
| 0 | 8 | 4 | 3 vs 4, no | `hi = 4` |
| 0 | 4 | 2 | 2 vs 3, no | `hi = 2` |
| 0 | 2 | 0 | 1 vs 1, yes | `lo = 2` |

`lo == hi == 2`.

**Output:** `2`.
