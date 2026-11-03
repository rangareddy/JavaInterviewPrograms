# Binary Tree Maximum Path Sum

A path in a binary tree is a sequence of nodes where each pair of adjacent nodes is connected by an edge and no node appears twice; it does not have to pass through the root. Return the maximum path sum of any non-empty path.

## Algorithm Strategy (Post-order DFS with a Global Maximum)

1. For every node, compute the best "downward" gain it can offer to its parent: `node.val + max(0, leftGain, rightGain)`. Negative gains are ignored by taking `max(0, ...)`.
2. A path that bends at this node uses both children: its sum is `node.val + max(0, leftGain) + max(0, rightGain)`. Compare it with the global best.
3. Return only the one-sided gain to the parent, because a path cannot split into two branches above the node.
4. The global best after visiting the whole tree is the answer.

## Example walkthrough

- For `[-10, 9, 20, null, null, 15, 7]`: node 20 offers gain `20 + 15 = 35` and its bent path is `15 + 20 + 7 = 42`.
- At the root the bent path is `-10 + 9 + 35 = 34`, smaller than 42. Result: `42`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every node is visited once.
- **Space Complexity:** `O(h)` — Recursion depth equals the tree height.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force (Best Path Through Every Node)

For every node compute the best downward path in each subtree separately, so the same subtrees are recomputed many times.

```java
public static int maxPathSumBrute(TreeNode root) {
    if (root == null) return Integer.MIN_VALUE;
    int through = root.val + Math.max(0, down(root.left)) + Math.max(0, down(root.right));
    return Math.max(through, Math.max(maxPathSumBrute(root.left), maxPathSumBrute(root.right)));
}

private static int down(TreeNode node) {
    if (node == null) return 0;
    return node.val + Math.max(0, Math.max(down(node.left), down(node.right)));
}
```

- **Time Complexity:** O(n^2) for a skewed tree (O(n log n) balanced)
- **Space Complexity:** O(h)

### Approach 2: Post-order DFS Returning Both Values (No Global State)

Return a pair `{best path inside the subtree, best downward gain}` from each call, so the method needs no static field and is thread-safe.

```java
public static int maxPathSumPair(TreeNode root) {
    return pair(root)[0];
}

// returns {bestPathInside, bestDownwardGain}
private static int[] pair(TreeNode node) {
    if (node == null) return new int[]{Integer.MIN_VALUE, 0};
    int[] left = pair(node.left), right = pair(node.right);
    int leftGain = Math.max(0, left[1]), rightGain = Math.max(0, right[1]);
    int through = node.val + leftGain + rightGain;
    return new int[]{Math.max(through, Math.max(left[0], right[0])), node.val + Math.max(leftGain, rightGain)};
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 3: Brute Force with a Memoized Downward Gain

The brute-force idea (compute the best bent path through every node) with the downward gains cached in a map, so each subtree is evaluated once instead of repeatedly.

```java
public static int maxPathSumMemo(TreeNode root) {
    Map<TreeNode, Integer> gains = new IdentityHashMap<>();
    int[] best = {Integer.MIN_VALUE};
    visit(root, gains, best);
    return best[0];
}

private static void visit(TreeNode node, Map<TreeNode, Integer> gains, int[] best) {
    if (node == null) return;
    visit(node.left, gains, best);
    visit(node.right, gains, best);
    best[0] = Math.max(best[0], node.val + Math.max(0, gain(node.left, gains)) + Math.max(0, gain(node.right, gains)));
}

