# Find Peak Element

A peak element is strictly greater than its neighbours. Given an array `nums` where adjacent elements are different, return the index of any peak. Assume `nums[-1] = nums[n] = -infinity`. The solution must run in `O(log n)` time.

## Algorithm Strategy (Binary Search on the Slope)

1. Keep the search window `[low, high]`. Look at the middle element and its right neighbour.
2. If `nums[mid] < nums[mid + 1]` the array is going up, so a peak must exist to the right (at worst the last element, since the right border is minus infinity): move `low = mid + 1`.
3. Otherwise the array goes down after `mid`, so a peak exists at `mid` or to its left: move `high = mid`.
4. When `low == high` the window holds a peak; return that index.

## Example walkthrough

- For `[1, 2, 3, 1]`: `mid = 1`, 2 < 3 so go right; window `[2, 3]`, `mid = 2`, 3 > 1 so `high = 2`; the peak is index 2.
- For `[1, 2, 1, 3, 5, 6, 4]` the search ends at index 5 (value 6); index 1 (value 2) is also a valid peak.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — The window halves in every step.
- **Space Complexity:** `O(1)` — Iterative binary search.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Linear Scan

Return the first element that is larger than the next one (or the last index).

```java
public static int findPeakLinear(int[] nums) {
    for (int i = 0; i < nums.length - 1; i++) if (nums[i] > nums[i + 1]) return i;
    return nums.length - 1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Maximum Element

The global maximum is always a peak.

```java
public static int findPeakMax(int[] nums) {
    int best = 0;
    for (int i = 1; i < nums.length; i++) if (nums[i] > nums[best]) best = i;
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Recursive Binary Search

The same slope-based search written recursively.

```java
public static int findPeakRecursive(int[] nums) {
    return search(nums, 0, nums.length - 1);
}

private static int search(int[] nums, int low, int high) {
    if (low == high) return low;
    int mid = low + (high - low) / 2;
    return nums[mid] < nums[mid + 1] ? search(nums, mid + 1, high) : search(nums, low, mid);
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(log n)

### Approach 4: Iterative Binary Search (Optimized)

Follow the upward slope with a binary search window. This is the version implemented in the repository.

```java
public static int findPeakElement(int[] nums) {
    int low = 0, high = nums.length - 1;
    while (low < high) {
        int mid = low + (high - low) / 2;
        if (nums[mid] < nums[mid + 1]) low = mid + 1; else high = mid;
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
| 2 | Maximum Element | O(n) | O(1) |
| 3 | Recursive Binary Search | O(log n) | O(log n) |
| 4 | Iterative Binary Search (Optimized) | O(log n) | O(1) |

## Source Code

### FindPeakElement.java

```java
import java.util.Arrays;

public class FindPeakElement {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 1}, {1, 2, 1, 3, 5, 6, 4}, {1}, {3, 2, 1}, {1, 2, 3}};
        for (int[] nums : inputs) {
            int index = findPeakElement(nums);
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : index " + index + " (value " + nums[index] + ")");
            System.out.println();
        }
    }

    public static int findPeakElement(int[] nums) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < nums[mid + 1]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
```

## Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

The idea: a peak is an element larger than both neighbours (the ends count as having a smaller neighbour outside the array). Look at the slope at `mid`: if `nums[mid] < nums[mid + 1]` the array is climbing, so a peak must exist on the right (keep climbing until it turns down or hits the end), and `low = mid + 1`. Otherwise `nums[mid] > nums[mid + 1]` (values are distinct between neighbours), so `mid` or something on its left is a peak, and `high = mid`. The window shrinks until `low == high`, which is a peak. This is O(log n) and returns one valid peak, not necessarily the largest.

**Input:** `nums = [1, 2, 1, 3, 5, 6, 4]`.

| Step | low | high | mid | nums[mid] | nums[mid + 1] | Slope | Next |
|------|-----|------|-----|-----------|---------------|-------|------|
| 1 | 0 | 6 | 3 | 3 | 5 | rising | low = 4 |
| 2 | 4 | 6 | 5 | 6 | 4 | falling | high = 5 |
| 3 | 4 | 5 | 4 | 5 | 6 | rising | low = 5 |
| 4 | 5 | 5 | - | - | - | `low == high`, stop | return 5 |

```text
step 1: window 0..6  [1 2 1 (3) 5 6 4]   mid=3: 3 < 5 rising  -> go right
step 2: window 4..6  [5 (6) 4]           mid=5: 6 > 4 falling -> keep mid
step 3: window 4..5  [(5) 6]             mid=4: 5 < 6 rising  -> go right
done  : window 5..5  [6]                 peak at index 5
```

**Edge cases.** A single element returns index 0 with no comparison. A strictly decreasing array `[3, 2, 1]` keeps `high = mid` and returns 0; a strictly increasing one `[1, 2, 3]` keeps moving right and returns 2. `mid + 1` is always valid because `mid < high` inside the loop. An empty array is not handled: `high = -1`, the loop is skipped and the method returns `low = 0`, which is not a valid index.

**Result:** index `5` (value 6), matching the class output. Index 1 (value 2) is also a valid peak, which the search does not report.
