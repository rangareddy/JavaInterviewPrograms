package com.ranga.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Design a time-based key-value data structure that can store multiple values for the same key at different timestamps and retrieve the value of a key at a given timestamp. {@code set(key, value, timestamp)} stores the value, {@code get(key, timestamp)} returns the value stored with the largest timestamp {@code <= timestamp} (or an empty string). Timestamps passed to {@code set} are strictly increasing.
 *
 * <p><b>Algorithm Strategy (Hash Map of Timestamp-Sorted Lists + Binary Search):</b>
 * <ol>
 *   <li>Map every key to a list of {@code (timestamp, value)} entries. Because the timestamps of {@code set} calls are strictly increasing, every list stays sorted by timestamp simply by appending.</li>
 *   <li>{@code set} appends the entry to the list of its key.</li>
 *   <li>{@code get} binary-searches the list of the key for the last entry whose timestamp is {@code <= timestamp}.</li>
 *   <li>If there is no such entry (unknown key or all timestamps are larger), return the empty string.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>After {@code set("foo","bar",1)} and {@code set("foo","bar2",4)}: {@code get("foo",1)} is {@code "bar"}, {@code get("foo",3)} is {@code "bar"} (largest timestamp 1 &lt;= 3), {@code get("foo",4)} is {@code "bar2"}.</li>
 *   <li>{@code get("foo",0)} has no timestamp &lt;= 0 and returns {@code ""}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) for set, O(log n) for get} — {@code n} is the number of values stored for the key.</li>
 *   <li><b>Space Complexity:</b> {@code O(total number of entries)} — Every set call is stored.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TimeBasedKeyValueStore {

    private static class Entry {
        final int timestamp;
        final String value;

        Entry(int timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    private final Map<String, List<Entry>> store = new HashMap<>();

    public static void main(String[] args) {
        TimeBasedKeyValueStore timeMap = new TimeBasedKeyValueStore();
        timeMap.set("foo", "bar", 1);
        System.out.println("Input  : set(\"foo\", \"bar\", 1)");
        System.out.println("Input  : get(\"foo\", 1)");
        System.out.println("Output : \"" + timeMap.get("foo", 1) + "\"");
        System.out.println("Input  : get(\"foo\", 3)");
        System.out.println("Output : \"" + timeMap.get("foo", 3) + "\"");
        timeMap.set("foo", "bar2", 4);
        System.out.println("Input  : set(\"foo\", \"bar2\", 4), get(\"foo\", 4)");
        System.out.println("Output : \"" + timeMap.get("foo", 4) + "\"");
        System.out.println("Input  : get(\"foo\", 5)");
        System.out.println("Output : \"" + timeMap.get("foo", 5) + "\"");
        System.out.println("Input  : get(\"foo\", 0)");
        System.out.println("Output : \"" + timeMap.get("foo", 0) + "\"");
    }

    public void set(String key, String value, int timestamp) {
        store.computeIfAbsent(key, k -> new ArrayList<>()).add(new Entry(timestamp, value));
    }

    public String get(String key, int timestamp) {
        List<Entry> entries = store.get(key);
        if (entries == null) {
            return "";
        }

        int low = 0;
        int high = entries.size() - 1;
        String result = "";
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (entries.get(mid).timestamp <= timestamp) {
                result = entries.get(mid).value;       // candidate: look for a later timestamp
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }
}
