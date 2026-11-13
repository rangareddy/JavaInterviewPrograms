package com.ranga.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Place the root at {@code (row 0, column 0)}; a node at {@code (row, col)} has its left child at {@code (row + 1, col - 1)} and its right child at {@code (row + 1, col + 1)}. Return the vertical order traversal: the values grouped by column from left to right, and inside a column ordered by row from top to bottom, with nodes that share the same row and column sorted by value.
 *
 * <p><b>Algorithm Strategy (Collect (Column, Row, Value), Sort, Group):</b>
 * <ol>
 *   <li>Traverse the tree with DFS (any order works) and record a triple {@code (column, row, value)} for every node, passing the row and column down.</li>
 *   <li>Sort the triples by column first, then by row, then by value. The value tie-break is what the problem requires for nodes at the same position.</li>
 *   <li>Walk through the sorted triples and start a new group whenever the column changes.</li>
 *   <li>Each group, in order, is one entry of the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 9, 20, null, null, 15, 7]}: the triples are {@code (0, 0, 3), (-1, 1, 9), (1, 1, 20), (0, 2, 15), (2, 2, 7)}.</li>
 *   <li>Sorted and grouped by column: {@code [[9], [3, 15], [20], [7]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Sorting {@code n} triples.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The triples.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class VerticalOrderTraversal {

    public static void main(String[] args) {
        Integer[][] inputs = {
                {3, 9, 20, null, null, 15, 7},
                {1, 2, 3, 4, 5, 6, 7},
                {1, 2, 3, 4, 6, 5, 7},
                {1}
        };
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + verticalTraversal(root));
            System.out.println();
        }
    }

    public static List<List<Integer>> verticalTraversal(TreeNode root) {
        List<int[]> nodes = new ArrayList<>();
        collect(root, 0, 0, nodes);
        nodes.sort((a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            if (a[1] != b[1]) {
                return Integer.compare(a[1], b[1]);
            }
            return Integer.compare(a[2], b[2]);
        });
        List<List<Integer>> result = new ArrayList<>();
        int lastColumn = Integer.MIN_VALUE;
        for (int[] node : nodes) {
            if (node[0] != lastColumn) {
                result.add(new ArrayList<>());
                lastColumn = node[0];
            }
            result.get(result.size() - 1).add(node[2]);
        }
        return result;
    }

    private static void collect(TreeNode node, int row, int column, List<int[]> nodes) {
        if (node == null) {
            return;
        }
        nodes.add(new int[]{column, row, node.val});
        collect(node.left, row + 1, column - 1, nodes);
        collect(node.right, row + 1, column + 1, nodes);
    }
}
