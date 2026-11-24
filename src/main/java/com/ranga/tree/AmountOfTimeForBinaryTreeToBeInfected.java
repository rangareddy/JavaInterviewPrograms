package com.ranga.tree;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * You are given the root of a binary tree with unique values and an integer {@code start}. At minute 0 the node with value {@code start} is infected. Each minute, an infected node infects its uninfected adjacent nodes (its left child, right child and parent). Return the number of minutes needed for the whole tree to be infected.
 *
 * <p><b>Algorithm Strategy (Tree to Graph, then BFS):</b>
 * <ol>
 *   <li>Record the parent of every node with one traversal, and find the node with value {@code start}.</li>
 *   <li>Run a BFS from the start node over three neighbours: left, right and parent.</li>
 *   <li>Keep a visited set and process the queue level by level; each level is one minute.</li>
 *   <li>The number of levels after the start level is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1,5,3,null,4,10,6,9,2]} and {@code start = 3}: minute 1 infects 1, 10 and 6; minute 2 infects 5; minute 3 infects 4; minute 4 infects 9 and 2.</li>
 *   <li>Result: {@code 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each node is visited a constant number of times.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The parent map and the queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class AmountOfTimeForBinaryTreeToBeInfected {

    public static void main(String[] args) {
        Object[][] inputs = {
            {new Integer[]{1, 5, 3, null, 4, 10, 6, 9, 2}, 3},
            {new Integer[]{1}, 1},
            {new Integer[]{1, 2, null, 3, null, 4}, 2}
        };
        for (Object[] in : inputs) {
            TreeNode root = TreeNode.of((Integer[]) in[0]);
            int start = (Integer) in[1];
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", start = " + start);
            System.out.println("Output : " + amountOfTime(root, start));
            System.out.println();
        }
    }

    public static int amountOfTime(TreeNode root, int start) {
        if (root == null) {
            return 0;
        }
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        TreeNode startNode = null;
        Queue<TreeNode> walk = new ArrayDeque<>();
        walk.add(root);
        while (!walk.isEmpty()) {
            TreeNode node = walk.poll();
            if (node.val == start) {
                startNode = node;
            }
            if (node.left != null) {
                parent.put(node.left, node);
                walk.add(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                walk.add(node.right);
            }
        }
        if (startNode == null) {
            return 0;
        }
        Set<TreeNode> infected = new HashSet<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(startNode);
        infected.add(startNode);
        int minutes = -1;
        while (!queue.isEmpty()) {
            minutes++;
            for (int size = queue.size(); size > 0; size--) {
                TreeNode node = queue.poll();
                TreeNode[] neighbours = {node.left, node.right, parent.get(node)};
                for (TreeNode next : neighbours) {
                    if (next != null && infected.add(next)) {
                        queue.add(next);
                    }
                }
            }
        }
        return minutes;
    }
}
