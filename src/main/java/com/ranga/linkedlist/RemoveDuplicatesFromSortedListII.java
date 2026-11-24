package com.ranga.linkedlist;

/**
 * Given the head of a sorted linked list, delete every node that has a duplicate value, so that only values that appear exactly once remain, and return the list still sorted.
 *
 * <p><b>Algorithm Strategy (Dummy Head and Run Skipping):</b>
 * <ol>
 *   <li>Put a {@code dummy} node in front of the head, because the head itself may be removed. Keep {@code prev} pointing at the last node that is known to stay.</li>
 *   <li>At {@code cur}, check whether {@code cur.next} has the same value. If it does, this value is duplicated.</li>
 *   <li>Walk {@code cur} forward past every node with that value, then set {@code prev.next = cur.next} to drop the whole run.</li>
 *   <li>If there was no duplicate, move {@code prev} to {@code cur}. Move {@code cur} forward either way. Return {@code dummy.next}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5}: the runs of 3 and 4 are skipped entirely.</li>
 *   <li>Result: {@code 1 -> 2 -> 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each node is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Only a few pointers are used.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RemoveDuplicatesFromSortedListII {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 3, 4, 4, 5}, {1, 1, 1, 2, 3}, {1, 1}, {1, 2, 3}, {}};
        for (int[] values : inputs) {
            ListNode head = ListNode.of(values);
            System.out.println("Input  : head = " + ListNode.toString(head));
            System.out.println("Output : " + ListNode.toString(deleteDuplicates(head)));
            System.out.println();
        }
    }

    public static ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode cur = head;
        while (cur != null) {
            if (cur.next != null && cur.val == cur.next.val) {
                int dup = cur.val;
                while (cur != null && cur.val == dup) {
                    cur = cur.next;
                }
                prev.next = cur;
            } else {
                prev = cur;
                cur = cur.next;
            }
        }
        return dummy.next;
    }
}
