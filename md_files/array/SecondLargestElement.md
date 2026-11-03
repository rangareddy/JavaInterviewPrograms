# Second Largest Element in an Array

Find the second largest distinct value of an integer array in a single pass, or report that it does not exist (fewer than two distinct values).

## Algorithm Strategy (Single Pass Tracking the Two Largest Values)

1. Keep two variables, `largest` and `second`, both initialised to "nothing yet" (`Long.MIN_VALUE` is used so that `Integer.MIN_VALUE` remains a valid element).
2. For every element: if it is greater than `largest`, the old largest becomes `second` and the element becomes the new `largest`.
3. Otherwise, if it is greater than `second` and different from `largest`, it becomes the new `second`. Equal values are skipped so that duplicates are not counted twice.
4. If `second` was never set, there is no second largest value.

## Example walkthrough

- For `[12, 35, 1, 10, 34, 1]`: the pass ends with `largest = 35` and `second = 34`.
- For `[7, 7, 7]` there is only one distinct value, so the answer does not exist.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Two variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort and Scan from the End

Sort a copy and walk back to the first value different from the maximum.

```java
public static OptionalInt secondLargestSort(int[] nums) {
    if (nums.length == 0) return OptionalInt.empty();
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    for (int i = sorted.length - 2; i >= 0; i--)
        if (sorted[i] != sorted[sorted.length - 1]) return OptionalInt.of(sorted[i]);
    return OptionalInt.empty();
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Two Passes

Find the maximum, then the maximum of the elements that are smaller than it.

```java
public static OptionalInt secondLargestTwoPasses(int[] nums) {
    if (nums.length == 0) return OptionalInt.empty();
    int max = Integer.MIN_VALUE;
    for (int n : nums) max = Math.max(max, n);
    boolean found = false;
    int second = Integer.MIN_VALUE;
    for (int n : nums) if (n < max && (!found || n > second)) { second = n; found = true; }
    return found ? OptionalInt.of(second) : OptionalInt.empty();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: TreeSet of Distinct Values

A sorted set keeps each value once; the second element from the top is the answer.

```java
public static OptionalInt secondLargestTreeSet(int[] nums) {
    TreeSet<Integer> set = new TreeSet<>();
    for (int n : nums) set.add(n);
    if (set.size() < 2) return OptionalInt.empty();
    return OptionalInt.of(set.lower(set.last()));
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 4: Single Pass (Optimized)

Track the two largest values while scanning once. This is the version implemented in the repository.

```java
public static OptionalInt secondLargest(int[] nums) {
    long largest = Long.MIN_VALUE, second = Long.MIN_VALUE;
    for (int num : nums) {
        if (num > largest) { second = largest; largest = num; }
        else if (num > second && num != largest) second = num;
    }
    return second == Long.MIN_VALUE ? OptionalInt.empty() : OptionalInt.of((int) second);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort and Scan from the End | O(n log n) | O(n) |
| 2 | Two Passes | O(n) | O(1) |
| 3 | TreeSet of Distinct Values | O(n log n) | O(n) |
| 4 | Single Pass (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### SecondLargestElement.java

```java
import java.util.Arrays;
import java.util.OptionalInt;

public class SecondLargestElement {

    public static void main(String[] args) {
        int[][] inputs = {{12, 35, 1, 10, 34, 1}, {10, 5, 10}, {7, 7, 7}, {3}, {-5, -1, -9}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            OptionalInt second = secondLargest(nums);
            System.out.println("Output : " + (second.isPresent() ? String.valueOf(second.getAsInt()) : "does not exist"));
            System.out.println();
        }
    }

    public static OptionalInt secondLargest(int[] nums) {
        long largest = Long.MIN_VALUE;
        long second = Long.MIN_VALUE;
        for (int num : nums) {
            if (num > largest) {
                second = largest;
                largest = num;
            } else if (num > second && num != largest) {
                second = num;
            }
        }
        return second == Long.MIN_VALUE ? OptionalInt.empty() : OptionalInt.of((int) second);
    }
}
```
