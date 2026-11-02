# Reverse Bits

Reverse the bits of a given 32-bit unsigned integer. (Java has no unsigned int, so the value is passed as a signed `int` with the same bit pattern.)

## Algorithm Strategy (Shift Bits One by One)

1. Keep a `result` that starts at 0.
2. Repeat 32 times: shift `result` left by one to make room, copy the lowest bit of `n` (`n & 1`) into it with OR, and then shift `n` right by one.
3. Use the unsigned shift `>>>` so that the sign bit does not get copied into the upper bits.
4. After 32 iterations the bit that was lowest in `n` is the highest in `result`, i.e. the bits are reversed.

## Example walkthrough

- For an 8-bit sketch of `00000110`: the loop takes bits 0, 1, 1, 0, 0, 0, 0, 0 in that order and builds `01100000`.
- For the 32-bit input `43261596` the result is `964176192`.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Always 32 iterations.
- **Space Complexity:** `O(1)` — Two variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Shift Bits One by One

Take the lowest bit of `n` 32 times and push it into the result. This is the version implemented in the repository.

```java
public static int reverseBits(int n) {
    int result = 0;
    for (int i = 0; i < 32; i++) {
        result = (result << 1) | (n & 1);
        n >>>= 1;
    }
    return result;
}
```

- **Time Complexity:** O(32)
- **Space Complexity:** O(1)

### Approach 2: Divide and Conquer with Masks (Optimized)

Swap the two 16-bit halves, then the bytes inside each half, then nibbles, pairs and single bits: five constant-time steps instead of a 32-iteration loop.

```java
public static int reverseBitsMasks(int n) {
    n = (n >>> 16) | (n << 16);
    n = ((n & 0xff00ff00) >>> 8) | ((n & 0x00ff00ff) << 8);
    n = ((n & 0xf0f0f0f0) >>> 4) | ((n & 0x0f0f0f0f) << 4);
    n = ((n & 0xcccccccc) >>> 2) | ((n & 0x33333333) << 2);
    n = ((n & 0xaaaaaaaa) >>> 1) | ((n & 0x55555555) << 1);
    return n;
}
```

- **Time Complexity:** O(1) (5 steps)
- **Space Complexity:** O(1)

### Approach 3: Lookup Table of Bytes

Precompute the reverse of every byte value and assemble the result from the four reversed bytes in opposite order. Fast when the method is called many times.

```java
private static final int[] REVERSED = new int[256];
static {
    for (int i = 0; i < 256; i++) REVERSED[i] = (REVERSED[i >> 1] >> 1) | ((i & 1) << 7);
}

public static int reverseBitsTable(int n) {
    return (REVERSED[n & 0xff] << 24) | (REVERSED[(n >>> 8) & 0xff] << 16)
            | (REVERSED[(n >>> 16) & 0xff] << 8) | REVERSED[(n >>> 24) & 0xff];
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(256)

### Approach 4: Library: Integer.reverse

The JDK already provides the operation (it uses the mask technique, or a single instruction on some CPUs).

```java
public static int reverseBitsBuiltIn(int n) {
    return Integer.reverse(n);
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Shift Bits One by One | O(32) | O(1) |
| 2 | Divide and Conquer with Masks (Optimized) | O(1) (5 steps) | O(1) |
| 3 | Lookup Table of Bytes | O(1) | O(256) |
| 4 | Library: Integer.reverse | O(1) | O(1) |

### Optimized Solution

The optimized solution is Approach 2 above. The version implemented in this repository is Approach 1.

## Source Code

### ReverseBits.java

```java
public class ReverseBits {

    public static void main(String[] args) {
        int[] inputs = {43261596, -3, 1, 0};
        for (int n : inputs) {
            int reversed = reverseBits(n);
            System.out.println("Input  : n = " + toBinary(n) + " (" + Integer.toUnsignedString(n) + ")");
            System.out.println("Output : " + toBinary(reversed) + " (" + Integer.toUnsignedString(reversed) + ")");
            System.out.println();
        }
    }

    public static int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) | (n & 1);
            n >>>= 1;
        }
        return result;
    }

    private static String toBinary(int n) {
        return String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0');
    }
}
```
