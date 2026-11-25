# Design Memory Allocator

Design an allocator for a memory array of `n` units, all initially free. `allocate(size, mID)` finds the leftmost block of `size` consecutive free units, marks them with `mID` and returns the index of its first unit, or `-1` if no such block exists. `freeMemory(mID)` frees every unit with that `mID` and returns how many were freed.

## Algorithm Strategy (Array Scan for Runs of Free Cells)

1. Store the memory as an `int[]` where 0 means free and any other value is an allocation id.
2. `allocate` scans left to right counting the current run of free cells; when the run reaches `size`, fill that range and return its start.
3. If the scan ends without a long enough run, return -1.
4. `freeMemory` sweeps the array and zeroes every cell equal to `mID`, counting them.

## Example walkthrough

- On 10 units: `allocate(1, 1)` returns 0, `allocate(1, 2)` returns 1, `freeMemory(1)` returns 1, `allocate(1, 3)` returns 0 again.
- `allocate(10, 4)` fails because no run of 10 free units exists.

## Complexity Analysis

- **Time Complexity:** `O(n) per operation` — One scan of the memory.
- **Space Complexity:** `O(n)` — The memory array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Interval List of Free Blocks

Track free blocks as intervals and carve from the first one that is large enough. Faster for big memories, more code to maintain.

```java
public static class Intervals {
    private final java.util.TreeMap<Integer, Integer> free = new java.util.TreeMap<>(); private final java.util.Map<Integer, java.util.List<int[]>> used = new java.util.HashMap<>();
    public Intervals(int n) { free.put(0, n); }
    public int allocate(int size, int id) {
        for (java.util.Map.Entry<Integer, Integer> e : free.entrySet()) if (e.getValue() >= size) {
            int s = e.getKey(), len = e.getValue(); free.remove(s); if (len > size) free.put(s + size, len - size);
            used.computeIfAbsent(id, k -> new java.util.ArrayList<>()).add(new int[]{s, size}); return s;
        }
        return -1;
    }
    public int freeMemory(int id) {
        java.util.List<int[]> l = used.remove(id); if (l == null) return 0; int total = 0;
        for (int[] b : l) { total += b[1]; int s = b[0], len = b[1];
            java.util.Map.Entry<Integer, Integer> lo = free.floorEntry(s); if (lo != null && lo.getKey() + lo.getValue() == s) { s = lo.getKey(); len += lo.getValue(); free.remove(lo.getKey()); }
            Integer hi = free.get(s + len); if (hi != null) { len += hi; free.remove(s + len - hi); } free.put(s, len); }
        return total;
    }
}
```

- **Time Complexity:** O(blocks)
- **Space Complexity:** O(blocks)

### Approach 2: Array Scan (Optimized)

Keep the memory as an array and scan for a run of free cells. This is the version implemented in the repository.

```java
public static class Scan {
    private final int[] m;
    public Scan(int n) { m = new int[n]; }
    public int allocate(int size, int id) { int run = 0; for (int i = 0; i < m.length; i++) { run = m[i] == 0 ? run + 1 : 0; if (run == size) { int s = i - size + 1; for (int j = s; j <= i; j++) m[j] = id; return s; } } return -1; }
    public int freeMemory(int id) { int f = 0; for (int i = 0; i < m.length; i++) if (m[i] == id) { m[i] = 0; f++; } return f; }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Interval List of Free Blocks | O(blocks) | O(blocks) |
| 2 | Array Scan (Optimized) | O(n) | O(n) |

## Source Code

### DesignMemoryAllocator.java

```java
public class DesignMemoryAllocator {

    public static void main(String[] args) {
        DesignMemoryAllocator allocator = new DesignMemoryAllocator(10);
        System.out.println("Input  : allocate(1, 1)");
        System.out.println("Output : " + allocator.allocate(1, 1));
        System.out.println();
        System.out.println("Input  : allocate(1, 2), freeMemory(1), allocate(3, 3), allocate(1, 4)");
        System.out.println("Output : " + allocator.allocate(1, 2) + ", " + allocator.freeMemory(1) + ", " + allocator.allocate(3, 3) + ", " + allocator.allocate(1, 4));
        System.out.println();
        System.out.println("Input  : allocate(10, 5), freeMemory(7)");
        System.out.println("Output : " + allocator.allocate(10, 5) + ", " + allocator.freeMemory(7));
    }

    private final int[] memory;

    public DesignMemoryAllocator(int n) {
        memory = new int[n];
    }

    public int allocate(int size, int mID) {
        int run = 0;
        for (int i = 0; i < memory.length; i++) {
            run = memory[i] == 0 ? run + 1 : 0;
            if (run == size) {
                int start = i - size + 1;
                for (int j = start; j <= i; j++) {
                    memory[j] = mID;
                }
                return start;
            }
        }
        return -1;
    }

    public int freeMemory(int mID) {
        int freed = 0;
        for (int i = 0; i < memory.length; i++) {
            if (memory[i] == mID) {
                memory[i] = 0;
                freed++;
            }
        }
        return freed;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: for small memories, the clearest allocator is a first-fit scan over an array where 0 marks a free cell. Counting the current run of free cells finds the leftmost block of the required size in one pass.

**Sequence** on 10 units:

| Call | Memory afterwards (id per cell, `.` is free) | Result |
|------|----------------------------------------------|--------|
| allocate(1, 1) | 1 . . . . . . . . . | 0 |
| allocate(1, 2) | 1 2 . . . . . . . . | 1 |
| freeMemory(1) | . 2 . . . . . . . . | 1 |
| allocate(3, 3) | . 2 3 3 3 . . . . . | 2 |
| allocate(1, 4) | 4 2 3 3 3 . . . . . | 0 |

**First output in `main`:** `0`.
