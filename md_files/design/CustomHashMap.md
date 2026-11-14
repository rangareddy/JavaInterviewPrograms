# Design a HashMap

Design a hash map from scratch (without using the built-in `HashMap`) that supports `put(key, value)`, `get(key)` (returning -1 when the key is missing), `remove(key)` and `size()`, and grows automatically as entries are added.

## Algorithm Strategy (Array of Buckets with Chaining and Resizing)

1. Keep an array of buckets; every bucket is a singly linked list of entries (separate chaining) so keys with the same index can coexist.
2. Compute the bucket index from the key: `(hashCode & 0x7fffffff) % capacity`, which is never negative.
3. `put`: walk the bucket; if the key exists replace its value, otherwise insert a new entry at the head and increase the size. `get` and `remove` walk the same bucket.
4. When the load factor (`size / capacity`) exceeds `0.75`, double the array and re-insert every entry with the new capacity, so the chains stay short and operations stay O(1) on average.

## Example walkthrough

- `put(1, 10), put(2, 20), put(17, 30)` with capacity 16: keys 1 and 17 land in the same bucket and are chained.
- `get(17)` returns 30, `remove(1)` unlinks only the entry for key 1, and `get(1)` then returns -1.

## Complexity Analysis

- **Time Complexity:** `O(1) average, O(n) worst case` — Per operation; resizing is amortised over the insertions.
- **Space Complexity:** `O(n)` — One entry per stored key plus the bucket array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Direct Address Table (Limited Keys)

When keys are small non-negative integers, an array indexed by the key is a perfect hash map. The memory use depends on the key range.

```java
static class DirectMap {
    private final int[] values;
    private final boolean[] present;
    DirectMap(int maxKey) { values = new int[maxKey + 1]; present = new boolean[maxKey + 1]; }
    void put(int key, int value) { values[key] = value; present[key] = true; }
    int get(int key) { return present[key] ? values[key] : -1; }
    void remove(int key) { present[key] = false; }
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(key range)

### Approach 2: Sorted Tree Map Wrapper

Back the map with a balanced tree. All operations are O(log n) but keys stay sorted.

```java
static class TreeBackedMap {
    private final TreeMap<Integer, Integer> map = new TreeMap<>();
    void put(int key, int value) { map.put(key, value); }
    int get(int key) { return map.getOrDefault(key, -1); }
    void remove(int key) { map.remove(key); }
    int size() { return map.size(); }
}
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(n)

### Approach 3: Open Addressing with Linear Probing

Store entries directly in the array and probe the following slots on a collision; deletions leave a tombstone marker.

```java
static class ProbingMap {
    private int[] keys = new int[16], values = new int[16];
    private byte[] state = new byte[16];            // 0 = empty, 1 = used, 2 = deleted
    private int used;                               // used + deleted slots
    private int size;

    void put(int key, int value) {
        if ((used + 1) * 4 > keys.length * 3) resize();
        int i = slot(key, keys.length);
        int firstDeleted = -1;
        while (state[i] != 0) {
            if (state[i] == 1 && keys[i] == key) { values[i] = value; return; }
            if (state[i] == 2 && firstDeleted < 0) firstDeleted = i;
            i = (i + 1) % keys.length;
        }
        if (firstDeleted >= 0) i = firstDeleted; else used++;
        keys[i] = key; values[i] = value; state[i] = 1; size++;
    }

    int get(int key) {
        int i = slot(key, keys.length);
        while (state[i] != 0) {
            if (state[i] == 1 && keys[i] == key) return values[i];
            i = (i + 1) % keys.length;
        }
        return -1;
    }

    void remove(int key) {
        int i = slot(key, keys.length);
        while (state[i] != 0) {
            if (state[i] == 1 && keys[i] == key) { state[i] = 2; size--; return; }
            i = (i + 1) % keys.length;
        }
    }

    int size() { return size; }

    private void resize() {
        int[] oldKeys = keys, oldValues = values;
        byte[] oldState = state;
        keys = new int[oldKeys.length * 2]; values = new int[keys.length]; state = new byte[keys.length];
        used = 0; size = 0;
        for (int i = 0; i < oldKeys.length; i++) if (oldState[i] == 1) put(oldKeys[i], oldValues[i]);
    }

    private static int slot(int key, int capacity) { return (Integer.hashCode(key) & 0x7fffffff) % capacity; }
}
```

- **Time Complexity:** O(1) average
- **Space Complexity:** O(n)

### Approach 4: Separate Chaining with Resizing (Optimized)

An array of linked lists that doubles when the load factor exceeds 0.75. This is the version implemented in the repository.

