# Find Minimum in Rotated Sorted Array

A sorted array of unique elements was rotated an unknown number of times. Return its minimum element in `O(log n)` time.

## Algorithm Strategy (Binary Search on the Rotation Point)

1. Keep `low = 0` and `high = n - 1`. The minimum is the only element that is smaller than its predecessor (the rotation point).
2. While `low < high`, compute `mid`. If `nums[mid] > nums[high]`, the rotation point is to the right of `mid`, so set `low = mid + 1`.
3. Otherwise the minimum is at `mid` or to its left, so set `high = mid` (not `mid - 1`, because `mid` itself may be the minimum).
4. When `low == high`, that index holds the minimum.

## Example walkthrough

- For `[3, 4, 5, 1, 2]`: mid = 2 (5 > 2) so low = 3; mid = 3 (1 <= 2) so high = 3.
- Result: `nums[3] = 1`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — The range is halved each step.
- **Space Complexity:** `O(1)` — Index variables only.

## Source Code

### FindMinimumInRotatedSortedArray.java

```java
import java.util.Arrays;

public class FindMinimumInRotatedSortedArray {

    public static void main(String[] args) {
        int[][] inputs = {{3, 4, 5, 1, 2}, {4, 5, 6, 7, 0, 1, 2}, {11, 13, 15, 17}, {2, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + findMin(nums));
            System.out.println();
        }
    }

    public static int findMin(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > nums[high]) {
                low = mid + 1;      // minimum is in the right half
            } else {
                high = mid;         // minimum is at mid or in the left half
            }
        }
        return nums[low];
    }
}
```
