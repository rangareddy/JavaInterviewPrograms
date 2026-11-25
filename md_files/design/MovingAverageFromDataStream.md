# Moving Average from Data Stream

Given a stream of integers and a window size, calculate the moving average of the last `size` values. Implement `MovingAverage(size)` and `next(val)`, which adds `val` to the stream and returns the average of the last `size` values (or of all values if fewer have arrived).

## Algorithm Strategy (Queue plus Running Sum)

1. Keep a queue of the values in the window and a running `sum`.
2. On `next(val)`, add `val` to the queue and to the sum.
3. If the queue now holds more than `size` values, remove the oldest one and subtract it from the sum.
4. Return `sum / queue.size()` as a double.

## Example walkthrough

- With `size = 3` and values 1, 10, 3, 5: the averages are 1.0, 5.5, 4.666..., then 6.0 (the window slides to 10, 3, 5).
- The first output is `1.0`.

## Complexity Analysis

- **Time Complexity:** `O(1) per call` — One enqueue, at most one dequeue.
- **Space Complexity:** `O(size)` — The window.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recompute the Sum Each Time

Keep all values and sum the last `size` on each call.

```java
public static class Recompute {
    private final java.util.List<Integer> all = new java.util.ArrayList<>(); private final int size; public Recompute(int size) { this.size = size; }
    public double next(int v) { all.add(v); long s = 0; int from = Math.max(0, all.size() - size); for (int i = from; i < all.size(); i++) s += all.get(i); return (double) s / (all.size() - from); }
}
```

- **Time Complexity:** O(size) per call
- **Space Complexity:** O(n)

### Approach 2: Queue plus Running Sum (Optimized)

Maintain a sliding window and its sum. This is the version implemented in the repository.

```java
public static class Running {
    private final java.util.Queue<Integer> q = new java.util.ArrayDeque<>(); private final int size; private long sum; public Running(int size) { this.size = size; }
    public double next(int v) { q.add(v); sum += v; if (q.size() > size) sum -= q.poll(); return (double) sum / q.size(); }
}
```

- **Time Complexity:** O(1) per call
- **Space Complexity:** O(size)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recompute the Sum Each Time | O(size) per call | O(n) |
| 2 | Queue plus Running Sum (Optimized) | O(1) per call | O(size) |

## Source Code

### MovingAverageFromDataStream.java

```java
import java.util.ArrayDeque;
import java.util.Queue;

public class MovingAverageFromDataStream {

    public static void main(String[] args) {
        MovingAverageFromDataStream average = new MovingAverageFromDataStream(3);
        int[] values = {1, 10, 3, 5};
        for (int value : values) {
            System.out.println("Input  : next(" + value + ")");
            System.out.println("Output : " + average.next(value));
            System.out.println();
        }
    }

    private final int size;
    private final Queue<Integer> window = new ArrayDeque<>();
    private long sum;

    public MovingAverageFromDataStream(int size) {
        this.size = Math.max(1, size);
    }

    public double next(int val) {
        window.add(val);
        sum += val;
        if (window.size() > size) {
            sum -= window.poll();
        }
        return (double) sum / window.size();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: when the window slides, only one value enters and one leaves, so the sum can be updated with two operations instead of being recomputed from scratch. A queue gives the oldest value in constant time.

**Sequence (`size = 3`):**

| Call | Window | Sum | Average |
|------|--------|-----|---------|
| `next(1)` | [1] | 1 | 1.0 |
| `next(10)` | [1, 10] | 11 | 5.5 |
| `next(3)` | [1, 10, 3] | 14 | 4.666... |
| `next(5)` | [10, 3, 5] | 18 | 6.0 |

**First output in `main`:** `1.0`.
