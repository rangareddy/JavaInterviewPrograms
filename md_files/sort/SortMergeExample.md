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

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Library Sort | O(n log n) | O(n) for the copy |
| 2 | Merge Sort with `Arrays.copyOfRange` | O(n log n) | O(n) |

### Optimized Solution

The optimized variants (one shared buffer, bottom-up iteration) are described on the [Merge Sort](./MergeSort.md) page.

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
