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
