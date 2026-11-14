# Palindrome Check (String and Number)

Check whether a string or an integer reads the same forwards and backwards. For strings only letters and digits are compared and case is ignored (so `"A man, a plan, a canal: Panama"` is a palindrome); for integers negative numbers are never palindromes.

## Algorithm Strategy (Two Pointers for Strings, Half Reversal for Numbers)

1. String: put one pointer at the start and one at the end; skip characters that are not letters or digits and compare the rest ignoring case. Any mismatch means the text is not a palindrome.
2. Number: a negative number, or a positive number that ends with `0` (except `0` itself), can never be a palindrome.
3. Reverse only the second half of the digits: repeatedly move the last digit of `x` onto `reversed` until `reversed >= x`.
4. The number is a palindrome if `x == reversed` (even number of digits) or `x == reversed / 10` (odd number of digits, the middle digit is dropped).

## Example walkthrough

- `"race a car"`: `r/r`, `a/a`, `c/c` match, the space is skipped, then `e` is compared with `a`: `false`.
- For `12321`: `x = 12321, rev = 0` -> `x = 1232, rev = 1` -> `x = 123, rev = 12` -> `x = 12, rev = 123`; `x == rev / 10`: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n) for strings, O(log10 n) for numbers` — One pass over the characters or over half of the digits.
- **Space Complexity:** `O(1)` — Only a few variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Reverse and Compare (String)

Build the cleaned lowercase text, reverse it with `StringBuilder` and compare. Simple but uses extra memory.

```java
public static boolean isPalindromeReverse(String text) {
    StringBuilder cleaned = new StringBuilder();
    for (char c : text.toCharArray())
        if (Character.isLetterOrDigit(c)) cleaned.append(Character.toLowerCase(c));
    return cleaned.toString().equals(cleaned.reverse().toString());
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Recursion (String)

Compare the outer characters of the cleaned text and recurse on the inside.

```java
public static boolean isPalindromeRecursive(String text) {
    StringBuilder cleaned = new StringBuilder();
    for (char c : text.toCharArray())
        if (Character.isLetterOrDigit(c)) cleaned.append(Character.toLowerCase(c));
    return check(cleaned.toString(), 0, cleaned.length() - 1);
}

private static boolean check(String s, int left, int right) {
    if (left >= right) return true;
    return s.charAt(left) == s.charAt(right) && check(s, left + 1, right - 1);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Convert the Number to a String

Turn the integer into text and compare it with its reverse.

```java
public static boolean isPalindromeNumberString(int x) {
    if (x < 0) return false;
    String s = String.valueOf(x);
    return new StringBuilder(s).reverse().toString().equals(s);
}
```

- **Time Complexity:** O(log10 n)
- **Space Complexity:** O(log10 n)

### Approach 4: Full Digit Reversal (Number)

Reverse all digits into a `long` and compare with the original.

```java
public static boolean isPalindromeNumberFull(int x) {
    if (x < 0) return false;
    long reversed = 0;
    for (int n = x; n > 0; n /= 10) reversed = reversed * 10 + n % 10;
    return reversed == x;
}
```

- **Time Complexity:** O(log10 n)
- **Space Complexity:** O(1)

### Approach 5: Two Pointers and Half Reversal (Optimized)

Two pointers skip non-alphanumeric characters for text; only half of the digits are reversed for numbers. This is the version implemented in the repository.

```java
public static boolean isPalindrome(String text) {
    int left = 0, right = text.length() - 1;
    while (left < right) {
        if (!Character.isLetterOrDigit(text.charAt(left))) left++;
        else if (!Character.isLetterOrDigit(text.charAt(right))) right--;
        else {
            if (Character.toLowerCase(text.charAt(left)) != Character.toLowerCase(text.charAt(right))) return false;
            left++;
            right--;
        }
    }
    return true;
}

public static boolean isPalindrome(int x) {
    if (x < 0 || (x % 10 == 0 && x != 0)) return false;
    int reversed = 0;
    while (x > reversed) {
        reversed = reversed * 10 + x % 10;
        x /= 10;
    }
    return x == reversed || x == reversed / 10;
}
```

- **Time Complexity:** O(n) for text, O(log10 n) for numbers
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Reverse and Compare (String) | O(n) | O(n) |
| 2 | Recursion (String) | O(n) | O(n) |
| 3 | Convert the Number to a String | O(log10 n) | O(log10 n) |
| 4 | Full Digit Reversal (Number) | O(log10 n) | O(1) |
| 5 | Two Pointers and Half Reversal (Optimized) | O(n) for text, O(log10 n) for numbers | O(1) |

## Source Code

### PalindromeCheck.java

```java
public class PalindromeCheck {

    public static void main(String[] args) {
        String[] texts = {"A man, a plan, a canal: Panama", "race a car", "", "No lemon, no melon"};
        for (String text : texts) {
            System.out.println("Input  : text = \"" + text + "\"");
            System.out.println("Output : " + isPalindrome(text));
            System.out.println();
        }

        int[] numbers = {121, -121, 10, 0, 12321, 1221};
        for (int number : numbers) {
            System.out.println("Input  : number = " + number);
            System.out.println("Output : " + isPalindrome(number));
            System.out.println();
        }
    }

    public static boolean isPalindrome(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (!Character.isLetterOrDigit(text.charAt(left))) {
                left++;
            } else if (!Character.isLetterOrDigit(text.charAt(right))) {
                right--;
            } else {
                if (Character.toLowerCase(text.charAt(left)) != Character.toLowerCase(text.charAt(right))) {
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
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

The optimized solution is Approach 5 above, which is also the version implemented in this repository.

The idea: for text, put one pointer at each end, skip characters that are not letters or digits, and compare the two remaining characters ignoring case; any mismatch means "not a palindrome". For a number, no string is built: reverse only the second half of the digits and compare it with the first half, which also avoids integer overflow.

**Input (text):** `"No lemon, no melon"` (18 characters; indices 2, 8, 9 and 12 are a space or comma).

| Step | `left` | `right` | Characters | Action |
|------|--------|---------|------------|--------|
| 1 | 0 | 17 | `N`, `n` | equal ignoring case, move both: 1, 16 |
| 2 | 1 | 16 | `o`, `o` | equal, move: 2, 15 |
| 3 | 2 | 15 | space | not alphanumeric, `left++` |
| 4 | 3 | 15 | `l`, `l` | equal, move: 4, 14 |
| 5 | 4 | 14 | `e`, `e` | equal, move: 5, 13 |
| 6 | 5 | 13 | `m`, `m` | equal, move: 6, 12 |
| 7 | 6 | 12 | `right` is a space | `right--` |
| 8 | 6 | 11 | `o`, `o` | equal, move: 7, 10 |
| 9 | 7 | 10 | `n`, `n` | equal, move: 8, 9 |
| 10 | 8 | 9 | `left` is a comma | `left++`; now `left == right`, loop ends |

**Input (number):** `12321`. Loop while `x > reversed`:

| Step | `x` before | `reversed` after | `x` after |
|------|-----------|------------------|-----------|
| 1 | 12321 | 1 | 1232 |
| 2 | 1232 | 12 | 123 |
| 3 | 123 | 123 | 12 |

Now `x (12) > reversed (123)` is false. For an odd number of digits the middle digit is in `reversed`, so the final test `x == reversed || x == reversed / 10` compares 12 with `123 / 10 = 12`, which is true.

**Edge cases.** For text, `""` and a string with no letters or digits return `true`; `"race a car"` returns `false`. For numbers, negatives (`-121`) and numbers that end in 0 (`10`) return `false` at once, but `0` is `true`. For `1221`, the loop stops at `x = 12`, `reversed = 12`, so `x == reversed`.

**Result:** `true` for both `"No lemon, no melon"` and `12321`, matching the class output.
