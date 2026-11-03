package com.ranga.linkedlist;

/**
 * Given the head of a linked list, return the node where the cycle begins, or {@code null} if there is no cycle. Do not modify the list and use only {@code O(1)} extra memory.
 *
 * <p><b>Algorithm Strategy (Floyd's Cycle Detection: Find the Meeting Point, then the Entrance):</b>
 * <ol>
 *   <li>Move {@code slow} one step and {@code fast} two steps at a time. If {@code fast} reaches {@code null}, there is no cycle.</li>
 *   <li>If they meet, there is a cycle. Let {@code a} be the distance from the head to the cycle entrance and {@code c} the cycle length: the distance travelled by {@code fast} is twice that of {@code slow}, which means that walking {@code a} more steps from the meeting point also reaches the entrance.</li>
 *   <li>Put a new pointer {@code start} at the head and keep {@code slow} at the meeting point.</li>
 *   <li>Move both one step at a time; the node where they meet is the first node of the cycle.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 3 -> 2 -> 0 -> -4} with the tail connected back to the node 2: slow and fast meet at node 0; a pointer from the head and slow then both reach node 2 after one step each.</li>
 *   <li>Result: the node with value {@code 2}. For a list without a cycle the result is {@code null}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — The pointers travel a bounded number of laps.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Three pointers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LinkedListCycleII {

    public static void main(String[] args) {
        ListNode withCycle = ListNode.of(3, 2, 0, -4);
        ListNode tail = withCycle;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = withCycle.next;         // the tail points back to the node with value 2

        System.out.println("Input  : head = [3, 2, 0, -4], pos = 1");
        ListNode entrance = detectCycle(withCycle);
        System.out.println("Output : cycle starts at value " + (entrance == null ? "none" : entrance.val));
        System.out.println();

        ListNode selfLoop = ListNode.of(1);
        selfLoop.next = selfLoop;
        System.out.println("Input  : head = [1], pos = 0");
        System.out.println("Output : cycle starts at value " + detectCycle(selfLoop).val);
        System.out.println();

        ListNode noCycle = ListNode.of(1, 2);
        System.out.println("Input  : head = [1, 2], pos = -1");
        ListNode none = detectCycle(noCycle);
        System.out.println("Output : cycle starts at value " + (none == null ? "none" : none.val));
    }

    public static ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                ListNode start = head;
                while (start != slow) {
                    start = start.next;
                    slow = slow.next;
                }
                return start;
            }
        }
        return null;
    }
}
