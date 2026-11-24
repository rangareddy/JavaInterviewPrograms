# Reverse Words in a String

Given a string `s`, reverse the order of its words. A word is a maximal run of non-space characters. The result must contain the words separated by a single space, with no leading or trailing spaces, even when the input has extra spaces.

## Algorithm Strategy (Scan Backward Collecting Words)

1. Walk the string from the end towards the start.
2. Skip spaces, then mark the end of a word and move left until the next space or the start to find its beginning.
3. Append the word to a `StringBuilder`, adding a single space first if something is already there.
4. Return the builder's content.

## Example walkthrough

- For `"  hello world  "` the scan finds `world` and then `hello`.
- Result: `"world hello"`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each character is looked at once.
- **Space Complexity:** `O(n)` — The result builder.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Split and Reverse

Trim, split on one or more spaces, reverse the array and join.

```java
public static String splitReverse(String s) {
    String t = s.trim();
    if (t.isEmpty()) return "";
    String[] parts = t.split("\\s+");
    java.util.Collections.reverse(java.util.Arrays.asList(parts));
    return String.join(" ", parts);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Stack of Words

Push each word on a stack and pop them in reverse.

```java
public static String stackWords(String s) {
    java.util.Deque<String> st = new java.util.ArrayDeque<>();
    StringBuilder w = new StringBuilder();
    for (int i = 0; i <= s.length(); i++) {
        if (i < s.length() && s.charAt(i) != ' ') w.append(s.charAt(i));
        else if (w.length() > 0) { st.push(w.toString()); w.setLength(0); }
    }
    return String.join(" ", st);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Backward Scan (Optimized)

Read words from the end with two indexes and append them in order. This is the version implemented in the repository.

```java
public static String backward(String s) {
    StringBuilder out = new StringBuilder(); int i = s.length() - 1;
    while (i >= 0) {
        while (i >= 0 && s.charAt(i) == ' ') i--;
        if (i < 0) break;
        int end = i;
        while (i >= 0 && s.charAt(i) != ' ') i--;
        if (out.length() > 0) out.append(' ');
        out.append(s, i + 1, end + 1);
    }
    return out.toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Split and Reverse | O(n) | O(n) |
| 2 | Stack of Words | O(n) | O(n) |
| 3 | Backward Scan (Optimized) | O(n) | O(n) |

## Source Code

### ReverseWordsInAString.java

```java
public class ReverseWordsInAString {

    public static void main(String[] args) {
        String[] inputs = {"the sky is blue", "  hello world  ", "a good   example", "", "word"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + reverseWords(s) + "\"");
            System.out.println();
        }
    }

    public static String reverseWords(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        int i = s.length() - 1;
        while (i >= 0) {
            while (i >= 0 && s.charAt(i) == ' ') {
                i--;
            }
            if (i < 0) {
                break;
            }
            int end = i;
            while (i >= 0 && s.charAt(i) != ' ') {
                i--;
            }
            if (result.length() > 0) {
                result.append(' ');
            }
            result.append(s, i + 1, end + 1);
        }
        return result.toString();
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: walking from the end hands you the words already in reverse order, and skipping spaces while scanning removes the extra whitespace for free.

**Input:** `"  hello world  "`.

| Step | Action | `result` |
|------|--------|----------|
| 1 | skip trailing spaces | "" |
| 2 | find word `world` | "world" |
| 3 | skip the space, find word `hello` | "world hello" |
| 4 | skip leading spaces, reach the start | "world hello" |

**Output:** `"world hello"`.
