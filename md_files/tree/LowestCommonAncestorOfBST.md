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

### Approach 3: Parent Map and Ancestor Set

Walk the tree once to record the parent of every node, collect all ancestors of `p` in a set, and then climb from `q` until the first node that is in that set. Works for any binary tree, not only BSTs.

```java
public static TreeNode lcaParents(TreeNode root, TreeNode p, TreeNode q) {
    Map<Integer, TreeNode> parent = new HashMap<>();
    Map<Integer, TreeNode> byValue = new HashMap<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(root);
    parent.put(root.val, null);
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        byValue.put(node.val, node);
        for (TreeNode child : new TreeNode[]{node.left, node.right}) {
            if (child != null) { parent.put(child.val, node); stack.push(child); }
        }
    }
    Set<Integer> ancestors = new HashSet<>();
    for (TreeNode node = byValue.get(p.val); node != null; node = parent.get(node.val)) ancestors.add(node.val);
    for (TreeNode node = byValue.get(q.val); node != null; node = parent.get(node.val)) {
        if (ancestors.contains(node.val)) return node;
    }
    return null;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: General Binary-Tree Recursion (Post-order)

The solution that ignores the BST ordering: return a node when it is `p` or `q`, and the lowest common ancestor is the first node that receives a hit from both subtrees (or is a hit itself with a hit below).

```java
public static TreeNode lcaGeneral(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root.val == p.val || root.val == q.val) return root;
    TreeNode left = lcaGeneral(root.left, p, q), right = lcaGeneral(root.right, p, q);
    if (left != null && right != null) return root;
    return left != null ? left : right;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 5: Climb Using Depths

Record the depth and parent of every node. Move the deeper of the two nodes up until both are at the same depth, then move both up together until they meet.

```java
public static TreeNode lcaDepths(TreeNode root, TreeNode p, TreeNode q) {
    Map<Integer, TreeNode> parent = new HashMap<>(), byValue = new HashMap<>();
    Map<Integer, Integer> depth = new HashMap<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(root);
    depth.put(root.val, 0);
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        byValue.put(node.val, node);
        for (TreeNode child : new TreeNode[]{node.left, node.right}) {
            if (child != null) { parent.put(child.val, node); depth.put(child.val, depth.get(node.val) + 1); stack.push(child); }
        }
    }
    TreeNode a = byValue.get(p.val), b = byValue.get(q.val);
    while (depth.get(a.val) > depth.get(b.val)) a = parent.get(a.val);
    while (depth.get(b.val) > depth.get(a.val)) b = parent.get(b.val);
    while (a != b) { a = parent.get(a.val); b = parent.get(b.val); }
    return a;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 6: Euler Tour and Range-Minimum Query

Write down the nodes of an Euler tour (every node each time it is entered or returned to) together with their depths. The LCA of two nodes is the shallowest node between their first occurrences in the tour. With a sparse table the minimum is found in `O(1)` after `O(n log n)` preprocessing, which pays off for many queries.

```java
public static TreeNode lcaEuler(TreeNode root, TreeNode p, TreeNode q) {
    List<TreeNode> tour = new ArrayList<>();
    List<Integer> depths = new ArrayList<>();
    Map<Integer, Integer> first = new HashMap<>();
    walk(root, 0, tour, depths, first);
    int left = Math.min(first.get(p.val), first.get(q.val)), right = Math.max(first.get(p.val), first.get(q.val));
    int m = tour.size(), levels = 1;
    while ((1 << levels) <= m) levels++;
    int[][] sparse = new int[levels][m];                       // index of the shallowest tour entry
    for (int i = 0; i < m; i++) sparse[0][i] = i;
    for (int j = 1; j < levels; j++) {
        for (int i = 0; i + (1 << j) <= m; i++) {
            int a = sparse[j - 1][i], b = sparse[j - 1][i + (1 << (j - 1))];
            sparse[j][i] = depths.get(a) <= depths.get(b) ? a : b;
        }
    }
    int j = 31 - Integer.numberOfLeadingZeros(right - left + 1);
    int a = sparse[j][left], b = sparse[j][right - (1 << j) + 1];
    return tour.get(depths.get(a) <= depths.get(b) ? a : b);
}

private static void walk(TreeNode node, int depth, List<TreeNode> tour, List<Integer> depths, Map<Integer, Integer> first) {
    first.putIfAbsent(node.val, tour.size());
    tour.add(node); depths.add(depth);
    for (TreeNode child : new TreeNode[]{node.left, node.right}) {
        if (child != null) { walk(child, depth + 1, tour, depths, first); tour.add(node); depths.add(depth); }
    }
}
```

- **Time Complexity:** O(n log n) preprocessing, O(1) per query
- **Space Complexity:** O(n log n)

### Approach 7: Iterative Walk Down (Optimized)

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
| 3 | Parent Map and Ancestor Set | O(n) | O(n) |
| 4 | General Binary-Tree Recursion (Post-order) | O(n) | O(h) |
| 5 | Climb Using Depths | O(n) | O(n) |
| 6 | Euler Tour and Range-Minimum Query | O(n log n) preprocessing, O(1) per query | O(n log n) |
| 7 | Iterative Walk Down (Optimized) | O(h) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
