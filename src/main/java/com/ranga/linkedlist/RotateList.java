package com.ranga.linkedlist;

/**
 * Given the head of a linked list, rotate the list to the right by {@code k} places.
 *
 * <p><b>Algorithm Strategy (Close the Ring and Cut):</b>
 * <ol>
 *   <li>Walk the list to find its length {@code n} and its tail.</li>
 *   <li>Rotating by {@code k} is the same as rotating by {@code k % n}; if that is 0, nothing changes.</li>
 *   <li>Link the tail to the head to form a ring.</li>
 *   <li>The new tail is the node at position {@code n - k % n - 1}; its next node is the new head. Cut the ring there.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1 -> 2 -> 3 -> 4 -> 5} and {@code k = 2}: {@code n = 5}, the new tail is node 3, the new head is node 4.</li>
 *   <li>Result: {@code 4 -> 5 -> 1 -> 2 -> 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass to measure and one partial pass to cut.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Pointers only.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RotateList {

    public static void main(String[] args) {
        int[][] values = {{1, 2, 3, 4, 5}, {0, 1, 2}, {1, 2}, {}};
        int[] ks = {2, 4, 0, 3};
        for (int i = 0; i < values.length; i++) {
            ListNode head = ListNode.of(values[i]);
            System.out.println("Input  : head = " + ListNode.toString(head) + ", k = " + ks[i]);
            System.out.println("Output : " + ListNode.toString(rotateRight(head, ks[i])));
            System.out.println();
        }
    }

    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k <= 0) {
            return head;
        }
        int length = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }
        int shift = k % length;
        if (shift == 0) {
            return head;
        }
        tail.next = head;
        ListNode newTail = head;
        for (int i = 0; i < length - shift - 1; i++) {
            newTail = newTail.next;
        }
        ListNode newHead = newTail.next;
        newTail.next = null;
        return newHead;
    }
}
