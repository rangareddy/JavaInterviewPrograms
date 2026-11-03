# K Closest Points to Origin

Given an array of points `[x, y]` on the plane and an integer `k`, return the `k` points closest to the origin (0, 0) by Euclidean distance. The answer may be returned in any order and is guaranteed to be unique apart from its order.

## Algorithm Strategy (Max-Heap of Size k)

1. Compare squared distances `x^2 + y^2`: the square root is not needed because it preserves the order.
2. Keep a max-heap (ordered by squared distance) that holds the `k` closest points seen so far; its top is the farthest of them.
3. For every point push it; if the heap grows beyond `k`, remove the top (the farthest), which cannot be among the `k` closest.
4. The points left in the heap are the answer.

## Example walkthrough

- For `[[3,3],[5,-1],[-2,4]]` and `k = 2` the squared distances are 18, 26 and 20.
- The point with distance 26 is evicted: `[[3,3],[-2,4]]`.

## Complexity Analysis

- **Time Complexity:** `O(n log k)` — Every point causes at most one push and one removal on a heap of size `k`.
- **Space Complexity:** `O(k)` — The heap.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort All Points by Distance

Sort the points by squared distance and take the first `k`.

```java
public static int[][] kClosestSort(int[][] points, int k) {
    int[][] sorted = points.clone();
    Arrays.sort(sorted, Comparator.comparingLong(p -> (long) p[0] * p[0] + (long) p[1] * p[1]));
    return Arrays.copyOf(sorted, k);
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Min-Heap of All Points

Put every point into a min-heap ordered by distance and remove the top `k` times. Building the heap is linear and each removal logarithmic.

```java
public static int[][] kClosestMinHeap(int[][] points, int k) {
    PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingLong(p -> (long) p[0] * p[0] + (long) p[1] * p[1]));
    heap.addAll(Arrays.asList(points));
    int[][] result = new int[k][];
    for (int i = 0; i < k; i++) result[i] = heap.poll();
    return result;
}
```

- **Time Complexity:** O(n + k log n)
- **Space Complexity:** O(n)

### Approach 3: Quickselect on the Distances

Partition the points around a random pivot distance until the `k` closest occupy the first `k` positions; no full sort is needed. Expected linear time.

```java
public static int[][] kClosestQuickselect(int[][] points, int k) {
    int[][] a = points.clone();
    int low = 0, high = a.length - 1;
    Random random = new Random(1);
    while (low < high) {
        int pivotIndex = low + random.nextInt(high - low + 1);
        long pivot = distance(a[pivotIndex]);
        swap(a, pivotIndex, high);
        int store = low;
        for (int i = low; i < high; i++) if (distance(a[i]) < pivot) swap(a, i, store++);
        swap(a, store, high);
        if (store == k) break;
        if (store < k) low = store + 1; else high = store - 1;
    }
    return Arrays.copyOf(a, k);
}

private static long distance(int[] p) { return (long) p[0] * p[0] + (long) p[1] * p[1]; }
private static void swap(int[][] a, int i, int j) { int[] t = a[i]; a[i] = a[j]; a[j] = t; }
```

- **Time Complexity:** O(n) expected
- **Space Complexity:** O(n)

### Approach 4: Max-Heap of Size k (Optimized)

Keep the `k` closest points in a max-heap so the farthest of them is on top and can be evicted cheaply. This is the version implemented in the repository.

```java
public static int[][] kClosest(int[][] points, int k) {
    PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Long.compare((long) b[0] * b[0] + (long) b[1] * b[1], (long) a[0] * a[0] + (long) a[1] * a[1]));
    for (int[] point : points) {
        heap.add(point);
        if (heap.size() > k) heap.poll();
    }
    int[][] result = new int[heap.size()][];
    for (int i = 0; i < result.length; i++) result[i] = heap.poll();
    return result;
}
```

- **Time Complexity:** O(n log k)
- **Space Complexity:** O(k)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort All Points by Distance | O(n log n) | O(n) |
| 2 | Min-Heap of All Points | O(n + k log n) | O(n) |
| 3 | Quickselect on the Distances | O(n) expected | O(n) |
| 4 | Max-Heap of Size k (Optimized) | O(n log k) | O(k) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### KClosestPointsToOrigin.java

```java
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class KClosestPointsToOrigin {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 3}, {-2, 2}}, {{3, 3}, {5, -1}, {-2, 4}}, {{0, 1}, {1, 0}}, {{1, 1}}};
        int[] ks = {1, 2, 2, 1};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : points = " + Arrays.deepToString(inputs[i]) + ", k = " + ks[i]);
            int[][] closest = kClosest(inputs[i], ks[i]);
            Arrays.sort(closest, Comparator.comparingLong(p -> (long) p[0] * p[0] + (long) p[1] * p[1]));
            System.out.println("Output : " + Arrays.deepToString(closest));
            System.out.println();
        }
    }

    public static int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>(
                (a, b) -> Long.compare(squaredDistance(b), squaredDistance(a)));         // farthest point on top
        for (int[] point : points) {
            heap.add(point);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        int[][] result = new int[heap.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = heap.poll();
        }
        return result;
    }

    private static long squaredDistance(int[] point) {
        return (long) point[0] * point[0] + (long) point[1] * point[1];
    }
}
```
