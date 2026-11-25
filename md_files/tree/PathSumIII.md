# Path Sum III

Given the root of a binary tree and an integer `targetSum`, return the number of paths whose node values sum to `targetSum`. A path does not need to start at the root or end at a leaf, but it must go downwards (from parent to child).

## Algorithm Strategy (Prefix Sum Map along the Current Path)

1. Do a DFS that carries the running sum from the root to the current node.
2. Keep a map from prefix sum to how many times it appeared on the path so far (start with `{0: 1}`).
3. At each node, the number of downward paths ending here with the target sum is `map[currentSum - targetSum]`.
4. Recurse into the children, then remove the node's prefix sum from the map when backtracking.

## Example walkthrough

- For the tree `[10, 5, -3, 3, 2, null, 11, 3, -2, null, 1]` and target 8: the paths are `5 -> 3`, `5 -> 2 -> 1` and `-3 -> 11`.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every node is visited once.
- **Space Complexity:** `O(h)` — The recursion and the map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS from Every Node

Start a downward sum search from every node.

```java
public static int everyNode(TreeNode root, int t) { return root == null ? 0 : from(root, t) + everyNode(root.left, t) + everyNode(root.right, t); }
private static int from(TreeNode n, long rest) { if (n == null) return 0; rest -= n.val; return (rest == 0 ? 1 : 0) + from(n.left, rest) + from(n.right, rest); }
```

- **Time Complexity:** O(n^2) worst, O(n log n) balanced
- **Space Complexity:** O(h)

### Approach 2: Prefix Sum Map (Optimized)

Count paths ending at each node with a map of prefix sums along the current path. This is the version implemented in the repository.

```java
public static int prefix(TreeNode root, int t) { java.util.Map<Long, Integer> m = new java.util.HashMap<>(); m.put(0L, 1); return go(root, 0L, t, m); }
private static int go(TreeNode n, long cur, int t, java.util.Map<Long, Integer> m) { if (n == null) return 0; cur += n.val; int p = m.getOrDefault(cur - t, 0); m.merge(cur, 1, Integer::sum); p += go(n.left, cur, t, m) + go(n.right, cur, t, m); m.merge(cur, -1, Integer::sum); return p; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS from Every Node | O(n^2) worst, O(n log n) balanced | O(h) |
| 2 | Prefix Sum Map (Optimized) | O(n) | O(h) |

## Source Code

### PathSumIII.java

```java
import java.util.HashMap;
import java.util.Map;

public class PathSumIII {

    public static void main(String[] args) {
        Integer[][] inputs = {{10, 5, -3, 3, 2, null, 11, 3, -2, null, 1}, {5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1}, {1}, {}};
        int[] targets = {8, 22, 1, 0};
        for (int i = 0; i < inputs.length; i++) {
            TreeNode root = TreeNode.of(inputs[i]);
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", targetSum = " + targets[i]);
            System.out.println("Output : " + pathSum(root, targets[i]));
            System.out.println();
        }
    }

    public static int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> prefixCounts = new HashMap<>();
        prefixCounts.put(0L, 1);
        return count(root, 0L, targetSum, prefixCounts);
    }

    private static int count(TreeNode node, long current, int target, Map<Long, Integer> prefixCounts) {
        if (node == null) {
            return 0;
        }
        current += node.val;
        int paths = prefixCounts.getOrDefault(current - target, 0);
        prefixCounts.merge(current, 1, Integer::sum);
        paths += count(node.left, current, target, prefixCounts);
        paths += count(node.right, current, target, prefixCounts);
        prefixCounts.merge(current, -1, Integer::sum);
        return paths;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a downward path ending at the current node is the difference between the root-to-node prefix sum and the prefix sum at some ancestor. So the number of such paths with sum `target` is the number of ancestors whose prefix sum equals `current - target`. A map of prefix-sum counts along the current root-to-node path gives that in constant time, and removing the entry when leaving a node keeps the map limited to the current path.

**Input:** `root = [10, 5, -3, 3, 2, null, 11, 3, -2, null, 1]`, `targetSum = 8`.

| Path found | Nodes |
|------------|-------|
| 1 | 5 -> 3 |
| 2 | 5 -> 2 -> 1 |
| 3 | -3 -> 11 |

**Output:** `3`.
