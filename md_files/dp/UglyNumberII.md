# Ugly Number II

An ugly number is a positive integer whose prime factors are limited to `2`, `3` and `5`. Given an integer `n`, return the `n`-th ugly number (the sequence starts `1, 2, 3, 4, 5, 6, 8, 9, 10, 12, ...`).

## Algorithm Strategy (Generate the Sequence in Order with Three Pointers)

1. Every ugly number after 1 is an earlier ugly number times 2, 3 or 5.
2. Keep three indexes `i2`, `i3`, `i5` into the list of ugly numbers found so far.
3. The next ugly number is the minimum of `ugly[i2] * 2`, `ugly[i3] * 3` and `ugly[i5] * 5`.
4. Advance every pointer that produced that minimum, so duplicates such as `6 = 2 * 3 = 3 * 2` appear only once.

## Example walkthrough

- The sequence grows as 1, 2, 3, 4, 5, 6, 8, 9, 10, 12.
- The 10th element is 12.
- Result: `12`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One constant-time step per number.
- **Space Complexity:** `O(n)` — The list of ugly numbers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Min-Heap with a Set

Pop the smallest number and push its multiples by 2, 3 and 5, skipping values already seen.

```java
public static int heap(int n) { java.util.PriorityQueue<Long> pq = new java.util.PriorityQueue<>(); java.util.Set<Long> seen = new java.util.HashSet<>(); pq.add(1L); seen.add(1L); long cur = 1; for (int i = 0; i < n; i++) { cur = pq.poll(); for (int f : new int[]{2, 3, 5}) { long nx = cur * f; if (seen.add(nx)) pq.add(nx); } } return (int) cur; }
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Three Pointers (Optimized)

Merge the three multiples sequences in order without any extra structure. This is the version implemented in the repository.

```java
public static int pointers(int n) { int[] u = new int[n]; u[0] = 1; int a = 0, b = 0, c = 0; for (int i = 1; i < n; i++) { int x = u[a] * 2, y = u[b] * 3, z = u[c] * 5; int m = Math.min(x, Math.min(y, z)); u[i] = m; if (m == x) a++; if (m == y) b++; if (m == z) c++; } return u[n - 1]; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Min-Heap with a Set | O(n log n) | O(n) |
| 2 | Three Pointers (Optimized) | O(n) | O(n) |

## Source Code

### UglyNumberII.java

```java
public class UglyNumberII {

    public static void main(String[] args) {
        int[] inputs = {10, 1, 7, 15, 1690};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + nthUglyNumber(n));
            System.out.println();
        }
    }

    public static int nthUglyNumber(int n) {
        if (n <= 0) {
            return 0;
        }
        int[] ugly = new int[n];
        ugly[0] = 1;
        int i2 = 0;
        int i3 = 0;
        int i5 = 0;
        for (int i = 1; i < n; i++) {
            int next2 = ugly[i2] * 2;
            int next3 = ugly[i3] * 3;
            int next5 = ugly[i5] * 5;
            int next = Math.min(next2, Math.min(next3, next5));
            ugly[i] = next;
            if (next == next2) {
                i2++;
            }
            if (next == next3) {
                i3++;
            }
            if (next == next5) {
                i5++;
            }
        }
        return ugly[n - 1];
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the ugly numbers are the union of three streams, `2 * ugly`, `3 * ugly` and `5 * ugly`. Each stream is sorted, so a three-way merge produces the sequence in order, and advancing all pointers that matched removes duplicates.

**Input:** `n = 10`.

| `i` | `i2, i3, i5` values | Candidates (x2, x3, x5) | Next |
|-----|---------------------|-------------------------|------|
| 1 | 1, 1, 1 | 2, 3, 5 | 2 |
| 2 | 2, 1, 1 | 4, 3, 5 | 3 |
| 3 | 2, 2, 1 | 4, 6, 5 | 4 |
| 4 | 3, 2, 1 | 6, 6, 5 | 5 |
| 5 | 3, 2, 2 | 6, 6, 10 | 6 (both pointers advance) |

Continuing, the sequence reaches 8, 9, 10, 12.

**Output:** `12`.
