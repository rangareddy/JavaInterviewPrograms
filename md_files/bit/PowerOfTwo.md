# Power of Two

Given an integer `n`, return `true` if it is a power of two, that is, `n == 2^k` for some integer `k >= 0`.

## Algorithm Strategy (Single Set Bit)

1. A power of two has exactly one bit set in binary (`1`, `10`, `100`, ...).
2. Subtracting 1 from it flips that bit to 0 and all lower bits to 1, so `n & (n - 1)` clears the lowest set bit.
3. For a power of two the result is 0.
4. Reject `n <= 0` first, since zero and negatives are never powers of two.

## Example walkthrough

- `8 = 1000` and `7 = 0111`, so `8 & 7 = 0`: power of two.
- `6 = 110` and `5 = 101`, so `6 & 5 = 100`, not 0: not a power of two.

## Complexity Analysis

- **Time Complexity:** `O(1)` — One bitwise operation.
- **Space Complexity:** `O(1)` — No extra memory.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Divide by Two

Keep dividing by 2 while the number is even; a power of two ends at 1.

```java
public static boolean divide(int n) {
    if (n <= 0) return false;
    while (n % 2 == 0) n /= 2;
    return n == 1;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Approach 2: Count the Set Bits

A power of two has exactly one set bit.

```java
public static boolean bitCount(int n) { return n > 0 && Integer.bitCount(n) == 1; }
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 3: Clear the Lowest Set Bit (Optimized)

`n & (n - 1)` is zero only for a single set bit. This is the version implemented in the repository.

```java
public static boolean lowestBit(int n) { return n > 0 && (n & (n - 1)) == 0; }
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Divide by Two | O(log n) | O(1) |
| 2 | Count the Set Bits | O(1) | O(1) |
| 3 | Clear the Lowest Set Bit (Optimized) | O(1) | O(1) |

## Source Code

### PowerOfTwo.java

```java
public class PowerOfTwo {

    public static void main(String[] args) {
        int[] inputs = {1, 16, 3, 0, -8, 1024, Integer.MIN_VALUE};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + isPowerOfTwo(n));
            System.out.println();
        }
    }

    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: subtracting 1 turns the lowest set bit into 0 and every lower bit into 1, so `n & (n - 1)` removes exactly the lowest set bit. If that leaves 0, there was only one set bit.

**Input:** `n = 16`.

| Expression | Binary | Result |
|------------|--------|--------|
| `n` | 10000 | |
| `n - 1` | 01111 | |
| `n & (n - 1)` | 00000 | 0 |

**Output:** `true`.
