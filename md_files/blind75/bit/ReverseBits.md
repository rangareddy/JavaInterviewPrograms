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
