# Remove Element

Given an integer array `nums` and a value `val`, remove all occurrences of `val` in place and return the number `k` of elements that are not equal to `val`. The first `k` elements of `nums` must hold those values (in any order).

## Algorithm Strategy (Write Pointer)

1. Keep `write = 0`: the position where the next kept value goes.
2. Scan every element; if it is not `val`, copy it to `nums[write]` and advance `write`.
3. Elements equal to `val` are simply skipped.
4. Return `write`.

## Example walkthrough

- For `[3, 2, 2, 3]` and `val = 3`: the two 2s are copied to the front.
- Result: `2`, with the array starting `[2, 2, ...]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — In place.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Remove from a List

Copy into an `ArrayList` and remove each match. Shifts elements on every removal.

```java
public static int listRemove(int[] a, int val) {
    java.util.List<Integer> l = new java.util.ArrayList<>(); for (int x : a) l.add(x);
    while (l.remove((Integer) val)) { }
    return l.size();
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Write Pointer (Optimized)

Copy kept values forward. This is the version implemented in the repository.

```java
public static int writePointer(int[] a, int val) {
    int w = 0; for (int x : a) if (x != val) a[w++] = x; return w;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Swap with the End

When `nums[i] == val`, replace it with the last element and shrink. Fewer writes when matches are rare.

```java
public static int swapEnd(int[] a, int val) {
    int i = 0, n = a.length;
    while (i < n) { if (a[i] == val) a[i] = a[--n]; else i++; }
    return n;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Remove from a List | O(n^2) | O(n) |
| 2 | Write Pointer (Optimized) | O(n) | O(1) |
| 3 | Swap with the End | O(n) | O(1) |

## Source Code

### RemoveElement.java

```java
import java.util.Arrays;

public class RemoveElement {

    public static void main(String[] args) {
        int[][] nums = {{3, 2, 2, 3}, {0, 1, 2, 2, 3, 0, 4, 2}, {1, 1}, {}};
        int[] vals = {3, 2, 1, 5};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", val = " + vals[i]);
            int k = removeElement(nums[i], vals[i]);
            System.out.println("Output : " + k + ", nums = " + Arrays.toString(Arrays.copyOf(nums[i], k)));
            System.out.println();
        }
    }

    public static int removeElement(int[] nums, int val) {
        if (nums == null) {
            return 0;
        }
        int write = 0;
        for (int x : nums) {
            if (x != val) {
                nums[write++] = x;
            }
        }
        return write;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the kept values only ever move forward, so a second index `write` marks where the next kept value lands, and the scan never needs to backtrack.

**Input:** `nums = [3, 2, 2, 3]`, `val = 3`.

| `i` | `nums[i]` | Action | `write` | Array start |
|-----|-----------|--------|---------|-------------|
| 0 | 3 | skip | 0 | [3, 2, 2, 3] |
| 1 | 2 | copy | 1 | [2, 2, 2, 3] |
| 2 | 2 | copy | 2 | [2, 2, 2, 3] |
| 3 | 3 | skip | 2 | [2, 2, 2, 3] |

**Output:** `2`.
