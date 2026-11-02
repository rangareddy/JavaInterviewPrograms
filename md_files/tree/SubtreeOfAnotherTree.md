# Subtree of Another Tree

Given the roots of two binary trees `root` and `subRoot`, return `true` if some subtree of `root` (a node together with all its descendants) has exactly the structure and values of `subRoot`.

## Algorithm Strategy (Compare Every Node with Same Tree)

1. An empty `subRoot` is a subtree of any tree; an empty `root` cannot contain a non-empty `subRoot`.
2. If the tree rooted at the current node is identical to `subRoot` (checked with the Same Tree routine), return `true`.
3. Otherwise look for `subRoot` in the left subtree or in the right subtree.
4. Return `false` when all nodes were tried without a match.

## Example walkthrough

- For `root = [3, 4, 5, 1, 2]` and `subRoot = [4, 1, 2]`: the root 3 does not match 4; its left child 4 has children 1 and 2 and matches exactly.
- Result: `true`. If node 4 had an extra child, the structure would differ and the result would be `false`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — In the worst case each of the `m` nodes of root starts a comparison of up to `n` nodes.
- **Space Complexity:** `O(h)` — Recursion depth.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Compare Every Node with Same Tree (Optimized)

At every node of `root` test whether the tree rooted there is identical to `subRoot`. This is the version implemented in the repository.

```java
public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
    if (subRoot == null) return true;
    if (root == null) return false;
    return same(root, subRoot) || isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
}

private static boolean same(TreeNode p, TreeNode q) {
    if (p == null && q == null) return true;
    if (p == null || q == null || p.val != q.val) return false;
    return same(p.left, q.left) && same(p.right, q.right);
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(h)

### Approach 2: Serialize and Search the String

Serialize both trees in pre-order with null markers (each value wrapped in separators, so 1 does not match 11) and check whether the serialization of `subRoot` is a substring of the one of `root`. With KMP the substring search is linear.

```java
public static boolean isSubtreeSerialize(TreeNode root, TreeNode subRoot) {
    return serialize(root).contains(serialize(subRoot));
}

private static String serialize(TreeNode node) {
    if (node == null) return ",#";
    return "," + node.val + serialize(node.left) + serialize(node.right);
}
```

- **Time Complexity:** O(m * n) with contains(), O(m + n) with KMP
- **Space Complexity:** O(m + n)

### Approach 3: Tree Hashing

Give every subtree a canonical id from the triple `(value, left id, right id)` using a map, then `subRoot` is a subtree if its id appears among the ids of the nodes of `root`. Linear time, because the id assignment never compares whole subtrees.

```java
public static boolean isSubtreeHash(TreeNode root, TreeNode subRoot) {
    if (subRoot == null) return true;
    Map<String, Integer> ids = new HashMap<>();
    Set<Integer> rootIds = new HashSet<>();
    collect(root, ids, rootIds);
    return rootIds.contains(collect(subRoot, ids, new HashSet<>()));
}

private static int collect(TreeNode node, Map<String, Integer> ids, Set<Integer> seen) {
    if (node == null) return 0;
    String key = node.val + "," + collect(node.left, ids, seen) + "," + collect(node.right, ids, seen);
    int id = ids.computeIfAbsent(key, k -> ids.size() + 1);
    seen.add(id);
    return id;
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(m + n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Compare Every Node with Same Tree (Optimized) | O(m * n) | O(h) |
| 2 | Serialize and Search the String | O(m * n) with contains(), O(m + n) with KMP | O(m + n) |
| 3 | Tree Hashing | O(m + n) | O(m + n) |

### Optimized Solution

The optimized solution is Approach 1 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../src/main/java/com/ranga/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### SubtreeOfAnotherTree.java

```java
public class SubtreeOfAnotherTree {

    public static void main(String[] args) {
        TreeNode[][] inputs = {
                {TreeNode.of(3, 4, 5, 1, 2), TreeNode.of(4, 1, 2)},
                {TreeNode.of(3, 4, 5, 1, 2, null, null, null, null, 0), TreeNode.of(4, 1, 2)}
        };
        for (TreeNode[] pair : inputs) {
            System.out.println("Input  : root = " + TreeNode.toString(pair[0]) + ", subRoot = " + TreeNode.toString(pair[1]));
            System.out.println("Output : " + isSubtree(pair[0], pair[1]));
            System.out.println();
        }
    }

    public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (subRoot == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        return isSameTree(root, subRoot) || isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private static boolean isSameTree(TreeNode p, TreeNode q) {
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
