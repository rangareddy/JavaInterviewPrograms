# Construct Binary Tree from Preorder and Inorder Traversal

Given the pre-order and in-order traversals of a binary tree (with unique values), construct and return the tree.

## Algorithm Strategy (Root from Pre-order, Split by In-order)

1. The first element of the pre-order range is the root of that subtree.
2. Find the root in the in-order array: everything to its left belongs to the left subtree and everything to its right to the right subtree. Store the in-order positions in a `HashMap` for `O(1)` lookups.
3. The number of nodes in the left subtree tells where the left part of the pre-order range ends; recurse on the left ranges, then on the right ranges.
4. A range that is empty returns `null`.

## Example walkthrough

- For preorder `[3, 9, 20, 15, 7]` and inorder `[9, 3, 15, 20, 7]`: the root is 3; in-order has one node (9) on its left, so 9 is the left subtree.
- The rest `[20, 15, 7]` / `[15, 20, 7]` gives root 20 with children 15 and 7: `[3, 9, 20, null, null, 15, 7]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each node is created once and each lookup is `O(1)`.
- **Space Complexity:** `O(n)` — The index map and the recursion stack.

## Source Code

Uses the shared helper [`TreeNode`](../../../src/main/java/com/ranga/blind75/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### ConstructBinaryTreeFromPreorderInorder.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConstructBinaryTreeFromPreorderInorder {

    public static void main(String[] args) {
        int[][] preorders = {{3, 9, 20, 15, 7}, {-1}};
        int[][] inorders = {{9, 3, 15, 20, 7}, {-1}};
        for (int i = 0; i < preorders.length; i++) {
            System.out.println("Input  : preorder = " + Arrays.toString(preorders[i]) + ", inorder = " + Arrays.toString(inorders[i]));
            System.out.println("Output : " + TreeNode.toString(buildTree(preorders[i], inorders[i])));
            System.out.println();
        }
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }

        Map<Integer, Integer> inorderIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndex.put(inorder[i], i);
        }
        return build(preorder, 0, preorder.length - 1, 0, inorderIndex);
    }

    private static TreeNode build(int[] preorder, int preStart, int preEnd, int inStart, Map<Integer, Integer> inorderIndex) {
        if (preStart > preEnd) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[preStart]);
        int rootIndex = inorderIndex.get(root.val);
        int leftSize = rootIndex - inStart;

        root.left = build(preorder, preStart + 1, preStart + leftSize, inStart, inorderIndex);
        root.right = build(preorder, preStart + leftSize + 1, preEnd, rootIndex + 1, inorderIndex);
        return root;
    }
}
```
