# Longest Consecutive Sequence

Given an unsorted array of integers, return the length of the longest sequence of consecutive values (such as 1, 2, 3, 4). The algorithm must run in `O(n)` time.

## Algorithm Strategy (Hash Set and Sequence Starts)

1. Put all numbers into a `HashSet` for `O(1)` membership tests.
2. A number is the start of a sequence only if `num - 1` is not in the set. Ignore all other numbers: they will be visited from their start.
3. From each start, count how long `num + 1`, `num + 2`, ... keep being in the set.
4. Return the longest length found. Every number is part of exactly one sequence and is walked over once, so the total work is linear.

## Example walkthrough

- For `[100, 4, 200, 1, 3, 2]`: the starts are 100, 200 and 1 (because 0 is missing).
- From 1 the sequence 1, 2, 3, 4 has length 4; from 100 and 200 the length is 1. Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each number is counted only from the start of its sequence.
- **Space Complexity:** `O(n)` — The hash set.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sorting

Sort the numbers and count the length of each run of consecutive values (equal values are skipped).

```java
public static int longestConsecutiveSort(int[] nums) {
    if (nums.length == 0) return 0;
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    int best = 1, current = 1;
    for (int i = 1; i < sorted.length; i++) {
        if (sorted[i] == sorted[i - 1]) continue;
        current = (sorted[i] == sorted[i - 1] + 1) ? current + 1 : 1;
        best = Math.max(best, current);
    }
    return best;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Map of Sequence Lengths at the Boundaries

Store for the two ends of every sequence its length. A new number joins the sequences on its left and right: its length is `left + right + 1`, and that value is written to the two new ends. Needs a single pass and no repeated walking.

```java
public static int longestConsecutiveMap(int[] nums) {
    Map<Integer, Integer> length = new HashMap<>();
    int best = 0;
    for (int num : nums) {
        if (length.containsKey(num)) continue;
        int left = length.getOrDefault(num - 1, 0), right = length.getOrDefault(num + 1, 0);
        int total = left + right + 1;
        length.put(num, total);
        length.put(num - left, total);
        length.put(num + right, total);
        best = Math.max(best, total);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Brute Force (Linear Search for Each Next Number)

From every number keep asking "is the next number in the array?" with a linear scan until the answer is no. Easy to write, cubic in the worst case.

```java
public static int longestConsecutiveBrute(int[] nums) {
    int best = 0;
    for (int start : nums) {
        int current = start, length = 1;
        while (contains(nums, current + 1)) { current++; length++; }
        best = Math.max(best, length);
    }
    return nums.length == 0 ? 0 : best;
}

private static boolean contains(int[] nums, int value) {
    for (int num : nums) if (num == value) return true;
    return false;
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(1)

### Approach 4: Union-Find over the Values

Give every distinct value a set and union each value with its successor when that exists. The size of the largest set is the answer.

```java
public static int longestConsecutiveUnionFind(int[] nums) {
    Map<Integer, Integer> index = new HashMap<>();
    for (int num : nums) index.putIfAbsent(num, index.size());
    int[] parent = new int[index.size()], size = new int[index.size()];
    for (int i = 0; i < parent.length; i++) { parent[i] = i; size[i] = 1; }
    int best = 0;
    for (int num : index.keySet()) {
        Integer next = index.get(num + 1);
        if (next != null) {
            int a = find(parent, index.get(num)), b = find(parent, next);
            if (a != b) { parent[a] = b; size[b] += size[a]; }
        }
    }
    for (int i = 0; i < parent.length; i++) best = Math.max(best, size[find(parent, i)]);
    return best;
}

private static int find(int[] parent, int x) {
    while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
    return x;
}
```

- **Time Complexity:** O(n * alpha(n))
- **Space Complexity:** O(n)

### Approach 5: TreeSet Iteration

A `TreeSet` keeps the distinct values sorted, so one pass over it counts the runs of consecutive values.

```java
public static int longestConsecutiveTreeSet(int[] nums) {
    TreeSet<Integer> set = new TreeSet<>();
    for (int num : nums) set.add(num);
    int best = 0, length = 0;
    Integer previous = null;
    for (int value : set) {
        length = (previous != null && value == previous + 1) ? length + 1 : 1;
        best = Math.max(best, length);
        previous = value;
    }
    return best;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 6: BitSet over a Bounded Range

When the values lie in a limited range, mark them in a `BitSet` (shifted by the minimum) and measure the runs of set bits with `nextClearBit`. Very compact for dense data.

```java
public static int longestConsecutiveBitSet(int[] nums) {
    if (nums.length == 0) return 0;
    int min = Integer.MAX_VALUE;
    for (int num : nums) min = Math.min(min, num);
    BitSet bits = new BitSet();
    for (int num : nums) bits.set(num - min);
    int best = 0;
    for (int i = bits.nextSetBit(0); i >= 0; ) {
        int end = bits.nextClearBit(i);
        best = Math.max(best, end - i);
        i = bits.nextSetBit(end);
    }
    return best;
}
```

- **Time Complexity:** O(n + R) where R is the value range
- **Space Complexity:** O(R / 8) bytes

### Approach 7: Hash Set and Sequence Starts (Optimized)

Only start counting at numbers whose predecessor is absent, so every number is walked over once in total. This is the version implemented in the repository.

```java
public static int longestConsecutive(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int num : nums) set.add(num);
    int best = 0;
    for (int num : set) {
        if (!set.contains(num - 1)) {
            int length = 1;
            while (set.contains(num + length)) length++;
            best = Math.max(best, length);
        }
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sorting | O(n log n) | O(n) |
| 2 | Map of Sequence Lengths at the Boundaries | O(n) | O(n) |
| 3 | Brute Force (Linear Search for Each Next Number) | O(n^3) | O(1) |
| 4 | Union-Find over the Values | O(n * alpha(n)) | O(n) |
| 5 | TreeSet Iteration | O(n log n) | O(n) |
| 6 | BitSet over a Bounded Range | O(n + R) where R is the value range | O(R / 8) bytes |
| 7 | Hash Set and Sequence Starts (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### LongestConsecutiveSequence.java

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence {

    public static void main(String[] args) {
        int[][] inputs = {{100, 4, 200, 1, 3, 2}, {0, 3, 7, 2, 5, 8, 4, 6, 0, 1}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + longestConsecutive(nums));
            System.out.println();
        }
    }

    public static int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;
        for (int num : set) {
            if (!set.contains(num - 1)) {           // num is the start of a sequence
                int current = num;
                int length = 1;
                while (set.contains(current + 1)) {
                    current++;
                    length++;
                }
                longest = Math.max(longest, length);
            }
        }
        return longest;
    }
}
```
