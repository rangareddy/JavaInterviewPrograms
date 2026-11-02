# Counting Bits

Given an integer `n`, return an array `ans` of length `n + 1` where `ans[i]` is the number of 1's in the binary representation of `i`.

## Algorithm Strategy (Dynamic Programming on the Shifted Number)

1. `ans[0] = 0`.
2. Shifting a number right by one (`i >> 1`) removes its lowest bit, so `i` has the same number of 1s as `i / 2`, plus one if the lowest bit of `i` is 1.
3. Therefore `ans[i] = ans[i >> 1] + (i & 1)`, and `i >> 1` is always smaller than `i`, so it has already been computed.
4. Fill the array for `i = 1 .. n`.

## Example walkthrough

- For `n = 5`: `ans[1] = ans[0] + 1 = 1`, `ans[2] = ans[1] + 0 = 1`, `ans[3] = ans[1] + 1 = 2`, `ans[4] = ans[2] + 0 = 1`, `ans[5] = ans[2] + 1 = 2`.
- Result: `[0, 1, 1, 2, 1, 2]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One constant-time step per number (no per-number bit loop).
- **Space Complexity:** `O(n)` — The output array.

## Source Code

### CountingBits.java

```java
import java.util.Arrays;

public class CountingBits {

    public static void main(String[] args) {
        int[] inputs = {2, 5, 0};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + Arrays.toString(countBits(n)));
            System.out.println();
        }
    }

    public static int[] countBits(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            ans[i] = ans[i >> 1] + (i & 1);
        }
        return ans;
    }
}
```
