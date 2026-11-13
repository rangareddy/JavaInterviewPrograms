# Asteroid Collision

Asteroids in a row are given as integers: the absolute value is the size and the sign is the direction (positive moves right, negative moves left). All move at the same speed. When two meet, the smaller one explodes, both explode if they are the same size, and asteroids moving in the same direction never meet. Return the state after all collisions.

## Algorithm Strategy (Stack Simulation)

1. Only a right-moving asteroid followed by a left-moving one can collide. Keep a stack of the asteroids that survive so far.
2. For each new asteroid, if it moves right (or the stack is empty, or the top moves left) it cannot collide with anything on the stack, so push it.
3. Otherwise it moves left and the top moves right, so they collide: if the top is smaller, pop it and keep checking against the next top; if they are equal, pop the top and destroy the new one; if the top is bigger, the new asteroid is destroyed.
4. The stack from bottom to top is the final state, in the original order.

## Example walkthrough

- For `[5, 10, -5]`: `-5` meets `10` and is destroyed, leaving `[5, 10]`.
- For `[10, 2, -5]`: `-5` destroys `2`, then meets `10` and is destroyed, leaving `[10]`.
- For `[8, -8]`: both explode, leaving `[]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every asteroid is pushed once and popped at most once.
- **Space Complexity:** `O(n)` — The stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Repeat Until Stable

Repeatedly find the first adjacent pair (right-moving, left-moving), resolve it, and start again until no pair remains.

```java
public static int[] asteroidCollisionRepeat(int[] asteroids) {
    List<Integer> list = new ArrayList<>();
    for (int a : asteroids) list.add(a);
    boolean changed = true;
    while (changed) {
        changed = false;
        for (int i = 0; i + 1 < list.size(); i++) {
            int left = list.get(i), right = list.get(i + 1);
            if (left > 0 && right < 0) {
                if (left > -right) list.remove(i + 1);
                else if (left < -right) list.remove(i);
                else { list.remove(i + 1); list.remove(i); }
                changed = true;
                break;
            }
        }
    }
    int[] result = new int[list.size()];
    for (int i = 0; i < result.length; i++) result[i] = list.get(i);
    return result;
}
```

- **Time Complexity:** O(n^2) in the worst case
- **Space Complexity:** O(n)

### Approach 2: Stack (Optimized)

Only the top of the stack can collide with a new left-moving asteroid, so each collision is handled in constant time. This is the version implemented in the repository.

```java
public static int[] asteroidCollision(int[] asteroids) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (int asteroid : asteroids) {
        boolean alive = true;
        while (alive && asteroid < 0 && !stack.isEmpty() && stack.peek() > 0) {
            int top = stack.peek();
            if (top < -asteroid) {
                stack.pop();
            } else if (top == -asteroid) {
                stack.pop();
                alive = false;
            } else {
                alive = false;
            }
        }
        if (alive) {
            stack.push(asteroid);
        }
    }
    int[] result = new int[stack.size()];
    for (int i = result.length - 1; i >= 0; i--) {
        result[i] = stack.pop();
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Repeat Until Stable | O(n^2) in the worst case | O(n) |
| 2 | Stack (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### AsteroidCollision.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class AsteroidCollision {

    public static void main(String[] args) {
        int[][] inputs = {{5, 10, -5}, {8, -8}, {10, 2, -5}, {-2, -1, 1, 2}};
        for (int[] asteroids : inputs) {
            System.out.println("Input  : asteroids = " + Arrays.toString(asteroids));
            System.out.println("Output : " + Arrays.toString(asteroidCollision(asteroids)));
            System.out.println();
        }
    }

    public static int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int asteroid : asteroids) {
            boolean alive = true;
            while (alive && asteroid < 0 && !stack.isEmpty() && stack.peek() > 0) {
                int top = stack.peek();
                if (top < -asteroid) {
                    stack.pop();
                } else if (top == -asteroid) {
                    stack.pop();
                    alive = false;
                } else {
                    alive = false;
                }
            }
            if (alive) {
                stack.push(asteroid);
            }
        }
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }
}
```
