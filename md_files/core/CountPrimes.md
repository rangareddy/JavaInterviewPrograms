# Count Primes

Given an integer `n`, return the number of prime numbers that are strictly less than `n`.

## Algorithm Strategy (Sieve of Eratosthenes)

1. Create a boolean array `composite[0..n-1]`, initially all false.
2. For each `i` from 2 while `i * i < n`, if `i` is still prime, mark `i * i, i * i + i, i * i + 2i, ...` as composite.
3. Starting at `i * i` is enough because smaller multiples were already marked by smaller primes.
4. Count the indices from 2 to `n - 1` that are not marked.

## Example walkthrough

- For `n = 10`: 2 marks 4, 6, 8; 3 marks 9.
- The primes below 10 are 2, 3, 5, 7: four of them.

## Complexity Analysis

- **Time Complexity:** `O(n log log n)` — The classic sieve bound.
- **Space Complexity:** `O(n)` — The boolean array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Trial Division

Test every number below `n` by dividing by all values up to its square root.

```java
public static int trial(int n) {
    int c = 0;
    for (int i = 2; i < n; i++) { boolean p = true; for (int d = 2; (long) d * d <= i; d++) if (i % d == 0) { p = false; break; } if (p) c++; }
    return c;
}
```

- **Time Complexity:** O(n sqrt(n))
- **Space Complexity:** O(1)

### Approach 2: Sieve of Eratosthenes (Optimized)

Cross out the multiples of each prime. This is the version implemented in the repository.

```java
public static int sieve(int n) {
    if (n < 3) return 0;
    boolean[] comp = new boolean[n]; int c = 0;
    for (int i = 2; i < n; i++) if (!comp[i]) { c++; if ((long) i * i < n) for (int j = i * i; j < n; j += i) comp[j] = true; }
    return c;
}
```

- **Time Complexity:** O(n log log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Trial Division | O(n sqrt(n)) | O(1) |
| 2 | Sieve of Eratosthenes (Optimized) | O(n log log n) | O(n) |

## Source Code

### CountPrimes.java

```java
public class CountPrimes {

    public static void main(String[] args) {
        int[] inputs = {10, 0, 1, 2, 3, 100, 1000};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + countPrimes(n));
            System.out.println();
        }
    }

    public static int countPrimes(int n) {
        if (n < 3) {
            return 0;
        }
        boolean[] composite = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!composite[i]) {
                count++;
                if ((long) i * i < n) {
                    for (int j = i * i; j < n; j += i) {
                        composite[j] = true;
                    }
                }
            }
        }
        return count;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: instead of testing each number, cross out the multiples of every prime found. What remains uncrossed is prime.

**Input:** `n = 10`.

| `i` | Prime? | Marks as composite | Count so far |
|-----|--------|---------------------|--------------|
| 2 | yes | 4, 6, 8 | 1 |
| 3 | yes | 9 | 2 |
| 4 | no | | 2 |
| 5 | yes | (25 is out of range) | 3 |
| 7 | yes | | 4 |

**Output:** `4`.
