# Invert Binary Tree

Given the root of a binary tree, invert the tree (swap the left and right child of every node) and return its root.

## Algorithm Strategy (Recursive Swap)

1. If the node is `null`, there is nothing to invert.
2. Recursively invert the left and the right subtree.
3. Swap the two inverted subtrees: `node.left = invertedRight` and `node.right = invertedLeft`.
4. Return the node, so the parent can attach it.

## Example walkthrough

- For `[4, 2, 7, 1, 3, 6, 9]`: node 2 becomes `(3, 1)`, node 7 becomes `(9, 6)`.
- The root swaps them: `[4, 7, 2, 9, 6, 3, 1]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every node is visited once.
- **Space Complexity:** `O(h)` — Recursion depth equals the tree height.

## Source Code

Uses the shared helper [`TreeNode`](../../../src/main/java/com/ranga/blind75/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### InvertBinaryTree.java

```java
public class InvertBinaryTree {

    public static void main(String[] args) {
        TreeNode[] inputs = {TreeNode.of(4, 2, 7, 1, 3, 6, 9), TreeNode.of(2, 1, 3), TreeNode.of()};
        for (TreeNode root : inputs) {
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + TreeNode.toString(invertTree(root)));
            System.out.println();
        }
    }

    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }
}
```
