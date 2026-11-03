# Reverse Integer

Given a signed 32-bit integer `x`, return `x` with its digits reversed. If reversing causes the value to go outside the signed 32-bit range `[-2^31, 2^31 - 1]`, return `0`. Assume the environment does not allow 64-bit integers.

## Algorithm Strategy (Pop and Push Digits with Overflow Check)

1. Repeatedly pop the last digit of `x` with `x % 10` (it keeps the sign in Java) and shrink `x` with `x / 10`.
2. Push the digit onto `result` with `result * 10 + digit`.
3. Before pushing, check that the multiplication cannot overflow: if `result > Integer.MAX_VALUE / 10`, or it is equal and `digit > 7`, the result would exceed the range. Mirror the check for negative numbers with `Integer.MIN_VALUE / 10` and `digit < -8`.
4. On overflow return 0; otherwise return `result` when `x` reaches 0.

## Example walkthrough

- For 123: pops 3, 2, 1 build `3, 32, 321`. For -123 the result is `-321`.
- Result: `321`. For `1534236469` the reversed value does not fit in 32 bits: `0`.

## Complexity Analysis

- **Time Complexity:** `O(log10 |x|)` — One iteration per digit.
- **Space Complexity:** `O(1)` — A few integers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: String Reversal

Reverse the digits as text and parse the result, returning 0 if parsing overflows.

```java
public static int reverseString(int x) {
    boolean negative = x < 0;
    String digits = new StringBuilder(String.valueOf(Math.abs((long) x))).reverse().toString();
    try {
        int value = Integer.parseInt(digits);
        return negative ? -value : value;
    } catch (NumberFormatException e) {
        return 0;
    }
}
```

- **Time Complexity:** O(log10 |x|)
- **Space Complexity:** O(log10 |x|)

### Approach 2: Long Accumulator

Accumulate in a `long` and check the range at the end. (It relies on 64-bit arithmetic, which the original statement discourages.)

```java
public static int reverseLong(int x) {
    long result = 0;
    long value = x;
    while (value != 0) {
        result = result * 10 + value % 10;
        value /= 10;
    }
    return result > Integer.MAX_VALUE || result < Integer.MIN_VALUE ? 0 : (int) result;
}
```

- **Time Complexity:** O(log10 |x|)
- **Space Complexity:** O(1)

### Approach 3: Digit Deque

Push the digits onto a deque and rebuild the number from the front with an overflow check via `Math.multiplyExact`.

```java
public static int reverseDeque(int x) {
    Deque<Integer> digits = new ArrayDeque<>();
    int value = x;
    while (value != 0) { digits.add(value % 10); value /= 10; }
    int result = 0;
    try {
        for (int d : digits) result = Math.addExact(Math.multiplyExact(result, 10), d);
    } catch (ArithmeticException e) {
        return 0;
    }
    return result;
}
```

- **Time Complexity:** O(log10 |x|)
- **Space Complexity:** O(log10 |x|)

### Approach 4: Pop and Push with Pre-Check (Optimized)

Check for overflow before each push, so only 32-bit arithmetic is needed. This is the version implemented in the repository.

```java
public static int reverse(int x) {
    int result = 0;
    while (x != 0) {
        int digit = x % 10;
        x /= 10;
        if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && digit > 7)) return 0;
        if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && digit < -8)) return 0;
        result = result * 10 + digit;
    }
    return result;
}
```

- **Time Complexity:** O(log10 |x|)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | String Reversal | O(log10 |x|) | O(log10 |x|) |
| 2 | Long Accumulator | O(log10 |x|) | O(1) |
| 3 | Digit Deque | O(log10 |x|) | O(log10 |x|) |
| 4 | Pop and Push with Pre-Check (Optimized) | O(log10 |x|) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### ReverseInteger.java

```java
public class ReverseInteger {

    public static void main(String[] args) {
        int[] inputs = {123, -123, 120, 0, 1534236469, -2147483648, 1463847412};
        for (int x : inputs) {
            System.out.println("Input  : x = " + x);
            System.out.println("Output : " + reverse(x));
            System.out.println();
        }
    }

    public static int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int digit = x % 10;
            x /= 10;
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0;
            }
            if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0;
            }
            result = result * 10 + digit;
        }
        return result;
    }
}
```
