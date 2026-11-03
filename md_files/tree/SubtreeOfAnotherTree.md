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

### Approach 1: Serialize and Search the String

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

### Approach 2: Tree Hashing

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

### Approach 3: Compare Only Nodes of the Same Height

Compute the height of every node and of `subRoot`. A subtree can only match at a node whose height equals the height of `subRoot`, so the expensive node-by-node comparison runs on far fewer nodes.

```java
public static boolean isSubtreeHeights(TreeNode root, TreeNode subRoot) {
    if (subRoot == null) return true;
    int target = height(subRoot);
    return search(root, subRoot, target) == -2;
}

// returns the height of 'node', or -2 as soon as a matching subtree has been found
private static int search(TreeNode node, TreeNode subRoot, int target) {
    if (node == null) return -1;
    int left = search(node.left, subRoot, target);
    if (left == -2) return -2;
    int right = search(node.right, subRoot, target);
    if (right == -2) return -2;
    int height = Math.max(left, right) + 1;
    if (height == target && same(node, subRoot)) return -2;
    return height;
}

private static int height(TreeNode node) {
    return node == null ? -1 : 1 + Math.max(height(node.left), height(node.right));
}

private static boolean same(TreeNode p, TreeNode q) {
    if (p == null && q == null) return true;
    if (p == null || q == null || p.val != q.val) return false;
    return same(p.left, q.left) && same(p.right, q.right);
}
```

- **Time Complexity:** O(m + n * k) where k is the number of nodes with the right height
- **Space Complexity:** O(h)

### Approach 4: Iterative Traversal of root with an Iterative Same-Tree Check

Visit the nodes of `root` with an explicit stack and, at every node whose value equals the root of `subRoot`, run an iterative same-tree comparison. No recursion anywhere.

```java
public static boolean isSubtreeIterative(TreeNode root, TreeNode subRoot) {
    if (subRoot == null) return true;
    Deque<TreeNode> stack = new ArrayDeque<>();
    if (root != null) stack.push(root);
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        if (node.val == subRoot.val && sameIterative(node, subRoot)) return true;
        if (node.right != null) stack.push(node.right);
        if (node.left != null) stack.push(node.left);
    }
    return false;
}

private static boolean sameIterative(TreeNode p, TreeNode q) {
    Deque<TreeNode[]> stack = new ArrayDeque<>();
    stack.push(new TreeNode[]{p, q});
    while (!stack.isEmpty()) {
        TreeNode[] pair = stack.pop();
        if (pair[0] == null && pair[1] == null) continue;
        if (pair[0] == null || pair[1] == null || pair[0].val != pair[1].val) return false;
        stack.push(new TreeNode[]{pair[0].left, pair[1].left});
        stack.push(new TreeNode[]{pair[0].right, pair[1].right});
    }
    return true;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(h)

### Approach 5: KMP over the Pre-order Serializations

Serialize both trees to lists of tokens in pre-order with null markers and look for the pattern list inside the text list with the Knuth-Morris-Pratt algorithm, which makes the search linear (unlike `String.contains` in the worst case).

```java
public static boolean isSubtreeKmp(TreeNode root, TreeNode subRoot) {
    List<String> text = new ArrayList<>(), pattern = new ArrayList<>();
    flatten(root, text);
    flatten(subRoot, pattern);
    int[] fail = new int[pattern.size()];
    for (int i = 1, k = 0; i < pattern.size(); i++) {
        while (k > 0 && !pattern.get(i).equals(pattern.get(k))) k = fail[k - 1];
        if (pattern.get(i).equals(pattern.get(k))) k++;
        fail[i] = k;
    }
    for (int i = 0, k = 0; i < text.size(); i++) {
        while (k > 0 && !text.get(i).equals(pattern.get(k))) k = fail[k - 1];
        if (text.get(i).equals(pattern.get(k))) k++;
        if (k == pattern.size()) return true;
    }
    return false;
}

private static void flatten(TreeNode node, List<String> tokens) {
    if (node == null) { tokens.add("#"); return; }
    tokens.add("v" + node.val);                 // the prefix keeps a value distinct from the null marker
    flatten(node.left, tokens);
    flatten(node.right, tokens);
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(m + n)

### Approach 6: Merkle Hashing with Verification

Give each subtree a hash computed from its value and the hashes of its children. Only nodes whose hash equals the hash of `subRoot` are compared exactly, so a hash collision can cause extra work but never a wrong answer.

```java
public static boolean isSubtreeMerkle(TreeNode root, TreeNode subRoot) {
    if (subRoot == null) return true;
    long target = hash(subRoot);
    return find(root, subRoot, target);
}

private static boolean find(TreeNode node, TreeNode subRoot, long target) {
    if (node == null) return false;
    if (hash(node) == target && identical(node, subRoot)) return true;
    return find(node.left, subRoot, target) || find(node.right, subRoot, target);
}

private static long hash(TreeNode node) {
    if (node == null) return 7;
    return 31 * (31 * (31L * node.val + 17) + hash(node.left)) + hash(node.right);
}

private static boolean identical(TreeNode p, TreeNode q) {
    if (p == null && q == null) return true;
    if (p == null || q == null || p.val != q.val) return false;
    return identical(p.left, q.left) && identical(p.right, q.right);
}
```

- **Time Complexity:** O(m * n) here because the hash is recomputed per node; O(m + n) when hashes are cached
- **Space Complexity:** O(h)

### Approach 7: Compare Every Node with Same Tree (Optimized)

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

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Serialize and Search the String | O(m * n) with contains(), O(m + n) with KMP | O(m + n) |
| 2 | Tree Hashing | O(m + n) | O(m + n) |
| 3 | Compare Only Nodes of the Same Height | O(m + n * k) where k is the number of nodes with the right height | O(h) |
| 4 | Iterative Traversal of root with an Iterative Same-Tree Check | O(m * n) | O(h) |
| 5 | KMP over the Pre-order Serializations | O(m + n) | O(m + n) |
| 6 | Merkle Hashing with Verification | O(m * n) here because the hash is recomputed per node; O(m + n) when hashes are cached | O(h) |
| 7 | Compare Every Node with Same Tree (Optimized) | O(m * n) | O(h) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
