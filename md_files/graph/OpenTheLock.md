# Open the Lock

A lock has four circular wheels, each showing a digit from `0` to `9`, starting at `"0000"`. One move turns one wheel one step up or down (`9` wraps to `0` and back). Some combinations in `deadends` lock the wheels permanently. Given a `target`, return the minimum number of moves to reach it, or `-1` if it is impossible.

## Algorithm Strategy (BFS over the 10,000 States)

1. Put all deadends in a set and mark them as visited; if `"0000"` is a deadend return -1.
2. BFS from `"0000"`: each state has up to 8 neighbours (4 wheels, 2 directions).
3. Skip visited states, add the rest to the queue and count one move per level.
4. Return the level at which the target is dequeued, or -1 when the queue empties.

## Example walkthrough

- For `deadends = ["0201","0101","0102","1212","2002"]` and `target = "0202"`: a shortest path is `0000 -> 1000 -> 1100 -> 1200 -> 1201 -> 1202 -> 0202`.
- Result: `6`.

## Complexity Analysis

- **Time Complexity:** `O(10^4 * 8)` — At most 10,000 states with 8 neighbours each.
- **Space Complexity:** `O(10^4)` — The visited set and the queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: One-Directional BFS (Optimized)

Plain BFS from `0000`. This is the version implemented in the repository.

```java
public static int bfs(String[] dead, String target) {
    java.util.Set<String> v = new java.util.HashSet<>(java.util.Arrays.asList(dead)); if (v.contains("0000")) return -1;
    java.util.Queue<String> q = new java.util.ArrayDeque<>(); q.add("0000"); v.add("0000"); int m = 0;
    while (!q.isEmpty()) { for (int s = q.size(); s > 0; s--) { String c = q.poll(); if (c.equals(target)) return m;
        for (int w = 0; w < 4; w++) for (int d = -1; d <= 1; d += 2) { char[] n = c.toCharArray(); n[w] = (char) ('0' + (n[w] - '0' + d + 10) % 10); String x = new String(n); if (v.add(x)) q.add(x); } } m++; }
    return -1;
}
```

- **Time Complexity:** O(10^4 * 8)
- **Space Complexity:** O(10^4)

### Approach 2: Bidirectional BFS

Grow search frontiers from `0000` and from the target and stop when they meet.

```java
public static int bidirectional(String[] dead, String target) {
    java.util.Set<String> bad = new java.util.HashSet<>(java.util.Arrays.asList(dead)); if (bad.contains("0000") || bad.contains(target)) return -1;
    java.util.Set<String> a = new java.util.HashSet<>(), b = new java.util.HashSet<>(), seen = new java.util.HashSet<>(); a.add("0000"); b.add(target); int m = 0;
    while (!a.isEmpty() && !b.isEmpty()) {
        if (a.size() > b.size()) { java.util.Set<String> t = a; a = b; b = t; }
        java.util.Set<String> next = new java.util.HashSet<>();
        for (String c : a) { if (b.contains(c)) return m; if (!seen.add(c)) continue;
            for (int w = 0; w < 4; w++) for (int d = -1; d <= 1; d += 2) { char[] n = c.toCharArray(); n[w] = (char) ('0' + (n[w] - '0' + d + 10) % 10); String x = new String(n); if (!bad.contains(x)) next.add(x); } }
        a = next; m++;
    }
    return -1;
}
```

- **Time Complexity:** O(10^4 * 8)
- **Space Complexity:** O(10^4)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | One-Directional BFS (Optimized) | O(10^4 * 8) | O(10^4) |
| 2 | Bidirectional BFS | O(10^4 * 8) | O(10^4) |

## Source Code

### OpenTheLock.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class OpenTheLock {

    public static void main(String[] args) {
        String[][] deadends = {{"0201", "0101", "0102", "1212", "2002"}, {"8888"}, {"8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888"}, {"0000"}};
        String[] targets = {"0202", "0009", "8888", "8888"};
        for (int i = 0; i < deadends.length; i++) {
            System.out.println("Input  : deadends = " + Arrays.toString(deadends[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + openLock(deadends[i], targets[i]));
            System.out.println();
        }
    }

    public static int openLock(String[] deadends, String target) {
        Set<String> visited = new HashSet<>(Arrays.asList(deadends));
        if (visited.contains("0000")) {
            return -1;
        }
        Queue<String> queue = new ArrayDeque<>();
        queue.add("0000");
        visited.add("0000");
        int moves = 0;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                String state = queue.poll();
                if (state.equals(target)) {
                    return moves;
                }
                for (int wheel = 0; wheel < 4; wheel++) {
                    for (int delta = -1; delta <= 1; delta += 2) {
                        char[] next = state.toCharArray();
                        next[wheel] = (char) ('0' + (next[wheel] - '0' + delta + 10) % 10);
                        String candidate = new String(next);
                        if (visited.add(candidate)) {
                            queue.add(candidate);
                        }
                    }
                }
            }
            moves++;
        }
        return -1;
    }
}
```

## Solution

The optimized solution is Approach 1 above, which is also the version implemented in this repository; bidirectional search (Approach 2) explores fewer states.

The idea: every lock combination is a node, every one-wheel turn is an edge, and the minimum number of moves is the shortest path from `0000`, which BFS finds level by level.

**Input:** `target = "0202"` with the deadends of the first sample.

| Move | State |
|------|-------|
| 0 | 0000 |
| 1 | 1000 |
| 2 | 1100 |
| 3 | 1200 |
| 4 | 1201 |
| 5 | 1202 |
| 6 | 0202 |

**Output:** `6`.
