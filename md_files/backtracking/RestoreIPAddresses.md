# Restore IP Addresses

A valid IPv4 address has four integers (segments) between 0 and 255 separated by dots, with no leading zeros in a segment (except the segment `0` itself). Given a string `s` of digits, return all valid IP addresses that can be formed by inserting dots into `s` without reordering or removing digits, in any order.

## Algorithm Strategy (Backtracking over 1 to 3 Digit Segments)

1. Build the address one segment at a time, keeping the start index in `s` and the number of segments placed.
2. At each step try segment lengths 1, 2 and 3, as long as they fit in the remaining string.
3. Reject a segment with a leading zero (length above 1) or a value above 255.
4. When four segments are placed and the whole string is consumed, add the address; prune early if the remaining digits cannot fill the remaining segments.

## Example walkthrough

- For `25525511135` the valid splits are `255.255.11.135` and `255.255.111.35`.
- Splits such as `255.255.1.1135` fail because a segment would exceed 255.

## Complexity Analysis

- **Time Complexity:** `O(1)` — At most 3^4 = 81 combinations are explored, because an address has four segments of at most three digits.
- **Space Complexity:** `O(1)` — The path and the (bounded) result.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Three Nested Loops over the Dot Positions

Choose the positions of the three dots with three loops and validate each segment.

```java
public static java.util.List<String> loops(String s) {
    java.util.List<String> out = new java.util.ArrayList<>(); int n = s.length();
    for (int a = 1; a <= 3; a++) for (int b = a + 1; b <= a + 3; b++) for (int c = b + 1; c <= b + 3 && c < n; c++) {
        if (n - c > 3 || n - c < 1) continue;
        String[] p = {s.substring(0, a), s.substring(a, b), s.substring(b, c), s.substring(c)}; boolean ok = true;
        for (String x : p) if (x.isEmpty() || (x.length() > 1 && x.charAt(0) == '0') || Integer.parseInt(x) > 255) ok = false;
        if (ok) out.add(String.join(".", p));
    }
    return out;
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 2: Backtracking over Segment Lengths (Optimized)

Place one segment at a time and prune impossible suffixes. This is the version implemented in the repository.

```java
public static java.util.List<String> backtrack(String s) {
    java.util.List<String> out = new java.util.ArrayList<>(); if (s.length() < 4 || s.length() > 12) return out; go(s, 0, new java.util.ArrayList<>(), out); return out;
}
private static void go(String s, int st, java.util.List<String> parts, java.util.List<String> out) {
    int rp = 4 - parts.size(), rd = s.length() - st; if (rd < rp || rd > rp * 3) return;
    if (parts.size() == 4) { out.add(String.join(".", parts)); return; }
    for (int len = 1; len <= 3 && st + len <= s.length(); len++) { String p = s.substring(st, st + len); if ((p.length() > 1 && p.charAt(0) == '0') || Integer.parseInt(p) > 255) continue; parts.add(p); go(s, st + len, parts, out); parts.remove(parts.size() - 1); }
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Three Nested Loops over the Dot Positions | O(1) | O(1) |
| 2 | Backtracking over Segment Lengths (Optimized) | O(1) | O(1) |

## Source Code

### RestoreIPAddresses.java

```java
import java.util.ArrayList;
import java.util.List;

public class RestoreIPAddresses {

    public static void main(String[] args) {
        String[] inputs = {"25525511135", "0000", "101023", "1111", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + restoreIpAddresses(s));
            System.out.println();
        }
    }

    public static List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() < 4 || s.length() > 12) {
            return result;
        }
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(String s, int start, List<String> parts, List<String> result) {
        int remainingParts = 4 - parts.size();
        int remainingDigits = s.length() - start;
        if (remainingDigits < remainingParts || remainingDigits > remainingParts * 3) {
            return;
        }
        if (parts.size() == 4) {
            result.add(String.join(".", parts));
            return;
        }
        for (int len = 1; len <= 3 && start + len <= s.length(); len++) {
            String part = s.substring(start, start + len);
            if ((part.length() > 1 && part.charAt(0) == '0') || Integer.parseInt(part) > 255) {
                continue;
            }
            parts.add(part);
            backtrack(s, start + len, parts, result);
            parts.remove(parts.size() - 1);
        }
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: an IPv4 address has exactly four segments of one to three digits, so there are only a handful of ways to cut the string. Backtracking tries each segment length, rejects invalid segments immediately, and prunes when the remaining digits cannot fit the remaining segments.

**Input:** `s = "25525511135"`.

| Segments so far | Next try | Result |
|-----------------|----------|--------|
| | `255` | ok |
| 255 | `255` | ok |
| 255.255 | `11` then `135` | `255.255.11.135` |
| 255.255 | `111` then `35` | `255.255.111.35` |
| 255.255 | `1` then `1135` | rejected, too many digits left |

**Output:** `[255.255.11.135, 255.255.111.35]`.
