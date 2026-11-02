# Palindromic Substrings

Given a string `s`, return the number of palindromic substrings in it. Substrings with different start or end positions are counted separately even if they have the same characters.

## Algorithm Strategy (Expand Around Every Center)

1. Every palindrome has a center: one character (odd length) or two adjacent characters (even length). There are `2n - 1` centers.
2. For each center, expand outwards while the characters on both sides are equal.
3. Every successful expansion step is one more palindromic substring, so add 1 to the counter each time.
4. Return the counter after all centers have been processed.

## Example walkthrough

- For `"aaa"`: the centers produce `a, a, a` (3 single letters), `aa, aa` (2) and `aaa` (1).
- Result: `6`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — `2n - 1` centers, each expansion up to `O(n)`.
- **Space Complexity:** `O(1)` — Only counters.

## Source Code

### PalindromicSubstrings.java

```java
public class PalindromicSubstrings {

    public static void main(String[] args) {
        String[] inputs = {"abc", "aaa", "abba"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + countSubstrings(s));
            System.out.println();
        }
    }

    public static int countSubstrings(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (int center = 0; center < s.length(); center++) {
            count += expand(s, center, center);         // odd length palindromes
            count += expand(s, center, center + 1);     // even length palindromes
        }
        return count;
    }

    private static int expand(String s, int left, int right) {
        int count = 0;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++;
            left--;
            right++;
        }
        return count;
    }
}
```
