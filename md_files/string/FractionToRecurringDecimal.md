# Fraction to Recurring Decimal

Given two integers representing the `numerator` and `denominator` of a fraction, return the fraction in string format. If the fractional part is repeating, enclose the repeating part in parentheses.

## Algorithm Strategy (Long Division Remembering Remainders)

1. Handle the sign (negative if exactly one of the numerator and the denominator is negative) and work with `long` absolute values to avoid overflow.
2. Write the integer part `numerator / denominator`; if the remainder is 0, you are done.
3. Otherwise add `.` and do long division: multiply the remainder by 10, append the digit `remainder / denominator`, and take the new remainder.
4. Store each remainder with the position where its digit was written; when a remainder repeats, insert `(` at that position and append `)`.

## Example walkthrough

- For `2 / 3`: remainder 2 -> digit 6 remainder 2 again. The remainder 2 was seen at position 0 of the fraction, so the 6 repeats.
- Result: `0.(6)`.

## Complexity Analysis

- **Time Complexity:** `O(d)` — `d` is the length of the fraction part, bounded by the denominator.
- **Space Complexity:** `O(d)` — The remainder map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BigDecimal with Cycle Detection by Length

Compute digits up to a fixed length and look for a repeating period. Fragile: the period can be as long as the denominator.

```java
public static String digits(int n, int d) {
    if (n == 0) return "0"; StringBuilder sb = new StringBuilder(); if ((n < 0) ^ (d < 0)) sb.append('-'); long a = Math.abs((long) n), b = Math.abs((long) d); sb.append(a / b); long rem = a % b; if (rem == 0) return sb.toString(); sb.append('.');
    java.util.List<Long> rems = new java.util.ArrayList<>(); java.util.List<Integer> digs = new java.util.ArrayList<>(); while (rem != 0 && !rems.contains(rem)) { rems.add(rem); rem *= 10; digs.add((int) (rem / b)); rem %= b; }
    int start = rem == 0 ? -1 : rems.indexOf(rem); for (int i = 0; i < digs.size(); i++) { if (i == start) sb.append('('); sb.append(digs.get(i)); } if (start >= 0) sb.append(')'); return sb.toString();
}
```

- **Time Complexity:** O(d^2)
- **Space Complexity:** O(d)

### Approach 2: Long Division with a Remainder Map (Optimized)

Remember the output position of every remainder. This is the version implemented in the repository.

```java
public static String map(int n, int d) {
    if (n == 0) return "0"; StringBuilder sb = new StringBuilder(); if ((n < 0) ^ (d < 0)) sb.append('-'); long a = Math.abs((long) n), b = Math.abs((long) d); sb.append(a / b); long rem = a % b; if (rem == 0) return sb.toString(); sb.append('.');
    java.util.Map<Long, Integer> seen = new java.util.HashMap<>(); while (rem != 0) { Integer p = seen.get(rem); if (p != null) { sb.insert((int) p, '('); sb.append(')'); break; } seen.put(rem, sb.length()); rem *= 10; sb.append(rem / b); rem %= b; } return sb.toString();
}
```

- **Time Complexity:** O(d)
- **Space Complexity:** O(d)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | BigDecimal with Cycle Detection by Length | O(d^2) | O(d) |
| 2 | Long Division with a Remainder Map (Optimized) | O(d) | O(d) |

## Source Code

### FractionToRecurringDecimal.java

```java
import java.util.HashMap;
import java.util.Map;

public class FractionToRecurringDecimal {

    public static void main(String[] args) {
        long[][] inputs = {{2, 3}, {1, 2}, {2, 1}, {4, 333}, {-50, 8}, {1, -3}, {-2147483648L, -1}};
        for (long[] in : inputs) {
            System.out.println("Input  : numerator = " + in[0] + ", denominator = " + in[1]);
            System.out.println("Output : " + fractionToDecimal((int) in[0], (int) in[1]));
            System.out.println();
        }
    }

    public static String fractionToDecimal(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("denominator must not be zero");
        }
        if (numerator == 0) {
            return "0";
        }
        StringBuilder result = new StringBuilder();
        if ((numerator < 0) ^ (denominator < 0)) {
            result.append('-');
        }
        long num = Math.abs((long) numerator);
        long den = Math.abs((long) denominator);
        result.append(num / den);
        long remainder = num % den;
        if (remainder == 0) {
            return result.toString();
        }
        result.append('.');
        Map<Long, Integer> seen = new HashMap<>();
        while (remainder != 0) {
            Integer position = seen.get(remainder);
            if (position != null) {
                result.insert(position.intValue(), '(');
                result.append(')');
                break;
            }
            seen.put(remainder, result.length());
            remainder *= 10;
            result.append(remainder / den);
            remainder %= den;
        }
        return result.toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: long division is a deterministic process, with the remainder as its whole state. When a remainder shows up for the second time, the digits after it will repeat forever. Remembering at which output position each remainder first produced a digit tells exactly where the repeating part starts.

**Input:** `numerator = 2`, `denominator = 3`.

| Step | Remainder | Digit | Note |
|------|-----------|-------|------|
| integer part | 2 | 0 | writes `0.` |
| 1 | 2 -> 20 | 6 | remainder 2 stored at position 2 |
| 2 | 2 again | | seen at position 2: insert `(` there and `)` at the end |

**Output:** `0.(6)`.
