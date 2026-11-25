package com.ranga.design;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement a {@code SnapshotArray} with {@code set(index, val)} to set an element, {@code snap()} to take a snapshot and return its id (the number of snapshots minus one), and {@code get(index, snapId)} to return the value at {@code index} when the snapshot {@code snapId} was taken. The array starts with all zeros.
 *
 * <p><b>Algorithm Strategy (Version List per Index):</b>
 * <ol>
 *   <li>Do not copy the array on {@code snap()}: keep only a counter {@code snapId}.</li>
 *   <li>For each index store a list of {@code (snapId, value)} entries in increasing {@code snapId} order; the first entry is {@code (0, 0)}.</li>
 *   <li>{@code set} updates the last entry if it belongs to the current snapshot, otherwise appends {@code (snapId, val)}.</li>
 *   <li>{@code get(index, id)} binary searches the index's list for the last entry with {@code snapId <= id} and returns its value.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>{@code set(0, 5)}, {@code snap()} (id 0), {@code set(0, 6)}, then {@code get(0, 0)} finds the entry {@code (0, 5)}.</li>
 *   <li>Result: {@code 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) set and snap, O(log s) get} — {@code s} is the number of changes at that index.</li>
 *   <li><b>Space Complexity:</b> {@code O(number of sets)} — One entry per change, not per snapshot.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SnapshotArray {

    public static void main(String[] args) {
        SnapshotArray array = new SnapshotArray(3);
        array.set(0, 5);
        int id = array.snap();
        array.set(0, 6);
        System.out.println("Input  : set(0, 5), snap(), set(0, 6), get(0, " + id + ")");
        System.out.println("Output : " + array.get(0, id));
        System.out.println();
        int second = array.snap();
        System.out.println("Input  : snap(), get(0, " + second + "), get(1, " + second + ")");
        System.out.println("Output : " + array.get(0, second) + ", " + array.get(1, second));
    }

    private final List<List<int[]>> history = new ArrayList<>();
    private int snapId;

    public SnapshotArray(int length) {
        for (int i = 0; i < length; i++) {
            List<int[]> versions = new ArrayList<>();
            versions.add(new int[]{0, 0});
            history.add(versions);
        }
    }

    public void set(int index, int val) {
        List<int[]> versions = history.get(index);
        int[] last = versions.get(versions.size() - 1);
        if (last[0] == snapId) {
            last[1] = val;
        } else {
            versions.add(new int[]{snapId, val});
        }
    }

    public int snap() {
        return snapId++;
    }

    public int get(int index, int id) {
        List<int[]> versions = history.get(index);
        int lo = 0;
        int hi = versions.size() - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (versions.get(mid)[0] <= id) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return versions.get(lo)[1];
    }
}
