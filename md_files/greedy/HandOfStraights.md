# Hand of Straights

Alice has a `hand` of cards and wants to rearrange them into groups of `groupSize` consecutive cards. Return `true` if this is possible.

## Algorithm Strategy (Greedy with a Sorted Frequency Map)

1. If `hand.length` is not divisible by `groupSize`, return `false`.
2. Count the cards in a `TreeMap` (sorted by value).
3. Repeatedly take the smallest remaining card: it must start a group, because nothing smaller is left to extend it.
4. Remove one card of each value `start .. start + groupSize - 1`; if any is missing, return `false`. Drop values whose count reaches zero. If all cards are used, return `true`.

## Example walkthrough

- For `hand = [1, 2, 3, 6, 2, 3, 4, 7, 8]` and `groupSize = 3`: groups `[1,2,3]`, `[2,3,4]` and `[6,7,8]`.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Sorted map operations for every card.
- **Space Complexity:** `O(n)` — The frequency map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort and Remove from a List

Sort the hand and repeatedly build a group from the smallest card by removing the following consecutive values.

```java
public static boolean isNStraightHandList(int[] hand, int groupSize) {
    if (hand.length % groupSize != 0) return false;
    List<Integer> cards = new ArrayList<>();
    for (int c : hand) cards.add(c);
    Collections.sort(cards);
    while (!cards.isEmpty()) {
        int start = cards.remove(0);
        for (int k = 1; k < groupSize; k++) {
            if (!cards.remove(Integer.valueOf(start + k))) return false;
        }
    }
    return true;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Sort and Count Array

Sort the hand, count with a map and walk through the sorted cards, starting a group at each card with a positive count.

```java
public static boolean isNStraightHandSorted(int[] hand, int groupSize) {
    if (hand.length % groupSize != 0) return false;
    int[] sorted = hand.clone();
    Arrays.sort(sorted);
    Map<Integer, Integer> counts = new HashMap<>();
    for (int c : sorted) counts.merge(c, 1, Integer::sum);
    for (int c : sorted) {
        if (counts.get(c) == 0) continue;
        for (int k = 0; k < groupSize; k++) {
            int need = c + k;
            Integer have = counts.get(need);
            if (have == null || have == 0) return false;
            counts.put(need, have - 1);
        }
    }
    return true;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Min-Heap of Distinct Values

Keep the distinct values in a heap; the smallest value with a positive count starts the next group.

```java
public static boolean isNStraightHandHeap(int[] hand, int groupSize) {
    if (hand.length % groupSize != 0) return false;
    Map<Integer, Integer> counts = new HashMap<>();
    for (int c : hand) counts.merge(c, 1, Integer::sum);
    PriorityQueue<Integer> heap = new PriorityQueue<>(counts.keySet());
    while (!heap.isEmpty()) {
        int start = heap.peek();
        for (int card = start; card < start + groupSize; card++) {
            Integer have = counts.get(card);
            if (have == null) return false;
            counts.put(card, have - 1);
            if (have == 1) {
                if (card != heap.peek()) return false;
                heap.poll();
            }
        }
    }
    return true;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 4: TreeMap Greedy (Optimized)

A sorted frequency map gives the smallest card in O(log n). This is the version implemented in the repository.

```java
public static boolean isNStraightHand(int[] hand, int groupSize) {
    if (hand.length % groupSize != 0) return false;
    TreeMap<Integer, Integer> counts = new TreeMap<>();
    for (int card : hand) counts.merge(card, 1, Integer::sum);
    while (!counts.isEmpty()) {
        int start = counts.firstKey();
        for (int card = start; card < start + groupSize; card++) {
            Integer count = counts.get(card);
            if (count == null) return false;
            if (count == 1) counts.remove(card); else counts.put(card, count - 1);
        }
    }
    return true;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort and Remove from a List | O(n^2) | O(n) |
| 2 | Sort and Count Array | O(n log n) | O(n) |
| 3 | Min-Heap of Distinct Values | O(n log n) | O(n) |
| 4 | TreeMap Greedy (Optimized) | O(n log n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### HandOfStraights.java

```java
import java.util.Arrays;
import java.util.TreeMap;

public class HandOfStraights {

    public static void main(String[] args) {
        int[][] hands = {{1, 2, 3, 6, 2, 3, 4, 7, 8}, {1, 2, 3, 4, 5}, {1, 1, 2, 2, 3, 3}, {5}};
        int[] sizes = {3, 4, 3, 1};
        for (int i = 0; i < hands.length; i++) {
            System.out.println("Input  : hand = " + Arrays.toString(hands[i]) + ", groupSize = " + sizes[i]);
            System.out.println("Output : " + isNStraightHand(hands[i], sizes[i]));
            System.out.println();
        }
    }

    public static boolean isNStraightHand(int[] hand, int groupSize) {
        if (hand.length % groupSize != 0) {
            return false;
        }

        TreeMap<Integer, Integer> counts = new TreeMap<>();
        for (int card : hand) {
            counts.merge(card, 1, Integer::sum);
        }

        while (!counts.isEmpty()) {
            int start = counts.firstKey();
            for (int card = start; card < start + groupSize; card++) {
                Integer count = counts.get(card);
                if (count == null) {
                    return false;
                }
                if (count == 1) {
                    counts.remove(card);
                } else {
                    counts.put(card, count - 1);
                }
            }
        }
        return true;
    }
}
```
