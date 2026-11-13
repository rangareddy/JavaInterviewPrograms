package com.ranga.design;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Design a Least Frequently Used (LFU) cache with a fixed {@code capacity}. {@code get(key)} returns the value or {@code -1}. {@code put(key, value)} inserts or updates a key; when the cache is full it first evicts the key used the fewest times, and if several keys tie it evicts the least recently used among them. Every {@code get} or {@code put} of a key counts as one use. Both operations must run in {@code O(1)} average time.
 *
 * <p><b>Algorithm Strategy (Three Maps with Frequency Buckets):</b>
 * <ol>
 *   <li>Keep {@code values} (key to value), {@code freq} (key to its use count) and {@code buckets} (use count to the set of keys with that count). Each bucket is a {@code LinkedHashSet}, which keeps insertion order, so its first element is the least recently used key of that frequency.</li>
 *   <li>Also keep {@code minFreq}, the smallest frequency that currently has a non-empty bucket.</li>
 *   <li>On a use of key {@code k} with frequency {@code f}: remove {@code k} from bucket {@code f} and add it to bucket {@code f + 1}. If bucket {@code f} became empty and {@code f == minFreq}, then {@code minFreq} becomes {@code f + 1}.</li>
 *   <li>To insert a new key into a full cache, evict the first key of bucket {@code minFreq}, then add the new key with frequency 1 and set {@code minFreq = 1}. Every step is a constant number of hash operations.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>With capacity 2: {@code put(1, 1)}, {@code put(2, 2)}, {@code get(1)} returns 1 and bumps key 1 to frequency 2.</li>
 *   <li>{@code put(3, 3)} must evict: bucket 1 holds only key 2, so key 2 goes. {@code get(2)} is {@code -1}, {@code get(3)} is 3, and after another {@code put(4, 4)} key 1 and key 3 tie at frequency 2, so the least recently used of them (key 1) is evicted.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Average time for both {@code get} and {@code put}.</li>
 *   <li><b>Space Complexity:</b> {@code O(capacity)} — The three maps.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LFUCache {

    public static void main(String[] args) {
        LFUCache cache = new LFUCache(2);
        System.out.println("Input  : LFUCache(2), put(1, 1), put(2, 2), get(1), put(3, 3), get(2), get(3), put(4, 4), get(1), get(3), get(4)");
        cache.put(1, 1);
        cache.put(2, 2);
        int a = cache.get(1);
        cache.put(3, 3);
        int b = cache.get(2);
        int c = cache.get(3);
        cache.put(4, 4);
        int d = cache.get(1);
        int e = cache.get(3);
        int f = cache.get(4);
        System.out.println("Output : " + a + ", " + b + ", " + c + ", " + d + ", " + e + ", " + f);
    }

    private final int capacity;
    private int minFreq;
    private final Map<Integer, Integer> values = new HashMap<>();
    private final Map<Integer, Integer> freq = new HashMap<>();
    private final Map<Integer, LinkedHashSet<Integer>> buckets = new HashMap<>();

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!values.containsKey(key)) {
            return -1;
        }
        touch(key);
        return values.get(key);
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        if (values.containsKey(key)) {
            values.put(key, value);
            touch(key);
            return;
        }
        if (values.size() == capacity) {
            LinkedHashSet<Integer> lowest = buckets.get(minFreq);
            int evict = lowest.iterator().next();
            lowest.remove(evict);
            values.remove(evict);
            freq.remove(evict);
        }
        values.put(key, value);
        freq.put(key, 1);
        buckets.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        minFreq = 1;
    }

    private void touch(int key) {
        int f = freq.get(key);
        LinkedHashSet<Integer> bucket = buckets.get(f);
        bucket.remove(key);
        if (bucket.isEmpty() && f == minFreq) {
            minFreq++;
        }
        freq.put(key, f + 1);
        buckets.computeIfAbsent(f + 1, k -> new LinkedHashSet<>()).add(key);
    }
}
