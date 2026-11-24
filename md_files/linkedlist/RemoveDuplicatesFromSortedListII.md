# Remove Duplicates from Sorted List II

Given the head of a sorted linked list, delete every node that has a duplicate value, so that only values that appear exactly once remain, and return the list still sorted.

## Algorithm Strategy (Dummy Head and Run Skipping)

1. Put a `dummy` node in front of the head, because the head itself may be removed. Keep `prev` pointing at the last node that is known to stay.
2. At `cur`, check whether `cur.next` has the same value. If it does, this value is duplicated.
3. Walk `cur` forward past every node with that value, then set `prev.next = cur.next` to drop the whole run.
4. If there was no duplicate, move `prev` to `cur`. Move `cur` forward either way. Return `dummy.next`.

## Example walkthrough

- For `1 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5`: the runs of 3 and 4 are skipped entirely.
- Result: `1 -> 2 -> 5`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each node is visited once.
- **Space Complexity:** `O(1)` — Only a few pointers are used.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Count Frequencies

Count each value in a `HashMap`, then rebuild the list from values whose count is 1.

```java
public static ListNode dedupIIViaCounts(ListNode head) {
    java.util.Map<Integer, Integer> counts = new java.util.LinkedHashMap<>();
    for (ListNode n = head; n != null; n = n.next) counts.merge(n.val, 1, Integer::sum);
    ListNode dummy = new ListNode(0), tail = dummy;
    for (java.util.Map.Entry<Integer, Integer> e : counts.entrySet()) {
        if (e.getValue() == 1) { tail.next = new ListNode(e.getKey()); tail = tail.next; }
    }
    return dummy.next;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Dummy Head and Run Skipping (Optimized)

Skip whole runs of equal values in place. This is the version implemented in the repository.

```java
public static ListNode dedupIIInPlace(ListNode head) {
    ListNode dummy = new ListNode(0); dummy.next = head;
    ListNode prev = dummy, cur = head;
    while (cur != null) {
        if (cur.next != null && cur.val == cur.next.val) {
            int dup = cur.val;
            while (cur != null && cur.val == dup) cur = cur.next;
            prev.next = cur;
        } else { prev = cur; cur = cur.next; }
    }
    return dummy.next;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Count Frequencies | O(n) | O(n) |
| 2 | Dummy Head and Run Skipping (Optimized) | O(n) | O(1) |

## Source Code

### RemoveDuplicatesFromSortedListII.java

```java
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
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a `dummy` node handles the case where the head is a duplicate. `prev` always points at the last node that stays. When `cur` starts a run of equal values, the whole run is skipped and `prev.next` jumps past it.

**Input:** `1 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5`.

| Step | `prev` | `cur` | Action | List afterwards |
|------|--------|-------|--------|-----------------|
| 1 | dummy | 1 | no duplicate, keep | 1 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5 |
| 2 | 1 | 2 | no duplicate, keep | same |
| 3 | 2 | 3 | run of 3, skip both | 1 -> 2 -> 4 -> 4 -> 5 |
| 4 | 2 | 4 | run of 4, skip both | 1 -> 2 -> 5 |
| 5 | 2 | 5 | no duplicate, keep | 1 -> 2 -> 5 |

**Output:** `1 -> 2 -> 5`.
