# Minimum Number of Steps to Make Two Strings Anagram

Given two strings `s` and `t` of the same length, in one step you may replace any character of `t` with another character. Return the minimum number of steps to make `t` an anagram of `s`.

## Algorithm Strategy (Count the Surplus Letters)

1. Count the letters of `s` in a 26-entry array.
2. For each letter of `t`, decrement its count.
3. A letter of `t` that has no remaining count in `s` is surplus: it must be replaced.
4. The answer is the number of positive counts left (or equivalently the number of surplus letters in `t`).

## Example walkthrough

- For `s = "bab"` and `t = "aba"`: `s` has two `b` and one `a`, `t` has two `a` and one `b`, so one `a` in `t` must become `b`.
- Result: `1`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over each string.
- **Space Complexity:** `O(1)` — 26 counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Half of the Sum of Count Differences

Every letter whose counts differ is either surplus or missing, and each replacement fixes one of each, so the answer is half of the total count difference.

```java
public static int halfDiff(String s, String t) {
    int[] a = new int[26], b = new int[26]; for (char c : s.toCharArray()) a[c - 'a']++; for (char c : t.toCharArray()) b[c - 'a']++;
    int d = 0; for (int i = 0; i < 26; i++) d += Math.abs(a[i] - b[i]); return d / 2;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Count Surplus Letters in t (Optimized)

Decrement the counts of `s` while reading `t` and count the letters of `t` that have no match. This is the version implemented in the repository.

```java
public static int surplus(String s, String t) {
    int[] c = new int[26]; for (char ch : s.toCharArray()) c[ch - 'a']++;
    int steps = 0; for (char ch : t.toCharArray()) { if (c[ch - 'a'] > 0) c[ch - 'a']--; else steps++; }
    return steps;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Half of the Sum of Count Differences | O(n) | O(1) |
| 2 | Count Surplus Letters in t (Optimized) | O(n) | O(1) |

## Source Code

### MinimumNumberOfStepsToMakeTwoStringsAnagram.java

```java
public class MinimumNumberOfStepsToMakeTwoStringsAnagram {

    public static void main(String[] args) {
        String[][] inputs = {{"bab", "aba"}, {"leetcode", "practice"}, {"anagram", "mangaar"}, {"xxyyzz", "xxyyzz"}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", t = \"" + in[1] + "\"");
            System.out.println("Output : " + minSteps(in[0], in[1]));
            System.out.println();
        }
    }

    public static int minSteps(String s, String t) {
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            counts[c - 'a']++;
        }
        int steps = 0;
        for (char c : t.toCharArray()) {
            if (counts[c - 'a'] > 0) {
                counts[c - 'a']--;
            } else {
                steps++;
            }
        }
        return steps;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: letters of `t` that `s` can still use are already fine. Every other letter of `t` is surplus and must be replaced, and each replacement can turn a surplus letter into a missing one.

**Input:** `s = "bab"`, `t = "aba"`.

| Letter of `t` | Count left in `s` | Action | Steps |
|---------------|-------------------|--------|-------|
| a | a: 1, b: 2 | use one `a` | 0 |
| b | a: 0, b: 2 | use one `b` | 0 |
| a | a: 0, b: 1 | no `a` left, surplus | 1 |

**Output:** `1`.
