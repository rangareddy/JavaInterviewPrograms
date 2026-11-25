# Shortest Palindrome

Given a string `s`, you can convert it to a palindrome by adding characters in front of it. Return the shortest palindrome you can find this way.

## Algorithm Strategy (KMP Prefix Function on s + # + reverse(s))

1. The answer is `reverse(suffix) + s`, where the suffix is the part of `s` that is left after removing its longest palindromic prefix.
2. Find the longest palindromic prefix: build `t = s + '#' + reverse(s)` and compute its KMP prefix function; the last value is the length of that prefix.
3. Take the remaining suffix `s.substring(len)`, reverse it and put it in front of `s`.
4. The separator `#` keeps the match from running past the end of `s`.

## Example walkthrough

- For `aacecaaa`, the longest palindromic prefix is `aacecaa` (length 7); the suffix is `a`.
- Result: `a` + `aacecaaa` = `aaacecaaa`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — The prefix function is linear.
- **Space Complexity:** `O(n)` — The combined string and the table.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Check Each Prefix

From the longest prefix down, test whether it is a palindrome; the first one found decides the answer.

```java
public static String prefixes(String s) {
    int n = s.length(); for (int len = n; len >= 0; len--) { boolean ok = true; for (int i = 0, j = len - 1; i < j; i++, j--) if (s.charAt(i) != s.charAt(j)) { ok = false; break; } if (ok) return new StringBuilder(s.substring(len)).reverse() + s; }
    return s;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: KMP Prefix Function (Optimized)

Find the longest palindromic prefix with the failure table of `s + # + reverse(s)`. This is the version implemented in the repository.

```java
public static String kmp(String s) {
    if (s.length() <= 1) return s; String t = s + "#" + new StringBuilder(s).reverse(); int[] p = new int[t.length()];
    for (int i = 1; i < t.length(); i++) { int k = p[i - 1]; while (k > 0 && t.charAt(i) != t.charAt(k)) k = p[k - 1]; if (t.charAt(i) == t.charAt(k)) k++; p[i] = k; }
    return new StringBuilder(s.substring(p[t.length() - 1])).reverse() + s;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Check Each Prefix | O(n^2) | O(n) |
| 2 | KMP Prefix Function (Optimized) | O(n) | O(n) |

## Source Code

### ShortestPalindrome.java

```java
public class ShortestPalindrome {

    public static void main(String[] args) {
        String[] inputs = {"aacecaaa", "abcd", "", "a", "abb"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + shortestPalindrome(s));
            System.out.println();
        }
    }

    public static String shortestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s == null ? "" : s;
        }
        String reversed = new StringBuilder(s).reverse().toString();
        String combined = s + "#" + reversed;
        int[] prefix = new int[combined.length()];
        for (int i = 1; i < combined.length(); i++) {
            int k = prefix[i - 1];
            while (k > 0 && combined.charAt(i) != combined.charAt(k)) {
                k = prefix[k - 1];
            }
            if (combined.charAt(i) == combined.charAt(k)) {
                k++;
            }
            prefix[i] = k;
        }
        int palindromeLength = prefix[combined.length() - 1];
        return new StringBuilder(s.substring(palindromeLength)).reverse() + s;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: whatever is added in front mirrors the part of `s` that is not already covered by a palindrome at the start. So the shortest answer comes from the longest palindromic prefix. A prefix of `s` is a palindrome exactly when it equals a suffix of `reverse(s)`, which is what the KMP prefix function of `s + "#" + reverse(s)` measures.

**Input:** `s = "aacecaaa"`.

| Step | Value |
|------|-------|
| combined string | `aacecaaa#aaacecaa` |
| last prefix-function value | 7 (the prefix `aacecaa` matches the end) |
| remaining suffix | `a` |
| reversed suffix + `s` | `a` + `aacecaaa` |

**Output:** `aaacecaaa`.
