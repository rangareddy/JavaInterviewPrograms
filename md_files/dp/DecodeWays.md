# Decode Ways

A message of letters A-Z is encoded as numbers ('A' = 1, 'B' = 2, ..., 'Z' = 26). Given a string of digits, return the number of ways to decode it.

## Algorithm Strategy (Dynamic Programming over Prefixes)

1. Let `dp[i]` be the number of ways to decode the first `i` digits; `dp[0] = 1` (the empty string).
2. One digit: if `s[i - 1]` is not '0', it can be decoded alone, so add `dp[i - 1]`. A '0' cannot be decoded alone.
3. Two digits: if the number formed by `s[i - 2]` and `s[i - 1]` is between 10 and 26, add `dp[i - 2]`.
4. Only the last two values are needed, so they are kept in two variables. The answer is the last value (0 means the string cannot be decoded).

## Example walkthrough

- For `"226"`: `"2 2 6"` (BBF), `"22 6"` (VF) and `"2 26"` (BZ) are valid, so the result is `3`.
- For `"06"`: a leading zero can neither stand alone nor pair with a previous digit, so the result is `0`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single pass.
- **Space Complexity:** `O(1)` — Two variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Recursion

At each position decode one digit (if it is not `0`) or two digits (if they form 10 to 26) and add up the results. Exponential.

```java
public static int numDecodingsRecursive(String s) {
    return solve(s, 0);
}

private static int solve(String s, int index) {
    if (index == s.length()) return 1;
    if (s.charAt(index) == '0') return 0;
    int ways = solve(s, index + 1);
    if (index + 1 < s.length() && Integer.parseInt(s.substring(index, index + 2)) <= 26) ways += solve(s, index + 2);
    return ways;
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Memoization (Top-Down DP)

Cache the number of ways to decode the suffix starting at each index.

```java
public static int numDecodingsMemo(String s) {
    return solve(s, 0, new Integer[s.length()]);
}

private static int solve(String s, int index, Integer[] memo) {
    if (index == s.length()) return 1;
    if (s.charAt(index) == '0') return 0;
    if (memo[index] != null) return memo[index];
    int ways = solve(s, index + 1, memo);
    if (index + 1 < s.length() && Integer.parseInt(s.substring(index, index + 2)) <= 26) ways += solve(s, index + 2, memo);
    return memo[index] = ways;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Tabulation (Bottom-Up DP)

`dp[i]` is the number of ways to decode the first `i` digits.

```java
public static int numDecodingsTable(String s) {
    int n = s.length();
    if (n == 0 || s.charAt(0) == '0') return 0;
    int[] dp = new int[n + 1];
    dp[0] = 1;
    dp[1] = 1;
    for (int i = 2; i <= n; i++) {
        if (s.charAt(i - 1) != '0') dp[i] += dp[i - 1];
        int two = Integer.parseInt(s.substring(i - 2, i));
        if (two >= 10 && two <= 26) dp[i] += dp[i - 2];
    }
    return dp[n];
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Two Variables (Optimized)

Only `dp[i - 1]` and `dp[i - 2]` are used, so keep them in two variables. This is the version implemented in the repository.

```java
public static int numDecodings(String s) {
    if (s == null || s.isEmpty() || s.charAt(0) == '0') return 0;
    int prev2 = 1, prev1 = 1;
    for (int i = 2; i <= s.length(); i++) {
        int current = 0;
        if (s.charAt(i - 1) != '0') current += prev1;
        int two = Integer.parseInt(s.substring(i - 2, i));
        if (two >= 10 && two <= 26) current += prev2;
        prev2 = prev1;
        prev1 = current;
    }
    return prev1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Recursion | O(2^n) | O(n) |
| 2 | Memoization (Top-Down DP) | O(n) | O(n) |
| 3 | Tabulation (Bottom-Up DP) | O(n) | O(n) |
| 4 | Two Variables (Optimized) | O(n) | O(1) |

## Source Code

### DecodeWays.java

```java
public class DecodeWays {

    public static void main(String[] args) {
        String[] inputs = {"12", "226", "06", "11106", "10"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + numDecodings(s));
            System.out.println();
        }
    }

    public static int numDecodings(String s) {
        if (s == null || s.isEmpty() || s.charAt(0) == '0') {
            return 0;
        }

        int prev2 = 1;      // dp[i - 2]
        int prev1 = 1;      // dp[i - 1]
        for (int i = 2; i <= s.length(); i++) {
            int current = 0;
            if (s.charAt(i - 1) != '0') {
                current += prev1;                       // decode the last digit alone
            }
            int twoDigits = Integer.parseInt(s.substring(i - 2, i));
            if (twoDigits >= 10 && twoDigits <= 26) {
                current += prev2;                       // decode the last two digits together
            }
            prev2 = prev1;
            prev1 = current;
        }
        return prev1;
    }
}
```

## Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

The idea: let `dp[i]` be the number of ways to decode the first `i` characters, with `dp[0] = 1` (the empty prefix) and `dp[1] = 1` (the first character is not `'0'`, checked at the start). The last character can be decoded alone if it is not `'0'` (adds `dp[i-1]`), and the last two characters can be decoded together if they form a number from 10 to 26 (adds `dp[i-2]`). Only the previous two values are needed, so `prev2` (= `dp[i-2]`) and `prev1` (= `dp[i-1]`) replace the array: O(n) time, O(1) space.

**Input:** `s = "226"`. Start: `prev2 = 1`, `prev1 = 1`. The loop runs for `i = 2 .. 3`.

| `i` | Last digit alone (`s[i-1]`) | Last two digits (`s[i-2..i-1]`) | `current` | `prev2` after | `prev1` after |
|-----|-----------------------------|-----------------------------------|-----------|---------------|---------------|
| 2 | `2` is not `0`: add `prev1` = 1 | `22` is in 10..26: add `prev2` = 1 | 2 | 1 | 2 |
| 3 | `6` is not `0`: add `prev1` = 2 | `26` is in 10..26: add `prev2` = 1 | 3 | 2 | 3 |

```text
prefix:   ""   "2"   "22"   "226"
dp:        1     1      2      3
decodings of "226": 2|2|6 (BBF), 22|6 (VF), 2|26 (BZ)
```

The method returns `prev1 = 3`.

**Zeros.** For `"11106"` the values of `current` for `i = 2 .. 5` are 2, 3, 2, 2: at `i = 4` the last digit `0` cannot stand alone, so only the pair `10` counts (`prev2` = 2); at `i = 5` the pair `06` is below 10 and is rejected, so only the digit `6` counts (`prev1` = 2). The result is `2`. For `"10"` the digit `0` is alone not allowed but `10` is, so `current = prev2 = 1` and the result is `1`.

**Edge cases.** `null`, an empty string, or a string starting with `'0'` (such as `"06"`) returns `0` at once. A single non-zero digit never enters the loop and returns `1`. A `0` that is not preceded by `1` or `2` (such as `"30"`) makes `current = 0`, which then propagates and the result is `0`.

**Result:** `3` for the traced input, matching the class output (`2`, `3`, `0`, `2`, `1` for its five samples).
