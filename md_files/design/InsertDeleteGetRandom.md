# Insert Delete GetRandom O(1)

Implement a `RandomizedSet` with `insert(val)` (returns true if the value was absent), `remove(val)` (returns true if it was present) and `getRandom()` (returns a random element, each element with equal probability). Every operation must run in average `O(1)` time.

## Algorithm Strategy (ArrayList plus HashMap with Swap-Remove)

1. A hash map gives `O(1)` insert and lookup but cannot return a random element in `O(1)`; an array list can return a random element by index but cannot delete from the middle in `O(1)`. Use both.
2. Store the values in an `ArrayList` and a map from each value to its index in the list.
3. `insert` appends to the list and records the index in the map.
4. `remove` swaps the value with the last element of the list, updates the index of the moved element in the map, then removes the last element. Removing the tail of an array list is `O(1)`.
5. `getRandom` picks a random index in `[0, size)`, which gives every element the same probability.

## Example walkthrough

- Insert 1, 2, 3: list `[1, 2, 3]`, map `{1:0, 2:1, 3:2}`.
- Remove 1: swap with the last element, giving `[3, 2, 1]` and then drop the tail: `[3, 2]`, map `{3:0, 2:1}`.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Average for all three operations.
- **Space Complexity:** `O(n)` — The list and the map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: ArrayList Only

Use `contains` and `remove(Object)` on the list. Everything but `getRandom` scans the list.

```java
class ListRandomizedSet {
    private final List<Integer> values = new ArrayList<>();
    private final Random random = new Random();

    boolean insert(int val) {
        if (values.contains(val)) return false;
        values.add(val);
        return true;
    }

    boolean remove(int val) { return values.remove(Integer.valueOf(val)); }

    int getRandom() { return values.get(random.nextInt(values.size())); }
}
```

- **Time Complexity:** insert/remove O(n), getRandom O(1)
- **Space Complexity:** O(n)

### Approach 2: ArrayList and HashMap with Swap-Remove (Optimized)

Keep the index of each value in a map and delete by swapping the value with the last element. This is the version implemented in the repository.

```java
private final List<Integer> values = new ArrayList<>();
private final Map<Integer, Integer> indexOf = new HashMap<>();
private final Random random = new Random();

public boolean insert(int val) {
    if (indexOf.containsKey(val)) {
        return false;
    }
    indexOf.put(val, values.size());
    values.add(val);
    return true;
}

public boolean remove(int val) {
    Integer index = indexOf.remove(val);
    if (index == null) {
        return false;
    }
    int last = values.remove(values.size() - 1);
    if (last != val) {
        values.set(index, last);
        indexOf.put(last, index);
    }
    return true;
}

public int getRandom() {
    return values.get(random.nextInt(values.size()));
}
```

- **Time Complexity:** O(1) average for every operation
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | ArrayList Only | insert/remove O(n), getRandom O(1) | O(n) |
| 2 | ArrayList and HashMap with Swap-Remove (Optimized) | O(1) average for every operation | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### InsertDeleteGetRandom.java

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InsertDeleteGetRandom {

    public static void main(String[] args) {
        InsertDeleteGetRandom set = new InsertDeleteGetRandom();
        System.out.println("Input  : insert(1), remove(2), insert(2), getRandom(), remove(1), insert(2), getRandom()");
        boolean a = set.insert(1);
        boolean b = set.remove(2);
        boolean c = set.insert(2);
        int r1 = set.getRandom();
        boolean d = set.remove(1);
        boolean e = set.insert(2);
        int r2 = set.getRandom();
        System.out.println("Output : " + a + ", " + b + ", " + c + ", " + (r1 == 1 || r1 == 2) + ", " + d + ", " + e + ", " + r2);
    }

    private final List<Integer> values = new ArrayList<>();
    private final Map<Integer, Integer> indexOf = new HashMap<>();
    private final Random random = new Random();

    public boolean insert(int val) {
        if (indexOf.containsKey(val)) {
            return false;
        }
        indexOf.put(val, values.size());
        values.add(val);
        return true;
    }

    public boolean remove(int val) {
        Integer index = indexOf.remove(val);
        if (index == null) {
            return false;
        }
        int last = values.remove(values.size() - 1);
        if (last != val) {
            values.set(index, last);
            indexOf.put(last, index);
        }
        return true;
    }

    public int getRandom() {
        return values.get(random.nextInt(values.size()));
    }
}
```
