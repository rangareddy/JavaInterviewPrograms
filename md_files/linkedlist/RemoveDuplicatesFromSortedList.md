# Remove Duplicates from Sorted List

Given the head of a sorted linked list, delete all duplicates so that each value appears only once, and return the list still sorted.

## Algorithm Strategy (Single Pointer Walk)

1. Because the list is sorted, equal values are always adjacent, so one pass is enough.
2. Keep a pointer `cur` at the head. While `cur` and `cur.next` exist, compare their values.
3. If they are equal, unlink the next node with `cur.next = cur.next.next` and stay on `cur`, since the new neighbour may also be a duplicate.
4. If they differ, advance `cur`. Return the original head, which is never removed.

## Example walkthrough

- For `1 -> 1 -> 2 -> 3 -> 3`: the second 1 is unlinked, `cur` moves to 2 and then 3, and the second 3 is unlinked.
- Result: `1 -> 2 -> 3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each node is visited once.
- **Space Complexity:** `O(1)` — Nodes are unlinked in place.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Collect Values in a Set

Copy the distinct values into a `LinkedHashSet` and rebuild the list from it.

```java
public static ListNode dedupViaSet(ListNode head) {
    java.util.LinkedHashSet<Integer> seen = new java.util.LinkedHashSet<>();
    for (ListNode n = head; n != null; n = n.next) seen.add(n.val);
    ListNode dummy = new ListNode(0), tail = dummy;
    for (int v : seen) { tail.next = new ListNode(v); tail = tail.next; }
    return dummy.next;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: In-Place Pointer Skipping (Optimized)

Unlink a node whenever it equals its successor. This is the version implemented in the repository.

```java
public static ListNode dedupInPlace(ListNode head) {
    ListNode cur = head;
    while (cur != null && cur.next != null) {
        if (cur.val == cur.next.val) cur.next = cur.next.next; else cur = cur.next;
    }
    return head;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Collect Values in a Set | O(n) | O(n) |
| 2 | In-Place Pointer Skipping (Optimized) | O(n) | O(1) |

## Source Code

### RemoveDuplicatesFromSortedList.java

```java
public class RemoveDuplicatesFromSortedList {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 2}, {1, 1, 2, 3, 3}, {1, 1, 1}, {}};
        for (int[] values : inputs) {
            ListNode head = ListNode.of(values);
            System.out.println("Input  : head = " + ListNode.toString(head));
            System.out.println("Output : " + ListNode.toString(deleteDuplicates(head)));
            System.out.println();
        }
    }

    public static ListNode deleteDuplicates(ListNode head) {
        ListNode cur = head;
        while (cur != null && cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the list is sorted, so duplicates are neighbours. One pointer walks the list; when a node equals its next node, the next node is cut out, otherwise the pointer moves on.

**Input:** `1 -> 1 -> 2 -> 3 -> 3`.

| Step | `cur.val` | `cur.next.val` | Action | List afterwards |
|------|-----------|----------------|--------|-----------------|
| 1 | 1 | 1 | equal, unlink next | 1 -> 2 -> 3 -> 3 |
| 2 | 1 | 2 | different, advance | 1 -> 2 -> 3 -> 3 |
| 3 | 2 | 3 | different, advance | 1 -> 2 -> 3 -> 3 |
| 4 | 3 | 3 | equal, unlink next | 1 -> 2 -> 3 |

```text
1 -> 1 -> 2 -> 3 -> 3
^
cur stays on the first 1 until the next node differs
```

**Output:** `1 -> 2 -> 3`.
