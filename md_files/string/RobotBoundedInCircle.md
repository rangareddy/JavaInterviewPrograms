# Robot Bounded In Circle

On an infinite plane a robot starts at `(0, 0)` facing north. It executes `instructions` forever, where `G` means go forward one unit, `L` turns left 90 degrees and `R` turns right 90 degrees. Return `true` if and only if there is a circle in the plane such that the robot never leaves it.

## Algorithm Strategy (One Pass Is Enough)

1. Simulate the instructions once, tracking position and direction.
2. If the robot is back at the origin, it repeats the same cycle forever: bounded.
3. If it ends facing a different direction than north, then after at most four repetitions it has turned all the way around and returned to the origin: bounded.
4. Otherwise it ends facing north away from the origin, so each repetition moves it farther: unbounded.

## Example walkthrough

- For `GGLLGG`: the robot goes up 2, turns around, and goes back down 2, ending at the origin facing south.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Position and direction.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Simulate Four Cycles

Run the instruction string four times and check whether the robot is back at the origin. Safe because the direction repeats after four cycles.

```java
public static boolean four(String ins) { int[] dx = {0, 1, 0, -1}, dy = {1, 0, -1, 0}; int x = 0, y = 0, d = 0; for (int rep = 0; rep < 4; rep++) for (char c : ins.toCharArray()) { if (c == 'G') { x += dx[d]; y += dy[d]; } else if (c == 'L') d = (d + 3) % 4; else if (c == 'R') d = (d + 1) % 4; } return x == 0 && y == 0; }
```

- **Time Complexity:** O(4n)
- **Space Complexity:** O(1)

### Approach 2: Single Pass and End State (Optimized)

Run once; bounded if back at the origin or no longer facing north. This is the version implemented in the repository.

```java
public static boolean once(String ins) { int[] dx = {0, 1, 0, -1}, dy = {1, 0, -1, 0}; int x = 0, y = 0, d = 0; for (char c : ins.toCharArray()) { if (c == 'G') { x += dx[d]; y += dy[d]; } else if (c == 'L') d = (d + 3) % 4; else if (c == 'R') d = (d + 1) % 4; } return (x == 0 && y == 0) || d != 0; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Simulate Four Cycles | O(4n) | O(1) |
| 2 | Single Pass and End State (Optimized) | O(n) | O(1) |

## Source Code

### RobotBoundedInCircle.java

```java
public class RobotBoundedInCircle {

    public static void main(String[] args) {
        String[] inputs = {"GGLLGG", "GG", "GL", "GRGRGRG", ""};
        for (String instructions : inputs) {
            System.out.println("Input  : instructions = \"" + instructions + "\"");
            System.out.println("Output : " + isRobotBounded(instructions));
            System.out.println();
        }
    }

    public static boolean isRobotBounded(String instructions) {
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        int x = 0;
        int y = 0;
        int dir = 0;
        for (int i = 0; i < instructions.length(); i++) {
            char c = instructions.charAt(i);
            if (c == 'G') {
                x += dx[dir];
                y += dy[dir];
            } else if (c == 'L') {
                dir = (dir + 3) % 4;
            } else if (c == 'R') {
                dir = (dir + 1) % 4;
            }
        }
        return (x == 0 && y == 0) || dir != 0;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the end state of one pass is enough to know the long-run behaviour. A net turn changes the heading each cycle, and after four cycles the displacements cancel out. Only "same heading and not at the origin" drifts away forever.

**Input:** `instructions = "GGLLGG"`.

| Step | Position | Direction |
|------|----------|-----------|
| start | (0, 0) | north |
| `G`, `G` | (0, 2) | north |
| `L`, `L` | (0, 2) | south |
| `G`, `G` | (0, 0) | south |

The robot is back at the origin (and also faces south).

**Output:** `true`.
