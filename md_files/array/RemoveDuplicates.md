# Remove Duplicates from Sorted Array

Utility class to remove duplicates from a sorted array in-place.

## Algorithm Strategy (Two-Pointer Technique)

1. The input is sorted, so equal values are adjacent.
2. Use a slow pointer `i` that marks the last unique element written so far (initially index 0) and a fast pointer `j` that scans from index 1.
3. If `arr[j] != arr[i]`, a new unique value was found: advance `i` and copy `arr[j]` into `arr[i]`.
4. If they are equal, just move `j` on and skip the duplicate.
5. After the scan the first `i + 1` positions hold the unique values; return a trimmed copy using `Arrays.copyOf(arr, i + 1)`.

## Example walkthrough

- For `[1, 1, 2, 2, 3]`:
- `j = 2`: value 2 differs from 1, so `i = 1` and `arr[1] = 2`. `j = 4`: value 3 differs from 2, so `i = 2` and `arr[2] = 3`.
- Return the first 3 elements: `[1, 2, 3]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Single linear pass where `n` is the array length.
- **Space Complexity:** `O(k)` (where `k` is the number of unique elements) due to resizing the final output array using `Arrays.copyOf()`. Auxiliary working space is `O(1)`.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: LinkedHashSet / Streams

Insert all elements into a `LinkedHashSet` (keeps first-seen order and drops repeats) and copy it back. Works for unsorted input too. The stream form is `Arrays.stream(arr).distinct().toArray()`.

```java
public static int[] removeDuplicatesSet(int[] arr) {
    Set<Integer> set = new LinkedHashSet<>();
    for (int v : arr) set.add(v);
    int[] result = new int[set.size()];
    int i = 0;
    for (int v : set) result[i++] = v;
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Temporary Array

Because the input is sorted, copy an element into a temporary array only when it differs from the previous one, then trim the result.

```java
public static int[] removeDuplicatesTemp(int[] arr) {
    if (arr.length < 2) return arr;
    int[] temp = new int[arr.length];
    int j = 0;
    for (int i = 0; i < arr.length - 1; i++) {
        if (arr[i] != arr[i + 1]) temp[j++] = arr[i];
    }
    temp[j++] = arr[arr.length - 1];
    return Arrays.copyOf(temp, j);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Streams distinct

Let the stream API drop the repeated values: `Arrays.stream(arr).distinct().toArray()`. Concise; works for unsorted input too.

```java
public static int[] removeDuplicatesStream(int[] arr) {
    return Arrays.stream(arr).distinct().toArray();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Shift the Tail Left (Naive In-Place)

Whenever an element equals its predecessor, shift everything after it one position to the left and shrink the logical length. Needs no extra memory but is quadratic with many duplicates.

```java
public static int[] removeDuplicatesShift(int[] arr) {
    int[] work = arr.clone();
    int length = work.length;
    for (int i = 1; i < length; ) {
        if (work[i] == work[i - 1]) {
            for (int j = i; j < length - 1; j++) work[j] = work[j + 1];
            length--;
        } else {
            i++;
        }
    }
    return Arrays.copyOf(work, length);
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1) auxiliary

### Approach 5: Count the Unique Values, Then Copy

First pass: count how many values differ from their predecessor. Allocate a result of exactly that size and fill it in a second pass, so no trimming copy is needed.

```java
public static int[] removeDuplicatesCount(int[] arr) {
    if (arr.length == 0) return arr;
    int unique = 1;
    for (int i = 1; i < arr.length; i++) if (arr[i] != arr[i - 1]) unique++;
    int[] result = new int[unique];
    result[0] = arr[0];
    int index = 1;
    for (int i = 1; i < arr.length; i++) if (arr[i] != arr[i - 1]) result[index++] = arr[i];
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(k) for the result

### Approach 6: TreeSet

A sorted set stores each value once and iterates in ascending order. It also works when the input is not sorted, at the price of `O(n log n)`.

```java
public static int[] removeDuplicatesTreeSet(int[] arr) {
    TreeSet<Integer> set = new TreeSet<>();
    for (int value : arr) set.add(value);
    int[] result = new int[set.size()];
    int index = 0;
    for (int value : set) result[index++] = value;
    return result;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 7: Two Pointers, In Place (Optimized)

A slow pointer marks the last unique element and a fast pointer scans ahead; unique values are written forward over the duplicates. Only the final trim allocates, and the in-place part needs no extra memory. Implemented in the repository.

```java
public static int[] removeDuplicates(int[] arr) {
    if (arr == null || arr.length < 2) return arr;
    int i = 0;
    for (int j = 1; j < arr.length; j++) {
        if (arr[i] != arr[j]) {
            i++;
            arr[i] = arr[j];
        }
    }
    return Arrays.copyOf(arr, i + 1);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1) auxiliary

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | LinkedHashSet / Streams | O(n) | O(n) |
| 2 | Temporary Array | O(n) | O(n) |
| 3 | Streams distinct | O(n) | O(n) |
| 4 | Shift the Tail Left (Naive In-Place) | O(n^2) | O(1) auxiliary |
| 5 | Count the Unique Values, Then Copy | O(n) | O(k) for the result |
| 6 | TreeSet | O(n log n) | O(n) |
| 7 | Two Pointers, In Place (Optimized) | O(n) | O(1) auxiliary |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### RemoveDuplicates.java

```java
import java.util.Arrays;

public class RemoveDuplicates {

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 3, 3, 3, 4};

        System.out.println("Original Array : " + Arrays.toString(arr));
        int[] result = removeDuplicates(arr);
        System.out.println("Unique Array   : " + Arrays.toString(result));
    }

    /**
     * Removes duplicates from a sorted integer array and returns a trimmed copy
     * containing only unique elements.
     *
     * @param arr the sorted input integer array
     * @return a new array containing only the unique elements in their original relative order
     */
    public static int[] removeDuplicates(int[] arr) {
        // Guard Clause: null or single-element arrays have no duplicates to remove
        if (arr == null || arr.length < 2) {
            return arr;
        }

        int i = 0; // Points to the last known unique element

        for (int j = 1; j < arr.length; j++) {
            // When a new unique element is found, advance i and update its position
            if (arr[i] != arr[j]) {
                i++;
                arr[i] = arr[j];
            }
        }

        // Return a trimmed copy of the array up to the new unique boundary (index i)
        return Arrays.copyOf(arr, i + 1);
    }
}
```
