# LFU Cache

Design a Least Frequently Used (LFU) cache with a fixed `capacity`. `get(key)` returns the value or `-1`. `put(key, value)` inserts or updates a key; when the cache is full it first evicts the key used the fewest times, and if several keys tie it evicts the least recently used among them. Every `get` or `put` of a key counts as one use. Both operations must run in `O(1)` average time.

## Algorithm Strategy (Three Maps with Frequency Buckets)

1. Keep `values` (key to value), `freq` (key to its use count) and `buckets` (use count to the set of keys with that count). Each bucket is a `LinkedHashSet`, which keeps insertion order, so its first element is the least recently used key of that frequency.
2. Also keep `minFreq`, the smallest frequency that currently has a non-empty bucket.
3. On a use of key `k` with frequency `f`: remove `k` from bucket `f` and add it to bucket `f + 1`. If bucket `f` became empty and `f == minFreq`, then `minFreq` becomes `f + 1`.
4. To insert a new key into a full cache, evict the first key of bucket `minFreq`, then add the new key with frequency 1 and set `minFreq = 1`. Every step is a constant number of hash operations.

## Example walkthrough

- With capacity 2: `put(1, 1)`, `put(2, 2)`, `get(1)` returns 1 and bumps key 1 to frequency 2.
- `put(3, 3)` must evict: bucket 1 holds only key 2, so key 2 goes. `get(2)` is `-1`, `get(3)` is 3, and after another `put(4, 4)` key 1 and key 3 tie at frequency 2, so the least recently used of them (key 1) is evicted.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Average time for both `get` and `put`.
- **Space Complexity:** `O(capacity)` — The three maps.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Linear Scan on Eviction

Store a use count and a last-used tick per key and scan all keys to find the eviction victim.

```java
class ScanLFUCache {
    private final int capacity;
    private long tick;
    private final Map<Integer, int[]> entries = new HashMap<>();   // key -> {value, frequency, lastUsed}

    ScanLFUCache(int capacity) { this.capacity = capacity; }

    int get(int key) {
        int[] e = entries.get(key);
        if (e == null) return -1;
        e[1]++;
        e[2] = (int) ++tick;
        return e[0];
    }

    void put(int key, int value) {
        if (capacity == 0) return;
        int[] e = entries.get(key);
        if (e != null) {
            e[0] = value; e[1]++; e[2] = (int) ++tick;
            return;
        }
        if (entries.size() == capacity) {
            int victim = -1;
            for (Map.Entry<Integer, int[]> entry : entries.entrySet()) {
                int[] c = entry.getValue();
                if (victim == -1 || c[1] < entries.get(victim)[1]
                        || (c[1] == entries.get(victim)[1] && c[2] < entries.get(victim)[2])) victim = entry.getKey();
            }
            entries.remove(victim);
        }
        entries.put(key, new int[]{value, 1, (int) ++tick});
    }
}
```

- **Time Complexity:** get O(1), put O(capacity) on eviction
- **Space Complexity:** O(capacity)

### Approach 2: Frequency Buckets of Linked Hash Sets (Optimized)

Group keys by use count in insertion-ordered sets and track the minimum frequency, so the victim is found without a scan. This is the version implemented in the repository.

```java
private final int capacity;
private int minFreq;
private final Map<Integer, Integer> values = new HashMap<>();
private final Map<Integer, Integer> freq = new HashMap<>();
private final Map<Integer, LinkedHashSet<Integer>> buckets = new HashMap<>();

public LFUCache(int capacity) {
    this.capacity = capacity;
}

public int get(int key) {
    if (!values.containsKey(key)) {
        return -1;
    }
    touch(key);
    return values.get(key);
}

public void put(int key, int value) {
    if (capacity == 0) {
        return;
    }
    if (values.containsKey(key)) {
        values.put(key, value);
        touch(key);
        return;
    }
    if (values.size() == capacity) {
        LinkedHashSet<Integer> lowest = buckets.get(minFreq);
        int evict = lowest.iterator().next();
        lowest.remove(evict);
        values.remove(evict);
        freq.remove(evict);
    }
    values.put(key, value);
    freq.put(key, 1);
    buckets.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
    minFreq = 1;
}

private void touch(int key) {
    int f = freq.get(key);
    LinkedHashSet<Integer> bucket = buckets.get(f);
    bucket.remove(key);
    if (bucket.isEmpty() && f == minFreq) {
        minFreq++;
    }
    freq.put(key, f + 1);
    buckets.computeIfAbsent(f + 1, k -> new LinkedHashSet<>()).add(key);
}
```

