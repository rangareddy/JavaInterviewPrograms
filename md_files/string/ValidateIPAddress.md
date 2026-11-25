# Validate IP Address

Given a string `queryIP`, return `"IPv4"` if it is a valid IPv4 address, `"IPv6"` if it is a valid IPv6 address, and `"Neither"` otherwise. A valid IPv4 address has four decimal parts in 0..255 without leading zeros, separated by dots. A valid IPv6 address has eight groups of one to four hexadecimal digits (either case) separated by colons.

## Algorithm Strategy (Split and Validate by Type)

1. If the string contains a dot (and no colon), treat it as IPv4: split on `.` with limit -1, require 4 parts, and check each is 1-3 digits, has no leading zero (unless it is `0`) and is at most 255.
2. If it contains a colon, treat it as IPv6: split on `:` with limit -1, require 8 parts, and check each is 1-4 hex digits.
3. Splitting with a negative limit keeps trailing empty parts, so `1.1.1.1.` is rejected.
4. Anything else is `"Neither"`.

## Example walkthrough

- `172.16.254.1` has four valid decimal parts: IPv4.
- `2001:0db8:85a3:0:0:8A2E:0370:7334` has eight valid hex groups: IPv6.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over the characters.
- **Space Complexity:** `O(n)` — The split parts.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Regular Expressions

Two patterns, one for each address type. Compact, but the patterns are easy to get subtly wrong.

```java
public static String regex(String s) {
    String seg4 = "(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])";
    if (s.matches(seg4 + "(\\." + seg4 + "){3}")) return "IPv4";
    if (s.matches("([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}")) return "IPv6";
    return "Neither";
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Split and Validate Each Group (Optimized)

Split on the separator with a negative limit and check every part by hand. This is the version implemented in the repository.

```java
public static String manual(String s) {
    if (s.contains(".") && !s.contains(":")) { String[] p = s.split("\\.", -1); if (p.length != 4) return "Neither";
        for (String x : p) { if (x.isEmpty() || x.length() > 3 || (x.length() > 1 && x.charAt(0) == '0')) return "Neither"; for (char c : x.toCharArray()) if (c < '0' || c > '9') return "Neither"; if (Integer.parseInt(x) > 255) return "Neither"; }
        return "IPv4"; }
    if (s.contains(":") && !s.contains(".")) { String[] p = s.split(":", -1); if (p.length != 8) return "Neither";
        for (String x : p) { if (x.isEmpty() || x.length() > 4) return "Neither"; for (char c : x.toCharArray()) if (Character.digit(c, 16) < 0) return "Neither"; }
        return "IPv6"; }
    return "Neither";
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Regular Expressions | O(n) | O(1) |
| 2 | Split and Validate Each Group (Optimized) | O(n) | O(n) |

## Source Code

### ValidateIPAddress.java

```java
public class ValidateIPAddress {

    public static void main(String[] args) {
        String[] inputs = {"172.16.254.1", "2001:0db8:85a3:0:0:8A2E:0370:7334", "256.256.256.256", "192.168.01.1", "1e1.4.5.6", "2001:0db8:85a3::8A2E:037j:7334"};
        for (String ip : inputs) {
            System.out.println("Input  : queryIP = \"" + ip + "\"");
            System.out.println("Output : " + validIPAddress(ip));
            System.out.println();
        }
    }

    public static String validIPAddress(String queryIP) {
        if (queryIP == null) {
            return "Neither";
        }
        if (queryIP.contains(".") && !queryIP.contains(":")) {
            return isIPv4(queryIP) ? "IPv4" : "Neither";
        }
        if (queryIP.contains(":") && !queryIP.contains(".")) {
            return isIPv6(queryIP) ? "IPv6" : "Neither";
        }
        return "Neither";
    }

    private static boolean isIPv4(String ip) {
        String[] parts = ip.split("\\.", -1);
        if (parts.length != 4) {
            return false;
        }
        for (String part : parts) {
            if (part.isEmpty() || part.length() > 3 || (part.length() > 1 && part.charAt(0) == '0')) {
                return false;
            }
            for (char c : part.toCharArray()) {
                if (c < '0' || c > '9') {
                    return false;
                }
            }
            if (Integer.parseInt(part) > 255) {
                return false;
            }
        }
        return true;
    }

    private static boolean isIPv6(String ip) {
        String[] parts = ip.split(":", -1);
        if (parts.length != 8) {
            return false;
        }
        for (String part : parts) {
            if (part.isEmpty() || part.length() > 4) {
                return false;
            }
            for (char c : part.toCharArray()) {
                boolean digit = c >= '0' && c <= '9';
                boolean lower = c >= 'a' && c <= 'f';
                boolean upper = c >= 'A' && c <= 'F';
                if (!digit && !lower && !upper) {
                    return false;
                }
            }
        }
        return true;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: decide the type from the separator, split into groups, and check each group against the rules for its type. Splitting with a limit of -1 keeps empty groups, so a trailing dot or colon is caught.

**Input:** `queryIP = "172.16.254.1"`.

| Part | Length | Leading zero? | Digits only | Value | Valid? |
|------|--------|---------------|-------------|-------|--------|
| 172 | 3 | no | yes | 172 | yes |
| 16 | 2 | no | yes | 16 | yes |
| 254 | 3 | no | yes | 254 | yes |
| 1 | 1 | no | yes | 1 | yes |

Four valid parts and no colon: IPv4.

**Output:** `IPv4`.
