# LRU Cache

Design a data structure that follows the Least Recently Used (LRU) cache policy. `LRUCache(capacity)` creates a cache with a positive capacity. `get(key)` returns the value of the key or `null` if it does not exist (the class is generic, `LRUCache<K, V>`). `put(key, value)` inserts or updates the value and, when the number of keys exceeds the capacity, evicts the least recently used key. Both operations must run in `O(1)` average time.

## Algorithm Strategy (Hash Map + Doubly Linked List)

1. A hash map gives `O(1)` access from a key to its node; a doubly linked list keeps the nodes ordered from the most recently used (next to the head) to the least recently used (next to the tail).
2. Use two dummy nodes `head` and `tail` so inserting and removing never has to handle empty-list special cases.
3. `get`: look the node up, move it right behind the head (it is now the most recently used) and return its value.
4. `put`: if the key exists update its value and move it to the front; otherwise insert a new node at the front, and if the size exceeds the capacity remove the node in front of the tail (the least recently used) and delete its key from the map.

## Example walkthrough

- Capacity 2: `put(1,1)`, `put(2,2)`, `get(1)` returns 1 (key 1 is now the most recent); `put(3,3)` evicts key 2.
- `get(2)` returns `null`; `put(4,4)` evicts key 1; `get(1)` returns `null`, `get(3)` returns 3, `get(4)` returns 4.

## Complexity Analysis

- **Time Complexity:** `O(1)` — for `get` and `put`.
- **Space Complexity:** `O(capacity)` — One node per cached key.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: List Ordered by Recency (Brute Force)

Keep the key/value pairs in an `ArrayList` ordered from least to most recently used. Every access searches the list and moves the entry to the end, so operations cost `O(n)`.

```java
static class ListLRU {
    private final int capacity;
    private final List<int[]> entries = new ArrayList<>();          // {key, value}, least recently used first

    ListLRU(int capacity) { this.capacity = capacity; }

    public int get(int key) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i)[0] == key) { int[] entry = entries.remove(i); entries.add(entry); return entry[1]; }
        }
        return -1;
    }

    public void put(int key, int value) {
        entries.removeIf(entry -> entry[0] == key);
        entries.add(new int[]{key, value});
        if (entries.size() > capacity) entries.remove(0);
    }
}
```

- **Time Complexity:** O(n) per operation
- **Space Complexity:** O(capacity)

### Approach 2: TreeMap Ordered by a Usage Counter

Give every access an increasing counter value. A `HashMap` maps keys to `(value, counter)` and a `TreeMap` maps counters to keys, so the smallest counter is the least recently used key. Operations are `O(log n)`.

```java
static class TreeMapLRU {
    private final int capacity;
    private long clock;
    private final Map<Integer, long[]> data = new HashMap<>();            // key -> {value, last use}
    private final TreeMap<Long, Integer> byUse = new TreeMap<>();         // last use -> key

    TreeMapLRU(int capacity) { this.capacity = capacity; }

    public int get(int key) {
        long[] entry = data.get(key);
        if (entry == null) return -1;
        touch(key, entry);
        return (int) entry[0];
    }

    public void put(int key, int value) {
        long[] entry = data.get(key);
        if (entry != null) { entry[0] = value; touch(key, entry); return; }
        data.put(key, new long[]{value, clock});
        byUse.put(clock++, key);
        if (data.size() > capacity) {
            Map.Entry<Long, Integer> eldest = byUse.pollFirstEntry();
            data.remove(eldest.getValue());
        }
    }

    private void touch(int key, long[] entry) {
        byUse.remove(entry[1]);
        entry[1] = clock;
        byUse.put(clock++, key);
    }
}
```

- **Time Complexity:** O(log n) per operation
- **Space Complexity:** O(capacity)

### Approach 3: LinkedHashMap in Access Order

The JDK already contains the data structure: a `LinkedHashMap` constructed with `accessOrder = true` moves an entry to the end on every access, and overriding `removeEldestEntry` evicts the least recently used one automatically.

