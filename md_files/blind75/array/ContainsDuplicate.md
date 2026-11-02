# Contains Duplicate

Given an integer array `nums`, return `true` if any value appears at least twice, and `false` if every element is distinct.

## Algorithm Strategy (Hash Set of Seen Values)

1. Create an empty `HashSet` that stores the values seen so far.
2. For each number, try to add it to the set. `Set.add` returns `false` when the value is already present.
3. If the add fails, a duplicate exists, so return `true` immediately.
4. If the loop ends without a failed add, all values are distinct, so return `false`.

## Example walkthrough

- For `[1, 2, 3, 1]`: 1, 2 and 3 are added; the second 1 cannot be added, so the result is `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each element is inserted into the set at most once (`O(1)` average).
- **Space Complexity:** `O(n)` — The set may hold every element.

## Source Code

### ContainsDuplicate.java

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ContainsDuplicate {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 1}, {1, 2, 3, 4}, {1, 1, 1, 3, 3, 4, 3, 2, 4, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + containsDuplicate(nums));
            System.out.println();
        }
    }

    public static boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            if (!seen.add(num)) {
                return true;
            }
        }
        return false;
    }
}
```
