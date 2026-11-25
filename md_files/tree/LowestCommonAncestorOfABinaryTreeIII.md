# Lowest Common Ancestor of a Binary Tree III

Given two nodes `p` and `q` of a binary tree in which every node has a reference to its parent, return their lowest common ancestor. The nodes are guaranteed to exist in the same tree. A node can be its own ancestor.

## Algorithm Strategy (Two Pointers that Swap Routes)

1. Start `a = p` and `b = q`.
2. Move each pointer up to its parent each step; when a pointer falls off the root, restart it at the other node.
3. Both pointers then travel `depth(p) + depth(q)` steps in total and meet exactly at the lowest common ancestor.
4. This is the same idea as finding the intersection of two linked lists.

## Example walkthrough

- In the tree `[3,5,1,6,2,0,8,null,null,7,4]` the nodes 5 and 1 have the root 3 as their lowest common ancestor.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(h)` — Each pointer walks at most the two depths added together.
- **Space Complexity:** `O(1)` — Two pointers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Set of Ancestors

Store all ancestors of `p` in a set, then climb from `q` until a node is in the set.

```java
public static LowestCommonAncestorOfABinaryTreeIII.Node viaSet(LowestCommonAncestorOfABinaryTreeIII.Node p, LowestCommonAncestorOfABinaryTreeIII.Node q) {
    java.util.Set<LowestCommonAncestorOfABinaryTreeIII.Node> seen = new java.util.HashSet<>();
    for (LowestCommonAncestorOfABinaryTreeIII.Node x = p; x != null; x = x.parent) seen.add(x);
    for (LowestCommonAncestorOfABinaryTreeIII.Node x = q; x != null; x = x.parent) if (seen.contains(x)) return x;
    return null;
}
```

- **Time Complexity:** O(h)
- **Space Complexity:** O(h)

### Approach 2: Two Pointers that Swap Routes (Optimized)

Each pointer switches to the other node after reaching the root, so both walk the same total distance. This is the version implemented in the repository.

```java
public static LowestCommonAncestorOfABinaryTreeIII.Node twoPointers(LowestCommonAncestorOfABinaryTreeIII.Node p, LowestCommonAncestorOfABinaryTreeIII.Node q) {
    LowestCommonAncestorOfABinaryTreeIII.Node a = p, b = q;
    while (a != b) { a = a == null ? q : a.parent; b = b == null ? p : b.parent; }
    return a;
}
```

- **Time Complexity:** O(h)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Set of Ancestors | O(h) | O(h) |
| 2 | Two Pointers that Swap Routes (Optimized) | O(h) | O(1) |

## Source Code

### LowestCommonAncestorOfABinaryTreeIII.java

```java
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class LowestCommonAncestorOfABinaryTreeIII {

    public static void main(String[] args) {
        Integer[] values = {3, 5, 1, 6, 2, 0, 8, null, null, 7, 4};
        int[][] pairs = {{5, 1}, {5, 4}, {7, 4}, {6, 6}};
        TreeNode root = TreeNode.of(values);
        Map<Integer, Node> nodes = withParents(root);
        for (int[] pair : pairs) {
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", p = " + pair[0] + ", q = " + pair[1]);
            System.out.println("Output : " + lowestCommonAncestor(nodes.get(pair[0]), nodes.get(pair[1])).val);
            System.out.println();
        }
    }

    /** A tree node that also knows its parent. */
    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int val) {
            this.val = val;
        }
    }

    public static Node lowestCommonAncestor(Node p, Node q) {
        Node a = p;
        Node b = q;
        while (a != b) {
            a = a == null ? q : a.parent;
            b = b == null ? p : b.parent;
        }
        return a;
    }

    /** Copies a plain tree into nodes with parent links and returns them by value. */
    public static Map<Integer, Node> withParents(TreeNode root) {
        Map<Integer, Node> byValue = new HashMap<>();
        if (root == null) {
            return byValue;
        }
        Node top = new Node(root.val);
        byValue.put(top.val, top);
        Queue<TreeNode> source = new ArrayDeque<>();
        Queue<Node> copy = new ArrayDeque<>();
        source.add(root);
        copy.add(top);
        while (!source.isEmpty()) {
            TreeNode s = source.poll();
            Node c = copy.poll();
            if (s.left != null) {
                c.left = new Node(s.left.val);
                c.left.parent = c;
                byValue.put(c.left.val, c.left);
                source.add(s.left);
                copy.add(c.left);
            }
            if (s.right != null) {
                c.right = new Node(s.right.val);
                c.right.parent = c;
                byValue.put(c.right.val, c.right);
                source.add(s.right);
                copy.add(c.right);
            }
        }
        return byValue;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: following parent links turns each node into a linked list that ends at the root, and the lowest common ancestor is where the two lists intersect. Switching each pointer to the other start when it runs out equalises the path lengths, as in "Intersection of Two Linked Lists".

**Input:** `p = 5`, `q = 1` in `[3, 5, 1, 6, 2, 0, 8, null, null, 7, 4]`.

| Step | `a` | `b` |
|------|-----|-----|
| 1 | 5 | 1 |
| 2 | 3 (parent of 5) | 3 (parent of 1) |

`a == b == 3`.

**Output:** `3`.
