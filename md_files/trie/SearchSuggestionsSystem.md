# Search Suggestions System

Given an array of `products` and a `searchWord`, design a system that, after each character of `searchWord` is typed, suggests up to three products with the common prefix typed so far. Suggested products are the lexicographically smallest ones; return a list of lists, one per typed character.

## Algorithm Strategy (Sort Once, Binary Search Each Prefix)

1. Sort the products lexicographically.
2. For each prefix of `searchWord`, binary search the first product that is greater than or equal to the prefix.
3. Take up to three products from that position while they still start with the prefix.
4. Collect the lists for all prefixes.

## Example walkthrough

- For products `mobile, mouse, moneypot, monitor, mousepad` and the word `mouse`: the prefixes `m` and `mo` give `mobile, moneypot, monitor`, while `mou` and longer give `mouse, mousepad`.
- Result: five lists.

## Complexity Analysis

- **Time Complexity:** `O(n log n + L * log n)` — Sorting plus one binary search per typed character.
- **Space Complexity:** `O(n)` — Apart from the output.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Filter and Sort for Every Prefix

For each prefix, filter the products that start with it, sort them and take three.

```java
public static java.util.List<java.util.List<String>> filter(String[] p, String w) {
    java.util.List<java.util.List<String>> out = new java.util.ArrayList<>();
    for (int len = 1; len <= w.length(); len++) { String pre = w.substring(0, len); java.util.List<String> m = new java.util.ArrayList<>(); for (String x : p) if (x.startsWith(pre)) m.add(x); java.util.Collections.sort(m); out.add(new java.util.ArrayList<>(m.subList(0, Math.min(3, m.size())))); }
    return out;
}
```

- **Time Complexity:** O(L * n log n)
- **Space Complexity:** O(n)

### Approach 2: Trie with Stored Suggestions

Insert every product in a trie and keep up to three smallest words at each node.

```java
public static java.util.List<java.util.List<String>> trie(String[] p, String w) {
    class N { N[] kids = new N[26]; java.util.List<String> top = new java.util.ArrayList<>(); }
    N root = new N(); String[] s = p.clone(); java.util.Arrays.sort(s);
    for (String x : s) { N n = root; for (char c : x.toCharArray()) { if (n.kids[c - 'a'] == null) n.kids[c - 'a'] = new N(); n = n.kids[c - 'a']; if (n.top.size() < 3) n.top.add(x); } }
    java.util.List<java.util.List<String>> out = new java.util.ArrayList<>(); N n = root;
    for (char c : w.toCharArray()) { if (n != null) n = n.kids[c - 'a']; out.add(n == null ? new java.util.ArrayList<>() : n.top); }
    return out;
}
```

- **Time Complexity:** O(total characters)
- **Space Complexity:** O(total characters)

### Approach 3: Sort Once, Binary Search Each Prefix (Optimized)

Sort the products once and binary search the start of each prefix block. This is the version implemented in the repository.

```java
public static java.util.List<java.util.List<String>> binary(String[] in, String w) {
    String[] p = in.clone(); java.util.Arrays.sort(p); java.util.List<java.util.List<String>> out = new java.util.ArrayList<>();
    for (int len = 1; len <= w.length(); len++) { String pre = w.substring(0, len); int lo = 0, hi = p.length; while (lo < hi) { int mid = (lo + hi) / 2; if (p[mid].compareTo(pre) < 0) lo = mid + 1; else hi = mid; }
        java.util.List<String> s = new java.util.ArrayList<>(); for (int i = lo; i < Math.min(lo + 3, p.length) && p[i].startsWith(pre); i++) s.add(p[i]); out.add(s); }
    return out;
}
```

- **Time Complexity:** O(n log n + L log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Filter and Sort for Every Prefix | O(L * n log n) | O(n) |
| 2 | Trie with Stored Suggestions | O(total characters) | O(total characters) |
| 3 | Sort Once, Binary Search Each Prefix (Optimized) | O(n log n + L log n) | O(n) |

## Source Code

### SearchSuggestionsSystem.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: in a sorted list, all words that share a prefix sit next to each other, starting at the first word that is greater than or equal to the prefix. Binary search finds that spot and the next three words are the suggestions.

**Input:** `products = [mobile, mouse, moneypot, monitor, mousepad]` (sorted: `mobile, moneypot, monitor, mouse, mousepad`), `searchWord = "mouse"`.

| Typed | First word >= prefix | Suggestions |
|-------|----------------------|-------------|
| m | mobile | mobile, moneypot, monitor |
| mo | mobile | mobile, moneypot, monitor |
| mou | mouse | mouse, mousepad |
| mous | mouse | mouse, mousepad |
| mouse | mouse | mouse, mousepad |

**Output:** `[[mobile, moneypot, monitor], [mobile, moneypot, monitor], [mouse, mousepad], [mouse, mousepad], [mouse, mousepad]]`.
