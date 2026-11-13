# Design Hit Counter

Design a `HitCounter` that records hits and returns the number of hits in the past 300 seconds (so for a query at time `t`, the hits with `t - 300 < timestamp <= t`). Calls to `hit` and `getHits` arrive in non-decreasing timestamp order, in seconds.

## Algorithm Strategy (Queue of Timestamps with Lazy Expiry)

1. Because timestamps never decrease, the hits form a sorted sequence. Keep them in a queue, oldest at the head.
2. `hit(t)` appends `t` at the tail.
3. `getHits(t)` first removes from the head every timestamp that is 300 seconds or more older than `t` (they can never count again, since later queries have larger timestamps), then returns the size of the queue.
4. Each timestamp is added once and removed once, so the cost is amortized constant. The memory is proportional to the number of hits in the last 300 seconds.

## Example walkthrough

- Hits at 1, 2, 3 and `getHits(4)`: all three are within 300 seconds, so the answer is 3.
- A hit at 300 and `getHits(300)` gives 4. At `getHits(301)` the hit at 1 is exactly 300 seconds old and expires, leaving 3.

## Complexity Analysis

- **Time Complexity:** `O(1) amortized` — Each hit is enqueued and dequeued once.
- **Space Complexity:** `O(h)` — `h` is the number of hits in the last 300 seconds.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Circular Buffer of 300 Buckets

Use two arrays of 300 slots indexed by `timestamp % 300`: the timestamp stored in the slot and the number of hits at it. A slot whose stored timestamp is too old is treated as empty. Memory is constant even for millions of hits per second.

```java
class BucketHitCounter {
    private final int[] times = new int[300];
    private final int[] counts = new int[300];

    void hit(int timestamp) {
        int index = timestamp % 300;
        if (times[index] != timestamp) {
            times[index] = timestamp;
            counts[index] = 1;
        } else {
            counts[index]++;
        }
    }

    int getHits(int timestamp) {
        int total = 0;
        for (int i = 0; i < 300; i++) {
            if (timestamp - times[i] < 300 && counts[i] > 0) total += counts[i];
        }
        return total;
    }
}
```

- **Time Complexity:** hit O(1), getHits O(300)
- **Space Complexity:** O(300)

### Approach 2: Store Every Hit and Scan

Keep all timestamps in a list and count those in the window on every query.

```java
class ListHitCounter {
    private final List<Integer> hits = new ArrayList<>();

    void hit(int timestamp) { hits.add(timestamp); }

    int getHits(int timestamp) {
        int count = 0;
        for (int t : hits) if (t > timestamp - 300 && t <= timestamp) count++;
        return count;
    }
}
```

- **Time Complexity:** hit O(1), getHits O(n)
- **Space Complexity:** O(n)

### Approach 3: Queue with Lazy Expiry (Optimized)

Drop the expired timestamps from the head when a query arrives and report the queue size. This is the version implemented in the repository.

```java
private static final int WINDOW = 300;
private final Deque<Integer> hits = new ArrayDeque<>();

public void hit(int timestamp) {
    hits.addLast(timestamp);
}

public int getHits(int timestamp) {
    while (!hits.isEmpty() && hits.peekFirst() <= timestamp - WINDOW) {
        hits.removeFirst();
    }
    return hits.size();
}
```

- **Time Complexity:** O(1) amortized
- **Space Complexity:** O(hits in the window)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Circular Buffer of 300 Buckets | hit O(1), getHits O(300) | O(300) |
| 2 | Store Every Hit and Scan | hit O(1), getHits O(n) | O(n) |
| 3 | Queue with Lazy Expiry (Optimized) | O(1) amortized | O(hits in the window) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### DesignHitCounter.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class DesignHitCounter {

    public static void main(String[] args) {
        DesignHitCounter counter = new DesignHitCounter();
        System.out.println("Input  : hit(1), hit(2), hit(3), getHits(4), hit(300), getHits(300), getHits(301)");
        counter.hit(1);
        counter.hit(2);
        counter.hit(3);
        int a = counter.getHits(4);
        counter.hit(300);
        int b = counter.getHits(300);
        int c = counter.getHits(301);
        System.out.println("Output : " + a + ", " + b + ", " + c);
    }

    private static final int WINDOW = 300;
    private final Deque<Integer> hits = new ArrayDeque<>();

    public void hit(int timestamp) {
        hits.addLast(timestamp);
    }

    public int getHits(int timestamp) {
        while (!hits.isEmpty() && hits.peekFirst() <= timestamp - WINDOW) {
            hits.removeFirst();
        }
        return hits.size();
    }
}
```
