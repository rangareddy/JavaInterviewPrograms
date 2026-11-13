package com.ranga.design;

import java.util.Random;

/**
 * Design a skip list without using a built-in balanced tree. It stores integers (duplicates allowed) and supports {@code search(num)}, {@code add(num)} and {@code erase(num)} (removes one occurrence and returns whether it existed), each in {@code O(log n)} expected time.
 *
 * <p><b>Algorithm Strategy (Layered Sorted Linked Lists with Random Heights):</b>
 * <ol>
 *   <li>A skip list is a sorted linked list with extra "express lane" lists above it. Level 0 holds every element; each higher level holds a random subset of the level below, about half of it.</li>
 *   <li>Each node has a {@code next} pointer per level it belongs to. Search starts at the top level of a sentinel head node, moves right while the next value is smaller than the target, and drops down a level when it cannot. At level 0 the next node is the candidate.</li>
 *   <li>{@code add} records, for every level, the last node before the insertion point (found with the same walk). It picks a random height by flipping a coin until it gets tails (capped at the maximum level) and links the new node into that many levels.</li>
 *   <li>{@code erase} does the same walk, then unlinks one node with the value from every level where it appears. The random heights keep the structure balanced on average, with no rotations needed.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>With elements 1, 2, 3, 4 and levels {@code L1: 1 -> 3}, {@code L0: 1 -> 2 -> 3 -> 4}, searching 4 starts at {@code L1} (head to 3), cannot go right past 4, drops to {@code L0} and walks from 3 to 4: found.</li>
 *   <li>Searching 5 ends at 4 with no next node, so it returns false. Erasing 3 unlinks it from both levels.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — Expected time for search, add and erase.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Expected, about two pointers per node on average.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DesignSkipList {

    private static final int MAX_LEVEL = 16;

    private static class Node {
        final int val;
        final Node[] next;

        Node(int val, int level) {
            this.val = val;
            this.next = new Node[level];
        }
    }

    public static void main(String[] args) {
        DesignSkipList list = new DesignSkipList();
        System.out.println("Input  : add(1), add(2), add(3), search(0), add(4), search(1), erase(0), erase(1), search(1)");
        list.add(1);
        list.add(2);
        list.add(3);
        boolean a = list.search(0);
        list.add(4);
        boolean b = list.search(1);
        boolean c = list.erase(0);
        boolean d = list.erase(1);
        boolean e = list.search(1);
        System.out.println("Output : " + a + ", " + b + ", " + c + ", " + d + ", " + e);
    }

    private final Node head = new Node(-1, MAX_LEVEL);
    private final Random random = new Random(42);
    private int level = 1;

    public boolean search(int num) {
        Node node = head;
        for (int i = level - 1; i >= 0; i--) {
            while (node.next[i] != null && node.next[i].val < num) {
                node = node.next[i];
            }
        }
        node = node.next[0];
        return node != null && node.val == num;
    }

    public void add(int num) {
        Node[] update = predecessors(num);
        int height = randomHeight();
        if (height > level) {
            for (int i = level; i < height; i++) {
                update[i] = head;
            }
            level = height;
        }
        Node node = new Node(num, height);
        for (int i = 0; i < height; i++) {
            node.next[i] = update[i].next[i];
            update[i].next[i] = node;
        }
    }

    public boolean erase(int num) {
        Node[] update = predecessors(num);
        Node target = update[0].next[0];
        if (target == null || target.val != num) {
            return false;
        }
        for (int i = 0; i < level && update[i].next[i] == target; i++) {
            update[i].next[i] = target.next[i];
        }
        while (level > 1 && head.next[level - 1] == null) {
            level--;
        }
        return true;
    }

    private Node[] predecessors(int num) {
        Node[] update = new Node[MAX_LEVEL];
        Node node = head;
        for (int i = level - 1; i >= 0; i--) {
            while (node.next[i] != null && node.next[i].val < num) {
                node = node.next[i];
            }
            update[i] = node;
        }
        return update;
    }

    private int randomHeight() {
        int height = 1;
        while (height < MAX_LEVEL && random.nextBoolean()) {
            height++;
        }
        return height;
    }
}
