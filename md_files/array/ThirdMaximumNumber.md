# Third Maximum Number

Given an integer array `nums`, return the third distinct maximum number in the array. If the third maximum does not exist, return the maximum number.

## Algorithm Strategy (Three Variables for Distinct Maximums)

1. Keep `first`, `second` and `third` as `Long` values (or `null`) so that `Integer.MIN_VALUE` in the input is not confused with 'not set'.
2. For each number, skip it if it equals one of the three tracked values.
3. Insert it into the right slot and shift the lower ones down.
4. At the end, return `third` if it was set, otherwise `first`.

## Example walkthrough

- For `[2, 2, 3, 1]` the distinct values in order are 3, 2, 1, so the third maximum is 1.
- Result: `1`. For `[1, 2]` there is no third value, so the answer is the maximum `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Three variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: TreeSet of Distinct Values

Put every value in a sorted set and read the third largest.

```java
public static int treeSet(int[] a) { java.util.TreeSet<Integer> s = new java.util.TreeSet<>(); for (int v : a) s.add(v); if (s.size() < 3) return s.last(); s.pollLast(); s.pollLast(); return s.last(); }
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Three Tracked Values (Optimized)

Keep the top three distinct values in constant space. This is the version implemented in the repository.

```java
public static int three(int[] a) {
    Long f = null, s = null, t = null; for (int v : a) { long n = v; if ((f != null && n == f) || (s != null && n == s) || (t != null && n == t)) continue; if (f == null || n > f) { t = s; s = f; f = n; } else if (s == null || n > s) { t = s; s = n; } else if (t == null || n > t) t = n; }
    return (int) (long) (t != null ? t : f);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | TreeSet of Distinct Values | O(n log n) | O(n) |
| 2 | Three Tracked Values (Optimized) | O(n) | O(1) |

## Source Code

### ThirdMaximumNumber.java

```java
import java.util.Arrays;

public class ThirdMaximumNumber {

    public static void main(String[] args) {
        int[][] inputs = {{2, 2, 3, 1}, {3, 2, 1}, {1, 2}, {1, 1, 2}, {Integer.MIN_VALUE, 1, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + thirdMax(nums));
            System.out.println();
        }
    }

    public static int thirdMax(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Long first = null;
        Long second = null;
        Long third = null;
        for (int value : nums) {
            long num = value;
            if ((first != null && num == first) || (second != null && num == second) || (third != null && num == third)) {
                continue;
            }
            if (first == null || num > first) {
                third = second;
                second = first;
                first = num;
            } else if (second == null || num > second) {
                third = second;
                second = num;
            } else if (third == null || num > third) {
                third = num;
            }
        }
        return (int) (long) (third != null ? third : first);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: only the three largest distinct values can matter, so three slots suffice. Using boxed `Long` values lets the code tell "no value yet" apart from a real `Integer.MIN_VALUE` in the input.

**Input:** `nums = [2, 2, 3, 1]`.

| Number | `first` | `second` | `third` |
|--------|---------|----------|---------|
| 2 | 2 | | |
| 2 | duplicate, skipped | | |
| 3 | 3 | 2 | |
| 1 | 3 | 2 | 1 |

**Output:** `1`.
