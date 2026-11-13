# Number of 1 Bits

Return the number of `1` bits (the Hamming weight) in the binary representation of a 32-bit integer, counting the bits of the two's complement form for negative numbers.

## Algorithm Strategy (Brian Kernighan's Algorithm)

1. For any non-zero `n`, `n & (n - 1)` clears the lowest set bit: subtracting 1 turns the lowest 1 into 0 and every 0 below it into 1, so the AND removes exactly that one bit.
2. Repeat `n &= n - 1` and count the iterations until `n` becomes 0.
3. The loop runs once per set bit, not once per bit position, so it is fast for sparse numbers and never more than 32 iterations. It also works for negative numbers because only bit operations are used.

## Example walkthrough

- For `n = 11` (`1011`): `1011 & 1010 = 1010`, then `1010 & 1001 = 1000`, then `1000 & 0111 = 0`.
- Three iterations, so the result is `3`.

## Complexity Analysis

- **Time Complexity:** `O(k)` — `k` is the number of set bits, at most 32.
- **Space Complexity:** `O(1)` — A counter.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Test Every Bit

Check the lowest bit, count it and shift right (unsigned) 32 times.

```java
public static int hammingWeightLoop(int n) {
    int count = 0;
    for (int i = 0; i < 32; i++) {
        count += n & 1;
        n >>>= 1;
    }
    return count;
}
```

- **Time Complexity:** O(32)
- **Space Complexity:** O(1)

### Approach 2: Parallel Bit Counting (SWAR)

Add neighbouring bits in pairs, then nibbles, then bytes with masks and finish with a multiplication. It counts in a fixed number of operations, which is how `Integer.bitCount` is written.

```java
public static int hammingWeightSwar(int n) {
    n = n - ((n >>> 1) & 0x55555555);
    n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
    n = (n + (n >>> 4)) & 0x0F0F0F0F;
    return (n * 0x01010101) >>> 24;
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 3: Clear the Lowest Set Bit (Optimized)

Use `n &= n - 1` to drop one set bit per iteration and count the iterations. This is the version implemented in the repository.

```java
public static int hammingWeight(int n) {
    int count = 0;
    while (n != 0) {
        n &= n - 1;
        count++;
    }
    return count;
}
```

- **Time Complexity:** O(k) where k is the number of set bits
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Test Every Bit | O(32) | O(1) |
| 2 | Parallel Bit Counting (SWAR) | O(1) | O(1) |
| 3 | Clear the Lowest Set Bit (Optimized) | O(k) where k is the number of set bits | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### NumberOf1Bits.java

```java
public class NumberOf1Bits {

    public static void main(String[] args) {
        int[] inputs = {11, 128, -3, 0, 2147483645};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n + " (" + String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0') + ")");
            System.out.println("Output : " + hammingWeight(n));
            System.out.println();
        }
    }

    public static int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n &= n - 1;
            count++;
        }
        return count;
    }
}
```
