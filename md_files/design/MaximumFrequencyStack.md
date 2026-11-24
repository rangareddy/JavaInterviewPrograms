# Maximum Frequency Stack

Design a stack-like structure with `push(val)` and `pop()`. `pop()` removes and returns the most frequent element in the stack; if several elements share the highest frequency, it removes the one that was pushed closest to the top (most recently).

## Algorithm Strategy (One Stack per Frequency Level)

1. Keep `freq[val]`, the number of times `val` is in the stack, and `groups[f]`, a stack of the values whose count has reached `f`.
2. `push(val)` increments `freq[val]` to `f` and pushes `val` onto `groups[f]`; update `maxFreq` if needed.
3. `pop()` takes the top of `groups[maxFreq]`, decrements its `freq`, and lowers `maxFreq` if that group is now empty.
4. A value with count 3 appears in `groups[1]`, `groups[2]` and `groups[3]`, so popping from the top group respects recency among the most frequent.

## Example walkthrough

- Push 5, 7, 5, 7, 4, 5: the count of 5 is 3, the highest. `pop()` returns 5, then 7 (tied at 2 with 5, but pushed later), then 5, then 4.
- The first pop returns `5`.

## Complexity Analysis

- **Time Complexity:** `O(1) per operation` — Map and stack operations.
- **Space Complexity:** `O(n)` — The values across the groups.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Scan the Whole Stack on Every Pop

Keep a plain list of pushed values; on `pop`, count them and pick the most frequent, latest value. O(n) per pop.

```java
public static class Scan {
    private final java.util.List<Integer> list = new java.util.ArrayList<>();
    public void push(int v) { list.add(v); }
    public int pop() {
        java.util.Map<Integer, Integer> c = new java.util.HashMap<>(); for (int x : list) c.merge(x, 1, Integer::sum);
        int best = -1, bestCount = 0; for (int i = list.size() - 1; i >= 0; i--) { int cnt = c.get(list.get(i)); if (cnt > bestCount) { bestCount = cnt; best = i; } }
        return list.remove(best);
    }
}
```

- **Time Complexity:** push O(1), pop O(n)
- **Space Complexity:** O(n)

### Approach 2: One Stack per Frequency (Optimized)

Group values by how often they have appeared. This is the version implemented in the repository.

```java
public static class Groups {
    private final java.util.Map<Integer, Integer> f = new java.util.HashMap<>(); private final java.util.Map<Integer, java.util.Deque<Integer>> g = new java.util.HashMap<>(); private int max;
    public void push(int v) { int c = f.merge(v, 1, Integer::sum); max = Math.max(max, c); g.computeIfAbsent(c, k -> new java.util.ArrayDeque<>()).push(v); }
    public int pop() { java.util.Deque<Integer> d = g.get(max); int v = d.pop(); if (d.isEmpty()) { g.remove(max); max--; } if (f.merge(v, -1, Integer::sum) == 0) f.remove(v); return v; }
}
```

- **Time Complexity:** O(1) per operation
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Scan the Whole Stack on Every Pop | push O(1), pop O(n) | O(n) |
| 2 | One Stack per Frequency (Optimized) | O(1) per operation | O(n) |

## Source Code

### MaximumFrequencyStack.java

```java
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class MaximumFrequencyStack {

    public static void main(String[] args) {
        MaximumFrequencyStack stack = new MaximumFrequencyStack();
        for (int x : new int[]{5, 7, 5, 7, 4, 5}) {
            stack.push(x);
        }
        System.out.println("Input  : push 5, 7, 5, 7, 4, 5, then pop()");
        System.out.println("Output : " + stack.pop());
        System.out.println();
        System.out.println("Input  : pop(), pop(), pop()");
        System.out.println("Output : " + stack.pop() + ", " + stack.pop() + ", " + stack.pop());
    }

    private final Map<Integer, Integer> freq = new HashMap<>();
    private final Map<Integer, Deque<Integer>> groups = new HashMap<>();
    private int maxFreq;

    public void push(int val) {
        int f = freq.merge(val, 1, Integer::sum);
        maxFreq = Math.max(maxFreq, f);
        groups.computeIfAbsent(f, k -> new ArrayDeque<>()).push(val);
    }

    public int pop() {
        Deque<Integer> top = groups.get(maxFreq);
        int val = top.pop();
        if (top.isEmpty()) {
            groups.remove(maxFreq);
            maxFreq--;
        }
        if (freq.merge(val, -1, Integer::sum) == 0) {
            freq.remove(val);
        }
        return val;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a value with count `k` is pushed onto `k` different stacks, one per level. The top of the highest non-empty level is both the most frequent element and, among ties, the most recently pushed one.

**Pushes:** 5, 7, 5, 7, 4, 5.

| Level | Stack (top on the right) |
|-------|--------------------------|
| 1 | 5, 7, 4 |
| 2 | 5, 7 |
| 3 | 5 |

| Pop | Taken from | Returns |
|-----|-----------|---------|
| 1 | level 3 | 5 |
| 2 | level 2 | 7 |
| 3 | level 2 | 5 |
| 4 | level 1 | 4 |

**First output in `main`:** `5`.
