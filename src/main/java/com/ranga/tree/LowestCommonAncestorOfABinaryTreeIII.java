package com.ranga.tree;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * Given two nodes {@code p} and {@code q} of a binary tree in which every node has a reference to its parent, return their lowest common ancestor. The nodes are guaranteed to exist in the same tree. A node can be its own ancestor.
 *
 * <p><b>Algorithm Strategy (Two Pointers that Swap Routes):</b>
 * <ol>
 *   <li>Start {@code a = p} and {@code b = q}.</li>
 *   <li>Move each pointer up to its parent each step; when a pointer falls off the root, restart it at the other node.</li>
 *   <li>Both pointers then travel {@code depth(p) + depth(q)} steps in total and meet exactly at the lowest common ancestor.</li>
 *   <li>This is the same idea as finding the intersection of two linked lists.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>In the tree {@code [3,5,1,6,2,0,8,null,null,7,4]} the nodes 5 and 1 have the root 3 as their lowest common ancestor.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(h)} — Each pointer walks at most the two depths added together.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two pointers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LowestCommonAncestorOfABinaryTreeIII {

    public static void main(String[] args) {
        Integer[] values = {3, 5, 1, 6, 2, 0, 8, null, null, 7, 4};
        int[][] pairs = {{5, 1}, {5, 4}, {7, 4}, {6, 6}};
        TreeNode root = TreeNode.of(values);
        Map<Integer, Node> nodes = withParents(root);
        for (int[] pair : pairs) {
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", p = " + pair[0] + ", q = " + pair[1]);
            System.out.println("Output : " + lowestCommonAncestor(nodes.get(pair[0]), nodes.get(pair[1])).val);
            System.out.println();
        }
    }

    /** A tree node that also knows its parent. */
    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int val) {
            this.val = val;
        }
    }

    public static Node lowestCommonAncestor(Node p, Node q) {
        Node a = p;
        Node b = q;
        while (a != b) {
            a = a == null ? q : a.parent;
            b = b == null ? p : b.parent;
        }
        return a;
    }

    /** Copies a plain tree into nodes with parent links and returns them by value. */
    public static Map<Integer, Node> withParents(TreeNode root) {
        Map<Integer, Node> byValue = new HashMap<>();
        if (root == null) {
            return byValue;
        }
        Node top = new Node(root.val);
        byValue.put(top.val, top);
        Queue<TreeNode> source = new ArrayDeque<>();
        Queue<Node> copy = new ArrayDeque<>();
        source.add(root);
        copy.add(top);
        while (!source.isEmpty()) {
            TreeNode s = source.poll();
            Node c = copy.poll();
            if (s.left != null) {
                c.left = new Node(s.left.val);
                c.left.parent = c;
                byValue.put(c.left.val, c.left);
                source.add(s.left);
                copy.add(c.left);
            }
            if (s.right != null) {
                c.right = new Node(s.right.val);
                c.right.parent = c;
                byValue.put(c.right.val, c.right);
                source.add(s.right);
                copy.add(c.right);
            }
        }
        return byValue;
    }
}
