package com.ranga.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * Given the root of a binary tree, return the vertical order traversal of its nodes' values: from top to bottom, column by column from left to right. If two nodes are in the same row and column, order them from left to right.
 *
 * <p><b>Algorithm Strategy (BFS with Column Offsets):</b>
 * <ol>
 *   <li>Do a level-order traversal (BFS) and keep, for each node, its column: the root is 0, a left child is {@code col - 1}, a right child is {@code col + 1}.</li>
 *   <li>Append each node's value to the list of its column in a {@code TreeMap} (sorted by column).</li>
 *   <li>BFS visits nodes top to bottom and left to right, so each column list comes out in the right order.</li>
 *   <li>Return the lists of the map in key order.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 9, 20, null, null, 15, 7]}: column -1 has 9, column 0 has 3 and 15, column 1 has 20, column 2 has 7.</li>
 *   <li>Result: {@code [[9], [3, 15], [20], [7]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — {@code TreeMap} inserts; O(n) with a min/max column array.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The map and queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BinaryTreeVerticalOrderTraversal {

    public static void main(String[] args) {
        Integer[][] inputs = {{3, 9, 20, null, null, 15, 7}, {3, 9, 8, 4, 0, 1, 7}, {1}, {}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + verticalOrder(root));
            System.out.println();
        }
    }

    public static List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Map<Integer, List<Integer>> columns = new TreeMap<>();
        Queue<TreeNode> nodes = new ArrayDeque<>();
        Queue<Integer> indexes = new ArrayDeque<>();
        nodes.add(root);
        indexes.add(0);
        while (!nodes.isEmpty()) {
            TreeNode node = nodes.poll();
            int column = indexes.poll();
            columns.computeIfAbsent(column, k -> new LinkedList<>()).add(node.val);
            if (node.left != null) {
                nodes.add(node.left);
                indexes.add(column - 1);
            }
            if (node.right != null) {
                nodes.add(node.right);
                indexes.add(column + 1);
            }
        }
        result.addAll(columns.values());
        return result;
    }
}
