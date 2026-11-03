# Search Insert Position

Given a sorted array of distinct integers and a target value, return the index of the target if it is found. If not, return the index where it would be inserted to keep the array sorted. The algorithm must run in `O(log n)` time.

## Algorithm Strategy (Binary Search for the Lower Bound)

1. The answer is the first index whose element is greater than or equal to the target (the lower bound), or `n` if every element is smaller.
2. Search the half-open window `[low, high)` starting with `low = 0`, `high = n`.
3. If `nums[mid] < target` the answer lies to the right: `low = mid + 1`. Otherwise `mid` could be the answer: `high = mid`.
4. When the window is empty, `low` is the insertion position (and the index of the target when it exists).

## Example walkthrough

- For `[1, 3, 5, 6]` and target 5 the answer is index 2; for target 2 it is 1 (between 1 and 3); for target 7 it is 4 (after the last element).
- For target 0 the answer is 0 because every element is larger.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — The window halves in every step.
- **Space Complexity:** `O(1)` — Iterative.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Linear Scan

Return the first index whose element is not smaller than the target.

```java
public static int searchInsertLinear(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) if (nums[i] >= target) return i;
    return nums.length;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Library Binary Search

`Arrays.binarySearch` returns `-(insertion point) - 1` when the key is missing.

```java
public static int searchInsertLibrary(int[] nums, int target) {
    int index = Arrays.binarySearch(nums, target);
    return index >= 0 ? index : -index - 1;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Approach 3: Classic Binary Search with a Closed Window

Search `[low, high]` for the target and, when it is missing, `low` ends at the insertion point.

```java
public static int searchInsertClosed(int[] nums, int target) {
    int low = 0, high = nums.length - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (nums[mid] == target) return mid;
        if (nums[mid] < target) low = mid + 1; else high = mid - 1;
    }
    return low;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Approach 4: Lower Bound Binary Search (Optimized)

A half-open window finds the first element `>= target` directly. This is the version implemented in the repository.

```java
public static int searchInsert(int[] nums, int target) {
    int low = 0, high = nums.length;
    while (low < high) {
        int mid = low + (high - low) / 2;
        if (nums[mid] < target) low = mid + 1; else high = mid;
    }
    return low;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Linear Scan | O(n) | O(1) |
| 2 | Library Binary Search | O(log n) | O(1) |
| 3 | Classic Binary Search with a Closed Window | O(log n) | O(1) |
| 4 | Lower Bound Binary Search (Optimized) | O(log n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### SearchInsertPosition.java

```java
import java.util.Arrays;

public class SearchInsertPosition {

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 6};
        int[] targets = {5, 2, 7, 0, 6};
        for (int target : targets) {
            System.out.println("Input  : nums = " + Arrays.toString(nums) + ", target = " + target);
            System.out.println("Output : " + searchInsert(nums, target));
            System.out.println();
        }
    }

    public static int searchInsert(int[] nums, int target) {
        int low = 0;
        int high = nums.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
```
