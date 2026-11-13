package com.ranga.design;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Design a data structure that stores string counts and supports {@code inc(key)} (add 1, inserting the key with count 1 if needed), {@code dec(key)} (subtract 1, removing the key at 0), {@code getMaxKey()} and {@code getMinKey()} (a key with the largest and the smallest count, or {@code ""} when empty). Each operation must run in {@code O(1)} average time.
 *
 * <p><b>Algorithm Strategy (Doubly Linked List of Count Buckets plus a HashMap):</b>
 * <ol>
 *   <li>Keep a doubly linked list of buckets ordered by count, smallest at the head and largest at the tail. Each bucket has a {@code count} and the set of keys with exactly that count.</li>
 *   <li>Keep a map from key to its bucket, so a key can be found in {@code O(1)}.</li>
 *   <li>{@code inc(key)}: look at the next bucket. If its count is {@code count + 1} move the key there, otherwise create a new bucket with that count right after the current one (or at the head for a new key). Remove the old bucket if it is empty.</li>
 *   <li>{@code dec(key)} works the same way towards the previous bucket, and removes the key from the map when its count reaches 0.</li>
 *   <li>{@code getMaxKey} and {@code getMinKey} read any key from the tail and head buckets, so they take constant time.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code inc("a")}, {@code inc("b")}, {@code inc("b")}: buckets {@code 1: {a}} then {@code 2: {b}}. {@code getMaxKey()} is {@code b} and {@code getMinKey()} is {@code a}.</li>
 *   <li>{@code dec("b")} moves {@code b} to bucket 1 ({@code 1: {a, b}}) and the empty bucket 2 is unlinked. The second {@code dec("b")} removes {@code b} entirely, so bucket 1 holds only {@code a}; {@code getMaxKey()} and {@code getMinKey()} both return {@code a}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Average for all four operations.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The keys, their buckets and the list nodes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class AllOneDataStructure {

    private static class Bucket {
        final int count;
        final Set<String> keys = new HashSet<>();
        Bucket prev;
        Bucket next;

        Bucket(int count) {
            this.count = count;
        }
    }

    public static void main(String[] args) {
        AllOneDataStructure allOne = new AllOneDataStructure();
        System.out.println("Input  : inc(a), inc(b), inc(b), getMaxKey(), getMinKey(), dec(b), dec(b), getMaxKey(), getMinKey()");
        allOne.inc("a");
        allOne.inc("b");
        allOne.inc("b");
        String maxKey1 = allOne.getMaxKey();
        String minKey1 = allOne.getMinKey();
        allOne.dec("b");
        allOne.dec("b");
        String maxKey2 = allOne.getMaxKey();
        String minKey2 = allOne.getMinKey();
        System.out.println("Output : " + maxKey1 + ", " + minKey1 + ", " + maxKey2 + ", " + minKey2);
    }

    private final Bucket head = new Bucket(0);
    private final Bucket tail = new Bucket(Integer.MAX_VALUE);
    private final Map<String, Bucket> bucketOf = new HashMap<>();

    public AllOneDataStructure() {
        head.next = tail;
        tail.prev = head;
    }

    public void inc(String key) {
        Bucket current = bucketOf.get(key);
        Bucket target;
        if (current == null) {
            target = head.next.count == 1 ? head.next : insertAfter(head, 1);
        } else {
            target = current.next.count == current.count + 1 ? current.next : insertAfter(current, current.count + 1);
            current.keys.remove(key);
            removeIfEmpty(current);
        }
        target.keys.add(key);
        bucketOf.put(key, target);
    }

    public void dec(String key) {
        Bucket current = bucketOf.get(key);
        if (current == null) {
            return;
        }
        if (current.count == 1) {
            bucketOf.remove(key);
        } else {
            Bucket target = current.prev.count == current.count - 1 ? current.prev : insertAfter(current.prev, current.count - 1);
            target.keys.add(key);
            bucketOf.put(key, target);
        }
        current.keys.remove(key);
        removeIfEmpty(current);
    }

    public String getMaxKey() {
        return tail.prev == head ? "" : tail.prev.keys.iterator().next();
    }

    public String getMinKey() {
        return head.next == tail ? "" : head.next.keys.iterator().next();
    }

    private Bucket insertAfter(Bucket node, int count) {
        Bucket bucket = new Bucket(count);
        bucket.prev = node;
        bucket.next = node.next;
        node.next.prev = bucket;
        node.next = bucket;
        return bucket;
    }

    private void removeIfEmpty(Bucket bucket) {
        if (bucket.keys.isEmpty()) {
            bucket.prev.next = bucket.next;
            bucket.next.prev = bucket.prev;
        }
    }
}
