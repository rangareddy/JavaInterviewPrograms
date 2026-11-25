# Maximum Swap

Given a non-negative integer `num`, you may swap two digits at most once. Return the maximum value you can get.

## Algorithm Strategy (Last Position of Each Digit)

1. Record the last index of every digit `0-9` in the number.
2. Scan the digits from the left. For the digit at position `i`, look for a larger digit `d` from 9 down to the current digit + 1 whose last index is after `i`.
3. On the first such digit, swap the two positions and stop: the leftmost improvable position gives the biggest gain, and using the last occurrence of the larger digit keeps the number as large as possible.
4. If no swap helps, the number is already the maximum.

## Example walkthrough

- For `2736`: at the first digit 2, the digit 7 occurs later, so swap them.
- Result: `7236`. For `9973` nothing larger exists to the right, so the answer stays `9973`.

## Complexity Analysis

- **Time Complexity:** `O(d)` — `d` is the number of digits; the inner search is at most 10 steps.
- **Space Complexity:** `O(1)` — A 10-element array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Swap

Swap every pair of digits and keep the maximum.

```java
public static int brute(int num) {
    char[] c = String.valueOf(num).toCharArray(); int best = num;
    for (int i = 0; i < c.length; i++) for (int j = i + 1; j < c.length; j++) { char t = c[i]; c[i] = c[j]; c[j] = t; best = Math.max(best, Integer.parseInt(new String(c))); t = c[i]; c[i] = c[j]; c[j] = t; }
    return best;
}
```

- **Time Complexity:** O(d^2 * d)
- **Space Complexity:** O(d)

### Approach 2: Last Position of Each Digit (Optimized)

Swap the first digit that has a bigger digit later with the last occurrence of that bigger digit. This is the version implemented in the repository.

```java
public static int lastIndex(int num) {
    char[] c = String.valueOf(num).toCharArray(); int[] last = new int[10]; for (int i = 0; i < c.length; i++) last[c[i] - '0'] = i;
    for (int i = 0; i < c.length; i++) for (int d = 9; d > c[i] - '0'; d--) if (last[d] > i) { char t = c[i]; c[i] = c[last[d]]; c[last[d]] = t; return Integer.parseInt(new String(c)); }
    return num;
}
```

- **Time Complexity:** O(d)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Swap | O(d^2 * d) | O(d) |
| 2 | Last Position of Each Digit (Optimized) | O(d) | O(1) |

## Source Code

### MaximumSwap.java

```java
public class MaximumSwap {

    public static void main(String[] args) {
        int[] inputs = {2736, 9973, 0, 98368, 1993};
        for (int num : inputs) {
            System.out.println("Input  : num = " + num);
            System.out.println("Output : " + maximumSwap(num));
            System.out.println();
        }
    }

    public static int maximumSwap(int num) {
        if (num < 10) {
            return num;
        }
        char[] digits = String.valueOf(num).toCharArray();
        int[] last = new int[10];
        for (int i = 0; i < digits.length; i++) {
            last[digits[i] - '0'] = i;
        }
        for (int i = 0; i < digits.length; i++) {
            for (int d = 9; d > digits[i] - '0'; d--) {
                if (last[d] > i) {
                    char temp = digits[i];
                    digits[i] = digits[last[d]];
                    digits[last[d]] = temp;
                    return Integer.parseInt(new String(digits));
                }
            }
        }
        return num;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the most significant digit that can be increased decides the result, so look for the leftmost position that has a larger digit somewhere to its right. Of all the larger digits, take the largest, and of its occurrences take the last one, because moving a small digit as far right as possible loses the least.

**Input:** `num = 2736`.

| Step | Action |
|------|--------|
| last index of digits | 2 at 0, 7 at 1, 3 at 2, 6 at 3 |
| position 0 (digit 2) | digit 7 has last index 1 > 0, so swap positions 0 and 1 |
| result | 7236 |

**Output:** `7236`.
