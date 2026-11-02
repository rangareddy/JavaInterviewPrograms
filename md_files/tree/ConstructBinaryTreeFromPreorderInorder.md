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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Linear Search in the In-order Array

Take the root from the pre-order array, find it in the in-order array with a linear search, and recurse on the two parts.

```java
public static TreeNode buildTreeSearch(int[] preorder, int[] inorder) {
    return buildSearch(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
}

private static TreeNode buildSearch(int[] pre, int preStart, int preEnd, int[] in, int inStart, int inEnd) {
    if (preStart > preEnd) return null;
    TreeNode root = new TreeNode(pre[preStart]);
    int rootIndex = inStart;
    while (in[rootIndex] != root.val) rootIndex++;
    int leftSize = rootIndex - inStart;
    root.left = buildSearch(pre, preStart + 1, preStart + leftSize, in, inStart, rootIndex - 1);
    root.right = buildSearch(pre, preStart + leftSize + 1, preEnd, in, rootIndex + 1, inEnd);
    return root;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(h)

### Approach 2: Hash Map of In-order Indexes (Optimized)

Precompute the position of every value in the in-order array so each root is located in `O(1)`. This is the version implemented in the repository.

```java
public static TreeNode buildTree(int[] preorder, int[] inorder) {
    Map<Integer, Integer> index = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) index.put(inorder[i], i);
    return build(preorder, 0, preorder.length - 1, 0, index);
}

private static TreeNode build(int[] pre, int preStart, int preEnd, int inStart, Map<Integer, Integer> index) {
    if (preStart > preEnd) return null;
    TreeNode root = new TreeNode(pre[preStart]);
    int rootIndex = index.get(root.val), leftSize = rootIndex - inStart;
    root.left = build(pre, preStart + 1, preStart + leftSize, inStart, index);
    root.right = build(pre, preStart + leftSize + 1, preEnd, rootIndex + 1, index);
    return root;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Single Pass with a Stack and Limits

Without any map: pass the in-order array as a stream and use a "stop value" to know when the current subtree ends, advancing two global indexes. Shorter recursion, no extra array.

```java
private static int preIndex, inIndex;

public static TreeNode buildTreeStop(int[] preorder, int[] inorder) {
    preIndex = 0;
    inIndex = 0;
    return buildStop(preorder, inorder, Integer.MIN_VALUE, false);
}

// 'hasStop' says whether 'stop' is a real in-order value that ends the current subtree
private static TreeNode buildStop(int[] pre, int[] in, int stop, boolean hasStop) {
    if (preIndex >= pre.length) return null;
    if (hasStop && in[inIndex] == stop) { inIndex++; return null; }
    TreeNode root = new TreeNode(pre[preIndex++]);
    root.left = buildStop(pre, in, root.val, true);
    root.right = buildStop(pre, in, stop, hasStop);
    return root;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Linear Search in the In-order Array | O(n^2) | O(h) |
| 2 | Hash Map of In-order Indexes (Optimized) | O(n) | O(n) |
| 3 | Single Pass with a Stack and Limits | O(n) | O(h) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../src/main/java/com/ranga/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

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
