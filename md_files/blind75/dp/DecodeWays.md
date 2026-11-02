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
