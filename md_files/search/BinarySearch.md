# Binary Search

Utility class to search an element in a sorted array using Binary Search.

## Algorithm Strategy (Halve the Search Range)

1. **Precondition:** the array must be sorted in ascending order.
2. Keep two indices, `low = 0` and `high = n - 1`, that bound the range which can still contain the element.
3. While `low <= high`, compute the middle index `mid = low + (high - low) / 2` (this form avoids integer overflow for very large arrays).
4. If `arr[mid] == element`, return `mid`.
5. If `arr[mid] < element`, the element can only be on the right, so set `low = mid + 1`.
6. Otherwise it can only be on the left, so set `high = mid - 1`.
7. When the range becomes empty (`low > high`) the element is not present, return `-1`.

## Example walkthrough for finding `9` in `[4, 5, 6, 7, 9, 12, 15, 20, 30]`

- low = 0, high = 8, mid = 4, `arr[4] = 9` — found at index 4.
- Searching `16`: mid = 4 (9 < 16, low = 5), mid = 6 (15 < 16, low = 7), mid = 7 (20 > 16, high = 6), range is empty — not found.

## Complexity Analysis

- **Time Complexity (Best):** `O(1)` — The target is the middle element.
- **Time Complexity (Average):** `O(log n)` — The range is halved on every step.
- **Time Complexity (Worst):** `O(log n)` — The target is absent or found in the last step.
- **Space Complexity:** `O(1)` — Iterative implementation with a few index variables.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Linear Search

Scan the array from the start until the element is found. Works on unsorted data, but cannot exploit ordering.

```java
public static int linearSearch(int[] arr, int element) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == element) return i;
    }
    return -1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Recursive Binary Search

Compare with the middle element and recurse into the half that can contain the element.

```java
public static int binarySearchRecursive(int[] arr, int element, int low, int high) {
    if (low > high) return -1;
    int mid = low + (high - low) / 2;
    if (arr[mid] == element) return mid;
    if (arr[mid] < element) return binarySearchRecursive(arr, element, mid + 1, high);
    return binarySearchRecursive(arr, element, low, mid - 1);
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(log n) recursion stack

### Approach 3: Iterative Binary Search (Optimized)

The same halving idea in a loop: no recursion overhead and `O(1)` space. Computing `mid = low + (high - low) / 2` avoids integer overflow for very large arrays. This is the version implemented in the repository.

```java
public static int binarySearch(int[] arr, int element) {
    int low = 0, high = arr.length - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (arr[mid] == element) return mid;
        else if (arr[mid] < element) low = mid + 1;
        else high = mid - 1;
    }
    return -1;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Approach 4: Library: `Arrays.binarySearch`

The JDK provides the same algorithm. It returns `-(insertionPoint) - 1` (a negative number) when the element is missing, so convert it if you want `-1`.

```java
public static int binarySearchLibrary(int[] arr, int element) {
    int index = Arrays.binarySearch(arr, element);
    return index >= 0 ? index : -1;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Approach 5: First Occurrence (Lower Bound) for Duplicates

When the array contains duplicates the plain search may return any matching index. Keep searching to the left after a match to get the first occurrence.

```java
public static int firstOccurrence(int[] arr, int element) {
    int low = 0, high = arr.length - 1, result = -1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (arr[mid] == element) {
            result = mid;
            high = mid - 1;      // keep looking on the left
        } else if (arr[mid] < element) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return result;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Linear Search | O(n) | O(1) |
| 2 | Recursive Binary Search | O(log n) | O(log n) recursion stack |
| 3 | Iterative Binary Search (Optimized) | O(log n) | O(1) |
| 4 | Library: `Arrays.binarySearch` | O(log n) | O(1) |
| 5 | First Occurrence (Lower Bound) for Duplicates | O(log n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### BinarySearch.java

```java
import java.util.Arrays;

public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        Arrays.sort(arr);
        System.out.println("Sorted Array : " + Arrays.toString(arr));

        int[] elements = {20, 16, 9, 4, 8};
        for (int element : elements) {
            int index = binarySearch(arr, element);
            System.out.println("Search : " + element + " -> " + (index != -1 ? "found at index " + index : "not found"));
        }
    }

    /**
     * Searches for an element in a sorted array.
     *
     * @param arr     the array sorted in ascending order
     * @param element the element to find
     * @return the index of the element, or {@code -1} if it is not present
     */
    public static int binarySearch(int[] arr, int element) {
        // Guard Clause: nothing to search in a null or empty array
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            // Equivalent to (low + high) / 2 but cannot overflow
            int mid = low + (high - low) / 2;
            int val = arr[mid];
            if (val == element) {
                return mid;
            } else if (val < element) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
```
