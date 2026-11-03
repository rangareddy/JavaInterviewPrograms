# Jump Game II

You are given a 0-indexed array `nums` where `nums[i]` is the maximum jump length from index `i`. Starting at index 0, return the minimum number of jumps needed to reach the last index. The last index is always reachable.

## Algorithm Strategy (Greedy BFS by Reachable Window)

1. Treat each jump as one BFS level: the indices reachable with `jumps` jumps form a window `[start, end]`.
2. While scanning the window, track `farthest`, the furthest index reachable from any index in it.
3. When the scan reaches `end`, the current level is finished: take one more jump and set `end = farthest`.
4. Stop as soon as `end >= n - 1`; the number of jumps taken is the answer.

## Example walkthrough

- For `[2, 3, 1, 1, 4]`: window `[0, 0]` reaches index 2, jump 1; window `[1, 2]` reaches index 4, jump 2.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index is visited once.
- **Space Complexity:** `O(1)` — Three variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force Recursion

Try every jump length from each index and keep the minimum.

```java
public static int jumpBrute(int[] nums) {
    return go(nums, 0);
}

private static int go(int[] nums, int i) {
    if (i >= nums.length - 1) return 0;
    int best = Integer.MAX_VALUE;
    for (int step = 1; step <= nums[i]; step++) {
        int sub = go(nums, i + step);
        if (sub != Integer.MAX_VALUE) best = Math.min(best, 1 + sub);
    }
    return best;
}
```

- **Time Complexity:** O(k^n)
- **Space Complexity:** O(n)

### Approach 2: Bottom-Up DP

`dp[i]` is the minimum number of jumps to reach index `i`; relax every index reachable from `i`.

```java
public static int jumpDp(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    for (int i = 0; i < n; i++)
        for (int j = i + 1; j <= Math.min(n - 1, i + nums[i]); j++)
            if (dp[i] != Integer.MAX_VALUE) dp[j] = Math.min(dp[j], dp[i] + 1);
    return dp[n - 1];
}
```

- **Time Complexity:** O(n * k)
- **Space Complexity:** O(n)

### Approach 3: Greedy: Furthest Landing Spot

From the current index jump to the index in range that reaches furthest next.

```java
public static int jumpFurthest(int[] nums) {
    int jumps = 0, current = 0;
    while (current < nums.length - 1) {
        if (current + nums[current] >= nums.length - 1) return jumps + 1;
        int bestIndex = current + 1;
        for (int j = current + 1; j <= current + nums[current]; j++)
            if (j + nums[j] > bestIndex + nums[bestIndex]) bestIndex = j;
        current = bestIndex;
        jumps++;
    }
    return jumps;
}
```

- **Time Complexity:** O(n * k) worst case
- **Space Complexity:** O(1)

### Approach 4: Greedy BFS Window (Optimized)

Track the end of the current level and the furthest reach in one pass. This is the version implemented in the repository.

```java
public static int jump(int[] nums) {
    int jumps = 0, end = 0, farthest = 0;
    for (int i = 0; i < nums.length - 1; i++) {
        farthest = Math.max(farthest, i + nums[i]);
        if (i == end) {
            jumps++;
            end = farthest;
        }
    }
    return jumps;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force Recursion | O(k^n) | O(n) |
| 2 | Bottom-Up DP | O(n * k) | O(n) |
| 3 | Greedy: Furthest Landing Spot | O(n * k) worst case | O(1) |
| 4 | Greedy BFS Window (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### JumpGameII.java

```java
import java.util.Arrays;

public class JumpGameII {

    public static void main(String[] args) {
        int[][] inputs = {{2, 3, 1, 1, 4}, {2, 3, 0, 1, 4}, {0}, {1, 1, 1, 1}, {5, 1, 1, 1, 1, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + jump(nums));
            System.out.println();
        }
    }

    public static int jump(int[] nums) {
        int jumps = 0;
        int end = 0;            // last index reachable with the current number of jumps
        int farthest = 0;       // furthest index reachable with one more jump
        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (i == end) {
                jumps++;
                end = farthest;
            }
        }
        return jumps;
    }
}
```
