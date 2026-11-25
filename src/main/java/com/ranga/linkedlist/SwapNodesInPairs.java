package com.ranga.linkedlist;

/**
 * Given a linked list, swap every two adjacent nodes and return its head. Solve it by changing the node links, not the values inside the nodes.
 *
 * <p><b>Algorithm Strategy (Dummy Head and Pairwise Rewiring):</b>
 * <ol>
 *   <li>Put a {@code dummy} node before the head and let {@code prev} point to it.</li>
 *   <li>While {@code prev.next} and {@code prev.next.next} both exist, name them {@code first} and {@code second}.</li>
 *   <li>Rewire: {@code first.next = second.next}, {@code second.next = first}, {@code prev.next = second}.</li>
 *   <li>Move {@code prev} to {@code first} (now the second node of the pair) and repeat. Return {@code dummy.next}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1 -> 2 -> 3 -> 4}: the pair (1, 2) becomes 2 -> 1, then (3, 4) becomes 4 -> 3.</li>
 *   <li>Result: {@code 2 -> 1 -> 4 -> 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only a few pointers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SwapNodesInPairs {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 4}, {1, 2, 3}, {1}, {}};
        for (int[] values : inputs) {
            ListNode head = ListNode.of(values);
            System.out.println("Input  : head = " + ListNode.toString(head));
            System.out.println("Output : " + ListNode.toString(swapPairs(head)));
            System.out.println();
        }
    }

    public static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = first.next;
            first.next = second.next;
            second.next = first;
            prev.next = second;
            prev = first;
        }
        return dummy.next;
    }
}
