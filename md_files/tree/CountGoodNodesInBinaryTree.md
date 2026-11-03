# Count Good Nodes in Binary Tree

In a binary tree, a node `X` is good if on the path from the root to `X` there is no node with a value greater than `X`. Given the root of a binary tree, return the number of good nodes.

## Algorithm Strategy (DFS Carrying the Maximum of the Path)

1. Walk the tree depth-first and pass down the largest value seen on the path from the root to the current node (`maxSoFar`).
2. The root is always good; any other node is good when its value is `>= maxSoFar`.
3. Count the node if it is good, then recurse on both children with `max(maxSoFar, node.val)`.
4. The sum of the counts of both subtrees plus the node itself is the answer for the node.

## Example walkthrough

- For `[3, 1, 4, 3, null, 1, 5]`: 3 (root) is good; 1 is not (3 > 1); 3 under 1 is good (3 >= 3); 4 is good; 1 under 4 is not; 5 is good.
- Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every node is visited once.
- **Space Complexity:** `O(h)` — Recursion depth equals the tree height.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force (Maximum of the Whole Path per Node)

Keep the current root-to-node path in a list and, at every node, scan the path for a larger value. Straightforward, but `O(depth)` extra work per node.

```java
public static int goodNodesBrute(TreeNode root) {
    return walk(root, new ArrayList<>());
}

private static int walk(TreeNode node, List<Integer> path) {
    if (node == null) return 0;
    boolean good = true;
    for (int value : path) if (value > node.val) good = false;
    path.add(node.val);
    int count = (good ? 1 : 0) + walk(node.left, path) + walk(node.right, path);
    path.remove(path.size() - 1);
    return count;
}
```

- **Time Complexity:** O(n * h)
- **Space Complexity:** O(h)

### Approach 2: Recursive DFS Carrying the Maximum (Optimized)

Pass the largest value of the path down the recursion; a node is good when it is not smaller. This is the version implemented in the repository.

```java
public static int goodNodes(TreeNode root) {
    return count(root, Integer.MIN_VALUE);
}

private static int count(TreeNode node, int maxSoFar) {
    if (node == null) return 0;
    return (node.val >= maxSoFar ? 1 : 0) + count(node.left, Math.max(maxSoFar, node.val)) + count(node.right, Math.max(maxSoFar, node.val));
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 3: Iterative DFS with a Stack of (node, maximum)

The same traversal with an explicit stack of `(node, maximum so far)` pairs, so deep trees cannot overflow the call stack.

```java
public static int goodNodesStack(TreeNode root) {
    if (root == null) return 0;
    Deque<Object[]> stack = new ArrayDeque<>();
    stack.push(new Object[]{root, Integer.MIN_VALUE});
    int count = 0;
    while (!stack.isEmpty()) {
        Object[] entry = stack.pop();
        TreeNode node = (TreeNode) entry[0];
        int max = (Integer) entry[1];
        if (node.val >= max) count++;
        int newMax = Math.max(max, node.val);
        if (node.right != null) stack.push(new Object[]{node.right, newMax});
        if (node.left != null) stack.push(new Object[]{node.left, newMax});
    }
    return count;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 4: BFS with a Queue of (node, maximum)

A level-order traversal where every queue entry carries the maximum of its root path.

```java
public static int goodNodesBfs(TreeNode root) {
    if (root == null) return 0;
    Queue<Object[]> queue = new LinkedList<>();
    queue.add(new Object[]{root, Integer.MIN_VALUE});
    int count = 0;
    while (!queue.isEmpty()) {
        Object[] entry = queue.poll();
        TreeNode node = (TreeNode) entry[0];
        int max = (Integer) entry[1];
        if (node.val >= max) count++;
        int newMax = Math.max(max, node.val);
        if (node.left != null) queue.add(new Object[]{node.left, newMax});
        if (node.right != null) queue.add(new Object[]{node.right, newMax});
    }
    return count;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(w)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (Maximum of the Whole Path per Node) | O(n * h) | O(h) |
| 2 | Recursive DFS Carrying the Maximum (Optimized) | O(n) | O(h) |
| 3 | Iterative DFS with a Stack of (node, maximum) | O(n) | O(h) |
| 4 | BFS with a Queue of (node, maximum) | O(n) | O(w) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../src/main/java/com/ranga/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### CountGoodNodesInBinaryTree.java

```java
public class CountGoodNodesInBinaryTree {

    public static void main(String[] args) {
        Integer[][] inputs = {{3, 1, 4, 3, null, 1, 5}, {3, 3, null, 4, 2}, {1}, {}, {2, null, 4, 10, 8, null, null, 4}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + goodNodes(root));
            System.out.println();
        }
    }

    public static int goodNodes(TreeNode root) {
        return count(root, Integer.MIN_VALUE);
    }

    private static int count(TreeNode node, int maxSoFar) {
        if (node == null) {
            return 0;
        }
        int good = node.val >= maxSoFar ? 1 : 0;
        int newMax = Math.max(maxSoFar, node.val);
        return good + count(node.left, newMax) + count(node.right, newMax);
    }
}
```
