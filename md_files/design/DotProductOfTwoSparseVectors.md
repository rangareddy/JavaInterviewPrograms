# Dot Product of Two Sparse Vectors

Implement a `SparseVector` class built from an integer array that mostly holds zeros. It must store the vector efficiently and offer `dotProduct(other)`, the dot product of this vector and another sparse vector.

## Algorithm Strategy (Index to Value Map of Non-Zeros)

1. In the constructor keep only the non-zero entries as `(index, value)` pairs.
2. `dotProduct` iterates over the smaller of the two non-zero sets.
3. For each entry look the index up in the other vector and multiply when present.
4. The sum of those products is the dot product; zero entries never contribute.

## Example walkthrough

- For `[1, 0, 0, 2, 3]` and `[0, 3, 0, 4, 0]` the only shared non-zero index is 3, giving `2 * 4`.
- Result: `8`.

## Complexity Analysis

- **Time Complexity:** `O(min(a, b))` — `a` and `b` are the numbers of non-zero entries.
- **Space Complexity:** `O(non-zeros)` — Only non-zero entries are stored.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Dense Arrays

Keep the full arrays and multiply every position. Simple, but wastes memory and time on zeros.

```java
public static int dense(int[] a, int[] b) { int s = 0; for (int i = 0; i < a.length; i++) s += a[i] * b[i]; return s; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Sorted Index and Value Pairs

Keep two parallel arrays of indexes and values and merge them with two pointers.

```java
public static int pairs(int[] a, int[] b) {
    java.util.List<int[]> x = new java.util.ArrayList<>(), y = new java.util.ArrayList<>();
    for (int i = 0; i < a.length; i++) { if (a[i] != 0) x.add(new int[]{i, a[i]}); if (b[i] != 0) y.add(new int[]{i, b[i]}); }
    int i = 0, j = 0, s = 0; while (i < x.size() && j < y.size()) { if (x.get(i)[0] == y.get(j)[0]) s += x.get(i++)[1] * y.get(j++)[1]; else if (x.get(i)[0] < y.get(j)[0]) i++; else j++; }
    return s;
}
```

- **Time Complexity:** O(a + b)
- **Space Complexity:** O(a + b)

### Approach 3: HashMap of Non-Zero Entries (Optimized)

Store the non-zero entries and iterate over the smaller map. This is the version implemented in the repository.

```java
public static int hashMap(int[] a, int[] b) {
    java.util.Map<Integer, Integer> x = new java.util.HashMap<>(), y = new java.util.HashMap<>();
    for (int i = 0; i < a.length; i++) { if (a[i] != 0) x.put(i, a[i]); if (b[i] != 0) y.put(i, b[i]); }
    java.util.Map<Integer, Integer> s = x.size() <= y.size() ? x : y, l = s == x ? y : x; int sum = 0;
    for (java.util.Map.Entry<Integer, Integer> e : s.entrySet()) { Integer v = l.get(e.getKey()); if (v != null) sum += e.getValue() * v; }
    return sum;
}
```

- **Time Complexity:** O(min(a, b))
- **Space Complexity:** O(a + b)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Dense Arrays | O(n) | O(n) |
| 2 | Sorted Index and Value Pairs | O(a + b) | O(a + b) |
| 3 | HashMap of Non-Zero Entries (Optimized) | O(min(a, b)) | O(a + b) |

## Source Code

### DotProductOfTwoSparseVectors.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DotProductOfTwoSparseVectors {

    public static void main(String[] args) {
        int[][][] pairs = {{{1, 0, 0, 2, 3}, {0, 3, 0, 4, 0}}, {{0, 1, 0, 0, 0}, {0, 0, 0, 0, 2}}, {{0, 1, 0, 0, 2, 0, 0}, {1, 0, 0, 0, 3, 0, 4}}};
        for (int[][] pair : pairs) {
            System.out.println("Input  : nums1 = " + Arrays.toString(pair[0]) + ", nums2 = " + Arrays.toString(pair[1]));
            System.out.println("Output : " + new DotProductOfTwoSparseVectors(pair[0]).dotProduct(new DotProductOfTwoSparseVectors(pair[1])));
            System.out.println();
        }
    }

    private final Map<Integer, Integer> entries = new HashMap<>();

    public DotProductOfTwoSparseVectors(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                entries.put(i, nums[i]);
            }
        }
    }

    public int dotProduct(DotProductOfTwoSparseVectors other) {
        Map<Integer, Integer> small = entries.size() <= other.entries.size() ? entries : other.entries;
        Map<Integer, Integer> large = small == entries ? other.entries : entries;
        int sum = 0;
        for (Map.Entry<Integer, Integer> e : small.entrySet()) {
            Integer value = large.get(e.getKey());
            if (value != null) {
                sum += e.getValue() * value;
            }
        }
        return sum;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: a product with a zero is zero, so only positions that are non-zero in both vectors matter. Storing just the non-zeros and probing the larger set with the entries of the smaller one touches nothing else.

**Input:** `[1, 0, 0, 2, 3]` and `[0, 3, 0, 4, 0]`.

| Vector | Stored entries (index: value) |
|--------|-------------------------------|
| first | 0: 1, 3: 2, 4: 3 |
| second | 1: 3, 3: 4 |

Iterating over the second (smaller) vector: index 1 is absent in the first, index 3 gives `2 * 4 = 8`.

**Output:** `8`.
