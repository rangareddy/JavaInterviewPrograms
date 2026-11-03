# Valid Palindrome

A phrase is a palindrome if, after converting all uppercase letters to lowercase and removing every non-alphanumeric character, it reads the same forward and backward. Given a string `s`, return `true` if it is a palindrome.

## Algorithm Strategy (Two Pointers Skipping Non-Alphanumeric Characters)

1. Place `left` at the start and `right` at the end of the string.
2. Move `left` forward while it points to a character that is not a letter or digit, and move `right` backward in the same way.
3. Compare the two characters in lower case; if they differ, the string is not a palindrome.
4. Move both pointers inward and repeat until they cross. No cleaned copy of the string is built.

## Example walkthrough

- For `"A man, a plan, a canal: Panama"`: skipping spaces and punctuation, the pairs a-a, m-m, a-a, n-n, ... all match.
- Result: `true`. For `"race a car"` the first mismatch is `e` vs `r`: `false`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each character is visited once.
- **Space Complexity:** `O(1)` — Two pointers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Clean the String, Then Compare with Its Reverse

Build a lower-case copy that only contains letters and digits and compare it with its reverse. Easy to read; allocates two strings.

```java
public static boolean isPalindromeReverse(String s) {
    StringBuilder cleaned = new StringBuilder();
    for (char c : s.toCharArray()) {
        if (Character.isLetterOrDigit(c)) cleaned.append(Character.toLowerCase(c));
    }
    return cleaned.toString().equals(cleaned.reverse().toString());
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Regular Expression Cleanup

Strip everything that is not a letter or digit with `replaceAll` and compare the lower-cased text with its reverse. Concise but compiles a regex on every call.

```java
public static boolean isPalindromeRegex(String s) {
    String cleaned = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
    return new StringBuilder(cleaned).reverse().toString().equals(cleaned);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Recursion on the Two Ends

Compare the first and last relevant characters and recurse on the inside. The recursion depth equals half the length of the string.

```java
public static boolean isPalindromeRecursive(String s) {
    return check(s, 0, s.length() - 1);
}

private static boolean check(String s, int left, int right) {
    while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
    while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
    if (left >= right) return true;
    if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) return false;
    return check(s, left + 1, right - 1);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n) recursion stack

### Approach 4: Two Pointers (Optimized)

Walk two pointers inwards, skipping characters that are not letters or digits. No copy of the string. This is the version implemented in the repository.

```java
public static boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    while (left < right) {
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
        if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) return false;
        left++;
        right--;
    }
    return true;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Clean the String, Then Compare with Its Reverse | O(n) | O(n) |
| 2 | Regular Expression Cleanup | O(n) | O(n) |
| 3 | Recursion on the Two Ends | O(n) | O(n) recursion stack |
| 4 | Two Pointers (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### ValidPalindrome.java

```java
public class ValidPalindrome {

    public static void main(String[] args) {
        String[] inputs = {"A man, a plan, a canal: Panama", "race a car", " ", "0P", "No 'x' in Nixon"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + isPalindrome(s));
            System.out.println();
        }
    }

    public static boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }

        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
```
