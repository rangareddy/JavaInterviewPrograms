# Quick Sort

**Definition:** Quick sort is based on the concept of **Divide and Conquer** approach, just like **Merge Sort**. The
idea of choosing one element as a pivot element and partitioning the array around it such that: Left side of pivot
contains all the elements that are less than the pivot element Right side contains all elements greater than the pivot.

It reduces the space complexity and removes the use of the auxiliary array that is used in merge sort. Selecting a
random pivot in an array results in an improved time complexity in most of the cases.

![Mergesort](https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Quicksort-diagram.svg/400px-Quicksort-diagram.svg.png)

## Algorithm Strategy (Partition Around a Pivot)

1. **Base case:** a range with fewer than two elements (`low >= high`) is already sorted.
2. **Choose a pivot:** take the rightmost element `arr[high]`.
3. **Partition:** keep a pointer `i` to the last element known to be `<= pivot` (initially `low - 1`). Scan `j = low .. high - 1`; whenever `arr[j] <= pivot`, increment `i` and swap `arr[i]` with `arr[j]`.
4. Swap the pivot into position `i + 1`. Everything to its left is `<= pivot`, everything to its right is `> pivot`, so the pivot is in its final position.
5. **Recurse:** sort `arr[low..pivot-1]` and `arr[pivot+1..high]` independently.

## Example walkthrough for `[8, 3, 7, 1, 5]` (pivot = 5)

- Scan: 8 > 5 skip, 3 <= 5 swap to front, 7 > 5 skip, 1 <= 5 swap `-> [3, 1, 7, 8, 5]`
- Place pivot: `-> [3, 1, 5, 8, 7]`; 5 is final. Recurse on `[3, 1]` and `[8, 7]`.

## Complexity Analysis

- **Time Complexity (Best):** `O(n log n)` — The pivot splits the range into two nearly equal parts.
- **Time Complexity (Average):** `O(n log n)` — Random-like input gives balanced partitions.
- **Time Complexity (Worst):** `O(n^2)` — Already sorted input with the rightmost pivot gives maximally unbalanced partitions.
- **Space Complexity:** `O(log n)` — Recursion stack on average (`O(n)` in the worst case); sorts in place.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Lomuto Partition, Last Element as Pivot

Pick the last element as pivot, move everything `<= pivot` to the front with a moving boundary and put the pivot after it. This is the version implemented in the repository. Sorted or all-equal input makes it quadratic.

```java
public static void quickSortLomuto(int[] arr, int low, int high) {
    if (low < high) {
        int pivot = arr[high], i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) swap(arr, ++i, j);
        }
        swap(arr, i + 1, high);
        quickSortLomuto(arr, low, i);
        quickSortLomuto(arr, i + 2, high);
    }
}
```

- **Time Complexity:** Average O(n log n), worst O(n^2)
- **Space Complexity:** O(log n) average, O(n) worst

### Approach 2: Hoare Partition

Two pointers start at both ends and move towards each other, swapping elements that are on the wrong side of the pivot. It does about three times fewer swaps than Lomuto.

```java
public static void quickSortHoare(int[] arr, int low, int high) {
    if (low < high) {
        int pivot = arr[low + (high - low) / 2];
        int i = low - 1, j = high + 1;
        while (true) {
            do { i++; } while (arr[i] < pivot);
            do { j--; } while (arr[j] > pivot);
            if (i >= j) break;
            swap(arr, i, j);
        }
        quickSortHoare(arr, low, j);
        quickSortHoare(arr, j + 1, high);
    }
}
```

- **Time Complexity:** Average O(n log n), worst O(n^2)
- **Space Complexity:** O(log n) average

### Approach 3: Three-Way Partition (Many Duplicates)

Split the range into `< pivot`, `== pivot` and `> pivot` (Dutch National Flag). All equal elements are done after one pass, so arrays with many duplicates no longer degrade.

```java
public static void quickSort3Way(int[] arr, int low, int high) {
    if (low >= high) return;
    int pivot = arr[low + (high - low) / 2];
    int lt = low, i = low, gt = high;
    while (i <= gt) {
        if (arr[i] < pivot) swap(arr, lt++, i++);
        else if (arr[i] > pivot) swap(arr, i, gt--);
        else i++;
    }
    quickSort3Way(arr, low, lt - 1);
    quickSort3Way(arr, gt + 1, high);
}
```

- **Time Complexity:** O(n log n) average, O(n) when all keys are equal
- **Space Complexity:** O(log n) average

### Approach 4: Random Pivot + Recurse on the Smaller Side (Optimized)

A random pivot makes the quadratic worst case extremely unlikely for any input order, and recursing only into the smaller partition (looping on the larger one) bounds the stack depth to `O(log n)` in the worst case.

