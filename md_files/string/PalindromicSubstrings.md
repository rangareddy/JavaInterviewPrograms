# Palindromic Substrings

Given a string `s`, return the number of palindromic substrings in it. Substrings with different start or end positions are counted separately even if they have the same characters.

## Algorithm Strategy (Manacher's Algorithm)

1. Guard first: `null` and empty strings have 0 palindromic substrings.
2. Insert a separator between all characters and add sentinels at both ends (`"aaa"` becomes `"^#a#a#a#$"`), so every palindrome is an odd-length palindrome around one center.
3. `radius[i]` is the palindrome radius around center `i`; it equals the length of the longest palindrome there in the original string. Reuse the mirror position of the rightmost palindrome: `radius[i] = min(right - i, radius[2 * center - i])`, then expand further and move the boundary if needed.
4. A center with radius `r` contributes `(r + 1) / 2` palindromic substrings (the palindromes of length `r`, `r - 2`, ... that are centered there and start and end on a real character).
5. The sum over all centers is the answer.

## Example walkthrough

- For `"aaa"` the new string is `^#a#a#a#$`; the radii of the seven centers between the sentinels are `0, 1, 2, 3, 2, 1, 0`, which contribute `(r + 1) / 2 = 0, 1, 1, 2, 1, 1, 0`.
- Result: `6`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — The right boundary only moves forward, so the total number of expansion steps is linear.
- **Space Complexity:** `O(n)` — The transformed string and the radius array.

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

## Source Code

### PalindromicSubstrings.java

```java
public class PalindromicSubstrings {

    public static void main(String[] args) {
        String[] inputs = {"abc", "aaa", "abba", "", null};
        for (String s : inputs) {
            System.out.println("Input  : s = " + (s == null ? "null" : "\"" + s + "\""));
            System.out.println("Output : " + countSubstrings(s));
            System.out.println();
        }
    }

    public static int countSubstrings(String s) {
        // Guard Clause: nothing to count
        if (s == null || s.isEmpty()) {
            return 0;
        }

        StringBuilder sb = new StringBuilder("^");
        for (int i = 0; i < s.length(); i++) {
            sb.append('#').append(s.charAt(i));
        }
        String t = sb.append("#$").toString();

        int n = t.length(), center = 0, right = 0, count = 0;
        int[] radius = new int[n];
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
            count += (radius[i] + 1) / 2;
        }
        return count;
    }
}
```

## Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

The idea: the same transformed string and mirror trick as for the longest palindrome, but instead of keeping the maximum, every position contributes the number of palindromic substrings centred there. With radius `r`, the palindromes centred there have lengths `r`, `r - 2`, ... down to 1 or 2, which is `(r + 1) / 2` of them.

**Input:** `s = "aaa"`, which becomes `t = ^#a#a#a#$`.

| `i` | `t[i]` | `radius[i]` | `(radius + 1) / 2` |
|-----|--------|-------------|--------------------|
| 1 | `#` | 0 | 0 |
| 2 | `a` | 1 | 1 |
| 3 | `#` | 2 | 1 |
| 4 | `a` | 3 | 2 |
| 5 | `#` | 2 | 1 |
| 6 | `a` | 1 | 1 |
| 7 | `#` | 0 | 0 |

The counts add up to 6: the three single letters, `aa` twice and `aaa` once.

**A second input:** `s = "abba"` becomes `^#a#b#b#a#$`, the counts per position are 0 + 1 + 0 + 1 + 2 + 1 + 0 + 1 + 0, so the total is 6: `a`, `b`, `b`, `a`, `bb` and `abba`.

**Edge cases.** `null` and the empty string return 0 through the guard clause. A string of different letters, such as `abc`, has only the single-letter palindromes, so the answer is its length (3). Like the longest-palindrome version, the boundary `right` only moves forward, so the whole count is linear.

**Result:** `6` for `"aaa"` and `6` for `"abba"`, matching the class output.
