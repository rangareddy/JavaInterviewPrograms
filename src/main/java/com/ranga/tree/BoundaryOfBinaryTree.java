package com.ranga.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The boundary of a binary tree is the concatenation, in anti-clockwise order starting at the root, of: the root, the left boundary (nodes from the root's left child down, choosing the left child when there is one and the right otherwise, excluding leaves), the leaves from left to right, and the right boundary in reverse (excluding leaves). Return the values of the boundary.
 *
 * <p><b>Algorithm Strategy (Three Walks):</b>
 * <ol>
 *   <li>Add the root's value (if the root is not a leaf).</li>
 *   <li>Left boundary: starting at {@code root.left}, repeatedly add the node (if it is not a leaf) and go to its left child, or the right child if there is no left child.</li>
 *   <li>Leaves: a DFS that adds every leaf from left to right.</li>
 *   <li>Right boundary: walk the same way on the right side, collecting values in a list and adding them in reverse.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the tree {@code [1, null, 2, 3, 4]} the root is 1, there is no left boundary, the leaves are 3 and 4, and the right boundary is just 2 (a non-leaf).</li>
 *   <li>Result: {@code [1, 3, 4, 2]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each node is visited a constant number of times.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — The recursion for the leaves.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BoundaryOfBinaryTree {

    public static void main(String[] args) {
        Integer[][] inputs = {{1, null, 2, 3, 4}, {1, 2, 3, 4, 5, 6, null, null, null, 7, 8, 9, 10}, {1}, {}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + boundaryOfBinaryTree(root));
            System.out.println();
        }
    }

    public static List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        if (!isLeaf(root)) {
            result.add(root.val);
        }
        TreeNode node = root.left;
        while (node != null) {
            if (!isLeaf(node)) {
                result.add(node.val);
            }
            node = node.left != null ? node.left : node.right;
        }
        addLeaves(root, result);
        List<Integer> right = new ArrayList<>();
        node = root.right;
        while (node != null) {
            if (!isLeaf(node)) {
                right.add(node.val);
            }
            node = node.right != null ? node.right : node.left;
        }
        Collections.reverse(right);
        result.addAll(right);
        return result;
    }

    private static boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }

    private static void addLeaves(TreeNode node, List<Integer> out) {
        if (node == null) {
            return;
        }
        if (isLeaf(node)) {
            out.add(node.val);
            return;
        }
        addLeaves(node.left, out);
        addLeaves(node.right, out);
    }
}
