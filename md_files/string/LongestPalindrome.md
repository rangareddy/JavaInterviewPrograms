# Longest Palindromic Substring

Utility class to find the longest palindromic substring using the expand-around-center approach.

## Algorithm Strategy (Manacher's Algorithm)

1. Guard first: `null`, empty and single-character strings are returned as they are.
2. Insert a separator between all characters and add sentinels at both ends, for example `"aba"` becomes `"^#a#b#a#$"`. Every palindrome of the original string is now an odd-length palindrome around one center, and the sentinels stop the expansion at the borders without bounds checks.
3. `radius[i]` is the palindrome radius around center `i` in the new string; it equals the length of the palindrome in the original string.
4. Keep the palindrome that reaches furthest to the right (`center`, `right`). For a position `i` inside it, the mirror position `2 * center - i` already gives a lower bound: `radius[i] = min(right - i, radius[mirror])`. Only then expand further, and move `center` and `right` when the new palindrome reaches beyond `right`.
5. The largest radius is the longest palindrome; convert its center back with `start = (centerIndex - maxLen) / 2`.

## Example walkthrough for `"bananas"`

- The new string is `^#b#a#n#a#n#a#s#$`. The position of the middle letter `a` (index 8) expands to radius 5, which is `"anana"`.
- The positions to the right of it (the second `n` and the last `a`) start from the radius of their mirror position instead of from 0, so they are not expanded again from scratch.
- Result: `"anana"`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — The right boundary only moves forward, so the total number of expansion steps is linear.
- **Space Complexity:** `O(n)` — The transformed string and the radius array.

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

## Source Code

### LongestPalindrome.java

```java
import java.util.Arrays;
import java.util.List;

public class LongestPalindrome {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("BBABCBCAB", "aaa", "12321", "bananas", "abba", "a", "");
        for (String str : list) {
            System.out.println("Input  : \"" + str + "\"");
            System.out.println("Output : \"" + getLongestPalindrome(str) + "\"");
            System.out.println();
        }
        System.out.println("Input  : null");
        System.out.println("Output : " + getLongestPalindrome(null));
    }

    /**
     * Finds the longest palindromic substring of {@code str} (the first one when several have the same length).
     *
     * @param str the input string
     * @return the longest palindromic substring; {@code null} for {@code null} input, and the input itself when it has fewer than two characters
     */
    private static String getLongestPalindrome(String str) {
        // Guard Clause: null, empty and single-character strings are palindromes themselves
        if (str == null || str.length() < 2) {
            return str;
        }

        StringBuilder sb = new StringBuilder("^");
        for (int i = 0; i < str.length(); i++) {
            sb.append('#').append(str.charAt(i));
        }
        String t = sb.append("#$").toString();          // e.g. "^#a#b#a#$"

        int n = t.length();
        int[] radius = new int[n];
        int center = 0, right = 0;
        for (int i = 1; i < n - 1; i++) {
            if (i < right) {
                radius[i] = Math.min(right - i, radius[2 * center - i]);
            }
            while (t.charAt(i + 1 + radius[i]) == t.charAt(i - 1 - radius[i])) {
                radius[i]++;
            }
            if (i + radius[i] > right) {
                center = i;
                right = i + radius[i];
            }
        }

        int maxLen = 0, centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (radius[i] > maxLen) {
                maxLen = radius[i];
                centerIndex = i;
            }
        }
        int start = (centerIndex - maxLen) / 2;
        return str.substring(start, start + maxLen);
    }
}
```

## Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

The idea: put a `#` between all characters (and sentinels `^` and `$` at the ends) so every palindrome has odd length, then compute the radius of the palindrome around every position. A position inside an already known palindrome starts from the radius of its mirror position instead of from zero, so no character is compared again and again.

**Input:** `str = "bananas"`, which becomes `t = ^#b#a#n#a#n#a#s#$` (length 17). `radius[i]` is the palindrome radius around position `i`; the table shows how it was found.

| `i` | `t[i]` | Start value | Final `radius[i]` | New boundary |
|-----|--------|-------------|-------------------|--------------|
| 1 | `#` | expand from 0 | 0 | center=1, right=1 |
| 2 | `b` | expand from 0 | 1 | center=2, right=3 |
| 3 | `#` | expand from 0 | 0 |  |
| 4 | `a` | expand from 0 | 1 | center=4, right=5 |
| 5 | `#` | expand from 0 | 0 |  |
| 6 | `n` | expand from 0 | 3 | center=6, right=9 |
| 7 | `#` | mirror 5 gives 0 | 0 |  |
| 8 | `a` | mirror 4 gives 1 | 5 | center=8, right=13 |
| 9 | `#` | mirror 7 gives 0 | 0 |  |
| 10 | `n` | mirror 6 gives 3 | 3 |  |
| 11 | `#` | mirror 5 gives 0 | 0 |  |
| 12 | `a` | mirror 4 gives 1 | 1 |  |
| 13 | `#` | expand from 0 | 0 |  |
| 14 | `s` | expand from 0 | 1 | center=14, right=15 |
| 15 | `#` | expand from 0 | 0 |  |

The largest radius is 5 at position 8 (the letter `a`), so the palindrome starts at `(ci - maxLen) / 2 = (8 - 5) / 2 = 1` and has length 5: `"anana"`.

**Why it is linear.** `right` only moves forward. A position inside the boundary starts from a known radius, and the `while` loop only compares characters beyond `right`, which is new ground; each character is therefore passed once by the expansion. The expand-around-center version restarts at every center, which makes it quadratic on strings like `aaaa...`.

**Edge cases.** `null`, the empty string and one-character strings are returned as they are. For `"abba"` the even centre is a separator in `t`, and the radius there is 4. When several palindromes have the same length, the first one (smallest centre) wins, because only a strictly larger radius replaces the best.

**Result:** `"anana"`, matching the class output.
