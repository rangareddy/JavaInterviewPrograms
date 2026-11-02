# Kth Smallest Element in a BST

Given the root of a binary search tree and an integer `k`, return the `k`-th smallest value (1-indexed) of all node values.

## Algorithm Strategy (Iterative In-order Traversal)

1. An in-order traversal (left, node, right) of a BST visits the values in ascending order.
2. Traverse iteratively with a stack: go as far left as possible pushing nodes, then pop a node, count it, and continue with its right subtree.
3. Decrement `k` for every popped node; when it reaches 0, the popped node is the answer.
4. Stopping early means only the first `k` nodes (plus the path to them) are visited.

## Example walkthrough

- For `[3, 1, 4, null, 2]` and `k = 1`: go left to 1 (no left child), pop it: the first value is 1.
- For `[5, 3, 6, 2, 4, null, null, 1]` and `k = 3`: the order is 1, 2, 3, so the answer is 3.

## Complexity Analysis

- **Time Complexity:** `O(h + k)` — Walk down the left spine, then visit `k` nodes.
- **Space Complexity:** `O(h)` — The stack holds one root-to-node path.

## Source Code

Uses the shared helper [`TreeNode`](../../../src/main/java/com/ranga/blind75/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### KthSmallestElementInBST.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class KthSmallestElementInBST {

    public static void main(String[] args) {
        TreeNode[] inputs = {TreeNode.of(3, 1, 4, null, 2), TreeNode.of(5, 3, 6, 2, 4, null, null, 1)};
        int[] ks = {1, 3};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : root = " + TreeNode.toString(inputs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + kthSmallest(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (--k == 0) {
                return curr.val;
            }
            curr = curr.right;
        }
        throw new IllegalArgumentException("k is larger than the number of nodes.");
    }
}
```
