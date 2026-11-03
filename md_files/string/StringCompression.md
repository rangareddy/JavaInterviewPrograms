# String Compression

Compress a string by replacing every run of repeated characters with the character followed by the run length when the length is greater than 1 (for example `"aabcccccaaa"` becomes `"a2bc5a3"`). Return the original string if it is not made shorter.

## Algorithm Strategy (Run-Length Encoding with a Single Pass)

1. Walk through the string and count how many equal characters follow each other.
2. When the run ends (the next character is different or the string ends), append the character and, if the run is longer than 1, its length.
3. Use a `StringBuilder` so the appends do not copy the whole result every time.
4. If the compressed string is not shorter than the input, return the input unchanged.

## Example walkthrough

- `"aabcccccaaa"`: runs `aa, b, ccccc, aaa` give `a2 b c5 a3` = `"a2bc5a3"` (7 < 11).
- `"abc"` would become `"abc"` (no run longer than 1), which is not shorter, so the original is returned.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(n)` — The result builder.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Regex Replace

Use a back-reference regex to find runs of the same character and rewrite each one.

```java
public static String compressRegex(String s) {
    Matcher matcher = Pattern.compile("(.)\\1*").matcher(s);
    StringBuilder result = new StringBuilder();
    while (matcher.find()) {
        result.append(matcher.group(1));
        if (matcher.group().length() > 1) result.append(matcher.group().length());
    }
    return result.length() < s.length() ? result.toString() : s;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Compare with the Previous Character

Keep a running count and flush it when the character changes.

```java
public static String compressCounter(String s) {
    if (s.isEmpty()) return s;
    StringBuilder result = new StringBuilder();
    int count = 1;
    for (int i = 1; i <= s.length(); i++) {
        if (i < s.length() && s.charAt(i) == s.charAt(i - 1)) {
            count++;
        } else {
            result.append(s.charAt(i - 1));
            if (count > 1) result.append(count);
            count = 1;
        }
    }
    return result.length() < s.length() ? result.toString() : s;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Streams Grouping Adjacent Runs

Collect the runs with a manual list; shown for comparison, not as an interview answer.

```java
public static String compressRuns(String s) {
    List<String> runs = new ArrayList<>();
    for (int i = 0; i < s.length(); ) {
        int j = i;
        while (j < s.length() && s.charAt(j) == s.charAt(i)) j++;
        runs.add(s.charAt(i) + (j - i > 1 ? String.valueOf(j - i) : ""));
        i = j;
    }
    String joined = String.join("", runs);
    return joined.length() < s.length() ? joined : s;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Two Nested Loops with StringBuilder (Optimized)

Count each run with an inner loop and append it. This is the version implemented in the repository.

```java
public static String compress(String s) {
    StringBuilder result = new StringBuilder();
    int i = 0;
    while (i < s.length()) {
        char c = s.charAt(i);
        int run = 0;
        while (i < s.length() && s.charAt(i) == c) { run++; i++; }
        result.append(c);
        if (run > 1) result.append(run);
    }
    return result.length() < s.length() ? result.toString() : s;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Regex Replace | O(n) | O(n) |
| 2 | Compare with the Previous Character | O(n) | O(n) |
| 3 | Streams Grouping Adjacent Runs | O(n) | O(n) |
| 4 | Two Nested Loops with StringBuilder (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### StringCompression.java

```java
public class StringCompression {

    public static void main(String[] args) {
        String[] inputs = {"aabcccccaaa", "abc", "aaaaaaaaaaaa", "aabb", "a", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + compress(s) + "\"");
            System.out.println();
        }
    }

    public static String compress(String s) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            int run = 0;
            while (i < s.length() && s.charAt(i) == c) {
                run++;
                i++;
            }
            result.append(c);
            if (run > 1) {
                result.append(run);
            }
        }
        return result.length() < s.length() ? result.toString() : s;
    }
}
```
