package com.ranga.design;

/**
 * Design a hash map from scratch (without using the built-in {@code HashMap}) that supports {@code put(key, value)}, {@code get(key)} (returning -1 when the key is missing), {@code remove(key)} and {@code size()}, and grows automatically as entries are added.
 *
 * <p><b>Algorithm Strategy (Array of Buckets with Chaining and Resizing):</b>
 * <ol>
 *   <li>Keep an array of buckets; every bucket is a singly linked list of entries (separate chaining) so keys with the same index can coexist.</li>
 *   <li>Compute the bucket index from the key: {@code (hashCode & 0x7fffffff) % capacity}, which is never negative.</li>
 *   <li>{@code put}: walk the bucket; if the key exists replace its value, otherwise insert a new entry at the head and increase the size. {@code get} and {@code remove} walk the same bucket.</li>
 *   <li>When the load factor ({@code size / capacity}) exceeds {@code 0.75}, double the array and re-insert every entry with the new capacity, so the chains stay short and operations stay O(1) on average.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code put(1, 10), put(2, 20), put(17, 30)} with capacity 16: keys 1 and 17 land in the same bucket and are chained.</li>
 *   <li>{@code get(17)} returns 30, {@code remove(1)} unlinks only the entry for key 1, and {@code get(1)} then returns -1.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) average, O(n) worst case} — Per operation; resizing is amortised over the insertions.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — One entry per stored key plus the bucket array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CustomHashMap {

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Entry[] buckets = new Entry[INITIAL_CAPACITY];
    private int size;

    public static void main(String[] args) {
        CustomHashMap map = new CustomHashMap();
        map.put(1, 10);
        map.put(2, 20);
        map.put(17, 30);
        System.out.println("Input  : put(1, 10), put(2, 20), put(17, 30), get(17), get(5)");
        System.out.println("Output : get(17) = " + map.get(17) + ", get(5) = " + map.get(5) + ", size = " + map.size());
        System.out.println();

        map.put(1, 11);
        map.remove(2);
        System.out.println("Input  : put(1, 11), remove(2), get(1), get(2)");
        System.out.println("Output : get(1) = " + map.get(1) + ", get(2) = " + map.get(2) + ", size = " + map.size());
        System.out.println();

        for (int i = 100; i < 140; i++) {
            map.put(i, i * 2);
        }
        System.out.println("Input  : put 40 more keys (forces the table to grow)");
        System.out.println("Output : get(139) = " + map.get(139) + ", size = " + map.size());
    }

    public void put(int key, int value) {
        int index = indexFor(key, buckets.length);
        for (Entry entry = buckets[index]; entry != null; entry = entry.next) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }

        buckets[index] = new Entry(key, value, buckets[index]);
        size++;
        if (size > buckets.length * LOAD_FACTOR) {
            resize();
        }
    }

    public int get(int key) {
        for (Entry entry = buckets[indexFor(key, buckets.length)]; entry != null; entry = entry.next) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return -1;
    }

    public void remove(int key) {
        int index = indexFor(key, buckets.length);
        Entry previous = null;
        for (Entry entry = buckets[index]; entry != null; entry = entry.next) {
            if (entry.key == key) {
                if (previous == null) {
                    buckets[index] = entry.next;
                } else {
                    previous.next = entry.next;
                }
                size--;
                return;
            }
            previous = entry;
        }
    }

    public int size() {
        return size;
    }

    private void resize() {
        Entry[] bigger = new Entry[buckets.length * 2];
        for (Entry head : buckets) {
            for (Entry entry = head; entry != null; ) {
                Entry next = entry.next;
                int index = indexFor(entry.key, bigger.length);
                entry.next = bigger[index];
                bigger[index] = entry;
                entry = next;
            }
        }
        buckets = bigger;
    }

    private static int indexFor(int key, int capacity) {
        return (Integer.hashCode(key) & 0x7fffffff) % capacity;
    }

    private static class Entry {
        final int key;
        int value;
        Entry next;

        Entry(int key, int value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
