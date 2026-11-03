package com.ranga.linkedlist;

import com.ranga.linkedlist.ListNode;

/**
 * Two non-empty linked lists represent two non-negative integers. The digits are stored in reverse order (the least significant digit first) and each node holds a single digit. Add the two numbers and return the sum as a linked list in the same format.
 *
 * <p><b>Algorithm Strategy (Digit-by-Digit Addition with a Carry):</b>
 * <ol>
 *   <li>Both lists start with the least significant digit, which is exactly the order in which school addition works.</li>
 *   <li>Walk both lists together with a {@code carry} (initially 0). In each step add the two current digits (a missing digit counts as 0) and the carry.</li>
 *   <li>The new digit is {@code sum % 10} and the new carry is {@code sum / 10}; append a node with the digit to the result (built behind a dummy head).</li>
 *   <li>Continue while either list has digits left or the carry is not 0, so a final carry creates one more node.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 4, 3]} (342) and {@code [5, 6, 4]} (465): {@code 2+5=7}, {@code 4+6=10} (digit 0, carry 1), {@code 3+4+1=8}.</li>
 *   <li>Result: {@code [7, 0, 8]} (807).</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(max(m, n))} — One step per digit.</li>
 *   <li><b>Space Complexity:</b> {@code O(max(m, n))} — The result list.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class AddTwoNumbers {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{2, 4, 3}, {5, 6, 4}},
                {{0}, {0}},
                {{9, 9, 9, 9, 9, 9, 9}, {9, 9, 9, 9}},
                {{5}, {5}}
        };
        for (int[][] pair : inputs) {
            ListNode l1 = ListNode.of(pair[0]);
            ListNode l2 = ListNode.of(pair[1]);
            System.out.println("Input  : l1 = " + ListNode.toString(l1) + ", l2 = " + ListNode.toString(l2));
            System.out.println("Output : " + ListNode.toString(addTwoNumbers(l1, l2)));
            System.out.println();
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            tail.next = new ListNode(sum % 10);
            tail = tail.next;
            carry = sum / 10;
        }
        return dummy.next;
    }
}
