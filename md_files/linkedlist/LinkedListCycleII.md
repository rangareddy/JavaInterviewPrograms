# Linked List Cycle II

Given the head of a linked list, return the node where the cycle begins, or `null` if there is no cycle. Do not modify the list and use only `O(1)` extra memory.

## Algorithm Strategy (Floyd's Cycle Detection: Find the Meeting Point, then the Entrance)

1. Move `slow` one step and `fast` two steps at a time. If `fast` reaches `null`, there is no cycle.
2. If they meet, there is a cycle. Let `a` be the distance from the head to the cycle entrance and `c` the cycle length: the distance travelled by `fast` is twice that of `slow`, which means that walking `a` more steps from the meeting point also reaches the entrance.
3. Put a new pointer `start` at the head and keep `slow` at the meeting point.
4. Move both one step at a time; the node where they meet is the first node of the cycle.

## Example walkthrough

- For `3 -> 2 -> 0 -> -4` with the tail connected back to the node 2: slow and fast meet at node 0; a pointer from the head and slow then both reach node 2 after one step each.
- Result: the node with value `2`. For a list without a cycle the result is `null`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — The pointers travel a bounded number of laps.
- **Space Complexity:** `O(1)` — Three pointers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Hash Set of Visited Nodes

The first node that is visited twice is the start of the cycle.

```java
public static ListNode detectCycleSet(ListNode head) {
    Set<ListNode> seen = new HashSet<>();
    for (ListNode node = head; node != null; node = node.next)
        if (!seen.add(node)) return node;
    return null;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Cycle Length, then Two Pointers

Find the cycle length `c` with Floyd, move one pointer `c` steps ahead and then advance both until they meet.

```java
public static ListNode detectCycleLength(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) {
            int length = 1;
            for (ListNode node = slow.next; node != slow; node = node.next) length++;
            ListNode lead = head, follow = head;
            for (int i = 0; i < length; i++) lead = lead.next;
            while (lead != follow) { lead = lead.next; follow = follow.next; }
            return follow;
        }
    }
    return null;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Floyd Meeting Point then Restart from Head (Optimized)

After the pointers meet, a pointer from the head and one from the meeting point reach the entrance at the same time. This is the version implemented in the repository.

```java
public static ListNode detectCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) {
            ListNode start = head;
            while (start != slow) { start = start.next; slow = slow.next; }
            return start;
        }
    }
    return null;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Hash Set of Visited Nodes | O(n) | O(n) |
| 2 | Cycle Length, then Two Pointers | O(n) | O(1) |
| 3 | Floyd Meeting Point then Restart from Head (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`ListNode`](../../src/main/java/com/ranga/linkedlist/ListNode.java) (a small `ListNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### LinkedListCycleII.java

```java
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
```
