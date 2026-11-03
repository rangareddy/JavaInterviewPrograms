# Find Median from Data Stream

Design a data structure that supports `addNum(int)` to add a number from a data stream and `findMedian()` to return the median of all numbers added so far.

## Algorithm Strategy (Two Heaps)

1. Keep the smaller half of the numbers in a max-heap `low` and the larger half in a min-heap `high`.
2. To add a number, push it into `low`, move the largest element of `low` to `high`, and if `high` became larger than `low`, move its smallest element back. This keeps `low.size() == high.size()` or `low.size() == high.size() + 1`.
3. The median is the top of `low` when the total count is odd, otherwise the average of both tops.
4. Every insert costs `O(log n)` and every median query `O(1)`.

## Example walkthrough

- Add 1, 2: low = {1}, high = {2}, median (1 + 2) / 2 = 1.5.
- Add 3: low = {1, 2}, high = {3}, median = 2.0.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — per `addNum`; `findMedian` is `O(1)`.
- **Space Complexity:** `O(n)` — All numbers are stored in the two heaps.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort on Every Query

Store the numbers in a list and sort a copy whenever the median is requested. `addNum` is trivial but every `findMedian` is expensive.

```java
static class MedianSortEveryTime {
    private final List<Integer> nums = new ArrayList<>();

    public void addNum(int num) { nums.add(num); }

    public double findMedian() {
        List<Integer> sorted = new ArrayList<>(nums);
        Collections.sort(sorted);
        int n = sorted.size();
        return n % 2 == 1 ? sorted.get(n / 2) : (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2.0;
    }
}
```

- **Time Complexity:** addNum O(1), findMedian O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Keep a Sorted List (Binary Insertion)

Find the insertion position with binary search and insert the number there, so the list is always sorted and the median is read from the middle. The insertion shifts elements, so it is `O(n)`.

```java
static class MedianSortedList {
    private final List<Integer> nums = new ArrayList<>();

    public void addNum(int num) {
        int index = Collections.binarySearch(nums, num);
        nums.add(index < 0 ? -index - 1 : index, num);
    }

    public double findMedian() {
        int n = nums.size();
        return n % 2 == 1 ? nums.get(n / 2) : (nums.get(n / 2 - 1) + nums.get(n / 2)) / 2.0;
    }
}
```

- **Time Complexity:** addNum O(n), findMedian O(1)
- **Space Complexity:** O(n)

### Approach 3: Sorted Array with System.arraycopy

Keep a plain `int[]` that grows when full. A new number is inserted at its binary-searched position by shifting the larger numbers with `System.arraycopy`, so the median is just the middle element. No boxing and no list overhead.

```java
static class MedianArray {
    private int[] data = new int[16];
    private int size;

    public void addNum(int num) {
        if (size == data.length) data = Arrays.copyOf(data, size * 2);
        int position = Arrays.binarySearch(data, 0, size, num);
        if (position < 0) position = -position - 1;
        System.arraycopy(data, position, data, position + 1, size - position);
        data[position] = num;
        size++;
    }

    public double findMedian() {
        return size % 2 == 1 ? data[size / 2] : (data[size / 2 - 1] + data[size / 2]) / 2.0;
    }
}
```

- **Time Complexity:** addNum O(n) (fast memmove), findMedian O(1)
- **Space Complexity:** O(n)

### Approach 4: Counting Buckets for a Bounded Range

If the numbers are limited to a small range (for example `0..100` like test scores), count how often each value occurs and find the median by walking the cumulative counts. Insertion is `O(1)`, a query costs `O(range)`, independent of how many numbers arrived.

```java
static class MedianBuckets {
    private final int offset;
    private final int[] counts;
    private int total;

    MedianBuckets(int min, int max) { offset = -min; counts = new int[max - min + 1]; }

    public void addNum(int num) { counts[num + offset]++; total++; }

    public double findMedian() {
        int low = kth((total + 1) / 2), high = kth(total / 2 + 1);
        return (low + high) / 2.0;
    }

    private int kth(int k) {                       // k-th smallest, 1-based
        int seen = 0;
        for (int v = 0; v < counts.length; v++) {
            seen += counts[v];
            if (seen >= k) return v - offset;
        }
        throw new IllegalStateException();
    }
}
```

- **Time Complexity:** addNum O(1), findMedian O(R)
- **Space Complexity:** O(R)

### Approach 5: Quickselect on Every Query

Store the numbers unsorted and compute the median on demand with quickselect, which finds the k-th smallest element in expected linear time without sorting everything.

