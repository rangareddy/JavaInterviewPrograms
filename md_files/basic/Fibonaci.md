# Fibonacci

**Definition:** 

The Fibonacci numbers is a series of numbers in which each number ( Fibonacci number ) is the sum of the two preceding numbers. \

Let the first two numbers in the series be taken as 0 and 1. By adding 0 and 1, we get the third number as 1. \
Then by adding the second and the third number (i.e) 1 and 1, we get the fourth number as 2, and similarly, the process goes on.

**Example:** 

`0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, .... ∞`

**Formula:**

The sequence of Fibonacci numbers can be defined as:

`Fn = Fn-1 + Fn-2`

Where Fn is the nth term or number

Fn-1 is the (n-1)th term
Fn-2 is the (n-2)th term

## Algorithm Strategy (Rolling Pair)

1. The sequence is defined by `F(0) = 0`, `F(1) = 1` and `F(i) = F(i - 1) + F(i - 2)`.
2. Keep only the last two numbers in `first` and `second`, starting with `0` and `1`, and print them.
3. For each remaining position, compute `third = first + second` and print it.
4. Slide the pair forward: `first = second` and `second = third`.
5. Stop after `n` numbers have been produced.

## Example walkthrough for `n = 6`

- Start: `0 1`
- Then `0 + 1 = 1`, `1 + 1 = 2`, `1 + 2 = 3`, `2 + 3 = 5`
- Output: `0 1 1 2 3 5`

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single loop of `n` iterations.
- **Space Complexity:** `O(1)` — Only three integer variables are used.

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

## Source Code

### basic/Fibonacci.java

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
     * Prints the first {@code n} Fibonacci numbers.
     *
     * @param n how many numbers to print
     */
    private static void printFibonacciNumber(int n) {
        // Guard Clause: nothing to print for non-positive n
        if (n <= 0) {
            return;
        }
        if (n == 1) {
            System.out.print(0 + " ");
            return;
        }

        int first = 0, second = 1;
        System.out.print(first + " " + second + " ");
        int third;
        for (int i = 2; i < n; i++) {
            third = first + second;
            System.out.print(third + " ");
            first = second;
            second = third;
        }
    }
}
```

### dp/Fibonacci.java

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
