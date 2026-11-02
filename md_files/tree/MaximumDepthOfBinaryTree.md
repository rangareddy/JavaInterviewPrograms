# Maximum Depth of Binary Tree

Given the root of a binary tree, return its maximum depth: the number of nodes along the longest path from the root down to the farthest leaf.

## Algorithm Strategy (Recursion (Depth-First Search))

1. The depth of an empty tree is 0.
2. The depth of any other tree is 1 (for the root) plus the larger of the depths of its left and right subtrees.
3. Recurse on both children and combine the results with `Math.max`.

## Example walkthrough

- For `[3, 9, 20, null, null, 15, 7]`: node 9 has depth 1; node 20 has depth 2 (via 15 or 7).
- The root gives `1 + max(1, 2) = 3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every node is visited once.
- **Space Complexity:** `O(h)` — Recursion depth equals the height `h` of the tree (`O(n)` for a skewed tree).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursive DFS (Optimized)

The depth of a tree is one more than the depth of its deeper subtree. This is the version implemented in the repository.

```java
public static int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 2: Iterative DFS with a Stack

Push each node together with its depth on an explicit stack and remember the largest depth seen. Avoids stack overflow on very deep (skewed) trees.

```java
public static int maxDepthStack(TreeNode root) {
    if (root == null) return 0;
    Deque<TreeNode> nodes = new ArrayDeque<>();
    Deque<Integer> depths = new ArrayDeque<>();
    nodes.push(root);
    depths.push(1);
    int best = 0;
    while (!nodes.isEmpty()) {
        TreeNode node = nodes.pop();
        int depth = depths.pop();
        best = Math.max(best, depth);
        if (node.left != null) { nodes.push(node.left); depths.push(depth + 1); }
        if (node.right != null) { nodes.push(node.right); depths.push(depth + 1); }
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 3: Level-Order BFS

Process the tree level by level with a queue and count the levels.

```java
public static int maxDepthBfs(TreeNode root) {
    if (root == null) return 0;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    int depth = 0;
    while (!queue.isEmpty()) {
        depth++;
        for (int i = queue.size(); i > 0; i--) {
            TreeNode node = queue.poll();
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
    }
    return depth;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(w) (maximum width)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursive DFS (Optimized) | O(n) | O(h) |
| 2 | Iterative DFS with a Stack | O(n) | O(h) |
| 3 | Level-Order BFS | O(n) | O(w) (maximum width) |

### Optimized Solution

The optimized solution is Approach 1 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../src/main/java/com/ranga/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### MaximumDepthOfBinaryTree.java

```java
public class MaximumDepthOfBinaryTree {

    public static void main(String[] args) {
        TreeNode[] inputs = {TreeNode.of(3, 9, 20, null, null, 15, 7), TreeNode.of(1, null, 2), TreeNode.of()};
        for (TreeNode root : inputs) {
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + maxDepth(root));
            System.out.println();
        }
    }

    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
}
```
