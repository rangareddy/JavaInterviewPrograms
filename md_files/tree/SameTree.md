# Same Tree

Given the roots of two binary trees `p` and `q`, check whether they are the same: structurally identical and with equal node values.

## Algorithm Strategy (Simultaneous Recursion)

1. If both nodes are `null`, the (empty) subtrees are the same.
2. If only one of them is `null`, or the values differ, the trees are different.
3. Otherwise the trees are the same exactly when the left subtrees are the same and the right subtrees are the same; recurse on both pairs.

## Example walkthrough

- For `[1, 2, 3]` and `[1, 2, 3]`: roots equal (1), left children equal (2), right children equal (3), all leaves have null children on both sides.
- Result: `true`. For `[1, 2]` and `[1, null, 2]` the left children differ, so `false`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each pair of nodes is compared once.
- **Space Complexity:** `O(h)` — Recursion depth equals the tree height.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursive Comparison (Optimized)

Two trees are the same when their roots are equal and both pairs of subtrees are the same. This is the version implemented in the repository.

```java
public static boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null) return true;
    if (p == null || q == null || p.val != q.val) return false;
    return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 2: Iterative with a Queue of Pairs

Compare the trees node pair by node pair with a queue, stopping at the first difference.

```java
public static boolean isSameTreeIterative(TreeNode p, TreeNode q) {
    Deque<TreeNode[]> queue = new ArrayDeque<>();
    queue.add(new TreeNode[]{p, q});
    while (!queue.isEmpty()) {
        TreeNode[] pair = queue.poll();
        if (pair[0] == null && pair[1] == null) continue;
        if (pair[0] == null || pair[1] == null || pair[0].val != pair[1].val) return false;
        queue.add(new TreeNode[]{pair[0].left, pair[1].left});
        queue.add(new TreeNode[]{pair[0].right, pair[1].right});
    }
    return true;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(w)

### Approach 3: Compare the Serializations

Serialize both trees in pre-order with explicit null markers and compare the two strings. Simple but always processes both whole trees.

```java
public static boolean isSameTreeSerialize(TreeNode p, TreeNode q) {
    return serialize(p).equals(serialize(q));
}

private static String serialize(TreeNode node) {
    if (node == null) return "#";
    return node.val + "," + serialize(node.left) + "," + serialize(node.right);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursive Comparison (Optimized) | O(n) | O(h) |
| 2 | Iterative with a Queue of Pairs | O(n) | O(w) |
| 3 | Compare the Serializations | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 1 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../src/main/java/com/ranga/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### SameTree.java

```java
public class SameTree {

    public static void main(String[] args) {
        TreeNode[][] inputs = {
                {TreeNode.of(1, 2, 3), TreeNode.of(1, 2, 3)},
                {TreeNode.of(1, 2), TreeNode.of(1, null, 2)},
                {TreeNode.of(1, 2, 1), TreeNode.of(1, 1, 2)}
        };
        for (TreeNode[] pair : inputs) {
            System.out.println("Input  : p = " + TreeNode.toString(pair[0]) + ", q = " + TreeNode.toString(pair[1]));
            System.out.println("Output : " + isSameTree(pair[0], pair[1]));
            System.out.println();
        }
    }

    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null || p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```
