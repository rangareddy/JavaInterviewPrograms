# Roman to Integer

Convert a Roman numeral (symbols `I=1, V=5, X=10, L=50, C=100, D=500, M=1000`) to an integer. A smaller symbol written before a larger one is subtracted, for example `IV = 4` and `IX = 9`. The input is a valid numeral in the range 1 to 3999.

## Algorithm Strategy (Left-to-Right Scan with Subtraction Rule)

1. Store the value of each symbol in a lookup (an array indexed by the character).
2. Scan the string from left to right.
3. If the current symbol is smaller than the next one, subtract its value (`IV`: subtract 1, then add 5); otherwise add it.
4. The running total after the last symbol is the answer (the last symbol is always added).

## Example walkthrough

- `"MCMXCIV"`: `M +1000`, `C -100`, `M +1000`, `X -10`, `C +100`, `I -1`, `V +5` gives `1994`.
- `"LVIII"` has no subtraction: `50 + 5 + 1 + 1 + 1 = 58`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over the numeral.
- **Space Complexity:** `O(1)` — A fixed lookup table.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Greedy Subtraction of Known Pairs

Replace the six subtractive pairs (`IV`, `IX`, `XL`, `XC`, `CD`, `CM`) and then add the single symbols.

```java
public static int romanToIntPairs(String roman) {
    String[] pairs = {"CM", "CD", "XC", "XL", "IX", "IV"};
    int[] values = {900, 400, 90, 40, 9, 4};
    int total = 0;
    for (int i = 0; i < pairs.length; i++) {
        while (roman.contains(pairs[i])) {
            roman = roman.replaceFirst(pairs[i], "");
            total += values[i];
        }
    }
    for (char c : roman.toCharArray()) total += value(c);
    return total;
}

private static int value(char c) {
    return "IVXLCDM".indexOf(c) < 0 ? 0 : new int[]{1, 5, 10, 50, 100, 500, 1000}["IVXLCDM".indexOf(c)];
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Right-to-Left Scan

Walk from the end: add a symbol unless it is smaller than the one to its right.

```java
public static int romanToIntRightToLeft(String roman) {
    int total = 0, previous = 0;
    for (int i = roman.length() - 1; i >= 0; i--) {
        int current = value(roman.charAt(i));
        total += current < previous ? -current : current;
        previous = current;
    }
    return total;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: HashMap Lookup

Store the symbol values in a `Map` and apply the left-to-right rule.

```java
public static int romanToIntMap(String roman) {
    Map<Character, Integer> values = new HashMap<>();
    values.put('I', 1); values.put('V', 5); values.put('X', 10); values.put('L', 50);
    values.put('C', 100); values.put('D', 500); values.put('M', 1000);
    int total = 0;
    for (int i = 0; i < roman.length(); i++) {
        int current = values.get(roman.charAt(i));
        if (i + 1 < roman.length() && current < values.get(roman.charAt(i + 1))) total -= current;
        else total += current;
    }
    return total;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: Left-to-Right Scan with a Switch (Optimized)

Subtract a symbol when the next one is larger, otherwise add it. This is the version implemented in the repository.

```java
public static int romanToInt(String roman) {
    int total = 0;
    for (int i = 0; i < roman.length(); i++) {
        int value = valueOf(roman.charAt(i));
        if (i + 1 < roman.length() && value < valueOf(roman.charAt(i + 1))) total -= value;
        else total += value;
    }
    return total;
}

private static int valueOf(char symbol) {
    switch (symbol) {
        case 'I': return 1;
        case 'V': return 5;
        case 'X': return 10;
        case 'L': return 50;
        case 'C': return 100;
        case 'D': return 500;
        case 'M': return 1000;
        default: throw new IllegalArgumentException("Not a Roman symbol: " + symbol);
    }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Greedy Subtraction of Known Pairs | O(n) | O(n) |
| 2 | Right-to-Left Scan | O(n) | O(1) |
| 3 | HashMap Lookup | O(n) | O(1) |
| 4 | Left-to-Right Scan with a Switch (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: read the symbols left to right and add each value, except when a symbol is smaller than the one right after it (as in `IV` or `CM`): then it is subtracted instead. One pass, one running `total`, no table of two-letter combinations.

**Input:** `roman = "MCMXCIV"`.

| Step | `i` | Symbol | Value | Next symbol | Smaller than next? | `total` |
|------|-----|--------|-------|-------------|--------------------|---------|
| 1 | 0 | M | 1000 | C (100) | no, add | 1000 |
| 2 | 1 | C | 100 | M (1000) | yes, subtract | 900 |
| 3 | 2 | M | 1000 | X (10) | no, add | 1900 |
| 4 | 3 | X | 10 | C (100) | yes, subtract | 1890 |
| 5 | 4 | C | 100 | I (1) | no, add | 1990 |
| 6 | 5 | I | 1 | V (5) | yes, subtract | 1989 |
| 7 | 6 | V | 5 | none | last symbol, add | 1994 |

```text
M   CM   XC   IV
1000 + (1000-100) + (100-10) + (5-1) = 1994
```

**Edge cases.** The last symbol has no neighbour (`i + 1 < length` is false), so it is always added: `"III"` gives 3, `"IV"` gives 4. An invalid symbol makes `valueOf` throw `IllegalArgumentException`; the empty string returns `0`; a `null` string would throw `NullPointerException`. The class does not check that the numeral is well formed (`"IIII"` returns 4).

**Result:** `1994`, matching the class output.

## Source Code

### RomanToInteger.java

```java
public class RomanToInteger {

    public static void main(String[] args) {
        String[] inputs = {"III", "IV", "IX", "LVIII", "MCMXCIV", "MMMCMXCIX"};
        for (String roman : inputs) {
            System.out.println("Input  : roman = \"" + roman + "\"");
            System.out.println("Output : " + romanToInt(roman));
            System.out.println();
        }
    }

    public static int romanToInt(String roman) {
        int total = 0;
        for (int i = 0; i < roman.length(); i++) {
            int value = valueOf(roman.charAt(i));
            if (i + 1 < roman.length() && value < valueOf(roman.charAt(i + 1))) {
                total -= value;
            } else {
                total += value;
            }
        }
        return total;
    }

    private static int valueOf(char symbol) {
        switch (symbol) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: throw new IllegalArgumentException("Not a Roman symbol: " + symbol);
        }
    }
}
```
