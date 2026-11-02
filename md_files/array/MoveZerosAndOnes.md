# Move Zeros and Ones (Sort 0s, 1s, 2s)

Utility class to sort an array containing 0s, 1s, and 2s using Dijkstra's Dutch National Flag algorithm.

## Algorithm Strategy (Dutch National Flag, Three-Way Partitioning)

1. Maintain three pointers: `low` (everything before it is 0), `mid` (the element being examined) and `high` (everything after it is 2). The region between `low` and `mid` holds 1s and `[mid, high]` is still unknown.
2. Loop while `mid <= high` and look at `arr[mid]`.
3. If it is 0, swap it with `arr[low]`, then advance both `low` and `mid`.
4. If it is 1, it is already in the middle region, so just advance `mid`.
5. If it is 2, swap it with `arr[high]` and decrement `high`. Do not advance `mid`, because the element swapped in from `high` has not been examined yet.
6. Any other value throws an `IllegalArgumentException`. When `mid` passes `high` the array is sorted in a single pass.

## Example walkthrough

- For `[2, 0, 1]`:
- `mid = 0`: value 2, swap with `high` gives `[1, 0, 2]`, `high = 1`.
- `mid = 0`: value 1, advance `mid`. `mid = 1`: value 0, swap with `low` gives `[0, 1, 2]`.
- `mid` passes `high`: sorted.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Single pass linear scan where `n` is the array length.
- **Space Complexity:** `O(1)` — Operates entirely in-place with zero additional heap memory allocation.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Library Sort

Simply sort the array. Correct for any values, but ignores the fact that only three distinct values exist.

```java
public static void sortColorsLibrary(int[] arr) {
    Arrays.sort(arr);
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1) to O(log n)

### Approach 2: Counting Sort (Two Passes)

Pass 1: count how many 0s, 1s and 2s there are. Pass 2: overwrite the array with that many 0s, then 1s, then 2s.

```java
public static void sortColorsCounting(int[] arr) {
    int[] count = new int[3];
    for (int v : arr) count[v]++;

    int index = 0;
    for (int value = 0; value < 3; value++) {
        for (int i = 0; i < count[value]; i++) {
            arr[index++] = value;
        }
    }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Dutch National Flag (Optimized, One Pass)

Three pointers partition the array in a single pass using swaps only: 0s go to the front, 2s to the back and 1s stay in the middle. Implemented in the repository.

```java
public static void sortColors(int[] arr) {
    int low = 0, mid = 0, high = arr.length - 1;
    while (mid <= high) {
        if (arr[mid] == 0) {
            swap(arr, low++, mid++);
        } else if (arr[mid] == 1) {
            mid++;
        } else {
            swap(arr, mid, high--);
        }
    }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Library Sort | O(n log n) | O(1) to O(log n) |
| 2 | Counting Sort (Two Passes) | O(n) | O(1) |
| 3 | Dutch National Flag (Optimized, One Pass) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### MoveZerosAndOnes.java

```java
import java.util.Arrays;

public class MoveZerosAndOnes {

    public static void main(String[] args) {
        int[] input = {0, 1, 2, 0, 1, 0, 1, 2, 1, 0, 2};

        System.out.println("Original Array : " + Arrays.toString(input));
        sortColors(input);
        System.out.println("Sorted Array   : " + Arrays.toString(input));
    }

    /**
     * Sorts an array consisting of only 0s, 1s, and 2s in-place.
     *
     * <p>Note: Mutates the array directly. For standard API design, returning {@code void}
     * is preferred for in-place modifications.
     *
     * @param arr the input array containing elements of value 0, 1, or 2
     */
    public static void sortColors(int[] arr) {
        // Guard Clause: null or single-element inputs require no sorting
        if (arr == null || arr.length <= 1) {
            return;
        }

        int low = 0;
        int mid = 0;
        int high = arr.length - 1;

        while (mid <= high) {
            switch (arr[mid]) {
                case 0:
                    // Swap current 0 to the low pointer boundary
                    swap(arr, low, mid);
                    low++;
                    mid++;
                    break;

                case 1:
                    // 1 is in its correct relative middle position; move to next element
                    mid++;
                    break;

                case 2:
                    // Swap current 2 to the high pointer boundary.
                    // Do NOT increment mid yet because the swapped element from 'high'
                    // has not been evaluated.
                    swap(arr, mid, high);
                    high--;
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Array contains invalid element: " + arr[mid] + ". Only 0, 1, and 2 are supported."
                    );
            }
        }
    }

    /**
     * Helper method to swap two elements in an array in-place.
     *
     * @param arr the array where swap occurs
     * @param i   index of first element
     * @param j   index of second element
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```
