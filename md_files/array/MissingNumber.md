# Missing Number

Given an array `nums` containing `n` distinct numbers in the range `[0, n]`, return the only number in the range that is missing from the array.

## Algorithm Strategy (XOR of Indices and Values)

1. XOR is its own inverse: `a ^ a = 0` and `a ^ 0 = a`, and the order of the operands does not matter.
2. Start with `result = n`, the one index that the loop below never visits.
3. For every index `i`, XOR `result` with both `i` and `nums[i]`.
4. Every number that is present appears once as an index and once as a value, so the pair cancels. Only the missing number appears once (as an index), so it is what remains.

## Example walkthrough

- For `[3, 0, 1]` with `n = 3`: `3 ^ (0 ^ 3) ^ (1 ^ 0) ^ (2 ^ 1) = 2`.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over the array.
- **Space Complexity:** `O(1)` — A single integer.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sorting

Sort a copy and return the first index whose value differs from the index.

```java
public static int missingNumberSort(int[] nums) {
    int[] copy = nums.clone();
    Arrays.sort(copy);
    for (int i = 0; i < copy.length; i++) {
        if (copy[i] != i) return i;
    }
    return copy.length;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n) for the copy

### Approach 2: Sum Formula

The numbers `0..n` sum to `n * (n + 1) / 2`; subtract the actual sum.

```java
public static int missingNumberSum(int[] nums) {
    long expected = (long) nums.length * (nums.length + 1) / 2;
    long actual = 0;
    for (int x : nums) actual += x;
    return (int) (expected - actual);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: XOR (Optimized)

XOR every index and every value; the pairs cancel and the missing number is left. This is the version implemented in the repository.

```java
public static int missingNumber(int[] nums) {
    int result = nums.length;
    for (int i = 0; i < nums.length; i++) {
        result ^= i ^ nums[i];
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sorting | O(n log n) | O(n) for the copy |
| 2 | Sum Formula | O(n) | O(1) |
| 3 | XOR (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

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
        int result = nums.length;
        for (int i = 0; i < nums.length; i++) {
            result ^= i ^ nums[i];
        }
        return result;
    }
}
```
