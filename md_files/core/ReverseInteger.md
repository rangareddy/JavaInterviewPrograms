# Reverse Integer

Given a signed 32-bit integer `x`, return `x` with its digits reversed. If reversing makes the value go outside the signed 32-bit range, return `0`.

## Algorithm Strategy (Pop and Push Digits)

1. Repeatedly take the last digit with `x % 10` (the sign is kept automatically) and remove it with `x / 10`.
2. Before pushing the digit onto the result, check that `result * 10 + digit` would not overflow: compare `result` with `Integer.MAX_VALUE / 10` and `Integer.MIN_VALUE / 10`.
3. If an overflow would happen, return 0.
4. Otherwise build `result = result * 10 + digit` and continue until `x` is 0.

## Example walkthrough

- For `123`: digits 3, 2, 1 are pushed, giving 3, 32, 321.
- For `1534236469` the reversed value does not fit in 32 bits, so the result is 0.

## Complexity Analysis

- **Time Complexity:** `O(log10 |x|)` — One step per digit.
- **Space Complexity:** `O(1)` — Two integers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Use a long

Accumulate in a `long` and range-check at the end.

```java
public static int viaLong(int x) {
    long r = 0; long t = x;
    while (t != 0) { r = r * 10 + t % 10; t /= 10; }
    return r > Integer.MAX_VALUE || r < Integer.MIN_VALUE ? 0 : (int) r;
}
```

- **Time Complexity:** O(log10 |x|)
- **Space Complexity:** O(1)

### Approach 2: String Reversal

Reverse the digits of the absolute value as a string and parse it back.

```java
public static int viaString(int x) {
    String s = new StringBuilder(Long.toString(Math.abs((long) x))).reverse().toString();
    long v = Long.parseLong(s) * (x < 0 ? -1 : 1);
    return v > Integer.MAX_VALUE || v < Integer.MIN_VALUE ? 0 : (int) v;
}
```

- **Time Complexity:** O(log10 |x|)
- **Space Complexity:** O(log10 |x|)

### Approach 3: Pop and Push with an Overflow Guard (Optimized)

Check the limits before each push so no wider type is needed. This is the version implemented in the repository.

```java
public static int guarded(int x) {
    int r = 0;
    while (x != 0) {
        int d = x % 10; x /= 10;
        if (r > Integer.MAX_VALUE / 10 || (r == Integer.MAX_VALUE / 10 && d > 7)) return 0;
        if (r < Integer.MIN_VALUE / 10 || (r == Integer.MIN_VALUE / 10 && d < -8)) return 0;
        r = r * 10 + d;
    }
    return r;
}
```

- **Time Complexity:** O(log10 |x|)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Use a long | O(log10 |x|) | O(1) |
| 2 | String Reversal | O(log10 |x|) | O(log10 |x|) |
| 3 | Pop and Push with an Overflow Guard (Optimized) | O(log10 |x|) | O(1) |

## Source Code

### ReverseInteger.java

```java
public class ReverseInteger {

    public static void main(String[] args) {
        int[] inputs = {123, -123, 120, 0, 1534236469, Integer.MIN_VALUE};
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

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: `% 10` and `/ 10` peel digits off the right while `* 10 +` pushes them onto the result. Checking the limit before the push keeps the arithmetic inside 32 bits.

**Input:** `x = 123`.

| Step | `x` | digit | `result` |
|------|-----|-------|----------|
| 1 | 12 | 3 | 3 |
| 2 | 1 | 2 | 32 |
| 3 | 0 | 1 | 321 |

**Output:** `321`.
