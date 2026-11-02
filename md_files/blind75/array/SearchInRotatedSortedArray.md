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
