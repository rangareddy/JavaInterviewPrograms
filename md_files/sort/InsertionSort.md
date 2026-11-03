# Insertion Sort

Sort an array in ascending order by repeatedly taking the next element and inserting it into its correct place in the already sorted part on its left, the way most people sort playing cards in their hand.

## Algorithm Strategy (Grow a Sorted Prefix One Element at a Time)

1. Treat the first element as a sorted prefix of length 1.
2. For every following element `key = arr[i]`, look at the sorted prefix from right to left.
3. Shift every element that is greater than `key` one position to the right to open a gap.
4. Put `key` into the gap. After processing index `i` the prefix `arr[0..i]` is sorted.

## Example walkthrough

- For `[5, 2, 4, 6, 1, 3]`: insert 2 -> `[2, 5, 4, 6, 1, 3]`, insert 4 -> `[2, 4, 5, 6, 1, 3]`, 6 stays, insert 1 -> `[1, 2, 4, 5, 6, 3]`, insert 3 -> `[1, 2, 3, 4, 5, 6]`.
- An already sorted array needs only one comparison per element, which makes the algorithm very fast on nearly sorted data.

## Complexity Analysis

- **Time Complexity:** `O(n^2) worst/average, O(n) best` — The best case is an already sorted array; the worst case is a reversed array.
- **Space Complexity:** `O(1)` — Sorts in place; it is stable (equal elements keep their order).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Swap Neighbours (Bubble Style)

Move each new element left with adjacent swaps until it is in place. Correct, but writes more than shifting.

```java
public static void insertionSortSwaps(int[] arr) {
    for (int i = 1; i < arr.length; i++)
        for (int j = i; j > 0 && arr[j - 1] > arr[j]; j--) {
            int temp = arr[j];
            arr[j] = arr[j - 1];
            arr[j - 1] = temp;
        }
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Binary Search for the Position

Find the insertion point with binary search (fewer comparisons), then shift the elements with `System.arraycopy`.

```java
public static void insertionSortBinary(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
        int key = arr[i];
        int low = 0, high = i;
        while (low < high) {
            int mid = (low + high) >>> 1;
            if (arr[mid] <= key) low = mid + 1; else high = mid;
        }
        System.arraycopy(arr, low, arr, low + 1, i - low);
        arr[low] = key;
    }
}
```

- **Time Complexity:** O(n^2) moves, O(n log n) comparisons
- **Space Complexity:** O(1)

### Approach 3: Recursive Insertion Sort

Sort the first `n - 1` elements recursively and insert the last one.

```java
public static void insertionSortRecursive(int[] arr, int n) {
    if (n <= 1) return;
    insertionSortRecursive(arr, n - 1);
    int key = arr[n - 1];
    int j = n - 2;
    while (j >= 0 && arr[j] > key) { arr[j + 1] = arr[j]; j--; }
    arr[j + 1] = key;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 4: Shifting Insertion (Optimized)

Shift larger elements right and drop the key into the gap. This is the version implemented in the repository.

```java
public static void insertionSort(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
        int key = arr[i];
        int j = i - 1;
        while (j >= 0 && arr[j] > key) { arr[j + 1] = arr[j]; j--; }
        arr[j + 1] = key;
    }
}
```

- **Time Complexity:** O(n^2) worst, O(n) best
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Swap Neighbours (Bubble Style) | O(n^2) | O(1) |
| 2 | Binary Search for the Position | O(n^2) moves, O(n log n) comparisons | O(1) |
| 3 | Recursive Insertion Sort | O(n^2) | O(n) |
| 4 | Shifting Insertion (Optimized) | O(n^2) worst, O(n) best | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### InsertionSort.java

```java
import java.util.Arrays;

public class InsertionSort {

    public static void main(String[] args) {
        int[][] inputs = {{5, 2, 4, 6, 1, 3}, {12, 4, 5, 7, 9, 20, 30, 6, 15}, {1, 2, 3, 4}, {4, 3, 2, 1}, {7}};
        for (int[] arr : inputs) {
            System.out.println("Original Array : " + Arrays.toString(arr));
            insertionSort(arr);
            System.out.println("Sorted Array   : " + Arrays.toString(arr));
            System.out.println();
        }
    }

    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}
```
