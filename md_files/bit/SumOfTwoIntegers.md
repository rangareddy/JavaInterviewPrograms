# Sum of Two Integers

Given two integers `a` and `b`, return their sum without using the operators `+` and `-`.

## Algorithm Strategy (XOR for Sum, AND for Carry)

1. Adding two bits without a carry is XOR (`1 ^ 1 = 0`, `1 ^ 0 = 1`); the carry appears only when both bits are 1, which is AND, and it moves one position to the left.
2. Compute the carry: `carry = (a & b) << 1`.
3. Compute the sum without the carry: `a = a ^ b`, then set `b = carry`.
4. Repeat until the carry `b` is 0; then `a` holds the result. Java integers use two's complement, so this also works for negative numbers.

## Example walkthrough

- For `a = 2` and `b = 3`: `10 ^ 11 = 01` with carry `(10 & 11) << 1 = 100`.
- Next round: `01 ^ 100 = 101` with carry 0. Result: `5`.

## Complexity Analysis

- **Time Complexity:** `O(1)` — At most 32 iterations for a 32-bit integer.
- **Space Complexity:** `O(1)` — Two variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Bit-by-Bit Full Adder

Add the two numbers one bit at a time like a hardware full adder: the sum bit is `a ^ b ^ carry` and the new carry is the majority of the three bits.

```java
public static int getSumBitByBit(int a, int b) {
    int result = 0, carry = 0;
    for (int i = 0; i < 32; i++) {
        int bitA = (a >>> i) & 1, bitB = (b >>> i) & 1;
        int sum = bitA ^ bitB ^ carry;
        carry = (bitA & bitB) | (bitA & carry) | (bitB & carry);
        result |= sum << i;
    }
    return result;
}
```

- **Time Complexity:** O(32) = O(1)
- **Space Complexity:** O(1)

### Approach 2: XOR and Shifted AND in a Loop (Optimized)

XOR gives the sum without carries and `(a & b) << 1` gives the carries; repeat until no carry is left. This is the version implemented in the repository.

```java
public static int getSum(int a, int b) {
    while (b != 0) {
        int carry = (a & b) << 1;
        a = a ^ b;
        b = carry;
    }
    return a;
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 3: Recursive XOR and Carry

The same idea written recursively: the sum of `a` and `b` is the sum of `a ^ b` and the carry `(a & b) << 1`.

```java
public static int getSumRecursive(int a, int b) {
    return b == 0 ? a : getSumRecursive(a ^ b, (a & b) << 1);
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1) (at most 32 calls)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Bit-by-Bit Full Adder | O(32) = O(1) | O(1) |
| 2 | XOR and Shifted AND in a Loop (Optimized) | O(1) | O(1) |
| 3 | Recursive XOR and Carry | O(1) | O(1) (at most 32 calls) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### SumOfTwoIntegers.java

```java
public class SumOfTwoIntegers {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2}, {2, 3}, {-1, 1}, {-5, 3}, {7, -7}};
        for (int[] input : inputs) {
            System.out.println("Input  : a = " + input[0] + ", b = " + input[1]);
            System.out.println("Output : " + getSum(input[0], input[1]));
            System.out.println();
        }
    }

    public static int getSum(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }
        return a;
    }
}
```
