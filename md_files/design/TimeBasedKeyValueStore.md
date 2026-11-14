# Time Based Key-Value Store

Design a time-based key-value data structure that can store multiple values for the same key at different timestamps and retrieve the value of a key at a given timestamp. `set(key, value, timestamp)` stores the value, `get(key, timestamp)` returns the value stored with the largest timestamp `<= timestamp` (or an empty string). Timestamps passed to `set` are strictly increasing.

## Algorithm Strategy (Hash Map of Timestamp-Sorted Lists + Binary Search)

1. Map every key to a list of `(timestamp, value)` entries. Because the timestamps of `set` calls are strictly increasing, every list stays sorted by timestamp simply by appending.
2. `set` appends the entry to the list of its key.
3. `get` binary-searches the list of the key for the last entry whose timestamp is `<= timestamp`.
4. If there is no such entry (unknown key or all timestamps are larger), return the empty string.

## Example walkthrough

- After `set("foo","bar",1)` and `set("foo","bar2",4)`: `get("foo",1)` is `"bar"`, `get("foo",3)` is `"bar"` (largest timestamp 1 <= 3), `get("foo",4)` is `"bar2"`.
- `get("foo",0)` has no timestamp <= 0 and returns `""`.

## Complexity Analysis

- **Time Complexity:** `O(1) for set, O(log n) for get` — `n` is the number of values stored for the key.
- **Space Complexity:** `O(total number of entries)` — Every set call is stored.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Linear Scan Backwards

Keep the entries of a key in a list in the order they were added (increasing timestamps) and scan from the newest entry until one is not newer than the requested timestamp.

```java
static class ScanStore {
    private final Map<String, List<Object[]>> store = new HashMap<>();      // {timestamp, value}

    public void set(String key, String value, int timestamp) {
        store.computeIfAbsent(key, k -> new ArrayList<>()).add(new Object[]{timestamp, value});
    }

    public String get(String key, int timestamp) {
        List<Object[]> entries = store.getOrDefault(key, Collections.emptyList());
        for (int i = entries.size() - 1; i >= 0; i--) {
            if ((Integer) entries.get(i)[0] <= timestamp) return (String) entries.get(i)[1];
        }
        return "";
    }
}
```

- **Time Complexity:** set O(1), get O(n)
- **Space Complexity:** O(total entries)

### Approach 2: TreeMap per Key

A `TreeMap<Integer, String>` per key keeps the timestamps sorted for free; `floorEntry(timestamp)` is exactly "the largest timestamp not after this one". It also works when timestamps arrive in any order.

```java
static class TreeMapStore {
    private final Map<String, TreeMap<Integer, String>> store = new HashMap<>();

    public void set(String key, String value, int timestamp) {
        store.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        TreeMap<Integer, String> history = store.get(key);
        if (history == null) return "";
        Map.Entry<Integer, String> entry = history.floorEntry(timestamp);
        return entry == null ? "" : entry.getValue();
    }
}
```

- **Time Complexity:** set O(log n), get O(log n)
- **Space Complexity:** O(total entries)

### Approach 3: Binary Search in a List per Key (Optimized)

Timestamps are strictly increasing, so appending keeps each list sorted and `get` can binary-search it. `set` is `O(1)`. This is the version implemented in the repository.

```java
static class ListStore {
    private static class Entry { final int timestamp; final String value; Entry(int t, String v) { timestamp = t; value = v; } }
    private final Map<String, List<Entry>> store = new HashMap<>();

    public void set(String key, String value, int timestamp) {
        store.computeIfAbsent(key, k -> new ArrayList<>()).add(new Entry(timestamp, value));
    }

    public String get(String key, int timestamp) {
        List<Entry> entries = store.get(key);
        if (entries == null) return "";
        int low = 0, high = entries.size() - 1;
        String result = "";
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (entries.get(mid).timestamp <= timestamp) { result = entries.get(mid).value; low = mid + 1; }
            else high = mid - 1;
        }
        return result;
    }
}
```

- **Time Complexity:** set O(1), get O(log n)
- **Space Complexity:** O(total entries)

### Approach 4: Parallel Lists and Collections.binarySearch

Store the timestamps of a key in an `ArrayList<Integer>` and the values in a second list at the same positions, then let `Collections.binarySearch` find the position: a negative result encodes the insertion point, so the entry just before it is the answer.

```java
static class ParallelStore {
    private final Map<String, List<Integer>> times = new HashMap<>();
    private final Map<String, List<String>> values = new HashMap<>();

    public void set(String key, String value, int timestamp) {
        times.computeIfAbsent(key, k -> new ArrayList<>()).add(timestamp);
        values.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    public String get(String key, int timestamp) {
        List<Integer> stamps = times.get(key);
        if (stamps == null) return "";
        int position = Collections.binarySearch(stamps, timestamp);
        if (position < 0) position = -position - 2;          // the entry before the insertion point
        return position < 0 ? "" : values.get(key).get(position);
    }
}
```

