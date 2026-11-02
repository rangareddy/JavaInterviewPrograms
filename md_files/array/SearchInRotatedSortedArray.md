# Search in Rotated Sorted Array

Given a sorted array of unique values that was rotated at an unknown pivot and a `target`, return the index of the target, or `-1` if it is absent, in `O(log n)` time.

## Algorithm Strategy (Binary Search on the Sorted Half)

1. Run a normal binary search with `low`, `high` and `mid`; return `mid` when `nums[mid] == target`.
2. At least one half around `mid` is always sorted. If `nums[low] <= nums[mid]` the left half `[low, mid]` is sorted, otherwise the right half is sorted.
3. Check whether the target lies inside the sorted half using its endpoints. If so, continue in that half; otherwise continue in the other half.
4. If the range becomes empty, return `-1`.

## Example walkthrough

- For `[4, 5, 6, 7, 0, 1, 2]` and target 0: mid = 3 (7), left half `[4..7]` is sorted but does not contain 0, so low = 4.
- mid = 5 (1): the left half `[0, 1]` is sorted and contains 0, so high = 4; then `nums[4] = 0`, result `4`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — The search range halves each step.
- **Space Complexity:** `O(1)` — Index variables only.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Linear Scan

Check every element. Correct but ignores the sorted structure.

```java
public static int searchLinear(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] == target) return i;
    }
    return -1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Find the Pivot, Then Binary Search

First binary-search the index of the minimum (the rotation point). Then run an ordinary binary search on the half that can contain the target.

```java
public static int searchTwoPass(int[] nums, int target) {
    int n = nums.length, low = 0, high = n - 1;
    while (low < high) {                           // find the index of the minimum
        int mid = low + (high - low) / 2;
        if (nums[mid] > nums[high]) low = mid + 1; else high = mid;
    }
    int pivot = low;
    low = 0; high = n - 1;
    if (target >= nums[pivot] && target <= nums[n - 1]) low = pivot; else high = pivot - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (nums[mid] == target) return mid;
        if (nums[mid] < target) low = mid + 1; else high = mid - 1;
    }
    return -1;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Approach 3: One-Pass Binary Search (Optimized)

At every step one half is sorted; test whether the target lies inside it to decide which half to keep. This is the version implemented in the repository.

```java
public static int search(int[] nums, int target) {
    int low = 0, high = nums.length - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (nums[mid] == target) return mid;
        if (nums[low] <= nums[mid]) {
            if (nums[low] <= target && target < nums[mid]) high = mid - 1; else low = mid + 1;
        } else {
            if (nums[mid] < target && target <= nums[high]) low = mid + 1; else high = mid - 1;
        }
    }
    return -1;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Linear Scan | O(n) | O(1) |
| 2 | Find the Pivot, Then Binary Search | O(log n) | O(1) |
| 3 | One-Pass Binary Search (Optimized) | O(log n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### SearchInRotatedSortedArray.java

```java
import java.util.Arrays;

public class SearchInRotatedSortedArray {

    public static void main(String[] args) {
        int[][] inputs = {{4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2}, {1}};
        int[] targets = {0, 3, 1};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + search(inputs[i], targets[i]));
            System.out.println();
        }
    }

    public static int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[low] <= nums[mid]) {                       // left half is sorted
                if (nums[low] <= target && target < nums[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {                                            // right half is sorted
                if (nums[mid] < target && target <= nums[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }
}
```
