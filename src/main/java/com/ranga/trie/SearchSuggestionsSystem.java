package com.ranga.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array of {@code products} and a {@code searchWord}, design a system that, after each character of {@code searchWord} is typed, suggests up to three products with the common prefix typed so far. Suggested products are the lexicographically smallest ones; return a list of lists, one per typed character.
 *
 * <p><b>Algorithm Strategy (Sort Once, Binary Search Each Prefix):</b>
 * <ol>
 *   <li>Sort the products lexicographically.</li>
 *   <li>For each prefix of {@code searchWord}, binary search the first product that is greater than or equal to the prefix.</li>
 *   <li>Take up to three products from that position while they still start with the prefix.</li>
 *   <li>Collect the lists for all prefixes.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For products {@code mobile, mouse, moneypot, monitor, mousepad} and the word {@code mouse}: the prefixes {@code m} and {@code mo} give {@code mobile, moneypot, monitor}, while {@code mou} and longer give {@code mouse, mousepad}.</li>
 *   <li>Result: five lists.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n + L * log n)} — Sorting plus one binary search per typed character.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Apart from the output.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SearchSuggestionsSystem {

    public static void main(String[] args) {
        String[][] products = {{"mobile", "mouse", "moneypot", "monitor", "mousepad"}, {"havana"}, {"bags", "baggage", "banner", "box", "cloths"}};
        String[] words = {"mouse", "havana", "bags"};
        for (int i = 0; i < products.length; i++) {
            System.out.println("Input  : products = " + Arrays.toString(products[i]) + ", searchWord = \"" + words[i] + "\"");
            System.out.println("Output : " + suggestedProducts(products[i].clone(), words[i]));
            System.out.println();
        }
    }

    public static List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> result = new ArrayList<>();
        if (products == null || searchWord == null) {
            return result;
        }
        Arrays.sort(products);
        for (int len = 1; len <= searchWord.length(); len++) {
            String prefix = searchWord.substring(0, len);
            int lo = 0;
            int hi = products.length;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (products[mid].compareTo(prefix) < 0) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
            List<String> suggestions = new ArrayList<>();
            for (int i = lo; i < Math.min(lo + 3, products.length) && products[i].startsWith(prefix); i++) {
                suggestions.add(products[i]);
            }
            result.add(suggestions);
        }
        return result;
    }
}
