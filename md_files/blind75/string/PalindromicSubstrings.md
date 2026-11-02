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

### Approach 3: Expand Around Every Center

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
| 3 | Expand Around Every Center | O(n^2) | O(1) |
| 4 | Manacher's Algorithm (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above. The version implemented in this repository is Approach 3.

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
