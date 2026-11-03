# Armstrong Number

A number with `d` digits is an Armstrong (narcissistic) number if the sum of its digits, each raised to the power `d`, equals the number itself. For example `153 = 1^3 + 5^3 + 3^3`. Check a number and list all Armstrong numbers in a range.

## Algorithm Strategy (Digit Extraction with Power Sum)

1. Count the digits `d` of the number.
2. Take the digits one by one with `n % 10` and drop each with `n / 10`.
3. Add `digit^d` to a running `sum`. Use integer multiplication rather than `Math.pow` to avoid floating point rounding.
4. The number is an Armstrong number when `sum == number`. To list them, test every number in the range.

## Example walkthrough

- For `153`: `d = 3`, `1 + 125 + 27 = 153`: Armstrong.
- For `154`: `1 + 125 + 64 = 190 != 154`: not Armstrong. The Armstrong numbers up to 1000 are `1..9, 153, 370, 371, 407`.

## Complexity Analysis

- **Time Complexity:** `O(d) per number` — `d` digits, each raised to the power `d` in `O(d)` multiplications, so O(d^2) in total.
- **Space Complexity:** `O(1)` — A few variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: String Digits and Math.pow

Read the digits from the decimal string and add `Math.pow(digit, length)`. Floating point rounding makes this fragile for large numbers.

```java
public static boolean isArmstrongString(int number) {
    if (number < 0) return false;
    String s = String.valueOf(number);
    long sum = 0;
    for (char c : s.toCharArray()) sum += Math.round(Math.pow(c - '0', s.length()));
    return sum == number;
}
```

- **Time Complexity:** O(d^2)
- **Space Complexity:** O(d)

### Approach 2: Streams

Map every digit to `digit^d` and sum the stream.

```java
public static boolean isArmstrongStream(int number) {
    if (number < 0) return false;
    String s = String.valueOf(number);
    long sum = s.chars().mapToLong(c -> BigInteger.valueOf(c - '0').pow(s.length()).longValue()).sum();
    return sum == number;
}
```

- **Time Complexity:** O(d^2)
- **Space Complexity:** O(d)

### Approach 3: Digit Extraction with a Power Helper (Optimized)

Take digits with `% 10` and raise them with integer multiplication, so there is no rounding. This is the version implemented in the repository.

```java
public static boolean isArmstrong(int number) {
    if (number < 0) return false;
    int digits = String.valueOf(number).length();
    long sum = 0;
    for (int n = number; n > 0; n /= 10) sum += power(n % 10, digits);
    return sum == number;
}

private static long power(int base, int exponent) {
    long result = 1;
    for (int i = 0; i < exponent; i++) result *= base;
    return result;
}
```

- **Time Complexity:** O(d^2)
- **Space Complexity:** O(1)

### Approach 4: Precomputed Powers

Build the table `0^d..9^d` once for the number of digits `d`, then each digit is a lookup.

```java
public static boolean isArmstrongTable(int number) {
    if (number < 0) return false;
    int digits = String.valueOf(number).length();
    long[] powers = new long[10];
    for (int digit = 0; digit < 10; digit++) {
        powers[digit] = 1;
        for (int i = 0; i < digits; i++) powers[digit] *= digit;
    }
    long sum = 0;
    for (int n = number; n > 0; n /= 10) sum += powers[n % 10];
    return sum == number;
}
```

- **Time Complexity:** O(d + 10 d)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | String Digits and Math.pow | O(d^2) | O(d) |
| 2 | Streams | O(d^2) | O(d) |
| 3 | Digit Extraction with a Power Helper (Optimized) | O(d^2) | O(1) |
| 4 | Precomputed Powers | O(d + 10 d) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### ArmstrongNumber.java

```java
import java.util.ArrayList;
import java.util.List;

public class ArmstrongNumber {

    public static void main(String[] args) {
        int[] inputs = {153, 154, 9474, 370, 0, 1634};
        for (int number : inputs) {
            System.out.println("Input  : number = " + number);
            System.out.println("Output : " + (isArmstrong(number) ? "Armstrong" : "Not Armstrong"));
            System.out.println();
        }

        System.out.println("Input  : Armstrong numbers from 1 to 1000");
        System.out.println("Output : " + armstrongNumbers(1, 1000));
    }

    public static boolean isArmstrong(int number) {
        if (number < 0) {
            return false;
        }

        int digits = String.valueOf(number).length();
        long sum = 0;
        for (int n = number; n > 0; n /= 10) {
            sum += power(n % 10, digits);
        }
        return sum == number;
    }

    public static List<Integer> armstrongNumbers(int from, int to) {
        List<Integer> result = new ArrayList<>();
        for (int n = from; n <= to; n++) {
            if (isArmstrong(n)) {
                result.add(n);
            }
        }
        return result;
    }

    private static long power(int base, int exponent) {
        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }
}
```
