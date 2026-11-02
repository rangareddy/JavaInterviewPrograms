# Linked List Cycle

Given the head of a linked list, determine whether the list has a cycle, i.e. some node can be reached again by following the `next` pointers.

## Algorithm Strategy (Floyd's Tortoise and Hare)

1. Use two pointers: `slow` moves one node per step and `fast` moves two nodes per step.
2. If the list has no cycle, `fast` reaches the end (`null`) and the answer is `false`.
3. If there is a cycle, both pointers eventually run inside it and `fast` gains one node on `slow` per step, so they must meet; then the answer is `true`.
4. This needs no extra memory, unlike remembering every visited node in a set.

## Example walkthrough

- For `3 -> 2 -> 0 -> -4` with the tail linked back to the node 2: slow visits 2, 0, -4 while fast visits 0, 2, -4... and they meet inside the cycle.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — The pointers meet within at most the list length plus the cycle length steps.
- **Space Complexity:** `O(1)` — Two pointers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Hash Set of Visited Nodes

Remember every visited node in a set; reaching a node twice means there is a cycle.

```java
public static boolean hasCycleSet(ListNode head) {
    Set<ListNode> visited = new HashSet<>();
    for (ListNode curr = head; curr != null; curr = curr.next) {
        if (!visited.add(curr)) return true;
    }
    return false;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Brent's Cycle Detection

A variant of the tortoise and hare that avoids moving two pointers every step: the hare walks ahead, and the tortoise is teleported to the hare's position each time the number of steps reaches a power of two. If the hare ever meets the tortoise there is a cycle. It needs fewer pointer moves than Floyd's algorithm on average.

```java
public static boolean hasCycleBrent(ListNode head) {
    if (head == null) return false;
    ListNode tortoise = head, hare = head.next;
    int power = 1, length = 1;
    while (hare != null) {
        if (hare == tortoise) return true;
        if (length == power) {
            tortoise = hare;
            power *= 2;
            length = 0;
        }
        hare = hare.next;
        length++;
    }
    return false;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Mark Visited Nodes (Destroys the List)

Redirect the `next` pointer of every visited node to a special marker node. If the walk reaches a node that already points to the marker, it has been visited before, so there is a cycle. `O(1)` extra space, but the list is destroyed, so it is only acceptable when the list is not needed afterwards.

```java
public static boolean hasCycleMarking(ListNode head) {
    ListNode visitedMarker = new ListNode(0);
    ListNode curr = head;
    while (curr != null) {
        if (curr.next == visitedMarker) return true;
        ListNode next = curr.next;
        curr.next = visitedMarker;
        curr = next;
    }
    return false;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: Step Limit from the Constraints

When the problem guarantees an upper bound on the length (the original statement says at most 10,000 nodes), a walk that takes more steps than that cannot be a plain list, so there must be a cycle. Simple, but only valid under that guarantee.

```java
public static boolean hasCycleStepLimit(ListNode head, int maxNodes) {
    int steps = 0;
    for (ListNode curr = head; curr != null; curr = curr.next) {
        if (++steps > maxNodes) return true;
    }
    return false;
}
```

- **Time Complexity:** O(maxNodes)
- **Space Complexity:** O(1)

### Approach 5: Floyd's Tortoise and Hare (Optimized)

A slow pointer (one step) and a fast pointer (two steps) meet inside the cycle if there is one. No extra memory. This is the version implemented in the repository.

```java
public static boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Hash Set of Visited Nodes | O(n) | O(n) |
| 2 | Brent's Cycle Detection | O(n) | O(1) |
| 3 | Mark Visited Nodes (Destroys the List) | O(n) | O(1) |
| 4 | Step Limit from the Constraints | O(maxNodes) | O(1) |
| 5 | Floyd's Tortoise and Hare (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 5 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`ListNode`](../../src/main/java/com/ranga/linkedlist/ListNode.java) (a small `ListNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### LinkedListCycle.java

```java
import com.ranga.linkedlist.ListNode;

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
```
