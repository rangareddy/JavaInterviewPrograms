# Longest Common Prefix

Write a function that returns the longest common prefix of an array of strings, or an empty string if there is none.

## Algorithm Strategy (Vertical Scanning)

1. If the array is empty there is no prefix. Otherwise compare the strings column by column.
2. For every index `i` of the first string, take its character `c`.
3. If any other string is shorter than `i + 1` or has a different character at index `i`, stop: the answer is the first string up to (not including) `i`.
4. If the whole first string matches in every other string, the first string itself is the answer.

## Example walkthrough

- For `["flower", "flow", "flight"]`: column 0 is `f` everywhere, column 1 is `l` everywhere, column 2 is `o`, `o`, `i` - mismatch, so the prefix is `"fl"`.
- For `["dog", "racecar", "car"]` column 0 already differs: `""`.

## Complexity Analysis

- **Time Complexity:** `O(S)` — `S` is the total number of characters of all strings, in the worst case every character is compared once.
- **Space Complexity:** `O(1)` — Only the answer is a substring of the first string.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Horizontal Scanning

Shrink the prefix until each string starts with it.

```java
public static String longestCommonPrefixHorizontal(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    String prefix = strs[0];
    for (int i = 1; i < strs.length; i++)
        while (!strs[i].startsWith(prefix)) prefix = prefix.substring(0, prefix.length() - 1);
    return prefix;
}
```

- **Time Complexity:** O(S)
- **Space Complexity:** O(1)

### Approach 2: Sort and Compare the Extremes

After sorting, the common prefix of all strings is the common prefix of the first and last one.

```java
public static String longestCommonPrefixSorted(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    String[] sorted = strs.clone();
    Arrays.sort(sorted);
    String first = sorted[0], last = sorted[sorted.length - 1];
    int i = 0;
    while (i < first.length() && i < last.length() && first.charAt(i) == last.charAt(i)) i++;
    return first.substring(0, i);
}
```

- **Time Complexity:** O(n log n * m)
- **Space Complexity:** O(n)

### Approach 3: Divide and Conquer

Find the prefix of each half recursively and merge the two results.

```java
public static String longestCommonPrefixDivide(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    return divide(strs, 0, strs.length - 1);
}

private static String divide(String[] strs, int left, int right) {
    if (left == right) return strs[left];
    int mid = (left + right) / 2;
    String a = divide(strs, left, mid), b = divide(strs, mid + 1, right);
    int i = 0;
    while (i < a.length() && i < b.length() && a.charAt(i) == b.charAt(i)) i++;
    return a.substring(0, i);
}
```

- **Time Complexity:** O(S)
- **Space Complexity:** O(m log n)

### Approach 4: Vertical Scanning (Optimized)

Compare column by column and stop at the first mismatch. This is the version implemented in the repository.

```java
public static String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    String first = strs[0];
    for (int i = 0; i < first.length(); i++) {
        char c = first.charAt(i);
        for (int j = 1; j < strs.length; j++)
            if (i >= strs[j].length() || strs[j].charAt(i) != c) return first.substring(0, i);
    }
    return first;
}
```

- **Time Complexity:** O(S)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Horizontal Scanning | O(S) | O(1) |
| 2 | Sort and Compare the Extremes | O(n log n * m) | O(n) |
| 3 | Divide and Conquer | O(S) | O(m log n) |
| 4 | Vertical Scanning (Optimized) | O(S) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### LongestCommonPrefix.java

```java
import java.util.Arrays;

public class LongestCommonPrefix {

    public static void main(String[] args) {
        String[][] inputs = {{"flower", "flow", "flight"}, {"dog", "racecar", "car"}, {"interview", "internet", "internal"}, {"single"}, {"", "abc"}, {}};
        for (String[] words : inputs) {
            System.out.println("Input  : strs = " + Arrays.toString(words));
            System.out.println("Output : \"" + longestCommonPrefix(words) + "\"");
            System.out.println();
        }
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        String first = strs[0];
        for (int i = 0; i < first.length(); i++) {
            char c = first.charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[j].charAt(i) != c) {
                    return first.substring(0, i);
                }
            }
        }
        return first;
    }
}
```
