# Quick Sort

**Definition:** Quick sort is based on the concept of **Divide and Conquer** approach, just like **Merge Sort**. The idea of choosing one element as a pivot element and partitioning the array around it such that: Left side of pivot contains all the elements that are less than the pivot element Right side contains all elements greater than the pivot.

It reduces the space complexity and removes the use of the auxiliary array that is used in merge sort. Selecting a random pivot in an array results in an improved time complexity in most of the cases.

![Mergesort](https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Quicksort-diagram.svg/400px-Quicksort-diagram.svg.png)

## Quick Sort Complexity

**Time Complexity**
* Best Case Time Complexity [Big-omega]: O(n*log n)
* Worst Case Time Complexity [Big-O] : O(n^2)
* Average Case Time Complexity [Big-theta]: O(n*log n)

**Space Complexity**
* Space complexity: O(log n).

### QuickSort.java

```java
import java.util.Arrays;

/**
 * User: Ranga Reddy
 * Date: 10/5/2020
 * Time: 5:12 PM
 * Description: Quick Sort example
 */

public class QuickSort {
    public static void main(String[] args) {
        int arr[] = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        System.out.println("Array elements before sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
        quickSort(arr, 0, arr.length-1);
        System.out.println("\nArray elements after sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {

            // Select the pivot position
            int pivot = partition(arr, low, high);

            // Sort the elements on the left of pivot
            quickSort(arr, low, pivot-1);

            // Sort the elements on the right of pivot
            quickSort(arr, pivot+1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {

        // Select the pivot element
        int pivot = array[high];
        int i = low - 1;

        for (int j=low; j<high; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i+1, high);
        return i + 1;
    }

    // Swapping the elements
    private static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j]= temp;
    }
}
```

