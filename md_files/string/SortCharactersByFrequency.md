# Sort Characters By Frequency

Given a string `s`, sort its characters in decreasing order of frequency and return the sorted string. Characters with the same frequency may appear in any order.

## Algorithm Strategy (Count then Bucket by Frequency)

1. Count every character in a `HashMap` (or a 128-entry array).
2. Create buckets indexed by frequency from 1 to `n`; drop each character into the bucket of its count.
3. Walk the buckets from the highest frequency down.
4. Append each character repeated by its frequency.

## Example walkthrough

- For `tree`: `e` appears twice, `t` and `r` once.
- Result: `eert` (or `eetr`).

## Complexity Analysis

- **Time Complexity:** `O(n)` — Counting plus a bucket walk bounded by `n`.
- **Space Complexity:** `O(n)` — The map, the buckets and the result.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort the Characters

Sort the characters by their frequency using a comparator on the counts.

```java
public static String sortByCount(String s) {
    java.util.Map<Character, Integer> c = new java.util.HashMap<>(); for (char ch : s.toCharArray()) c.merge(ch, 1, Integer::sum);
    Character[] a = new Character[s.length()]; for (int i = 0; i < a.length; i++) a[i] = s.charAt(i);
    java.util.Arrays.sort(a, (x, y) -> c.get(y) != c.get(x).intValue() ? c.get(y) - c.get(x) : x - y);
    StringBuilder sb = new StringBuilder(); for (char ch : a) sb.append(ch); return sb.toString();
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Max-Heap of Counts

Push each distinct character with its count into a max-heap and pop in order.

```java
public static String heap(String s) {
    java.util.Map<Character, Integer> c = new java.util.HashMap<>(); for (char ch : s.toCharArray()) c.merge(ch, 1, Integer::sum);
    java.util.PriorityQueue<Character> pq = new java.util.PriorityQueue<>((x, y) -> c.get(y) != c.get(x).intValue() ? c.get(y) - c.get(x) : x - y);
    pq.addAll(c.keySet()); StringBuilder sb = new StringBuilder();
    while (!pq.isEmpty()) { char ch = pq.poll(); for (int i = 0; i < c.get(ch); i++) sb.append(ch); }
    return sb.toString();
}
```

- **Time Complexity:** O(n + d log d)
- **Space Complexity:** O(n)

### Approach 3: Buckets by Frequency (Optimized)

Bucket the characters by count and read from the highest bucket. This is the version implemented in the repository.

```java
public static String bucket(String s) {
    java.util.Map<Character, Integer> c = new java.util.HashMap<>(); for (char ch : s.toCharArray()) c.merge(ch, 1, Integer::sum);
    java.util.List<java.util.List<Character>> b = new java.util.ArrayList<>(); for (int i = 0; i <= s.length(); i++) b.add(new java.util.ArrayList<>());
    for (java.util.Map.Entry<Character, Integer> e : c.entrySet()) b.get(e.getValue()).add(e.getKey());
    StringBuilder sb = new StringBuilder();
    for (int f = s.length(); f > 0; f--) for (char ch : b.get(f)) for (int k = 0; k < f; k++) sb.append(ch);
    return sb.toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort the Characters | O(n log n) | O(n) |
| 2 | Max-Heap of Counts | O(n + d log d) | O(n) |
| 3 | Buckets by Frequency (Optimized) | O(n) | O(n) |

## Source Code

### SortCharactersByFrequency.java

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortCharactersByFrequency {

    public static void main(String[] args) {
        String[] inputs = {"tree", "cccaaa", "Aabb", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + frequencySort(s) + "\"");
            System.out.println();
        }
    }

    public static String frequencySort(String s) {
        if (s == null || s.length() < 2) {
            return s == null ? "" : s;
        }
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : s.toCharArray()) {
            counts.merge(c, 1, Integer::sum);
        }
        List<List<Character>> buckets = new ArrayList<>();
        for (int i = 0; i <= s.length(); i++) {
            buckets.add(new ArrayList<>());
        }
        for (Map.Entry<Character, Integer> e : counts.entrySet()) {
            buckets.get(e.getValue()).add(e.getKey());
        }
        StringBuilder sb = new StringBuilder();
        for (int freq = s.length(); freq > 0; freq--) {
            for (char c : buckets.get(freq)) {
                for (int k = 0; k < freq; k++) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: the frequency of any character is at most `n`, so instead of sorting by frequency, use frequency as an array index (a bucket) and walk the buckets from the top.

**Input:** `s = "tree"`.

| Step | Data |
|------|------|
| counts | t: 1, r: 1, e: 2 |
| bucket 1 | t, r |
| bucket 2 | e |
| walk from 4 down to 1 | e e, then t, then r (bucket order) |

**Output:** `eert` (the order of `t` and `r` within the same frequency may differ).
