# Validate Binary Search Tree

Given the root of a binary tree, determine whether it is a valid binary search tree: for every node, all keys in the left subtree are smaller and all keys in the right subtree are greater, and both subtrees are valid BSTs.

## Algorithm Strategy (Recursion with Allowed Value Range)

1. Checking only the direct children is not enough, because a deep node must respect the bounds of all of its ancestors.
2. Recurse with a range `(min, max)`: the root may take any value; when going left the new upper bound is the parent value, when going right the new lower bound is the parent value.
3. A node is invalid when its value is not strictly between `min` and `max`. Use `long` bounds so `Integer.MIN_VALUE` / `MAX_VALUE` nodes are handled.
4. An empty subtree is valid. The tree is valid when both subtrees are valid for their ranges.

## Example walkthrough

- For `[5, 1, 4, null, null, 3, 6]`: the right child 4 is below 5 but not greater than it, so the check fails at 4.
- Result: `false`. For `[2, 1, 3]` every node is inside its range: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every node is visited once.
- **Space Complexity:** `O(h)` — Recursion depth equals the tree height.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion with Value Bounds (Optimized)

Pass the allowed `(min, max)` range down the tree; every node must lie strictly inside it. This is the version implemented in the repository.

```java
public static boolean isValidBST(TreeNode root) {
    return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
}

private static boolean isValid(TreeNode node, long min, long max) {
    if (node == null) return true;
    if (node.val <= min || node.val >= max) return false;
    return isValid(node.left, min, node.val) && isValid(node.right, node.val, max);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 2: In-order Traversal Must Be Increasing

An in-order traversal of a BST is strictly increasing, so remember the previous value and fail at the first value that is not larger.

```java
public static boolean isValidBSTInOrder(TreeNode root) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    Long previous = null;
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) { stack.push(curr); curr = curr.left; }
        curr = stack.pop();
        if (previous != null && curr.val <= previous) return false;
        previous = (long) curr.val;
        curr = curr.right;
    }
    return true;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 3: Brute Force (Check Every Node Against Its Subtrees)

For every node check that all values of the left subtree are smaller and all values of the right subtree larger, by scanning the subtrees. Quadratic on a skewed tree.

```java
public static boolean isValidBSTBrute(TreeNode root) {
    if (root == null) return true;
    return allSmaller(root.left, root.val) && allLarger(root.right, root.val)
            && isValidBSTBrute(root.left) && isValidBSTBrute(root.right);
}

private static boolean allSmaller(TreeNode node, int value) {
    return node == null || (node.val < value && allSmaller(node.left, value) && allSmaller(node.right, value));
}

private static boolean allLarger(TreeNode node, int value) {
    return node == null || (node.val > value && allLarger(node.left, value) && allLarger(node.right, value));
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(h)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion with Value Bounds (Optimized) | O(n) | O(h) |
| 2 | In-order Traversal Must Be Increasing | O(n) | O(h) |
| 3 | Brute Force (Check Every Node Against Its Subtrees) | O(n^2) | O(h) |

### Optimized Solution

The optimized solution is Approach 1 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../src/main/java/com/ranga/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### ValidateBinarySearchTree.java

```java
public class ValidateBinarySearchTree {

    public static void main(String[] args) {
        TreeNode[] inputs = {TreeNode.of(2, 1, 3), TreeNode.of(5, 1, 4, null, null, 3, 6), TreeNode.of(5, 4, 6, null, null, 3, 7)};
        for (TreeNode root : inputs) {
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + isValidBST(root));
            System.out.println();
        }
    }

    public static boolean isValidBST(TreeNode root) {
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean isValid(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }
        if (node.val <= min || node.val >= max) {
            return false;
        }
        return isValid(node.left, min, node.val) && isValid(node.right, node.val, max);
    }
}
```
