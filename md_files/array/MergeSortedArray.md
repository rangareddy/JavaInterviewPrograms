# Merge Sorted Array

You are given two sorted integer arrays `nums1` (with `m` elements followed by `n` free slots at the end) and `nums2` (with `n` elements). Merge `nums2` into `nums1` so that `nums1` becomes one sorted array, in place.

## Algorithm Strategy (Merge from the Back with Three Pointers)

1. Merging from the front would overwrite elements of `nums1` that have not been used yet, so fill the array from the back, where the free slots are.
2. Keep three pointers: `i = m - 1` (last real element of `nums1`), `j = n - 1` (last element of `nums2`) and `k = m + n - 1` (next slot to fill).
3. Place the larger of `nums1[i]` and `nums2[j]` at `nums1[k]` and move the corresponding pointer and `k` one step left.
4. When `nums2` is used up, the remaining part of `nums1` is already in place. (If `nums1` runs out first, copy the rest of `nums2`.)

## Example walkthrough

- For `nums1 = [1, 2, 3, 0, 0, 0]`, `m = 3`, `nums2 = [2, 5, 6]`: fill from the back with 6, 5, 3, 2, 2, 1.
- Result: `[1, 2, 2, 3, 5, 6]`.

## Complexity Analysis

- **Time Complexity:** `O(m + n)` — Every element is placed once.
- **Space Complexity:** `O(1)` — No extra array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Append and Sort

Copy `nums2` after the real elements of `nums1` and sort the whole array.

```java
public static void mergeSort(int[] nums1, int m, int[] nums2, int n) {
    System.arraycopy(nums2, 0, nums1, m, n);
    Arrays.sort(nums1, 0, m + n);
}
```

- **Time Complexity:** O((m + n) log (m + n))
- **Space Complexity:** O(1)

### Approach 2: Copy and Merge from the Front

Copy the real part of `nums1`, then merge two sorted sequences into `nums1` from the front.

```java
public static void mergeCopy(int[] nums1, int m, int[] nums2, int n) {
    int[] copy = Arrays.copyOf(nums1, m);
    int i = 0, j = 0, k = 0;
    while (i < m && j < n) nums1[k++] = copy[i] <= nums2[j] ? copy[i++] : nums2[j++];
    while (i < m) nums1[k++] = copy[i++];
    while (j < n) nums1[k++] = nums2[j++];
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(m)

### Approach 3: Insert Each Element with Shifting

Insert every element of `nums2` into `nums1` by shifting. Easy to write but quadratic.

```java
public static void mergeShift(int[] nums1, int m, int[] nums2, int n) {
    int size = m;
    for (int j = 0; j < n; j++) {
        int pos = size;
        while (pos > 0 && nums1[pos - 1] > nums2[j]) { nums1[pos] = nums1[pos - 1]; pos--; }
        nums1[pos] = nums2[j];
        size++;
    }
}
```

- **Time Complexity:** O(n * (m + n))
- **Space Complexity:** O(1)

### Approach 4: Merge from the Back (Optimized)

Fill `nums1` from its free end with the larger of the two last elements. This is the version implemented in the repository.

```java
public static void merge(int[] nums1, int m, int[] nums2, int n) {
    int i = m - 1, j = n - 1, k = m + n - 1;
    while (j >= 0) {
        if (i >= 0 && nums1[i] > nums2[j]) nums1[k--] = nums1[i--];
        else nums1[k--] = nums2[j--];
    }
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Append and Sort | O((m + n) log (m + n)) | O(1) |
| 2 | Copy and Merge from the Front | O(m + n) | O(m) |
| 3 | Insert Each Element with Shifting | O(n * (m + n)) | O(1) |
| 4 | Merge from the Back (Optimized) | O(m + n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### MergeSortedArray.java

```java
import java.util.Arrays;

public class MergeSortedArray {

    public static void main(String[] args) {
        int[][] first = {{1, 2, 3, 0, 0, 0}, {1}, {0}, {4, 5, 6, 0, 0, 0}};
        int[] sizes = {3, 1, 0, 3};
        int[][] second = {{2, 5, 6}, {}, {1}, {1, 2, 3}};
        for (int i = 0; i < first.length; i++) {
            System.out.println("Input  : nums1 = " + Arrays.toString(first[i]) + ", m = " + sizes[i] + ", nums2 = " + Arrays.toString(second[i]));
            merge(first[i], sizes[i], second[i], second[i].length);
            System.out.println("Output : " + Arrays.toString(first[i]));
            System.out.println();
        }
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while (j >= 0) {
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
    }
}
```
