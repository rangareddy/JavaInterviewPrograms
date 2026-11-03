# Last Stone Weight

You are given an array of stone weights. In each turn pick the two heaviest stones with weights `x <= y` and smash them: if `x == y` both stones are destroyed, otherwise the stone of weight `x` is destroyed and the other one gets the weight `y - x`. Return the weight of the last remaining stone, or 0 if no stone is left.

## Algorithm Strategy (Max-Heap Simulation)

1. Put all stone weights into a max-heap, so the two heaviest stones are always available in `O(log n)`.
2. While at least two stones remain, remove the two largest ones, `y` then `x`.
3. If they differ, push the difference `y - x` back into the heap (equal stones vanish).
4. At the end the heap holds zero or one stone: return its weight, or 0 for an empty heap.

## Example walkthrough

- For `[2, 7, 4, 1, 8, 1]`: smash 8 and 7 `-> 1`; smash 4 and 2 `-> 2`; smash 2 and 1 `-> 1`; smash 1 and 1 `-> 0`.
- One stone of weight 1 remains: `1`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Each stone is pushed and removed a logarithmic number of times.
- **Space Complexity:** `O(n)` — The heap.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort Repeatedly

Sort the list, take the two largest, smash them and put the result back; repeat. Correct but re-sorts the whole list every turn.

```java
public static int lastStoneWeightSort(int[] stones) {
    List<Integer> list = new ArrayList<>();
    for (int stone : stones) list.add(stone);
    while (list.size() > 1) {
        Collections.sort(list);
        int heaviest = list.remove(list.size() - 1), second = list.remove(list.size() - 1);
        if (heaviest != second) list.add(heaviest - second);
    }
    return list.isEmpty() ? 0 : list.get(0);
}
```

- **Time Complexity:** O(n^2 log n)
- **Space Complexity:** O(n)

### Approach 2: TreeMap Multiset

A `TreeMap<weight, count>` is a sorted multiset: `lastKey` gives the heaviest stone, and counts are decremented instead of removing elements one by one.

```java
public static int lastStoneWeightTreeMap(int[] stones) {
    TreeMap<Integer, Integer> counts = new TreeMap<>();
    for (int stone : stones) counts.merge(stone, 1, Integer::sum);
    while (!counts.isEmpty()) {
        int heaviest = take(counts);
        if (counts.isEmpty()) return heaviest;
        int second = take(counts);
        if (heaviest != second) counts.merge(heaviest - second, 1, Integer::sum);
    }
    return 0;
}

private static int take(TreeMap<Integer, Integer> counts) {
    int weight = counts.lastKey();
    if (counts.merge(weight, -1, Integer::sum) == 0) counts.remove(weight);
    return weight;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Bucket Array of Weights

When the weights are small integers, count the stones of each weight in an array and process the weights from the largest down. Pairs of equal stones cancel in bulk (`count % 2`), and only a single stone per weight has to be smashed against the next lighter one.

```java
public static int lastStoneWeightBuckets(int[] stones) {
    int max = 0;
    for (int stone : stones) max = Math.max(max, stone);
    int[] bucket = new int[max + 1];
    for (int stone : stones) bucket[stone]++;
    int first = max, second = max;
    while (first > 0) {
        if (bucket[first] == 0) { first--; continue; }
        bucket[first] %= 2;                              // pairs of equal stones destroy each other
        if (bucket[first] == 0) continue;
        second = Math.min(first - 1, second);
        while (second > 0 && bucket[second] == 0) second--;
        if (second == 0) break;                          // nothing left to smash against
        bucket[first]--;
        bucket[second]--;
        bucket[first - second]++;
        first = Math.max(first - second, second);
    }
    return first;
}
```

- **Time Complexity:** O(n + M)
- **Space Complexity:** O(M)

### Approach 4: Max-Heap (Optimized)

A max-heap hands out the two heaviest stones in `O(log n)`. This is the version implemented in the repository.

```java
public static int lastStoneWeight(int[] stones) {
    PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
    for (int stone : stones) heap.add(stone);
    while (heap.size() > 1) {
        int heaviest = heap.poll(), second = heap.poll();
        if (heaviest != second) heap.add(heaviest - second);
    }
    return heap.isEmpty() ? 0 : heap.peek();
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort Repeatedly | O(n^2 log n) | O(n) |
| 2 | TreeMap Multiset | O(n log n) | O(n) |
| 3 | Bucket Array of Weights | O(n + M) | O(M) |
| 4 | Max-Heap (Optimized) | O(n log n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### LastStoneWeight.java

```java
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class LastStoneWeight {

    public static void main(String[] args) {
        int[][] inputs = {{2, 7, 4, 1, 8, 1}, {1}, {2, 2}, {3, 7, 2}, {}};
        for (int[] stones : inputs) {
            System.out.println("Input  : stones = " + Arrays.toString(stones));
            System.out.println("Output : " + lastStoneWeight(stones));
            System.out.println();
        }
    }

    public static int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        for (int stone : stones) {
            heap.add(stone);
        }

        while (heap.size() > 1) {
            int heaviest = heap.poll();
            int second = heap.poll();
            if (heaviest != second) {
                heap.add(heaviest - second);
            }
        }
        return heap.isEmpty() ? 0 : heap.peek();
    }
}
```
