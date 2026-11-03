# Plus One

You are given a large integer as an array of digits `digits`, most significant digit first, with no leading zeros. Increment the integer by one and return the resulting array of digits.

## Algorithm Strategy (Right-to-Left Carry)

1. Walk from the last digit to the first.
2. If the digit is less than 9, increment it and return the array: no carry remains.
3. Otherwise set the digit to 0 (the carry moves one place to the left) and continue.
4. If the loop finishes, every digit was 9: return a new array `[1, 0, 0, ..., 0]` with one extra digit.

## Example walkthrough

- For `[1, 2, 9]`: the 9 becomes 0, the 2 becomes 3.
- Result: `[1, 3, 0]`. For `[9, 9]` the result is `[1, 0, 0]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — At most one pass.
- **Space Complexity:** `O(1)` — In place, except for the extra digit when all digits are 9 (then O(n)).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BigInteger Conversion

Convert the digits to a `BigInteger`, add one and convert back. Simple, but it defeats the purpose of the exercise.

```java
public static int[] plusOneBigInteger(int[] digits) {
    StringBuilder sb = new StringBuilder();
    for (int d : digits) sb.append(d);
    String result = new BigInteger(sb.toString()).add(BigInteger.ONE).toString();
    int[] answer = new int[result.length()];
    for (int i = 0; i < answer.length; i++) answer[i] = result.charAt(i) - '0';
    return answer;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Explicit Carry

Add one to the last digit and propagate an explicit carry.

```java
public static int[] plusOneCarry(int[] digits) {
    int[] copy = digits.clone();
    int carry = 1;
    for (int i = copy.length - 1; i >= 0 && carry > 0; i--) {
        int sum = copy[i] + carry;
        copy[i] = sum % 10;
        carry = sum / 10;
    }
    if (carry == 0) return copy;
    int[] result = new int[copy.length + 1];
    result[0] = 1;
    System.arraycopy(copy, 0, result, 1, copy.length);
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Reverse, Add, Reverse

Work on the digits in a list with the least significant digit first.

```java
public static int[] plusOneList(int[] digits) {
    List<Integer> list = new ArrayList<>();
    for (int i = digits.length - 1; i >= 0; i--) list.add(digits[i]);
    int i = 0;
    while (true) {
        if (i == list.size()) { list.add(1); break; }
        if (list.get(i) == 9) { list.set(i, 0); i++; }
        else { list.set(i, list.get(i) + 1); break; }
    }
    int[] result = new int[list.size()];
    for (int k = 0; k < result.length; k++) result[k] = list.get(list.size() - 1 - k);
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Right-to-Left Early Exit (Optimized)

Increment the first non-nine digit from the right and stop. This is the version implemented in the repository.

```java
public static int[] plusOne(int[] digits) {
    for (int i = digits.length - 1; i >= 0; i--) {
        if (digits[i] < 9) { digits[i]++; return digits; }
        digits[i] = 0;
    }
    int[] result = new int[digits.length + 1];
    result[0] = 1;
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1) (O(n) when all digits are 9)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | BigInteger Conversion | O(n) | O(n) |
| 2 | Explicit Carry | O(n) | O(n) |
| 3 | Reverse, Add, Reverse | O(n) | O(n) |
| 4 | Right-to-Left Early Exit (Optimized) | O(n) | O(1) (O(n) when all digits are 9) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### PlusOne.java

```java
import java.util.Arrays;

public class PlusOne {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3}, {4, 3, 2, 1}, {9}, {1, 2, 9}, {9, 9, 9}};
        for (int[] digits : inputs) {
            System.out.println("Input  : digits = " + Arrays.toString(digits));
            System.out.println("Output : " + Arrays.toString(plusOne(digits.clone())));
            System.out.println();
        }
    }

    public static int[] plusOne(int[] digits) {
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
