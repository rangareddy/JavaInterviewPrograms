# Number of 1 Bits

Write a function that takes the binary representation of an unsigned integer and returns the number of '1' bits it has (the Hamming weight).

## Algorithm Strategy (Brian Kernighan's Bit Trick)

1. Subtracting 1 from a number flips its lowest set bit to 0 and all the zeros below it to 1.
2. So `n & (n - 1)` clears exactly the lowest set bit of `n`.
3. Repeat `n = n & (n - 1)` and count the iterations until `n` becomes 0; the loop runs once per set bit, not once per bit position.
4. Java's `int` is signed, but the loop only uses AND and subtraction, so a value with the top bit set (a negative `int`) works without any special case.

## Example walkthrough

- For `n = 11` (binary `1011`): `1011 & 1010 = 1010`, then `1010 & 1001 = 1000`, then `1000 & 0111 = 0`.
- Three iterations: the answer is `3`.

## Complexity Analysis

- **Time Complexity:** `O(k)` — `k` is the number of set bits (at most 32).
- **Space Complexity:** `O(1)` — One counter.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Check Every Bit

Test all 32 bits with a shifting mask and count the ones.

```java
public static int hammingWeightLoop(int n) {
    int count = 0;
    for (int i = 0; i < 32; i++) {
        if ((n & (1 << i)) != 0) count++;
    }
    return count;
}
```

- **Time Complexity:** O(32)
- **Space Complexity:** O(1)

### Approach 2: Brian Kernighan's Trick (Optimized)

`n & (n - 1)` clears the lowest set bit, so the loop runs once per set bit. This is the version implemented in the repository.

```java
public static int hammingWeight(int n) {
    int count = 0;
    while (n != 0) {
        n &= (n - 1);
        count++;
    }
    return count;
}
```

- **Time Complexity:** O(k) where k is the number of set bits
- **Space Complexity:** O(1)

### Approach 3: Lookup Table of Bytes

Precompute the bit count of all 256 byte values and add up the four bytes of the number. Handy when the function is called very often.

```java
private static final int[] TABLE = new int[256];
static {
    for (int i = 1; i < 256; i++) TABLE[i] = TABLE[i >> 1] + (i & 1);
}

public static int hammingWeightTable(int n) {
    return TABLE[n & 0xff] + TABLE[(n >>> 8) & 0xff] + TABLE[(n >>> 16) & 0xff] + TABLE[(n >>> 24) & 0xff];
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(256)

### Approach 4: Library: Integer.bitCount

The JDK method is an intrinsic that compiles to a single `popcnt` instruction on modern CPUs, so it is the best choice in production code.

```java
public static int hammingWeightBuiltIn(int n) {
    return Integer.bitCount(n);
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Check Every Bit | O(32) | O(1) |
| 2 | Brian Kernighan's Trick (Optimized) | O(k) where k is the number of set bits | O(1) |
| 3 | Lookup Table of Bytes | O(1) | O(256) |
| 4 | Library: Integer.bitCount | O(1) | O(1) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### NumberOf1Bits.java

```java
public class NumberOf1Bits {

    public static void main(String[] args) {
        int[] inputs = {11, 128, -3, 0};
        for (int n : inputs) {
            System.out.println("Input  : n = " + toBinary(n));
            System.out.println("Output : " + hammingWeight(n));
            System.out.println();
        }
    }

    public static int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n &= (n - 1);       // clear the lowest set bit
            count++;
        }
        return count;
    }

    private static String toBinary(int n) {
        return String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0');
    }
}
```
