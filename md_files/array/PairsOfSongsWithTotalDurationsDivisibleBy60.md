# Pairs of Songs With Total Durations Divisible by 60

You are given a list of songs where the `i`-th song has a duration of `time[i]` seconds. Return the number of pairs of songs `(i, j)` with `i < j` whose total duration in seconds is divisible by `60`.

## Algorithm Strategy (Remainder Counting)

1. Only `time[i] % 60` matters.
2. Keep an array `seen[60]` counting how many earlier songs have each remainder.
3. For each song with remainder `r`, the earlier songs that pair with it have remainder `(60 - r) % 60`; add that count to the answer.
4. Then record the remainder of the current song.

## Example walkthrough

- For `[30, 20, 150, 100, 40]`: remainders are 30, 20, 30, 40, 40.
- The pairs are (30, 30), (20, 40) and (20, 40), so the answer is 3.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass with constant work per song.
- **Space Complexity:** `O(1)` — A fixed array of 60 counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Check Every Pair

Test each pair directly.

```java
public static int brute(int[] a) { int c = 0; for (int i = 0; i < a.length; i++) for (int j = i + 1; j < a.length; j++) if ((a[i] + a[j]) % 60 == 0) c++; return c; }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Counting by Remainder (Optimized)

Look up how many earlier songs complement the current remainder. This is the version implemented in the repository.

```java
public static int counts(int[] a) { int[] s = new int[60]; int c = 0; for (int t : a) { int r = t % 60; c += s[(60 - r) % 60]; s[r]++; } return c; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Check Every Pair | O(n^2) | O(1) |
| 2 | Counting by Remainder (Optimized) | O(n) | O(1) |

## Source Code

### PairsOfSongsWithTotalDurationsDivisibleBy60.java

```java
import java.util.Arrays;

public class PairsOfSongsWithTotalDurationsDivisibleBy60 {

    public static void main(String[] args) {
        int[][] inputs = {{30, 20, 150, 100, 40}, {60, 60, 60}, {10}, {}, {1, 59, 61, 119}};
        for (int[] time : inputs) {
            System.out.println("Input  : time = " + Arrays.toString(time));
            System.out.println("Output : " + numPairsDivisibleBy60(time));
            System.out.println();
        }
    }

    public static int numPairsDivisibleBy60(int[] time) {
        if (time == null) {
            return 0;
        }
        int[] seen = new int[60];
        int pairs = 0;
        for (int t : time) {
            int remainder = t % 60;
            pairs += seen[(60 - remainder) % 60];
            seen[remainder]++;
        }
        return pairs;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: two durations sum to a multiple of 60 exactly when their remainders add up to 0 or 60. So each song only needs the number of earlier songs with the complementary remainder, and `(60 - r) % 60` handles the special case `r = 0`.

**Input:** `time = [30, 20, 150, 100, 40]`.

| Song | Remainder | Complement | Earlier matches | Total |
|------|-----------|------------|-----------------|-------|
| 30 | 30 | 30 | 0 | 0 |
| 20 | 20 | 40 | 0 | 0 |
| 150 | 30 | 30 | 1 | 1 |
| 100 | 40 | 20 | 1 | 2 |
| 40 | 40 | 20 | 1 | 3 |

**Output:** `3`.
