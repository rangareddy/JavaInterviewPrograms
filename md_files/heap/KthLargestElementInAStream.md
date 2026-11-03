# Kth Largest Element in a Stream

Design a class that finds the `k`-th largest element in a stream of numbers (the k-th largest in sorted order, not the k-th distinct one). `KthLargest(k, nums)` initialises it with an initial array and `add(val)` appends a number and returns the current k-th largest element.

## Algorithm Strategy (Min-Heap of the k Largest Values)

1. Keep a min-heap that holds only the `k` largest values seen so far. The smallest of them (the top of the heap) is exactly the `k`-th largest.
2. Initialise by adding every number of `nums` with the same rule as `add`.
3. `add(val)`: push the value; if the heap now holds more than `k` values, remove the top (the smallest), which can no longer be the k-th largest.
4. Return the top of the heap.

## Example walkthrough

- For `k = 3` and `nums = [4, 5, 8, 2]` the heap holds `[4, 5, 8]`. `add(3)` changes nothing (3 is dropped): the answer is 4.
- `add(5)` gives heap `[5, 5, 8]`: 5. `add(10)`: `[5, 8, 10]`: 5. `add(9)`: `[8, 9, 10]`: 8. `add(4)`: 8.

## Complexity Analysis

- **Time Complexity:** `O(log k)` — per `add`.
- **Space Complexity:** `O(k)` — The heap never holds more than `k + 1` numbers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort on Every add

Keep all numbers in a list, sort it on every `add` and read the element `k` places from the end. Trivial, but `add` costs `O(n log n)` and the memory grows with the stream.

```java
static class SortingKth {
    private final int k;
    private final List<Integer> numbers = new ArrayList<>();

    SortingKth(int k, int[] nums) { this.k = k; for (int num : nums) numbers.add(num); }

    public int add(int val) {
        numbers.add(val);
        Collections.sort(numbers);
        return numbers.get(numbers.size() - k);
    }
}
```

- **Time Complexity:** O(n log n) per add
- **Space Complexity:** O(n)

### Approach 2: Sorted List with Binary Insertion

Keep the numbers sorted: find the insertion position with binary search and insert there (an `O(n)` shift). The k-th largest is then read in `O(1)`.

```java
static class SortedListKth {
    private final int k;
    private final List<Integer> numbers = new ArrayList<>();

    SortedListKth(int k, int[] nums) { this.k = k; for (int num : nums) insert(num); }

    private void insert(int val) {
        int position = Collections.binarySearch(numbers, val);
        numbers.add(position < 0 ? -position - 1 : position, val);
    }

    public int add(int val) {
        insert(val);
        return numbers.get(numbers.size() - k);
    }
}
```

- **Time Complexity:** O(n) per add
- **Space Complexity:** O(n)

### Approach 3: TreeMap of Counts Limited to k Values

A `TreeMap<value, count>` acts as a sorted multiset that only keeps the `k` largest numbers; when it holds more than `k`, one copy of the smallest value is removed. The first key is the k-th largest.

```java
static class TreeMapKth {
    private final int k;
    private final TreeMap<Integer, Integer> counts = new TreeMap<>();
    private int size;

    TreeMapKth(int k, int[] nums) { this.k = k; for (int num : nums) add(num); }

    public int add(int val) {
        counts.merge(val, 1, Integer::sum);
        size++;
        if (size > k) {
            int smallest = counts.firstKey();
            if (counts.merge(smallest, -1, Integer::sum) == 0) counts.remove(smallest);
            size--;
        }
        return counts.firstKey();
    }
}
```

- **Time Complexity:** O(log k) per add
- **Space Complexity:** O(k)

### Approach 4: Min-Heap of the k Largest (Optimized)

A min-heap that never holds more than `k` numbers: its top is the k-th largest. This is the version implemented in the repository.

```java
static class HeapKth {
    private final int k;
    private final PriorityQueue<Integer> heap = new PriorityQueue<>();

    HeapKth(int k, int[] nums) { this.k = k; for (int num : nums) add(num); }

    public int add(int val) {
        heap.add(val);
        if (heap.size() > k) heap.poll();
        return heap.peek();
    }
}
```

- **Time Complexity:** O(log k) per add
- **Space Complexity:** O(k)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort on Every add | O(n log n) per add | O(n) |
| 2 | Sorted List with Binary Insertion | O(n) per add | O(n) |
| 3 | TreeMap of Counts Limited to k Values | O(log k) per add | O(k) |
| 4 | Min-Heap of the k Largest (Optimized) | O(log k) per add | O(k) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### KthLargestElementInAStream.java

```java
import java.util.PriorityQueue;

public class KthLargestElementInAStream {

    private final int k;
    private final PriorityQueue<Integer> heap = new PriorityQueue<>();      // min-heap with the k largest values

    public KthLargestElementInAStream(int k, int[] nums) {
        this.k = k;
        for (int num : nums) {
            add(num);
        }
    }

    public static void main(String[] args) {
        KthLargestElementInAStream kthLargest = new KthLargestElementInAStream(3, new int[]{4, 5, 8, 2});
        System.out.println("Input  : KthLargest(3, [4, 5, 8, 2])");
        for (int val : new int[]{3, 5, 10, 9, 4}) {
            System.out.println("Input  : add(" + val + ")");
            System.out.println("Output : " + kthLargest.add(val));
        }
    }

    public int add(int val) {
        heap.add(val);
        if (heap.size() > k) {
            heap.poll();
        }
        return heap.peek();
    }
}
```
