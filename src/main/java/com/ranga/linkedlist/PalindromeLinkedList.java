package com.ranga.linkedlist;

/**
 * Given the head of a singly linked list, return {@code true} if it is a palindrome (reads the same forwards and backwards). Aim for {@code O(n)} time and {@code O(1)} extra space.
 *
 * <p><b>Algorithm Strategy (Find the Middle, Reverse the Second Half, Compare):</b>
 * <ol>
 *   <li>Use slow and fast pointers to find the middle: when {@code fast} reaches the end, {@code slow} is at the middle (the start of the second half).</li>
 *   <li>Reverse the second half of the list in place.</li>
 *   <li>Walk the first half and the reversed second half together, comparing the values. Any difference means the list is not a palindrome.</li>
 *   <li>Optionally reverse the second half back to restore the original list.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1 -> 2 -> 2 -> 1}: the second half {@code 2 -> 1} is reversed to {@code 1 -> 2}; comparing with the first half {@code 1 -> 2} matches: {@code true}.</li>
 *   <li>For {@code 1 -> 2 -> 3}: the second half {@code 3} is compared with {@code 1}: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Three linear passes.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — The list is modified in place (and restored).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PalindromeLinkedList {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 2, 1}, {1, 2, 3, 2, 1}, {1, 2, 3}, {1}, {1, 2}};
        for (int[] values : inputs) {
            ListNode head = ListNode.of(values);
            System.out.println("Input  : head = " + ListNode.toString(head));
            System.out.println("Output : " + isPalindrome(head) + " (list afterwards: " + ListNode.toString(head) + ")");
            System.out.println();
        }
    }

    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode secondHalf = reverse(slow);
        ListNode left = head;
        ListNode right = secondHalf;
        boolean palindrome = true;
        while (right != null) {
            if (left.val != right.val) {
                palindrome = false;
                break;
            }
            left = left.next;
            right = right.next;
        }

        reverse(secondHalf);        // restore the list
        return palindrome;
    }

    private static ListNode reverse(ListNode head) {
        ListNode previous = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = previous;
            previous = head;
            head = next;
        }
        return previous;
    }
}
