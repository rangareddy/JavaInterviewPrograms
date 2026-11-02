package com.ranga.blind75.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Node of a binary tree, shared by the tree problems of the Blind 75 list.
 *
 * <p>Trees are written in the same level-order format used by LeetCode, where {@code null} marks a missing child,
 * for example {@code TreeNode.of(3, 9, 20, null, null, 15, 7)}.
 *
 * @author Ranga Reddy
 */
public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    /**
     * Builds a binary tree from its level-order representation.
     *
     * @param values the node values level by level, with {@code null} for missing nodes
     * @return the root of the tree, or {@code null} for an empty input
     */
    public static TreeNode of(Integer... values) {
        if (values.length == 0 || values[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            if (i < values.length && values[i] != null) {
                node.left = new TreeNode(values[i]);
                queue.add(node.left);
            }
            i++;
            if (i < values.length && values[i] != null) {
                node.right = new TreeNode(values[i]);
                queue.add(node.right);
            }
            i++;
        }
        return root;
    }

    /**
     * Formats a tree in level-order as {@code [3, 9, 20, null, null, 15, 7]} (trailing nulls are removed).
     */
    public static String toString(TreeNode root) {
        List<String> out = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                out.add("null");
            } else {
                out.add(String.valueOf(node.val));
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        while (!out.isEmpty() && out.get(out.size() - 1).equals("null")) {
            out.remove(out.size() - 1);
        }
        return out.toString();
    }
}
