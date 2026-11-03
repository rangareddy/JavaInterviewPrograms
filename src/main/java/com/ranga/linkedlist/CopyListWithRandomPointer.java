package com.ranga.linkedlist;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A linked list of length {@code n} is given in which every node has an additional {@code random} pointer that points to any node of the list or to {@code null}. Construct a deep copy of the list: {@code n} brand-new nodes whose {@code next} and {@code random} pointers point to new nodes only.
 *
 * <p><b>Algorithm Strategy (Interweave the Copies with the Originals):</b>
 * <ol>
 *   <li>Pass 1: after every original node insert its copy ({@code A -> A' -> B -> B' -> ...}). The copy of a node is now always {@code node.next}.</li>
 *   <li>Pass 2: set the random pointer of each copy: {@code copy.random = node.random.next} (the copy of the node that {@code node.random} points to), or {@code null}.</li>
 *   <li>Pass 3: unweave the two lists: restore the {@code next} pointers of the originals and link the copies to each other.</li>
 *   <li>No hash map is needed, so the extra space is constant (not counting the copy itself).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[7,null],[13,0],[11,4],[10,2],[1,0]]} (each pair is {@code [value, index of the random node]}) the copy has the same values and the same random indexes, but none of its nodes is an original node.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Three linear passes.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Besides the copied nodes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CopyListWithRandomPointer {

    public static class Node {
        public int val;
        public Node next;
        public Node random;

        public Node(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        int[][][] inputs = {
                {{7, -1}, {13, 0}, {11, 4}, {10, 2}, {1, 0}},
                {{1, 1}, {2, 1}},
                {{3, -1}, {3, 0}, {3, -1}},
                {}
        };
        for (int[][] pairs : inputs) {
            Node head = build(pairs);
            Node copy = copyRandomList(head);
            System.out.println("Input  : " + toString(head));
            System.out.println("Output : " + toString(copy) + ", shares a node with the original: " + sharesNode(head, copy));
            System.out.println();
        }
    }

    public static Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        // 1. Insert a copy after every node: A -> A' -> B -> B' -> ...
        for (Node curr = head; curr != null; curr = curr.next.next) {
            Node copy = new Node(curr.val);
            copy.next = curr.next;
            curr.next = copy;
        }

        // 2. The random pointer of a copy is the copy of the random target
        for (Node curr = head; curr != null; curr = curr.next.next) {
            curr.next.random = (curr.random == null) ? null : curr.random.next;
        }

        // 3. Separate the two lists
        Node copyHead = head.next;
        for (Node curr = head; curr != null; curr = curr.next) {
            Node copy = curr.next;
            curr.next = copy.next;
            copy.next = (copy.next == null) ? null : copy.next.next;
        }
        return copyHead;
    }

    // pairs[i] = {value, index of the random node or -1}
    private static Node build(int[][] pairs) {
        Node[] nodes = new Node[pairs.length];
        for (int i = 0; i < pairs.length; i++) {
            nodes[i] = new Node(pairs[i][0]);
            if (i > 0) {
                nodes[i - 1].next = nodes[i];
            }
        }
        for (int i = 0; i < pairs.length; i++) {
            nodes[i].random = pairs[i][1] < 0 ? null : nodes[pairs[i][1]];
        }
        return pairs.length == 0 ? null : nodes[0];
    }

    private static String toString(Node head) {
        Map<Node, Integer> index = new IdentityHashMap<>();
        for (Node curr = head; curr != null; curr = curr.next) {
            index.put(curr, index.size());
        }
        StringBuilder sb = new StringBuilder("[");
        for (Node curr = head; curr != null; curr = curr.next) {
            sb.append(curr == head ? "" : ", ").append("[").append(curr.val).append(",")
                    .append(curr.random == null ? "null" : index.get(curr.random)).append("]");
        }
        return sb.append("]").toString();
    }

    private static boolean sharesNode(Node a, Node b) {
        Set<Node> originals = Collections.newSetFromMap(new IdentityHashMap<>());
        for (Node curr = a; curr != null; curr = curr.next) {
            originals.add(curr);
        }
        for (Node curr = b; curr != null; curr = curr.next) {
            if (originals.contains(curr) || (curr.random != null && originals.contains(curr.random))) {
                return true;
            }
        }
        return false;
    }
}
