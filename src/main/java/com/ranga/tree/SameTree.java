package com.ranga.tree;

/**
 * Given the roots of two binary trees {@code p} and {@code q}, check whether they are the same: structurally identical and with equal node values.
 *
 * <p><b>Algorithm Strategy (Simultaneous Recursion):</b>
 * <ol>
 *   <li>If both nodes are {@code null}, the (empty) subtrees are the same.</li>
 *   <li>If only one of them is {@code null}, or the values differ, the trees are different.</li>
 *   <li>Otherwise the trees are the same exactly when the left subtrees are the same and the right subtrees are the same; recurse on both pairs.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3]} and {@code [1, 2, 3]}: roots equal (1), left children equal (2), right children equal (3), all leaves have null children on both sides.</li>
 *   <li>Result: {@code true}. For {@code [1, 2]} and {@code [1, null, 2]} the left children differ, so {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each pair of nodes is compared once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — Recursion depth equals the tree height.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
