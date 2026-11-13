package com.ranga.design;

import java.util.LinkedList;

/**
 * Design a {@code MyHashSet} of non-negative integers without using the built-in hash table, supporting {@code add(key)}, {@code remove(key)} and {@code contains(key)}. Adding an existing key or removing a missing one does nothing.
 *
 * <p><b>Algorithm Strategy (Separate Chaining with a Fixed Number of Buckets):</b>
 * <ol>
 *   <li>Allocate an array of buckets; each bucket is a small linked list of the keys that hash to it. A prime bucket count such as 769 spreads keys well.</li>
 *   <li>The bucket of a key is {@code key % buckets}. All operations search only that bucket.</li>
 *   <li>{@code add} appends the key if it is not in the bucket, {@code remove} deletes it if present and {@code contains} scans the bucket.</li>
 *   <li>With {@code n} keys spread over {@code b} buckets each chain holds about {@code n / b} keys, so operations take {@code O(n / b)} on average. A production table would also resize when the load grows; this fixed version keeps the structure visible.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code add(1)}, {@code add(2)}: keys 1 and 2 go to buckets 1 and 2. {@code contains(1)} looks only in bucket 1 and returns true; {@code contains(3)} looks in bucket 3 and returns false.</li>
 *   <li>{@code add(2)} again is ignored; {@code remove(2)} empties bucket 2, so {@code contains(2)} is false.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n / b)} — Average per operation, with {@code b} buckets and {@code n} keys.</li>
 *   <li><b>Space Complexity:</b> {@code O(n + b)} — The keys plus the bucket array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DesignHashSet {

    public static void main(String[] args) {
        DesignHashSet set = new DesignHashSet();
        System.out.println("Input  : add(1), add(2), contains(1), contains(3), add(2), contains(2), remove(2), contains(2)");
        set.add(1);
        set.add(2);
        boolean a = set.contains(1);
        boolean b = set.contains(3);
        set.add(2);
        boolean c = set.contains(2);
        set.remove(2);
        boolean d = set.contains(2);
        System.out.println("Output : " + a + ", " + b + ", " + c + ", " + d);
    }

    private static final int BUCKETS = 769;
    private final LinkedList<Integer>[] table;

    @SuppressWarnings("unchecked")
    public DesignHashSet() {
        table = new LinkedList[BUCKETS];
        for (int i = 0; i < BUCKETS; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public void add(int key) {
        LinkedList<Integer> bucket = table[key % BUCKETS];
        if (!bucket.contains(key)) {
            bucket.add(key);
        }
    }

    public void remove(int key) {
        table[key % BUCKETS].remove(Integer.valueOf(key));
    }

    public boolean contains(int key) {
        return table[key % BUCKETS].contains(key);
    }
}
