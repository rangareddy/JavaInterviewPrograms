# Multiply Strings

Given two non-negative integers `num1` and `num2` represented as strings, return their product, also as a string. You must not use a big-integer library or convert the inputs to integers directly.

## Algorithm Strategy (Grade-School Multiplication with a Digit Array)

1. The product of an `m`-digit and an `n`-digit number has at most `m + n` digits: allocate an `int[] product` of that length.
2. Digit `i` of `num1` times digit `j` of `num2` contributes to positions `i + j` (carry) and `i + j + 1` (units).
3. Process the digits from right to left: add the digit product plus the value already at `i + j + 1`, store `sum % 10` there and add `sum / 10` to position `i + j`.
4. Skip the leading zeros of the array and join the rest into a string (return `"0"` if everything is zero).

## Example walkthrough

- For `"123"` and `"456"`: the digit array ends as `[0, 5, 6, 0, 8, 8]`, which after dropping the leading zero is `56088`.
- Result: `"56088"`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every pair of digits is multiplied once.
- **Space Complexity:** `O(m + n)` — The digit array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BigInteger (Reference)

Let `BigInteger` do the work. The problem forbids this, but it makes a convenient cross-check.

```java
public static String multiplyBigInteger(String a, String b) {
    return new BigInteger(a).multiply(new BigInteger(b)).toString();
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m + n)

### Approach 2: Repeated String Addition

Multiply `num1` by each digit of `num2` (shifted by its position) and add the partial products with string addition.

```java
public static String multiplyAdd(String num1, String num2) {
    if (num1.equals("0") || num2.equals("0")) return "0";
    String total = "0";
    for (int j = num2.length() - 1; j >= 0; j--) {
        int digit = num2.charAt(j) - '0';
        StringBuilder partial = new StringBuilder();
        for (int z = 0; z < num2.length() - 1 - j; z++) partial.append('0');
        int carry = 0;
        for (int i = num1.length() - 1; i >= 0; i--) {
            int value = (num1.charAt(i) - '0') * digit + carry;
            partial.append(value % 10);
            carry = value / 10;
        }
        if (carry > 0) partial.append(carry);
        total = addStrings(total, partial.reverse().toString());
    }
    return total;
}

private static String addStrings(String a, String b) {
    StringBuilder sb = new StringBuilder();
    int i = a.length() - 1, j = b.length() - 1, carry = 0;
    while (i >= 0 || j >= 0 || carry > 0) {
        int sum = carry + (i >= 0 ? a.charAt(i--) - '0' : 0) + (j >= 0 ? b.charAt(j--) - '0' : 0);
        sb.append(sum % 10);
        carry = sum / 10;
    }
    return sb.reverse().toString();
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m + n)

### Approach 3: Column-wise Sums

Add every digit product to its column first and propagate the carries once at the end.

```java
public static String multiplyColumns(String num1, String num2) {
    int m = num1.length(), n = num2.length();
    int[] columns = new int[m + n];
    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++) columns[i + j + 1] += (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
    for (int k = m + n - 1; k > 0; k--) {
        columns[k - 1] += columns[k] / 10;
        columns[k] %= 10;
    }
    StringBuilder sb = new StringBuilder();
    for (int digit : columns) if (sb.length() > 0 || digit != 0) sb.append(digit);
    return sb.length() == 0 ? "0" : sb.toString();
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m + n)

### Approach 4: Digit Array with Immediate Carry (Optimized)

The classic positional product array, carrying as it goes. This is the version implemented in the repository.

```java
public static String multiply(String num1, String num2) {
    int m = num1.length(), n = num2.length();
    int[] product = new int[m + n];
    for (int i = m - 1; i >= 0; i--)
        for (int j = n - 1; j >= 0; j--) {
            int sum = (num1.charAt(i) - '0') * (num2.charAt(j) - '0') + product[i + j + 1];
            product[i + j + 1] = sum % 10;
            product[i + j] += sum / 10;
        }
    StringBuilder result = new StringBuilder();
    for (int digit : product) if (result.length() > 0 || digit != 0) result.append(digit);
    return result.length() == 0 ? "0" : result.toString();
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m + n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | BigInteger (Reference) | O(m * n) | O(m + n) |
| 2 | Repeated String Addition | O(m * n) | O(m + n) |
| 3 | Column-wise Sums | O(m * n) | O(m + n) |
| 4 | Digit Array with Immediate Carry (Optimized) | O(m * n) | O(m + n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### MultiplyStrings.java

```java
public class MultiplyStrings {

    public static void main(String[] args) {
        String[][] inputs = {{"2", "3"}, {"123", "456"}, {"0", "9999"}, {"999", "999"}, {"12345678", "87654321"}};
        for (String[] input : inputs) {
            System.out.println("Input  : num1 = \"" + input[0] + "\", num2 = \"" + input[1] + "\"");
            System.out.println("Output : " + multiply(input[0], input[1]));
            System.out.println();
        }
    }

    public static String multiply(String num1, String num2) {
        int m = num1.length();
        int n = num2.length();
        int[] product = new int[m + n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int sum = (num1.charAt(i) - '0') * (num2.charAt(j) - '0') + product[i + j + 1];
                product[i + j + 1] = sum % 10;
                product[i + j] += sum / 10;
            }
        }

        StringBuilder result = new StringBuilder();
        for (int digit : product) {
            if (result.length() > 0 || digit != 0) {
                result.append(digit);
            }
        }
        return result.length() == 0 ? "0" : result.toString();
    }
}
```
