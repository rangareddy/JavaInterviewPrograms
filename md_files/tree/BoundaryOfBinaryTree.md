# Boundary of Binary Tree

The boundary of a binary tree is the concatenation, in anti-clockwise order starting at the root, of: the root, the left boundary (nodes from the root's left child down, choosing the left child when there is one and the right otherwise, excluding leaves), the leaves from left to right, and the right boundary in reverse (excluding leaves). Return the values of the boundary.

## Algorithm Strategy (Three Walks)

1. Add the root's value (if the root is not a leaf).
2. Left boundary: starting at `root.left`, repeatedly add the node (if it is not a leaf) and go to its left child, or the right child if there is no left child.
3. Leaves: a DFS that adds every leaf from left to right.
4. Right boundary: walk the same way on the right side, collecting values in a list and adding them in reverse.

## Example walkthrough

- For the tree `[1, null, 2, 3, 4]` the root is 1, there is no left boundary, the leaves are 3 and 4, and the right boundary is just 2 (a non-leaf).
- Result: `[1, 3, 4, 2]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each node is visited a constant number of times.
- **Space Complexity:** `O(h)` — The recursion for the leaves.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: One DFS with Flags

A single recursive walk that tells each node whether it is on the left or the right boundary and adds it at the right moment. Compact but harder to read.

```java
public static java.util.List<Integer> flags(TreeNode root) {
    java.util.List<Integer> out = new java.util.ArrayList<>(), right = new java.util.ArrayList<>(); if (root == null) return out; if (root.left == null && root.right == null) { out.add(root.val); return out; } out.add(root.val);
    go(root.left, true, false, out, right); go(root.right, false, true, out, right); java.util.Collections.reverse(right); out.addAll(right); return out;
}
private static void go(TreeNode n, boolean left, boolean right, java.util.List<Integer> out, java.util.List<Integer> rightList) {
    if (n == null) return; boolean leaf = n.left == null && n.right == null;
    if (left && !leaf) out.add(n.val); if (leaf) out.add(n.val);
    go(n.left, left, right && n.right == null, out, rightList); go(n.right, left && n.left == null, right, out, rightList);
    if (right && !leaf) rightList.add(n.val);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 2: Three Separate Walks (Optimized)

Collect the left boundary, the leaves and the right boundary one after the other. This is the version implemented in the repository.

```java
public static java.util.List<Integer> three(TreeNode root) {
    java.util.List<Integer> out = new java.util.ArrayList<>(); if (root == null) return out; if (root.left != null || root.right != null) out.add(root.val);
    TreeNode n = root.left; while (n != null) { if (n.left != null || n.right != null) out.add(n.val); n = n.left != null ? n.left : n.right; }
    leaves(root, out); java.util.List<Integer> r = new java.util.ArrayList<>(); n = root.right; while (n != null) { if (n.left != null || n.right != null) r.add(n.val); n = n.right != null ? n.right : n.left; }
    java.util.Collections.reverse(r); out.addAll(r); return out;
}
private static void leaves(TreeNode n, java.util.List<Integer> out) { if (n == null) return; if (n.left == null && n.right == null) { out.add(n.val); return; } leaves(n.left, out); leaves(n.right, out); }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | One DFS with Flags | O(n) | O(h) |
| 2 | Three Separate Walks (Optimized) | O(n) | O(h) |

## Source Code

### BoundaryOfBinaryTree.java

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoundaryOfBinaryTree {

    public static void main(String[] args) {
        Integer[][] inputs = {{1, null, 2, 3, 4}, {1, 2, 3, 4, 5, 6, null, null, null, 7, 8, 9, 10}, {1}, {}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + boundaryOfBinaryTree(root));
            System.out.println();
        }
    }

    public static List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        if (!isLeaf(root)) {
            result.add(root.val);
        }
        TreeNode node = root.left;
        while (node != null) {
            if (!isLeaf(node)) {
                result.add(node.val);
            }
            node = node.left != null ? node.left : node.right;
        }
        addLeaves(root, result);
        List<Integer> right = new ArrayList<>();
        node = root.right;
        while (node != null) {
            if (!isLeaf(node)) {
                right.add(node.val);
            }
            node = node.right != null ? node.right : node.left;
        }
        Collections.reverse(right);
        result.addAll(right);
        return result;
    }

    private static boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }

    private static void addLeaves(TreeNode node, List<Integer> out) {
        if (node == null) {
            return;
        }
        if (isLeaf(node)) {
            out.add(node.val);
            return;
        }
        addLeaves(node.left, out);
        addLeaves(node.right, out);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the boundary is three simple pieces glued together anti-clockwise: down the left edge, along the leaves, and up the right edge. Each piece has a simple rule, and the leaves are excluded from the two edges so that nothing is counted twice.

**Input:** `root = [1, null, 2, 3, 4]`.

| Piece | Nodes |
|-------|-------|
| root | 1 |
| left boundary | (no left child) |
| leaves, left to right | 3, 4 |
| right boundary, non-leaves, then reversed | 2 |

**Output:** `[1, 3, 4, 2]`.
