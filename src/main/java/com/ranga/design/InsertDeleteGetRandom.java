package com.ranga.design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Implement a {@code RandomizedSet} with {@code insert(val)} (returns true if the value was absent), {@code remove(val)} (returns true if it was present) and {@code getRandom()} (returns a random element, each element with equal probability). Every operation must run in average {@code O(1)} time.
 *
 * <p><b>Algorithm Strategy (ArrayList plus HashMap with Swap-Remove):</b>
 * <ol>
 *   <li>A hash map gives {@code O(1)} insert and lookup but cannot return a random element in {@code O(1)}; an array list can return a random element by index but cannot delete from the middle in {@code O(1)}. Use both.</li>
 *   <li>Store the values in an {@code ArrayList} and a map from each value to its index in the list.</li>
 *   <li>{@code insert} appends to the list and records the index in the map.</li>
 *   <li>{@code remove} swaps the value with the last element of the list, updates the index of the moved element in the map, then removes the last element. Removing the tail of an array list is {@code O(1)}.</li>
 *   <li>{@code getRandom} picks a random index in {@code [0, size)}, which gives every element the same probability.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Insert 1, 2, 3: list {@code [1, 2, 3]}, map {@code {1:0, 2:1, 3:2}}.</li>
 *   <li>Remove 1: swap with the last element, giving {@code [3, 2, 1]} and then drop the tail: {@code [3, 2]}, map {@code {3:0, 2:1}}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Average for all three operations.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The list and the map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class InsertDeleteGetRandom {

    public static void main(String[] args) {
        InsertDeleteGetRandom set = new InsertDeleteGetRandom();
        System.out.println("Input  : insert(1), remove(2), insert(2), getRandom(), remove(1), insert(2), getRandom()");
        boolean a = set.insert(1);
        boolean b = set.remove(2);
        boolean c = set.insert(2);
        int r1 = set.getRandom();
        boolean d = set.remove(1);
        boolean e = set.insert(2);
        int r2 = set.getRandom();
        System.out.println("Output : " + a + ", " + b + ", " + c + ", " + (r1 == 1 || r1 == 2) + ", " + d + ", " + e + ", " + r2);
    }

    private final List<Integer> values = new ArrayList<>();
    private final Map<Integer, Integer> indexOf = new HashMap<>();
    private final Random random = new Random();

    public boolean insert(int val) {
        if (indexOf.containsKey(val)) {
            return false;
        }
        indexOf.put(val, values.size());
        values.add(val);
        return true;
    }

    public boolean remove(int val) {
        Integer index = indexOf.remove(val);
        if (index == null) {
            return false;
        }
        int last = values.remove(values.size() - 1);
        if (last != val) {
            values.set(index, last);
            indexOf.put(last, index);
        }
        return true;
    }

    public int getRandom() {
        return values.get(random.nextInt(values.size()));
    }
}
