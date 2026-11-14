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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Check whether every substring is a palindrome.

```java
public static int countSubstringsBrute(String s) {
    int count = 0;
    for (int i = 0; i < s.length(); i++) {
        for (int j = i; j < s.length(); j++) {
            if (isPalindrome(s, i, j)) count++;
        }
    }
    return count;
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

`dp[i][j]` is true when `s[i..j]` is a palindrome: the ends match and the inside `dp[i + 1][j - 1]` is a palindrome (or has at most one character).

```java
public static int countSubstringsDp(String s) {
    int n = s.length(), count = 0;
    boolean[][] dp = new boolean[n][n];
    for (int i = n - 1; i >= 0; i--) {
        for (int j = i; j < n; j++) {
            dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i + 1][j - 1]);
            if (dp[i][j]) count++;
        }
    }
    return count;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Approach 3: Expand Around Every Center (Implemented)

Expand from each of the `2n - 1` centers and count every successful step. This is the version implemented in the repository.

```java
public static int countSubstrings(String s) {
    int count = 0;
    for (int center = 0; center < s.length(); center++) {
        count += expand(s, center, center);
        count += expand(s, center, center + 1);
    }
    return count;
}

private static int expand(String s, int left, int right) {
    int count = 0;
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        count++; left--; right++;
    }
    return count;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 4: Manacher's Algorithm (Optimized)

Insert separators so every palindrome has odd length and reuse the radii already computed around the rightmost palindrome. A center with radius `r` contributes `(r + 1) / 2` palindromic substrings. Linear time.

```java
public static int countSubstringsManacher(String s) {
    StringBuilder sb = new StringBuilder("^");
    for (char c : s.toCharArray()) sb.append('#').append(c);
    String t = sb.append("#$").toString();
    int n = t.length(), center = 0, right = 0, count = 0;
    int[] radius = new int[n];
    for (int i = 1; i < n - 1; i++) {
        if (i < right) radius[i] = Math.min(right - i, radius[2 * center - i]);
        while (t.charAt(i + 1 + radius[i]) == t.charAt(i - 1 - radius[i])) radius[i]++;
        if (i + radius[i] > right) { center = i; right = i + radius[i]; }
        count += (radius[i] + 1) / 2;
    }
    return count;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^3) | O(1) |
| 2 | Dynamic Programming Table | O(n^2) | O(n^2) |
| 3 | Expand Around Every Center (Implemented) | O(n^2) | O(1) |
| 4 | Manacher's Algorithm (Optimized) | O(n) | O(n) |

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

## Solution

This repository implements Approach 3 (Expand Around Every Center), marked `(Implemented)` in the Approaches section. The optimized approach on this page, Approach 4 (Manacher's Algorithm), is not implemented in the class.

The idea: every palindromic substring has a center, either one character (odd length) or the gap between two (even length). For every index, expand outward from `(c, c)` and `(c, c+1)`; every step where the two ends match is one more palindromic substring, so the number of successful steps is added to `count`.

**Input:** `s = "abba"`.

| Center | Odd expansion `(c, c)` | Even expansion `(c, c+1)` | Added | `count` |
|--------|------------------------|---------------------------|-------|---------|
| 0 | `a` matches (1); then left is out of range | `a` vs `b` no (0) | 1 | 1 |
| 1 | `b` (1); then `a` vs `b` no | `bb` (1), then `abba` (2) | 3 | 4 |
| 2 | `b` (1); then `b` vs `a` no | `b` vs `a` no (0) | 1 | 5 |
| 3 | `a` (1); then right is out of range | right is out of range (0) | 1 | 6 |

The six palindromic substrings are `a`, `b`, `b`, `a`, `bb`, `abba`.

```text
index: 0 1 2 3
s:     a b b a
center 1 (even): s[1]=s[2] -> "bb", then s[0]=s[3] -> "abba", then left < 0 stop
```

**Edge cases.** `null` or `""` returns `0`. `"abc"` returns `3` (only single letters); `"aaa"` returns `6`. Each expansion stops at the first mismatch or string end, so the time is O(n^2) in the worst case with O(1) extra space.

**Result:** `6`, matching the class output.
