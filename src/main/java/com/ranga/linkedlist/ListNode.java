package com.ranga.linkedlist;

/**
 * Node of a singly linked list, shared by the linked list problems of the Blind 75 list.
 *
 * @author Ranga Reddy
 */
public class ListNode {

    public int val;
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    /**
     * Builds a linked list from the given values.
     *
     * @param values the node values in order
     * @return the head of the list, or {@code null} when no values are given
     */
    public static ListNode of(int... values) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int value : values) {
            tail.next = new ListNode(value);
            tail = tail.next;
        }
        return dummy.next;
    }

    /**
     * Formats a list as {@code [1, 2, 3]}. Stops after a safety limit so that a list with a cycle cannot loop forever.
     */
    public static String toString(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        int count = 0;
        for (ListNode curr = head; curr != null && count < 1000; curr = curr.next, count++) {
            if (count > 0) {
                sb.append(", ");
            }
            sb.append(curr.val);
        }
        return sb.append("]").toString();
    }
}