private static int gain(TreeNode node, Map<TreeNode, Integer> gains) {
    if (node == null) return 0;
    Integer cached = gains.get(node);
    if (cached != null) return cached;
    int value = node.val + Math.max(0, Math.max(gain(node.left, gains), gain(node.right, gains)));
    gains.put(node, value);
    return value;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Iterative Post-order with an IdentityHashMap

Visit the nodes in post-order with an explicit stack (children before parents) and store every node's downward gain in a map. Avoids recursion, so very deep trees cannot overflow the stack.

```java
public static int maxPathSumIterative(TreeNode root) {
    Map<TreeNode, Integer> gain = new IdentityHashMap<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root, lastVisited = null;
    int best = Integer.MIN_VALUE;
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) { stack.push(curr); curr = curr.left; }
        TreeNode top = stack.peek();
        if (top.right != null && lastVisited != top.right) {
            curr = top.right;
        } else {
            stack.pop();
            int left = Math.max(0, top.left == null ? 0 : gain.get(top.left));
            int right = Math.max(0, top.right == null ? 0 : gain.get(top.right));
            best = Math.max(best, top.val + left + right);
            gain.put(top, top.val + Math.max(left, right));
            lastVisited = top;
        }
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 5: Process Nodes in Reverse Level Order

Collect the nodes level by level with a BFS and then handle them in reverse order, so that every node is processed after both of its children. Each node reads its children's gains from a map and updates the answer. No recursion at all.

```java
public static int maxPathSumReverseLevels(TreeNode root) {
    List<TreeNode> order = new ArrayList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        order.add(node);
        if (node.left != null) queue.add(node.left);
        if (node.right != null) queue.add(node.right);
    }
    Map<TreeNode, Integer> gain = new IdentityHashMap<>();
    int best = Integer.MIN_VALUE;
    for (int i = order.size() - 1; i >= 0; i--) {
        TreeNode node = order.get(i);
        int left = Math.max(0, node.left == null ? 0 : gain.get(node.left));
        int right = Math.max(0, node.right == null ? 0 : gain.get(node.right));
        best = Math.max(best, node.val + left + right);
        gain.put(node, node.val + Math.max(left, right));
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 6: Recursion with a Single-Element Holder

The global-maximum solution without a static field: the running maximum lives in a one-element array that is passed down, so the method is re-entrant and thread-safe.

```java
public static int maxPathSumHolder(TreeNode root) {
    int[] best = {Integer.MIN_VALUE};
    gainOf(root, best);
    return best[0];
}

private static int gainOf(TreeNode node, int[] best) {
    if (node == null) return 0;
    int left = Math.max(0, gainOf(node.left, best)), right = Math.max(0, gainOf(node.right, best));
    best[0] = Math.max(best[0], node.val + left + right);
    return node.val + Math.max(left, right);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Approach 7: Post-order DFS with a Global Maximum (Optimized)

Compute each node's one-sided gain once, bottom-up, and update the best bent path at the same time. This is the version implemented in the repository.

```java
private static int best;

public static int maxPathSum(TreeNode root) {
    best = Integer.MIN_VALUE;
    gain(root);
    return best;
}

private static int gain(TreeNode node) {
    if (node == null) return 0;
    int leftGain = Math.max(0, gain(node.left)), rightGain = Math.max(0, gain(node.right));
    best = Math.max(best, node.val + leftGain + rightGain);
    return node.val + Math.max(leftGain, rightGain);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(h)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (Best Path Through Every Node) | O(n^2) for a skewed tree (O(n log n) balanced) | O(h) |
| 2 | Post-order DFS Returning Both Values (No Global State) | O(n) | O(h) |
| 3 | Brute Force with a Memoized Downward Gain | O(n) | O(n) |
| 4 | Iterative Post-order with an IdentityHashMap | O(n) | O(n) |
| 5 | Process Nodes in Reverse Level Order | O(n) | O(n) |
| 6 | Recursion with a Single-Element Holder | O(n) | O(h) |
| 7 | Post-order DFS with a Global Maximum (Optimized) | O(n) | O(h) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../src/main/java/com/ranga/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### BinaryTreeMaximumPathSum.java

```java
public class BinaryTreeMaximumPathSum {

    private static int best;

    public static void main(String[] args) {
        TreeNode[] inputs = {TreeNode.of(1, 2, 3), TreeNode.of(-10, 9, 20, null, null, 15, 7), TreeNode.of(-3)};
        for (TreeNode root : inputs) {
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + maxPathSum(root));
            System.out.println();
        }
    }

    public static int maxPathSum(TreeNode root) {
        best = Integer.MIN_VALUE;
        gain(root);
        return best;
    }

    // Returns the best sum of a path that starts at the node and goes down one side only
    private static int gain(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftGain = Math.max(0, gain(node.left));
        int rightGain = Math.max(0, gain(node.right));

        best = Math.max(best, node.val + leftGain + rightGain);     // path that bends at this node
        return node.val + Math.max(leftGain, rightGain);
    }
}
```
