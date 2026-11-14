# Move Zeroes

Given an integer array `nums`, move all `0`s to the end while keeping the relative order of the non-zero elements. Do it in place, without making a copy of the array.

## Algorithm Strategy (Fast and Slow Pointers (Stable Partition))

1. Keep a write pointer `slow` that marks where the next non-zero value belongs; everything before it is already the compacted non-zero prefix.
2. Scan with a read pointer `fast`. When `nums[fast]` is non-zero, swap it with `nums[slow]` and advance `slow`.
3. Swapping (instead of overwriting and then zero-filling) keeps the algorithm a single pass and makes zero extra writes when the array has no zeros in front.
4. The relative order of the non-zero values is preserved because they are moved forward in the order they are read.

## Example walkthrough

- For `[0, 1, 0, 3, 12]`: `fast = 1` swaps 1 into index 0, `fast = 3` swaps 3 into index 1, `fast = 4` swaps 12 into index 2.
- Result: `[1, 3, 12, 0, 0]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single pass.
- **Space Complexity:** `O(1)` — In-place swaps only.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Extra Array

Copy the non-zero values to a new array in order, then pad with zeros.

```java
public static int[] moveZeroesCopy(int[] nums) {
    int[] result = new int[nums.length];
    int index = 0;
    for (int x : nums) {
        if (x != 0) result[index++] = x;
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Overwrite, then Zero-Fill

Write the non-zero values forward, then fill the remaining tail with zeros.

```java
public static void moveZeroesFill(int[] nums) {
    int index = 0;
    for (int x : nums) {
        if (x != 0) nums[index++] = x;
    }
    while (index < nums.length) nums[index++] = 0;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Two Pointers with Swap (Optimized)

Swap each non-zero value into the next free slot so the zeros drift to the end in one pass. This is the version implemented in the repository.

```java
public static void moveZeroes(int[] nums) {
    int slow = 0;
    for (int fast = 0; fast < nums.length; fast++) {
        if (nums[fast] != 0) {
            int temp = nums[slow];
            nums[slow] = nums[fast];
            nums[fast] = temp;
            slow++;
        }
    }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Extra Array | O(n) | O(n) |
| 2 | Overwrite, then Zero-Fill | O(n) | O(1) |
| 3 | Two Pointers with Swap (Optimized) | O(n) | O(1) |

## Source Code

### MoveZeroes.java

```java
import java.util.Arrays;

public class MoveZeroes {

    public static void main(String[] args) {
        int[][] inputs = {{0, 1, 0, 3, 12}, {0}, {1, 2, 3}, {0, 0, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            moveZeroes(nums);
            System.out.println("Output : " + Arrays.toString(nums));
            System.out.println();
        }
    }

    public static void moveZeroes(int[] nums) {
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                int temp = nums[slow];
                nums[slow] = nums[fast];
                nums[fast] = temp;
                slow++;
            }
        }
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: two pointers. `fast` reads every element; `slow` marks the next position where a non-zero value belongs. Each time `fast` finds a non-zero, it is swapped with `slow`, and `slow` advances. Everything before `slow` is non-zero in original order; zeros are pushed behind it. One pass, in place.

**Input:** `nums = [0, 1, 0, 3, 12]`. Start: `slow = 0`.

| Step | `fast` | `nums[fast]` | Action | `slow` afterwards | Array afterwards |
|------|--------|--------------|--------|-------------------|------------------|
| 1 | 0 | 0 | zero, skip | 0 | [0, 1, 0, 3, 12] |
| 2 | 1 | 1 | swap `nums[0]` and `nums[1]` | 1 | [1, 0, 0, 3, 12] |
| 3 | 2 | 0 | zero, skip | 1 | [1, 0, 0, 3, 12] |
| 4 | 3 | 3 | swap `nums[1]` and `nums[3]` | 2 | [1, 3, 0, 0, 12] |
| 5 | 4 | 12 | swap `nums[2]` and `nums[4]` | 3 | [1, 3, 12, 0, 0] |

```text
slow:   0  1  1  2  3
fast:   0  1  2  3  4
array: [1, 3, 12, 0, 0]   non-zeros first (order kept), zeros last
```

**Edge cases.** With no zeros (`[1, 2, 3]`) `slow` equals `fast` every time, so each swap is an element with itself and the array is unchanged. `[0, 0, 1]` becomes `[1, 0, 0]`; `[0]` stays `[0]`. An empty array does nothing. The class does not check for `null`.

**Result:** `[1, 3, 12, 0, 0]`, matching the class output.
