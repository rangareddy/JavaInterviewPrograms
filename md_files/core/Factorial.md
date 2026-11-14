# Factorial

Compute `n!` (the product of the integers from 1 to `n`, with `0! = 1`). Because factorials grow very quickly, show how to handle results that do not fit in a `long`.

## Algorithm Strategy (Iterative Product with BigInteger for Large Values)

1. Reject negative input, because the factorial is only defined for non-negative integers.
2. Start with `result = 1` (this also covers `0! = 1`).
3. Multiply `result` by every integer from 2 up to `n`.
4. A `long` overflows after `20!`, so use `BigInteger` (an arbitrary precision integer) when larger values are needed.

## Example walkthrough

- For `n = 5`: `1 * 2 * 3 * 4 * 5 = 120`.
- For `n = 25` the value `15511210043330985984000000` needs `BigInteger`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — `n - 1` multiplications (for BigInteger each multiplication costs more as the number grows).
- **Space Complexity:** `O(1) for long, O(n) digits for BigInteger` — The result itself grows with `n`.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

The textbook definition `n! = n * (n - 1)!`. Simple, but it uses one stack frame per step.

```java
public static long factorialRecursive(int n) {
    if (n < 0 || n > 20) throw new IllegalArgumentException("out of range: " + n);
    return n <= 1 ? 1 : n * factorialRecursive(n - 1);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Streams Reduce

Multiply the numbers `1..n` with `LongStream.rangeClosed(...).reduce`.

```java
public static long factorialStream(int n) {
    if (n < 0 || n > 20) throw new IllegalArgumentException("out of range: " + n);
    return LongStream.rangeClosed(1, n).reduce(1L, (a, b) -> a * b);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Memoized Table

Cache `0!..20!` once so repeated queries are O(1).

```java
private static final long[] TABLE = new long[21];
static {
    TABLE[0] = 1;
    for (int i = 1; i <= 20; i++) TABLE[i] = TABLE[i - 1] * i;
}

public static long factorialTable(int n) {
    if (n < 0 || n > 20) throw new IllegalArgumentException("out of range: " + n);
    return TABLE[n];
}
```

- **Time Complexity:** O(1) per query after O(20) setup
- **Space Complexity:** O(1)

### Approach 4: Iterative Loop (Optimized)

A plain loop with a `long` accumulator, plus a `BigInteger` version for large `n`. This is the version implemented in the repository.

```java
public static long factorial(int n) {
    if (n < 0 || n > 20) throw new IllegalArgumentException("out of range: " + n);
    long result = 1;
    for (int i = 2; i <= n; i++) result *= i;
    return result;
}

public static BigInteger bigFactorial(int n) {
    if (n < 0) throw new IllegalArgumentException("negative: " + n);
    BigInteger result = BigInteger.ONE;
    for (int i = 2; i <= n; i++) result = result.multiply(BigInteger.valueOf(i));
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1) for long, O(n) digits for BigInteger

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(n) | O(n) |
| 2 | Streams Reduce | O(n) | O(1) |
| 3 | Memoized Table | O(1) per query after O(20) setup | O(1) |
| 4 | Iterative Loop (Optimized) | O(n) | O(1) for long, O(n) digits for BigInteger |

## Source Code

### Factorial.java

```java
import java.math.BigInteger;

public class Factorial {

    public static void main(String[] args) {
        int[] inputs = {0, 1, 5, 10, 20};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + factorial(n));
            System.out.println();
        }

        System.out.println("Input  : n = 25 (needs BigInteger)");
        System.out.println("Output : " + bigFactorial(25));
    }

    public static long factorial(int n) {
        if (n < 0 || n > 20) {
            throw new IllegalArgumentException("n must be between 0 and 20 for a long result: " + n);
        }

        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static BigInteger bigFactorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must not be negative: " + n);
        }

        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

The idea: `n! = 1 * 2 * 3 * ... * n`, so start with `result = 1` and multiply by each `i` from 2 up to `n`. One loop, constant extra memory, no recursion depth and no table. Starting at 2 is enough because multiplying by 1 changes nothing, and the empty loop for `n = 0` or `n = 1` leaves the correct `1`.

**Input:** `n = 5`. `result` starts at `1`.

| Step | `i` | `result` before | `result *= i` | `result` after |
|------|-----|-----------------|---------------|----------------|
| 1 | 2 | 1 | 1 * 2 | 2 |
| 2 | 3 | 2 | 2 * 3 | 6 |
| 3 | 4 | 6 | 6 * 4 | 24 |
| 4 | 5 | 24 | 24 * 5 | 120 |

After `i = 5` the condition `i <= n` fails and the loop ends. The diagram is omitted because this is pure arithmetic and the table shows every step.

**Edge cases.** `n = 0` and `n = 1` never enter the loop and return `1`. `long` holds `20! = 2432902008176640000` but would overflow at `21!`, so `factorial` throws `IllegalArgumentException("n must be between 0 and 20 for a long result: ...")` for `n < 0` or `n > 20` instead of returning a wrong number. For larger `n`, `bigFactorial` uses the same loop with `BigInteger`; it only rejects negative `n` (`bigFactorial(25)` is `15511210043330985984000000`).

**Result:** `factorial(5)` returns `120`; the class prints `1, 1, 120, 3628800, 2432902008176640000` for `0, 1, 5, 10, 20`, which matches.
