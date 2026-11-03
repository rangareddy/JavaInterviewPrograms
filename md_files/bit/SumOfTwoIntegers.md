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

### Approach 2: Recursive XOR and Carry

The same idea written recursively: the sum of `a` and `b` is the sum of `a ^ b` and the carry `(a & b) << 1`.

```java
public static int getSumRecursive(int a, int b) {
    return b == 0 ? a : getSumRecursive(a ^ b, (a & b) << 1);
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1) (at most 32 calls)

### Approach 3: Repeated Increment or Decrement

Add `b` to `a` by incrementing `a` exactly `b` times (or decrementing `|b|` times for a negative `b`), where increment and decrement are implemented with bit operations only. Correct but linear in the size of `b`, so only for small values.

```java
public static int getSumIncrement(int a, int b) {
    boolean negative = b < 0;
    int steps = negative ? increment(~b) : b;                 // |b| without using '-'
    for (int i = 0; i < steps; i++) a = negative ? decrement(a) : increment(a);
    return a;
}

private static int increment(int x) {
    int mask = 1;
    while ((x & mask) != 0) { x ^= mask; mask <<= 1; }        // flip the trailing ones
    return x ^ mask;
}

private static int decrement(int x) {
    int mask = 1;
    while (mask != 0 && (x & mask) == 0) { x ^= mask; mask <<= 1; }   // flip the trailing zeros
    return x ^ mask;
}
```

- **Time Complexity:** O(|b|)
- **Space Complexity:** O(1)

### Approach 4: Library Call: Integer.sum

The JDK already contains the operation (`Integer.sum`, which the compiler turns into the CPU's add instruction). Not an answer to the puzzle, but what production code should do.

```java
public static int getSumLibrary(int a, int b) {
    return Integer.sum(a, b);
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 5: Array Length Trick (Educational, Non-Negative Only)

The length of the concatenation of two arrays of sizes `a` and `b` is `a + b`, computed by the runtime rather than by our code. It needs memory proportional to the result and works only for non-negative numbers.

```java
public static int getSumLength(int a, int b) {
    return new StringBuilder().append(new char[a]).append(new char[b]).length();
}
```

- **Time Complexity:** O(a + b)
- **Space Complexity:** O(a + b)

### Approach 6: Parallel-Prefix (Carry-Lookahead) Adder

Compute all carries at once like a hardware adder instead of waiting for them to ripple: generate (`a & b`) and propagate (`a ^ b`) bits are combined with doubling shift distances 1, 2, 4, 8, 16 (a Kogge-Stone adder), then the sum is `(a ^ b) ^ (carries << 1)`. Five fixed steps instead of up to 32 loop iterations.

```java
public static int getSumPrefix(int a, int b) {
    int x = a ^ b;                       // sum without carries
    int generate = a & b, propagate = x;
    for (int shift = 1; shift < 32; shift <<= 1) {
        generate |= propagate & (generate << shift);
        propagate &= propagate << shift;
    }
    return x ^ (generate << 1);
}
```

- **Time Complexity:** O(1) (5 steps)
- **Space Complexity:** O(1)

### Approach 7: XOR and Shifted AND in a Loop (Optimized)

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

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Bit-by-Bit Full Adder | O(32) = O(1) | O(1) |
| 2 | Recursive XOR and Carry | O(1) | O(1) (at most 32 calls) |
| 3 | Repeated Increment or Decrement | O(|b|) | O(1) |
| 4 | Library Call: Integer.sum | O(1) | O(1) |
| 5 | Array Length Trick (Educational, Non-Negative Only) | O(a + b) | O(a + b) |
| 6 | Parallel-Prefix (Carry-Lookahead) Adder | O(1) (5 steps) | O(1) |
| 7 | XOR and Shifted AND in a Loop (Optimized) | O(1) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
