# Sort an Array

Given an array of integers `nums`, sort it in ascending order and return it, without using a built-in sorting function. Aim for `O(n log n)` time.

## Algorithm Strategy (Merge Sort (Top-Down, with One Shared Buffer))

1. Split the range `[lo, hi)` in the middle, sort each half recursively, then merge the two sorted halves.
2. A range of length 0 or 1 is already sorted, which ends the recursion.
3. To merge, copy the two halves into the shared buffer and write back into `nums` by repeatedly taking the smaller front element. Taking from the left half on ties makes the sort stable.
4. Because the work is split evenly, the time is `O(n log n)` in the best, average and worst case, unlike quick sort whose worst case is `O(n^2)`.

## Example walkthrough

- For `[5, 2, 3, 1]`: split into `[5, 2]` and `[3, 1]`, sort to `[2, 5]` and `[1, 3]`.
- Merge by taking 1, 2, 3, 5: `[1, 2, 3, 5]`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — The array is halved `log n` times and each level merges `n` elements.
- **Space Complexity:** `O(n)` — The merge buffer.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Heap Sort

Build a max-heap in place, then repeatedly swap the maximum to the end and sift down. It is `O(n log n)` in the worst case with `O(1)` extra space, but not stable.

```java
public static int[] sortArrayHeap(int[] nums) {
    int n = nums.length;
    for (int i = n / 2 - 1; i >= 0; i--) siftDown(nums, i, n);
    for (int end = n - 1; end > 0; end--) {
        int t = nums[0]; nums[0] = nums[end]; nums[end] = t;
        siftDown(nums, 0, end);
    }
    return nums;
}

private static void siftDown(int[] a, int i, int size) {
    while (true) {
        int largest = i, l = 2 * i + 1, r = l + 1;
        if (l < size && a[l] > a[largest]) largest = l;
        if (r < size && a[r] > a[largest]) largest = r;
        if (largest == i) return;
        int t = a[i]; a[i] = a[largest]; a[largest] = t;
        i = largest;
    }
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1)

### Approach 2: Quick Sort with a Random Pivot

Partition around a random pivot and sort both sides. The expected time is `O(n log n)`; a random pivot makes the `O(n^2)` worst case very unlikely.

```java
public static int[] sortArrayQuick(int[] nums) {
    quickSort(nums, 0, nums.length - 1, new Random(1));
    return nums;
}

private static void quickSort(int[] a, int lo, int hi, Random rnd) {
    if (lo >= hi) return;
    int p = lo + rnd.nextInt(hi - lo + 1);
    int pivot = a[p];
    int i = lo, j = hi;
    while (i <= j) {                       // Hoare-style partition
        while (a[i] < pivot) i++;
        while (a[j] > pivot) j--;
        if (i <= j) { int t = a[i]; a[i] = a[j]; a[j] = t; i++; j--; }
    }
    quickSort(a, lo, j, rnd);
    quickSort(a, i, hi, rnd);
}
```

- **Time Complexity:** O(n log n) expected, O(n^2) worst case
- **Space Complexity:** O(log n) recursion expected

### Approach 3: Merge Sort (Optimized)

Halve the range, sort each half and merge through a shared buffer; the running time does not depend on the input order. This is the version implemented in the repository.

```java
public static int[] sortArray(int[] nums) {
    mergeSort(nums, new int[nums.length], 0, nums.length);
    return nums;
}

private static void mergeSort(int[] nums, int[] buffer, int lo, int hi) {
    if (hi - lo < 2) {
        return;
    }
    int mid = lo + (hi - lo) / 2;
    mergeSort(nums, buffer, lo, mid);
    mergeSort(nums, buffer, mid, hi);
    System.arraycopy(nums, lo, buffer, lo, hi - lo);
    int left = lo;
    int right = mid;
    for (int k = lo; k < hi; k++) {
        if (left < mid && (right >= hi || buffer[left] <= buffer[right])) {
            nums[k] = buffer[left++];
        } else {
            nums[k] = buffer[right++];
        }
    }
}
```

- **Time Complexity:** O(n log n) in every case
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Heap Sort | O(n log n) | O(1) |
| 2 | Quick Sort with a Random Pivot | O(n log n) expected, O(n^2) worst case | O(log n) recursion expected |
| 3 | Merge Sort (Optimized) | O(n log n) in every case | O(n) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### SortAnArray.java

```java
import java.util.Arrays;

public class SortAnArray {

    public static void main(String[] args) {
        int[][] inputs = {{5, 2, 3, 1}, {5, 1, 1, 2, 0, 0}, {}, {7}, {-3, 4, -3, 0}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + Arrays.toString(sortArray(nums.clone())));
            System.out.println();
        }
    }

    public static int[] sortArray(int[] nums) {
        mergeSort(nums, new int[nums.length], 0, nums.length);
        return nums;
    }

    private static void mergeSort(int[] nums, int[] buffer, int lo, int hi) {
        if (hi - lo < 2) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        mergeSort(nums, buffer, lo, mid);
        mergeSort(nums, buffer, mid, hi);
        System.arraycopy(nums, lo, buffer, lo, hi - lo);
        int left = lo;
        int right = mid;
        for (int k = lo; k < hi; k++) {
            if (left < mid && (right >= hi || buffer[left] <= buffer[right])) {
                nums[k] = buffer[left++];
            } else {
                nums[k] = buffer[right++];
            }
        }
    }
}
```
