# Minimum Interval to Include Each Query

You are given a 2D array `intervals` where `intervals[i] = [left, right]` is an inclusive interval of size `right - left + 1`, and an array `queries`. For each query `q` return the size of the smallest interval that contains `q` (`left <= q <= right`), or `-1` if none does.

## Algorithm Strategy (Offline Processing: Sort and Min-Heap)

1. Sort the intervals by their left end, and process the queries in increasing order (remember each query's original index).
2. For each query `q`, push into a min-heap (keyed by size) every interval whose left end is `<= q`.
3. Pop heap tops whose right end is `< q`: they end before the query and cannot help any later (larger) query either.
4. The heap top, if present, is the smallest interval containing `q`; otherwise the answer is `-1`.

## Example walkthrough

- For `intervals = [[1,4],[2,4],[3,6],[4,4]]` and `queries = [2,3,4,5]`: sizes 3, 3, 1 and 4.
- Result: `[3, 3, 1, 4]`.

## Complexity Analysis

- **Time Complexity:** `O((n + q) log (n + q))` — Sorting plus heap operations.
- **Space Complexity:** `O(n + q)` — The heap and the sorted query order.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force Scan

For each query scan all intervals and keep the smallest one containing it.

```java
public static int[] minIntervalBrute(int[][] intervals, int[] queries) {
    int[] answer = new int[queries.length];
    for (int i = 0; i < queries.length; i++) {
        int best = -1;
        for (int[] interval : intervals)
            if (interval[0] <= queries[i] && queries[i] <= interval[1]) {
                int size = interval[1] - interval[0] + 1;
                if (best == -1 || size < best) best = size;
            }
        answer[i] = best;
    }
    return answer;
}
```

- **Time Complexity:** O(n * q)
- **Space Complexity:** O(1)

### Approach 2: Sort by Size, Union-Find over Queries

Process intervals from smallest to largest and assign their size to every still-unanswered query inside them; union-find skips answered queries.

```java
public static int[] minIntervalUnionFind(int[][] intervals, int[] queries) {
    int q = queries.length;
    Integer[] order = new Integer[q];
    for (int i = 0; i < q; i++) order[i] = i;
    Arrays.sort(order, (a, b) -> Integer.compare(queries[a], queries[b]));
    int[] parent = new int[q + 1];
    for (int i = 0; i <= q; i++) parent[i] = i;
    int[] answer = new int[q];
    Arrays.fill(answer, -1);
    int[][] bySize = intervals.clone();
    Arrays.sort(bySize, (a, b) -> Integer.compare(a[1] - a[0], b[1] - b[0]));
    for (int[] interval : bySize) {
        int lo = 0, hi = q;
        while (lo < hi) { int mid = (lo + hi) / 2; if (queries[order[mid]] < interval[0]) lo = mid + 1; else hi = mid; }
        int pos = find(parent, lo);
        while (pos < q && queries[order[pos]] <= interval[1]) {
            answer[order[pos]] = interval[1] - interval[0] + 1;
            parent[pos] = pos + 1;
            pos = find(parent, pos + 1);
        }
    }
    return answer;
}

private static int find(int[] parent, int x) {
    while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
    return x;
}
```

- **Time Complexity:** O((n + q) log (n + q))
- **Space Complexity:** O(q)

### Approach 3: Sorted Queries and Min-Heap (Optimized)

Sweep queries in increasing order with a heap of candidate intervals keyed by size. This is the version implemented in the repository.

```java
public static int[] minInterval(int[][] intervals, int[] queries) {
    int[][] sorted = intervals.clone();
    Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));
    Integer[] order = new Integer[queries.length];
    for (int i = 0; i < order.length; i++) order[i] = i;
    Arrays.sort(order, (a, b) -> Integer.compare(queries[a], queries[b]));
    PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
    int[] answer = new int[queries.length];
    int next = 0;
    for (int index : order) {
        int q = queries[index];
        while (next < sorted.length && sorted[next][0] <= q) {
            heap.add(new int[]{sorted[next][1] - sorted[next][0] + 1, sorted[next][1]});
            next++;
        }
        while (!heap.isEmpty() && heap.peek()[1] < q) heap.poll();
        answer[index] = heap.isEmpty() ? -1 : heap.peek()[0];
    }
    return answer;
}
```

- **Time Complexity:** O((n + q) log (n + q))
- **Space Complexity:** O(n + q)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force Scan | O(n * q) | O(1) |
| 2 | Sort by Size, Union-Find over Queries | O((n + q) log (n + q)) | O(q) |
| 3 | Sorted Queries and Min-Heap (Optimized) | O((n + q) log (n + q)) | O(n + q) |

## Source Code

### MinimumIntervalToIncludeEachQuery.java

```java
import java.util.Arrays;
import java.util.PriorityQueue;

public class MinimumIntervalToIncludeEachQuery {

    public static void main(String[] args) {
        int[][][] intervals = {
                {{1, 4}, {2, 4}, {3, 6}, {4, 4}},
                {{2, 3}, {2, 5}, {1, 8}, {20, 25}},
                {{1, 1}}
        };
        int[][] queries = {{2, 3, 4, 5}, {2, 19, 5, 22}, {2}};
        for (int i = 0; i < intervals.length; i++) {
            System.out.println("Input  : intervals = " + Arrays.deepToString(intervals[i]) + ", queries = " + Arrays.toString(queries[i]));
            System.out.println("Output : " + Arrays.toString(minInterval(intervals[i], queries[i])));
            System.out.println();
        }
    }

    public static int[] minInterval(int[][] intervals, int[] queries) {
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));

        Integer[] order = new Integer[queries.length];
        for (int i = 0; i < order.length; i++) {
            order[i] = i;
        }
        Arrays.sort(order, (a, b) -> Integer.compare(queries[a], queries[b]));

        // heap entries are {size, right end}
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        int[] answer = new int[queries.length];
        int next = 0;
        for (int index : order) {
            int q = queries[index];
            while (next < sorted.length && sorted[next][0] <= q) {
                heap.add(new int[]{sorted[next][1] - sorted[next][0] + 1, sorted[next][1]});
                next++;
            }
            while (!heap.isEmpty() && heap.peek()[1] < q) {
                heap.poll();
            }
            answer[index] = heap.isEmpty() ? -1 : heap.peek()[0];
        }
        return answer;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: answer the queries offline, from smallest to largest, while sweeping the intervals sorted by left end. For each query, every interval whose left end is not past the query is pushed into a min-heap keyed by size. Heap tops whose right end is before the query are discarded for good, since all later queries are at least as large. What is left on top is the smallest interval that starts at or before `q` and ends at or after `q`.

**Input:** `intervals = [[1,4],[2,4],[3,6],[4,4]]`, `queries = [2,3,4,5]`. Sorted intervals are the same order. Heap entries are `{size, right}`; the queries are already in increasing order.

| Query `q` | Pushed (left `<= q`) | Heap after pushing (by size) | Popped (right `< q`) | Heap top | `answer` |
|-----------|----------------------|------------------------------|----------------------|----------|----------|
| 2 | `[1,4]`, `[2,4]` | `{3,4}`, `{4,4}` | none | `{3,4}` | 3 |
| 3 | `[3,6]` | `{3,4}`, `{4,4}`, `{4,6}` | none | `{3,4}` | 3 |
| 4 | `[4,4]` | `{1,4}`, `{3,4}`, `{4,4}`, `{4,6}` | none | `{1,4}` | 1 |
| 5 | none | the four above | `{1,4}`, `{3,4}` (right 4 is below 5); a size-4 entry is then on top and `{4,6}` is the only one with right `>= 5` | size 4 | 4 |

Results are stored at each query's original index (`order` remembers it), so the output order matches the input order even when queries are unsorted. The answer is `[3, 3, 1, 4]`.

**Second input** `intervals = [[2,3],[2,5],[1,8],[20,25]]`, `queries = [2,19,5,22]` is processed in the order 2, 5, 19, 22: for 2 the heap holds sizes 2, 4, 8 and the top is 2; for 5 the interval `[2,3]` is popped and the top is 4; for 19 the intervals `[2,5]` and then `[1,8]` are popped, leaving the heap empty, so `-1`; for 22 `[20,25]` is pushed and gives 6. The answer is `[2, -1, 4, 6]`.

**Edge cases.** A query with an empty heap after popping gets `-1`. Intervals with `left > q` are not pushed yet. The pop only removes the top, but a buried expired interval is harmless: it is removed when it reaches the top, and it can never be returned before that because only the top is read.

**Result:** `[3, 3, 1, 4]`, `[2, -1, 4, 6]` and `[-1]` for the three inputs in `main`, matching the class output.
