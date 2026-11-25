# Frog Jump

A frog crosses a river by jumping on stones at the positions in the sorted array `stones` (the first stone is at position 0). The frog starts on the first stone and its first jump must be 1 unit. If its last jump was `k` units, the next jump must be `k - 1`, `k` or `k + 1` units, and it can only land on a stone. Return `true` if the frog can reach the last stone.

## Algorithm Strategy (Set of Jump Sizes per Stone)

1. Map each stone position to the set of jump sizes with which the frog can arrive there. Start with `{0}` on the first stone.
2. For each stone in order and for each arriving jump size `k`, try the jumps `k - 1`, `k` and `k + 1` (only positive ones).
3. If the landing position is a stone, add the jump size to that stone's set.
4. The frog can cross if the last stone's set is not empty.

## Example walkthrough

- For `[0,1,3,5,6,8,12,17]`: 0 -> 1 (jump 1) -> 3 (jump 2) -> 5 (jump 2) -> 8 (jump 3) -> 12 (jump 4) -> 17 (jump 5).
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Each stone has at most n distinct jump sizes.
- **Space Complexity:** `O(n^2)` — The sets of jump sizes.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursive Search with Memoisation

Try the three jump sizes from each (stone, last jump) pair and cache failures.

```java
public static boolean memo(int[] stones) {
    java.util.Set<Integer> set = new java.util.HashSet<>(); for (int s : stones) set.add(s); return go(stones[stones.length - 1], set, 0, 0, new java.util.HashSet<>());
}
private static boolean go(int end, java.util.Set<Integer> set, int pos, int k, java.util.Set<Long> bad) {
    if (pos == end) return true; long key = (long) pos * 100000 + k; if (bad.contains(key)) return false;
    for (int next = k - 1; next <= k + 1; next++) if (next > 0 && set.contains(pos + next) && go(end, set, pos + next, next, bad)) return true;
    bad.add(key); return false;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Approach 2: Set of Jump Sizes per Stone (Optimized)

Propagate the reachable jump sizes forward through the stones. This is the version implemented in the repository.

```java
public static boolean sets(int[] stones) {
    java.util.Map<Integer, java.util.Set<Integer>> m = new java.util.HashMap<>(); for (int s : stones) m.put(s, new java.util.HashSet<>()); m.get(stones[0]).add(0);
    for (int s : stones) for (int k : m.get(s)) for (int nx = k - 1; nx <= k + 1; nx++) if (nx > 0 && m.containsKey(s + nx)) m.get(s + nx).add(nx);
    return !m.get(stones[stones.length - 1]).isEmpty();
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursive Search with Memoisation | O(n^2) | O(n^2) |
| 2 | Set of Jump Sizes per Stone (Optimized) | O(n^2) | O(n^2) |

## Source Code

### FrogJump.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FrogJump {

    public static void main(String[] args) {
        int[][] inputs = {{0, 1, 3, 5, 6, 8, 12, 17}, {0, 1, 2, 3, 4, 8, 9, 11}, {0, 1}, {0, 2}};
        for (int[] stones : inputs) {
            System.out.println("Input  : stones = " + Arrays.toString(stones));
            System.out.println("Output : " + canCross(stones));
            System.out.println();
        }
    }

    public static boolean canCross(int[] stones) {
        if (stones == null || stones.length == 0) {
            return false;
        }
        Map<Integer, Set<Integer>> jumps = new HashMap<>();
        for (int stone : stones) {
            jumps.put(stone, new HashSet<>());
        }
        jumps.get(stones[0]).add(0);
        for (int stone : stones) {
            for (int k : jumps.get(stone)) {
                for (int next = k - 1; next <= k + 1; next++) {
                    if (next > 0 && jumps.containsKey(stone + next)) {
                        jumps.get(stone + next).add(next);
                    }
                }
            }
        }
        return !jumps.get(stones[stones.length - 1]).isEmpty();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: where the frog can go next depends on the stone and on its last jump size, so remember, for every stone, the set of last jump sizes that can reach it.

**Input:** `stones = [0, 1, 3, 5, 6, 8, 12, 17]`.

| Stone | Arrival jump sizes |
|-------|--------------------|
| 0 | {0} |
| 1 | {1} |
| 3 | {2} |
| 5 | {2} |
| 6 | {1, 3} |
| 8 | {2, 3} |
| 12 | {4} |
| 17 | {5} |

The last stone has a non-empty set.

**Output:** `true`.
