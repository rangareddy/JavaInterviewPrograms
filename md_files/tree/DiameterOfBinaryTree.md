# Diameter of Binary Tree

Given the root of a binary tree, return the length of its diameter: the length (number of edges) of the longest path between any two nodes. The path may or may not pass through the root.

## Algorithm Strategy (Post-order Heights with a Global Maximum)

1. The longest path that bends at a node goes down its deepest left branch and its deepest right branch, so its length in edges is `leftHeight + rightHeight`.
2. Compute heights bottom-up: the height of a missing child is 0 and the height of a node is `1 + max(leftHeight, rightHeight)`.
3. At every node update the best diameter with `leftHeight + rightHeight`.
4. The best value after visiting the whole tree is the diameter.

## Example walkthrough

- For `[1, 2, 3, 4, 5]`: node 2 has heights 1 and 1, so the path 4-2-5 has 2 edges; at the root the left height is 2 and the right height is 1.
- Diameter: `3` (the path 4-2-1-3 or 5-2-1-3).

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every node is visited once.
- **Space Complexity:** `O(h)` — Recursion depth equals the tree height.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force (Heights Recomputed per Node)

For every node the longest path bending there is `height(left) + height(right)`. Computing the heights from scratch for each node and taking the maximum over all nodes revisits subtrees many times.

```java
public static int diameterBrute(TreeNode root) {
    if (root == null) return 0;
    int through = height(root.left) + height(root.right);
    return Math.max(through, Math.max(diameterBrute(root.left), diameterBrute(root.right)));
}

private static int height(TreeNode node) {
    return node == null ? 0 : 1 + Math.max(height(node.left), height(node.right));
}
```

- **Time Complexity:** O(n^2) for a skewed tree
- **Space Complexity:** O(h)

### Approach 2: Post-order with a Global Maximum (Optimized)

Compute heights bottom-up once and update the best bend `left + right` at each node. This is the version implemented in the repository.

```java
public static int diameterOfBinaryTree(TreeNode root) {
    int[] best = {0};
    height(root, best);
    return best[0];
}

private static int height(TreeNode node, int[] best) {
    if (node == null) return 0;
    int left = height(node.left, best), right = height(node.right, best);
    best[0] = Math.max(best[0], left + right);
    return 1 + Math.max(left, right);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 3: Return a (height, diameter) Pair

Each call returns both the height and the best diameter of its subtree, so the method has no shared state and combines the two children's results directly.

```java
public static int diameterPair(TreeNode root) {
    return solve(root)[1];
}

// returns {height, diameter}
private static int[] solve(TreeNode node) {
    if (node == null) return new int[]{0, 0};
    int[] left = solve(node.left), right = solve(node.right);
    return new int[]{1 + Math.max(left[0], right[0]), Math.max(left[0] + right[0], Math.max(left[1], right[1]))};
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 4: Iterative Post-order with a Height Map

Visit the nodes in post-order with an explicit stack, store each node's height in a map and update the diameter when both children are known. No recursion.

```java
public static int diameterIterative(TreeNode root) {
    Map<TreeNode, Integer> heights = new IdentityHashMap<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root, lastVisited = null;
    int best = 0;
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) { stack.push(curr); curr = curr.left; }
        TreeNode top = stack.peek();
        if (top.right != null && lastVisited != top.right) {
            curr = top.right;
        } else {
            stack.pop();
            int left = top.left == null ? 0 : heights.get(top.left), right = top.right == null ? 0 : heights.get(top.right);
            best = Math.max(best, left + right);
            heights.put(top, 1 + Math.max(left, right));
            lastVisited = top;
        }
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (Heights Recomputed per Node) | O(n^2) for a skewed tree | O(h) |
| 2 | Post-order with a Global Maximum (Optimized) | O(n) | O(h) |
| 3 | Return a (height, diameter) Pair | O(n) | O(h) |
| 4 | Iterative Post-order with a Height Map | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../src/main/java/com/ranga/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### DiameterOfBinaryTree.java

```java
public class DiameterOfBinaryTree {

    public static void main(String[] args) {
        Integer[][] inputs = {{1, 2, 3, 4, 5}, {1, 2}, {1}, {1, 2, 3, 4, null, null, 5, 6, null, null, 7}, {}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + diameterOfBinaryTree(root));
            System.out.println();
        }
    }

    public static int diameterOfBinaryTree(TreeNode root) {
        int[] best = {0};
        height(root, best);
        return best[0];
    }

    // returns the height of the node (number of nodes on the longest downward path)
    private static int height(TreeNode node, int[] best) {
        if (node == null) {
            return 0;
        }
        int left = height(node.left, best);
        int right = height(node.right, best);
        best[0] = Math.max(best[0], left + right);          // longest path bending at this node
        return 1 + Math.max(left, right);
    }
}
```
