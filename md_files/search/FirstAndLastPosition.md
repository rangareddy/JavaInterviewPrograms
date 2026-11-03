# Find First and Last Position of Element in Sorted Array

Given an array of integers `nums` sorted in non-decreasing order, find the starting and ending position of a given `target`. Return `[-1, -1]` if the target is not in the array. The algorithm must run in `O(log n)` time.

## Algorithm Strategy (Two Binary Searches (Lower Bound and Upper Bound))

1. The first position is the lower bound: the first index whose value is `>= target`.
2. The last position is one before the upper bound: the first index whose value is `> target`, minus 1.
3. Implement one helper `bound(nums, target, strict)` that finds the first index with `nums[i] > target` (strict) or `nums[i] >= target` (not strict).
4. If the lower bound is out of range or the value there is not the target, the target is absent: return `[-1, -1]`.

## Example walkthrough

- For `[5, 7, 7, 8, 8, 10]` and target 8: lower bound = 3, upper bound = 5, so the range is `[3, 4]`.
- For target 6 the lower bound points at 7 which is not 6, so the result is `[-1, -1]`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — Two binary searches.
- **Space Complexity:** `O(1)` — Iterative.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Linear Scan

Walk the array and remember the first and last index of the target.

```java
public static int[] searchRangeLinear(int[] nums, int target) {
    int first = -1, last = -1;
    for (int i = 0; i < nums.length; i++)
        if (nums[i] == target) { if (first < 0) first = i; last = i; }
    return new int[]{first, last};
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Binary Search, then Expand

Find any occurrence with binary search and walk outwards. Degrades to `O(n)` when the target repeats a lot.

```java
public static int[] searchRangeExpand(int[] nums, int target) {
    int index = Arrays.binarySearch(nums, target);
    if (index < 0) return new int[]{-1, -1};
    int left = index, right = index;
    while (left > 0 && nums[left - 1] == target) left--;
    while (right < nums.length - 1 && nums[right + 1] == target) right++;
    return new int[]{left, right};
}
```

- **Time Complexity:** O(log n + k)
- **Space Complexity:** O(1)

### Approach 3: Two Separate Binary Searches

One binary search biased to the left, one biased to the right.

```java
public static int[] searchRangeTwoSearches(int[] nums, int target) {
    return new int[]{find(nums, target, true), find(nums, target, false)};
}

private static int find(int[] nums, int target, boolean leftmost) {
    int low = 0, high = nums.length - 1, answer = -1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (nums[mid] == target) { answer = mid; if (leftmost) high = mid - 1; else low = mid + 1; }
        else if (nums[mid] < target) low = mid + 1;
        else high = mid - 1;
    }
    return answer;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Approach 4: Lower and Upper Bound (Optimized)

One helper returns the first index `>= target` (or `> target`); the range is `[lower, upper - 1]`. This is the version implemented in the repository.

```java
public static int[] searchRange(int[] nums, int target) {
    int first = bound(nums, target, false);
    if (first == nums.length || nums[first] != target) return new int[]{-1, -1};
    return new int[]{first, bound(nums, target, true) - 1};
}

private static int bound(int[] nums, int target, boolean strict) {
    int low = 0, high = nums.length;
    while (low < high) {
        int mid = low + (high - low) / 2;
        if (nums[mid] < target || (strict && nums[mid] == target)) low = mid + 1; else high = mid;
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
| 2 | Binary Search, then Expand | O(log n + k) | O(1) |
| 3 | Two Separate Binary Searches | O(log n) | O(1) |
| 4 | Lower and Upper Bound (Optimized) | O(log n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### FirstAndLastPosition.java

```java
import java.util.Arrays;

public class FirstAndLastPosition {

    public static void main(String[] args) {
        int[] nums = {5, 7, 7, 8, 8, 10};
        int[] targets = {8, 6, 5, 10, 11};
        for (int target : targets) {
            System.out.println("Input  : nums = " + Arrays.toString(nums) + ", target = " + target);
            System.out.println("Output : " + Arrays.toString(searchRange(nums, target)));
            System.out.println();
        }
    }

    public static int[] searchRange(int[] nums, int target) {
        int first = bound(nums, target, false);
        if (first == nums.length || nums[first] != target) {
            return new int[]{-1, -1};
        }
        return new int[]{first, bound(nums, target, true) - 1};
    }

    /** First index whose value is >= target, or > target when strict is true. */
    private static int bound(int[] nums, int target, boolean strict) {
        int low = 0;
        int high = nums.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < target || (strict && nums[mid] == target)) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
```