```java
// see the Source Code section for the full CustomHashMap class
static class ChainedMap {
    private static class Entry { final int key; int value; Entry next; Entry(int k, int v, Entry n) { key = k; value = v; next = n; } }
    private Entry[] buckets = new Entry[16];
    private int size;

    void put(int key, int value) {
        int index = (Integer.hashCode(key) & 0x7fffffff) % buckets.length;
        for (Entry e = buckets[index]; e != null; e = e.next) if (e.key == key) { e.value = value; return; }
        buckets[index] = new Entry(key, value, buckets[index]);
        if (++size > buckets.length * 0.75) resize();
    }

    int get(int key) {
        for (Entry e = buckets[(Integer.hashCode(key) & 0x7fffffff) % buckets.length]; e != null; e = e.next) if (e.key == key) return e.value;
        return -1;
    }

    void remove(int key) {
        int index = (Integer.hashCode(key) & 0x7fffffff) % buckets.length;
        Entry previous = null;
        for (Entry e = buckets[index]; e != null; previous = e, e = e.next)
            if (e.key == key) { if (previous == null) buckets[index] = e.next; else previous.next = e.next; size--; return; }
    }

    int size() { return size; }

    private void resize() {
        Entry[] bigger = new Entry[buckets.length * 2];
        for (Entry head : buckets)
            for (Entry e = head; e != null; ) {
                Entry next = e.next;
                int index = (Integer.hashCode(e.key) & 0x7fffffff) % bigger.length;
                e.next = bigger[index];
                bigger[index] = e;
                e = next;
            }
        buckets = bigger;
    }
}
```

- **Time Complexity:** O(1) average
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Direct Address Table (Limited Keys) | O(1) | O(key range) |
| 2 | Sorted Tree Map Wrapper | O(log n) | O(n) |
| 3 | Open Addressing with Linear Probing | O(1) average | O(n) |
| 4 | Separate Chaining with Resizing (Optimized) | O(1) average | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### CustomHashMap.java

```java
public class CustomHashMap {

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Entry[] buckets = new Entry[INITIAL_CAPACITY];
    private int size;

    public static void main(String[] args) {
        CustomHashMap map = new CustomHashMap();
        map.put(1, 10);
        map.put(2, 20);
        map.put(17, 30);
        System.out.println("Input  : put(1, 10), put(2, 20), put(17, 30), get(17), get(5)");
        System.out.println("Output : get(17) = " + map.get(17) + ", get(5) = " + map.get(5) + ", size = " + map.size());
        System.out.println();

        map.put(1, 11);
        map.remove(2);
        System.out.println("Input  : put(1, 11), remove(2), get(1), get(2)");
        System.out.println("Output : get(1) = " + map.get(1) + ", get(2) = " + map.get(2) + ", size = " + map.size());
        System.out.println();

        for (int i = 100; i < 140; i++) {
            map.put(i, i * 2);
        }
        System.out.println("Input  : put 40 more keys (forces the table to grow)");
        System.out.println("Output : get(139) = " + map.get(139) + ", size = " + map.size());
    }

    public void put(int key, int value) {
        int index = indexFor(key, buckets.length);
        for (Entry entry = buckets[index]; entry != null; entry = entry.next) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }

        buckets[index] = new Entry(key, value, buckets[index]);
        size++;
        if (size > buckets.length * LOAD_FACTOR) {
            resize();
        }
    }

    public int get(int key) {
        for (Entry entry = buckets[indexFor(key, buckets.length)]; entry != null; entry = entry.next) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return -1;
    }

    public void remove(int key) {
        int index = indexFor(key, buckets.length);
        Entry previous = null;
        for (Entry entry = buckets[index]; entry != null; entry = entry.next) {
            if (entry.key == key) {
                if (previous == null) {
                    buckets[index] = entry.next;
                } else {
                    previous.next = entry.next;
                }
                size--;
                return;
            }
            previous = entry;
        }
    }

    public int size() {
        return size;
    }

    private void resize() {
        Entry[] bigger = new Entry[buckets.length * 2];
        for (Entry head : buckets) {
            for (Entry entry = head; entry != null; ) {
                Entry next = entry.next;
                int index = indexFor(entry.key, bigger.length);
                entry.next = bigger[index];
                bigger[index] = entry;
                entry = next;
            }
        }
        buckets = bigger;
    }

    private static int indexFor(int key, int capacity) {
        return (Integer.hashCode(key) & 0x7fffffff) % capacity;
    }

    private static class Entry {
        final int key;
        int value;
        Entry next;

        Entry(int key, int value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
```
