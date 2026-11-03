# Partition Labels

Given a string `s`, partition it into as many parts as possible so that each letter appears in at most one part. Return the sizes of the parts, in order.

## Algorithm Strategy (Greedy with Last Occurrences)

1. Record the last index of every letter.
2. Scan the string, keeping the `end` of the current part: the maximum last index of all letters seen in it.
3. When the scan index reaches `end`, no letter of the part appears later: close the part and record its size.
4. Start the next part right after `end`.

## Example walkthrough

- For `"ababcbacadefegdehijhklij"`: the first part must reach the last `a` (index 8), the second ends at 15 (last `e`), the third at 23.
- Result: `[9, 7, 8]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Two passes over the string.
- **Space Complexity:** `O(1)` — At most 26 letters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force Expansion

Start a part at the first unused character and keep extending it to cover the last occurrence of every letter inside it.

```java
public static List<Integer> partitionLabelsBrute(String s) {
    List<Integer> sizes = new ArrayList<>();
    int start = 0;
    while (start < s.length()) {
        int end = s.lastIndexOf(s.charAt(start));
        int i = start;
        while (i <= end) {
            end = Math.max(end, s.lastIndexOf(s.charAt(i)));
            i++;
        }
        sizes.add(end - start + 1);
        start = end + 1;
    }
    return sizes;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Merge Letter Intervals

Each letter spans the interval from its first to its last occurrence; merging overlapping intervals gives the parts.

```java
public static List<Integer> partitionLabelsIntervals(String s) {
    int[] first = new int[26], last = new int[26];
    Arrays.fill(first, -1);
    for (int i = 0; i < s.length(); i++) {
        int c = s.charAt(i) - 'a';
        if (first[c] < 0) first[c] = i;
        last[c] = i;
    }
    List<int[]> intervals = new ArrayList<>();
    for (int c = 0; c < 26; c++) if (first[c] >= 0) intervals.add(new int[]{first[c], last[c]});
    intervals.sort((a, b) -> Integer.compare(a[0], b[0]));
    List<Integer> sizes = new ArrayList<>();
    int start = -1, end = -1;
    for (int[] interval : intervals) {
        if (interval[0] > end) {
            if (end >= 0) sizes.add(end - start + 1);
            start = interval[0];
            end = interval[1];
        } else {
            end = Math.max(end, interval[1]);
        }
    }
    if (end >= 0) sizes.add(end - start + 1);
    return sizes;
}
```

- **Time Complexity:** O(n + 26 log 26)
- **Space Complexity:** O(1)

### Approach 3: Greedy with Last Occurrences (Optimized)

Record the last index of each letter and cut when the scan reaches the part end. This is the version implemented in the repository.

```java
public static List<Integer> partitionLabels(String s) {
    int[] last = new int[26];
    for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;
    List<Integer> sizes = new ArrayList<>();
    int start = 0, end = 0;
    for (int i = 0; i < s.length(); i++) {
        end = Math.max(end, last[s.charAt(i) - 'a']);
        if (i == end) { sizes.add(end - start + 1); start = i + 1; }
    }
    return sizes;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force Expansion | O(n^2) | O(1) |
| 2 | Merge Letter Intervals | O(n + 26 log 26) | O(1) |
| 3 | Greedy with Last Occurrences (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### PartitionLabels.java

```java
import java.util.ArrayList;
import java.util.List;

public class PartitionLabels {

    public static void main(String[] args) {
        String[] inputs = {"ababcbacadefegdehijhklij", "eccbbbbdec", "a", "abc"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + partitionLabels(s));
            System.out.println();
        }
    }

    public static List<Integer> partitionLabels(String s) {
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }

        List<Integer> sizes = new ArrayList<>();
        int start = 0;
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {
                sizes.add(end - start + 1);
                start = i + 1;
            }
        }
        return sizes;
    }
}
```
