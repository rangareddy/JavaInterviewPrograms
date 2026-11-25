# Sort an Array

Given an array of integers `nums`, sort it in ascending order and return it without using any built-in sorting function, in `O(n log n)` time.

## Algorithm Strategy (Top-Down Merge Sort)

1. Split the array into two halves at the midpoint.
2. Sort each half recursively; a range with one element is already sorted.
3. Merge the two sorted halves into a temporary array by repeatedly taking the smaller front element (take from the left on ties to keep the sort stable).
4. Copy the merged range back into the original array.

## Example walkthrough

- For `[5, 2, 3, 1]`: split into `[5, 2]` and `[3, 1]`, sort them to `[2, 5]` and `[1, 3]`, and merge to `[1, 2, 3, 5]`.
- Result: `[1, 2, 3, 5]`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — `log n` levels of splitting, each merge pass is linear.
- **Space Complexity:** `O(n)` — The temporary array used by merging.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Insertion Sort

Insert each element into the sorted prefix. Quadratic, so it is too slow for large inputs.

```java
public static int[] insertion(int[] in) {
    int[] a = in.clone(); for (int i = 1; i < a.length; i++) { int v = a[i], j = i - 1; while (j >= 0 && a[j] > v) { a[j + 1] = a[j]; j--; } a[j + 1] = v; } return a;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Quick Sort

Partition around a pivot and recurse. Fast on average, but O(n^2) on adversarial inputs without a randomised pivot.

```java
public static int[] quick(int[] in) { int[] a = in.clone(); qs(a, 0, a.length - 1, new java.util.Random(1)); return a; }
private static void qs(int[] a, int lo, int hi, java.util.Random r) {
    if (lo >= hi) return; int p = lo + r.nextInt(hi - lo + 1); int t = a[p]; a[p] = a[hi]; a[hi] = t; int pivot = a[hi], i = lo;
    for (int j = lo; j < hi; j++) if (a[j] < pivot) { t = a[i]; a[i] = a[j]; a[j] = t; i++; }
    t = a[i]; a[i] = a[hi]; a[hi] = t; qs(a, lo, i - 1, r); qs(a, i + 1, hi, r);
}
```

- **Time Complexity:** O(n log n) average
- **Space Complexity:** O(log n)

### Approach 3: Merge Sort (Optimized)

Split, sort the halves and merge. Guaranteed `O(n log n)` and stable. This is the version implemented in the repository.

```java
public static int[] merge(int[] in) { int[] a = in.clone(); if (a.length > 1) ms(a, new int[a.length], 0, a.length - 1); return a; }
private static void ms(int[] a, int[] t, int lo, int hi) {
    if (lo >= hi) return; int mid = (lo + hi) / 2; ms(a, t, lo, mid); ms(a, t, mid + 1, hi); int i = lo, j = mid + 1, k = lo;
    while (i <= mid && j <= hi) t[k++] = a[i] <= a[j] ? a[i++] : a[j++]; while (i <= mid) t[k++] = a[i++]; while (j <= hi) t[k++] = a[j++]; System.arraycopy(t, lo, a, lo, hi - lo + 1);
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Insertion Sort | O(n^2) | O(1) |
| 2 | Quick Sort | O(n log n) average | O(log n) |
| 3 | Merge Sort (Optimized) | O(n log n) | O(n) |

## Source Code

### SortAnArray.java

```java
import java.util.Arrays;

public class SortAnArray {

    public static void main(String[] args) {
        int[][] inputs = {{5, 2, 3, 1}, {5, 1, 1, 2, 0, 0}, {1}, {}, {3, -1, 2, -5, 0}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + Arrays.toString(sortArray(nums.clone())));
            System.out.println();
        }
    }

    public static int[] sortArray(int[] nums) {
        if (nums == null || nums.length < 2) {
            return nums == null ? new int[0] : nums;
        }
        mergeSort(nums, new int[nums.length], 0, nums.length - 1);
        return nums;
    }

    private static void mergeSort(int[] nums, int[] tmp, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        mergeSort(nums, tmp, lo, mid);
        mergeSort(nums, tmp, mid + 1, hi);
        int i = lo;
        int j = mid + 1;
        int k = lo;
        while (i <= mid && j <= hi) {
            tmp[k++] = nums[i] <= nums[j] ? nums[i++] : nums[j++];
        }
        while (i <= mid) {
            tmp[k++] = nums[i++];
        }
        while (j <= hi) {
            tmp[k++] = nums[j++];
        }
        System.arraycopy(tmp, lo, nums, lo, hi - lo + 1);
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: sorting two small sorted halves is easy (merge them), and splitting in half reaches single elements after `log n` levels. Merge sort therefore always takes `O(n log n)`.

**Input:** `nums = [5, 2, 3, 1]`.

| Level | Arrays |
|-------|--------|
| split | [5, 2, 3, 1] |
| split | [5, 2] and [3, 1] |
| split | [5] [2] [3] [1] |
| merge | [2, 5] and [1, 3] |
| merge | [1, 2, 3, 5] |

**Output:** `[1, 2, 3, 5]`.
