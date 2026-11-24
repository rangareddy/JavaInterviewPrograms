# Palindrome Number

Given an integer `x`, return `true` if `x` reads the same forward and backward, and `false` otherwise. Negative numbers are not palindromes.

## Algorithm Strategy (Reverse Half of the Digits)

1. Negative numbers, and non-zero numbers ending in 0, can never be palindromes, so return `false` for them.
2. Build `reversed` from the last digits of `x` while `x > reversed`, moving each digit across.
3. Stop when the reversed half is at least as large as what remains of `x`: this means half of the digits have moved.
4. An even-length palindrome has `x == reversed`; an odd-length one has `x == reversed / 10` (the middle digit is dropped).

## Example walkthrough

- For `1221`: `reversed` becomes 1 then 12 while `x` shrinks to 122 then 12; `x == reversed`, so it is a palindrome.
- For `121`: the loop ends with `x = 1` and `reversed = 12`; dropping the middle digit, `reversed / 10 = 1 = x`, so it is a palindrome too.

## Complexity Analysis

- **Time Complexity:** `O(log10 n)` — Half of the digits are processed.
- **Space Complexity:** `O(1)` — Only two integers. No string is created.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Convert to a String

Compare the decimal string with its reverse. Simple, but allocates.

```java
public static boolean viaString(int x) {
    if (x < 0) return false;
    String s = Integer.toString(x);
    return new StringBuilder(s).reverse().toString().equals(s);
}
```

- **Time Complexity:** O(log10 n)
- **Space Complexity:** O(log10 n)

### Approach 2: Reverse the Whole Number

Reverse all digits with a `long` to avoid overflow, then compare.

```java
public static boolean fullReverse(int x) {
    if (x < 0) return false;
    long rev = 0; int t = x;
    while (t > 0) { rev = rev * 10 + t % 10; t /= 10; }
    return rev == x;
}
```

- **Time Complexity:** O(log10 n)
- **Space Complexity:** O(1)

### Approach 3: Reverse Half (Optimized)

Reverse only the second half and compare with the first. This is the version implemented in the repository.

```java
public static boolean halfReverse(int x) {
    if (x < 0 || (x % 10 == 0 && x != 0)) return false;
    int rev = 0;
    while (x > rev) { rev = rev * 10 + x % 10; x /= 10; }
    return x == rev || x == rev / 10;
}
```

- **Time Complexity:** O(log10 n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Convert to a String | O(log10 n) | O(log10 n) |
| 2 | Reverse the Whole Number | O(log10 n) | O(1) |
| 3 | Reverse Half (Optimized) | O(log10 n) | O(1) |

## Source Code

### PalindromeNumber.java

```java
public class PalindromeNumber {

    public static void main(String[] args) {
        int[] inputs = {121, -121, 10, 1221, 0, 12321};
        for (int x : inputs) {
            System.out.println("Input  : x = " + x);
            System.out.println("Output : " + isPalindrome(x));
            System.out.println();
        }
    }

    public static boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int reversed = 0;
        while (x > reversed) {
            reversed = reversed * 10 + x % 10;
            x /= 10;
        }
        return x == reversed || x == reversed / 10;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: a number is a palindrome when its second half, reversed, equals its first half. Reversing only half of the digits also avoids any integer overflow.

**Input:** `x = 12321`.

| Step | `x` | `reversed` |
|------|-----|-----------|
| start | 12321 | 0 |
| 1 | 1232 | 1 |
| 2 | 123 | 12 |
| 3 | 12 | 123 |

Now `x` (12) is no longer larger than `reversed` (123), so the loop stops. The number has an odd length, so compare `x` with `reversed / 10 = 12`: equal.

**Output:** `true`.
