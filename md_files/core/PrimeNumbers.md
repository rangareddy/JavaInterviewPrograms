# Prime Numbers

Check whether a number is prime (greater than 1 and divisible only by 1 and itself) and list all prime numbers up to a limit `n`.

## Algorithm Strategy (Trial Division up to the Square Root and the Sieve of Eratosthenes)

1. Primality test: numbers below 2 are not prime; 2 and 3 are prime; even numbers and multiples of 3 are not.
2. If `n` has a divisor, it has one that is at most `sqrt(n)`, so test only the candidates `5, 7, 11, 13, ...` (numbers of the form `6k +- 1`) while `i * i <= n`.
3. Listing primes: create a boolean array `composite[0..n]` and, for each `i` from 2 while `i * i <= n`, if `i` is still unmarked, mark its multiples `i * i, i * i + i, ...` as composite.
4. Every index from 2 that is still unmarked is a prime.

## Example walkthrough

- `isPrime(29)`: not divisible by 2 or 3; `5 * 5 = 25 <= 29` and `29 % 5 != 0`, `7 * 7 > 29`: prime.
- The sieve for `n = 30` marks the multiples of 2, 3 and 5 and leaves `2, 3, 5, 7, 11, 13, 17, 19, 23, 29`.

## Complexity Analysis

- **Time Complexity:** `O(sqrt n) for the test, O(n log log n) for the sieve` — The test checks about sqrt(n)/3 candidates; the sieve touches every composite a few times.
- **Space Complexity:** `O(1) for the test, O(n) for the sieve` — The sieve needs one flag per number.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Divide by Every Number

Try every divisor from 2 to `n - 1`.

```java
public static boolean isPrimeBrute(int n) {
    if (n < 2) return false;
    for (int i = 2; i < n; i++) if (n % i == 0) return false;
    return true;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Divide up to the Square Root

A composite number has a divisor of at most `sqrt(n)`.

```java
public static boolean isPrimeSqrt(int n) {
    if (n < 2) return false;
    for (int i = 2; (long) i * i <= n; i++) if (n % i == 0) return false;
    return true;
}
```

- **Time Complexity:** O(sqrt n)
- **Space Complexity:** O(1)

### Approach 3: 6k +- 1 Trial Division (Optimized test)

After 2 and 3, every prime is of the form `6k +- 1`, so only a third of the candidates are tested. This is the primality test implemented in the repository.

```java
public static boolean isPrime(int n) {
    if (n < 2) return false;
    if (n < 4) return true;
    if (n % 2 == 0 || n % 3 == 0) return false;
    for (long i = 5; i * i <= n; i += 6)
        if (n % i == 0 || n % (i + 2) == 0) return false;
    return true;
}
```

- **Time Complexity:** O(sqrt n)
- **Space Complexity:** O(1)

### Approach 4: Filter the Range with the Test

To list primes, test every number up to `n` one by one.

```java
public static List<Integer> primesUpToTrial(int n) {
    List<Integer> primes = new ArrayList<>();
    for (int i = 2; i <= n; i++) if (isPrimeSqrt(i)) primes.add(i);
    return primes;
}
```

- **Time Complexity:** O(n sqrt n)
- **Space Complexity:** O(1) extra

### Approach 5: Sieve of Eratosthenes (Optimized list)

Cross out multiples of each prime. This is the list version implemented in the repository.

```java
public static List<Integer> primesUpTo(int n) {
    boolean[] composite = new boolean[n + 1];
    for (int i = 2; (long) i * i <= n; i++)
        if (!composite[i])
            for (int multiple = i * i; multiple <= n; multiple += i) composite[multiple] = true;
    List<Integer> primes = new ArrayList<>();
    for (int i = 2; i <= n; i++) if (!composite[i]) primes.add(i);
    return primes;
}
```

- **Time Complexity:** O(n log log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Divide by Every Number | O(n) | O(1) |
| 2 | Divide up to the Square Root | O(sqrt n) | O(1) |
| 3 | 6k +- 1 Trial Division (Optimized test) | O(sqrt n) | O(1) |
| 4 | Filter the Range with the Test | O(n sqrt n) | O(1) extra |
| 5 | Sieve of Eratosthenes (Optimized list) | O(n log log n) | O(n) |

## Source Code

### PrimeNumbers.java

```java
import java.util.ArrayList;
import java.util.List;

