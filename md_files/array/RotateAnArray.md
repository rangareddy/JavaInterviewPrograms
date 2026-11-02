# Rotate an Array

Utility class demonstrating in-place array rotation using the Reversal Algorithm.

## Algorithm Strategy (3-Step Reversal)

1. Normalize `k`: `k = k % n`, and add `n` if it is negative, so that any (even negative or very large) `k` becomes a right rotation in `[0, n - 1]`. If `k` is 0 nothing needs to be done.
2. Reverse the entire array `[0, n - 1]`. The last `k` elements are now at the front, but in reverse order.
3. Reverse the first `k` elements `[0, k - 1]` to restore their original order.
4. Reverse the remaining `n - k` elements `[k, n - 1]` to restore their original order.

## Example walkthrough

- For `[1, 2, 3, 4, 5, 6, 7]` and `k = 3`:
- Reverse all: `[7, 6, 5, 4, 3, 2, 1]`. Reverse the first 3: `[5, 6, 7, 4, 3, 2, 1]`. Reverse the last 4: `[5, 6, 7, 1, 2, 3, 4]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each element is visited/swapped a constant number of times.
- **Space Complexity:** `O(1)` — Operates entirely in-place with zero additional memory allocation.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Rotate by One, k Times

Shift every element one position to the right (moving the last element to the front) and repeat `k` times.

```java
public static void rotateOneByOne(int[] arr, int k) {
    int n = arr.length;
    k = ((k % n) + n) % n;
    for (int step = 0; step < k; step++) {
        int last = arr[n - 1];
        for (int i = n - 1; i > 0; i--) arr[i] = arr[i - 1];
        arr[0] = last;
    }
}
```

- **Time Complexity:** O(n * k)
- **Space Complexity:** O(1)

### Approach 2: Extra Array

Place every element `arr[i]` directly at its destination index `(i + k) % n` in a new array, then copy the result back.

```java
public static void rotateExtraArray(int[] arr, int k) {
    int n = arr.length;
    k = ((k % n) + n) % n;
    int[] result = new int[n];
    for (int i = 0; i < n; i++) result[(i + k) % n] = arr[i];
    System.arraycopy(result, 0, arr, 0, n);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Cyclic Replacements (Juggling)

Move each element straight to its destination `(i + k) % n`, carrying the displaced element forward along the cycle. There are `gcd(n, k)` independent cycles, so every element is moved exactly once.

```java
public static void rotateCyclic(int[] arr, int k) {
    int n = arr.length;
    k = ((k % n) + n) % n;
    int count = 0;
    for (int start = 0; count < n; start++) {
        int current = start;
        int prev = arr[start];
        do {
            int next = (current + k) % n;
            int temp = arr[next];
            arr[next] = prev;
            prev = temp;
            current = next;
            count++;
        } while (start != current);
    }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: Three Reversals (Optimized)

Reverse the whole array, then reverse the first `k` and the last `n - k` elements. Simple, in place and cache friendly; implemented in the repository.

```java
public static void rotateAnArray(int[] arr, int k) {
    int n = arr.length;
    k = ((k % n) + n) % n;
    if (k == 0) return;
    reverse(arr, 0, n - 1);
    reverse(arr, 0, k - 1);
    reverse(arr, k, n - 1);
}

private static void reverse(int[] arr, int start, int end) {
    while (start < end) {
        int temp = arr[start];
        arr[start++] = arr[end];
        arr[end--] = temp;
    }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Rotate by One, k Times | O(n * k) | O(1) |
| 2 | Extra Array | O(n) | O(n) |
| 3 | Cyclic Replacements (Juggling) | O(n) | O(1) |
| 4 | Three Reversals (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### RotateAnArray.java

```java
import java.util.Arrays;

public class RotateAnArray {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        int k = -1;

        System.out.println("Original Array : " + Arrays.toString(arr));
        rotateAnArray(arr, k);
        System.out.println("Rotated Array  : " + Arrays.toString(arr));
    }

    /**
     * Rotates an array in-place to the right by {@code k} positions.
     *
     * <p>Handles edge cases seamlessly:
     * <ul>
     *   <li>{@code null} or single-element arrays</li>
     *   <li>{@code k} larger than array length (using modulo {@code k % n})</li>
     *   <li>Negative values of {@code k} (converting left shifts to equivalent right shifts)</li>
     * </ul>
     *
     * @param arr the integer array to rotate in-place
     * @param k   the number of positions to shift elements to the right
     */
    public static void rotateAnArray(int[] arr, int k) {
        // Guard clause: no operation needed for empty, single-element arrays, or null input
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;

        // Normalize k to fall within array bounds [0, n - 1]
        k = k % n;

        // Handle negative rotations (e.g., rotating -1 to right is equivalent to rotating n - 1)
        if (k < 0) {
            k += n;
        }

        // If normalized k is 0, no shift is required
        if (k == 0) {
            return;
        }

        // Step 1: Reverse the entire array
        reverse(arr, 0, n - 1);

        // Step 2: Reverse the first k elements (former suffix)
        reverse(arr, 0, k - 1);

        // Step 3: Reverse the remaining n - k elements (former prefix)
        reverse(arr, k, n - 1);
    }

    /**
     * Helper method to reverse elements in an array within a specified index range [start, end].
     *
     * @param arr   the array containing elements to swap
     * @param start starting index (inclusive)
     * @param end   ending index (inclusive)
     */
    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}
```
