# Plus One

A non-negative integer is stored as an array of its decimal digits, most significant digit first. Add one to it and return the resulting array.

## Algorithm Strategy (Right-to-Left Carry)

1. Walk the array from the last digit.
2. If the digit is less than 9, increment it and return the array: no carry is left.
3. If it is 9, set it to 0 and carry on to the next digit to the left.
4. If every digit was 9, the number is `10...0`: return a new array with a leading 1.

## Example walkthrough

- For `[1, 2, 3]` the last digit becomes 4.
- For `[9, 9]` both digits become 0 and a leading 1 is added, giving `[1, 0, 0]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — At most one pass.
- **Space Complexity:** `O(1)` — In place, except when a new array is needed for the all-nines case.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BigInteger

Convert to a `BigInteger`, add one and convert back.

```java
public static int[] viaBigInteger(int[] d) {
    StringBuilder sb = new StringBuilder(); for (int x : d) sb.append(x);
    String s = new java.math.BigInteger(sb.toString()).add(java.math.BigInteger.ONE).toString();
    int[] out = new int[s.length()]; for (int i = 0; i < out.length; i++) out[i] = s.charAt(i) - '0';
    return out;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Carry from the Right (Optimized)

Stop at the first digit below 9. This is the version implemented in the repository.

```java
public static int[] carry(int[] d) {
    d = d.clone();
    for (int i = d.length - 1; i >= 0; i--) { if (d[i] < 9) { d[i]++; return d; } d[i] = 0; }
    int[] r = new int[d.length + 1]; r[0] = 1; return r;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | BigInteger | O(n) | O(n) |
| 2 | Carry from the Right (Optimized) | O(n) | O(1) |

## Source Code

### PlusOne.java

```java
import java.util.Arrays;

public class PlusOne {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3}, {4, 3, 2, 1}, {9}, {9, 9}, {1, 9}};
        for (int[] digits : inputs) {
            System.out.println("Input  : digits = " + Arrays.toString(digits));
            System.out.println("Output : " + Arrays.toString(plusOne(digits.clone())));
            System.out.println();
        }
    }

    public static int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) {
            return new int[]{1};
        }
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }
        int[] result = new int[digits.length + 1];
        result[0] = 1;
        return result;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: adding one only disturbs the trailing nines. The first digit from the right that is below 9 absorbs the carry, and everything after it has already become 0.

**Input:** `digits = [1, 9, 9]`.

| `i` | Digit | Action | Array |
|-----|-------|--------|-------|
| 2 | 9 | becomes 0, carry on | [1, 9, 0] |
| 1 | 9 | becomes 0, carry on | [1, 0, 0] |
| 0 | 1 | increment, done | [2, 0, 0] |

**Output:** `[2, 0, 0]`.
