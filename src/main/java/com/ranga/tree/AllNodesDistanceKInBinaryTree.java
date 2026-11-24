package com.ranga.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Given the root of a binary tree, a target node and an integer {@code k}, return the values of all nodes that are at distance {@code k} from the target node, in any order.
 *
 * <p><b>Algorithm Strategy (Parent Links and BFS):</b>
 * <ol>
 *   <li>Build a map from each node to its parent with one traversal.</li>
 *   <li>Start a BFS from the target node, moving to left child, right child and parent.</li>
 *   <li>Keep a visited set to avoid walking back.</li>
 *   <li>After exactly {@code k} levels, the nodes in the queue are the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3,5,1,6,2,0,8,null,null,7,4]}, target 5 and {@code k = 2}: the nodes two steps from 5 are 7, 4 (its grandchildren) and 1 (through the parent 3).</li>
 *   <li>Result: {@code [7, 4, 1]} (any order).</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each node is visited a constant number of times.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The parent map, the visited set and the queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class AllNodesDistanceKInBinaryTree {

    public static void main(String[] args) {
        Integer[] values = {3, 5, 1, 6, 2, 0, 8, null, null, 7, 4};
        int[][] queries = {{5, 2}, {5, 0}, {1, 1}, {7, 5}};
        TreeNode root = TreeNode.of(values);
        for (int[] q : queries) {
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", target = " + q[0] + ", k = " + q[1]);
            System.out.println("Output : " + distanceK(root, find(root, q[0]), q[1]));
            System.out.println();
        }
    }

    public static List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<Integer> result = new ArrayList<>();
        if (root == null || target == null || k < 0) {
            return result;
        }
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        Queue<TreeNode> walk = new ArrayDeque<>();
        walk.add(root);
        while (!walk.isEmpty()) {
            TreeNode node = walk.poll();
            if (node.left != null) {
                parent.put(node.left, node);
                walk.add(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                walk.add(node.right);
            }
        }
        Set<TreeNode> seen = new HashSet<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(target);
        seen.add(target);
        int distance = 0;
        while (!queue.isEmpty() && distance < k) {
            for (int size = queue.size(); size > 0; size--) {
                TreeNode node = queue.poll();
                for (TreeNode next : new TreeNode[]{node.left, node.right, parent.get(node)}) {
                    if (next != null && seen.add(next)) {
                        queue.add(next);
                    }
                }
            }
            distance++;
        }
        for (TreeNode node : queue) {
            result.add(node.val);
        }
        return result;
    }

    /** Finds the node with the given value in the tree, or null. */
    public static TreeNode find(TreeNode root, int value) {
        if (root == null || root.val == value) {
            return root;
        }
        TreeNode left = find(root.left, value);
        return left != null ? left : find(root.right, value);
    }
}
