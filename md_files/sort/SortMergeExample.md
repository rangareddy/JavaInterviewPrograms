# Sort an Array using Merge Sort

Utility class to sort an integer array using the Merge Sort algorithm.

## Algorithm Strategy (Divide and Conquer)

1. **Base case:** an array with 0 or 1 elements is already sorted.
2. **Divide:** split the array at the middle into a left and a right half and recursively sort each half.
3. **Combine:** merge the two sorted halves by comparing their front elements, copying the smaller one into the output, until both halves are consumed.
4. Every element is copied once per recursion level, and there are `log n` levels.

## Example walkthrough

- For `[3, 4, 1, 9]`:
- Divide into `[3, 4]` and `[1, 9]` (each already sorted after further splitting).
- Merge `[3, 4]` and `[1, 9]`: take 1, then 3, then 4, then 9, giving `[1, 3, 4, 9]`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` across all cases (Best, Average, Worst) due to consistent recursive tree splitting and linear merging work.
- **Space Complexity:** `O(n)` auxiliary space for maintaining temporary sub-arrays and the recursive call stack depth of `O(log n)`.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Library Sort

Use `Arrays.sort`, which is a tuned Dual-Pivot Quicksort for primitives (TimSort, a merge-sort variant, for objects). In real code this is the right answer; the exercise is about implementing the algorithm yourself.

```java
public static int[] sortLibrary(int[] arr) {
    int[] copy = arr.clone();
    Arrays.sort(copy);
    return copy;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n) for the copy

### Approach 2: Merge Sort with `Arrays.copyOfRange`

Split with `copyOfRange`, sort both halves recursively and merge them with two indices. This is the version implemented in the repository.