```java
public static void quickSortOptimized(int[] arr, int low, int high) {
    Random random = new Random();
    while (low < high) {
        swap(arr, low + random.nextInt(high - low + 1), high);   // random pivot to the end
        int pivot = arr[high], i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) swap(arr, ++i, j);
        }
        swap(arr, i + 1, high);
        int p = i + 1;

        if (p - low < high - p) {          // recurse on the smaller side, loop on the larger
            quickSortOptimized(arr, low, p - 1);
            low = p + 1;
        } else {
            quickSortOptimized(arr, p + 1, high);
            high = p - 1;
        }
    }
}
```

- **Time Complexity:** O(n log n) expected
- **Space Complexity:** O(log n) worst case

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Lomuto Partition, Last Element as Pivot | Average O(n log n), worst O(n^2) | O(log n) average, O(n) worst |
| 2 | Hoare Partition | Average O(n log n), worst O(n^2) | O(log n) average |
| 3 | Three-Way Partition (Many Duplicates) | O(n log n) average, O(n) when all keys are equal | O(log n) average |
| 4 | Random Pivot + Recurse on the Smaller Side (Optimized) | O(n log n) expected | O(log n) worst case |

### Optimized Solution

The optimized solution is Approach 4 above. The version implemented in this repository is Approach 1.

#### Optimized solution explained

The idea: pick one element as the pivot, rearrange the range so that everything `<=` the pivot is on its left and everything larger is on its right (`partition`), which puts the pivot at its final sorted index, then sort the left part and the right part the same way. Here the pivot is the rightmost element; `i` marks the end of the "<= pivot" zone and `j` scans the range, swapping each small element into that zone. On average each level does O(n) work and there are about log n levels, giving O(n log n); the sort is in place. The page names a random-pivot variant as the optimized one, but the class in this repository uses the rightmost pivot and plain recursion, which is what is traced.

**Input:** `arr = [12, 4, 5, 7, 9, 20, 30, 6, 15]`.

First partition (`low = 0`, `high = 8`, pivot = 15, `i` starts at -1). Elements `<= 15` are swapped to the front; 20 and 30 are skipped; when `j = 7` the value 6 is swapped with `arr[5]` (20); finally the pivot is swapped into position `i + 1 = 6`.

| j | arr[j] | arr[j] <= 15 ? | i after | Array |
|---|--------|----------------|---------|-------|
| 0 | 12 | yes | 0 | `12 4 5 7 9 20 30 6 15` |
| 1 | 4 | yes | 1 | unchanged |
| 2 | 5 | yes | 2 | unchanged |
| 3 | 7 | yes | 3 | unchanged |
| 4 | 9 | yes | 4 | unchanged |
| 5 | 20 | no | 4 | unchanged |
| 6 | 30 | no | 4 | unchanged |
| 7 | 6 | yes | 5 | `12 4 5 7 9 6 30 20 15` (swap indices 5 and 7) |
| end | pivot 15 | swap `arr[6]` with `arr[8]` | | `12 4 5 7 9 6 15 20 30`, pivot index 6 |

All partitions in the order the recursion runs them:

| Range (low..high) | Pivot | Pivot ends at | Array after the partition |
|-------------------|-------|---------------|---------------------------|
| 0..8 | 15 | 6 | `12 4 5 7 9 6 15 20 30` |
| 0..5 | 6 | 2 | `4 5 6 7 9 12 15 20 30` |
| 0..1 | 5 | 1 | `4 5 6 7 9 12 15 20 30` |
| 3..5 | 12 | 5 | `4 5 6 7 9 12 15 20 30` |
| 3..4 | 9 | 4 | `4 5 6 7 9 12 15 20 30` |
| 7..8 | 30 | 8 | `4 5 6 7 9 12 15 20 30` |

Ranges with `low >= high` (such as 0..0, 3..3, 7..7 or 9..8) stop immediately.

**Edge cases.** One element or an empty range (`low >= high`) does nothing. Duplicates are fine because the test is `<=`. A sorted or reverse-sorted array with the rightmost pivot is the worst case, O(n^2) time and deep recursion, which is the weakness the random-pivot variant addresses. The recursion needs O(log n) stack on average.

**Result:** `[4, 5, 6, 7, 9, 12, 15, 20, 30]`, matching the class output.

## Source Code

### QuickSort.java

```java
import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};

        System.out.println("Original Array : " + Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println("Sorted Array   : " + Arrays.toString(arr));
    }

    /**
     * Sorts {@code arr[low..high]} in place using Quick Sort.
     *
     * @param arr  the array to sort
     * @param low  the first index of the range
     * @param high the last index of the range
     */
    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Select the pivot position
            int pivot = partition(arr, low, high);

            // Sort the elements on the left of pivot
            quickSort(arr, low, pivot - 1);

            // Sort the elements on the right of pivot
            quickSort(arr, pivot + 1, high);
        }
    }

    /**
     * Partitions {@code arr[low..high]} around the rightmost element.
     *
     * @param arr  the array to partition
     * @param low  the first index of the range
     * @param high the last index of the range
     * @return the final index of the pivot
     */
    private static int partition(int[] arr, int low, int high) {
        // choose the rightmost element as pivot
        int pivot = arr[high];

        // pointer for greater element
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // Swapping the elements
    private static void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```
