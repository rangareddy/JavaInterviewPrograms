# Pow(x, n)

Implement `pow(x, n)`, which raises `x` to the integer power `n` (which may be negative).

## Algorithm Strategy (Fast Exponentiation (Exponentiation by Squaring))

1. Convert the exponent to a `long` so that `Integer.MIN_VALUE` can be negated safely. If it is negative, invert the base (`x = 1 / x`) and make the exponent positive.
2. Keep a `result` initialised to 1 and a running power `base = x`.
3. For every bit of the exponent from the lowest: if the bit is 1, multiply `result` by `base`; then square `base`.
4. After about `log2(n)` iterations `result` equals `x^n`.

## Example walkthrough

- For `x = 2`, `n = 10` (binary `1010`): the bits multiply `4` and `256`, giving `4 * 256 = 1024`.
- Result: `1024.0`. For `x = 2`, `n = -2` the result is `0.25`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — One iteration per bit of the exponent.
- **Space Complexity:** `O(1)` — A few variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Linear Multiplication

Multiply `x` by itself `|n|` times. Too slow for large exponents.

```java
public static double myPowLinear(double x, int n) {
    long exponent = Math.abs((long) n);
    double result = 1;
    for (long i = 0; i < exponent; i++) result *= x;
    return n < 0 ? 1 / result : result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Recursive Squaring

Compute `x^(n/2)` once and square it, multiplying by `x` for odd exponents.

```java
public static double myPowRecursive(double x, int n) {
    long exponent = n;
    if (exponent < 0) { x = 1 / x; exponent = -exponent; }
    return fastPow(x, exponent);
}

private static double fastPow(double x, long n) {
    if (n == 0) return 1;
    double half = fastPow(x, n / 2);
    return n % 2 == 0 ? half * half : half * half * x;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(log n)

### Approach 3: Math.pow (Reference)

The library function; useful as a check but not an answer to the interview question.

```java
public static double myPowLibrary(double x, int n) {
    return Math.pow(x, n);
}
```

- **Time Complexity:** O(1) (library)
- **Space Complexity:** O(1)

### Approach 4: Iterative Binary Exponentiation (Optimized)

Walk through the bits of the exponent, squaring the base each step. This is the version implemented in the repository.

```java
public static double myPow(double x, int n) {
    long exponent = n;
    if (exponent < 0) { x = 1 / x; exponent = -exponent; }
    double result = 1, base = x;
    while (exponent > 0) {
        if ((exponent & 1) == 1) result *= base;
        base *= base;
        exponent >>= 1;
    }
    return result;
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Linear Multiplication | O(n) | O(1) |
| 2 | Recursive Squaring | O(log n) | O(log n) |
| 3 | Math.pow (Reference) | O(1) (library) | O(1) |
| 4 | Iterative Binary Exponentiation (Optimized) | O(log n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### PowXN.java

```java
public class PowXN {

    public static void main(String[] args) {
        double[] bases = {2.0, 2.1, 2.0, 0.5, 1.0, 2.0};
        int[] exponents = {10, 3, -2, 4, Integer.MIN_VALUE, 0};
        for (int i = 0; i < bases.length; i++) {
            System.out.println("Input  : x = " + bases[i] + ", n = " + exponents[i]);
            System.out.println("Output : " + myPow(bases[i], exponents[i]));
            System.out.println();
        }
    }

    public static double myPow(double x, int n) {
        long exponent = n;
        if (exponent < 0) {
            x = 1 / x;
            exponent = -exponent;
        }

        double result = 1;
        double base = x;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result *= base;
            }
            base *= base;
            exponent >>= 1;
        }
        return result;
    }
}
```
