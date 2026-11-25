# Compare Version Numbers

Given two version strings `version1` and `version2`, each made of numeric revisions joined by dots, compare them revision by revision from left to right, ignoring leading zeros. A missing revision counts as 0. Return `-1` if `version1 < version2`, `1` if `version1 > version2`, and `0` if they are equal.

## Algorithm Strategy (Segment-by-Segment Comparison)

1. Split both strings on `.` (the dot must be escaped in a regular expression).
2. Iterate to the longer of the two lengths.
3. For each position parse the revision as an integer, using 0 when the array is exhausted.
4. Return -1 or 1 at the first difference, otherwise 0.

## Example walkthrough

- For `1.01` and `1.001`: the revisions are 1 and 1, then 1 and 1 (leading zeros are ignored): equal.
- For `1.0.1` and `1`: the third revision is 1 against 0, so the first is larger.

## Complexity Analysis

- **Time Complexity:** `O(n + m)` — Each character is read a constant number of times.
- **Space Complexity:** `O(n + m)` — The split arrays.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Split and Parse (Optimized)

Split on dots and compare the integers. This is the version implemented in the repository.

```java
public static int splitParse(String v1, String v2) {
    String[] a = v1.split("\\."), b = v2.split("\\.");
    for (int i = 0; i < Math.max(a.length, b.length); i++) { int x = i < a.length ? Integer.parseInt(a[i]) : 0, y = i < b.length ? Integer.parseInt(b[i]) : 0; if (x != y) return x < y ? -1 : 1; }
    return 0;
}
```

- **Time Complexity:** O(n + m)
- **Space Complexity:** O(n + m)

### Approach 2: Two Pointers without Splitting

Parse each revision directly from the string with two indexes, using no extra arrays.

```java
public static int pointers(String v1, String v2) {
    int i = 0, j = 0;
    while (i < v1.length() || j < v2.length()) {
        int x = 0, y = 0;
        while (i < v1.length() && v1.charAt(i) != '.') x = x * 10 + v1.charAt(i++) - '0';
        while (j < v2.length() && v2.charAt(j) != '.') y = y * 10 + v2.charAt(j++) - '0';
        if (x != y) return x < y ? -1 : 1;
        i++; j++;
    }
    return 0;
}
```

- **Time Complexity:** O(n + m)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Split and Parse (Optimized) | O(n + m) | O(n + m) |
| 2 | Two Pointers without Splitting | O(n + m) | O(1) |

## Source Code

### CompareVersionNumbers.java

```java
public class CompareVersionNumbers {

    public static void main(String[] args) {
        String[][] inputs = {{"1.2", "1.10"}, {"1.01", "1.001"}, {"1.0", "1.0.0"}, {"1.0.1", "1"}, {"0.1", "1.1"}};
        for (String[] in : inputs) {
            System.out.println("Input  : version1 = \"" + in[0] + "\", version2 = \"" + in[1] + "\"");
            System.out.println("Output : " + compareVersion(in[0], in[1]));
            System.out.println();
        }
    }

    public static int compareVersion(String version1, String version2) {
        String[] a = version1.split("\\.");
        String[] b = version2.split("\\.");
        int length = Math.max(a.length, b.length);
        for (int i = 0; i < length; i++) {
            int x = i < a.length ? Integer.parseInt(a[i]) : 0;
            int y = i < b.length ? Integer.parseInt(b[i]) : 0;
            if (x != y) {
                return x < y ? -1 : 1;
            }
        }
        return 0;
    }
}
```

## Solution

The optimized solution is Approach 1 above, which is also the version implemented in this repository.

The idea: a version is a list of numbers, and comparing lists element by element, with missing elements as 0, gives the order. Parsing the text to an integer removes leading zeros.

**Input:** `version1 = "1.2"`, `version2 = "1.10"`.

| Index | `version1` part | `version2` part | Result |
|-------|-----------------|-----------------|--------|
| 0 | 1 | 1 | equal |
| 1 | 2 | 10 | 2 < 10, return -1 |

**Output:** `-1`.