- **Time Complexity:** set O(1), get O(log n)
- **Space Complexity:** O(total entries)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Linear Scan Backwards | set O(1), get O(n) | O(total entries) |
| 2 | TreeMap per Key | set O(log n), get O(log n) | O(total entries) |
| 3 | Binary Search in a List per Key (Optimized) | set O(1), get O(log n) | O(total entries) |
| 4 | Parallel Lists and Collections.binarySearch | set O(1), get O(log n) | O(total entries) |

## Source Code

### TimeBasedKeyValueStore.java

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeBasedKeyValueStore {

    private static class Entry {
        final int timestamp;
        final String value;

        Entry(int timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    private final Map<String, List<Entry>> store = new HashMap<>();

    public static void main(String[] args) {
        TimeBasedKeyValueStore timeMap = new TimeBasedKeyValueStore();
        timeMap.set("foo", "bar", 1);
        System.out.println("Input  : set(\"foo\", \"bar\", 1)");
        System.out.println("Input  : get(\"foo\", 1)");
        System.out.println("Output : \"" + timeMap.get("foo", 1) + "\"");
        System.out.println("Input  : get(\"foo\", 3)");
        System.out.println("Output : \"" + timeMap.get("foo", 3) + "\"");
        timeMap.set("foo", "bar2", 4);
        System.out.println("Input  : set(\"foo\", \"bar2\", 4), get(\"foo\", 4)");
        System.out.println("Output : \"" + timeMap.get("foo", 4) + "\"");
        System.out.println("Input  : get(\"foo\", 5)");
        System.out.println("Output : \"" + timeMap.get("foo", 5) + "\"");
        System.out.println("Input  : get(\"foo\", 0)");
        System.out.println("Output : \"" + timeMap.get("foo", 0) + "\"");
    }

    public void set(String key, String value, int timestamp) {
        store.computeIfAbsent(key, k -> new ArrayList<>()).add(new Entry(timestamp, value));
    }

    public String get(String key, int timestamp) {
        List<Entry> entries = store.get(key);
        if (entries == null) {
            return "";
        }

        int low = 0;
        int high = entries.size() - 1;
        String result = "";
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (entries.get(mid).timestamp <= timestamp) {
                result = entries.get(mid).value;       // candidate: look for a later timestamp
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: for each key keep a list of `(timestamp, value)` entries. Because `set` calls arrive with increasing timestamps, each list is already sorted, so `get(key, t)` can binary-search for the **latest entry whose timestamp is `<= t`**. Whenever the middle entry qualifies it is remembered as the candidate and the search continues to the right for a later one; otherwise the search goes left.

**Input:** `set("foo","bar",1)`, `get("foo",1)`, `get("foo",3)`, `set("foo","bar2",4)`, `get("foo",4)`, `get("foo",5)`, `get("foo",0)`.

| Step | Operation | List for `foo` | Binary search (`low`, `high`, `mid`, check) | Result |
|------|-----------|----------------|---------------------------------------------|--------|
| 1 | `set(foo, bar, 1)` | `[(1,bar)]` | | |
| 2 | `get(foo, 1)` | same | 0,0,0: ts 1 <= 1, result = bar, `low = 1`, stop | `"bar"` |
| 3 | `get(foo, 3)` | same | 0,0,0: ts 1 <= 3, result = bar, stop | `"bar"` |
| 4 | `set(foo, bar2, 4)` | `[(1,bar), (4,bar2)]` | | |
| 5 | `get(foo, 4)` | same | 0,1,0: 1 <= 4, result = bar, `low = 1`; 1,1,1: 4 <= 4, result = bar2, `low = 2`, stop | `"bar2"` |
| 6 | `get(foo, 5)` | same | 0,1,0: bar; 1,1,1: 4 <= 5, bar2, stop | `"bar2"` |
| 7 | `get(foo, 0)` | same | 0,1,0: 1 <= 0 is false, `high = -1`, stop with the initial `""` | `""` |

Step 6 is the useful case: no entry has timestamp 5, but the latest one before it (4) is returned.

**Edge cases.** An unknown key returns `""` without any search. A timestamp earlier than every entry also returns `""` (step 7). A timestamp after the last entry returns the last value. Each `set` is O(1) (a list append) and each `get` is O(log n). The class relies on `set` timestamps being increasing; it does not sort. No diagram is added; the search table is the picture.

**Result:** the gets return `"bar"`, `"bar"`, `"bar2"`, `"bar2"`, `""`, exactly what the class prints.