```java
static class LinkedHashMapLRU extends LinkedHashMap<Integer, Integer> {
    private final int capacity;

    LinkedHashMapLRU(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    public int get(int key) { return getOrDefault(key, -1); }

    public void put(int key, int value) { super.put(key, value); }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) { return size() > capacity; }
}
```

- **Time Complexity:** O(1) per operation
- **Space Complexity:** O(capacity)

### Approach 4: HashMap + Doubly Linked List (Optimized)

The classic design: the map finds a node in `O(1)`, the doubly linked list moves it to the front and evicts from the back in `O(1)`. This is the version implemented in the repository.

```java
static class DoublyLinkedLRU {
    private static class Node { int key, value; Node prev, next; Node(int k, int v) { key = k; value = v; } }
    private final int capacity;
    private final Map<Integer, Node> map = new HashMap<>();
    private final Node head = new Node(0, 0), tail = new Node(0, 0);

    DoublyLinkedLRU(int capacity) { this.capacity = capacity; head.next = tail; tail.prev = head; }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) return -1;
        remove(node);
        addToFront(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (node != null) { node.value = value; remove(node); addToFront(node); return; }
        node = new Node(key, value);
        map.put(key, node);
        addToFront(node);
        if (map.size() > capacity) { Node lru = tail.prev; remove(lru); map.remove(lru.key); }
    }

    private void remove(Node n) { n.prev.next = n.next; n.next.prev = n.prev; }
    private void addToFront(Node n) { n.next = head.next; n.prev = head; head.next.prev = n; head.next = n; }
}
```

- **Time Complexity:** O(1) per operation
- **Space Complexity:** O(capacity)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | List Ordered by Recency (Brute Force) | O(n) per operation | O(capacity) |
| 2 | TreeMap Ordered by a Usage Counter | O(log n) per operation | O(capacity) |
| 3 | LinkedHashMap in Access Order | O(1) per operation | O(capacity) |
| 4 | HashMap + Doubly Linked List (Optimized) | O(1) per operation | O(capacity) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository, written with generics (`LRUCache<K, V>`), so a miss returns `null` rather than the `-1` used by the integer snippets above.

## Source Code

### LRUCache.java

```java
import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;
        Node<K, V> prev;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, Node<K, V>> cacheMap = new HashMap<>();
    private final Node<K, V> head = new Node<>(null, null);       // dummy: head.next is the most recently used
    private final Node<K, V> tail = new Node<>(null, null);       // dummy: tail.prev is the least recently used

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public static void main(String[] args) {
        LRUCache<Integer, Integer> cache = new LRUCache<>(2);
        System.out.println("Input  : LRUCache(2), put(1, 1), put(2, 2)");
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println("Input  : get(1)");
        System.out.println("Output : " + cache.get(1));
        System.out.println("Input  : put(3, 3)   (evicts key 2), get(2)");
        cache.put(3, 3);
        System.out.println("Output : " + cache.get(2));
        System.out.println("Input  : put(4, 4)   (evicts key 1), get(1), get(3), get(4)");
        cache.put(4, 4);
        System.out.println("Output : " + cache.get(1) + ", " + cache.get(3) + ", " + cache.get(4));
    }

    public void put(K key, V value) {
        Node<K, V> node = cacheMap.get(key);
        if (node != null) {
            removeNode(node);
        }

        node = new Node<>(key, value);
        cacheMap.put(key, node);
        addNode(node);

        if (cacheMap.size() > capacity) {
            Node<K, V> nodeToDelete = tail.prev;
            removeNode(nodeToDelete);
            cacheMap.remove(nodeToDelete.key);
        }
    }

    public V get(K key) {
        Node<K, V> node = cacheMap.get(key);
        if (node == null) {
            return null;
        }
        removeNode(node);
        addNode(node);
        return node.value;
    }

    private void addNode(Node<K, V> node) {
        Node<K, V> nextNode = head.next;
        head.next = node;
        node.prev = head;
        node.next = nextNode;
        nextNode.prev = node;
    }

    private void removeNode(Node<K, V> node) {
        Node<K, V> prevNode = node.prev;
        Node<K, V> nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }
}
```
