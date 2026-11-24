# Split BST

Given the root of a binary search tree and an integer `target`, split the tree into two subtrees: one holds every node with a value less than or equal to `target`, the other holds every node with a larger value. Keep the original parent-child structure where possible, and return the two roots as an array.

## Algorithm Strategy (Recursive Split)

1. If the node is `null`, both parts are `null`.
2. If `root.val <= target`, the root and its whole left subtree belong to the smaller part. Split the right subtree; attach its smaller part as the new right child of `root`, and return `[root, larger part]`.
3. Otherwise the root and its right subtree belong to the larger part. Split the left subtree; attach its larger part as the new left child of `root`, and return `[smaller part, root]`.
4. Only one path from the root is followed, so the work is proportional to the height.

## Example walkthrough

- For `[4,2,6,1,3,5,7]` and `target = 2`: the root 4 is larger, so split its left subtree `[2,1,3]`; node 2 stays small and its right child 3 moves to the larger side.
- Result: `[[2,1],[4,3,6,null,null,5,7]]`.

## Complexity Analysis

- **Time Complexity:** `O(h)` — One root-to-leaf path is followed.
- **Space Complexity:** `O(h)` — Recursion depth equals the tree height.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Collect and Rebuild

Read all values with an in-order walk and build two new BSTs from them. Simple, but ignores the existing structure.

```java
public static TreeNode[] rebuild(TreeNode root, int target) {
    java.util.List<Integer> small = new java.util.ArrayList<>(), big = new java.util.ArrayList<>();
    inorder(root, target, small, big);
    return new TreeNode[]{build(small, 0, small.size() - 1), build(big, 0, big.size() - 1)};
}
private static void inorder(TreeNode n, int t, java.util.List<Integer> s, java.util.List<Integer> b) {
    if (n == null) return; inorder(n.left, t, s, b); (n.val <= t ? s : b).add(n.val); inorder(n.right, t, s, b);
}
private static TreeNode build(java.util.List<Integer> v, int lo, int hi) {
    if (lo > hi) return null; int mid = (lo + hi) / 2; TreeNode n = new TreeNode(v.get(mid));
    n.left = build(v, lo, mid - 1); n.right = build(v, mid + 1, hi); return n;
}
public static java.util.List<Integer> values(TreeNode r) { java.util.List<Integer> l = new java.util.ArrayList<>(); inorder2(r, l); return l; }
private static void inorder2(TreeNode n, java.util.List<Integer> l) { if (n == null) return; inorder2(n.left, l); l.add(n.val); inorder2(n.right, l); }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Recursive Split (Optimized)

Follow one root-to-leaf path and re-link only the nodes on it. This is the version implemented in the repository.

```java
public static TreeNode[] recursiveSplit(TreeNode root, int target) {
    if (root == null) return new TreeNode[]{null, null};
    if (root.val <= target) {
        TreeNode[] r = recursiveSplit(root.right, target);
        root.right = r[0];
        return new TreeNode[]{root, r[1]};
    }
    TreeNode[] l = recursiveSplit(root.left, target);
    root.left = l[1];
    return new TreeNode[]{l[0], root};
}
```

- **Time Complexity:** O(h)
- **Space Complexity:** O(h)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Collect and Rebuild | O(n) | O(n) |
| 2 | Recursive Split (Optimized) | O(h) | O(h) |

## Source Code

### SplitBST.java

```java
public class SplitBST {

    public static void main(String[] args) {
        Object[][] inputs = {
            {new Integer[]{4, 2, 6, 1, 3, 5, 7}, 2},
            {new Integer[]{1}, 1},
            {new Integer[]{4, 2, 6, 1, 3, 5, 7}, 10},
            {new Integer[]{}, 3}
        };
        for (Object[] in : inputs) {
            TreeNode root = TreeNode.of((Integer[]) in[0]);
            int target = (Integer) in[1];
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", target = " + target);
            TreeNode[] parts = splitBST(root, target);
            System.out.println("Output : [" + TreeNode.toString(parts[0]) + ", " + TreeNode.toString(parts[1]) + "]");
            System.out.println();
        }
    }

    public static TreeNode[] splitBST(TreeNode root, int target) {
        if (root == null) {
            return new TreeNode[]{null, null};
        }
        if (root.val <= target) {
            TreeNode[] right = splitBST(root.right, target);
            root.right = right[0];
            return new TreeNode[]{root, right[1]};
        }
        TreeNode[] left = splitBST(root.left, target);
        root.left = left[1];
        return new TreeNode[]{left[0], root};
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: in a BST, comparing the root with `target` tells which whole side can stay together. If the root is small enough, its left subtree stays with it and only the right subtree needs splitting; the mirror applies otherwise.

**Input:** `root = [4, 2, 6, 1, 3, 5, 7]`, `target = 2`.

| Call | Node | Decision | Returns |
|------|------|----------|---------|
| 1 | 4 | 4 > 2, root goes to the larger part, split the left subtree | |
| 2 | 2 | 2 <= 2, root stays small, split the right subtree | |
| 3 | 3 | 3 > 2, goes to the larger part, split its left (null) | `[null, 3]` |
| back in 2 | 2 | `2.right = null` | `[2, 3]` |
| back in 1 | 4 | `4.left = 3` | `[2, 4]` |

**Output:** `[[2, 1], [4, 3, 6, null, null, 5, 7]]`.
