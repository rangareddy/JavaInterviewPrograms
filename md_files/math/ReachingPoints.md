# Reaching Points

Given four integers `sx`, `sy`, `tx` and `ty`, return `true` if it is possible to convert the point `(sx, sy)` to `(tx, ty)` through some operations, and `false` otherwise. One operation turns `(x, y)` into `(x, x + y)` or `(x + y, y)`.

## Algorithm Strategy (Reverse the Operations)

1. Going backward from `(tx, ty)` there is only one possible previous point: the larger coordinate must have been produced by adding the smaller one. So if `tx > ty` the previous point is `(tx - ty, ty)`, otherwise `(tx, ty - tx)`.
2. Subtracting one by one is slow, so subtract many times at once with modulo: `tx %= ty` (or `ty %= tx`).
3. Stop when `tx <= sx` or `ty <= sy`, or when `tx == ty`.
4. At the end, check whether the remaining point can still reach the source: if `tx == sx` then `ty >= sy` and `(ty - sy) % tx == 0`; symmetric for `ty == sy`.

## Example walkthrough

- For `(1, 1)` to `(3, 5)`: `(3, 5)` came from `(3, 2)`, which came from `(1, 2)`, which came from `(1, 1)`.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(log(max(tx, ty)))` — Like the Euclidean algorithm.
- **Space Complexity:** `O(1)` — A few longs.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Forward Search

Apply both operations from the source (BFS/DFS) until the target is passed. Exponential.

```java
public static boolean forward(int sx, int sy, int tx, int ty) {
    if (sx > tx || sy > ty) return false; if (sx == tx && sy == ty) return true; return forward(sx + sy, sy, tx, ty) || forward(sx, sx + sy, tx, ty);
}
```

- **Time Complexity:** O(2^k)
- **Space Complexity:** O(k)

### Approach 2: Backward with Single Subtractions

Undo one operation at a time. Correct, but too slow when one coordinate is much larger than the other.

```java
public static boolean backwardSlow(int sx, int sy, int tx, int ty) {
    while (tx >= sx && ty >= sy) { if (tx == sx && ty == sy) return true; if (tx > ty) tx -= ty; else if (ty > tx) ty -= tx; else break; }
    return false;
}
```

- **Time Complexity:** O(max(tx, ty))
- **Space Complexity:** O(1)

### Approach 3: Backward with Modulo (Optimized)

Subtract many times at once with the modulo operator. This is the version implemented in the repository.

```java
public static boolean backwardFast(int sx, int sy, int tx, int ty) {
    while (tx > sx && ty > sy && tx != ty) { if (tx > ty) tx %= ty; else ty %= tx; }
    if (tx == sx && ty == sy) return true; if (tx == sx) return ty > sy && (ty - sy) % tx == 0; if (ty == sy) return tx > sx && (tx - sx) % ty == 0; return false;
}
```

- **Time Complexity:** O(log(max(tx, ty)))
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Forward Search | O(2^k) | O(k) |
| 2 | Backward with Single Subtractions | O(max(tx, ty)) | O(1) |
| 3 | Backward with Modulo (Optimized) | O(log(max(tx, ty))) | O(1) |

## Source Code

### ReachingPoints.java

```java
public class ReachingPoints {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 3, 5}, {1, 1, 2, 2}, {1, 1, 1, 1}, {1, 4, 5, 9}};
        for (int[] in : inputs) {
            System.out.println("Input  : sx = " + in[0] + ", sy = " + in[1] + ", tx = " + in[2] + ", ty = " + in[3]);
            System.out.println("Output : " + reachingPoints(in[0], in[1], in[2], in[3]));
            System.out.println();
        }
    }

    public static boolean reachingPoints(int sx, int sy, int tx, int ty) {
        while (tx > sx && ty > sy && tx != ty) {
            if (tx > ty) {
                tx %= ty;
            } else {
                ty %= tx;
            }
        }
        if (tx == sx && ty == sy) {
            return true;
        }
        if (tx == sx) {
            return ty > sy && (ty - sy) % tx == 0;
        }
        if (ty == sy) {
            return tx > sx && (tx - sx) % ty == 0;
        }
        return false;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: forward, every point has two successors. Backward, every point has exactly one predecessor, because the larger coordinate must have come from adding the smaller. That makes the reverse path unique, and the Euclidean algorithm skips straight to the end of a run of identical steps.

**Input:** `(sx, sy) = (1, 1)`, `(tx, ty) = (3, 5)`.

| Step | Point | Operation undone |
|------|-------|------------------|
| 0 | (3, 5) | |
| 1 | (3, 2) | `ty %= tx`: 5 % 3 = 2 |
| 2 | (1, 2) | `tx %= ty`: 3 % 2 = 1 |
| 3 | `tx == sx`, `(ty - sy) % tx = 1 % 1 = 0` | reachable |

**Output for this input:** `true`. For the first sample in `main`, `(1, 1)` to `(3, 5)`, the output is `true`.
