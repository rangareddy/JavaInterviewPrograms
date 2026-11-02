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

## Source Code

Uses the shared helper [`TreeNode`](../../../src/main/java/com/ranga/blind75/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

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
