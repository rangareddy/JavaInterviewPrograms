# First Missing Positive

Given an unsorted integer array `nums`, return the smallest positive integer that is not present in it. The algorithm must run in `O(n)` time and use `O(1)` auxiliary space.

## Algorithm Strategy (Cyclic Placement (Index Placement))

1. The answer is always in `[1, n + 1]`, because `n` numbers can cover at most `1..n`.
2. Try to place every value `v` in `[1, n]` at index `v - 1` by swapping it with the element that is currently there. Keep swapping at the same position until it holds a value that is out of range or is already in its place (this also handles duplicates).
3. Each swap puts at least one value in its final slot, so the total number of swaps is at most `n`.
4. Scan again: the first index `i` with `nums[i] != i + 1` means `i + 1` is missing. If every slot is right, the answer is `n + 1`.

## Example walkthrough

- For `[3, 4, -1, 1]`: swaps give `[1, -1, 3, 4]`.
- Index 1 holds `-1` instead of `2`, so the answer is `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each element is swapped into place at most once, then one scan.
- **Space Complexity:** `O(1)` — The array is rearranged in place.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: HashSet

Store every value, then count up from 1 until a number is missing.

```java
public static int firstMissingPositiveSet(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int x : nums) seen.add(x);
    int candidate = 1;
    while (seen.contains(candidate)) candidate++;
    return candidate;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Sorting

Sort a copy and walk through it, advancing the candidate whenever it is found.

```java
public static int firstMissingPositiveSort(int[] nums) {
    int[] copy = nums.clone();
    Arrays.sort(copy);
    int candidate = 1;
    for (int x : copy) {
        if (x == candidate) candidate++;
    }
    return candidate;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n) for the copy

### Approach 3: Cyclic Placement (Optimized)

Swap each value `v` in `[1, n]` into index `v - 1`, then the first misplaced index gives the answer. It meets the `O(1)` space requirement. This is the version implemented in the repository.

```java
public static int firstMissingPositive(int[] nums) {
    int n = nums.length;
    for (int i = 0; i < n; i++) {
        while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
            int target = nums[i] - 1;
            int temp = nums[target];
            nums[target] = nums[i];
            nums[i] = temp;
        }
    }
    for (int i = 0; i < n; i++) {
        if (nums[i] != i + 1) {
            return i + 1;
        }
    }
    return n + 1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | HashSet | O(n) | O(n) |
| 2 | Sorting | O(n log n) | O(n) for the copy |
| 3 | Cyclic Placement (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: the answer is always in `1..n+1`. So try to place every value `v` in `1..n` at index `v - 1` (cyclic sort), swapping it into place; values that are `<= 0`, `> n` or already duplicated in their slot are ignored. Afterwards the first index `i` where `nums[i] != i + 1` gives the answer `i + 1`; if all slots are right the answer is `n + 1`.

**Input:** `nums = [3, 4, -1, 1]` (`n = 4`).

| Step | `i` | Value at `i` | Action | Array afterwards |
|------|-----|--------------|--------|------------------|
| 1 | 0 | 3 | slot 2 holds -1, not 3, so swap `nums[0]` with `nums[2]` | [-1, 4, 3, 1] |
| 2 | 0 | -1 | not positive, the `while` stops | [-1, 4, 3, 1] |
| 3 | 1 | 4 | slot 3 holds 1, not 4, so swap | [-1, 1, 3, 4] |
| 4 | 1 | 1 | slot 0 holds -1, not 1, so swap | [1, -1, 3, 4] |
| 5 | 1 | -1 | not positive, the `while` stops | [1, -1, 3, 4] |
| 6 | 2 | 3 | slot 2 already holds 3, nothing to do | [1, -1, 3, 4] |
| 7 | 3 | 4 | slot 3 already holds 4, nothing to do | [1, -1, 3, 4] |

Scan: `nums[0] = 1` is right; `nums[1] = -1` is not `2`, so return `2`.

```text
index:      0    1    2    3
expected:   1    2    3    4
array:      1   -1    3    4
                 ^ first mismatch -> answer 2
```

**Edge cases.** `[1, 1]` stops swapping at the duplicate (`nums[nums[i]-1] != nums[i]` fails), so the scan returns `2`. `[1, 2, 0]` is fully placed except the `0`, so the answer is `n + 1 = 3`. `[7, 8, 9, 11, 12]` has nothing in range and returns `1`. Each swap puts one value in its final slot, so the total work is O(n). The class changes the input array.

**Result:** `2`, matching the class output.

## Source Code

### FirstMissingPositive.java

```java
import java.util.Arrays;

public class FirstMissingPositive {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 0}, {3, 4, -1, 1}, {7, 8, 9, 11, 12}, {1, 1}, {2, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + firstMissingPositive(nums.clone()));
            System.out.println();
        }
    }

    public static int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int target = nums[i] - 1;
                int temp = nums[target];
                nums[target] = nums[i];
                nums[i] = temp;
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return n + 1;
    }
}
```
