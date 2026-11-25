# Sum of Two Integers

Given two integers `a` and `b`, return the sum of the two integers without using the operators `+` and `-`.

## Algorithm Strategy (XOR plus Shifted AND Carry)

1. The XOR of two numbers is their sum without carries.
2. The AND of the two numbers, shifted left by one, is the carry.
3. Replace `a` with the XOR and `b` with the carry and repeat until the carry is zero.
4. In Java, `int` arithmetic is two's complement, so negative numbers work without extra handling.

## Example walkthrough

- For `a = 1`, `b = 2`: XOR is 3, the carry is `(1 & 2) << 1 = 0`.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(1)` — At most 32 iterations.
- **Space Complexity:** `O(1)` — Two variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plus Operator

Simply add the numbers. This is what the problem forbids, and is the reference for testing.

```java
public static int plus(int a, int b) { return a + b; }
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 2: Bit-by-Bit Full Adder

Add each bit position with a carry bit.

```java
public static int fullAdder(int a, int b) { int r = 0, carry = 0; for (int i = 0; i < 32; i++) { int x = (a >> i) & 1, y = (b >> i) & 1; int s = x ^ y ^ carry; carry = (x & y) | (x & carry) | (y & carry); r |= s << i; } return r; }
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 3: XOR and Carry Loop (Optimized)

Repeat XOR and shifted AND until no carry is left. This is the version implemented in the repository.

```java
public static int loop(int a, int b) { while (b != 0) { int c = (a & b) << 1; a ^= b; b = c; } return a; }
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plus Operator | O(1) | O(1) |
| 2 | Bit-by-Bit Full Adder | O(1) | O(1) |
| 3 | XOR and Carry Loop (Optimized) | O(1) | O(1) |

## Source Code

### SumOfTwoIntegers.java

```java
public class SumOfTwoIntegers {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2}, {2, 3}, {-1, 1}, {-5, -7}, {123456, 654321}};
        for (int[] in : inputs) {
            System.out.println("Input  : a = " + in[0] + ", b = " + in[1]);
            System.out.println("Output : " + getSum(in[0], in[1]));
            System.out.println();
        }
    }

    public static int getSum(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1;
            a ^= b;
            b = carry;
        }
        return a;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: this is a hardware adder. In each bit, `a XOR b` is the sum bit and `a AND b` is the carry that must be added into the next bit, so it is shifted left. Adding the carry is the same problem again, and the loop repeats until the carry vanishes. Two's complement makes negative numbers behave correctly.

**Input:** `a = 2`, `b = 3`.

| Step | `a` | `b` | carry `(a & b) << 1` |
|------|-----|-----|-----------------------|
| 1 | 2 (`10`) | 3 (`11`) | `10 << 1` = 4 |
| after | 1 (`01`) | 4 (`100`) | |
| 2 | 1 | 4 | `0` |
| after | 5 | 0 | done |

**Output for this input:** `5`. For the first sample in `main`, `1 + 2`, the output is `3`.
