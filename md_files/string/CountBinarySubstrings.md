# Count Binary Substrings

Given a binary string `s`, return the number of non-empty substrings that have the same number of `0`s and `1`s, where all the `0`s and all the `1`s in the substring are grouped consecutively.

## Algorithm Strategy (Adjacent Run Lengths)

1. Split the string into runs of equal characters, such as `00 11 0 1 00` for `0011010 0`.
2. Between two adjacent runs of lengths `a` and `b`, exactly `min(a, b)` valid substrings are centred on the boundary.
3. Keep `prev` (the previous run length) and `cur` (the current one) while scanning.
4. Whenever a run ends, add `min(prev, cur)` to the answer.

## Example walkthrough

- For `00110011`: runs are 2, 2, 2, 2, so each of the three boundaries adds `min(2, 2) = 2`.
- Result: `6`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Two counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Expand Around Each Boundary

At every position where the character changes, expand outwards while both sides keep their characters.

```java
public static int expand(String s) {
    int c = 0;
    for (int i = 1; i < s.length(); i++) {
        if (s.charAt(i) == s.charAt(i - 1)) continue;
        int l = i - 1, r = i;
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(i - 1) && s.charAt(r) == s.charAt(i)) { c++; l--; r++; }
    }
    return c;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Run Lengths with Two Counters (Optimized)

Add `min` of two adjacent run lengths. This is the version implemented in the repository.

```java
public static int runs(String s) {
    if (s.length() < 2) return 0;
    int c = 0, prev = 0, cur = 1;
    for (int i = 1; i < s.length(); i++) {
        if (s.charAt(i) == s.charAt(i - 1)) cur++; else { c += Math.min(prev, cur); prev = cur; cur = 1; }
    }
    return c + Math.min(prev, cur);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Expand Around Each Boundary | O(n^2) | O(1) |
| 2 | Run Lengths with Two Counters (Optimized) | O(n) | O(1) |

## Source Code

### CountBinarySubstrings.java

```java
public class CountBinarySubstrings {

    public static void main(String[] args) {
        String[] inputs = {"00110011", "10101", "000111", "0"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + countBinarySubstrings(s));
            System.out.println();
        }
    }

    public static int countBinarySubstrings(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        int count = 0;
        int prev = 0;
        int cur = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                cur++;
            } else {
                count += Math.min(prev, cur);
                prev = cur;
                cur = 1;
            }
        }
        return count + Math.min(prev, cur);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: every valid substring is centred on a boundary between a run of 0s and a run of 1s, and the number of valid substrings at one boundary is the shorter of the two run lengths.

**Input:** `s = "00110011"`.

| Boundary | Left run | Right run | Added | Total |
|----------|----------|-----------|-------|-------|
| 1 | 2 | 2 | 2 | 2 |
| 2 | 2 | 2 | 2 | 4 |
| 3 | 2 | 2 | 2 | 6 |

**Output:** `6`.
