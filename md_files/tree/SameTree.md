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

### Approach 1: Iterative with a Queue of Pairs

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

### Approach 2: Compare the Serializations

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

### Approach 3: Iterative DFS with a Stack of Pairs

Compare the trees node pair by node pair with an explicit stack. Same as the queue version but depth-first.

```java
public static boolean isSameTreeStack(TreeNode p, TreeNode q) {
    Deque<TreeNode[]> stack = new ArrayDeque<>();
    stack.push(new TreeNode[]{p, q});
    while (!stack.isEmpty()) {
        TreeNode[] pair = stack.pop();
        if (pair[0] == null && pair[1] == null) continue;
        if (pair[0] == null || pair[1] == null || pair[0].val != pair[1].val) return false;
        stack.push(new TreeNode[]{pair[0].right, pair[1].right});
        stack.push(new TreeNode[]{pair[0].left, pair[1].left});
    }
    return true;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 4: Compare Pre-order and In-order Lists

A binary tree is determined by its pre-order and in-order traversals (when null children are recorded too, pre-order alone suffices). Compare the two traversal lists including the `null` markers.

```java
public static boolean isSameTreeTraversals(TreeNode p, TreeNode q) {
    List<Integer> preP = new ArrayList<>(), preQ = new ArrayList<>(), inP = new ArrayList<>(), inQ = new ArrayList<>();
    traverse(p, preP, inP);
    traverse(q, preQ, inQ);
    return preP.equals(preQ) && inP.equals(inQ);
}

private static void traverse(TreeNode node, List<Integer> pre, List<Integer> in) {
    if (node == null) { pre.add(null); in.add(null); return; }
    pre.add(node.val);
    traverse(node.left, pre, in);
    in.add(node.val);
    traverse(node.right, pre, in);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 5: Compare Level-Order Lists with Null Markers

Produce the breadth-first listing of each tree (with `null` for missing children) and compare the two lists.

```java
public static boolean isSameTreeLevels(TreeNode p, TreeNode q) {
    return levels(p).equals(levels(q));
}

private static List<Integer> levels(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        if (node == null) { result.add(null); continue; }
        result.add(node.val);
        queue.add(node.left);
        queue.add(node.right);
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 6: Lazy Parallel Iterators

Create a pre-order iterator over each tree (an explicit stack that produces one node at a time) and advance both in lock step. It stops at the very first difference without building any list or visiting the rest of the trees.

```java
public static boolean isSameTreeIterators(TreeNode p, TreeNode q) {
    Iterator<Integer> a = preorder(p), b = preorder(q);
    while (a.hasNext() && b.hasNext()) {
        if (!Objects.equals(a.next(), b.next())) return false;
    }
    return !a.hasNext() && !b.hasNext();
}

// pre-order values with null for every missing child
private static Iterator<Integer> preorder(TreeNode root) {
    Deque<TreeNode> pending = new LinkedList<>();          // LinkedList (unlike ArrayDeque) accepts null = empty child
    pending.push(root);
    return new Iterator<Integer>() {
        public boolean hasNext() { return !pending.isEmpty(); }

        public Integer next() {
            TreeNode node = pending.pop();
            if (node == null) return null;
            pending.push(node.right);
            pending.push(node.left);
            return node.val;
        }
    };
}
```

- **Time Complexity:** O(n) worst case, stops at the first difference
- **Space Complexity:** O(h)

### Approach 7: Recursive Comparison (Optimized)

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

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Iterative with a Queue of Pairs | O(n) | O(w) |
| 2 | Compare the Serializations | O(n) | O(n) |
| 3 | Iterative DFS with a Stack of Pairs | O(n) | O(h) |
| 4 | Compare Pre-order and In-order Lists | O(n) | O(n) |
| 5 | Compare Level-Order Lists with Null Markers | O(n) | O(n) |
| 6 | Lazy Parallel Iterators | O(n) worst case, stops at the first difference | O(h) |
| 7 | Recursive Comparison (Optimized) | O(n) | O(h) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
