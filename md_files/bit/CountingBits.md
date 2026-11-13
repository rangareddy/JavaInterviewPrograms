# Counting Bits

Given an integer `n`, return an array `ans` of length `n + 1` where `ans[i]` is the number of `1` bits in the binary representation of `i`, for every `i` in `[0, n]`.

## Algorithm Strategy (DP on the Number with Its Last Bit Removed)

1. The binary form of `i` is the binary form of `i >> 1` followed by one more bit, `i & 1`.
2. So `ans[i] = ans[i >> 1] + (i & 1)`: the count for `i` reuses the count already computed for the smaller number `i >> 1`.
3. Fill the array from 1 up to `n` (`ans[0] = 0`). Each entry costs one shift, one mask and one addition, so the whole array takes linear time instead of counting bits for every number from scratch.

## Example walkthrough

- For `n = 5`: `ans[1] = ans[0] + 1 = 1`, `ans[2] = ans[1] + 0 = 1`, `ans[3] = ans[1] + 1 = 2`, `ans[4] = ans[2] + 0 = 1`, `ans[5] = ans[2] + 1 = 2`.
- Result: `[0, 1, 1, 2, 1, 2]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Constant work per number.
- **Space Complexity:** `O(1)` — Apart from the output array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Count Each Number Separately

Shift through all 32 bits of each number, or use Brian Kernighan's trick `x &= x - 1` to clear the lowest set bit until zero.

```java
public static int[] countBitsBrute(int n) {
    int[] ans = new int[n + 1];
    for (int i = 0; i <= n; i++) {
        int x = i, count = 0;
        while (x != 0) {
            x &= x - 1;
            count++;
        }
        ans[i] = count;
    }
    return ans;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1)

### Approach 2: Highest Power of Two

`ans[i] = ans[i - highest] + 1`, where `highest` is the largest power of two not above `i`.

```java
public static int[] countBitsHighest(int n) {
    int[] ans = new int[n + 1];
    int highest = 1;
    for (int i = 1; i <= n; i++) {
        if (highest * 2 == i) highest *= 2;
        ans[i] = ans[i - highest] + 1;
    }
    return ans;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: DP with a Right Shift (Optimized)

Reuse the count of `i >> 1` and add the last bit. This is the version implemented in the repository.

```java
public static int[] countBits(int n) {
    int[] ans = new int[n + 1];
    for (int i = 1; i <= n; i++) {
        ans[i] = ans[i >> 1] + (i & 1);
    }
    return ans;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1) besides the output

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Count Each Number Separately | O(n log n) | O(1) |
| 2 | Highest Power of Two | O(n) | O(1) |
| 3 | DP with a Right Shift (Optimized) | O(n) | O(1) besides the output |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### CountingBits.java

```java
import java.util.Arrays;

public class CountingBits {

    public static void main(String[] args) {
        int[] inputs = {2, 5, 0, 8};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + Arrays.toString(countBits(n)));
            System.out.println();
        }
    }

    public static int[] countBits(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            ans[i] = ans[i >> 1] + (i & 1);
        }
        return ans;
    }
}
```
