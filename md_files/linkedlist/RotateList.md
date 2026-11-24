# Rotate List

Given the head of a linked list, rotate the list to the right by `k` places.

## Algorithm Strategy (Close the Ring and Cut)

1. Walk the list to find its length `n` and its tail.
2. Rotating by `k` is the same as rotating by `k % n`; if that is 0, nothing changes.
3. Link the tail to the head to form a ring.
4. The new tail is the node at position `n - k % n - 1`; its next node is the new head. Cut the ring there.

## Example walkthrough

- For `1 -> 2 -> 3 -> 4 -> 5` and `k = 2`: `n = 5`, the new tail is node 3, the new head is node 4.
- Result: `4 -> 5 -> 1 -> 2 -> 3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass to measure and one partial pass to cut.
- **Space Complexity:** `O(1)` — Pointers only.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Rotate by One, k Times

Move the last node to the front `k % n` times. Quadratic in the worst case.

```java
public static ListNode oneByOne(ListNode head, int k) {
    if (head == null || head.next == null) return head;
    int n = 0; for (ListNode x = head; x != null; x = x.next) n++;
    for (int s = 0; s < k % n; s++) {
        ListNode prev = head; while (prev.next.next != null) prev = prev.next;
        ListNode last = prev.next; prev.next = null; last.next = head; head = last;
    }
    return head;
}
```

- **Time Complexity:** O(n * k)
- **Space Complexity:** O(1)

### Approach 2: Ring and Cut (Optimized)

Join the tail to the head, then cut at the new tail. This is the version implemented in the repository.

```java
public static ListNode ring(ListNode head, int k) {
    if (head == null || head.next == null || k <= 0) return head;
    int n = 1; ListNode tail = head; while (tail.next != null) { tail = tail.next; n++; }
    int s = k % n; if (s == 0) return head;
    tail.next = head; ListNode nt = head; for (int i = 0; i < n - s - 1; i++) nt = nt.next;
    ListNode nh = nt.next; nt.next = null; return nh;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Rotate by One, k Times | O(n * k) | O(1) |
| 2 | Ring and Cut (Optimized) | O(n) | O(1) |

## Source Code

### RotateList.java

```java
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
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: turning the list into a ring means "rotate" is just "choose where to cut". The cut is `k % n` nodes before the end.

**Input:** `1 -> 2 -> 3 -> 4 -> 5`, `k = 2`.

| Step | Action | Result |
|------|--------|--------|
| 1 | length `n = 5`, `shift = 2` | |
| 2 | link 5 back to 1 | ring |
| 3 | new tail is at index `5 - 2 - 1 = 2` (node 3) | |
| 4 | new head = node 4, cut after 3 | 4 -> 5 -> 1 -> 2 -> 3 |

**Output:** `4 -> 5 -> 1 -> 2 -> 3`.
