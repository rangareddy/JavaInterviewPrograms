package com.ranga.design;

import java.util.ArrayList;
import java.util.List;

/**
 * Design a class that keeps a stream of integers and can return the product of its last {@code k} numbers. {@code add(num)} appends a number to the stream; {@code getProduct(k)} returns the product of the last {@code k} numbers. There are always at least {@code k} numbers when {@code getProduct(k)} is called, and the product fits in a 32-bit integer.
 *
 * <p><b>Algorithm Strategy (Prefix Products Restarted After Every Zero):</b>
 * <ol>
 *   <li>Keep a list {@code prefix} of running products, starting with {@code [1]}.</li>
 *   <li>On {@code add(num)}: if {@code num} is 0, clear the list back to {@code [1]} (every product that spans this zero is 0); otherwise append {@code last * num}.</li>
 *   <li>On {@code getProduct(k)}: if {@code k} is larger than the number of values since the last zero ({@code prefix.size() - 1}), the window contains a zero, so return 0.</li>
 *   <li>Otherwise return {@code prefix[last] / prefix[last - k]}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Adding 3, 0, 2, 5, 4 builds prefix {@code [1, 2, 10, 40]} after the zero.</li>
 *   <li>{@code getProduct(2)} = 40 / 2 = 20, {@code getProduct(3)} = 40 / 1 = 40, and {@code getProduct(4)} is 0 because the window includes the zero.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) per call} — One list operation or a division.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The prefix products.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ProductOfTheLastKNumbers {

    public static void main(String[] args) {
        ProductOfTheLastKNumbers numbers = new ProductOfTheLastKNumbers();
        int[] adds = {3, 0, 2, 5, 4};
        for (int value : adds) {
            numbers.add(value);
        }
        System.out.println("Input  : add(3), add(0), add(2), add(5), add(4), getProduct(2)");
        System.out.println("Output : " + numbers.getProduct(2));
        System.out.println();
        System.out.println("Input  : getProduct(3)");
        System.out.println("Output : " + numbers.getProduct(3));
        System.out.println();
        System.out.println("Input  : getProduct(4)");
        System.out.println("Output : " + numbers.getProduct(4));
        System.out.println();
        numbers.add(8);
        System.out.println("Input  : add(8), getProduct(2)");
        System.out.println("Output : " + numbers.getProduct(2));
    }

    private final List<Integer> prefix = new ArrayList<>();

    public ProductOfTheLastKNumbers() {
        prefix.add(1);
    }

    public void add(int num) {
        if (num == 0) {
            prefix.clear();
            prefix.add(1);
        } else {
            prefix.add(prefix.get(prefix.size() - 1) * num);
        }
    }

    public int getProduct(int k) {
        int size = prefix.size();
        if (k >= size) {
            return 0;
        }
        return prefix.get(size - 1) / prefix.get(size - 1 - k);
    }
}
