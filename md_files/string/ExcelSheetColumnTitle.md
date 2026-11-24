# Excel Sheet Column Title

Given a positive integer `columnNumber`, return its column title as it appears in an Excel sheet: 1 is `A`, 26 is `Z`, 27 is `AA`, 28 is `AB`, and so on.

## Algorithm Strategy (Bijective Base-26)

1. There is no zero digit: `A` to `Z` stand for 1 to 26. So subtract 1 before taking each digit.
2. Compute `columnNumber--`, then the last letter is `'A' + columnNumber % 26`.
3. Divide `columnNumber` by 26 and repeat while it is above 0.
4. The letters come out from the last to the first, so reverse them.

## Example walkthrough

- For 28: `28 - 1 = 27`, `27 % 26 = 1` gives `B`; then `27 / 26 = 1`, `1 - 1 = 0`, `0 % 26 = 0` gives `A`.
- Reversed: `AB`.

## Complexity Analysis

- **Time Complexity:** `O(log26 n)` — One step per letter.
- **Space Complexity:** `O(log26 n)` — The result.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Convert the quotient first and append the last letter.

```java
public static String recursive(int n) {
    if (n == 0) return "";
    n--;
    return recursive(n / 26) + (char) ('A' + n % 26);
}
```

- **Time Complexity:** O(log26 n)
- **Space Complexity:** O(log26 n)

### Approach 2: Iterative with the Minus-One Shift (Optimized)

Subtract 1 before every digit to remove the missing zero. This is the version implemented in the repository.

```java
public static String iterative(int n) {
    StringBuilder sb = new StringBuilder();
    while (n > 0) { n--; sb.append((char) ('A' + n % 26)); n /= 26; }
    return sb.reverse().toString();
}
```

- **Time Complexity:** O(log26 n)
- **Space Complexity:** O(log26 n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(log26 n) | O(log26 n) |
| 2 | Iterative with the Minus-One Shift (Optimized) | O(log26 n) | O(log26 n) |

## Source Code

### ExcelSheetColumnTitle.java

```java
public class ExcelSheetColumnTitle {

    public static void main(String[] args) {
        int[] inputs = {28, 1, 26, 27, 701, 703, 2147483647};
        for (int n : inputs) {
            System.out.println("Input  : columnNumber = " + n);
            System.out.println("Output : " + convertToTitle(n));
            System.out.println();
        }
    }

    public static String convertToTitle(int columnNumber) {
        StringBuilder title = new StringBuilder();
        while (columnNumber > 0) {
            columnNumber--;
            title.append((char) ('A' + columnNumber % 26));
            columnNumber /= 26;
        }
        return title.reverse().toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: this is base 26 with digits 1 to 26 instead of 0 to 25. Subtracting 1 before taking each digit shifts the range back to 0 to 25.

**Input:** `columnNumber = 28`.

| Step | `n` after `n--` | Letter (`n % 26`) | `n / 26` |
|------|----------------|-------------------|----------|
| 1 | 27 | B | 1 |
| 2 | 0 | A | 0 |

Letters in order of discovery: `B`, `A`; reversed: `AB`.

**Output:** `AB`.
