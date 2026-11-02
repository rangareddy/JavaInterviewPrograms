# Same Tree

Given the roots of two binary trees `p` and `q`, check whether they are the same: structurally identical and with equal node values.

## Algorithm Strategy (Simultaneous Recursion)

1. If both nodes are `null`, the (empty) subtrees are the same.
2. If only one of them is `null`, or the values differ, the trees are different.
3. Otherwise the trees are the same exactly when the left subtrees are the same and the right subtrees are the same; recurse on both pairs.

## Example walkthrough

- For `[1, 2, 3]` and `[1, 2, 3]`: roots equal (1), left children equal (2), right children equal (3), all leaves have null children on both sides.
- Result: `true`. For `[1, 2]` and `[1, null, 2]` the left children differ, so `false`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each pair of nodes is compared once.
- **Space Complexity:** `O(h)` — Recursion depth equals the tree height.

## Source Code

Uses the shared helper [`TreeNode`](../../../src/main/java/com/ranga/blind75/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### SameTree.java

```java
public class SameTree {

    public static void main(String[] args) {
        TreeNode[][] inputs = {
                {TreeNode.of(1, 2, 3), TreeNode.of(1, 2, 3)},
                {TreeNode.of(1, 2), TreeNode.of(1, null, 2)},
                {TreeNode.of(1, 2, 1), TreeNode.of(1, 1, 2)}
        };
        for (TreeNode[] pair : inputs) {
            System.out.println("Input  : p = " + TreeNode.toString(pair[0]) + ", q = " + TreeNode.toString(pair[1]));
            System.out.println("Output : " + isSameTree(pair[0], pair[1]));
            System.out.println();
        }
    }

    public static boolean isSameTree(TreeNode p, TreeNode q) {
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
