# Missing Number

Given an array `nums` containing `n` distinct numbers in the range `[0, n]`, return the only number in the range that is missing from the array.

## Algorithm Strategy (XOR of Indices and Values)

1. XOR of a number with itself is 0 and XOR is commutative and associative, so equal numbers cancel out in any order.
2. Start with `missing = n` (the index that the array does not have).
3. For every index `i`, XOR `missing` with both `i` and `nums[i]`.
4. Every number that is present appears once as an index and once as a value and cancels out; only the missing number is left. (Alternative: `n(n+1)/2` minus the array sum.)

## Example walkthrough

- For `[3, 0, 1]` (`n = 3`): `3 ^ (0^3) ^ (1^0) ^ (2^1) = 2`.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single pass.
- **Space Complexity:** `O(1)` — One variable.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sorting

Sort the array; the first index whose value differs from the index is the missing number (or `n` if none).

```java
public static int missingNumberSort(int[] nums) {
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    for (int i = 0; i < sorted.length; i++) {
        if (sorted[i] != i) return i;
    }
    return sorted.length;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Hash Set

Put the numbers in a set and return the first number of the range `0..n` that is not in it.

```java
public static int missingNumberSet(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int num : nums) set.add(num);
    for (int i = 0; i <= nums.length; i++) {
        if (!set.contains(i)) return i;
    }
    return -1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Gauss Sum Formula

The numbers `0..n` add up to `n * (n + 1) / 2`; subtract the sum of the array. Use `long` (or subtract inside the loop) to avoid overflow for large `n`.

```java
public static int missingNumberSum(int[] nums) {
    long n = nums.length;
    long expected = n * (n + 1) / 2;
    for (int num : nums) expected -= num;
    return (int) expected;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: XOR (Optimized)

XOR the numbers `0..n` with the array values: every number that is present cancels out. No overflow concerns. This is the version implemented in the repository.

```java
public static int missingNumber(int[] nums) {
    int missing = nums.length;
    for (int i = 0; i < nums.length; i++) missing ^= i ^ nums[i];
    return missing;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sorting | O(n log n) | O(n) |
| 2 | Hash Set | O(n) | O(n) |
| 3 | Gauss Sum Formula | O(n) | O(1) |
| 4 | XOR (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### MissingNumber.java

```java
import java.util.Arrays;

public class MissingNumber {

    public static void main(String[] args) {
        int[][] inputs = {{3, 0, 1}, {0, 1}, {9, 6, 4, 2, 3, 5, 7, 0, 1}, {0}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + missingNumber(nums));
            System.out.println();
        }
    }

    public static int missingNumber(int[] nums) {
        int missing = nums.length;
        for (int i = 0; i < nums.length; i++) {
            missing ^= i ^ nums[i];
        }
        return missing;
    }
}
```
