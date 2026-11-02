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
