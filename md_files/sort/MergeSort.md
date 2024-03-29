# Merge Sort

**Definition:** Merge sort is a sorting technique based on divide and conquer technique.

**Merge sort works as follows:**
* Divide the unsorted list into n sublists, each containing one element (a list of one element is considered sorted).
* Repeatedly merge sublists to produce new sorted sublists until there is only one sublist remaining. This will be the sorted list.

![Mergesort](https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Merge_sort_algorithm_diagram.svg/600px-Merge_sort_algorithm_diagram.svg.png)

### Time Complexity

- Best: O(n*log n)
- Average: O(n*log n)
- Worst: O(n*log n)

> Time complexity of Merge Sort is O(n*Log n) in all the 3 cases (worst, average and best) as merge sort always divides the array in two halves and takes linear time to merge two halves.

### Space Complexity

- O(n)

### MergeSort.java

```java
import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int arr[] = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        System.out.println("Array elements before sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
        arr = mergeSort(arr);
        System.out.println("\nArray elements after sorting ");
        Arrays.stream(arr).forEach(val -> System.out.print(val + " "));
    }

    private static int[] mergeSort(int[] arr) {
        int n = arr.length;
        if (n <= 1) {
            return arr;
        }

        int mid = n / 2;
        int left[] = new int[mid];
        int right[] = new int[n - mid];

        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = arr[mid + i];
        }

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    private static int[] merge(int[] left, int[] right) {
        int lengthL = left.length;
        int lengthR = right.length;

        int result[] = new int[lengthL + lengthR];
        int k = 0, i = 0, j = 0;

        while (i < lengthL || j < lengthR) {
            if (i < lengthL && j < lengthR) {
                if (left[i] < right[j]) {
                    result[k++] = left[i++];
                } else {
                    result[k++] = right[j++];
                }
            } else if (i < lengthL) {
                result[k++] = left[i++];
            } else if (j < lengthR) {
                result[k++] = right[j++];
            }
        }
        return result;
    }
}
```
