# Add Binary

Given two binary strings `a` and `b`, return their sum as a binary string.

## Algorithm Strategy (Two Pointers from the Right)

1. Start at the last character of both strings with `carry = 0`.
2. At each step add the two bits (0 when a string is exhausted) and the carry.
3. Append `sum % 2` to the result and set `carry = sum / 2`.
4. Continue while either string has digits or a carry remains, then reverse the result.

## Example walkthrough

- For `11` + `1`: 1 + 1 = 10 (write 0, carry 1), 1 + 0 + carry 1 = 10 (write 0, carry 1), then the final carry writes 1.
- Result: `100`.

## Complexity Analysis

- **Time Complexity:** `O(max(m, n))` — One step per digit.
- **Space Complexity:** `O(max(m, n))` — The result string.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BigInteger

Parse both strings in base 2, add, and print in base 2. Short, but allocates and is not what an interviewer wants.

```java
public static String viaBigInteger(String a, String b) {
    return new java.math.BigInteger(a, 2).add(new java.math.BigInteger(b, 2)).toString(2);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Two Pointers with Carry (Optimized)

Add digit by digit from the right. This is the version implemented in the repository.

```java
public static String twoPointers(String a, String b) {
    StringBuilder sb = new StringBuilder(); int i = a.length() - 1, j = b.length() - 1, carry = 0;
    while (i >= 0 || j >= 0 || carry > 0) {
        int s = carry; if (i >= 0) s += a.charAt(i--) - '0'; if (j >= 0) s += b.charAt(j--) - '0';
        sb.append(s % 2); carry = s / 2;
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

### AddBinary.java

```java
public class AddBinary {

    public static void main(String[] args) {
        String[][] inputs = {{"11", "1"}, {"1010", "1011"}, {"0", "0"}, {"1111", "1111"}};
        for (String[] in : inputs) {
            System.out.println("Input  : a = \"" + in[0] + "\", b = \"" + in[1] + "\"");
            System.out.println("Output : " + addBinary(in[0], in[1]));
            System.out.println();
        }
    }

    public static String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) {
                sum += a.charAt(i--) - '0';
            }
            if (j >= 0) {
                sum += b.charAt(j--) - '0';
            }
            result.append(sum % 2);
            carry = sum / 2;
        }
        return result.reverse().toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: this is schoolbook addition in base 2. Each column gives a digit (`sum % 2`) and a carry (`sum / 2`).

**Input:** `a = "11"`, `b = "1"`.

| Step | Bits | `carry` in | `sum` | Digit | `carry` out |
|------|------|-----------|-------|-------|-------------|
| 1 | 1 + 1 | 0 | 2 | 0 | 1 |
| 2 | 1 + 0 | 1 | 2 | 0 | 1 |
| 3 | none | 1 | 1 | 1 | 0 |

Digits read from the last step to the first: `100`.

**Output:** `100`.
