package com.ranga.design;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Design a {@code HitCounter} that records hits and returns the number of hits in the past 300 seconds (so for a query at time {@code t}, the hits with {@code t - 300 < timestamp <= t}). Calls to {@code hit} and {@code getHits} arrive in non-decreasing timestamp order, in seconds.
 *
 * <p><b>Algorithm Strategy (Queue of Timestamps with Lazy Expiry):</b>
 * <ol>
 *   <li>Because timestamps never decrease, the hits form a sorted sequence. Keep them in a queue, oldest at the head.</li>
 *   <li>{@code hit(t)} appends {@code t} at the tail.</li>
 *   <li>{@code getHits(t)} first removes from the head every timestamp that is 300 seconds or more older than {@code t} (they can never count again, since later queries have larger timestamps), then returns the size of the queue.</li>
 *   <li>Each timestamp is added once and removed once, so the cost is amortized constant. The memory is proportional to the number of hits in the last 300 seconds.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Hits at 1, 2, 3 and {@code getHits(4)}: all three are within 300 seconds, so the answer is 3.</li>
 *   <li>A hit at 300 and {@code getHits(300)} gives 4. At {@code getHits(301)} the hit at 1 is exactly 300 seconds old and expires, leaving 3.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) amortized} — Each hit is enqueued and dequeued once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — {@code h} is the number of hits in the last 300 seconds.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DesignHitCounter {

    public static void main(String[] args) {
        DesignHitCounter counter = new DesignHitCounter();
        System.out.println("Input  : hit(1), hit(2), hit(3), getHits(4), hit(300), getHits(300), getHits(301)");
        counter.hit(1);
        counter.hit(2);
        counter.hit(3);
        int a = counter.getHits(4);
        counter.hit(300);
        int b = counter.getHits(300);
        int c = counter.getHits(301);
        System.out.println("Output : " + a + ", " + b + ", " + c);
    }

    private static final int WINDOW = 300;
    private final Deque<Integer> hits = new ArrayDeque<>();

    public void hit(int timestamp) {
        hits.addLast(timestamp);
    }

    public int getHits(int timestamp) {
        while (!hits.isEmpty() && hits.peekFirst() <= timestamp - WINDOW) {
            hits.removeFirst();
        }
        return hits.size();
    }
}
