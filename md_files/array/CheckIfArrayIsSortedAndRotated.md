# Check if Array Is Sorted and Rotated

Given an array `nums`, return `true` if it was originally sorted in non-decreasing order and then rotated by some number of positions (possibly zero), and `false` otherwise. The array may contain duplicates.

## Algorithm Strategy (Count the Descents Circularly)

1. In a sorted array that has been rotated, there is at most one place where a value is greater than the next one.
2. Walk the array and count the indexes `i` where `nums[i] > nums[(i + 1) % n]`, including the wrap-around from the last element back to the first.
3. If the count is at most 1, the array is a rotation of a sorted array.
4. Otherwise it is not.

## Example walkthrough

- For `[3, 4, 5, 1, 2]` the only descent is `5 > 1`; the wrap-around `2 <= 3` is fine.
- Result: `true`. For `[2, 1, 3, 4]` there are two descents (`2 > 1` and `4 > 2`), so the result is `false`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — A counter.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Rotation

Rotate by every amount and check whether the result is sorted.

```java
public static boolean tryRotations(int[] a) {
    int n = a.length; if (n <= 1) return true;
    for (int s = 0; s < n; s++) { boolean ok = true; for (int i = 0; i + 1 < n; i++) if (a[(s + i) % n] > a[(s + i + 1) % n]) { ok = false; break; } if (ok) return true; }
    return false;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Count Descents (Optimized)

At most one descent in the circular array. This is the version implemented in the repository.

```java
public static boolean descents(int[] a) {
    if (a.length <= 1) return true; int d = 0; for (int i = 0; i < a.length; i++) if (a[i] > a[(i + 1) % a.length]) d++; return d <= 1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Rotation | O(n^2) | O(1) |
| 2 | Count Descents (Optimized) | O(n) | O(1) |

## Source Code

### CheckIfArrayIsSortedAndRotated.java

```java
import java.util.Arrays;

public class CheckIfArrayIsSortedAndRotated {

    public static void main(String[] args) {
        int[][] inputs = {{3, 4, 5, 1, 2}, {2, 1, 3, 4}, {1, 2, 3}, {1, 1, 1}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + check(nums));
            System.out.println();
        }
    }

    public static boolean check(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return true;
        }
        int descents = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > nums[(i + 1) % nums.length]) {
                descents++;
            }
        }
        return descents <= 1;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a sorted array read in a circle has no descent at all (when all values are equal) or exactly one, at the point where the largest value meets the smallest. Rotation only moves where that point sits. Treating the array as circular means the wrap-around from the last element to the first is checked too.

**Input:** `nums = [3, 4, 5, 1, 2]`.

| Pair | Descent? |
|------|----------|
| 3, 4 | no |
| 4, 5 | no |
| 5, 1 | yes |
| 1, 2 | no |
| 2, 3 (wrap-around) | no |

One descent.

**Output:** `true`.
