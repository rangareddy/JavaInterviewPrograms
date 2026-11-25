# Backspace String Compare

Given two strings `s` and `t`, return `true` if they are equal after typing them into empty text editors, where `#` means a backspace character. Backspacing on an empty text leaves it empty.

## Algorithm Strategy (Two Pointers from the End)

1. Process both strings from the right; a `#` means skip the next normal character.
2. For each string, find the next character that survives: count `#` as a pending skip and decrement it for every normal character that is passed.
3. Compare the surviving characters of the two strings one by one.
4. The strings are equal when both are exhausted at the same time with no mismatch.

## Example walkthrough

- For `ab#c` and `ad#c`: both end up as `ac`.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(m + n)` — Each character is visited once.
- **Space Complexity:** `O(1)` — Only indexes and counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Build the Final Strings with a Stack

Apply the backspaces with a stack for each string and compare the results.

```java
public static boolean stackBuild(String s, String t) { return build(s).equals(build(t)); }
private static String build(String s) {
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) { if (c != '#') sb.append(c); else if (sb.length() > 0) sb.setLength(sb.length() - 1); }
    return sb.toString();
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(m + n)

### Approach 2: Two Pointers from the End (Optimized)

Walk both strings backward skipping erased characters. This is the version implemented in the repository.

```java
public static boolean pointers(String s, String t) {
    int i = s.length() - 1, j = t.length() - 1;
    while (i >= 0 || j >= 0) {
        i = next(s, i); j = next(t, j);
        if (i < 0 || j < 0) return i < 0 && j < 0;
        if (s.charAt(i) != t.charAt(j)) return false;
        i--; j--;
    }
    return true;
}
private static int next(String s, int k) {
    int skip = 0;
    while (k >= 0) { if (s.charAt(k) == '#') skip++; else if (skip > 0) skip--; else return k; k--; }
    return -1;
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Build the Final Strings with a Stack | O(m + n) | O(m + n) |
| 2 | Two Pointers from the End (Optimized) | O(m + n) | O(1) |

## Source Code

### BackspaceStringCompare.java

```java
public class BackspaceStringCompare {

    public static void main(String[] args) {
        String[][] inputs = {{"ab#c", "ad#c"}, {"ab##", "c#d#"}, {"a#c", "b"}, {"a##c", "#a#c"}};
        for (String[] in : inputs) {
            System.out.println("Input  : s = \"" + in[0] + "\", t = \"" + in[1] + "\"");
            System.out.println("Output : " + backspaceCompare(in[0], in[1]));
            System.out.println();
        }
    }

    public static boolean backspaceCompare(String s, String t) {
        int i = s.length() - 1;
        int j = t.length() - 1;
        while (i >= 0 || j >= 0) {
            i = nextValid(s, i);
            j = nextValid(t, j);
            if (i < 0 || j < 0) {
                return i < 0 && j < 0;
            }
            if (s.charAt(i) != t.charAt(j)) {
                return false;
            }
            i--;
            j--;
        }
        return true;
    }

    private static int nextValid(String s, int index) {
        int skip = 0;
        while (index >= 0) {
            if (s.charAt(index) == '#') {
                skip++;
            } else if (skip > 0) {
                skip--;
            } else {
                return index;
            }
            index--;
        }
        return -1;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: from the right, a `#` erases the next surviving character to its left, so scanning backward with a skip counter finds each surviving character without building a new string.

**Input:** `s = "ab#c"`, `t = "ad#c"`.

| Step | `s` survivor | `t` survivor | Equal? |
|------|--------------|--------------|--------|
| 1 | c (index 3) | c (index 3) | yes |
| 2 | `#` at 2 skips b, so a (index 0) | `#` skips d, so a (index 0) | yes |
| 3 | exhausted | exhausted | done |

**Output:** `true`.
