# Lowest Common Ancestor of a Binary Search Tree

Given a binary search tree and two nodes `p` and `q`, find their lowest common ancestor (LCA): the lowest node that has both as descendants (a node may be a descendant of itself).

## Algorithm Strategy (Walk Down Using the BST Property)

1. Start at the root. If both `p` and `q` are smaller than the current value, the LCA is in the left subtree, so move left.
2. If both are greater than the current value, the LCA is in the right subtree, so move right.
3. Otherwise the two values are on different sides of the current node (or one of them equals it), so the current node is the lowest node that is above both: it is the LCA.
4. No recursion or extra memory is needed.

## Example walkthrough

- For the tree `[6, 2, 8, 0, 4, 7, 9, null, null, 3, 5]` with `p = 2` and `q = 8`: both differ in side at the root 6 (2 < 6 < 8), so the LCA is `6`.
- For `p = 2` and `q = 4`: both are smaller than 6 so go left to 2; 2 equals `p`, so the LCA is `2`.

## Complexity Analysis

- **Time Complexity:** `O(h)` — One step per level.
- **Space Complexity:** `O(1)` — Iterative.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Root-to-Node Paths

Find the path from the root to each of the two nodes and return the last node the two paths share. It works for any binary tree, but ignores the BST ordering.

```java
public static TreeNode lcaPaths(TreeNode root, TreeNode p, TreeNode q) {
    List<TreeNode> pathP = new ArrayList<>(), pathQ = new ArrayList<>();
    path(root, p.val, pathP);
    path(root, q.val, pathQ);
    TreeNode lca = null;
    for (int i = 0; i < Math.min(pathP.size(), pathQ.size()) && pathP.get(i) == pathQ.get(i); i++) lca = pathP.get(i);
    return lca;
}

private static boolean path(TreeNode node, int target, List<TreeNode> path) {
    if (node == null) return false;
    path.add(node);
    if (node.val == target || path(node.left, target, path) || path(node.right, target, path)) return true;
    path.remove(path.size() - 1);
    return false;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 2: Recursion on the BST Property

If both values are smaller than the node, the answer is in the left subtree; if both are larger, in the right subtree; otherwise the node is the answer.

```java
public static TreeNode lcaRecursive(TreeNode root, TreeNode p, TreeNode q) {
    if (p.val < root.val && q.val < root.val) return lcaRecursive(root.left, p, q);
    if (p.val > root.val && q.val > root.val) return lcaRecursive(root.right, p, q);
    return root;
}
```

- **Time Complexity:** O(h)
- **Space Complexity:** O(h)

### Approach 3: Iterative Walk Down (Optimized)

The same decision in a loop: no recursion and `O(1)` space. This is the version implemented in the repository.

```java
public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    TreeNode curr = root;
    while (curr != null) {
        if (p.val < curr.val && q.val < curr.val) curr = curr.left;
        else if (p.val > curr.val && q.val > curr.val) curr = curr.right;
        else return curr;
    }
    return null;
}
```

- **Time Complexity:** O(h)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Root-to-Node Paths | O(n) | O(h) |
| 2 | Recursion on the BST Property | O(h) | O(h) |
| 3 | Iterative Walk Down (Optimized) | O(h) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../src/main/java/com/ranga/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### LowestCommonAncestorOfBST.java

```java
public class LowestCommonAncestorOfBST {

    public static void main(String[] args) {
        TreeNode root = TreeNode.of(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5);
        int[][] pairs = {{2, 8}, {2, 4}, {3, 5}};
        for (int[] pair : pairs) {
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", p = " + pair[0] + ", q = " + pair[1]);
            System.out.println("Output : " + lowestCommonAncestor(root, new TreeNode(pair[0]), new TreeNode(pair[1])).val);
            System.out.println();
        }
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode curr = root;
        while (curr != null) {
            if (p.val < curr.val && q.val < curr.val) {
                curr = curr.left;
            } else if (p.val > curr.val && q.val > curr.val) {
                curr = curr.right;
            } else {
                return curr;
            }
        }
        return null;
    }
}
```
