# Delete Node in a BST

Given the root of a binary search tree and a `key`, delete the node with that key if it exists, keep the tree a valid BST, and return the (possibly new) root.

## Algorithm Strategy (Search, then Replace by the Successor)

1. Search for the key using the BST property: go left if the key is smaller, right if it is larger.
2. If the node is a leaf, return `null`; if it has one child, return that child.
3. If it has two children, find the smallest node of the right subtree (the in-order successor).
4. Copy the successor's value into the node and delete the successor from the right subtree.

## Example walkthrough

- For `[5,3,6,2,4,null,7]` and key 3: node 3 has two children, so it takes 4 (the smallest value in its right subtree) and 4's old position is removed.
- Result: `[5, 4, 6, 2, null, null, 7]`.

## Complexity Analysis

- **Time Complexity:** `O(h)` — One root-to-leaf search plus one successor search.
- **Space Complexity:** `O(h)` — The recursion stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Rebuild from the In-Order Values

Read the in-order values, drop the key and rebuild a balanced BST. Simple, but changes the shape of the whole tree.

```java
public static TreeNode rebuild(TreeNode root, int key) {
    java.util.List<Integer> v = new java.util.ArrayList<>(); walk(root, v); v.remove((Integer) key);
    return build(v, 0, v.size() - 1);
}
private static void walk(TreeNode n, java.util.List<Integer> v) { if (n == null) return; walk(n.left, v); v.add(n.val); walk(n.right, v); }
private static TreeNode build(java.util.List<Integer> v, int lo, int hi) { if (lo > hi) return null; int m = (lo + hi) / 2; TreeNode n = new TreeNode(v.get(m)); n.left = build(v, lo, m - 1); n.right = build(v, m + 1, hi); return n; }
public static java.util.List<Integer> values(TreeNode r) { java.util.List<Integer> v = new java.util.ArrayList<>(); walk(r, v); return v; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Replace with the In-Order Successor (Optimized)

Splice out the node, replacing it by the smallest value of the right subtree. This is the version implemented in the repository.

```java
public static TreeNode successor(TreeNode root, int key) {
    if (root == null) return null;
    if (key < root.val) root.left = successor(root.left, key);
    else if (key > root.val) root.right = successor(root.right, key);
    else {
        if (root.left == null) return root.right; if (root.right == null) return root.left;
        TreeNode s = root.right; while (s.left != null) s = s.left;
        root.val = s.val; root.right = successor(root.right, s.val);
    }
    return root;
}
```

- **Time Complexity:** O(h)
- **Space Complexity:** O(h)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Rebuild from the In-Order Values | O(n) | O(n) |
| 2 | Replace with the In-Order Successor (Optimized) | O(h) | O(h) |

## Source Code

### DeleteNodeInABST.java

```java
public class DeleteNodeInABST {

    public static void main(String[] args) {
        Object[][] inputs = {
            {new Integer[]{5, 3, 6, 2, 4, null, 7}, 3},
            {new Integer[]{5, 3, 6, 2, 4, null, 7}, 0},
            {new Integer[]{5, 3, 6, 2, 4, null, 7}, 5},
            {new Integer[]{}, 0}
        };
        for (Object[] in : inputs) {
            TreeNode root = TreeNode.of((Integer[]) in[0]);
            int key = (Integer) in[1];
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", key = " + key);
            System.out.println("Output : " + TreeNode.toString(deleteNode(root, key)));
            System.out.println();
        }
    }

    public static TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            TreeNode successor = root.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            root.val = successor.val;
            root.right = deleteNode(root.right, successor.val);
        }
        return root;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: removing a node with two children would leave a gap. The smallest value of the right subtree is the next larger value in sorted order, so it can take the removed node's place without breaking the BST rule.

**Input:** `root = [5, 3, 6, 2, 4, null, 7]`, `key = 3`.

| Step | Action |
|------|--------|
| 1 | 3 < 5, go left |
| 2 | found 3, which has two children |
| 3 | successor = smallest node of the right subtree = 4 |
| 4 | copy 4 into the node, then delete 4 from the right subtree |

**Output:** `[5, 4, 6, 2, null, null, 7]`.
