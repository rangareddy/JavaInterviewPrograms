# Multiply Strings

Given two non-negative integers `num1` and `num2` represented as strings, return their product as a string. You must not use a built-in big integer library or convert the inputs to integers directly.

## Algorithm Strategy (Grade-School Multiplication into a Digit Array)

1. The product of an `m`-digit and an `n`-digit number has at most `m + n` digits, so allocate an `int[m + n]`.
2. The digit at position `i` of `num1` times the digit at position `j` of `num2` contributes to positions `i + j` (carry) and `i + j + 1` (units) of the result array.
3. Walk both strings from the right. Add the product to `result[i + j + 1]`, move the carry (`sum / 10`) into `result[i + j]` and keep `sum % 10` in place.
4. Skip leading zeros when building the output string and return `"0"` if either input is `"0"`.

## Example walkthrough

- For `"123" * "456"`: partial products accumulate into `[0, 5, 6, 0, 8, 8]`.
- Dropping the leading zero gives `56088`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Every digit of one number is multiplied by every digit of the other.
- **Space Complexity:** `O(m + n)` — The result digit array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BigInteger (not allowed by the problem)

The standard library does it in one line, which is useful as a reference to test against but breaks the rules of the question.

```java
public static String multiplyBigInteger(String num1, String num2) {
    return new java.math.BigInteger(num1).multiply(new java.math.BigInteger(num2)).toString();
}
```

- **Time Complexity:** Depends on the library (sub-quadratic for large inputs)
- **Space Complexity:** O(m + n)

### Approach 2: Add Shifted Partial Products

Multiply `num1` by each digit of `num2`, shift by the digit position and add the partial results with string addition.

```java
public static String multiplyPartials(String num1, String num2) {
    if (num1.equals("0") || num2.equals("0")) return "0";
    String total = "0";
    for (int j = num2.length() - 1; j >= 0; j--) {
        int d = num2.charAt(j) - '0';
        StringBuilder partial = new StringBuilder();
        int carry = 0;
        for (int i = num1.length() - 1; i >= 0; i--) {
            int p = (num1.charAt(i) - '0') * d + carry;
            partial.append(p % 10);
            carry = p / 10;
        }
        if (carry > 0) partial.append(carry);
        partial.reverse();
        for (int z = 0; z < num2.length() - 1 - j; z++) partial.append('0');
        total = addStrings(total, partial.toString());
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

### Approach 3: Digit Array with Carry (Optimized)

Add every digit product into position `i + j + 1` of a single array so no intermediate strings are built. This is the version implemented in the repository.

```java
public static String multiply(String num1, String num2) {
    if (num1.equals("0") || num2.equals("0")) {
        return "0";
    }
    int m = num1.length();
    int n = num2.length();
    int[] result = new int[m + n];
    for (int i = m - 1; i >= 0; i--) {
        for (int j = n - 1; j >= 0; j--) {
            int product = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
            int sum = product + result[i + j + 1];
            result[i + j + 1] = sum % 10;
            result[i + j] += sum / 10;
        }
    }
    StringBuilder sb = new StringBuilder();
    for (int digit : result) {
        if (sb.length() == 0 && digit == 0) {
            continue;
        }
        sb.append(digit);
    }
    return sb.toString();
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m + n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | BigInteger (not allowed by the problem) | Depends on the library (sub-quadratic for large inputs) | O(m + n) |
| 2 | Add Shifted Partial Products | O(m * n) | O(m + n) |
| 3 | Digit Array with Carry (Optimized) | O(m * n) | O(m + n) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### MultiplyStrings.java

```java
public class MultiplyStrings {

    public static void main(String[] args) {
        String[][] inputs = {{"2", "3"}, {"123", "456"}, {"0", "9133"}, {"999", "999"}};
        for (String[] in : inputs) {
            System.out.println("Input  : num1 = \"" + in[0] + "\", num2 = \"" + in[1] + "\"");
            System.out.println("Output : \"" + multiply(in[0], in[1]) + "\"");
            System.out.println();
        }
    }

    public static String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int m = num1.length();
        int n = num2.length();
        int[] result = new int[m + n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int product = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int sum = product + result[i + j + 1];
                result[i + j + 1] = sum % 10;
                result[i + j] += sum / 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int digit : result) {
            if (sb.length() == 0 && digit == 0) {
                continue;
            }
            sb.append(digit);
        }
        return sb.toString();
    }
}
```
