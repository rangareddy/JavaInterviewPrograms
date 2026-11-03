package com.ranga.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Print the nodes of a binary tree in the three depth-first orders: preorder (node, left, right), inorder (left, node, right) and postorder (left, right, node). Show both the recursive version and an iterative inorder traversal that uses an explicit stack.
 *
 * <p><b>Algorithm Strategy (Recursion and an Explicit Stack):</b>
 * <ol>
 *   <li>Recursive traversals follow the definition directly: visit the node before (preorder), between (inorder) or after (postorder) the recursive calls for the left and right children.</li>
 *   <li>For a binary search tree the inorder traversal returns the values in sorted order.</li>
 *   <li>Iterative inorder: keep a stack. Push the current node and move to its left child until there is none, then pop a node, visit it and continue with its right child.</li>
 *   <li>Repeat until the current node is {@code null} and the stack is empty.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the tree {@code [1, 2, 3, 4, 5, null, 6]}: preorder is {@code 1 2 4 5 3 6}, inorder is {@code 4 2 5 1 3 6} and postorder is {@code 4 5 2 6 3 1}.</li>
 *   <li>The iterative inorder gives the same {@code 4 2 5 1 3 6} without recursion.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every node is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — The recursion stack or the explicit stack holds at most the height {@code h} of the tree.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TreeTraversals {

    public static void main(String[] args) {
        Integer[][] inputs = {{1, 2, 3, 4, 5, null, 6}, {1, null, 2, 3}, {5}, {}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : preorder  = " + preorder(root));
            System.out.println("         inorder   = " + inorder(root));
            System.out.println("         postorder = " + postorder(root));
            System.out.println("         iterative inorder = " + inorderIterative(root));
            System.out.println();
        }
    }

    public static List<Integer> preorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }

    public static List<Integer> inorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    public static List<Integer> postorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }

    public static List<Integer> inorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            result.add(current.val);
            current = current.right;
        }
        return result;
    }

    private static void preorder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        result.add(node.val);
        preorder(node.left, result);
        preorder(node.right, result);
    }

    private static void inorder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.val);
        inorder(node.right, result);
    }

    private static void postorder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        postorder(node.left, result);
        postorder(node.right, result);
        result.add(node.val);
    }
}
