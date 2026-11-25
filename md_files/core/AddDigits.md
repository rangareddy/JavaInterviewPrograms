# Add Digits

Given an integer `num`, repeatedly add all its digits until the result has only one digit, and return it.

## Algorithm Strategy (Digital Root in Constant Time)

1. The repeated digit sum of a positive number is its digital root.
2. A number and its digit sum have the same remainder modulo 9, so the digital root is `1 + (num - 1) % 9` for `num > 0`.
3. For `num == 0` the answer is 0.
4. Return the formula's value without any loop.

## Example walkthrough

- For `38`: `3 + 8 = 11`, then `1 + 1 = 2`.
- The formula gives `1 + (38 - 1) % 9 = 1 + 1 = 2`.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Arithmetic only.
- **Space Complexity:** `O(1)` — No extra storage.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Repeat Until One Digit

Sum the digits in a loop until a single digit remains.

```java
public static int loop(int n) { while (n >= 10) { int s = 0; while (n > 0) { s += n % 10; n /= 10; } n = s; } return n; }
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Approach 2: Digital Root Formula (Optimized)

Use the remainder modulo 9. This is the version implemented in the repository.

```java
public static int formula(int n) { return n <= 0 ? 0 : 1 + (n - 1) % 9; }
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Repeat Until One Digit | O(log n) | O(1) |
| 2 | Digital Root Formula (Optimized) | O(1) | O(1) |

## Source Code

### AddDigits.java

```java
public class AddDigits {

    public static void main(String[] args) {
        int[] inputs = {38, 0, 9, 10, 999999999};
        for (int num : inputs) {
            System.out.println("Input  : num = " + num);
            System.out.println("Output : " + addDigits(num));
            System.out.println();
        }
    }

    public static int addDigits(int num) {
        if (num <= 0) {
            return 0;
        }
        return 1 + (num - 1) % 9;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: since `10 ≡ 1 (mod 9)`, every number leaves the same remainder modulo 9 as the sum of its digits. Repeating the digit sum therefore keeps the remainder, and the process ends at a single digit between 1 and 9 for any positive number. Shifting by one maps the remainder 0 case (multiples of 9) to 9.

**Input:** `num = 38`.

| Step | Value |
|------|-------|
| digit sum | 3 + 8 = 11 |
| digit sum again | 1 + 1 = 2 |
| formula | 1 + (38 - 1) % 9 = 1 + 1 = 2 |

**Output:** `2`.
