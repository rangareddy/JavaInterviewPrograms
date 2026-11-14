package com.ranga.design;

import java.util.HashMap;
import java.util.Map;

/**
 * Design a data structure that follows the Least Recently Used (LRU) cache policy. {@code LRUCache<K, V>(capacity)} creates a cache with a positive capacity. {@code get(key)} returns the value of the key or {@code null} if it does not exist. {@code put(key, value)} inserts or updates the value and, when the number of keys exceeds the capacity, evicts the least recently used key. Both operations must run in {@code O(1)} average time.
 *
 * <p>The cache is generic in the key and value types ({@code LRUCache<K, V>}), so a miss returns {@code null} instead of the {@code -1} sentinel.
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
 *   <li>{@code get(2)} returns {@code null}; {@code put(4,4)} evicts key 1; {@code get(1)} returns {@code null}, {@code get(3)} returns 3, {@code get(4)} returns 4.</li>
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
