# Find All Numbers Disappeared in an Array

Given an array `nums` of `n` integers where `nums[i]` is in the range `[1, n]`, return all the integers in the range `[1, n]` that do not appear in `nums`.

## Algorithm Strategy (In-Place Marking by Sign)

1. Every value `v` is a valid index `v - 1`, so the array itself can record which values have been seen.
2. For each element, take its absolute value `v` and make `nums[v - 1]` negative (if it is not already negative).
3. After the pass, an index `i` that still holds a positive number means the value `i + 1` never appeared.
4. Collect `i + 1` for every positive position. The input array is modified; restore the signs if the caller needs it back.

## Example walkthrough

- For `[4, 3, 2, 7, 8, 2, 3, 1]`: marking turns it into `[-4, -3, -2, -7, 8, 2, -3, -1]`.
- Indices 4 and 5 are still positive, so the missing numbers are `5` and `6`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Two linear passes.
- **Space Complexity:** `O(1)` — Not counting the output list; the marking is done in place.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: HashSet

Put every value in a set, then list the numbers from 1 to n that are not in it.

```java
public static List<Integer> findDisappearedNumbersSet(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int x : nums) seen.add(x);
    List<Integer> missing = new ArrayList<>();
    for (int v = 1; v <= nums.length; v++) {
        if (!seen.contains(v)) missing.add(v);
    }
    return missing;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: In-Place Sign Marking (Optimized)

Use the sign of `nums[v - 1]` as the "seen" flag for value `v`, so no extra set is needed. This is the version implemented in the repository.

```java
public static List<Integer> findDisappearedNumbers(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
        int index = Math.abs(nums[i]) - 1;
        if (nums[index] > 0) {
            nums[index] = -nums[index];
        }
    }
    List<Integer> missing = new ArrayList<>();
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] > 0) {
            missing.add(i + 1);
        }
    }
    return missing;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1) besides the output

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | HashSet | O(n) | O(n) |
| 2 | In-Place Sign Marking (Optimized) | O(n) | O(1) besides the output |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### FindAllNumbersDisappeared.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllNumbersDisappeared {

    public static void main(String[] args) {
        int[][] inputs = {{4, 3, 2, 7, 8, 2, 3, 1}, {1, 1}, {1, 2, 3}, {2, 2, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + findDisappearedNumbers(nums.clone()));
            System.out.println();
        }
    }

    public static List<Integer> findDisappearedNumbers(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1;
            if (nums[index] > 0) {
                nums[index] = -nums[index];
            }
        }
        List<Integer> missing = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                missing.add(i + 1);
            }
        }
        return missing;
    }
}
```
