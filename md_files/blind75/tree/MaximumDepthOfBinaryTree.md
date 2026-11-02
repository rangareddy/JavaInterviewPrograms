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

## Source Code

Uses the shared helper [`TreeNode`](../../../src/main/java/com/ranga/blind75/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

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
