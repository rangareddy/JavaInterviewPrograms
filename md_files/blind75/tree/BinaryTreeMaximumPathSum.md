# Binary Tree Maximum Path Sum

A path in a binary tree is a sequence of nodes where each pair of adjacent nodes is connected by an edge and no node appears twice; it does not have to pass through the root. Return the maximum path sum of any non-empty path.

## Algorithm Strategy (Post-order DFS with a Global Maximum)

1. For every node, compute the best "downward" gain it can offer to its parent: `node.val + max(0, leftGain, rightGain)`. Negative gains are ignored by taking `max(0, ...)`.
2. A path that bends at this node uses both children: its sum is `node.val + max(0, leftGain) + max(0, rightGain)`. Compare it with the global best.
3. Return only the one-sided gain to the parent, because a path cannot split into two branches above the node.
4. The global best after visiting the whole tree is the answer.

## Example walkthrough

- For `[-10, 9, 20, null, null, 15, 7]`: node 20 offers gain `20 + 15 = 35` and its bent path is `15 + 20 + 7 = 42`.
- At the root the bent path is `-10 + 9 + 35 = 34`, smaller than 42. Result: `42`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every node is visited once.
- **Space Complexity:** `O(h)` — Recursion depth equals the tree height.

## Source Code

Uses the shared helper [`TreeNode`](../../../src/main/java/com/ranga/blind75/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### BinaryTreeMaximumPathSum.java

```java
public class BinaryTreeMaximumPathSum {

    private static int best;

    public static void main(String[] args) {
        TreeNode[] inputs = {TreeNode.of(1, 2, 3), TreeNode.of(-10, 9, 20, null, null, 15, 7), TreeNode.of(-3)};
        for (TreeNode root : inputs) {
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + maxPathSum(root));
            System.out.println();
        }
    }

    public static int maxPathSum(TreeNode root) {
        best = Integer.MIN_VALUE;
        gain(root);
        return best;
    }

    // Returns the best sum of a path that starts at the node and goes down one side only
    private static int gain(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftGain = Math.max(0, gain(node.left));
        int rightGain = Math.max(0, gain(node.right));

        best = Math.max(best, node.val + leftGain + rightGain);     // path that bends at this node
        return node.val + Math.max(leftGain, rightGain);
    }
}
```