- **Time Complexity:** O(1) average for get and put
- **Space Complexity:** O(capacity)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Linear Scan on Eviction | get O(1), put O(capacity) on eviction | O(capacity) |
| 2 | Frequency Buckets of Linked Hash Sets (Optimized) | O(1) average for get and put | O(capacity) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: evict the key used least often, and among equals the one used longest ago. Three maps do it in O(1): `values` (key -> value), `freq` (key -> use count) and `buckets` (count -> `LinkedHashSet` of keys, in insertion order, so the first element is the oldest). `minFreq` is the lowest count currently present. Every access (`touch`) moves the key from bucket `f` to bucket `f + 1`, and bumps `minFreq` when bucket `minFreq` becomes empty. A new key goes into bucket 1 and sets `minFreq = 1`; eviction removes the first key of bucket `minFreq`.

**Input:** `LFUCache(2)`, then `put(1,1)`, `put(2,2)`, `get(1)`, `put(3,3)`, `get(2)`, `get(3)`, `put(4,4)`, `get(1)`, `get(3)`, `get(4)`.

| Step | Operation | Buckets afterwards (count: keys, oldest first) | `minFreq` | Result |
|------|-----------|------------------------------------------------|-----------|--------|
| 1 | `put(1,1)` | 1: [1] | 1 | |
| 2 | `put(2,2)` | 1: [1, 2] | 1 | |
| 3 | `get(1)` | 1: [2], 2: [1] | 1 | `1` |
| 4 | `put(3,3)` | full: evict first of bucket 1 = key 2; then 1: [3], 2: [1] | 1 | |
| 5 | `get(2)` | unchanged (2 is not in `values`) | 1 | `-1` |
| 6 | `get(3)` | bucket 1 empties and `f == minFreq`: 2: [1, 3] | 2 | `3` |
| 7 | `put(4,4)` | full: evict first of bucket 2 = key 1; then 1: [4], 2: [3] | 1 | |
| 8 | `get(1)` | unchanged | 1 | `-1` |
| 9 | `get(3)` | 1: [4], 3: [3] (bucket 2 empties but `minFreq` is 1, no change) | 1 | `3` |
| 10 | `get(4)` | bucket 1 empties and `f == minFreq`: 2: [4], 3: [3] | 2 | `4` |

Step 7 shows the tie-break: keys 1 and 3 both had count 2, and key 1 sat in front of the bucket (used earlier), so it is evicted.

**Edge cases.** Capacity `0` makes `put` return immediately. `get` of a missing key returns `-1` without touching any bucket. `put` of an existing key updates the value and counts as a use (`touch`) without evicting. Everything is O(1) per operation.

**Result:** the gets return `1, -1, 3, -1, 3, 4`, exactly what the class prints. No diagram is added; the bucket table is the picture.

## Source Code

### LFUCache.java

```java
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache {

    public static void main(String[] args) {
        LFUCache cache = new LFUCache(2);
        System.out.println("Input  : LFUCache(2), put(1, 1), put(2, 2), get(1), put(3, 3), get(2), get(3), put(4, 4), get(1), get(3), get(4)");
        cache.put(1, 1);
        cache.put(2, 2);
        int a = cache.get(1);
        cache.put(3, 3);
        int b = cache.get(2);
        int c = cache.get(3);
        cache.put(4, 4);
        int d = cache.get(1);
        int e = cache.get(3);
        int f = cache.get(4);
        System.out.println("Output : " + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f);
    }

    private final int capacity;
    private int minFreq;
    private final Map<Integer, Integer> values = new HashMap<>();
    private final Map<Integer, Integer> freq = new HashMap<>();
    private final Map<Integer, LinkedHashSet<Integer>> buckets = new HashMap<>();

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!values.containsKey(key)) {
            return -1;
        }
        touch(key);
        return values.get(key);
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        if (values.containsKey(key)) {
            values.put(key, value);
            touch(key);
            return;
        }
        if (values.size() == capacity) {
            LinkedHashSet<Integer> lowest = buckets.get(minFreq);
            int evict = lowest.iterator().next();
            lowest.remove(evict);
            values.remove(evict);
            freq.remove(evict);
        }
        values.put(key, value);
        freq.put(key, 1);
        buckets.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        minFreq = 1;
    }

    private void touch(int key) {
        int f = freq.get(key);
        LinkedHashSet<Integer> bucket = buckets.get(f);
        bucket.remove(key);
        if (bucket.isEmpty() && f == minFreq) {
            minFreq++;
        }
        freq.put(key, f + 1);
        buckets.computeIfAbsent(f + 1, k -> new LinkedHashSet<>()).add(key);
    }
}
```
