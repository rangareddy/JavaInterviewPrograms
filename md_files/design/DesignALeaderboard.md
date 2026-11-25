# Design A Leaderboard

Design a `Leaderboard` class with three functions: `addScore(playerId, score)` adds `score` to the player's total (creating the player at `score` if new), `top(K)` returns the sum of the scores of the top `K` players, and `reset(playerId)` sets the player's score back to `0`, removing the player. A player is guaranteed to exist before `reset` is called.

## Algorithm Strategy (Map for Scores, Bounded Heap for Top K)

1. Keep a `HashMap<Integer, Integer>` from player id to total score; `addScore` and `reset` are O(1).
2. For `top(K)`, scan the scores with a min-heap of size `K`: offer each score and drop the smallest whenever the heap grows past `K`.
3. The heap then holds the `K` largest scores; add them up.
4. This costs `O(n log K)` and avoids sorting all players.

## Example walkthrough

- After `addScore(1, 73)`, `addScore(2, 56)`, `addScore(3, 39)`, `addScore(4, 51)`, `addScore(5, 4)`: `top(1)` is 73.
- After `reset(1)`, `reset(2)`, `addScore(2, 51)`, the scores are 51, 39, 51, 4 and the top three sum to 141.

## Complexity Analysis

- **Time Complexity:** `O(1) update, O(n log K) top` — `top` scans all players with a heap of size `K`.
- **Space Complexity:** `O(n)` — The score map plus a heap of up to `K` elements.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort All Scores on Every top Call

Copy all scores into a list, sort descending and add the first `K`.

```java
public static class Sorted { private final java.util.Map<Integer, Integer> m = new java.util.HashMap<>(); public void addScore(int id, int s) { m.merge(id, s, Integer::sum); } public void reset(int id) { m.remove(id); } public int top(int k) { java.util.List<Integer> l = new java.util.ArrayList<>(m.values()); l.sort(java.util.Collections.reverseOrder()); int sum = 0; for (int i = 0; i < k && i < l.size(); i++) sum += l.get(i); return sum; } }
```

- **Time Complexity:** O(n log n) per top
- **Space Complexity:** O(n)

### Approach 2: Bounded Min-Heap (Optimized)

Keep only the `K` best scores while scanning. This is the version implemented in the repository.

```java
public static class Heap { private final java.util.Map<Integer, Integer> m = new java.util.HashMap<>(); public void addScore(int id, int s) { m.merge(id, s, Integer::sum); } public void reset(int id) { m.remove(id); } public int top(int k) { java.util.PriorityQueue<Integer> h = new java.util.PriorityQueue<>(); for (int v : m.values()) { h.offer(v); if (h.size() > k) h.poll(); } int sum = 0; for (int v : h) sum += v; return sum; } }
```

- **Time Complexity:** O(n log K) per top
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort All Scores on Every top Call | O(n log n) per top | O(n) |
| 2 | Bounded Min-Heap (Optimized) | O(n log K) per top | O(n) |

## Source Code

### DesignALeaderboard.java

```java
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class DesignALeaderboard {

    public static void main(String[] args) {
        DesignALeaderboard board = new DesignALeaderboard();
        board.addScore(1, 73);
        board.addScore(2, 56);
        board.addScore(3, 39);
        board.addScore(4, 51);
        board.addScore(5, 4);
        System.out.println("Input  : addScore(1, 73), addScore(2, 56), addScore(3, 39), addScore(4, 51), addScore(5, 4), top(1)");
        System.out.println("Output : " + board.top(1));
        System.out.println();
        board.reset(1);
        board.reset(2);
        board.addScore(2, 51);
        System.out.println("Input  : reset(1), reset(2), addScore(2, 51), top(3)");
        System.out.println("Output : " + board.top(3));
    }

    private final Map<Integer, Integer> scores = new HashMap<>();

    public void addScore(int playerId, int score) {
        scores.merge(playerId, score, Integer::sum);
    }

    public int top(int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int score : scores.values()) {
            heap.offer(score);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        int sum = 0;
        for (int score : heap) {
            sum += score;
        }
        return sum;
    }

    public void reset(int playerId) {
        scores.remove(playerId);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: updates need direct access by player, so a hash map is the source of truth. Only `top(K)` needs ordering, and a min-heap capped at `K` elements keeps exactly the `K` largest scores while scanning, without sorting everybody.

**Sequence:** `addScore` for players 1 to 5 with 73, 56, 39, 51, 4, then `top(1)`.

| Heap size limit | Scan order | Heap after scan |
|-----------------|------------|-----------------|
| 1 | 73, 56, 39, 51, 4 | [73] |

**Output:** `73`.

After `reset(1)`, `reset(2)`, `addScore(2, 51)` the map holds 51, 39, 51, 4, so `top(3)` is `51 + 51 + 39 = 141`.
