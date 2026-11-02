# Subtree of Another Tree

Given the roots of two binary trees `root` and `subRoot`, return `true` if some subtree of `root` (a node together with all its descendants) has exactly the structure and values of `subRoot`.

## Algorithm Strategy (Compare Every Node with Same Tree)

1. An empty `subRoot` is a subtree of any tree; an empty `root` cannot contain a non-empty `subRoot`.
2. If the tree rooted at the current node is identical to `subRoot` (checked with the Same Tree routine), return `true`.
3. Otherwise look for `subRoot` in the left subtree or in the right subtree.
4. Return `false` when all nodes were tried without a match.

## Example walkthrough

- For `root = [3, 4, 5, 1, 2]` and `subRoot = [4, 1, 2]`: the root 3 does not match 4; its left child 4 has children 1 and 2 and matches exactly.
- Result: `true`. If node 4 had an extra child, the structure would differ and the result would be `false`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — In the worst case each of the `m` nodes of root starts a comparison of up to `n` nodes.
- **Space Complexity:** `O(h)` — Recursion depth.

## Source Code

Uses the shared helper [`TreeNode`](../../../src/main/java/com/ranga/blind75/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### SubtreeOfAnotherTree.java

```java
public class SubtreeOfAnotherTree {

    public static void main(String[] args) {
        TreeNode[][] inputs = {
                {TreeNode.of(3, 4, 5, 1, 2), TreeNode.of(4, 1, 2)},
                {TreeNode.of(3, 4, 5, 1, 2, null, null, null, null, 0), TreeNode.of(4, 1, 2)}
        };
        for (TreeNode[] pair : inputs) {
            System.out.println("Input  : root = " + TreeNode.toString(pair[0]) + ", subRoot = " + TreeNode.toString(pair[1]));
            System.out.println("Output : " + isSubtree(pair[0], pair[1]));
            System.out.println();
        }
    }

    public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (subRoot == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        return isSameTree(root, subRoot) || isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null || p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```
