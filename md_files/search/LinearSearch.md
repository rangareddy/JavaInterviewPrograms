# Linear Search

Utility class to search an element in an array using Linear Search.

## Algorithm Strategy (Sequential Scan)

1. Start at index `0` and compare each element with the one that is searched for.
2. As soon as `arr[i] == element`, return `i`; the first matching index wins.
3. If the end of the array is reached without a match, return `-1`.
4. No ordering is required, which is the advantage over Binary Search; the cost is that every element may be inspected.

## Example walkthrough for finding `20` in `[12, 4, 5, 7, 9, 20, 30, 6, 15]`

- Indices 0 to 4 hold 12, 4, 5, 7 and 9, none of which equals 20.
- `arr[5] == 20`, so the result is index 5.

## Complexity Analysis

- **Time Complexity (Best):** `O(1)` — The element is the first one.
- **Time Complexity (Average):** `O(n)` — About half of the array is scanned.
- **Time Complexity (Worst):** `O(n)` — The element is last or absent.
- **Space Complexity:** `O(1)` — Only an index variable is used.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Simple Loop

Check the elements one by one from the start. This is the version implemented in the repository.

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

### Approach 2: Recursive

Compare the element at the current index and recurse on the rest of the array. Same number of comparisons, but uses stack space.

```java
public static int linearSearchRecursive(int[] arr, int element, int index) {
    if (index >= arr.length) return -1;
    if (arr[index] == element) return index;
    return linearSearchRecursive(arr, element, index + 1);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n) recursion stack

### Approach 3: Streams

Express the scan declaratively with `IntStream`. Convenient, but not faster than the loop.

```java
public static int linearSearchStream(int[] arr, int element) {
    return java.util.stream.IntStream.range(0, arr.length)
            .filter(i -> arr[i] == element)
            .findFirst()
            .orElse(-1);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: Sentinel Linear Search (Optimized)

Temporarily place the searched element in the last slot, so the loop does not need the `i < n` bound check: only one comparison per element instead of two. The last element is restored and checked separately. It is still `O(n)`, only the constant factor improves. If many searches are needed, sort once and use Binary Search, or build a `HashMap`.

```java
public static int linearSearchSentinel(int[] arr, int element) {
    int n = arr.length;
    if (n == 0) return -1;
    int last = arr[n - 1];
    arr[n - 1] = element;                 // sentinel guarantees the loop stops

    int i = 0;
    while (arr[i] != element) i++;

    arr[n - 1] = last;                    // restore the original value
    return (i < n - 1 || last == element) ? i : -1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Simple Loop | O(n) | O(1) |
| 2 | Recursive | O(n) | O(n) recursion stack |
| 3 | Streams | O(n) | O(1) |
| 4 | Sentinel Linear Search (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above. The version implemented in this repository is Approach 1.

## Source Code

### LinearSearch.java

```java
import java.util.Arrays;

public class LinearSearch {

    public static void main(String[] args) {
        int[] arr = {12, 4, 5, 7, 9, 20, 30, 6, 15};
        System.out.println("Array : " + Arrays.toString(arr));

        int[] elements = {20, 12, 15, 8};
        for (int element : elements) {
            int index = linearSearch(arr, element);
            System.out.println("Search : " + element + " -> " + (index != -1 ? "found at index " + index : "not found"));
        }
    }

    /**
     * Searches for an element by scanning the array from the start.
     *
     * @param arr     the array to scan (any order)
     * @param element the element to find
     * @return the index of the first occurrence, or {@code -1} if it is not present
     */
    public static int linearSearch(int[] arr, int element) {
        // Guard Clause: nothing to search in a null or empty array
        if (arr == null) {
            return -1;
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == element) {
                return i;
            }
        }
        return -1;
    }
}
```
