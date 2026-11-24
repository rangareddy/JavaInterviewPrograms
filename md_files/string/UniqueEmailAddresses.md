# Unique Email Addresses

Every email has a local name and a domain, separated by `@`. In the local name, dots are ignored, and everything after the first `+` is ignored. These rules do not apply to the domain. Return the number of different addresses that actually receive mail.

## Algorithm Strategy (Normalise and Hash Set)

1. Split each address at `@` into a local name and a domain.
2. In the local name, cut everything from the first `+` and remove every `.`.
3. Rebuild `local + "@" + domain` and add it to a `HashSet`.
4. The answer is the size of the set.

## Example walkthrough

- `test.email+alex@leetcode.com` and `test.e.mail+bob.cathy@leetcode.com` both normalise to `testemail@leetcode.com`.
- `testemail+david@lee.tcode.com` stays separate because its domain differs.

## Complexity Analysis

- **Time Complexity:** `O(n * L)` — Every address of length `L` is scanned once.
- **Space Complexity:** `O(n * L)` — The set stores the normalised addresses.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Regex Replacement

Use two regular expressions on the local part. Short, but compiles patterns on every call.

```java
public static int uniqueEmailsRegex(String[] emails) {
    java.util.Set<String> set = new java.util.HashSet<>();
    for (String e : emails) {
        String[] p = e.split("@");
        String local = p[0].replaceAll("\\+.*", "").replaceAll("\\.", "");
        set.add(local + "@" + p[1]);
    }
    return set.size();
}
```

- **Time Complexity:** O(n * L)
- **Space Complexity:** O(n * L)

### Approach 2: Character Scan

Build the normalised local name with a `StringBuilder` in one pass.

```java
public static int uniqueEmailsScan(String[] emails) {
    java.util.Set<String> set = new java.util.HashSet<>();
    for (String e : emails) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (e.charAt(i) != '@' && e.charAt(i) != '+') { if (e.charAt(i) != '.') sb.append(e.charAt(i)); i++; }
        sb.append(e.substring(e.indexOf('@')));
        set.add(sb.toString());
    }
    return set.size();
}
```

- **Time Complexity:** O(n * L)
- **Space Complexity:** O(n * L)

### Approach 3: Split and Hash Set (Optimized)

Cut at the first `+`, remove dots, keep the domain, and count distinct results. This is the version implemented in the repository.

```java
public static int uniqueEmailsSplit(String[] emails) {
    java.util.Set<String> set = new java.util.HashSet<>();
    for (String e : emails) {
        int at = e.indexOf('@');
        String local = e.substring(0, at);
        int plus = local.indexOf('+');
        if (plus >= 0) local = local.substring(0, plus);
        set.add(local.replace(".", "") + e.substring(at));
    }
    return set.size();
}
```

- **Time Complexity:** O(n * L)
- **Space Complexity:** O(n * L)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Regex Replacement | O(n * L) | O(n * L) |
| 2 | Character Scan | O(n * L) | O(n * L) |
| 3 | Split and Hash Set (Optimized) | O(n * L) | O(n * L) |

## Source Code

### UniqueEmailAddresses.java

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UniqueEmailAddresses {

    public static void main(String[] args) {
        String[][] inputs = {
            {"test.email+alex@leetcode.com", "test.e.mail+bob.cathy@leetcode.com", "testemail+david@lee.tcode.com"},
            {"a@leetcode.com", "b@leetcode.com", "c@leetcode.com"},
            {}
        };
        for (String[] emails : inputs) {
            System.out.println("Input  : emails = " + Arrays.toString(emails));
            System.out.println("Output : " + numUniqueEmails(emails));
            System.out.println();
        }
    }

    public static int numUniqueEmails(String[] emails) {
        if (emails == null || emails.length == 0) {
            return 0;
        }
        Set<String> unique = new HashSet<>();
        for (String email : emails) {
            int at = email.indexOf('@');
            String local = email.substring(0, at);
            int plus = local.indexOf('+');
            if (plus >= 0) {
                local = local.substring(0, plus);
            }
            unique.add(local.replace(".", "") + email.substring(at));
        }
        return unique.size();
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: two addresses are the same mailbox when their normalised forms are equal, so normalise first and let a set do the counting.

**Input:** `["test.email+alex@leetcode.com", "test.e.mail+bob.cathy@leetcode.com", "testemail+david@lee.tcode.com"]`.

| Address | Local name | After `+` cut | After removing dots | Normalised |
|---------|-----------|----------------|---------------------|------------|
| test.email+alex@leetcode.com | test.email+alex | test.email | testemail | testemail@leetcode.com |
| test.e.mail+bob.cathy@leetcode.com | test.e.mail+bob.cathy | test.e.mail | testemail | testemail@leetcode.com |
| testemail+david@lee.tcode.com | testemail+david | testemail | testemail | testemail@lee.tcode.com |

The set holds `testemail@leetcode.com` and `testemail@lee.tcode.com`, so the size is **2**.
