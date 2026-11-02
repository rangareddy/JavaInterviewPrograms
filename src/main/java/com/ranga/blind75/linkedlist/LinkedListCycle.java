package com.ranga.blind75.linkedlist;

import com.ranga.blind75.linkedlist.ListNode;

/**
 * Given the head of a linked list, determine whether the list has a cycle, i.e. some node can be reached again by following the {@code next} pointers.
 *
 * <p><b>Algorithm Strategy (Floyd's Tortoise and Hare):</b>
 * <ol>
 *   <li>Use two pointers: {@code slow} moves one node per step and {@code fast} moves two nodes per step.</li>
 *   <li>If the list has no cycle, {@code fast} reaches the end ({@code null}) and the answer is {@code false}.</li>
 *   <li>If there is a cycle, both pointers eventually run inside it and {@code fast} gains one node on {@code slow} per step, so they must meet; then the answer is {@code true}.</li>
 *   <li>This needs no extra memory, unlike remembering every visited node in a set.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 3 -> 2 -> 0 -> -4} with the tail linked back to the node 2: slow visits 2, 0, -4 while fast visits 0, 2, -4... and they meet inside the cycle.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — The pointers meet within at most the list length plus the cycle length steps.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two pointers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LinkedListCycle {

    public static void main(String[] args) {
        // 3 -> 2 -> 0 -> -4 with the tail connected to the node at index 1
        ListNode withCycle = ListNode.of(3, 2, 0, -4);
        ListNode tail = withCycle;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = withCycle.next;

        System.out.println("Input  : head = [3, 2, 0, -4], pos = 1");
        System.out.println("Output : " + hasCycle(withCycle));
        System.out.println();

        System.out.println("Input  : head = [1, 2], pos = -1");
        System.out.println("Output : " + hasCycle(ListNode.of(1, 2)));
        System.out.println();

        System.out.println("Input  : head = [1], pos = -1");
        System.out.println("Output : " + hasCycle(ListNode.of(1)));
    }

    public static boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
}
