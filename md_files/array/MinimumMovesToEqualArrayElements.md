# Minimum Moves to Equal Array Elements

Given an integer array `nums` of size `n`, return the minimum number of moves required to make all elements equal. In one move you can increment `n - 1` elements of the array by `1`.

## Algorithm Strategy (Incrementing n-1 Elements Equals Decrementing One)

1. Raising every element except one by 1 changes the differences between elements exactly as lowering that one element by 1 does.
2. So the task becomes: lower elements one at a time until all equal the minimum.
3. Each element `x` needs `x - min` such moves.
4. The answer is `sum(nums) - n * min`.

## Example walkthrough

- For `[1, 2, 3]`: the minimum is 1, so the moves are `(1 - 1) + (2 - 1) + (3 - 1) = 3`.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass for the sum and the minimum.
- **Space Complexity:** `O(1)` — Two numbers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Simulate the Moves

Repeatedly increment every element except the current maximum until all are equal. Correct, but far too slow when the values are large.

```java
public static int simulate(int[] in) { int[] a = in.clone(); int moves = 0; while (true) { int max = 0, min = 0; for (int i = 0; i < a.length; i++) { if (a[i] > a[max]) max = i; if (a[i] < a[min]) min = i; } if (a.length == 0 || a[max] == a[min]) return moves; for (int i = 0; i < a.length; i++) if (i != max) a[i]++; moves++; } }
```

- **Time Complexity:** O(n * range)
- **Space Complexity:** O(n)

### Approach 2: Sum Minus n Times Minimum (Optimized)

Use the decrement view and a closed form. This is the version implemented in the repository.

```java
public static int formula(int[] a) { if (a.length == 0) return 0; long min = Long.MAX_VALUE, sum = 0; for (int x : a) { min = Math.min(min, x); sum += x; } return (int) (sum - a.length * min); }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Simulate the Moves | O(n * range) | O(n) |
| 2 | Sum Minus n Times Minimum (Optimized) | O(n) | O(1) |

## Source Code

### MinimumMovesToEqualArrayElements.java

```java
import java.util.Arrays;

public class MinimumMovesToEqualArrayElements {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3}, {1, 1, 1}, {5}, {1, 1000000000}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + minMoves(nums));
            System.out.println();
        }
    }

    public static int minMoves(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long min = Long.MAX_VALUE;
        long sum = 0;
        for (int x : nums) {
            min = Math.min(min, x);
            sum += x;
        }
        return (int) (sum - nums.length * min);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: adding 1 to `n - 1` elements is equivalent, for the purpose of making them equal, to subtracting 1 from the remaining element. The final common value is therefore the minimum, and each element needs exactly `x - min` decrements.

**Input:** `nums = [1, 2, 3]`.

| Element | Distance to minimum (1) |
|---------|-------------------------|
| 1 | 0 |
| 2 | 1 |
| 3 | 2 |

Total: `0 + 1 + 2 = 3`, or `6 - 3 * 1 = 3`.

**Output:** `3`.
