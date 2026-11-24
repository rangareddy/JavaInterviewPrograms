# Top K Frequent Words

Given an array of strings `words` and an integer `k`, return the `k` most frequent strings, ordered from the most frequent to the least frequent. Words with the same frequency are ordered lexicographically (alphabetically).

## Algorithm Strategy (Count then Bounded Min-Heap)

1. Count the frequency of each word with a `HashMap`.
2. Keep a min-heap of at most `k` entries, ordered by frequency ascending and then by word descending, so the heap's top is the weakest candidate.
3. Offer each entry; if the heap grows beyond `k`, remove the top.
4. Pop the heap into the result and reverse it to get the best first.

## Example walkthrough

- For `["i","love","leetcode","i","love","coding"]` and `k = 2`: `i` and `love` both appear twice, and `i` sorts first.
- Result: `["i", "love"]`.

## Complexity Analysis

- **Time Complexity:** `O(n log k)` — Counting is O(n); every distinct word costs at most a log k heap operation.
- **Space Complexity:** `O(n)` — The frequency map and the heap.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort All Distinct Words

Sort the distinct words by frequency, then alphabetically, and take the first `k`.

```java
public static java.util.List<String> sortAll(String[] words, int k) {
    java.util.Map<String, Integer> c = new java.util.HashMap<>();
    for (String w : words) c.merge(w, 1, Integer::sum);
    java.util.List<String> list = new java.util.ArrayList<>(c.keySet());
    list.sort((a, b) -> c.get(a).equals(c.get(b)) ? a.compareTo(b) : c.get(b) - c.get(a));
    return list.subList(0, Math.min(k, list.size()));
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Bucket by Frequency

Put words into buckets indexed by count, sort each bucket alphabetically and read from the highest bucket.

```java
public static java.util.List<String> buckets(String[] words, int k) {
    java.util.Map<String, Integer> c = new java.util.HashMap<>();
    for (String w : words) c.merge(w, 1, Integer::sum);
    java.util.List<java.util.List<String>> b = new java.util.ArrayList<>();
    for (int i = 0; i <= words.length; i++) b.add(new java.util.ArrayList<>());
    for (java.util.Map.Entry<String, Integer> e : c.entrySet()) b.get(e.getValue()).add(e.getKey());
    java.util.List<String> out = new java.util.ArrayList<>();
    for (int f = words.length; f > 0 && out.size() < k; f--) { java.util.Collections.sort(b.get(f)); for (String w : b.get(f)) if (out.size() < k) out.add(w); }
    return out;
}
```

- **Time Complexity:** O(n + d log d)
- **Space Complexity:** O(n)

### Approach 3: Bounded Min-Heap (Optimized)

Keep only the `k` best candidates in a heap whose top is the weakest. This is the version implemented in the repository.

```java
public static java.util.List<String> heap(String[] words, int k) {
    java.util.Map<String, Integer> c = new java.util.HashMap<>();
    for (String w : words) c.merge(w, 1, Integer::sum);
    java.util.PriorityQueue<String> h = new java.util.PriorityQueue<>((a, b) -> c.get(a).equals(c.get(b)) ? b.compareTo(a) : c.get(a) - c.get(b));
    for (String w : c.keySet()) { h.offer(w); if (h.size() > k) h.poll(); }
    java.util.LinkedList<String> out = new java.util.LinkedList<>();
    while (!h.isEmpty()) out.addFirst(h.poll());
    return out;
}
```

- **Time Complexity:** O(n log k)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort All Distinct Words | O(n log n) | O(n) |
| 2 | Bucket by Frequency | O(n + d log d) | O(n) |
| 3 | Bounded Min-Heap (Optimized) | O(n log k) | O(n) |

## Source Code

### TopKFrequentWords.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequentWords {

    public static void main(String[] args) {
        String[][] words = {
            {"i", "love", "leetcode", "i", "love", "coding"},
            {"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"},
            {"a"}
        };
        int[] ks = {2, 4, 1};
        for (int i = 0; i < words.length; i++) {
            System.out.println("Input  : words = " + Arrays.toString(words[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + topKFrequent(words[i], ks[i]));
            System.out.println();
        }
    }

    public static List<String> topKFrequent(String[] words, int k) {
        List<String> result = new ArrayList<>();
        if (words == null || words.length == 0 || k <= 0) {
            return result;
        }
        Map<String, Integer> counts = new HashMap<>();
        for (String word : words) {
            counts.merge(word, 1, Integer::sum);
        }
        PriorityQueue<Map.Entry<String, Integer>> heap = new PriorityQueue<>((a, b) ->
                a.getValue().equals(b.getValue()) ? b.getKey().compareTo(a.getKey()) : a.getValue() - b.getValue());
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            heap.offer(entry);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        while (!heap.isEmpty()) {
            result.add(heap.poll().getKey());
        }
        Collections.reverse(result);
        return result;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: a heap of size `k` never has to hold more than the current best `k` words. Its comparator puts the weakest word on top: lower frequency first, and for equal frequency the alphabetically larger word, since that word would rank last.

**Input:** `words = ["i", "love", "leetcode", "i", "love", "coding"]`, `k = 2`.

| Word | Count | Heap after offering | Removed |
|------|-------|---------------------|---------|
| i | 2 | [i] | |
| love | 2 | [love, i] | |
| leetcode | 1 | [leetcode, love, i] -> top removed | leetcode |
| coding | 1 | [coding, love, i] -> top removed | coding |

The heap holds `love` (top) and `i`. Polling and reversing gives the best first.

**Output:** `["i", "love"]`.
