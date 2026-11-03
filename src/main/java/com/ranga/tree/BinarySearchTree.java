package com.ranga.tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Demonstrates a Binary Search Tree (BST) of integers with common interview operations.
 *
 * <p>A BST keeps, for every node, all smaller values in its left subtree and all larger values in its right
 * subtree (duplicates are ignored), so an in-order traversal prints the values in ascending order.
 *
 * <p><b>Algorithm Strategy:</b>
 * <ol>
 *   <li><b>add:</b> recursively go left if the value is smaller than the node, right if larger, and create a new
 *       node when an empty child is reached.</li>
 *   <li><b>display (in-order):</b> visit left subtree, the node, then right subtree; this yields sorted order.</li>
 *   <li><b>mirror:</b> recursively mirror both children and swap them.</li>
 *   <li><b>minValue / maxValue:</b> keep going left (minimum) or right (maximum) until there is no child.</li>
 *   <li><b>size:</b> iterative in-order traversal with an explicit stack, counting every visited node.</li>
 *   <li><b>isBST:</b> pass the allowed {@code (min, max)} range down the recursion and fail when a value falls outside it.</li>
 *   <li><b>levelOrderTraversal:</b> breadth-first search with a queue, processing the tree one level at a time.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Adding {@code 20, 30, -3, 25, 40} gives root {@code 20} with children {@code -3} and {@code 30}, and {@code 30} has children {@code 25} and {@code 40}.</li>
 *   <li>In-order display prints {@code -3 20 25 30 40} (sorted); after {@code mirror()} it prints {@code 40 30 25 20 -3}. The level-order traversal prints {@code 20}, then {@code -3 30}, then {@code 25 40}; {@code size} is {@code 5}, the minimum is {@code -3}, the maximum is {@code 40} and {@code isBST} is {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(h)} for add, minValue and maxValue where {@code h} is the tree height
 *       ({@code O(log n)} for a balanced tree, {@code O(n)} for a skewed one); {@code O(n)} for display, mirror,
 *       size, isBST and levelOrderTraversal.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} for the recursion/stack of the depth-first operations and
 *       {@code O(w)} for the queue of the level-order traversal ({@code w} = maximum width of the tree).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BinarySearchTree {

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        int[] values = {20, 30, -3, 25, 40};
        for (int value : values) {
            tree.add(value);
        }

        System.out.println("Input        : " + java.util.Arrays.toString(values));
        System.out.println("Tree size    : " + tree.size());
        tree.display();

        // converting tree to mirror tree
        tree.mirror();
        System.out.println("After mirror()");
        tree.display();

        tree.mirror();
        System.out.println("Min: " + tree.minValue() + ", Max: " + tree.maxValue());
        System.out.println("IsBST        : " + tree.isBST());

        tree.levelOrderTraversal();
    }
}

class BinaryNode {

    Integer value;
    BinaryNode left;
    BinaryNode right;

    public BinaryNode(Integer value) {
        this.value = value;
        left = right = null;
    }

    @Override
    public String toString() {
        return "value: " + value + ", left: " + left + ", right: " + right;
    }
}

class BinaryTree {

    private BinaryNode rootNode;

    public void add(Integer t) {
        rootNode = add(rootNode, t);
    }

    private BinaryNode add(BinaryNode node, Integer t) {
        // if the node is null, create a new node and return it
        if (node == null) {
            return new BinaryNode(t);
        }
        // if the given value is less than the root node, recur for the left subtree
        if (t < node.value) {
            node.left = add(node.left, t);
        }
        // if the given value is more than the root node, recur for the right subtree
        else if (t > node.value) {
            node.right = add(node.right, t);
        }
        return node;
    }

    private void display(BinaryNode node) {
        if (node == null)
            return;

        display(node.left);
        System.out.print(node.value + " ");
        display(node.right);
    }

    public void display() {
        System.out.print("In-order     : ");
        display(rootNode);
        System.out.println();
    }

    public void mirror() {
        rootNode = mirror(rootNode);
    }

    private BinaryNode mirror(BinaryNode node) {
        if (node == null)
            return null;
        BinaryNode left = mirror(node.left);
        BinaryNode right = mirror(node.right);
        node.left = right;
        node.right = left;
        return node;
    }

    public int maxValue() {
        if (rootNode == null) {
            throw new IllegalStateException("Tree is empty");
        }
        /* loop down to find the rightmost node */
        BinaryNode current = rootNode;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    public int minValue() {
        if (rootNode == null) {
            throw new IllegalStateException("Tree is empty");
        }
        /* loop down to find the leftmost node */
        BinaryNode current = rootNode;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    public boolean isBST() {
        return isBST(rootNode, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isBST(BinaryNode node, long minValue, long maxValue) {
        if (node == null) {
            return true;
        }

        if (node.value < minValue || node.value > maxValue) {
            return false;
        }

        return isBST(node.left, minValue, (long) node.value - 1) && isBST(node.right, (long) node.value + 1, maxValue);
    }

    public int size() {
        BinaryNode current = rootNode;
        int size = 0;
        Deque<BinaryNode> stack = new ArrayDeque<>();
        while (!stack.isEmpty() || current != null) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                size++;
                current = stack.pop();
                current = current.right;
            }
        }
        return size;
    }

    public void levelOrderTraversal() {
        if (rootNode == null) {
            return;
        }
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(rootNode);
        int level = 0;
        while (!queue.isEmpty()) {
            int nodesInLevel = queue.size();
            System.out.print("Level " + level++ + "      : ");
            for (int i = 0; i < nodesInLevel; i++) {
                BinaryNode tempNode = queue.poll();
                System.out.print(tempNode.value + " ");
                if (tempNode.left != null)
                    queue.add(tempNode.left);
                if (tempNode.right != null)
                    queue.add(tempNode.right);
            }
            System.out.println();
        }
    }

    public void clear() {
        rootNode = null;
    }
}
