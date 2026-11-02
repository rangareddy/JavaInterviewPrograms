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
