# Fibonacci (Dynamic Programming)

Utility class to print the first `n` Fibonacci numbers using bottom-up dynamic programming.

## Algorithm Strategy (Bottom-Up Tabulation)

1. Define the sub-problem: `fib_dp[i]` is the `i`-th Fibonacci number.
2. Create a table of size `n` and fill the base cases: `fib_dp[0] = 0` and `fib_dp[1] = 1`.
3. Fill the table from left to right with the recurrence `fib_dp[i] = fib_dp[i - 1] + fib_dp[i - 2]`; every entry only depends on two entries that are already computed.
4. Print the table. Each sub-problem is solved once, unlike plain recursion which recomputes the same values exponentially many times.

## Example walkthrough for `n = 6`

- Base: `[0, 1, _, _, _, _]`
- i = 2..5: `1, 2, 3, 5`
- Table: `[0, 1, 1, 2, 3, 5]`

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each entry is computed once from two earlier entries.
- **Space Complexity:** `O(n)` — The table stores all `n` numbers.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Naive Recursion

Translate the definition `F(n) = F(n-1) + F(n-2)` directly. The same sub-problems are recomputed over and over, so the number of calls grows exponentially.

```java
public static long fibRecursive(int n) {
    if (n <= 1) return n;
    return fibRecursive(n - 1) + fibRecursive(n - 2);
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n) recursion stack

### Approach 2: Memoization (Top-Down DP)

Same recursion, but cache every computed value so each sub-problem is solved only once.

```java
public static long fibMemo(int n, long[] memo) {
    if (n <= 1) return n;
    if (memo[n] != 0) return memo[n];
    memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
    return memo[n];
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Tabulation (Bottom-Up DP)

Fill a table from the base cases upwards: `fib[i] = fib[i-1] + fib[i-2]`. This is the version in the `dp` package.

```java
public static long fibTable(int n) {
    if (n <= 1) return n;
    long[] fib = new long[n + 1];
    fib[1] = 1;
    for (int i = 2; i <= n; i++) fib[i] = fib[i - 1] + fib[i - 2];
    return fib[n];
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Iterative with Two Variables (Optimized for Printing)

Each number only needs the previous two, so keep just a rolling pair. This is the version in the `basic` package and is optimal when you must produce all of the first `n` numbers.

```java
public static long fibIterative(int n) {
    if (n <= 1) return n;
    long first = 0, second = 1;
    for (int i = 2; i <= n; i++) {
        long third = first + second;
        first = second;
        second = third;
    }
    return second;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 5: Fast Doubling (Optimized for a Single Number)

To get only the n-th number, use the identities `F(2k) = F(k) * (2*F(k+1) - F(k))` and `F(2k+1) = F(k)^2 + F(k+1)^2` to halve `n` each step. (Beyond `n = 92` the value no longer fits in a `long`; use `BigInteger`.)

```java
public static long fibFastDoubling(int n) {
    return fastDoubling(n)[0];
}

// returns {F(n), F(n + 1)}
private static long[] fastDoubling(int n) {
    if (n == 0) return new long[]{0, 1};
    long[] half = fastDoubling(n / 2);
    long a = half[0], b = half[1];
    long c = a * (2 * b - a);   // F(2k)
    long d = a * a + b * b;     // F(2k + 1)
    return (n % 2 == 0) ? new long[]{c, d} : new long[]{d, c + d};
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(log n) recursion stack

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Naive Recursion | O(2^n) | O(n) recursion stack |
| 2 | Memoization (Top-Down DP) | O(n) | O(n) |
| 3 | Tabulation (Bottom-Up DP) | O(n) | O(n) |
| 4 | Iterative with Two Variables (Optimized for Printing) | O(n) | O(1) |
| 5 | Fast Doubling (Optimized for a Single Number) | O(log n) | O(log n) recursion stack |

### Optimized Solution

The optimized solution is Approach 4 and 5 above. The version implemented in this repository is Approach 3 and 4.

See also the [iterative version and the comparison of all approaches](../basic/Fibonacci.md).

## Source Code

### Fibonacci.java

```java
public class Fibonacci {

    public static void main(String[] args) {
        int n = 10;

        System.out.println("Input  : n = " + n);
        System.out.print("Output : ");
        printFibonacciNumber(n);
        System.out.println();
    }

    /**
     * Prints the first {@code n} Fibonacci numbers using a table.
     *
     * @param n how many numbers to print
     */
    private static void printFibonacciNumber(int n) {
        // Guard Clause: nothing to print for non-positive n
        if (n <= 0) {
            return;
        }

        int[] fib_dp = new int[Math.max(n, 2)];
        fib_dp[0] = 0;
        fib_dp[1] = 1;
        for (int i = 2; i < n; i++) {
            fib_dp[i] = fib_dp[i - 1] + fib_dp[i - 2];
        }
        for (int i = 0; i < n; i++) {
            System.out.print(fib_dp[i] + " ");
        }
    }
}
```
