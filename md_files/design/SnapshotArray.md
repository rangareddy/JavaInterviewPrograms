# Snapshot Array

Implement a `SnapshotArray` with `set(index, val)` to set an element, `snap()` to take a snapshot and return its id (the number of snapshots minus one), and `get(index, snapId)` to return the value at `index` when the snapshot `snapId` was taken. The array starts with all zeros.

## Algorithm Strategy (Version List per Index)

1. Do not copy the array on `snap()`: keep only a counter `snapId`.
2. For each index store a list of `(snapId, value)` entries in increasing `snapId` order; the first entry is `(0, 0)`.
3. `set` updates the last entry if it belongs to the current snapshot, otherwise appends `(snapId, val)`.
4. `get(index, id)` binary searches the index's list for the last entry with `snapId <= id` and returns its value.

## Example walkthrough

- `set(0, 5)`, `snap()` (id 0), `set(0, 6)`, then `get(0, 0)` finds the entry `(0, 5)`.
- Result: `5`.

## Complexity Analysis

- **Time Complexity:** `O(1) set and snap, O(log s) get` — `s` is the number of changes at that index.
- **Space Complexity:** `O(number of sets)` — One entry per change, not per snapshot.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Copy the Array on Every Snapshot

Store a full copy per snapshot. `get` is O(1) but `snap` and memory are O(length) each time.

```java
public static class Copying {
    private int[] cur; private final java.util.List<int[]> snaps = new java.util.ArrayList<>();
    public Copying(int n) { cur = new int[n]; }
    public void set(int i, int v) { cur[i] = v; }
    public int snap() { snaps.add(cur.clone()); return snaps.size() - 1; }
    public int get(int i, int id) { return snaps.get(id)[i]; }
}
```

- **Time Complexity:** snap O(n), get O(1)
- **Space Complexity:** O(n * snaps)

### Approach 2: Version List per Index (Optimized)

Store only the changes per index and binary search them. This is the version implemented in the repository.

```java
public static class Versions {
    private final java.util.List<java.util.TreeMap<Integer, Integer>> h = new java.util.ArrayList<>(); private int id;
    public Versions(int n) { for (int i = 0; i < n; i++) { java.util.TreeMap<Integer, Integer> m = new java.util.TreeMap<>(); m.put(0, 0); h.add(m); } }
    public void set(int i, int v) { h.get(i).put(id, v); }
    public int snap() { return id++; }
    public int get(int i, int s) { return h.get(i).floorEntry(s).getValue(); }
}
```

- **Time Complexity:** set O(log s), snap O(1), get O(log s)
- **Space Complexity:** O(sets)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Copy the Array on Every Snapshot | snap O(n), get O(1) | O(n * snaps) |
| 2 | Version List per Index (Optimized) | set O(log s), snap O(1), get O(log s) | O(sets) |

## Source Code

### SnapshotArray.java

```java
import java.util.ArrayList;
import java.util.List;

public class SnapshotArray {

    public static void main(String[] args) {
        SnapshotArray array = new SnapshotArray(3);
        array.set(0, 5);
        int id = array.snap();
        array.set(0, 6);
        System.out.println("Input  : set(0, 5), snap(), set(0, 6), get(0, " + id + ")");
        System.out.println("Output : " + array.get(0, id));
        System.out.println();
        int second = array.snap();
        System.out.println("Input  : snap(), get(0, " + second + "), get(1, " + second + ")");
        System.out.println("Output : " + array.get(0, second) + ", " + array.get(1, second));
    }

    private final List<List<int[]>> history = new ArrayList<>();
    private int snapId;

    public SnapshotArray(int length) {
        for (int i = 0; i < length; i++) {
            List<int[]> versions = new ArrayList<>();
            versions.add(new int[]{0, 0});
            history.add(versions);
        }
    }

    public void set(int index, int val) {
        List<int[]> versions = history.get(index);
        int[] last = versions.get(versions.size() - 1);
        if (last[0] == snapId) {
            last[1] = val;
        } else {
            versions.add(new int[]{snapId, val});
        }
    }

    public int snap() {
        return snapId++;
    }

    public int get(int index, int id) {
        List<int[]> versions = history.get(index);
        int lo = 0;
        int hi = versions.size() - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (versions.get(mid)[0] <= id) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return versions.get(lo)[1];
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository (written with plain lists and a manual binary search).

The idea: most elements do not change between snapshots, so store only the changes. A snapshot is then just a counter, and reading an old value means finding the latest change made at or before that snapshot.

**Sequence** on a 3-element array:

| Call | History of index 0 | Result |
|------|--------------------|--------|
| `set(0, 5)` | [(0, 0)] -> [(0, 5)] (same snapshot, overwrite) | |
| `snap()` | counter becomes 1 | 0 |
| `set(0, 6)` | [(0, 5), (1, 6)] | |
| `get(0, 0)` | last entry with snapId <= 0 is (0, 5) | 5 |

**Output of the first `get` in `main`:** `5`.
