# Kth Smallest Element in a BST

Given the root of a binary search tree and an integer `k`, return the `k`-th smallest value (1-indexed) of all node values.

## Algorithm Strategy (Iterative In-order Traversal)

1. An in-order traversal (left, node, right) of a BST visits the values in ascending order.
2. Traverse iteratively with a stack: go as far left as possible pushing nodes, then pop a node, count it, and continue with its right subtree.
3. Decrement `k` for every popped node; when it reaches 0, the popped node is the answer.
4. Stopping early means only the first `k` nodes (plus the path to them) are visited.

## Example walkthrough

- For `[3, 1, 4, null, 2]` and `k = 1`: go left to 1 (no left child), pop it: the first value is 1.
- For `[5, 3, 6, 2, 4, null, null, 1]` and `k = 3`: the order is 1, 2, 3, so the answer is 3.

## Complexity Analysis

- **Time Complexity:** `O(h + k)` — Walk down the left spine, then visit `k` nodes.
- **Space Complexity:** `O(h)` — The stack holds one root-to-node path.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Collect In-order, Then Index

Perform a full recursive in-order traversal into a list and return element `k - 1`. Always visits every node.

```java
public static int kthSmallestList(TreeNode root, int k) {
    List<Integer> values = new ArrayList<>();
    inOrder(root, values);
    return values.get(k - 1);
}

private static void inOrder(TreeNode node, List<Integer> values) {
    if (node == null) return;
    inOrder(node.left, values);
    values.add(node.val);
    inOrder(node.right, values);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Iterative In-order with Early Stop (Optimized)

Walk the in-order sequence with a stack and stop at the `k`-th node, visiting only `O(h + k)` nodes. This is the version implemented in the repository.

```java
public static int kthSmallest(TreeNode root, int k) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) { stack.push(curr); curr = curr.left; }
        curr = stack.pop();
        if (--k == 0) return curr.val;
        curr = curr.right;
    }
    throw new IllegalArgumentException("k is larger than the number of nodes.");
}
```

- **Time Complexity:** O(h + k)
- **Space Complexity:** O(h)

### Approach 3: Augmented Tree (Subtree Sizes)

If the tree is queried many times, store the size of every subtree. Then the `k`-th smallest is found by descending: go left if `k` is at most the left size, return the node if `k` equals left size plus one, otherwise go right.

```java
public static int kthSmallestSizes(TreeNode root, int k) {
    Map<TreeNode, Integer> size = new HashMap<>();
    computeSizes(root, size);              // done once, reused for every query
    TreeNode curr = root;
    while (curr != null) {
        int leftSize = size.getOrDefault(curr.left, 0);
        if (k <= leftSize) curr = curr.left;
        else if (k == leftSize + 1) return curr.val;
        else { k -= leftSize + 1; curr = curr.right; }
    }
    throw new IllegalArgumentException("k is larger than the number of nodes.");
}

private static int computeSizes(TreeNode node, Map<TreeNode, Integer> size) {
    if (node == null) return 0;
    int total = 1 + computeSizes(node.left, size) + computeSizes(node.right, size);
    size.put(node, total);
    return total;
}
```

- **Time Complexity:** O(h) per query after an O(n) preprocessing
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Collect In-order, Then Index | O(n) | O(n) |
| 2 | Iterative In-order with Early Stop (Optimized) | O(h + k) | O(h) |
| 3 | Augmented Tree (Subtree Sizes) | O(h) per query after an O(n) preprocessing | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../../src/main/java/com/ranga/blind75/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### KthSmallestElementInBST.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class KthSmallestElementInBST {

    public static void main(String[] args) {
        TreeNode[] inputs = {TreeNode.of(3, 1, 4, null, 2), TreeNode.of(5, 3, 6, 2, 4, null, null, 1)};
        int[] ks = {1, 3};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : root = " + TreeNode.toString(inputs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + kthSmallest(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (--k == 0) {
                return curr.val;
            }
            curr = curr.right;
        }
        throw new IllegalArgumentException("k is larger than the number of nodes.");
    }
}
```
