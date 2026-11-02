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
