# Selection Sort

Selection sorting is a sorting algorithm where we select the minimum element from the array and put that at its correct position.

!["Selection Sort"](./images/selection_sort_algorithm.png)

## Algorithm Strategy (Select the Minimum)

1. Split the array into a sorted prefix (initially empty) and an unsorted suffix.
2. For each position `i` from `0` to `n - 2`, assume `arr[i]` is the minimum and remember its index in `min_index`.
3. Scan the rest of the array (`j = i + 1 .. n - 1`); whenever `arr[j] < arr[min_index]`, update `min_index = j`.
4. Swap `arr[i]` with `arr[min_index]`, which grows the sorted prefix by one element.
5. When `i` reaches `n - 1` the last element is automatically the largest, so the array is sorted.

## Example walkthrough for `[29, 10, 14, 37, 13]`

- i = 0: minimum is 10, swap with 29 `-> [10, 29, 14, 37, 13]`
- i = 1: minimum is 13, swap with 29 `-> [10, 13, 14, 37, 29]`
- i = 2: minimum is 14, already in place
- i = 3: minimum is 29, swap with 37 `-> [10, 13, 14, 29, 37]`

## Complexity Analysis

- **Time Complexity (Best):** `O(n^2)` — The minimum is always searched for in the whole unsorted part.
- **Time Complexity (Average):** `O(n^2)` — Nested loops over the array.
- **Time Complexity (Worst):** `O(n^2)` — Same number of comparisons regardless of input order.
- **Space Complexity:** `O(1)` — Sorts in place. At most `n - 1` swaps are performed.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Basic Selection Sort

For each position find the minimum of the unsorted suffix and swap it into place. This is the version implemented in the repository.

```java
public static void selectionSortBasic(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < n; j++) {
            if (arr[j] < arr[minIndex]) minIndex = j;
        }
        swap(arr, minIndex, i);
    }
}
```

- **Time Complexity:** O(n^2) in every case
- **Space Complexity:** O(1)

### Approach 2: Stable Selection Sort

The plain swap can change the relative order of equal elements. Instead of swapping, shift the elements between `i` and the minimum one position to the right and insert the minimum at `i`; equal elements keep their order, at the cost of extra moves.

```java
public static void selectionSortStable(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < n; j++) {
            if (arr[j] < arr[minIndex]) minIndex = j;
        }
        int min = arr[minIndex];
        while (minIndex > i) {
            arr[minIndex] = arr[minIndex - 1];
            minIndex--;
        }
        arr[i] = min;
    }
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 3: Double Selection: Min and Max per Pass (Optimized)

Find both the minimum and the maximum in each pass and place them at the two ends, so the number of passes is halved. It is still `O(n^2)` comparisons, but with about half the passes and swaps. If you need better than quadratic time, use Merge Sort, Quick Sort or Heap Sort.

```java
public static void selectionSortDouble(int[] arr) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        int minIndex = left, maxIndex = left;
        for (int j = left; j <= right; j++) {
            if (arr[j] < arr[minIndex]) minIndex = j;
            if (arr[j] > arr[maxIndex]) maxIndex = j;
        }
        swap(arr, left, minIndex);
        // If the maximum was at 'left', it has just been moved to minIndex
        if (maxIndex == left) maxIndex = minIndex;
        swap(arr, right, maxIndex);
        left++;
        right--;
    }
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Basic Selection Sort | O(n^2) in every case | O(1) |
| 2 | Stable Selection Sort | O(n^2) | O(1) |
| 3 | Double Selection: Min and Max per Pass (Optimized) | O(n^2) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above. The version implemented in this repository is Approach 1.

## Source Code

### SelectionSort.java

```java
import java.util.Arrays;

public class SelectionSort {

    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};

        System.out.println("Original Array : " + Arrays.toString(arr));
        selectionSort(arr);
        System.out.println("Sorted Array   : " + Arrays.toString(arr));
    }

    /**
     * Sorts the array in ascending order using Selection Sort.
     *
     * @param arr the array to sort in place
     */
    private static void selectionSort(int[] arr) {
        // Guard Clause: null or single-element arrays are already sorted
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int min_index = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[min_index]) {
                    min_index = j;   // searching for minimum index
                }
            }
            swap(arr, min_index, i); // place the minimum at the end of the sorted prefix
        }
    }

    private static void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```
