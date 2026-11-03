# Counting Bits

Given an integer `n`, return an array `ans` of length `n + 1` where `ans[i]` is the number of 1's in the binary representation of `i`.

## Algorithm Strategy (Dynamic Programming on the Shifted Number)

1. `ans[0] = 0`.
2. Shifting a number right by one (`i >> 1`) removes its lowest bit, so `i` has the same number of 1s as `i / 2`, plus one if the lowest bit of `i` is 1.
3. Therefore `ans[i] = ans[i >> 1] + (i & 1)`, and `i >> 1` is always smaller than `i`, so it has already been computed.
4. Fill the array for `i = 1 .. n`.

## Example walkthrough

- For `n = 5`: `ans[1] = ans[0] + 1 = 1`, `ans[2] = ans[1] + 0 = 1`, `ans[3] = ans[1] + 1 = 2`, `ans[4] = ans[2] + 0 = 1`, `ans[5] = ans[2] + 1 = 2`.
- Result: `[0, 1, 1, 2, 1, 2]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One constant-time step per number (no per-number bit loop).
- **Space Complexity:** `O(n)` — The output array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Count the Bits of Every Number

Compute the number of set bits of each `i` independently with Kernighan's trick.

```java
public static int[] countBitsBrute(int n) {
    int[] ans = new int[n + 1];
    for (int i = 0; i <= n; i++) {
        int count = 0;
        for (int x = i; x != 0; x &= x - 1) count++;
        ans[i] = count;
    }
    return ans;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: DP on the Lowest Set Bit

`i & (i - 1)` is `i` without its lowest set bit, so `ans[i] = ans[i & (i - 1)] + 1`.

```java
public static int[] countBitsLowestBit(int n) {
    int[] ans = new int[n + 1];
    for (int i = 1; i <= n; i++) ans[i] = ans[i & (i - 1)] + 1;
    return ans;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Integer.bitCount for Every Number

Call the JDK intrinsic for each number. Simple and fast per call, but it does not reuse earlier results.

```java
public static int[] countBitsLibrary(int n) {
    int[] ans = new int[n + 1];
    for (int i = 0; i <= n; i++) ans[i] = Integer.bitCount(i);
    return ans;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Offset by the Highest Power of Two

Numbers `offset .. 2 * offset - 1` are the numbers `0 .. offset - 1` with one extra leading 1 bit, where `offset` is the highest power of two not above `i`. So `ans[i] = ans[i - offset] + 1`.

```java
public static int[] countBitsOffset(int n) {
    int[] ans = new int[n + 1];
    int offset = 1;
    for (int i = 1; i <= n; i++) {
        if (offset * 2 == i) offset *= 2;
        ans[i] = ans[i - offset] + 1;
    }
    return ans;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 5: Nibble Lookup Table

Precompute the bit counts of the 16 values of a 4-bit nibble and add up the table entries of the eight nibbles of every number.

```java
public static int[] countBitsNibble(int n) {
    int[] table = {0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4};
    int[] ans = new int[n + 1];
    for (int i = 0; i <= n; i++) {
        int count = 0;
        for (int x = i; x != 0; x >>>= 4) count += table[x & 15];
        ans[i] = count;
    }
    return ans;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 6: Memoized Recursion

`bits(x) = bits(x >> 1) + (x & 1)` written as a recursive function with a cache, the top-down version of the DP.

```java
public static int[] countBitsMemo(int n) {
    int[] memo = new int[n + 1];
    Arrays.fill(memo, -1);
    int[] ans = new int[n + 1];
    for (int i = 0; i <= n; i++) ans[i] = bits(i, memo);
    return ans;
}

private static int bits(int x, int[] memo) {
    if (x == 0) return 0;
    if (memo[x] != -1) return memo[x];
    return memo[x] = bits(x >> 1, memo) + (x & 1);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 7: DP on the Shifted Number (Optimized)

`i` has the same bits as `i >> 1` plus its lowest bit, so `ans[i] = ans[i >> 1] + (i & 1)`. This is the version implemented in the repository.

```java
public static int[] countBits(int n) {
    int[] ans = new int[n + 1];
    for (int i = 1; i <= n; i++) ans[i] = ans[i >> 1] + (i & 1);
    return ans;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Count the Bits of Every Number | O(n log n) | O(n) |
| 2 | DP on the Lowest Set Bit | O(n) | O(n) |
| 3 | Integer.bitCount for Every Number | O(n) | O(n) |
| 4 | Offset by the Highest Power of Two | O(n) | O(n) |
| 5 | Nibble Lookup Table | O(n) | O(n) |
| 6 | Memoized Recursion | O(n) | O(n) |
| 7 | DP on the Shifted Number (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### CountingBits.java

```java
import java.util.Arrays;

public class CountingBits {

    public static void main(String[] args) {
        int[] inputs = {2, 5, 0};
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
