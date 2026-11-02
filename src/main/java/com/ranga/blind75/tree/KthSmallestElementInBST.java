package com.ranga.blind75.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given the root of a binary search tree and an integer {@code k}, return the {@code k}-th smallest value (1-indexed) of all node values.
 *
 * <p><b>Algorithm Strategy (Iterative In-order Traversal):</b>
 * <ol>
 *   <li>An in-order traversal (left, node, right) of a BST visits the values in ascending order.</li>
 *   <li>Traverse iteratively with a stack: go as far left as possible pushing nodes, then pop a node, count it, and continue with its right subtree.</li>
 *   <li>Decrement {@code k} for every popped node; when it reaches 0, the popped node is the answer.</li>
 *   <li>Stopping early means only the first {@code k} nodes (plus the path to them) are visited.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 1, 4, null, 2]} and {@code k = 1}: go left to 1 (no left child), pop it: the first value is 1.</li>
 *   <li>For {@code [5, 3, 6, 2, 4, null, null, 1]} and {@code k = 3}: the order is 1, 2, 3, so the answer is 3.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(h + k)} — Walk down the left spine, then visit {@code k} nodes.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — The stack holds one root-to-node path.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
