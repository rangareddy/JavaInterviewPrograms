# Single Number II

In an array where every element appears exactly three times except for one element that appears once, find the element that appears once. Use linear time and constant extra space.

## Algorithm Strategy (Two-Bit State Machine per Bit Position)

1. For any bit position, the number of ones among the elements is a multiple of 3 for the values that appear three times, plus 0 or 1 from the single value. So that count modulo 3 is the single number's bit.
2. Instead of 32 counters, keep two integers `ones` and `twos` that together hold, for every bit position at once, a two-bit counter that cycles `00 -> 01 -> 10 -> 00`.
3. For each number `x`: `ones = (ones ^ x) & ~twos` and `twos = (twos ^ x) & ~ones`. A bit of `x` moves the counter of that position one step; after three hits it is back to `00`.
4. After all elements, positions that were hit once are `01`, so `ones` is the single number (`twos` is 0 at those positions).

## Example walkthrough

- For `[2, 2, 3, 2]`: after the first 2, `ones = 2` (bit 1 seen once). After the second 2, `ones = 0, twos = 2` (bit 1 seen twice).
- The 3 hits bits 0 and 1: bit 1 reaches its third hit and clears, bit 0 is seen once, so `ones = 1, twos = 0`. The last 2 sets bit 1 again: `ones = 3`. Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass with a few bitwise operations per element.
- **Space Complexity:** `O(1)` — Two integers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: HashMap of Counts

Count the occurrences of every value and return the one with count 1.

```java
public static int singleNumberMap(int[] nums) {
    Map<Integer, Integer> counts = new HashMap<>();
    for (int x : nums) counts.merge(x, 1, Integer::sum);
    for (Map.Entry<Integer, Integer> e : counts.entrySet()) {
        if (e.getValue() == 1) return e.getKey();
    }
    throw new IllegalArgumentException("no single number");
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Count Each Bit Modulo 3

For each of the 32 bit positions count how many numbers have the bit set; if the count is not a multiple of 3 the single number has that bit.

```java
public static int singleNumberBits(int[] nums) {
    int result = 0;
    for (int bit = 0; bit < 32; bit++) {
        int count = 0;
        for (int x : nums) count += (x >>> bit) & 1;
        if (count % 3 != 0) result |= 1 << bit;
    }
    return result;
}
```

- **Time Complexity:** O(32 * n)
- **Space Complexity:** O(1)

### Approach 3: Two-Integer State Machine (Optimized)

Keep a two-bit counter modulo 3 for all 32 positions at once in `ones` and `twos`. This is the version implemented in the repository.

```java
public static int singleNumber(int[] nums) {
    int ones = 0;
    int twos = 0;
    for (int x : nums) {
        ones = (ones ^ x) & ~twos;
        twos = (twos ^ x) & ~ones;
    }
    return ones;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | HashMap of Counts | O(n) | O(n) |
| 2 | Count Each Bit Modulo 3 | O(32 * n) | O(1) |
| 3 | Two-Integer State Machine (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### SingleNumberII.java

```java
import java.util.Arrays;

public class SingleNumberII {

    public static void main(String[] args) {
        int[][] inputs = {{2, 2, 3, 2}, {0, 1, 0, 1, 0, 1, 99}, {-2, -2, 1, 1, 4, 1, 4, 4, -4, -2}, {7}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + singleNumber(nums));
            System.out.println();
        }
    }

    public static int singleNumber(int[] nums) {
        int ones = 0;
        int twos = 0;
        for (int x : nums) {
            ones = (ones ^ x) & ~twos;
            twos = (twos ^ x) & ~ones;
        }
        return ones;
    }
}
```