public class PrimeNumbers {

    public static void main(String[] args) {
        int[] numbers = {1, 2, 17, 25, 29, 97, 100};
        for (int number : numbers) {
            System.out.println("Input  : n = " + number);
            System.out.println("Output : " + (isPrime(number) ? "Prime" : "Not Prime"));
            System.out.println();
        }

        System.out.println("Input  : primes up to 30");
        System.out.println("Output : " + primesUpTo(30));
    }

    public static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n < 4) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    public static List<Integer> primesUpTo(int n) {
        boolean[] composite = new boolean[n + 1];
        for (int i = 2; (long) i * i <= n; i++) {
            if (!composite[i]) {
                for (int multiple = i * i; multiple <= n; multiple += i) {
                    composite[multiple] = true;
                }
            }
        }

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (!composite[i]) {
                primes.add(i);
            }
        }
        return primes;
    }
}
```

## Solution

The optimized solution is Approach 3 and 5 above. The version implemented in this repository is Approach 3 and 5.

The idea has two parts, matching the two methods in the class.

**Single test, `isPrime(n)` (6k +- 1 trial division).** Every prime above 3 has the form `6k - 1` or `6k + 1`, because the other residues mod 6 are divisible by 2 or 3. So the code rejects `n < 2`, accepts 2 and 3, rejects multiples of 2 and 3, and then only tries divisors `i` and `i + 2` for `i = 5, 11, 17, ...` (step 6), stopping when `i * i > n`, since a composite number has a factor no larger than its square root.

**Input:** `n = 97`.

| Step | `i` | `i * i <= 97`? | `97 % i` | `97 % (i + 2)` | Action |
|------|-----|---------------|----------|----------------|--------|
| 0 | | | | | `97 >= 4`, `97 % 2 = 1`, `97 % 3 = 1`: continue |
| 1 | 5 | 25, yes | 2 | `97 % 7 = 6` | no divisor, `i += 6` |
| 2 | 11 | 121, no | | | loop ends |

No divisor was found, so `isPrime(97)` returns `true`. For `n = 25`, the first step finds `25 % 5 == 0` and returns `false`.

**All primes up to n, `primesUpTo(n)` (Sieve of Eratosthenes).** Keep a `composite[]` flag per number. For each `i` from 2 while `i * i <= n` that is not yet marked, mark its multiples starting at `i * i` (smaller multiples were already marked by smaller primes). The unmarked numbers from 2 up are the primes.

**Input:** `n = 30`.

| Step | `i` | Marked as composite |
|------|-----|---------------------|
| 1 | 2 (prime) | 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30 |
| 2 | 3 (prime) | 9, 15, 21, 27 (the others are already marked) |
| 3 | 4 | already composite, skipped |
| 4 | 5 (prime) | 25 (30 is already marked) |
| 5 | 6 | `36 > 30`, loop ends |

Collecting the unmarked numbers from 2 to 30 gives `[2, 3, 5, 7, 11, 13, 17, 19, 23, 29]`. No diagram is added because the tables show the arithmetic completely.

**Edge cases.** `isPrime` returns `false` for `1`, `0` and negatives, and `true` for `2` and `3`. The loop variable is a `long`, so `i * i` cannot overflow for large `n`. `primesUpTo(n)` with `n < 2` returns an empty list (no number reaches the collecting loop's range; an `n` below `-1` would throw `NegativeArraySizeException`).

**Result:** `isPrime` prints Prime for 2, 17, 29, 97 and Not Prime for 1, 25, 100; `primesUpTo(30)` returns `[2, 3, 5, 7, 11, 13, 17, 19, 23, 29]`, matching the class output.
