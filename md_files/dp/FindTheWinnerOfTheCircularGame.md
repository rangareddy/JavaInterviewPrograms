# Find the Winner of the Circular Game

`n` friends sit in a circle numbered `1` to `n`. Starting at friend 1, count `k` friends clockwise (including the starting one); the friend at the end of the count leaves the circle, and the next count starts from the friend clockwise of the one who left. Repeat until one friend remains and return that friend's number.

## Algorithm Strategy (Josephus Recurrence)

1. Let `f(n)` be the 0-indexed winner among `n` friends. With one friend, `f(1) = 0`.
2. After the first removal, the circle has `n - 1` friends and the count restarts `k` positions later, so `f(n) = (f(n - 1) + k) % n`.
3. Compute it iteratively from 2 to `n`.
4. Return `f(n) + 1` to get a 1-indexed friend.

## Example walkthrough

- For `n = 5`, `k = 2`: the friends leave in the order 2, 4, 1, 5, so friend 3 wins.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One loop.
- **Space Complexity:** `O(1)` — One integer.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Simulate with a Queue

Rotate the queue `k - 1` times and remove the front, until one is left.

```java
public static int queue(int n, int k) {
    java.util.Queue<Integer> q = new java.util.ArrayDeque<>(); for (int i = 1; i <= n; i++) q.add(i);
    while (q.size() > 1) { for (int i = 1; i < k; i++) q.add(q.poll()); q.poll(); }
    return q.peek();
}
```

- **Time Complexity:** O(n * k)
- **Space Complexity:** O(n)

### Approach 2: Simulate with a List and an Index

Keep the friends in a list and jump the index by `k - 1`.

```java
public static int list(int n, int k) {
    java.util.List<Integer> l = new java.util.ArrayList<>(); for (int i = 1; i <= n; i++) l.add(i); int idx = 0;
    while (l.size() > 1) { idx = (idx + k - 1) % l.size(); l.remove(idx); }
    return l.get(0);
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 3: Josephus Recurrence (Optimized)

`f(n) = (f(n - 1) + k) % n`. This is the version implemented in the repository.

```java
public static int josephus(int n, int k) { int w = 0; for (int s = 2; s <= n; s++) w = (w + k) % s; return w + 1; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Simulate with a Queue | O(n * k) | O(n) |
| 2 | Simulate with a List and an Index | O(n^2) | O(n) |
| 3 | Josephus Recurrence (Optimized) | O(n) | O(1) |

## Source Code

### FindTheWinnerOfTheCircularGame.java

```java
public class FindTheWinnerOfTheCircularGame {

    public static void main(String[] args) {
        int[][] inputs = {{5, 2}, {6, 5}, {1, 3}, {7, 3}};
        for (int[] in : inputs) {
            System.out.println("Input  : n = " + in[0] + ", k = " + in[1]);
            System.out.println("Output : " + findTheWinner(in[0], in[1]));
            System.out.println();
        }
    }

    public static int findTheWinner(int n, int k) {
        int winner = 0;
        for (int size = 2; size <= n; size++) {
            winner = (winner + k) % size;
        }
        return winner + 1;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: after the first friend leaves, the game restarts with one fewer friend, but the numbering is shifted by `k`. Tracing the winner's position back from a one-person circle gives the recurrence `f(n) = (f(n - 1) + k) % n`.

**Input:** `n = 5`, `k = 2`.

| Circle size | Winner position (0-indexed) |
|-------------|-----------------------------|
| 1 | 0 |
| 2 | (0 + 2) % 2 = 0 |
| 3 | (0 + 2) % 3 = 2 |
| 4 | (2 + 2) % 4 = 0 |
| 5 | (0 + 2) % 5 = 2 |

The 0-indexed winner is 2, which is friend 3.

**Output:** `3`.
