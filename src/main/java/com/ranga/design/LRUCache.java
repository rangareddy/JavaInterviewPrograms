package com.ranga.design;

import java.util.HashMap;
import java.util.Map;

/**
 * Design a data structure that follows the Least Recently Used (LRU) cache policy. {@code LRUCache(capacity)} creates a cache with a positive capacity. {@code get(key)} returns the value of the key or -1 if it does not exist. {@code put(key, value)} inserts or updates the value and, when the number of keys exceeds the capacity, evicts the least recently used key. Both operations must run in {@code O(1)} average time.
 *
 * <p><b>Algorithm Strategy (Hash Map + Doubly Linked List):</b>
 * <ol>
 *   <li>A hash map gives {@code O(1)} access from a key to its node; a doubly linked list keeps the nodes ordered from the most recently used (next to the head) to the least recently used (next to the tail).</li>
 *   <li>Use two dummy nodes {@code head} and {@code tail} so inserting and removing never has to handle empty-list special cases.</li>
 *   <li>{@code get}: look the node up, move it right behind the head (it is now the most recently used) and return its value.</li>
 *   <li>{@code put}: if the key exists update its value and move it to the front; otherwise insert a new node at the front, and if the size exceeds the capacity remove the node in front of the tail (the least recently used) and delete its key from the map.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Capacity 2: {@code put(1,1)}, {@code put(2,2)}, {@code get(1)} returns 1 (key 1 is now the most recent); {@code put(3,3)} evicts key 2.</li>
 *   <li>{@code get(2)} returns -1; {@code put(4,4)} evicts key 1; {@code get(1)} returns -1, {@code get(3)} returns 3, {@code get(4)} returns 4.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — for {@code get} and {@code put}.</li>
 *   <li><b>Space Complexity:</b> {@code O(capacity)} — One node per cached key.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LRUCache {

    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map = new HashMap<>();
    private final Node head = new Node(0, 0);       // dummy: head.next is the most recently used
    private final Node tail = new Node(0, 0);       // dummy: tail.prev is the least recently used

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
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

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        remove(node);
        addToFront(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        if (node != null) {
            node.value = value;
            remove(node);
            addToFront(node);
            return;
        }

        node = new Node(key, value);
        map.put(key, node);
        addToFront(node);
        if (map.size() > capacity) {
            Node leastRecentlyUsed = tail.prev;
            remove(leastRecentlyUsed);
            map.remove(leastRecentlyUsed.key);
        }
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addToFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
