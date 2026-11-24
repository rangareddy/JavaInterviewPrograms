# Happy Number

Starting from a positive integer, repeatedly replace it with the sum of the squares of its digits. The number is happy if this process reaches 1; otherwise it loops forever in a cycle that does not contain 1. Return `true` if `n` is happy.

## Algorithm Strategy (Fast and Slow Pointers on the Sequence)

1. Define `next(n)` as the sum of the squares of the digits of `n`.
2. The sequence either reaches 1 (and stays there) or enters a cycle, so it can be treated like a linked list with a possible loop.
3. Advance `slow` one step and `fast` two steps per round until they meet or `fast` reaches 1.
4. If the meeting value is 1, the number is happy; otherwise it is in a cycle.

## Example walkthrough

- For 19: 19 -> 82 -> 68 -> 100 -> 1, so it is happy.
- For 2: 2 -> 4 -> 16 -> 37 -> 58 -> 89 -> 145 -> 42 -> 20 -> 4, a cycle without 1.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — The values stay small after the first step and the cycle is short.
- **Space Complexity:** `O(1)` — Two numbers, no set of seen values.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: HashSet of Seen Values

Stop with `false` as soon as a value repeats.

```java
public static boolean viaSet(int n) {
    java.util.Set<Integer> seen = new java.util.HashSet<>();
    while (n != 1 && seen.add(n)) n = sq(n);
    return n == 1;
}
private static int sq(int n) { int s = 0; while (n > 0) { int d = n % 10; s += d * d; n /= 10; } return s; }
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(log n)

### Approach 2: Known Cycle Check

Every unhappy number falls into the cycle that contains 4, so stop when 4 appears.

```java
public static boolean knownCycle(int n) {
    while (n != 1 && n != 4) n = sq2(n);
    return n == 1;
}
private static int sq2(int n) { int s = 0; while (n > 0) { int d = n % 10; s += d * d; n /= 10; } return s; }
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Approach 3: Fast and Slow Pointers (Optimized)

Floyd's cycle detection on the digit-square sequence. This is the version implemented in the repository.

```java
public static boolean floyd(int n) {
    int slow = n, fast = sq3(n);
    while (fast != 1 && slow != fast) { slow = sq3(slow); fast = sq3(sq3(fast)); }
    return fast == 1;
}
private static int sq3(int n) { int s = 0; while (n > 0) { int d = n % 10; s += d * d; n /= 10; } return s; }
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | HashSet of Seen Values | O(log n) | O(log n) |
| 2 | Known Cycle Check | O(log n) | O(1) |
| 3 | Fast and Slow Pointers (Optimized) | O(log n) | O(1) |

## Source Code

### HappyNumber.java

```java
public class HappyNumber {

    public static void main(String[] args) {
        int[] inputs = {19, 2, 1, 7, 4};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + isHappy(n));
            System.out.println();
        }
    }

    public static boolean isHappy(int n) {
        int slow = n;
        int fast = next(n);
        while (fast != 1 && slow != fast) {
            slow = next(slow);
            fast = next(next(fast));
        }
        return fast == 1;
    }

    private static int next(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: the digit-square sequence behaves like a linked list. If it ends at 1 the number is happy; otherwise it loops, and Floyd's two-speed walk detects the loop without storing the visited numbers.

**Input:** `n = 19`.

| Round | `slow` | `fast` |
|-------|--------|--------|
| start | 19 | 82 |
| 1 | 82 | 100 |
| 2 | 68 | 1 |

`fast` reaches 1, so the number is happy. The sequence is 19 -> 82 (1 + 81) -> 68 (64 + 4) -> 100 (36 + 64) -> 1 (1 + 0 + 0).

**Output:** `true`.
