package com.ranga.linkedlist;

/**
 * Given the head of a singly linked list, return the middle node. If there are two middle nodes, return the second one.
 *
 * <p><b>Algorithm Strategy (Fast and Slow Pointers):</b>
 * <ol>
 *   <li>Start {@code slow} and {@code fast} at the head.</li>
 *   <li>Move {@code slow} one node and {@code fast} two nodes per step while {@code fast} and {@code fast.next} exist.</li>
 *   <li>When {@code fast} reaches the end, {@code slow} has moved half as far, so it is at the middle.</li>
 *   <li>For an even length the loop ends with {@code fast == null} and {@code slow} on the second of the two middle nodes, which is what the problem asks for.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, 4, 5]}: {@code slow} ends at 3 ({@code fast} at the last node).</li>
 *   <li>For {@code [1, 2, 3, 4, 5, 6]}: {@code slow} ends at 4 ({@code fast} is {@code null}), the second middle.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One traversal.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two pointers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MiddleOfTheLinkedList {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 4, 5}, {1, 2, 3, 4, 5, 6}, {1}, {1, 2}};
        for (int[] values : inputs) {
            ListNode head = ListNode.of(values);
            System.out.println("Input  : head = " + ListNode.toString(head));
            System.out.println("Output : " + ListNode.toString(middleNode(head)));
            System.out.println();
        }
    }

    public static ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
