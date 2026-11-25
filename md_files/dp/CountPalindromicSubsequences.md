# Count Palindromic Subsequences

Given a string `s` of digits, return the number of palindromic subsequences of length 5 (the subsequence may contain leading zeros), modulo `10^9 + 7`. A subsequence is obtained by deleting characters without changing the order of the rest.

## Algorithm Strategy (Prefix Pairs and Suffix Pairs)

1. A palindrome of length 5 looks like `a b c b a`. Fix the middle index `i`.
2. Maintain `pre[a][b]`: the number of subsequences `a b` (in that order) in `s[0..i-1]`, and `suf[b][a]`: the number of subsequences `b a` in `s[i+1..]`.
3. For the middle position, the count is `sum over a, b of pre[a][b] * suf[b][a]`.
4. Update `pre` as the index moves right (adding `s[i]` as the second digit for every first digit) and remove `s[i]` from `suf`. Sum over all middle positions.

## Example walkthrough

- For `103301`: the palindromic subsequences of length 5 are `10301`, `10301` (a second time using the other 3)... in total 2.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n * 100)` — 100 digit pairs per position.
- **Space Complexity:** `O(100)` — Two 10 x 10 count tables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force over All Subsequences of Length 5

Choose five indexes and check whether the digits form a palindrome.

```java
public static int brute(String s) {
    int n = s.length(); long c = 0;
    for (int a = 0; a < n; a++) for (int b = a + 1; b < n; b++) for (int m = b + 1; m < n; m++) for (int d = m + 1; d < n; d++) for (int e = d + 1; e < n; e++) if (s.charAt(a) == s.charAt(e) && s.charAt(b) == s.charAt(d)) c++;
    return (int) (c % 1_000_000_007L);
}
```

- **Time Complexity:** O(n^5)
- **Space Complexity:** O(1)

### Approach 2: Prefix and Suffix Pair Counts (Optimized)

Count `a b` pairs on the left and `b a` pairs on the right of each middle position. This is the version implemented in the repository.

```java
public static int pairs(String s) {
    int n = s.length(); if (n < 5) return 0; final long M = 1_000_000_007L; long[][] pre = new long[10][10], suf = new long[10][10]; int[] sc = new int[10], seen = new int[10];
    for (int i = n - 1; i >= 0; i--) { int d = s.charAt(i) - '0'; for (int x = 0; x < 10; x++) suf[d][x] = (suf[d][x] + sc[x]) % M; sc[d]++; }
    long total = 0;
    for (int i = 0; i < n; i++) { int d = s.charAt(i) - '0'; sc[d]--; for (int x = 0; x < 10; x++) suf[d][x] = ((suf[d][x] - sc[x]) % M + M) % M;
        for (int a = 0; a < 10; a++) for (int b = 0; b < 10; b++) total = (total + pre[a][b] * suf[b][a]) % M;
        for (int x = 0; x < 10; x++) pre[x][d] = (pre[x][d] + seen[x]) % M; seen[d]++; }
    return (int) total;
}
```

- **Time Complexity:** O(n * 100)
- **Space Complexity:** O(100)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force over All Subsequences of Length 5 | O(n^5) | O(1) |
| 2 | Prefix and Suffix Pair Counts (Optimized) | O(n * 100) | O(100) |

## Source Code

### CountPalindromicSubsequences.java

```java
import java.util.Arrays;

public class CountPalindromicSubsequences {

    public static void main(String[] args) {
        String[] inputs = {"103301", "0000000", "9999900000", "123"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + countPalindromes(s));
            System.out.println();
        }
    }

    private static final long MOD = 1_000_000_007L;

    public static int countPalindromes(String s) {
        if (s == null || s.length() < 5) {
            return 0;
        }
        int n = s.length();
        long[][] pre = new long[10][10];
        long[][] suf = new long[10][10];
        int[] preCount = new int[10];
        int[] sufCount = new int[10];
        for (int i = n - 1; i >= 0; i--) {
            int d = s.charAt(i) - '0';
            for (int x = 0; x < 10; x++) {
                suf[d][x] = (suf[d][x] + sufCount[x]) % MOD;
            }
            sufCount[d]++;
        }
        long total = 0;
        int[] seen = new int[10];
        for (int i = 0; i < n; i++) {
            int d = s.charAt(i) - '0';
            sufCount[d]--;
            for (int x = 0; x < 10; x++) {
                suf[d][x] = ((suf[d][x] - sufCount[x]) % MOD + MOD) % MOD;
            }
            for (int a = 0; a < 10; a++) {
                for (int b = 0; b < 10; b++) {
                    total = (total + pre[a][b] * suf[b][a]) % MOD;
                }
            }
            for (int x = 0; x < 10; x++) {
                pre[x][d] = (pre[x][d] + seen[x]) % MOD;
            }
            seen[d]++;
        }
        return (int) total;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a length-5 palindrome `a b c b a` is determined by its middle digit `c` and two pairs: the left half `a b` before the middle and the right half `b a` after it. Counting those pairs on each side with tables indexed by the two digits turns a five-way choice into a sum of 100 products per middle position.

**Input:** `s = "103301"`.

| Middle index | Left pairs `a b` available | Right pairs `b a` available | Matches |
|--------------|-----------------------------|------------------------------|---------|
| 2 (`3`) | `10`, `13`, `03` | `30`, `31`, `01` | `10` with `01`: 1 |
| 3 (`3`) | `10`, `13`, `03`, `33`... | `01` | `10` with `01`: 1 |

**Output:** `2`.
