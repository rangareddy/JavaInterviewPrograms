package com.ranga.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Given the {@code root} of a binary tree, return the inorder traversal of its nodes' values: for every node visit the left subtree, then the node, then the right subtree.
 *
 * <p><b>Algorithm Strategy (Iterative Traversal with a Stack):</b>
 * <ol>
 *   <li>Keep a pointer {@code cur} and a stack.</li>
 *   <li>While {@code cur} is not null, push it and move to its left child.</li>
 *   <li>When {@code cur} is null, pop a node, record its value, and move to its right child.</li>
 *   <li>Stop when both {@code cur} is null and the stack is empty.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the tree {@code 1 -> right 2 -> left 3}: go left from 1 (nothing), visit 1, go right to 2, go left to 3, visit 3, then visit 2.</li>
 *   <li>Result: {@code [1, 3, 2]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every node is pushed and popped once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — The stack holds at most one root-to-leaf path.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BinaryTreeInorderTraversal {

    public static void main(String[] args) {
        Integer[][] inputs = {{1, null, 2, 3}, {1, 2, 3, 4, 5}, {1}, {}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + inorderTraversal(root));
            System.out.println();
        }
    }

    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            result.add(cur.val);
            cur = cur.right;
        }
        return result;
    }
}
