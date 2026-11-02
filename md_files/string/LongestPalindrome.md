# Longest Palindromic Substring

Utility class to find the longest palindromic substring using the expand-around-center approach.

## Algorithm Strategy (Expand Around Center)

1. Every palindrome mirrors around its center. The center is a single character for odd-length palindromes (such as `"aba"`) or a pair of adjacent characters for even-length palindromes (such as `"abba"`).
2. A string of length `n` has `2n - 1` possible centers. For every index `i`, try the odd center `(i, i)` and the even center `(i, i + 1)`.
3. For each center, move `start` left and `end` right while both are inside the string and `str.charAt(start) == str.charAt(end)`.
4. When the expansion stops, `str.substring(start + 1, end)` is the longest palindrome around that center.
5. Keep the longest palindrome seen over all centers.

## Example walkthrough for `"bananas"`

- Center `i = 2` (letter `n`): expands `n -> ana` and stops at `"ana"` because `b != n`.
- Center `i = 3` (letter `a`): expands `a -> nan -> anana` (length 5, new best) and stops.
- Result: `"anana"`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — There are `2n - 1` centers and each expansion takes up to `O(n)`.
- **Space Complexity:** `O(1)` auxiliary space (excluding the result substring).

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Brute Force

Check every substring to see whether it is a palindrome and keep the longest one.

```java
public static String palindromeBrute(String s) {
    String best = s.isEmpty() ? "" : s.substring(0, 1);
    for (int i = 0; i < s.length(); i++) {
        for (int j = i + best.length(); j < s.length(); j++) {
            if (isPalindrome(s, i, j) && j - i + 1 > best.length()) best = s.substring(i, j + 1);
        }
    }
    return best;
}

private static boolean isPalindrome(String s, int i, int j) {
    while (i < j) {
        if (s.charAt(i++) != s.charAt(j--)) return false;
    }
    return true;
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(1)

### Approach 2: Dynamic Programming Table

`dp[i][j]` is true when `s[i..j]` is a palindrome: `s[i] == s[j]` and the inside `dp[i+1][j-1]` is a palindrome (or the length is at most 2). Fill the table by increasing length.

```java
public static String palindromeDp(String s) {
    int n = s.length();
    if (n < 2) return s;
    boolean[][] dp = new boolean[n][n];
    int start = 0, maxLen = 1;
    for (int len = 1; len <= n; len++) {
        for (int i = 0; i + len - 1 < n; i++) {
            int j = i + len - 1;
            dp[i][j] = s.charAt(i) == s.charAt(j) && (len <= 2 || dp[i + 1][j - 1]);
            if (dp[i][j] && len > maxLen) { start = i; maxLen = len; }
        }
    }
    return s.substring(start, start + maxLen);
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Approach 3: Expand Around Center

Every palindrome is centered on a character (odd length) or between two characters (even length). Expand outwards from each of the `2n - 1` centers. This is the version implemented in the repository: simple and `O(1)` space.

```java
public static String palindromeExpand(String s) {
    if (s == null || s.length() < 2) return s;
    int start = 0, maxLen = 1;
    for (int i = 0; i < s.length(); i++) {
        int len = Math.max(expand(s, i, i), expand(s, i, i + 1));
        if (len > maxLen) {
            maxLen = len;
            start = i - (len - 1) / 2;
        }
    }
    return s.substring(start, start + maxLen);
}

private static int expand(String s, int left, int right) {
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        left--;
        right++;
    }
    return right - left - 1;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 4: Manacher's Algorithm (Optimized)

Insert a separator between characters so every palindrome has odd length, then reuse the palindromes already found: for a position inside the rightmost palindrome, its mirror position gives a lower bound for its radius, so the centers are never re-expanded from scratch. Total work is linear.

```java
public static String palindromeManacher(String s) {
    if (s == null || s.length() < 2) return s;

    StringBuilder sb = new StringBuilder("^");
    for (char c : s.toCharArray()) sb.append('#').append(c);
    String t = sb.append("#$").toString();          // e.g. "^#a#b#a#$"

    int n = t.length();
    int[] radius = new int[n];
    int center = 0, right = 0;
    for (int i = 1; i < n - 1; i++) {
        if (i < right) radius[i] = Math.min(right - i, radius[2 * center - i]);
        while (t.charAt(i + 1 + radius[i]) == t.charAt(i - 1 - radius[i])) radius[i]++;
        if (i + radius[i] > right) { center = i; right = i + radius[i]; }
    }

    int maxLen = 0, centerIndex = 0;
    for (int i = 1; i < n - 1; i++) {
        if (radius[i] > maxLen) { maxLen = radius[i]; centerIndex = i; }
    }
    int start = (centerIndex - maxLen) / 2;
    return s.substring(start, start + maxLen);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^3) | O(1) |
| 2 | Dynamic Programming Table | O(n^2) | O(n^2) |
| 3 | Expand Around Center | O(n^2) | O(1) |
| 4 | Manacher's Algorithm (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above. The version implemented in this repository is Approach 3.

## Source Code

### LongestPalindrome.java

```java
import java.util.Arrays;
import java.util.List;

public class LongestPalindrome {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("BBABCBCAB", "aaa", "12321", "bananas");
        for (String str : list) {
            System.out.println("Input  : \"" + str + "\"");
            System.out.println("Output : \"" + getLongestPalindrome(str) + "\"");
            System.out.println();
        }
    }

    /**
     * Finds the longest palindromic substring of {@code str}.
     *
     * @param str the input string
     * @return the longest palindromic substring
     */
    private static String getLongestPalindrome(String str) {
        // Guard Clause: null, empty and single-character strings are palindromes themselves
        if (str == null || str.length() < 2) {
            return str;
        }

        String longest = str.substring(0, 1);
        int n = str.length();
        for (int i = 0; i < n; i++) {

            // get longest palindrome with center of i (odd length)
            String palindrome = intermediatePalindrome(str, i, i);
            if (palindrome.length() > longest.length()) {
                longest = palindrome;
            }

            // get longest palindrome with center of i, i+1 (even length)
            palindrome = intermediatePalindrome(str, i, i + 1);
            if (palindrome.length() > longest.length()) {
                longest = palindrome;
            }
        }
        return longest;
    }

    /**
     * Expands outwards from the given center while the characters match.
     *
     * @param str   the input string
     * @param start the left index of the center
     * @param end   the right index of the center
     * @return the palindrome found around the center
     */
    private static String intermediatePalindrome(String str, int start, int end) {
        while (start >= 0 && end < str.length() && str.charAt(start) == str.charAt(end)) {
            start--;
            end++;
        }
        return str.substring(start + 1, end);
    }
}
```
