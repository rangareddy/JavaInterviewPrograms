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

## Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

The idea: keep the left part of the array sorted and grow it one element at a time, like sorting playing cards in the hand. Take the next element as `key`, shift every larger element of the sorted part one slot to the right, and drop `key` into the gap. Shifting (a single assignment per element) is cheaper than repeated swaps, and when the array is nearly sorted very few elements move, so the best case is O(n).

**Input:** `arr = [5, 2, 4, 6, 1, 3]`.

| i | key | Elements shifted right | Array after inserting key |
|---|-----|------------------------|---------------------------|
| 1 | 2 | 5 | `[2, 5, 4, 6, 1, 3]` |
| 2 | 4 | 5 | `[2, 4, 5, 6, 1, 3]` |
| 3 | 6 | none (5 is not greater than 6) | `[2, 4, 5, 6, 1, 3]` |
| 4 | 1 | 6, 5, 4, 2 | `[1, 2, 4, 5, 6, 3]` |
| 5 | 3 | 6, 5, 4 | `[1, 2, 3, 4, 5, 6]` |

```text
i = 4, key = 1:   2 4 5 6 | 1 3    shift 6,5,4,2 right ->  _ 2 4 5 6 | 3    place 1 at index 0
```

**Edge cases.** An empty or one-element array never enters the loop (`i` starts at 1). An already sorted array such as `[1, 2, 3, 4]` does no shifts, only one comparison per element, O(n). A reversed array `[4, 3, 2, 1]` is the worst case with every element shifted past all earlier ones, O(n^2). The test is strictly `arr[j] > key`, so equal elements are not moved past each other and the sort is stable. A `null` array is not guarded and would throw `NullPointerException`.

**Result:** `[1, 2, 3, 4, 5, 6]`, matching the class output.
