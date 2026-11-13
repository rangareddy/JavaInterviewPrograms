package com.ranga.design;

import java.util.HashMap;
import java.util.Map;

/**
 * Design a {@code Logger} with {@code shouldPrintMessage(timestamp, message)}. A message may be printed only if it has not been printed in the last 10 seconds; the method returns true (and records the print) when the message is allowed and false otherwise. Timestamps are in seconds and never decrease.
 *
 * <p><b>Algorithm Strategy (HashMap of the Next Allowed Time):</b>
 * <ol>
 *   <li>Keep a map from each message to the earliest timestamp at which it may be printed again.</li>
 *   <li>If the message is not in the map, or its stored time is at most the current timestamp, allow it and store {@code timestamp + 10}.</li>
 *   <li>Otherwise reject it and leave the map unchanged: a rejected message must not extend its own blocking window.</li>
 *   <li>Each call is a constant number of hash operations. The map grows with the number of distinct messages, which is the memory trade-off.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code (1, "foo")} is new: print, store 11. {@code (2, "bar")}: print, store 12. {@code (3, "foo")}: 3 &lt; 11, reject. {@code (8, "bar")}: 8 &lt; 12, reject.</li>
 *   <li>{@code (10, "foo")}: reject. {@code (11, "foo")}: 11 &gt;= 11, print and store 21.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Average, one map lookup and update.</li>
 *   <li><b>Space Complexity:</b> {@code O(m)} — {@code m} distinct messages in the map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LoggerRateLimiter {

    public static void main(String[] args) {
        LoggerRateLimiter logger = new LoggerRateLimiter();
        int[] times = {1, 2, 3, 8, 10, 11};
        String[] messages = {"foo", "bar", "foo", "bar", "foo", "foo"};
        StringBuilder input = new StringBuilder();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < times.length; i++) {
            input.append(i == 0 ? "" : ", ").append("(").append(times[i]).append(", ").append(messages[i]).append(")");
            output.append(i == 0 ? "" : ", ").append(logger.shouldPrintMessage(times[i], messages[i]));
        }
        System.out.println("Input  : shouldPrintMessage" + input);
        System.out.println("Output : " + output);
    }

    private static final int INTERVAL = 10;
    private final Map<String, Integer> nextAllowed = new HashMap<>();

    public boolean shouldPrintMessage(int timestamp, String message) {
        Integer allowedAt = nextAllowed.get(message);
        if (allowedAt != null && timestamp < allowedAt) {
            return false;
        }
        nextAllowed.put(message, timestamp + INTERVAL);
        return true;
    }
}
