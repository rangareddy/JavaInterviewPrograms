# Integer to Roman

Convert an integer in the range `[1, 3999]` to its Roman numeral. Roman numerals use the symbols `I, V, X, L, C, D, M` and the subtractive forms `IV, IX, XL, XC, CD, CM`.

## Algorithm Strategy (Greedy over Value-Symbol Pairs)

1. List the 13 values that can appear in a numeral, largest first, including the six subtractive pairs: `1000 M, 900 CM, 500 D, 400 CD, 100 C, 90 XC, 50 L, 40 XL, 10 X, 9 IX, 5 V, 4 IV, 1 I`.
2. For each value, while the remaining number is at least that value, append its symbol and subtract the value.
3. Putting the subtractive pairs in the table is what makes the greedy choice correct: no special handling of 4 or 9 is needed.

## Example walkthrough

- For `1994`: `M` leaves 994, `CM` leaves 94, `XC` leaves 4, `IV` leaves 0.
- Result: `MCMXCIV`.

## Complexity Analysis

- **Time Complexity:** `O(1)` — The input is bounded by 3999, so at most 15 symbols are appended and the table has 13 entries.
- **Space Complexity:** `O(1)` — The two constant tables and the output.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Digit by Digit Table Lookup

Split the number into thousands, hundreds, tens and ones and look each digit up in a table of its Roman form.

```java
public static String intToRomanTable(int num) {
    String[] thousands = {"", "M", "MM", "MMM"};
    String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    return thousands[num / 1000] + hundreds[num % 1000 / 100] + tens[num % 100 / 10] + ones[num % 10];
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 2: Greedy with Subtractive Pairs (Optimized)

Walk a 13-entry value table from the largest value down, appending each symbol while it still fits. This is the version implemented in the repository.

```java
private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

public static String intToRoman(int num) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < VALUES.length && num > 0; i++) {
        while (num >= VALUES[i]) {
            sb.append(SYMBOLS[i]);
            num -= VALUES[i];
        }
    }
    return sb.toString();
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Digit by Digit Table Lookup | O(1) | O(1) |
| 2 | Greedy with Subtractive Pairs (Optimized) | O(1) | O(1) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### IntegerToRoman.java

```java
public class IntegerToRoman {

    public static void main(String[] args) {
        int[] inputs = {3, 58, 1994, 3999, 4, 9};
        for (int num : inputs) {
            System.out.println("Input  : num = " + num);
            System.out.println("Output : " + intToRoman(num));
            System.out.println();
        }
    }

    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < VALUES.length && num > 0; i++) {
            while (num >= VALUES[i]) {
                sb.append(SYMBOLS[i]);
                num -= VALUES[i];
            }
        }
        return sb.toString();
    }
}
```
