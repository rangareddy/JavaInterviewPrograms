# Path Sum

Given the root of a binary tree and an integer `targetSum`, return `true` if the tree has a root-to-leaf path whose node values add up to `targetSum`, and `false` otherwise. A leaf is a node with no children.

## Algorithm Strategy (DFS with Remaining Sum)

1. Subtract each node's value from the target as you walk down.
2. At a leaf, the path matches exactly when the remaining target equals the leaf's value.
3. For an inner node, the answer is true if either child subtree can reach the reduced target.
4. An empty tree has no path, so it returns `false`.

## Example walkthrough

- For the tree `[5,4,8,11,null,13,4,7,2,null,null,null,1]` and target 22: the path 5 -> 4 -> 11 -> 2 adds to 22.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every node is visited at most once.
- **Space Complexity:** `O(h)` — Recursion depth equals the tree height.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: All Root-to-Leaf Sums

Collect the sum of every root-to-leaf path in a list, then check whether the target is in it.

```java
public static boolean pathSumViaList(TreeNode root, int target) {
    java.util.List<Integer> sums = new java.util.ArrayList<>();
    collect(root, 0, sums);
    return sums.contains(target);
}
private static void collect(TreeNode n, int acc, java.util.List<Integer> out) {
    if (n == null) return;
    acc += n.val;
    if (n.left == null && n.right == null) { out.add(acc); return; }
    collect(n.left, acc, out); collect(n.right, acc, out);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Iterative DFS with a Stack

Keep the running sum next to each node on an explicit stack.

```java
public static boolean pathSumIterative(TreeNode root, int target) {
    if (root == null) return false;
    java.util.Deque<TreeNode> nodes = new java.util.ArrayDeque<>();
    java.util.Deque<Integer> sums = new java.util.ArrayDeque<>();
    nodes.push(root); sums.push(root.val);
    while (!nodes.isEmpty()) {
        TreeNode n = nodes.pop(); int s = sums.pop();
        if (n.left == null && n.right == null && s == target) return true;
        if (n.right != null) { nodes.push(n.right); sums.push(s + n.right.val); }
        if (n.left != null) { nodes.push(n.left); sums.push(s + n.left.val); }
    }
    return false;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 3: Recursive DFS with Remaining Sum (Optimized)

Subtract the node value on the way down and test at the leaves. This is the version implemented in the repository.

```java
public static boolean pathSumRecursive(TreeNode root, int target) {
    if (root == null) return false;
    int rem = target - root.val;
    if (root.left == null && root.right == null) return rem == 0;
    return pathSumRecursive(root.left, rem) || pathSumRecursive(root.right, rem);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | All Root-to-Leaf Sums | O(n) | O(n) |
| 2 | Iterative DFS with a Stack | O(n) | O(h) |
| 3 | Recursive DFS with Remaining Sum (Optimized) | O(n) | O(h) |

## Source Code

### PathSum.java

```java
public class PathSum {

    public static void main(String[] args) {
        Object[][] inputs = {
            {new Integer[]{5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1}, 22},
            {new Integer[]{1, 2, 3}, 5},
            {new Integer[]{1, 2}, 1},
            {new Integer[]{}, 0}
        };
        for (Object[] in : inputs) {
            TreeNode root = TreeNode.of((Integer[]) in[0]);
            int target = (Integer) in[1];
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", targetSum = " + target);
            System.out.println("Output : " + hasPathSum(root, target));
            System.out.println();
        }
    }

    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        int remaining = targetSum - root.val;
        if (root.left == null && root.right == null) {
            return remaining == 0;
        }
        return hasPathSum(root.left, remaining) || hasPathSum(root.right, remaining);
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: carry the part of the target that is still needed. At a leaf the path is valid when nothing is left over, and the check must be made at a leaf only, because a path must end at one.

**Input:** `root = [5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1]`, `targetSum = 22`.

| Node | Remaining before | Remaining after | Note |
|------|------------------|-----------------|------|
| 5 | 22 | 17 | root |
| 4 | 17 | 13 | left child of 5 |
| 11 | 13 | 2 | left child of 4 |
| 7 | 2 | -5 | leaf, remaining is not 0 |
| 2 | 2 | 0 | leaf, remaining is 0, path found |

**Output:** `true`.
