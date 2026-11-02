# Quick Sort

**Definition:** Quick sort is based on the concept of **Divide and Conquer** approach, just like **Merge Sort**. The
idea of choosing one element as a pivot element and partitioning the array around it such that: Left side of pivot
contains all the elements that are less than the pivot element Right side contains all elements greater than the pivot.

It reduces the space complexity and removes the use of the auxiliary array that is used in merge sort. Selecting a
random pivot in an array results in an improved time complexity in most of the cases.

![Mergesort](https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Quicksort-diagram.svg/400px-Quicksort-diagram.svg.png)

### Algorithm Strategy (Partition Around a Pivot)

1. Choose the rightmost element as the pivot.
2. Partition the range so that elements `<= pivot` are on its left and larger elements are on its right.
3. Recursively sort the left and right parts of the pivot.

### Time Complexity

- Best: O(n*log n)
- Average: O(n*log n)
- Worst: O(n^2)

### Space Complexity

- O(log n)

### QuickSort.java

```java
import java.util.Arrays;

/**
 * Utility class to sort an integer array using Quick Sort (divide and conquer).
 *
 * <p><b>Algorithm Strategy (Partition Around a Pivot):</b>
 * <ol>
 *   <li>Choose the rightmost element as the pivot.</li>
 *   <li>Partition the range so that elements {@code <= pivot} are on its left and larger elements are on its right.</li>
 *   <li>Recursively sort the left and right parts of the pivot.</li>
 * </ol>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity (Best):</b> {@code O(n log n)} — The pivot splits the range into two nearly equal parts.</li>
 *   <li><b>Time Complexity (Average):</b> {@code O(n log n)} — Random-like input gives balanced partitions.</li>
 *   <li><b>Time Complexity (Worst):</b> {@code O(n^2)} — Already sorted input with the rightmost pivot gives maximally unbalanced partitions.</li>
 *   <li><b>Space Complexity:</b> {@code O(log n)} — Recursion stack on average (O(n) in the worst case); sorts in place.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        System.out.println("Array elements before sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
        quickSort(arr, 0, arr.length - 1);
        System.out.println("\nArray elements after sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
    }

    /**
     * Sorts {@code arr[low..high]} in place using Quick Sort.
     *
     * @param arr the array to sort
     * @param low the first index of the range
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