```java
public static int[] mergeSort(int[] arr) {
    if (arr.length <= 1) return arr;
    int mid = arr.length / 2;
    int[] left = mergeSort(Arrays.copyOfRange(arr, 0, mid));
    int[] right = mergeSort(Arrays.copyOfRange(arr, mid, arr.length));

    int[] merged = new int[arr.length];
    int i = 0, j = 0, k = 0;
    while (i < left.length && j < right.length) {
        merged[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
    }
    while (i < left.length) merged[k++] = left[i++];
    while (j < right.length) merged[k++] = right[j++];
    return merged;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Bottom-Up (Iterative)

Start with sorted runs of length 1 and merge neighbouring runs, doubling the run length each round. No recursion, so no stack usage.

```java
public static int[] mergeSortBottomUp(int[] arr) {
    int n = arr.length;
    int[] src = arr.clone(), dst = new int[n];
    for (int width = 1; width < n; width *= 2) {
        for (int lo = 0; lo < n; lo += 2 * width) {
            int mid = Math.min(lo + width, n), hi = Math.min(lo + 2 * width, n);
            int i = lo, j = mid, k = lo;
            while (i < mid && j < hi) dst[k++] = (src[i] <= src[j]) ? src[i++] : src[j++];
            while (i < mid) dst[k++] = src[i++];
            while (j < hi) dst[k++] = src[j++];
        }
        int[] temp = src; src = dst; dst = temp;
    }
    return src;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 4: Merge Sort with an Insertion-Sort Cutoff

Small ranges are sorted faster by insertion sort than by further recursion. Switching to insertion sort for ranges of at most 16 elements removes most of the recursive calls; libraries such as TimSort use the same idea.

```java
public static int[] mergeSortCutoff(int[] arr) {
    int[] result = arr.clone();
    sortCutoff(result, new int[result.length], 0, result.length - 1);
    return result;
}

private static void sortCutoff(int[] a, int[] buffer, int low, int high) {
    if (high - low < 16) {                            // insertion sort for small ranges
        for (int i = low + 1; i <= high; i++) {
            int value = a[i], j = i - 1;
            while (j >= low && a[j] > value) { a[j + 1] = a[j]; j--; }
            a[j + 1] = value;
        }
        return;
    }
    int mid = low + (high - low) / 2;
    sortCutoff(a, buffer, low, mid);
    sortCutoff(a, buffer, mid + 1, high);
    System.arraycopy(a, low, buffer, low, high - low + 1);
    int i = low, j = mid + 1;
    for (int k = low; k <= high; k++) {
        if (i > mid) a[k] = buffer[j++];
        else if (j > high) a[k] = buffer[i++];
        else if (buffer[j] < buffer[i]) a[k] = buffer[j++];
        else a[k] = buffer[i++];
    }
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 5: Top-Down with One Shared Buffer (Optimized)

Allocate a single auxiliary array once and merge index ranges instead of creating arrays at every level of the recursion. Skipping the merge when the two halves are already in order makes sorted input linear.

```java
public static int[] mergeSortBuffer(int[] arr) {
    int[] result = arr.clone();
    sort(result, new int[result.length], 0, result.length - 1);
    return result;
}

private static void sort(int[] a, int[] buffer, int low, int high) {
    if (low >= high) return;
    int mid = low + (high - low) / 2;
    sort(a, buffer, low, mid);
    sort(a, buffer, mid + 1, high);
    if (a[mid] <= a[mid + 1]) return;                 // already in order
    System.arraycopy(a, low, buffer, low, high - low + 1);
    int i = low, j = mid + 1;
    for (int k = low; k <= high; k++) {
        if (i > mid) a[k] = buffer[j++];
        else if (j > high) a[k] = buffer[i++];
        else if (buffer[j] < buffer[i]) a[k] = buffer[j++];
        else a[k] = buffer[i++];
    }
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n) buffer + O(log n) stack

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Library Sort | O(n log n) | O(n) for the copy |
| 2 | Merge Sort with `Arrays.copyOfRange` | O(n log n) | O(n) |
| 3 | Bottom-Up (Iterative) | O(n log n) | O(n) |
| 4 | Merge Sort with an Insertion-Sort Cutoff | O(n log n) | O(n) |
| 5 | Top-Down with One Shared Buffer (Optimized) | O(n log n) | O(n) buffer + O(log n) stack |

### Optimized Solution

The optimized solution is Approach 5 above. The version implemented in this repository is Approach 2.

## Source Code

### SortMergeExample.java

```java
import java.util.Arrays;

public class SortMergeExample {
    public static void main(String[] args) {
        int[] arr = {3, 4, 1, 9, 5, 8};

        System.out.println("Original Array : " + Arrays.toString(arr));
        int[] sortedArr = mergeSort(arr);
        System.out.println("Sorted Array   : " + Arrays.toString(sortedArr));
    }

    /**
     * Recursively divides the array into sub-arrays and merges them in sorted order.
     *
     * @param arr the input integer array to sort
     * @return a new sorted integer array
     */

    private static int[] mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return arr;
        }
        int n = arr.length;
        int mid = n/2;

        // Split array into left and right sub-arrays
        int left[] = new int[mid];
        int right[] = new int[n-mid];
        for(int i=0; i<mid; i++) {
            left[i] = arr[i];
        }
        for(int j=mid; j<n; j++) {
            right[j-mid] = arr[j];
        }

        // Recursively sort both halves
        left = mergeSort(left);
        right = mergeSort(right);

        // Combine the sorted halves
        return merge(left, right);
    }

    /**
     * Merges two sorted arrays into a single sorted array.
     *
     * @fn_param a the first sorted sub-array
     * @fn_param b the second sorted sub-array
     * @return a merged sorted array containing all elements from {@code a} and {@code b}
     */
    private static int[] merge(int[] a, int[] b) {
        int []result = new int[a.length + b.length];
        int m = a.length;
        int n = b.length;
        int i = 0; // Pointer for array a
        int j = 0; // Pointer for array b
        int k = 0; // Pointer for result array

        while(i < m || j < n) {
            if(i < m && j < n) {
                if(a[i] < b[j]) {
                    result[k++] = a[i++];
                } else {
                    result[k++] = b[j++];
                }

            } else if (i < m) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }
        return result;
    }
}
```
