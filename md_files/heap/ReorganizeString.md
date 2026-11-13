# Reorganize String

Rearrange the characters of a string `s` so that no two adjacent characters are the same. Return any valid rearrangement, or an empty string if it is not possible.

## Algorithm Strategy (Max-Heap by Frequency)

1. A valid arrangement exists only if no character occurs more than `(n + 1) / 2` times; otherwise two copies must touch.
2. Count the frequency of each character and put `(count, char)` pairs in a max-heap ordered by count.
3. Repeatedly take the two most frequent characters, append both to the result, decrement their counts and push back those with a count above 0. The two characters are different, so no two equal characters become adjacent.
4. If one character is left at the end, append it (its count is 1 when a valid arrangement exists). Using the most frequent characters first prevents a large group from being left for the end.

## Example walkthrough

- For `"aab"`: the heap holds `(a, 2)` and `(b, 1)`. Take `a`, `b` giving `"ab"`, push `(a, 1)`, then append `a`.
- Result: `"aba"`. For `"aaab"` the count of `a` is 3 and `(4 + 1) / 2 = 2`, so the answer is `""`.

## Complexity Analysis

- **Time Complexity:** `O(n log k)` — `k` is the number of distinct characters (at most 26 for lowercase letters), so effectively `O(n)`.
- **Space Complexity:** `O(k)` — The heap.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Fill Even Positions, Then Odd Positions

Put the most frequent character first at indices `0, 2, 4, ...`, then place the other characters (grouped by frequency) continuing through the even indices and wrapping to the odd ones.

```java
public static String reorganizeStringFill(String s) {
    int n = s.length();
    int[] counts = new int[26];
    int maxChar = 0;
    for (char c : s.toCharArray()) counts[c - 'a']++;
    for (int i = 1; i < 26; i++) if (counts[i] > counts[maxChar]) maxChar = i;
    if (counts[maxChar] > (n + 1) / 2) return "";
    char[] out = new char[n];
    int index = 0;
    while (counts[maxChar]-- > 0) {
        out[index] = (char) ('a' + maxChar);
        index += 2;
    }
    for (int c = 0; c < 26; c++) {
        while (counts[c] > 0) {
            if (index >= n) index = 1;
            out[index] = (char) ('a' + c);
            index += 2;
            counts[c]--;
        }
    }
    return new String(out);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Max-Heap (Optimized)

Always take the two most frequent remaining characters so that the same character never follows itself. This is the version implemented in the repository.

```java
public static String reorganizeString(String s) {
    int[] counts = new int[26];
    for (char c : s.toCharArray()) {
        counts[c - 'a']++;
    }
    PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[1] - a[1]);
    for (int i = 0; i < 26; i++) {
        if (counts[i] > (s.length() + 1) / 2) {
            return "";
        }
        if (counts[i] > 0) {
            heap.add(new int[]{i, counts[i]});
        }
    }
    StringBuilder sb = new StringBuilder();
    while (heap.size() >= 2) {
        int[] first = heap.poll();
        int[] second = heap.poll();
        sb.append((char) ('a' + first[0])).append((char) ('a' + second[0]));
        if (--first[1] > 0) {
            heap.add(first);
        }
        if (--second[1] > 0) {
            heap.add(second);
        }
    }
    if (!heap.isEmpty()) {
        sb.append((char) ('a' + heap.poll()[0]));
    }
    return sb.toString();
}
```

- **Time Complexity:** O(n log k)
- **Space Complexity:** O(k)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Fill Even Positions, Then Odd Positions | O(n) | O(n) |
| 2 | Max-Heap (Optimized) | O(n log k) | O(k) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### ReorganizeString.java

```java
import java.util.PriorityQueue;

public class ReorganizeString {

    public static void main(String[] args) {
        String[] inputs = {"aab", "aaab", "vvvlo", "a", "aabbcc"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + reorganizeString(s) + "\"");
            System.out.println();
        }
    }

    public static String reorganizeString(String s) {
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            counts[c - 'a']++;
        }
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        for (int i = 0; i < 26; i++) {
            if (counts[i] > (s.length() + 1) / 2) {
                return "";
            }
            if (counts[i] > 0) {
                heap.add(new int[]{i, counts[i]});
            }
        }
        StringBuilder sb = new StringBuilder();
        while (heap.size() >= 2) {
            int[] first = heap.poll();
            int[] second = heap.poll();
            sb.append((char) ('a' + first[0])).append((char) ('a' + second[0]));
            if (--first[1] > 0) {
                heap.add(first);
            }
            if (--second[1] > 0) {
                heap.add(second);
            }
        }
        if (!heap.isEmpty()) {
            sb.append((char) ('a' + heap.poll()[0]));
        }
        return sb.toString();
    }
}
```
