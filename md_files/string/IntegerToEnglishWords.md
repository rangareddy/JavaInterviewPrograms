# Integer to English Words

Convert a non-negative integer `num` to its English words representation.

## Algorithm Strategy (Three-Digit Chunks with Scale Words)

1. Handle 0 separately (`Zero`).
2. Split the number into groups of three digits from the right and pair each non-zero group with its scale: none, `Thousand`, `Million`, `Billion`.
3. Convert a three-digit group: hundreds with `Hundred`, then a number below 20 from a table, or tens plus ones.
4. Join the pieces with single spaces, skipping empty groups.

## Example walkthrough

- `123` is one group: `One Hundred Twenty Three`.
- `1234567` is `One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven`.

## Complexity Analysis

- **Time Complexity:** `O(1)` — At most four groups of three digits for a 32-bit integer.
- **Space Complexity:** `O(1)` — The word tables and the result.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion on the Largest Unit

Repeatedly peel off the largest unit (billion, million, thousand, hundred) and recurse on the quotient and the remainder.

```java
private static final String[] SMALL = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
private static final String[] TEN = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
public static String recursive(int n) { return n == 0 ? "Zero" : go(n); }
private static String go(int n) {
    if (n < 20) return n == 0 ? "" : SMALL[n];
    if (n < 100) return (TEN[n / 10] + " " + go(n % 10)).trim();
    if (n < 1000) return (go(n / 100) + " Hundred " + go(n % 100)).trim();
    if (n < 1000000) return (go(n / 1000) + " Thousand " + go(n % 1000)).trim();
    if (n < 1000000000) return (go(n / 1000000) + " Million " + go(n % 1000000)).trim();
    return (go(n / 1000000000) + " Billion " + go(n % 1000000000)).trim();
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 2: Three-Digit Chunks with Scale Words (Optimized)

Convert each group of three digits and attach its scale. This is the version implemented in the repository.

```java
public static String chunks(int num) {
    if (num == 0) return "Zero";
    String[] below = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"}; String[] scales = {"", "Thousand", "Million", "Billion"};
    java.util.List<String> parts = new java.util.ArrayList<>(); int s = 0;
    while (num > 0) {
        int c = num % 1000;
        if (c > 0) { StringBuilder sb = new StringBuilder(); if (c >= 100) { sb.append(below[c / 100]).append(" Hundred"); c %= 100; }
            if (c >= 20) { if (sb.length() > 0) sb.append(' '); sb.append(tens[c / 10]); c %= 10; } if (c > 0) { if (sb.length() > 0) sb.append(' '); sb.append(below[c]); }
            if (!scales[s].isEmpty()) sb.append(' ').append(scales[s]); parts.add(0, sb.toString()); }
        num /= 1000; s++;
    }
    return String.join(" ", parts);
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion on the Largest Unit | O(1) | O(1) |
| 2 | Three-Digit Chunks with Scale Words (Optimized) | O(1) | O(1) |

## Source Code

### IntegerToEnglishWords.java

```java
public class IntegerToEnglishWords {

    public static void main(String[] args) {
        int[] inputs = {123, 12345, 1234567, 1000010, 0, 100, Integer.MAX_VALUE};
        for (int n : inputs) {
            System.out.println("Input  : num = " + n);
            System.out.println("Output : " + numberToWords(n));
            System.out.println();
        }
    }

    private static final String[] BELOW_TWENTY = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static final String[] TENS = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private static final String[] SCALES = {"", "Thousand", "Million", "Billion"};

    public static String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        StringBuilder result = new StringBuilder();
        int scale = 0;
        while (num > 0) {
            int chunk = num % 1000;
            if (chunk > 0) {
                String words = chunkToWords(chunk);
                if (!SCALES[scale].isEmpty()) {
                    words += " " + SCALES[scale];
                }
                result.insert(0, result.length() > 0 ? words + " " : words);
            }
            num /= 1000;
            scale++;
        }
        return result.toString();
    }

    private static String chunkToWords(int n) {
        StringBuilder sb = new StringBuilder();
        if (n >= 100) {
            sb.append(BELOW_TWENTY[n / 100]).append(" Hundred");
            n %= 100;
        }
        if (n >= 20) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(TENS[n / 10]);
            n %= 10;
        }
        if (n > 0) {
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(BELOW_TWENTY[n]);
        }
        return sb.toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: English reads numbers in groups of three digits, each followed by a scale word. A single routine for a three-digit group, reused for every group, covers any 32-bit integer.

**Input:** `num = 1234567`.

| Group (from the right) | Value | Words | Scale |
|------------------------|-------|-------|-------|
| 567 | 567 | Five Hundred Sixty Seven | |
| 234 | 234 | Two Hundred Thirty Four | Thousand |
| 1 | 1 | One | Million |

Reading the groups from the left: `One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven`.

**Output for the first sample (`123`):** `One Hundred Twenty Three`.
