# Add Strings

Given two non-negative integers `num1` and `num2` represented as decimal strings, return their sum as a string, without converting the inputs to integers or using `BigInteger`.

## Algorithm Strategy (Two Pointers from the Right)

1. Walk both strings from the last digit with `carry = 0`.
2. Add the two digits (0 once a string is used up) and the carry.
3. Append `sum % 10` and set `carry = sum / 10`.
4. Stop when both strings and the carry are finished, then reverse.

## Example walkthrough

- For `11` + `123`: 1 + 3 = 4, 1 + 2 = 3, then 0 + 1 = 1.
- Result: `134`.

## Complexity Analysis

- **Time Complexity:** `O(max(m, n))` — One step per digit.
- **Space Complexity:** `O(max(m, n))` — The result.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BigInteger

Add with `BigInteger`. Correct, but the problem forbids it.

```java
public static String viaBigInteger(String a, String b) { return new java.math.BigInteger(a).add(new java.math.BigInteger(b)).toString(); }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Two Pointers with Carry (Optimized)

Schoolbook addition from the right. This is the version implemented in the repository.

```java
public static String twoPointers(String a, String b) {
    StringBuilder sb = new StringBuilder(); int i = a.length() - 1, j = b.length() - 1, c = 0;
    while (i >= 0 || j >= 0 || c > 0) {
        int s = c; if (i >= 0) s += a.charAt(i--) - '0'; if (j >= 0) s += b.charAt(j--) - '0';
        sb.append(s % 10); c = s / 10;
    }
    return sb.reverse().toString();
}
```

- **Time Complexity:** O(max(m, n))
- **Space Complexity:** O(max(m, n))

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | BigInteger | O(n) | O(n) |
| 2 | Two Pointers with Carry (Optimized) | O(max(m, n)) | O(max(m, n)) |

## Source Code

### AddStrings.java

```java
public class AddStrings {

    public static void main(String[] args) {
        String[][] inputs = {{"11", "123"}, {"456", "77"}, {"0", "0"}, {"999", "1"}};
        for (String[] in : inputs) {
            System.out.println("Input  : num1 = \"" + in[0] + "\", num2 = \"" + in[1] + "\"");
            System.out.println("Output : " + addStrings(in[0], in[1]));
            System.out.println();
        }
    }

    public static String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;
        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) {
                sum += num1.charAt(i--) - '0';
            }
            if (j >= 0) {
                sum += num2.charAt(j--) - '0';
            }
            result.append(sum % 10);
            carry = sum / 10;
        }
        return result.reverse().toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: add column by column from the right, carrying 1 whenever a column reaches 10.

**Input:** `num1 = "11"`, `num2 = "123"`.

| Step | Digits | `carry` in | `sum` | Digit | `carry` out |
|------|--------|-----------|-------|-------|-------------|
| 1 | 1 + 3 | 0 | 4 | 4 | 0 |
| 2 | 1 + 2 | 0 | 3 | 3 | 0 |
| 3 | 0 + 1 | 0 | 1 | 1 | 0 |

**Output:** `134`.
