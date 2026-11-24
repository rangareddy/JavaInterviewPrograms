# Pow(x, n)

Implement `pow(x, n)`, which raises `x` to the power `n`, where `n` may be negative.

## Algorithm Strategy (Exponentiation by Squaring)

1. Work with `long exp = n` so that `Integer.MIN_VALUE` can be negated safely; if it is negative, replace `x` by `1 / x` and `exp` by `-exp`.
2. Look at the bits of `exp` from the lowest. Keep `base` equal to `x` raised to the current bit's power of two.
3. If the current bit is 1, multiply the result by `base`.
4. Square `base`, shift `exp` right by one, and repeat until `exp` is 0.

## Example walkthrough

- For `2^10`: 10 is `1010` in binary, so the result multiplies `2^2` and `2^8`.
- Result: `4 * 256 = 1024`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — One loop iteration per bit of the exponent.
- **Space Complexity:** `O(1)` — Only a few variables are used.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Repeated Multiplication

Multiply `x` by itself `|n|` times. Too slow for large exponents.

```java
public static double linear(double x, int n) {
    long e = Math.abs((long) n); double r = 1;
    for (long i = 0; i < e; i++) r *= x;
    return n < 0 ? 1 / r : r;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Recursive Squaring

`x^n = (x^(n/2))^2`, times `x` when `n` is odd.

```java
public static double recursive(double x, long n) {
    if (n == 0) return 1;
    if (n < 0) return 1 / recursive(x, -n);
    double half = recursive(x, n / 2);
    return n % 2 == 0 ? half * half : half * half * x;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(log n)

### Approach 3: Iterative Squaring on the Bits (Optimized)

Walk the bits of the exponent and square the base each step. This is the version implemented in the repository.

```java
public static double iterative(double x, int n) {
    long e = n; if (e < 0) { x = 1 / x; e = -e; }
    double r = 1, b = x;
    while (e > 0) { if ((e & 1) == 1) r *= b; b *= b; e >>= 1; }
    return r;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Repeated Multiplication | O(n) | O(1) |
| 2 | Recursive Squaring | O(log n) | O(log n) |
| 3 | Iterative Squaring on the Bits (Optimized) | O(log n) | O(1) |

## Source Code

### PowXN.java

```java
public class PowXN {

    public static void main(String[] args) {
        double[] xs = {2.0, 2.1, 2.0, 0.0, 1.0};
        int[] ns = {10, 3, -2, 5, Integer.MIN_VALUE};
        for (int i = 0; i < xs.length; i++) {
            System.out.println("Input  : x = " + xs[i] + ", n = " + ns[i]);
            System.out.println("Output : " + myPow(xs[i], ns[i]));
            System.out.println();
        }
    }

    public static double myPow(double x, int n) {
        long exp = n;
        if (exp < 0) {
            x = 1 / x;
            exp = -exp;
        }
        double result = 1.0;
        double base = x;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result *= base;
            }
            base *= base;
            exp >>= 1;
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: write the exponent in binary. Each set bit contributes `x` raised to that bit's power of two, and the base is squared once per bit, so only `log2(n)` multiplications are needed.

**Input:** `x = 2`, `n = 10` (binary `1010`).

| Step | `exp` | bit | `base` | `result` |
|------|-------|-----|--------|----------|
| 1 | 10 | 0 | 2 | 1 |
| 2 | 5 | 1 | 4 | 4 |
| 3 | 2 | 0 | 16 | 4 |
| 4 | 1 | 1 | 256 | 1024 |

**Output:** `1024.0`.
