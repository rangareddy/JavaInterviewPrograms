# Sort Colors

Given an array `nums` with `n` objects coloured red, white or blue (represented by 0, 1 and 2), sort them in place so that objects of the same colour are adjacent, in the order red, white, blue. Do it without using the library sort function.

## Algorithm Strategy (Dutch National Flag in One Pass)

1. Keep three regions with pointers `low`, `mid` and `high`: everything before `low` is 0, everything from `high + 1` on is 2, and `mid` scans the unknown middle.
2. If `nums[mid]` is 0, swap it with `nums[low]` and advance both `low` and `mid`.
3. If it is 1, just advance `mid`.
4. If it is 2, swap it with `nums[high]` and shrink `high` only, because the swapped-in value has not been looked at yet.

## Example walkthrough

- For `[2, 0, 2, 1, 1, 0]` the pointers sweep once: 2s are pushed to the right end and 0s to the left end.
- Result: `[0, 0, 1, 1, 2, 2]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single pass.
- **Space Complexity:** `O(1)` — Three indexes.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Library Sort

Call `Arrays.sort`. Correct, but the problem asks you not to use it.

```java
public static void librarySort(int[] a) { java.util.Arrays.sort(a); }
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(log n)

### Approach 2: Counting Sort (Two Passes)

Count the zeros, ones and twos and overwrite the array.

```java
public static void counting(int[] a) {
    int[] c = new int[3]; for (int v : a) c[v]++; int i = 0; for (int color = 0; color < 3; color++) while (c[color]-- > 0) a[i++] = color;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Dutch National Flag (One Pass) (Optimized)

Three pointers partition the array in a single pass. This is the version implemented in the repository.

```java
public static void flag(int[] a) {
    int lo = 0, mid = 0, hi = a.length - 1;
    while (mid <= hi) { if (a[mid] == 0) { int t = a[lo]; a[lo++] = a[mid]; a[mid++] = t; } else if (a[mid] == 1) mid++; else { int t = a[mid]; a[mid] = a[hi]; a[hi--] = t; } }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Library Sort | O(n log n) | O(log n) |
| 2 | Counting Sort (Two Passes) | O(n) | O(1) |
| 3 | Dutch National Flag (One Pass) (Optimized) | O(n) | O(1) |

## Source Code

### SortColors.java

```java
import java.util.Arrays;

public class SortColors {

    public static void main(String[] args) {
        int[][] inputs = {{2, 0, 2, 1, 1, 0}, {2, 0, 1}, {0}, {1, 1, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            sortColors(nums);
            System.out.println("Output : " + Arrays.toString(nums));
            System.out.println();
        }
    }

    public static void sortColors(int[] nums) {
        if (nums == null) {
            return;
        }
        int low = 0;
        int mid = 0;
        int high = nums.length - 1;
        while (mid <= high) {
            if (nums[mid] == 0) {
                swap(nums, low++, mid++);
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                swap(nums, mid, high--);
            }
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: this is Dijkstra's Dutch national flag problem. Maintain the invariant that `[0, low)` holds zeros, `[low, mid)` holds ones, `(high, end]` holds twos, and `[mid, high]` is still unsorted. Each step either grows a sorted region or shrinks the unknown one, so one pass is enough.

**Input:** `[2, 0, 2, 1, 1, 0]`.

| Step | `low` | `mid` | `high` | Action | Array |
|------|-------|-------|--------|--------|-------|
| 1 | 0 | 0 | 5 | 2: swap with `high` | [0, 0, 2, 1, 1, 2] |
| 2 | 0 | 0 | 4 | 0: swap with `low`, advance both | [0, 0, 2, 1, 1, 2] |
| 3 | 1 | 1 | 4 | 0: advance both | [0, 0, 2, 1, 1, 2] |
| 4 | 2 | 2 | 4 | 2: swap with `high` | [0, 0, 1, 1, 2, 2] |
| 5 | 2 | 2 | 3 | 1: advance | |
| 6 | 2 | 3 | 3 | 1: advance, `mid > high` | done |

**Output:** `[0, 0, 1, 1, 2, 2]`.