```java
static class MedianQuickselect {
    private final List<Integer> nums = new ArrayList<>();
    private final Random random = new Random(1);

    public void addNum(int num) { nums.add(num); }

    public double findMedian() {
        int n = nums.size();
        int[] copy = new int[n];
        for (int i = 0; i < n; i++) copy[i] = nums.get(i);
        if (n % 2 == 1) return select(copy, n / 2);
        return (select(copy, n / 2 - 1) + select(copy, n / 2)) / 2.0;
    }

    private int select(int[] a, int k) {            // k-th smallest, 0-based
        int low = 0, high = a.length - 1;
        while (low < high) {
            int pivot = a[low + random.nextInt(high - low + 1)];
            int i = low, j = high;
            while (i <= j) {
                while (a[i] < pivot) i++;
                while (a[j] > pivot) j--;
                if (i <= j) { int t = a[i]; a[i] = a[j]; a[j] = t; i++; j--; }
            }
            if (k <= j) high = j; else if (k >= i) low = i; else return a[k];
        }
        return a[k];
    }
}
```

- **Time Complexity:** addNum O(1), findMedian O(n) expected
- **Space Complexity:** O(n)

### Approach 6: Fenwick Tree over a Bounded Range

Keep the counts of the values in a Fenwick (binary indexed) tree. Both an insertion and the search for the k-th smallest value (by walking down the tree) cost `O(log R)`, so queries stay fast even for a huge number of numbers.

```java
static class MedianFenwick {
    private final int offset;
    private final int[] tree;
    private int total;
    private final int highestPower;

    MedianFenwick(int min, int max) {
        offset = 1 - min;
        tree = new int[max - min + 2];
        highestPower = Integer.highestOneBit(tree.length - 1);
    }

    public void addNum(int num) {
        for (int i = num + offset; i < tree.length; i += i & -i) tree[i]++;
        total++;
    }

    public double findMedian() {
        return (kth((total + 1) / 2) + kth(total / 2 + 1)) / 2.0;
    }

    private int kth(int k) {                       // k-th smallest, 1-based
        int position = 0;
        for (int step = highestPower; step > 0; step >>= 1) {
            if (position + step < tree.length && tree[position + step] < k) {
                position += step;
                k -= tree[position];
            }
        }
        return position + 1 - offset;
    }
}
```

- **Time Complexity:** addNum O(log R), findMedian O(log R)
- **Space Complexity:** O(R)

### Approach 7: Two Heaps (Optimized)

A max-heap holds the smaller half and a min-heap the larger half, kept balanced; the median is read from the heap tops. This is the version implemented in the repository.

```java
static class MedianTwoHeaps {
    private final PriorityQueue<Integer> low = new PriorityQueue<>(Collections.reverseOrder());
    private final PriorityQueue<Integer> high = new PriorityQueue<>();

    public void addNum(int num) {
        low.add(num);
        high.add(low.poll());
        if (high.size() > low.size()) low.add(high.poll());
    }

    public double findMedian() {
        return low.size() > high.size() ? low.peek() : (low.peek() + high.peek()) / 2.0;
    }
}
```

- **Time Complexity:** addNum O(log n), findMedian O(1)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort on Every Query | addNum O(1), findMedian O(n log n) | O(n) |
| 2 | Keep a Sorted List (Binary Insertion) | addNum O(n), findMedian O(1) | O(n) |
| 3 | Sorted Array with System.arraycopy | addNum O(n) (fast memmove), findMedian O(1) | O(n) |
| 4 | Counting Buckets for a Bounded Range | addNum O(1), findMedian O(R) | O(R) |
| 5 | Quickselect on Every Query | addNum O(1), findMedian O(n) expected | O(n) |
| 6 | Fenwick Tree over a Bounded Range | addNum O(log R), findMedian O(log R) | O(R) |
| 7 | Two Heaps (Optimized) | addNum O(log n), findMedian O(1) | O(n) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### FindMedianFromDataStream.java

```java
import java.util.Collections;
import java.util.PriorityQueue;

public class FindMedianFromDataStream {

    private final PriorityQueue<Integer> low = new PriorityQueue<>(Collections.reverseOrder());   // max-heap, smaller half
    private final PriorityQueue<Integer> high = new PriorityQueue<>();                             // min-heap, larger half

    public static void main(String[] args) {
        FindMedianFromDataStream medianFinder = new FindMedianFromDataStream();
        int[] stream = {1, 2, 3, 10, -5};
        for (int num : stream) {
            medianFinder.addNum(num);
            System.out.println("Input  : addNum(" + num + ")");
            System.out.println("Output : findMedian() = " + medianFinder.findMedian());
            System.out.println();
        }
    }

    public void addNum(int num) {
        low.add(num);
        high.add(low.poll());           // the largest of the small half moves to the large half
        if (high.size() > low.size()) {
            low.add(high.poll());       // rebalance so that low is never smaller than high
        }
    }

    public double findMedian() {
        if (low.isEmpty()) {
            throw new IllegalStateException("No numbers have been added.");
        }
        if (low.size() > high.size()) {
            return low.peek();
        }
        return (low.peek() + high.peek()) / 2.0;
    }
}
```
