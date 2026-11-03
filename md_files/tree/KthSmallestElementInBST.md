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

### Approach 2: Augmented Tree (Subtree Sizes)

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

### Approach 3: Recursive In-order with Early Exit

Count the nodes while visiting them in order and stop descending as soon as the `k`-th node has been found.

```java
public static int kthSmallestRecursive(TreeNode root, int k) {
    int[] state = {k, -1};                        // {remaining, answer}
    visit(root, state);
    return state[1];
}

private static void visit(TreeNode node, int[] state) {
    if (node == null || state[0] == 0) return;
    visit(node.left, state);
    if (state[0] == 0) return;
    if (--state[0] == 0) { state[1] = node.val; return; }
    visit(node.right, state);
}
```

- **Time Complexity:** O(h + k)
- **Space Complexity:** O(h)

### Approach 4: Morris Traversal (O(1) Space)

Thread the tree: before descending into the left subtree, make the right-most node of that subtree point back to the current node. The in-order walk then needs neither a stack nor recursion, and the threads are removed again as it goes.

```java
public static int kthSmallestMorris(TreeNode root, int k) {
    TreeNode curr = root;
    int result = -1;
    while (curr != null) {
        if (curr.left == null) {
            if (--k == 0) result = curr.val;
            curr = curr.right;
        } else {
            TreeNode predecessor = curr.left;
            while (predecessor.right != null && predecessor.right != curr) predecessor = predecessor.right;
            if (predecessor.right == null) {
                predecessor.right = curr;                 // create the thread
                curr = curr.left;
            } else {
                predecessor.right = null;                 // remove the thread
                if (--k == 0) result = curr.val;
                curr = curr.right;
            }
        }
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 5: Binary Search by Counting Subtree Sizes

Count the nodes of the left subtree: if there are at least `k`, the answer is in the left subtree; if there are exactly `k - 1`, the current node is the answer; otherwise continue in the right subtree with a smaller `k`. Each step recounts a subtree, so it is `O(n)` per level.

```java
public static int kthSmallestCounting(TreeNode root, int k) {
    TreeNode curr = root;
    while (curr != null) {
        int leftSize = count(curr.left);
        if (k <= leftSize) curr = curr.left;
        else if (k == leftSize + 1) return curr.val;
        else { k -= leftSize + 1; curr = curr.right; }
    }
    throw new IllegalArgumentException("k is larger than the number of nodes.");
}

private static int count(TreeNode node) {
    return node == null ? 0 : 1 + count(node.left) + count(node.right);
}
```

- **Time Complexity:** O(n * h) worst case
- **Space Complexity:** O(h)

### Approach 6: Max-Heap of the k Smallest Values

Traverse the whole tree in any order and keep the `k` smallest values seen in a max-heap; the root of the heap is the `k`-th smallest. It does not use the BST property at all, so it also works for an arbitrary binary tree.

```java
public static int kthSmallestHeap(TreeNode root, int k) {
    PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
    Deque<TreeNode> stack = new ArrayDeque<>();
    if (root != null) stack.push(root);
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        heap.add(node.val);
        if (heap.size() > k) heap.poll();
        if (node.left != null) stack.push(node.left);
        if (node.right != null) stack.push(node.right);
    }
    return heap.peek();
}
```

- **Time Complexity:** O(n log k)
- **Space Complexity:** O(k + h)

### Approach 7: Iterative In-order with Early Stop (Optimized)

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

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Collect In-order, Then Index | O(n) | O(n) |
| 2 | Augmented Tree (Subtree Sizes) | O(h) per query after an O(n) preprocessing | O(n) |
| 3 | Recursive In-order with Early Exit | O(h + k) | O(h) |
| 4 | Morris Traversal (O(1) Space) | O(n) | O(1) |
| 5 | Binary Search by Counting Subtree Sizes | O(n * h) worst case | O(h) |
| 6 | Max-Heap of the k Smallest Values | O(n log k) | O(k + h) |
| 7 | Iterative In-order with Early Stop (Optimized) | O(h + k) | O(h) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../src/main/java/com/ranga/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

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
