# Keys and Rooms

There are `n` rooms labeled `0` to `n - 1`, and all rooms are locked except room `0`. Each room holds a set of keys, and a key `i` opens room `i`. Given `rooms`, where `rooms[i]` is the list of keys in room `i`, return `true` if you can visit every room.

## Algorithm Strategy (Depth-First Search from Room 0)

1. Treat each room as a node and each key as a directed edge to another room.
2. Start at room 0 and walk with an explicit stack, marking every room the first time it is reached.
3. Push every unvisited room whose key you find.
4. When the stack is empty, all rooms were visited exactly when the count of visited rooms equals `n`.

## Example walkthrough

- For `[[1], [2], [3], []]`: room 0 holds key 1, room 1 holds key 2, room 2 holds key 3, so all four rooms are reached.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n + k)` — Every room and every key is processed once, where `k` is the total number of keys.
- **Space Complexity:** `O(n)` — The visited array and the stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursive DFS

Visit rooms recursively. Simple, but deep chains of rooms can overflow the call stack.

```java
public static boolean recursive(int[][] rooms) { boolean[] seen = new boolean[rooms.length]; dfs(rooms, 0, seen); for (boolean b : seen) if (!b) return false; return true; }
static void dfs(int[][] rooms, int room, boolean[] seen) { seen[room] = true; for (int key : rooms[room]) if (!seen[key]) dfs(rooms, key, seen); }
```

- **Time Complexity:** O(n + k)
- **Space Complexity:** O(n)

### Approach 2: Iterative DFS with a Stack (Optimized)

Keep the pending rooms on an explicit stack and count what was reached. This is the version implemented in the repository.

```java
public static boolean iterative(int[][] rooms) { boolean[] seen = new boolean[rooms.length]; java.util.Deque<Integer> st = new java.util.ArrayDeque<>(); seen[0] = true; st.push(0); int c = 1; while (!st.isEmpty()) { int room = st.pop(); for (int key : rooms[room]) if (!seen[key]) { seen[key] = true; c++; st.push(key); } } return c == rooms.length; }
```

- **Time Complexity:** O(n + k)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursive DFS | O(n + k) | O(n) |
| 2 | Iterative DFS with a Stack (Optimized) | O(n + k) | O(n) |

## Source Code

### KeysAndRooms.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class KeysAndRooms {

    public static void main(String[] args) {
        int[][][] inputs = {{{1}, {2}, {3}, {}}, {{1, 3}, {3, 0, 1}, {2}, {0}}, {{}}, {{2}, {}, {1}}};
        for (int[][] rooms : inputs) {
            System.out.println("Input  : rooms = " + Arrays.deepToString(rooms));
            System.out.println("Output : " + canVisitAllRooms(rooms));
            System.out.println();
        }
    }

    public static boolean canVisitAllRooms(int[][] rooms) {
        if (rooms == null || rooms.length == 0) {
            return true;
        }
        boolean[] seen = new boolean[rooms.length];
        Deque<Integer> stack = new ArrayDeque<>();
        seen[0] = true;
        stack.push(0);
        int visited = 1;
        while (!stack.isEmpty()) {
            int room = stack.pop();
            for (int key : rooms[room]) {
                if (!seen[key]) {
                    seen[key] = true;
                    visited++;
                    stack.push(key);
                }
            }
        }
        return visited == rooms.length;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: keys are directed edges, so the question is whether every node is reachable from node 0. A stack-based search marks a room when it is first discovered, so each room is pushed at most once.

**Input:** `rooms = [[1], [2], [3], []]`.

| Step | Room opened | Keys found | Newly reached | Visited |
|------|-------------|------------|---------------|---------|
| 1 | 0 | 1 | 1 | 2 |
| 2 | 1 | 2 | 2 | 3 |
| 3 | 2 | 3 | 3 | 4 |
| 4 | 3 | none | none | 4 |

All 4 rooms are visited.

**Output:** `true`.
