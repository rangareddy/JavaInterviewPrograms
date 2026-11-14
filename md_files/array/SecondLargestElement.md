# Second Largest Element in an Array

Find the second largest distinct value of an integer array in a single pass, or report that it does not exist (fewer than two distinct values). The method returns a plain `Integer`, never an `Optional`, and is `null` when there is no answer.

## Algorithm Strategy (Single Pass Tracking the Two Largest Values)

1. Keep two variables, `largest` and `second`, both initialised to "nothing yet" (`Long.MIN_VALUE` is used so that `Integer.MIN_VALUE` remains a valid element).
2. For every element: if it is greater than `largest`, the old largest becomes `second` and the element becomes the new `largest`.
3. Otherwise, if it is greater than `second` and different from `largest`, it becomes the new `second`. Equal values are skipped so that duplicates are not counted twice.
4. If `second` was never set, there is no second largest value.

## Example walkthrough

- For `[12, 35, 1, 10, 34, 1]`: the pass ends with `largest = 35` and `second = 34`.
- For `[7, 7, 7]` there is only one distinct value, so the answer does not exist (`null`); the same holds for `[]`, `[3]` and a `null` array.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Two variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort and Scan from the End

Sort a copy and walk back to the first value different from the maximum.

```java
public static Integer secondLargestSort(int[] nums) {
    if (nums == null || nums.length < 2) return null;
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    for (int i = sorted.length - 2; i >= 0; i--)
        if (sorted[i] != sorted[sorted.length - 1]) return sorted[i];
    return null;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Two Passes

Find the maximum, then the maximum of the elements that are smaller than it.

```java
public static Integer secondLargestTwoPasses(int[] nums) {
    if (nums == null || nums.length < 2) return null;
    int max = Integer.MIN_VALUE;
    for (int n : nums) max = Math.max(max, n);
    boolean found = false;
    int second = Integer.MIN_VALUE;
    for (int n : nums) if (n < max && (!found || n > second)) { second = n; found = true; }
    return found ? Integer.valueOf(second) : null;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: TreeSet of Distinct Values

A sorted set keeps each value once; the second element from the top is the answer.

```java
public static Integer secondLargestTreeSet(int[] nums) {
    if (nums == null || nums.length < 2) return null;
    TreeSet<Integer> set = new TreeSet<>();
    for (int n : nums) set.add(n);
    if (set.size() < 2) return null;
    return set.lower(set.last());
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 4: Single Pass (Optimized)

Track the two largest values while scanning once. This is the version implemented in the repository.

```java
public static Integer secondLargest(int[] nums) {
    if (nums == null || nums.length < 2) return null;
    long largest = Long.MIN_VALUE, second = Long.MIN_VALUE;
    for (int num : nums) {
        if (num > largest) { second = largest; largest = num; }
        else if (num > second && num != largest) second = num;
    }
    return second == Long.MIN_VALUE ? null : Integer.valueOf((int) second);
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

**Step by step on the input `[12, 35, 1, 10, 34, 1]`.** First the safeguard: the array is not `null` and has at least two elements, so the scan runs. `largest` and `second` both start as `Long.MIN_VALUE` ("nothing yet").

| Step | Element | Comparison | `largest` | `second` |
|------|---------|------------|-----------|----------|
| start | | | none | none |
| 1 | `12` | greater than `largest` | `12` | none |
| 2 | `35` | greater than `largest`, so the old largest `12` becomes `second` | `35` | `12` |
| 3 | `1` | not greater than `largest`, not greater than `second` | `35` | `12` |
| 4 | `10` | not greater than `second` (`12`) | `35` | `12` |
| 5 | `34` | greater than `second` and different from `largest` | `35` | `34` |
| 6 | `1` | not greater than `second` | `35` | `34` |

`second` was set, so the method returns `34`. For `[7, 7, 7]` every element after the first equals `largest` and is skipped, `second` stays "nothing yet" and the method returns `null`; a `null` array, `[]` and `[3]` return `null` straight from the safeguard.

## Source Code

### SecondLargestElement.java

```java
import java.util.Arrays;

public class SecondLargestElement {

    public static void main(String[] args) {
        int[][] inputs = {{12, 35, 1, 10, 34, 1}, {10, 5, 10}, {7, 7, 7}, {3}, {-5, -1, -9}, {}, null};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            Integer second = secondLargest(nums);
            System.out.println("Output : " + (second != null ? String.valueOf(second) : "does not exist"));
            System.out.println();
        }
    }

    /** The second largest distinct value, or {@code null} when it does not exist (also for a null or too short array). */
    public static Integer secondLargest(int[] nums) {
        if (nums == null || nums.length < 2) {
            return null;
        }
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
        return second == Long.MIN_VALUE ? null : Integer.valueOf((int) second);
    }
}
```
