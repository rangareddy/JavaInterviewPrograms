package com.ranga.linkedlist;

/**
 * Given the head of a singly linked list and two positions {@code left <= right} (1-indexed), reverse the nodes from position {@code left} to position {@code right} and return the list.
 *
 * <p><b>Algorithm Strategy (Head Insertion Inside the Window):</b>
 * <ol>
 *   <li>Add a dummy node before the head so that {@code left = 1} needs no special case.</li>
 *   <li>Move {@code prev} to the node just before position {@code left}; {@code curr} is the node at {@code left} and stays the tail of the reversed part.</li>
 *   <li>Repeat {@code right - left} times: detach the node after {@code curr} and insert it right after {@code prev}.</li>
 *   <li>Each step moves one more node to the front of the window, so the window ends up reversed in one pass without touching the nodes outside it.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1 -> 2 -> 3 -> 4 -> 5} with {@code left = 2}, {@code right = 4}: {@code prev} is node 1 and {@code curr} is node 2.</li>
 *   <li>Move 3 behind {@code prev}: {@code 1 -> 3 -> 2 -> 4 -> 5}. Move 4 behind {@code prev}: {@code 1 -> 4 -> 3 -> 2 -> 5}.</li>
 *   <li>Result: {@code [1, 4, 3, 2, 5]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — At most one pass to reach {@code left} and {@code right - left} pointer moves.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few pointers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReverseLinkedListII {

    public static void main(String[] args) {
        int[][] values = {{1, 2, 3, 4, 5}, {5}, {3, 5}, {1, 2, 3}};
        int[][] ranges = {{2, 4}, {1, 1}, {1, 2}, {1, 3}};
        for (int i = 0; i < values.length; i++) {
            ListNode head = ListNode.of(values[i]);
            System.out.println("Input  : head = " + ListNode.toString(head) + ", left = " + ranges[i][0] + ", right = " + ranges[i][1]);
            System.out.println("Output : " + ListNode.toString(reverseBetween(head, ranges[i][0], ranges[i][1])));
            System.out.println();
        }
    }

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }
        ListNode curr = prev.next;
        for (int i = 0; i < right - left; i++) {
            ListNode next = curr.next;
            curr.next = next.next;
            next.next = prev.next;
            prev.next = next;
        }
        return dummy.next;
    }
}
