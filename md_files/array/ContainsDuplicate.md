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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Compare every pair of elements.

```java
public static boolean containsDuplicateBrute(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] == nums[j]) return true;
        }
    }
    return false;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Sorting

After sorting, equal values are adjacent, so only neighbours need to be compared.

```java
public static boolean containsDuplicateSort(int[] nums) {
    int[] copy = nums.clone();
    Arrays.sort(copy);
    for (int i = 1; i < copy.length; i++) {
        if (copy[i] == copy[i - 1]) return true;
    }
    return false;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n) for the copy

### Approach 3: Streams

Count the distinct values and compare with the length. Short, but it always processes the whole array.

```java
public static boolean containsDuplicateStream(int[] nums) {
    return Arrays.stream(nums).distinct().count() != nums.length;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Hash Set (Optimized)

Stop at the first value that is already in the set. This is the version implemented in the repository.

```java
public static boolean containsDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int num : nums) {
        if (!seen.add(num)) return true;
    }
    return false;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Sorting | O(n log n) | O(n) for the copy |
| 3 | Streams | O(n) | O(n) |
| 4 | Hash Set (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

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
