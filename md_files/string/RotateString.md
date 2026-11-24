# Rotate String

Given two strings `s` and `goal`, return `true` if `s` can become `goal` after some number of left shifts, where one shift moves the first character of `s` to the end.

## Algorithm Strategy (Search in the Doubled String)

1. If the lengths differ, the answer is `false`.
2. Every rotation of `s` is a substring of `s + s`.
3. So `s` can become `goal` exactly when `(s + s).contains(goal)`.
4. Both strings being empty is a valid rotation.

## Example walkthrough

- For `abcde` and `cdeab`: `abcdeabcde` contains `cdeab` starting at index 2.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — With a linear-time substring search; `String.contains` is O(n * m) worst case.
- **Space Complexity:** `O(n)` — The doubled string.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Rotation

Rotate by each offset and compare.

```java
public static boolean everyRotation(String s, String g) {
    if (s.length() != g.length()) return false;
    if (s.isEmpty()) return true;
    for (int k = 0; k < s.length(); k++) if ((s.substring(k) + s.substring(0, k)).equals(g)) return true;
    return false;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Doubled String (Optimized)

Look for `goal` inside `s + s`. This is the version implemented in the repository.

```java
public static boolean doubled(String s, String g) { return s.length() == g.length() && (s + s).contains(g); }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Rotation | O(n^2) | O(n) |
| 2 | Doubled String (Optimized) | O(n) | O(n) |

## Source Code

### RotateString.java

```java
public class RotateString {

    public static void main(String[] args) {
        String[][] inputs = {{"abcde", "cdeab"}, {"abcde", "abced"}, {"", ""}, {"a", "ab"}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", goal = \"" + in[1] + "\"");
            System.out.println("Output : " + rotateString(in[0], in[1]));
            System.out.println();
        }
    }

    public static boolean rotateString(String s, String goal) {
        if (s == null || goal == null || s.length() != goal.length()) {
            return false;
        }
        return (s + s).contains(goal);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: writing `s` twice lays out every rotation of `s` as a window of length `n`, so a single substring search checks all of them.

**Input:** `s = "abcde"`, `goal = "cdeab"`.

| Rotation | Value |
|----------|-------|
| 0 | abcde |
| 1 | bcdea |
| 2 | cdeab |

`s + s = "abcdeabcde"` contains `cdeab` at index 2.

**Output:** `true`.
