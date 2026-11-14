# Valid Palindrome II

Given a string `s`, return `true` if it can become a palindrome after deleting at most one character.

## Algorithm Strategy (Two Pointers with One Skip)

1. Walk two pointers inward from both ends while the characters match.
2. On the first mismatch, one of the two characters must be the one deleted.
3. Check whether the rest is a palindrome after skipping the left character, or after skipping the right one.
4. If neither remainder is a palindrome, no single deletion works. Only the first mismatch needs this branch, so the work stays linear.

## Example walkthrough

- For `abca`: the outer `a` characters match, then `b != c` at indexes 1 and 2.
- Skipping the left `b` leaves indexes 2..2, the single character `c`, which is a palindrome. Result: `true` (delete `b` to get `aca`).
- For `abc`: `a != c` at once; `bc` and `ab` are not palindromes. Result: `false`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — The main scan and at most two remainder scans, each linear.
- **Space Complexity:** `O(1)` — Only pointers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Deletion

Test the string itself, then every string obtained by deleting one character.

```java
public static boolean validPalindromeBrute(String s) {
    if (new StringBuilder(s).reverse().toString().equals(s)) return true;
    for (int i = 0; i < s.length(); i++) {
        String t = s.substring(0, i) + s.substring(i + 1);
        if (new StringBuilder(t).reverse().toString().equals(t)) return true;
    }
    return false;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n) per candidate string

### Approach 2: Two Pointers with One Skip (Optimized)

Scan inward and, on the first mismatch, test the two remainders in place. This is the version implemented in the repository.

```java
public static boolean validPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;
    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) {
            return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right - 1);
        }
        left++;
        right--;
    }
    return true;
}

private static boolean isPalindrome(String s, int left, int right) {
    while (left < right) {
        if (s.charAt(left++) != s.charAt(right--)) {
            return false;
        }
    }
    return true;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Deletion | O(n^2) | O(n) per candidate string |
| 2 | Two Pointers with One Skip (Optimized) | O(n) | O(1) |

## Source Code

### ValidPalindromeII.java

```java
public class ValidPalindromeII {

    public static void main(String[] args) {
        String[] inputs = {"aba", "abca", "abc", "deeee", "a", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + validPalindrome(s));
            System.out.println();
        }
    }

    public static boolean validPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right - 1);
            }
            left++;
            right--;
        }
        return true;
    }

    private static boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: walk two pointers inward while the characters match. At the first mismatch there is exactly one chance to delete a character: either the left one or the right one. So check whether the substring without `s[left]` (`left+1..right`) or the substring without `s[right]` (`left..right-1`) is a palindrome. Characters before the mismatch were already matched and are never re-checked.

**Input:** `s = "abca"`.

| Step | `left` | `right` | `s[left]`, `s[right]` | Action |
|------|--------|---------|-----------------------|--------|
| 1 | 0 | 3 | `a`, `a` | equal, move to 1, 2 |
| 2 | 1 | 2 | `b`, `c` | mismatch: try `isPalindrome(s, 2, 2)` (delete `b`) |
| 3 | helper | | `left = 2`, `right = 2` | `left < right` is false, returns `true` |

The `||` stops here, so the second option is not evaluated. Deleting `b` leaves `"aca"`, a palindrome.

```text
index: 0 1 2 3
s:     a b c a
       L     R     a = a
         L R       b != c  -> skip b: check [2..2] -> true
```

**Edge cases.** `"aba"`, `"a"` and `""` never mismatch and return `true`. For `"abc"`: step 1 mismatches `a`/`c`, then `isPalindrome(s, 1, 2)` compares `b`/`c` (false) and `isPalindrome(s, 0, 1)` compares `a`/`b` (false), so the result is `false`. `"deeee"` mismatches `d`/`e`; dropping the left `d` leaves `"eeee"`, so `true`. A `null` string would throw `NullPointerException`.

**Result:** `true`, matching the class output.
