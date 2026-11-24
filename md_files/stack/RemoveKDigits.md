# Remove K Digits

Given a string `num` representing a non-negative integer and an integer `k`, remove `k` digits so that the remaining number is the smallest possible. Return it as a string without leading zeros, or `"0"` if nothing is left.

## Algorithm Strategy (Monotonic Increasing Stack)

1. Scan the digits left to right with a stack. When the current digit is smaller than the digit on top, the top digit is a peak that makes the number bigger, so pop it (using one removal) while removals remain.
2. Push the current digit.
3. After the scan, if removals remain, drop digits from the end: the stack is non-decreasing, so the tail is the largest part.
4. Strip leading zeros and return `"0"` for an empty result.

## Example walkthrough

- For `"1432219"` and `k = 3`: 4 is popped when 3 arrives, 3 is popped when the first 2 arrives, and the older 2 is popped when the last 1 arrives, leaving `1219`.
- Result: `"1219"`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each digit is pushed and popped at most once.
- **Space Complexity:** `O(n)` — The stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Remove the First Peak k Times

Repeat `k` times: delete the first digit that is larger than its successor (or the last digit).

```java
public static String peakRemoval(String num, int k) {
    StringBuilder s = new StringBuilder(num);
    for (int r = 0; r < k && s.length() > 0; r++) {
        int i = 0;
        while (i < s.length() - 1 && s.charAt(i) <= s.charAt(i + 1)) i++;
        s.deleteCharAt(i);
    }
    int st = 0; while (st < s.length() - 1 && s.charAt(st) == '0') st++;
    return s.length() == 0 ? "0" : s.substring(st);
}
```

- **Time Complexity:** O(n * k)
- **Space Complexity:** O(n)

### Approach 2: Brute Force over Subsets

Try every way of keeping `n - k` digits and take the smallest number. Only for tiny inputs.

```java
public static String bruteForce(String num, int k) {
    int keep = num.length() - k; if (keep <= 0) return "0";
    long best = Long.MAX_VALUE;
    for (int mask = 0; mask < (1 << num.length()); mask++) {
        if (Integer.bitCount(mask) != keep) continue;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num.length(); i++) if ((mask >> i & 1) == 1) sb.append(num.charAt(i));
        best = Math.min(best, Long.parseLong(sb.toString()));
    }
    return Long.toString(best);
}
```

- **Time Complexity:** O(2^n * n)
- **Space Complexity:** O(n)

### Approach 3: Monotonic Stack (Optimized)

Pop larger digits when a smaller one arrives. This is the version implemented in the repository.

```java
public static String stack(String num, int k) {
    if (num.length() <= k) return "0";
    StringBuilder st = new StringBuilder();
    for (char c : num.toCharArray()) {
        while (k > 0 && st.length() > 0 && st.charAt(st.length() - 1) > c) { st.deleteCharAt(st.length() - 1); k--; }
        st.append(c);
    }
    st.setLength(st.length() - k);
    int s = 0; while (s < st.length() - 1 && st.charAt(s) == '0') s++;
    return st.substring(s);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Remove the First Peak k Times | O(n * k) | O(n) |
| 2 | Brute Force over Subsets | O(2^n * n) | O(n) |
| 3 | Monotonic Stack (Optimized) | O(n) | O(n) |

## Source Code

### RemoveKDigits.java

```java
public class RemoveKDigits {

    public static void main(String[] args) {
        String[] nums = {"1432219", "10200", "10", "112", "9"};
        int[] ks = {3, 1, 2, 1, 1};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : num = \"" + nums[i] + "\", k = " + ks[i]);
            System.out.println("Output : \"" + removeKdigits(nums[i], ks[i]) + "\"");
            System.out.println();
        }
    }

    public static String removeKdigits(String num, int k) {
        if (num == null || num.length() <= k) {
            return "0";
        }
        StringBuilder stack = new StringBuilder();
        for (char c : num.toCharArray()) {
            while (k > 0 && stack.length() > 0 && stack.charAt(stack.length() - 1) > c) {
                stack.deleteCharAt(stack.length() - 1);
                k--;
            }
            stack.append(c);
        }
        stack.setLength(stack.length() - k);
        int start = 0;
        while (start < stack.length() - 1 && stack.charAt(start) == '0') {
            start++;
        }
        return stack.substring(start);
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: a digit that is larger than the digit after it should go first, because removing it lowers the number more than removing anything to its right. A stack that stays non-decreasing does exactly that in one pass.

**Input:** `num = "1432219"`, `k = 3`.

| Digit | Stack before | Pops | Stack after | `k` left |
|-------|--------------|------|-------------|----------|
| 1 | | | 1 | 3 |
| 4 | 1 | | 14 | 3 |
| 3 | 14 | 4 | 13 | 2 |
| 2 | 13 | 3 | 12 | 1 |
| 2 | 12 | | 122 | 1 |
| 1 | 122 | 2 | 121 | 0 |
| 9 | 121 | | 1219 | 0 |

**Output:** `"1219"`.
