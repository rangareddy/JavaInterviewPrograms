package com.ranga.blind75.tree;

/**
 * Given the root of a binary tree, invert the tree (swap the left and right child of every node) and return its root.
 *
 * <p><b>Algorithm Strategy (Recursive Swap):</b>
 * <ol>
 *   <li>If the node is {@code null}, there is nothing to invert.</li>
 *   <li>Recursively invert the left and the right subtree.</li>
 *   <li>Swap the two inverted subtrees: {@code node.left = invertedRight} and {@code node.right = invertedLeft}.</li>
 *   <li>Return the node, so the parent can attach it.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [4, 2, 7, 1, 3, 6, 9]}: node 2 becomes {@code (3, 1)}, node 7 becomes {@code (9, 6)}.</li>
 *   <li>The root swaps them: {@code [4, 7, 2, 9, 6, 3, 1]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every node is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — Recursion depth equals the tree height.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class InvertBinaryTree {

    public static void main(String[] args) {
        TreeNode[] inputs = {TreeNode.of(4, 2, 7, 1, 3, 6, 9), TreeNode.of(2, 1, 3), TreeNode.of()};
        for (TreeNode root : inputs) {
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + TreeNode.toString(invertTree(root)));
            System.out.println();
        }
    }

    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }
}
