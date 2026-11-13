# Reverse Bits

Reverse the bits of a 32-bit unsigned integer. In Java the input is an `int` whose bits are interpreted as unsigned, so the result is also returned as an `int` with the same 32 bits reversed.

## Algorithm Strategy (Shift Bits One by One)

1. Build the result bit by bit: for each of the 32 positions, shift `result` left by one to make room.
2. Copy the lowest bit of `n` into it with `result |= n & 1`.
3. Shift `n` right with the unsigned shift `>>>` so the sign bit is not copied back in, and repeat 32 times.
4. After 32 iterations the first bit of the input is the last bit of the result and vice versa.

## Example walkthrough

- For the 8-bit pattern `00000110` (considered inside a 32-bit value) the low bits `0, 1, 1, 0, 0, 0, 0, 0` are pushed into the result one at a time.
- They end up as the top bits `01100000...`, which is the input read backwards.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Exactly 32 iterations.
- **Space Complexity:** `O(1)` — A few integers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Swap Halves Recursively (Divide and Conquer)

Swap the two 16-bit halves, then the bytes inside each half, then nibbles, pairs and single bits, using masks. It needs only five steps and no loop, which is how `Integer.reverse` works.

```java
public static int reverseBitsMasks(int n) {
    n = (n >>> 16) | (n << 16);
    n = ((n & 0xFF00FF00) >>> 8) | ((n & 0x00FF00FF) << 8);
    n = ((n & 0xF0F0F0F0) >>> 4) | ((n & 0x0F0F0F0F) << 4);
    n = ((n & 0xCCCCCCCC) >>> 2) | ((n & 0x33333333) << 2);
    n = ((n & 0xAAAAAAAA) >>> 1) | ((n & 0x55555555) << 1);
    return n;
}
```

- **Time Complexity:** O(1) with 5 steps
- **Space Complexity:** O(1)

### Approach 2: Loop over 32 Bits (Optimized)

Push the lowest bit of the input onto the result 32 times using the unsigned right shift. This is the version implemented in the repository.

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

- **Time Complexity:** O(1) with 32 steps
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Swap Halves Recursively (Divide and Conquer) | O(1) with 5 steps | O(1) |
| 2 | Loop over 32 Bits (Optimized) | O(1) with 32 steps | O(1) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### ReverseBits.java

```java
public class ReverseBits {

    public static void main(String[] args) {
        int[] inputs = {0b00000010100101000001111010011100, 0b11111111111111111111111111111101, 1, 0};
        for (int n : inputs) {
            System.out.println("Input  : n = " + binary(n));
            System.out.println("Output : " + binary(reverseBits(n)));
            System.out.println();
        }
    }

    private static String binary(int n) {
        return String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0');
    }

    public static int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) | (n & 1);
            n >>>= 1;
        }
        return result;
    }
}
```
