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
