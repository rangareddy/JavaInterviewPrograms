# Jump Game

Each element of an array is the maximum jump length from that position. Starting at the first index, return `true` if the last index can be reached.

## Algorithm Strategy (Greedy: Track the Farthest Reachable Index)

1. Keep `farthest`, the largest index reachable using the positions seen so far (initially 0).
2. For each index `i`: if `i > farthest`, this position cannot be reached at all, so the last index is unreachable: return `false`.
3. Otherwise update `farthest = max(farthest, i + nums[i])`.
4. If `farthest` ever reaches the last index, return `true`. No table is needed because reaching a position means every earlier position is reachable too.

## Example walkthrough

- For `[2, 3, 1, 1, 4]`: farthest becomes 2, 4 (at index 1) and the last index 4 is reached: `true`.
- For `[3, 2, 1, 0, 4]`: farthest is stuck at 3 and index 4 is never reachable: `false`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single pass.
- **Space Complexity:** `O(1)` — One variable.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Recursion (Backtracking)

From each index try every possible jump length. Exponential in the worst case.

```java
public static boolean canJumpRecursive(int[] nums) {
    return solve(nums, 0);
}

private static boolean solve(int[] nums, int index) {
    if (index >= nums.length - 1) return true;
    for (int jump = nums[index]; jump >= 1; jump--) {
        if (solve(nums, index + jump)) return true;
    }
    return false;
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Bottom-Up DP

`reachable[i]` is true if some earlier reachable index can jump to `i`.

```java
public static boolean canJumpDp(int[] nums) {
    boolean[] reachable = new boolean[nums.length];
    reachable[0] = true;
    for (int i = 0; i < nums.length; i++) {
        if (!reachable[i]) continue;
        for (int j = i + 1; j <= Math.min(nums.length - 1, i + nums[i]); j++) reachable[j] = true;
    }
    return reachable[nums.length - 1];
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 3: Greedy from the End

Keep the leftmost index from which the last index can be reached (`goal`); move it left whenever an earlier index can jump to it. The answer is whether `goal` ends at index 0.

```java
public static boolean canJumpFromEnd(int[] nums) {
    int goal = nums.length - 1;
    for (int i = nums.length - 2; i >= 0; i--) {
        if (i + nums[i] >= goal) goal = i;
    }
    return goal == 0;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: Greedy: Farthest Reachable Index (Optimized)

Sweep once and keep the farthest index reachable so far; fail when the sweep reaches an index beyond it. This is the version implemented in the repository.

```java
public static boolean canJump(int[] nums) {
    int farthest = 0;
    for (int i = 0; i < nums.length; i++) {
        if (i > farthest) return false;
        farthest = Math.max(farthest, i + nums[i]);
        if (farthest >= nums.length - 1) return true;
    }
    return true;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Recursion (Backtracking) | O(2^n) | O(n) |
| 2 | Bottom-Up DP | O(n^2) | O(n) |
| 3 | Greedy from the End | O(n) | O(1) |
| 4 | Greedy: Farthest Reachable Index (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### JumpGame.java

```java
import java.util.Arrays;

public class JumpGame {

    public static void main(String[] args) {
        int[][] inputs = {{2, 3, 1, 1, 4}, {3, 2, 1, 0, 4}, {0}, {2, 0, 0}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + canJump(nums));
            System.out.println();
        }
    }

    public static boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int farthest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > farthest) {
                return false;       // position i cannot be reached
            }
            farthest = Math.max(farthest, i + nums[i]);
            if (farthest >= nums.length - 1) {
                return true;
            }
        }
        return true;
    }
}
```
