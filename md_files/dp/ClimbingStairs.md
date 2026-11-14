# Climbing Stairs

You are climbing a staircase with `n` steps. Each time you can climb 1 or 2 steps. In how many distinct ways can you reach the top?

## Algorithm Strategy (Dynamic Programming (Fibonacci Recurrence))

1. To stand on step `i`, you came either from step `i - 1` (one step) or from step `i - 2` (two steps), so `ways(i) = ways(i - 1) + ways(i - 2)`.
2. Base cases: there is 1 way to stay at the ground (`ways(0) = 1`) and 1 way to reach step 1.
3. Only the last two values are needed, so keep two variables instead of a table.
4. Roll them forward `n` times; the last value is the answer.

## Example walkthrough

- For `n = 5`: ways are 1, 1, 2, 3, 5, 8 for steps 0 to 5.
- Result: `8`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single loop.
- **Space Complexity:** `O(1)` — Two variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Recursion

The number of ways to reach step `n` is the ways to reach `n - 1` plus the ways to reach `n - 2`. Directly recursive, but the same sub-problems are recomputed exponentially often.

```java
public static int climbStairsRecursive(int n) {
    if (n <= 1) return 1;
    return climbStairsRecursive(n - 1) + climbStairsRecursive(n - 2);
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n) recursion stack

### Approach 2: Memoization (Top-Down DP)

Cache every result so each step is computed once.

```java
public static int climbStairsMemo(int n) {
    return memo(n, new int[n + 2]);
}

private static int memo(int n, int[] cache) {
    if (n <= 1) return 1;
    if (cache[n] != 0) return cache[n];
    return cache[n] = memo(n - 1, cache) + memo(n - 2, cache);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Tabulation (Bottom-Up DP)

Fill an array from the base cases upwards.

```java
public static int climbStairsTable(int n) {
    int[] ways = new int[n + 2];
    ways[0] = 1;
    ways[1] = 1;
    for (int i = 2; i <= n; i++) ways[i] = ways[i - 1] + ways[i - 2];
    return ways[n];
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Two Variables (Optimized)

Only the last two values are ever needed, so keep them in two variables. This is the version implemented in the repository.

```java
public static int climbStairs(int n) {
    if (n <= 1) return 1;
    int previous = 1, current = 1;
    for (int i = 2; i <= n; i++) {
        int next = previous + current;
        previous = current;
        current = next;
    }
    return current;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Recursion | O(2^n) | O(n) recursion stack |
| 2 | Memoization (Top-Down DP) | O(n) | O(n) |
| 3 | Tabulation (Bottom-Up DP) | O(n) | O(n) |
| 4 | Two Variables (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: the number of ways to reach step `i` is the number of ways to reach step `i - 1` (then take one step) plus the ways to reach step `i - 2` (then take two steps). Only those two previous values are ever needed, so two variables replace the whole table.

**Input:** `n = 5`. Start with `previous = 1` (ways to stand on step 0) and `current = 1` (ways to reach step 1).

| `i` (step) | `next = previous + current` | `previous` becomes | `current` becomes | Ways to reach step `i` |
|------------|-----------------------------|--------------------|-------------------|------------------------|
| start | | 1 | 1 | step 0: 1, step 1: 1 |
| 2 | `1 + 1 = 2` | 1 | 2 | 2 |
| 3 | `1 + 2 = 3` | 2 | 3 | 3 |
| 4 | `2 + 3 = 5` | 3 | 5 | 5 |
| 5 | `3 + 5 = 8` | 5 | 8 | 8 |

The loop ends after `i = 5` and the method returns `current = 8`.

```text
ways:        step 0   step 1   step 2   step 3   step 4   step 5
             [ 1        1        2        3        5        8 ]

i = 2:       previous current  -> next = 2
i = 3:                previous current  -> next = 3
i = 4:                         previous current  -> next = 5
i = 5:                                  previous current  -> next = 8
```

The window of two values slides one step to the right each time, which is why the memory use is `O(1)` instead of an array of `n + 1` values.

**Edge cases.** `n = 0` and `n = 1` return `1` immediately (there is one way to do nothing and one way to climb a single step), so the loop only runs for `n >= 2`.

## Source Code

### ClimbingStairs.java

```java
public class ClimbingStairs {

    public static void main(String[] args) {
        int[] inputs = {2, 3, 5, 1};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + climbStairs(n));
            System.out.println();
        }
    }

    public static int climbStairs(int n) {
        if (n <= 1) {
            return 1;
        }

        int previous = 1;       // ways to reach step i - 2
        int current = 1;        // ways to reach step i - 1
        for (int i = 2; i <= n; i++) {
            int next = previous + current;
            previous = current;
            current = next;
        }
        return current;
    }
}
```
